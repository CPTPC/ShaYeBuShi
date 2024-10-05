package shayebushi.type.unit;

import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import shayebushi.SYBSStats;
import shayebushi.entities.units.ZhanJiUnitEntity;

public class ZhanJiUnitType extends UnitType {
    public float shanbi = 0.5f ;
    public boolean zhandou = true ;
    public boolean hongzha = false ;
    public boolean paoting = false ;
    public ZhanJiUnitType(String name) {
        super(name);
        useUnitCap = false ;
    }
    @Override
    public void setStats(){
        super.setStats();
        stats.add(SYBSStats.shanbi, shanbi * 100 + "%");
    }

    @Override
    public Unit create(Team team){
        Unit u = super.create(team) ;
        if (u instanceof ZhanJiUnitEntity z){
            z.shanbi = shanbi ;
            z.zhandou = zhandou ;
            z.hongzha = hongzha ;
            z.paoting = paoting ;
        }
        return u ;
    }
}
