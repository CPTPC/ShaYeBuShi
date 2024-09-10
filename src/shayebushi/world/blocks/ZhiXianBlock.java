package shayebushi.world.blocks;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Lines;
import arc.math.geom.Vec2;
import arc.scene.ui.layout.Table;
import arc.util.Strings;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;
import mindustry.world.Tile;
import shayebushi.ShaYeBuShi;
import shayebushi.entities.TimedWorldLabel;

import static mindustry.Vars.content;
import static mindustry.Vars.tilesize;

public class ZhiXianBlock extends Block {
    public Color color ;
    public ZhiXianBlock(String name) {
        super(name);
        update = true ;
        configurable = true ;
    }
    @Override
    public void drawShadow(Tile t) {

    }
    public class ZhiXianBuild extends Building {
        public float angle = 0 ;
        public String pointName = "" ;
        public String targetName = "" ;
        public BaseDialog dialog ;
        public float length = 40 ;
        @Override
        public void updateTile() {
            super.updateTile();
        }
        @Override
        public void draw() {
            //super.draw() ;
            Lines.stroke(4);
            Vec2 from = ShaYeBuShi.circle(angle, Vars.world.width() * tilesize, x, y) ;
            Vec2 to = ShaYeBuShi.circle(angle + 180, Vars.world.width() * tilesize, x, y) ;
            Lines.line(from.x, from.y, to.x, to.y);
            TimedWorldLabel t = new TimedWorldLabel() ;
            t.text(pointName) ;
            t.lifetime = 5 ;
            t.x = x ;
            t.y = y ;
            t.fontSize *= 2 ;
            t.add();
            if (length > 0) {
                TimedWorldLabel t2 = new TimedWorldLabel() ;
                t2.text(targetName) ;
                t2.lifetime = 5 ;
                Vec2 v = ShaYeBuShi.circle(angle, length, x, y) ;
                t2.x = v.x ;
                t2.y = v.y ;
                t2.fontSize *= 2 ;
                t2.add();
            }
        }
        @Override
        public void buildConfiguration(Table table) {
            super.buildConfiguration(table);
            table.button(Icon.upOpen, Styles.cleari, () -> {
                dialog.show() ;
                deselect();
            }).size(40f);
        }
        @Override
        public Building create(Block b, Team t) {
            super.create(b, t) ;
            dialog = new BaseDialog(Core.bundle.get("line.zhixian")) ;
            dialog.cont.add(Core.bundle.get("line.rotation"));
            dialog.cont.field(angle + "", text -> {
                angle = Strings.parseFloat(text);
            }).width(200f).valid(Strings::canParseFloat).row();
            dialog.cont.add(Core.bundle.get("line.fromtoto"));
            dialog.cont.field(length + "", text -> {
                length = Strings.parseFloat(text);
            }).width(200f).valid(Strings::canParseFloat).row();
            dialog.cont.add(Core.bundle.get("line.from"));
            dialog.cont.field(pointName + "", text -> {
                pointName = text;
            }).width(200f).row();
            dialog.cont.add(Core.bundle.get("line.to"));
            dialog.cont.field(targetName + "", text -> {
                targetName = text;
            }).width(200f).row();
            dialog.addCloseButton();
            return this ;
        }
        @Override
        public void write(Writes write) {
            super.write(write);
            write.str(pointName);
            write.str(targetName);
            write.f(angle);
            write.f(length);
            //System.out.println(flag);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            pointName = read.str() ;
            targetName = read.str() ;
            angle = read.f() ;
            length = read.f() ;
        }
    }
}
