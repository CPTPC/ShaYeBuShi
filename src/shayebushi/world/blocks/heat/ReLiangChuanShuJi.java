package shayebushi.world.blocks.heat;

import arc.math.Mathf;
import arc.struct.IntSet;
import mindustry.gen.Building;
import mindustry.world.blocks.heat.HeatBlock;
import mindustry.world.blocks.heat.HeatConductor;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;
import shayebushi.SYBSStatValues;
import shayebushi.SYBSStats;

import java.util.Arrays;

public class ReLiangChuanShuJi extends HeatConductor {
    public float sunhao = 3 ;
    public ReLiangChuanShuJi(String name) {
        super(name);
    }
    public void setStats() {
        super.setStats();
        if (sunhao != 0) {
            stats.add(SYBSStats.sunhao, sunhao + "%");
        }
    }
    public class ReLiangChuanShuJiBuild extends HeatConductorBuild {
        @Override
        public float calculateHeat(float[] sideHeat, IntSet cameFrom) {

            Arrays.fill(sideHeat, 0.0F);
            if (cameFrom != null) cameFrom.clear();
            float heat = 0.0F;
            for (var edge : block.getEdges()) {
                Building build = nearby(edge.x, edge.y);
                if (build != null && build.team == team && build instanceof HeatBlock heater) {
                    if (heater instanceof HeatConductorBuild cond) {
                        cond.updateHeat();
                    }
                    boolean split = build.block instanceof HeatConductor cond && cond.splitHeat;
                    if (!build.block.rotate || (!split && (relativeTo(build) + 2) % 4 == build.rotation) || (split && relativeTo(build) != build.rotation)) {
                        if (!(build instanceof HeatConductorBuild hc && hc.cameFrom.contains(id()))) {
                            float add = heater.heat() / build.block.size;
                            if (split) {
                                add /= 3.0F;
                            }
                            add *= (1 - sunhao / 100) ;
                            sideHeat[Mathf.mod(relativeTo(build), 4)] += add;
                            heat += add;
                        }
                        if (cameFrom != null) {
                            cameFrom.add(build.id);
                            if (build instanceof HeatConductorBuild hc) {
                                cameFrom.addAll(hc.cameFrom);
                            }
                        }
                    }
                }
            }
            return heat;
        }
    }
}
