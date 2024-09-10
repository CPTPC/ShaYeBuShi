package shayebushi.entities.bullet;

import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.LaserBoltBulletType;
import mindustry.gen.Bullet;

public class FanTanBullet extends LaserBoltBulletType {
    {
        pierce = true ;
    }
    @Override
    public void hit(Bullet b){
        int r = (int) b.rotation() ;
        switch (r) {
            case 90, 180, 270, 0, 360 :
                b.rotation(b.rotation() + 180) ;
                break ;
            default :
                b.rotation(360 - b.rotation()) ;
        }
        b.lifetime *= 2 ;
    }
}
