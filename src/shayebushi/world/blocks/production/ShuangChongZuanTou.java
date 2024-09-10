package shayebushi.world.blocks.production;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.scene.ui.layout.Scl;
import arc.struct.EnumSet;
import arc.struct.ObjectFloatMap;
import arc.util.*;
import arc.util.io.Reads;
import arc.util.io.Writes;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.annotations.Annotations;
import mindustry.entities.units.BuildPlan;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.ui.Fonts;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.blocks.production.BeamDrill;
import mindustry.world.blocks.production.Drill;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.consumers.ConsumeLiquidBase;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class ShuangChongZuanTou extends Drill {
    protected Rand rand = new Rand();

    public
    //@Annotations.Load(value = "@-beam", fallback = "drill-laser")
    TextureRegion laser = Core.atlas.find("" + name + "-beam", "drill-laser");
    public
    //@Annotations.Load(value = "@-beam-end", fallback = "drill-laser-end")
    TextureRegion laserEnd = Core.atlas.find("" + name + "-beam-end", "drill-laser-end");
    public
    //@Annotations.Load(value = "@-beam-center", fallback = "drill-laser-center")
    TextureRegion laserCenter = Core.atlas.find("" + name + "-beam-center", "drill-laser-center");

    public
    //@Annotations.Load(value = "@-beam-boost", fallback = "drill-laser-boost")
    TextureRegion laserBoost = Core.atlas.find("" + name + "-beam-boost", "drill-laser-boost");
    public
    //@Annotations.Load(value = "@-beam-boost-end", fallback = "drill-laser-boost-end")
    TextureRegion laserEndBoost = Core.atlas.find("" + name + "-beam-boost-end", "drill-laser-boost-end");
    public
    //@Annotations.Load(value = "@-beam-boost-center", fallback = "drill-laser-boost-center")
    TextureRegion laserCenterBoost= Core.atlas.find("" + name + "-beam-boost-center", "drill-laser-boost-center");

    public
    //@Annotations.Load("@-pop")
    TextureRegion topRegionn = Core.atlas.find("" + name + "-pop");
    public
    @Annotations.Load("@-glow")
    TextureRegion glowRegion = Core.atlas.find("" + name + "-glow");

    public float drillTimee = 200f;
    public int range = 5;
    public int tierr = 1;
    public float laserWidth = 0.65f;
    /** How many times faster the drill will progress when boosted by an optional consumer. */
    public float optionalBoostIntensity = 2.5f;

    /** Multipliers of drill speed for each item. Defaults to 1. */
    public ObjectFloatMap<Item> drillMultipliers = new ObjectFloatMap<>();

    public Color sparkColor = Color.valueOf("fd9e81"), glowColor = Color.white;
    public float glowIntensity = 0.2f, pulseIntensity = 0.07f;
    public float glowScl = 3f;
    public int sparks = 7;
    public float sparkRange = 10f, sparkLife = 27f, sparkRecurrence = 4f, sparkSpread = 45f, sparkSize = 3.5f;

    public Color boostHeatColor = Color.sky.cpy().mul(0.87f);
    public Color heatColor = new Color(1f, 0.35f, 0.35f, 0.9f);
    public float heatPulse = 0.3f, heatPulseScl = 7f;

    public ShuangChongZuanTou(String name){
        super(name);

        hasItems = true;
        rotate = true;
        update = true;
        solid = true;
        drawArrow = false;
        regionRotated1 = 1;
        ambientSoundVolume = 0.05f;
        ambientSound = Sounds.minebeam;
        itemCapacity = 40 ;
        envEnabled |= Env.space;
        flags = EnumSet.of(BlockFlag.drill);
        //timers ++ ;
    }

    @Override
    public void init(){
        updateClipRadius((range + 2) * tilesize);
        super.init();
    }
    @Override
    public void load(){
        super.load();
        topRegionn = Core.atlas.find("" + name + "-pop") ;
        glowRegion = Core.atlas.find("" + name + "-glow") ;
        laser = Core.atlas.find("" + name + "-beam", "drill-laser");
        laserEnd = Core.atlas.find("" + name + "-beam-end", "drill-laser-end");
        laserCenter = Core.atlas.find("" + name + "-beam-center", "drill-laser-center");
        laserBoost = Core.atlas.find("" + name + "-beam-boost", "drill-laser-boost");
        laserEndBoost = Core.atlas.find("" + name + "-beam-boost-end", "drill-laser-boost-end");
        laserCenterBoost= Core.atlas.find("" + name + "-beam-boost-center", "drill-laser-boost-center");

    }
    @Override
    public void setBars(){
        super.setBars();
//        addBar("drillspeed", (ShuangChongZuanTouBuild e) ->
//                new Bar(() -> Core.bundle.format("bar.drillspeed", Strings.fixed(e.lastDrillSpeed * 60 * e.timeScale(), 2)), () -> Pal.ammo, () -> e.warmup));

        addBar("drillspeedd", (ShuangChongZuanTouBuild e) ->
                new Bar(() -> Core.bundle.format("bar.drillspeed", Strings.fixed(e.lastDrillSpeedd * 60, 2)), () -> Pal.ammo, () -> e.warmupp));
    }

    @Override
    public boolean outputsItems(){
        return true;
    }

    @Override
    public boolean rotatedOutput(int x, int y){
        return false;
    }

    @Override
    public TextureRegion[] icons(){
        TextureRegion[] t = new TextureRegion[]{region, topRegionn,rotatorRegion,topRegion} ;
//        for (TextureRegion tt : t){
//            if (tt != null){
//                System.out.println(tt.toString());
//            }
//        }
        return t;
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        super.drawPlanRegion(plan,list);
        Draw.rect(region, plan.drawx(), plan.drawy());
        Draw.rect(topRegionn, plan.drawx(), plan.drawy(), plan.rotation * 90);
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.drillTier, StatValues.drillables(drillTimee, 0f, size, drillMultipliers, b -> (b instanceof Floor f && f.wallOre && f.itemDrop != null && f.itemDrop.hardness <= tierr) || (b instanceof StaticWall w && w.itemDrop != null && w.itemDrop.hardness <= tierr)));

        stats.add(Stat.drillSpeed, 60f / drillTimee * size, StatUnit.itemsSecond);

        if(optionalBoostIntensity != 1 && findConsumer(f -> f instanceof ConsumeLiquidBase && f.booster) instanceof ConsumeLiquidBase consBase){
            stats.remove(Stat.booster);
            stats.add(Stat.booster,
                    StatValues.speedBoosters("{0}" + StatUnit.timesSpeed.localized() + "（仅限等离子钻机部分）",
                            consBase.amount, optionalBoostIntensity, false,
                            l -> (consumesLiquid(l) && (findConsumer(f -> f instanceof ConsumeLiquid).booster || ((ConsumeLiquid)findConsumer(f -> f instanceof ConsumeLiquid)).liquid != l)))
            );
        }
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x,y,rotation,valid);
        Item item = null, invalidItem = null;
        boolean multiple = false;
        int count = 0;

        for(int i = 0; i < size; i++){
            nearbySide(x, y, rotation, i, Tmp.p1);

            int j = 0;
            Item found = null;
            for(; j < range; j++){
                int rx = Tmp.p1.x + Geometry.d4x(rotation)*j, ry = Tmp.p1.y + Geometry.d4y(rotation)*j;
                Tile other = world.tile(rx, ry);
                if(other != null && other.solid()){
                    Item drop = other.wallDrop();
                    if(drop != null){
                        if(drop.hardness <= tierr){
                            found = drop;
                            count++;
                        }else{
                            invalidItem = drop;
                        }
                    }
                    break;
                }
            }

            if(found != null){
                //check if multiple items will be drilled
                if(item != found && item != null){
                    multiple = true;
                }
                item = found;
            }

            int len = Math.min(j, range - 1);
            Drawf.dashLine(found == null ? Pal.remove : Pal.placing,
                    Tmp.p1.x * tilesize,
                    Tmp.p1.y *tilesize,
                    (Tmp.p1.x + Geometry.d4x(rotation)*len) * tilesize,
                    (Tmp.p1.y + Geometry.d4y(rotation)*len) * tilesize
            );
        }

        if(item != null){
            float width = drawPlaceTextt(Core.bundle.formatFloat("bar.drillspeed", 60f / getDrillTimee(item) * count, 2), x, y, valid);
            if(!multiple){
                float dx = x * tilesize + offset - width/2f - 4f, dy = y * tilesize - offset + size * tilesize / 2f + 5, s = iconSmall / 4f;
                Draw.mixcol(Color.darkGray, 1f);
                Draw.rect(item.fullIcon, dx, dy - 1, s, s);
                Draw.reset();
                Draw.rect(item.fullIcon, dx, dy, s, s);
            }
        }else if(invalidItem != null){
            drawPlaceText(Core.bundle.get("bar.drilltierreq"), x, y, false);
        }

    }

    public float drawPlaceTextt(String text, int x, int y, boolean valid){
        if(renderer.pixelator.enabled()) return 0;

        Color color = valid ? Pal.accent : Pal.remove;
        Font font = Fonts.outline;
        GlyphLayout layout = Pools.obtain(GlyphLayout.class, GlyphLayout::new);
        boolean ints = font.usesIntegerPositions();
        font.setUseIntegerPositions(false);
        font.getData().setScale(1f / 4f / Scl.scl(1f));
        layout.setText(font, text);

        float width = layout.width;

        font.setColor(color);
        float dx = x * tilesize + offset, dy = y * tilesize - offset + size * tilesize / 2f + 3;
        font.draw(text, dx, dy + layout.height + 1, Align.center);
        dy -= 1f;
        Lines.stroke(2f, Color.darkGray);
        Lines.line(dx - layout.width / 2f - 2f, dy, dx + layout.width / 2f + 1.5f, dy);
        Lines.stroke(1f, color);
        Lines.line(dx - layout.width / 2f - 2f, dy, dx + layout.width / 2f + 1.5f, dy);

        font.setUseIntegerPositions(ints);
        font.setColor(Color.white);
        font.getData().setScale(1f);
        Draw.reset();
        Pools.free(layout);

        return width;
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        return super.canPlaceOn(tile,team,rotation) || placeOn(tile,team,rotation);

    }
    public boolean placeOn(Tile tile, Team team, int rotation){
        for(int i = 0; i < size; i++){
            nearbySide(tile.x, tile.y, rotation, i, Tmp.p1);
            for(int j = 0; j < range; j++){
                Tile other = world.tile(Tmp.p1.x + Geometry.d4x(rotation)*j, Tmp.p1.y + Geometry.d4y(rotation)*j);
                if(other != null && other.solid()){
                    Item drop = other.wallDrop();
                    if(drop != null && drop.hardness <= tierr){
                        return true;
                    }
                    break;
                }
            }
        }

        return false;
    }
    public float getDrillTimee(Item item){
        return drillTimee / drillMultipliers.get(item, 1f);
    }

    public class ShuangChongZuanTouBuild extends DrillBuild {
        public Tile[] facing = new Tile[size];
        public Point2[] lasers = new Point2[size];
        public @Nullable
        Item lastItem;
        public float time;
        public float warmupp, boostWarmup;
        public float lastDrillSpeedd;
        public int facingAmount;

        @Override
        public void drawSelect(){
            super.drawSelect();
            if(lastItem != null){
                float dx = x + size * tilesize/2f, dy = y + size * tilesize/2f, s = iconSmall / 4f;
                Draw.mixcol(Color.darkGray, 1f);
                Draw.rect(lastItem.fullIcon, dx, dy - 1, s, s);
                Draw.reset();
                Draw.rect(lastItem.fullIcon, dx, dy, s, s);
            }
        }

        @Override
        public void updateTile(){
            super.updateTile();

            if(lasers[0] == null) updateLasers();

            warmupp = Mathf.approachDelta(warmupp, Mathf.num(efficiency > 0), 1f / 60f);

            updateFacing();

            float multiplier = Mathf.lerp(1f, optionalBoostIntensity, optionalEfficiency);
            float drillTime = getDrillTimee(lastItem);
            boostWarmup = Mathf.lerpDelta(boostWarmup, optionalEfficiency, 0.1f);
            lastDrillSpeedd = (facingAmount * multiplier * timeScale) / drillTime;

            time += edelta() * multiplier;

            if(time >= drillTime){
                for(Tile tile : facing){
                    Item drop = tile == null ? null : tile.wallDrop();
                    if(items.total() < itemCapacity && drop != null){
                        items.add(drop, 1);
                        produced(drop);
                    }
                }
                time %= drillTime;
            }

            if(timer(timerDump, dumpTime)){
                dump();
            }
        }

        @Override
        public boolean shouldConsume(){
            boolean b = true ;
            for (Item i : Vars.content.items()) {
                b = items.get(i) < getMaximumAccepted(i) && b ;
            }
            //return super.shouldConsume() || (b && enabled && lastItem != null) ;
            //return items.total() < itemCapacity && lastItem != null && enabled;
            return super.shouldConsume() || (lastItem != null && items.get(lastItem) < itemCapacity && enabled && dominantItem != null && items.get(dominantItem) < itemCapacity) ;
        }

        @Override
        public void draw(){
            super.draw() ;
            Draw.reset() ;
            //Draw.rect(block.region, x, y);
            Draw.rect(topRegionn, x, y, rotdeg());

            if(isPayload()) return;

            var dir = Geometry.d4(rotation);
            int ddx = Geometry.d4x(rotation + 1), ddy = Geometry.d4y(rotation + 1);

            for(int i = 0; i < size; i++){
                Tile face = facing[i];
                if(face != null){
                    Point2 p = lasers[i];
                    float lx = face.worldx() - (dir.x/2f)*tilesize, ly = face.worldy() - (dir.y/2f)*tilesize;

                    float width = (laserWidth + Mathf.absin(Time.time + i*5 + (id % 9)*9, glowScl, pulseIntensity)) * warmupp;

                    Draw.z(Layer.power - 1);
                    Draw.mixcol(glowColor, Mathf.absin(Time.time + i*5 + id*9, glowScl, glowIntensity));
                    if(Math.abs(p.x - face.x) + Math.abs(p.y - face.y) == 0){
                        Draw.scl(width);

                        if(boostWarmup < 0.99f){
                            Draw.alpha(1f - boostWarmup);
                            Draw.rect(laserCenter, lx, ly);
                        }

                        if(boostWarmup > 0.01f){
                            Draw.alpha(boostWarmup);
                            Draw.rect(laserCenterBoost, lx, ly);
                        }

                        Draw.scl();
                    }else{
                        float lsx = (p.x - dir.x/2f) * tilesize, lsy = (p.y - dir.y/2f) * tilesize;

                        if(boostWarmup < 0.99f){
                            Draw.alpha(1f - boostWarmup);
                            Drawf.laser(laser, laserEnd, lsx, lsy, lx, ly, width);
                        }

                        if(boostWarmup > 0.001f){
                            Draw.alpha(boostWarmup);
                            Drawf.laser(laserBoost, laserEndBoost, lsx, lsy, lx, ly, width);
                        }
                    }
                    Draw.color();
                    Draw.mixcol();

                    Draw.z(Layer.effect);
                    Lines.stroke(warmupp);
                    rand.setState(i, id);
                    Color col = face.wallDrop().color;
                    Color spark = Tmp.c3.set(sparkColor).lerp(boostHeatColor, boostWarmup);
                    for(int j = 0; j < sparks; j++){
                        float fin = (Time.time / sparkLife + rand.random(sparkRecurrence + 1f)) % sparkRecurrence;
                        float or = rand.range(2f);
                        Tmp.v1.set(sparkRange * fin, 0).rotate(rotdeg() + rand.range(sparkSpread));

                        Draw.color(spark, col, fin);
                        float px = Tmp.v1.x, py = Tmp.v1.y;
                        if(fin <= 1f) Lines.lineAngle(lx + px + or * ddx, ly + py + or * ddy, Angles.angle(px, py), Mathf.slope(fin) * sparkSize);
                    }
                    Draw.reset();
                }
            }

            if(glowRegion.found()){
                Draw.z(Layer.blockAdditive);
                Draw.blend(Blending.additive);
                Draw.color(Tmp.c1.set(heatColor).lerp(boostHeatColor, boostWarmup), warmupp * (heatColor.a * (1f - heatPulse + Mathf.absin(heatPulseScl, heatPulse))));
                Draw.rect(glowRegion, x, y, rotdeg());
                Draw.blend();
                Draw.color();
            }

            Draw.blend();
            Draw.reset();
        }

        @Override
        public void onProximityUpdate(){
            super.onProximityUpdate();
            //when rotated.
            updateLasers();
            updateFacing();
        }

        protected void updateLasers(){
            for(int i = 0; i < size; i++){
                if(lasers[i] == null) lasers[i] = new Point2();
                nearbySide(tileX(), tileY(), rotation, i, lasers[i]);
            }
        }

        protected void updateFacing(){
            lastItem = null;
            boolean multiple = false;
            int dx = Geometry.d4x(rotation), dy = Geometry.d4y(rotation);
            facingAmount = 0;

            //update facing tiles
            for(int p = 0; p < size; p++){
                Point2 l = lasers[p];
                Tile dest = null;
                for(int i = 0; i < range; i++){
                    int rx = l.x + dx*i, ry = l.y + dy*i;
                    Tile other = world.tile(rx, ry);
                    if(other != null){
                        if(other.solid()){
                            Item drop = other.wallDrop();
                            if(drop != null && drop.hardness <= tierr){
                                facingAmount ++;
                                if(lastItem != drop && lastItem != null){
                                    multiple = true;
                                }
                                lastItem = drop;
                                dest = other;
                            }
                            break;
                        }
                    }
                }

                facing[p] = dest;
            }

            //when multiple items are present, count that as no item
            if(multiple){
                lastItem = null;
            }
        }

        @Override
        public byte version(){
            return 1;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(time);
            write.f(warmupp);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            if(revision >= 1){
                time = read.f();
                warmupp = read.f();
            }
        }
    }
}
