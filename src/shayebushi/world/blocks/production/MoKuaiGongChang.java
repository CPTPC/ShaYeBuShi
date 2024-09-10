package shayebushi.world.blocks.production;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Vec2;
import arc.struct.EnumSet;
import arc.struct.Seq;
import arc.util.Eachable;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.graphics.Shaders;
import mindustry.logic.LAccess;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.consumers.Consume;
import mindustry.world.consumers.ConsumeItems;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;
import shayebushi.type.LanTuItem;
import shayebushi.world.blocks.units.LanTuChongGouChang;

import static mindustry.Vars.tilesize;

public class MoKuaiGongChang extends GenericCrafter {
    public MoKuaiGongChang(String name) {
        super(name);
    }
    @Override
    public void setBars(){
        super.setBars();
        addBar("progress", (MoKuaiGongChangBuild entity) -> new Bar("bar.progress", Pal.ammo, entity::progress));
    }
    public class MoKuaiGongChangBuild extends GenericCrafterBuild {
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
        @Override
        public void draw() {
            super.draw() ;
            float rad = tilesize * 2 ;
            Draw.draw(Layer.blockBuilding, () -> {
                if (efficiency <= 0) return ;
                Draw.color(Pal.accent);

                Shaders.blockbuild.region = outputItems[0].item.uiIcon;
                Shaders.blockbuild.time = Time.time;
                Shaders.blockbuild.progress = Mathf.clamp(fraction() + 0.05f);

                Draw.rect(outputItems[0].item.uiIcon, x, y, rad, rad);
                Draw.flush();
                Draw.color();
            });
            float rad2 = size * tilesize / 2f ;
            Draw.reset();

            Draw.z(Layer.buildBeam);

            Draw.mixcol(Tmp.c1.set(Pal.accent), 1f);
            Draw.rect(outputItems[0].item.uiIcon, x, y, rad, rad);
            Draw.color() ;
        }
        public float fraction() {
            return progress / craftTime ;
        }
    }
}
