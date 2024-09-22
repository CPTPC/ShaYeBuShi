package shayebushi;

import arc.*;
import arc.files.Fi;
import arc.func.*;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.input.InputDevice;
import arc.input.KeyCode;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Point2;
import arc.math.geom.Vec2;
import arc.scene.Element;
import arc.scene.event.Touchable;
import arc.scene.ui.CheckBox;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.Tooltip;
import arc.scene.ui.layout.Cell;
import arc.scene.ui.layout.Table;
import arc.struct.Bits;
import arc.struct.IntSeq;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.*;
//import mindustry.*;
//import mindustry.content.*;
//import jdk.jfr.Event;
import mindustry.Vars;
import mindustry.core.ContentLoader;
import mindustry.core.UI;
import mindustry.core.Version;
import mindustry.ctype.MappableContent;
import mindustry.ctype.UnlockableContent;
import mindustry.editor.MapResizeDialog;
import mindustry.entities.Damage;
import mindustry.entities.EntityGroup;
import mindustry.entities.EntityIndexer;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ContinuousBulletType;
import mindustry.entities.bullet.ExplosionBulletType;
import mindustry.entities.pattern.ShootMulti;
import mindustry.entities.units.AIController;
import mindustry.entities.units.WeaponMount;
import mindustry.game.*;
import mindustry.game.EventType.*;
//import mindustry.gen.*;
//import mindustry.mod.*;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.input.Binding;
import mindustry.io.SaveFileReader;
import mindustry.io.SaveVersion;
import mindustry.mod.Mod;
//import mindustry.ui.dialogs.*;
import mindustry.type.*;
import mindustry.type.unit.MissileUnitType;
import mindustry.ui.Bar;
import mindustry.ui.IntFormat;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.*;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.power.PowerGraph;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.blocks.sandbox.PowerSource;
import mindustry.world.consumers.Consume;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.modules.PowerModule;
import shayebushi.async.SYBSPhysicsProcess;
import shayebushi.entities.SYBSEntityGroup;
import shayebushi.entities.TimedWorldLabel;
import shayebushi.entities.abilities.FixBarAbility;
import shayebushi.entities.bullet.CircleBullet;
import shayebushi.entities.bullet.LineBullet;
import shayebushi.entities.bullet.TeShuExplosionBulletType;
import shayebushi.entities.units.*;
import shayebushi.entities.units.Ownerc;
import shayebushi.entities.weapons.TeShuWeapon;
import shayebushi.graphics.SYBSMinimapRenderer;
import shayebushi.type.unit.DuoJieTiUnitType;
import shayebushi.type.unit.SYBSUnitType;
import shayebushi.type.unit.YuLeiUnitType;
import shayebushi.ui.SYBSCoreItemsDisplay;
import shayebushi.ui.dialogs.SYBSContentInfoDialog;
import shayebushi.ui.dialogs.SYBSPlanetDialog;
import shayebushi.ui.dialogs.SYBSResearchDialog;
import shayebushi.world.blocks.ZhaDanBlock;
import shayebushi.world.blocks.defense.FangFuSheLiChang;
import shayebushi.world.blocks.defense.turrets.AbilityContinuousTurret;
import shayebushi.world.blocks.power.DianYaBlock;
import shayebushi.world.blocks.power.DianYaNode;
import shayebushi.world.consumers.ConsumeCiChang;
import shayebushi.world.consumers.ConsumeDianYa;
import shayebushi.world.consumers.ConsumeFuShe;
import shayebushi.world.sandbox.DianYaSoure;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static arc.Core.keybinds;
import static arc.Core.settings;
import static arc.util.Time.toSeconds;
import static mindustry.Vars.*;
import static shayebushi.SYBSBlocks.zhadans;
import static shayebushi.SYBSUnitTypes.*;
import static shayebushi.world.blocks.defense.FangFuSheLiChang.cichangqiangduChangeEvent;

public class ShaYeBuShi extends Mod implements SaveFileReader.CustomChunk {
    public static int I = 1 ;
    public static int II = 0 ;
    public static int III = 0 ;
    Seq<StatusEffect> effects = Vars.content.statusEffects() ;
    UnitType unitType ;
    PlanetDialog p ;
//    public static Font f ;
    public static BaseDialog welcomeDialog ;
    public static Rand r = new Rand() ;
    public static Seq<CircleBullet> circleBulletTypes = new Seq<>() ;
    public static Seq<Unit> controlingUnits = new Seq<>() ;
    public static Seq<Integer> controlingUnitIds = new Seq<>() ;
    public static int clickTimer = 0, clickTime = 10 ;
    public static Seq<Seq<Vec2>> pucongweis = new Seq<>() ;
    public static int launchTimes = 0 ;
    public static Fi data ;
    public static Fi launchTimesCounter;
    public static Seq<Tile> fusheTiles = new Seq<>() ;
    public static Seq<Tile> fushiTiles = new Seq<>() ;
    public static int maxCiChangQiangDu = 50 ;
    public static int fangFuSheCiChangQiangDu = 25 ;
    public static float playTime = 0 ;
    public static Fi playTimeCounter ;
    public static boolean showDialog = true ;
    public static Fi showDialogFi;
    //public static String[] threats = {"low", "medium", "high", "extreme", "extreme", "eradication", "eradication", "siji", "siji", "siji", "gengu", "jixian"} ;
    public static String[] threats = {"low", "medium", "high", "extreme", "eradication", "siji", "gengu", "jixian"} ;
    public static BaseDialog anquanshengming ;
    public static float zengyuanjishi = 0 ;
    public static int zengyuanboci = 1 ;
    public static boolean inited = false ;
    public static int timer = 0 ;
    public static Unit current ;
    public static boolean lianyu = false ;
    public static boolean tiaoshi = false ;
    public static String[] nandus = {"tiaoshi", "jiandan", "zhengchang", "kunnan", "lianyu"} ;
    public static Fi nanduFi;
    public static int nandu ;
    public static float minimapUnitSize = 1f ;
    public static ObjectMap<Block, Float> fangfushis = new ObjectMap<>() ;
    public static ObjectMap<Block, Float> fangfushes = new ObjectMap<>() ;
    //public static Seq<FangFuSheLiChang.FangFuSheliChangBuild> fangfusheBuilds = new Seq<>() ;
    public static Seq<ObjectMap<Unit, Float>> beilvs = new Seq<>() ;
    public static int shuxings = 5 ;
    public static String[] shuxing = {"shuxing.maxHealth", "shuxing.heal", "shuxing.armor", "shuxing.speed", "shuxing.damage"} ;
    static
    {
        for (int i = 1 ; i <= 33 ; i ++) {
            circleBulletTypes.add(new CircleBullet(i * 0.2f, tilesize, tilesize, 240f, 1)) ;
        }
        for (int i = 0 ; i <= 40 ; i ++) {
            Seq<Vec2> s = new Seq<>() ;
            for (int z = 0 ; z < i ; z ++) {
                s.add(circle(360f / i * z, i * tilesize, 0, 0)) ;
                //if (i == 3) {
                    //fixedPrintln(" ", s.get(z).x, s.get(z).y) ;
                //}
            }
            pucongweis.add(s) ;
        }
        settings.setAppName(appName);
        data = Core.settings.getDataDirectory().child("saves/").child("shayebushi/") ;
        launchTimesCounter = data.child("launchTimesCounter.dat") ;
        playTimeCounter = data.child("playTimeCounter.dat") ;
        showDialogFi = data.child("showDialogFi.dat") ;
        nanduFi = data.child("nandu.dat") ;
        for (int i = 0 ; i < shuxings ; i ++) {
            beilvs.add(new ObjectMap<>()) ;
        }
    }
//    public static SettingsMenuDialog.SettingsTable sybsSettings = new SettingsMenuDialog.SettingsTable();

