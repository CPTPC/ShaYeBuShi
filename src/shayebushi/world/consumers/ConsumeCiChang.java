package shayebushi.world.consumers;

import arc.Core;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.entities.Units;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.consumers.Consume;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.Stats;
import shayebushi.SYBSBlocks;
import shayebushi.SYBSStats;
import shayebushi.ShaYeBuShi;
import shayebushi.world.blocks.defense.FangFuSheLiChang;

public class ConsumeCiChang extends Consume {
    public int cichangqiangdu = 1 ;
    public ConsumeCiChang(int cichangqiangdu){
        this.cichangqiangdu = cichangqiangdu ;
    }

    protected ConsumeCiChang(){
        this(1);
    }

    @Override
    public void apply(Block block){
        super.apply(block);
        /*
        block.addBar("cichangqiangdu", b -> {
            Building fa = null ;
            Building faa = null ;
            Building faaa = null ;
            for (Team te : Team.baseTeams) {
                fa = Units.closestBuilding(te, b.x, b.y, Integer.MAX_VALUE, bu -> bu instanceof FangFuSheLiChang.FangFuSheliChangBuild fan && bu.efficiency > 0 && bu.block instanceof FangFuSheLiChang ff && bu.dst(bu) < ff.radius && fan.cichangqiangdu >= cichangqiangdu);
                faa = Units.closestBuilding(te, b.x, b.y, Integer.MAX_VALUE, bu -> bu instanceof FangFuSheLiChang.FangFuSheliChangBuild fan && bu.efficiency > 0 && bu.block instanceof FangFuSheLiChang ff && bu.dst(bu) < ff.radius && fan.cichangqiangdu >= 50);
            }
            if (fa == null && faa == null) {
                for (Team te : Team.baseTeams) {
                    faaa = Units.closestBuilding(te, b.x, b.y, Integer.MAX_VALUE, bu -> bu instanceof FangFuSheLiChang.FangFuSheliChangBuild fan && bu.efficiency > 0 && bu.block instanceof FangFuSheLiChang ff && bu.dst(bu) < ff.radius);
                }
            }
            else if (faa != null) {
                faaa = fa ;
            }
            else if (fa != null) {
                faaa = fa ;
            }
            Building finalFaaa = faaa;
            return new Bar(Core.bundle.format("cichang.qiangdu", faaa != null ? ((FangFuSheLiChang.FangFuSheliChangBuild)faaa).cichangqiangdu : 0), Pal.place, () -> (finalFaaa != null ? ((FangFuSheLiChang.FangFuSheliChangBuild) finalFaaa).cichangqiangdu : 0) / cichangqiangdu) ;
        });
        */
    }
    @Override
    public void trigger(Building b){
        super.trigger(b);
    }

    @Override
    public float efficiency(Building build){
        Building fa = null ;
        Building faa = null ;
        for (Team te : Team.baseTeams) {
            fa = Units.closestBuilding(te, build.x, build.y, Integer.MAX_VALUE, bu -> bu instanceof FangFuSheLiChang.FangFuSheliChangBuild fan && bu.efficiency > 0 && bu.block instanceof FangFuSheLiChang ff && bu.dst(bu) < fan.radius() && fan.cichangqiangdu >= cichangqiangdu);
            faa = Units.closestBuilding(te, build.x, build.y, Integer.MAX_VALUE, bu -> bu instanceof FangFuSheLiChang.FangFuSheliChangBuild fan && bu.efficiency > 0 && bu.block instanceof FangFuSheLiChang ff && bu.dst(bu) < fan.radius() && fan.cichangqiangdu >= ShaYeBuShi.maxCiChangQiangDu);
            if (faa != null) {
                return 0 ;
            }
            if (fa != null) {
                return 1 ;
            }
        }
        return 0f ;
    }

    @Override
    public void display(Stats stats){
        stats.add(SYBSStats.cichangqiangdu, cichangqiangdu) ;
    }

    public int cichangqiangdu(Building build) {
        Building fa = null ;
        Building faa = null ;
        for (Team te : Team.baseTeams) {
            fa = Units.closestBuilding(te, build.x, build.y, Integer.MAX_VALUE, bu -> bu instanceof FangFuSheLiChang.FangFuSheliChangBuild fan && bu.efficiency > 0 && bu.block instanceof FangFuSheLiChang ff && bu.dst(bu) < fan.radius());
            faa = Units.closestBuilding(te, build.x, build.y, Integer.MAX_VALUE, bu -> bu instanceof FangFuSheLiChang.FangFuSheliChangBuild fan && bu.efficiency > 0 && bu.block instanceof FangFuSheLiChang ff && bu.dst(bu) < fan.radius() && fan.cichangqiangdu >= ShaYeBuShi.maxCiChangQiangDu);
            if (faa != null) {
                return 0 ;
            }
            if (fa != null) {
                return ((FangFuSheLiChang.FangFuSheliChangBuild)fa).cichangqiangdu ;
            }
        }
        return 0 ;
    }
}
