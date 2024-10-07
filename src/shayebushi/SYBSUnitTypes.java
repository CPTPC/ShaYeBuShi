package shayebushi;

import arc.Core;
import arc.files.Fi;
import arc.files.ZipFi;
import arc.graphics.Color;
import arc.graphics.Pixmap;
import arc.graphics.Pixmaps;
import arc.graphics.Texture;
import arc.graphics.g2d.*;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Strings;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.ai.types.*;
import mindustry.audio.SoundLoop;
import mindustry.content.*;
import mindustry.core.Version;
import mindustry.core.World;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.bullet.FlakBulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.entities.bullet.ShrapnelBulletType;
import mindustry.entities.effect.ExplosionEffect;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.effect.WrapEffect;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.MultiPacker;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.type.ammo.PowerAmmoType;
import mindustry.type.unit.ErekirUnitType;
import mindustry.type.unit.MissileUnitType;
import mindustry.type.weapons.PointDefenseWeapon;
import mindustry.type.weapons.RepairBeamWeapon;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.meta.*;
import shayebushi.ai.types.FlyingSuicideAI;
import shayebushi.entities.abilities.*;
import shayebushi.entities.bullet.*;
import shayebushi.entities.units.*;
import shayebushi.entities.weapons.RepairWaveWeapon;
import shayebushi.entities.weapons.TeShuWeapon;
import shayebushi.entities.weapons.TuiJinQiWeapon;
import shayebushi.type.unit.*;

import java.lang.reflect.Field;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;
import static arc.math.Interp.pow10Out;
import static arc.util.Time.*;
import static mindustry.Vars.*;
import static mindustry.content.Fx.*;
import static mindustry.content.UnitTypes.*;
import static shayebushi.SYBSPal.*;
import static shayebushi.SYBSPal.chaokongjian1;
import static shayebushi.SYBSStatusEffects.kuiluan;
import static shayebushi.SYBSStatusEffects.shuaibian;
//import static mindustry.gen.EntityMapping.nameMap;

