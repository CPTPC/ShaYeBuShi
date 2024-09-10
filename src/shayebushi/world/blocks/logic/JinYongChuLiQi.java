package shayebushi.world.blocks.logic;

import arc.Core;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectSet;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.core.Version;
import mindustry.entities.Units;
import mindustry.game.Gamemode;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;
import mindustry.world.blocks.ItemSelection;
import shayebushi.SYBSTeams;
import shayebushi.ShaYeBuShi;

import static mindustry.Vars.*;

public class JinYongChuLiQi extends Block {
    public JinYongChuLiQi(String name) {
        super(name);
        targetable = false ;
        update = configurable = saveConfig = clearOnDoubleTap = true ;
        config(Block.class, (JinYongLuoJiChuLiQiBuild build, Block unit) -> {
            if(build.block != unit){
                build.block = unit;
            }
        });

        configClear((JinYongLuoJiChuLiQiBuild build) -> {
            build.block = null;
        });
    }
    public class JinYongLuoJiChuLiQiBuild extends Building {
        public BaseDialog dialog = new BaseDialog(Core.bundle.get("jianzhu.jinyong")) ;
        public String flag = "" ;
        public Block block ;
        public Team team2;
        public ObjectSet<Building> build = new ObjectSet<>() ;
        @Override
        public void damage(float d) {

        }
        @SuppressWarnings("deprecation")
        @Override
        public Building create(Block b, Team t) {
            dialog.cont.add(Core.bundle.get("jianzhu.tiaojian")).padRight(10f);
            dialog.cont.field(flag, text -> {
                flag = text;
            }).width(200f);
            dialog.cont.row() ;
            dialog.cont.add(Core.bundle.get("jianzhu.zhonglei")).padRight(10f);
            ItemSelection.buildTable(JinYongChuLiQi.this, dialog.cont, Vars.content.blocks().copy().filter(b2 -> !b2.isFloor() && !b2.isOverlay() && !b2.isStatic()), () -> block, this::configure, false, selectionRows, selectionColumns);
            dialog.cont.row() ;
            dialog.cont.add(Core.bundle.get("jianzhu.duiwu")).padRight(10f);
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
            dialog.cont.button("@back", () -> {
                dialog.hide();
            }) ;
            return super.create(b, t) ;
        }
        @Override
        public void buildConfiguration(Table t) {
            super.buildConfiguration(t);
            t.button(Icon.upOpen, Styles.cleari, () -> {
                if (state.isEditor() || ShaYeBuShi.tiaoshi || Version.build == -1 || state.rules.mode() == Gamemode.sandbox) {
                    dialog.show();
                    deselect();
                }
            }).size(40f);
        }
        @Override
        public void updateTile() {
            super.updateTile() ;
            Units.nearbyBuildings(x, y, Integer.MAX_VALUE, b -> {
                if (b.block == block && b.team == team2) {
                    b.enabled = state.rules.objectiveFlags.contains(flag);
                }
            });
            //System.out.println(state.rules.objectiveFlags.contains(flag));
        }
        @Override
        public void write(Writes write) {
            super.write(write);
            write.str(flag);
            write.i(block.id);
            write.i(team2.id);
            //System.out.println(flag);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            flag = read.str() ;
            //System.out.println(flag + "r");
            block = content.block(read.i()) ;
            team2 = Team.get(read.i()) ;
        }
    }
}
