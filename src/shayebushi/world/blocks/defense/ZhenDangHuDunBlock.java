package shayebushi.world.blocks.defense;

import arc.Events;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.game.EventType;
import mindustry.world.blocks.defense.ForceProjector;
import shayebushi.SYBSFx;
import shayebushi.SYBSStats;

import static mindustry.Vars.state;
import static mindustry.Vars.tilesize;

public class ZhenDangHuDunBlock extends ForceProjector {
    public float damage = 2000 ;
    public boolean poxianshang = false ;
    public ZhenDangHuDunBlock(String name) {
        super(name);
        breakEffect = SYBSFx.fixedShieldBreak ;
    }
    @Override
    public void setStats() {
        super.setStats();
        stats.add(SYBSStats.podunshanghai, damage) ;
    }
    public class ZhenDangHuDunBuild extends ForceBuild {
        @Override
        public void updateTile() {
            boolean phaseValid = itemConsumer != null && itemConsumer.efficiency(this) > 0;

            phaseHeat = Mathf.lerpDelta(phaseHeat, Mathf.num(phaseValid), 0.1f);

            if(phaseValid && !broken && timer(timerUse, phaseUseTime) && efficiency > 0){
                consume();
            }

            radscl = Mathf.lerpDelta(radscl, broken ? 0f : warmup, 0.05f);

            if(Mathf.chanceDelta(buildup / shieldHealth * 0.1f)){
                Fx.reactorsmoke.at(x + Mathf.range(tilesize / 2f), y + Mathf.range(tilesize / 2f));
            }

            warmup = Mathf.lerpDelta(warmup, efficiency, 0.1f);

            if(buildup > 0){
                float scale = !broken ? cooldownNormal : cooldownBrokenBase;

                //TODO I hate this system
                if(coolantConsumer != null){
                    if(coolantConsumer.efficiency(this) > 0){
                        coolantConsumer.update(this);
                        scale *= (cooldownLiquid * (1f + (liquids.current().heatCapacity - 0.4f) * 0.9f));
                    }
                }

                buildup -= delta() * scale;
            }

            if(broken && buildup <= 0){
                broken = false;
            }

            if(buildup >= shieldHealth + phaseShieldBoost * phaseHeat && !broken){
                broken = true;
                buildup = shieldHealth;
                shieldBreakEffect.at(x, y, realRadius(), team.color, block);
                Units.nearby(null, x, y, radius, u -> {
                    if (u.team != team) {
                        if (poxianshang) {
                            u.health -= damage ;
                        }
                        else {
                            u.damage(damage);
                        }
                    }
                });
                if(team != state.rules.defaultTeam){
                    Events.fire(EventType.Trigger.forceProjectorBreak);
                }
            }

            if(hit > 0f){
                hit -= 1f / 5f * Time.delta;
            }

            deflectBullets();
        }
        @Override
        public void onRemoved(){
            float radius = realRadius();
            if(!broken && radius > 1f) SYBSFx.fixedForceShrink.at(x, y, radius, team.color, sides);
            super.onRemoved();
        }
    }
}
