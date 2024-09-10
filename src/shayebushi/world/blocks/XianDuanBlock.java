package shayebushi.world.blocks;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Lines;
import arc.math.geom.Vec2;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
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

public class XianDuanBlock extends Block {
    public Color color ;
    public XianDuanBlock(String name) {
        super(name);
        update = true ;
        configurable = true ;
    }
    @Override
    public void drawShadow(Tile t) {

    }
    public class XianDuanBuild extends Building {
        public BaseDialog dialog ;
        public Seq<Line> lines = new Seq<>() ;
        @Override
        public void updateTile() {
            super.updateTile();
        }
        @Override
        public void draw() {
            //super.draw() ;
            for (Line l : lines) {
                l.draw();
            }
            //System.out.println(lines.size);
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
            dialog = new BaseDialog(Core.bundle.get("line.xianduan")) ;
            dialog.cont.pane(i -> {
                i.button("@add", Icon.add, () -> build(i)).size(300, 64).row() ;
            }).row() ;
            dialog.addCloseButton();
            return this ;
        }
        public void build(Table t) {
            Line l = new Line() ;
            l.x = x ;
            l.y = y ;
            l.build(t);
            lines.add(l) ;
        }
        @Override
        public void write(Writes write) {
            super.write(write);
            write.i(lines.size);
            for (Line l : lines) {
                l.write(write);
            }
            //System.out.println(flag);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            int size = read.i() ;
            for (int i = 0 ; i < size ; i ++) {
                Line l = new Line() ;
                l.read(read, revision);
                lines.add(l) ;
            }
        }
    }
    public class Line {
        public float angle = 0 ;
        public String pointName = "" ;
        public String targetName = "" ;
        public float length = 40 ;
        public float x, y ;
        public TimedWorldLabel current ;
        public void draw() {
            Lines.stroke(4);
            Vec2 to = ShaYeBuShi.circle(angle, length, x, y) ;
            Lines.line(x, y, to.x, to.y);
            if (current == null || !current.isAdded()) {
                TimedWorldLabel t = new TimedWorldLabel();
                t.text(pointName);
                t.lifetime = 5;
                t.x = x;
                t.y = y;
                t.fontSize *= 2;
                t.add();
                current = t ;
                if (length > 0) {
                    TimedWorldLabel t2 = new TimedWorldLabel();
                    t2.text(targetName);
                    t2.lifetime = 5;
                    t2.x = to.x;
                    t2.y = to.y;
                    t2.fontSize *= 2;
                    t2.add();
                }
            }
        }
        public void build(Table t) {
            t.add(Core.bundle.get("line.rotation"));
            t.field(angle + "", text -> {
                angle = Strings.parseFloat(text);
            }).width(200f).valid(Strings::canParseFloat).row();
            t.add(Core.bundle.get("line.length"));
            t.field(length + "", text -> {
                length = Strings.parseFloat(text);
            }).width(200f).valid(Strings::canParseFloat).row();
            t.add(Core.bundle.get("line.from"));
            t.field(pointName + "", text -> {
                pointName = text;
            }).width(200f).row();
            t.add(Core.bundle.get("line.to"));
            t.field(targetName + "", text -> {
                targetName = text;
            }).width(200f).row();
        }
        public void write(Writes write) {
            write.str(pointName);
            write.str(targetName);
            write.f(angle);
            write.f(length);
            write.f(x);
            write.f(y);
        }

        public void read(Reads read, byte revision) {
            pointName = read.str() ;
            targetName = read.str() ;
            angle = read.f() ;
            length = read.f() ;
            x = read.f() ;
            y = read.f() ;
        }
    }
}
