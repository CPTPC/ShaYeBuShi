package shayebushi.entities.bullet;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Nullable;
import arc.util.Tmp;
import mindustry.content.StatusEffects;
import mindustry.entities.Damage;
import mindustry.entities.Fires;
import mindustry.entities.UnitSorts;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.world.blocks.ConstructBlock;
import shayebushi.entities.SYBSDamage;

import static mindustry.Vars.indexer;

public class BaiFenBiBulletType extends BulletType {
    public float baifenbi = -1 ;
    public boolean wushixianshang = true ;
    public boolean zuida = true ;
    public Color backColor = Pal.bulletYellowBack, frontColor = Pal.bulletYellow;
    public Color mixColorFrom = new Color(1f, 1f, 1f, 0f), mixColorTo = new Color(1f, 1f, 1f, 0f);
    public float width = 5f, height = 7f;
    public float shrinkX = 0f, shrinkY = 0.5f;
    public Interp shrinkInterp = Interp.linear;
    public float spin = 0, rotationOffset = 0f;
    public String sprite;
    public @Nullable
    String backSprite;
    public float fanweibaifenbi = -1 ;
    public TextureRegion backRegion;
    public TextureRegion frontRegion;
    static final EventType.UnitDamageEvent bulletDamageEvent = new EventType.UnitDamageEvent();
    public BaiFenBiBulletType(float speed,float damage,float baifenbi,String bulletSprite){
        super(speed,damage);
        this.baifenbi = baifenbi ;
        this.sprite = bulletSprite;
    }
    public BaiFenBiBulletType(float speed, float damage,float baifenbi){
        this(speed, damage, baifenbi,"bullet");
    }
    public BaiFenBiBulletType(){
        this(1f, 1f, 1f,"bullet");
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
            if (this.baifenbi > 0) {
                build.health -= this.baifenbi * (zuida ? build.maxHealth() : build.health());
            }
            else {
                build.health -= this.damage ;
            }
        }

        handlePierce(b, initialHealth, x, y);
    }
    @Override
    public void load(){
        super.load();

        backRegion = Core.atlas.find(backSprite == null ? (sprite + "-back") : backSprite);
        frontRegion = Core.atlas.find(sprite);
    }
    @Override
    public void draw(Bullet b){
        super.draw(b);
        float shrink = shrinkInterp.apply(b.fout());
        float height = this.height * ((1f - shrinkY) + shrinkY * shrink);
        float width = this.width * ((1f - shrinkX) + shrinkX * shrink);
        float offset = -90 + (spin != 0 ? Mathf.randomSeed(b.id, 360f) + b.time * spin : 0f) + rotationOffset;

        Color mix = Tmp.c1.set(mixColorFrom).lerp(mixColorTo, b.fin());

        Draw.mixcol(mix, mix.a);

        if(backRegion.found()){
            Draw.color(backColor);
            Draw.rect(backRegion, b.x, b.y, width, height, b.rotation() + offset);
        }

        Draw.color(frontColor);
        Draw.rect(frontRegion, b.x, b.y, width, height, b.rotation() + offset);

        Draw.reset();
    }
    @Override
    public void createSplashDamage(Bullet b, float x, float y){
        ObjectMap<Float,Unit> un = new ObjectMap() ;//空Map，用来储存范围内所有单位及其最大生命值
        //获取范围内所有单位
        Units.nearby(null,b.x,b.y,splashDamageRadius,u ->{
            //如果当前单位的队伍不等于子弹的队伍就往un里添加键值对（键为最大生命值，值为单位）
            if (u.team != b.team){
                un.put(u.maxHealth,u) ;
            }
            //到这里还很正常
        });
        //手动获取un里最大的键(逐渐开始离谱）
        float max = 0;
        if (!un.isEmpty()) {
            for (float f : un.keys()) {
                if (f > max) {
                    max = f;
                }
            }
        }
        Unit unit = un.get(max) ;//获取最大生命值最高的单位(为什么还要找单位，max已经是最大生命值最高的单位的最大生命值了)
        float maxx ;
        /**这里是后来的CPTPC，第151行有可能抛出空指针，所以就先改了*/
        try {
            maxx = un.isEmpty() ? 0 : unit.maxHealth;//获取最大生命值最高的单位的最大生命值(max == maxx)
        }
        catch (Throwable t) {
            maxx = max ;
        }
        /*
         * 我为什么不直接搞一个空Seq用来存放最大生命值然后获取范围内单位经过判断存放最大生命值，像这样
         * Seq<Float> s = new Seq<>() ;
         * Units.nearBy(null,b.x,b.y,splashDamageRadius,u->{if(u.team!=b.team)s.add(u.maxHealth);});
         * float max = 0 ;
         * for (float f : s){if(f > max)max = f;}
         *
         */
        Seq<Float> s = new Seq<Float>() ;
        Units.nearbyBuildings(b.x,b.y,splashDamageRadius,bb -> {
            if (bb.team != b.team){
                s.add(bb.maxHealth) ;
            }
        });
        float maxxx = 0;
        if (!s.isEmpty()) {
            for (float f : s) {
                if (f > maxxx) {
                    maxxx = f;
                }
            }
        }
        if(splashDamageRadius > 0 && !b.absorbed){
            if (fanweibaifenbi > 0) {
                SYBSDamage.damage(b.team, x, y, splashDamageRadius, fanweibaifenbi * Math.max(maxx, maxxx) * b.damageMultiplier(), splashDamagePierce, collidesAir, collidesGround, scaledSplashDamage, b);
            }
            else if (fanweibaifenbi == 0){
                SYBSDamage.damage(b.team, x, y, splashDamageRadius, splashDamage, splashDamagePierce, collidesAir, collidesGround, scaledSplashDamage, b);
            }

            if(status != StatusEffects.none){
                Damage.status(b.team, x, y, splashDamageRadius, status, statusDuration, collidesAir, collidesGround);
            }

            if(heals()){
                indexer.eachBlock(b.team, x, y, splashDamageRadius, Building::damaged, other -> {
                    healEffect.at(other.x, other.y, 0f, healColor, other.block);
                    other.heal(healPercent / 100f * other.maxHealth() + healAmount);
                });
            }

            if(makeFire){
                indexer.eachBlock(null, x, y, splashDamageRadius, other -> other.team != b.team, other -> Fires.create(other.tile));
            }
        }
    }
}
