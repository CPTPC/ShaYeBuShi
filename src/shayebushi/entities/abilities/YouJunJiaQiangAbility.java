package shayebushi.entities.abilities;

import arc.Core;
import arc.func.Boolf;
import arc.math.*;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.*;
import mindustry.*;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.type.unit.MissileUnitType;
import mindustry.ui.Bar;
import shayebushi.SYBSUnitTypes;
import shayebushi.entities.units.JieTic;
import shayebushi.type.unit.YuLeiUnitType;

/** Spawns a certain amount of units upon death. */
public class YouJunJiaQiangAbility extends Ability {
    public UnitType unittt;
    public float amount = 1;
    public int randAmount = 0;
    /** Random spread of units away from the spawned. */
    public float max = 8f;
    /** If true, units spawned face outwards from the middle. */
    public boolean faceOutwards = true;
    public float p, pp ;
    public YouJunJiaQiangAbility(UnitType unit, float amount, float max){
        this.unittt = unit;
        this.unittt = SYBSUnitTypes.shenqi ;
        this.amount = amount;
        this.max = max;
    }
    public YouJunJiaQiangAbility(float amount, float max){
        this.amount = amount;
        this.max = max;
    }
    public YouJunJiaQiangAbility(){
    }
    @Override
    public String localized(){
        return Core.bundle.format("ability.youjunjiaqiang", amount*100+"%", max*100+"%");
    }

//    int i = 0 ;
    //this.unittt = SYBSUnitTypes.shenqi ;
//    float II = this.unittt.create(Team.derelict).maxHealth;
//    float III = this.unittt.create(Team.derelict).armor;
//    float IV = this.unittt.create(Team.derelict).damageMultiplier;
//    float V = this.unittt.create(Team.derelict).speedMultiplier;
//    float VI = this.unittt.create(Team.derelict).reloadMultiplier;
//    float IV = SYBSUnitTypes.shenqi.create(Team.derelict).damageMultiplier;
//    float V = SYBSUnitTypes.shenqi.create(Team.derelict).speedMultiplier;
//    float VI = SYBSUnitTypes.shenqi.create(Team.derelict).reloadMultiplier;
//    int VII = 0 ;
    @Override
    public void update(Unit unit){
//        int I = Units.count(-Vars.world.width(),-Vars.world.height(),  Integer.MAX_VALUE, new Boolf<Unit>() {
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
        int I = 0 ;
        Seq<Unit> s = new Seq<>() ;
        Tmp.v1.trns(unit.rotation - 90, unit.x, unit.y).add(unit.x, unit.y);
        float rx = Tmp.v1.x, ry = Tmp.v1.y;
        Units.nearby(unit.team, rx, ry, Integer.MAX_VALUE, other -> {
            if (other.team == unit.team && !(other.type instanceof MissileUnitType) && !(other instanceof JieTic) && !(other.type instanceof YuLeiUnitType)){
                s.add(other) ;
            }
        });
        I = s.size - 1 ;
//        if ( i == 60 ){
//            for (Unit u : Groups.unit){
//                if (u.team == unit.team){
//                    VII ++ ;
//                }
//            }
//            System.out.println(VII);
//            System.out.println(I);
//            i = 0 ;
//        }
//        else {
//            ++ i ;
//        }
        float h = unit.health ;
        float m = unit.maxHealth ;
        p = max / amount ;
        pp = Math.min(I , p) ;
        unit.maxHealth = unit.type.health * (1 + pp * amount) ;
        unit.health = (h * unit.maxHealth / m) ;
        unit.health = Math.min(unit.health, unit.maxHealth) ;
        unit.armor = unit.type.armor * (1 + pp * amount) ;
        unit.damageMultiplier *= (1 + pp * amount) ;
        unit.speedMultiplier *= (1 + pp * amount);
        unit.reloadMultiplier *= (1 + pp * amount);
        //System.out.println(unit.health);
    }
    @Override
    public void displayBars(Unit unit , Table table){
        super.displayBars(unit , table);
        table.add(new Bar(() -> Core.bundle.format("bar.jiaqiangfudu" , pp * amount * 100 + "%") , () -> Pal.accent , () -> pp / p));
        table.row() ;
    }
}
