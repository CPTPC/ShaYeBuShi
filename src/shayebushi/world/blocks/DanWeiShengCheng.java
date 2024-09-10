package shayebushi.world.blocks;

import arc.Core;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Table;
import arc.util.Strings;
import mindustry.Vars;
import mindustry.core.Version;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.ItemSelection;
import shayebushi.SYBSTeams;

import static mindustry.Vars.*;

public class DanWeiShengCheng extends Block {
    public DanWeiShengCheng(String name) {
        super(name);
        update = configurable = saveConfig = clearOnDoubleTap = true ;
        config(UnitType.class, (DanWeiShengChengBuild build, UnitType unit) -> {
            if(build.unit != unit){
                build.unit = unit;
            }
        });

        configClear((DanWeiShengChengBuild build) -> {
            build.unit = null;
        });
    }
    public class DanWeiShengChengBuild extends Building {
        public BaseDialog dialog = new BaseDialog(Core.bundle.get("danwei.shengcheng")) ;
        public int amount = 0 ;
        public UnitType unit ;
        public Team team2;
        @Override
        public Building create(Block b, Team t) {
            dialog.cont.add(Core.bundle.get("danwei.shuliang")).padRight(10f);
            dialog.cont.field(amount + "", text -> {
                amount = Strings.parseInt(text);
            }).width(200f).valid(Strings::canParseInt);
            dialog.cont.row() ;
            dialog.cont.add(Core.bundle.get("danwei.zhonglei")).padRight(10f);
            ItemSelection.buildTable(DanWeiShengCheng.this, dialog.cont, Vars.content.units(), () -> unit, this::configure, false, selectionRows, selectionColumns);
            dialog.cont.row() ;
            dialog.cont.add(Core.bundle.get("danwei.duiwu")).padRight(10f);
            int i2 = 0 ;
            int ii = 0 ;
            for(Team t2 : Team.baseTeams){
                ImageButton button = new ImageButton(Tex.whiteui, Styles.clearNoneTogglei);
                button.margin(4f);
                button.getImageCell().grow();
                button.getStyle().imageUpColor = t2.color;
                button.clicked(() -> team2 = t2);
                button.update(() -> button.setChecked(team2 == t2));
                button.resizeImage(40);
                dialog.cont.add(button);

                if(i2++ % 3 == 2) dialog.cont.row();
            }
            for(Team t2 : SYBSTeams.sybsTeams){
                ImageButton button = new ImageButton(Tex.whiteui, Styles.clearNoneTogglei);
                button.margin(4f);
                button.getImageCell().grow();
                button.getStyle().imageUpColor = t2.color;
                button.clicked(() -> team2 = t2);
                button.update(() -> button.setChecked(team2 == t2));
                dialog.cont.add(button);

                if(ii++ % 3 == 2) dialog.cont.row();
            }
            dialog.cont.add(Core.bundle.get("danwei.shengcheng")).padRight(10f);
            dialog.cont.button(Icon.upOpen, Styles.cleari, () -> {
                for (Tile ti : Vars.spawner.getSpawns()) {
                    for (int i = 0 ; i < amount ; i ++) {
                        if (team2 == null || unit == null) break ;
                        Unit u = unit.create(team2) ;
                        u.set(ti.x * tilesize, ti.y * tilesize);
                        u.add();
                        if (Version.build == -1 && false) {
                            player.team(team2) ;
                            if (player.unit() != null) {
                                player.unit().team(team2) ;
                            }
                        }
                    }
                }
                deselect();
            }).size(40f);
            dialog.cont.row() ;
            dialog.cont.button("@back", () -> {
                dialog.hide();
            }) ;
            return super.create(b, t) ;
        }
        @Override
        public void buildConfiguration(Table t) {
            super.buildConfiguration(t);
            t.button(Icon.upOpen, Styles.cleari, () -> {
                dialog.show() ;
                deselect();
            }).size(40f);
        }
    }
}
