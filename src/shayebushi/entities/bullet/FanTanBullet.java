package shayebushi.entities.bullet;

import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.LaserBoltBulletType;
import mindustry.gen.Bullet;

public class FanTanBullet extends LaserBoltBulletType {
    {
        pierce = true ;
    }
    @Override
    public void handlePierce(Bullet b, float initialHealth, float x, float y){
        int r = (int) b.rotation() ;
        switch (r) {
            case 0, 180, 360 :
                b.rotation(b.rotation() + 180) ;
                break ;
            default :
                b.rotation(360 - b.rotation()) ;
        }
        b.lifetime *= 2 ;
    }
}
