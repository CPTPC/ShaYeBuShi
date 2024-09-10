package shayebushi.entities.abilities;

import arc.func.Boolf;
import arc.graphics.Color;
import arc.math.Rand;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import shayebushi.SYBSFx;
import shayebushi.SYBSPal;
import shayebushi.ShaYeBuShi;

import static arc.util.Time.delta;
import static arc.util.Time.toSeconds;
import static mindustry.Vars.content;
import static mindustry.Vars.tilesize;
import static shayebushi.SYBSUnitTypes.shenlingjidanwei;

public class ChuanSongMenAbility extends Ability {
    public static Rand rand = new Rand() ;
    public Seq<UnitType> units = new Seq<>() ;
    public Boolf<UnitType> can = u -> {
       return shenlingjidanwei.contains(u) && u.flying ;
    } ;
    public float reload1 = 10 * toSeconds, reload2 = 60, reload3 = 300 ;
    public float timer1 = 0, timer2 = 0  ;
    public int amount = 5 ;
    public Color color = SYBSPal.chaokongjian1 ;
    public Effect effect = SYBSFx.chuangkou ;
    public float range = 50 * tilesize ;
    public boolean done1 = false, done2 = false ;
    @Override
    public void update(Unit u) {
        if (timer2 == 0) {
            timer1 += delta;
        }
        if (timer1 >= reload1) {
            timer2 += delta ;
            if (!done1) {
                for (int i = 0; i < amount; i++) {
                    rand.setSeed(u.id + i);
                    Vec2 v = ShaYeBuShi.random(rand, u.x, u.y, range);
                    effect.at(v.x, v.y, color);
                }
                done1 = true ;
            }
        }
        if (timer2 >= reload2 && !done2) {
            for (int i = 0 ; i < amount ; i ++) {
                rand.setSeed(u.id + i);
                Vec2 v = ShaYeBuShi.random(rand, u.x, u.y, range) ;
                for (UnitType ut : units) {
                    Unit u2 = ut.create(u.team) ;
                    u2.set(v);
                    u2.add() ;
                    Fx.unitSpawn.at(u2.x, u2.y, u2.rotation, ut) ;
                }
            }
            done2 = true ;
        }
        if (timer2 >= reload3) {
            timer1 = 0 ;
            timer2 = 0 ;
            done1 = true ;
            done2 = false ;
        }
    }
    @Override
    public void init(UnitType u) {
        super.init(u);
        content.units().each(can, u2 -> units.add(u2));
    }
}
