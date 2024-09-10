package shayebushi.entities.abilities;

import arc.Core;
import arc.func.Boolf;
import arc.util.Nullable;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

/** Spawns a certain amount of units upon death. */
public class PoFuChenZhouAbility extends Ability {
    public UnitType unittt;
    public float amount = 1;
    public int randAmount = 0;
    /** Random spread of units away from the spawned. */
    public float max = 8f;
    /** If true, units spawned face outwards from the middle. */
    public boolean faceOutwards = true;

    public PoFuChenZhouAbility(UnitType unit,float amont){
        this.unittt = unit;
        this.amount = amont ;
    }

    public PoFuChenZhouAbility(){
    }
    @Override
    public String localized(){
        return Core.bundle.format("ability.pofuchenzhou");
    }

    int i = 0 ;
//    float II = this.unittt.create(Team.derelict).maxHealth;
//    float III = this.unittt.create(Team.derelict).armor;
//    float IV = this.unittt.create(Team.derelict).damageMultiplier;
//    float V = this.unittt.create(Team.derelict).speedMultiplier;
//    float VI = this.unittt.create(Team.derelict).reloadMultiplier;
    @Override
    public void update(Unit unit){
//        int I = Units.count(unit.x, unit.y,  Integer.MAX_VALUE, new Boolf<Unit>() {
//            @Override
//            public boolean get(Unit unitt) {
//                if (unitt.team == unit.team){
//                    return true ;
//                }
//                else {
//                    return false ;
//                }
//            }
//        });
        if ( i == 300 ){
//            System.out.println(Groups.unit.size());
//            System.out.println(I);
            unit.heal(amount);
            i = 0 ;
        }
        else {
            ++ i ;
        }
        if (unit.health != 0) {
            unit.reloadMultiplier *= unit.maxHealth / unit.health;
            unit.damageMultiplier *= unit.maxHealth / unit.health;
        }
    }
}
