package shayebushi.world.blocks;

import arc.Core;
import arc.math.Interp;
import arc.scene.actions.Actions;
import arc.scene.ui.layout.Table;
import arc.scene.ui.layout.WidgetGroup;
import arc.util.Align;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.input.InputHandler;
import mindustry.ui.Bar;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;

import java.lang.reflect.Field;

import static mindustry.Vars.player;
import static mindustry.Vars.state;

public class ShangHaiXianShiBlock extends Block {
    public ShangHaiXianShiBlock(String name) {
        super(name);
        update = true ;
        //configurable = true ;
    }
    @Override
    public void setBars() {
        super.setBars();
        addBar("zongs", (ShangHaiXianShiBuild b) ->
            new Bar(
                () -> Core.bundle.format("shanghai.zong", b.damageAll[0] + "\n"),
                () -> Pal.accent,
                () -> 1
            ));
        addBar("seconds", (ShangHaiXianShiBuild b) ->
                new Bar(
                        () -> Core.bundle.format("shanghai.second", b.damageAll[1] + "\n"),
                        () -> Pal.accent,
                        () -> 1
                ));
        addBar("minutes", (ShangHaiXianShiBuild b) ->
                new Bar(
                        () -> Core.bundle.format("shanghai.minute", b.damageAll[2] + "\n"),
                        () -> Pal.accent,
                        () -> 1
                ));
        addBar("zongh", (ShangHaiXianShiBuild b) ->
                new Bar(
                        () -> Core.bundle.format("shanghai.cishu", b.hitAll[0] + "\n"),
                        () -> Pal.accent,
                        () -> 1
                ));
        addBar("secondh", (ShangHaiXianShiBuild b) ->
                new Bar(
                        () -> Core.bundle.format("shanghai.cishus", b.hitAll[1] + "\n"),
                        () -> Pal.accent,
                        () -> 1
                ));
        addBar("minuteh", (ShangHaiXianShiBuild b) ->
                new Bar(
                        () -> Core.bundle.format("shanghai.cishum", b.hitAll[2] + "\n"),
                        () -> Pal.accent,
                        () -> 1
                ));
    }
    public class ShangHaiXianShiBuild extends Building {
        public float damage = 0 ;
        public float damages = 0 ;
        public float damagem = 0 ;
        public float timers = 0 ;
        public float timerm = 0 ;
        public int hit = 0 ;
        public int hits = 0 ;
        public int hitm = 0 ;
        public float[] damageAll = new float[]{damage, 0, 0} ;
        public float[] hitAll = new float[]{hit, 0, 0} ;
        @Override
        public float handleDamage(float d) {
            d *= state.rules.blockHealth(team) ;
            damage += d ;
            damages += d ;
            damagem += d ;
            hit ++ ;
            hits ++ ;
            hitm ++ ;
            return 0 ;
        }
        @Override
        public void updateTile() {
            super.updateTile() ;
            timers += Time.delta ;
            timerm += Time.delta ;
            damageAll[0] = damage ;
            hitAll[0] = hit ;
            if (timers >= Time.toSeconds) {
                if (damages != 0) {
                    damageAll[1] = damages ;
                }
                if (hits != 0) {
                    hitAll[1] = hits ;
                }
                timers = 0 ;
                damages = 0 ;
                hits = 0 ;
            }
            if (timerm >= Time.toMinutes) {
                if (damagem != 0) {
                    damageAll[2] = damagem ;
                }
                if (hitm != 0) {
                    hitAll[2] = hitm ;
                }
                timerm = 0 ;
                damagem = 0 ;
                hitm = 0 ;
            }
        }
//        @Override
//        public void buildConfiguration(Table table) {
//            super.buildConfiguration(table);
//
//        }
    }
}
