package shayebushi.entities;

import arc.Core;
import arc.Events;
import arc.func.Cons;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.math.geom.Rect;
import arc.math.geom.Vec2;
import arc.struct.IntFloatMap;
import arc.util.Nullable;
import mindustry.core.World;
import mindustry.entities.Units;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.gen.Unit;
import mindustry.world.Tile;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;
import static shayebushi.entities.bullet.WuShiXianShangc.bulletDamageEvent;

public class SYBSDamage {
    public static Vec2 vec = new Vec2(), seg1 = new Vec2(), seg2 = new Vec2();
    public static Rect rect = new Rect();
    public static IntFloatMap damages = new IntFloatMap();
    public static float calculateDamage(float dist, float radius, float damage){
        float falloff = 0.4f;
        float scaled = Mathf.lerp(1f - dist / radius, 1f, falloff);
        return damage * scaled;
    }
    public static void tileDamage(Team team, int x, int y, float baseRadius, float damage, @Nullable Bullet source){
        Core.app.post(() -> {
            var in = world.build(x, y);
            if(in != null && in.team != team && in.block.size > 1 && in.health > damage){
                in.damage(team, 0);
                in.health -= damage * Math.min((in.block.size), baseRadius * 0.4f) ;
                return;
            }

            float radius = Math.min(baseRadius, 100), rad2 = radius * radius;
            int rays = Mathf.ceil(radius * 2 * Mathf.pi);
            double spacing = Math.PI * 2.0 / rays;
            damages.clear();

            for(int i = 0; i <= rays; i++){
                float dealt = 0f;
                int startX = x;
                int startY = y;
                int endX = x + (int)(Math.cos(spacing * i) * radius), endY = y + (int)(Math.sin(spacing * i) * radius);

                int xDist = Math.abs(endX - startX);
                int yDist = -Math.abs(endY - startY);
                int xStep = (startX < endX ? +1 : -1);
                int yStep = (startY < endY ? +1 : -1);
                int error = xDist + yDist;

                while(startX != endX || startY != endY){
                    var build = world.build(startX, startY);
                    if(build != null && build.team != team){
                        //damage dealt at circle edge
                        float edgeScale = 0.6f;
                        float mult = (1f-(Mathf.dst2(startX, startY, x, y) / rad2) + edgeScale) / (1f + edgeScale);
                        float next = damage * mult - dealt;
                        //register damage dealt
                        int p = Point2.pack(startX, startY);
                        damages.put(p, Math.max(damages.get(p), next));
                        //register as hit
                        dealt += build.health;

                        if(next - dealt <= 0){
                            break;
                        }
                    }

                    if(2 * error - yDist > xDist - 2 * error){
                        error += yDist;
                        startX += xStep;
                    }else{
                        error += xDist;
                        startY += yStep;
                    }
                }
            }

            //apply damage
            for(var e : damages){
                int cx = Point2.x(e.key), cy = Point2.y(e.key);
                var build = world.build(cx, cy);
                if(build != null){
                    if(source != null){
                        build.damage(source, team, 0);
                        build.health -= e.value ;
                    }else{
                        build.damage(team, 0);
                        build.health -= e.value ;
                    }
                }
            }
        });
    }
    private static void completeDamage(Team team, float x, float y, float radius, float damage){

        int trad = (int)(radius / tilesize);
        for(int dx = -trad; dx <= trad; dx++){
            for(int dy = -trad; dy <= trad; dy++){
                Tile tile = world.tile(Math.round(x / tilesize) + dx, Math.round(y / tilesize) + dy);
                if(tile != null && tile.build != null && (team == null || team != tile.team()) && dx*dx + dy*dy <= trad*trad){
                    tile.build.damage(team, 0);
                    tile.build.health -= damage;
                }
            }
        }
    }

    public static void damage(Team team, float x, float y, float radius, float damage, boolean complete, boolean air, boolean ground, boolean scaled, @Nullable Bullet source){
        Cons<Unit> cons = unit -> {
            if(unit.team == team  || !unit.checkTarget(air, ground) || !unit.hittable() || !unit.within(x, y, radius + (scaled ? unit.hitSize / 2f : 0f))){
                return;
            }

            boolean dead = unit.dead;

            float amount = calculateDamage(scaled ? Math.max(0, unit.dst(x, y) - unit.type.hitSize/2) : unit.dst(x, y), radius, damage);
            unit.health -= amount ;

            if(source != null){
                Events.fire(bulletDamageEvent.set(unit, source));
                unit.controller().hit(source);

                if(!dead && unit.dead){
                    Events.fire(new EventType.UnitBulletDestroyEvent(unit, source));
                }
            }
            //TODO better velocity displacement
            float dst = vec.set(unit.x - x, unit.y - y).len();
            unit.vel.add(vec.setLength((1f - dst / radius) * 2f / unit.mass()));

            if(complete && damage >= 9999999f && unit.isPlayer()){
                Events.fire(EventType.Trigger.exclusionDeath);
            }
        };

        rect.setSize(radius * 2).setCenter(x, y);
        if(team != null){
            Units.nearbyEnemies(team, rect, cons);
        }else{
            Units.nearby(rect, cons);
        }

        if(ground){
            if(!complete){
                tileDamage(team, World.toTile(x), World.toTile(y), radius / tilesize, damage * (source == null ? 1f : source.type.buildingDamageMultiplier), source);
            }else{
                completeDamage(team, x, y, radius, damage);
            }
        }
    }

}
