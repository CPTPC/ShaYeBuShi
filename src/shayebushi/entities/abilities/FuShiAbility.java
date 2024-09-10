package shayebushi.entities.abilities;

import arc.util.Time;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;

public class FuShiAbility extends Ability {
    public float beilv = 1 ;
    public float max ;
    public float more ;
    public float timerU = 0 ;
    public float lastHealth ;
    public FuShiAbility(float m, float u) {
        max = m ;
        more = u ;
    }
    @Override
    public void update(Unit u) {
        super.update(u) ;
        timerU += Time.delta ;
        if (timerU <= 10) {
            lastHealth = u.health ;
        }
        if (u.health < lastHealth) {
            float lost = lastHealth - u.health ;
            u.health += lost ;
            beilv *= more ;
            if (beilv >= max) {
                beilv = 1 ;
            }
            lost *= beilv ;
            u.health -= lost ;
        }
        lastHealth = u.health ;
    }
}
