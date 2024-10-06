package shayebushi.entities.bullet;

import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Hitboxc;
import shayebushi.ShaYeBuShi;

import static arc.util.Time.toSeconds;

public class ZhongJiLengJingBulletType extends ContinuousLaserBulletType implements WuShiXianShangc{
    public BulletType sanshetype ;
    public float sansheshuliang = 10 ;
    public float chushijiange = 10 ;
    public float jvlongshijian = 5 * toSeconds;
    public void update(Bullet b) {
        super.update(b);
        if (!(b.data instanceof Data)) {
            Data d = new Data() ;
            float jiange = chushijiange * Math.max(0, (jvlongshijian - (int) b.time)) / jvlongshijian;
            //System.out.println(jiange);
            for (int i = 0; i <= sansheshuliang; i++) {
                float angleOffset = i * jiange - sansheshuliang * jiange / 2f + b.rotation() + Mathf.range(inaccuracy + sanshetype.inaccuracy);
                Bullet bu = sanshetype.create(b.owner, b.team, b.x, b.y, angleOffset);
                d.b.add(bu) ;
                bu.add();
            }
            b.data = d ;
        }
        Data d = (Data) b.data ;
        float jiange = chushijiange * Math.max(0, (jvlongshijian - (int) b.time)) / jvlongshijian;
        //System.out.println(jiange);
        int i = 0 ;
        for (Bullet bu : d.b) {
            float angleOffset = i * jiange - sansheshuliang * jiange / 2f + b.rotation() + Mathf.range(inaccuracy + sanshetype.inaccuracy);
            bu.set(b.x, b.y) ;
            bu.rotation(angleOffset) ;
            i ++ ;
        }
    }
    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        poxianshang(this, b, entity);
        handlePierce(b, health, entity.x(), entity.y());
    }

    public class Data {
        public Seq<Bullet> b = new Seq<>() ;
    }
}
