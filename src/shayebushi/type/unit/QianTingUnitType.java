package shayebushi.type.unit;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.ai.UnitCommand;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.WeaponMount;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.graphics.CacheLayer;
import mindustry.graphics.Layer;
import mindustry.graphics.Shaders;
import mindustry.type.UnitType;
import shayebushi.SYBSUnitCommands;
import shayebushi.entities.abilities.QianShuiAbility;

public class QianTingUnitType extends SYBSUnitType {
    public QianTingUnitType(String name) {
        super(name);
        naval = true ;
        abilities.add(new QianShuiAbility()) ;
        UnitCommand[] us = new UnitCommand[commands.length + 1] ;
        us[commands.length] = SYBSUnitCommands.xiaqianCommand ;
        System.arraycopy(commands, 0, us, 0, commands.length);
        commands = us ;
    }
    @Override
    public boolean targetable(Unit unit, Team team){
        return !((QianShuiAbility)unit.abilities[0]).isxiaqian ;
    }
    @Override
    public void applyColor(Unit unit){
        //super.applyColor(unit);
        for (Ability a : unit.abilities){
            if (a instanceof QianShuiAbility q && q.isxiaqian){
                Draw.z(Layer.shields);
                //CacheLayer.water.begin();
                //Shaders.water.apply();
                Draw.mixcol(Tmp.c1.set(unit.floorOn().mapColor).mul(0.83f), 0.859f * 0.9f);
            }
            else{
                super.applyColor(unit);
            }
        }
    }
    @Override
    public void draw(Unit u) {
        super.draw(u);
        //Draw.flush();
    }
}
