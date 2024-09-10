package shayebushi.world.blocks.units;

import arc.Core;
import arc.Events;
import arc.Graphics;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.scene.ui.ButtonGroup;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.*;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.ai.UnitCommand;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.units.BuildPlan;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.io.TypeIO;
import mindustry.logic.LAccess;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.ui.Bar;
import mindustry.ui.Fonts;
import mindustry.ui.Styles;
import mindustry.world.blocks.payloads.Payload;
import mindustry.world.blocks.payloads.UnitPayload;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitBlock;
import mindustry.world.consumers.Consume;
import mindustry.world.consumers.ConsumeItems;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import shayebushi.SYBSItems;
import shayebushi.type.LanTuItem;

import static mindustry.Vars.state;

public class LanTuChongGouChang extends Reconstructor {
    public ObjectMap<Short, Integer> map = new ObjectMap<>() ;
    public LanTuChongGouChang(String name) {
        super(name);
    }
    @Override
    public void init() {
        super.init() ;
        for (Short s : map.keys()) {
            capacities[s] = map.get(s) ;
        }
    }
    public class LanTuChongGouChangBuild extends Reconstructor.ReconstructorBuild {
        /*
        @Override
        public void consume(){
            for (Consume cons : block.consumers) {
                if (cons instanceof ConsumeItems c){
                    for (ItemStack i : c.items){
                        if (!(i.item instanceof LanTuItem)){
                            cons.trigger(this);
                        }
                    }
                }
                else {
                    cons.trigger(this);
                }
            }
        }
        */
    }
}
