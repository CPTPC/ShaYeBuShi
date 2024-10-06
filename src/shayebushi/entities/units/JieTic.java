package shayebushi.entities.units;

import arc.math.geom.Vec2;
import mindustry.entities.Units;
import mindustry.entities.units.AIController;
import mindustry.gen.Groups;
import mindustry.gen.Unit;

public interface JieTic extends Ownerc {
    Unit shangjie() ;
    void shangjie(Unit u) ;
    float beilv() ;
    @Override
    default void updateOwner() {
//        if ((owner() != null && (owner().health <= 0 || owner().dead || !owner().isAdded() || !Groups.unit.contains(u -> u == owner()))) || owner() == null) {
//            if (this instanceof JieTiUnit q) {
//                q.fixedKill();
//            }
//        }
        if (owner() != null && this instanceof JieTiUnit j) {
            j.setHealth(owner().healthf() * j.maxHealth) ;
        }
    }
    default void updateJieti() {
        Units.nearby(null, x(), y(), hitSize(), u -> {
            if (u.team != team() && u.hittable() && !u.isFlying()) {
                u.damage(200) ;
            }
        });
        Units.nearbyBuildings(x(), y(), hitSize(), u -> {
            if (u.team != team()) {
                u.damage(200) ;
            }
        });
    }
    default void damageJieti(float damage) {
        //if (1 + 1 == 2) return ;
        if (owner() instanceof XianShangUnitEntity x) {
            if (x.dangqianshanghaimiao < x.miaoxianshang && x.dangqianshanghaifen < x.fenxianshang) {
                owner().health -= Math.max(Math.min(damage * beilv(), x.dancixianshang), 0) ;
                if (!(owner() instanceof QiangZhiXianShangUnitEntity)) {
                    x.dangqianshanghaimiao += Math.min(damage * beilv(), x.dancixianshang);
                    x.dangqianshanghaifen += Math.min(damage * beilv(), x.dancixianshang);
                }
            }
        }
    }
    default void healJieTi(float amount) {
        owner().heal(amount) ;
    }
}
