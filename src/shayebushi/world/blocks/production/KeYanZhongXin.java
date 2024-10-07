package shayebushi.world.blocks.production;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.graphics.Shaders;
import mindustry.type.*;
import mindustry.ui.Bar;
import mindustry.world.*;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.meta.*;
import shayebushi.type.LanTuItem;
//import shayebushi.ItemSelection;

import static mindustry.Vars.*;

public class KeYanZhongXin extends Block{
    public int itemsPerSecond = 100;
    public int time = 1800 ;
    public KeYanZhongXin(String name){
        super(name);
        hasItems = true;
        update = true;
        solid = true;
        group = BlockGroup.transportation;
        configurable = true;
        saveConfig = true;
        noUpdateDisabled = true;
        envEnabled = Env.any;
        clearOnDoubleTap = true;
        consumePower(50);
        config(Item.class, (KeYanZhongXinBuild tile, Item item) -> tile.outputItem = item);
        configClear((KeYanZhongXinBuild tile) -> tile.outputItem = null);
    }

    @Override
    public void setBars(){
        super.setBars();
        removeBar("items");
        addBar("progress", (KeYanZhongXinBuild entity) -> new Bar("bar.progress", Pal.ammo, entity::fraction));
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.output, itemsPerSecond - itemsPerSecond + 60f / time, StatUnit.itemsSecond);
    }

    @Override
    public boolean outputsItems(){
        return true;
    }

    public class KeYanZhongXinBuild extends Building{
        public float counter;
        public Item outputItem;
        public float fraction(){
            return counter / time;
        }
        @Override
        public void draw(){
            if(outputItem == null){
                Draw.rect("cross-full", x, y);
            }else{
                Draw.color(outputItem.color);
                Fill.square(x, y, tilesize/2f - 0.00001f);
                Draw.color();
            }

            super.draw();
            float rad = tilesize * 2f ;
            if (outputItem == null) return ;
            Draw.draw(Layer.blockBuilding, () -> {
                if (efficiency <= 0) return ;
                Draw.color(Pal.accent);

                Shaders.blockbuild.region = outputItem.uiIcon;
                Shaders.blockbuild.time = Time.time;
                //margin due to units not taking up whole region
                Shaders.blockbuild.progress = Mathf.clamp(counter / time + 0.05f);

                Draw.rect(outputItem.uiIcon, x, y, rad, rad);
                Draw.flush();
                Draw.color();
            });
            float rad2 = size * tilesize / 2f ;
            Draw.reset();

            Draw.z(Layer.buildBeam);

            Draw.mixcol(Tmp.c1.set(Pal.accent), 1f);
            Draw.rect(outputItem.uiIcon, x, y, rad, rad);
            if (efficiency <= 0) return ;
            for (Vec2 v : new Vec2[]{new Vec2(rad2, rad2), new Vec2(-rad2, rad2), new Vec2(-rad2, -rad2), new Vec2(rad2, -rad2)}) {
                Drawf.buildBeam(x + v.x, y + v.y, x, y, tilesize);
            }
            Draw.color() ;
        }

        @Override
        public void updateTile(){
            if(outputItem == null) return;

            counter += edelta();
//            float limit = 60f / itemsPerSecond;
//
//            while(counter >= limit){
//                items.set(outputItem, 1);
//                dump(outputItem);
//                items.set(outputItem, 0);
//                counter -= limit;
//            }
            if (counter >= time){
                offload(outputItem) ;
                counter = 0 ;
            }
        }

        @Override
        public void buildConfiguration(Table table){
            ItemSelection.buildTable(KeYanZhongXin.this, table, content.items().select(i -> i instanceof LanTuItem), () -> outputItem, this::configure, selectionRows, selectionColumns);
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return false;
        }

        @Override
        public Item config(){
            return outputItem;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.s(outputItem == null ? -1 : outputItem.id);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            outputItem = content.item(read.s());
        }
    }
}