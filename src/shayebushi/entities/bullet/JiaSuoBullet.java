package shayebushi.entities.bullet;

import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.geom.Vec2;
import mindustry.content.StatusEffects;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.*;

import static mindustry.Vars.tilesize;

public class JiaSuoBullet extends BulletType {
    public boolean inited = false ;
    public float radius = 55 * tilesize ;
    @Override
    public void init(Bullet b) {
        super.init(b) ;
        if (inited) return ;
        JiaSuoBullet j = (JiaSuoBullet) copy() ;
        j.inited = true ;
        Entityc e = b.owner ;
        Units.nearby(null, b.x, b.y, calculateRange(), u -> {
            if (u.team != b.team) {
                j.create(b, u.x, u.y, Angles.angle(b.x, b.y, u.x, u.y)).add();
            }
        });
    }
    @Override
    public float calculateRange() {
        return radius ;
    }
    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        super.hitEntity(b, entity, health);
        if (!(entity instanceof Unit u)) return ;
        if (b.data == null) {
            b.data = u ;
        }
    }
    @Override
    public void update(Bullet b) {
        super.update(b);
        if (b.data instanceof Unit u && u.health > 0) {
            b.set(u);
            u.apply(StatusEffects.unmoving, 10);
        }
    }
    @Override
    public void draw(Bullet b) {
        super.draw(b);
        if (b.data instanceof Unit u && u.health > 0) {
            Draw.rect(Icon.lock.getRegion(), u.x, u.y, u.hitSize, u.hitSize) ;
        }
    }
}
