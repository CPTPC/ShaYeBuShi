package shayebushi.world.blocks;

import arc.Core;
import arc.func.Func;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.ObjectMap;
import arc.struct.OrderedMap;
import arc.struct.Seq;
import arc.util.Eachable;
import arc.util.Interval;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.audio.SoundLoop;
import mindustry.content.Liquids;
import mindustry.content.UnitTypes;
import mindustry.ctype.MappableContent;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.units.BuildPlan;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.input.Binding;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.blocks.ControlBlock;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.payloads.BuildPayload;
import mindustry.world.blocks.payloads.Payload;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.*;
import mindustry.world.modules.ItemModule;
import mindustry.world.modules.LiquidModule;
import mindustry.world.modules.PowerModule;
import shayebushi.SYBSUnitTypes;
import shayebushi.ShaYeBuShi;
import shayebushi.world.blocks.defense.WallTurret;
import shayebushi.world.blocks.defense.turrets.AbilityTurret;
import shayebushi.world.draw.DrawAbilityTurret;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static mindustry.Vars.emptyTile;
import static mindustry.Vars.tilesize;

public class MultiBlock extends Block {
    public Seq<Object> turrets = new Seq<>() ;
//    public ObjectMap<Payload, Vec2> payloads = new ObjectMap<>() ;
//    public ObjectMap<ItemTurret, Item> ammos = new ObjectMap<>() ;
    public boolean playerControllable = true;
    public MultiBlock(String name) {
        super(name);
        itemCapacity = 50 ;
    }
    public MultiBlock(String name, Object... turret) {
        this(name);
        configurable = true ;
        saveConfig = true ;
        hasItems = true ;
        hasLiquids = true ;
        update = true ;
        for (Object o : turret){
            /*
            if (o instanceof ItemTurret t && t.drawer instanceof DrawTurret) {
                //t.load();
                //t.loadIcon();
                ItemTurret copy = new ItemTurret(ShaYeBuShi.fixedRandomString(t)) ;
                ShaYeBuShi.clone(t, copy) ;
                //System.out.println((copy.ammoTypes.isEmpty()) + " " + (t.ammoTypes.isEmpty()));
                DrawTurret d = new DrawTurret();
                ShaYeBuShi.clone((DrawTurret) t.drawer, d);
                d.basePrefix = "multi-";
                copy.drawer = d;
                copy.buildVisibility = BuildVisibility.hidden ;
                turrets.add(copy);
            }
            else if (o instanceof AbilityTurret t && t.drawer instanceof DrawAbilityTurret) {
                //t.load();
                //t.loadIcon();
                AbilityTurret copy = new AbilityTurret(ShaYeBuShi.fixedRandomString(t)) ;
                ShaYeBuShi.clone(t, copy) ;
                DrawAbilityTurret d = new DrawAbilityTurret();
                ShaYeBuShi.clone((DrawAbilityTurret) t.drawer, d);
                d.basePrefix = "multi-" ;
                //d.base = SYBSUnitTypes.getError() ;
                copy.drawer = d;
                copy.buildVisibility = BuildVisibility.hidden ;
                turrets.add(copy);
            }
            else {
                turrets.add(o);
            }
            */
            turrets.add(o);
        }
    }
    /*
    @Override
    public void setStats() {
        super.setStats();
        Stats ss = stats ;
        for (int i = 0, z = 1 ; i < turrets.size && z < turrets.size ; i += 2, z += 2){
            if (turrets.get(i) instanceof Block b && turrets.get(z) instanceof Vec2 v) {
                if(!b.stats.intialized){
                    b.setStats();
                    b.stats.intialized = true;
                }
                for (StatCat s : StatCat.all) {
                    ObjectMap<Stat, Boolean> has = new ObjectMap<>() ;
                    for (Stat s2 : ss.toMap().get(s, OrderedMap::new).keys()) {
                        has.put(s2, ss.toMap().get(s).get(s2).size > 0) ;
                    }
                    for (Stat s3 : b.stats.toMap().get(s, OrderedMap::new).keys()) {
                        if (!has.get(s3, false)) {
                            b.stats.toMap().get(s).get(s3, Seq::new).each(sv -> stats.add(s3, sv));
                        }
                    }
                }
            }
        }
    }
    */
    @Override
    public void setStats() {
        super.setStats();
        for (int i = 0, z = 1 ; i < turrets.size && z < turrets.size ; i += 2, z += 2){
            if (turrets.get(i) instanceof Block b && turrets.get(z) instanceof Vec2 v) {
                if(!b.stats.intialized){
                    b.setStats();
                    b.stats.intialized = true;
                }
                for (StatCat s : StatCat.all) {
                    if (!(s == StatCat.crafting || s == StatCat.function)) continue; ;
                    for (Stat s3 : b.stats.toMap().get(s, OrderedMap::new).keys()) {
                        b.stats.toMap().get(s).get(s3, Seq::new).each(sv -> stats.add(s3, sv));
                    }
                }
            }
        }
    }


