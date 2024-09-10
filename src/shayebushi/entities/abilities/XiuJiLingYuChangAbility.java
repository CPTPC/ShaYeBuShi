package shayebushi.entities.abilities;

import arc.Core;
import arc.Events;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.struct.Seq;
import arc.util.Time;
//import jdk.jfr.Event;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import shayebushi.SYBSShenShengUnitTypes;
import shayebushi.SYBSUnitTypes;

import static mindustry.Vars.tilesize;

public class XiuJiLingYuChangAbility extends Ability {
    public StatusEffect[] effect;
    public float amount = 0.6f, reload = 60, range = 560,heal = 100 * 10.8f;
    public boolean onShoot = false;
    public Effect applyEffect = Fx.none;
    public Effect applyEffectt = Fx.none;
    public Effect activeEffect = Fx.overdriveWave;
    public float effectX, effectY;
    public boolean parentizeEffects, effectSizeParam = true;
    public float damage ;
    public float timer;
    public Seq<Unit> units = new Seq<>() ;

    public float timerU = 0 ;
    public float lastHealth ;
    public float radius = 50 * tilesize ;

    public XiuJiLingYuChangAbility(){

    }
    public XiuJiLingYuChangAbility(float amount , float heal , float reload){
        this.amount = amount;
        this.reload = reload;
        this.heal = heal ;
    }

    @Override
    public String localized(){
        return Core.bundle.format("ability.xiujilingyuchang", amount * 100 , 60 / reload * heal);
    }

    @Override
    public void update(Unit unit){
        timer += Time.delta ;
        timerU += Time.delta ;
        if (timer >= reload){
            unit.heal(heal) ;
            timer = 0 ;
        }
        if (timerU <= 10) {
            lastHealth = unit.health ;
            return ;
        }
        if (unit.health < lastHealth) {
            float lost = lastHealth - unit.health ;
            Units.nearby(null, unit.x, unit.y, radius, u -> {
                if (u.team != unit.team) {
                    u.damage(lost * amount * (unit.healthf() <= 0.5f ? 4 : 1)) ;
                }
            });
            //System.out.println(lost);
        }
        lastHealth = unit.health ;
    }
}
