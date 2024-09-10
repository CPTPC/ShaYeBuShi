package shayebushi.world.blocks.power;

import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.blocks.power.PowerGenerator;
import shayebushi.world.consumers.ConsumeCiChang;

public class CiChangFaDianJi extends DianYaGenerator {
    public CiChangFaDianJi(String name) {
        super(name);
        consume(new ConsumeCiChang(20)) ;
    }
    public class CiChangFaDianJiBuild extends DianYaGeneratorBuild {
        @Override
        public float getPowerProduction() {
            ConsumeCiChang ccc = (ConsumeCiChang) consumeBuilder.find(c -> c instanceof ConsumeCiChang) ;
            if (ccc == null) {
                return 0 ;
            }
            productionEfficiency = ccc.efficiency(this) ;
            float f = ccc.cichangqiangdu(this) > ccc.cichangqiangdu ? (ccc.cichangqiangdu(this) - ccc.cichangqiangdu) / 3f : 0 ;
            return super.getPowerProduction() + f ;
        }
    }
}
