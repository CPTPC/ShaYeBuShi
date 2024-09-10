package shayebushi.entities.bullet;

import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Fires;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.world.blocks.ConstructBlock;

public class BaiFenBiChiXuJiGuangBulletType extends LightningContinuousLaserBulletType {
    public float length = 220f;
    public float shake = 0f;
    public float damageInterval = 5f;
    public boolean largeHit = false;
    public boolean continuous = true;
    public float baifenbi = -1 ;
    public boolean wushixianshang = true ;
    public boolean zuida = true ;
    static final EventType.UnitDamageEvent bulletDamageEvent = new EventType.UnitDamageEvent();
    {
        removeAfterPierce = false;
        pierceCap = -1;
        speed = 0f;
        despawnEffect = Fx.none;
        shootEffect = Fx.none;
        lifetime = 16f;
        impact = true;
        keepVelocity = false;
        collides = false;
        pierce = true;
        hittable = false;
        absorbable = false;
        lightningColor = Pal.accent ;
    }
    @Override
    public void draw(Bullet b){
        float fout = Mathf.clamp(b.time > b.lifetime - fadeTime ? 1f - (b.time - (lifetime - fadeTime)) / fadeTime : 1f);
        float realLength = Damage.findLength(b, length * fout, laserAbsorb, pierceCap);
        float rot = b.rotation();

        for(int i = 0; i < colors.length; i++){
            Draw.color(Tmp.c1.set(colors[i]).mul(1f + Mathf.absin(Time.time, 1f, 0.1f)));

            float colorFin = i / (float)(colors.length - 1);
            float baseStroke = Mathf.lerp(strokeFrom, strokeTo, colorFin);
            float stroke = (width + Mathf.absin(Time.time, oscScl, oscMag)) * fout * baseStroke;
            float ellipseLenScl = Mathf.lerp(1 - i / (float)(colors.length), 1f, pointyScaling);

            Lines.stroke(stroke);
            Lines.lineAngle(b.x, b.y, rot, realLength - frontLength, false);

            //back ellipse
            Drawf.flameFront(b.x, b.y, divisions, rot + 180f, backLength, stroke / 2f);

            //front ellipse
            Tmp.v1.trnsExact(rot, realLength - frontLength);
            Drawf.flameFront(b.x + Tmp.v1.x, b.y + Tmp.v1.y, divisions, rot, frontLength * ellipseLenScl, stroke / 2f);
        }
        for (int i = 0 ; i < 9 ; i ++) {
            Effect effect2 = Fx.lightningCharge;
            effect2.at(b.owner instanceof Posc p ? p.getX() : 0, b.owner instanceof Posc p ? p.getY() : 0, 0f, Color.darkGray, new Position() {
                @Override
                public float getX() {
                    Vec2 v = new Vec2() ;
                    Rand r = new Rand() ;
                    v.trns(b.rotation(), realLength - frontLength) ;
                    return v.x + r.random(-8, 8);
                }

                @Override
                public float getY() {
                    Vec2 v = new Vec2() ;
                    Rand r = new Rand() ;
                    v.trns(b.rotation(), realLength - frontLength) ;
                    return v.y + r.random(-8, 8);
                }
            });
        }
        Tmp.v1.trns(b.rotation(), realLength * 1.1f);

        Drawf.light(b.x, b.y, b.x + Tmp.v1.x, b.y + Tmp.v1.y, lightStroke, lightColor, 0.7f);
        Draw.reset();
    }

    @Override
    public float continuousDamage(){
        if(!continuous) return -1f;
        return damage / damageInterval * 60f;
    }

    @Override
    public float estimateDPS(){
        if(!continuous) return super.estimateDPS();
        //assume firing duration is about 100 by default, may not be accurate there's no way of knowing in this method
        //assume it pierces 3 blocks/units
        return damage * 100f / damageInterval * 3f;
    }

    @Override
    protected float calculateRange(){
        return Math.max(length, maxRange);
    }

    @Override
    public void init(){
        super.init();

        drawSize = Math.max(drawSize, length*2f);
    }

    @Override
    public void init(Bullet b){
        super.init(b);

        if(!continuous){
            applyDamage(b);
        }
    }

    @Override
    public void update(Bullet b){
        if(!continuous) return;

        //damage every 5 ticks
        if(b.timer(1, damageInterval)){
            applyDamage(b);
        }

        if(shake > 0){
            Effect.shake(shake, shake, b);
        }

        updateBulletInterval(b);
    }

    public void applyDamage(Bullet b){
        Damage.collideLine(b, b.team, hitEffect, b.x, b.y, b.rotation(), currentLength(b), largeHit, laserAbsorb, pierceCap);
    }

    public float currentLength(Bullet b){
        return length;
    }
    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        boolean wasDead = entity instanceof Unit u && u.dead;

        if(entity instanceof Healthc h && b.type == this){
            if (wushixianshang) {
                if (baifenbi == 0) {
                    h.health(h.health() - damage);
                }
                h.health(h.health() - this.baifenbi * (zuida ? h.maxHealth() : h.health()));
            }
            else {
                if (pierceArmor) {
                    if (baifenbi == 0) {
                        h.damagePierce(damage);
                    }
                    h.damagePierce(this.baifenbi * (zuida ? h.maxHealth() : h.health()));
                }
                else {
                    if (baifenbi == 0) {
                        h.damage(damage);
                    }
                    h.damage(this.baifenbi * (zuida ? h.maxHealth() : h.health()));
                }
            }
        }

        if(entity instanceof Unit unit){
            Tmp.v3.set(unit).sub(b).nor().scl(knockback * 80f);
            if(impact) Tmp.v3.setAngle(b.rotation() + (knockback < 0 ? 180f : 0f));
            unit.impulse(Tmp.v3);
            unit.apply(status, statusDuration);
            Events.fire(bulletDamageEvent.set(unit, b));
        }

        if(!wasDead && entity instanceof Unit unit && unit.dead){
            Events.fire(new EventType.UnitBulletDestroyEvent(unit, b));
        }

        handlePierce(b, health, entity.x(), entity.y());
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

        handlePierce(b, initialHealth, x, y);
    }
}
