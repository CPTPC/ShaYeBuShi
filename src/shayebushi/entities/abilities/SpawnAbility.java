package shayebushi.entities.abilities;

import arc.struct.Seq;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import shayebushi.entities.units.Linkc;
import shayebushi.entities.units.Ownerc;

public class SpawnAbility extends Ability {
    public Seq<UnitType> spawnTypes = new Seq<>();
    public boolean spawn = true ;
    public SpawnAbility() {
        display = false ; ;
    }
    @Override
    public void update(Unit unit) {
        super.update(unit);
        Linkc link = null ;
        if (spawn) {
            for (UnitType u : spawnTypes) {
                Unit un = u.create(unit.team);
                un.x = unit.x;
                un.y = unit.y;
                un.add();
                if (un instanceof Ownerc o) {
                    o.owner(unit) ;
                }
                if (un instanceof Linkc l) {
                    if (link != null) {
                        l.link((Unit) link) ;
                        link.link((Unit) l) ;
                    }
                    link = l ;
                }
            }
            unit.trail = null ;
            unit.remove() ;
            spawn = false;
        }
    }
}
