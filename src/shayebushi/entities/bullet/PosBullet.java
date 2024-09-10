package shayebushi.entities.bullet;

import arc.func.Cons;
import arc.math.geom.Vec2;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.content.Fx;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.PointBulletType;
import mindustry.gen.Bullet;

public class PosBullet extends PointBulletType {
    public Seq<Data> bullets = new Seq<>() ;
    @Override
    public void init() {
        super.init();
        trailEffect = Fx.none ;
    }
    @Override
    public void init(Bullet b) {
        super.init(b) ;
        for (Data d : bullets) {
            d.b.create(b, b.x + d.p.x, b.y + d.p.y, d.r) ;
        }

    }
    public class Data {
        public BulletType b ;
        public Vec2 p ;
        public float r ;
        public Data(BulletType b, Vec2 p, float r) {
            this.b = b ;
            this.p = p ;
            this.r = r ;
        }
        public Data(BulletType b, Vec2 p, float r, Cons<BulletType> c) {
            BulletType b2 = b.copy() ;
            c.get(b2) ;
            this.b = b2 ;
            this.p = p ;
            this.r = r ;
        }
    }
}
