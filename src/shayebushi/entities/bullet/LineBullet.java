package shayebushi.entities.bullet;

import arc.func.Boolf;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Entityc;
import mindustry.gen.Hitboxc;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import shayebushi.ShaYeBuShi;
import shayebushi.entities.units.ZhuTic;

import static mindustry.Vars.tilesize;

public class LineBullet extends BulletType implements WuShiXianShangc {
    public float fadeTime = 16f;
    public float lightStroke = 40f;
    public int divisions = 13;
    public Color[] colors = {Color.valueOf("ec745855"), Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.white};
    public float strokeFrom = 2f, strokeTo = 1.25f, pointyScaling = 0.75f;
    public float backLength = 7f, frontLength = 35f;
    public float width = 9f, oscScl = 0.8f, oscMag = 1.5f;
    public float length = 220f;
    public float shake = 0f;
    public float damageInterval = 5f;
    public boolean largeHit = false;
    public boolean continuous = true;

    public int amount = 5 ;
    public float radius = 5 * tilesize ;
    public Boolf<Bullet> can = b -> {
        Entityc e = b.owner ;
        while (e instanceof Bullet b2) {
            e = b2.owner ;
        }
        return e instanceof ZhuTic z && z.pucongs().size == z.pucongweiAmount();
    } ;
    public boolean overrideRotation = true ;
    public boolean onlyOne = false ;

    public LineBullet(float damage){
        this.damage = damage;
    }

    public LineBullet(){
    }

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
        shake = 1f;
        largeHit = true;
        hitEffect = Fx.hitBeam;
        hitSize = 4;
        drawSize = 420f;
        lifetime = 16f;
        hitColor = colors[2];
        incendAmount = 1;
        incendSpread = 5;
        incendChance = 0.4f;
        lightColor = Color.orange;
        oscMag = 0 ;
    }

    @Override
    public void draw(Bullet b){
        float fout = Mathf.clamp(b.time > b.lifetime - fadeTime ? 1f - (b.time - (lifetime - fadeTime)) / fadeTime : 1f);
        float realLength = Damage.findLength(b, length * fout, laserAbsorb, pierceCap);
        float zs = Draw.z() ;
        for (int z : new int[]{0, 180}) {
            if (z == 0 && onlyOne) continue ;
            float rot = z + b.rotation();

            for (int i = 0; i < colors.length; i++) {
                Draw.alpha(1);
                Color c = Tmp.c1.set(colors[i]).mul(1f + Mathf.absin(Time.time, 1f, 0.1f)) ;
                if (c.r <= 0.2f && c.g <= 0.2f && c.b <= 0.2f) {
                    Draw.z(Layer.endPixeled);
                }
                else {
                    Draw.z(zs) ;
                }
                Draw.color(c.r, c.g, c.b, 1);
                //Draw.mixcol(colors[i], colors[i], 1);
                float colorFin = i / (float) (colors.length - 1);
                float baseStroke = Mathf.lerp(strokeFrom, strokeTo, colorFin);
                float stroke = (width + Mathf.absin(Time.time, oscScl, oscMag)) * fout * baseStroke;
                float ellipseLenScl = Mathf.lerp(1 - i / (float) (colors.length), 1f, pointyScaling);

                //Lines.stroke(stroke);
                Drawf.tri(b.x, b.y, stroke, realLength - frontLength, rot);
                Draw.reset();
            }

            Tmp.v1.trns(z + b.rotation(), realLength * 1.1f);

            Drawf.light(b.x, b.y, b.x + Tmp.v1.x, b.y + Tmp.v1.y, lightStroke, lightColor, 0.7f);
            Draw.reset();
        }
        Draw.z(zs) ;
    }

    @Override
    public void drawLight(Bullet b){
        //no light drawn here
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

        if (overrideRotation) {
            b.rotation(45);
        }

        for (int i = 0 ; i < amount ; i ++) {
            LineBullet l = (LineBullet) copy() ;
            l.amount = 0 ;
            l.length = ShaYeBuShi.r.random(length / 2, length) ;
            Vec2 v = ShaYeBuShi.random(b.x, b.y, radius) ;
            Bullet out = l.create(b, v.x, v.y, b.rotation()) ;
            out.add();
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
        for (int i : new int[]{0, 180}) {
            Damage.collideLine(b, b.team, hitEffect, b.x, b.y, i + b.rotation(), currentLength(b), largeHit, laserAbsorb, pierceCap);
        }
    }
    public float currentLength(Bullet b){
        float fout = Mathf.clamp(b.time > b.lifetime - fadeTime ? 1f - (b.time - (lifetime - fadeTime)) / fadeTime : 1f);
        return length * fout;
    }
    @Override
    public Boolf<Bullet> can() {
        return can ;
    }
    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        poxianshang(this, b, entity);
        handlePierce(b, health, entity.x(), entity.y());
    }
}
