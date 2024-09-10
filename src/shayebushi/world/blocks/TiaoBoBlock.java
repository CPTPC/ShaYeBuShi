package shayebushi.world.blocks;

import arc.Events;
import arc.scene.ui.layout.Table;
import arc.util.Time;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.world.Block;
import static mindustry.Vars.* ;

public class TiaoBoBlock extends Block {
    public TiaoBoBlock(String name) {
        super(name);
        update = true ;
        configurable = true ;
        saveConfig = true ;
    }
    public class TiaoBoBuild extends Building {
        public float boshu = 1 ;
        @Override
        public void buildConfiguration(Table table){
            super.buildConfiguration(table);
            if(net.client()){
                deselect();
                return;
            }
            table.slider(1, 32, 1, value ->{
                boshu = value ;
            });
            table.button(Icon.upOpen, Styles.cleari, () -> {
                for (int i = 0 ; i < boshu ; i ++) {
                    spawner.spawnEnemies();
                    state.wave++;
                    state.wavetime = state.rules.waveSpacing;

                    Events.fire(new EventType.WaveEvent());
                }
                deselect();
            }).size(40f);
        }
    }
}
