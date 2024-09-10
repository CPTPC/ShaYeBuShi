package shayebushi.world.blocks;

import arc.math.Rand;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.UnitTypes;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.type.PayloadStack;
import mindustry.type.UnitType;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.blocks.storage.CoreBlock;
import shayebushi.SYBSBlocks;
import shayebushi.SYBSUnitTypes;


import static mindustry.Vars.net;

public class ZengYuanBuShuBlock extends Block {
    public ObjectMap<Block, Seq<PayloadStack>> map = new ObjectMap<>() ;
    public ZengYuanBuShuBlock(String name) {
        super(name);
        update = true;
        configurable = true ;
    }

    @Override
    public void init() {
        super.init();
        map.put(Blocks.coreShard, PayloadStack.list(UnitTypes.corvus, 3));
        map.put(Blocks.coreFoundation, PayloadStack.list(UnitTypes.toxopid, 5));
        map.put(Blocks.coreNucleus, PayloadStack.list(UnitTypes.eclipse, 5, UnitTypes.reign, 5));
        map.put(Blocks.coreBastion, PayloadStack.list(UnitTypes.disrupt, 4));
        map.put(Blocks.coreCitadel, PayloadStack.list(UnitTypes.conquer, 3, UnitTypes.collaris, 3));
        map.put(Blocks.coreAcropolis, PayloadStack.list(UnitTypes.conquer, 2, UnitTypes.collaris, 2, UnitTypes.disrupt, 2, SYBSUnitTypes.moneng, 1));
        map.put(SYBSBlocks.yijihexin, PayloadStack.list(SYBSUnitTypes.xieling, 1, SYBSUnitTypes.anye, 1));
    }

    public class ZengYuanBuShuBuild extends Building {
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
            if (bushu){
                kill();
                Rand r = new Rand() ;
                CoreBlock.CoreBuild b = Vars.state.teams.cores(team).get(r.random(0,Vars.state.teams.cores(team).size - 1));
                b.kill();
                if (map.containsKey(b.block)) {
                    for (PayloadStack p : map.get(b.block)) {
                        if (p.item instanceof UnitType u) {
                            for (int i = 0; i < p.amount; i++) {
                                Unit unit = u.create(team);
                                unit.add();
                                unit.x = b.x + r.random(-b.block.size * 5, b.block.size * 5);
                                unit.y = b.y + r.random(-b.block.size * 5, b.block.size * 5);
                            }
                        }
                    }
                }
                else {
                    float h = b.block.health * 5 ;
                    while(h > 0){
                        Unit unit = Vars.content.units().random(r).create(team) ;
                        if (unit instanceof BlockUnitc) continue ;
                        unit.add();
                        unit.x = b.x + r.random(-b.block.size * 5, b.block.size * 5);
                        unit.y = b.y + r.random(-b.block.size * 5, b.block.size * 5);
                        h -= unit.type.health ;
                    }
                }
                bushu = false ;
            }
        }
    }
}
