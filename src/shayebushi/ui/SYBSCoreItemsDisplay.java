package shayebushi.ui;

import arc.Core;
import arc.struct.ObjectSet;
import mindustry.Vars;
import mindustry.core.UI;
import mindustry.type.Item;
import mindustry.ui.CoreItemsDisplay;
import mindustry.ui.Styles;
import mindustry.world.blocks.storage.CoreBlock;
import shayebushi.SYBSItems;

import static mindustry.Vars.content;
import static mindustry.Vars.iconSmall;

public class SYBSCoreItemsDisplay extends CoreItemsDisplay {
    public ObjectSet<Item> usedItems = new ObjectSet<>();
    public CoreBlock.CoreBuild core;
    public int timer = 0 ;
    public static boolean inited = false ;

    public SYBSCoreItemsDisplay(){
        rebuild(false);
        update(() -> {
            core = Vars.player.team().core();
            content.items().contains(item -> core != null && core.items.get(item) > 0 && usedItems.add(item)) ;
            timer ++ ;
            if (timer >= 20) {
                rebuild(true);
                timer = 0 ;
            }
        }) ;
    }

    public void resetUsed(){
        usedItems.clear();
        background(null);
    }

    public void rebuild(boolean once){
        clear();
        if(usedItems.size > 0){
            background(Styles.black6);
            margin(4);
        }

        if (!once) {
            update(() -> {
                core = Vars.player.team().core();

                if (content.items().contains(item -> core != null && core.items.get(item) > 0 && usedItems.add(item))) {
                    rebuild(true);
                }
            });
        }

        int i = 0;

        for(Item item : content.items()){
            if(usedItems.contains(item) && (Core.settings.getBool((item instanceof SYBSItems.SYBSItem s && s.colors.size > 0 ? s.rawLocalizedName : item.localizedName)) || !inited)){
                image(item.uiIcon).size(iconSmall).padRight(3).tooltip(t -> t.background(Styles.black6).margin(4f).add(item.localizedName).style(Styles.outlineLabel));
                //TODO leaks garbage
                label(() -> core == null ? "0" : UI.formatAmount(core.items.get(item))).padRight(3).minWidth(52f).left().tooltip(t -> t.background(Styles.black6).margin(4f).label(() -> core == null ? "0" : core.items.get(item) + "").style(Styles.outlineLabel));

                if(++i % 4 == 0){
                    row();
                }
            }
        }

    }
}
