package shayebushi.world.blocks.defense;

import arc.Events;
import arc.func.Cons;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Intersector;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.annotations.Annotations;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.logic.LAccess;
import mindustry.logic.Ranged;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.ForceProjector;
import mindustry.world.consumers.Consume;
import mindustry.world.consumers.ConsumeCoolant;
import mindustry.world.consumers.ConsumeItems;
import mindustry.world.meta.*;
import shayebushi.SYBSFx;
import shayebushi.SYBSStats;
import shayebushi.world.blocks.AbilityBlock;
import shayebushi.world.blocks.abilities.BlockAbility;

import static mindustry.Vars.*;

public class ZhenDangHuDunBlock extends AbilityBlock {
    public final int timerUse = timers++;
    public float phaseUseTime = 350f;

    public float phaseRadiusBoost = 80f;
    public float phaseShieldBoost = 400f;
    public float radius = 101.7f;
    public int sides = 6;
    public float shieldRotation = 0f;
    public float shieldHealth = 700f;
    public float cooldownNormal = 1.75f;
    public float cooldownLiquid = 1.5f;
    public float cooldownBrokenBase = 0.35f;
    public float coolantConsumption = 0.1f;
    public boolean consumeCoolant = true;
    public Effect absorbEffect = Fx.absorb;
    public Effect shieldBreakEffect = Fx.shieldBreak;
    public @Annotations.Load("@-top")
    TextureRegion topRegion;

    //TODO json support
    public @Nullable
    Consume itemConsumer, coolantConsumer;

    protected static ZhenDangHuDunBuild paramEntity;
    protected static Effect paramEffect;
    protected static final Cons<Bullet> shieldConsumer = bullet -> {
        if(bullet.team != paramEntity.team && bullet.type.absorbable && Intersector.isInRegularPolygon(((ZhenDangHuDunBlock)(paramEntity.block)).sides, paramEntity.x, paramEntity.y, paramEntity.realRadius(), ((ZhenDangHuDunBlock)(paramEntity.block)).shieldRotation, bullet.x, bullet.y)){
            if (((ZhenDangHuDunBlock)paramEntity.block).fantan) {
                bullet.trns(-bullet.vel.x, -bullet.vel.y);

                float penX = Math.abs(paramEntity.x - bullet.x), penY = Math.abs(paramEntity.y - bullet.y);

                if(penX > penY){
                    bullet.vel.x *= -1;
                }else{
                    bullet.vel.y *= -1;
                }

                bullet.owner = paramEntity;
                bullet.team = paramEntity.team;
                bullet.time += 1f;
            }
            bullet.absorb();
            paramEffect.at(bullet);
            paramEntity.hit = 1f;
            paramEntity.buildup += bullet.damage;
        }
    };
    public float damage = 2000 ;
    public boolean poxianshang = false ;
    public float poxianshangDamage = 0 ;
    public boolean fantan = false ;
    public ZhenDangHuDunBlock(String name) {
        super(name);
        update = true;
        solid = true;
        group = BlockGroup.projectors;
        hasPower = true;
        hasLiquids = true;
        hasItems = true;
        envEnabled |= Env.space;
        ambientSound = Sounds.shield;
        ambientSoundVolume = 0.08f;

        if(consumeCoolant){
            consume(coolantConsumer = new ConsumeCoolant(coolantConsumption)).boost().update(false);
        }
        breakEffect = SYBSFx.fixedShieldBreak ;
    }

    @Override
    public void init(){
        updateClipRadius(radius + phaseRadiusBoost + 3f);
        super.init();
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("shield", (ZhenDangHuDunBuild entity) -> new Bar("stat.shieldhealth", Pal.accent, () -> entity.broken ? 0f : 1f - entity.buildup / (shieldHealth + phaseShieldBoost * entity.phaseHeat)).blink(Color.white));
    }

    @Override
    public boolean outputsItems(){
        return false;
    }

