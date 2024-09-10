package shayebushi.entities.abilities;

import arc.Core;
import arc.Events;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import shayebushi.SYBSUnitTypes;

public class XiuJiDunJiaAbility extends Ability {
    public StatusEffect[] effect;
    public float amount = 60, reload = 60, range = 560,heal = 100;
    public boolean onShoot = false;
    public Effect applyEffect = Fx.none;
    public Effect applyEffectt = Fx.none;
    public Effect activeEffect = Fx.overdriveWave;
    public float effectX, effectY;
    public boolean parentizeEffects, effectSizeParam = true;
    public float damage ;
    protected float timer;
    public XiuJiDunJiaAbility(){}
    public Seq<Unit> units = new Seq<>() ;
    public XiuJiDunJiaAbility(float amount , float heal , float reload){
        this.amount = amount;
        this.reload = reload;
        this.heal = heal ;
    }

    @Override
    public String localized(){
        return Core.bundle.format("ability.xiujidunjia");
    }

    @Override
    public void update(Unit unit){
        if (unit.type == SYBSUnitTypes.xiuji && unit.healthf() <= 0.5f){
            unit.armor = unit.type.armor * 4 ;
        }
    }
}
