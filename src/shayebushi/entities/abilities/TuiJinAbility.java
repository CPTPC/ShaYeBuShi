package shayebushi.entities.abilities;

import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit ;

import static arc.util.Time.delta;
import static mindustry.Vars.tilesize;

public class TuiJinAbility extends Ability {
    public int status = 0 ;
    public float reload = 5f * tilesize ;
    public float timer = 0 ;
    public float speed = 1.5f ;
    @Override
    public void update(Unit u) {
        super.update(u);
        if (u.moving()) {
            timer += delta ;
        }
        else {
            timer = 0 ;
        }
        if (timer >= reload) {
            status = 1 ;
        }
        else {
            status = 0 ;
        }
        if (status == 1) {
            u.speedMultiplier *= speed ;
        }
    }
}
