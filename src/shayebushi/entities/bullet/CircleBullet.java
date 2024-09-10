package shayebushi.entities.bullet;

import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;

import static mindustry.Vars.net;

public class CircleBullet extends BasicBulletType {
    public float speed2 = 0 ;
    public CircleBullet(float s) {
        speed = s ;
        lifetime = 360 ;
        pierce = true ;
        hittable = false ;
        collides = false ;
    }
    public CircleBullet(float s, float w, float h) {
        this(s) ;
        width = trailWidth = w ;
        height = h ;
    }
    public CircleBullet(float s, float w, float h, float angle) {
        this(s, w, h) ;
        trailLength = (int)(angle * speed) ;
    }
    public CircleBullet(float s, float w, float h, float angle, float s2) {
        this(s, w, h, angle) ;
        speed2 = s2 ;
    }
    @Override
    public void update(Bullet b) {
        super.update(b) ;
        for (int i = 0 ; i < speed2 ; i ++) {
            if (!net.client() || b.isLocal()) {
                float px = b.x;
                float py = b.y;
                b.move(b.vel.x * Time.delta, b.vel.y * Time.delta);
                if (Mathf.equal(px, b.x)) b.vel.x = 0;
                if (Mathf.equal(py, b.y)) b.vel.y = 0;
                b.vel.scl(Math.max(1.0F - drag * Time.delta, 0));
            }
            b.time += Time.delta ;
        }
        for (int i = 0 ; i < speed2 + 1 ; i ++) {
            b.rotation(b.rotation() + 1);
        }
    }
}
