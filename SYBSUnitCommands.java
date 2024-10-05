package shayebushi;

import mindustry.ai.UnitCommand;
import mindustry.entities.abilities.Ability;
import shayebushi.entities.abilities.QianShuiAbility;
import shayebushi.type.unit.QianTingUnitType;

public class SYBSUnitCommands {
    public static UnitCommand xiaqianCommand, shangfuCommand;

    public static void load() {
        xiaqianCommand = new UnitCommand("xiaiqan", "down", u -> {
            if (u.type instanceof QianTingUnitType) {
                for (Ability a : u.abilities) {
                    if (a instanceof QianShuiAbility q) {
                        q.xiaQian(u);
                    }
                }
            }
            return null ;
        }){{
            drawTarget = true;
            resetTarget = false;
        }} ;
        shangfuCommand = new UnitCommand("shangfu", "up", u -> {
            if (u.type instanceof QianTingUnitType) {
                for (Ability a : u.abilities) {
                    if (a instanceof QianShuiAbility q) {
                        q.shangFu(u);
                    }
                }
            }
            return null ;
        }){{
            drawTarget = true;
            resetTarget = false;
        }} ;
    }
}