    @Override
    public void setStats() {
        super.setStats();
        boolean consItems = itemConsumer != null;

        if(consItems) stats.timePeriod = phaseUseTime;
        stats.add(Stat.shieldHealth, shieldHealth, StatUnit.none);
        stats.add(Stat.cooldownTime, (int) (shieldHealth / cooldownBrokenBase / 60f), StatUnit.seconds);

        if(consItems && itemConsumer instanceof ConsumeItems coni){
            stats.remove(Stat.booster);
            stats.add(Stat.booster, StatValues.itemBoosters("+{0} " + StatUnit.shieldHealth.localized(), stats.timePeriod, phaseShieldBoost, phaseRadiusBoost, coni.items, this::consumesItem));
            stats.add(Stat.booster, StatValues.speedBoosters("", coolantConsumption, Float.MAX_VALUE, true, this::consumesLiquid));
        }
        stats.add(SYBSStats.podunshanghai, damage) ;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        Draw.color(Pal.gray);
        Lines.stroke(3f);
        Lines.poly(x * tilesize + offset, y * tilesize + offset, sides, radius, shieldRotation);
        Draw.color(player.team().color);
        Lines.stroke(1f);
        Lines.poly(x * tilesize + offset, y * tilesize + offset, sides, radius, shieldRotation);
        Draw.color();
    }

    public class ZhenDangHuDunBuild extends AbilityBuild implements Ranged {
        public boolean broken = true;
        public float buildup, radscl, hit, warmup, phaseHeat;

        @Override
        public float range(){
            return realRadius();
        }

        @Override
        public boolean shouldAmbientSound(){
            return !broken && realRadius() > 1f;
        }

        @Override
        public void pickedUp(){
            super.pickedUp();
            radscl = warmup = 0f;
        }

        @Override
        public boolean inFogTo(Team viewer){
            return false;
        }

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
                        u.health -= poxianshangDamage ;
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
            for (BlockAbility ba : as) {
                ba.update(this);
            }
        }

        @Override
        public void onRemoved(){
            float radius = realRadius();
            if(!broken && radius > 1f) SYBSFx.fixedForceShrink.at(x, y, radius, team.color, sides);
            super.onRemoved();
            for (BlockAbility ba : as) {
                ba.onRemoved(this);
            }
        }

        public void deflectBullets(){
            float realRadius = realRadius();

            if(realRadius > 0 && !broken){
                paramEntity = this;
                paramEffect = absorbEffect;
                Groups.bullet.intersect(x - realRadius, y - realRadius, realRadius * 2f, realRadius * 2f, shieldConsumer);
            }
        }

        public float realRadius(){
            return (radius + phaseHeat * phaseRadiusBoost) * radscl;
        }

        @Override
        public double sense(LAccess sensor){
            if(sensor == LAccess.heat) return buildup;
            //if(sensor == LAccess.shield) return broken ? 0f : Math.max(shieldHealth + phaseShieldBoost * phaseHeat - buildup, 0);
            return super.sense(sensor);
        }

        @Override
        public void draw(){
            super.draw();

            if(buildup > 0f){
                Draw.alpha(buildup / shieldHealth * 0.75f);
                Draw.z(Layer.blockAdditive);
                Draw.blend(Blending.additive);
                Draw.rect(topRegion, x, y);
                Draw.blend();
                Draw.z(Layer.block);
                Draw.reset();
            }

            drawShield();
        }

        public void drawShield(){
            if(!broken){
                float radius = realRadius();

                if(radius > 0.001f){
                    Draw.color(team.color, Color.white, Mathf.clamp(hit));

                    if(renderer.animateShields){
                        Draw.z(Layer.shields + 0.001f * hit);
                        Fill.poly(x, y, sides, radius, shieldRotation);
                    }else{
                        Draw.z(Layer.shields);
                        Lines.stroke(1.5f);
                        Draw.alpha(0.09f + Mathf.clamp(0.08f * hit));
                        Fill.poly(x, y, sides, radius, shieldRotation);
                        Draw.alpha(1f);
                        Lines.poly(x, y, sides, radius, shieldRotation);
                        Draw.reset();
                    }
                }
            }

            Draw.reset();
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.bool(broken);
            write.f(buildup);
            write.f(radscl);
            write.f(warmup);
            write.f(phaseHeat);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            broken = read.bool();
            buildup = read.f();
            radscl = read.f();
            warmup = read.f();
            phaseHeat = read.f();
            for (BlockAbility a : abilities) {
                as.add(a.copy()) ;
            }
            for (BlockAbility ba : as) {
                ba.onRead(this);
            }
        }
    }
}
