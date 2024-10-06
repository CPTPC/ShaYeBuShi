package shayebushi.world.blocks.abilities;

import arc.Core;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.gen.Building;
import mindustry.graphics.Pal;

import static arc.util.Time.delta;
import static arc.util.Time.toSeconds;
import static mindustry.Vars.tilesize;

public class RepairAbility extends BlockAbility {
    public float perU = 0.25f, perB = 0.15f, reload = 5 * toSeconds, range = 80 * tilesize ;
    public float timer = 0 ;
    @Override
    public void update(Building b) {
        super.update(b) ;
        timer += delta ;
        if (timer >= reload) {
            Units.nearby(b.team, b.x, b.y, range, u -> {
                u.heal(u.maxHealth * perU) ;
            });
            Units.nearbyBuildings(b.x, b.y, range, u -> {
                u.heal(u.maxHealth * perU) ;
                Fx.healBlockFull.at(u.x, u.y, 0, Pal.heal, u);
                Fx.healBlockFull.at(u.x, u.y, 0,  Pal.heal, u);
            });
            timer = 0 ;
        }
    }
    @Override
    public String localized(){
        return Core.bundle.format("ability.xiufu", perU * 100, perB * 100, reload / toSeconds) ;
    }

}
