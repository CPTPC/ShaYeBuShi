package shayebushi.entities.bullet;

import arc.graphics.Color;
import arc.math.Angles;
import arc.math.geom.Vec2;
import mindustry.content.Fx;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Pal;
import shayebushi.SYBSFx;
import shayebushi.ShaYeBuShi;

import static mindustry.Vars.tilesize;

public class LightningContinuousLaserBulletType extends ContinuousLaserBulletType {
    public Color lightningColor = Pal.accentBack ;
    public float reload = 10 ;
    public int amount = 5 ;
    @Override
    public void draw(Bullet b) {
        super.draw(b);
        if (b.timer(4, reload)) {
            for (int i = 0; i < amount; i++) {
                Vec2 v = ShaYeBuShi.circle(b.rotation(), length, b.x, b.y);
                SYBSFx.fixedChainLightning.at(b.x, b.y, width + tilesize, lightningColor, v);
            }
        }
    }
}
