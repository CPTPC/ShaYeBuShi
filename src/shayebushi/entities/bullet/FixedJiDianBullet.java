package shayebushi.entities.bullet;

import arc.audio.Sound;
import arc.graphics.Color;
import arc.math.geom.Vec2;
import arc.util.Time;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Pal;
import shayebushi.ShaYeBuShi;

import static mindustry.Vars.tilesize;

public class FixedJiDianBullet extends BulletType {
    public BulletType type1, type2, type3 ;
    public float maxLength = 60 * tilesize, minLength = 6 * tilesize, maxRadius = 60 * tilesize ;
    public Effect effect ;
    public Color color = Pal.redderDust ;
    @Override
    public void update(Bullet b) {
        super.update(b);
        if (!(b.data instanceof Data)) {
            b.data = new Data() ;
        }
        Data d = (Data) b.data ;
        d.update(b);
    }
    @Override
    public void init(Bullet b) {
        super.init(b);
        if (type1 != null) {
            type1.create(b, b.x, b.y, 0);
        }
    }
    @Override
    public void removed(Bullet b) {
        super.removed(b);
        if (type1 != null) {
            type1.create(b, b.x, b.y, 0);
        }
    }
    @Override
    public void init() {
        super.init();
        speed = 0 ;
    }
    public class Data {
        public int status ;
        public float time ;
        public Bullet[] bullets = new Bullet[2] ;
        public void update(Bullet b) {
            time += Time.delta ;
            if (status == 0 || status == 1) {
                if (b.timer(1, 30)) {
                    LineBullet l = new LineBullet() {{
                        damage = time * 100;
                        length = Data.this.status == 0 ? Math.max(time / 4, minLength + 1) : maxLength - time / 4;
                        width = tilesize ;
                        if (length >= maxLength) {
                            if (type1 != null) {
                                type1.create(b, b.x, b.y, 0);
                            }
                            Data.this.status = 1 ;
                            time = 0 ;
                        }
                        if (length <= minLength) {
                            Data.this.status = 2 ;
                            if (type1 != null) {
                                type1.create(b, b.x, b.y, 0);
                            }
                            if (type2 != null) {
                                bullets[0] = type2.create(b, b.x, b.y, 0);
                                bullets[1] = type2.create(b, b.x, b.y, 90);
                            }
                        }
                        amount = 0;
                        overrideRotation = false;
                        lifetime = 120;
                        colors[0] = color ;
                    }};
                    l.create(b, b.x, b.y, time + 90);
                }
                b.time = 0;
            }
            if (status == 2) {
                if (b.timer(2, 10)) {
                    if (type3 != null) {
                        for (int i = 0 ; i < 5 ; i ++) {
                            Vec2 v = ShaYeBuShi.random(b.x, b.y, maxRadius) ;
                            type3.create(b, v.x, v.y, 0) ;
                        }
                    }
                }
            }
        }
    }
}
