package shayebushi.entities.weapons;

import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.type.Weapon;
import shayebushi.entities.abilities.TuiJinAbility;

public class TuiJinQiWeapon extends Weapon {
    public TuiJinQiWeapon(String name) {
        super(name) ;
    }
    @Override
    public void update(Unit u, WeaponMount wm) {
//        TuiJinAbility t = (TuiJinAbility) u.type.abilities.find(a -> a instanceof TuiJinAbility) ;
//        if (t == null) return ;
//        if (t.status == 1) {
//            super.update(u, wm) ;
//        }
        if (u.moving()) {
            super.update(u, wm) ;
        }
    }
}
