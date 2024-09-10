package shayebushi.entities.bullet;

import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.part.FlarePart;
import mindustry.entities.part.HaloPart;
import mindustry.entities.part.HoverPart;
import mindustry.entities.part.ShapePart;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.Block;
import mindustry.world.blocks.ConstructBlock;
import shayebushi.SYBSFx;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.stroke;

public class JiDianBulletType extends BasicBulletType {
    public float damageInterval = 1f;
    static final EventType.UnitDamageEvent bulletDamageEvent = new EventType.UnitDamageEvent();
    public float range = 200 ;
    public boolean chuansong = false ;
    public float t = 0 ;
    public JiDianBulletType(float speed , float damage){
        super(speed, damage);
        this.collides = true ;
        this.speed = speed ;
        this.chuansong = false ;
        this.damage = damage ;
        this.hitSize = 40 ;
        this.width = 80 ;
        this.height = 80 ;
        this.backColor = Color.valueOf("222222") ;
        this.lifetime = 300 ;
        this.pierce = true ;
        //this.pierceBuilding = true ;
        //this.pierceCap = 114 ;
        //this.trailEffect = SYBSFx.jidianshifang ;
        this.hitEffect = Fx.none;
        this.intervalBullets = 30 ;
        this.intervalDelay = 0.5f ;
        this.intervalAngle = 15f ;
        this.hitShake = this.despawnShake = 6 ;
        this.parts.add(new HaloPart(){{
            color = Pal.redderDust;
            layer = Layer.effect;
            y = 0;
            haloRotateSpeed = 3f;

            shapes = 2;
            shapeRotation = 180f;
            triLength = 80f;
            triLengthTo = 80f;
            haloRotation = 45f;
            haloRadius = 0f;
            tri = true;
            radius = 10f;
        }}, new HaloPart(){{
            color = Pal.redderDust;
            layer = Layer.effect;
            y = 0;
            haloRotateSpeed = 3f;

            shapes = 2;
            shapeRotation = 180f;
            triLength = 80f;
            triLengthTo = 80f;
            haloRotation = 135f;
            haloRadius = 0f;
            tri = true;
            radius = 10f;
        }}) ;
        this.fragBullets = 1 ;
        this.fragBullet = new JiDianBulletTypee(0,400){{
            chuansong = true ;
        }};
        this.fragOnHit = false ;
        load();
    }
    @Override
    public float continuousDamage(){
        return damage * 60 / damageInterval ;
    }
    public void applyDamage(Bullet b){
        Damage.collidePoint(b, b.team, hitEffect, b.x, b.y);
        //this.damage *= 2 ;
    }
    @Override
    public void update(Bullet b){
        super.update(b);
        if (!collides) return;
        t ++ ;
        if (t + Time.delta >= damageInterval){
            applyDamage(b);
            float rx = Tmp.v1.x, ry = Tmp.v1.y;
            if (chuansong) {
                Units.nearby(null, b.x, b.y, range, other -> {
                    if (other.team != b.team) {
                        other.x = b.x + new Rand().random(-80,80);
                        other.y = b.y + new Rand().random(-80,80);
                    }
                });
            }
            t = 0 ;
        }
    }
    @Override
    public void draw(Bullet b){
        super.draw(b) ;
        if (b.timer(1,trailInterval)){
            trailEffect.at(b.x,b.y) ;
        }
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
            build.health -= damage ;
        }

    }
    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        boolean wasDead = entity instanceof Unit u && u.dead;

        if(entity instanceof Healthc h){
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
            unit.health -= damage;
            Events.fire(bulletDamageEvent.set(unit, b));
        }

        if(!wasDead && entity instanceof Unit unit && unit.dead){
            Events.fire(new EventType.UnitBulletDestroyEvent(unit, b));
        }

    }
    public void hit(Bullet b, float x, float y){
        hitEffect.at(x, y, b.rotation(), hitColor);
        hitSound.at(x, y, hitSoundPitch, hitSoundVolume);

        Effect.shake(hitShake, hitShake, b);

        if(fragOnHit){
            createFrags(b, x, y);
        }
        createPuddles(b, x, y);
        createIncend(b, x, y);
        createUnits(b, x, y);
        if(suppressionRange > 0){
            //bullets are pooled, require separate Vec2 instance
            Damage.applySuppression(b.team, b.x, b.y, suppressionRange, suppressionDuration, 0f, suppressionEffectChance, new Vec2(b.x, b.y));
        }

        createSplashDamage(b, x, y);

        for(int i = 0; i < lightning; i++){
            Lightning.create(b, lightningColor, lightningDamage < 0 ? damage : lightningDamage, b.x, b.y, b.rotation() + Mathf.range(lightningCone/2) + lightningAngle, lightningLength + Mathf.random(lightningLengthRand));
        }
    }
}