    @Override
    public void setBars() {
        super.setBars();
        /*
        for (Object o : turrets) {
            if (o instanceof Block b) {
                b.setBars();
                try {
                    Field f = Block.class.getDeclaredField("barMap") ;
                    f.setAccessible(true) ;
                    for (String s : ((OrderedMap<String, Func<Building, Bar>>)f.get(b)).keys()) {
                        if (!barMap.containsKey(s)) {
                            addBar(s, ((OrderedMap<String, Func<Building, Bar>>) f.get(b)).get(s));
                        }
                    }
                }
                catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
        */
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);
        for (int i = 0, z = 1 ; i < turrets.size && z < turrets.size ; i += 2, z += 2){
            if (turrets.get(i) instanceof Block b && turrets.get(z) instanceof Vec2 v) {
                b.drawPlace((int)(x + v.x / tilesize), (int)(y + v.y / tilesize), rotation, valid);
            }
        }
    }

    @Override
    public void drawPlan(BuildPlan plan, Eachable<BuildPlan> list, boolean valid) {
        super.drawPlan(plan, list, valid);
        for (int i = 0, z = 1 ; i < turrets.size && z < turrets.size ; i += 2, z += 2){
            if (turrets.get(i) instanceof Block b && turrets.get(z) instanceof Vec2 v) {
                BuildPlan b2 = new BuildPlan((int)((plan.x * tilesize + v.x) / tilesize), (int)((plan.y * tilesize + v.y) / tilesize), plan.rotation, b) ;
                b.drawPlan(b2, list, valid);
            }
        }
    }

    @Override
    public void drawPlan(BuildPlan plan, Eachable<BuildPlan> list, boolean valid, float a) {
        super.drawPlan(plan, list, valid, a);
        for (int i = 0, z = 1 ; i < turrets.size && z < turrets.size ; i += 2, z += 2){
            if (turrets.get(i) instanceof Block b && turrets.get(z) instanceof Vec2 v) {
                BuildPlan b2 = new BuildPlan((int)((plan.x * tilesize + v.x) / tilesize), (int)((plan.y * tilesize + v.y) / tilesize), plan.rotation, b) ;
                b.drawPlan(b2, list, valid, a * (plan.block instanceof Turret || plan.block instanceof AbilityTurret ? 0.15f : 1));
            }
        }
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        super.drawPlanRegion(plan,list);
        for (int i = 0, z = 1 ; i < turrets.size && z < turrets.size ; i += 2, z += 2){
            if (turrets.get(i) instanceof Block b && turrets.get(z) instanceof Vec2 v) {
                BuildPlan b2 = new BuildPlan((int)((plan.x * tilesize + v.x) / tilesize), (int)((plan.y * tilesize + v.y) / tilesize), plan.rotation, b) ;
                b.drawPlanRegion(b2, list);
            }
        }
    }

    /*
        @Override
        public TextureRegion[] icons(){
            Seq<TextureRegion> s = new Seq<>() ;
            for (Object o : turrets) {
                if (o instanceof Turret t) {
                    t.drawer.load(t);
                    s.add(t.icons()[1]) ;
                    if (t.icons().length > 2) {
                        s.add(t.icons()[2]);
                    }
                }
                else if (o instanceof Block b) {
                    TextureRegion r = b.variants > 0 ? Core.atlas.find(b.name + "1") : b.region;
                    s.addAll(b.teamRegion.found() && b.minfo.mod == null ? new TextureRegion[]{r, b.teamRegions[Team.sharded.id]} : new TextureRegion[]{r}) ;
                }
            }
            return s.toArray(TextureRegion.class) ;
        }
        */
    public class MultiBuild extends Building implements ControlBlock {
        public BlockUnitc unit = (BlockUnitc) UnitTypes.block.create(team);
        public ObjectMap<Payload, Vec2> pay = new ObjectMap<>() ;
        public Vec2 targetPos = new Vec2() ;
        public float logicControlTime = -1;
        public boolean logicShooting = false;
        @Override
        public void updateTile(){
            super.updateTile();
            for (Payload p : pay.keys()){
                if (p instanceof BuildPayload b) {
                    b.build.wasVisible = true ;
                    b.build.enabled = true ;
                    b.build.efficiency = efficiency ;
                    if (b.build.block.consumers.length == 0) {
                        b.build.enabled = efficiency > 0 ;
                    }
                }
                p.set(x + pay.get(p).x, y + pay.get(p).y, p instanceof BuildPayload b ? b.build.payloadRotation : 0);
                p.update(null, this);
                if (efficiency > 0 || (!shouldConsume())) {
                    if (p instanceof BuildPayload b && b.build.team != team) {
                        b.build.team = team;
                    }
                    if (p instanceof BuildPayload b && b.build instanceof ItemTurret.ItemTurretBuild i && b.build.block instanceof ItemTurret ii) {
                        for (Item it : ii.ammoTypes.keys()) {
                            if (items.has(it) && canDump(b.build, it) && b.build.acceptItem(this, it)) {
                                while (items.has(it) && i.acceptItem(this, it)) {
                                    b.build.handleItem(this, it);
                                    items.remove(it, ii.ammoPerShot);
                                }
                            }
                        }
                    }
                    if (p instanceof BuildPayload b && b.build.items != null) {
                        for (Item it : Vars.content.items()) {
                            if (items.has(it) && canDump(b.build, it) && b.build.acceptItem(this, it)) {
                                while (items.has(it) && b.build.acceptItem(this, it)) {
                                    b.build.handleItem(this, it);
                                    items.remove(it, 1);
                                }
                            }
                        }
                        if (b.build.block instanceof GenericCrafter g) {
                            if (g.outputItems != null) {
                                for (var output : g.outputItems) {
                                    if (b.build.items.get(output.item) > 0) {
                                        for (int i = 0; i < b.build.items.get(output.item); i++) {
                                            if (acceptItem(b.build, output.item) && b.build.canDump(this, output.item)) {
                                                handleItem(b.build, output.item);
                                                b.build.items.remove(output.item, 1);
                                            }
                                        }
                                    }
                                    if (items.get(output.item) > 0) {
                                        dump(output.item);
                                    }
                                }
                            }
                        }
                    }
                    if (p instanceof BuildPayload b && b.build.liquids != null) {
                        for (Liquid it : Vars.content.liquids()) {
                            if (liquids.hasFlowLiquid(it) && canDumpLiquid(b.build, it) && b.build.acceptLiquid(this, it)) {
                                while (liquids.hasFlowLiquid(it) && b.build.acceptLiquid(this, it)) {
                                    b.build.handleLiquid(this, it, 1);
                                    liquids.remove(it, 1);
                                }
                            }
                        }
                        if (b.build.block instanceof GenericCrafter g) {
                            if (g.outputLiquids != null) {
                                for (var output : g.outputLiquids) {
                                    if (b.build.liquids.get(output.liquid) > 0) {
                                        for (int i = 0; i < b.build.liquids.get(output.liquid); i++) {
                                            if (acceptLiquid(b.build, output.liquid) && b.build.canDumpLiquid(this, output.liquid)) {
                                                handleLiquid(b.build, output.liquid, output.amount);
                                                b.build.liquids.remove(output.liquid, 1);
                                            }
                                        }
                                    }
                                    if (liquids.get(output.liquid) > 0) {
                                        dumpLiquid(output.liquid);
                                    }
                                }
                            }
                        }
                    }
                    if (p instanceof BuildPayload b && b.build.power != null) {
                        b.build.power.graph.update();
                    }
                    targetPos = new Vec2(Vars.player.mouseX, Vars.player.mouseY);
                    if (p instanceof BuildPayload b && b.build instanceof Turret.TurretBuild t && isControlled() && isShooting()) {
                        try {
                            Method m = Turret.TurretBuild.class.getDeclaredMethod("turnToTarget", float.class);
                            m.setAccessible(true);
                            m.invoke(t, t.angleTo(targetPos));
                        } catch (Throwable t2) {
                            System.out.println(6);
                        }
                        try {
                            Method m = Turret.TurretBuild.class.getDeclaredMethod("updateShooting");
                            m.setAccessible(true);
                            m.invoke(t);
                        } catch (Throwable t2) {
                            System.out.println(6);
                        }
                    }
                }
            }
        }
        @Override
        public Building create(Block block, Team team){

            this.block = block;
            this.team = team;
            if (block.loopSound != Sounds.none) {
                sound = new SoundLoop(block.loopSound, block.loopSoundVolume);
            }
            health = block.health;
            maxHealth(block.health);
            timer(new Interval(block.timers));
            if (block.hasItems) items = new ItemModule();
            if (block.hasLiquids) liquids = new LiquidModule();
            if (block.hasPower) {
                power = new PowerModule();
                power.graph.add(this);
            }
            initialized = true;
            for (int i = 0, z = 1 ; i < turrets.size && z < turrets.size ; i += 2, z += 2){
                if (turrets.get(i) instanceof Block t && turrets.get(z) instanceof Vec2 v) {
//                    if (t instanceof Turret tu) {
//                        tu.drawer.load(tu);
//                        //System.out.println(((DrawTurret)tu.drawer).base.found());
//                    }
//                    if (t instanceof AbilityTurret tu) {
//                        tu.drawer.load(tu);
//                    }
                    pay.put(new BuildPayload(t, team){{
                        build.items = new ItemModule() ;
                        build.liquids = new LiquidModule() ;
                        build.enabled = build.wasVisible = true ;
//                        if (build.block.hasPower && power != null) {
//                            power.graph.add(build);
//                        }
                    }}, v);
//                if (t instanceof ItemTurret it){
//                    ammos.put(it, null) ;
//                }
                }
            }
            return this;
        }
        @Override
        public void draw(){
            super.draw();
            //Draw.alpha(0.5f);
            for (Payload p : pay.keys()){
                //p.draw();
                if (p instanceof BuildPayload b && (b.build instanceof Turret.TurretBuild || b.build instanceof AbilityTurret.AbilityTurretBuild || b.build instanceof WallTurret.WallTurretBuild)) {
                    float prevZ = Draw.z();
                    Draw.z(prevZ - 0.0001f);
                    Draw.z(prevZ);
                    Draw.zTransform(z -> z >= Layer.flyingUnitLow + 1f ? z : 0.0011f + Math.min(Mathf.clamp(z, prevZ - 0.001f, prevZ + 0.9f), Layer.flyingUnitLow - 1f));
                    b.build.tile = emptyTile;
                    Draw.alpha(0f);
                    b.build.payloadDraw();
                    Draw.zTransform();
                    Draw.z(prevZ);
                }
                else {
                    p.draw();
                }
            }
            //Draw.reset();
        }
        @Override
        public void drawSelect(){
            super.drawSelect();
            for (Payload p : pay.keys()){
                if (p instanceof BuildPayload b && (b.build instanceof Turret.TurretBuild || b.build instanceof AbilityTurret.AbilityTurretBuild || b.build instanceof WallTurret.WallTurretBuild)){
                    b.build.drawSelect();
//                    float prevZ = Draw.z();
//                    Draw.z(prevZ - 0.0001f);
//                    Draw.z(prevZ);
//                    Draw.zTransform(z -> z >= Layer.flyingUnitLow + 1f ? z : 0.0011f + Math.min(Mathf.clamp(z, prevZ - 0.001f, prevZ + 0.9f), Layer.flyingUnitLow - 1f));
//                    b.build.tile = emptyTile;
//                    Draw.alpha(0f);
//                    b.build.payloadDraw();
//                    Draw.zTransform();
//                    Draw.z(prevZ);
                }
                else if (p instanceof BuildPayload b) {
                    b.build.drawSelect();
                }
            }
        }
        @Override
        public boolean acceptItem(Building source, Item item){
//            boolean bb = false ;
//            for (TurretWeaponMount t : mounts){
//                if (t.weapon.ammoTypes.keys().toSeq().contains(item)){
//                    bb = true ;
//                }
//            }
//            return items.get(item) < itemCapacity && (bb || (ammoTypes.get(item) != null && totalAmmo + ammoTypes.get(item).ammoMultiplier <= maxAmmo));
            return items.get(item) < itemCapacity ;
        }
        @Override
        public boolean canControl(){
            return playerControllable && pay.keys().toSeq().contains(p -> p instanceof BuildPayload b && b.build instanceof Turret.TurretBuild);
        }
        @Override
        public Unit unit(){
            unit.tile(this);
            unit.team(team);
            return (Unit)unit;
        }
        public boolean isShooting(){
            return isControlled() && Core.input.keyDown(Binding.select) ;
        }
        @Override
        public float getPowerProduction(){
            float out = super.getPowerProduction() ;
            for (Payload p : pay.keys()) {
                if (p instanceof BuildPayload b) {
                    out += b.build.getPowerProduction() ;
                }
            }
            return out ;
        }
        @Override
        public void drawStatus() {
            super.drawStatus() ;
            for (Payload p : pay.keys()) {
                if (p instanceof BuildPayload b) {
                    b.build.drawStatus() ;
                }
            }
        }
        @Override
        public byte version(){
            return 1;
        }

        @Override
        public void write(Writes write){
            super.write(write);
//            for (Payload p : pay.keys()) {
//                if (p instanceof BuildPayload b) {
//                    b.build.write(write) ;
//                }
//            }
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
//            for (Payload p : pay.keys()) {
//                if (p instanceof BuildPayload b) {
//                    b.build.read(read, revision) ;
//                }
//            }
        }
    }
}
