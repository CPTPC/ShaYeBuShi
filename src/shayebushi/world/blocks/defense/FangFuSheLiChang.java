package shayebushi.world.blocks.defense;

import arc.Core;
import arc.Events;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.scene.Element;
import arc.scene.ui.Button;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.Units;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.logic.Ranged;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;
import shayebushi.SYBSItems;
import shayebushi.SYBSStatusEffects;
import shayebushi.ShaYeBuShi;
import shayebushi.world.consumers.ConsumeFuShe;

import static mindustry.Vars.*;
import static shayebushi.SYBSStatusEffects.dangji;
import static shayebushi.SYBSStatusEffects.fangfushe;

public class FangFuSheLiChang extends Block {
    public int sides = 4;
    public int radius = 220;
    public float shieldRotation = 45f;
    public Item itemConsumer = SYBSItems.fangfushe ;
    public float itemUseTime = 80 ;
    public boolean use = false ;
    public boolean alwaysWork = false ;
    public boolean wuxianRange = false ;
    public static CiChangQiangDuChangeEvent cichangqiangduChangeEvent = new CiChangQiangDuChangeEvent() ;

    public FangFuSheLiChang(String name) {
        super(name);
        hasPower = true;
        hasItems = true;
        update = true ;
        solid = true;
        group = BlockGroup.projectors;
        configurable = true ;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        Draw.color(Pal.gray);
        Lines.stroke(3f);
        Lines.poly(x * tilesize + offset, y * tilesize + offset, sides, radius, shieldRotation);
        Draw.color(Pal.place);
        Lines.stroke(1f);
        Lines.poly(x * tilesize + offset, y * tilesize + offset, sides, radius, shieldRotation);

    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("cichangqiangdu", (FangFuSheliChangBuild b) -> {
            return new Bar(Core.bundle.format("cichang.qiangdu", b.cichangqiangdu), Pal.place, () -> b.cichangqiangdu / 50f) ;
        });
    }

    @Override
    public boolean outputsItems(){
        return false;
    }

    @Override
    public void setStats(){
        boolean consItems = itemConsumer != null && false;

        if(consItems) stats.timePeriod = itemUseTime;
        super.setStats();

    }

    @Override
    public void init(){
        updateClipRadius(radius + 3f);
        super.init();
    }

    public class FangFuSheliChangBuild extends Building implements Ranged {
        public float timer = 0 ;
        public float waveRadius = 0 ;
        public float waveTimer = 0 ;
        public Seq<FangFuSheliChangBuild> others = new Seq<>() ;
        public int cichangqiangdu = 1 ;
        public BaseDialog dialog = new BaseDialog(Core.bundle.get("cichang.set")) ;

        @Override
        public float range() {
            return radius;
        }

        public int radius() {
            if (wuxianRange) {
                return Math.max(world.width() * 2, world.height() * 2) * tilesize;
            }
            return radius ;
        }

        public int radiusTile() {
            return radius() / tilesize ;
        }

        @Override
        public void updateTile() {
            super.updateTile();
            if (efficiency > 0 || alwaysWork) {
                if (timer >= itemUseTime && use) {
                    consume();
                    timer = 0;
                }
                timer += Time.delta;
            }
            Units.nearby(null, x, y, radius(), unit -> {
                if (efficiency > 0) {
                    if (cichangqiangdu >= ShaYeBuShi.maxCiChangQiangDu) {
                        unit.apply(dangji, 60);
                    }
                    if (cichangqiangdu >= ShaYeBuShi.fangFuSheCiChangQiangDu) {
                        unit.apply(SYBSStatusEffects.fangfushe);
                    }
                }
            });
            Units.nearbyBuildings(x, y, radius(), b -> {
                ConsumeFuShe.Data d = ConsumeFuShe.status.get(b, (ConsumeFuShe.Data) null) ;
                if (d == null) {
                    d = new ConsumeFuShe.Data() ;
                }
                if (efficiency > 0 && cichangqiangdu >= ShaYeBuShi.fangFuSheCiChangQiangDu) {
                    d.status = 1 ;
                    d.last = this ;
                }
                else {
                    if (d.last == this) {
                        d.status = 0 ;
                    }
                }
                ConsumeFuShe.status.put(b, d) ;
            });
        }

