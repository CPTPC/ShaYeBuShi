package shayebushi.world.blocks.abilities;

import arc.Core;
import arc.scene.ui.layout.Table;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import mindustry.world.Block;

public class BlockAbility implements Cloneable {
    public boolean display = true;
    public float data;

    public void update(Building build){}
    public void draw(Building build){}
    public void death(Building build){}
    public void init(Block type){
        displayBars(type);
    }

    public BlockAbility copy(){
        try{
            return (BlockAbility)clone();
        }catch(CloneNotSupportedException e){
            //I am disgusted
            throw new RuntimeException("java sucks", e);
        }
    }

    public void displayBars(Block build){

    }

    public String localized(){
        var type = getClass();
        return Core.bundle.get("ability." + (type.isAnonymousClass() ? type.getSuperclass() : type).getSimpleName().replace("Ability", "").toLowerCase());
    }

    public void onBuilt(Building b) {

    }

    public void onRemoved(Building b) {

    }

    public void onRead(Building b) {

    }
}
