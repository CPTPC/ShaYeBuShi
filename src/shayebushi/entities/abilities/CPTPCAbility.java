package shayebushi.entities.abilities;

import arc.math.geom.Vec2;
import mindustry.entities.abilities.Ability;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Unit;
import shayebushi.SYBSItems;
import shayebushi.ShaYeBuShi;
import shayebushi.entities.TimedWorldLabel;

import static arc.util.Time.toSeconds;
import static mindustry.Vars.state;
import static mindustry.Vars.tilesize;

public class CPTPCAbility extends Ability {
    public boolean d1, d2, d3, d4, d5;
    public BulletType b1, b2, b3, b4, b5;
    public float rotation1, rotation2, rotation3, rotation4, rotation5 ;
    @Override
    public void update(Unit unit) {
        super.update(unit);
        if (unit.healthf() < 1) {
            d1 = true;
            if (b1 != null) {
                b1.create(unit, unit.x, unit.y, 0);
            }
        }
        if (unit.healthf() < 0.8f) {
            d2 = true;
            if (b2 != null) {
                b2.create(unit, unit.x, unit.y, 0);
            }
        }
        if (unit.healthf() < 0.6f) {
            d3 = true;
            if (b3 != null) {
                b3.create(unit, unit.x, unit.y, 0);
            }
        }
        if (unit.healthf() < 0.4f) {
            d4 = true;
            if (b4 != null) {
                b4.create(unit, unit.x, unit.y, 0);
            }
        }
        if (unit.healthf() < 0.2f) {
            d5 = true;
            if (b5 != null) {
                b5.create(unit, unit.x, unit.y, 0);
            }
        }
        if (!d1) {
            unit.mounts[0].reload = 1145;
        }
        else {
            rotation1 += 25 ;
        }
        if (!d2) {
            unit.mounts[1].reload = 1145;
        }
        else {
            rotation2 += 20 ;
        }
        if (!d3) {
            unit.mounts[2].reload = 1145;
        }
        else {
            rotation3 += 15 ;
        }
        if (!d4) {
            unit.mounts[3].reload = 1145;
        }
        else {
            rotation4 += 10 ;
        }
        if (!d5) {
            unit.mounts[4].reload = 1145;
        }
        else {
            rotation5 += 5 ;
        }
        unit.health -= 3333333 ;
        if (unit.health <= 0) {
            unit.trail = null ;
            TimedWorldLabel t = new TimedWorldLabel() ;
            t.lifetime = 33 * toSeconds ;
            t.text = "你通过了我的试炼\n有缘再会" ;
            t.fontSize = 33 ;
            t.x = unit.x ;
            t.y = unit.y ;
            t.add() ;
            if (unit.team == state.rules.waveTeam) {
                state.rules.defaultTeam.core().handleItem(null, SYBSItems.yuemingzhu) ;
            }
            //unit.remove();
        }
    }
    @Override
    public void draw(Unit unit) {
        super.draw(unit);
        if (d1) {
            for (int i = 0 ; i < 360 ; i += 360 / 1f) {
                TimedWorldLabel t = new TimedWorldLabel() ;
                Vec2 v = ShaYeBuShi.circle(i + rotation1, 3f * tilesize, unit.x, unit.y) ;
                t.lifetime = 1 ;
                t.text = "壹" ;
                t.fontSize = 1 ;
                t.x = v.x ;
                t.y = v.y ;
                t.add() ;
            }
        }
        if (d2) {
            for (int i = 0 ; i < 360 ; i += 360 / 2f) {
                TimedWorldLabel t = new TimedWorldLabel() ;
                Vec2 v = ShaYeBuShi.circle(i - rotation2, 6 * tilesize, unit.x, unit.y) ;
                t.lifetime = 1 ;
                t.text = "贰" ;
                t.fontSize = 2 ;
                t.x = v.x ;
                t.y = v.y ;
                t.add() ;
            }
        }
        if (d3) {
            for (int i = 0 ; i < 360 ; i += 360 / 3f) {
                TimedWorldLabel t = new TimedWorldLabel() ;
                Vec2 v = ShaYeBuShi.circle(i + rotation3, 9f * tilesize, unit.x, unit.y) ;
                t.lifetime = 1 ;
                t.text = "叁" ;
                t.fontSize = 3 ;
                t.x = v.x ;
                t.y = v.y ;
                t.add() ;
            }
        }
        if (d4) {
            for (int i = 0 ; i < 360 ; i += 360 / 4f) {
                TimedWorldLabel t = new TimedWorldLabel() ;
                Vec2 v = ShaYeBuShi.circle(i - rotation4, 12 * tilesize, unit.x, unit.y) ;
                t.lifetime = 1 ;
                t.text = "肆" ;
                t.fontSize = 4 ;
                t.x = v.x ;
                t.y = v.y ;
                t.add() ;
            }
        }
        if (d5) {
            for (int i = 0 ; i < 360 ; i += 360 / 5f) {
                TimedWorldLabel t = new TimedWorldLabel() ;
                Vec2 v = ShaYeBuShi.circle(i + rotation5, 15f * tilesize, unit.x, unit.y) ;
                t.lifetime = 1 ;
                t.text = "伍" ;
                t.fontSize = 5 ;
                t.x = v.x ;
                t.y = v.y ;
                t.add() ;
            }
        }
    }
}
