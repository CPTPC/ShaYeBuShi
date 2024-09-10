package shayebushi.type.unit;

import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import shayebushi.SYBSFx;
import shayebushi.SYBSStats;
import shayebushi.entities.units.TouBuUnit;
import shayebushi.entities.units.XianShangUnitEntity;

import static shayebushi.SYBSShenShengUnitTypes.shougejieti;

public class DuoJieTiUnitType extends XianShangUnitType {
    public UnitType jietitype = shougejieti;
    public int jietiAmount = 50 ;
    public DuoJieTiUnitType(String name) {
        super(name);
        //deathExplosionEffect = SYBSFx.xingliubaozha ;
    }
    @Override
    public void setStats() {
        super.setStats();
        stats.add(SYBSStats.jietishuliang, jietiAmount + 1);
    }
    @Override
    public Unit create(Team team){
        Unit u = super.create(team) ;
        if (u instanceof TouBuUnit x){
            x.dancixianshang = dancixianshang ;
            x.miaoxianshang = miaoxianshang ;
            x.fenxianshang = fenxianshang ;
            x.jietiAmount = jietiAmount ;
            x.jietiTypeId = jietitype.id ;
            x.setup(jietitype);
        }
        return u ;
    }
}