        @Override
        public void drawSelect(){
            super.drawSelect();
            if (efficiency > 0 || alwaysWork) {
                Draw.color(Pal.place);

                if (renderer.animateShields) {
                    Draw.z(Layer.effect);
                    Draw.alpha(0.5f);
                    Fill.poly(x, y, sides, radius(), shieldRotation);

                    Draw.alpha(1f);
                    Lines.poly(x, y, sides, radius(), shieldRotation);
                } else {
                    Draw.z(Layer.effect);
                    Lines.stroke(1.5f);
                    Draw.alpha(0.09f);
                    Fill.poly(x, y, sides, radius(), shieldRotation);
                    Draw.alpha(1f);
                    Lines.poly(x, y, sides, radius(), shieldRotation);
                    Draw.reset();
                }
                /*
                Building build = Units.closestBuilding(team, x, y, radius, b -> b instanceof FangFuSheliChangBuild && b != this && !((FangFuSheliChangBuild) b).others.contains(this));
                //System.out.println((build != null) + " " + (build != this) + " " +(build instanceof FangFuSheliChangBuild));
                if (build != null && build != this && build instanceof FangFuSheliChangBuild) {
//                    System.out.println("("+x+","+y+")"+"("+build.x+","+build.y+")");
                    others.add((FangFuSheliChangBuild) build) ;
                    build.drawSelect();
                }
                 */
                Units.nearbyBuildings(x, y, radius(), b -> {
                    if (b instanceof FangFuSheliChangBuild && b != this) {
                       ((FangFuSheliChangBuild)b).drawS();
                    }
                });
            }
        }

        public void drawS(){
            super.drawSelect();
            if (efficiency > 0) {
                Draw.color(Pal.place);

                if (renderer.animateShields) {
                    Draw.z(Layer.effect);
                    Draw.alpha(0.65f);
                    Fill.poly(x, y, sides, radius(), shieldRotation);

                    Draw.alpha(1f);
                    Lines.poly(x, y, sides, radius(), shieldRotation);
                } else {
                    Draw.z(Layer.effect);
                    Lines.stroke(1.5f);
                    Draw.alpha(0.09f);
                    Fill.poly(x, y, sides, radius(), shieldRotation);
                    Draw.alpha(1f);
                    Lines.poly(x, y, sides, radius(), shieldRotation);
                    Draw.reset();
                }
                /*
                Building build = Units.closestBuilding(team, x, y, radius, b -> b instanceof FangFuSheliChangBuild && b != this && !((FangFuSheliChangBuild) b).others.contains(this));
                //System.out.println((build != null) + " " + (build != this) + " " +(build instanceof FangFuSheliChangBuild));
                if (build != null && build != this && build instanceof FangFuSheliChangBuild) {
//                    System.out.println("("+x+","+y+")"+"("+build.x+","+build.y+")");
                    others.add((FangFuSheliChangBuild) build) ;
                    build.drawSelect();
                }
                 */
            }
        }

        @Override
        public void draw() {
            super.draw();
            if (efficiency > 0 && !state.isPaused()) {
                if (waveRadius < radius() * 0.95f) {
                    Draw.color(Pal.place);
                    Lines.stroke(3);
                    Lines.circle(x, y, waveRadius);
                    waveRadius += 2;
                } else {
                    if (waveTimer >= 180) {
                        waveRadius = 0;
                    }
                    waveTimer += Time.delta;
                }
            }
        }

        @Override
        public void buildConfiguration(Table t) {
            super.buildConfiguration(t);
            t.button(Icon.upOpen, Styles.cleari, () -> {
                dialog.show() ;
                deselect();
            }).size(40f);
        }

        @Override
        public Building create(Block b, Team t) {
            dialog.cont.add(Core.bundle.get("cichang.set")).padRight(10f);
            var s =  dialog.cont.slider(1, ShaYeBuShi.maxCiChangQiangDu, 1, value ->{
                Events.fire(new CiChangQiangDuChangeEvent(this, cichangqiangdu, (int) value));
                cichangqiangdu = (int)value ;
            });
            var b2 = dialog.cont.button(cichangqiangdu + "", () -> {}).width(75) ;
            s.get().moved(f -> b2.get().setText(cichangqiangdu + ""));
            dialog.cont.row() ;
            dialog.cont.button("@back", () -> {
                dialog.hide();
            }) ;
            return super.create(b, t) ;
        }
        @Override
        public byte version() {
            return 1 ;
        }
        @Override
        public void write(Writes write) {
            super.write(write);
            write.i(cichangqiangdu);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            cichangqiangdu = read.i() ;
        }
    }
    public static class CiChangQiangDuChangeEvent {
        public FangFuSheliChangBuild build ;
        public int from, to ;
        public CiChangQiangDuChangeEvent(FangFuSheliChangBuild b, int f, int t) {
            set(b, f, t) ;
        }
        public CiChangQiangDuChangeEvent() {

        }
        public CiChangQiangDuChangeEvent set(FangFuSheliChangBuild b, int f, int t) {
            build = b ;
            from = f ;
            to = t ;
            return this ;
        }
    }
}
