package shayebushi.world.blocks.environment;

import mindustry.Vars;
import mindustry.content.StatusEffects;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.StatusEffect;
import mindustry.world.blocks.environment.OreBlock;
import shayebushi.SYBSItems;

public class FangSheXingKuang extends OreBlock {
    public StatusEffect effect = StatusEffects.none ;
    public float time = 60 ;
    public float range = 5 * Vars.tilesize ;

    public FangSheXingKuang(String name, Item ore) {
        super(name, ore);
    }

    public FangSheXingKuang(Item ore) {
        super(ore);
    }

    public FangSheXingKuang(String name) {
        super(name);
    }

    public void apply(Unit unit){
        boolean b = true ;
        if (unit.type.getFirstRequirements() != null) {
            for (ItemStack i : unit.type.getFirstRequirements()) {
                if (i.item == SYBSItems.fangfushe) {
                    b = false;
                    break;
                }
            }
        }
        if (b) {
            unit.apply(effect, time);
        }
    }

    public void apply(Building b) {
        boolean bb = true ;
        if (b.block.requirements != null) {
            for (ItemStack s : b.block.requirements) {
                if (s.item == SYBSItems.fangfushe) {
                    bb = false;
                    break;
                }
            }
        }
        if (bb){
            b.damage(b.health * 0.05f / 60) ;
        }
    }
}
