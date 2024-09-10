package shayebushi.entities.bullet;

import arc.func.Boolf;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Hitboxc;
import mindustry.graphics.Trail;
import shayebushi.SYBSFx;
import shayebushi.SYBSPal;
import shayebushi.ShaYeBuShi;

import static mindustry.Vars.headless;
import static mindustry.Vars.tilesize;

public class RotateBullet extends LightningContinuousLaserBulletType implements WuShiXianShangc {
    public float rotateSpeed = -3f ;
    public boolean overrideRotation = true ;
    public Effect rotateEffect = Fx.none ;
    public float startRotation = 45 ;
    public boolean overrideBullet = true ;
    public Boolf<Bullet> can = b -> false ;
    public RotateBullet() {
        super() ;
        fadeTime = 1 ;
        lightningColor = SYBSPal.yaofeng ;
        reload = 2 ;
    }
    @Override
    public void init() {
        trailLength = (int)(length / tilesize) ;
        trailWidth = tilesize ;
    }
    @Override
    public void update(Bullet b) {
        if (!(b.data instanceof Data)) {
            b.data = new Data() ;
        }
        super.update(b);
        updateTrail(b);
        b.rotation(b.rotation() + rotateSpeed) ;
        ((Data)b.data).update(b);
    }
    @Override
    public void draw(Bullet b) {
        super.draw(b) ;
        drawTrail(b);
    }
    @Override
    public void removed(Bullet b) {
        super.removed(b);
        ((Data)b.data).begin = true ;
        Vec2 v = ShaYeBuShi.circle(b.rotation(), length, b.x, b.y) ;
        for (Bullet bi : ((Data)b.data).bs) {
            SYBSFx.fixedChainLightning.at(v.x, v.y, 6, lightningColor, bi);
        }
    }
    @Override
    public void init(Bullet b) {
        super.init(b);
        b.rotation(b.rotation() + startRotation);
    }
    @Override
    public void updateBulletInterval(Bullet b){
        if(intervalBullet != null && b.time >= intervalDelay && b.timer.get(2, bulletInterval)){
            float ang = b.rotation();
            Vec2 v = ShaYeBuShi.circle(ang, length, b.x, b.y) ;
            //System.out.println(v);
            rotateEffect.at(v.x, v.y, 0, b);
            for(int i = 0; i < intervalBullets; i++){
                ((Data)b.data).rawPos.add(new Vec2(v.x, v.y)) ;
                ((Data)b.data).bs.add(intervalBullet.create(b, v.x, v.y, overrideRotation ? ang : ang + Mathf.range(intervalRandomSpread) + intervalAngle + ((i - (intervalBullets - 1f)/2f) * intervalSpread)));
                if (overrideBullet) {
                    for (float z = 0 ; z <= Math.abs(rotateSpeed) ; z ++) {
                        float ang2 = b.rotation() - z * (Math.abs(rotateSpeed) / rotateSpeed);
                        Vec2 v2 = ShaYeBuShi.circle(ang2, length, b.x, b.y) ;
                        ((Data)b.data).rawPos.add(new Vec2(v2.x, v2.y)) ;
                        ((Data)b.data).bs.add(intervalBullet.create(b, v2.x, v2.y, overrideRotation ? ang2 : ang2 + Mathf.range(intervalRandomSpread) + intervalAngle + ((i - (intervalBullets - 1f)/2f) * intervalSpread)));
                    }
                }
            }
        }
    }
    @Override
    public void updateTrail(Bullet b){
        if(!headless && trailLength > 0){
            if (!(b.data instanceof Data d)) {
                return ;
            }
            if (d.ts.size == 0) {
                for (int i = 0 ; i < trailLength ; i ++) {
                    d.ts.add(new Trail(i * 2)) ;
                }
            }
            for (int i = 0 ; i < trailLength ; i ++) {
                d.ts.get(i).length = i * 2 ;
                float ang = b.rotation();
                Vec2 v = ShaYeBuShi.circle(ang, i * tilesize, b.x, b.y);
                d.ts.get(i).update(v.x, v.y, trailInterp.apply(b.fin()) * (1f + (trailSinMag > 0 ? Mathf.absin(Time.time, trailSinScl, trailSinMag) : 0f)));
            }
        }
    }
    @Override
    public void drawTrail(Bullet b){
        if(trailLength > 0 && b.data instanceof Data d){
            //draw below bullets? TODO
            float z = Draw.z();
            Draw.z(z - 0.0001f);
            for (Trail trail : d.ts) {
                trail.draw(trailColor, trailWidth);
            }
            Draw.z(z);
        }
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
    public class Data {
        public Seq<Trail> ts = new Seq<>() ;
        public Seq<Bullet> bs = new Seq<>() ;
        public Seq<Vec2> rawPos = new Seq<>() ;
        public boolean begin = false ;
        public void update(Bullet b) {
            if (!begin) {
                Vec2 v = ShaYeBuShi.circle(b.rotation(), length, b.x, b.y) ;
                for (Bullet bi : bs) {
                    bi.set(rawPos.get(bs.indexOf(bi)));
                    //System.out.println(b.timer().getTime(2)) ;
                    if (b.timer(3, reload)) {
                        SYBSFx.fixedChainLightning.at(v.x, v.y, 6, lightningColor, bi);
                    }
                    bi.time = 0;
                }
            }
        }
    }
}
