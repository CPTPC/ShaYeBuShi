package shayebushi.entities.bullet;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Rand;
import arc.util.Nullable;
import arc.util.Tmp;
import mindustry.entities.Fires;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.world.blocks.ConstructBlock;
import shayebushi.SYBSShenShengUnitTypes;
import shayebushi.SYBSUnitTypes;

import java.util.Random;

public class GaiLvMiaoBulletType extends BulletType {
    public float gailv ;
    public Color backColor = Pal.bulletYellowBack, frontColor = Pal.bulletYellow;
    public Color mixColorFrom = new Color(1f, 1f, 1f, 0f), mixColorTo = new Color(1f, 1f, 1f, 0f);
    public float width = 5f, height = 7f;
    public float shrinkX = 0f, shrinkY = 0.5f;
    public Interp shrinkInterp = Interp.linear;
    public float spin = 0, rotationOffset = 0f;
    public String sprite;
    public @Nullable
    String backSprite;

    public TextureRegion backRegion;
    public TextureRegion frontRegion;
    static final EventType.UnitDamageEvent bulletDamageEvent = new EventType.UnitDamageEvent();
    public Rand r = new Rand() ;
    public GaiLvMiaoBulletType(float speed, float damage, float gailv,String bulletSprite){
        super(speed,damage);
        this.gailv = gailv ;
        this.sprite = bulletSprite;
    }
    public GaiLvMiaoBulletType(float speed, float damage,float gailv){
        this(speed, damage, gailv,"bullet");
    }
    public GaiLvMiaoBulletType(){
        this(1f, 1f, 1f,"bullet");
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
}
