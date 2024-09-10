package shayebushi.world.blocks.power;

import arc.math.geom.Point2;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.world.blocks.power.PowerGraph;
import shayebushi.SYBSBlocks;
import shayebushi.world.consumers.ConsumeDianYa;

public class DianYaGraph extends PowerGraph {
    public float dianya ;
    public DianYaGraph(int dianya) {
        this.dianya = dianya ;
    }
    /*
    @Override
    public void add(Building build){
        Building b1 = build.getPowerConnections(new Seq<>()).find(bu -> bu.block == SYBSBlocks.bianyaqi) ;
        boolean b = b1 != null && b1.getPowerConnections(new Seq<>()).contains(bu -> bu.block instanceof DianYaNode d && d.dianya == dianya);
        //assert b1 != null;
        //System.out.println("d:" + (b1.getPowerConnections(new Seq<>()).contains(bu -> bu.block instanceof DianYaNode d && d.dianya == dianya)));
        //System.out.println(dianya + " " +(build.block instanceof DianYaBlock ? build.getDisplayName() : "") + " " + (build.block instanceof DianYaBlock d && d.dianya == dianya) + " " + (build.block == SYBSBlocks.bianyaqi) + " " + (build.block.consPower instanceof ConsumeDianYa c && c.dianya == dianya));
        if ((build.block instanceof DianYaBlock d && d.dianya == dianya) || build.block == SYBSBlocks.bianyaqi || (build.block.consPower instanceof ConsumeDianYa c && c.dianya == dianya) || b) {
            super.add(build);
        }
    }
    */
}
