package shayebushi.type.unit;

import mindustry.game.Team;
import mindustry.gen.EntityMapping;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import shayebushi.SYBSStats;
import shayebushi.entities.units.WaterMoveXianShangUnit;
import shayebushi.entities.units.XianShangUnitEntity;
import shayebushi.entities.units.ZhanJiUnitEntity;

public class XianShangUnitType extends UnitType {
    public float miaoxianshang = 20000 ;
    public float dancixianshang = 2000 ;
    public float fenxianshang = 1919810 ;
    public XianShangUnitType(String name) {
        super(name);
    }
    @Override
    public void setStats(){
        super.setStats();
        if (miaoxianshang < health) {
            stats.add(SYBSStats.miaoxianshang, miaoxianshang);
        }
        if (fenxianshang < health) {
            stats.add(SYBSStats.fenxianshang, fenxianshang);
        }
        if (dancixianshang < health) {
            stats.add(SYBSStats.dancixianshang, dancixianshang);
        }
    }
    @Override
    public Unit create(Team team){
        constructor = EntityMapping.map(this.name);
        Unit u = super.create(team) ;
        if (u instanceof XianShangUnitEntity x){
            x.dancixianshang = dancixianshang ;
            x.miaoxianshang = miaoxianshang ;
            x.fenxianshang = fenxianshang ;
        }
        if (u instanceof WaterMoveXianShangUnit w) {
            w.dancixianshang = dancixianshang ;
            w.miaoxianshang = miaoxianshang ;
            w.fenxianshang = fenxianshang ;
        }
        return u ;
    }
}
