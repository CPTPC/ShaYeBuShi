package shayebushi.world.sandbox;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import arc.util.Eachable;
import mindustry.Vars;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.tilesize;

public class WuPinYuan extends Block {
    public int itemsPerSecond = 100;

    public WuPinYuan(String name){
        super(name);
        hasItems = true;
        update = true;
        solid = true;
        group = BlockGroup.transportation;
        //configurable = true;
        saveConfig = true;
        noUpdateDisabled = true;
        envEnabled = Env.any;
        itemCapacity = 100 ;
//        clearOnDoubleTap = true;
//
//        config(Item.class, (WuPinYuanBuild tile, Item item) -> tile.outputItem = item);
//        configClear((WuPinYuanBuild tile) -> tile.outputItem = null);
    }

    @Override
    public void setBars(){
        super.setBars();
        removeBar("items");
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.output, itemsPerSecond, StatUnit.itemsSecond);
    }

    @Override
    protected TextureRegion[] icons(){
        return new TextureRegion[]{Core.atlas.find("source-bottom"), region};
    }

    @Override
    public void drawPlanConfig(BuildPlan plan, Eachable<BuildPlan> list){
        drawPlanConfigCenter(plan, plan.config, "center", true);
    }

    @Override
    public boolean outputsItems(){
        return true;
    }

    public class WuPinYuanBuild extends Building {
        public float counter;
        public Item outputItem;

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
        }

        @Override
        public void updateTile(){
            //if(outputItem == null) return;

            counter += edelta();
            float limit = 60f / itemsPerSecond;


            while(counter >= limit){
                for (Item item : Vars.content.items()) {
                    items.set(item, 1);
                    dump(item);
                    items.set(item, 0);
                }
                counter -= limit;
            }
        }


        @Override
        public boolean acceptItem(Building source, Item item){
            return false;
        }

        @Override
        public Item config(){
            return outputItem;
        }

    }
}
