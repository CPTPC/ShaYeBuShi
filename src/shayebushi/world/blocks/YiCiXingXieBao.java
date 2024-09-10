package shayebushi.world.blocks;

import arc.math.Rand;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.UnitTypes;
import mindustry.entities.Units;
import mindustry.gen.BlockUnitc;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Unit;
import mindustry.type.PayloadStack;
import mindustry.type.UnitType;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.blocks.storage.CoreBlock;
import shayebushi.SYBSBlocks;
import shayebushi.SYBSUnitTypes;

import static mindustry.Vars.net;
import static mindustry.Vars.state;

public class YiCiXingXieBao extends Block {

    public YiCiXingXieBao(String name) {
        super(name);
        update = true;
        configurable = true ;
    }

    public class YiCiXingXieBaoBuild extends Building {
        public boolean bushu ;
        @Override
        public void buildConfiguration(Table table){
            super.buildConfiguration(table);
            if(net.client()){
                deselect();
                return;
            }

            table.button(Icon.upOpen, Styles.cleari, () -> {
                bushu = true ;
                deselect();
            }).size(40f);
        }
        @Override
        public void updateTile(){
            super.updateTile();
            if (bushu || team == state.rules.waveTeam){
                kill();
                Units.nearbyBuildings(x, y, Integer.MAX_VALUE, b -> {
                    if (b.team == team) {
                        //System.out.println(b.getDisplayName());
                        b.heal(b.maxHealth);
                    }
                });
                bushu = false ;
            }
        }
    }
}
