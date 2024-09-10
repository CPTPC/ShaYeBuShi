package shayebushi.entities.abilities;

import arc.Core;
import arc.util.Time;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;

import static arc.util.Time.toSeconds;

public class ShangHaiShouRongAbility extends Ability {
    public float amount = 65000 ;
    public float once = 30000 ;
    public float current ;
    public float timerI = 0 ;
    public float timerU = 0 ;
    public float lastHealth = 0 ;
    public float reloadS = 30 * toSeconds ;
    public float reloadU = 10 ;
    @Override
    public void update(Unit u) {
        timerU += Time.delta ;
        timerI += Time.delta ;
        if (timerU <= reloadU) {
            lastHealth = u.health ;
            return ;
        }
        if (u.health < lastHealth) {
            float lost = lastHealth - u.health ;
            if (lost <= once) {
                current += lost;
                u.health = lastHealth ;
            }
        }
        if (timerI >= reloadS) {
            if (current > amount) {
                u.health -= current ;
                timerU = 0 ;
            }
            timerI = 0 ;
        }
        lastHealth = u.health() ;
    }
    @Override
    public String localized() {
        return Core.bundle.format("ability.shanghaishourong", amount, once) ;
    }
}
