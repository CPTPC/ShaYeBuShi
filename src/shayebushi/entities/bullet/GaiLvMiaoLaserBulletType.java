package shayebushi.entities.bullet;

import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.Rand;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Fires;
import mindustry.entities.Lightning;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.world.blocks.ConstructBlock;
import shayebushi.SYBSShenShengUnitTypes;
import shayebushi.SYBSUnitTypes;

public class GaiLvMiaoLaserBulletType extends LaserBulletType {
    public Color[] colors = {Pal.lancerLaser.cpy().mul(1f, 1f, 1f, 0.4f), Pal.lancerLaser, Color.white};
    public Effect laserEffect = Fx.lancerLaserShootSmoke;
    public float length = 160f;
    public float width = 15f;
    public float lengthFalloff = 0.5f;
    public float sideLength = 29f, sideWidth = 0.7f;
    public float sideAngle = 90f;
    public float lightningSpacing = -1, lightningDelay = 0.1f, lightningAngleRand;
    public boolean largeHit = false;
    static final EventType.UnitDamageEvent bulletDamageEvent = new EventType.UnitDamageEvent();
    public float gailv ;
    public Rand r = new Rand() ;
    public GaiLvMiaoLaserBulletType(float damage, float gailv){
        this.damage = damage;
        this.speed = 0f;
        this.gailv = gailv ;
        hitEffect = Fx.hitLaserBlast;
        hitColor = colors[2];
        despawnEffect = Fx.none;
        shootEffect = Fx.hitLancer;
        smokeEffect = Fx.none;
        hitSize = 4;
        lifetime = 16f;
        impact = true;
        keepVelocity = false;
        collides = false;
        pierce = true;
        hittable = false;
        absorbable = false;
        removeAfterPierce = false;
    }

    public GaiLvMiaoLaserBulletType(){
        this(1f,1f);
    }

    //assume it pierces at least 3 blocks
    @Override
    public float estimateDPS(){
        return super.estimateDPS() * 3f;
    }

    @Override
    public void init(){
        super.init();

        drawSize = Math.max(drawSize, length*2f);
    }

    @Override
    protected float calculateRange(){
        return Math.max(length, maxRange);
    }

    @Override
    public void init(Bullet b){
        float resultLength = Damage.collideLaser(b, length, largeHit, laserAbsorb, pierceCap), rot = b.rotation();

        laserEffect.at(b.x, b.y, rot, resultLength * 0.75f);

        if(lightningSpacing > 0){
            int idx = 0;
            for(float i = 0; i <= resultLength; i += lightningSpacing){
                float cx = b.x + Angles.trnsx(rot,  i),
                        cy = b.y + Angles.trnsy(rot, i);

                int f = idx++;

                for(int s : Mathf.signs){
                    Time.run(f * lightningDelay, () -> {
                        if(b.isAdded() && b.type == this){
                            Lightning.create(b, lightningColor,
                                    lightningDamage < 0 ? damage : lightningDamage,
                                    cx, cy, rot + 90*s + Mathf.range(lightningAngleRand),
                                    lightningLength + Mathf.random(lightningLengthRand));
                        }
                    });
                }
            }
        }
    }

    @Override
    public void draw(Bullet b){
        super.draw(b) ;
        float realLength = b.fdata;

        float f = Mathf.curve(b.fin(), 0f, 0.2f);
        float baseLen = realLength * f;
        float cwidth = width;
        float compound = 1f;

        Lines.lineAngle(b.x, b.y, b.rotation(), baseLen);
        for(Color color : colors){
            Draw.color(color);
            Lines.stroke((cwidth *= lengthFalloff) * b.fout());
            Lines.lineAngle(b.x, b.y, b.rotation(), baseLen, false);
            Tmp.v1.trns(b.rotation(), baseLen);
            Drawf.tri(b.x + Tmp.v1.x, b.y + Tmp.v1.y, Lines.getStroke(), cwidth * 2f + width / 2f, b.rotation());

            Fill.circle(b.x, b.y, 1f * cwidth * b.fout());
            for(int i : Mathf.signs){
                Drawf.tri(b.x, b.y, sideWidth * b.fout() * cwidth, sideLength * compound, b.rotation() + sideAngle * i);
            }

            compound *= lengthFalloff;
        }
        Draw.reset();

        Tmp.v1.trns(b.rotation(), baseLen * 1.1f);
        Drawf.light(b.x, b.y, b.x + Tmp.v1.x, b.y + Tmp.v1.y, width * 1.4f * b.fout(), colors[0], 0.6f);
    }

    @Override
    public void drawLight(Bullet b){
        //no light drawn here
    }
    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        boolean wasDead = entity instanceof Unit u && u.dead;

        if(entity instanceof Healthc h && b.type == this){
            if(pierceArmor){
                h.damagePierce(b.damage);
//                h.damagePierce(this.baifenbi * h.maxHealth());
            }else{
                h.damage(b.damage);
//                h.damage(this.baifenbi * h.maxHealth());
            }
        }

        if(entity instanceof Unit unit){
            Tmp.v3.set(unit).sub(b).nor().scl(knockback * 80f);
            if(impact) Tmp.v3.setAngle(b.rotation() + (knockback < 0 ? 180f : 0f));
            unit.impulse(Tmp.v3);
            unit.apply(status, statusDuration);
            if (r.random(1,this.gailv) == 1 && !SYBSUnitTypes.shendijidanwei.contains(unit.type) && !SYBSShenShengUnitTypes.shenshengjidanwei.contains(unit.type)){
                unit.kill();
            }
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
            if (r.random(1,this.gailv) == 1){
                build.kill();
            }
        }

        handlePierce(b, initialHealth, x, y);
    }

}
