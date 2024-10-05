package shayebushi;

import arc.graphics.Color;
import mindustry.content.StatusEffects;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;

public class SYBSLiquids {
    public static Liquid yanjiang , nongliusuan , huaxueranji , huoxingdenglizi;
    public static void load(){
        yanjiang = new Liquid("yanjiang",Color.valueOf("ffba66")){{
            //heatCapacity = 0 ;
            temperature = 1.5f;
            viscosity = 0.7f;
            effect = StatusEffects.burning;
            lightColor = Color.valueOf("f0511d").a(0.4f);
        }};
        nongliusuan = new SYBSLiquid("nongliusuan",Color.valueOf("00aa00")){{
            //heatCapacity = 0 ;
            temperature = 0.55f;
            viscosity = 0.7f;
            effect = SYBSStatusEffects.zhongdufushi;
            lightColor = Color.valueOf("00aa00").a(0.4f);
            fushi = 2 ;
        }};
        huaxueranji = new Liquid("huaxueranji", Color.valueOf("e8e9de")){{
            //heatCapacity = 0 ;
            temperature = 0.55f;
            flammability = 5f;
            explosiveness = 1.4f;
            viscosity = 0.7f;
            effect = SYBSStatusEffects.huaxuedianran;
            lightColor = Pal.lightOrange.a(0.4f);
        }};
        huoxingdenglizi = new Liquid("huoxingdenglizi", Color.valueOf("4acad8")){{
            heatCapacity = 8.69f ;
            temperature = 0.45f;
            lightColor = Pal.techBlue.a(0.4f);
        }};
    }
    public static class SYBSLiquid extends Liquid {
        public float fushi = 0 ;

        public SYBSLiquid(String name, Color color) {
            super(name, color);
        }

        public SYBSLiquid(String name) {
            super(name);
        }

        @Override
        public void setStats() {
            super.setStats();
            stats.addPercent(SYBSStats.fushi, fushi);
        }
    }
}
