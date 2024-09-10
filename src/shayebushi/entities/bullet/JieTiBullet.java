package shayebushi.entities.bullet;

import arc.audio.Sound;
import arc.math.Mathf;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;

import static arc.util.Time.toSeconds;
import static mindustry.Vars.tilesize;

public class JieTiBullet extends BulletType {
    public float reload = toSeconds ;
    public int times = 10 ;
    public float offset = tilesize ;
    public BulletType type ;
    public Sound sound ;
    public float rot = 90 ;
    @Override
    public void init() {
        super.init();
        speed = 0 ;
        lifetime = reload + 30 ;
    }
    @Override
    public void update(Bullet b) {
        super.update(b);
        if (b.data == null) {
            b.data = new Data() ;
        }
        Data d = (Data) b.data ;
        if (b.time >= reload && d.status < times) {
            b.time = 0 ;
            d.status ++ ;
            if (type != null) {
                for (int i : Mathf.signs) {
                    Bullet b2 = type.create(b, b.x + i * d.status * offset, b.y, rot) ;
                    if (sound != null) {
                        sound.at(b2.x, b2.y) ;
                    }
                }
            }
        }
    }
    public class Data {
        public int status = 0 ;
    }
}
