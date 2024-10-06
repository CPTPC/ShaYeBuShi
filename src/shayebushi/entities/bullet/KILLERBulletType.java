package shayebushi.entities.bullet;

import mindustry.entities.bullet.PointLaserBulletType;
import mindustry.game.Team;
import mindustry.gen.*;
import shayebushi.ShaYeBuShi;
import shayebushi.entities.SYBSEntityGroup;

public class KILLERBulletType extends LineBullet {
    @Override
    public void hitEntity(Bullet b , Hitboxc h , float hh){
        super.hitEntity(b,h,hh);
        if (h instanceof Unit u){
//            u.health = 0 ;
//            u.dead = true ;
//            u.kill();
//            u.remove() ;
            if (u.type.name.contains("empathy")) {
                ((SYBSEntityGroup)Groups.unit).removed.add(u) ;
                ((SYBSEntityGroup)Groups.all).removed.add(u) ;
                ((SYBSEntityGroup)Groups.sync).removed.add(u) ;
                ((SYBSEntityGroup)Groups.draw).removed.add(u) ;
                u.type.targetable = false ;
                u.type.drawMinimap = false ;
                return ;
            }
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
    @Override
    public void init(Bullet b) {
        super.init(b) ;
        b.data = b.team ;
    }
    @Override
    public void update(Bullet b) {
        super.update(b) ;
        if (b.team != b.data) {
            b.team = (Team) b.data ;
            for (Unit u : Groups.unit) {
                if (u.team != b.team) {
                    if (u.type.name.contains("empathy")) {
                        ((SYBSEntityGroup)Groups.unit).removed.add(u) ;
                        ((SYBSEntityGroup)Groups.all).removed.add(u) ;
                        ((SYBSEntityGroup)Groups.sync).removed.add(u) ;
                        ((SYBSEntityGroup)Groups.draw).removed.add(u) ;
                        u.type.targetable = false ;
                        u.type.drawMinimap = false ;
                        continue ;
                    }
                    Groups.unit.remove(u) ;
                    Groups.all.remove(u) ;
                    Groups.sync.remove(u) ;
                    Groups.draw.remove(u) ;
                }
            }
        }
        b.data = b.team ;
    }
}
