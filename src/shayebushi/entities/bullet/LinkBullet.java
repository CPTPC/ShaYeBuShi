package shayebushi.entities.bullet;

import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ExplosionBulletType;
import mindustry.entities.part.HaloPart;
import mindustry.entities.part.ShapePart;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import shayebushi.SYBSFx;
import shayebushi.ShaYeBuShi;

import static mindustry.Vars.tilesize;
import static shayebushi.SYBSPal.chaokongjian1;
import static shayebushi.SYBSPal.chaokongjian2;

public class LinkBullet extends BasicBulletType {
    public boolean link = true, move = true ;
    public float length = 80 * tilesize ;
    public static Rand rand = new Rand() ;
    @Override
    public void init() {
        super.init() ;
        speed = 0 ;
        float rad = Math.max(height, width), len = 8 ;
        for (int i = 0 ; i < 3 ; i ++) {
            int z = i ;
            parts.add(new HaloPart() {{
                color = frontColor;
                colorTo = backColor;
                shapes = 5;
                tri = true;
                triLength = len;
                haloRadius = rad + z * tilesize + len ;
                radius = 4 * triLength;
                haloRotateSpeed = 2 * (z + 1) ;
                haloRotation = 60 * z ;
            }});
            parts.add(new HaloPart() {{
                color = frontColor;
                colorTo = backColor;
                shapes = 5;
                tri = true;
                triLength = len;
                shapeRotation = 180;
                haloRadius = rad + z * tilesize;
                radius = triLength;
                haloRotateSpeed = 2 * (z + 1);
                haloRotation = 60 * z ;
            }});
        }
        pierce = true ;
        parts.addAll(new ShapePart() {{
            color = backColor ;
            circle = true ;
            radius = rad ;
        }}/*, new ShapePart() {{
            color = Color.white ;
            circle = true ;
            radius = rad / 2f * 1.75f ;
        }}*/, new ShapePart() {{
            color = Color.black ;
            layer = Layer.space ;
            //color.a = 255 ;
            circle = true ;
            radius = rad / 2f * 1.5f ;
        }}) ;
        fragBullets = 1 ;
        fragBullet = new BulletType() {
            public Sound intervalSound = Sounds.spark ;
            @Override
            public void updateBulletInterval(Bullet b) {
                if(intervalBullet != null && b.time >= intervalDelay && b.timer.get(2, bulletInterval)){
                    float ang = b.rotation();
                    for(int i = 0; i < intervalBullets; i++){
                        intervalBullet.create(b, b.x, b.y, ang + Mathf.range(intervalRandomSpread) + intervalAngle + ((i - (intervalBullets - 1f)/2f) * intervalSpread));
                    }
                    intervalSound.at(b.x, b.y, 1, 2) ;
                }
            }
            {
            killShooter = false ;
            despawnHit = true ;
            hitSound = Sounds.laserblast ;
            hitEffect = SYBSFx.baozha10 ;
            hitColor = backColor ;
            splashDamage = 40000 ;
            splashDamageRadius = 200 * tilesize ;
            lifetime = 1 ;
            hitShake = 100 ;
            speed = 0 ;
            parts.addAll(new ShapePart() {{
                color = backColor ;
                circle = true ;
                radius = rad ;
                radiusTo = 0 ;
            }}, new ShapePart() {{
                color = Color.white ;
                circle = true ;
                radius = rad / 2f * 1.75f ;
                radiusTo = 0 ;
            }}, new ShapePart() {{
                color = Color.black ;
                layer = Layer.space ;
                //color.a = 255 ;
                circle = true ;
                radius = rad / 2f * 1.5f ;
                radiusTo = 0 ;
            }}) ;
            /*
            intervalSpread = 0 ;
            intervalBullets = 1 ;
            intervalDelay = 60 ;
            intervalBullet = new ArtilleryBulletType(5f, 320){{
                lifetime = 120 * 0.8f ;
                trailEffect = Fx.none ;
                parts.addAll(new HaloPart() {{
                    tri = true ;
                    shapes = 2 ;
                    haloRotation = 90 ;
                    triLength = 30 * tilesize ;
                    haloRadius = 0 ;
                    radius = 8 * tilesize ;
                    color = chaokongjian2 ;
                    radiusTo = 0 ;
                }}) ;
                splashDamage = 500 ;
                splashDamageRadius = 10 * tilesize ;
                hitEffect = Fx.none ;
            }};
            */
        }} ;
        fragOnHit = false ;
    }
    @Override
    public void init(Bullet b) {
        super.init(b) ;
        if (!link) return ;
        rand.setSeed(b.id) ;
        float len = rand.random(length * 0.8f, length * 1.2f) ;
        Vec2 v = ShaYeBuShi.circle(b.rotation(), len, b.x, b.y) ;
        LinkBullet l = (LinkBullet) copy() ;
        l.link = false ;
        if (move) {
            l.speed = len / lifetime ;
        }
        l.create(b, v.x, v.y, b.rotation() + 180) ;
    }
    @Override
    public void draw(Bullet b) {
        super.draw(b) ;
        if (!link) return ;
        float rad = Math.max(height, width) ;
        float z = Draw.z() ;
        rand.setSeed(b.id) ;
        float len = rand.random(length * 0.8f, length * 1.2f) * (move ? b.fout() : 1) ;
        Vec2 v = ShaYeBuShi.circle(b.rotation(), len, b.x, b.y) ;
        Vec2 v2 = ShaYeBuShi.circle(225, rad * 0.3f, b.x, b.y) ;
        Vec2 v3 = ShaYeBuShi.circle(45, rad * 0.2f, b.x, b.y) ;
        if (b.timer(2, 2)) {
            SYBSFx.fixedChainLightning.at(b.x, b.y, rad * 2, backColor, v);
            SYBSFx.fixedChainLightning.at(b.x, b.y, rad * 2, Color.black, v);
        }
        Draw.color(backColor) ;
        Lines.stroke(rad) ;
        Lines.lineAngle(b.x, b.y, b.rotation(), len) ;
        Draw.color(Color.black) ;
        Draw.z(Layer.space) ;
        Lines.stroke(rad * 0.3f) ;
        Lines.lineAngle(v2.x, v2.y, b.rotation(), len);
        Lines.stroke(rad * 0.2f) ;
        Lines.lineAngle(v3.x, v3.y, b.rotation(), len);
        Draw.reset() ;
        Draw.z(z) ;
    }
    @Override
    public void update(Bullet b) {
        super.update(b) ;
        if (!(!link && move)) {
            b.drag = 0;
            b.vel.set(0, 0);
        }
        /*
        if (!link && move) {
            float len = (Float) b.data / lifetime ;
            Vec2 v = ShaYeBuShi.circle(b.rotation() + 180, len, b.x, b.y) ;
            b.x = v.x ;
            b.y = v.y ;
        }
        */
        if (!link) return ;
        Damage.collideLine(b, b.team, Fx.none, b.x, b.y, b.rotation(), length, true, true, -1) ;
    }
    @Override
    public void createFrags(Bullet b, float x, float y) {
        if (link) {
            super.createFrags(b, x, y) ;
        }
    }
    @Override
    public void removed(Bullet b) {
        super.removed(b) ;
        createFrags(b, b.x, b.y) ;
    }
}