public class SYBSUnitTypes {
    public static UnitType xieling ;
    public static UnitType shenqi , cptpc, qianneng, jianglin, tianping, wuji ;
    public static UnitType shuangxingyijieduan, daodanxing, jiguangxing,shuangxingerjieduan, duoxing ;
    public static UnitType deerta, pi ;
    public static UnitType shengling, moneng;
    public static UnitType anye , shamie , lingjian , yaofeng, z01, affh01, yeguang, gen, zhen, kan, qian;
    public static UnitType anyong , wsz01, chichao, shuihua;
    public static
    //@Annotations.EntityDef(value = {Unitc.class, WaterMovec.class, Payloadc.class}, legacy = true)
    UnitType chengbang ;
    public static XianShangUnitType xiuji, jiyuan, huanyu;
    static {
        EntityMapping.idMap[113] = XianShangUnitEntity::new ;
        EntityMapping.idMap[115] = DieTaiUnitEntity::new ;
        EntityMapping.idMap[116] = ZhanJiUnitEntity::new ;
        EntityMapping.idMap[118] = OwnerLegsUnit::new ;
        EntityMapping.idMap[117] = WaterMovePayLoadUnit::create ;
        EntityMapping.idMap[120] = QiangZhiXianShangUnitEntity::new ;
        EntityMapping.idMap[122] = LinkLegsUnit::new ;
        EntityMapping.idMap[123] = ZhuTiLegsUnit::new ;
        EntityMapping.idMap[124] = WaterMoveXianShangUnit::new ;
        EntityMapping.idMap[125] = ZhuTiMechUnit::new ;
        EntityMapping.idMap[126] = OwnerUnitEntity::new ;
        EntityMapping.nameMap.put("shayebushi-xieling", EntityMapping.idMap[24]);
        EntityMapping.nameMap.put("shayebushi-shenqi", EntityMapping.idMap[4]);
        EntityMapping.nameMap.put("shayebushi-shuangxingyijieduan", EntityMapping.idMap[24]);
        EntityMapping.nameMap.put("shayebushi-shuangxingerjieduan", EntityMapping.idMap[123]);
        EntityMapping.nameMap.put("shuangxingerjieduan", EntityMapping.idMap[123]);
        EntityMapping.nameMap.put("shayebushi-deerta", EntityMapping.idMap[31]);
        EntityMapping.nameMap.put("shayebushi-anye", EntityMapping.idMap[3]);
        EntityMapping.nameMap.put("shayebushi-shamie", EntityMapping.idMap[3]);
        EntityMapping.nameMap.put("shayebushi-lingjian", EntityMapping.idMap[3]);
        EntityMapping.nameMap.put("shayebushi-yaofeng", EntityMapping.idMap[3]);
        EntityMapping.nameMap.put("shayebushi-shengling", EntityMapping.idMap[26]);
        EntityMapping.nameMap.put("shayebushi-moneng", EntityMapping.idMap[26]);
        EntityMapping.nameMap.put("shayebushi-xiuji", EntityMapping.idMap[113]);
        EntityMapping.nameMap.put("shayebushi-anyong", EntityMapping.idMap[20]);
        EntityMapping.nameMap.put("shayebushi-cptpc", EntityMapping.idMap[120]);
        EntityMapping.nameMap.put("shayebushi-xiuji", XianShangUnitEntity::new);
        EntityMapping.nameMap.put("shayebushi-z01", EntityMapping.idMap[116]);
        EntityMapping.nameMap.put("shayebushi-affh01", EntityMapping.idMap[116]);
        EntityMapping.nameMap.put("shayebushi-chengbang", EntityMapping.idMap[117]);
        EntityMapping.nameMap.put("shayebushi-qianneng", EntityMapping.idMap[4]);
        EntityMapping.nameMap.put("shayebushi-jianglin", EntityMapping.idMap[4]);
        EntityMapping.nameMap.put("shayebushi-duoxing", EntityMapping.idMap[118]);
        EntityMapping.nameMap.put("shayebushi-daodanxing", EntityMapping.idMap[122]);
        EntityMapping.nameMap.put("shayebushi-jiguangxing", EntityMapping.idMap[122]);
        EntityMapping.nameMap.put("shayebushi-tianping", EntityMapping.idMap[4]);
        //EntityMapping.nameMap.put("tianping", EntityMapping.idMap[113]);
        EntityMapping.nameMap.put("shayebushi-chichao", EntityMapping.idMap[20]);
        EntityMapping.nameMap.put("shayebushi-shuihua", EntityMapping.idMap[20]);
        EntityMapping.nameMap.put("shayebushi-yeguang", EntityMapping.idMap[4]);
        EntityMapping.nameMap.put("shayebushi-jiyuan", EntityMapping.idMap[124]) ;
        EntityMapping.nameMap.put("shayebushi-wuji", EntityMapping.idMap[125]) ;
        EntityMapping.nameMap.put("shayebushi-gen", EntityMapping.idMap[126]) ;
        EntityMapping.nameMap.put("shayebushi-kan", EntityMapping.idMap[126]) ;
        EntityMapping.nameMap.put("shayebushi-zhen", EntityMapping.idMap[126]) ;
        EntityMapping.nameMap.put("shayebushi-qian", EntityMapping.idMap[126]) ;
        EntityMapping.nameMap.put("shayebushi-huanyu", EntityMapping.idMap[114]);
        EntityMapping.nameMap.put("shayebushi-pi", EntityMapping.idMap[113]);



        EntityMapping.nameMap.put("xieling", EntityMapping.idMap[24]);
        EntityMapping.nameMap.put("shenqi", EntityMapping.idMap[4]);
        EntityMapping.nameMap.put("shuangxingyijieduan", EntityMapping.idMap[24]);
        EntityMapping.nameMap.put("shuangxingerjieduan", EntityMapping.idMap[123]);
        EntityMapping.nameMap.put("shuangxingerjieduan", EntityMapping.idMap[123]);
        EntityMapping.nameMap.put("deerta", EntityMapping.idMap[31]);
        EntityMapping.nameMap.put("anye", EntityMapping.idMap[3]);
        EntityMapping.nameMap.put("shamie", EntityMapping.idMap[3]);
        EntityMapping.nameMap.put("lingjian", EntityMapping.idMap[3]);
        EntityMapping.nameMap.put("yaofeng", EntityMapping.idMap[3]);
        EntityMapping.nameMap.put("shengling", EntityMapping.idMap[26]);
        EntityMapping.nameMap.put("moneng", EntityMapping.idMap[26]);
        EntityMapping.nameMap.put("xiuji", EntityMapping.idMap[113]);
        EntityMapping.nameMap.put("anyong", EntityMapping.idMap[20]);
        EntityMapping.nameMap.put("cptpc", EntityMapping.idMap[120]);
        EntityMapping.nameMap.put("xiuji", XianShangUnitEntity::new);
        EntityMapping.nameMap.put("xiuji", EntityMapping.idMap[113]);
        EntityMapping.nameMap.put("z01", EntityMapping.idMap[116]);
        EntityMapping.nameMap.put("affh01", EntityMapping.idMap[116]);
        EntityMapping.nameMap.put("chengbang", EntityMapping.idMap[117]);
        EntityMapping.nameMap.put("qianneng", EntityMapping.idMap[4]);
        EntityMapping.nameMap.put("jianglin", EntityMapping.idMap[4]);
        EntityMapping.nameMap.put("duoxing", EntityMapping.idMap[118]);
        EntityMapping.nameMap.put("daodanxing", EntityMapping.idMap[122]);
        EntityMapping.nameMap.put("jiguangxing", EntityMapping.idMap[122]);
        EntityMapping.nameMap.put("tianping", EntityMapping.idMap[4]);
        EntityMapping.nameMap.put("chichao", EntityMapping.idMap[20]);
        EntityMapping.nameMap.put("shuihua", EntityMapping.idMap[20]);
        EntityMapping.nameMap.put("yeguang", EntityMapping.idMap[4]);
        EntityMapping.nameMap.put("jiyuan", EntityMapping.idMap[124]) ;
        EntityMapping.nameMap.put("wuji", EntityMapping.idMap[125]) ;
        EntityMapping.nameMap.put("gen", EntityMapping.idMap[126]) ;
        EntityMapping.nameMap.put("kan", EntityMapping.idMap[126]) ;
        EntityMapping.nameMap.put("zhen", EntityMapping.idMap[126]) ;
        EntityMapping.nameMap.put("qian", EntityMapping.idMap[126]) ;
        EntityMapping.nameMap.put("huanyu", EntityMapping.idMap[114]);
        EntityMapping.nameMap.put("pi", EntityMapping.idMap[113]);
        //nameMap.put("chengbang", PayloadUnit::create) ;
    }
    public static Seq<StatusEffect> statuses;
    public static Seq<UnitType> shendijidanwei = new Seq<>();
    public static Seq<UnitType> shenwangjidanwei = new Seq<>();
    public static Seq<UnitType> shenlingjidanwei = new Seq<>();
    //@SafeVarargs
    @SuppressWarnings("deprecation")
    public static void immunise(UnitType type,boolean isyange,float yangeamount){
        if(statuses == null){
            statuses = Vars.content.statusEffects().copy();
//            for (StatusEffect ss : statuses){
//                System.out.println(ss.localizedName);
//            }
            statuses.select(ShaYeBuShi::isJianYi);
            statuses.add(StatusEffects.wet);
        }
        if (!isyange) {
            type.immunities.addAll(statuses);
        }
        else{
            for (int i = 0 ; i <= (int)(statuses.size/yangeamount);i++){
                StatusEffect s = statuses.get(ShaYeBuShi.r.random(0, statuses.size - 1)) ;
                while (type.immunities.contains(s)) {
                    s = statuses.get(ShaYeBuShi.r.random(0, statuses.size - 1)) ;
                }
                type.immunities.add(s) ;
            }
        }
    }
    public static void load() {
        if (Version.build == -1 || ShaYeBuShi.tiaoshi) {
            content.units().each(u -> {
                if (u.minfo.mod == null || u.health >= 18000) {
                    //u.health *= 2.5f;
                    //u.armor *= 2.5f ;
                    u.weapons.each(w -> {
                        w.reload /= 1.5f;
                        w.bullet.damage *= 1.5f;
                    });
                }
            });
            corvus.weapons.first().bullet.damage = 960;
            corvus.weapons.first().reload /= 1.25f;
            corvus.weapons.first().bullet.recoil = 3f;
            corvus.speed *= 1.25f;
            reign.weapons.add(new Weapon("sybs-wangzuo-fupao") {{
                reload = 12f;
                y = 5f;
                x = 12.5f;
                recoil = 0;
                bullet = new MissileBulletType() {{
                    homingPower *= 2;
                    damage = 60f;
                    trailColor = Pal.unitBack;
                    backColor = Pal.unitBack;
                    frontColor = Pal.unitFront;
                    hitEffect = Fx.blastExplosion;
                    despawnEffect = Fx.blastExplosion;
                    weaveScale = 6f;
                    weaveMag = 1f;
                    speed *= 4;
                }};
                rotate = true;
                recoil = 2f;
                shootSound = Sounds.missile;
                shake = 1.45f;
                inaccuracy = 10f;
            }});
            reign.weapons.each(w -> w.bullet.speed *= 1.5f);
            //shenqi.weapons.get(2).region = reign.weapons.first().region;
            toxopid.stepShake *= 2;
            toxopid.legSplashDamage *= 6;
            toxopid.weapons.each(w -> {
                w.reload /= 1.5f;
                if (w.bullet instanceof ShrapnelBulletType s) {
                    s.length *= 1.5f;
                } else {
                    w.bullet.speed *= 1.5f;
                }
            });
            toxopid.speed *= 1.5f;
            toxopid.legSpeed *= 1.5f;
            eclipse.speed *= 1.5f;
            Weapon ec1 = eclipse.weapons.get(0).copy();
            ec1.x = 11;
            ec1.y = -27;
            ((LaserBulletType) ec1.bullet).length *= 1.25f;
            eclipse.weapons.add(ec1);
            eclipse.weapons.get(1).reload /= 1.25f;
            eclipse.weapons.get(2).reload /= 1.25f;
            ((ForceFieldAbility) oct.abilities.get(0)).regen *= 1.25f;
            ((ForceFieldAbility) oct.abilities.get(0)).cooldown /= 1.25f;
            ((RepairFieldAbility) oct.abilities.get(1)).amount *= 4;
        }
        xieling = new UnitType("xieling") {
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            rotateSpeed = 180 ;
            drag = 0.1f;
            speed = 0.4f;
            hitSize = 34f;
            health = 66000;
            armor = 22f;
            lightRadius = 140f;
            rotateSpeed = 1.9f;
            drownTimeMultiplier = 3f;
            abilities.addAll(new ForceFieldAbility(40f, 4.0f, 4500f, 60f * 6,3,180),new ForceFieldAbility(40f, 4.0f, 4500f, 60f * 6,3,0),new UnitSpawnAbility(spiroct, 300f, 0f, -7f));
            legCount = 10;
            legMoveSpace = 0.8f;
            legPairOffset = 3;
            legLength = 100f;
            legExtension = -20;
            legBaseOffset = 8f;
            stepShake = 1f;
            legLengthScl = 0.93f;
            rippleScale = 3f;
            legSpeed = 0.19f;
            ammoType = new ItemAmmoType(Items.graphite, 8);
            itemCapacity = 225 ;
            legSplashDamage = 80;
            legSplashRange = 60;

            hovering = true;
            shadowElevation = 0.95f;
            groundLayer = Layer.legUnit;

            weapons.add(
                    new Weapon("xieling-fupao") {{
                        y = -5f;
                        x = 11f;
                        top = false;
                        shootY = 7f;
                        reload = 30;
                        shake = 4f;
                        rotateSpeed = 2f;
                        ejectEffect = Fx.casing1;
                        shootSound = Sounds.shootBig;
                        rotate = true;
                        shadow = 12f;
                        recoil = 3f;
                        range = 100 ;
                        shoot = new ShootSpread(6, 6f);

                        bullet = new ShrapnelBulletType(){
                            @Override
                            public void hitEntity(Bullet b, Hitboxc entity, float health){
                                super.hitEntity(b,entity,health);
                                if (b.owner instanceof Healthc h){
                                    h.heal(b.damage * 0.2f) ;
                                }
                            }
                            @Override
                            public void hitTile(Bullet b, Building build, float x, float y, float initialHealth, boolean direct){
                                super.hitTile(b,build,x,y,initialHealth,direct) ;
                                if (b.owner instanceof Healthc h){
                                    h.heal(b.damage * 0.2f) ;
                                }
                            }
                            {
                            length = 180f;
                            damage = 95f;
                            width = 25f;
                            serrationLenScl = 7f;
                            serrationSpaceOffset = 60f;
                            serrationFadeOffset = 0f;
                            serrations = 10;
                            serrationWidth = 6f;
                            fromColor = Pal.sapBullet;
                            toColor = Pal.sapBulletBack;
                            shootEffect = smokeEffect = Fx.sparkShoot;
                            status = StatusEffects.sapped ;
                            //lifetime = 100 ;
                        }};
                    }});

            weapons.add(new Weapon("xieling-zhupao") {{
                y = 7f;
                x = 0f;
                shootY = 22f;
                mirror = false;
                reload = 4.5f;
                shake = 2f;
                rotateSpeed = 1f;
                ejectEffect = Fx.casing3;
                shootSound = Sounds.malignShoot;
                rotate = true;
                shadow = 30f;
                top = false;
                rotationLimit = 80f;
                Color haloColor = Pal.sapBullet ;
                var circleColor = haloColor;
                bullet = new FlakBulletType(8f, 100f){
                    @Override
                    public void hitEntity(Bullet b, Hitboxc entity, float health){
                        super.hitEntity(b,entity,health);
                        if (b.owner instanceof Healthc h){
                            h.heal(b.damage * 0.2f) ;
                        }
                    }
                    @Override
                    public void hitTile(Bullet b, Building build, float x, float y, float initialHealth, boolean direct){
                        super.hitTile(b,build,x,y,initialHealth,direct) ;
                        if (b.owner instanceof Healthc h){
                            h.heal(b.damage * 0.2f) ;
                        }
                    }
                    {
                    sprite = "missile-large";
                    status = StatusEffects.sapped ;
                    lifetime = 40f;
                    width = 12f;
                    height = 22f;

                    hitSize = 7f;
                    shootEffect = Fx.shootSmokeSquareBig;
                    smokeEffect = Fx.shootSmokeDisperse;
                    ammoMultiplier = 1;
                    hitColor = backColor = trailColor = lightningColor = circleColor;
                    frontColor = Color.white;
                    trailWidth = 3f;
                    trailLength = 12;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    buildingDamageMultiplier = 0.5f;
                    buildingDamageMultiplier = 1f;

                    trailEffect = Fx.colorSpark;
                    trailRotation = true;
                    trailInterval = 3f;
                    lightning = 1;
                    lightningCone = 15f;
                    lightningLength = 20;
                    lightningLengthRand = 30;
                    lightningDamage = 50f;
                    homingPower = 0.17f;
                    homingDelay = 19f;
                    homingRange = 160f;

                    explodeRange = 160f;
                    explodeDelay = 0f;

                    flakInterval = 20f;
                    despawnShake = 3f;


                    fragSpread = fragRandomSpread = 0f;

                    splashDamage = 0f;
                    hitEffect = Fx.hitSquaresColor;
                    collidesGround = true;
                }};
                var circleProgress = DrawPart.PartProgress.warmup.delay(0.9f);
                float circleY = 25f, circleRad = 11f, circleRotSpeed = 3.5f, circleStroke = 1.6f;
                float haloY = -15f, haloRotSpeed = 1.5f;
                parts.addAll(
                        new ShapePart(){{
                            progress = circleProgress;
                            color = circleColor;
                            circle = true;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad;
                            layer = Layer.effect;
                            y = circleY;
                        }},

                        new ShapePart(){{
                            progress = circleProgress;
                            rotateSpeed = -circleRotSpeed;
                            color = circleColor;
                            sides = 4;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad - 1f;
                            layer = Layer.effect;
                            y = circleY;
                        }},

                        //outer squares

                        new ShapePart(){{
                            progress = circleProgress;
                            rotateSpeed = -circleRotSpeed;
                            color = circleColor;
                            sides = 4;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad - 1f;
                            layer = Layer.effect;
                            y = circleY;
                        }},

                        //inner square
                        new ShapePart(){{
                            progress = circleProgress;
                            rotateSpeed = -circleRotSpeed/2f;
                            color = circleColor;
                            sides = 4;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = 2f;
                            radius = 3f;
                            layer = Layer.effect;
                            y = circleY;
                        }},

                        //spikes on circle
                        new HaloPart(){{
                            progress = circleProgress;
                            color = circleColor;
                            tri = true;
                            shapes = 3;
                            triLength = 0f;
                            triLengthTo = 5f;
                            radius = 6f;
                            haloRadius = circleRad;
                            haloRotateSpeed = haloRotSpeed / 2f;
                            shapeRotation = 180f;
                            haloRotation = 180f;
                            layer = Layer.effect;
                            y = circleY;
                        }}
                );
            }});
            weapons.add(new Weapon("xieling-fuzhupao"){{
                rotate = true;
                mirror = false ;
                x = 0f;
                y = -26f/4f;
                top = false;
                reload = 120f;
                shake = 3f;
                rotateSpeed = 2f;
                shadow = 30f;
                shootY = 7f;
                recoil = 4f;
                cooldownTime = reload - 10f;
                /*//TODO better sound//*/
                shootSound = Sounds.laser;
                shoot = new ShootSpread(3, 5f);
                range = 300 ;
                bullet = new mindustry.entities.bullet.EmpBulletType(){
                    @Override
                    public void hitEntity(Bullet b, Hitboxc entity, float health){
                        super.hitEntity(b,entity,health);
                        if (b.owner instanceof Healthc h){
                            h.heal(b.damage * 0.2f) ;
                        }
                    }
                    @Override
                    public void hitTile(Bullet b, Building build, float x, float y, float initialHealth, boolean direct){
                        super.hitTile(b,build,x,y,initialHealth,direct) ;
                        if (b.owner instanceof Healthc h){
                            h.heal(b.damage * 0.2f) ;
                        }
                    }
                    {
                    float rad = 100f;
                    scaleLife = true;
                    lightOpacity = 0.7f;
                    unitDamageScl = 0.8f;
                    //healPercent = 20f;
                    timeIncrease = 3f;
                    timeDuration = 60f * 20f;
                    powerDamageScl = 3f;
                    damage = 400;
                    buildingDamageMultiplier = 1.25f ;
                    buildingDamageMultiplier = 1f;
                    hitColor = lightColor = Pal.sapBullet;
                    lightRadius = 70f;
                    clipSize = 250f;
                    shootEffect = Fx.hitEmpSpark;
                    smokeEffect = Fx.shootBigSmoke2;
                    lifetime = 64f;
                    sprite = "circle-bullet";
                    backColor = Pal.sapBullet;
                    frontColor = Color.white;
                    width = height = 12f;
                    shrinkY = 0f;
                    speed = 5f;
                    trailLength = 20;
                    trailWidth = 6f;
                    trailColor = Pal.sapBullet;
                    trailInterval = 3f;
                    splashDamageRadius = rad;
                    splashDamage = 400f;
                    hitShake = 4f;
                    trailRotation = true;
                    status = StatusEffects.sapped ;
                    hitSound = Sounds.plasmaboom;

                    trailEffect = new Effect(16f, e -> {
                        color(Pal.sapBullet);
                        for(int s : Mathf.signs){
                            Drawf.tri(e.x, e.y, 4f, 30f * e.fslope(), e.rotation + 90f*s);
                        }
                    });

                    hitEffect = new Effect(50f, 100f, e -> {
                        e.scaled(7f, b -> {
                            color(Pal.sapBullet, b.fout());
                            Fill.circle(e.x, e.y, rad);
                        });

                        color(Pal.sapBullet);
                        stroke(e.fout() * 3f);
                        Lines.circle(e.x, e.y, rad);

                        int points = 10;
                        float offset = Mathf.randomSeed(e.id, 360f);
                        for(int i = 0; i < points; i++){
                            float angle = i* 360f / points + offset;
                            //for(int s : Mathf.zeroOne){
                            Drawf.tri(e.x + Angles.trnsx(angle, rad), e.y + Angles.trnsy(angle, rad), 6f, 50f * e.fout(), angle/* + s*180f*/);
                            //}
                        }

                        Fill.circle(e.x, e.y, 12f * e.fout());
                        color();
                        Fill.circle(e.x, e.y, 6f * e.fout());
                        Drawf.light(e.x, e.y, rad * 1.6f, Pal.heal, e.fout());
                    });
                }};
            }});
        }};
        shenqi = new UnitType("shenqi"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            speed = 0.35f;
            hitSize = 36f;
            rotateSpeed = 1.65f;
            health = 72000;
            armor = 24f;
            mechStepParticles = true;
            stepShake = 0.75f;
            drownTimeMultiplier = 6f;
            mechFrontSway = 1.9f;
            mechSideSway = 0.6f;
            ammoType = new ItemAmmoType(Items.thorium);

            weapons.add(
                    new Weapon("shenqi-zhupao"){{
                        top = false;
                        mirror = false;
                        y = -6f;
                        x = 0f;
                        shootY = 11f;
                        reload = 120f;
                        recoil = 5f;
                        shake = 2f;
                        range = 300f ;
                        ejectEffect = Fx.casing4;
                        shootSound = Sounds.shootSmite;
                        var haloProgress = DrawPart.PartProgress.warmup.delay(0.5f);
                        float haloY = -15f, haloRotSpeed = 1f;
                        parts.addAll(
                                new ShapePart(){{
                                    progress = PartProgress.warmup.delay(0.2f);
                                    color = Pal.accent;
                                    circle = true;
                                    hollow = true;
                                    stroke = 0f;
                                    strokeTo = 2f;
                                    radius = 10f;
                                    layer = Layer.effect;
                                    y = haloY;
                                    rotateSpeed = haloRotSpeed;
                                }},
                                new ShapePart(){{
                                    progress = PartProgress.warmup.delay(0.2f);
                                    color = Pal.accent;
                                    circle = true;
                                    hollow = true;
                                    stroke = 0f;
                                    strokeTo = 1.6f;
                                    radius = 4f;
                                    layer = Layer.effect;
                                    y = haloY;
                                    rotateSpeed = haloRotSpeed;
                                }},
                                new HaloPart(){{
                                    progress = haloProgress;
                                    color = Pal.accent;
                                    layer = Layer.effect;
                                    y = haloY;

                                    haloRotation = 90f;
                                    shapes = 2;
                                    triLength = 0f;
                                    triLengthTo = 20f;
                                    haloRadius = 16f;
                                    tri = true;
                                    radius = 4f;
                                }},
                                new HaloPart(){{
                                    progress = haloProgress;
                                    color = Pal.accent;
                                    layer = Layer.effect;
                                    y = haloY;

                                    haloRotation = 90f;
                                    shapes = 2;
                                    triLength = 0f;
                                    triLengthTo = 5f;
                                    haloRadius = 16f;
                                    tri = true;
                                    radius = 4f;
                                    shapeRotation = 180f;
                                }},
                                new HaloPart(){{
                                    progress = haloProgress;
                                    color = Pal.accent;
                                    layer = Layer.effect;
                                    y = haloY;
                                    haloRotateSpeed = -haloRotSpeed;

                                    shapes = 4;
                                    triLength = 0f;
                                    triLengthTo = 5f;
                                    haloRotation = 45f;
                                    haloRadius = 16f;
                                    tri = true;
                                    radius = 8f;
                                }},
                                new HaloPart(){{
                                    progress = haloProgress;
                                    color = Pal.accent;
                                    layer = Layer.effect;
                                    y = haloY;
                                    haloRotateSpeed = -haloRotSpeed;

                                    shapes = 4;
                                    shapeRotation = 180f;
                                    triLength = 0f;
                                    triLengthTo = 2f;
                                    haloRotation = 45f;
                                    haloRadius = 16f;
                                    tri = true;
                                    radius = 8f;
                                }},
                                new HaloPart(){{
                                    progress = haloProgress;
                                    color = Pal.accent;
                                    layer = Layer.effect;
                                    y = haloY;
                                    haloRotateSpeed = haloRotSpeed;

                                    shapes = 4;
                                    triLength = 0f;
                                    triLengthTo = 3f;
                                    haloRotation = 45f;
                                    haloRadius = 10f;
                                    tri = true;
                                    radius = 6f;
                                }}
                        );
                        shoot = new ShootMulti(new ShootAlternate(){{
                            spread = 3.3f * 1.9f;
                            shots = barrels = 5;
                        }}, new ShootHelix(){{
                            scl = 4f;
                            mag = 3f;
                        }});
                        shake = 5f ;
                        bullet = new BasicBulletType(7,220){{
                            sprite = "large-orb";
                            width = 17f;
                            height = 21f;
                            hitSize = 8f;

                            shootEffect = new MultiEffect(Fx.shootTitan, Fx.colorSparkBig, new WaveEffect(){{
                                colorFrom = colorTo = Pal.accent;
                                lifetime = 12f;
                                sizeTo = 20f;
                                strokeFrom = 3f;
                                strokeTo = 0.3f;
                            }});
                            smokeEffect = Fx.shootSmokeSmite;
                            ammoMultiplier = 1;
                            pierceCap = 4;
                            pierce = true;
                            pierceBuilding = true;
                            hitColor = backColor = trailColor = Pal.accent;
                            frontColor = Color.white;
                            trailWidth = 2.8f;
                            trailLength = 9;
                            hitEffect = Fx.hitBulletColor;
                            buildingDamageMultiplier = 0.3f;
                            buildingDamageMultiplier = 1f;
                            despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect(){{
                                sizeTo = 30f;
                                colorFrom = colorTo = Pal.accent;
                                lifetime = 12f;
                            }});

                            trailRotation = true;
                            trailEffect = Fx.disperseTrail;
                            trailInterval = 3f;

                            intervalBullet = new LightningBulletType(){{
                                damage = 50;
                                collidesAir = false;
                                ammoMultiplier = 1f;
                                lightningColor = Pal.accent;
                                lightningLength = 5;
                                lightningLengthRand = 10;

                                //for visual stats only.
                                buildingDamageMultiplier = 0.25f;
                                buildingDamageMultiplier = 1f;

                                lightningType = new BulletType(0.0001f, 0f){{
                                    lifetime = Fx.lightning.lifetime;
                                    hitEffect = Fx.hitLancer;
                                    despawnEffect = Fx.none;
                                    status = StatusEffects.shocked;
                                    statusDuration = 10f;
                                    hittable = false;
                                    lightColor = Color.white;
                                    buildingDamageMultiplier = 0.25f;
                                }};
                            }};

                            bulletInterval = 3f;
                        }};
                    }}
            );
            weapons.add(new Weapon("shenqi-fuzhupao"){{
                top = false;
                mirror = false ;
                x = 0 ;
                shootSound = Sounds.missileLarge;
                //loopSound = Sounds.beam;
                //loopSoundVolume = 2f;
                shootY = 2f;
                reload = 240f;
                recoil = 1f;
                ejectEffect = Fx.none;
                range = 320f ;
                //shootStatus = StatusEffects.unmoving ;
                shoot.shots = 10 ;
                shoot.shotDelay = 6 ;
                //shootStatusDuration = 128f ;
                bullet = new BulletType(){{
                    shootEffect = Fx.sparkShoot;
                    smokeEffect = Fx.shootSmokeTitan;
                    hitColor = Pal.accent;
                    shake = 3f;
                    speed = 0f;
                    keepVelocity = false;
                    //collidesAir = false;
                    spawnUnit = new MissileUnitType("shenqi-daodan"){{
                        //targetAir = false;
                        speed = 4.6f;
                        maxRange = 5f;
                        outlineColor = Pal.accent;
                        health = 460;
                        homingDelay = 10f;
                        lowAltitude = true;
                        engineSize = 3f;
                        engineColor = trailColor = Pal.accent;
                        engineLayer = Layer.effect;
                        deathExplosionEffect = Fx.none;
                        loopSoundVolume = 0.1f;
                        lifetime = 40 / 4.6f * 8 ;
                        weapons.add(new Weapon(){{
                            shootCone = 360f;
                            mirror = false;
                            reload = 1f;
                            shootOnDeath = true;
                            bullet = new ExplosionBulletType(360f, 25f){{
                                collidesAir = false;
                                //suppressionRange = 140f;
                                shootEffect = new ExplosionEffect(){{
                                    lifetime = 50f;
                                    waveStroke = 5f;
                                    waveLife = 8f;
                                    waveColor = Color.white;
                                    sparkColor = smokeColor = Pal.accent;
                                    waveRad = 40f;
                                    smokeSize = 4f;
                                    smokes = 7;
                                    smokeSizeBase = 0f;
                                    sparks = 10;
                                    sparkRad = 40f;
                                    sparkLen = 6f;
                                    sparkStroke = 2f;
                                }};
                            }};
                        }});
                        weapons.add(new Weapon(){{
                            reload = 5f;
                            alwaysShooting = true;
                            bullet = new LaserBulletType(){{
                                damage = 10 ;
                                pierceArmor = true ;
                                length = 160 ;
                                colors[0] = Pal.accent.cpy().mul(1f, 1f, 1f, 0.4f) ;
                                colors[1] = Pal.accent ;
                            }};
                        }});
                    }};
                }};
            }});
            weapons.add(
                    new Weapon("shenqi-fupao"){{
                        top = false;
                        y = -5f;
                        x = 30f;
                        shootY = 11f;
                        reload = 7.5f;
                        recoil = 5f;
                        shake = 2f;
                        ejectEffect = Fx.casing4;
                        shootSound = Sounds.bang;

                        bullet = new BasicBulletType(13f, 80){{
                            pierce = true;
                            pierceCap = 10;
                            width = 14f;
                            height = 33f;
                            lifetime = 15f;
                            shootEffect = Fx.shootBig;
                            fragVelocityMin = 0.4f;

                            hitEffect = Fx.blastExplosion;
                            splashDamage = 18f;
                            splashDamageRadius = 13f;

                            fragBullets = 6;
                            fragLifeMin = 0f;
                            fragRandomSpread = 30f;

                            fragBullet = new BasicBulletType(9f, 20){{
                                width = 10f;
                                height = 10f;
                                pierce = true;
                                pierceBuilding = true;
                                pierceCap = 10;

                                lifetime = 20f;
                                hitEffect = Fx.flakExplosion;
                                splashDamage = 15f;
                                splashDamageRadius = 10f;
                            }};
                        }};
                    }}
            );
            /*
            for (Weapon w : weapons){
                w.region = Core.atlas.find(w.name,"error");
            }
            */
//            weapons.add(
//                    new Weapon("shenqi-ceshi"){{
//                        bullet = new PointLaserBulletType(){{
//                            //trailWidth = 20 ;
//                            //speed = 10 ;
//                            color = Pal.heal ;
//                            damage = 666.6666666666666666666666666666666666666666666666666666666666666666666666666666666f;
//                            //buildingDamageMultiplier = 0.3f;
//                            hitColor = Pal.heal ;
//                            healPercent = 10 ;
//                            shake = 0 ;
//                        }
//                            @Override
//                            public void draw(Bullet b){
//                                super.draw(b);
//                                float rx = Tmp.v1.x, ry = Tmp.v1.y;
//                                float curStroke = 5;
//                                curStroke = Mathf.lerpDelta(curStroke, true ? 1 : 0, 0.09f);
//                                Drawf.light(rx, ry, range * 1.5f, color, curStroke * 0.8f);
//                                Drawf.arrow(b.x,b.y,b.x+15,b.y+15,range,range,color);
//                            }
//                        };
//                    }}
//            );
        }};
        duoxing = new UnitType("duoxing") {
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            hitSize = 35f;
            health = 63000f;
            armor = 18f;
            stepShake = 2f;
            rotateSpeed = 1.8f;
            drownTimeMultiplier = 6f;
            useUnitCap = false ;
            legCount = 4;
            legLength = 14f;
            legBaseOffset = 11f;
            legMoveSpace = 1.5f;
            legForwardScl = 0.58f;
            hovering = true;
            shadowElevation = 0.2f;
            ammoType = new PowerAmmoType(4000);
            groundLayer = Layer.legUnit;

            speed = 0.5f;

            drawShields = false;
            abilities.add(new RepairFieldAbility(1250, 2 * toSeconds, 25 * tilesize)) ;
            weapons.add(new Weapon("duoxing-zhupao"){{
                shootSound = Sounds.laserblast;
                chargeSound = Sounds.lasercharge;
                soundPitchMin = 1f;
                top = false;
                mirror = false;
                shake = 14f;
                shootY = 5f;
                x = y = 0;
                reload = 400f;
                recoil = 0f;

                cooldownTime = 400f;

                shoot.firstShotDelay = Fx.greenLaserCharge.lifetime;
                parentizeEffects = true;

                bullet = new LaserBulletType(){{
                    length = 512f;
                    damage = 1080f;
                    width = 75f;

                    lifetime = 65f;

                    lightningSpacing = 35f;
                    lightningLength = 5;
                    lightningDelay = 1.1f;
                    lightningLengthRand = 15;
                    lightningDamage = 50;
                    lightningAngleRand = 40f;
                    largeHit = true;
                    lightColor = lightningColor = Pal.heal;

                    chargeEffect = Fx.greenLaserCharge;

                    healPercent = 30f;
                    collidesTeam = true;

                    sideAngle = 15f;
                    sideWidth = 0f;
                    sideLength = 0f;
                    buildingDamageMultiplier = 0.9f ;
                    buildingDamageMultiplier = 1f;
                    colors = new Color[]{Pal.heal.cpy().a(0.4f), Pal.heal, Color.white};
                }};
            }});
            weapons.add(new Weapon("duoxing-fupao"){
                @Override
                public void addStats(UnitType u , Table t){
                    if(inaccuracy > 0){
                        t.row();
                        t.add("[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int)inaccuracy + " " + StatUnit.degrees.localized());
                    }
                    if(!alwaysContinuous && reload > 0){
                        t.row();
                        t.add("[lightgray]" + Stat.reload.localized() + ": " + (mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / reload * shoot.shots, 2) + " " + StatUnit.perSecond.localized());
                    }
                    SYBSStatValues.ammo(ObjectMap.of(u, bullet)).display(t);
                }
                {
                mirror = true;
                top = false;
                shake = 4f;
                shootY = 14f;
                rotate = true ;
                Vec2 v = textureToReal(new Vec2(217, 113), 320, 310) ;
                x = v.x ;
                y = v.y ;

                shoot.firstShotDelay = Fx.greenLaserChargeSmall.lifetime - 1f;
                parentizeEffects = true;

                reload = 115f;
                recoil = 0f;
                chargeSound = Sounds.lasercharge2;
                shootSound = Sounds.beam;
                continuous = true;
                cooldownTime = 120f;
                bullet = new JiZengBulletType(20 / 6f){{
                    meimiaofanbeicishu = 1 ;
                    damageInterval = 1 ;
                    length = 32 * tilesize;
                    hitEffect = Fx.hitMeltHeal;
                    drawSize = 420f;
                    lifetime = 240f;
                    chixushijian = lifetime ;
                    shake = 1f;
                    despawnEffect = Fx.smokeCloud;
                    smokeEffect = Fx.none;
                    rangeOverride = 32 * 2 * tilesize ;
                    chargeEffect = Fx.greenLaserChargeSmall;

                    incendChance = 0.1f;
                    incendSpread = 5f;
                    incendAmount = 1;

                    //constant healing
                    healPercent = 0.8f;
                    collidesTeam = true;
                    buildingDamageMultiplier = 0.75f ;
                    buildingDamageMultiplier = 1f;

                    colors = new Color[]{Pal.heal.cpy().a(.2f), Pal.heal.cpy().a(.5f), Pal.heal.cpy().mul(1.2f), Color.white};
                    BulletType b = copy() ;
                    b.speed = 3 ;
                    //b.lifetime = 8 * tilesize / toSeconds ;
                    b.reflectable = false ;
                    fragBullets = 1 ;
                    fragBullet = b ;
                    fragRandomSpread = 0 ;
                    fragOnHit = false ;
                }};

                shootStatus = StatusEffects.slow;
                shootStatusDuration = bullet.lifetime + shoot.firstShotDelay;
            }});
        }};
        daodanxing = new UnitType("daodanxing"){{
            hitSize = 75f;
            health = 252000f;
            armor = 63f;
            stepShake = 1.5f;
            rotateSpeed = 1.5f;
            drownTimeMultiplier = 8f;
            buildSpeed = 4f;
            mineSpeed = 10 ;
            mineTier = 5 ;
            legCount = 6;
            legLength = 35f;
            legBaseOffset = 11f;
            legMoveSpace = 1.5f;
            legForwardScl = 0.58f;
            hovering = true;
            deathExplosionEffect = Fx.none;
            deathSound = Sounds.none;
            shadowElevation = 0.2f;
            ammoType = new PowerAmmoType(4000);
            groundLayer = Layer.legUnit;
            boostMultiplier = 6 ;
            canBoost = true ;
            speed = 0.25f;
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
            //playerControllable = false;
            //logicControllable = false ;
            immunise(this,true,3f);
            drawShields = false;
            parts.add(new FlarePart(){{
                progress = PartProgress.life.slope().curve(Interp.pow2In);
                radius = 0f;
                radiusTo = 45f;
                stroke = 3f;
                rotation = 45f;
                followRotation = true;
                color1 = Pal.heal ;
            }});
            weapons.addAll(new Weapon("daodanxing-zhupao"){{
                shootSound = Sounds.missileLarge;
                x = 78f / 8f;
                y = -10f / 4f;
                mirror = true;
                rotate = true;
                rotateSpeed = 0.4f;
                reload = 120f;
                layerOffset = -20f;
                recoil = 1f;
                rotationLimit = 22f;
                minWarmup = 0.95f;
                shootWarmupSpeed = 0.1f;
                shootY = 2f;
                shootCone = 40f;
                shoot.shots = 3;
                shoot.shotDelay = 5f;
                inaccuracy = 28f;

                bullet = new BulletType(){{
                    shootEffect = Fx.sparkShoot;
                    smokeEffect = Fx.shootSmokeTitan;
                    hitColor = Pal.heal;
                    shake = 1f;
                    speed = 0f;
                    keepVelocity = false;
                    collidesAir = false;

                    spawnUnit = new MissileUnitType("daodanxing-daodan"){{
                        //targetAir = false;
                        speed = 4.6f;
                        lifetime = 60 * 3 ;
                        maxRange = 5f;
                        outlineColor = Pal.darkOutline;
                        health = 640;
                        homingDelay = 10f;
                        lowAltitude = true;
                        engineSize = 3f;
                        engineColor = trailColor = Pal.heal;
                        engineLayer = Layer.effect;
                        deathExplosionEffect = Fx.none;
                        loopSoundVolume = 0.1f;
                        weapons.add(new Weapon(){{
                            shootCone = 360f;
                            mirror = false;
                            reload = 1f;
                            shootOnDeath = true;
                            bullet = new ExplosionBulletType(1145f, 25f){{
                                //collidesAir = false;
                                //suppressionRange = 140f;
                                shootEffect = new ExplosionEffect(){{
                                    lifetime = 50f;
                                    waveStroke = 5f;
                                    waveLife = 8f;
                                    waveColor = Color.white;
                                    sparkColor = smokeColor = Pal.heal;
                                    waveRad = 40f;
                                    smokeSize = 4f;
                                    smokes = 7;
                                    smokeSizeBase = 0f;
                                    sparks = 10;
                                    sparkRad = 40f;
                                    sparkLen = 6f;
                                    sparkStroke = 2f;
                                }};
                            }};
                        }});
                        /*
                        weapons.add(new Weapon(){{
                            shootCone = 360f;
                            mirror = false;
                            alwaysShooting = true ;
                            reload = 90f;
                            shoot = new ShootSpread(720, 0.5f);
                            //shootOnDeath = true;
                            bullet = new BasicBulletType(4f, 1250f){{
                                pierce = true ;
                                pierceBuilding = true ;
                                pierceCap = 114 ;
                                backColor = Pal.heal ;
                            }};
                        }});
                         */
                    }};
                }};
            }}, new Weapon("daodanxing-fuzhupao"){{
                x = y = 0 ;
                mirror = false ;
                shoot = new ShootSpread(80, 45) ;
                shoot.shotDelay = 0.75f ;
                reload = 180 ;
                rotate = false ;
                shootCone = 360 ;
                shake = 2 ;
                shootSound = Sounds.missileSmall ;
                bullet = new BasicBulletType(9f, 160){
                    @Override
                    public void updateHoming(Bullet b) {
                        super.updateHoming(b);
                        if (state.enemies > 0) {
                            b.time = 30 ;
                        }
                    }
                    {
                        width = 12f;
                        hitSize = 7f;
                        height = 20f;
                        shootEffect = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
                        smokeEffect = Fx.shootBigSmoke;
                        ammoMultiplier = 1;
                        //pierceCap = 2;
                        //pierce = true;
                        //pierceBuilding = true;
                        pierceArmor = true ;
                        hitColor = backColor = trailColor = Pal.heal;
                        frontColor = Color.white;
                        trailWidth = 2.1f;
                        trailLength = 10;
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        buildingDamageMultiplier = 0.5f;
                        buildingDamageMultiplier = 1f;
                        homingPower = 0.2f ;
                        homingDelay = 20f ;
                        homingRange = Integer.MAX_VALUE ;
                        lifetime = 60f ;
                        hitShake = despawnShake = 2 ;
                    }} ;
            }});
        }};
        jiguangxing = new UnitType("jiguangxing"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            hitSize = 75f;
            health = 252000f;
            armor = 63f;
            stepShake = 1.5f;
            rotateSpeed = 1.5f;
            drownTimeMultiplier = 8f;
            buildSpeed = 4f;
            mineSpeed = 10 ;
            mineTier = 5 ;
            legCount = 6;
            legLength = 40f;
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
            legBaseOffset = 11f;
            legMoveSpace = 1.5f;
            legForwardScl = 0.58f;
            hovering = true;
            deathExplosionEffect = Fx.none;
            deathSound = Sounds.none;
            shadowElevation = 0.2f;
            ammoType = new PowerAmmoType(4000);
            groundLayer = Layer.legUnit;
            boostMultiplier = 6 ;
            canBoost = true ;
            speed = 0.25f;
            //playerControllable = false;
            //logicControllable = false ;

            immunise(this,true,3f);
            drawShields = false;

            weapons.add(new Weapon("jiguangxing-zhupao"){{
                shootSound = Sounds.none;
                //chargeSound = Sounds.lasercharge;
                soundPitchMin = 1f;
                top = false;
                mirror = false;
                //shake = 14f;
                shootY = 5f;
                x = y = 0;
                //reload = 350f;
                recoil = 2f;
                range = 700 ;
                //cooldownTime = 350f;
                reload = 5f ;
                //shootStatusDuration = 60f * 2f;
                //shootStatus = StatusEffects.unmoving;
                shoot.firstShotDelay = 0;
                envEnabled |= Env.space;
                //alwaysShooting = true ;
                parentizeEffects = true;
                var circleProgress = DrawPart.PartProgress.warmup.delay(0.9f);
                float circleY = 0f, circleRad = 11f, circleRotSpeed = 3.5f, circleStroke = 1.6f;
                float haloY = -15f, haloRotSpeed = 1.5f;
                Color haloColor = Pal.heal ;
                var circleColor = haloColor;
                parts.addAll(
                        new ShapePart(){{
                            progress = circleProgress;
                            color = circleColor;
                            circle = true;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad;
                            layer = Layer.effect;
                            y = circleY;
                        }},
                        new ShapePart(){{
                            progress = circleProgress;
                            rotateSpeed = -circleRotSpeed;
                            color = circleColor;
                            sides = 4;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad - 1f;
                            layer = Layer.effect;
                            y = circleY;
                        }},

                        //outer squares

                        new ShapePart(){{
                            progress = circleProgress;
                            //rotateSpeed = -circleRotSpeed;
                            color = circleColor;
                            sides = 1;
                            //hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad - 1f;
                            layer = Layer.effect;
                            y = circleY;
                        }},

                        //inner square
                        new ShapePart(){{
                            progress = circleProgress;
                            //rotateSpeed = -circleRotSpeed/2f;
                            color = circleColor;
                            sides = 1;
                            //hollow = true;
                            stroke = 0f;
                            strokeTo = 2f;
                            radius = 3f;
                            layer = Layer.effect;
                            y = circleY;
                        }},

                        //spikes on circle
                        new HaloPart(){{
                            progress = circleProgress;
                            color = circleColor;
                            tri = true;
                            shapes = 4;
                            triLength = 0f;
                            triLengthTo = 6f;
                            radius = 3f;
                            haloRadius = circleRad +5f ;
                            haloRotateSpeed = haloRotSpeed / 2f;
                            shapeRotation = 180f;
                            haloRotation = 180f;
                            layer = Layer.effect;
                            y = circleY;
                        }}
                );
                bullet = new ShrapnelBulletType(){{
                        //trailWidth = 20 ;
                        //speed = 10 ;
                        length = 700 ;
                        lifetime = 16 ;
                        toColor = Pal.heal ;
                        damage = 666.667f;
                        width = 22.5f ;
                        //buildingDamageMultiplier = 0.3f;
                        hitColor = Pal.heal ;
                        healPercent = 10 ;
                        shake = 0 ;
                    }};
            }});
            weapons.addAll(new Weapon("jiguangxing-fuzhupao"){{
                y = 0 ;
                x = 20 ;
                rotateSpeed = 2f;
                reload = 100f;
                recoil = 5f;
                cooldownTime = reload;
                shake = 4f;
                shootCone = 2f;
                shootSound = Sounds.railgun;
                envEnabled |= Env.space;
                range = 50 ;
                rotate = true ;
                bullet = new PointBulletType(){{
                    shootEffect = Fx.instShoot;
                    hitColor = Pal.heal ;
                    hitEffect = Fx.instHit;
                    smokeEffect = Fx.smokeCloud;
                    trailEffect = new Effect(30, e -> {
                        for(int i = 0; i < 2; i++){
                            color(i == 0 ? Pal.heal : Pal.heal);

                            float m = i == 0 ? 1f : 0.5f;

                            float rot = e.rotation + 180f;
                            float w = 15f * e.fout() * m;
                            Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot);
                            Drawf.tri(e.x, e.y, w, 10f * m, rot + 180f);
                        }

                        Drawf.light(e.x, e.y, 60f, Pal.heal, 0.6f * e.fout());
                    });
                    lifetime = 1 ;
                    despawnEffect = Fx.instBomb;
                    trailSpacing = 20f;
                    damage = 2520;
                    //buildingDamageMultiplier = 0.2f;
                    speed = 600;
                    hitShake = 6f;
                    ammoMultiplier = 1f;
                }} ;
            }});
            weapons.addAll(new Weapon("jiguangxing-fuzhupao"){{
                y = -20 ;
                x = 0 ;
                mirror = false ;
                rotateSpeed = 2f;
                reload = 100f;
                recoil = 5f;
                range = 800 ;
                cooldownTime = reload;
                shake = 4f;
                shootCone = 2f;
                rotate = true ;
                shootSound = Sounds.railgun;
                envEnabled |= Env.space;
                //range = 400 ;
                bullet = new LaserBulletType(){{
                    pierce = true ;
                    pierceBuilding = true ;
                    shootEffect = Fx.instShoot;
                    hitColor = Pal.heal ;
                    hitEffect = Fx.instHit;
                    smokeEffect = Fx.smokeCloud;
                    trailEffect = new Effect(30, e -> {
                        for(int i = 0; i < 2; i++){
                            color(i == 0 ? Pal.heal : Pal.heal);

                            float m = i == 0 ? 1f : 0.5f;

                            float rot = e.rotation + 180f;
                            float w = 15f * e.fout() * m;
                            Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot);
                            Drawf.tri(e.x, e.y, w, 10f * m, rot + 180f);
                        }

                        Drawf.light(e.x, e.y, 60f, Pal.heal, 0.6f * e.fout());
                    });
                    despawnEffect = Fx.instBomb;
                    //trailSpacing = 20f;
                    damage = 2520;
                    healPercent = 15 ;
                    lifetime = 1 ;
                    //buildingDamageMultiplier = 0.2f;
                    speed = 800;
                    hitShake = 6f;
                    ammoMultiplier = 1f;
                }};
            }});
        }};
        shuangxingyijieduan = new UnitType("shuangxingyijieduan"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            hitSize = 75f;
            health = 252000f;
            armor = 63f;
            stepShake = 1.5f;
            rotateSpeed = 1.5f;
            drownTimeMultiplier = 8f;
            buildSpeed = 4f;
            mineSpeed = 10 ;
            mineTier = 5 ;
            legCount = 6;
            legLength = 35f;
            legBaseOffset = 11f;
            legMoveSpace = 1.5f;
            legForwardScl = 0.58f;
            hovering = true;
            shadowElevation = 0.2f;
            ammoType = new PowerAmmoType(4000);
            groundLayer = Layer.legUnit;
            boostMultiplier = 6 ;
            canBoost = true ;
            speed = 0.25f;
            immunise(this,true,3f);
            drawShields = false;
            //hittable = false ;
            //targetable = false ;
            parts.add(new FlarePart(){{
                progress = PartProgress.life.slope().curve(Interp.pow2In);
                radius = 0f;
                radiusTo = 45f;
                stroke = 3f;
                rotation = 45f;
                followRotation = true;
                color1 = Pal.heal ;
            }});
            abilities.add(new SpawnAbility(){{
                spawnTypes.add(daodanxing).add(jiguangxing) ;
            }}) ;

            weapons.add(new Weapon("shuangxingyijieduan-zhupao"){{
                shootSound = Sounds.none;
                //chargeSound = Sounds.lasercharge;
                soundPitchMin = 1f;
                top = false;
                mirror = false;
                //shake = 14f;
                shootY = 5f;
                x = y = 0;
                //reload = 350f;
                recoil = 2f;
                range = 700 ;
                //cooldownTime = 350f;
                reload = 5f ;
                //shootStatusDuration = 60f * 2f;
                //shootStatus = StatusEffects.unmoving;
                shoot.firstShotDelay = 0;
                envEnabled |= Env.space;
                //alwaysShooting = true ;
                parentizeEffects = true;
                var circleProgress = DrawPart.PartProgress.warmup.delay(0.9f);
                float circleY = 0f, circleRad = 11f, circleRotSpeed = 3.5f, circleStroke = 1.6f;
                float haloY = -15f, haloRotSpeed = 1.5f;
                Color haloColor = Pal.heal ;
                var circleColor = haloColor;
                parts.addAll(
                        new ShapePart(){{
                            progress = circleProgress;
                            color = circleColor;
                            circle = true;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad;
                            layer = Layer.effect;
                            y = circleY;
                        }},
                        new ShapePart(){{
                            progress = circleProgress;
                            rotateSpeed = -circleRotSpeed;
                            color = circleColor;
                            sides = 4;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad - 1f;
                            layer = Layer.effect;
                            y = circleY;
                        }},

                        //outer squares

                        new ShapePart(){{
                            progress = circleProgress;
                            //rotateSpeed = -circleRotSpeed;
                            color = circleColor;
                            sides = 1;
                            //hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad - 1f;
                            layer = Layer.effect;
                            y = circleY;
                        }},

                        //inner square
                        new ShapePart(){{
                            progress = circleProgress;
                            //rotateSpeed = -circleRotSpeed/2f;
                            color = circleColor;
                            sides = 1;
                            //hollow = true;
                            stroke = 0f;
                            strokeTo = 2f;
                            radius = 3f;
                            layer = Layer.effect;
                            y = circleY;
                        }},

                        //spikes on circle
                        new HaloPart(){{
                            progress = circleProgress;
                            color = circleColor;
                            tri = true;
                            shapes = 4;
                            triLength = 0f;
                            triLengthTo = 6f;
                            radius = 3f;
                            haloRadius = circleRad +5f ;
                            haloRotateSpeed = haloRotSpeed / 2f;
                            shapeRotation = 180f;
                            haloRotation = 180f;
                            layer = Layer.effect;
                            y = circleY;
                        }}
                );
                bullet = new ShrapnelBulletType(){{
                    //trailWidth = 20 ;
                    //speed = 10 ;
                    length = 700 ;
                    lifetime = 16 ;
                    toColor = Pal.heal ;
                    damage = 666.667f;
                    width = 22.5f ;
                    //buildingDamageMultiplier = 0.3f;
                    hitColor = Pal.heal ;
                    healPercent = 10 ;
                    shake = 0 ;
                }};
            }});
            weapons.addAll(new Weapon("shuangxingyijieduan-fuzhupao"){{
                y = 0 ;
                x = 20 ;
                rotateSpeed = 2f;
                reload = 100f;
                recoil = 5f;
                cooldownTime = reload;
                shake = 4f;
                shootCone = 2f;
                rotate = true ;
                shootSound = Sounds.railgun;
                envEnabled |= Env.space;
                range = 50 ;
                bullet = new PointBulletType(){{
                    shootEffect = Fx.instShoot;
                    hitColor = Pal.heal ;
                    hitEffect = Fx.instHit;
                    smokeEffect = Fx.smokeCloud;
                    trailEffect = new Effect(30, e -> {
                        for(int i = 0; i < 2; i++){
                            color(i == 0 ? Pal.heal : Pal.heal);

                            float m = i == 0 ? 1f : 0.5f;

                            float rot = e.rotation + 180f;
                            float w = 15f * e.fout() * m;
                            Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot);
                            Drawf.tri(e.x, e.y, w, 10f * m, rot + 180f);
                        }

                        Drawf.light(e.x, e.y, 60f, Pal.heal, 0.6f * e.fout());
                    });
                    lifetime = 1 ;
                    despawnEffect = Fx.instBomb;
                    trailSpacing = 20f;
                    damage = 2520;
                    //buildingDamageMultiplier = 0.2f;
                    speed = 600;
                    hitShake = 6f;
                    ammoMultiplier = 1f;
                }} ;
            }});
            weapons.addAll(new Weapon("shuangxingyijieduan-fuzhupao"){{
                y = -20 ;
                x = 0 ;
                mirror = false ;
                rotateSpeed = 2f;
                reload = 100f;
                recoil = 5f;
                range = 800 ;
                cooldownTime = reload;
                shake = 4f;
                shootCone = 2f;
                rotate = true ;
                shootSound = Sounds.railgun;
                envEnabled |= Env.space;
                //range = 400 ;
                bullet = new LaserBulletType(){{
                    pierce = true ;
                    pierceBuilding = true ;
                    shootEffect = Fx.instShoot;
                    hitColor = Pal.heal ;
                    hitEffect = Fx.instHit;
                    smokeEffect = Fx.smokeCloud;
                    trailEffect = new Effect(30, e -> {
                        for(int i = 0; i < 2; i++){
                            color(i == 0 ? Pal.heal : Pal.heal);

                            float m = i == 0 ? 1f : 0.5f;

                            float rot = e.rotation + 180f;
                            float w = 15f * e.fout() * m;
                            Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot);
                            Drawf.tri(e.x, e.y, w, 10f * m, rot + 180f);
                        }

                        Drawf.light(e.x, e.y, 60f, Pal.heal, 0.6f * e.fout());
                    });
                    despawnEffect = Fx.instBomb;
                    //trailSpacing = 20f;
                    damage = 2520;
                    healPercent = 15 ;
                    lifetime = 1 ;
                    //buildingDamageMultiplier = 0.2f;
                    speed = 800;
                    hitShake = 6f;
                    ammoMultiplier = 1f;
                }};
            }});
            weapons.addAll(new Weapon("shuangxingyijieduan-fupao"){{
                shootSound = Sounds.missileLarge;
                x = 78f / 8f;
                y = -10f / 4f;
                mirror = true;
                rotate = true;
                rotateSpeed = 0.4f;
                reload = 120f;
                layerOffset = -20f;
                recoil = 1f;
                rotationLimit = 22f;
                minWarmup = 0.95f;
                shootWarmupSpeed = 0.1f;
                shootY = 2f;
                shootCone = 40f;
                shoot.shots = 3;
                shoot.shotDelay = 5f;
                inaccuracy = 28f;

                bullet = new BulletType(){{
                    shootEffect = Fx.sparkShoot;
                    smokeEffect = Fx.shootSmokeTitan;
                    hitColor = Pal.heal;
                    shake = 1f;
                    speed = 0f;
                    keepVelocity = false;
                    collidesAir = false;

                    spawnUnit = new MissileUnitType("shuangxingyijieduan-daodan"){{
                        //targetAir = false;
                        speed = 4.6f;
                        lifetime = 60 * 3 ;
                        maxRange = 5f;
                        outlineColor = Pal.darkOutline;
                        health = 640;
                        homingDelay = 10f;
                        lowAltitude = true;
                        engineSize = 3f;
                        engineColor = trailColor = Pal.heal;
                        engineLayer = Layer.effect;
                        deathExplosionEffect = Fx.none;
                        loopSoundVolume = 0.1f;
                        weapons.add(new Weapon(){{
                            shootCone = 360f;
                            mirror = false;
                            reload = 1f;
                            shootOnDeath = true;
                            bullet = new ExplosionBulletType(1145f, 25f){{
                                //collidesAir = false;
                                //suppressionRange = 140f;
                                shootEffect = new ExplosionEffect(){{
                                    lifetime = 50f;
                                    waveStroke = 5f;
                                    waveLife = 8f;
                                    waveColor = Color.white;
                                    sparkColor = smokeColor = Pal.heal;
                                    waveRad = 40f;
                                    smokeSize = 4f;
                                    smokes = 7;
                                    smokeSizeBase = 0f;
                                    sparks = 10;
                                    sparkRad = 40f;
                                    sparkLen = 6f;
                                    sparkStroke = 2f;
                                }};
                            }};
                        }});
                        /*
                        weapons.add(new Weapon(){{
                            shootCone = 360f;
                            mirror = false;
                            alwaysShooting = true ;
                            reload = 90f;
                            shoot = new ShootSpread(720, 0.5f);
                            //shootOnDeath = true;
                            bullet = new BasicBulletType(4f, 1250f){{
                                pierce = true ;
                                pierceBuilding = true ;
                                pierceCap = 114 ;
                                backColor = Pal.heal ;
                            }};
                        }});
                         */
                    }};
                }};
            }}, new Weapon("shuangxingyijieduan-fupao"){{
                x = y = 0 ;
                mirror = false ;
                shoot = new ShootSpread(80, 45) ;
                shoot.shotDelay = 0.75f ;
                reload = 180 ;
                rotate = false ;
                shootCone = 360 ;
                shake = 2 ;
                shootSound = Sounds.missileSmall ;
                bullet = new BasicBulletType(9f, 160){
                    @Override
                    public void updateHoming(Bullet b) {
                        super.updateHoming(b);
                        if (state.enemies > 0) {
                            b.time = 30 ;
                        }
                    }
                    {
                        width = 12f;
                        hitSize = 7f;
                        height = 20f;
                        shootEffect = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
                        smokeEffect = Fx.shootBigSmoke;
                        ammoMultiplier = 1;
                        //pierceCap = 2;
                        //pierce = true;
                        //pierceBuilding = true;
                        pierceArmor = true ;
                        hitColor = backColor = trailColor = Pal.heal;
                        frontColor = Color.white;
                        trailWidth = 2.1f;
                        trailLength = 10;
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        buildingDamageMultiplier = 0.5f;
                        buildingDamageMultiplier = 1f;
                        homingPower = 0.2f ;
                        homingDelay = 20f ;
                        homingRange = Integer.MAX_VALUE ;
                        lifetime = 60f ;
                        hitShake = despawnShake = 2 ;
                    }} ;
            }});
        }};

        shuangxingerjieduan = new UnitType("shuangxingerjieduan"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            hitSize = 75f;
            health = 126000f;
            armor = 63f;
            //hidden = true ;
            stepShake = 1.5f;
            rotateSpeed = 1.5f;
            drownTimeMultiplier = 8f;
            legCount = 6;
            legLength = 35f;
            legBaseOffset = 11f;
            legMoveSpace = 1.5f;
            legForwardScl = 0.58f;
            hovering = true;
            shadowElevation = 0.2f;
            ammoType = new PowerAmmoType(4000);
            groundLayer = Layer.legUnit;
            buildSpeed = 4f;
            mineSpeed = 10 ;
            mineTier = 5 ;
            boostMultiplier = 6 ;
            canBoost = true ;
            speed = 0.25f;
            drawShields = false;
            immunise(this,true,2f);
            parts.add(new FlarePart(){{
                progress = PartProgress.life.slope().curve(Interp.pow2In);
                radius = 0f;
                radiusTo = 45f;
                stroke = 3f;
                rotation = 45f;
                followRotation = true;
                color1 = Pal.heal ;
            }});
            abilities.add(new ZhuTiAbility() {{
                    spawnTypes.add(duoxing) ;
                    display = false ;
                    spawns = 4 ;
                    pucongweis.add(new Vec2(0, 7f * tilesize), new Vec2(0, -7f * tilesize), new Vec2(7f * tilesize, 0), new Vec2(-7f * tilesize, 0)) ;
                }}) ;
            weapons.add(new Weapon("shuangxing-zhupao"){
                @Override
                public void update(Unit unit, WeaponMount mount) {
                    super.update(unit,mount);
                    boolean can = unit.canShoot();
                    float weaponRotation = unit.rotation - 90 + (rotate ? mount.rotation : baseRotation);
                    float mountX = unit.x + Angles.trnsx(unit.rotation - 90, x, y);
                    float mountY = unit.y + Angles.trnsy(unit.rotation - 90, x, y);
                    float bulletX = mountX + Angles.trnsx(weaponRotation, mount.target != null ? mount.target.x() : 0, mount.target != null ? mount.target.y() : 0);
                    float bulletY = mountY + Angles.trnsy(weaponRotation, mount.target != null ? mount.target.x() : 0, mount.target != null ? mount.target.y() : 0);
                    float shootAngle = bulletRotation(unit, mount, bulletX, bulletY);
                    boolean wasFlipped = mount.side;
                    //shoot if applicable
                    if(mount.shoot && //must be shooting
                            can && //must be able to shoot
                            (!useAmmo || unit.ammo > 0 || !state.rules.unitAmmo || unit.team.rules().infiniteAmmo) && //check ammo
                            (!alternate || wasFlipped == flipSprite) &&
                            mount.warmup >= minWarmup && //must be warmed up
                            unit.vel.len() >= minShootVelocity && //check velocity requirements
                            (mount.reload <= 0.0001f || (alwaysContinuous && mount.bullet == null)) && //reload has to be 0, or it has to be an always-continuous weapon
                            (alwaysShooting || Angles.within(rotate ? mount.rotation : unit.rotation + baseRotation, mount.targetRotation, shootCone)) //has to be within the cone
                    ){
                        shoot(unit, mount, bulletX, bulletY , shootAngle);

                        mount.reload = reload;

                        if(useAmmo){
                            unit.ammo--;
                            if(unit.ammo < 0) unit.ammo = 0;
                        }
                    }
                }
                {
                shootSound = Sounds.none;
                //unitSort = UnitSorts.strongest;
                //chargeSound = Sounds.lasercharge;
                //soundPitchMin = 1f;
                top = false;
                mirror = false;
                shake = 14f;
                shootY = 5f;
                x = y = 0;
                //reload = 350f;
                recoil = 0f;
                range = 700 ;
                //cooldownTime = 350f;
                reload = 5f ;
                //shootStatusDuration = 60f * 2f;
                //shootStatus = StatusEffects.unmoving;
                shoot.firstShotDelay = 0;
                parentizeEffects = true;
                shake = 0 ;
                //alwaysShooting = true ;
                envEnabled |= Env.space;
                var circleProgress = DrawPart.PartProgress.warmup.delay(0.9f);
                float circleY = 0f, circleRad = 11f, circleRotSpeed = 3.5f, circleStroke = 1.6f;
                float haloY = -15f, haloRotSpeed = 1.5f;
                Color haloColor = Pal.heal ;
                var circleColor = haloColor;
                parts.addAll(
                        new ShapePart(){{
                            progress = circleProgress;
                            color = circleColor;
                            circle = true;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad;
                            layer = Layer.effect;
                            y = circleY;
                        }},
                        new ShapePart(){{
                            progress = circleProgress;
                            rotateSpeed = -circleRotSpeed;
                            color = circleColor;
                            sides = 4;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad - 1f;
                            layer = Layer.effect;
                            y = circleY;
                        }},

                        //outer squares

                        new ShapePart(){{
                            progress = circleProgress;
                            //rotateSpeed = -circleRotSpeed;
                            color = circleColor;
                            sides = 1;
                            //hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad - 1f;
                            layer = Layer.effect;
                            y = circleY;
                        }},

                        //inner square
                        new ShapePart(){{
                            progress = circleProgress;
                            //rotateSpeed = -circleRotSpeed/2f;
                            color = circleColor;
                            sides = 1;
                            //hollow = true;
                            stroke = 0f;
                            strokeTo = 2f;
                            radius = 3f;
                            layer = Layer.effect;
                            y = circleY;
                        }},

                        //spikes on circle
                        new HaloPart(){{
                            progress = circleProgress;
                            color = circleColor;
                            tri = true;
                            shapes = 4;
                            triLength = 0f;
                            triLengthTo = 6f;
                            radius = 3f;
                            haloRadius = circleRad +5f ;
                            haloRotateSpeed = haloRotSpeed / 2f;
                            shapeRotation = 180f;
                            haloRotation = 180f;
                            layer = Layer.effect;
                            y = circleY;
                        }}
                );
                bullet = new ShrapnelBulletType(){{
                    //trailWidth = 20 ;
                    //speed = 10 ;
                    length = 700 ;
                    lifetime = 16 ;
                    toColor = Pal.heal ;
                    damage = 666.667f;
                    //buildingDamageMultiplier = 0.3f;
                    hitColor = Pal.heal ;
                    width = 22.5f ;
                    healPercent = 10 ;
                    shake = 0 ;
                }};
            }
            });
            weapons.addAll(new Weapon("shuangxing-fuzhupao"){{
                y = 0 ;
                x = 20 ;
                rotateSpeed = 2f;
                reload = 100f;
                recoil = 5f;
                cooldownTime = reload;
                shake = 4f;
                shootCone = 2f;
                shootSound = Sounds.railgun;
                envEnabled |= Env.space;
                range = 600 ;
                bullet = new PointBulletType(){{
                    shootEffect = Fx.instShoot;
                    hitColor = Pal.heal ;
                    hitEffect = Fx.instHit;
                    smokeEffect = Fx.smokeCloud;
                    trailEffect = new Effect(30, e -> {
                        for(int i = 0; i < 2; i++){
                            color(i == 0 ? Pal.heal : Pal.heal);

                            float m = i == 0 ? 1f : 0.5f;

                            float rot = e.rotation + 180f;
                            float w = 15f * e.fout() * m;
                            Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot);
                            Drawf.tri(e.x, e.y, w, 10f * m, rot + 180f);
                        }

                        Drawf.light(e.x, e.y, 60f, Pal.heal, 0.6f * e.fout());
                    });
                    despawnEffect = Fx.instBomb;
                    trailSpacing = 20f;
                    damage = 2520;
                    //buildingDamageMultiplier = 0.2f;
                    speed = 600;
                    lifetime = 1 ;
                    hitShake = 6f;
                    ammoMultiplier = 1f;
                }} ;
            }});
            weapons.addAll(new Weapon("shuangxing-fuzhupao"){{
                y = -20 ;
                x = 0 ;
                mirror = false ;
                rotateSpeed = 2f;
                reload = 100f;
                recoil = 5f;
                cooldownTime = reload;
                shake = 4f;
                shootCone = 2f;
                shootSound = Sounds.railgun;
                envEnabled |= Env.space;
                range = 800 ;
                bullet = new LaserBulletType(){{
                    pierce = true ;
                    pierceBuilding = true ;
                    shootEffect = Fx.instShoot;
                    hitColor = Pal.heal ;
                    hitEffect = Fx.instHit;
                    smokeEffect = Fx.smokeCloud;
                    trailEffect = new Effect(30, e -> {
                        for(int i = 0; i < 2; i++){
                            color(i == 0 ? Pal.heal : Pal.heal);

                            float m = i == 0 ? 1f : 0.5f;

                            float rot = e.rotation + 180f;
                            float w = 15f * e.fout() * m;
                            Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot);
                            Drawf.tri(e.x, e.y, w, 10f * m, rot + 180f);
                        }

                        Drawf.light(e.x, e.y, 60f, Pal.heal, 0.6f * e.fout());
                    });
                    despawnEffect = Fx.instBomb;
                    //trailSpacing = 20f;
                    damage = 2520;

                    //buildingDamageMultiplier = 0.2f;
                    speed = 800;
                    lifetime = 1 ;
                    hitShake = 6f;
                    ammoMultiplier = 1f;
                }};
            }});
            weapons.addAll(new Weapon("shuangxing-fupao"){{
                shootSound = Sounds.missileLarge;
                x = 78f / 4f;
                y = -10f / 4f;
                mirror = true;
                rotate = true;
                rotateSpeed = 0.4f;
                reload = 120f;
                layerOffset = -20f;
                recoil = 1f;
                rotationLimit = 22f;
                minWarmup = 0.95f;
                shootWarmupSpeed = 0.1f;
                shootY = 2f;
                shootCone = 40f;
                shoot.shots = 3;
                shoot.shotDelay = 5f;
                inaccuracy = 28f;

                bullet = new BulletType(){{
                    shootEffect = Fx.sparkShoot;
                    smokeEffect = Fx.shootSmokeTitan;
                    hitColor = Pal.heal;
                    shake = 1f;
                    speed = 0f;
                    keepVelocity = false;
                    collidesAir = false;

                    spawnUnit = new MissileUnitType("shuangxingerjiaduan-daodan"){{
                        //targetAir = false;
                        speed = 4.6f;
                        maxRange = 5f;
                        outlineColor = Pal.heal;
                        lifetime = 60 * 3f ;
                        health = 640;
                        homingDelay = 10f;
                        lowAltitude = true;
                        engineSize = 3f;
                        engineColor = trailColor = Pal.heal;
                        engineLayer = Layer.effect;
                        deathExplosionEffect = Fx.none;
                        loopSoundVolume = 0.1f;
                        weapons.add(new Weapon(){{
                            shootCone = 360f;
                            mirror = false;
                            reload = 1f;
                            shootOnDeath = true;
                            bullet = new ExplosionBulletType(1145f, 25f){{
                                shootEffect = new ExplosionEffect(){{
                                    lifetime = 50f;
                                    waveStroke = 5f;
                                    waveLife = 8f;
                                    waveColor = Color.white;
                                    sparkColor = smokeColor = Pal.heal;
                                    waveRad = 40f;
                                    smokeSize = 4f;
                                    smokes = 7;
                                    smokeSizeBase = 0f;
                                    sparks = 10;
                                    sparkRad = 40f;
                                    sparkLen = 6f;
                                    sparkStroke = 2f;
                                }};
                            }};
                        }});
                        /*
                        weapons.add(new Weapon(){{
                            shootCone = 360f;
                            mirror = false;
                            alwaysShooting = true ;
                            reload = 90f;
                            shoot = new ShootSpread(720, 0.5f);
                            //shootOnDeath = true;
                            bullet = new BasicBulletType(4f, 1250f){{
                                pierce = true ;
                                pierceBuilding = true ;
                                pierceCap = 114 ;
                                backColor = Pal.heal ;
                            }};
                        }});
                         */
                    }};
                }};
            }}, new Weapon("shuangxingerjieduan-fupao"){{
                x = y = 0 ;
                mirror = false ;
                shoot = new ShootSpread(80, 45) ;
                shoot.shotDelay = 0.75f ;
                reload = 180 ;
                rotate = false ;
                shootCone = 360 ;
                shake = 2 ;
                shootSound = Sounds.missileSmall ;
                bullet = new BasicBulletType(9f, 160){
                    @Override
                    public void updateHoming(Bullet b) {
                        super.updateHoming(b);
                        if (state.enemies > 0) {
                            b.time = 30 ;
                        }
                    }
                    {
                        width = 12f;
                        hitSize = 7f;
                        height = 20f;
                        shootEffect = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
                        smokeEffect = Fx.shootBigSmoke;
                        ammoMultiplier = 1;
                        //pierceCap = 2;
                        //pierce = true;
                        //pierceBuilding = true;
                        pierceArmor = true ;
                        hitColor = backColor = trailColor = Pal.heal;
                        frontColor = Color.white;
                        trailWidth = 2.1f;
                        trailLength = 10;
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        buildingDamageMultiplier = 0.5f;
                        buildingDamageMultiplier = 1f;
                        homingPower = 0.2f ;
                        homingDelay = 20f ;
                        homingRange = Integer.MAX_VALUE ;
                        lifetime = 60f ;
                        hitShake = despawnShake = 2 ;
                    }} ;
            }});
        }};
        deerta = new UnitType("deerta"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
//            constructor = DieTaiUnitEntity::new ;
            aiController = BuilderAI::new;
            isEnemy = false;

            lowAltitude = true;
            flying = true;
            mineSpeed = 10f;
            mineTier = 3;
            buildSpeed = 1.2f;
            drag = 0.05f;
            speed = 3.7f;
            rotateSpeed = 19f;
            accel = 0.11f;
            itemCapacity = 90;
            health = 280f;
            engineOffset = 6f;
            hitSize = 13f;
            armor = 1 ;
            weapons.add(new Weapon("deerta-zhupao"){{
                top = false;
                reload = 10f;
                Vec2 v = textureToReal(new Vec2(28, 18), 68, 68) ;
                x = v.x ;
                y = v.y ;
                shoot = new ShootSpread(){{
                    shots = 3;
                    shotDelay = 3f;
                    spread = 2f;
                }};

                inaccuracy = 3f;
                ejectEffect = Fx.casing1;
                bullet = new BasicBulletType(3.5f, 20){{
                    width = 6.5f;
                    height = 11f;
                    lifetime = 70f;
                    shootEffect = Fx.shootSmall;
                    smokeEffect = Fx.shootSmallSmoke;
                    buildingDamageMultiplier = 0.01f;
                    homingPower = 0.04f;
                }};
            }});
        }};
        shengling = new UnitType("shengling"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            aiController = DefenderAI::new;

            armor = 24f;
            health = 72000;
            speed = 0.7f;
            rotateSpeed = 1f;
            accel = 0.04f;
            drag = 0.018f;
            flying = true;
            engineOffset = 46f;
            engineSize = 7.8f;
            faceTarget = true;
            hitSize = 75f;
            payloadCapacity = (7.5f * 7.5f) * tilePayload;
            buildSpeed = 5f;
            drawShields = false;
            lowAltitude = true;
            buildBeamOffset = 43;
            ammoCapacity = 1;
            weapons.add(
                    new Weapon("shengling-zhupao"){{
                        shootSound = Sounds.laserblast;
                        chargeSound = Sounds.lasercharge;
                        soundPitchMin = 1f;
                        top = false;
                        //mirror = false;
                        shake = 14f;
                        shootY = 5f;
                        x = 30;
                        y = 0;
                        reload = 120f;
                        recoil = 0f;

                        cooldownTime = 60f;

//                        shootStatusDuration = 60f * 2f;
//                        shootStatus = StatusEffects.unmoving;
                        shoot.firstShotDelay = Fx.greenLaserCharge.lifetime;
                        parentizeEffects = true;

                        bullet = new LaserBulletType(){{
                            length = 320f;
                            damage = 375f;
                            width = 75f;

                            lifetime = 65f;

                            lightningSpacing = 35f;
                            lightningLength = 5;
                            lightningDelay = 1.1f;
                            lightningLengthRand = 15;
                            lightningDamage = 50;
                            lightningAngleRand = 40f;
                            largeHit = true;
                            lightColor = lightningColor = Pal.heal;

                            chargeEffect = Fx.greenLaserCharge;

                            healPercent = 25f;
                            collidesTeam = true;

                            sideAngle = 15f;
                            sideWidth = 0f;
                            sideLength = 0f;
                            colors = new Color[]{Pal.heal.cpy().a(0.4f), Pal.heal, Color.white};
                        }};
                    }}
            );
            abilities.add(new ForceFieldAbility(140f, 40f, 10000f, 60f * 7, 5, 0f), new RepairFieldAbility(500f, 60f * 2, 240f), new StatusFieldAbility(StatusEffects.overclock,240,300,560), new DuiDiZhuangTaiChangAbility(SYBSStatusEffects.duidies.get("paichi").get(0),240,300,560));
        }};
        anye = new UnitType("anye"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            constructor = AnYeUnitEntity::new ;
            speed = 0.5f;
            accel = 0.04f;
            drag = 0.04f;
            rotateSpeed = 1f;
            flying = true;
            lowAltitude = true;
            health = 66000;
            engineOffset = 38;
            engineSize = 7.3f;
            hitSize = 64f;
            armor = 22f;
            targetFlags = new BlockFlag[]{BlockFlag.turret, BlockFlag.core, null};
            ammoType = new ItemAmmoType(Items.thorium);
            abilities.add(new StatusFieldAbility(StatusEffects.overdrive,300,560,100),new ShieldRegenFieldAbility(200,20000,560,200),new HongZhaAbility(20,640,600,5, "anye"){{range = 320;}});
            weapons.add(
                    new Weapon("anye-zhupao"){{
                        shake = 4f;
                        shootY = 9f;
                        x = 18f;
                        y = 5f;
                        rotateSpeed = 2f;
                        reload = 240f;
                        recoil = 4f;
                        shootSound = Sounds.laserbig;
                        shadow = 20f;
                        rotate = true;
                        continuous = true ;
                        bullet = new ContinuousLaserBulletType(){{
                            lifetime = 240f ;
                            damage = 200f;
                            width = 10f;
                            length = 320f;
                            shootEffect = Fx.shockwave;
                            colors = new Color[]{Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.white};
                        }};
                    }},
                    new Weapon("anye-fuzhupao"){{
                        x = 0f;
                        y = 0f;
                        mirror = false ;
                        rotateSpeed = 2f;
                        reload = 180f;
                        shootSound = Sounds.shootSmite;
                        shadow = 7f;
                        //rotate = true;
                        recoil = 0.5f;
                        shootY = 7.25f;
                        var haloProgress = DrawPart.PartProgress.warmup.delay(0.5f);
                        float haloY = -15f, haloRotSpeed = 1f;
                        parts.addAll(
                                new ShapePart(){{
                                    progress = PartProgress.warmup.delay(0.2f);
                                    color = Pal.accent;
                                    circle = true;
                                    hollow = true;
                                    stroke = 0f;
                                    strokeTo = 2f;
                                    radius = 10f;
                                    layer = Layer.effect;
                                    y = haloY;
                                    rotateSpeed = haloRotSpeed;
                                }},
                                new ShapePart(){{
                                    progress = PartProgress.warmup.delay(0.2f);
                                    color = Pal.accent;
                                    circle = true;
                                    hollow = true;
                                    stroke = 0f;
                                    strokeTo = 1.6f;
                                    radius = 4f;
                                    layer = Layer.effect;
                                    y = haloY;
                                    rotateSpeed = haloRotSpeed;
                                }},
                                new HaloPart(){{
                                    progress = haloProgress;
                                    color = Pal.accent;
                                    layer = Layer.effect;
                                    y = haloY;

                                    haloRotation = 90f;
                                    shapes = 2;
                                    triLength = 0f;
                                    triLengthTo = 20f;
                                    haloRadius = 16f;
                                    tri = true;
                                    radius = 4f;
                                }},
                                new HaloPart(){{
                                    progress = haloProgress;
                                    color = Pal.accent;
                                    layer = Layer.effect;
                                    y = haloY;

                                    haloRotation = 90f;
                                    shapes = 2;
                                    triLength = 0f;
                                    triLengthTo = 5f;
                                    haloRadius = 16f;
                                    tri = true;
                                    radius = 4f;
                                    shapeRotation = 180f;
                                }},
                                new HaloPart(){{
                                    progress = haloProgress;
                                    color = Pal.accent;
                                    layer = Layer.effect;
                                    y = haloY;
                                    haloRotateSpeed = -haloRotSpeed;

                                    shapes = 4;
                                    triLength = 0f;
                                    triLengthTo = 5f;
                                    haloRotation = 45f;
                                    haloRadius = 16f;
                                    tri = true;
                                    radius = 8f;
                                }},
                                new HaloPart(){{
                                    progress = haloProgress;
                                    color = Pal.accent;
                                    layer = Layer.effect;
                                    y = haloY;
                                    haloRotateSpeed = -haloRotSpeed;

                                    shapes = 4;
                                    shapeRotation = 180f;
                                    triLength = 0f;
                                    triLengthTo = 2f;
                                    haloRotation = 45f;
                                    haloRadius = 16f;
                                    tri = true;
                                    radius = 8f;
                                }},
                                new HaloPart(){{
                                    progress = haloProgress;
                                    color = Pal.accent;
                                    layer = Layer.effect;
                                    y = haloY;
                                    haloRotateSpeed = haloRotSpeed;

                                    shapes = 4;
                                    triLength = 0f;
                                    triLengthTo = 3f;
                                    haloRotation = 45f;
                                    haloRadius = 10f;
                                    tri = true;
                                    radius = 6f;
                                }}
                        );
                        shoot = new ShootMulti(new ShootAlternate(){{
                            spread = 3.3f * 1.9f;
                            shots = barrels = 5;
                        }}, new ShootHelix(){{
                            scl = 4f;
                            mag = 3f;
                        }});
                        bullet = new BasicBulletType(7,160){{
                            sprite = "large-orb";
                            width = 17f;
                            height = 21f;
                            hitSize = 8f;

                            shootEffect = new MultiEffect(Fx.shootTitan, Fx.colorSparkBig, new WaveEffect(){{
                                colorFrom = colorTo = Pal.accent;
                                lifetime = 12f;
                                sizeTo = 20f;
                                strokeFrom = 3f;
                                strokeTo = 0.3f;
                            }});
                            smokeEffect = Fx.shootSmokeSmite;
                            ammoMultiplier = 1;
                            pierceCap = 4;
                            pierce = true;
                            pierceBuilding = true;
                            hitColor = backColor = trailColor = Pal.accent;
                            frontColor = Color.white;
                            trailWidth = 2.8f;
                            trailLength = 9;
                            hitEffect = Fx.hitBulletColor;
                            buildingDamageMultiplier = 0.3f;
                            buildingDamageMultiplier = 1f;
                            despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect(){{
                                sizeTo = 30f;
                                colorFrom = colorTo = Pal.accent;
                                lifetime = 12f;
                            }});

                            trailRotation = true;
                            trailEffect = Fx.disperseTrail;
                            trailInterval = 3f;

                            intervalBullet = new LightningBulletType(){{
                                damage = 50;
                                collidesAir = false;
                                ammoMultiplier = 1f;
                                lightningColor = Pal.accent;
                                lightningLength = 5;
                                lightningLengthRand = 10;

                                //for visual stats only.
                                buildingDamageMultiplier = 0.25f;
                                buildingDamageMultiplier = 1f;

                                lightningType = new BulletType(0.0001f, 0f){{
                                    lifetime = Fx.lightning.lifetime;
                                    hitEffect = Fx.hitLancer;
                                    despawnEffect = Fx.none;
                                    status = StatusEffects.shocked;
                                    statusDuration = 10f;
                                    hittable = false;
                                    lightColor = Color.white;
                                    buildingDamageMultiplier = 0.25f;
                                    buildingDamageMultiplier = 1f;
                                }};
                            }};

                            bulletInterval = 3f;
                        }};
                    }},
                    new Weapon("anye-fupao"){{
                        mirror = false ;
                        y = 0f;
                        x = 0f;
                        reload = 120f;
                        ejectEffect = Fx.casing1;
                        rotateSpeed = 7f;
                        shake = 1f;
                        shootSound = Sounds.none;
                        rotate = true;
                        shadow = 12f;
                        shootY = 7.25f;
                        bullet = new BombBulletType(140f, 50f){{
                            width = 30f;
                            height = 38f;
                            hitEffect = Fx.flakExplosion;
                            shootEffect = Fx.none;
                            smokeEffect = Fx.none;
                            shoot.shots = 5 ;
                            status = StatusEffects.blasted;
                            statusDuration = 60f;
                        }};
                    }});
        }};
        //TODO
        xiuji = new XianShangUnitType("xiuji"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            constructor = XianShangUnitEntity::new ;
            speed = 0.3f;
            hitSize = 120f;
            rotateSpeed = 1.65f;
            health = 850000;
            armor = 300f;
            mechStepParticles = true;
            stepShake = 3.75f;
            drownTimeMultiplier = 6f;
            mechFrontSway = 1.9f;
            mechSideSway = 0.6f;
            dancixianshang = 3800 * 1.1f;
            miaoxianshang = 28000 * 1.1f;
            //fenxianshang = 780000 * 1.1f;
            //immunise(this,false,1);
            //ammoType = new ItemAmmoType(Items.thorium);
            abilities.add(new XiuJiFuShiChangAbility(300,60,1800,400,100,SYBSStatusEffects.zhongdufushi,SYBSStatusEffects.dangji,StatusEffects.disarmed,StatusEffects.blasted,StatusEffects.freezing,StatusEffects.sapped,StatusEffects.wet,StatusEffects.shocked,StatusEffects.electrified,SYBSStatusEffects.diaoling),
                new XiuJiLingYuChangAbility(0.4f,1024,30),new XiuJiDunJiaAbility()
            );
            var haloProgress = DrawPart.PartProgress.warmup;
            Color haloColor = Color.valueOf("aa6600");
            float haloY = -15f, haloRotSpeed = 1.5f;

            var circleProgress = DrawPart.PartProgress.warmup.delay(0.9f);
            var circleColor = haloColor;
            float circleY = 0f, circleRad = 16.5f, circleRotSpeed = 3.5f, circleStroke = 1.6f;
            weapons.add(new PointDefenseWeapon("xiuji-liejieguangshu"){{
                x = 46f / 4f;
                y = -50f / 4f;
                reload = 1f;

                targetInterval = 9f;
                targetSwitchInterval = 12f;
                recoil = 0.5f;

                bullet = new BulletType(){{
                    shootSound = Sounds.lasershoot;
                    shootEffect = Fx.sparkShoot;
                    hitEffect = Fx.pointHit;
                    maxRange = 100f;
                    damage = 120f;
                }};
            }});
            weapons.add(new PointDefenseWeapon("xiuji-liejieguangshu"){{
                x = 66f / 4f;
                y = -70f / 4f;
                reload = 1f;

                targetInterval = 9f;
                targetSwitchInterval = 12f;
                recoil = 0.5f;

                bullet = new BulletType(){{
                    shootSound = Sounds.lasershoot;
                    shootEffect = Fx.sparkShoot;
                    hitEffect = Fx.pointHit;
                    maxRange = 100f;
                    damage = 120f;
                }};
            }});
            weapons.add(new Weapon("xiuji-zhupao"){
                public BulletType bullett ;
                public int timer = 0 ;
                public int genghuan ;
                @Override
                public void addStats(UnitType u , Table t){
                    if(inaccuracy > 0){
                        t.row();
                        t.add("[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int)inaccuracy + " " + StatUnit.degrees.localized());
                    }
                    if(!alwaysContinuous && reload > 0){
                        t.row();
                        t.add("[lightgray]" + Stat.reload.localized() + ": " + (mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / reload * shoot.shots, 2) + " " + StatUnit.perSecond.localized());
                    }
                    SYBSStatValues.ammo(ObjectMap.of(u, bullet)).display(t);
                }
                @Override
                public void bullet(Unit unit, WeaponMount mount, float xOffset, float yOffset, float angleOffset, Mover mover){
                    if(!unit.isAdded()) return;
                    timer ++ ;
                    if (timer == genghuan) {
                        mount.charging = false;
                        float
                                xSpread = Mathf.range(xRand),
                                weaponRotation = unit.rotation - 90 + (rotate ? mount.rotation : baseRotation),
                                mountX = unit.x + Angles.trnsx(unit.rotation - 90, x, y),
                                mountY = unit.y + Angles.trnsy(unit.rotation - 90, x, y),
                                bulletX = mountX + Angles.trnsx(weaponRotation, this.shootX + xOffset + xSpread, this.shootY + yOffset),
                                bulletY = mountY + Angles.trnsy(weaponRotation, this.shootX + xOffset + xSpread, this.shootY + yOffset),
                                shootAngle = bulletRotation(unit, mount, bulletX, bulletY) + angleOffset,
                                lifeScl = bullett.scaleLife ? Mathf.clamp(Mathf.dst(bulletX, bulletY, mount.aimX, mount.aimY) / bullett.range) : 1f,
                                angle = angleOffset + shootAngle + Mathf.range(inaccuracy + bullett.inaccuracy);

                        Entityc shooter = unit.controller() instanceof MissileAI ai ? ai.shooter : unit; //Pass the missile's shooter down to its bullets
                        mount.bullet = bullett.create(unit, shooter, unit.team, bulletX, bulletY, angle, -1f, (1f - velocityRnd) + Mathf.random(velocityRnd), lifeScl, null, mover, mount.aimX, mount.aimY);
                        handleBullet(unit, mount, mount.bullet);
                        if (!continuous) {
                            shootSound.at(bulletX, bulletY, Mathf.random(soundPitchMin, soundPitchMax));
                        }

                        ejectEffect.at(mountX, mountY, angle * Mathf.sign(this.x));
                        bullett.shootEffect.at(bulletX, bulletY, angle, bullett.hitColor, unit);
                        bullett.smokeEffect.at(bulletX, bulletY, angle, bullett.hitColor, unit);

                        unit.vel.add(Tmp.v1.trns(shootAngle + 180f, bullett.recoil));
                        Effect.shake(shake, shake, bulletX, bulletY);
                        mount.recoil = 1f;
                        if (recoils > 0) {
                            mount.recoils[mount.barrelCounter % recoils] = 1f;
                        }
                        mount.heat = 1f;
                        mount.totalShots++;
                        timer = 0 ;
                    }
                    else{
                        mount.charging = false;
                        float
                                xSpread = Mathf.range(xRand),
                                weaponRotation = unit.rotation - 90 + (rotate ? mount.rotation : baseRotation),
                                mountX = unit.x + Angles.trnsx(unit.rotation - 90, x, y),
                                mountY = unit.y + Angles.trnsy(unit.rotation - 90, x, y),
                                bulletX = mountX + Angles.trnsx(weaponRotation, this.shootX + xOffset + xSpread, this.shootY + yOffset),
                                bulletY = mountY + Angles.trnsy(weaponRotation, this.shootX + xOffset + xSpread, this.shootY + yOffset),
                                shootAngle = bulletRotation(unit, mount, bulletX, bulletY) + angleOffset,
                                lifeScl = bullet.scaleLife ? Mathf.clamp(Mathf.dst(bulletX, bulletY, mount.aimX, mount.aimY) / bullet.range) : 1f,
                                angle = angleOffset + shootAngle + Mathf.range(inaccuracy + bullet.inaccuracy);

                        Entityc shooter = unit.controller() instanceof MissileAI ai ? ai.shooter : unit; //Pass the missile's shooter down to its bullets
                        mount.bullet = bullet.create(unit, shooter, unit.team, bulletX, bulletY, angle, -1f, (1f - velocityRnd) + Mathf.random(velocityRnd), lifeScl, null, mover, mount.aimX, mount.aimY);
                        handleBullet(unit, mount, mount.bullet);
                        if (!continuous) {
                            shootSound.at(bulletX, bulletY, Mathf.random(soundPitchMin, soundPitchMax));
                        }

                        ejectEffect.at(mountX, mountY, angle * Mathf.sign(this.x));
                        bullet.shootEffect.at(bulletX, bulletY, angle, bullet.hitColor, unit);
                        bullet.smokeEffect.at(bulletX, bulletY, angle, bullet.hitColor, unit);

                        unit.vel.add(Tmp.v1.trns(shootAngle + 180f, bullet.recoil));
                        Effect.shake(shake, shake, bulletX, bulletY);
                        mount.recoil = 1f;
                        if (recoils > 0) {
                            mount.recoils[mount.barrelCounter % recoils] = 1f;
                        }
                        mount.heat = 1f;
                        mount.totalShots++;
                    }
                }
                {
                top = false;
                mirror = true ;
                x = 10 * tilesize ;
                shootSound = Sounds.laserblast;
                //loopSound = Sounds.beam;
                //loopSoundVolume = 2f;
                shootY = textureToReal(new Vec2(160, 76), 299, 750).y ;
                    parts.addAll(
                            new ShapePart(){{
                                progress = circleProgress;
                                color = circleColor;
                                sides = 4;
                                hollow = true;
                                stroke = 0f;
                                strokeTo = circleStroke;
                                radius = circleRad;
                                layer = Layer.effect;
                                y = shootY;
                            }},
                            new ShapePart(){{
                                progress = circleProgress;
                                rotateSpeed = circleRotSpeed;
                                color = circleColor;
                                sides = 3 ;
                                hollow = true;
                                stroke = 0f;
                                strokeTo = circleStroke;
                                radius = circleRad;
                                layer = Layer.effect;
                                y = shootY;
                            }},
                            new ShapePart(){{
                                progress = circleProgress;
                                rotateSpeed = -circleRotSpeed;
                                color = circleColor;
                                sides = 3 ;
                                hollow = true;
                                rotation = 90 ;
                                stroke = 0f;
                                strokeTo = circleStroke;
                                radius = circleRad;
                                layer = Layer.effect;
                                y = shootY;
                            }}
                    );
                    reload = 200f;
                recoil = 1f;
                ejectEffect = Fx.none;
                //range = 1000f ;
                //shootStatus = StatusEffects.unmoving ;
                shoot = new ShootSpread(3, 3f);
                genghuan = 2 ;
                //shootStatusDuration = 128f ;
                bullet = new GaiLvMiaoLaserBulletType(10000,100){{
                    shootEffect = Fx.sparkShoot;
                    smokeEffect = Fx.shootSmokeTitan;
                    hitColor = Color.valueOf("aa6600");
                    colors[0] = Color.valueOf("aa6600").cpy().mul(1f, 1f, 1f, 0.4f) ;
                    colors[1] = Color.valueOf("aa6600") ;
                    shake = 1f;
                    speed = 0f;
                    length = 1000 * 0.9f ;
                    lifetime = 70 ;
                    //hitSize = 60 ;
                    width = 80 ;
                }};
                bullett = new GaiLvMiaoLaserBulletType(15000,75){{
                    shootEffect = Fx.sparkShoot;
                    smokeEffect = Fx.shootSmokeTitan;
                    hitColor = Color.valueOf("aa6600");
                    shake = 1f;
                    speed = 0f;
                    length = 1200 * 0.9f;
                    //hitSize = 90 ;
                    lifetime = 90 ;
                    width = 120 ;
                    colors[0] = Color.valueOf("aa6600").cpy().mul(1f, 1f, 1f, 0.4f) ;
                    colors[1] = Color.valueOf("aa6600") ;
                }};
                }});
            weapons.add(
                    new Weapon("xiuji-fuzhupao"){
                        @Override
                        public void addStats(UnitType u , Table t){
                            if(inaccuracy > 0){
                                t.row();
                                t.add("[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int)inaccuracy + " " + StatUnit.degrees.localized());
                            }
                            if(!alwaysContinuous && reload > 0){
                                t.row();
                                t.add("[lightgray]" + Stat.reload.localized() + ": " + (mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / reload * shoot.shots, 2) + " " + StatUnit.perSecond.localized());
                            }
                            SYBSStatValues.ammo(ObjectMap.of(u, bullet)).display(t);
                        }
                        {
                            parts.addAll(
                                    new ShapePart(){{
                                        progress = circleProgress;
                                        color = circleColor;
                                        sides = 4;
                                        hollow = true;
                                        stroke = 0f;
                                        strokeTo = circleStroke;
                                        radius = circleRad;
                                        layer = Layer.effect;
                                        y = circleY;
                                    }},
                                    new ShapePart(){{
                                        progress = circleProgress;
                                        rotateSpeed = circleRotSpeed;
                                        color = circleColor;
                                        sides = 3 ;
                                        hollow = true;
                                        stroke = 0f;
                                        strokeTo = circleStroke;
                                        radius = circleRad;
                                        layer = Layer.effect;
                                        y = circleY;
                                    }},
                                    new ShapePart(){{
                                        progress = circleProgress;
                                        rotateSpeed = -circleRotSpeed;
                                        color = circleColor;
                                        sides = 3 ;
                                        hollow = true;
                                        rotation = 90 ;
                                        stroke = 0f;
                                        strokeTo = circleStroke;
                                        radius = circleRad;
                                        layer = Layer.effect;
                                        y = circleY;
                                    }}
                            );
                            top = false;
                        y = 40f;
                        x = 45.5f;
                        shootY = 11f;
                        reload = 150f;
                        recoil = 5f;
                        shake = 2f;
                        ejectEffect = Fx.casing4;
                        shootSound = Sounds.missileLarge;
                            shoot = new ShootSpread(30, 0.75f);
                            shoot.shotDelay = 0.2f ;
                        bullet = new GaiLvMiaoBulletType(13f, 1600,1000){{
                            pierce = true;
                            pierceCap = 10;
                            width = 14f;
                            height = 33f;
                            hitSize = 30 ;
                            lifetime = 45f;
                            homingPower = 0.5f;
                            homingDelay = 15f;
                            homingRange = 585f;
                            shootEffect = Fx.shootSmokeMissile;
                            fragVelocityMin = 0.4f;
                            trailChance = 1f ;
                            trailInterval = 1f ;
                            trailEffect = new Effect(30, e -> {
                                for(int i = 0; i < 2; i++){
                                    color(i == 0 ? Color.valueOf("aa6600") : Color.valueOf("aa6600"));

                                    float m = i == 0 ? 1f : 0.5f;

                                    float rot = e.rotation + 180f;
                                    float w = 15f * e.fout() * m;
                                    Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot);
                                    Drawf.tri(e.x, e.y, w, 10f * m, rot + 180f);
                                }

                                Drawf.light(e.x, e.y, 60f, Color.valueOf("aa6600"), 0.6f * e.fout());
                            }) ;
                            hitColor = Color.valueOf("aa6600") ;
                            hitEffect = Fx.titanExplosion;
                            splashDamage = 1800f;
                            splashDamageRadius = 80f;
                        }};
                    }}
            );
            weapons.add(
                    new Weapon("xiuji-fuzhupao"){
                        @Override
                        public void addStats(UnitType u , Table t){
                            if(inaccuracy > 0){
                                t.row();
                                t.add("[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int)inaccuracy + " " + StatUnit.degrees.localized());
                            }
                            if(!alwaysContinuous && reload > 0){
                                t.row();
                                t.add("[lightgray]" + Stat.reload.localized() + ": " + (mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / reload * shoot.shots, 2) + " " + StatUnit.perSecond.localized());
                            }
                            SYBSStatValues.ammo(ObjectMap.of(u, bullet)).display(t);
                        }
                        {
                        top = false;
                        y = 20f;
                        x = 25.5f;
                            parts.addAll(
                                    new ShapePart(){{
                                        progress = circleProgress;
                                        color = circleColor;
                                        sides = 4;
                                        hollow = true;
                                        stroke = 0f;
                                        strokeTo = circleStroke;
                                        radius = circleRad;
                                        layer = Layer.effect;
                                        y = circleY;
                                    }},
                                    new ShapePart(){{
                                        progress = circleProgress;
                                        rotateSpeed = circleRotSpeed;
                                        color = circleColor;
                                        sides = 3 ;
                                        hollow = true;
                                        stroke = 0f;
                                        strokeTo = circleStroke;
                                        radius = circleRad;
                                        layer = Layer.effect;
                                        y = circleY;
                                    }},
                                    new ShapePart(){{
                                        progress = circleProgress;
                                        rotateSpeed = -circleRotSpeed;
                                        color = circleColor;
                                        sides = 3 ;
                                        hollow = true;
                                        rotation = 90 ;
                                        stroke = 0f;
                                        strokeTo = circleStroke;
                                        radius = circleRad;
                                        layer = Layer.effect;
                                        y = circleY;
                                    }}
                            );
                            shootY = 11f;
                        reload = 150f * 1.2f;
                        recoil = 5f;
                        shake = 2f;
                        ejectEffect = Fx.casing4;
                        shootSound = Sounds.missileLarge;
                        shoot = new ShootSpread(30, 0.75f);
                        shoot.shotDelay = 0.2f ;
                        bullet = new GaiLvMiaoBulletType(13f, 1600,1000){{
                            pierce = true;
                            pierceCap = 10;
                            width = 14f;
                            height = 33f;
                            hitSize = 30 ;
                            lifetime = 45f;
                            homingPower = 0.5f;
                            homingDelay = 15f;
                            homingRange = 585f;
                            hitSize = 10 ;
                            shootEffect = Fx.shootSmokeMissile;
                            fragVelocityMin = 0.4f;
                            trailEffect = new Effect(30, e -> {
                                for(int i = 0; i < 2; i++){
                                    color(i == 0 ? Color.valueOf("aa6600") : Color.valueOf("aa6600"));

                                    float m = i == 0 ? 1f : 0.5f;

                                    float rot = e.rotation + 180f;
                                    float w = 15f * e.fout() * m;
                                    Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot);
                                    Drawf.tri(e.x, e.y, w, 10f * m, rot + 180f);
                                }

                                Drawf.light(e.x, e.y, 60f, Color.valueOf("aa6600"), 0.6f * e.fout());
                            }) ;
                            hitColor = Color.valueOf("aa6600") ;
                            hitEffect = Fx.titanExplosion;
                            splashDamage = 1800f;
                            splashDamageRadius = 80f;
                        }};
                    }}
            );
            /*
            weapons.add(
                    new Weapon("xiuji-lianjv"){
                        @Override
                        public void addStats(UnitType u , Table t){
                            if(inaccuracy > 0){
                                t.row();
                                t.add("[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int)inaccuracy + " " + StatUnit.degrees.localized());
                            }
                            if(!alwaysContinuous && reload > 0){
                                t.row();
                                t.add("[lightgray]" + Stat.reload.localized() + ": " + (mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / reload * shoot.shots, 2) + " " + StatUnit.perSecond.localized());
                            }
                            SYBSStatValues.ammo(ObjectMap.of(u, bullet)).display(t);
                        }
                        {
                            top = false;
                            mirror = false ;
                            y = 0;
                            x = 0;
                            shootX = 8f * tilesize ;
                            shootY = -4.5f * tilesize ;
                            reload = 1 ;
                            recoil = 0f;
                            shake = 2f;
                            ejectEffect = Fx.casing4;
                            shootSound = Sounds.missileLarge;
                            bullet = new JvChiBulletType(333.334f, 1145, 1000);
                        }}
            );
            */
            weapons.add(new Weapon("xiuji-lianjv") {{
                mirror = false ;
                shootSound = ((ItemTurret) Blocks.breach).shootSound ;
                shoot = new ShootSummon(0f, 0f, 1, 2f);
                reload = 1 ;
                shootX = 10 * tilesize ;
                int i = id ;
                rotate = false ;
                bullet = new TianDingBulletType() {{
                    //blockId = i ;
                    len = 60 * tilesize ;
                    wid = 20 * tilesize ;
                    speed = 15 ;
                    width = height = 1.25f * tilesize ;
                    color = SYBSPal.xiuji ;
                    baifenbi = 0 ;
                    damage = 25000 / (60 / reload) ;
                    status = 1 ;
                    multiplier = 0.15f ;
                    drawItem = false ;
                    sprite = "missile-large-back" ;
                    unitId = i ;
                }} ;
            }}) ;
        }};
