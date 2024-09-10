package shayebushi.entities.bullet;

import arc.Events;
import arc.math.geom.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Fires;
import mindustry.entities.Units;
import mindustry.entities.bullet.PointBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.effect.WrapEffect;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Hitboxc;
import mindustry.gen.Unit;
import mindustry.world.blocks.ConstructBlock;
import shayebushi.SYBSShenShengUnitTypes;

public class CeFanBulletType extends PointBulletType {
    private static float cdist = 0f;
    private static Unit result;
    static final EventType.UnitDamageEvent bulletDamageEvent = new EventType.UnitDamageEvent();
    public float trailSpacing = 10f;
    public float cefanshangxian = 1;
    public CeFanBulletType(){
        scaleLife = true;
        lifetime = 100f;
        collides = false;
        reflectable = false;
        keepVelocity = false;
        backMove = false;
    }
    @Override
    public void init(Bullet b){
        if(killShooter && b.owner() instanceof Healthc h && !h.dead()){
            h.kill();
        }

        if(instantDisappear){
            b.time = lifetime + 1f;
        }

        if(spawnBullets.size > 0){
            for(var bullet : spawnBullets){
                bullet.create(b, b.x, b.y, b.rotation());
            }
        }
        trailColor = b.team.color ;
        hitColor = b.team.color ;
        hitEffect = new MultiEffect(Fx.massiveExplosion, new WrapEffect(Fx.dynamicSpikes, b.team.color, 24f), new WaveEffect(){{
            //colorFrom = colorTo = color;
            colorFrom = colorTo = b.team.color;
            sizeTo = 40f;
            lifetime = 12f;
            strokeFrom = 4f;
        }});
        float px = b.x + b.lifetime * b.vel.x,
                py = b.y + b.lifetime * b.vel.y,
                rot = b.rotation();

        Geometry.iterateLine(0f, b.x, b.y, px, py, trailSpacing, (x, y) -> {
            trailEffect.at(x, y, rot, trailColor);
        });

        b.time = b.lifetime;
        b.set(px, py);

        //calculate hit entity

        cdist = 0f;
        result = null;
        float range = 1f;

        Units.nearbyEnemies(b.team, px - range, py - range, range*2f, range*2f, e -> {
            if(e.dead() || !e.checkTarget(collidesAir, collidesGround) || !e.hittable()) return;

            e.hitbox(Tmp.r1);
            if(!Tmp.r1.contains(px, py)) return;

            float dst = e.dst(px, py) - e.hitSize;
            if((result == null || dst < cdist)){
                result = e;
                cdist = dst;
            }
        });

        if(result != null){
            b.collision(result, px, py);
        }else if(collidesTiles){
            Building build = Vars.world.buildWorld(px, py);
            if(build != null && build.team != b.team){
                build.collision(b);
            }
        }

        b.remove();

        b.vel.setZero();
    }
    @Override
    public void updateTrailEffects(Bullet b){
        super.updateTrailEffects(b);

    }
    @Override
    public void hitTile(Bullet b, Building build, float x, float y, float initialHealth, boolean direct){
        if(makeFire && build.team != b.team){
            Fires.create(build.tile);
        }

        if(heals() && build.team == b.team && !(build.block instanceof ConstructBlock)){
            healEffect.at(build.x, build.y, 0f, healColor, build.block);
            build.heal(healPercent / 100f * build.maxHealth + healAmount);
        }else if(build.team != b.team && direct){
            hit(b);
        }
        if (build.health < cefanshangxian) {
            build.changeTeam(b.team);
        }
        handlePierce(b, initialHealth, x, y);
    }
    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        boolean wasDead = entity instanceof Unit u && u.dead;

        if(entity instanceof mindustry.gen.Healthc h){
            if(pierceArmor){
                h.damagePierce(b.damage);
            }else{
                h.damage(b.damage);
            }
        }

        if(entity instanceof Unit unit){
            Tmp.v3.set(unit).sub(b).nor().scl(knockback * 80f);
            if(impact) Tmp.v3.setAngle(b.rotation() + (knockback < 0 ? 180f : 0f));
            unit.impulse(Tmp.v3);
            unit.apply(status, statusDuration);

            Events.fire(bulletDamageEvent.set(unit, b));
            if (b.team != unit.team && unit.health < this.cefanshangxian){
                unit.team(b.team);
            }
            //unit.team=b.team;
        }

        if(!wasDead && entity instanceof Unit unit && unit.dead){
            Events.fire(new EventType.UnitBulletDestroyEvent(unit, b));
        }
        handlePierce(b, health, entity.x(), entity.y());
    }

}
