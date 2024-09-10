package shayebushi.ui.dialogs;

import arc.Core;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Table;
import arc.struct.OrderedMap;
import arc.struct.Seq;
import arc.util.Scaling;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Iconc;
import mindustry.graphics.Pal;
import mindustry.ui.dialogs.ContentInfoDialog;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;
import mindustry.world.meta.StatValue;
import mindustry.world.meta.Stats;

import static arc.Core.settings;
import static mindustry.Vars.iconXLarge;
import static shayebushi.SYBSUnitTypes.cptpc;

public class SYBSContentInfoDialog extends ContentInfoDialog {
    public UnlockableContent curretContent ;
    @Override
    public void show(UnlockableContent u) {
        cont.clear();

        Table table = new Table();
        table.margin(10);

        u.checkStats();

        if (u == cptpc) {
            table.add("天子守国门，君王死社稷\n[#ffa480]神！不惧死亡！[]").fontScale(33);
            table.row();
            ScrollPane pane = new ScrollPane(table);
            cont.add(pane);
            show();
            return ;
        }

        table.table(title1 -> {
            title1.image(u.uiIcon).size(iconXLarge).scaling(Scaling.fit);
            var b = title1.button("", () -> {}).padLeft(5).width(250) ;
            title1.update(() -> b.get().setText("[accent]" + u.localizedName + (settings.getBool("console") ? "\n[gray]" + u.name : ""))) ;
        });

        table.row();

        if(u.description != null){
            var any = u.stats.toMap().size > 0;

            if(any){
                table.add("@category.purpose").color(Pal.accent).fillX().padTop(10);
                table.row();
            }

            table.add("[lightgray]" + u.displayDescription()).wrap().fillX().padLeft(any ? 10 : 0).width(500f).padTop(any ? 0 : 10).left();
            table.row();

            if(!u.stats.useCategories && any){
                table.add("@category.general").fillX().color(Pal.accent);
                table.row();
            }
        }

        Stats stats = u.stats;

        for(StatCat cat : stats.toMap().keys()){
            OrderedMap<Stat, Seq<StatValue>> map = stats.toMap().get(cat);

            if(map.size == 0) continue;

            if(stats.useCategories){
                table.add("@category." + cat.name).color(Pal.accent).fillX();
                table.row();
            }

            for(Stat stat : map.keys()){
                table.table(inset -> {
                    inset.left();
                    inset.add("[lightgray]" + stat.localized() + ":[] ").left().top();
                    Seq<StatValue> arr = map.get(stat);
                    for(StatValue value : arr){
                        value.display(inset);
                        inset.add().size(10f);
                    }

                }).fillX().padLeft(10);
                table.row();
            }
        }

        if(u.details != null){
            table.add("[gray]" + (u.unlocked() || !u.hideDetails ? u.details : Iconc.lock + " " + Core.bundle.get("unlock.incampaign"))).pad(6).padTop(20).width(400f).wrap().fillX();
            table.row();
        }

        u.displayExtra(table);

        ScrollPane pane = new ScrollPane(table);
        cont.add(pane);

        show();
    }
}
