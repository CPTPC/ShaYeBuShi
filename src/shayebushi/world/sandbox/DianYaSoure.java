package shayebushi.world.sandbox;

import arc.struct.Seq;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.meta.Env;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import shayebushi.SYBSStats;
import shayebushi.world.blocks.power.DianYaNode;

public class DianYaSoure extends DianYaNode {
    public float powerProduction = 10000f;

    public DianYaSoure(String name){
        super(name);
        maxNodes = 100;
        outputsPower = true;
        consumesPower = false;
        //TODO maybe don't?
        envEnabled = Env.any;
    }
    @Override
    public void setStats() {
        super.setStats();
        Seq<Stat> s = new Seq<>() ;
        s.add(Stat.basePowerGeneration, SYBSStats.diyadianshuchu, SYBSStats.zhongyadianshuchu, SYBSStats.gaoyadianshuchu) ;
        stats.add(s.get(dianya), powerProduction * 60.0f, StatUnit.powerSecond);
    }
    public class DiYaDianYuanBuild extends DianYaNodeBuild {
        @Override
        public float getPowerProduction(){
            return enabled ? powerProduction : 0f;
        }
    }

}
