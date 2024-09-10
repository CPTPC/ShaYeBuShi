package shayebushi.entities.bullet;

import arc.math.Angles;
import mindustry.Vars;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Teamc;

public class TeSiLaBullet extends BasicBulletType {
    public float maxLinks = 5 ;
    public float dam = 5 ;
    public TeSiLaBullet() {
        speed = 0 ;
        lifetime = 180 ;
        width = height = 16 ;
        damage = 300 ;
        pierce = true ;
        inaccuracy = 10 ;
    }
    @Override
    public void update(Bullet b) {
        var o = new Object(){
           public int amount = 0 ;
        } ;
        Groups.bullet.intersect(b.x, b.y, Vars.world.width(), Vars.world.height(), b2 -> {
            if (o.amount > maxLinks) {
                return ;
            }
            if (b2.type == b.type && b2.team == b.team) {
                o.amount++;
                BulletType b3 = new LaserBulletType() {{
                    damage = dam;
                    length = b.dst(b2) * Vars.tilesize;
                    width = TeSiLaBullet.this.width ;
                    colors[0] = frontColor.cpy().mul(1f, 1f, 1f, 0.4f);
                    colors[1] = backColor;
                    lifetime = 10 ;
                }};
                Bullet bb = b3.create((Teamc)b.owner, b.x, b.y, Angles.angle(b.x, b.y, b2.x, b2.y));
                bb.rotation(Angles.angle(b.x, b.y, b2.x, b2.y));
                bb.add();
            }
        }) ;
    }
}
