package shayebushi.entities.abilities;

import arc.math.geom.Vec2;
import arc.struct.Seq;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import shayebushi.ShaYeBuShi;
import shayebushi.entities.units.Ownerc;
import shayebushi.entities.units.ZhuTic;

import static arc.util.Time.delta;
import static arc.util.Time.toSeconds;
import static shayebushi.SYBSUnitTypes.wuji;

public class ZhuTiAbility extends Ability {
    public Seq<UnitType> spawnTypes = new Seq<>();
    public int spawns;
    public boolean spawn = true;
    public Seq<Vec2> pucongweis = new Seq<>();
    public boolean buquan = true ;
    public int times = 1 ;
    public float timerB = 0 ;
    public float reloadB = 30 * toSeconds ;
    public int timesB = 0 ;
    public ZhuTiAbility() {
        display = false ;
    }

    public void buQuan(Unit unit) {
        if (unit instanceof ZhuTic z && buquan) {
            for (UnitType u : spawnTypes) {
                for (int i = z.pucongs().size; i < (spawnTypes.size == 1 ? z.pucongweiAmount() : z.pucongs().size + 1); i++) {
                    if (i >= z.pucongweiAmount() || i >= z.pucongweis().size) break;
                    Unit un = u.create(unit.team);
                    un.x = unit.x;
                    un.y = unit.y;
                    //System.out.println(z.pucongweiAmount() + " " + i);
                    un.x += z.pucongweis().get(i).x;
                    un.y += z.pucongweis().get(i).y;
                    if (un instanceof Ownerc o) {
                        o.pucongwei(i);
                    }
                    un.add();
                    if (unit instanceof ZhuTic zt) {
                        zt.pucongs().add(un);
                    }
                    if (un instanceof Ownerc o) {
                        o.owner(unit);
                    }
                }
            }
        }
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);
        timerB += delta ;
//        if (unit.type == wuji) {
//            System.out.println(unit.damageMultiplier);
//        }
        if (spawn && unit instanceof ZhuTic ztc && ztc.pucongs().isEmpty() && ztc.healthf() == 1) {
            int z = 0;
            if (unit instanceof ZhuTic zt) {
                zt.pucongweis(pucongweis);
                zt.pucongweiAmount(spawns);
            }
            for (UnitType u : spawnTypes) {
                int size = ztc.pucongs().size + 1 ;
                for (int i = ztc.pucongs().size; i < (spawnTypes.size == 1 ? ztc.pucongweiAmount() : size); i++) {
                    //System.out.println(i);
                    Unit un = u.create(unit.team);
                    un.x = unit.x;
                    un.y = unit.y;
                    if (unit instanceof ZhuTic zt && z < zt.pucongweiAmount()) {
                        un.x += zt.pucongweis().get(z).x;
                        un.y += zt.pucongweis().get(z).y;
                        if (un instanceof Ownerc o) {
                            o.pucongwei(z);
                        }
                        z++;
                    }
                    un.add();
                    if (unit instanceof ZhuTic zt) {
                        zt.pucongs().add(un);
                        zt.pucongIds().add(un.id);
                    }
                    if (un instanceof Ownerc o) {
                        o.owner(unit);
                    }

                }
            }
            spawn = false;
        }
        if (timerB >= reloadB && buquan && timesB < times) {
            buQuan(unit);
            timerB = 0 ;
            timesB ++ ;
        }
    }
}
