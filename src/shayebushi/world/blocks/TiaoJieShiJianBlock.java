package shayebushi.world.blocks;

import arc.Core;
import arc.math.Mathf;
import arc.scene.ui.Slider;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.SettingsMenuDialog;
import mindustry.world.Block;

import static arc.Core.settings;
import static mindustry.Vars.net;

public class TiaoJieShiJianBlock extends Block {
    public static final float def ;
    static
    {
        float result = Core.graphics.getDeltaTime() * 60f;
        def = (Float.isNaN(result) || Float.isInfinite(result)) ? 1f : Mathf.clamp(result, 0.0001f, 60f / 10f);
    }
    public TiaoJieShiJianBlock(String name) {
        super(name);
        update = true ;
        configurable = true ;
        saveConfig = true ;
//        config(Float.class, (TiaoJieShiJianBuild b, Float value) -> {
//
//        });
    }
    public class TiaoJieShiJianBuild extends Building {
        @Override
        public void buildConfiguration(Table table){
            super.buildConfiguration(table);
            if(net.client()){
                deselect();
                return;
            }
            Seq<Float> f = new Seq<>() ;
            f.add(1f) ;
            var s = table.slider(-9, 50, 1f, value ->{
                Time.setDeltaProvider(() -> {
                    f.set(0, (value < 0 ? 1 + value / 10 : value > 0 ? value : 1));
                    return f.get(0) ;
                });
                //System.out.println(value);
            });
            var b = table.button(f.get(0) + "x", () -> {}).width(90) ;
            s.get().moved(f2 -> {
                b.get().setText((f2 < 0 ? 1 + f2 / 10 : f2 > 0 ? f2 : 1)+"x") ;
            });
            //t.add(Time.delta + "x").right().row();
//            table.add(Time.delta + "x").right();
//            table = (SettingsMenuDialog.SettingsTable)table ;
//            ((SettingsMenuDialog.SettingsTable) table).sliderPref("botton.shijianliusu", ) ;
        }
        @Override
        public void remove(){
            super.remove();
            Time.setDeltaProvider(() -> {
                float result = Core.graphics.getDeltaTime() * 60f;
                return (Float.isNaN(result) || Float.isInfinite(result)) ? 1f : Mathf.clamp(result, 0.0001f, 60f / 10f) ;
            });
        }
    }
}
