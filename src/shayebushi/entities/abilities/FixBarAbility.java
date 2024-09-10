package shayebushi.entities.abilities;

import arc.Core;
import arc.graphics.Color;
import arc.scene.ui.layout.Table;
import mindustry.core.Version;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import shayebushi.ShaYeBuShi;

public class FixBarAbility extends Ability {
    public FixBarAbility(){
        display = false ;
    }
    @Override
    public void displayBars(Unit unit, Table bars){
        //bars.removeAction(new Bar("stat.health", Pal.health, unit::healthf).blink(Color.white));
        //bars.removeChild(new Bar("stat.health", Pal.health, unit::healthf).blink(Color.white)) ;
        bars.add(new Bar(()-> Core.bundle.format("bar.health", Math.max(unit.health, 0), Math.max((int)((unit.healthf()) * 100), 0) + "%"), ()->Pal.health, unit::healthf).blink(Color.white).blink(Color.white));
        bars.row();
        //bars.add(new Bar(()-> Core.bundle.format("bar.speed", unit.speedMultiplier, (int)((unit.speedMultiplier) * 100) + "%"), () -> Pal.place, () -> unit.speedMultiplier).blink(Color.white).blink(Color.white));
        //bars.row();
//        if (Version.build == -1 || ShaYeBuShi.tiaoshi) {
//            for (int i = 0 ; i < ShaYeBuShi.shuxings ; i ++) {
//                bars.add(Core.bundle.format(ShaYeBuShi.shuxing[i]), ShaYeBuShi.beilvs.get(i).get(unit, 0f));
//                bars.row();
//            }
//        }
    }
}
