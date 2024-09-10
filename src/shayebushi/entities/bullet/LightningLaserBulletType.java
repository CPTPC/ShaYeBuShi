package shayebushi.entities.bullet;

import arc.graphics.Color;
import arc.math.geom.Vec2;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Pal;
import shayebushi.SYBSFx;
import shayebushi.ShaYeBuShi;

import static mindustry.Vars.tilesize;

public class LightningLaserBulletType extends LaserBulletType {
    public Color lightningColor = Pal.accentBack ;
    public float reload = 100 ;
    public int amount = 5 ;
    @Override
    public void draw(Bullet b) {
        super.draw(b);
        if (b.timer(2, reload)) {
            for (int i = 0; i < amount; i++) {
                Vec2 v = ShaYeBuShi.circle(b.rotation(), length, b.x, b.y);
                SYBSFx.fixedChainLightning.at(b.x, b.y, width + tilesize, lightningColor, v);
            }
        }
    }
}
