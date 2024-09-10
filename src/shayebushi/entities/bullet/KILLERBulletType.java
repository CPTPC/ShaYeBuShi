package shayebushi.entities.bullet;

import mindustry.entities.bullet.PointLaserBulletType;
import mindustry.gen.*;

public class KILLERBulletType extends LineBullet {
    @Override
    public void hitEntity(Bullet b , Hitboxc h , float hh){
        super.hitEntity(b,h,hh);
        if (h instanceof Unit u){
//            u.health = 0 ;
//            u.dead = true ;
//            u.kill();
//            u.remove() ;
            Groups.unit.remove(u) ;
            Groups.all.remove(u) ;
            Groups.sync.remove(u) ;
            Groups.draw.remove(u) ;
        }
    }
    @Override
    public void hitTile(Bullet bb , Building b , float x , float y , float i , boolean d){
        super.hitTile(bb,b,x,y,i,d);
        if (b.team != bb.team) {
            b.kill();
            b.health = 0 ;
            b.dead = true ;
        }
    }
}
