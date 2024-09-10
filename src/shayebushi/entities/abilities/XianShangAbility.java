package shayebushi.entities.abilities;

import arc.Core;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

/** Spawns a certain amount of units upon death. */
public class XianShangAbility extends Ability {
    public UnitType unittt;
    public float amount = 1;
    public int randAmount = 0;
    /** Random spread of units away from the spawned. */
    public float max = 8f;
    /** If true, units spawned face outwards from the middle. */
    public boolean faceOutwards = true;

    public XianShangAbility(UnitType unit, float amont){
        this.unittt = unit;
        this.amount = amont ;
    }

    public XianShangAbility(){
    }
    @Override
    public String localized(){
        return Core.bundle.format("ability.pofuchenzhou");
    }

//    int i = 0 ;
//    float II = this.unittt.create(Team.derelict).maxHealth;
//    float III = this.unittt.create(Team.derelict).armor;
//    float IV = this.unittt.create(Team.derelict).damageMultiplier;
//    float V = this.unittt.create(Team.derelict).speedMultiplier;
//    float VI = this.unittt.create(Team.derelict).reloadMultiplier;
    @Override
    public void update(Unit unit){
        super.update(unit);
        if (unit.hitTime > 0){

        }
    }
}
