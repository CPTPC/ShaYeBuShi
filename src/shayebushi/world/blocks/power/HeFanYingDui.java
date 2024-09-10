package shayebushi.world.blocks.power;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.EnumSet;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.annotations.Annotations;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.Units;
import mindustry.game.EventType;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.logic.LAccess;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.world.blocks.power.NuclearReactor;
import mindustry.world.blocks.power.PowerGenerator;
import mindustry.world.consumers.ConsumeItemFilter;
import mindustry.world.consumers.ConsumeLiquidFilter;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Env;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import shayebushi.*;

import static mindustry.Vars.content;
import static mindustry.Vars.tilesize;

public class HeFanYingDui extends DianYaGenerator {
    public final int timerFuel = timers++;

    public Color lightColor = Color.valueOf("7f19ea");
    public Color coolColor = new Color(1, 1, 1, 0f);
    public Color hotColor = Color.valueOf("ff9575a3");
    /** ticks to consume 1 fuel */
    public float itemDuration = 120;
    /** heating per frame * fullness */
    public float heating = 0.01f;
    /** threshold at which block starts smoking */
    public float smokeThreshold = 0.3f;
    /** heat threshold at which lights start flashing */
    public float flashThreshold = 0.46f;

    /** heat removed per unit of coolant */
    public float coolantPower = 0.15f;

    //public Item fuelItem = Items.thorium;

    public @Annotations.Load("@-top")
    TextureRegion topRegion;
    public @Annotations.Load("@-lights") TextureRegion lightsRegion;

    public @Nullable
    ConsumeItemFilter filterItem;
    public @Nullable
    ConsumeLiquidFilter filterLiquid;
    public int danpians = 15 ;

    public HeFanYingDui(String name){
        super(name);
        itemCapacity = 150;
        liquidCapacity = 350;
        hasItems = true;
        hasLiquids = true;
        rebuildable = false;
        flags = EnumSet.of(BlockFlag.reactor, BlockFlag.generator);
        schematicPriority = -5;
        envEnabled = Env.any;

        explosionShake = 80f;
        explosionShakeDuration = 120f;

        explosionRadius = 80;
        explosionDamage = 6000 * 16 ;

        explodeEffect = SYBSFx.hedanbaozha;
        explodeSound = Sounds.explosionbig;
        dianya = 3 ;
    }

    @Override
    public void setStats(){
        super.setStats();

        if(hasItems){
            stats.add(Stat.productionTime, itemDuration / 60f, StatUnit.seconds);
        }
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("heat", (HeFanYingDuiBuild entity) -> new Bar("bar.heat", Pal.lightOrange, () -> entity.heat));
    }

    @Override
    public void load() {
        super.load() ;
        topRegion = Core.atlas.find(name + "-top", SYBSUnitTypes.getError()) ;
        lightsRegion = Core.atlas.find(name + "-lights", SYBSUnitTypes.getError()) ;
    }

    public class HeFanYingDuiBuild extends DianYaGeneratorBuild {
        public float heat;
        public float flash;
        public float smoothLight;
        public float efficiencyMultiplier = 1 ;

        @Override
        public void updateTile(){
            int fuel = 0;
            for (Item i : content.items()) {
                if (i.radioactivity > 0) {
                    fuel += items.get(i) ;
                }
            }
            float fullness = (float)fuel / itemCapacity;
            productionEfficiency = fullness * efficiencyMultiplier;

            if(fuel > 0 && enabled){
                heat += fullness * heating * Math.min(delta(), 4f);

                if(timer(timerFuel, itemDuration / timeScale)){
                    consume();
                    offload(SYBSItems.hefeiliao);
                }
            }else{
                productionEfficiency = 0f;
            }

            if(heat > 0){
                float maxUsed = Math.min(liquids.currentAmount(), heat / coolantPower);
                heat -= maxUsed * coolantPower;
                liquids.remove(liquids.current(), maxUsed);
            }

            if(heat > smokeThreshold){
                float smoke = 1.0f + (heat - smokeThreshold) / (1f - smokeThreshold); //ranges from 1.0 to 2.0
                if(Mathf.chance(smoke / 20.0 * delta())){
                    Fx.reactorsmoke.at(x + Mathf.range(size * tilesize / 2f),
                            y + Mathf.range(size * tilesize / 2f));
                }
            }

            heat = Mathf.clamp(heat);

            if(heat >= 0.999f){
                Events.fire(EventType.Trigger.thoriumReactorOverheat);
                kill();
            }
        }

        @Override
        public double sense(LAccess sensor){
            if(sensor == LAccess.heat) return heat;
            return super.sense(sensor);
        }

        @Override
        public boolean shouldExplode(){
            int amount = 0 ;
            for (Item i : content.items()) {
                if (i.radioactivity > 0) {
                    amount += items.get(i) ;
                }
            }
            return super.shouldExplode() && (amount >= 5 || heat >= 0.5f);
        }

        @Override
        public void drawLight(){
            float fract = productionEfficiency;
            smoothLight = Mathf.lerpDelta(smoothLight, fract, 0.08f);
            Drawf.light(x, y, (90f + Mathf.absin(5, 5f)) * smoothLight, Tmp.c1.set(lightColor).lerp(Color.scarlet, heat), 0.6f * smoothLight);
        }

        @Override
        public void draw(){
            super.draw();

            Draw.color(coolColor, hotColor, heat);
            Fill.rect(x, y, size * tilesize, size * tilesize);

            Draw.color(liquids.current().color);
            Draw.alpha(liquids.currentAmount() / liquidCapacity);
            if (topRegion != null) {
                Draw.rect(topRegion, x, y);
            }

            if(heat > flashThreshold){
                flash += (1f + ((heat - flashThreshold) / (1f - flashThreshold)) * 5.4f) * Time.delta;
                Draw.color(Color.red, Color.yellow, Mathf.absin(flash, 9f, 1f));
                Draw.alpha(0.3f);
                if (lightsRegion != null) {
                    Draw.rect(lightsRegion, x, y);
                }
            }

            Draw.reset();
        }

        @Override
        public void updateEfficiencyMultiplier(){
            if(filterItem != null){
                float m = filterItem.efficiencyMultiplier(this);
                if(m > 0) efficiencyMultiplier = m ;
            }else if(filterLiquid != null){
                float m = filterLiquid.efficiencyMultiplier(this);
                if(m > 0) efficiencyMultiplier = m ;
            }
        }

        @Override
        public void kill() {
            super.kill() ;
            if (!shouldExplode()) return ;
            Units.nearby(null, x, y, explosionRadius, u -> {
                u.apply(SYBSStatusEffects.fushe, explosionDamage / 9600f * 60) ;
                u.apply(SYBSStatusEffects.shuaibian, explosionDamage / 9600f * 60) ;
            }) ;
            for (int i = 0 ; i < danpians ; i ++) {
                Vec2 v = ShaYeBuShi.random(x, y, explosionRadius) ;
                SYBSBullets.hedandanpian.create(this, v.x, v.y, ShaYeBuShi.r.random(360)).add() ;
            }
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(heat);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            heat = read.f();
        }
    }
}
