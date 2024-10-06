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
    public int id ;
    public static int count ;

    public void update(Building build){}
    public void draw(Building build){}
    public void death(Building build){}
    public void init(Block type){
        resetId() ;
    }

    public BlockAbility copy(){
        try{
            BlockAbility ba = (BlockAbility)clone() ;
            ba.resetId() ;
            return ba ;
        }catch(CloneNotSupportedException e){
            //I am disgusted
            throw new RuntimeException("java sucks", e);
        }
    }

    public void displayBars(Building build, Table table){

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

    public void drawPlace(int x, int y, int rotation, boolean valid){

    }

    public void drawSelect(Building b) {

    }

    public void resetId() {
        id = count ++ ;
    }
}
