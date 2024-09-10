package shayebushi.entities.units;

import arc.graphics.Color;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.Rand;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import shayebushi.SYBSPal;

public class YaoFengUnitEntity extends UnitEntity {
    Seq<Unit> all = new Seq<>() ;
    public float fanwei = 800 ;
    public float dam = 10000 ;
    public Color col = SYBSPal.yaofeng ;
    public int timer = 0 ;
    public float reload = 6 ;
    @Override
    public void update(){
        super.update();
        Units.nearby(null,x,y,fanwei,o ->{
            if (o.team != team){
                all.add(o) ;
            }
        });
        timer += Time.delta ;
        if (!all.isEmpty() && timer >= reload) {
            Unit e = all.get(new Rand().random(0, all.size - 1));
            float shanxianchangdu = Mathf.dst(x, y, e.x, e.y);
            double angle = (float) Angles.angle(x,y,e.x,e.y);
            angle = Math.toRadians(angle);
            double x = 0, y = 0;
            if (angle >= 0 && angle < 90) {
                if (angle <= 45) {
                    x = x + shanxianchangdu * Vars.tilesize * Math.cos(!isAI() ? angle : rotation) + e.hitSize;
                    y = y + shanxianchangdu * Vars.tilesize * Math.sin(!isAI() ? angle : rotation) + e.hitSize;
                } else {
                    y = y + shanxianchangdu * Vars.tilesize * Math.cos(!isAI() ? angle : rotation) + e.hitSize;
                    x = x + shanxianchangdu * Vars.tilesize * Math.sin(!isAI() ? angle : rotation) + e.hitSize;
                }
            } else if (angle >= 90 && angle < 180) {
                if (angle <= 135) {
                    x = x - shanxianchangdu * Vars.tilesize * Math.sin(!isAI() ? angle : rotation - 90) + e.hitSize;
                    y = y + shanxianchangdu * Vars.tilesize * Math.cos(!isAI() ? angle : rotation - 90) + e.hitSize;
                } else {
                    y = y + shanxianchangdu * Vars.tilesize * Math.sin(!isAI() ? angle : rotation - 90) + e.hitSize;
                    x = x - shanxianchangdu * Vars.tilesize * Math.cos(!isAI() ? angle : rotation - 90) + e.hitSize;
                }
            } else if (angle >= 180 && angle < 270) {
                if (angle <= 225) {
                    y = y - shanxianchangdu * Vars.tilesize * Math.sin(!isAI() ? angle : rotation - 180) + e.hitSize;
                    x = x - shanxianchangdu * Vars.tilesize * Math.cos(!isAI() ? angle : rotation - 180) + e.hitSize;
                } else {
                    x = x - shanxianchangdu * Vars.tilesize * Math.sin(!isAI() ? angle : rotation - 180) + e.hitSize;
                    y = y - shanxianchangdu * Vars.tilesize * Math.cos(!isAI() ? angle : rotation - 180) + e.hitSize;
                }
            } else if (angle >= 270 && angle < 360) {
                if (angle <= 315) {
                    y = y - shanxianchangdu * Vars.tilesize * Math.cos(!isAI() ? angle : rotation - 270) + e.hitSize;
                    x = x + shanxianchangdu * Vars.tilesize * Math.sin(!isAI() ? angle : rotation - 270) + e.hitSize;
                } else {
                    x = x + shanxianchangdu * Vars.tilesize * Math.sin(!isAI() ? angle : rotation - 270) + e.hitSize;
                    y = y - shanxianchangdu * Vars.tilesize * Math.cos(!isAI() ? angle : rotation - 270) + e.hitSize;
                }
            }
            this.x = (float) x;
            this.y = (float) y;
            BulletType b = new LaserBulletType() {{
                damage = dam;
                length = shanxianchangdu * Vars.tilesize;
                width = hitSize ;
                colors[0] = col.cpy().mul(1f, 1f, 1f, 0.4f);
                colors[1] = col;
            }};
            b.create(this, this.x, this.y, (float) angle).add();
        }
    }
    @Override
    public void draw(){
        super.draw() ;
        Fx.titanSmoke.at(x,y,col);
    }
}
