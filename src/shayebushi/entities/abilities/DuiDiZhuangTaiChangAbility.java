package shayebushi.entities.abilities;

import arc.*;
import arc.math.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.abilities.Ability;
import mindustry.game.Rules;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.type.*;

public class DuiDiZhuangTaiChangAbility extends Ability {
    public StatusEffect effect;
    public float duration = 60, reload = 100, range = 20;
    public boolean onShoot = false;
    public Effect applyEffect = Fx.none;
    public Effect activeEffect = Fx.overdriveWave;
    public float effectX, effectY;
    public boolean parentizeEffects, effectSizeParam = true;

    protected float timer;

    DuiDiZhuangTaiChangAbility(){}

    public DuiDiZhuangTaiChangAbility(StatusEffect effect, float duration, float reload, float range){
        this.duration = duration;
        this.reload = reload;
        this.range = range;
        this.effect = effect;
    }

    @Override
    public String localized(){
        return Core.bundle.format("ability.statusfield", effect.emoji());
    }

    @Override
    public void update(Unit unit){
        timer += Time.delta;

        if(timer >= reload && (!onShoot || unit.isShooting)){
            Units.nearby(null, unit.x, unit.y, range, other -> {
                if (other.team != unit.team) {
                    other.apply(effect, duration);
                    applyEffect.at(other, parentizeEffects);
                }
            });
            float x = unit.x + Angles.trnsx(unit.rotation, effectY, effectX), y = unit.y + Angles.trnsy(unit.rotation, effectY, effectX);
            activeEffect.at(x, y, effectSizeParam ? range : unit.rotation, parentizeEffects ? unit : null);

            timer = 0f;
        }
    }
}