//        ((TianDingBulletType)xiuji.weapons.get(xiuji.weapons.size - 1).bullet).unitId = xiuji.id ;
        shamie = new UnitType("shamie"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
            aiController = FlyingSuicideAI::new;
            armor = 60f ;
            speed = 7f;
            targetFlags = new BlockFlag[]{BlockFlag.core, null};
            hitSize = 60f;
            health = 50000;
            flying = true ;
            range = 80f;
            ammoType = new ItemAmmoType(Items.coal);
            abilities.add(new MoveEffectAbility(){
                public String localized(){
                    return "";
                }
                {
                effect = Fx.missileTrailSmoke;
                rotation = 180f;
                y = -9f;
                color = Color.grays(0.6f).lerp(Pal.redderDust, 0.5f).a(0.4f);
                interval = 3f;
            }},
                    new Ability(){
                        public boolean a ;
                        @Override
                        public void death(Unit unit){
                            super.death(unit);
                            a = unit != null ;
                            if (a) {
                                a = false ;
                                unit.remove() ;
                                throw new NullPointerException("ShaMie killed your game before death.This is not a BUG");
                            }
                        }
                        public String localized(){
                            return Core.bundle.get("ability.shamie");
                        }
                        {

                    }});
            weapons.add(new Weapon(){{
                deathExplosionEffect = Fx.massiveExplosion;
                reload = 24f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.largeExplosion;
                x = shootY = 0f;
                mirror = false;
                shake = 15f ;
                shootOnDeath = true ;
                range = 60f ;
                bullet = new BulletType(){{
                    hitColor = Pal.redderDust ;
                    hitEffect = new MultiEffect(Fx.massiveExplosion, Fx.scatheExplosion, Fx.scatheLight, new WaveEffect(){{
                        lifetime = 10f;
                        strokeFrom = 4f;
                        sizeTo = 130f;
                    }});
                    collidesTiles = false;
                    collides = false;
                    hitSound = Sounds.largeExplosion ;
                    rangeOverride = 30f;
                    speed = 0f;
                    splashDamageRadius = 80f;
                    instantDisappear = true;
                    splashDamage = 8000f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};
        lingjian = new UnitType("lingjian"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
            aiController = FlyingSuicideAI::new;
            armor = 150f ;
            speed = 14f;
            immunise(this,false,1);
            targetFlags = new BlockFlag[]{BlockFlag.core, null};
            hitSize = 60f;
            health = 100000;
            flying = true ;
            range = 120f;
            ammoType = new ItemAmmoType(Items.coal);
            abilities.add(new MoveEffectAbility(){
                              public String localized(){
                                  return "";
                              }
                              {
                                  effect = Fx.missileTrailSmoke;
                                  rotation = 180f;
                                  y = -9f;
                                  color = Color.grays(0.6f).lerp(Pal.lightishGray, 0.5f).a(0.4f);
                                  interval = 3f;
                              }},
                    new SpawnDeathAbility(xiuji,2,5){
                        public String localized(){
                            return Core.bundle.format("ability.kongjiang",amount,unit.emoji(),unit.localizedName);
                        }
                        {
                    }});
            weapons.add(new Weapon(){
                @Override
                public void addStats(UnitType u , Table t){
                    if(inaccuracy > 0){
                        t.row();
                        t.add("[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int)inaccuracy + " " + StatUnit.degrees.localized());
                    }
                    if(!alwaysContinuous && reload > 0){
                        t.row();
                        t.add("[lightgray]" + Stat.reload.localized() + ": " + (mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / reload * shoot.shots, 2) + " " + StatUnit.perSecond.localized());
                    }
                    SYBSStatValues.ammo(ObjectMap.of(u, bullet)).display(t);
                }
                {
                deathExplosionEffect = Fx.massiveExplosion;
                reload = 24f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.largeExplosion;
                x = shootY = 0f;
                mirror = false;
                shake = 20f ;
                shootOnDeath = true ;
                range = 120f ;
                bullet = new BaiFenBiBulletType(0,0,0){{
                    hitColor = Pal.lightishGray ;
                    hitEffect = new MultiEffect(
                            new Effect(30, e -> {
                                    color(Pal.missileYellow);
                                    e.scaled(7, i -> {
                                        stroke(3f * i.fout());
                                        Lines.circle(e.x, e.y, 2*(4f + i.fin() * 30f));
                                    });

                                    color(Pal.lightishGray);

                                    randLenVectors(e.id, 8, 2f + 30f * e.finpow(), (x, y) -> {
                                        Fill.circle(e.x + x, e.y + y, 2*(e.fout() * 4f + 0.5f));
                                    });

                                    color(Pal.missileYellowBack);
                                    stroke(e.fout());

                                    randLenVectors(e.id + 1, 6, 1f + 29f * e.finpow(), (x, y) -> {
                                        lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 2*(1f + e.fout() * 4f));
                                    });

                                    Drawf.light(e.x, e.y, 50f, Pal.missileYellowBack, 0.8f * e.fout());
                            }),
                            new Effect(60f, 160f, e -> {
                                e.color = Pal.lightishGray ;
                                color(e.color);
                                stroke(e.fout() * 5f);
                                float circleRad = 2*(6f + e.finpow() * 60f);
                                Lines.circle(e.x, e.y, circleRad);
                                rand.setSeed(e.id);
                                for(int i = 0; i < 16; i++){
                                    float angle = rand.random(360f);
                                    float lenRand = rand.random(0.5f, 1f);
                                    Tmp.v1.trns(angle, circleRad);

                                    for(int s : Mathf.signs){
                                        Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 40f, e.fout() * 30f * lenRand + 6f, angle + 90f + s * 90f);
                                    }
                                }
                            }),
                            new Effect(60f, 160f, e -> {
                                float circleRad = 2*(6f + e.finpow() * 60f);
                                e.color = Pal.lightishGray ;
                                color(e.color, e.foutpow());
                                Fill.circle(e.x, e.y, circleRad);
                            }).layer(Layer.bullet + 2f),
                            new WaveEffect(){{
                                lifetime = 15f;
                                strokeFrom = 4f;
                                sizeTo = 260f;
                            }}
                    );
                    collidesTiles = false;
                    collides = false;
                    hitSound = Sounds.largeExplosion ;
                    rangeOverride = 120f;
                    speed = 0f;
                    splashDamageRadius = 160f;
                    instantDisappear = true;
                    fanweibaifenbi = 0.5f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};
        shendijidanwei.add(xiuji,lingjian);
        anyong = new QianTingUnitType("anyong"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            health = 270000 ;
            hitSize = 105 ;
            armor = 66 ;
            //abilities.add(new QianShuiAbility()) ;
            immunise(this,true,3);
            naval = true ;
            faceTarget = true ;
            weapons.add(new TeShuWeapon("anyong-fuzhupao"){{
                fanqian = true ;
                reload = 360 ;
                mirror = true ;
                Vec2 v = textureToReal(new Vec2(230, 450), 400, 750) ;
                x = v.x ;
                y = v.y ;
                rotate = true ;
                top = false ;
                targetAir = true ;
                targetGround = true ;
                shootSound = Sounds.missile ;
                bullet = new BasicBulletType(12,0){{
                   splashDamage = 4500 ;
                   splashDamageRadius = 40 ;
                   homingRange = 160 ;
                   homingPower = 0.15f ;
                   homingDelay = 5 ;
                   lifetime = 60 ;
                   trailInterval = 1 ;
                   trailEffect = new Effect(30, e -> {
                       for(int i = 0; i < 2; i++){
                           color(i == 0 ? Pal.accent : Pal.accentBack);

                           float m = i == 0 ? 1f : 0.5f;

                           float rot = e.rotation + 180f;
                           float w = 15f * e.fout() * m;
                           Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot);
                           Drawf.tri(e.x, e.y, w, 10f * m, rot + 180f);
                       }

                       Drawf.light(e.x, e.y, 60f, Pal.accent, 0.6f * e.fout());
                   }) ;
                   width = 20 ;
                   height = 40 ;
                   hitColor = Pal.accent ;
                   hitEffect = titanExplosion ;
                   hitSound = Sounds.artillery ;
                   fragBullets = 5 ;
                   fragBullet = new BasicBulletType(4,0){{
                       splashDamage = 3500 ;
                       splashDamageRadius = 25 ;
                       homingRange = 160 ;
                       homingPower = 0.15f ;
                       homingDelay = 5 ;
                       lifetime = 60 ;
                       width = 20 ;
                       height = 10 ;
                       hitColor = Pal.accent ;
                       hitEffect = titanExplosion ;
                       hitSound = Sounds.artillery ;
                   }};
                }};
            }});
            weapons.add(new TeShuWeapon("anyong-fupao"){{
                reload = 150 ;
                mirror = true ;
                Vec2 v = textureToReal(new Vec2(200, 150), 400, 750) ;
                x = v.x ;
                y = v.y ;
                top = false ;
                targetAir = false ;
                targetGround = false ;
                targetNaval = true ;
                fanqian = true ;
                shootSound = Sounds.shockBlast ;
                //range = 800 ;
                bullet = new BulletType(){{
                    shootEffect = Fx.sparkShoot;
                    smokeEffect = Fx.shootSmokeTitan;
                    hitColor = Pal.suppress;
                    shake = 1f;
                    speed = 0f;
                    keepVelocity = false;
                    collidesAir = false ;
                    spawnUnit = new YuLeiUnitType("anyong-yulei"){{
                        health = 400 ;
                        hittable = false ;
                        lifetime = 180 ;
                        speed = 2.5f ;
                        naval = true ;
                        targetAir = false;
                        maxRange = 5f;
                        outlineColor = Pal.darkOutline;
                        lowAltitude = true;
                        deathExplosionEffect = Fx.none;
                        loopSoundVolume = 0.1f;
                        weapons.add(new TeShuWeapon(){
                            @Override
                            public void addStats(UnitType u , Table t){
                                if(inaccuracy > 0){
                                    t.row();
                                    t.add("[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int)inaccuracy + " " + StatUnit.degrees.localized());
                                }
                                if(!alwaysContinuous && reload > 0){
                                    t.row();
                                    t.add("[lightgray]" + Stat.reload.localized() + ": " + (mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / reload * shoot.shots, 2) + " " + StatUnit.perSecond.localized());
                                }
                                SYBSStatValues.ammo(ObjectMap.of(u, bullet)).display(t);
                            }
                            {
                            fanqian = true ;
                            controllable = false ;
                            autoTarget = true ;
                            targetGround = targetAir = false ;
                            targetNaval = true ;
                            shootCone = 360f;
                            reload = 1 ;
                            shootOnDeath = true ;
                            //alwaysShooting = true ;
                            bullet = new TeShuExplosionBulletType(6000,80){{
                                hitColor = Pal.accent ;
                                shootEffect = hitEffect = titanExplosion;
                                hitSound = Sounds.artillery;
                                //width = 24 ;
                                //height = 24 ;
                                fragBullets = 10;
                                fragSpread = 5f ;
                                //killShooter = false ;
                                fragBullet = new ShuiLeiBulletType(0, 0) {{
                                    backColor = Pal.lightishGray ;
                                    splashDamage = 2500;
                                    splashDamageRadius = 80;
                                    lifetime = Float.POSITIVE_INFINITY;
                                    shootEffect = hitEffect = titanExplosion;
                                    hitSound = Sounds.artillery;
                                }};
                                naval = 2 ;
                            }} ;
                        }}) ;
                    }} ;
                }};
            }});
            weapons.add(new Weapon("anyong-zhupao"){{
                reload = 1200 ;
                mirror = false ;
                Vec2 v = textureToReal(new Vec2(199, 453), 400, 750) ;
                x = v.x ;
                y = v.y ;
                rotate = true ;
                top = false ;
                shootSound = 1 + 1 == 3 ? Sounds.shockBlast : Sounds.corexplode ;
                shoot = new ShootAlternate(40f);
                shoot.shots = 2 ;
                targetAir = true ;
                targetGround = true ;
                bullet = new PointBulletType(){{
                    trailEffect = SYBSFx.guidaopaolujing ;
                    trailColor = Pal.accent ;
                    trailInterval = 1 ;
                    speed = 800 ;
                    lifetime = 1 ;
                    damage = 6000 ;
                    status = SYBSStatusEffects.dangji ;
                    statusDuration = 300 ;
                    splashDamage = 3200 ;
                    splashDamageRadius = 80 ;
                    hitEffect = SYBSFx.guidaopaomingzhong;
                    fragBullet = new BasicBulletType(6,0){{
                        splashDamage = 3200 ;
                        splashDamageRadius = 25 ;
                        homingRange = 160 ;
                        homingPower = 0.15f ;
                        homingDelay = 5 ;
                        lifetime = 60 ;
                        trailColor = Pal.accentBack ;
                        trailWidth = 2.1f;
                        trailLength = 10;
                        width = 15 ;
                        height = 30 ;
                        hitColor = Pal.accent ;
                        hitEffect = Fx.instHit ;
                        hitSound = Sounds.explosionbig ;
                        fragBullets = 3 ;
                        fragAngle = 120 ;
                        fragBullet = new LaserBulletType(3200){{
                            length = 160 ;
                            width = 20 ;
                        }};
                    }};
                }};
            }});
            weapons.add(new Weapon("anyong-shuilei"){{
                alwaysShooting = true ;
                x = 0 ;
                y = 0 ;
                shootSound = Sounds.plasmadrop ;
                //hidden = true ;
                reload = 1200 ;
                bullet = new ShuiLeiBulletType(0,0) {{
                    splashDamage = 1200 ;
                }};
            }});
        }} ;
        yaofeng = new UnitType("yaofeng"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            abilities.add(new YaoFengXiJuanAbility(),new ShanXianAbility(){{shanxianchangdu = 30;drawCircle = false;drawLine = false;}});
            hitSize = 50 ;
            health = 190000 ;
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
        }};
        moneng = new ErekirUnitType("moneng"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            aiController = FlyingFollowAI::new;
            envDisabled = 0;
            abilities.add(new CeFanAbility()) ;
            lowAltitude = false;
            flying = true;
            drag = 0.07f;
            speed = 0.85f;
            rotateSpeed = 1.5f;
            accel = 0.1f;
            health = 48000f;
            armor = 12f;
            hitSize = 60f;
            payloadCapacity = Mathf.sqr(8f) * tilePayload;

            engineSize = 6f / 46f * 60f ;
            engineOffset = 25.25f / 46f * 60f ;

            float orbRad = 5f / 46f * 60f, partRad = 3f / 46f * 60f;
            int parts = Math.round(10f / 46f * 60f);

            Vec2 v = textureToReal(new Vec2(300, 240), 330, 330) ;
            for (int i = 360 ; i >= 180 ; i -= 36) {
                int finalI = i;
                Vec2 v2 = ShaYeBuShi.circle(finalI, v.dst(0, 0) + 2 * tilesize, 0, 0) ;
                abilities.add(new SuppressionFieldAbility() {{
                    orbRadius = orbRad;
                    particleSize = partRad;
                    x = v2.x ;
                    y = v2.y ;
                    particles = parts;
                }});
                Weapon w = xieling.weapons.get(1).copy() ;
                w.shoot = w.shoot.copy() ;
                w.shoot.shots = 18 ;
                w.shoot.shotDelay = 10 ;
                w.reload = 600 ;
                w.x = v2.x ;
                w.y = v2.y ;
                w.shootX = w.shootY = 0 ;
                w.bullet = w.bullet.copy() ;
//                if (w.bullet instanceof BasicBulletType b) {
//                    b.backColor = Pal.suppress ;
//                }
                w.parts = new Seq<>() ;
                var circleColor = Pal.suppress ;
                var circleProgress = DrawPart.PartProgress.warmup.delay(0.9f);
                float circleY = 25f, circleRad = 11f, circleRotSpeed = 3.5f, circleStroke = 1.6f;
                float haloY = -15f, haloRotSpeed = 1.5f;
                w.parts.addAll(
                        new ShapePart(){{
                            progress = circleProgress;
                            color = circleColor;
                            circle = true;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad;
                            layer = Layer.effect;
                            y = circleY;
                        }},
                        new ShapePart(){{
                            progress = circleProgress;
                            rotateSpeed = -circleRotSpeed;
                            color = circleColor;
                            sides = 4;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad - 1f;
                            layer = Layer.effect;
                            y = circleY;
                        }},
                        new ShapePart(){{
                            progress = circleProgress;
                            rotateSpeed = -circleRotSpeed;
                            color = circleColor;
                            sides = 4;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad - 1f;
                            layer = Layer.effect;
                            y = circleY;
                        }},
                        new ShapePart(){{
                            progress = circleProgress;
                            rotateSpeed = -circleRotSpeed/2f;
                            color = circleColor;
                            sides = 4;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = 2f;
                            radius = 3f;
                            layer = Layer.effect;
                            y = circleY;
                        }},
                        new HaloPart(){{
                            progress = circleProgress;
                            color = circleColor;
                            tri = true;
                            shapes = 3;
                            triLength = 0f;
                            triLengthTo = 5f;
                            radius = 6f;
                            haloRadius = circleRad;
                            haloRotateSpeed = haloRotSpeed / 2f;
                            shapeRotation = 180f;
                            haloRotation = 180f;
                            layer = Layer.effect;
                            y = circleY;
                        }}
                );
                w.parts.each(p -> {
                    if (p instanceof ShapePart s) {
                        s.color = Pal.suppress ;
                        s.x = 0 ;
                        s.y = 0 ;
                    }
                    if (p instanceof HaloPart h) {
                        h.color = Pal.suppress ;
                        h.x = 0 ;
                        h.y = 0 ;
                    }
                });
                w.bullet.hitColor = w.bullet.trailColor = w.bullet.lightningColor = Pal.suppress ;
                weapons.add(w) ;
            }

//            for(int i : Mathf.signs){
//                abilities.add(new SuppressionFieldAbility(){{
//                    orbRadius = orbRad;
//                    particleSize = partRad;
//                    y = 10f;
//                    x = 56f * i / 4f;
//                    particles = parts;
//                }});
//            }

            weapons.add(new Weapon("moneng-zhupao"){{
                shootSound = Sounds.missileLarge;
                x = 78f / 4f / 46f * 60f ;
                y = -10f / 4f / 46f * 60f ;
                mirror = true;
                rotate = true;
                rotateSpeed = 0.4f;
                reload = 60f;
                layerOffset = -20f;
                recoil = 1f;
                rotationLimit = 22f;
                minWarmup = 0.95f;
                shootWarmupSpeed = 0.1f;
                shootY = 2f;
                shootCone = 40f;
                shoot.shots = 6;
                shoot.shotDelay = 5f;
                inaccuracy = 28f;

                parts.add(new RegionPart("-blade"){{
                    heatProgress = PartProgress.warmup;
                    progress = PartProgress.warmup.blend(PartProgress.reload, 0.15f);
                    heatColor = Color.valueOf("9c50ff");
                    x = 5 / 4f;
                    y = 0f;
                    moveRot = -33f;
                    moveY = -1f;
                    moveX = -1f;
                    under = true;
                    mirror = true;
                }}, new RegionPart("-daodan"){{
                    progress = PartProgress.reload.curve(Interp.pow2In);

                    colorTo = new Color(1f, 1f, 1f, 0f);
                    color = Color.white;
                    mixColorTo = Pal.accent;
                    mixColor = new Color(1f, 1f, 1f, 0f);
                    outline = false;
                    under = true;

                    layerOffset = -0.01f;

                    moves.add(new PartMove(PartProgress.warmup.inv(), 0f, -4f, 0f));
                }});

                bullet = new BulletType(){{
                    shootEffect = Fx.sparkShoot;
                    smokeEffect = Fx.shootSmokeTitan;
                    hitColor = Pal.suppress;
                    shake = 2f;
                    speed = 0f;
                    keepVelocity = false;

                    spawnUnit = new MissileUnitType("moneng-daodan"){{
                        speed = 5f;
                        maxRange = 5f;
                        outlineColor = Pal.darkOutline;
                        health = 140;
                        homingDelay = 10f;
                        lowAltitude = true;
                        engineSize = 3f / 46f * 60f ;
                        engineColor = trailColor = Pal.sapBulletBack;
                        engineLayer = Layer.effect;
                        deathExplosionEffect = Fx.none;
                        loopSoundVolume = 0.1f;

                        parts.add(new ShapePart(){{
                            layer = Layer.effect;
                            circle = true;
                            y = -0.25f;
                            radius = 1.5f;
                            color = Pal.suppress;
                            colorTo = Color.white;
                            progress = PartProgress.life.curve(Interp.pow5In);
                        }});
                        parts.add(new FlarePart(){{
                            progress = PartProgress.life.slope().curve(Interp.pow2In);
                            radius = 0f;
                            radiusTo = 50f;
                            stroke = 5f;
                            rotation = 45f;
                            y = -5f;
                            color1 = Pal.suppress ;
                            followRotation = true;
                        }});
                        parts.add(new RegionPart("-fin"){{
                            mirror = true;
                            progress = PartProgress.life.mul(3f).curve(Interp.pow5In);
                            moveRot = 32f;
                            rotation = -6f;
                            moveY = 1.5f;
                            x = 3f / 4f;
                            y = -6f / 4f;
                        }});

                        weapons.add(new Weapon(){{
                            shootCone = 360f;
                            mirror = false;
                            reload = 1f;
                            shootOnDeath = true;
                            bullet = new ExplosionBulletType(420f, 32f){{
                                suppressionRange = 140f / 46f * 60f ;
                                shootEffect = new ExplosionEffect(){{
                                    lifetime = 50f / 46f * 60f ;
                                    waveStroke = 5f / 46f * 60f ;
                                    waveLife = 8f / 46f * 60f ;
                                    waveColor = Color.white;
                                    sparkColor = smokeColor = Pal.suppress;
                                    waveRad = 40f / 46f * 60f ;
                                    smokeSize = 4f / 46f * 60f ;
                                    smokes = Math.round(7 / 46f * 60f) ;
                                    smokeSizeBase = 0f;
                                    sparks = Math.round(10 / 46f * 60f) ;
                                    sparkRad = 40f / 46f * 60f ;
                                    sparkLen = 6f / 46f * 60f ;
                                    sparkStroke = 2f / 46f * 60f ;
                                }};
                            }};
                        }});
                    }};
                }};
            }});

//            setEnginesMirror(
//                    new UnitEngine(95 / 46f * 60f  / 4f, -56 / 46f * 60f  / 4f, 5f / 46f * 60f , 330f),
//                    new UnitEngine(89 / 46f * 60f  / 4f, -95 / 46f * 60f  / 4f, 4f / 46f * 60f , 315f),
//                    new UnitEngine(102 / 4f, -68 / 4f, 5f / 46f * 60.75f , 300f),
//                    new UnitEngine(114 / 4f, -108 / 4f, 4f / 46f * 60.75f , 285f)
//            );
        }} ;
        z01 = new ZhanJiUnitType("z01"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
//            constructor = DieTaiUnitEntity::new ;
            aiController = flare.aiController;
            //isEnemy = false;
            //constructor = ZhanJiUnitEntity::new ;
            lowAltitude = true;
            flying = true;
            drag = 0.05f;
            speed = 5f;
            rotateSpeed = 19f;
            accel = 0.11f;
            itemCapacity = 90;
            health = 8000f;
            engineOffset = 6f;
            hitSize = 16f;
            armor = 18 ;
            useUnitCap = false ;
            circleTarget = true;
            weapons.add(new Weapon("z01-zhupao"){
                @Override
                public void addStats(UnitType u, Table t){
                    if(inaccuracy > 0){
                        t.row();
                        t.add("[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int)inaccuracy + " " + StatUnit.degrees.localized());
                    }
                    if(!alwaysContinuous && reload > 0){
                        t.row();
                        t.add("[lightgray]" + Stat.reload.localized() + ": " + (mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / reload * shoot.shots, 2) + " " + StatUnit.perSecond.localized());
                    }

                    SYBSStatValues.ammo(ObjectMap.of(u, bullet)).display(t);
                }
                {
                top = false;
                reload = 1f;
                x = 1f;
                y = 2f;
                shoot = new ShootAlternate(){{
                    barrels = 4 ;
                    spread = 4 ;
                    shots = 4 ;
                }};

                inaccuracy = 1.2f;
                ejectEffect = Fx.casing1;
//                shootSound = locus.weapons.first().shootSound ;
                bullet = new TeShuBasicBulletType(15f, 160){{
                    width = 4f;
                    height = 11f;
                    lifetime = 18f;
                    shootEffect = Fx.shootSmall;
                    smokeEffect = Fx.shootSmallSmoke;
                    buildingDamageMultiplier = 0.5f;
                    buildingDamageMultiplier = 1f;
                }};
//                bullet = new TeShuRailBulletType(){{
//                    length = 160f;
//                    damage = 160f;
//                    air = 1.5f ;
//                    hitColor = Color.valueOf("feb380");
//                    hitEffect = endEffect = Fx.hitBulletColor;
//                    pierceDamageFactor = 0.8f;
//
//                    smokeEffect = Fx.colorSpark;
//
//                    endEffect = new Effect(14f, e -> {
//                        color(e.color);
//                        Drawf.tri(e.x, e.y, e.fout() * 1.5f, 5f, e.rotation);
//                    });
//
//                    shootEffect = new Effect(10, e -> {
//                        color(e.color);
//                        float w = 1.2f + 7 * e.fout();
//
//                        Drawf.tri(e.x, e.y, w, 30f * e.fout(), e.rotation);
//                        color(e.color);
//
//                        for(int i : Mathf.signs){
//                            Drawf.tri(e.x, e.y, w * 0.9f, 18f * e.fout(), e.rotation + i * 90f);
//                        }
//
//                        Drawf.tri(e.x, e.y, w, 4f * e.fout(), e.rotation + 180f);
//                    });
//
//                    lineEffect = new Effect(20f, e -> {
//                        if(!(e.data instanceof Vec2 v)) return;
//
//                        color(e.color);
//                        stroke(e.fout() * 0.9f + 0.6f);
//
//                        Fx.rand.setSeed(e.id);
//                        for(int i = 0; i < 7; i++){
//                            Fx.v.trns(e.rotation, Fx.rand.random(8f, v.dst(e.x, e.y) - 8f));
//                            Lines.lineAngleCenter(e.x + Fx.v.x, e.y + Fx.v.y, e.rotation + e.finpow(), e.foutpowdown() * 20f * Fx.rand.random(0.5f, 1f) + 0.3f);
//                        }
//
//                        e.scaled(14f, b -> {
//                            stroke(b.fout() * 1.5f);
//                            color(e.color);
//                            Lines.line(e.x, e.y, v.x, v.y);
//                        });
//                    });
//                }};
            }});
            Weapon w = anthicus.weapons.first().copy() ;
            w.reload = 1200 ;
            w.mirror = true ;
            w.x = w.y = 0 ;
            w.bullet.spawnUnit.weapons.first().bullet.splashDamage = 1200 ;
            w.bullet.spawnUnit.weapons.first().bullet.collidesGround = false ;
            w.bullet.spawnUnit.weapons.first().bullet.splashDamageRadius = 32 ;
            w.bullet.spawnUnit.speed = 7.5f ;
            w.bullet.spawnUnit.lifetime = 60;
            w.shootStatus = StatusEffects.none ;
            weapons.add(w) ;
            shanbi = 0.8f ;
        }};
        chengbang = new UnitType("chengbang"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
           hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
           health = 540000 ;
           armor = 260 ;
           hitSize = 120 ;
           //constructor = WaterMovePayLoadUnit::new ;
           abilities.add(new ZaiJiAbility(){{
               be.x = be.x / 36f * 120 ;
               be.y = be.y / 36f * 120 ;
               en.x = en.x / 36f * 120 ;
               en.y = en.y / 36f * 120 ;
           }});
           speed = 0.35f ;
           payloadCapacity = (280f * 280f) * tilePayload;
           naval = true ;
            canDrown = false;
            omniMovement = false;
            immunities.add(StatusEffects.wet);
            if(shadowElevation < 0f){
                shadowElevation = 0.11f;
            }
            weapons.add(new Weapon("chengbang-fupao"){{
                rotate = true ;
                recoil = 5f;
                shootY = 7f;
                inaccuracy = 7 ;
                shoot = new ShootAlternate(){{
                    spread = 8 ;
                    shots = 2 ;
                }};
                reload = 1 ;
                shootSound = Sounds.lasershoot;
                bullet = new LaserBoltBulletType(6f, 160){{
                    collidesGround = false ;
                    lifetime = 35f;
                    healPercent = 0.1f;
                    collidesTeam = true;
                    backColor = Pal.heal;
                    frontColor = Color.white;
                    pierceArmor = true ;
                }};
                x = 20 ;
                y = 20 ;
            }}, new Weapon("chengbang-zhupao"){{
                rotate = true ;
                shoot = new ShootBarrel() {{
                    barrels = new float[]{
                      16, 16, 0,
                      16, -16, 0,
                      -16, 16, 0,
                      -16, -16, 0,
                      12, 12, 0,
                      12, -12, 0,
                      -12, 12, 0,
                      -12, -12, 0,
                      8, 8, 0,
                      8, -8, 0,
                      -8, 8, 0,
                      -8, -8, 0,
                       4, 4, 0,
                       4, -4, 0,
                       -4, 4, 0,
                       -4, -4, 0
                    };
                    shotDelay = 15 ;
                    shots = 16 ;
                }};
                mirror = false ;
                reload = 600 ;
                shootY = 10f;
                shootSound = Sounds.missileLarge ;
                bullet = new BulletType(){{
                    shootEffect = Fx.sparkShoot;
                    smokeEffect = Fx.shootSmokeTitan;
                    hitColor = Pal.heal;
                    shake = 1f;
                    speed = 0f;
                    keepVelocity = false;

                    spawnUnit = new MissileUnitType("chengbang-daodan"){{
                        //targetAir = false;
                        speed = 4.6f;
                        lifetime = 60 * 3 ;
                        maxRange = 5f;
                        outlineColor = Pal.darkOutline;
                        health = 640;
                        homingDelay = 10f;
                        lowAltitude = true;
                        engineSize = 3f;
                        engineColor = Pal.heal;
                        engineLayer = Layer.effect;
                        deathExplosionEffect = Fx.none;
                        loopSoundVolume = 0.1f;
                        abilities.add(new MoveEffectAbility(){{
                            effect = Fx.missileTrailSmoke;
                            rotation = 180f;
                            y = -9f;
                            color = Color.grays(0.6f).lerp(Pal.heal, 0.5f).a(0.4f);
                            interval = 7f;
                        }}) ;
                        weapons.add(new Weapon(){{
                            shootCone = 360f;
                            mirror = false;
                            reload = 1f;
                            shootOnDeath = true;
                            bullet = new ExplosionBulletType(1600f, 2.5f * 8){{
                                //collidesAir = false;
                                //suppressionRange = 140f;
                                shootEffect = new ExplosionEffect(){{
                                    lifetime = 50f;
                                    waveStroke = 5f;
                                    waveLife = 8f;
                                    waveColor = Color.white;
                                    sparkColor = smokeColor = Pal.heal;
                                    waveRad = 40f;
                                    smokeSize = 4f;
                                    smokes = 7;
                                    smokeSizeBase = 0f;
                                    sparks = 10;
                                    sparkRad = 40f;
                                    sparkLen = 6f;
                                    sparkStroke = 2f;
                                }};
                            }};
                        }});
                        Weapon w = weapons.get(0).copy() ;
                        w.y = -w.y ;
                        w.x = -w.x ;
                        weapons.add(w) ;
                    }};
                }};
            }});
        }};
        affh01 = new ZhanJiUnitType("affh01"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
//            constructor = DieTaiUnitEntity::new ;
            aiController = horizon.aiController;
            //isEnemy = false;
            //constructor = ZhanJiUnitEntity::new ;
            lowAltitude = true;
            flying = true;
            drag = 0.05f;
            speed = 5f / 40 * 28;
            rotateSpeed = 19f;
            accel = 0.11f;
            itemCapacity = 90;
            health = 12000f;
            engineOffset = 6f;
            hitSize = 20f;
            armor = 18 ;
            useUnitCap = false ;
            circleTarget = true;
            weapons.add(new Weapon("affh01-zhupao"){
                @Override
                public void addStats(UnitType u, Table t){
                    if(inaccuracy > 0){
                        t.row();
                        t.add("[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int)inaccuracy + " " + StatUnit.degrees.localized());
                    }
                    if(!alwaysContinuous && reload > 0){
                        t.row();
                        t.add("[lightgray]" + Stat.reload.localized() + ": " + (mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / reload * shoot.shots, 2) + " " + StatUnit.perSecond.localized());
                    }

                    SYBSStatValues.ammo(ObjectMap.of(u, bullet)).display(t);
                }
                {
                    top = false;
                    reload = 420f;
                    shoot = new ShootBarrel(){{
                        barrels = new float[]{
                          6, -6, 0,
                          6, 6, 0,
                          -6, -6, 0,
                          -6, 6, 0
                        };
                        shots = 80;
                        shotDelay = 60 * 5 / 80f;
                    }};
                    inaccuracy = 20f;
                    ejectEffect = Fx.none;
                    ignoreRotation = true;
                    shootSound = Sounds.none ;
                    bullet = new BombBulletType(600, 3.5f * 8){{
                        width = 10f;
                        height = 20f;
                        lifetime = 60f;
                        hitEffect = Fx.flakExplosion;
                        buildingDamageMultiplier = 1.25f;
                        buildingDamageMultiplier = 1f;
                        incendChance = 1 ;
                        incendAmount = 700 ;
                        incendSpread = 6 ;
                    }};
                }});
            shanbi = 0.35f ;
            hongzha = true ;
            zhandou = false ;
        }};
        qianneng = new UnitType("qianneng") {
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
            health = 300 ;
            armor = 4 ;
            speed = 3 * tilesize / toSeconds ;
            hitSize = dagger.hitSize ;
            faceTarget = true ;
            weapons.add(new Weapon("qiannneng-zhupao") {{
                mirror = true ;
                reload = dagger.weapons.get(0).reload ;
                range = 16 * tilesize ;
                x = 4f;
                y = 2f;
                top = false;
                ejectEffect = Fx.casing1;
                bullet = new BasicBulletType(8, 17){{
                    width = 7f;
                    height = 9f;
                    lifetime = 16 * tilesize / 8f ;
                }} ;
            }});
        }};
        jianglin = new UnitType("jianglin") {
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
            health = 320 ;
            armor = 5 ;
            speed = 3.5f * tilesize / toSeconds ;
            hitSize = nova.hitSize ;
            canBoost = true;
            boostMultiplier = 1.5f;
            faceTarget = true ;
            buildSpeed = 0.95f ;
            mineSpeed = 4 ;
            mineTier = 2 ;
            abilities.add(new RepairFieldAbility(60, 5f * toSeconds, 20 * tilesize)) ;
            weapons.add(new Weapon("qiannneng-zhupao") {{
                mirror = true ;
                reload = 30f ;
                range = 20 * tilesize ;
                x = 4f;
                y = 2f;
                top = false;
                shootSound = Sounds.lasershoot;
                shoot = new ShootSpread() {{
                   shots = 5 ;
                   spread = 5 ;
                }};
                bullet = new LaserBoltBulletType(8, 10){{
                    pierceArmor = true ;
                    healPercent = 5f;
                    collidesTeam = true;
                    backColor = Pal.heal;
                    frontColor = Color.white;
                    lifetime = 20 * tilesize / 8f ;
                }} ;
            }});
        }};
        tianping = new UnitType("tianping"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
            health = 10500 ;
            armor = 35 ;
            speed = 3.2f / 60f * tilesize;
            hitSize = 4 * tilesize ;
            //miaoxianshang = 500 ;
            //dancixianshang = 500 ;
            immunities.addAll(StatusEffects.burning, StatusEffects.melting, StatusEffects.tarred, StatusEffects.wet, StatusEffects.shocked, SYBSStatusEffects.huaxuedianran, SYBSStatusEffects.zhongdufushi, SYBSStatusEffects.qiangxiaodianji);
            abilities.addAll(new UnitSpawnAbility(qianneng, 40 * toSeconds, 10, 0), new UnitSpawnAbility(qianneng, 40 * toSeconds, -10, 0),
                    new ZhenDangHuDunAbility(25 * tilesize / 4f, 1, 7200, 45 * toSeconds, 4, 45, 100) {{
                        statuss = Seq.with(SYBSStatusEffects.duidies.get("paichi").get(2), SYBSStatusEffects.duidies.get("biaoji").get(0));
                        heal = 40;
                    }});
            Weapon w = new Weapon("tianping-fuzhupao"){{
                x = 10 ;
                y = -10 ;
                rotate = true ;
                reload = 90 ;
                shootSound = Sounds.artillery ;
                shake = 3 ;
                targetGround = targetAir = true ;
                bullet = new ArtilleryBulletType(){{
                    collidesAir = collidesGround = true ;
                    lifetime = 120 ;
                    speed = 46 * tilesize / lifetime ;
                    damage = 600 ;
                    pierceCap = 3 ;
                    pierce = true ;
                    fragBullets = 3 ;
                    hitSound = Sounds.explosionbig ;
                    hitEffect = titanExplosion ;
                    hitColor = Pal.accent ;
                    shootEffect = shootBig2 ;
                    despawnHit = true ;
                    shake = despawnShake = 3 ;
                    trailWidth = width = height = 2 * tilesize ;
                    fragBullet = new ArtilleryBulletType() {{
                        lifetime = 120 ;
                        speed = 23 * tilesize / lifetime ;
                        splashDamage = 320 ;
                        splashDamageRadius = 2 * tilesize ;
                        pierceArmor = true ;
                        hitSound = Sounds.explosionbig ;
                        hitColor = Pal.accent ;
                        despawnHit = true ;
                        hitEffect = titanExplosion ;
                        shootEffect = shootBig2 ;
                        shake = despawnShake = 2 ;
                        trailWidth = width = height = tilesize ;
                    }};
                }};
            }};
            Weapon w2 = w.copy() ;
            w2.y = -w2.y ;
            weapons.add(w, w2, new Weapon("tianping-zhupao"){{
                x = 0 ;
                y = 0 ;
                mirror = false ;
                rotate = true ;
                reload = 60 * 12 ;
                shake = 3 ;
                shootSound = Sounds.shockBlast ;
                bullet = new PointBulletType(){{
                    lifetime = 1 ;
                    speed = 46 * tilesize / lifetime ;
                    damage = 680 ;
                    splashDamage = 700 ;
                    splashDamageRadius = 10 * tilesize ;
                    pierce = true ;
                    fragOnHit = false ;
                    fragBullets = 1 ;
                    despawnHit = true ;
                    shake = despawnShake = 2 ;
                    shootEffect = Fx.instShoot;
                    hitEffect = Fx.instHit;
                    smokeEffect = Fx.smokeCloud;
                    trailEffect = SYBSFx.jiangeHuang;
                    despawnEffect = Fx.instBomb;
                    trailSpacing = 20f;
                    fragBullet = new ExplosionBulletType() {{
                        killShooter = false ;
                        splashDamage = 1200 ;
                        splashDamageRadius = 15 * tilesize ;
                        hitSound = Sounds.explosionbig ;
                        hitColor = Pal.accent ;
                        despawnHit = true ;
                        hitEffect = titanExplosion ;
                        shootEffect = shootBig2 ;
                        fragBullets = 15 ;
                        shake = despawnShake = 4 ;
                        fragBullet = new BasicBulletType(){
                            @Override
                            public void hitEntity(Bullet b, Hitboxc ht, float h) {
                                super.hitEntity(b, ht, h);
                                if (b.owner instanceof Healthc he) {
                                    he.heal(damage);
                                }
                            }
                            {
                                damage = 120 ;
                                pierceArmor = true ;
                                homingRange = 160f ;
                                homingPower = 0.2f ;
                                homingDelay = 20 ;
                                shake = despawnShake = 2 ;
                                hitColor = Pal.accent ;
                                despawnHit = true ;
                                trailColor = Pal.accent ;
                                trailLength = 3 * tilesize ;
                                trailWidth = width = height = tilesize ;
                                lifetime = 120 ;
                                speed = 20 * tilesize / lifetime ;
                        }};
                    }};
                }};
            }}) ;
        }};
        chichao = new UnitType("chichao") {
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            health = 66000;
            speed = 0.58f;
            drag = 0.18f;
            hitSize = 62f;
            armor = 22f;
            accel = 0.19f;
            rotateSpeed = 0.8f;
            faceTarget = false;
            immunities.add(SYBSStatusEffects.zhongdufushi);
            naval = true ;
            abilities.add(new ArmorPlateAbility(){{healthMultiplier = 0.25f;}}, new YouJunJiaQiangAbility(0.02f, 0.5f), new ShieldArcAbility(){{
                region = "anthicus" ;
                radius = 62f;
                angle = 100f;
                regen = 160 / toSeconds;
                cooldown = 60f * 5f;
                max = 5000f;
                y = -40f;
                width = 6f;
                whenShooting = false;
            }}, new ShieldArcAbility(){{
                radius = 62f;
                angle = 120f;
                regen = 160 / toSeconds;
                cooldown = 60f * 5f;
                max = 5000f;
                y = -20f;
                width = 6f;
                whenShooting = true;
            }}) ;
            float spawnTime = 60f * 5f;

            abilities.add(new UnitSpawnAbility(horizon, spawnTime, 19.25f, -31.75f), new UnitSpawnAbility(horizon, spawnTime, -19.25f, -31.75f));

            trailLength = 70;
            waveTrailX = 23f;
            waveTrailY = -32f;
            trailScl = 3.5f;

            weapons.add(new TeShuWeapon("chichao-fuzhupao"){{
                fanqian = true ;
                targetAir = false ;
                targetNaval = true ;
                targetGround = false ;
                reload = 60f;
                cooldownTime = 90f;
                mirror = true;
                x = 0f;
                y = -3.5f;
                shake = 1f;
                rotate = true ;
                bullet = new BulletType(){{
                    shootEffect = Fx.sparkShoot;
                    smokeEffect = Fx.shootSmokeTitan;
                    hitColor = Pal.suppress;
                    shake = 1f;
                    speed = 0f;
                    keepVelocity = false;
                    collidesAir = false ;
                    spawnUnit = new YuLeiUnitType("chichao-yulei"){{
                        health = 400 ;
                        hittable = false ;
                        speed = 2.5f ;
                        lifetime = 320 / speed ;
                        naval = true ;
                        targetAir = false;
                        maxRange = 5f;
                        outlineColor = Pal.darkOutline;
                        lowAltitude = true;
                        deathExplosionEffect = Fx.none;
                        loopSoundVolume = 0.1f;
                        weapons.add(new TeShuWeapon(){{
                                fanqian = true ;
                                controllable = false ;
                                autoTarget = true ;
                                targetGround = targetAir = false ;
                                targetNaval = true ;
                                shootCone = 360f;
                                reload = 1 ;
                                shootOnDeath = true ;
                                //alwaysShooting = true ;
                                bullet = new TeShuExplosionBulletType(640,40){{
                                    hitColor = Pal.accent ;
                                    shootEffect = hitEffect = titanExplosion;
                                    hitSound = Sounds.artillery;
                                    //width = 24 ;
                                    //height = 24 ;
                                    fragBullets = 10;
                                    fragSpread = 5f ;
                                    naval = 2 ;
                                    //killShooter = false ;
                                    fragBullet = new ShuiLeiBulletType(0, 0) {{
                                        backColor = Pal.lightishGray ;
                                        splashDamage = 320;
                                        splashDamageRadius = 40;
                                        lifetime = Integer.MAX_VALUE;
                                        shootEffect = hitEffect = titanExplosion;
                                        hitSound = Sounds.artillery;
                                    }};
                                }} ;
                            }}) ;
                    }} ;
                }};

            }}
                    /*
                    , new Weapon("chichao-zhupao"){{
                x = -10 ;
                y = 0 ;
                mirror = false ;
                rotate = true ;
                reload = 60 * 10 ;
                shake = 3 ;
                shootSound = Sounds.missileSmall ;
                shoot = new ShootSpread(360, 0.25f) ;
                bullet = SYBSBlocks.langyong.ammoTypes.get(Items.blastCompound).copy() ;
                bullet.lifetime *= 1.5f ;
                bullet.speed *= 1.5f ;
                bullet.damage = 200 ;
                bullet.splashDamage *= 1.5f ;
            }}
                     */
                    , new Weapon("chichao-zhupao"){{
                x = 0 ;
                y = 0 ;
                targetAir = targetGround = true ;
                mirror = false ;
                rotate = true ;
                reload = 180 ;
                shake = 3 ;
                shootSound = Sounds.missileSmall ;
                shoot = new ShootMulti(new ShootSpread(8, 5){{shotDelay = 2.5f;}}, new ShootPattern(){{shots = 10;shotDelay = 1f;}}) ;
                bullet = SYBSBlocks.langyong.ammoTypes.get(Items.blastCompound).copy() ;
                bullet.lifetime *= 1.5f ;
                bullet.speed *= 1.5f ;
                bullet.damage = 80 ;
                bullet.splashDamage *= 1.25f ;
                bullet.collidesGround = bullet.collidesAir = true ;
            }}, new Weapon("chichao-fupao"){{
                shake = 4f;
                shootY = 9f;
                x = 18f;
                y = 5f;
                rotateSpeed = 2f;
                reload = 60f;
                recoil = 4f;
                shootSound = Sounds.laser;
                shadow = 20f;
                rotate = true;
                bullet = new LaserBulletType(){{
                    damage = 400f;
                    width = 15f;
                    length = 320f;
                    shootEffect = Fx.shockwave;
                    colors = new Color[]{Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.white};
                }};
            }});
        }};
        shuihua = new UnitType("shuihua") {
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            health = 60000;
            speed = 0.7f;
            drag = 0.17f;
            hitSize = 62f;
            armor = 20f;
            accel = 0.2f;
            rotateSpeed = 1.2f;
            faceTarget = false;
            ammoType = new PowerAmmoType(4500);
            immunities.add(SYBSStatusEffects.zhongdufushi) ;
            naval = true ;
            abilities.addAll(new RepairFieldAbility(2000, 5 * toSeconds, hitSize * 20), new UnitSpawnAbility(mega, 15 * toSeconds, -117 / 4f, 0), new UnitSpawnAbility(mega, 15 * toSeconds, 117 / 4f, 0), new UnitSpawnAbility(oxynoe, 5 * toSeconds, 0, 117 / 4f), new UnitSpawnAbility(oxynoe, 5 * toSeconds, 0, -117 / 4f), new ForceFieldAbility(hitSize, 120 / toSeconds, 5000, 5 * toSeconds, 4, 45), new ShieldArcAbility(){{
                radius = hitSize ;
                angle = 360 ;
                max = 5000 ;
                regen = 160f / toSeconds ;
                region = "cyerce" ;
                whenShooting = false ;
            }}) ;
            trailLength = 70;
            waveTrailX = 23f;
            waveTrailY = -32f;
            trailScl = 3.5f;

            buildSpeed = 3.5f;

            for(float mountY : new float[]{-117/4f, 50/4f}){
                for(float sign : Mathf.signs){
                    weapons.add(new Weapon("shuihua-fupao"){{
                        shadow = 20f;
                        controllable = true;
                        //autoTarget = true;
                        mirror = true;
                        shake = 3f;
                        shootY = 7f;
                        rotate = true;
                        x = 84f/4f * sign;
                        y = mountY;

                        targetInterval = 20f;
                        targetSwitchInterval = 35f;

                        rotateSpeed = 3.5f;
                        reload = 300f;
                        recoil = 1f;
                        shootSound = Sounds.beam;
                        continuous = true;
                        cooldownTime = reload;
                        immunities.add(StatusEffects.burning);

                        bullet = new ContinuousLaserBulletType(){{
                            maxRange = 90f;
                            damage = 27f * 3;
                            length = 95f * 3;
                            hitEffect = Fx.hitMeltHeal;
                            drawSize = 200f;
                            lifetime = 155f * 2;
                            shake = 1f * 2;

                            shootEffect = Fx.shootHeal;
                            smokeEffect = Fx.none;
                            width = 4f * 3 ;
                            largeHit = false;

                            incendChance = 1f;
                            incendSpread = 10f;
                            incendAmount = 10;

                            healPercent = 1f;
                            collidesTeam = true;

                            colors = new Color[]{Pal.heal.cpy().a(.2f), Pal.heal.cpy().a(.5f), Pal.heal.cpy().mul(1.2f), Color.white};
                        }};
                    }});
                }
            }

            weapons.add(new Weapon("shuihua-fuzhupao"){{
                rotate = true;

                x = 70f/4f;
                y = -26f/4f;

                reload = 45f;
                shake = 3f;
                rotateSpeed = 2f;
                shadow = 30f;
                shootY = 7f;
                recoil = 4f;
                cooldownTime = reload - 10f;
                //TODO better sound
                shootSound = Sounds.laser;

                bullet = new EmpBulletType(){{
                    float rad = 100f;

                    scaleLife = true;
                    lightOpacity = 0.7f;
                    unitDamageScl = 0.8f;
                    healPercent = 25f;
                    timeIncrease = 3f;
                    timeDuration = 60f * 20f;
                    powerDamageScl = 3f;
                    damage = 60 * 4;
                    hitColor = lightColor = Pal.heal;
                    lightRadius = 70f;
                    clipSize = 250f;
                    shootEffect = Fx.hitEmpSpark;
                    smokeEffect = Fx.shootBigSmoke2;
                    lifetime = 320 / 7f;
                    sprite = "circle-bullet";
                    backColor = Pal.heal;
                    frontColor = Color.white;
                    width = height = 12f * 1.25f;
                    shrinkY = 0f;
                    speed = 3.5f * 2;
                    trailLength = (int)(20 * 1.25f);
                    trailWidth = 6f * 1.25f;
                    trailColor = Pal.heal;
                    trailInterval = 3f;
                    splashDamage = 70f * 4;
                    splashDamageRadius = rad;
                    hitShake = 4f;
                    trailRotation = true;
                    status = StatusEffects.electrified;
                    hitSound = Sounds.plasmaboom;

                    trailEffect = new Effect(16f, e -> {
                        color(Pal.heal);
                        for(int s : Mathf.signs){
                            Drawf.tri(e.x, e.y, 4f, 30f * e.fslope(), e.rotation + 90f*s);
                        }
                    });

                    hitEffect = new Effect(50f, 100f, e -> {
                        e.scaled(7f, b -> {
                            color(Pal.heal, b.fout());
                            Fill.circle(e.x, e.y, rad);
                        });

                        color(Pal.heal);
                        stroke(e.fout() * 3f);
                        Lines.circle(e.x, e.y, rad);

                        int points = 10;
                        float offset = Mathf.randomSeed(e.id, 360f);
                        for(int i = 0; i < points; i++){
                            float angle = i* 360f / points + offset;
                            //for(int s : Mathf.zeroOne){
                            Drawf.tri(e.x + Angles.trnsx(angle, rad), e.y + Angles.trnsy(angle, rad), 6f, 50f * e.fout(), angle/* + s*180f*/);
                            //}
                        }

                        Fill.circle(e.x, e.y, 12f * e.fout());
                        color();
                        Fill.circle(e.x, e.y, 6f * e.fout());
                        Drawf.light(e.x, e.y, rad * 1.6f, Pal.heal, e.fout());
                    });
                }};
            }});
            Weapon w = new RepairBeamWeapon("shuihua-xiufu"){{
                x = 10f;
                y = -10f;
                shootY = 6f;
                beamWidth = 0.8f;
                repairSpeed = 5f;
                bullet = new BulletType(){{
                    maxRange = 130f;
                }};
            }} ;
            Weapon w2 = w.copy() ;
            w2.y *= -1 ;
            weapons.add(w, w2, new Weapon("shuihua-zhupao"){{
                reload = 240f;
                x = 0f;
                y = 0f;
                shoot = new ShootMulti(new ShootSpread(4, 15){{shotDelay = 5f;}}, new ShootPattern(){{shots = 2;shotDelay = 1f;}}) ;

                shadow = 5f;
                mirror = false ;
                rotateSpeed = 4f;
                rotate = true;
                inaccuracy = 1f;
                velocityRnd = 0.1f;
                shootSound = Sounds.missile;

                ejectEffect = Fx.none;
                bullet = new FlakBulletType(2.5f * 1.5f, 25 * 3 * 5){{
                    sprite = "missile-large";
                    //for targeting
                    collidesGround = collidesAir = true;
                    explodeRange = 40f;
                    width = height = 12f * 1.25f ;
                    shrinkY = 0f;
                    drag = -0.003f;
                    homingRange = 60f;
                    homingPower = 0.15f ;
                    homingDelay = 1.25f ;
                    keepVelocity = false;
                    lightRadius = 60f;
                    lightOpacity = 0.7f;
                    lightColor = Pal.heal;

                    splashDamageRadius = 30f * 2 ;
                    splashDamage = 25f * 2 * 5 ;

                    lifetime = 320 / speed;
                    backColor = Pal.heal;
                    frontColor = Color.white;

                    hitEffect = new ExplosionEffect(){{
                        lifetime = 28f;
                        waveStroke = 6f;
                        waveLife = 10f;
                        waveRadBase = 7f;
                        waveColor = Pal.heal;
                        waveRad = 30f;
                        smokes = 6;
                        smokeColor = Color.white;
                        sparkColor = Pal.heal;
                        sparks = 6;
                        sparkRad = 35f;
                        sparkStroke = 1.5f;
                        sparkLen = 4f;
                    }};

                    weaveScale = 8f / 4;
                    weaveMag = 1f * 4;

                    trailColor = Pal.heal;
                    trailWidth = 4.5f * 1.25f;
                    trailLength = (int)(29 * 1.25f);

                    fragBullets = 8;
                    fragVelocityMin = 0.3f;

                    fragBullet = new MissileBulletType(3.9f * 1.25f, 50 * 5){{
                        homingPower = 0.2f;
                        weaveMag = 4 ;
                        weaveScale = 4;
                        lifetime = 60f * 1.25f;
                        keepVelocity = false;
                        shootEffect = Fx.shootHeal;
                        smokeEffect = Fx.hitLaser;
                        splashDamage = 30f * 5;
                        splashDamageRadius = 20f;
                        frontColor = Color.white;
                        hitSound = Sounds.none;

                        lightColor = Pal.heal;
                        lightRadius = 40f;
                        lightOpacity = 0.7f;

                        trailColor = Pal.heal;
                        trailWidth = 2.5f * 1.25f;
                        trailLength = (int)(20 * 1.25f);
                        trailChance = -1f;

                        healPercent = 6f;
                        collidesTeam = true;
                        backColor = Pal.heal;

                        despawnEffect = Fx.none;
                        hitEffect = new ExplosionEffect(){{
                            lifetime = 20f;
                            waveStroke = 2f;
                            waveColor = Pal.heal;
                            waveRad = 12f;
                            smokeSize = 0f;
                            smokeSizeBase = 0f;
                            sparkColor = Pal.heal;
                            sparks = 9;
                            sparkRad = 35f;
                            sparkLen = 4f;
                            sparkStroke = 1.5f;
                        }};
                    }};
                }};
            }});
        }};
        yeguang = new UnitType("yeguang"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
            health = 11300 ;
            armor = 35 ;
            speed = 5f / 60f * tilesize;
            hitSize = 4 * tilesize ;
            flying = true ;
            //miaoxianshang = 500 ;
            //dancixianshang = 500 ;
            Weapon w = new Weapon("yeguang-fupao"){{
                x = 10 ;
                y = -10 ;
                rotate = true ;
                reload = 15 ;
                shootSound = SYBSBlocks.youling.shootSound ;
                shake = 2 ;
                bullet = new FlakBulletType(){{
                    lifetime = 120 ;
                    speed = 45 * tilesize / lifetime ;
                    damage = 60 ;
                    splashDamageRadius = -1 ;
                    collidesGround = true ;
                    //pierceCap = 3 ;
                    //pierce = true ;
                    fragBullets = 2 ;
                    hitSound = Sounds.bang ;
                    //hitEffect = titanExplosion ;
                    hitColor = Pal.accent ;
                    shootEffect = shootBig2 ;
                    despawnHit = true ;
                    shake = despawnShake = 2 ;
                    trailWidth = width = height = tilesize ;
                    pierceArmor = true ;
                    incendAmount = 100 ;
                    incendChance = 1 ;
                    incendSpread = 2.5f * tilesize ;
                    status = StatusEffects.burning ;
                    statusDuration = toSeconds ;
                    fragBullet = new FlakBulletType() {{
                        lifetime = 120 ;
                        speed = 23 * tilesize / lifetime ;
                        collidesGround = true ;
                        splashDamage = 20 ;
                        splashDamageRadius = 2 * tilesize ;
                        pierceArmor = true ;
                        hitSound = Sounds.bang ;
                        hitColor = Pal.accent ;
                        despawnHit = true ;
                        //hitEffect = titanExplosion ;
                        shootEffect = shootBig2 ;
                        shake = despawnShake = 2 ;
                        trailWidth = width = height = 0.5f * tilesize ;
                        incendAmount = 20 ;
                        incendChance = 1 ;
                        incendSpread = 2.5f * tilesize ;
                        status = StatusEffects.burning ;
                        statusDuration = toSeconds ;
                    }};
                }};
            }};
            Weapon w2 = w.copy() ;
            w2.y = -w2.y ;
            weapons.add(w, w2, new Weapon("yeguang-zhupao"){{
                x = 12 ;
                y = 0 ;
                mirror = true ;
                rotate = true ;
                reload = toSeconds * 4 ;
                shake = 3 ;
                shootSound = Sounds.laser ;
                shoot.shots = 4 ;
                shoot.shotDelay = 15 ;
                bullet = new LaserBulletType(){{
                    damage = 640f;
                    sideAngle = 20f;
                    sideWidth = 3f;
                    sideLength = 160f;
                    width = 50f;
                    length = 320f;
                    shootEffect = Fx.shockwave;
                    colors = new Color[]{Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.white};
                    incendAmount = 20 ;
                    incendChance = 1 ;
                    incendSpread = 2.5f * tilesize ;
                    status = StatusEffects.burning ;
                    statusDuration = toSeconds ;
                }};
            }}) ;
            weapons.add(new Weapon("yeguang-chongjibo"){{
                x = y = 0 ;
                shootCone = 360f;
                mirror = false;
                //alwaysShooting = true ;
                reload = toSeconds / 0.15f ;
                shoot = new ShootSpread(720, 0.5f);
                //shootOnDeath = true;
                bullet = new BasicBulletType(4f, 32f){{
                    lifetime = 40 * tilesize / speed ;
                    pierce = true ;
                    pierceBuilding = true ;
                    pierceArmor = true ;
                    //pierceCap = 114 ;
                    backColor = Pal.accent ;
                    incendAmount = 20 ;
                    incendChance = 1 ;
                    incendSpread = 2.5f * tilesize ;
                    status = StatusEffects.burning ;
                    statusDuration = toSeconds ;
                    shake = 3 ;
                }};
            }});
            Weapon w3 = new Weapon("yeguang-fuzhupao"){{
                x = 5f ;
                y = -5 ;
                rotate = true ;
                reload = 15 ;
                shootSound = Sounds.missile ;
                shake = 2 ;
                bullet = new MissileBulletType(){{
                    lifetime = 120 ;
                    speed = 50 * tilesize / lifetime ;
                    damage = 0 ;
                    splashDamage = 100 ;
                    splashDamageRadius = 4 * tilesize ;
                    collidesGround = true ;
                    //pierceCap = 3 ;
                    //pierce = true ;
                    //fragBullets = 2 ;
                    //hitSound = Sounds.bang ;
                    hitEffect = Fx.explosion ;
                    hitColor = Pal.accent ;
                    shootEffect = shootBig2 ;
                    despawnHit = true ;
                    shake = despawnShake = 2 ;
                    trailWidth = width = height = tilesize ;
                    //pierceArmor = true ;
                    incendAmount = 20 ;
                    incendChance = 1 ;
                    incendSpread = 2.5f * tilesize ;
                    homingDelay = 15 ;
                    homingRange = 25 * tilesize ;
                    status = StatusEffects.burning ;
                    statusDuration = toSeconds ;
                }};
            }};
            Weapon w4 = w3.copy() ;
            w3.y = -w3.y ;
            weapons.add(w3, w4) ;
            immunities.addAll(tianping.immunities) ;
            abilities.add(new GuangYaoAbility(), new ChuanSongAbility()) ;
        }};
        jiyuan = new XianShangUnitType("jiyuan"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
           hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
           health = 1.1f * 1000 * 1000 ;
           armor = 361 ;
           naval = true ;
           miaoxianshang = xiuji.miaoxianshang ;
           dancixianshang = xiuji.dancixianshang ;
           fenxianshang = xiuji.fenxianshang ;
           speed = 3.3f * tilesize / toSeconds ;
           hitSize = xiuji.hitSize ;
           trailLength = 70;
           waveTrailX = 23f;
           waveTrailY = -32f;
           trailScl = 3.5f;
           faceTarget = false ;
           weapons.add(new Weapon("jiyuan-1"){{
               x = 0 ;
               y = -10 ;
               mirror = false ;
               rotate = true ;
               reload = 600 ;
               shoot = new ShootAlternate(40f);
               shoot.shots = 2 ;
               shootSound = 1 + 1 == 2 ? Sounds.shockBlast : Sounds.corexplode ;
               shake = 5 ;
               bullet = new BaiFenBiPointBulletType() {{
                   damage = 15000 ;
                   pierceArmor = true ;
                   baifenbi = 0 ;
                   fragBullets = 4 ;
                   speed = 160 * tilesize ;
                   trailEffect = SYBSFx.guidaopaolujing ;
                   trailColor = Pal.accent ;
                   trailInterval = 1 ;
                   hitColor = Pal.accent ;
                   hitEffect = SYBSFx.guidaopaomingzhong ;
                   hitShake = 5 ;
                   lifetime = 1 ;
                   fragBullet = new ArtilleryBulletType() {{
                       damage = 0 ;
                       splashDamage = 600 ;
                       splashDamageRadius = 10 * tilesize ;
                       width = height = 2 * tilesize ;
                       trailWidth = width / 2 ;
                       speed = 2 ;
                       //lifetime = 120 ;
                       hitEffect = titanExplosion ;
                       hitColor = Pal.accent ;
                       trailColor = Pal.accent ;
                       trailLength = 114514 ;
                       hitShake = 3 ;
                   }};
               }} ;
           }}) ;
           weapons.add(new Weapon("jiyuan-2"){{
               x = 20 ;
               y = 0 ;
               //mirror = false ;
               //rotate = true ;
               shootCone = 361 ;
               reload = toSeconds / 0.04f ;
               shoot = new ShootSpread(50, 0f);
               shoot.shotDelay = 2 ;
               inaccuracy = 20 ;
               shootSound = Sounds.missileLarge ;
               shake = 3 ;
               bullet = new BaiFenBiBulletType() {{
                   splashDamage = 510 ;
                   splashDamageRadius = 4 * tilesize ;
                   damage = 0 ;
                   baifenbi = 0 ;
                   pierceArmor = true ;
                   fanweibaifenbi = 0 ;
                   homingRange = 50 * tilesize ;
                   homingPower = 0.2f ;
                   homingDelay = 20 ;
                   lifetime = 5 * toSeconds;
                   speed = 100 * tilesize / lifetime;
                   width = 13f;
                   height = 19f;
                   hitSize = 7f;
                   shootEffect = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
                   smokeEffect = Fx.shootBigSmoke;
                   hitColor = backColor = trailColor = Pal.tungstenShot;
                   frontColor = Color.white;
                   trailWidth = 2.2f;
                   trailLength = 11;
                   hitEffect = despawnEffect = Fx.hitBulletColor;
                   hitShake = 10 ;
               }} ;
           }}) ;
            weapons.add(new Weapon("jiyuan-3"){{
                x = 0 ;
                y = 10 ;
                mirror = false ;
                //rotate = true ;
                shootCone = 361 ;
                reload = toSeconds / 0.05f ;
                shootSound = Sounds.largeExplosion ;
                shake = 5 ;
                bullet = new BulletType(){{
                    spawnUnit = new MissileUnitType("jiyuan-hedan"){{
                       health = 500 ;
                       lifetime = 10 * toSeconds ;
                       speed = 120 * tilesize / lifetime ;
                       hitSize = 2 * tilesize ;
                       for (int i : new int[]{270, 30, 150}) {
                           abilities.add(new ShieldArcAbility(){{
                               angle = 80 ;
                               radius = hitSize ;
                               whenShooting = false ;
                               Vec2 v = ShaYeBuShi.circle(i, radius, x, y) ;
                               x = v.x ;
                               y = v.y ;
                               angleOffset = i - 60 ;
                           }}) ;
                       }
                       abilities.add(new ForceFieldAbility(3, 1, 2, 0, 114514, 0)) ;
                       weapons.add(new Weapon(){{
                           shootOnDeath = true ;
                           shootCone = 361 ;
                           autoTarget = true ;
                           bullet = new BaiFenBiBulletType() {
                               @Override
                               public void createSplashDamage(Bullet b, float x, float y){
                                   super.createSplashDamage(b, x, y);
                                   for (StatusEffect e : content.statusEffects()) {
                                       if (ShaYeBuShi.isJianYi(e)) {
                                           Damage.status(b.team, x, y, splashDamageRadius, e, statusDuration, collidesAir, collidesGround);
                                       }
                                   }
                               }
                               {
                               splashDamage = 96000 ;
                               splashDamageRadius = 80 * tilesize ;
                               pierceArmor = true ;
                               fanweibaifenbi = 0 ;
                               killShooter = true ;
                               status = shuaibian ;
                               statusDuration = 10 * toSeconds ;
                               hitShake = 20 ;
                               hitEffect = SYBSFx.hedanbaozha ;
                               hitColor = Pal.accent ;
                               speed = 0 ;
                               lifetime = 5 ;
                               damage = 0 ;
                               fragBullets = 15 ;
                               fragBullet = SYBSBullets.hedandanpian ;
                               incendAmount = 400 ;
                               incendChance = 1 ;
                               incendSpread = 40 * tilesize ;
                           }} ;
                       }});
                    }};
                }} ;
            }}) ;
            Weapon w1 = new TeShuWeapon("jiyuan-4"){{
                fanqian = true ;
                targetAir = false ;
                targetNaval = true ;
                targetGround = false ;
                reload = 60f / 0.3f ;
                cooldownTime = 90f;
                mirror = true;
                x = 20f;
                y = 20f;
                shake = 1f;
                rotate = true ;
                bullet = new BulletType(){{
                    shootEffect = Fx.sparkShoot;
                    smokeEffect = Fx.shootSmokeTitan;
                    hitColor = Pal.suppress;
                    shake = 1f;
                    speed = 0f;
                    keepVelocity = false;
                    collidesAir = false ;
                    spawnUnit = new YuLeiUnitType("jiyuan-yulei"){{
                        health = 400 ;
                        hittable = false ;
                        lifetime = 300 ;
                        speed = 2.5f ;
                        naval = true ;
                        targetAir = false;
                        maxRange = 5f;
                        outlineColor = Pal.darkOutline;
                        lowAltitude = true;
                        deathExplosionEffect = Fx.none;
                        loopSoundVolume = 0.1f;
                        weapons.add(new TeShuWeapon(){{
                            fanqian = true ;
                            controllable = false ;
                            autoTarget = true ;
                            targetGround = targetAir = false ;
                            targetNaval = true ;
                            shootCone = 360f;
                            reload = 1 ;
                            shootOnDeath = true ;
                            //alwaysShooting = true ;
                            bullet = new TeShuExplosionBulletType(1200,80){{
                                hitColor = Pal.accent ;
                                shootEffect = hitEffect = titanExplosion;
                                hitSound = Sounds.artillery;
                                //width = 24 ;
                                //height = 24 ;
                                fragBullets = 5;
                                fragSpread = 5f ;
                                //naval = 2 ;
                                //killShooter = false ;
                                fragBullet = new ShuiLeiBulletType(0, 0) {{
                                    backColor = Pal.lightishGray ;
                                    splashDamage = 620;
                                    splashDamageRadius = 40;
                                    lifetime = Integer.MAX_VALUE;
                                    shootEffect = hitEffect = titanExplosion;
                                    hitSound = Sounds.artillery;
                                }};
                            }} ;
                        }}) ;
                    }} ;
                }};

            }}, w2 = w1.copy() ;
            w2.y = -w1.y ;
            Weapon w3 = new Weapon("jiyuan-5"){{
                x = 15 ;
                y = 30 ;
                mirror = false ;
                rotate = true ;
                //shootCone = 361 ;
                reload = toSeconds / 2.5f ;
                //shoot = new ShootSpread(50, 40f);
                //shoot.shots = 2 ;
                inaccuracy = 4 ;
                shake = 2 ;
                shootSound = Sounds.shootBig ;
                bullet = new BaiFenBiBulletType() {{
                    damage = 140 ;
                    width = tilesize ;
                    height = 2 * width ;
                    backColor = trailColor = Pal.accent ;
                    trailWidth = width / 2 ;
                    trailLength = (int)(height * 5) ;
                    pierceArmor = true ;
                    baifenbi = 0 ;
                    lifetime = 3 * toSeconds;
                    speed = 80 * tilesize / lifetime;
                    pierce = true ;
                    despawnShake = hitShake = 3 ;
                    despawnHit = true ;
                    hitEffect = despawnEffect = SYBSFx.fanhuanxiao ;
                    hitColor = Pal.accent ;
                    intervalBullets = 2 ;
                    bulletInterval = 60f ;
                    intervalBullet = new BasicBulletType(){{
                        width = tilesize ;
                        height = 2 * width ;
                        backColor = trailColor = Pal.redderDust ;
                        trailWidth = width / 2 ;
                        trailLength = (int)(height * 5) ;
                        damage = 65 ;
                        pierceArmor = true ;
                        pierce = true ;
                        pierceCap = 4 ;
                        homingDelay = 10 ;
                        homingPower = 0.17f ;
                        homingRange = 20 * tilesize ;
                        lifetime = 2 * toSeconds;
                        speed = 40 * tilesize / lifetime;
                        hitEffect = despawnEffect = SYBSFx.fanhuanxiao ;
                        hitColor = Pal.accent ;
                        despawnShake = hitShake = 2 ;
                        despawnHit = true ;
                    }} ;
                }} ;
            }}, w4 = w3.copy();
            w4.y = -w3.y ;
            weapons.add(w1, w2, w3, w4) ;
            weapons.add(new Weapon("jiyuan-6"){
                @Override
                public void update(Unit u, WeaponMount m) {
                    if (u.shield <= 0) {
                        super.update(u, m);
                    }
                }
                {
                x = 30 ;
                y = 0 ;
                //mirror = false ;
                rotate = true ;
                //shootCone = 361 ;
                reload = toSeconds / 3.8f ;
                //shoot = new ShootSpread(50, 40f);
                //shoot.shots = 2 ;
                inaccuracy = 5 ;
                shootSound = Sounds.shootSmite ;
                shake = 5 ;
                bullet = new BasicBulletType() {{
                    damage = 6000 ;
                    //splashDamageRadius = 4 * tilesize ;
                    pierceArmor = true ;
                    //fanweibaifenbi = 0 ;
                    //homingRange = 50 * tilesize ;
                    //homingPower = 0.2f ;
                    //homingDelay = 20 ;
                    lifetime = 8 * toSeconds;
                    speed = 90 * tilesize / lifetime;
                    parts.add(new HaloPart(){{
                        color = Pal.accent;
                        layer = Layer.effect;
                        y = 0;
                        haloRotateSpeed = -1.5f;

                        shapes = 2;
                        shapeRotation = 180f;
                        triLength = 25f;
                        triLengthTo = 25f;
                        haloRotation = 45f;
                        haloRadius = 0f;
                        tri = true;
                        radius = 10f;
                    }}, new HaloPart(){{
                        color = Pal.accent;
                        layer = Layer.effect;
                        y = 0;
                        haloRotateSpeed = 2f;

                        shapes = 2;
                        shapeRotation = 180f;
                        triLength = 35f;
                        triLengthTo = 35f;
                        haloRotation = 90f;
                        haloRadius = 0f;
                        tri = true;
                        radius = 10f;
                    }}) ;
                    hitShake = 5 ;
                    hitSound = Sounds.plasmaboom ;
                    hitEffect = SYBSFx.baozha1 ;
                    hitColor = Pal.accent ;
                    fragBullets = 10 ;
                    fragBullet = new BasicBulletType(){{
                        splashDamage = 5300 ;
                        splashDamageRadius = 5 * tilesize ;
                        hitShake = 3 ;
                        parts.add(new HaloPart(){{
                            color = Pal.redderDust;
                            layer = Layer.effect;
                            y = 0;
                            haloRotateSpeed = -1.5f;

                            shapes = 2;
                            shapeRotation = 180f;
                            triLength = 20f;
                            triLengthTo = 20f;
                            haloRotation = 45f;
                            haloRadius = 0f;
                            tri = true;
                            radius = 10f;
                        }}, new HaloPart(){{
                            color = Pal.redderDust;
                            layer = Layer.effect;
                            y = 0;
                            haloRotateSpeed = 2f;

                            shapes = 2;
                            shapeRotation = 180f;
                            triLength = 25f;
                            triLengthTo = 25f;
                            haloRotation = 90f;
                            haloRadius = 0f;
                            tri = true;
                            radius = 10f;
                        }}) ;
                        //pierceArmor = true ;
                        //fanweibaifenbi = 0 ;
                        //homingRange = 50 * tilesize ;
                        //homingPower = 0.2f ;
                        //homingDelay = 20 ;
                        lifetime = 4 * toSeconds;
                        speed = 90 * tilesize / lifetime;
                        hitSound = Sounds.plasmaboom ;
                        hitEffect = SYBSFx.baozha1 ;
                        hitColor = Pal.redderDust ;
                    }} ;
                }} ;
            }}) ;
            abilities.add(new ForceFieldAbility(hitSize * 2, 30000 / toSeconds, 300000, 30 * toSeconds, 6, 90)) ;
        }};
        shendijidanwei.add(jiyuan) ;
        wuji = new UnitType("wuji"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
                speed = 0.35f;
                hitSize = 82f;
                rotateSpeed = 1.65f;
                health = 320000;
                armor = 72f;
                mechStepParticles = true;
                stepShake = 1.5f;
                drownTimeMultiplier = 8f;
                mechFrontSway = 1.9f;
                mechSideSway = 0.6f;
                immunise(this,true,3);
                weapons.add(
                        new Weapon("wuji-1"){{
                            top = false;
                            mirror = false;
                            y = -6f + 1.25f * tilesize;
                            x = 0f;
                            shootY = textureToReal(new Vec2(100, 162), 200, 450).y;
                            reload = 180f;
                            recoil = 5f;
                            shake = 2f;
                            range = 300f ;
                            ejectEffect = Fx.casing4;
                            shootSound = Sounds.shootSmite;
                            var haloProgress = DrawPart.PartProgress.warmup.delay(0.5f);
                            float haloY = -15f, haloRotSpeed = 1f;
                            parts.addAll(
                                    new ShapePart(){{
                                        progress = PartProgress.warmup.delay(0.2f);
                                        color = Pal.accent;
                                        circle = true;
                                        hollow = true;
                                        stroke = 0f;
                                        strokeTo = 2f;
                                        radius = 10f;
                                        layer = Layer.effect;
                                        y = haloY;
                                        rotateSpeed = haloRotSpeed;
                                    }},
                                    new ShapePart(){{
                                        progress = PartProgress.warmup.delay(0.2f);
                                        color = Pal.accent;
                                        circle = true;
                                        hollow = true;
                                        stroke = 0f;
                                        strokeTo = 1.6f;
                                        radius = 4f;
                                        layer = Layer.effect;
                                        y = haloY;
                                        rotateSpeed = haloRotSpeed;
                                    }},
                                    new HaloPart(){{
                                        progress = haloProgress;
                                        color = Pal.accent;
                                        layer = Layer.effect;
                                        y = haloY;

                                        haloRotation = 90f;
                                        shapes = 2;
                                        triLength = 0f;
                                        triLengthTo = 20f;
                                        haloRadius = 16f;
                                        tri = true;
                                        radius = 4f;
                                    }},
                                    new HaloPart(){{
                                        progress = haloProgress;
                                        color = Pal.accent;
                                        layer = Layer.effect;
                                        y = haloY;

                                        haloRotation = 90f;
                                        shapes = 2;
                                        triLength = 0f;
                                        triLengthTo = 5f;
                                        haloRadius = 16f;
                                        tri = true;
                                        radius = 4f;
                                        shapeRotation = 180f;
                                    }},
                                    new HaloPart(){{
                                        progress = haloProgress;
                                        color = Pal.accent;
                                        layer = Layer.effect;
                                        y = haloY;
                                        haloRotateSpeed = -haloRotSpeed;

                                        shapes = 4;
                                        triLength = 0f;
                                        triLengthTo = 5f;
                                        haloRotation = 45f;
                                        haloRadius = 16f;
                                        tri = true;
                                        radius = 8f;
                                    }},
                                    new HaloPart(){{
                                        progress = haloProgress;
                                        color = Pal.accent;
                                        layer = Layer.effect;
                                        y = haloY;
                                        haloRotateSpeed = -haloRotSpeed;

                                        shapes = 4;
                                        shapeRotation = 180f;
                                        triLength = 0f;
                                        triLengthTo = 2f;
                                        haloRotation = 45f;
                                        haloRadius = 16f;
                                        tri = true;
                                        radius = 8f;
                                    }},
                                    new HaloPart(){{
                                        progress = haloProgress;
                                        color = Pal.accent;
                                        layer = Layer.effect;
                                        y = haloY;
                                        haloRotateSpeed = haloRotSpeed;

                                        shapes = 4;
                                        triLength = 0f;
                                        triLengthTo = 3f;
                                        haloRotation = 45f;
                                        haloRadius = 10f;
                                        tri = true;
                                        radius = 6f;
                                    }}
                            );
                            shake = 5f ;
                            bullet = new BasicBulletType(){{
                                shoot = new ShootHelix(){{
                                    mag = 1f;
                                    scl = 5f;
                                    shots = 5 ;
                                    shotDelay = 12f ;
                                }};

                                shootEffect = new MultiEffect(Fx.shootTitan, new WaveEffect(){{
                                    colorTo = Pal.accent;
                                    sizeTo = 26f;
                                    lifetime = 14f;
                                    strokeFrom = 4f;
                                }});
                                smokeEffect = Fx.shootSmokeTitan;
                                hitColor = Pal.accent;
                                despawnSound = Sounds.spark;

                                sprite = "large-orb";
                                trailEffect = Fx.missileTrail;
                                trailInterval = 3f;
                                trailParam = 4f;
                                speed = 6f;
                                damage = 460f;
                                lifetime = 90f;
                                width = height = 30f;
                                backColor = Pal.accentBack;
                                frontColor = Pal.accent;
                                shrinkX = shrinkY = 0f;
                                trailColor = Pal.accentBack;
                                trailLength = 12;
                                trailWidth = 2.2f;
                                despawnEffect = hitEffect = new ExplosionEffect(){{
                                    waveColor = Pal.accent;
                                    smokeColor = Color.gray;
                                    sparkColor = Pal.accentBack;
                                    waveStroke = 4f;
                                    waveRad = 40f;
                                }};

                                intervalBullet = new LightningBulletType(){{
                                    damage = 320;
                                    collidesAir = true;
                                    ammoMultiplier = 1f;
                                    lightningColor = Pal.accent;
                                    lightningLength = 5;
                                    lightningLengthRand = 6;

                                    //for visual stats only.
                                    buildingDamageMultiplier = 0.5f;
                                    buildingDamageMultiplier = 1f;

                                    lightningType = new BulletType(0.0001f, 160f){{
                                        lifetime = Fx.lightning.lifetime;
                                        hitEffect = Fx.hitLancer;
                                        despawnEffect = Fx.none;
                                        status = StatusEffects.shocked;
                                        statusDuration = 10f;
                                        hittable = false;
                                        lightColor = Color.white;
                                        buildingDamageMultiplier = 0.5f;
                                    }};
                                }};

                                bulletInterval = 4f;

                                lightningColor = Pal.accent;
                                lightningDamage = 350;
                                lightning = 8;
                                lightningLength = 2;
                                lightningLengthRand = 8;
                            }};
                        }}
                );
                weapons.add(new Weapon("wuji-2"){{
                    top = false;
                    mirror = false ;
                    x = 0 ;
                    shootSound = SYBSSounds.impact;
                    //loopSound = Sounds.beam;
                    //loopSoundVolume = 2f;
                    shootY = 2f;
                    reload = 300f;
                    recoil = 1f;
                    ejectEffect = Fx.none;
                    range = 600f ;
                    bullet = new PointBulletType() {
                        @Override
                        public void createFrags(Bullet b, float x, float y) {
                            if(fragBullet != null && (fragOnAbsorb || !b.absorbed)){
                                for(int i = 0; i < fragBullets; i++){
                                    fragBullet.create(b, b.x, b.y, fragAngle * i) ;
                                }
                            }
                        }
                        {
                        lifetime = 1 ;
                        trailEffect = Fx.none ;
                        fragBullets = 1 ;
                        fragSpread = 0 ;
                        fragRandomSpread = 0 ;
                        fragAngle = 45 ;
                        speed = 80 * tilesize ;
                        damage = 1400 ;
                        despawnSound = hitSound = SYBSSounds.impact ;
                        despawnShake = hitShake = 10 ;
                        fragBullet = new LineBullet() {{
                            colors = new Color[]{Pal.accent, Pal.accentBack} ;
                            length = 55 * tilesize ;
                            width = 2 * tilesize ;
                            lifetime = 90 ;
                            damage = 120 ;
                            damageInterval = 1;
                            hitColor = lightColor = Color.black ;
                            lightOpacity = 1 ;
                            //continuous = false ;
                        }};
                        fragBullets = 8 ;
                        fragAngle = 360 / 8f ;
                        //fragLifeMin = fragLifeMax = 240 ;
                        //fragSpread = 8 * tilesize ;
                        fragBullet = new PointBulletType() {
                            @Override
                            public void createFrags(Bullet b, float x, float y) {
                                if(fragBullet != null && (fragOnAbsorb || !b.absorbed)){
                                    for(int i = 0; i < fragBullets; i++){
                                        fragBullet.create(b, b.x, b.y, b.rotation() + fragAngle) ;
                                    }
                                }
                            }
                            {
                            speed = 8 * tilesize ;
                            lifetime = 1 ;
                            trailEffect = Fx.none ;
                            fragBullets = 1 ;
                            fragAngle = 180 ;
                            despawnShake = hitShake = 10 ;
                            damage = 400 ;
                            fragBullet = new PayloadBullet() {{
                                speed = 0.5f ;
                                block = SYBSBlocks.ronghui ;
                                lifetime = 240 ;
                                kill = false ;
                            }} ;
                            //fragBullet = weapons.get(0).bullet ;
                        }} ;
                    }};
                }});
                weapons.add(
                        new Weapon("wuji-3"){{
                            top = false;
                            y = -5f;
                            x = 30f;
                            Vec2 v = textureToReal(new Vec2(508, 209), 600, 450) ;
                            x = v.x ;
                            y = v.y ;
                            shootY = textureToReal(new Vec2(59, 23), 120, 340).y;
                            reload = 90f;
                            recoil = 5f;
                            shake = 4f;
                            ejectEffect = Fx.casing4;
                            shootSound = Sounds.bang;
                            shoot.shots = 10 ;
                            shoot.shotDelay = 6 ;
                            bullet = new BasicBulletType(13f,  110){{
                                pierce = true;
                                pierceCap = 30;
                                width = 20f;
                                height = 50f;
                                lifetime = 30f;
                                shootEffect = Fx.shootBig;
                                fragVelocityMin = 0.4f;

                                hitEffect = Fx.blastExplosion;
                                splashDamage = 160f;
                                splashDamageRadius = 24f;

                                fragBullets = 20;
                                fragLifeMin = 0f;
                                fragRandomSpread = 30f;

                                fragBullet = new BasicBulletType(18f, 140){{
                                    width = 12f;
                                    height = 12f;
                                    pierce = true;
                                    pierceBuilding = true;
                                    pierceCap = 20;

                                    lifetime = 25f;
                                    hitEffect = Fx.flakExplosion;
                                    splashDamage = 125f;
                                    splashDamageRadius = 12f;
                                }};
                            }};
                        }}
                );
            }};
        gen = new UnitType("gen") {{
            speed = 1.54f;
            accel = 0.04f;
            drag = 0.04f;
            rotateSpeed = 5f;
            flying = true;
            lowAltitude = true;
            health = 18000;
            //engineOffset = 38;
            //engineSize = 7.3f;
            hitSize = 2.5f * tilesize;
            armor = 18f;
            engineSize = 0 ;
            Weapon w = wuji.weapons.get(1).copy() ;
            w.shootSound = Sounds.none ;
            w.bullet = new PointBulletType() {{
                lifetime = 1 ;
                trailEffect = Fx.none ;
                fragBullets = 1 ;
                fragSpread = 0 ;
                fragRandomSpread = 0 ;
                fragAngle = 45 ;
                speed = 80 * tilesize ;
                damage = 1400 ;
                despawnSound = hitSound = SYBSSounds.impact ;
                despawnShake = hitShake = 10 ;
            }};
            w.bullet.despawnSound = w.bullet.hitSound = Sounds.none ;
            w.bullet.fragBullet = new YunShiBulletType(2400, 160){{
                damage = 3200;
                shootEffect = Fx.shockwave;
                lifetime = 90 ;
                pierce = true ;
            }};
            w.shake = 4f;
            w.shootY = 9f;
            w.x = 0f;
            w.y = 0f;
            w.rotateSpeed = 2f;
            w.reload = 480f;
            w.recoil = 4f;
            w.shootSound = Sounds.buttonClick;
            w.shadow = 20f;
            w.rotate = true;
            w.name = "shayebushi-gen-1" ;
            /*
            abilities.add(new ZhenDangHuDunAbility(25 * tilesize / 4f, 500 / toSeconds, 72000, 45 * toSeconds, 4, 0, 100) {
                @Override
                public void update(Unit u) {
                    super.update(u) ;
                    rotation += 0.75f ;
                }
                {
                heal = 40;
            }}) ;
            */
            weapons.add(w);
        }} ;
        kan = new UnitType("kan") {{
            speed = 1.54f;
            accel = 0.04f;
            drag = 0.04f;
            rotateSpeed = 5f;
            flying = true;
            lowAltitude = true;
            health = 18000;
            engineSize = 0 ;
            //engineOffset = 38;
            //engineSize = 7.3f;
            hitSize = 2.5f * tilesize;
            armor = 18f;
            weapons.add(
                    new Weapon("shayebushi-kan-1"){{
                        top = false;
                        y = -5f;
                        x = 19.5f;
                        shootY = 11f;
                        reload = 120f;
                        recoil = 5f;
                        shake = 4f;
                        //ejectEffect = Fx.casing4;
                        shootSound = Sounds.explosion;
                        shoot.shots = 20 ;
                        inaccuracy = 7.5f ;
                        bullet = new LiquidBullet(){{
                            speed = 13 / 2f ;
                            width = 50f;
                            height = 50f;
                            lifetime = 30f * 2;
                            shootEffect = Fx.shootBig;
                            fragVelocityMin = 0.4f;

                            hitEffect = Fx.blastExplosion;
                            splashDamage = 160f;
                            splashDamageRadius = 40f;

                            fragBullets = 20;
                            fragLifeMin = 0f;
                            fragRandomSpread = 30f;
                        }};
                    }},
                    new Weapon("shayebushi-kan-2") {{
                        top = false;
                        y = 5f;
                        x = 0f;
                        shootY = 11f;
                        reload = 1f;
                        recoil = 5f;
                        //ejectEffect = Fx.casing4;
                        shootSound = Sounds.spray;
                        //shoot.shots = 2 ;
                        inaccuracy = 7.5f ;
                        loopSound = Sounds.spray;
                        bullet = new LiquidBulletType(Liquids.water){{
                            knockback = 3f;
                            drag = 0.05f;
                            layer = Layer.bullet - 2f;
                            speed *= 2 ;
                            damage = 40 ;
                            orbSize *= 2 ;
                            lifetime *= 2 ;
                        }};
                    }},
                    new RepairBeamWeapon("shayebushi-kan-3") {{
                        x = 2.5f;
                        y = -5f;
                        shootY = 6f;
                        beamWidth = 1.6f;
                        repairSpeed = 3f;
                        laserColor = healColor = Liquids.water.color ;
                        bullet = new BulletType(){{
                            maxRange = 260f;
                        }};
                    }}
            );
        }} ;
        zhen = new UnitType("zhen") {{
            speed = 1.54f;
            accel = 0.04f;
            drag = 0.04f;
            rotateSpeed = 5f;
            flying = true;
            lowAltitude = true;
            health = 18000;
            //engineOffset = 38;
            //engineSize = 7.3f;
            hitSize = 2.5f * tilesize;
            armor = 18f;
            engineSize = 0 ;
            weapons.add(
                    new Weapon("shayebushi-zhen-1"){{
                        top = false;
                        y = 0f;
                        x = 0f;
                        shootY = 11f;
                        reload = 6f;
                        recoil = 5f;
                        shake = 4f;
                        //ejectEffect = Fx.casing4;
                        shootSound = Sounds.spark;
                        shoot.shots = 4 ;
                        bullet = new LianXuShanDianBullet(){{
                            damage = 75 ;
                            pierceArmor = true ;
                            lightningLength = 50 ;
                            pierce = true ;
                        }};
                    }}
            );
        }} ;
        qian = new UnitType("qian") {{
            speed = 1.54f;
            accel = 0.04f;
            drag = 0.04f;
            rotateSpeed = 5f;
            flying = true;
            lowAltitude = true;
            health = 18000;
            //engineOffset = 38;
            //engineSize = 7.3f;
            hitSize = 2.5f * tilesize;
            armor = 18f;
            engineSize = 0 ;
            Weapon w = new Weapon("shayebushi-qian-1"){{
                top = false;
                mirror = false ;
                x = 0 ;
                //loopSound = Sounds.beam;
                //loopSoundVolume = 2f;
                shootY = 2f;
                reload = 300f;
                recoil = 1f;
                ejectEffect = Fx.none;
                range = 600f ;
                bullet = new PointBulletType() {{
                    lifetime = 1 ;
                    trailEffect = Fx.none ;
                    fragBullets = 1 ;
                    fragSpread = 0 ;
                    fragRandomSpread = 0 ;
                    fragAngle = 45 ;
                    speed = 120 * tilesize ;
                    damage = 1400 ;
                    despawnShake = hitShake = 10 ;
                    fragBullet = new LineBullet() {{
                        colors = new Color[]{Pal.accent, Pal.accentBack} ;
                        length = 55 * tilesize ;
                        width = 2 * tilesize ;
                        lifetime = 90 ;
                        damage = 120 ;
                        damageInterval = 1;
                        //continuous = false ;
                    }};
                }};
            }} ;
            w.shootSound = Sounds.none ;
            w.bullet.despawnSound = w.bullet.hitSound = SYBSSounds.bell ;
            w.bullet.fragBullet = new JiaSuoBullet(){{
                damage = 300 ;
                pierceArmor = true ;
                pierce = true ;
                status = StatusEffects.sapped ;
                lifetime = 90 ;
            }};
            w.shake = 4f;
            w.shootY = 9f;
            w.x = 0f;
            w.y = 0f;
            w.rotateSpeed = 2f;
            w.reload = 240f;
            w.recoil = 4f;
            w.shootSound = SYBSSounds.bell;
            w.shadow = 20f;
            w.rotate = true;
            abilities.add(new ZhenDangHuDunAbility(160 * tilesize / 4f,  1, 72000, 45 * toSeconds, 4, 0, 100) {
                @Override
                public void update(Unit u) {
                    super.update(u) ;
                    rotation += 0.75f ;
                }
                {
                    heal = 40;
                    damage = 800 ;
                }}) ;
            weapons.add(w);
        }} ;
        shenwangjidanwei.addAll(wuji, daodanxing, jiguangxing, zhen, kan, gen) ;
        huanyu = new XianShangUnitType("huanyu") {{
            hitSize = 16 * tilesize ;
            speed = 4 * tilesize / toSeconds ;
            itemCapacity = 800 ;
            flying = true ;
            faceTarget = false ;
            miaoxianshang = 20000 ;
            dancixianshang = 10000 ;
            fenxianshang = 999999 ;
            health = 1000000 ;
            engineSize = 0 ;
            armor = 280 ;
            Weapon w1 = new Weapon("shayebushi-huanyu-0") {{
                Vec2 v = textureToReal(new Vec2(221, 417), 512, 512) ;
                x = v.x ;
                y = v.y ;
                x *= 2 ;
                y *= 2 ;
                alwaysShooting = true ;
                rotate = false ;
                baseRotation = 180 ;
                reload = 1 ;
                rotateSpeed = 0 ;
                //shootWarmupSpeed = 0.03f ;
                //minShootVelocity = 1.8f ;
                continuous = true ;
                controllable = false ;
                alwaysContinuous = true ;
                bullet = new ContinuousFlameBulletType() {{
                    damage = 16000 / toMinutes ;
                    damageInterval = 1 ;
                    lifetime = 10 ;
                    oscMag = 0.1f ;
                    oscScl = 2.5f ;
                    width = 2 * tilesize ;
                    length /= 2 ;
                    lightStroke = 20 ;
                    lightOpacity = 0.25f ;
                    hitEffect = Fx.none ;
                    lengthInterp = pow10Out ;
                    colors = new Color[] {chaokongjian3, chaokongjian2, chaokongjian1, Color.white} ;
                    shootEffect = smokeEffect = Fx.none ;
                    drawFlare = false ;
                }} ;
                alternate = false ;
                shootSound = Sounds.none ;
                display = false ;
            }} ;
            weapons.add(w1) ;
            Weapon w1t = new TuiJinQiWeapon("shayebushi-huanyu-0") {{
                Vec2 v = textureToReal(new Vec2(387, 397), 512, 512) ;
                x = v.x ;
                y = v.y ;
                x *= 2 ;
                y *= 2 ;
                alwaysShooting = true ;
                rotate = false ;
                baseRotation = 225 ;
                reload = 1 ;
                rotateSpeed = 0 ;
                continuous = true ;
                mirror = false ;
                controllable = false ;
                bullet = new ContinuousFlameBulletType() {{
                    damage = 8000 / toMinutes ;
                    damageInterval = 1 ;
                    pierceArmor = true ;
                    lifetime = 10 ;
                    oscMag = 0.1f ;
                    oscScl = 1 ;
                    lightStroke = 20 ;
                    lightOpacity = 0.4f ;
                    hitEffect = Fx.none ;
                    lengthInterp = pow10Out ;
                    length = 4 * tilesize ;
                    width = 1.5f * tilesize ;
                    colors = new Color[] {chaokongjian3, chaokongjian2, chaokongjian1, Color.white} ;
                    shootEffect = smokeEffect = Fx.none ;
                    drawFlare = false ;
                }} ;
                alternate = false ;
                shootSound = Sounds.none ;
                display = false ;
            }} ;
            Weapon w2t = w1t.copy() ;
            Vec2 v5 = textureToReal(new Vec2(131, 397), 512, 512) ;
            w2t.x = v5.x ;
            w2t.y = v5.y ;
            w2t.x *= 2 ;
            w2t.y *= 2 ;
            w2t.baseRotation = 135 ;
            weapons.add(w1t, w2t) ;
            /*
            Weapon ww1 = new Weapon("shayebushi-chaokongjian-1") {{
                shootCone = 360 ;
                rotate = false ;
                baseRotation = 45 ;
                rotateSpeed = 0 ;
                bullet = new BulletType() {{
                    for (int i : Mathf.signs) {
                        spawnBullets.add(new BasicBulletType() {{
                            homingPower = 1 ;
                            weaveRandom = false ;
                            weaveMag = i ;
                            damage = 800 ;
                            pierce = true ;
                            height = width = 3 * tilesize ;
                            trailWidth = 3 ;
                            trailLength = 10 * tilesize ;
                            frontColor = backColor = trailColor = i == 1 ? chaokongjian3 : chaokongjian1 ;
                            parts.addAll(new ShapePart() {{
                                color = chaokongjian2 ;
                                circle = true ;
                                radius = 3 * tilesize ;
                            }}, new ShapePart() {{
                                color = Color.white ;
                                circle = true ;
                                radius = 3 * tilesize / 2f * 1.75f ;
                            }}, new ShapePart() {{
                                color = Pal.coalBlack ;
                                //color.a = 255 ;
                                circle = true ;
                                radius = 3 * tilesize / 2f * 1.5f ;
                            }}) ;
                            speed = 10 ;
                            lifetime = 80 ;
                            fragBullets = 1 ;
                            hitShake = 10 ;
                            fragBullet = new BasicBulletType() {{
                                lifetime = 120 ;
                                despawnHit = true ;
                                trailEffect = SYBSFx.baozha5 ;
                                trailColor = SYBSPal.chaokongjian2 ;
                                pierce = true ;
                                damage = 800 ;
                                fragBullets = 1 ;
                                speed = 0 ;
                                hitShake = 10 ;
                                hitSound = Sounds.explosionbig ;
                                fragBullet = new ExplosionBulletType(1200, 5 * tilesize) {{
                                    killShooter = false ;
                                    hitShake = 10 ;
                                    hitSound = Sounds.explosionbig ;
                                    hitEffect = SYBSFx.baozha7 ;
                                    hitColor = chaokongjian2 ;
                                    despawnHit = true ;
                                }} ;
                            }} ;
                        }});
                    }
                }} ;
                x = 15 * tilesize ;
                y = 6 * tilesize ;
                shootSound = Sounds.missileLarge ;
                reload = 60 ;
            }} ;
            Weapon ww2 = ww1.copy() ;
            ww2.y = 4 * tilesize ;
            Weapon ww3 = ww1.copy() ;
            ww3.y = 2 * tilesize ;
            Weapon ww4 = ww1.copy() ;
            ww4.y = 0 ;
            Weapon ww5 = ww1.copy() ;
            ww2.y = -2 * tilesize ;
            Weapon ww6 = ww1.copy() ;
            ww3.y = -4 * tilesize ;
            Weapon ww7 = ww1.copy() ;
            ww3.y = -6 * tilesize ;
            Weapon ww8 = ww1.copy() ;
            ww5.x = -15 * tilesize ;
            ww5.baseRotation = 135 ;
            Weapon ww9 = ww8.copy() ;
            ww6.y = 4 * tilesize ;
            Weapon ww10 = ww8.copy() ;
            ww7.y = 2 * tilesize ;
            Weapon ww11 = ww8.copy() ;
            ww6.y = 0 ;
            Weapon ww12 = ww8.copy() ;
            ww7.y = -2 * tilesize ;
            Weapon ww13 = ww8.copy() ;
            ww6.y = -4 * tilesize ;
            Weapon ww14 = ww8.copy() ;
            ww7.y = -6 * tilesize ;
            weapons.addAll(ww1, ww2, ww3, ww4, ww5, ww6, ww7, ww8, ww9, ww10, ww11, ww12, ww13, ww14) ;
            */
            Weapon ww1 = new Weapon("shayebushi-huanyu-1") {{
                rotate = false ;
                rotateSpeed = 0 ;
                baseRotation = 135 ;
                reload = 300 ;
                mirror = false ;
                shoot.shots = 16 ;
                shoot.shotDelay = 15 / 4f ;
                Vec2 v = textureToReal(new Vec2(217, 134), 512, 512) ;
                x = v.x ;
                y = v.y ;
                x *= 2 ;
                y *= 2 ;
                shootSound = Sounds.missileLarge ;
                shootCone = 360 ;
                bullet = new BasicBulletType() {
                    @Override
                    public void updateHoming(Bullet b) {
                        if (b.owner instanceof Unit u && u.isPlayer()) {
                            b.vel.setAngle(Angles.moveToward(b.rotation(), b.angleTo(player.mouseX, player.mouseY), homingPower * Time.delta * 50f));
                            return ;
                        }
                        super.updateHoming(b);
                    }
                    {
                        homingPower = 1 ;
                        homingDelay = 30 ;
                        //weaveRandom = false ;
                        //weaveMag = i ;
                        damage = 600 ;
                        splashDamage = 600 ;
                        splashDamageRadius = 5 * tilesize ;
                        pierce = true ;
                        height = width = 3 * tilesize ;
                        trailWidth = 3 ;
                        trailLength = 10 * tilesize ;
                        frontColor = backColor = trailColor = chaokongjian3 ;
                        speed = 10 ;
                        lifetime = 80 ;
                        hitShake = 10 ;
                        despawnHit = true ;
                        hitEffect = SYBSFx.baozha5 ;
                        hitColor = chaokongjian3 ;
                        hitSound = Sounds.plasmaboom ;
                    }} ;
            }} ;
            Weapon ww2 = ww1.copy() ;
            Vec2 v = textureToReal(new Vec2(177, 197), 512, 512) ;
            ww2.x = v.x ;
            ww2.y = v.y ;
            ww2.x *= 2 ;
            ww2.y *= 2 ;
            Weapon ww3 = ww1.copy() ;
            Vec2 v2 = textureToReal(new Vec2(294, 134), 512, 512) ;
            ww3.x = v2.x ;
            ww3.baseRotation = 45 ;
            ww3.x *= 2 ;
            ww3.y *= 2 ;
            Weapon ww4 = ww3.copy() ;
            Vec2 v3 = textureToReal(new Vec2(335, 197), 512, 512) ;
            ww4.x = v3.x ;
            ww4.y = v3.y ;
            ww4.x *= 2 ;
            ww4.y *= 2 ;
            weapons.add(ww1, ww2, ww3, ww4) ;
            Weapon www1 = new Weapon("shayebushi-huanyu-2") {{
                Vec2 v = textureToReal(new Vec2(217, 263), 512, 512) ;
                x = v.x ;
                y = v.y ;
                x *= 2 ;
                y *= 2 ;
                reload = 60 ;
                rotate = true ;
                shoot = new ShootAlternate(3 * tilesize) {{
                    barrels = 3 ;
                    shots = 3 ;
                }} ;
                bullet = new LightningLaserBulletType() {{
                    length = 55 * tilesize ;
                    lightningColor = chaokongjian2 ;
                    damage = 2000 ;
                    width = 5f * tilesize ;
                    colors = new Color[]{chaokongjian1.cpy().mul(1f, 1f, 1f, 0.4f), chaokongjian1, Color.white} ;
                    amount = 1 ;
                    sideAngle = 225 ;
                    sideLength = 10 * tilesize ;
                }} ;
                shootSound = Sounds.laser ;
            }} ;
            Weapon www2 = www1.copy() ;
            www2.reload = 30 ;
            Vec2 v4 = textureToReal(new Vec2(223, 221), 512, 512) ;
            www2.x = v4.x ;
            www2.y = v4.y ;
            www2.x *= 2 ;
            www2.y *= 2 ;
            weapons.add(www1, www2) ;
            /*
            weapons.add(new Weapon("shayebushi-huanyu-3") {{
                mirror = false ;
                shootSound = SYBSSounds.impact ;
                reload = 1800 ;
                rotate = true ;
                bullet = new PointBulletType() {{
                    speed = 120 * tilesize ;
                    lifetime = 1 ;
                    trailEffect = Fx.none ;
                    fragBullets = 1 ;
                    fragBullet = new BulletType() {{
                        for (int i : new int[]{45, 135}) {
                            spawnBullets.add(new JieTiBullet() {{
                                offset = 2 * tilesize;
                                sound = SYBSSounds.impact;
                                rot = i ;
                                type = new LineBullet() {{
                                    damage = 24000 / 60f;
                                    can = u -> true;
                                    damageInterval = 1;
                                    width = tilesize;
                                    length = 50 * tilesize;
                                    amount = 0;
                                    overrideRotation = false;
                                    lifetime = 6 * toSeconds;
                                    colors = new Color[]{chaokongjian2, Color.black};
                                }};
                            }}) ;
                        }
                    }} ;
                }} ;
            }}) ;
            */
            weapons.add(new Weapon("shayebushi-huanyu-4") {{
                x = y = 0 ;
                mirror = false ;
                shootSound = Sounds.plasmadrop ;
                shootCone = 360 ;
                //inaccuracy = 360 ;
                reload = 1200 ;
                rotate = true ;
                bullet = new PointBulletType() {{
                    shootEffect = SYBSFx.hujiao2 ;
                    hitColor = chaokongjian2 ;
                    damage = 0 ;
                    speed = 120 * tilesize ;
                    trailEffect = Fx.none ;
                    lifetime = 1 ;
                    fragBullets = 1 ;
                    fragBullet = new BulletType() {{
                        speed = 0 ;
                        damage = 0 ;
                        lifetime = 120 ;
                        //fragRandomSpread = 0 ;
                        //fragSpread = 0 ;
                        //fragAngle = 180 ;
                        fragBullets = 10 ;
                        fragVelocityMax = 1.1f ;
                        fragVelocityMin = 0.9f ;
                        pierce = true ;
                        fragOnHit = false ;
                        parts.add(new HaloPart() {{
                            color = chaokongjian2 ;
                            colorTo = chaokongjian1 ;
                            shapes = 4 ;
                            tri = true ;
                            triLength = 12 * tilesize ;
                            haloRadius = 19 * tilesize ;
                            radius = 4 * tilesize ;
                            haloRotateSpeed = 3 ;
                        }}) ;
                        parts.add(new HaloPart() {{
                            color = chaokongjian2 ;
                            colorTo = chaokongjian1 ;
                            shapes = 4 ;
                            tri = true ;
                            triLength = 6 * tilesize ;
                            haloRadius = 19 * tilesize ;
                            radius = 4 * tilesize ;
                            haloRotateSpeed = 3 ;
                            shapeRotation = 180 ;
                        }}) ;
                        parts.add(new HaloPart() {{
                            color = chaokongjian2 ;
                            colorTo = chaokongjian1 ;
                            shapes = 4 ;
                            tri = true ;
                            triLength = 6 * tilesize ;
                            haloRadius = 35 * tilesize ;
                            radius = 2 * triLength ;
                            haloRotateSpeed = 6 ;
                            shapeRotation = 180 ;
                        }}) ;
                        /*
                        parts.add(new HaloPart() {{
                            color = chaokongjian2 ;
                            colorTo = chaokongjian1 ;
                            shapes = 4 ;
                            tri = true ;
                            triLength = 3 * tilesize ;
                            haloRadius = 35 * tilesize ;
                            radius = 2 * triLength ;
                            haloRotateSpeed = 6 ;
                            shapeRotation = 180 ;
                        }}) ;
                        */
                        parts.add(new ShapePart() {{
                            color = chaokongjian2 ;
                            colorTo = chaokongjian1 ;
                            circle = true ;
                            hollow = true ;
                            radius = 15 * tilesize ;
                            stroke = 2 * tilesize ;
                        }}) ;
                        parts.add(new HaloPart() {{
                            color = chaokongjian2 ;
                            colorTo = chaokongjian1 ;
                            shapes = 2 ;
                            tri = true ;
                            triLength = 15f * tilesize ;
                            haloRadius = 0 ;
                            radius = 2 * tilesize ;
                            shapeRotation = 90 ;
                        }}) ;
                        parts.add(new HaloPart() {{
                            color = chaokongjian2 ;
                            colorTo = chaokongjian1 ;
                            shapes = 2 ;
                            tri = true ;
                            triLength = 15f * tilesize ;
                            haloRadius = 0 ;
                            radius = 2 * tilesize ;
                        }}) ;
                        fragBullet = new PointBulletType() {/*
                            @Override
                            public void createFrags(Bullet b, float x, float y){
                                if(fragBullet != null && (fragOnAbsorb || !b.absorbed)){
                                    for(int i = 0; i < fragBullets; i++){
                                        float len = Mathf.random(0.5f * tilesize, 2 * tilesize);
                                        float a = b.rotation() + Mathf.range(fragRandomSpread / 2) + fragAngle + ((i - fragBullets/2) * fragSpread);
                                        Vec2 v = ShaYeBuShi.random(x, y, len) ;
                                        fragBullet.create(b, x + v.x, y + v.y, a, Mathf.random(fragVelocityMin, fragVelocityMax), Mathf.random(fragLifeMin, fragLifeMax));
                                    }
                                }
                            }*/
                            {
                                speed = 120 * tilesize ;
                                trailEffect = Fx.none ;
                                lifetime = 1 ;
                                fragBullets = 1 ;
                                fragRandomSpread = 0 ;
                                fragSpread = 0 ;
                                fragAngle = 180 ;
                                fragBullet = new BulletType() {
                                    @Override
                                    public void update(Bullet b) {
                                        super.update(b) ;
                                        if (b.timer(2, 20)) {
                                            Vec2 v = ShaYeBuShi.random(b.x, b.y, 5 * tilesize) ;
                                            createFrags(b, v.x, v.y) ;
                                            hitSound.at(b.x, b.y, 1, 2) ;
                                            hitEffect.at(b.x, b.y, b.rotation(), hitColor) ;
                                        }
                                    }
                                    {
                                    killShooter = false ;
                                    splashDamage = 2000 ;
                                    splashDamageRadius = 20 * tilesize ;
                                    hitSound = Sounds.largeCannon ;
                                    fragBullets = 1 ;
                                    fragVelocityMax = 1.1f ;
                                    fragVelocityMin = 0.9f ;
                                    fragRandomSpread = 0 ;
                                    fragSpread = 0 ;
                                    fragAngle = 0 ;
                                    lifetime = 100 ;
                                    pierce = true ;
                                    fragOnHit = false ;
                                    hitEffect = new MultiEffect(new WrapEffect(SYBSFx.fixedMassiveExplosion, chaokongjian3, 0), new WrapEffect(Fx.dynamicSpikes, chaokongjian2, 48f), new WaveEffect(){{
                                        colorFrom = colorTo = chaokongjian1;
                                        sizeTo = 80f;
                                        lifetime = 12f;
                                        strokeFrom = 4f;
                                    }}) ;
                                    fragBullet = new BasicBulletType(10, 450, "missile-large-back") {{
                                        lifetime = 120 * tilesize / speed ;
                                        splashDamage = 500 ;
                                        splashDamageRadius = 16 * tilesize ;
                                        width = 3 * tilesize ;
                                        height = width * 3 ;
                                        frontColor = trailColor = chaokongjian3 ;
                                        backColor = chaokongjian1 ;
                                        trailEffect = SYBSFx.jiange3 ;
                                        trailLength = 10 * tilesize ;
                                        trailWidth = 3 ;
                                        trailChance = 1 ;
                                        pierce = true ;
                                        fragOnHit = false ;
                                        parts.add(new FlarePart(){{
                                            progress = PartProgress.life.slope().curve(Interp.pow2In);
                                            radius = 0f;
                                            radiusTo = 70f;
                                            stroke = 6f;
                                            rotation = 45f;
                                            y = -5f;
                                            followRotation = true;
                                            color1 = chaokongjian1 ;
                                            color2 = chaokongjian3 ;
                                        }});
                                        hitEffect = new MultiEffect(new WrapEffect(SYBSFx.fixedMassiveExplosion, chaokongjian3, 0), new WrapEffect(Fx.dynamicSpikes, chaokongjian2, 24f), new WaveEffect(){{
                                            colorFrom = colorTo = chaokongjian1;
                                            sizeTo = 40f;
                                            lifetime = 12f;
                                            strokeFrom = 4f;
                                        }}); ;
                                        hitShake = 15 ;
                                        despawnHit = true ;
                                        fragBullets = 5 ;
                                        hitSound = Sounds.plasmaboom ;
                                        fragLifeMin = 0.25f ;
                                        fragLifeMax = 0.75f ;
                                        knockback = 15f;
                                        fragBullet = new BasicBulletType(4, 100, "mine-bullet-back") {{
                                            splashDamage = 150 ;
                                            splashDamageRadius = 16 / 2.5f * tilesize ;
                                            hitSound = Sounds.plasmaboom ;
                                            hitShake = 12 ;
                                            knockback = 12f;
                                            frontColor = backColor = hitColor = chaokongjian3 ;
                                            hitEffect = new MultiEffect(new WrapEffect(SYBSFx.fixedMassiveExplosion, chaokongjian1, 0), Fx.scatheExplosion, Fx.scatheLight, new WaveEffect(){{
                                                lifetime = 10f;
                                                strokeFrom = 4f;
                                                sizeTo = 130f;
                                            }});
                                            fragLifeMin = 0.1f;
                                            fragBullets = 7;
                                            fragBullet = new ArtilleryBulletType(3.4f, 160){{
                                                drag = 0.02f;
                                                hitEffect = SYBSFx.fixedMassiveExplosion;
                                                despawnEffect = Fx.scatheSlash;
                                                knockback = 8f;
                                                lifetime = 23f;
                                                width = height = 18f;
                                                collidesTiles = false;
                                                splashDamageRadius = 40f;
                                                splashDamage = 250f;
                                                backColor = trailColor = hitColor = chaokongjian1;
                                                frontColor = Color.white;
                                                smokeEffect = SYBSFx.fixedShootBigSmoke2;
                                                despawnShake = 8f;
                                                lightRadius = 30f;
                                                lightColor = chaokongjian1;
                                                lightOpacity = 0.5f;

                                                trailLength = 20;
                                                trailWidth = 3.5f;
                                                trailEffect = Fx.none;
                                            }};
                                        }} ;
                                    }} ;
                                }} ;
                            }} ;
                    }} ;
                }} ;
            }}) ;
            abilities.add(new LiFangHuDunAbility(), new TuoYuanGuiDaoAbility() {{
                rotation = 90 ;
                b *= 3 ;
                rotateSpeed = 6 ;
            }}, new TuoYuanGuiDaoAbility() {{
                rotation = 150 ;
                rotateSpeed = 6 * 0.8f ;
            }}, new TuoYuanGuiDaoAbility() {{
                a = 35 * tilesize ;
                b *= 1.875f / 2 * 3 ;
                rotation = 30 ;
                rotateSpeed = 6 * 0.9f ;
            }}) ;
            /*
            parts.addAll(new RegionPart(), new ShapePart() {{
                color = chaokongjian3 ;
                radius = 2.5f * tilesize ;
                circle = true ;
                hollow = true ;
                stroke = 0.5f * tilesize ;
            }}, new ShapePart() {{
                color = chaokongjian3 ;
                radius = 2.5f * tilesize ;
                circle = true ;
                hollow = true ;
                Vec2 v = ShaYeBuShi.circle(315, 2.5f * tilesize, 0, 0) ;
                x = v.x ;
                y = v.y ;
                stroke = 0.5f * tilesize ;
            }}, new HaloPart() {{
                shapes = 1 ;
                sides = 114514 ;
                color = chaokongjian1 ;
                radius = 0.5f * tilesize ;
                haloRadius = 2.5f * tilesize ;
                rotateSpeed = 3 ;
            }}, new HaloPart() {{
                shapes = 1 ;
                sides = 114514 ;
                color = chaokongjian1 ;
                radius = 0.5f * tilesize ;
                haloRadius = 2.5f * tilesize ;
                Vec2 v = ShaYeBuShi.circle(315, 2.5f * tilesize, 0, 0) ;
                x = v.x ;
                y = v.y ;
                rotateSpeed = 3 ;
            }}) ;
            */
            rotateSpeed = 1f ;
        }} ;
        pi = new XianShangUnitType("pi") {{
            health = 80000 ;
            armor = 20 ;
            speed = 60 * tilesize / toSeconds ;
            hitSize = 3 * tilesize ;
            dancixianshang = 8000 ;
            miaoxianshang = 10000 ;
            mineSpeed = 16 ;
            itemCapacity = 3141 ;
            mineTier = Integer.MAX_VALUE ;
            buildSpeed = 10 ;
            flying = true ;
            mineRange = 30 * tilesize ;
            weapons.add(new RepairWaveWeapon("shayebusho-pi-1") {{
                rotate = true ;
                targetBuildings = true ;
                targetUnits = false ;
                bullet = bullet.copy() ;
                bullet.rangeOverride = 40 * tilesize ;
                mirror = false ;
                healColor = laserColor = Pal.accent ;
                repairSpeed = 0 ;
                healPer = 0.01f / 60f ;
            }}) ;
            weapons.add(new RepairBeamWeapon("shayebusho-pi-2") {
                @Override
                public void update(Unit unit, WeaponMount mount){
                    boolean can = unit.canShoot();
                    float lastReload = mount.reload;
                    mount.reload = Math.max(mount.reload - Time.delta * unit.reloadMultiplier, 0);
                    mount.recoil = Mathf.approachDelta(mount.recoil, 0, unit.reloadMultiplier / recoilTime);
                    if(recoils > 0){
                        if(mount.recoils == null) mount.recoils = new float[recoils];
                        for(int i = 0; i < recoils; i++){
                            mount.recoils[i] = Mathf.approachDelta(mount.recoils[i], 0, unit.reloadMultiplier / recoilTime);
                        }
                    }
                    mount.smoothReload = Mathf.lerpDelta(mount.smoothReload, mount.reload / reload, smoothReloadSpeed);
                    mount.charge = mount.charging && shoot.firstShotDelay > 0 ? Mathf.approachDelta(mount.charge, 1, 1 / shoot.firstShotDelay) : 0;

                    float warmupTarget = (can && mount.shoot) || (continuous && mount.bullet != null) || mount.charging ? 1f : 0f;
                    if(linearWarmup){
                        mount.warmup = Mathf.approachDelta(mount.warmup, warmupTarget, shootWarmupSpeed);
                    }else{
                        mount.warmup = Mathf.lerpDelta(mount.warmup, warmupTarget, shootWarmupSpeed);
                    }

                    //rotate if applicable
                    if(rotate && (mount.rotate || mount.shoot) && can){
                        float axisX = unit.x + Angles.trnsx(unit.rotation - 90,  x, y),
                                axisY = unit.y + Angles.trnsy(unit.rotation - 90,  x, y);

                        mount.targetRotation = Angles.angle(axisX, axisY, mount.aimX, mount.aimY) - unit.rotation;
                        mount.rotation = Angles.moveToward(mount.rotation, mount.targetRotation, rotateSpeed * Time.delta);
                        if(rotationLimit < 360){
                            float dst = Angles.angleDist(mount.rotation, baseRotation);
                            if(dst > rotationLimit/2f){
                                mount.rotation = Angles.moveToward(mount.rotation, baseRotation, dst - rotationLimit/2f);
                            }
                        }
                    }else if(!rotate){
                        mount.rotation = baseRotation;
                        mount.targetRotation = unit.angleTo(mount.aimX, mount.aimY);
                    }

                    float
                            weaponRotation = unit.rotation - 90 + (rotate ? mount.rotation : baseRotation),
                            mountX = unit.x + Angles.trnsx(unit.rotation - 90, x, y),
                            mountY = unit.y + Angles.trnsy(unit.rotation - 90, x, y),
                            bulletX = mountX + Angles.trnsx(weaponRotation, this.shootX, this.shootY),
                            bulletY = mountY + Angles.trnsy(weaponRotation, this.shootX, this.shootY),
                            shootAngle = bulletRotation(unit, mount, bulletX, bulletY);

                    //find a new target
                    if(!controllable && autoTarget){
                        if((mount.retarget -= Time.delta) <= 0f){
                            mount.target = findTarget(unit, mountX, mountY, bullet.range, bullet.collidesAir, bullet.collidesGround);
                            mount.retarget = mount.target == null ? targetInterval : targetSwitchInterval;
                        }
                        if(mount.target != null && checkTarget(unit, mount.target, mountX, mountY, bullet.range)){
                            mount.target = null;
                        }

                        boolean shoot = false;

                        if(mount.target != null){
                            shoot = mount.target.within(mountX, mountY, bullet.range + Math.abs(shootY) + (mount.target instanceof Sized s ? s.hitSize()/2f : 0f)) && can;

                            if(predictTarget){
                                Vec2 to = Predict.intercept(unit, mount.target, bullet.speed);
                                mount.aimX = to.x;
                                mount.aimY = to.y;
                            }else{
                                mount.aimX = mount.target.x();
                                mount.aimY = mount.target.y();
                            }
                        }

                        mount.shoot = mount.rotate = shoot;

                        //note that shooting state is not affected, as these cannot be controlled
                        //logic will return shooting as false even if these return true, which is fine
                    }

                    if(alwaysShooting) mount.shoot = true;

                    //update continuous state
                    if(continuous && mount.bullet != null){
                        if(!mount.bullet.isAdded() || mount.bullet.time >= mount.bullet.lifetime || mount.bullet.type != bullet){
                            mount.bullet = null;
                        }else{
                            mount.bullet.rotation(weaponRotation + 90);
                            mount.bullet.set(bulletX, bulletY);
                            mount.reload = reload;
                            mount.recoil = 1f;
                            unit.vel.add(Tmp.v1.trns(unit.rotation + 180f, mount.bullet.type.recoil * Time.delta));
                            if(shootSound != Sounds.none && !headless){
                                if(mount.sound == null) mount.sound = new SoundLoop(shootSound, 1f);
                                mount.sound.update(bulletX, bulletY, true);
                            }

                            if(alwaysContinuous && mount.shoot){
                                mount.bullet.time = mount.bullet.lifetime * mount.bullet.type.optimalLifeFract * mount.warmup;
                                mount.bullet.keepAlive = true;

                                unit.apply(shootStatus, shootStatusDuration);
                            }
                        }
                    }else{
                        //heat decreases when not firing
                        mount.heat = Math.max(mount.heat - Time.delta * unit.reloadMultiplier / cooldownTime, 0);

                        if(mount.sound != null){
                            mount.sound.update(bulletX, bulletY, false);
                        }
                    }

                    //flip weapon shoot side for alternating weapons
                    boolean wasFlipped = mount.side;
                    if(otherSide != -1 && alternate && mount.side == flipSprite && mount.reload <= reload / 2f && lastReload > reload / 2f){
                        unit.mounts[otherSide].side = !unit.mounts[otherSide].side;
                        mount.side = !mount.side;
                    }

                    //shoot if applicable
                    if(mount.shoot && //must be shooting
                            can && //must be able to shoot
                            (!useAmmo || unit.ammo > 0 || !state.rules.unitAmmo || unit.team.rules().infiniteAmmo) && //check ammo
                            (!alternate || wasFlipped == flipSprite) &&
                            mount.warmup >= minWarmup && //must be warmed up
                            unit.vel.len() >= minShootVelocity && //check velocity requirements
                            (mount.reload <= 0.0001f || (alwaysContinuous && mount.bullet == null)) && //reload has to be 0, or it has to be an always-continuous weapon
                            (alwaysShooting || Angles.within(rotate ? mount.rotation : unit.rotation + baseRotation, mount.targetRotation, shootCone)) //has to be within the cone
                    ){
                        shoot(unit, mount, bulletX, bulletY, shootAngle);

                        mount.reload = reload;

                        if(useAmmo){
                            unit.ammo--;
                            if(unit.ammo < 0) unit.ammo = 0;
                        }
                    }

                    float wx = unit.x + Angles.trnsx(weaponRotation, x, y),
                            wy = unit.y + Angles.trnsy(weaponRotation, x, y);

                    HealBeamMount heal = (HealBeamMount)mount;
                    boolean canShoot = mount.shoot;

                    if(!autoTarget){
                        heal.target = null;
                        if(canShoot){
                            heal.lastEnd.set(heal.aimX, heal.aimY);

                            if(!rotate && !Angles.within(Angles.angle(wx, wy, heal.aimX, heal.aimY), unit.rotation, shootCone)){
                                canShoot = false;
                            }
                        }

                        //limit range
                        heal.lastEnd.sub(wx, wy).limit(range()).add(wx, wy);

                        if(targetBuildings){
                            //snap to closest building
                            World.raycastEachWorld(wx, wy, heal.lastEnd.x, heal.lastEnd.y, (x, y) -> {
                                var build = Vars.world.build(x, y);
                                if(build != null && build.team == unit.team && build.damaged()){
                                    heal.target = build;
                                    heal.lastEnd.set(x * tilesize, y * tilesize);
                                    return true;
                                }
                                return false;
                            });
                        }
                        if(targetUnits){
                            //TODO does not support healing units manually yet
                        }
                    }

                    heal.strength = Mathf.lerpDelta(heal.strength, Mathf.num(autoTarget ? mount.target != null : canShoot), 0.2f);

                    //create heal effect periodically
                    if(canShoot && mount.target instanceof Building b && b.damaged() && (heal.effectTimer += Time.delta) >= reload){
                        healEffect.at(b.x, b.y, 0f, healColor, b.block);
                        heal.effectTimer = 0f;
                    }

                    if(canShoot && mount.target instanceof Healthc u){
                        float baseAmount = repairSpeed * heal.strength * Time.delta + fractionRepairSpeed * heal.strength * Time.delta * u.maxHealth() / 100f;
                        u.heal((u instanceof Building b && b.wasRecentlyDamaged() ? recentDamageMultiplier : 1f) * baseAmount * (u instanceof Unit u2 && u2.type == pi ? 0 : 1));
                    }
                }
                {
                rotate = true ;
                repairSpeed = 80000 / 60f ;
                //targetBuildings = true ;
                //beamWidth = 1.5f * tilesize ;
                //pulseStroke = 1.25f * tilesize ;
                //pulseRadius = 40 * tilesize ;
                bullet = bullet.copy() ;
                bullet.rangeOverride = 40 * tilesize ;
                mirror = false ;
                healColor = laserColor = Pal.accent ;
            }}) ;
            float orbRad = 5f / 46f * 60f, partRad = 3f / 46f * 60f;
            int parts = Math.round(10f / 46f * 60f);

            Vec2 v = textureToReal(new Vec2(300 / 330f * 96, 240 / 330f * 96), 96, 96) ;
            for (int i = 360 ; i >= 180 ; i -= 36) {
                int finalI = i;
                Vec2 v2 = ShaYeBuShi.circle(finalI, v.dst(0, 0) + 2 * tilesize, 0, 0) ;
                Weapon w = xieling.weapons.get(1).copy() ;
                w.shoot = w.shoot.copy() ;
                w.shoot.shots = 18 ;
                w.shoot.shotDelay = 10 ;
                w.reload = 240 ;
                w.x = v2.x ;
                w.y = v2.y ;
                w.shootX = w.shootY = 0 ;
                w.bullet = w.bullet.copy() ;
//                if (w.bullet instanceof FlakBulletType b) {
//                    b.backColor = Pal.accent ;
//                }
                w.parts = new Seq<>() ;
                var circleColor = Pal.accent ;
                var circleProgress = DrawPart.PartProgress.warmup.delay(0.9f);
                float circleY = 25f, circleRad = 11f, circleRotSpeed = 3.5f, circleStroke = 1.6f;
                float haloY = -15f, haloRotSpeed = 1.5f;
                w.parts.addAll(
                        new ShapePart(){{
                            progress = circleProgress;
                            color = circleColor;
                            circle = true;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad;
                            layer = Layer.effect;
                            y = circleY;
                        }},
                        new ShapePart(){{
                            progress = circleProgress;
                            rotateSpeed = -circleRotSpeed;
                            color = circleColor;
                            sides = 4;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad - 1f;
                            layer = Layer.effect;
                            y = circleY;
                        }},
                        new ShapePart(){{
                            progress = circleProgress;
                            rotateSpeed = -circleRotSpeed;
                            color = circleColor;
                            sides = 4;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad - 1f;
                            layer = Layer.effect;
                            y = circleY;
                        }},
                        new ShapePart(){{
                            progress = circleProgress;
                            rotateSpeed = -circleRotSpeed/2f;
                            color = circleColor;
                            sides = 4;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = 2f;
                            radius = 3f;
                            layer = Layer.effect;
                            y = circleY;
                        }},
                        new HaloPart(){{
                            progress = circleProgress;
                            color = circleColor;
                            tri = true;
                            shapes = 3;
                            triLength = 0f;
                            triLengthTo = 5f;
                            radius = 6f;
                            haloRadius = circleRad;
                            haloRotateSpeed = haloRotSpeed / 2f;
                            shapeRotation = 180f;
                            haloRotation = 180f;
                            layer = Layer.effect;
                            y = circleY;
                        }}
                );
                w.parts.each(p -> {
                    if (p instanceof ShapePart s) {
                        s.color = circleColor ;
                        s.x = 0 ;
                        s.y = 0 ;
                    }
                    if (p instanceof HaloPart h) {
                        h.color = circleColor ;
                        h.x = 0 ;
                        h.y = 0 ;
                    }
                });
                w.bullet.hitColor = w.bullet.trailColor = w.bullet.lightningColor = circleColor ;
                w.bullet.buildingDamageMultiplier = 0.01f ;
                w.bullet.status = StatusEffects.none ;
                weapons.add(w) ;
            }
            float n = 15 ;
            float m = 3 ;
            for (int i = 0 ; i < n ; i ++) {
                for (int z = 0 ; z < m ; z ++) {
                    int c = i ;
                    int l = z ;
                    abilities.add(new ShieldArcAbility() {
                        @Override
                        public void update(Unit u) {
                            if (u.isPlayer() || (c < 5)) {
                                super.update(u);
                            }
                            angleOffset += (2 * n + 1 - 2 * c) * 0.25f/* * (c % 2 == 0 ? 1 : -1)*/ ;
                        }
                        @Override
                        public void draw(Unit u) {
                            if (!u.isPlayer() && (c >= 5)) {
                                return ;
                            }
                            if(widthScale > 0.001f){
                                Draw.z(Layer.shields);

                                Draw.color(u.team.color/*.cpy().lerp(Color.clear, (c + 1) / n)*/, Color.white, Mathf.clamp(alpha));
                                var pos = ((Vec2) ShaYeBuShi.getPrivateField(ShieldArcAbility.class, null, "paramPos")).set(x, y).rotate(u.rotation - 90f).add(u);

                                if(!Vars.renderer.animateShields){
                                    Draw.alpha(0.4f);
                                }

                                if(region != null){
                                    Vec2 rp = offsetRegion ? pos : Tmp.v1.set(u);
                                    Draw.yscl = widthScale;
                                    Draw.rect(region, rp.x, rp.y, u.rotation - 90);
                                    Draw.yscl = 1f;
                                }

                                if(drawArc){
                                    Lines.stroke(width * widthScale);
                                    Lines.arc(pos.x, pos.y, radius, angle / 360f, u.rotation + angleOffset - angle / 2f);
                                }
                                Draw.reset();
                            }
                        }
                        @Override
                        public void displayBars(Unit u, Table t) {

                        }
                        {
                        radius = c * tilesize ;
                        angle = 360 / m - 10 ;
                        angleOffset = l * 360 / m/* + 360f / n * c */;
                        max = 31415 / n / m ;
                        cooldown = 10 * toSeconds ;
                        regen = 926 / 60f / n / m ;
                        whenShooting = false ;
                    }}) ;
                }
            }
        }} ;





        cptpc = new XianShangUnitType("cptpc"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
            health = Integer.MAX_VALUE ;
            dancixianshang = 3333333 ;
            miaoxianshang = 10181023 ;
            fenxianshang = Integer.MAX_VALUE ;
            speed *= 3 ;
            abilities.add(new HongZhaAbility(4000, 857142, 180, 1, "cptpc"), new CeFanAbility(4000, Integer.MAX_VALUE, toSeconds * health / miaoxianshang),
                    new CPTPCAbility()) ;
            weapons.add(new Weapon("shayebushu-cptpc-1"){{
                //continuous = true ;
                reload = 2 ;
                shootSound = Sounds.flame ;
                bullet = new HuoYanBulletType(9,114){{
                    can = b -> true ;
                    //trailWidth = 20 ;
                    //speed = 10 ;
                    //color = Pal.heal ;
                    damage = 142857 ;
                    //buildingDamageMultiplier = 0.3f;
                    //hitColor = Pal.heal ;
                    //healPercent = 10 ;
                    shake = 0 ;
                    status = kuiluan ;
                    statusDuration = 1018 * toSeconds ;
                    //speed *= 3 ;
                    lifetime *= 3 ;
                }
//                    @Override
//                    public void draw(Bullet b){
//                        super.draw(b);
//                        float rx = Tmp.v1.x, ry = Tmp.v1.y;
//                        float curStroke = 5;
////                    float shootLength = Math.min(Vec2.ZERO.dst(shootX,shootY), range);
////                    float curLength = Vec2.ZERO.dst(entry.bullet.aimX, entry.bullet.aimY);
////                    //resulting length of the bullet (smoothed)
////                    float resultLength = Mathf.approachDelta(curLength, shootLength, aimChangeSpeed);
////                    //actual aim end point based on length
////                    Tmp.v1.trns(rotation, lastLength = resultLength).add(x, y);
//                        curStroke = Mathf.lerpDelta(curStroke, true ? 1 : 0, 0.09f);
//                        Drawf.light(rx, ry, range * 1.5f, color, curStroke * 0.8f);
//                        Drawf.light(rx, ry, range * 1.5f, color, curStroke * 0.8f);
//                        Drawf.arrow(b.x,b.y,b.x+15,b.y+15,range,range,color);
//                    }
                };
            }});
            weapons.add(new Weapon("shayebushi-cptpc-2") {{
                top = false;
                mirror = false ;
                x = 0 ;
                shootSound = SYBSSounds.impact;
                //loopSound = Sounds.beam;
                //loopSoundVolume = 2f;
                shootY = 2f;
                reload = 120f;
                recoil = 1f;
                ejectEffect = Fx.none;
                range = Integer.MAX_VALUE ;
                bullet = new PointBulletType() {{
                    lifetime = 1 ;
                    trailEffect = Fx.none ;
                    fragBullets = 1 ;
                    fragSpread = 0 ;
                    fragRandomSpread = 0 ;
                    fragAngle = 45 ;
                    speed = Integer.MAX_VALUE ;
                    damage = 285714 ;
                    despawnSound = hitSound = SYBSSounds.impact ;
                    despawnShake = hitShake = 10 ;
                    status = kuiluan ;
                    statusDuration = 1018 * toSeconds ;
                    fragBullet = new LineBullet() {{
                        can = b -> true ;
                        colors = new Color[]{Pal.redderDust, Pal.accentBack} ;
                        length = 105 * tilesize ;
                        width = 2 * tilesize ;
                        lifetime = 90 ;
                        damage = 3333333 ;
                        damageInterval = 1;
                        hitColor = lightColor = Color.black ;
                        lightOpacity = 1 ;
                        status = kuiluan ;
                        statusDuration = 1018 * toSeconds ;
                        //continuous = false ;
                    }};
                }};
            }}) ;
            weapons.add(new Weapon("shayebushi-cptpc-3") {{
                mirror = false ;
                reload = 30 ;
                shootSound = SYBSSounds.flameBig;
                continuous = true ;
                bullet = new RotateBullet() {{
                    can = b -> true ;
                    lifetime = 30 ;
                    damage = 428571 ;
                    damageInterval = 1 ;
                    colors = new Color[]{Pal.redderDust.a(0.55f), Pal.lightishGray.a(0.7f), Pal.redderDust.a(0.8f), Pal.lightishGray, Color.white};
                    length = 18 * 3 * tilesize ;
                    status = kuiluan ;
                    statusDuration = 1018 * toSeconds ;
                    drawSize = length;
                    BulletType b = new MissileBulletType(4f, 120){{
                        frontColor = Color.valueOf("909090ff");
                        backColor = Color.valueOf("909090ff");
                        width = 7f;
                        height = 8f;
                        shrinkY = 0f;
                        homingPower = 0.14f;
                        splashDamageRadius = 80f;
                        splashDamage = 30f * 1.5f;
                        ammoMultiplier = 5f;
                        hitEffect = Fx.blastExplosion;
                        status = SYBSStatusEffects.kuiluan;
                        statusDuration = 60 ;
                        trailLength = 40 ;
                        trailWidth = 1 ;
                        trailColor = Color.valueOf("909090ff") ;
                        //knockback = -2 ;
                        //shake = 1 ;
                    }} ;
                    intervalBullet = b.copy() ;
                    ((BasicBulletType)intervalBullet).frontColor = Pal.redderDust ;
                    ((BasicBulletType)intervalBullet).backColor = Pal.redderDust ;
                    intervalBullet.trailColor = Pal.redderDust ;
                    intervalBullet.statusDuration = 1018 * toSeconds ;
                    intervalBullet.damage = 428571 ;
                    intervalBullet.splashDamage = 428571 ;
                    intervalBullet.pierce = true ;
                    intervalBullet.speed *= 4 ;
                    intervalBullet.lifetime = (48 * 3 - 18 * 3) * tilesize / intervalBullet.speed ;
                    intervalBullet.homingPower = 0 ;
                    intervalBullet.trailLength = 4 ;
                    intervalBullet.trailEffect = Fx.none ;
                    bulletInterval = 0.33f ;
                    trailColor = Pal.redderDust.a(1) ;
                }} ;
            }}) ;
            weapons.add(new Weapon("shayebushi-cptpc-4") {
                @Override
                public void addStats(UnitType u , Table t){
                    if(inaccuracy > 0){
                        t.row();
                        t.add("[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int)inaccuracy + " " + StatUnit.degrees.localized());
                    }
                    if(!alwaysContinuous && reload > 0){
                        t.row();
                        t.add("[lightgray]" + Stat.reload.localized() + ": " + (mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / reload * shoot.shots, 2) + " " + StatUnit.perSecond.localized());
                    }
                    SYBSStatValues.ammo(ObjectMap.of(u, bullet)).display(t);
                }
                {
                reload = 60 ;
                bullet = new CeFanBulletType() {{
                    speed = Integer.MAX_VALUE ;
                    cefanshangxian = Integer.MAX_VALUE - 1 ;
                    trailEffect = SYBSFx.jiange2;
                    damage = 571428 ;
                }} ;
            }}) ;
            weapons.add(new Weapon("shayebushi-cptpc-5") {
                @Override
                public void addStats(UnitType u , Table t){
                    if(inaccuracy > 0){
                        t.row();
                        t.add("[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int)inaccuracy + " " + StatUnit.degrees.localized());
                    }
                    if(!alwaysContinuous && reload > 0){
                        t.row();
                        t.add("[lightgray]" + Stat.reload.localized() + ": " + (mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / reload * shoot.shots, 2) + " " + StatUnit.perSecond.localized());
                    }
                    SYBSStatValues.ammo(ObjectMap.of(u, bullet)).display(t);
                }
                @Override
                public void load() {
                    super.load() ;
                    if (bullet instanceof PayloadBullet p) {
                        p.block = SYBSBlocks.qunxing ;
                    }
                }
                {
                reload = 60 ;
                bullet = new PayloadBullet() {{
                    speed = 1 ;
                    lifetime = Integer.MAX_VALUE ;
                    damage = 714285 ;
                }} ;
            }}) ;
        }};
        ShaYeBuShi.removeContent(cptpc);
        cptpc = new XianShangUnitType("cptpc") {
            @Override
            public void update(Unit u) {
                super.update(u) ;
                //if (1 + 1 == 2) return ;
                for (Unit u2 : Groups.unit) {
                    if (u2.type.minfo.mod != null && !u2.type.minfo.mod.name.contains("shayebushi")) {
                        Groups.unit.remove(u2) ;
                        Groups.all.remove(u2) ;
                        Groups.sync.remove(u2) ;
                        Groups.draw.remove(u2) ;
                        u2.remove() ;
                        //System.out.println(u2.type.localizedName);
                    }
                }
                Groups.bullet.intersect(u.x, u.y, Integer.MAX_VALUE, Integer.MAX_VALUE, b -> {
                    if (b.type.minfo.mod != null && !b.type.minfo.mod.name.contains("shayebushi")) {
                        Groups.all.remove(b) ;
                        Groups.bullet.remove(b) ;
                        Groups.draw.remove(b) ;
                        b.remove();
                        //System.out.println(6) ;
                    }
                }) ;
                u.x = 0 ;
                u.y = 0 ;
            }
            {
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
            //health = 80000 ;
            //dancixianshang = 3000 ;
            //miaoxianshang = 6000 ;
            health = 0 ;
            dancixianshang = -1 ;
            miaoxianshang = -1 ;
            fenxianshang = -1 ;
        }} ;
        shenwangjidanwei.addAll(shuangxingyijieduan, shuangxingerjieduan, daodanxing, jiguangxing, shamie, anyong) ;
        shenlingjidanwei.addAll(shenqi, duoxing, xieling, anye, shengling, chichao, shuihua) ;
        shendijidanwei.addAll(huanyu) ;



        /*
        for (UnitType u : content.units()) {
            if (u.minfo.mod != null && u.minfo.mod.name == "shayebushi") {
                for (Weapon w : u.weapons) {
                    float t = shenlingjidanwei.contains(u) ? 60 : shenwangjidanwei.contains(u) ? 100 : shendijidanwei.contains(u) ? 150 : -1 ;
                    if (t == -1) break ;
                    if (w.range() >= t * tilesize) {
                        w.bullet.buildingDamageMultiplier = 0.2f ;
                    }
                }
            }
        }*/
    }
    public static TextureRegion getError() {
        try {
            Field f = TextureAtlas.class.getDeclaredField("error") ;
            f.setAccessible(true);
//            if (f.get(Core.atlas) == null) {
//                Core.atlas.setErrorRegion("error") ;
//            }
            if (f.get(Core.atlas) == null) {
                return new TextureRegion(new Texture(new Pixmap(Version.build == -1 ? Core.files.absolute("D:\\Mindustry-master\\core\\build\\libs\\A-files\\sprites\\unit\\weapons\\error.png") : mods.getMod("shayebushi").file != null ? mods.getMod("shayebushi").file.child("sprites/unit/weapons/error.png") : null))) ;
            }
            return (TextureRegion) f.get(Core.atlas) ;
        }
        catch (Throwable t) {
            t.printStackTrace();
            return new TextureRegion(new Texture(new Pixmap(Version.build == -1 ? Core.files.absolute("D:\\Mindustry-master\\core\\build\\libs\\A-files\\sprites\\unit\\weapons\\error.png") : mods.getMod("shayebushi").file != null ? mods.getMod("shayebushi").file.child("sprites/unit/weapons/error.png") : null))) ;
        }
    }
    public static TextureRegion loadPng(Fi path, String name) {
        if (path == null) {
            return getError() ;
        }
        for (Fi f : path.list()) {
            if (f.name().equals(name + ".png")) {
                //System.out.println(f.name());
                return new TextureRegion(new Texture(new Pixmap(f)));
            }
        }
        return getError() ;
    }
    public static void loadWeapons() {
        Fi target = Version.build == -1 ? Core.files.absolute("D:\\Mindustry-master\\core\\build\\libs\\A-files\\sprites\\units\\weapons") : mods.getMod("shayebushi").file != null ? new Seq<Fi>(new ZipFi(mods.getMod("shayebushi").file).list()).find(f -> f.name().equals("sprites")).child("units").child("weapons") : null ;
        //System.out.println(target == null);
        //for (Fi f : target.list()) {
            //System.out.println(f.name());
        //}
        for (UnitType u : content.units()) {
            for (Weapon w : u.weapons) {
                //System.out.println((w.region == null || !w.region.found()) + (" " + w.name + ".png")) ;
                if ((u.minfo.mod == null && w.name.contains("sybs-")) || (u.minfo.mod != null && u.minfo.mod.name.equals("shayebushi"))) {
                    if (w.region == null || !w.region.found()){
                        w.region = loadPng(target, w.name);
                    }
                    if (w.heatRegion == null || !w.heatRegion.found()) {
                        w.heatRegion = loadPng(target, w.name + "-heat");
                    }
                    if (w.cellRegion == null || !w.cellRegion.found()) {
                        w.cellRegion = loadPng(target, w.name + "-cell");
                    }
                    if (w.outlineRegion == null || !w.outlineRegion.found()) {
                        w.outlineRegion = loadPng(target, w.name + "-outline");
                        if ((w.outlineRegion == null || !w.outlineRegion.found()) && w.region != null && w.region.found()) {
                            w.outlineRegion = new TextureRegion(new Texture(Pixmaps.outline(new PixmapRegion(w.region.texture.getTextureData().getPixmap()), u.outlineColor, u.outlineRadius)));
                        }
                    }
                    for (DrawPart p : w.parts) {
                        if (!(p instanceof RegionPart r) || !r.drawRegion) {
                            continue;
                        }
                        Seq<TextureRegion> rs = new Seq<>();
                        Seq<TextureRegion> os = new Seq<>();
                        String name = r.name == null ? w.name + r.suffix : r.name;
                        if (r.turretShading && r.mirror) {
                            if (r.regions.length < 2) {
                                rs.addAll(loadPng(target, name + "-l"), loadPng(target, name + "-l"));
                            }
                            if (r.outlines.length < 2) {
                                os.addAll(loadPng(target, name + "-l-outline"), loadPng(target, name + "-l-outline"));
                            }
                        } else {
                            if (r.regions.length < 1) {
                                rs.addAll(loadPng(target, name));
                            }
                            if (r.outlines.length < 1) {
                                os.addAll(loadPng(target, name + "-outline"));
                            }
                        }
                        r.regions = rs.toArray(TextureRegion.class) ;
                        r.outlines = rs.toArray(TextureRegion.class) ;
                    }
                }
                //System.out.println(ShaYeBuShi.getPrivateField(Vars.mods, "packer") == null) ;
                if (Version.build != -1) {
                    ShaYeBuShi.invokePrivateMethod(u, "makeOutLine", new Seq<>(new Object[]{MultiPacker.PageType.main, ShaYeBuShi.getPrivateField(Vars.mods, "packer"), w.region, !w.top || w.parts.contains(p -> p.under), u.outlineColor, u.outlineRadius}));
                }
            }
        }
    }
    public static void loadWeapons(UnitType u) {
        if (1 + 1 == 2) return ;
        Fi target = Version.build == -1 ? Core.files.absolute("D:\\Mindustry-master\\core\\build\\libs\\A-files\\sprites\\units\\weapons") : mods.getMod("shayebushi").file != null ? new Seq<Fi>(new ZipFi(mods.getMod("shayebushi").file).list()).find(f -> f.name().equals("sprites")).child("units").child("weapons") : null ;
//        System.out.println(target == null);
        //for (Fi f : new Seq<Fi>(new ZipFi(mods.getMod("shayebushi").file).list()).find(f -> f.name().equals("sprites")).child("unit/weapons").list()) {
            //System.out.println(f.name());
        //}
        //System.out.println(new Seq<Fi>(new ZipFi(mods.getMod("shayebushi").file).list()).find(f -> f.name().equals("sprites")).child("unit").child("weapons").list().length);
        for (Weapon w : u.weapons) {
//            if (u.minfo.mod != null && u.minfo.mod.name.equals("shayebushi")) {
//                System.out.println(w.name);
//            }
            //System.out.println((w.region == null || !w.region.found()) + (" " + w.name + ".png")) ;
            if ((u.minfo.mod == null && w.name.contains("sybs-")) || (u.minfo.mod != null && u.minfo.mod.name.equals("shayebushi"))) {
                if (w.region == null || !w.region.found()){
                    w.region = loadPng(target, w.name);
                }
                if (w.heatRegion == null || !w.heatRegion.found()) {
                    w.heatRegion = loadPng(target, w.name + "-heat");
                }
                if (w.cellRegion == null || !w.cellRegion.found()) {
                    w.cellRegion = loadPng(target, w.name + "-cell");
                }
                if (w.outlineRegion == null || !w.outlineRegion.found()) {
                    //System.out.println((loadPng(target, w.name + "-outline") == null) + " " + (getError() == null));
                    w.outlineRegion = loadPng(target, w.name + "-outline");
                }
                for (DrawPart p : w.parts) {
                    if (!(p instanceof RegionPart r) || !r.drawRegion) {
                        continue;
                    }
                    Seq<TextureRegion> rs = new Seq<>();
                    Seq<TextureRegion> os = new Seq<>();
                    String name = r.name == null ? w.name + r.suffix : r.name;
                    if (r.turretShading && r.mirror) {
                        if (r.regions.length < 2) {
                            rs.addAll(loadPng(target, name + "-l"), loadPng(target, name + "-l"));
                        }
                        if (r.outlines.length < 2) {
                            os.addAll(loadPng(target, name + "-l-outline"), loadPng(target, name + "-l-outline"));
                        }
                    }
                    else {
                        if (r.regions.length < 1) {
                            rs.addAll(loadPng(target, name));
                        }
                        if (r.outlines.length < 1) {
                            os.addAll(loadPng(target, name + "-outline"));
                        }
                    }
                    r.regions = rs.toArray(TextureRegion.class) ;
                    r.outlines = rs.toArray(TextureRegion.class) ;
                }
            }
            //MultiPacker mp = (MultiPacker) ShaYeBuShi.getPrivateField(Vars.mods, "packer") ;
            //ShaYeBuShi.invokePrivateMethod(u, "makeOutLine", new Seq<>(new Object[]{MultiPacker.PageType.main, mp, w.region, !w.top || w.parts.contains(p -> p.under), u.outlineColor, u.outlineRadius}));
//            if (u.minfo.mod != null && u.minfo.mod.name.equals("shayebushi")) {
//                System.out.println(w.region.found());
//            }
        }
    }
    public static Vec2 textureToReal(Vec2 v, int width, int height) {
        return new Vec2((v.x - width / 2f) / 4, (height / 2f - v.y) / 4) ;
    }
}
