package shayebushi.entities.abilities;

import arc.Core;
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
import mindustry.entities.abilities.Ability;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.logic.LUnitControl;
import shayebushi.SYBSPal;
import shayebushi.entities.bullet.BaiFenBiLaserBulletType;

public class YaoFengXiJuanAbility extends Ability {
    Seq<Unit> all = new Seq<>() ;
    public float fanwei = 800 ;
    public float dam = 10000 ;
    public Color col = SYBSPal.yaofeng ;
    public int timer = 0 ;
    public float reload = 6 ;
    @Override
    public void update(Unit u){
        super.update(u);
        all.clear();
        Units.nearby(null,u.x,u.y,fanwei, o ->{
            if (o.team != u.team){
                all.add(o) ;
            }
        });
        timer += Time.delta ;
        if (!all.isEmpty() && timer >= reload) {
            Unit e = all.get(new Rand().random(0, all.size - 1));
            //System.out.println(u.x + "," + u.y);
            float shanxianchangdu = e.hitSize * Vars.tilesize >= fanwei / 2 ? fanwei / (2 * Vars.tilesize) : e.hitSize ;
            double angle = (float) Angles.angle(u.x,u.y,e.x,e.y);
            //System.out.println(angle);
            angle = Math.toRadians(angle);
            double x = e.x, y = e.y;
            if (angle >= 0 && angle < 90) {
                if (angle <= 45) {
                    x = u.x + shanxianchangdu * Vars.tilesize * Math.cos(angle);
                    y = u.y + shanxianchangdu * Vars.tilesize * Math.sin(angle);
                } else {
                    y = u.y + shanxianchangdu * Vars.tilesize * Math.cos(angle);
                    x = u.x + shanxianchangdu * Vars.tilesize * Math.sin(angle);
                }
            } else if (angle >= 90 && angle < 180) {
                if (angle <= 135) {
                    x = u.x - shanxianchangdu * Vars.tilesize * Math.sin(angle - 90);
                    y = u.y + shanxianchangdu * Vars.tilesize * Math.cos(angle - 90);
                } else {
                    y = u.y + shanxianchangdu * Vars.tilesize * Math.sin(angle - 90);
                    x = u.x - shanxianchangdu * Vars.tilesize * Math.cos(angle - 90);
                }
            } else if (angle >= 180 && angle < 270) {
                if (angle <= 225) {
                    y = u.y - shanxianchangdu * Vars.tilesize * Math.sin(angle - 180);
                    x = u.x - shanxianchangdu * Vars.tilesize * Math.cos(angle - 180);
                } else {
                    x = u.x - shanxianchangdu * Vars.tilesize * Math.sin(angle - 180);
                    y = u.y - shanxianchangdu * Vars.tilesize * Math.cos(angle - 180);
                }
            } else if (angle >= 270 && angle < 360) {
                if (angle <= 315) {
                    y = u.y - shanxianchangdu * Vars.tilesize * Math.cos(angle - 270);
                    x = u.x + shanxianchangdu * Vars.tilesize * Math.sin(angle - 270);
                } else {
                    x = u.x + shanxianchangdu * Vars.tilesize * Math.sin(angle - 270);
                    y = u.y - shanxianchangdu * Vars.tilesize * Math.cos(angle - 270);
                }
            }
            BulletType b = new BaiFenBiLaserBulletType(dam,0) {{
                damage = dam;
                length = shanxianchangdu * Vars.tilesize;
                width = e.hitSize ;
                colors[0] = col.cpy().mul(1f, 1f, 1f, 0.4f);
                colors[1] = col;
            }};
            Bullet bb = b.create(u ,u.x, u.y, (float)Math.toDegrees(angle));
            bb.rotation((float)Math.toDegrees(angle));
            bb.add();
            u.x = (float) x;
            u.y = (float) y;
            timer = 0 ;
        }
    }
    @Override
    public void draw(Unit unit){
        super.draw(unit) ;
        Fx.missileTrailSmoke.at(unit.x,unit.y,SYBSPal.yaofeng);
        Drawf.light(unit.x,unit.y,unit.hitSize * 1.15f,SYBSPal.yaofeng,5);
    }
    @Override
    public String localized(){
        return Core.bundle.format("ability.yaofengxijuan", dam,fanwei / Vars.tilesize);
    }

    public boolean isAI(){
        return false ;
    }
}
