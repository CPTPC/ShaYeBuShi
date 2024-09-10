package shayebushi.entities.bullet;

import arc.math.Mathf;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Hitboxc;

import static arc.util.Time.toSeconds;

public class ZhongJiLengJingBulletType extends ContinuousLaserBulletType implements WuShiXianShangc{
    public BulletType sanshetype ;
    public float sansheshuliang = 10 ;
    public float chushijiange = 10 ;
    public float jvlongshijian = 5 * toSeconds;
    public void update(Bullet b) {
        super.update(b);
        float jiange = chushijiange * Math.max(0, (jvlongshijian - (int)b.time)) / jvlongshijian;
        //System.out.println(jiange);
        for(int i = 0; i <= sansheshuliang; i++){
            float angleOffset = i * jiange - sansheshuliang * jiange / 2f + b.rotation() + Mathf.range(inaccuracy + sanshetype.inaccuracy) ;
            Bullet bu = sanshetype.create(b.owner, b.team, b.x, b.y, angleOffset) ;
            bu.add();
        }
    }
    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        poxianshang(this, b, entity);
        handlePierce(b, health, entity.x(), entity.y());
    }
}
