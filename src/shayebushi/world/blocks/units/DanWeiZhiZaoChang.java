package shayebushi.world.blocks.units;

import arc.struct.Seq;
import arc.util.Structs;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.world.blocks.units.UnitAssembler;
import mindustry.world.blocks.units.UnitFactory;
import shayebushi.SYBSBlocks;
import shayebushi.ShaYeBuShi;

import static mindustry.Vars.state;
import static shayebushi.SYBSBlocks.shenlingzhuangpei;

public class DanWeiZhiZaoChang extends UnitFactory {
    public DanWeiZhiZaoChang(String name) {
        super(name);
    }

    public boolean canProduce(UnitType t){
        return !t.isHidden() && !t.isBanned() && t.supportsEnv(state.rules.env);
    }

    public void init() {
        plans = new Seq<UnitPlan>(Vars.content.units().size);
        for (UnitType u : Vars.content.units()){
            if (!u.isHidden() && u.health <= 60000 && u.getFirstRequirements() != null && canProduce(u) && isNotT6(u)){
                if (u.health <= 1000) {
                    plans.add(new UnitPlan(u, 1, u.getFirstRequirements()));
                }
                else if (u.health > 1000 && u.health <= 12000) {
                    ItemStack[] iss = u.getFirstRequirements() ;
                    for (ItemStack is : iss) {
                        is.amount *= 1.1f ;
                    }
                    plans.add(new UnitPlan(u, u.getBuildTime() * 0.2f, iss));
                }
                else if (u.health > 12000 && u.health <= 30000) {
                    ItemStack[] iss = u.getFirstRequirements() ;
                    for (ItemStack is : iss) {
                        is.amount *= 1.25f ;
                    }
                    plans.add(new UnitPlan(u, u.getBuildTime() * 0.4f, iss));
                }
                else if (u.health > 30000 && u.health <= 50000) {
                    ItemStack[] iss = u.getFirstRequirements() ;
                    for (ItemStack is : iss) {
                        is.amount *= 1.5f ;
                    }
                    plans.add(new UnitPlan(u, u.getBuildTime() / 2, iss));
                }
            }
        }
        consumePower(60) ;
        super.init() ;
    }
    public boolean isNotT6(UnitType u) {
        Seq<Seq<UnitAssembler.AssemblerUnitPlan>> s = new Seq<>() ;
        s.add(SYBSBlocks.feichuanchang.plans, SYBSBlocks.jijiachang.plans, SYBSBlocks.tankechang.plans) ;
        return !shenlingzhuangpei.upgrades.contains(u2 -> u2[1] == u)
                && !s.get(u.flying ? 0 : u.legCount > 0 ? 1 : 2).contains(u2 -> u2.unit == u) ;
    }
    public class DanWeiZhiZaoChangBuild extends UnitFactoryBuild{
        @Override
        public boolean acceptItem(Building source, Item item){
            if (currentPlan >= plans.size) {
                currentPlan = -1 ;
            }
            return currentPlan != -1 && items.get(item) < getMaximumAccepted(item) &&
                    Structs.contains(plans.get(currentPlan).requirements, stack -> stack.item == item);
        }
    }
}
