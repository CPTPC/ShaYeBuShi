package shayebushi.entities.abilities;

import arc.Core;
import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.entities.abilities.Ability;
import mindustry.gen.*;
import mindustry.type.*;
import shayebushi.SYBSStatusEffects;
import shayebushi.SYBSUnitTypes;

/** Spawns a certain amount of units upon death. */
public class ChongShengAbility extends Ability {
    public UnitType unit;
    public UnitType unitt ;
    public int amount = 1, randAmount = 0;
    /** Random spread of units away from the spawned. */
    public float spread = 8f;
    /** If true, units spawned face outwards from the middle. */
    public boolean faceOutwards = true;
    public ChongShengAbility(UnitType unit, int amount, float spread, UnitType unitt){
        this.unit = unit;
        this.unit = SYBSUnitTypes.shuangxingyijieduan ;
        this.amount = amount;
        this.spread = spread;
        this.unitt = unitt;
        this.unitt = SYBSUnitTypes.shuangxingerjieduan ;
    }

    public ChongShengAbility(){
    }
    @Override
    public String localized(){
        return Core.bundle.format("ability.chongsheng",(unitt.health / unit.health) * 100 + "%");
    }

    @Override
    public void death(Unit unit){
        if(!Vars.net.client()){
            unit.trail = null ;
            int spawned = amount + Mathf.random(randAmount);
            for(int i = 0; i < spawned; i++){
                Tmp.v1.rnd(Mathf.random(spread));
                var u = this.unitt.spawn(unit.team, unit.x + Tmp.v1.x, unit.y + Tmp.v1.y);
                u.apply(SYBSStatusEffects.denglizihua,600);
                u.rotation = faceOutwards ? Tmp.v1.angle() : unit.rotation + Mathf.range(5f);
            }
        }
    }
}
