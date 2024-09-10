package shayebushi.world.consumers;

import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Build;
import mindustry.world.blocks.power.PowerBlock;
import mindustry.world.consumers.ConsumePower;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.Stats;
import mindustry.world.modules.PowerModule;
import shayebushi.SYBSBlocks;
import shayebushi.SYBSStats;
import shayebushi.ShaYeBuShi;
import shayebushi.world.blocks.defense.FangFuSheLiChang;
import shayebushi.world.blocks.power.DianYaBlock;
import shayebushi.world.blocks.power.DianYaNode;

public class ConsumeDianYa extends ConsumePower {
    public int dianya ;
    public Seq<StatUnit> s = new Seq<>() ;
    public Seq<Stat> ss = new Seq<>() ;
    {
        s.add(StatUnit.powerSecond, SYBSStats.diyadian, SYBSStats.zhongyadian, SYBSStats.gaoyadian) ;
        ss.add(Stat.powerCapacity, SYBSStats.diyadianchi, SYBSStats.zhongyadianchi, SYBSStats.gaoyadianchi) ;
    }
    public ConsumeDianYa(float usage, float capacity, boolean buffered, int dianya){
        this.usage = usage;
        this.capacity = capacity;
        this.buffered = buffered;
        this.dianya = dianya;
    }

    protected ConsumeDianYa(){
        this(0f, 0f, false, 0);
    }

    @Override
    public void apply(Block block){
        block.hasPower = true;
        block.consPower = this;
    }
    @Override
    public void trigger(Building b){
        super.trigger(b);
//        b.power(new DianYaModule(){{
//            dianya = ConsumeDianYa.this.dianya ;
//        }});
    }
    @Override
    public boolean ignore(){
        return buffered;
    }
    /*
    @Override
    public float efficiency(Building build){
        if ((build.power instanceof DianYaModule d && d.dianya == dianya)){
            Seq<Building> b = build.power.graph.producers.select(bu -> !((bu.power instanceof DianYaModule dd && dd.dianya == d.dianya) || (bu.block == SYBSBlocks.bianyaqi))) ;
            int i = build.power.graph.producers.count(bu -> !((bu.power instanceof DianYaModule dd && dd.dianya == d.dianya) || (bu.block == SYBSBlocks.bianyaqi))) ;
            //System.out.println(b.size + " " + build.power.graph.producers.size);
            boolean bb = b.size == 0 || build.power.graph.producers.size > i;
            //System.out.println(bb + "6");
            if (!bb) {
                return 0f ;
            }
            //System.out.println(Mathf.zero(requestedPower(build)) && build.power.graph.getPowerProduced() + build.power.graph.getBatteryStored() > 0f ? 1f : build.power.status);
            //System.out.println(Mathf.zero(requestedPower(build)) + " " + (build.power.graph.getPowerProduced() + build.power.graph.getBatteryStored() > 0f));
            return Mathf.zero(requestedPower(build)) && build.power.graph.getPowerProduced() + build.power.graph.getBatteryStored() > 0f ? 1f : build.power.status;
        }
//        if ((!(build.power instanceof DianYaModule) || (build.power instanceof DianYaModule d && d.dianya != dianya)) && build.power.status > 0){
//            build.damage(build.maxHealth * 0.05f);
//        }
        return 0f ;
    }
    */
    @Override
    public float efficiency(Building build){
        //System.out.println(build.power.status);
        return super.efficiency(build) ;
        /*
        var power = new Object() {
          public float power = 0 ;
        };
        build.power.graph.producers.each(b -> !(b instanceof DianYaBlock.DianYaBuild), b -> power.power += b.getPowerProduction() * b.delta()) ;
        var dianya = new Object() {
            public float power = 0 ;
        };
        build.power.graph.producers.each(b -> b.block instanceof DianYaBlock d && d.dianya == this.dianya, b -> dianya.power += b.getPowerProduction() * b.delta()) ;
        var chucun = new Object() {
            public float power = 0 ;
        };
        build.power.graph.producers.each(b -> !(b instanceof DianYaBlock.DianYaBuild) && b.block.consPower != null && b.block.consPower.buffered, b -> chucun.power += b.power.status * b.block.consPower.capacity) ;
        return build.power.graph.getBatteryStored() - chucun.power > build.power.graph.getPowerNeeded() ? 1 : build.power.status - power.power / build.power.graph.getPowerNeeded() ;
        */
    }

    @Override
    public void display(Stats stats){
        if(buffered){
            stats.add(ss.get(dianya), capacity, StatUnit.none);
        }else{

            stats.add(Stat.powerUse, usage * 60f, s.get(dianya));
        }
    }

    /**
     * Retrieves the amount of power which is requested for the given block and entity.
     * @param entity The entity which contains the power module.
     * @return The amount of power which is requested per tick.
     */
    /*
    public float requestedPower(Building entity) {
        if (entity.power instanceof DianYaModule d && d.dianya == dianya) {
            Seq<Building> b = entity.power.graph.producers.select(bu -> !((bu.power instanceof DianYaModule dd && dd.dianya == d.dianya) || (bu.block == SYBSBlocks.bianyaqi))) ;
            int i = entity.power.graph.producers.count(bu -> !((bu.power instanceof DianYaModule dd && dd.dianya == d.dianya) || (bu.block == SYBSBlocks.bianyaqi))) ;
            boolean bb = b.size == 0 || entity.power.graph.producers.size > i;
            if (!bb) {
                return 0f ;
            }
            float beilv = entity instanceof FangFuSheLiChang.FangFuSheliChangBuild f ? (50 + 1 - f.cichangqiangdu) / 50f : 1 ;
            return buffered ?
                    (1f - entity.power.status) * capacity :
                            usage * (entity.shouldConsume() ? 1f : 0f) / beilv;
        }
        return 0f ;
    }
    */
    public float requestedPower(Building entity) {
        float beilv = entity instanceof FangFuSheLiChang.FangFuSheliChangBuild f ? (ShaYeBuShi.maxCiChangQiangDu + 1 - f.cichangqiangdu) / (float)(ShaYeBuShi.maxCiChangQiangDu) : 1 ;
        return super.requestedPower(entity) / beilv ;
    }
}
