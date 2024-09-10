package shayebushi.world.consumers;

import arc.struct.ObjectMap;
import mindustry.gen.Building;
import mindustry.world.consumers.Consume;
import shayebushi.world.blocks.defense.FangFuSheLiChang;

public class ConsumeFuShe extends Consume {
    public static ObjectMap<Building, Data> status = new ObjectMap<>() ;
    public static class Data {
        public int status = 0 ;
        public FangFuSheLiChang.FangFuSheliChangBuild last ;
    }
}