    public ShaYeBuShi(){
        SaveVersion.addCustomChunk("ShaYeBuShi", this);
        try {
            launchTimes = Integer.parseInt(launchTimesCounter.readString()) ;
            playTime = Float.parseFloat(playTimeCounter.readString()) ;
            //System.out.println(showDialogFi.readString());
            showDialog = Boolean.parseBoolean(showDialogFi.readString()) ;
            tiaoshi = Boolean.parseBoolean(nanduFi.readString().split(" ")[0]) ;
            lianyu = Boolean.parseBoolean(nanduFi.readString().split(" ")[1]) ;
            nandu = Integer.parseInt(nanduFi.readString().split(" ")[2]) ;
        }catch (Throwable t) {
            launchTimesCounter.writeString("0");
            launchTimes = Integer.parseInt(launchTimesCounter.readString()) ;
            playTimeCounter.writeString("0");
            playTime = Float.parseFloat(playTimeCounter.readString()) ;
            showDialogFi.writeString("true");
            showDialog = Boolean.parseBoolean(showDialogFi.readString()) ;
            nanduFi.writeString("false false 3");
        }
        launchTimes ++ ;
        launchTimesCounter.writeString(Integer.toString(launchTimes));
        r.setState(launchTimes, System.currentTimeMillis());
        Log.info("Loaded Mod constructor.");
//        Core.assets.load("default", Font.class, new FreetypeFontLoader.FreeTypeFontLoaderParameter("fonts/font.woff", new FreeTypeFontGenerator.FreeTypeFontParameter() {{
//            size = 50;
//            shadowColor = Color.darkGray;
//            shadowOffsetY = 2;
//            incremental = true;
//        }})).loaded = ff -> f = ff;
//        Label.LabelStyle l = new Label.LabelStyle() {{
//            font = f;
//        }};
        //listen for game load event
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {

                welcomeDialog = new BaseDialog(Core.bundle.get("shayebushi.welcome"));
                welcomeDialog.cont.add("behold").row();
                welcomeDialog.cont.pane(inner -> {
                            inner.pane(table -> {
                                table.pane(p -> {
                                    p.add("[accent]<<<ShaYeBuShi by CPTPC&ht6667>>>", Styles.techLabel).row();
                                    p.add("[accent]<[white]ShaYeBuShi[accent]>", Styles.defaultLabel)
                                            //.size(2500, 500)
                                            .row();
                                    p.add("[accent]Made in China", Styles.defaultLabel).row();
                                }).fillY().growX().row();
//                                table.add("").row();
                            }).growX().center().row();
                        });
                //mod sprites are prefixed with the mod name (this mod is called 'example-java-mod' in its config)
                welcomeDialog.cont.image(Core.atlas.find("shayebushi-logo")).pad(20f).row();
                welcomeDialog.cont.button("I see", welcomeDialog::hide).size(100f, 50f);
                welcomeDialog.show();
                anquanshengming = new BaseDialog(Core.bundle.get("shayebushi.anquanshengming"));
                anquanshengming.cont.add(Core.bundle.get("shayebushi.shengming")).wrap().fillX().padLeft(10).width(1050).left().row() ;
                anquanshengming.cont.add(Core.bundle.get("shayebushi.zuozhe")).wrap().fillX().padLeft(10).width(1050).left().row() ;
                anquanshengming.cont.add(Core.bundle.get("shayebushi.shengming1")).wrap().fillX().padLeft(10).width(1050).left().row() ;
                anquanshengming.cont.add(Core.bundle.get("shayebushi.shengming2")).wrap().fillX().padLeft(10).width(1050).left().row() ;
                anquanshengming.cont.add(Core.bundle.get("shayebushi.shengming3")).wrap().fillX().padLeft(10).width(1050).left().row() ;
                anquanshengming.cont.add(Core.bundle.get("shayebushi.shengming4")).wrap().fillX().padLeft(10).width(1050).left().row() ;
                anquanshengming.cont.add(Core.bundle.get("shayebushi.shengming5")).wrap().fillX().padLeft(10).width(1050).left().row() ;
                anquanshengming.cont.add(Core.bundle.get("shayebushi.shengming6")).wrap().fillX().padLeft(10).width(1050).left().row() ;
                anquanshengming.cont.add(Core.bundle.get("shayebushi.shengming7")).wrap().fillX().padLeft(10).width(1050).left().row() ;
                anquanshengming.check(Core.bundle.get("shayebushi.show"), b -> {
                    showDialog = !b ;
                    showDialogFi.writeString(Boolean.toString(showDialog));
                }).left().row() ;
                anquanshengming.addCloseButton();
                if (showDialog) {
                    anquanshengming.show();
                }
            });
        });
        genModFile();
        /*
        Events.on(EventType.UnitDamageEvent.class , event->{
            if (event.unit.hasEffect(SYBSStatusEffects.zhongdufushi)){
                if (I > 16){
                    I = 4 ;
                }
                else {
                    I *= 4;
                }
                event.unit.damage(event.bullet.damage * I - 1);
                //event.unit.damage(60);
                //event.unit.speedMultiplier = 0.5f ;
                //event.unit.reloadMultiplier = 0.9f ;
            }
            else if (!event.unit.hasEffect(SYBSStatusEffects.zhongdufushi)) {
                for (StatusEffect s : effects) {
                    if (event.unit.hasEffect(s)) {
                        II ++ ;
                    }
                }
                if (II == 0){
                    event.unit.reloadMultiplier = 1 ;
                    event.unit.maxHealth = event.unit.type.health ;
                    event.unit.speedMultiplier = 1 ;
                    event.unit.damageMultiplier = 1;
                }
            }
        });
        Events.on(EventType.UnitDamageEvent.class , event->{
            if (event.unit.hasEffect(SYBSStatusEffects.dangji)) {
                event.unit.reloadMultiplier(0);
                event.unit.speedMultiplier(0) ;
                event.unit.speedMultiplier = 0 ;
                event.unit.reloadMultiplier = 0 ;
            }
        });
        Events.on(EventType.UnitDamageEvent.class , event->{
            if (event.unit.hasEffect(SYBSStatusEffects.diaoling)) {
                event.unit.healthMultiplier(0.2f);
                event.unit.damageMultiplier(0.2f) ;
                event.unit.healthMultiplier = 0.2f ;
                event.unit.damageMultiplier = 0.2f ;
            }
        });
        Events.on(EventType.UnitDamageEvent.class , event->{
            int IV = 0 ;
            Seq<Unit> s = new Seq<>() ;
            Tmp.v1.trns(event.unit.rotation - 90, event.unit.x, event.unit.y).add(event.unit.x, event.unit.y);
            float rx = Tmp.v1.x, ry = Tmp.v1.y;
            Units.nearby(event.unit.team, rx, ry, Integer.MAX_VALUE, other -> {
                if ((other.type == SYBSShenShengUnitTypes.haojieyijieduan || other.type == SYBSShenShengUnitTypes.haojieerjieduan || other.type == SYBSShenShengUnitTypes.haojiesanjieduan) && other.team != event.unit.team){
                    s.add(other) ;
                }
            });
            IV = s.size ;
            if (event.unit.hasEffect(SYBSStatusEffects.huimiesuyi) && IV > 0){
                event.unit.damage(2700);
                event.unit.abilities(new Ability[]{});
                for (WeaponMount m :event.unit.mounts){
                    m.weapon.bullet.status = StatusEffects.none ;
                }
                UnitType type = event.unit.type ;
                type.immunities.clear();
                unitType = UnitTypes.dagger ;
                event.unit.type = type ;
                event.unit.abilities(new Ability[]{});
            }
            else if (event.unit.hasEffect(SYBSStatusEffects.huimiesuyi) && unitType!= null && IV == 0) {
//                unitType.weapons.clear() ;
//                unitType.weapons = UnitTypes.dagger.weapons ;
                event.unit.type = unitType ;
                event.unit.maxHealth = UnitTypes.dagger.health ;
            }
        });
         */
        Events.on(EventType.ContentInitEvent.class, event -> {
            Seq<StatusEffect> s = content.statusEffects() ;
            Seq<StatusEffect> ss = s.select(ShaYeBuShi::isJianYi) ;
            for (UnitType u : SYBSShenShengUnitTypes.shenshengjidanwei){
                u.immunities.addAll(ss);
            }
            for (UnitType u : shendijidanwei){
                u.immunities.addAll(ss);
            }
            cptpc.immunities.addAll(ss);
            for (UnitType u : content.units()){
                u.hidden = u.hidden ? !(Version.build == -1 || tiaoshi) : u.hidden ;
//                if (u.minfo.mod != null && u.minfo.mod.name.equals("flameout")) {
//                    content.units().remove(u) ;
//                }
                if (!(u instanceof SYBSUnitType)) {
                    u.abilities.add(new FixBarAbility());
                }
                if (u.getFirstRequirements() != null) {
                    for (ItemStack i : u.getFirstRequirements()) {
                        applyImm(u, i);
                    }
                }
                /*
                float dps = 0 ;
                for (Weapon w : u.weapons) {
                    w.display = true ;
                    float shesu = toSeconds / w.reload * w.shoot.shots ;
//                    System.out.println(u.name + " " + (w.bullet.damage + w.bullet.splashDamage + fenlieDps(w.bullet) + (w.bullet.spawnUnit != null ? w.bullet.spawnUnit.weapons.get(0).bullet.damage + w.bullet.spawnUnit.weapons.get(0).bullet.splashDamage : 0)) * shesu);
                    dps += dps(w.bullet) * shesu ;
                    //u.load();
                    //w.load() ;
                    //System.out.println(u.localizedName + " " + w.name + " " + (Core.atlas.find(w.name).found()));
//                    if (!w.region.found()) {
//                        w.region = u.region ;
//                    }
                }
                */
                u.stats.add(SYBSStats.dps, DPS(u) + (u instanceof DuoJieTiUnitType d ? DPS(d.jietitype) * d.jietiAmount : 0));
                u.stats.add(SYBSStats.kongzhishangxian, Math.min((int)(u.hitSize / tilesize * 3), 40)) ;
            }
            //System.out.println(content.blocks().size);
            for (Item i : content.items()) {
                i.hidden = i.hidden ? !(Version.build == -1 || tiaoshi) : i.hidden ;
            }
            for (Liquid i : content.liquids()) {
                i.hidden = i.hidden ? !(Version.build == -1 || tiaoshi) : i.hidden ;
            }
            for (Block b : content.blocks()){
                if (b.buildVisibility == BuildVisibility.debugOnly) {
                    b.buildVisibility = SYBSBuildVisibility.tiaoshiOnly ;
                }
                b.addBar("health", entity -> new Bar(()-> Core.bundle.format("bar.health", Math.max(entity.health, 0), Math.max((int)(entity.healthf() * 100), 0) + "%"), () -> Pal.health, entity::healthf).blink(Color.white));
                if (b instanceof PowerNode p && b != SYBSBlocks.bianyaqi) {
                    /*
                    removeContent(b);
                    b.load() ;
                    b.loadIcon();
                    Block b2 = b ;
                    clone(b, new DianYaNode(b.name)) ;
                    b2.buildType = null ;
                    invokePrivateMethod(b2, "initBuilding", new Seq<>());
                    //b.region = Core.atlas.find(b.name);
                    */
                    p.configurations.remove(Integer.class) ;
                    p.configurations.remove(Point2[].class) ;
                    p.config(Integer.class, (entity, value) -> {
                        PowerModule power = entity.power;
                        Building other = world.build(value);
                        boolean contains = power.links.contains(value), valid = other != null && other.power != null;

                        if(contains){
                            //unlink
                            power.links.removeValue(value);
                            if(valid) other.power.links.removeValue(entity.pos());

                            PowerGraph newgraph = new PowerGraph();

                            //reflow from this point, covering all tiles on this side
                            newgraph.reflow(entity);

                            if(valid && other.power.graph != newgraph){
                                //create new graph for other end
                                PowerGraph og = new PowerGraph();
                                //reflow from other end
                                og.reflow(other);
                            }
                        }else if(p.linkValid(entity, other) && valid && power.links.size < p.maxNodes && !(other instanceof DianYaBlock.DianYaBuild) && !(other.block.consPower instanceof ConsumeDianYa)){

                            power.links.addUnique(other.pos());

                            if(other.team == entity.team){
                                other.power.links.addUnique(entity.pos());
                            }

                            power.graph.addGraph(other.power.graph);
                        }
                    });

                    p.config(Point2[].class, (tile, value) -> {
                        IntSeq old = new IntSeq(tile.power.links);

                        //clear old
                        for(int i = 0; i < old.size; i++){
                            p.configurations.get(Integer.class).get(tile, old.get(i));
                        }

                        //set new
                        for(Point2 pp : value){
                            p.configurations.get(Integer.class).get(tile, Point2.pack(pp.x + tile.tileX(), pp.y + tile.tileY()));
                        }
                    });
                }

                if (b instanceof Turret) {
                    if (b instanceof PowerTurret t) {
                        float dps = 0 ;
                        float shesu = toSeconds / t.reload * (t.shoot instanceof ShootMulti s2 ? s2.source.shots : t.shoot.shots) ;
                        dps += dps(t.shootType) * shesu ;
                        b.stats.add(SYBSStats.dps, dps);
                    }
                    else if (b instanceof ItemTurret t) {
                        float dps = 0 ;
                        float shesu = toSeconds / t.reload * (t.shoot instanceof ShootMulti s2 ? s2.source.shots : t.shoot.shots) ;
                        for (Item i : t.ammoTypes.keys()) {
                            dps = Math.max(dps(t.ammoTypes.get(i)) * shesu, dps) ;
                        }
                        b.stats.add(SYBSStats.dps, dps);
                    }
                    else if (b instanceof ContinuousTurret t) {
                        float dps = 0 ;
                        float shesu = false ? toSeconds / t.reload * (t.shoot instanceof ShootMulti s2 ? s2.source.shots : t.shoot.shots) : 60 ;
                        dps += dps(t.shootType) * shesu ;
                        b.stats.add(SYBSStats.dps, dps);
                    }
                    else if (b instanceof LaserTurret t) {
                        float dps = 0 ;
                        float shesu = toSeconds / t.reload * (t.shoot instanceof ShootMulti s2 ? s2.source.shots : t.shoot.shots) ;
                        dps += dps(t.shootType) * shesu ;
                        b.stats.add(SYBSStats.dps, dps);
                    }
                    else if (b instanceof AbilityContinuousTurret t) {
                        float dps = 0 ;
                        float shesu = false ? toSeconds / t.reload * (t.shoot instanceof ShootMulti s2 ? s2.source.shots : t.shoot.shots) : 60 ;
                        dps += dps(t.shootType) * shesu ;
                        b.stats.add(SYBSStats.dps, dps);
                    }
                }
                for (Consume c : b.consumers) {
                    if (c instanceof ConsumeDianYa cdy) {
                        b.removeBar("power");
                        String[] st = new String[]{"power2", "diyadian", "zhongyadian", "gaoadian"} ;
                        String[] sst = new String[]{"bar.poweramount", "bar.diyadianamount", "bar.zhongyadianamount", "bar.gaoyadianamount"} ;
                        String[] ssst = new String[]{"bar.power", "bar.diyadianliang", "bar.zhongyadianliang", "bar.gaoyadianliang"} ;
                        Block finalB1 = b;
                        Block finalB2 = b;
                        b.addBar(st[cdy.dianya], entity -> new Bar(
                                () -> finalB1.consPower.buffered ? Core.bundle.format(sst[cdy.dianya], Float.isNaN(entity.power.status * finalB1.consPower.capacity) ? "<ERROR>" : UI.formatAmount((int)(entity.power.status * finalB1.consPower.capacity))) :
                                        Core.bundle.get(ssst[cdy.dianya]),
                                () -> Pal.powerBar,
                                () -> {
                                    /*
                                    DianYaModule d = entity.power instanceof DianYaModule dy ? dy : null ;
                                    if (d == null) {
                                        return 0 ;
                                    }
                                    Seq<Building> sq = entity.power.graph.producers.select(bu -> !(bu.power instanceof DianYaModule dd && dd.dianya == d.dianya)) ;
                                    int i = entity.power.graph.producers.count(bu -> !(bu.power instanceof DianYaModule dd && dd.dianya == d.dianya)) ;
                                    //System.out.println(b.size + " " + build.power.graph.producers.size);
                                    boolean bb = sq.size == 0 || entity.power.graph.producers.size > i;
                                    if (!bb) {
                                        return 0 ;
                                    }
                                    */
                                    float f = Mathf.zero(finalB2.consPower.requestedPower(entity)) && entity.power.graph.getPowerProduced() + entity.power.graph.getBatteryStored() > 0f ? 1f : entity.power.status ;
                                    //System.out.println(f + " " + (Mathf.zero(b.consPower.requestedPower(entity)) && entity.power.graph.getPowerProduced() + entity.power.graph.getBatteryStored() > 0));
                                    return f ;
                                })
                        );
                    }
                    else if (c instanceof ConsumeCiChang ccc) {
                        b.addBar("cichangqiangdu", bud -> {
                            Building budi = null;
                            Building budig = null;
                            for (Team te : Team.baseTeams) {
                                if (budi == null) {
                                    budi = Units.closestBuilding(te, bud.x, bud.y, Integer.MAX_VALUE, bu -> bu instanceof FangFuSheLiChang.FangFuSheliChangBuild fan && bu.efficiency > 0 && bu.block instanceof FangFuSheLiChang ff && bu.dst(bu) < fan.radius());
                                }
                                if (budig == null) {
                                    budig = Units.closestBuilding(te, bud.x, bud.y, Integer.MAX_VALUE, bu -> bu instanceof FangFuSheLiChang.FangFuSheliChangBuild fan && bu.efficiency > 0 && bu.block instanceof FangFuSheLiChang ff && bu.dst(bu) < fan.radius() && fan.cichangqiangdu >= maxCiChangQiangDu);
                                }
                            }
                            Building finalB = budi ;
                            Building finalBud = budig ;
                            return new Bar(Core.bundle.format("cichang.qiangdu", budig != null ? Core.bundle.get("cichang.guozai") : budi != null ? ((FangFuSheLiChang.FangFuSheliChangBuild)budi).cichangqiangdu : 0), Pal.place, () -> {
                                return (finalBud != null ? 1 : finalB != null ? ((FangFuSheLiChang.FangFuSheliChangBuild)finalB).cichangqiangdu / (ccc.cichangqiangdu * 1f) : 0) ;
                            }) ;
                        }) ;
                    }
                }
            }
            //System.out.println(content.blocks().size);
            //ui.settings.game.sliderPref("shijianliusu", 1, 1, 32, i -> (int)(i/4f * 100f) + "%");
        });
        Events.on(EventType.BlockBuildEndEvent.class, event -> {
            if (!event.breaking && event.tile.build != null){
                /*
                int d = 0 ;
                boolean have = false ;
                for (Consume c : event.tile.build.block.consumers) {
                    if (c instanceof ConsumeDianYa dd){
                        have = true ;
                        d = dd.dianya ;
                        break ;
                    }
                }
                if (have) {
                    int finalD = d;
                    event.tile.build.power(new DianYaModule(finalD));
                    event.tile.build.power.graph.add(event.tile.build);
                    DianYaNode.getNodeLinks(event.tile, event.tile.build.block, player.team(), other -> {
                        DianYaNode node = (DianYaNode)other.block;
                        Draw.color(node.laserColor1, Renderer.laserOpacity * 0.5f);
                        node.drawLaser(event.tile.x * tilesize + event.tile.build.block.offset, event.tile.y * tilesize + event.tile.build.block.offset, other.x, other.y, event.tile.build.block.size, other.block.size);

                        Drawf.square(other.x, other.y, other.block.size * tilesize / 2f + 2f, Pal.place);
                    });
                    boolean b = false ;
                    try {
                        Field f = Building.class.getDeclaredField("initialized") ;
                        f.setAccessible(true);
                        b = (boolean)f.get(event.tile.build) ;
                    }catch (Throwable t) {
                        t.printStackTrace();
                    }
                    if (b) {
                        event.tile.build.power.init = false;
                        new DianYaGraph(finalD).add(event.tile.build);
                    }
                }
                */
                if (event.tile.floor().liquidDrop instanceof SYBSLiquids.SYBSLiquid sl && sl.fushi > 0) {
                    fushiTiles.add(event.tile);
                }
//                if (event.tile.build instanceof FangFuSheLiChang.FangFuSheliChangBuild f) {
//                    fangfusheBuilds.add(f) ;
//                }
            }
            if (event.breaking) {
                if (fushiTiles.contains(event.tile)) {
                    fushiTiles.remove(event.tile);
                }
//                if (event.tile.build instanceof FangFuSheLiChang.FangFuSheliChangBuild f && fangfusheBuilds.contains(f)) {
//                    fangfusheBuilds.remove(f);
//                    for (int x = f.tile.x - f.radius() / tilesize ; x <= f.tile.x + f.radius() / tilesize ; x ++) {
//                        for (int y = f.tile.y - f.radius() / tilesize ; y <= f.tile.y + f.radius() / tilesize ; y ++) {
//                            if (world.tile(x, y).overlay() instanceof OreBlock o && o.itemDrop.radioactivity > 0) {
//                                fusheTiles.add(world.tile(x, y)) ;
//                            }
//                        }
//                    }
//                }
            }
        });
        /*
        Events.on(EventType.UnitDamageEvent.class,event -> {
            boolean b = false ;
            float range = 0 ;
            float amount = 0 ;
            for (Ability a : event.unit.type.abilities){
                if (a instanceof XiuJiLingYuChangAbility x){
                    b = true ;
                    range = x.range ;
                    amount = x.amount ;
                }
            }
            Seq<Unit> units = new Seq<>() ;
            Units.nearby(null, event.unit.x, event.unit.y, range, other ->{
                if (other.team != event.unit.team){
                    units.add(other) ;
                }
            });
            if (event.unit.type == xiuji && event.bullet.owner instanceof Unit u && units.contains(u)){
                u.damage(event.bullet.damage * amount * beilv);
            }
        });
        */
        Events.on(FangFuSheLiChang.CiChangQiangDuChangeEvent.class, e -> {
            handleFangFuShe(e.build, (t, ccqd) -> {
                if (t.overlay() instanceof OreBlock o && o.itemDrop.radioactivity > 0) {
                    float fusheqiangdu = ccqd * (o.itemDrop instanceof SYBSItems.SYBSItem s ? s.fusheqiangdu : 1) ;
                    //System.out.println(x + " " + y + " " + fusheqiangdu);
                    if (e.to < fusheqiangdu && !fusheTiles.contains(t)) {
                        fusheTiles.add(t) ;
                    }
                    else if (e.to >= fusheqiangdu && fusheTiles.contains(t)) {
                        fusheTiles.remove(t) ;
                    }
                }
            });
        }) ;
        Events.run(Trigger.update, () -> {
            if (Core.input.keyDown(KeyCode.f12)) {
                for (Unit u : Groups.unit) {
                    System.out.println(u.type.localizedName);
                }
            }
            if (!(Groups.unit instanceof SYBSEntityGroup)) {
                EntityIndexer ei = (EntityIndexer) getPrivateField(Groups.unit, "indexer") ;
                Groups.unit = new SYBSEntityGroup<>(mindustry.gen.Unit.class, true, true, ei);
                ei = (EntityIndexer) getPrivateField(Groups.all, "indexer") ;
                Groups.all = new SYBSEntityGroup<>(mindustry.gen.Entityc.class, false, false, ei);
                ei = (EntityIndexer) getPrivateField(Groups.sync, "indexer") ;
                Groups.sync = new SYBSEntityGroup<>(mindustry.gen.Syncc.class, false, true, ei);
                ei = (EntityIndexer) getPrivateField(Groups.draw, "indexer") ;
                Groups.draw = new SYBSEntityGroup<>(mindustry.gen.Drawc.class, false, false, ei);
            }
            for (Unit u : Groups.unit) {
                for (WeaponMount wm : u.mounts) {
                    if (wm.weapon instanceof TeShuWeapon t && t.checkTarget(u, wm.target, u.x, u.y, t.range())) {
                        wm.target = null ;
                    }
                }
            }
            for (int i = 0 ; i < shuxings ; i ++) {
                float f = nandu == 2 ? -0.25f : nandu == 4 ? 1.5f : nandu == 5 ? 3 : -1 ;
                if (f == -1) break ;
                for (Unit u : Groups.unit) {
                    if (u.team != state.rules.waveTeam) return ;
                    ObjectMap<Unit, Float> b = beilvs.get(i) ;
                    switch (i) {
                        case 0, 2 -> {
                            b.put(u, f * 10) ;
                        }
                        case 3 -> {
                            b.put(u, f) ;
                        }
                    }
                }
            }
            for (int i = 0 ; i < shuxings ; i ++) {
                ObjectMap<Unit, Float> b = beilvs.get(i) ;
                for (Unit u : b.keys()) {
                    switch (i) {
                        case 0 -> {
                            if (b.get(u) != 0) {
                                float f = Math.min(u.health, u.maxHealth) / u.maxHealth ;
                                u.maxHealth = u.type.health * (1 + b.get(u) / 100);
                                u.health = u.maxHealth * f;
                            }
                        }
                        case 1 -> {
                            if (b.get(u) > 0) {
                                u.heal(b.get(u) / toSeconds);
                            }
                        }
                        case 2 -> {
                            u.armor = u.type.armor + b.get(u) ;
                        }
                        case 3 -> {
                            u.speedMultiplier += b.get(u) ;
                        }
                        case 4 -> {
                            for (WeaponMount wm : u.mounts) {
                                Weapon w = wm.weapon.copy() ;
                                BulletType bt = w.bullet.copy() ;
                                bt.damage += 100 ;
                                w.bullet = bt ;
                                setPrivateField(wm, "weapon", w);
                            }
                        }
                    }
                }
            }
            if (Core.settings.getBool(Core.bundle.get("sybssets.youjian")) && Core.input.keyDown(KeyCode.mouseRight) && clickTimer >= clickTime && !Core.input.keyDown(Binding.command_mode)) {
                var ref = new Object() {
                    boolean found = false ;
                };
                Units.nearby(null, player.mouseX, player.mouseY, tilesize, u -> {
                    current = u ;
                    ref.found = true ;
                });
                if (!ref.found) {
                    current = null ;
                }
                clickTimer = 0 ;
            }
            if (current != null && current.health <= 0) {
                current = null ;
            }
            playTime += Time.delta ;
            playTimeCounter.writeString(Float.toString(playTime));
            if (player.unit() != null && !(player.unit() instanceof BlockUnitUnit)) {
                clickTimer += Time.delta ;
                if (state.isPlaying() && Core.input.keyDown(SYBSBinding.kongzhi) && clickTimer >= clickTime) {
                    //System.out.println(firstClick);
                    if (controlingUnits.isEmpty()) {
                        Units.nearby(player.unit().team, player.unit().x, player.unit().y, player.unit().type.range, u -> {
                            //fixedPrintln(" ", controlingUnits.size, player.unit().hitSize / tilesize * 3, controlingUnits.size < (int)(player.unit().hitSize / tilesize) * 3);
                            if (u != null && !controlingUnits.contains(u) && controlingUnits.size < Math.min((int)(player.unit().hitSize / tilesize * 3), 40) && u.hitSize <= player.unit().hitSize && !(u.type instanceof MissileUnitType) && !(u.type instanceof YuLeiUnitType) && u.isCommandable() && !(u instanceof JieTic) && !(u instanceof Ownerc && ((Ownerc) u).owner() != null) && bothType(u, player.unit()) && u != player.unit() && !u.isPlayer()) {
                                controlingUnits.add(u);
                                controlingUnitIds.add(u.id);
                            }
                        });
                        SYBSFx.kongzhiguanghuan.at(player.unit().x, player.unit().y, 0, player.unit()) ;
                    }
                    else {
                        controlingUnits.clear();
                        controlingUnitIds.clear();
                    }
                    clickTimer = 0 ;
                }
                for (Unit u : controlingUnits) {
                    if (u != null && player.unit() != Nulls.unit) {
                        if (u.dead) {
                            controlingUnits.remove(u) ;
                            controlingUnitIds.remove((Integer)u.id) ;
                        }
                        if (u.controller() instanceof AIController a && controlingUnits.contains(u)
                                && u.dst(pucongweis.get(Math.min((int)(player.unit().hitSize / tilesize * 3), 40)).get(controlingUnits.indexOf(u)).cpy().add(player.unit())) > u.hitSize * 0.5f
                        ) {
                            //System.out.println((int)(player.unit().hitSize / tilesize) + " " + controlingUnits.indexOf(u));
                            a.moveTo(pucongweis.get(Math.min((int)(player.unit().hitSize / tilesize * 3), 40)).get(controlingUnits.indexOf(u)).cpy().add(player.unit()), 0) ;
                            //fixedPrintln(controlingUnits.indexOf(u));
                        }
                        if (player.unit().mounts().length > 0 && player.unit().mounts[0].shoot) {
                            for (WeaponMount w : u.mounts) {
                                if (player.unit().mounts[0].target != null) {
                                    w.target = player.unit().mounts[0].target;
                                }
                                w.aimX = player.unit().aimX();
                                w.aimY = player.unit().aimY();
                                w.shoot = true;
                                u.lookAt(player.unit().aimX(), player.unit().aimY());
                                w.weapon.update(u, w);
                            }
                        }
                        if (player.unit().isBuilding()) {
                            u.plans().add(player.unit().buildPlan());
                        }
                        SYBSFx.kongzhi.at(u.x, u.y, 0, u);
                    }
                }
            }
            if (!inited) {
                if (control.saves.getCurrent() != null) {
                    try {
                        for (Fi f2 : data.child("saves").list()) {
                            for (Fi f3 : f2.list()) {
                                Saves.SaveSlot s = control.saves.getSaveSlots().find(s2 -> f3.name().contains(s2.file.name()));
                                if (s == null && f3.name().equals(".msav")) {
                                    f3.delete();
                                }
                            }
                        }
                        Fi f = data.child("saves").child(control.saves.getCurrent().file.name()) ;
                        Fi f2 = f.child(control.saves.getCurrent().file.name() + ".dat") ;
                        /*
                        InputStream is = new InflaterInputStream(f.read(bufferSize)) ;
                        CounterInputStream counter = new CounterInputStream(is);
                        DataInputStream stream2 = new DataInputStream(counter) ;
                        zengyuanboci = stream2.readInt() ;
                        zengyuanjishi = stream2.readFloat() ;
                        */
                        String s = f2.readString();
                        zengyuanboci = Integer.parseInt(s.split(" ")[0]);
                        zengyuanjishi = Float.parseFloat(s.split(" ")[1]);
                        current = s.split(" ")[2].equals("none") ? null : Groups.unit.getByID(Integer.parseInt(s.split(" ")[2])); ;
                        Fi f1 = f.child("units") ;
                        for (Fi f3 : f1.list()) {
                            String s2[] = f3.readString().split(" ") ;
                            for (int i = 0 ; i < shuxings ; i ++) {
                                Unit u = Groups.unit.getByID(Integer.parseInt(f3.nameWithoutExtension())) ;
                                if (u != null) {
                                    beilvs.get(i).put(u, Float.parseFloat(s2[i]));
                                }
                                else {
                                    f3.delete() ;
                                }
                            }
                        }
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                    inited = true ;
                }
            }
            if (!state.isPaused() && !state.isEditor()) {
                if (hasZengYuan() && zengyuanboci <= zengYuanBoCi()) {
                    zengyuanjishi += Time.delta ;
                    if (zengYuanJinDu() >= 1) {
                        for (SpawnGroup s : zengyuanDanWei()) {
                            if (s.type == null) continue ;
                            int spawned = s.getSpawned(zengyuanboci);
                            for(int i = 0 ; i < spawned ; i ++){
                                Tile t ;
                                do {
                                    Vec2 v = new Vec2();
                                    v.x = r.random(0, world.width());
                                    v.y = r.random(0, world.height());
                                    t = world.tile((int)v.x, (int)v.y) ;

                                }while (t == null || (!s.type.flying && (t.block().isStatic() || t.build != null)) || (isNaval(s.type.constructor.get()) && !t.floor().isLiquid)) ;
                                Unit unit = s.createUnit(state.rules.waveTeam, zengyuanboci);
                                unit.set(t.x * tilesize, t.y * tilesize);
                                unit.add() ;
                                spawner.spawnEffect(unit);
                            }
                        }
                        zengyuanjishi = 0 ;
                        zengyuanboci ++ ;
                    }
                }
                for (Tile t : fushiTiles) {
                    fushi(t, (SYBSLiquids.SYBSLiquid) t.floor().liquidDrop);
                }
                //fixedPrintln("", Core.settings.getBool("sybssets.fushe"));
                if ((Core.settings.getBool(Core.bundle.get("sybssets.fushe"))) || (state.getSector() != null && state.getSector().preset instanceof SYBSSectorPresets.SYBSSectorPreset ssp && ssp.alwaysFushe)) {
//                    for (FangFuSheLiChang.FangFuSheliChangBuild f : fangfusheBuilds) {
//                        if (f.efficiency <= 0) {
//                            handleFangFuShe(f, (t, ccqd) -> {
//                                if (!fusheTiles.contains(t) && t.overlay().itemDrop != null && t.overlay().itemDrop.radioactivity > 0) {
//                                    fusheTiles.add(t);
//                                }
//                            });
//                        }
//                    }
                    for (Tile t : fusheTiles) {
                        fushe(t.worldx(), t.worldy(), t.overlay().itemDrop) ;
                    }
                    /*
                    Units.nearbyBuildings(0, 0, Integer.MAX_VALUE, b -> {
                        if (b != null && b.items != null) {
                            for (Item i : content.items()) {
                                if (i.radioactivity > 0 && b.items.has(i)) {
                                    //System.out.println(i.localizedName);
                                    //fixedPrintln(" ", i.localizedName, b.getDisplayName());
                                    fushe(b.x, b.y, i);
                                    //break;
                                }
                            }
                        }
                    });
                    for (Unit unit : Groups.unit) {
                        if (unit.item() != null) {
                            if (unit.item().radioactivity > 0) {
                                fushe(unit.x, unit.y, unit.item());
                            }
                        }

                    }
                    */
                }
            }
            for (ZhaDanBlock z : zhadans) {
                z.timerB += Time.delta ;
            }
            for (Item i : content.items()) {
                if (i instanceof SYBSItems.SYBSItem s && s.colors.size > 1) {
                    if (s.curretColor == -1) {
                        s.curretColor = 0 ;
                        s.nextColor = 1 ;
                    }
                    s.timer += (Time.delta / 1) ;
                    float f  = 0.6f ;
                    Color c = s.colors.get(s.curretColor).cpy().lerp(s.colors.get(s.nextColor), f + Mathf.absin(Time.time, f * 5, 1 - f)) ;
                    s.color = c ;
                    s.localizedName = "[#" + c.toString() + "]" + s.rawLocalizedName ;
//                    if (ui.content instanceof SYBSContentInfoDialog sc && ui.content.isShown() && sc.curretContent == s) {
//                        sc.show(s);
//                    }
                    if (s.timer >= toSeconds * 0.7f) {
                        s.curretColor ++ ;
                        s.nextColor ++ ;
                        if (s.curretColor >= s.colors.size) {
                            s.curretColor -= s.colors.size ;
                        }
                        if (s.nextColor >= s.colors.size) {
                            s.nextColor -= s.colors.size ;
                        }
                        s.timer = 0 ;
                    }
                }
            }
        });
        Events.on(EventType.SaveLoadEvent.class, event -> {
            /*
            Units.nearbyBuildings(0, 0, Integer.MAX_VALUE, b -> {
                int d = 0 ;
                boolean have = false ;
                for (Consume c : b.block.consumers) {
                    if (c instanceof ConsumeDianYa dd){
                        have = true ;
                        d = dd.dianya ;
                        break ;
                    }
                }
                if (have) {
                    int finalD = d;
                    b.power(new DianYaModule(finalD));
                    b.power.graph.add(b);
                    //b.block.insulated = true ;
                }
            });
            */
            fusheTiles.clear();
            fushiTiles.clear();
            //fangfusheBuilds.clear() ;
            for (Tile t : world.tiles) {
                if (t.overlay() instanceof OreBlock o && o.itemDrop.radioactivity > 0) {
                    fusheTiles.add(t) ;
                }
                if (t.floor().liquidDrop instanceof SYBSLiquids.SYBSLiquid l && l.fushi > 0) {
                    fushiTiles.add(t) ;
                }
//                if (t.build instanceof FangFuSheLiChang.FangFuSheliChangBuild f) {
//                    fangfusheBuilds.add(f) ;
//                }
            }
        });
        /*
        Events.on(EventType.SaveWriteEvent.class, event -> {
            boolean bo = true ;
            try {
                Field f = PausedDialog.class.getDeclaredField("save") ;
                f.setAccessible(true);
                bo = ((SaveDialog)f.get(ui.paused)).isShown() ;
            }
            catch (Throwable t) {
                t.printStackTrace(System.err);
            }
            //System.out.println(control.saves.isSaving());
            if (bo || state.isMenu() || !control.saves.isSaving()) {
                Units.nearbyBuildings(0, 0, Integer.MAX_VALUE, b -> {
                    boolean have = false;
                    for (Consume c : b.block.consumers) {
                        if (c instanceof ConsumePower) {
                            have = true;
                            break;
                        }
                    }
                    if (have) {
                        b.power(new PowerModule());
                        b.power.graph = new PowerGraph() ;
                        b.power.graph.add(b);
                    }
                });
            }
        });
        */
        /*
        for (StatusEffect s : content.statusEffects()) {
            if (s instanceof SYBSStatusEffects.SYBSStatusEffect ss) {
                if (ss.fushi) {
                    Events.on(EventType.UnitDamageEvent.class, et -> {
                        if (et.unit.hasEffect(ss)) {
                            et.unit.damageContinuousPierce(et.bullet.damage * (ss.ft - 1));
                        }
                    });
                }
//                else if (ss.shoushangbeilv > 0) {
//                    Events.on(EventType.UnitDamageEvent.class, et -> {
//                        if (et.unit.hasEffect(ss)) {
//                            et.unit.damageContinuousPierce(et.bullet.damage * (ss.shoushangbeilv - 1));
//                        }
//                    });
//                }
            }
        }
        */
        Events.on(UnitDamageEvent.class, e -> {
            if (Core.settings.getBool(Core.bundle.get("sybssets.shanghaixianshi"))) {
                if (Damage.applyArmor(e.bullet.damage, e.unit.armor) / e.unit.healthMultiplier / Vars.state.rules.unitHealth(e.unit.team) >= 300) {
                    TimedWorldLabel t = new TimedWorldLabel();
                    t.text("[#" + e.unit.team.color.toString() + "]" + Damage.applyArmor(e.bullet.damage, e.unit.armor) / e.unit.healthMultiplier / Vars.state.rules.unitHealth(e.unit.team));
                    t.amount = Damage.applyArmor(e.bullet.damage, e.unit.armor) / e.unit.healthMultiplier / Vars.state.rules.unitHealth(e.unit.team);
                    t.fontSize = Mathf.clamp(t.amount / 300, 1, 6);
                    Vec2 v = random(e.unit.x, e.unit.y, Mathf.clamp(t.amount / 300 * tilesize, tilesize, 3 * tilesize) + e.unit.hitSize) ;
                    t.x = v.x ;
                    t.y = v.y ;
                    t.add();
                }
            }
        });
        Events.on(UnitChangeEvent.class, e -> {
            //fixedPrintln(" ", Core.input.keyDown(Binding.control), Core.input.keyDown(Binding.respawn));
            if (Core.input.keyDown(Binding.control) || Core.input.keyDown(Binding.respawn)) {
                controlingUnits.clear();
                controlingUnitIds.clear();
            }
        });
        Events.on(UnitDestroyEvent.class, e -> {
            if (e.unit == player.unit()) {
                controlingUnits.clear();
                controlingUnitIds.clear();
            }
            for (int i = 0 ; i < shuxings ; i ++) {
                beilvs.get(i).remove(e.unit) ;
            }
            if (control.saves.getCurrent() != null && !net.client()) {
                data.child("saves").child(control.saves.getCurrent().getName()).child("units").child(e.unit.id + ".dat").delete();
            }
        });
        Events.on(ClientLoadEvent.class, e -> {
            //ui.content = new SYBSContentInfoDialog() ;
            ui.settings.addCategory(Core.bundle.get("shayebushi.settings"), t -> {
                t.checkPref(Core.bundle.get("sybssets.fushe"), false, b -> {
                    if (b) {
                        if (!(Version.build == - 1 || tiaoshi)) {
                            settings.put(Core.bundle.get("sybssets.fushe"), false) ;
                        }
//                        for (FangFuSheLiChang.FangFuSheliChangBuild f : fangfusheBuilds) {
//                            Events.fire(cichangqiangduChangeEvent.set(f, f.cichangqiangdu, f.cichangqiangdu)) ;
//                        }
                    }
                });
                t.checkPref(Core.bundle.get("sybssets.shanghaixianshi"), false, b -> {
                    if (b) {
                        if (!(Version.build == - 1 || tiaoshi)) {
                            settings.put(Core.bundle.get("sybssets.shanghaixianshi"), false) ;
                        }
                    }
                });
                t.checkPref(Core.bundle.get("sybssets.youjian"), true);
                t.sliderPref(Core.bundle.get("sybssets.nandu"), 3, 1, 5, i -> {
                    if (i == 1) {
                        tiaoshi = true ;
                        BaseDialog b = new BaseDialog(Core.bundle.get("tiaoshi.tishi")) ;
                        b.cont.add(Core.bundle.get("tiaoshi.tishi1")).wrap().fillX().padLeft(10).width(1050).left().row() ;
                        b.cont.add(Core.bundle.get("tiaoshi.tishi2")).wrap().fillX().padLeft(10).width(1050).left().row() ;
                        b.cont.add(Core.bundle.get("tiaoshi.tishi3")).wrap().fillX().padLeft(10).width(1050).left().row() ;
                        b.cont.add(Core.bundle.get("tiaoshi.tishi4")).wrap().fillX().padLeft(10).width(1050).left().row() ;
                        b.addCloseButton() ;
                        b.show() ;
                    }
                    else {
                        tiaoshi = false ;
                        if (i == 5) {
                            lianyu = true ;
                        }
                        else {
                            lianyu = false ;
                        }
                    }
                    nandu = i ;
                    nanduFi.writeString(tiaoshi + " " + lianyu + " " + 3);
                    return Core.bundle.get("nandu." + nandus[i - 1]) ;
                }) ;
                t.row() ;
                SettingsMenuDialog.SettingsTable.Setting s = new SettingsMenuDialog.SettingsTable.Setting(Core.bundle.get("sybssets.items")) {
                    @Override
                    public void add(SettingsMenuDialog.SettingsTable table) {
                        table.button(Core.bundle.get("sybssets.items"), () -> {
                            BaseDialog dialog = new BaseDialog(Core.bundle.get("sybssets.items")) ;
                            SettingsMenuDialog.SettingsTable t2 = new SettingsMenuDialog.SettingsTable() ;
                            ScrollPane.ScrollPaneStyle s = (ScrollPane.ScrollPaneStyle)Core.scene.getStyle(ScrollPane.ScrollPaneStyle.class) ;
                            for (Item i : content.items()) {
                                i.load();
                                t2.margin(4).marginRight(8).left();
                                t2.image(i.uiIcon).size(8 * 3);
                                t2.checkPref((i instanceof SYBSItems.SYBSItem s2 && s2.colors.size > 0 ? s2.rawLocalizedName : i.localizedName), true);
                            }
                            dialog.cont.add((Element)new ScrollPane(t2, s)) ;
                            dialog.addCloseButton();
                            dialog.show() ;
                            SYBSCoreItemsDisplay.inited = true ;
                        }).size(250, 64).row() ;
                    }
                } ;
                t.pref(s);
            });
            ui.planet = new SYBSPlanetDialog() ;
            PlanetDialog.debugSelect = Version.build == -1 || tiaoshi ;
            ui.research = new SYBSResearchDialog() ;
            renderer.minZoom = 0.1f ;
            renderer.maxZoom = 10f ;
            ui.content = new SYBSContentInfoDialog() ;
            MapResizeDialog.maxSize = 5000 ;
            MapResizeDialog.minSize = 1 ;
            setPrivateField(ui.hudfrag, "coreItems", new SYBSCoreItemsDisplay());
            ui.hudGroup.clear();
            ui.hudGroup.setFillParent(true);
            ui.hudGroup.touchable = Touchable.childrenOnly;
            ui.hudGroup.visible(() -> state.isGame());
            ui.hudfrag.build(ui.hudGroup);
            ui.chatfrag.build(ui.hudGroup);
            //ui.minimapfrag = new SYBSMinimapFragment() ;
            ui.minimapfrag.build(ui.hudGroup);
            ui.listfrag.build(ui.hudGroup);
            ui.consolefrag.build(ui.hudGroup);
            ui.hudGroup.addChild(makeStatusTable().left().bottom().visible(() -> ui.hudfrag.shown));
            ui.hudGroup.addChild(makeBossBar().left().bottom().visible(() -> ui.hudfrag.shown));
            setPrivateField(renderer, "minimap", new SYBSMinimapRenderer()) ;
            EntityIndexer ei = (EntityIndexer) getPrivateField(Groups.unit, "indexer") ;
            Groups.unit = new SYBSEntityGroup<>(mindustry.gen.Unit.class, true, true, ei);
            ei = (EntityIndexer) getPrivateField(Groups.all, "indexer") ;
            Groups.all = new SYBSEntityGroup<>(mindustry.gen.Entityc.class, false, false, ei);
            ei = (EntityIndexer) getPrivateField(Groups.sync, "indexer") ;
            Groups.sync = new SYBSEntityGroup<>(mindustry.gen.Syncc.class, false, true, ei);
            ei = (EntityIndexer) getPrivateField(Groups.draw, "indexer") ;
            Groups.draw = new SYBSEntityGroup<>(mindustry.gen.Drawc.class, false, false, ei);
            SYBSFonts.load();
            try {
                Field f = Waves.class.getDeclaredField("spawns");
                f.setAccessible(true);
                Vars.waves.get();
                Seq<SpawnGroup> s = (Seq<SpawnGroup>)f.get(Vars.waves) ;
                s.addAll(new SpawnGroup(shenqi){{
                             begin = 157;
                             unitAmount = 1;
                             unitScaling = 3f;
                             spacing = 30;
                             shields = 4000;
                             shieldScaling = 40f;
                         }},
                        new SpawnGroup(xieling){{
                            begin = 162;
                            unitAmount = 1;
                            unitScaling = 5f;
                            spacing = 30;
                            shields = 6000;
                            shieldScaling = 40f;
                        }},
                        new SpawnGroup(anye){{
                            begin = 174;
                            unitAmount = 1;
                            unitScaling = 3f;
                            spacing = 35;
                            shields = 5000;
                            shieldScaling = 40f;
                        }},
                        new SpawnGroup(shengling){{
                            begin = 180;
                            unitAmount = 1;
                            unitScaling = 3f;
                            spacing = 35;
                            shields = 8000;
                            shieldScaling = 50f;
                        }},
                        new SpawnGroup(shuangxingyijieduan){{
                            begin = 200;
                            unitAmount = 1;
                            unitScaling = 10f;
                            spacing = 40;
                            shields = 4000;
                            shieldScaling = 35f;
                        }});
            }
            catch (NoSuchFieldException | IllegalAccessException s){
                s.printStackTrace();
            }
            asyncCore.processes.set(0, new SYBSPhysicsProcess());
        });
        Events.on(PlayEvent.class, e -> {
            SYBSUnitTypes.loadWeapons();
            //System.out.println(6);
        });
        Events.on(SaveLoadEvent.class, e -> {
            SYBSUnitTypes.loadWeapons();
            //System.out.println(6);
        });
        Events.on(EditorMapsDialog.class, e -> {
            SYBSUnitTypes.loadWeapons();
            //System.out.println(6);
        });
        Events.on(ContentInfoDialog.class, e -> {
            SYBSUnitTypes.loadWeapons();
            //System.out.println(6);
        });
        Events.on(SectorLoseEvent.class, e -> {
            if (control.saves.getCurrent() != null) {
                Fi f = data.child("saves").child(control.saves.getCurrent() + ".dat") ;
                if (f.exists()) {
                    f.writeString("1 0");
                }
            }
            zengyuanboci = 0 ;
            zengyuanjishi = 0 ;
        });
        /*
        Events.on(SaveWriteEvent.class, e -> {
            if (!control.saves.isSaving()) {
                Time.setDeltaProvider(() -> TiaoJieShiJianBlock.def);
            }
        }) ;
        */
//        Events.on(UnitDestroyEvent.class, e -> {
//            if (e.unit instanceof TouBuUnit t) {
//                System.out.println(6);
//                for (Unit u : t.jietis) {
//                    if (u instanceof QiangZhiXianShangUnitEntity q) {
//                        q.fixedKill();
//                    }
//                }
//            }
//        });
//        Events.on(EventType.UnitDamageEvent.class , event->{
//            int IV = Units.count( -Vars.world.width(),-Vars.world.height(), Integer.MAX_VALUE, new Boolf<Unit>() {
//                @Override
//                public boolean get(Unit unit) {
//                    if (unit.type == SYBSShenShengUnitTypes.tianqi && event.unit.team != unit.team){
//                        return true ;
//                    }
//                    else{
//                        return false ;
//                    }
//                }
//            });
//            if (event.unit.hasEffect(SYBSStatusEffects.tianfa) && IV > 0){
//                event.unit.damage(3600);
//                UnitType type = event.unit.type ;
//                for (Weapon w : type.weapons){
//                    w.bullet.damage = 0 ;
//                }
//                type.abilities.clear() ;
////                type.weapons.clear() ;
//                type.weapons = UnitTypes.dagger.weapons ;
//                unitType = UnitTypes.dagger ;
//                event.unit.type = type ;
//                event.unit.damageMultiplier = 0 ;
//                event.unit.hasWeapons() ;
//            }
//            else if (event.unit.hasEffect(SYBSStatusEffects.tianfa) && unitType != null && IV == 0) {
//                unitType.weapons.clear() ;
//                unitType.weapons.add(new Weapon("6")) ;
//                event.unit.type = unitType ;
//                event.unit.maxHealth = UnitTypes.dagger.health ;
//            }
//        });
//        Events.on(UnitDamageEvent.class,event -> {
//           if (SYBSShenShengUnitTypes.shenshengjidanwei.contains(event.unit.type)){
//               if (event.bullet.damage > 35000) {
//                   event.unit.heal(event.bullet.damage - 35000);
//               }
//               if (event.bullet.type instanceof ContinuousBulletType || (event.bullet.owner instanceof Turret turret && turret.reload < 60)){
//                   event.unit.heal(event.bullet.damage);
//               }
//           }
//        });
//        Events.on(EventType.SectorLaunchLoadoutEvent.class,event->{
//            if (event.sector != null) {
//                if (event.sector.name().equals("落脚点")) {
//                    event.sector.info.bestCoreType = SYBSBlocks.yijihexin;
//                }
//            }
//        });
    }

    @Override
    public void loadContent(){
        Log.info("Loading ShaYeBuShi's content.");
//        content.setCurrentMod(new Mods.LoadedMod(null, null, this, null, new Mods.ModMeta(){{
//            name = "shayebushi" ;
//            description = "This is nothing" ;
//            author = "CPTPC" ;
//            minGameVersion = "v145.1" ;
//            java = true ;
//            main = "shayebushi.ShaYeBuShi" ;
//        }}));

//        KeyBinds.KeyBind[] k = {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null} ;
//        //System.out.println(k.length);
//        int i = 0 ;
//        for (KeyBinds.KeyBind kk : Binding.values()){
//            k[i] = kk ;
//            i ++ ;
//        }
//        for (KeyBinds.KeyBind kk : SYBSBinding.values()){
//            k[i] = kk ;
//            i ++ ;
//        }
//        keybinds.setDefaults(k);
        try {
            Field f = KeyBinds.class.getDeclaredField("defaultCache");
            Field ff = KeyBinds.class.getDeclaredField("definitions");
            f.setAccessible(true);
            ff.setAccessible(true);
            KeyBinds.KeyBind[] d = SYBSBinding.values() ;
            KeyBinds.KeyBind[] dd = (KeyBinds.KeyBind[]) ff.get(keybinds);
            Seq<KeyBinds.KeyBind> s = new Seq<>(false,d.length + dd.length, KeyBinds.KeyBind.class);
            s.addAll(d).addAll(dd);
            ff.set(keybinds,s.toArray());
            ObjectMap<KeyBinds.KeyBind, ObjectMap<InputDevice.DeviceType, KeyBinds.Axis>> ddd = (ObjectMap<KeyBinds.KeyBind, ObjectMap<InputDevice.DeviceType, KeyBinds.Axis>>) f.get(keybinds);
            for (KeyBinds.KeyBind def : d) {
                ddd.put(def, new ObjectMap<>());
                for (InputDevice.DeviceType type : InputDevice.DeviceType.values()) {
                    ddd.get(def).put(type, def.defaultValue(type) instanceof KeyBinds.Axis ? (KeyBinds.Axis) def.defaultValue(type) : new KeyBinds.Axis((KeyCode) def.defaultValue(type)));
                }
            }
        }
        catch (NoSuchFieldException | IllegalAccessException s){
            s.printStackTrace();
        }
        SYBSItems.load();
        SYBSLiquids.load();
        SYBSStatusEffects.load();
        SYBSUnitCommands.load();
        SYBSSounds.load() ;
        SYBSBullets.load() ;
        SYBSUnitTypes.load();
        SYBSShenShengUnitTypes.load();
        SYBSBlocks.load();
        SYBSUnitAbilities.load();
        SYBSPlanets.load();
        SYBSSectorPresets.load();
        DeLeiKeTechTree.load();
        //SYBSTeams.load();
        //SYBSPlanetDialog.setPlanetDialog();
        //throw new NullPointerException("ShaYeBuShi killed your game.This is not a BUG.") ;
    }

    public static void applyShaYeBuShi() {
        ShaYeBuShi sybs = new ShaYeBuShi() ;
        sybs.loadContent();
    }

    public void fushe(float x, float y, Item i){
        //System.out.println(f.range);
        Units.nearby(null, x, y, i.radioactivity * 3 * tilesize, unit -> applyFushe(i, unit));
        Units.nearbyBuildings(x, y, i.radioactivity * 3 * tilesize, b -> applyFushe(i, b));
    }

    public void applyFushe(Item i, Posc e) {
        //System.out.println(unit.type.localizedName);
        if (e instanceof Unit u) {
            float fangfushe = true ? 0 : u.item() != null && u.item() instanceof SYBSItems.SYBSItem si && si.fangfushe > 0 ? si.fangfushe : 0 ;
            if (i.radioactivity >= 2) {
                u.apply(SYBSStatusEffects.shuaibian, i.radioactivity * 15 * Math.max(i.radioactivity - fangfushe, 0));
            }
            if (i.radioactivity > 1) {
                u.apply(SYBSStatusEffects.fushe, i.radioactivity * 15 * Math.max(i.radioactivity - fangfushe, 0));
            }
        }
        else if (e instanceof Building b) {
            if (!fangfushes.containsKey(b.block)) {
                float fangfushe = 0;
                for (ItemStack is : b.block.requirements) {
                    if (is.item instanceof SYBSItems.SYBSItem si && si.fangfushe > 0) {
                        fangfushe += si.fangfushe;
                    }
                }
                fangfushes.put(b.block, fangfushe) ;
            }
            if (ConsumeFuShe.status.get(b, (ConsumeFuShe.Data) null) != null && ConsumeFuShe.status.get(b).status == 0) {
                b.damage(b.health * 0.0000167f * Math.max(i.radioactivity - fangfushes.get(b.block), 0));
            }
        }
//                            boolean bbb = true ;
//                            for (ItemStack item : unit.type.getFirstRequirements()){
//                                if (item.item == SYBSItems.fangfushe) {
//                                    bbb = false ;
//                                }
//                            }
//                            if (bbb) {
//                                f.apply(unit);
//                            }

            //System.out.println(6);
    }
    public void fushi(Tile t, SYBSLiquids.SYBSLiquid sl){
        if (t.build != null) {
            if (!fangfushis.containsKey(t.build.block)) {
                float fangfushi1 = 0 ;
                for (ItemStack is : t.build.block.requirements) {
                    if (is.item instanceof SYBSItems.SYBSItem si && si.fangfushi > 0) {
                        fangfushi1 += si.fangfushi;
                    }
                }
                fangfushis.put(t.build.block, fangfushi1) ;
            }
            t.build.damage(t.build.health * 0.0000167f * Math.max(sl.fushi -fangfushis.get(t.build.block), 0));
        }
        /*
        Units.nearby(null, t.x * tilesize, t.y, tilesize, unit -> {
            float fangfushi = 0 ;
            if (unit.item() != null) {
                if (unit.item() instanceof SYBSItems.SYBSItem si && si.fangfushi > 0) {
                    fangfushi += si.fangfushi ;
                }
            }
            //fangfushi = 0 ;
            unit.apply(SYBSStatusEffects.zhongdufushi, sl.fushi * 15 * Math.max(sl.fushi - fangfushi, 0));
        });
        */
    }
    public static float fenlieDps(BulletType b) {
        if (b.fragBullets <= 0 || b.fragBullet == null) {
            return 0 ;
        }
        return ((b.fragBullet instanceof ContinuousBulletType c ? toSeconds / c.damageInterval : b instanceof LineBullet c ? toSeconds / c.damageInterval : 1) * (b.fragBullet.damage + b.fragBullet.splashDamage) + fenlieDps(b.fragBullet) + shanDianDps(b.fragBullet) + jianGeDps(b.fragBullet)) * b.fragBullets ;
    }
    public static float shanDianDps(BulletType b) {
        if (b.lightning <= 0 || b.lightningType == null) {
            return 0 ;
        }
        return (b.lightningDamage + b.lightningType.damage + b.lightningType.splashDamage + fenlieDps(b.lightningType) + shanDianDps(b.lightningType) + jianGeDps(b.lightningType)) * b.lightning ;
    }
    public static float jianGeDps(BulletType b) {
        if (b.intervalBullets <= 0 || b.intervalBullet == null) {
            return 0 ;
        }
        return (b.intervalBullet.damage + b.intervalBullet.splashDamage + fenlieDps(b.intervalBullet) + shanDianDps(b.intervalBullet) + jianGeDps(b.intervalBullet)) * b.intervalBullets / b.bulletInterval * 60 ;
    }
    public static float dps(BulletType b) {
        float dps = 0 ;
        for (BulletType bb : b.spawnBullets) {
            dps += dps(bb) ;
        }
        dps += (b instanceof ContinuousBulletType c ? toSeconds / c.damageInterval : b instanceof LineBullet c ? toSeconds / c.damageInterval : 1) * (b.damage + b.splashDamage) + fenlieDps(b) + shanDianDps(b) + jianGeDps(b) + (b.spawnUnit != null ? DPS(b.spawnUnit) : 0) ;
//        if (b.fragBullet instanceof YunShiBulletType) {
//            fixedPrintln(dps(b.fragBullet), dps);
//        }
        return dps ;
    }
    public static boolean withIn(float target, float value, float range) {
        return Math.abs(target - value) <= range ;
    }
    public static float DPS(UnitType u){
        float dps = 0 ;
        for (Weapon w : u.weapons) {
            float shesu = (u instanceof MissileUnitType || u instanceof YuLeiUnitType) && (w.bullet instanceof ExplosionBulletType || w.bullet instanceof TeShuExplosionBulletType) ? 1 : toSeconds / w.reload * w.shoot.shots ;
//                    System.out.println(u.name + " " + (w.bullet.damage + w.bullet.splashDamage + fenlieDps(w.bullet) + (w.bullet.spawnUnit != null ? w.bullet.spawnUnit.weapons.get(0).bullet.damage + w.bullet.spawnUnit.weapons.get(0).bullet.splashDamage : 0)) * shesu);
            dps += dps(w.bullet) * shesu ;
//            if (u.name.equals("chichao") || u.name.equals("chichao-yulei")) {
//                System.out.println(dps(w.bullet) * shesu + " " + w.name);
//            }
        }
        return dps ;
    }
    /*
    public static Vec2 circle(float angle, float radius, float posX, float posY){
        double x = 0, y = 0;
        if (angle >= 0 && angle < 90) {
            if (angle <= 45) {
                x = radius * Math.cos(angle);
                y = radius * Math.sin(angle);
            } else {
                y = radius * Math.cos(angle);
                x = radius * Math.sin(angle);
            }
        } else if (angle >= 90 && angle < 180) {
            if (angle <= 135) {
                x = -radius * Math.sin(angle - 90);
                y = radius * Math.cos(angle - 90);
            } else {
                y = radius * Math.sin(angle - 90);
                x = -radius * Math.cos(angle - 90);
            }
        } else if (angle >= 180 && angle < 270) {
            if (angle <= 225) {
                y = -radius * Math.sin(angle - 180);
                x = -radius * Math.cos(angle - 180);
            } else {
                x = -radius * Math.sin(angle - 180);
                y = -radius * Math.cos(angle - 180);
            }
        } else if (angle >= 270 && angle < 360) {
            if (angle <= 315) {
                y = -radius * Math.cos(angle - 270);
                x = radius * Math.sin(angle - 270);
            } else {
                x = radius * Math.sin(angle - 270);
                y = -radius * Math.cos(angle - 270);
            }
        }
        return new Vec2((float) (posX + x), (float) (posY + y)) ;
    }
    */
    public static Vec2 circle(float angle, float radius, float posX, float posY){
        while (angle < 0) {
            angle += 360 ;
        }
        angle %= 360 ;
        double x = 0, y = 0;
        //angle = (float) Math.toRadians(angle) ;
        if (angle >= 0 && angle < 90) {
            x = radius * Math.cos(Math.toRadians(angle));
            y = radius * Math.sin(Math.toRadians(angle));
        } else if (angle >= 90 && angle < 180) {
            x = -radius * Math.sin(Math.toRadians(angle - 90));
            y = radius * Math.cos(Math.toRadians(angle - 90));
        } else if (angle >= 180 && angle < 270) {
            y = -radius * Math.sin(Math.toRadians(angle - 180));
            x = -radius * Math.cos(Math.toRadians(angle - 180));
        } else if (angle >= 270 && angle < 360) {
            y = -radius * Math.cos(Math.toRadians(angle - 270));
            x = radius * Math.sin(Math.toRadians(angle - 270));
        }
        return new Vec2((float) (posX + x), (float) (posY + y)) ;
    }

    public static boolean realGrounded(Unit u) {
        return u.isGrounded() && !u.type.naval && !isNaval(u) ;
    }
    public static boolean isNaval(Unit u) {
        return u.isGrounded() && u.type.naval && (u instanceof UnitWaterMove || u instanceof WaterMoveXianShangUnit || u instanceof WaterMoveTimedKillUnit || u instanceof WaterMovePayLoadUnit) ;
    }
    public static boolean bothGrounded(Unit u1, Unit u2) {
        return realGrounded(u1) && realGrounded(u2) ;
    }
    public static boolean bothFlying(Unit u1, Unit u2) {
        return u1.isFlying() && u2.isFlying() ;
    }
    public static boolean bothNaval(Unit u1, Unit u2) {
        return isNaval(u1) && isNaval(u2) ;
    }
    public static boolean bothType(Unit u1, Unit u2) {
        return bothGrounded(u1, u2) || bothFlying(u1, u2) || bothNaval(u1, u2) ;
    }
    public static boolean allGrounded(Unit... units) {
        Unit last = units[0] ;
        for (Unit u : units) {
            if (last == u) {
                continue;
            }
            if (!bothGrounded(last, u)) {
                return false ;
            }
            last = u ;
        }
        return true ;
    }
    public static boolean allFlying(Unit... units) {
        Unit last = units[0] ;
        for (Unit u : units) {
            if (last == u) {
                continue;
            }
            if (!bothFlying(last, u)) {
                return false ;
            }
            last = u ;
        }
        return true ;
    }
    public static boolean allNaval(Unit... units) {
        Unit last = units[0] ;
        for (Unit u : units) {
            if (last == u) {
                continue;
            }
            if (!bothNaval(last, u)) {
                return false ;
            }
            last = u ;
        }
        return true ;
    }
    public static boolean allType(Unit... units) {
        Unit last = units[0] ;
        for (Unit u : units) {
            if (last == u) {
                continue;
            }
            if (!bothType(last, u)) {
                return false ;
            }
            last = u ;
        }
        return true ;
    }
    public static void fixedPrintln(String sep, Object... os) {
        int i = 0 ;
        for (Object o : os) {
            System.out.print(o + (++ i == os.length ? "\n" : sep));
        }
    }
    public static void fixedPrintln(Object... os) {
        for (Object o : os) {
            System.out.println(o);
        }
    }
    public static boolean isJianYi(StatusEffect e) {
        boolean b = e instanceof SYBSStatusEffects.SYBSStatusEffect && ((SYBSStatusEffects.SYBSStatusEffect) e).damageMultiplierr >= 1 && ((SYBSStatusEffects.SYBSStatusEffect) e).healthMultiplierr >= 1 && ((SYBSStatusEffects.SYBSStatusEffect) e).speedMultiplierr >= 1 && ((SYBSStatusEffects.SYBSStatusEffect)e).buildSpeedMultiplierr >= 1 && ((SYBSStatusEffects.SYBSStatusEffect) e).damagee <= 0 && e.transitionDamage <= 0 && !((SYBSStatusEffects.SYBSStatusEffect) e).fushi && !((SYBSStatusEffects.SYBSStatusEffect) e).huimie && ((SYBSStatusEffects.SYBSStatusEffect) e).reloadMultiplierr >= 1 && !((SYBSStatusEffects.SYBSStatusEffect)e).disarmm;
        boolean bb = e.damage <= 0 && e.healthMultiplier >= 1 && e.damageMultiplier >= 1 && e.speedMultiplier >= 1 && e.buildSpeedMultiplier >= 1 && e.transitionDamage <= 0 && e.reloadMultiplier >= 1 && !e.disarm;
        //System.out.println(e.name + " " + bb + b);
        return e instanceof SYBSStatusEffects.SYBSStatusEffect ? !(bb && b) : !bb ;
    }
    public static void applyImm(UnitType u, ItemStack i) {
        if (i.item instanceof SYBSItems.SYBSItem si) {
            if (si.fangfushe > 0) {
                if (si.fangfushe >= 2) {
                    u.immunities.add(SYBSStatusEffects.shuaibian);
                }
                if (si.fangfushe >= 1) {
                    u.immunities.add(SYBSStatusEffects.fushe);
                }
            }
            if (si.fangfushi > 0) {
                if (si.fangfushi >= 1) {
                    u.immunities.add(SYBSStatusEffects.zhongdufushi);
                }
            }
        }
    }
    public static <T, V> void clone(T source, V out) {
        if (source == null || out == null) {
            return ;
        }
        if (source instanceof UnlockableContent u) {
            u.load();
            u.loadIcon();
        }
        Class<?> c = source.getClass() ;
        Class<?> c2 = out.getClass() ;
        for (Field f : c.getFields()) {
           if (!(source instanceof MappableContent && f.getName().equals("name"))) {
               try {
                   f.setAccessible(true);
                   f.set(out, f.get(source));
               }
               catch (Throwable t) {
                   t.printStackTrace();
               }
           }
        }
    }
    public static <T> T clone(T source) {
        if (source == null) {
            return source ;
        }
        Class<?> c = source.getClass() ;
        Object out ;
        try {
            if (source instanceof MappableContent m) {
                out = c.getConstructor(String.class).newInstance(fixedRandomString(m)) ;
            }
            else {
                out = c.newInstance();
            }
        }
        catch (Throwable t) {
            //t.printStackTrace();
            //System.out.println(6);
            return source ;
        }
        for (Field f : c.getFields()) {
            if (!(source instanceof MappableContent && f.getName().equals("name"))) {
                setPrivateField(out, f.getName(), getPrivateField(source, f.getName()));
            }
        }
        return (T)out ;
    }
    public static void genModFile() {
        if (Version.build != -1) {
            return ;
        }
        Fi source = Core.files.absolute("D:\\Mindustry-master\\core\\build\\libs\\A-files") ;
        Fi target = source.child("libs/") ;
        Fi bundle = Core.files.absolute("D:\\Mindustry-master\\core\\src\\bundles") ;
        Fi code = Core.files.absolute("D:\\Mindustry-master\\core\\build\\classes\\java\\main\\shayebushi") ;
        source.child("bundles/").deleteDirectory() ;
        bundle.copyTo(source);
        source.child("shayebushi/").deleteDirectory() ;
        code.copyTo(source) ;
        Fi out = target.child("shayebushi-" + launchTimes) ;
        out.mkdirs() ;
        for (Fi f : source.list()) {
            if (!f.name().contains(".zip") && !f.name().contains(".jar") && !f.name().contains("0-anything") && !f.name().contains("libs")) {
                f.copyTo(out);
            }
        }
        File zipped = new File(out.path() + ".jar") ;
        try {
            FileOutputStream fos = new FileOutputStream(zipped) ;
            ZipOutputStream zos = new ZipOutputStream(fos) ;
            genZip(out.file(), "", zos) ;
            zos.close() ;
            fos.close() ;
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        out.deleteDirectory() ;
    }
    public static void genZip(File file, String baseDir, ZipOutputStream z) throws IOException {
        File[] files = file.listFiles() ;
        byte[] bs = new byte[1024] ;
        if (files != null) {
            for (File f : files) {
                String filePath = baseDir.isEmpty() || baseDir.endsWith(File.separator) ? f.getName() : baseDir + File.separator + f.getName() ;
                if (f.isDirectory()) {
                    genZip(f, filePath, z) ;
                }
                else {
                    FileInputStream fis = new FileInputStream(f) ;
                    ZipEntry ze = new ZipEntry(filePath) ;
                    z.putNextEntry(ze) ;
                    int len ;
                    while ((len = fis.read(bs)) > 0) {
                        z.write(bs, 0, len) ;
                    }
                    z.closeEntry() ;
                    fis.close() ;
                }
            }
        }
    }
    public static String randomString() {
        Seq<String> letters = new Seq<>() ;
        letters.addAll("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z") ;
        String out = "" ;
        for (int i = 0 ; i < 4 ; i ++) {
            out += letters.random(r) ;
        }
        return out ;
    }
    public static boolean checkString(String s, MappableContent m) {
        try {
            Field f = ContentLoader.class.getDeclaredField("contentNameMap") ;
            f.setAccessible(true) ;
            return ((ObjectMap<String, MappableContent>[])f.get(content))[m.getContentType().ordinal()].get(s) == null ;
        }
        catch (Throwable t) {
            return true ;
        }
    }
    public static String fixedRandomString(MappableContent m) {
        String out = randomString() ;
        while (!checkString(out, m)) {
            out = randomString() ;
        }
        return out ;
    }
    public static Object getPrivateField(Object target, String name) {
        try {
            Field f = target.getClass().getDeclaredField(name) ;
            f.setAccessible(true) ;
            return f.get(target) ;
        }
        catch (Throwable t) {
            t.printStackTrace() ;
            return null ;
        }
    }
    public static void invokePrivateMethod(Object target, String name, Seq<Object> arguments) {
        try {
            Method m = target.getClass().getDeclaredMethod(name, arguments.map(Object::getClass).toArray(Class.class)) ;
            m.setAccessible(true) ;
            m.invoke(target, arguments) ;
        }
        catch (Throwable t) {
            t.printStackTrace() ;
        }
    }
    public static void setPrivateField(Object target, String name, Object value) {
        try {
            Field f = target.getClass().getDeclaredField(name) ;
            f.setAccessible(true) ;
            f.set(target, value) ;
        }
        catch (Throwable t) {

        }
    }
    public static boolean hasZengYuan() {
        return (hasZengYuanS() || hasZengYuanM()) && !net.client() ;
    }
    public static boolean hasZengYuanS() {
        return state.getSector() != null && state.getSector().preset instanceof SYBSSectorPresets.SYBSSectorPreset s && !s.zengyuan.isEmpty() && s.zengyuanboci > 0 ;
    }
    public static boolean hasZengYuanM() {
        return state.map != null && SYBSSectorPresets.map.get(state.map.name(), (SYBSSectorPresets.SYBSSectorPreset)null) != null && !SYBSSectorPresets.map.get(state.map.name(), (SYBSSectorPresets.SYBSSectorPreset)null).zengyuan.isEmpty() && SYBSSectorPresets.map.get(state.map.name(), (SYBSSectorPresets.SYBSSectorPreset)null).zengyuanboci > 0 && (state.map.author().equals("CPTPC") || state.map.author().equals("ht6667")) ;
    }
    public static float zengYuanJinDu() {
        return hasZengYuanS() ? zengyuanjishi / ((SYBSSectorPresets.SYBSSectorPreset)state.getSector().preset).zengyuanjiange : hasZengYuanM() ? zengyuanjishi / SYBSSectorPresets.map.get(state.map.name(), (SYBSSectorPresets.SYBSSectorPreset)null).zengyuanjiange : 0 ;
    }
    public static int zengYuanBoCi() {
        return hasZengYuanS() ? ((SYBSSectorPresets.SYBSSectorPreset)state.getSector().preset).zengyuanboci : hasZengYuanM() ? SYBSSectorPresets.map.get(state.map.name(), (SYBSSectorPresets.SYBSSectorPreset)null).zengyuanboci :-1 ;
    }
    public static Seq<SpawnGroup> zengyuanDanWei() {
        return hasZengYuanS() ? ((SYBSSectorPresets.SYBSSectorPreset)state.getSector().preset).zengyuan : hasZengYuanM() ? SYBSSectorPresets.map.get(state.map.name(), (SYBSSectorPresets.SYBSSectorPreset)null).zengyuan : new Seq<>() ;
    }
    public static Table makeStatusTable(){
        Table table = new Table(Tex.wavepane);

        StringBuilder ibuild = new StringBuilder();

        IntFormat
                wavefc = new IntFormat("zengyuan.boci"),
                waitingf = new IntFormat("zengyuan.jiange", i -> {
                    ibuild.setLength(0);
                    int m = i/60;
                    int s = i % 60;
                    if(m > 0){
                        ibuild.append(m);
                        ibuild.append(":");
                        if(s < 10){
                            ibuild.append("0");
                        }
                    }
                    ibuild.append(s);
                    return ibuild.toString();
                });

        table.touchable = Touchable.enabled;

        StringBuilder builder = new StringBuilder();

        table.name = "zengyuan";

        table.marginTop(0).marginBottom(4).marginLeft(4);

        table.stack(
                new Element(){
                    @Override
                    public void draw(){
                        Draw.color(Pal.darkerGray, parentAlpha);
                        Fill.poly(x + width/2f, y + height/2f, 6, height / Mathf.sqrt3);
                        Draw.reset();
                        Drawf.shadow(x + width/2f, y + height/2f, height * 1.13f, parentAlpha);
                    }
                },
                new Table(t -> {
                    float bw = 40f;
                    float pad = -20;
                    t.margin(0);
                    t.add(new SideBar(ShaYeBuShi::zengYuanJinDu, () -> true, true)).width(bw).growY().padRight(pad);
                    t.image(() -> Icon.units.getRegion()).scaling(Scaling.bounded).grow().maxWidth(54f);
                    t.add(new SideBar(ShaYeBuShi::zengYuanJinDu, () -> true, false)).width(bw).growY().padLeft(pad);

                    t.getChildren().get(1).toFront();
                })
        ).size(120f, 80).padRight(4);

        Cell[] lcell = {null};
        boolean[] couldSkip = {true};

        lcell[0] = table.labelWrap(() -> {
            lcell[0].padRight(-125f);
            builder.setLength(0);
            if(!hasZengYuan() && net.client()){
                builder.append("[lightgray]").append(Core.bundle.get("zengyuan.fuwuqi"));
                return builder ;
            }
            if(!hasZengYuan() || (zengyuanboci > zengYuanBoCi())){
                builder.append("[lightgray]").append(Core.bundle.get("zengyuan.no"));
                return builder ;
            }
            builder.append(wavefc.get(zengyuanboci, zengYuanBoCi()));
            builder.append("\n");
            builder.append((zengyuanboci > zengYuanBoCi() ? Core.bundle.get("wave.waveInProgress") : (waitingf.get((int)((zengyuanjishi / zengYuanJinDu() - zengyuanjishi) / 60)))));

            return builder;
        }).growX().pad(8f);

        table.row();
        return table;
    }
    public static Vec2 random(Vec2 v, float radius) {
        return new Vec2(v.x + r.random(-radius, radius), v.y + r.random(-radius, radius)) ;
    }
    public static Vec2 random(float x, float y, float radius) {
        return random(new Vec2(x, y), radius) ;
    }
    public static Vec2 random(Rand rand, Vec2 v, float radius) {
        return new Vec2(v.x + rand.random(-radius, radius), v.y + rand.random(-radius, radius)) ;
    }
    public static Vec2 random(Rand rand, float x, float y, float radius) {
        return random(rand, new Vec2(x, y), radius) ;
    }
    public static Table makeBossBar() {
        Table t = new Table() ;
        t.top();

        if(Core.settings.getBool("macnotch") ){
            t.margin(macNotchHeight);
        }

        t.visible(() -> ui.hudfrag.shown && current != null);

        t.name = "current";
        var bossb = new StringBuilder();

        t.table(v -> {
            v.image(getError()).update(i -> {
                i.setDrawable(current != null ? current.type.uiIcon : getError());
            }).size(64).padRight(30);
            Bits statuses = new Bits();
            v.table().update(t2 -> {
                if (current == null) {
                    t2.clear() ;
                    return ;
                }
                t2.left();
                Bits applied = current.statusBits();
                if(!statuses.equals(applied)){
                    t2.clear();

                    if(applied != null && current != null){
                        for(StatusEffect effect : content.statusEffects()){
                            if(applied.get(effect.id) && !effect.isHidden()){
                                t2.image(effect.uiIcon).size(iconMed).get()
                                        .addListener(new Tooltip(l -> l.label(() ->
                                                effect.localizedName + " [lightgray]" + UI.formatTime(current.getDuration(effect))).style(Styles.outlineLabel)));
                            }
                        }

                        statuses.set(applied);
                    }
                }
            }).padBottom(80).left().padRight(30) ;
            v.margin(10f)
                    .add(new Bar(() -> {
                        if (current == null) {
                            return "" ;
                        }
                        bossb.setLength(0);
                        //bossb.append(current.type.emoji());
                        bossb.append("[accent]" + current.type.localizedName + "[]");
                        //bossb.append("[accent]+[]");
                        bossb.append(" ");
                        bossb.append(Math.max(current.health, 0) + "/" + current.maxHealth + "(" + Math.max((int) ((current.healthf()) * 100), 0) + "%" + ")") ;
                        return bossb;
                    }, () -> current == null ? Pal.health : current.team.color, () -> current == null ? 0 : current.healthf()).blink(Color.white).outline(new Color(0, 0, 0, 0.6f), 7f)).width(670).padLeft(30).grow() ;
        })
                .width(680).height(60f).name("current").visible(() -> state.rules.waves && current != null && !(mobile && Core.graphics.isPortrait())).padLeft(300).padTop(7).row();
        return t ;
    }
    public static <T> boolean has(T[] a, Boolf<T> b) {
        return new Seq<T>(a).contains(b) ;
    }
    public static <T> T[] add(T[] a, T o, Class<?> c) {
        Seq<T> s = new Seq<>(a) ;
        s.add(o) ;
        return s.toArray(c) ;
    }
    public static <T> T[] remove(T[] a, T o, Class<?> c) {
        Seq<T> s = new Seq<>(a) ;
        s.remove(o) ;
        return s.toArray(c) ;
    }
    public static void handleFangFuShe(FangFuSheLiChang.FangFuSheliChangBuild f, Cons2<Tile, Float> cons) {
        float ccqd = fangFuSheCiChangQiangDu * (state.getSector() != null && state.getSector().preset instanceof SYBSSectorPresets.SYBSSectorPreset s ? s.fusheqiangdu : 1) ;
        for (int x = f.tile.x - f.radiusTile() ; x <= f.tile.x + f.radiusTile() ; x ++) {
            for (int y = f.tile.y - f.radiusTile() ; y <= f.tile.y + f.radiusTile() ; y ++) {
                Tile t = world.tile(x, y) ;
                if (t == null) {
                    continue ;
                }
                cons.get(t, ccqd) ;
            }
        }
    }
    public static <T> T random(Rand rand, T... array){
        if(array.length == 0) return null;
        return array[rand.random(array.length - 1)];
    }
    public static <T> T random(T... array){
        if(array.length == 0) return null;
        return array[r.random(array.length - 1)];
    }
    public static <T> T[] filter(Class<T> type, T[] array, Boolf<T> value){
        Seq<T> out = new Seq<>(true, array.length, type);
        for(T t : array){
            if(value.get(t)) out.add(t);
        }
        return out.toArray();
    }
    public static void removeContentName(MappableContent m) {
        ((ObjectMap<String, MappableContent>[])getPrivateField(content, "contentNameMap"))[m.getContentType().ordinal()].remove(m.name) ;
    }
    public static void removeContent(MappableContent m) {
        removeContentName(m);
        content.getContentMap()[m.getContentType().ordinal()].remove(m) ;
    }
    public static Vec2 rotate(Vec2 source, float angle, float posX, float posY) {
        return circle(Angles.angle(posX, posY, source.x, source.y) - angle, source.dst(posX, posY), posX, posY) ;
    }
    public static Vec2 rotate(float sourceX, float sourceY, float angle, float posX, float posY) {
        return rotate(new Vec2(sourceX, sourceY), angle, posX, posY) ;
    }
    public static <T> Seq<T> filter(Seq<T> source, Boolf<T> check) {
        Seq<T> out =  new Seq<>() ;
        for (T t : source) {
            if (check.get(t)) {
                out.add(t) ;
            }
        }
        return out ;
    }


    public static class SideBar extends Element {
        public final Floatp amount;
        public final boolean flip;
        public final Boolp flash;

        float last, blink, value;

        public SideBar(Floatp amount, Boolp flash, boolean flip){
            this.amount = amount;
            this.flip = flip;
            this.flash = flash;

            update(() -> setColor(state.rules.waveTeam.color));
        }

        @Override
        public void draw(){
            float next = amount.get();

            if(Float.isNaN(next) || Float.isInfinite(next)) next = 1f;

            if(next < last && flash.get()){
                blink = 1f;
            }

            blink = Mathf.lerpDelta(blink, 0f, 0.2f);
            value = Mathf.lerpDelta(value, next, 0.15f);
            last = next;

            if(Float.isNaN(value) || Float.isInfinite(value)) value = 1f;

            drawInner(Pal.darkishGray, 1f);
            drawInner(Tmp.c1.set(color).lerp(Color.white, blink), value);
        }

        void drawInner(Color color, float fract){
            if(fract < 0) return;

            fract = Mathf.clamp(fract);
            if(flip){
                x += width;
                width = -width;
            }

            float stroke = width * 0.35f;
            float bh = height/2f;
            Draw.color(color, parentAlpha);

            float f1 = Math.min(fract * 2f, 1f), f2 = (fract - 0.5f) * 2f;

            float bo = -(1f - f1) * (width - stroke);

            Fill.quad(
                    x, y,
                    x + stroke, y,
                    x + width + bo, y + bh * f1,
                    x + width - stroke + bo, y + bh * f1
            );

            if(f2 > 0){
                float bx = x + (width - stroke) * (1f - f2);
                Fill.quad(
                        x + width, y + bh,
                        x + width - stroke, y + bh,
                        bx, y + height * fract,
                        bx + stroke, y + height * fract
                );
            }

            Draw.reset();

            if(flip){
                width = -width;
                x -= width;
            }
        }
    }


    @Override
    public void write(DataOutput stream) throws IOException {
        //stream.writeInt(firstClick);
        stream.writeInt(controlingUnitIds.size);
        for (int id : controlingUnitIds) {
            stream.writeInt(id);
        }
//        stream.writeInt(zengyuanboci);
//        stream.writeFloat(zengyuanjishi);
        if (control.saves.getCurrent() != null) {
            Fi f = data.child("saves").child(control.saves.getCurrent().file.name()) ;
            Fi f1 = f.child(control.saves.getCurrent().file.name() + ".dat") ;
            /*
            OutputStream os = new FastDeflaterOutputStream(f.write(false, bufferSize)) ;
            DataOutputStream stream2 = new DataOutputStream(os) ;
            stream2.writeInt(zengyuanboci) ;
            stream2.writeFloat(zengyuanjishi) ;
            */
            f1.writeString(zengyuanboci + " ", false) ;
            f1.writeString(zengyuanjishi + " ", true);
            f1.writeString((current != null ? current.id : "none") + "", true);
            Fi f2 = f.child("units") ;
            for (Unit u : Groups.unit) {
                Fi f3 = f2.child(u.id + ".dat") ;
                for (int i = 0 ; i < shuxings ; i ++) {
                    float fl = beilvs.get(i).get(u, 0f) ;
                    f3.writeString(fl + (i == shuxings -1 ? "" : " "), i != 0);
                }
            }
        }
        if (!control.saves.isSaving()) {
            controlingUnits.clear();
            controlingUnitIds.clear();
            zengyuanboci = 1;
            zengyuanjishi = 0 ;
            current = null ;
            inited = false ;
        }
    }

    @Override
    public void read(DataInput stream) throws IOException {
        //firstClick = stream.readInt() ;
        int size = stream.readInt();
        //System.out.println(size);
        for (int i = 0 ; i < size ; i ++) {
            int id = stream.readInt() ;
            controlingUnitIds.add(id) ;
            controlingUnits.add(Groups.unit.getByID(id)) ;
            //fixedPrintln("", Groups.unit.getByID(id).type.localizedName);
        }
//        zengyuanboci = stream.readInt();
//        zengyuanjishi = stream.readFloat();
    }
}
