package shayebushi.entities.abilities;

import arc.Core;
import arc.func.Cons;
import arc.math.Mathf;
import mindustry.Vars;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.world.blocks.environment.Cliff;
import mindustry.world.meta.Attribute;
import shayebushi.ShaYeBuShi;

import static mindustry.Vars.state;
import static mindustry.Vars.tilesize;

public class GuangYaoAbility extends Ability {
    public float shanghaibeilvZhou = 0.25f, shanghaibeilvYe = 2f, xieliangbeilvZhou = 2.5f, xieliangbeilvYe = 0.83f, shesubeilvZhou = 1.25f / shanghaibeilvZhou / xieliangbeilvZhou , shesubeilvYe = 1f ;
    public Cons<Unit> cons = u -> {
        float amount = 0 ;
        for (int x = (int)(u.x / tilesize) - 10 ; x < u.x / tilesize + 10 ; x ++) {
            for (int y = (int)(u.y / tilesize) - 10 ; y < u.y / tilesize + 10 ; y ++) {
                if (Vars.world.tile(x, y) != null) {
                    if (Vars.world.build(x, y) == null && !(Vars.world.tile(x, y).block() instanceof Cliff)) {
                        //ShaYeBuShi.fixedPrintln(" ", x, y);
                        amount++;
                    }
                }
            }
        }
        u.damageMultiplier = (1 + amount / 250) ;
    };
    @Override
    public void update(Unit u) {
        float time = Mathf.maxZero(Attribute.light.env() +
                (state.rules.lighting ?
                        1f - state.rules.ambientLight.a :
                        1f
                )) ;
        //System.out.println(time);
        u.damageMultiplier = (ShaYeBuShi.withIn(time, 1, 0.25f)) ? shanghaibeilvZhou : shanghaibeilvYe ;
        u.healthMultiplier = (ShaYeBuShi.withIn(time, 1, 0.25f)) ? xieliangbeilvZhou : xieliangbeilvYe ;
        u.reloadMultiplier = (ShaYeBuShi.withIn(time, 1, 0.25f)) ? shesubeilvZhou : shesubeilvYe ;
        cons.get(u) ;
    }
    @Override
    public String localized(){
        return Core.bundle.format("ability.guangyao", xieliangbeilvZhou * 100, shanghaibeilvZhou * 100, shesubeilvZhou * 100, xieliangbeilvYe * 100, shanghaibeilvYe * 100, shesubeilvYe * 100) ;
    }

}
