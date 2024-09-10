package shayebushi.world.blocks;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.entities.Units;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Tile;
import shayebushi.SYBSStats;
import shayebushi.world.blocks.defense.turrets.TurretXianZhi;

import static mindustry.Vars.player;

public interface XianZhic {
    int limitPlaceOnCount() ;
    int countt() ;
    void countt(int c) ;
    default void setBarsXianZhi() {
        if (this instanceof Block b) {
            b.addBar("fangzhishangxian", entity -> new Bar("bar.fangzhishangxian", Color.valueOf("00ff00"),() -> countt() / (float)limitPlaceOnCount()).blink(Color.white));
        }
    }
    default boolean canPlaceOnXianZhi(Tile t, Team te, int r) {
        Seq<Building> b = new Seq<>() ;
        Units.nearbyBuildings(t.x,t.y,Long.MAX_VALUE, bb -> {
            if (this instanceof Block bl && bb.block == bl && bb.team == player.team()){
                b.add(bb) ;
            }
        });
        countt(b.size) ;
        return countt() < limitPlaceOnCount() ;
    }
    default void setStatsXianZhi() {
        if (this instanceof Block b) {
            b.stats.add(SYBSStats.fangzhishangxian, limitPlaceOnCount()) ;
        }
    }
}
