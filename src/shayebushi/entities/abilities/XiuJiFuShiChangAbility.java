package shayebushi.entities.abilities;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.effect.WrapEffect;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import shayebushi.SYBSShenShengUnitTypes;
import shayebushi.SYBSUnitTypes;

public class XiuJiFuShiChangAbility extends Ability {
    public StatusEffect[] effect;
    public float duration = 60, reload = 60, range = 20,reloadd = 1800;
    public boolean onShoot = false;
    public Effect applyEffect = Fx.none;
    public Effect applyEffectt = new MultiEffect(Fx.massiveExplosion, new WrapEffect(Fx.dynamicSpikes, Color.valueOf("aa6600"), 24f), new WaveEffect(){{
        //colorFrom = colorTo = color;
        colorFrom = colorTo = Color.valueOf("aa6600");
        sizeTo = 40f;
        lifetime = 12f;
        strokeFrom = 4f;
    }});
    public Effect activeEffect = Fx.overdriveWave;
    public float effectX, effectY;
    public boolean parentizeEffects, effectSizeParam = true;
    public float damage ;
    protected float timer;
    protected float timerr;
    protected float timerrr;
    XiuJiFuShiChangAbility(){}

    public XiuJiFuShiChangAbility(float duration, float reload,float reloadd, float range,float damage, StatusEffect... effect){
        this.duration = duration;
        this.reload = reload;
        this.reloadd = reloadd ;
        this.range = range;
        this.effect = effect;
        this.damage = damage ;
    }
    public void draww(Unit unit,Unit u){
        Draw.color(unit.team.color);
        Lines.stroke(5);
        Lines.circle(u.x,u.y,u.hitSize * 1.3f) ;
        Lines.line(u.x - u.hitSize * 1.9f,u.y,u.x + u.hitSize * 1.9f,u.y);
        Lines.line(u.x,u.y - u.hitSize * 1.9f,u.x,u.y + u.hitSize * 1.9f);
        Lines.circle(u.x,u.y,u.hitSize * 1.9f - u.hitSize * 0.6f * (timer / reload)) ;
        Draw.reset();
    }
    public void drawww(Unit unit, Building bb){
        Draw.color(unit.team.color);
        Lines.stroke(5);
        Lines.circle(bb.x,bb.y,bb.block.size * 4 * 1.3f) ;
        Lines.line(bb.x,bb.y - bb.block.size * 4 * 1.9f,bb.x,bb.y + bb.block.size * 4 * 1.9f);
        Lines.line(bb.x - bb.block.size * 4 * 1.9f,bb.y,bb.x + bb.block.size * 4 * 1.9f,bb.y);
        Lines.circle(bb.x,bb.y,bb.block.size * 4 * 1.9f - bb.block.size * 4 * 0.6f * (timer / reload)) ;
        Draw.reset();

    }
    @Override
    public void draw(Unit unit){
        super.draw(unit);
        Draw.color(unit.team.color);
        Lines.stroke(5);
        Lines.arc(unit.x,unit.y,unit.hitSize*1.15f,timerr/reloadd);
        //draww(unit,unit) ;
    }
    @Override
    public String localized(){
        return Core.bundle.format("ability.xiujifushichang", effect[0].emoji(),effect[1].emoji(),effect[2].emoji(),effect[3].emoji(),effect[4].emoji(),effect[5].emoji(),effect[6].emoji(),effect[7].emoji(),effect[8].emoji(),effect[9].emoji(),damage);
    }

    @Override
    public void update(Unit unit){
        timer += Time.delta;
        timerr += Time.delta;
        timerrr += Time.delta;
        if(timer >= reload && (!onShoot || unit.isShooting)){
            Units.nearby(null, unit.x, unit.y, range, other -> {
                for (StatusEffect e : effect) {
                    if (other.team != unit.team) {
                        other.apply(e, duration);
                        other.speedMultiplier = 0.2f;
                    }
                }
                applyEffect.at(other, parentizeEffects);
            });
            float x = unit.x + Angles.trnsx(unit.rotation, effectY, effectX), y = unit.y + Angles.trnsy(unit.rotation, effectY, effectX);
            activeEffect.at(x, y, effectSizeParam ? range : unit.rotation, parentizeEffects ? unit : null);

            timer = 0f;
        }
        if(timerr >= reloadd && (!onShoot || unit.isShooting)){
            Units.nearby(Team.crux, unit.x, unit.y, range, other -> {
                if (other.team != unit.team && other.maxHealth <= 100000){
                    other.kill();
                }
                else{
                    other.health -= 100000 ;
                }
                applyEffectt.at(other, parentizeEffects);
            });
            Units.nearby(Team.malis, unit.x, unit.y, range, other -> {
                if (other.team != unit.team && other.maxHealth <= 100000){
                    other.kill();
                }
                else{
                    other.health -= 100000 ;
                }
                applyEffectt.at(other, parentizeEffects);
            });
            Units.nearby(Team.green, unit.x, unit.y, range, other -> {
                if (other.team != unit.team && other.maxHealth <= 100000){
                    other.kill();
                }
                else{
                    other.health -= 100000 ;
                }
                applyEffectt.at(other, parentizeEffects);
            });
            float x = unit.x + Angles.trnsx(unit.rotation, effectY, effectX), y = unit.y + Angles.trnsy(unit.rotation, effectY, effectX);
            activeEffect.at(x, y, effectSizeParam ? range : unit.rotation, parentizeEffects ? unit : null);

            timerr = 0f;
        }
        if (timerrr >= 60){
            Units.nearby(null, unit.x, unit.y, range, other -> {
                if (other.team != unit.team && !SYBSShenShengUnitTypes.shenshengjidanwei.contains(other.type)){
                    other.health -= damage;
                    other.speedMultiplier = 0.2f ;
                }
                //applyEffectt.at(other, parentizeEffects);
            });
            timerrr = 0 ;
        }
    }
}
