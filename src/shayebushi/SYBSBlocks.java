package shayebushi;

import arc.func.Boolf;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.math.geom.Vec2;
import arc.scene.ui.layout.Table;
import arc.struct.EnumSet;
import arc.struct.IntSeq;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Structs;
import mindustry.ai.UnitCommand;
import mindustry.content.*;
import mindustry.core.Version;
import mindustry.ctype.ContentType;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.UnitSorts;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.RadialEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.DrawPart;
import mindustry.entities.part.HaloPart;
import mindustry.entities.part.RegionPart;
import mindustry.entities.part.ShapePart;
import mindustry.entities.pattern.*;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.CacheLayer;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.Edges;
import mindustry.world.Tile;
import mindustry.world.blocks.defense.MendProjector;
import mindustry.world.blocks.defense.OverdriveProjector;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.heat.HeatConductor;
import mindustry.world.blocks.heat.HeatProducer;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.payloads.PayloadConveyor;
import mindustry.world.blocks.power.PowerGraph;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.HeatCrafter;
import mindustry.world.blocks.production.Pump;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.blocks.storage.Unloader;
import mindustry.world.blocks.units.UnitAssembler;
import mindustry.world.blocks.units.UnitAssemblerModule;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.consumers.ConsumeItemRadioactive;
import mindustry.world.consumers.ConsumeItems;
import mindustry.world.consumers.ConsumePower;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.world.modules.PowerModule;
import shayebushi.entities.bullet.*;
//import shayebushi.world.block.defense.turrets.TurretCeFan;
import shayebushi.world.blocks.*;
import shayebushi.world.blocks.abilities.*;
import shayebushi.world.blocks.defense.FangFuSheLiChang;
import shayebushi.world.blocks.defense.WallPowerTurret;
import shayebushi.world.blocks.defense.ZhenDangHuDunBlock;
import shayebushi.world.blocks.defense.turrets.*;
import shayebushi.world.blocks.environment.FangSheXingKuang;
import shayebushi.world.blocks.heat.ReLiangChuanShuJi;
import shayebushi.world.blocks.logic.FanDanWei;
import shayebushi.world.blocks.logic.JinYongChuLiQi;
import shayebushi.world.blocks.power.CiChangFaDianJi;
import shayebushi.world.blocks.power.DianYaConsumeGenerator;
import shayebushi.world.blocks.power.DianYaNode;
import shayebushi.world.blocks.power.HeFanYingDui;
import shayebushi.world.blocks.production.*;
import shayebushi.world.blocks.storage.MultiCore;
import shayebushi.world.blocks.units.ChongZhiRuKou;
import shayebushi.world.blocks.units.DanWeiZhiZaoChang;
import shayebushi.world.blocks.units.LanTuChongGouChang;
import shayebushi.world.consumers.ConsumeCiChang;
import shayebushi.world.consumers.ConsumeDianYa;
import shayebushi.world.draw.DrawWallTurret;
import shayebushi.world.sandbox.DianYaSoure;
import shayebushi.world.sandbox.WuPinYuan;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;
import static arc.util.Time.toMinutes;
import static arc.util.Time.toSeconds;
import static mindustry.Vars.*;
import static mindustry.content.Items.*;
import static mindustry.type.ItemStack.with;
import static mindustry.world.meta.StatValues.ammo;
import static shayebushi.SYBSItems.*;
import static shayebushi.SYBSUnitTypes.*;
//import static shayebushi.world.block.defense.turrets.TurretCeFan.color;

public class SYBSBlocks {

    public ContentType getContentType() {
        return null;
    }
    public static Block moyan , qunxing;
    public static Block cefan;
    public static Block jingu , jizeng ;
    public static Block likuangshi , lvkuangshi, youkuangshi, leikuangshi, hefeiliao ;
    public static Block yanjiang , nongliusuan;
    public static Block taihejinyelianchang ;
    public static Block lvzuantou , taihejinzuantou ;
    public static Block yijihexin ;
    public static TurretDieTai dietai ;
    public static TurretDieTai dietai1 , dietai2, dietai3 , dietai4 , dietai5 , dietai6 , dietai7 , dietai8 , dietai9 , dietai10 ;
    public static ItemTurret youling = (ItemTurret)Blocks.spectre , leiguang = (ItemTurret) Blocks.fuse , qixuan = (ItemTurret) Blocks.cyclone, ezhao = (ItemTurret) Blocks.foreshadow, langyong = (ItemTurret) Blocks.ripple, fengqun = (ItemTurret) Blocks.swarmer;
    public static ItemTurret chuangshang = (ItemTurret) Blocks.scathe, tianqian = (ItemTurret) Blocks.smite, quli = (ItemTurret) Blocks.disperse, taitan = (ItemTurret) Blocks.titan;
    public static LaserTurret ronghui = (LaserTurret) Blocks.meltdown ;
    public static LiquidTurret bolang = (LiquidTurret)Blocks.wave , haixiao = (LiquidTurret)Blocks.tsunami ;
    public static PowerTurret moling = (PowerTurret) Blocks.malign, jienan = (PowerTurret) Blocks.afflict ;
    public static ContinuousTurret guanghui = (ContinuousTurret) Blocks.lustre ;
    public static UnitAssembler feichuanchang = (UnitAssembler) Blocks.shipAssembler, jijiachang = (UnitAssembler) Blocks.mechAssembler, tankechang = (UnitAssembler) Blocks.tankAssembler ;
    public static Block ceshi , ceshii , ceshiiii;
    public static DanWeiZhiZaoChang danweizhizaochang ;
    public static KeYanZhongXin keyanzhongxin ;
    public static MoKuaiGongChang shenlingmokuai ;
    public static LanTuChongGouChang shenlingzhuangpei ;
    public static UnitAssemblerModule gaojimokuai ;
    public static ZhaDanBlock shuileibushu ;
    public static WuPinYuan wupinyuan ;
    public static LiquidTurret zhuoshao ;
    public static TurretSangZhong sangzhong ;
    public static OverdriveProjector jixianchaosu ;
    public static ShuangChongZuanTou duogongnengzuantou ;
    public static WallPowerTurret shenlingzhenghe, shendizhenghe ;
    public static ZengYuanBuShuBlock zengyuanbushu ;
    public static TiaoJieShiJianBlock tiaojieshijian ;
    public static ShiKuaiBlock[] shikuais = {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
    public static TiaoBoBlock tiaobo ;
    public static PowerTurret shefa ;
    public static DianYaNode diyadianjiedian, zhongyadianjiedian, gaoyadianjiedian ;
    public static DianYaSoure diyadianyuan, zhongyadianyuan, gaoyadianyuan ;
    public static MultiTurret ceshiii ;
    public static MultiBlock ceshiiiii ;
    public static Conveyor taihejinchuansongdai, fangfushichuansongdai ;
    public static Junction taihejinjiaochaqi ;
    public static DuctRouter taihejinluyouqi ;
    public static ItemBridge taihejinqiao ;
    public static GenericCrafter youlixinji, fangfushecailiaofuheji, hejinduanzaoqi;
    public static FangFuSheLiChang fangfushelichang, wuxianlichang ;
    public static ItemTurret shandian, dianci, jingkong ;
    public static PowerTurret xielei ;
    public static ShangHaiXianShiBlock shanghaixianshi ;
    public static FanDanWei fankongjun, fanlujun, fanhaijun, fanyiqie ;
    public static DanWeiShengCheng danweishengcheng ;
    public static YiCiXingXieBao yicixingxiebao ;
    public static PowerNode bianyaqi ;
    public static Pump fangfushibeng ;
    public static Conduit fangfushidaoguan ;
    public static StorageBlock taihejincangku ;
    public static Unloader taihejinzhuangxieqi ;
    public static PayloadConveyor jvxingzaihechuansongdai;
    public static AbilityContinuousTurret jianmo ;
    public static MultiBlock shandian6 ;
    public static GenericCrafter xietongrongliandanyuan;
    public static MultiBlock lianheyelianjizu ;
    public static MassDriver taihejinzhiqu ;
    public static MultiCrafter jisuchongyayitiji, xiangweifadiangongchang;
    public static DianYaConsumeGenerator xiangweifadianji ;
    public static MultiShuangChongZuanTou xiangweizonghegongchang ;
    public static CiChangFaDianJi cichangdianshifadianji ;
    public static OreBlock youqiang ;
    public static HeFanYingDui liebianfanyingdui ;
    public static ZhaDanBlock xiaoxinghedan, zhadan ;
    public static Seq<ZhaDanBlock> zhadans = new Seq<>() ;
    public static JinYongChuLiQi jinyongchuliqi ;
    public static ZhiXianBlock zhixian ;
    public static XianDuanBlock xianduan ;
    public static PowerTurret ceshiiiiii ;
    public static ChongZhiRuKou chongzhirukou ;
    public static MendProjector xiulijihuotouyingqi;
    public static MultiBlock zhanquweihuxitong ;
    public static HeatConductor weixingreliangchuanshuji, weixingreliangluyouqi ;
    public static ZhenDangHuDunBlock zhendanghudunfashengqi, shengyu ;
    public static UnitFactory kongjungongchang = (UnitFactory) Blocks.airFactory ;
    public static MultiCrafter lixichongzuhejinduanlu ;
    public static GenericCrafter daxingdianjieji, daxinglengdongyehunheqi, ruangangduandaji ;
    public static HeatCrafter xiangjiaohechengji, daxingtanhuawuganguo, fangyoutiquji ;
    public static HeatProducer daxingyanghuashi ;
    public static PowerTurret zaiezujian ;
    public static MultiBlock zaie ;
    public static MultiCore sanjihexin ;
    public static void load() {
        moyan = new TurretMoYan("moyan"){{
            requirements(Category.turret, with(Items.carbide, 900, Items.beryllium, 1000, silicon, 1000, li, 1000, lv, 1000, taihejin, 1200, Items.phaseFabric, 900));
            var haloProgress = DrawPart.PartProgress.warmup;
            Color haloColor = Color.valueOf("d370d3"), heatCol = Color.purple;
            float haloY = -15f, haloRotSpeed = 1.5f;

            var circleProgress = DrawPart.PartProgress.warmup.delay(0.9f);
            var circleColor = haloColor;
            float circleY = 0f, circleRad = 16.5f, circleRotSpeed = 3.5f, circleStroke = 1.6f;

            shootSound = Sounds.malignShoot;
            loopSound = Sounds.spellLoop;
            loopSoundVolume = 1.3f;
            rotWhenShooting = false ;
            alwaysRot = true ;

            shootType = new FlakBulletType(8f, 70f){{
                sprite = "missile-large";

                lifetime = 90f;
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
                buildingDamageMultiplier = 0.3f;

                trailEffect = Fx.colorSpark;
                trailRotation = true;
                trailInterval = 3f;
                lightning = 1;
                lightningCone = 15f;
                lightningLength = 20;
                lightningLengthRand = 30;
                lightningDamage = 20f;

                homingPower = 0.17f;
                homingDelay = 19f;
                homingRange = 160f;

                explodeRange = 160f;
                explodeDelay = 0f;

                flakInterval = 20f;
                despawnShake = 3f;

                fragBullet = new LaserBulletType(65f){{
                    colors = new Color[]{haloColor.cpy().a(0.4f), haloColor, Color.white};
                    buildingDamageMultiplier = 0.25f;
                    width = 19f;
                    hitEffect = Fx.hitLancer;
                    sideAngle = 175f;
                    sideWidth = 1f;
                    sideLength = 40f;
                    lifetime = 22f;
                    drawSize = 400f;
                    length = 180f;
                    pierceCap = 2;
                    pierceArmor = true ;
                }};

                fragSpread = fragRandomSpread = 0f;

                splashDamage = 0f;
                hitEffect = Fx.hitSquaresColor;
                collidesGround = true;
                pierceArmor = true ;
            }};

            size = 6;
            drawer = new DrawTurret("reinforced-"){{
                parts.addAll(

                        //summoning circle
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

//                        new ShapePart(){{
//                            progress = circleProgress;
//                            rotateSpeed = -circleRotSpeed;
//                            color = circleColor;
//                            sides = 4;
//                            hollow = true;
//                            stroke = 0f;
//                            strokeTo = circleStroke;
//                            radius = circleRad - 1f;
//                            layer = Layer.effect;
//                            y = circleY;
//                        }},

                        //outer squares

                        new ShapePart(){{
                            progress = circleProgress;
                            //rotateSpeed = -circleRotSpeed;
                            color = circleColor;
                            sides = 3;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = circleStroke;
                            radius = circleRad - 1.5f;
                            layer = Layer.effect;
                            y = circleY;
                        }},

                        //inner square
                        new ShapePart(){{
                            progress = circleProgress;
                            //rotateSpeed = -circleRotSpeed/2f;
                            rotation = 180f ;
                            color = circleColor;
                            sides = 3;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = 2f;
                            radius = circleRad - 1.5f;
                            layer = Layer.effect;
                            y = circleY;
                        }},
//                        new HaloPart(){{
//                            progress = haloProgress;
//                            color = Pal.accent;
//                            layer = Layer.effect;
//                            y = circleY;
//                            haloRotateSpeed = -haloRotSpeed;
//
//                            shapes = 6;
//                            shapeRotation = 180f;
//                            triLength = 0f;
//                            triLengthTo = 2f;
//                            haloRotation = 45f;
//                            haloRadius = 16f;
//                            tri = true;
//                            radius = 12f;
//                        }},
//                        new HaloPart(){{
//                            progress = haloProgress;
//                            color = Pal.accent;
//                            layer = Layer.effect;
//                            y = circleY;
//                            haloRotateSpeed = haloRotSpeed;
//
//                            shapes = 6;
//                            triLength = 0f;
//                            triLengthTo = 3f;
//                            haloRotation = 45f;
//                            haloRadius = 10f;
//                            tri = true;
//                            radius = 9f;
//                        }},
                        new HaloPart(){{
                            progress = haloProgress;
                            color = circleColor;
                            layer = Layer.effect;
                            y = circleY;
                            haloRotateSpeed = -haloRotSpeed;

                            shapes = 6;
                            shapeRotation = 180f;
                            triLength = 0f;
                            triLengthTo = 5f;
                            haloRotation = 45f;
                            haloRadius = 20f;
                            tri = true;
                            radius = 6f;
                        }},
                        new HaloPart(){{
                            progress = haloProgress;
                            color = circleColor;
                            layer = Layer.effect;
                            y = circleY;
                            haloRotateSpeed = haloRotSpeed;

                            shapes = 6;
                            triLength = 0f;
                            triLengthTo = 10f;
                            haloRotation = 45f;
                            haloRadius = 20f;
                            tri = true;
                            radius = 12f;
                        }},
                        new ShapePart(){{
                            progress = circleProgress;
                            //rotateSpeed = -circleRotSpeed/2f;
                            //rotation = 180f ;
                            color = circleColor;
                            circle = true;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = 2f;
                            radius = circleRad + 16.5f;
                            layer = Layer.effect;
                            y = circleY;
                        }},
                        new HaloPart(){{
                            progress = haloProgress;
                            color = circleColor;
                            layer = Layer.effect;
                            y = circleY;
                            haloRotateSpeed = haloRotSpeed;

                            shapes = 6;
                            triLength = 0f;
                            triLengthTo = 18f;
                            haloRotation = 45f;
                            haloRadius = circleRad + 16.5f;
                            tri = true;
                            radius = 6f ;
                        }},
                        new ShapePart(){{
                            progress = circleProgress;
                            //rotateSpeed = -circleRotSpeed/2f;
                            //rotation = 180f ;
                            color = circleColor;
                            circle = true;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = 2f;
                            radius = circleRad + 34.5f;
                            layer = Layer.effect;
                            y = circleY;
                        }}
                );

                Color heatCol2 = heatCol.cpy().add(0.1f, 0.1f, 0.1f).mul(1.2f);
                /*
                for(int i = 1; i < 4; i++){
                    int fi = i;
                    parts.add(new RegionPart("-spine"){{
                        outline = false;
                        progress = DrawPart.PartProgress.warmup.delay(fi / 5f);
                        heatProgress = DrawPart.PartProgress.warmup.add(p -> (Mathf.absin(3f, 0.2f) - 0.2f) * p.warmup);
                        mirror = true;
                        under = true;
                        layerOffset = -0.3f;
                        turretHeatLayer = Layer.turret - 0.2f;
                        moveY = 9f;
                        moveX = 1f + fi * 4f;
                        moveRot = fi * 60f - 130f;

                        color = Color.valueOf("bb68c3");
                        heatColor = heatCol2;
                        moves.add(new DrawPart.PartMove(DrawPart.PartProgress.recoil.delay(fi / 5f), 1f, 0f, 3f));
                    }});
                }
                */
            }};

            velocityRnd = 0.15f;
            heatRequirement = -1f;
            maxHeatEfficiency = 2f;
            warmupMaintainTime = 180f;
            consume(new ConsumeDianYa(200, 0, false, 3));

            //shoot = new ShootSummon(0f, 0f, circleRad, 48f);
            shoot = new ShootSpread(6, 60) ;

            minWarmup = 0.96f;
            shootWarmupSpeed = 0.03f;

            //shootY = circleY - 5f;

            outlineColor = Pal.darkOutline;
            envEnabled |= Env.space;
            reload = 1f / 6;
            range = 740;
            shootCone = 360f;
            scaledHealth = 370;
            rotateSpeed = 2f;
            //recoil = 0.5f;
            //recoilTime = 30f;
            shake = 3f;
        }};
        cefan = new ItemTurretXianZhi("cefan"){
            @Override
            public void setStats(){
                super.setStats();
            }
            {
            float brange = range = 400f;
            limitPlaceOnCount = 3 ;
            requirements(Category.turret, with(lv, 1000, copper,1000, taihejin, 600, Items.surgeAlloy, 300, SYBSItems.li, 200, silicon, 600));
            ammo(
                    Items.phaseFabric, new CeFanBulletType(){{
                        shootEffect = SYBSFx.fixedInstShoot;
                        //hitColor = color;
                        //hitColor = Team.crux.color;
                        //hitEffect = new MultiEffect(Fx.massiveExplosion, new WrapEffect(Fx.dynamicSpikes, color, 24f), new WaveEffect(){{

                        cefanshangxian = 200000 ;
                        smokeEffect = Fx.smokeCloud;
                        trailEffect = SYBSFx.fixedInstTrail;
                        //trailColor = color;
                        //trailColor = Team.crux.color;
                        despawnEffect = SYBSFx.fixedInstBomb;
                        trailSpacing = 20f;
                        damage = 114;
                        //buildingDamageMultiplier = 0.2f;
                        speed = brange;
                        hitShake = 6f;
                        ammoMultiplier = 1f;
                    }}
            );

            maxAmmo = 40;
            //itemCapacity = 50 ;
            ammoPerShot = 5;
            rotateSpeed = 2f;
            reload = 600f;
            ammoUseEffect = Fx.casing3Double;
            recoil = 5f;
            cooldownTime = reload;
            shake = 4f;
            size = 4;
            shootCone = 2f;
            shootSound = Sounds.railgun;
            unitSort = UnitSorts.strongest;
            envEnabled |= Env.space;

            coolantMultiplier = 0.4f;
            scaledHealth = 150;

            coolant = consumeCoolant(1f);
            consumePower(100f);
        }};
        jingu = new LaserTurretXianZhi("jingu"){{
            requirements(Category.turret, with(lv, 600 , copper, 1200, SYBSItems.li, 350, Items.graphite, 300, Items.surgeAlloy, 325,taihejin, 225, silicon, 325));
            limitPlaceOnCount = 2 ;
            shootEffect = Fx.shootBigSmoke2;
            shootCone = 40f;
            recoil = 4f;
            size = 4;
            shake = 2f;
            range = 195f;
            reload = 240f;
            firingMoveFract = 0.5f;
            shootDuration = 230f;
            shootSound = Sounds.laserbig;
            loopSound = Sounds.beam;
            loopSoundVolume = 2f;
            envEnabled |= Env.space;

            shootType = new LightningContinuousLaserBulletType(){{
                damage = 12f ;
                length = 200f;
                hitEffect = Fx.hitMeltdown;
                hitColor = Pal.meltdownHit;
                status = SYBSStatusEffects.dangji;
                drawSize = 420f;
                //damageInterval = 300 ;
                incendChance = 0.4f;
                incendSpread = 5f;
                incendAmount = 1;
                ammoMultiplier = 1f;
                lightningColor = Pal.powerLight ;
            }};

            scaledHealth = 200;
            coolant = consumeCoolant(0.5f);
            consumePower(25f);
        }};
        jizeng = new LaserTurretXianZhi("jizeng"){{
            requirements(Category.turret, with(lv, 600 , copper, 1200, SYBSItems.li, 350, Items.graphite, 300, Items.surgeAlloy, 325,taihejin, 225, silicon, 325));
            limitPlaceOnCount = 1 ;
            shootEffect = Fx.shootBigSmoke2;
            shootCone = 40f;
            recoil = 4f;
            size = 4;
            shake = 2f;
            range = 195f;
            reload = 240f;
            firingMoveFract = 0.5f;
            shootDuration = 230f;
            shootSound = Sounds.laserbig;
            loopSound = Sounds.beam;
            loopSoundVolume = 2f;
            envEnabled |= Env.space;

            shootType = new JiZengBulletType(1f){{
                chixushijian = shootDuration ;
                damageInterval = 1 ;
                lifetime = 32f ;
                length = 200f;
                hitEffect = Fx.hitMeltdown;
                hitColor = Pal.meltdownHit;
                //status = SYBSStatusEffects.zhongdufushi;
                drawSize = 420f;
                meimiaofanbeicishu = 4 ;
                //damageInterval = 300 ;
                incendChance = 0.4f;
                incendSpread = 5f;
                incendAmount = 1;
                ammoMultiplier = 1f;
            }};

            scaledHealth = 200;
            coolant = consumeCoolant(0.5f);
            consumePower(25f);
        }};
        likuangshi = new OreBlock("ore-li",SYBSItems.li){{
            //BasicGenerator.ore(Blocks.oreCopper, Blocks.ferricStone, 5f, 0.8f );
            enableDrawStatus = true ;
            oreDefault = true;
            oreThreshold = 0.81f;
            oreScale = 23.47619f;
            mapColor = li.color ;
        }};
        lvkuangshi = new OreBlock("ore-lv",lv){{
            enableDrawStatus = true ;
            oreDefault = true;
            oreThreshold = 0.81f;
            oreScale = 23.47619f;
            mapColor = lv.color ;
        }};
        yanjiang = new Floor("yanjiange"){{
            canOverdrive = false ;
            enableDrawStatus = true ;
            speedMultiplier = 0.5f;
            variants = 0;
            status = StatusEffects.melting;
            statusDuration = 90f;
            liquidDrop = SYBSLiquids.yanjiang;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
            mapColor = SYBSLiquids.yanjiang.color;
            attributes.set(Attribute.heat, SYBSLiquids.yanjiang.temperature * 0.85f);
        }};
        nongliusuan = new Floor("nongliusuane"){{
            canOverdrive = false ;
            enableDrawStatus = true ;
            speedMultiplier = 0.5f;
            variants = 0;
            status = SYBSStatusEffects.zhongdufushi ;
            statusDuration = 90f;
            liquidDrop = SYBSLiquids.nongliusuan;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
            mapColor = SYBSLiquids.nongliusuan.color ;
        }};
        taihejinyelianchang = new GenericCrafter("taihejinyelianchang"){{
            requirements(Category.crafting, with(SYBSItems.li, 30, Items.lead, 25, lv, 20 , Items.titanium,10));
            craftEffect = Fx.smeltsmoke;
            outputItem = new ItemStack(taihejin, 1);
            craftTime = 68.4f;
            size = 2;
            hasPower = true;
            hasLiquids = false;
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffef99")){{
                flameRadius = flameRadiusIn = 0 ;
            }});
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.07f;

            consumeItems(with(lv, 2, Items.titanium, 2));
            consumePower(4f);
        }};

        youling.ammo(
                Items.graphite, new BasicBulletType(7.5f, 70) {{
                    hitSize = 4.8f;
                    width = 15f;
                    height = 21f;
                    shootEffect = Fx.shootBig;
                    ammoMultiplier = 4;
                    reloadMultiplier = 2.4f;
                    knockback = 0.3f;
                }},
                Items.thorium, new BasicBulletType(8f, 140) {{
                    hitSize = 5;
                    width = 16f;
                    height = 23f;
                    shootEffect = Fx.shootBig;
                    pierceCap = 4;
                    pierceBuilding = true;
                    knockback = 1.4f;
                }},
                Items.pyratite, new BasicBulletType(7f, 120) {{
                    hitSize = 5;
                    width = 16f;
                    height = 21f;
                    frontColor = Pal.lightishOrange;
                    backColor = Pal.lightOrange;
                    status = StatusEffects.burning;
                    splashDamagePierce = true;
                    hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.fireHit);
                    shootEffect = Fx.shootBig;
                    makeFire = true;
                    pierceCap = 4;
                    pierceBuilding = true;
                    knockback = 1.6f;
                    ammoMultiplier = 3;
                    splashDamage = 46f;
                    splashDamageRadius = 24f;
                }},
                you238, new BasicBulletType(10, 280) {{
                    hitSize = 5;
                    width = 16f;
                    height = 23f;
                    shootEffect = Fx.shootBig;
                    pierceCap = 7;
                    pierceBuilding = true;
                    knockback = 1.8f;
                    status = SYBSStatusEffects.fushe;
                    statusDuration = 60;
                    lifetime /= 10.0f / 7;
                }}
        );
        bolang.
                ammo(
                        Liquids.water, new LiquidBulletType(Liquids.water) {{
                            knockback = 0.7f;
                            drag = 0.01f;
                            layer = Layer.bullet - 2f;
                        }},
                        Liquids.slag, new LiquidBulletType(Liquids.slag) {{
                            damage = 4;
                            drag = 0.01f;
                        }},
                        Liquids.cryofluid, new LiquidBulletType(Liquids.cryofluid) {{
                            drag = 0.01f;
                        }},
                        Liquids.oil, new LiquidBulletType(Liquids.oil) {{
                            drag = 0.01f;
                            layer = Layer.bullet - 2f;
                        }},
                        SYBSLiquids.yanjiang, new LiquidBulletType(SYBSLiquids.yanjiang) {{
                            drag = 0.01f;
                            damage = 4f;
                            status = StatusEffects.melting;
                            splashDamage = 1f;
                            splashDamageRadius = 50f;
                        }},
                        SYBSLiquids.nongliusuan, new LiquidBulletType(SYBSLiquids.nongliusuan) {{
                            drag = 0.01f;
                            damage = 4f;
                            splashDamage = 2f;
                            splashDamageRadius = 50f;
                            status = SYBSStatusEffects.zhongdufushi;
                        }},
                        SYBSLiquids.huaxueranji, new LiquidBulletType(SYBSLiquids.huaxueranji) {{
                            drag = 0.01f;
                            damage = 4f;
                            splashDamage = 2f;
                            splashDamageRadius = 50f;
                            status = SYBSStatusEffects.huaxuedianran;
                            statusDuration = 400;
                            incendAmount = 18;
                            incendChance = 1;
                            incendSpread = 8;
                        }}
                );
        haixiao.
                ammo(
                        Liquids.water, new LiquidBulletType(Liquids.water) {{
                            lifetime = 49f;
                            speed = 4f;
                            knockback = 1.7f;
                            puddleSize = 8f;
                            orbSize = 4f;
                            drag = 0.001f;
                            ammoMultiplier = 0.4f;
                            statusDuration = 60f * 4f;
                            damage = 0.2f;
                            layer = Layer.bullet - 2f;
                        }},
                        Liquids.slag, new LiquidBulletType(Liquids.slag) {{
                            lifetime = 49f;
                            speed = 4f;
                            knockback = 1.3f;
                            puddleSize = 8f;
                            orbSize = 4f;
                            damage = 4.75f;
                            drag = 0.001f;
                            ammoMultiplier = 0.4f;
                            statusDuration = 60f * 4f;
                        }},
                        Liquids.cryofluid, new LiquidBulletType(Liquids.cryofluid) {{
                            lifetime = 49f;
                            speed = 4f;
                            knockback = 1.3f;
                            puddleSize = 8f;
                            orbSize = 4f;
                            drag = 0.001f;
                            ammoMultiplier = 0.4f;
                            statusDuration = 60f * 4f;
                            damage = 0.2f;
                        }},
                        Liquids.oil, new LiquidBulletType(Liquids.oil) {{
                            lifetime = 49f;
                            speed = 4f;
                            knockback = 1.3f;
                            puddleSize = 8f;
                            orbSize = 4f;
                            drag = 0.001f;
                            ammoMultiplier = 0.4f;
                            statusDuration = 60f * 4f;
                            damage = 0.2f;
                            layer = Layer.bullet - 2f;
                        }},
                        SYBSLiquids.yanjiang, new LiquidBulletType(SYBSLiquids.yanjiang) {{
                            lifetime = 49f;
                            speed = 4f;
                            knockback = 1.3f;
                            puddleSize = 8f;
                            orbSize = 4f;
                            drag = 0.001f;
                            ammoMultiplier = 0.4f;
                            statusDuration = 60f * 4f;
                            damage = 5f;
                            status = StatusEffects.melting;
                            splashDamage = 3f;
                            splashDamageRadius = 50f;
                            layer = Layer.bullet - 2f;
                        }},
                        SYBSLiquids.nongliusuan, new LiquidBulletType(SYBSLiquids.nongliusuan) {{
                            lifetime = 49f;
                            speed = 4f;
                            knockback = 1.3f;
                            puddleSize = 8f;
                            orbSize = 4f;
                            drag = 0.001f;
                            ammoMultiplier = 0.4f;
                            statusDuration = 60f * 4f;
                            damage = 5f;
                            splashDamage = 4f;
                            splashDamageRadius = 50f;
                            layer = Layer.bullet - 2f;
                            status = SYBSStatusEffects.zhongdufushi;
                        }},
                        SYBSLiquids.huaxueranji, new LiquidBulletType(SYBSLiquids.huaxueranji) {{
                            knockback = 1.3f;
                            drag = 0.001f;
                            damage = 4f;
                            splashDamage = 4f;
                            splashDamageRadius = 50f;
                            status = SYBSStatusEffects.huaxuedianran;
                            statusDuration = 900;
                            incendAmount = 18;
                            incendChance = 1;
                            incendSpread = 8;
                            lifetime = 49;
                            speed = 4;
                        }}
                );
        leiguang.ammo(
                Items.titanium, new ShrapnelBulletType(){{
                    length = 100f ;
                    damage = 66f;
                    ammoMultiplier = 4f;
                    width = 17f;
                    reloadMultiplier = 1.3f;
                }},
                Items.thorium, new ShrapnelBulletType(){{
                    length = 100f ;
                    damage = 105f;
                    ammoMultiplier = 5f;
                    toColor = Pal.thoriumPink;
                    shootEffect = smokeEffect = Fx.thoriumShoot;
                }},
                taihejin, new ShrapnelBulletType(){{
                    length = 100f;
                    damage = 95f;
                    reloadMultiplier = 0.95f ;
                    ammoMultiplier = 4f;
                    toColor = taihejin.color;
                    fragBullets = 3 ;
                    fragBullet = new ShrapnelBulletType(){{
                        length = (range + 10f) / 5 ;
                        damage = 35 ;
                    }};
                    //shootEffect = smokeEffect = Fx.thoriumShoot;
                }}
        );
        qixuan.ammo(
                Items.metaglass, new FlakBulletType(4f, 6){{
                    ammoMultiplier = 2f;
                    shootEffect = Fx.shootSmall;
                    reloadMultiplier = 0.8f;
                    width = 6f;
                    height = 8f;
                    hitEffect = Fx.flakExplosion;
                    splashDamage = 45f;
                    splashDamageRadius = 25f;
                    fragBullet = new BasicBulletType(3f, 12, "bullet"){{
                        width = 5f;
                        height = 12f;
                        shrinkY = 1f;
                        lifetime = 20f;
                        backColor = Pal.gray;
                        frontColor = Color.white;
                        despawnEffect = Fx.none;
                    }};
                    fragBullets = 4;
                    explodeRange = 20f;
                    collidesGround = true;
                }},
                Items.blastCompound, new FlakBulletType(4f, 8){{
                    shootEffect = Fx.shootBig;
                    ammoMultiplier = 5f;
                    splashDamage = 45f;
                    splashDamageRadius = 60f;
                    collidesGround = true;

                    status = StatusEffects.blasted;
                    statusDuration = 60f;
                }},
                Items.plastanium, new FlakBulletType(4f, 8){{
                    ammoMultiplier = 4f;
                    splashDamageRadius = 40f;
                    splashDamage = 37.5f;
                    fragBullet = new BasicBulletType(2.5f, 12, "bullet"){{
                        width = 10f;
                        height = 12f;
                        shrinkY = 1f;
                        lifetime = 15f;
                        backColor = Pal.plastaniumBack;
                        frontColor = Pal.plastaniumFront;
                        despawnEffect = Fx.none;
                    }};
                    fragBullets = 6;
                    hitEffect = Fx.plasticExplosion;
                    frontColor = Pal.plastaniumFront;
                    backColor = Pal.plastaniumBack;
                    shootEffect = Fx.shootBig;
                    collidesGround = true;
                    explodeRange = 20f;
                }},
                Items.surgeAlloy, new FlakBulletType(4.5f, 13){{
                    ammoMultiplier = 5f;
                    splashDamage = 50f * 1.5f;
                    splashDamageRadius = 38f;
                    lightning = 2;
                    lightningLength = 7;
                    shootEffect = Fx.shootBig;
                    collidesGround = true;
                    explodeRange = 20f;
                }},
                taihejin, new FlakBulletType(4f, 10){{
                    ammoMultiplier = 3f;
                    shootEffect = Fx.shootSmall;
                    reloadMultiplier = 1.2f;
                    width = 6f;
                    height = 8f;
                    hitEffect = Fx.flakExplosion;
                    splashDamage = 50f;
                    splashDamageRadius = 32f;
                    fragBullet = new BasicBulletType(3f, 20, "bullet"){{
                        width = 5f;
                        height = 12f;
                        shrinkY = 1f;
                        lifetime = 20f;
                        backColor = taihejin.color;
                        frontColor = Color.white;
                        despawnEffect = Fx.none;
                    }};
                    fragBullets = 5;
                    explodeRange = 20f;
                    collidesGround = true;
                }}
        );
        ezhao.ammo(
                Items.surgeAlloy, new PointBulletType() {{
                    shootEffect = Fx.instShoot;
                    hitEffect = Fx.instHit;
                    smokeEffect = Fx.smokeCloud;
                    trailEffect = Fx.instTrail;
                    despawnEffect = Fx.instBomb;
                    trailSpacing = 20f;
                    damage = 1250;
                    buildingDamageMultiplier = 0.2f;
                    speed = 500;
                    hitShake = 6f;
                    ammoMultiplier = 1f;
                }},
                you235, new PointBulletType() {{
                    shootEffect = SYBSFx.fixedInstShoot;
                    hitColor = you235.color;
                    trailColor = you235.color;
                    hitEffect = SYBSFx.fixedInstHit;
                    smokeEffect = Fx.smokeCloud;
                    trailEffect = SYBSFx.fixedInstTrail;
                    despawnEffect = Fx.instBomb;
                    trailSpacing = 20f;
                    damage = 1950;
                    buildingDamageMultiplier = 0.2f;
                    speed = 500;
                    hitShake = 6f;
                    ammoMultiplier = 1f;
                    status = SYBSStatusEffects.shuaibian;
                    statusDuration = 180;
                }}
        );
        if (Version.build == -1 || ShaYeBuShi.tiaoshi) {
            content.blocks().each(b -> {
                if (b.size >= 4 || b.minfo.mod != null) return;
                if (b instanceof Turret t) {
                    if (b instanceof ContinuousTurret c) {
                        c.shootType.damage *= 2.5f;
                    }
                    if (b instanceof LaserTurret l) {
                        l.shootType.damage *= 2.5f;
                    } else {
                        t.reload /= 2.5f;
                    }
                    Seq<ItemStack> s = new Seq<>(t.requirements);
                    s.each(is -> is.amount *= 1.5f);
                    t.requirements = s.toArray(ItemStack.class);
                }
            });
            youling.ammo(
                    Items.graphite, new BasicBulletType(7.5f, 150) {{
                        hitSize = 4.8f;
                        width = 15f;
                        height = 21f;
                        shootEffect = Fx.shootBig;
                        ammoMultiplier = 4;
                        reloadMultiplier = 2.4f;
                        knockback = 0.3f;
                    }},
                    Items.thorium, new BasicBulletType(8f, 220) {{
                        hitSize = 5;
                        width = 16f;
                        height = 23f;
                        shootEffect = Fx.shootBig;
                        pierceCap = 4;
                        pierceBuilding = true;
                        knockback = 1.4f;
                    }},
                    Items.pyratite, new BasicBulletType(7f, 200) {{
                        hitSize = 5;
                        width = 16f;
                        height = 21f;
                        frontColor = Pal.lightishOrange;
                        backColor = Pal.lightOrange;
                        status = StatusEffects.burning;
                        splashDamagePierce = true;
                        hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.fireHit);
                        shootEffect = Fx.shootBig;
                        makeFire = true;
                        pierceCap = 4;
                        pierceBuilding = true;
                        knockback = 1.6f;
                        ammoMultiplier = 3;
                        splashDamage = 126f;
                        splashDamageRadius = 24f;
                    }},
                    you238, new BasicBulletType(10, 360) {{
                        hitSize = 5;
                        width = 16f;
                        height = 23f;
                        shootEffect = Fx.shootBig;
                        pierceCap = 7;
                        pierceBuilding = true;
                        knockback = 1.8f;
                        status = SYBSStatusEffects.fushe;
                        statusDuration = 60;
                        lifetime /= 10.0f / 7;
                    }}
            );
            youling.requirements = with(Items.copper, 1000, Items.graphite, 400, Items.surgeAlloy, 350, Items.plastanium, 275, Items.thorium, 350);
            ezhao.ammo(
                    Items.surgeAlloy, new PointBulletType() {{
                        shootEffect = Fx.instShoot;
                        hitEffect = Fx.instHit;
                        smokeEffect = Fx.smokeCloud;
                        trailEffect = Fx.instTrail;
                        despawnEffect = Fx.instBomb;
                        trailSpacing = 20f;
                        damage = 1950;
                        buildingDamageMultiplier = 0.2f;
                        speed = 500;
                        hitShake = 6f;
                        ammoMultiplier = 1f;
                    }},
                    you235, new PointBulletType() {{
                        shootEffect = SYBSFx.fixedInstShoot;
                        hitColor = you235.color;
                        trailColor = you235.color;
                        hitEffect = SYBSFx.fixedInstHit;
                        smokeEffect = Fx.smokeCloud;
                        trailEffect = SYBSFx.fixedInstTrail;
                        despawnEffect = Fx.instBomb;
                        trailSpacing = 20f;
                        damage = 2850;
                        buildingDamageMultiplier = 0.2f;
                        speed = 500;
                        hitShake = 6f;
                        ammoMultiplier = 1f;
                        status = SYBSStatusEffects.shuaibian;
                        statusDuration = 180;
                    }}
            );
            ezhao.reload /= 2f;
            ezhao.ammoPerShot *= 2f;
            chuangshang.ammoPerShot *= 2;
            chuangshang.reload /= 2;
            chuangshang.maxAmmo *= 2;
            tianqian.ammo(
                    Items.surgeAlloy, new BasicBulletType(7f, 280) {{
                        sprite = "large-orb";
                        width = 17f;
                        height = 21f;
                        hitSize = 8f;

                        shootEffect = new MultiEffect(Fx.shootTitan, Fx.colorSparkBig, new WaveEffect() {{
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

                        despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect() {{
                            sizeTo = 30f;
                            colorFrom = colorTo = Pal.accent;
                            lifetime = 12f;
                        }});

                        trailRotation = true;
                        trailEffect = Fx.disperseTrail;
                        trailInterval = 3f;

                        intervalBullet = new LightningBulletType() {{
                            damage = 80;
                            collidesAir = false;
                            ammoMultiplier = 1f;
                            lightningColor = Pal.accent;
                            lightningLength = 5;
                            lightningLengthRand = 10;

                            buildingDamageMultiplier = 0.25f;

                            lightningType = new BulletType(0.0001f, 0f) {{
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
                    }},
                    carbide, new BasicBulletType(7f * 45 / 37.5f, 1250) {{
                        sprite = "large-orb";
                        width = 17f;
                        height = 21f;
                        hitSize = 8f;
                        rangeChange = 7.5f * tilesize;
                        reloadMultiplier = 0.85f;
                        shootEffect = new MultiEffect(Fx.shootTitan, Fx.colorSparkBig, new WaveEffect() {{
                            colorFrom = colorTo = carbide.color;
                            lifetime = 12f;
                            sizeTo = 20f;
                            strokeFrom = 3f;
                            strokeTo = 0.3f;
                        }});
                        smokeEffect = Fx.shootSmokeSmite;
                        ammoMultiplier = 1;
                        //pierceCap = 4;
                        pierce = true;
                        pierceBuilding = true;
                        hitColor = backColor = trailColor = carbide.color;
                        frontColor = Color.white;
                        trailWidth = 2.8f;
                        trailLength = 9;
                        hitEffect = Fx.hitBulletColor;
                        buildingDamageMultiplier = 0.3f;

                        despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect() {{
                            sizeTo = 30f;
                            colorFrom = colorTo = carbide.color;
                            lifetime = 12f;
                        }});

                        trailRotation = true;
                        trailEffect = Fx.disperseTrail;
                        trailInterval = 3f;
                        //bulletInterval = 3f;
                    }}
            );
            tianqian.coolantMultiplier *= 2;
            //tianqian.reload /= 2 ;
            tianqian.requirements = with(Items.oxide, 400, Items.surgeAlloy, 800, Items.silicon, 1600, Items.carbide, 1000, Items.phaseFabric, 600);
            tianqian.ammoPerShot *= 2;
            tianqian.maxAmmo *= 2;
            ronghui.range += 7.5f * tilesize;
            ((ContinuousLaserBulletType) ronghui.shootType).length += 7.5f * tilesize;
            ((ContinuousLaserBulletType) ronghui.shootType).damage += 936 + 60 + 180;
            ((ContinuousLaserBulletType) ronghui.shootType).damageInterval = 60;
            ronghui.requirements = with(Items.copper, 1300, Items.lead, 450, Items.graphite, 400, Items.surgeAlloy, 425, Items.silicon, 425);
            moling.heatRequirement = 60;
            moling.maxHeatEfficiency = 3;
            moling.shootType.pierceCap = 2;
            moling.shootType.pierce = true;
            moling.shootType.pierceBuilding = true;
            moling.shootType.damage *= 2;
            moling.shootType.fragBullet = moling.shootType.copy();
            moling.shootType.fragBullets = 5;
            moling.shootType.fragOnHit = false;
            moling.shootType.fragRandomSpread = 0f;
            moling.shootType.fragSpread = 10f;
            moling.requirements = with(Items.carbide, 800, Items.beryllium, 4000, Items.silicon, 1600, Items.graphite, 1600, Items.phaseFabric, 600);
            moling.consPower.usage *= 6.5f;
            //moling.inaccuracy = 180 ;
            jienan.shoot = new ShootSpread(3, 45);
            jienan.maxHeatEfficiency = 3;
            jienan.shootType.homingPower = 0.05f;
            jienan.heatRequirement = 15;
            jienan.consPower.usage *= 3;
            quli.shoot.shots *= 2;
            quli.maxAmmo *= 2;
            quli.ammo(Items.tungsten, new BasicBulletType() {{
                        damage = 80;
                        speed = 8.5f;
                        width = height = 16;
                        shrinkY = 0.3f;
                        backSprite = "large-bomb-back";
                        sprite = "mine-bullet";
                        //velocityRnd = 0.11f;
                        collidesGround = false;
                        collidesTiles = false;
                        shootEffect = Fx.shootBig2;
                        smokeEffect = Fx.shootSmokeDisperse;
                        frontColor = Color.white;
                        backColor = trailColor = hitColor = Color.sky;
                        trailChance = 0.44f;
                        ammoMultiplier = 3f;

                        lifetime = 34f;
                        rotationOffset = 90f;
                        trailRotation = true;
                        trailEffect = Fx.disperseTrail;

                        hitEffect = despawnEffect = Fx.hitBulletColor;
                    }},
                    carbide, new BasicBulletType() {{
                        damage = 125;
                        speed = 8.5f * (38.75f / 41f);
                        width = height = 16;
                        shrinkY = 0.3f;
                        backSprite = "large-bomb-back";
                        sprite = "mine-bullet";
                        //velocityRnd = 0.11f;
                        collidesGround = false;
                        collidesTiles = false;
                        shootEffect = Fx.shootBig2;
                        smokeEffect = Fx.shootSmokeDisperse;
                        frontColor = Color.white;
                        backColor = trailColor = hitColor = carbide.color;
                        trailChance = 0.44f;
                        ammoMultiplier = 1f;
                        pierce = true;
                        pierceCap = 3;
                        rangeChange = 3.25f * tilesize;

                        lifetime = 34f;
                        rotationOffset = 90f;
                        trailRotation = true;
                        trailEffect = Fx.disperseTrail;

                        hitEffect = despawnEffect = Fx.hitBulletColor;
                    }});
            //quli.requirements = with(Items.thorium, 150, Items.oxide, 250, Items.silicon, 300, Items.beryllium, 450) ;
            taitan.ammo(
                    Items.thorium, new ArtilleryBulletType(2.5f * (54.75f / 58.75f), 400, "shell") {{
                        hitEffect = new MultiEffect(Fx.titanExplosion, Fx.titanSmoke);
                        despawnEffect = Fx.none;
                        knockback = 2f;
                        lifetime = 140f;
                        height = 19f;
                        width = 17f;
                        splashDamageRadius = 65f;
                        splashDamage = 400f;
                        scaledSplashDamage = true;
                        backColor = hitColor = trailColor = Color.valueOf("ea8878").lerp(Pal.redLight, 0.5f);
                        frontColor = Color.white;
                        ammoMultiplier = 1f;
                        hitSound = Sounds.titanExplosion;

                        status = StatusEffects.blasted;

                        trailLength = 32;
                        trailWidth = 3.35f;
                        trailSinScl = 2.5f;
                        trailSinMag = 0.5f;
                        trailEffect = Fx.none;
                        despawnShake = 7f;

                        shootEffect = Fx.shootTitan;
                        smokeEffect = Fx.shootSmokeTitan;

                        trailInterp = v -> Math.max(Mathf.slope(v), 0.8f);
                        shrinkX = 0.2f;
                        shrinkY = 0.1f;
                        buildingDamageMultiplier = 0.3f;
                        pierce = true;
                        pierceCap = 5;
                        fragBullets = 3;
                        fragBullet = new BasicBulletType() {{
                            width = 15;
                            height = 15;
                            speed = 9;
                            damage = 50;
                            lifetime = 3 * toSeconds;
                            hitEffect = despawnEffect = Fx.hitBeam;
                            hitShake = despawnShake = 5f;
                            trailColor = thorium.color;
                            trailLength = 8;
                            trailWidth = 4.5f;
//                    homingPower = 0.2f ;
//                    homingRange = 90 * tilesize / 2f ;
//                    homingDelay = 1 ;
                        }};
                    }},
                    blastCompound, new ArtilleryBulletType(2f * (54.75f / 58.75f), 850, "shell") {{
                        hitEffect = new MultiEffect(Fx.titanExplosion, Fx.titanSmoke);
                        despawnEffect = Fx.none;
                        knockback = 2f;
                        lifetime = 140f / 2 * 2.5f;
                        height = 19f;
                        width = 17f;
                        splashDamageRadius = 95f;
                        splashDamage = 850f;
                        scaledSplashDamage = true;
                        backColor = hitColor = trailColor = blastCompound.color.lerp(Pal.redLight, 0.5f);
                        frontColor = Color.white;
                        ammoMultiplier = 1f;
                        hitSound = Sounds.titanExplosion;
                        hitShake = knockback = 5;

                        status = StatusEffects.blasted;

                        trailLength = 32;
                        trailWidth = 3.35f;
                        trailSinScl = 2.5f;
                        trailSinMag = 0.5f;
                        trailEffect = Fx.none;
                        despawnShake = 7f;

                        shootEffect = Fx.shootTitan;
                        smokeEffect = Fx.shootSmokeTitan;

                        trailInterp = v -> Math.max(Mathf.slope(v), 0.8f);
                        shrinkX = 0.2f;
                        shrinkY = 0.1f;
                        buildingDamageMultiplier = 0.3f;
                    }},
                    pyratite, new ArtilleryBulletType(3.3f * (54.75f / 58.75f), 330, "shell") {{
                        hitEffect = new MultiEffect(Fx.titanExplosion, Fx.titanSmoke);
                        despawnEffect = Fx.none;
                        knockback = 2f;
                        lifetime = 140f / 3.3f * 2.5f;
                        height = 19f;
                        width = 17f;
                        splashDamageRadius = 65f;
                        splashDamage = 330f;
                        scaledSplashDamage = true;
                        backColor = hitColor = trailColor = pyratite.color.lerp(Pal.redLight, 0.33f);
                        frontColor = Color.white;
                        ammoMultiplier = 1f;
                        hitSound = Sounds.titanExplosion;

                        status = StatusEffects.burning;

                        trailLength = 32;
                        trailWidth = 3.35f;
                        trailSinScl = 2.5f;
                        trailSinMag = 0.5f;
                        trailEffect = Fx.none;
                        despawnShake = 7f;

                        shootEffect = Fx.shootTitan;
                        smokeEffect = Fx.shootSmokeTitan;

                        trailInterp = v -> Math.max(Mathf.slope(v), 0.8f);
                        shrinkX = 0.2f;
                        shrinkY = 0.1f;
                        buildingDamageMultiplier = 0.3f;
                        incendAmount = 33;
                        incendChance = 1.018f;
                        incendSpread = 33;
                        fragBullets = 3;
                        fragBullet = new LiquidBullet(Liquids.oil) {{
                            speed = 0;
                            puddleSize *= 3.5f;
                        }};
                    }},
                    surgeAlloy, new ArtilleryBulletType(3.5f * (54.75f / 58.75f), 450, "shell") {{
                        hitEffect = new MultiEffect(Fx.titanExplosion, Fx.titanSmoke);
                        despawnEffect = Fx.none;
                        knockback = 2f;
                        lifetime = 140f / 3.5f * 2.5f;
                        height = 19f;
                        width = 17f;
                        splashDamageRadius = 45f;
                        splashDamage = 450f;
                        scaledSplashDamage = true;
                        backColor = hitColor = trailColor = surgeAlloy.color.lerp(Pal.redLight, 0.25f);
                        frontColor = Color.white;
                        ammoMultiplier = 1f;
                        hitSound = Sounds.titanExplosion;

                        status = StatusEffects.shocked;

                        trailLength = 32;
                        trailWidth = 3.35f;
                        trailSinScl = 2.5f;
                        trailSinMag = 0.5f;
                        trailEffect = Fx.none;
                        despawnShake = 7f;

                        shootEffect = Fx.shootTitan;
                        smokeEffect = Fx.shootSmokeTitan;

                        trailInterp = v -> Math.max(Mathf.slope(v), 0.8f);
                        shrinkX = 0.2f;
                        shrinkY = 0.1f;
                        buildingDamageMultiplier = 0.3f;
                        lightning = 14;
                        lightningLength = 14;
                        lightningDamage = 42;
                        reloadMultiplier = 0.8f;
                    }}
            );
            taitan.coolantMultiplier *= 1.5f;
            taitan.range += 6 * tilesize;
            taitan.reload /= 1.5f;
            taitan.requirements = with(Items.tungsten, 350, Items.silicon, 400, Items.thorium, 500);
            guanghui.aimChangeSpeed *= 2f;
            guanghui.rotateSpeed *= 2f;
            guanghui.shootType.damage *= 2f;
            guanghui.requirements = with(Items.silicon, 350, Items.graphite, 300, Items.oxide, 150, Items.carbide, 190);
        }



        lvzuantou = new Drill("lvzuantou"){{
            requirements(Category.production,with(lv,15, copper,10, titanium, 5));
            health = 300 ;
            size = 2 ;
            tier = 5 ;
            drillTime = 300;
            liquidBoostIntensity = 1.5f;
            consumeLiquid(Liquids.water, 0.06f).boost();
        }};
        yijihexin = new CoreBlock("yijihexin"){{
            requirements(Category.effect, with(copper, 4000, Items.lead, 4000, silicon, 5000, Items.thorium, 1000,Items.surgeAlloy,1000,Items.plastanium,1000,SYBSItems.li,4000, lv,4000, taihejin,1000));

            unitType = SYBSUnitTypes.deerta;
            health = 9000;
            itemCapacity = 15000;
            size = 5;
            thrusterLength = 40/4f;
            drawTeamOverlay = true ;
            unitCapModifier = 36;
            researchCostMultiplier = 0.1f;
        }};
        dietai = new TurretDieTai("dietai"){{
            requirements(Category.turret, with(copper, 1800, lv, 1400, Items.surgeAlloy, 500, Items.carbide, 500, taihejin, 750));
            ammo(
                    Items.surgeAlloy, new BasicBulletType(15f, 220){{
                        hitSize = 4.8f;
                        width = 15f;
                        height = 21f;
                        shootEffect = Fx.shootBig;
                        reloadMultiplier = 0.75f;
                        pierceArmor = true ;
                        knockback = 0.5f;
                        lightning = 5 ;
                        lightningLength = 8;
                        lightningDamage = 50 ;
                    }},
                    Items.thorium, new BasicBulletType(15f, 180){{
                        hitSize = 5;
                        width = 16f;
                        height = 23f;
                        shootEffect = Fx.shootBig;
                        pierceArmor = true ;
                        pierceCap = 5;
                        pierceBuilding = true;
                        knockback = 1f;
                    }},
                    Items.pyratite, new BasicBulletType(15f, 150){{
                        hitSize = 5;
                        width = 16f;
                        height = 21f;
                        frontColor = Pal.lightishOrange;
                        backColor = Pal.lightOrange;
                        status = StatusEffects.burning;
                        hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.fireHit);
                        shootEffect = Fx.shootBig;
                        makeFire = true;
                        ammoMultiplier = 6;
                        splashDamagePierce = true ;
                        pierceCap = 3;
                        pierceArmor = true ;
                        pierceBuilding = true;
                        knockback = 0.6f;
                        ammoMultiplier = 3;
                        splashDamage = 150f;
                        splashDamageRadius = 40f;
                        reloadMultiplier = 1.1f ;
                    }},
                    taihejin , new BasicBulletType(15,200){{
                        hitSize = 4.8f;
                        width = 15f;
                        height = 21f;
                        shootEffect = Fx.shootBig;
                        reloadMultiplier = 0.9f;
                        knockback = 0.7f;
                        pierceArmor = true ;
                        fragBullets = 8 ;
                        fragBullet = new BasicBulletType(6,40){{
                            knockback = 0.1f ;
                        }};
                    }}
            );
            reload = 20f;
            recoilTime = reload * 2f;
            coolantMultiplier = 0.5f;
            ammoUseEffect = Fx.casing3;
            range = 520f;
            inaccuracy = 3f;
            recoil = 3f;
            maxAmmo = 12 ;
            shoot = new ShootAlternate(10f);
            shake = 2f;
            size = 5;
            shootCone = 24f;
            shootSound = Sounds.shootBig;
            health = 8000 ;
            armor = 5 ;
            scaledHealth = 160;
            coolant = consumeCoolant(1.2f);

            limitRange();
            initt() ;
        }};
        /*
        dietai1 = dietais.get(0) ;
        dietai2 = dietais.get(1) ;
        dietai3 = dietais.get(2) ;
        dietai4 = dietais.get(3) ;
        dietai5 = dietais.get(4) ;
        dietai6 = dietais.get(5) ;
        dietai7 = dietais.get(6) ;
        dietai8 = dietais.get(7) ;
        dietai9 = dietais.get(8) ;
        dietai10 = dietais.get(9) ;
        */
        danweizhizaochang = new DanWeiZhiZaoChang("danweizhizaochang"){{
            requirements(Category.units, with(taihejin, 1000, Items.surgeAlloy, 450, Items.plastanium, 1000,SYBSItems.li,500, silicon, 1300));
            size = 9;
            health = 8100 ;
        }};
        keyanzhongxin = new KeYanZhongXin("keyanzhongxin"){{
            requirements(Category.crafting, with(SYBSItems.li, 300, Items.surgeAlloy, 250, lv, 500 , Items.phaseFabric,100,Items.plastanium , 400));
            health = 1400 ;
            size = 4 ;
        }};
        shenlingmokuai = new MoKuaiGongChang("shenlingmokuai"){{
            requirements(Category.crafting, with(SYBSItems.li, 300, Items.plastanium, 250, lv, 500 , Items.phaseFabric,100));
            craftEffect = Fx.smeltsmoke;
            outputItems = new ItemStack[]{new ItemStack(shenlingwuzhuang,1),new ItemStack(shenlingdongli,1),new ItemStack(shenlingzhuangjia,1)};
            craftTime = 1200f;
            size = 4;
            itemCapacity = 5000 ;
            hasPower = true;
            health = 2000 ;
            drawer = new DrawMulti(new DrawWeave(), new DrawDefault()) ;
            //consume(new ConsumePayloads(new Seq<PayloadStack>().add(new PayloadStack(UnitTypes.poly,0))));
            consumeItems(with(shendonglantu,1,shenzhuanglantu,1,shenwulantu,1,Items.phaseFabric, 1550, silicon, 2500,Items.surgeAlloy,1400, taihejin,1500));
            consumePower(40f);
        }} ;
        shenlingzhuangpei = new LanTuChongGouChang("shenlingzhuangpei"){{
            requirements(Category.units, with(Items.carbide, 800, Items.thorium, 2000, Items.oxide, 800, Items.tungsten, 1300, silicon, 2500,li,1200,lv,1200,taihejin,1200));
            //regionSuffix = "-dark";
            size = 11;
            itemCapacity = 800 ;
            constructTime = 60f * 60f * 7f ;
            liquidCapacity = 9600 ;
            upgrades.addAll(
                    new UnitType[]{UnitTypes.reign, SYBSUnitTypes.shenqi},
                    new UnitType[]{UnitTypes.toxopid, SYBSUnitTypes.xieling},
                    new UnitType[]{UnitTypes.corvus, SYBSUnitTypes.duoxing},
                    new UnitType[]{UnitTypes.eclipse, SYBSUnitTypes.anye},
                    new UnitType[]{UnitTypes.oct, SYBSUnitTypes.shengling},
                    new UnitType[]{UnitTypes.omura, SYBSUnitTypes.chichao},
                    new UnitType[]{UnitTypes.navanax, SYBSUnitTypes.shuihua}
            );
            for (UnitType[] us : upgrades) {
                //System.out.println(ShaYeBuShi.realGrounded(us[1].create(Team.derelict)) + " " + us[1].localizedName) ;
                if (ShaYeBuShi.isNaval(us[1].create(Team.derelict))) {
                    ShaYeBuShi.applyImm(us[1], new ItemStack(hashihejin, 200)) ;
                }
                if (ShaYeBuShi.realGrounded(us[1].create(Team.derelict))) {
                    ShaYeBuShi.applyImm(us[1], new ItemStack(fangfushe, 200)) ;
                }
            }
            consumeItems(with(shenlinglantu,1, silicon,400,taihejin,400,Items.surgeAlloy,400,Items.phaseFabric,400, shenlingdongli,1,shenlingwuzhuang,1,shenlingzhuangjia,1));
            ConsumeItems c = new ConsumeItems(with(hashihejin, 200)){
                @Override
                public float efficiency(Building b) {
                    return b instanceof LanTuChongGouChangBuild l ? l.payload != null && l.payload.unit.type.naval ? super.efficiency(b) : 1 : 0 ;
                }
                @Override
                public void build(Building b, Table t) {
                    if (b instanceof LanTuChongGouChangBuild l && l.payload != null && l.payload.unit.type.naval) {
                        super.build(b, t);
                    }
                }
            } ;
            ConsumeItems cc = new ConsumeItems(with(fangfushe, 200)){
                @Override
                public float efficiency(Building b) {
                    return b instanceof LanTuChongGouChangBuild l ? l.payload != null && l.payload.unit.isGrounded() && !l.payload.unit.type.naval ? super.efficiency(b) : 1 : 0 ;
                }
                @Override
                public void build(Building b, Table t) {
                    if (b instanceof LanTuChongGouChangBuild l && l.payload != null && l.payload.unit.isGrounded() && !l.payload.unit.type.naval) {
                        super.build(b, t);
                    }
                }
            } ;
            consume(c) ;
            consume(cc) ;
            map.put(fangfushe.id, 400) ;
            map.put(hashihejin.id, 400) ;
            consumePower(30f);
            consume(new ConsumePower(30f,0,false));
            consumeLiquid(Liquids.cryofluid, 200f / 60f);
        }};
        gaojimokuai = new UnitAssemblerModule("gaojimokuai"){{
            requirements(Category.units,BuildVisibility.sandboxOnly, with(Items.carbide, 900, Items.thorium, 1500, Items.oxide, 1000, Items.phaseFabric, 1200, lv,1200,li,1000,taihejin,1400));
            consumePower(20f);
            //regionSuffix = "-dark";
            researchCostMultiplier = 0.75f;
            tier = 2 ;
            size = 7;
        }};
        shuileibushu = new ZhaDanBlock("shuileibushu"){{
            requirements(Category.effect, with( Items.thorium, 100, copper, 400, lv,120,li,100,taihejin,70));
            amount = 5 ;
            size = 3 ;
            can = (t, tt, r) -> t.floor().isLiquid ;
            //buildCostMultiplier = 2 ;
        }};
        zhuoshao = new TurretZhuoShao("zhuoshao"){{
            requirements(Category.turret, with(Items.metaglass, 400, Items.coal, 600, Items.pyratite, 333, taihejin, 100));
            ammo(SYBSLiquids.huaxueranji,new HuoYanBulletType(9f, 50f){
                @Override
                public float continuousDamage(){
                    return (60 / reload) * shoot.shots * damage ;
                }
                {
                ammoMultiplier = 3f;
                hitSize = 7f;
                lifetime = 62f;
                pierce = true;
                pierceBuilding = true;
                //pierceCap = 49;
                statusDuration = 60f * 30;
                //shootEffect = SYBSFx.huoyanShoot;
                //smokeEffect = SYBSFx.huoyanSmoke;
                //hitEffect = SYBSFx.huoyanHit;
                smokeColors = new Color[]{Pal.lightFlame,Pal.darkFlame,Pal.lightishGray} ;
                colors = new Color[]{Color.white, Color.valueOf("fff4ac"), Pal.lightFlame, Pal.darkFlame, Color.gray};
                despawnEffect = Fx.none;
                status = SYBSStatusEffects.huaxuedianran;
                keepVelocity = false;
                hittable = false;
                init();
            }}, SYBSLiquids.yanjiang,new HuoYanBulletType(9f, 22.5f){
                @Override
                public float continuousDamage(){
                    return (60 / reload) * shoot.shots * damage ;
                }
                {
                    ammoMultiplier = 5f;
                    hitSize = 7f;
                    lifetime = 62f;
                    pierce = true;
                    pierceBuilding = true;
                    //pierceCap = 37;
                    statusDuration = 60f * 30;
                    //shootEffect = SYBSFx.huoyanShoot;
                    //smokeEffect = SYBSFx.huoyanSmoke ;
                    //hitEffect = SYBSFx.huoyanHit;
                    smokeColors = new Color[]{Pal.lightFlame,Pal.darkFlame,Pal.lightishGray} ;
                    despawnEffect = Fx.none;
                    colors = new Color[]{Color.white, Color.valueOf("fff4ac"), Pal.lightFlame, Pal.darkFlame, Color.gray};
                    status = StatusEffects.melting;
                    keepVelocity = false;
                    hittable = false;
                }});
            health = 2500 ;
            size = 4;
            reload = 3f;
            //smokeEffect = ammoTypes.get(SYBSLiquids.huaxueranji).smokeEffect;
            //shootEffect = ammoTypes.get(SYBSLiquids.yanjiang).shootEffect;
            coolantMultiplier = 1.5f;
            velocityRnd = 0.1f;
            inaccuracy = 4f;
            recoil = 0f;
            shootSound = Sounds.flame ;
            shootCone = 45f;
            liquidCapacity = 90f;
            range = 285f * 2.25f * 0.92f;
            scaledHealth = 250;
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
        }};
        qunxing = new TurretMoYan("qunxing"){{
            requirements(Category.turret, with(li, 1200,lv,1200, Items.plastanium,800, taihejin, 800));
            alwaysRot = true ;
            rotWhenShooting = false ;
            shootSound = Sounds.shootAltLong;
            shoot = new ShootSpread(6, 60f);
            reload = 2 ;
            size = 5 ;
            health = 9600 ;
            itemCapacity = 40 ;
            liquidCapacity = 400 ;
            outlineIcon = false ;
            //hasShadow = false ;
            drawer = new DrawTurret() {
                @Override
                public void draw(Building build) {
                    Turret turret = (Turret)build.block;
                    TurretBuild tb = (TurretBuild)build;

                    Draw.rect(base, build.x, build.y);
                    Draw.color();

                    Draw.z(Layer.turret - 0.5f);

                    //Drawf.shadow(preview, build.x + tb.recoilOffset.x - turret.elevation, build.y + tb.recoilOffset.y - turret.elevation, tb.drawrot());

                    Draw.z(Layer.turret);

                    drawTurret(turret, tb);
                    drawHeat(turret, tb);

                    if(parts.size > 0){
                        if(outline.found()){
                            //draw outline under everything when parts are involved
                            Draw.z(Layer.turret - 0.01f);
                            Draw.rect(outline, build.x + tb.recoilOffset.x, build.y + tb.recoilOffset.y, tb.drawrot());
                            Draw.z(Layer.turret);
                        }

                        float progress = tb.progress();

                        //TODO no smooth reload
                        var params = DrawPart.params.set(build.warmup(), 1f - progress, 1f - progress, tb.heat, tb.curRecoil, tb.charge, tb.x + tb.recoilOffset.x, tb.y + tb.recoilOffset.y, tb.rotation);

                        for(var part : parts){
                            params.setRecoil(part.recoilIndex >= 0 && tb.curRecoils != null ? tb.curRecoils[part.recoilIndex] : tb.curRecoil);
                            part.draw(params);
                        }
                    }
                }
                {
//                for (int i = 0 ; i < 3 ; i ++) {
//                    int z = i ;
//                    parts.add(new RegionPart("-side-a") {{
//                        rotation = z * 120 ;
//                    }});
//                }
//                for (int i = 0 ; i < 3 ; i ++) {
//                    int z = i ;
//                    parts.add(new RegionPart("-side-b") {{
//                        rotation = 60 + z * 120 ;
//                    }});
//                }
//                parts.add(new RegionPart("-mid")) ;
                for (int i = 1 ; i <= 5 ; i ++) {
                    parts.add(new RegionPart("-circle-" + i) {{
                        outline = false ;
                    }}) ;
                }
                for (int i = 1 ; i <= 3 ; i ++) {
                    for (int z = 1; z <= 5; z ++) {
                        Vec2 v = ShaYeBuShi.circle(120 * i + 360 - 360f / z, z * tilesize, 0, 0);
                        Vec2 v2 = ShaYeBuShi.circle(120 * i + 360 - 360f / z, z * tilesize / 2f, 0, 0);
                        parts.add(new RegionPart("-star-" + i + "-" + z) {{
                            x = v2.x ;
                            y = v2.y ;
                            moveX = v.x;
                            moveY = v.y;
                            outline = false ;
                        }});
                    }
                }
            }} ;
            shootType = new BasicBulletType(7.5f,600){{
                sprite = "large-bomb";
                pierce = true ;
                pierceCap = 5 ;
                height = 30 ;
                width = 30 ;
                lifetime = 180 ;
                pierceBuilding = true ;
                fragBullets = 5 ;
                fragAngle = 72 ;
                buildingDamageMultiplier = 0.1f ;
                fragBullet = new BasicBulletType(3.75f,400){{
                    height = 15 ;
                    width = 15 ;
                    lifetime = 90 ;
                    sprite = "large-bomb";
                    pierce = true ;
                    pierceCap = 5 ;
                    pierceBuilding = true ;
                    buildingDamageMultiplier = 0.1f ;
                }};
                intervalBullets = 5 ;
                intervalAngle = 72 ;
                intervalBullet = new BasicBulletType(3.75f,400){{
                    height = 15 ;
                    width = 15 ;
                    lifetime = 90 ;
                    sprite = "large-bomb";
                    pierce = true ;
                    pierceCap = 5 ;
                    pierceBuilding = true ;
                    buildingDamageMultiplier = 0.1f ;
                }};
            }};
            range = 480 ;
            consume(new ConsumeDianYa(9000 / 60f, 0, false, 3)) ;
            consumeItems(ItemStack.with(Items.oxide,5,Items.pyratite,3));
            consumeLiquid(Liquids.oil,20 / 60f);
        }};
        sangzhong = new TurretSangZhong("sangzhong"){{
            requirements(Category.turret, with(Items.metaglass, 1200,lv,1000,li,1000, Items.phaseFabric,400, taihejin, 400, surgeAlloy, 400));
            size = 6 ;
            health = 12000 ;
            range = 1000 ;
            shootSound = Sounds.plasmaboom;
            outlineColor = Pal.lightishGray;
            //coolant = consumeCoolant(5f);
            shoot = new ShootSpread(12, 30f);
            minWarmup = 0 ;
            cooldownTime = 0 ;
            outlineIcon = false ;
            drawer = new DrawTurret() {{
                for (int i = 1 ; i <= 12 ; i ++) {
                    int z = i ;
                    parts.add(new RegionPart("sangzhong-shoot") {{
                        rotation = z * 30 ;
                        outline = false ;
                    }}) ;
                }
            }} ;
            shootType = new LightningContinuousFlameBulletType(){{
                damage = 900 ;
                length = 1000 ;
                damageInterval = 1 ;
                colors = new Color[]{SYBSPal.yaofeng.a(0.55f), Pal.lightishGray.a(0.7f), SYBSPal.yaofeng.a(0.8f), Pal.lightishGray, Color.white};
                flareColor = Color.valueOf("89e8b6");
                width = 20 ;
                lightColor = hitColor = flareColor;
                pierce = true ;
                knockback = 5 ;
                buildingDamageMultiplier = 0.1f ;
            }};
            consume(new ConsumeDianYa(200, 0, false, 3)) ;
        }};
        jixianchaosu = new OverdriveProjector("jixianchaosu"){{
            requirements(Category.effect, with(taihejin, 1000, Items.titanium, 500, silicon, 750, Items.phaseFabric, 200));
            consume(new ConsumeDianYa(60, 0, false, 2));
            size = 4;
            range = 400f;
            speedBoost = 4;
            useTime = toMinutes * 5 / 6f;
            hasBoost = false;
            itemCapacity = 50 ;
            health = 1000 ;
            //liquidCapacity = 100 ;
            //consumeLiquids(LiquidStack.with(Liquids.oil,50,SYBSLiquids.huoxingdenglizi,25,Liquids.slag,50));
            consumeItems(with(Items.phaseFabric, 25));
        }};
        duogongnengzuantou = new ShuangChongZuanTou("duogongnengzuantou"){{
            requirements(Category.production, with(taihejin, 1000, Items.titanium, 500, silicon, 750, Items.phaseFabric, 200));
            size = 6 ;
            tier = 7 ;
            tierr = 7 ;
            range = 10 ;
            drillTime = 160 ;
            drillTimee = 40 ;
            drawRim = true ;
            rotateSpeed = 8f ;
            hardnessDrillMultiplier = 0f;
            consumeBuilder.add(new ConsumePower(20,0,false));
            consumeLiquid(Liquids.nitrogen,0.1f).boost();
        }};
        taihejinzuantou = new Drill("taihejinzuantou"){{
            requirements(Category.production,with(taihejin,150,li,100,lv,100, silicon,100));
            //health = 300 ;
            size = 5 ;
            tier = 6 ;
            drillTime = 260;
            liquidBoostIntensity = 2f;
            rotateSpeed = 7f ;
            //consumeLiquid(Liquids.water, 0.06f).boost();
            consumePower(5);
            consumeLiquid(Liquids.water,0.06f) ;
            consumeLiquid(Liquids.cryofluid, 0.06f).boost();
        }};
        shenlingzhenghe = new WallPowerTurret("shenlingzhenghe"){{
            requirements(Category.defense, with(shenlingdongli, 1, shenlingwuzhuang, 1, shenlingzhuangjia, 1, shenlinglantu, 1));
            health = 72000 ;
            size = 2 ;
            shootY = 22f;
            reload = 4.5f;
            shake = 2f;
            rotateSpeed = 1f;
            shootSound = Sounds.malignShoot;
            absorbLasers = true;
            //rotate = true;
            lightningChance = 0.25f;
            lightningDamage = 100f;
            shieldHealth = 4500 ;
            outputsPower = false;
            hasPower = true;
            consumesPower = true;
            conductivePower = true;
            chanceDeflect = 20f;
            armor = 24f;
            buildCostMultiplier = 300f;
            consumePower(5.5f) ;
            ((DrawWallTurret)drawer).basePrefix = "shenlingchenghe-" ;
            Color haloColor = Pal.sapBullet ;
            var circleColor = haloColor;
            shootType = new FlakBulletType(8f, 100f){
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
                    lifetime = 45f;
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
            range = shootType.lifetime * shootType.speed ;
            var circleProgress = DrawPart.PartProgress.warmup.delay(0.9f);
            float circleY = 25f, circleRad = 11f, circleRotSpeed = 3.5f, circleStroke = 1.6f;
            float haloY = -15f, haloRotSpeed = 1.5f;
            ((DrawWallTurret)drawer).parts.addAll(
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
        }};
        feichuanchang.plans.add(new UnitAssembler.AssemblerUnitPlan(SYBSUnitTypes.moneng, 60 * 60 * 6.5f, PayloadStack.list(UnitTypes.obviate, 8, shenlingzhenghe, 1, Blocks.shieldedWall, 8))) ;
        zengyuanbushu = new ZengYuanBuShuBlock("zengyuanbushu"){{
            requirements(Category.effect, with(li, 400, lv, 400, taihejin, 400,shenlinglantu, 0));
            size = 3 ;
        }};
        tiaojieshijian = new TiaoJieShiJianBlock("tiaojieshijian"){{
            requirements(Category.effect, with(silicon, 1, silicon, 1, silicon, 4, silicon, 5, silicon, 1, silicon, 4, silicon, 1, silicon, 9, silicon, 1, silicon, 9, silicon, 8, silicon, 1, silicon, 0));
        }};
        tiaobo = new TiaoBoBlock("tiaobo"){{
            requirements(Category.effect, with(silicon, 114, metaglass, 514, silicon, 1919, taihejin, 810));
        }};
        for (int i = 0 ; i < 17 ; i ++){
            int f = i + 1;
            shikuais[i] = new ShiKuaiBlock("shikuai"+f){{
//                if (f == 3) {
//                    requirements(Category.effect, with());
//                }
                size = f ;
                armor = Float.POSITIVE_INFINITY ;
            }};
        }
        shefa = new PowerTurret("shefa"){{
           requirements(Category.turret, SYBSBuildVisibility.tiaoshiOnly, with(copper, 1500, lv, 1500, surgeAlloy, 400, metaglass, 800, taihejin, 500, silicon, 1000));
           size = 4 ;
           health = 8000 ;
           reload = 450 ;
           heatRequirement = 45 ;
           maxHeatEfficiency = 2.5f ;
           float brange = range = 600 ;
           shootType = new ShiHuaBullet(){{
               hitColor = Pal.items ;
               shootEffect = Fx.instShoot;
               hitEffect = Fx.instHit;
               smokeEffect = Fx.smokeCloud;
               //trailEffect = Fx.instTrail;
               trailEffect = SYBSFx.jiange;
               despawnEffect = Fx.instBomb;
               trailSpacing = 20f;
               damage = 1350;
               //buildingDamageMultiplier = 0.2f;
               speed = brange;
               hitShake = 12f;
               //ammoMultiplier = 1f;
           }};
           shootSound = Sounds.plasmadrop ;
           unitSort = UnitSorts.strongest ;
           //consumePower(70);
           consume(new ConsumeDianYa(7, 0, false, 1));
        }};
        diyadianjiedian = new DianYaNode("diyadianjiedian"){{
            requirements(Category.power, with(li, 3, lead, 5, silicon, 3));
            dianya = 1 ;
            maxNodes = 15 ;
            size = 2 ;
            laserRange = 15f;
        }};
        zhongyadianjiedian = new DianYaNode("zhongyadianjiedian"){{
            requirements(Category.power, with(li, 3, lead, 5, silicon, 3));
            dianya = 2 ;
            maxNodes = 15 ;
            size = 2 ;
            laserRange = 50f;
            hasPower = true ;
            consumesPower = true ;
            consume(new ConsumeDianYa(0.75f, 0, false, 2){
                @Override
                public float requestedPower(Building b) {
                    return usage ;
                }
            }) ;
        }};
        gaoyadianjiedian = new DianYaNode("gaoyadianjiedian"){{
            requirements(Category.power, with(li, 3, lead, 5, silicon, 3));
            dianya = 3 ;
            maxNodes = 15 ;
            size = 2 ;
            laserRange = 100f;
            hasPower = true ;
            consumesPower = true ;
            consume(new ConsumeDianYa(1.5f, 0, false, 3){
                @Override
                public float requestedPower(Building b) {
                    return usage ;
                }
            }) ;
        }};
        diyadianyuan = new DianYaSoure("diyadianyuan"){{
            requirements(Category.power, with());
            dianya = 1 ;
        }};
        zhongyadianyuan = new DianYaSoure("zhongyadianyuan"){{
            requirements(Category.power, with());
            dianya = 2 ;
        }};
        gaoyadianyuan = new DianYaSoure("gaoyadianyuan"){{
            requirements(Category.power, with());
            dianya = 3 ;
        }};
        taihejinchuansongdai = new Conveyor("taihejinchuansongdai"){{
            requirements(Category.distribution, with(Items.copper, 3, li, 3, taihejin, 3));
            health = 80;
            speed = 0.12f;
            displayedSpeed = 16.5f;
        }};
        taihejinjiaochaqi = new Junction("taihejinjiaochaqi"){{
            requirements(Category.distribution, with(copper, 6, lead, 6, taihejin, 6));
            speed = 26 * 1.5f;
            capacity = 24;
            health = 90;
            buildCostMultiplier = 6f;
        }};
        taihejinluyouqi = new DuctRouter("taihejinluyouqi"){{
            requirements(Category.distribution, with(Items.copper, 9, lead, 4, li, 4, taihejin, 10));
            speed = 3.5f ;
            buildCostMultiplier = 4f;
            health = 60 ;
        }};
        taihejinqiao = new BufferedItemBridge("taihejinqiao"){{
            requirements(Category.distribution, with(copper, 12, lead, 12, li, 12, taihejin, 12));
            fadeIn = moveArrows = true;
            range = 9;
            speed = 111f;
            arrowSpacing = 6f;
            bufferCapacity = 21;
            health = 60 ;
        }};
//        leikuangshi = new FangSheXingKuang("ore-lei",SYBSItems.lei){{
//            //BasicGenerator.ore(Blocks.oreCopper, Blocks.ferricStone, 5f, 0.8f );
//            enableDrawStatus = true ;
//            oreDefault = true;
//            oreThreshold = 0.89f;
//            oreScale = 26f;
//            mapColor = lei.color ;
//        }};
        youkuangshi = new FangSheXingKuang("ore-you",you){{
            enableDrawStatus = true ;
            oreDefault = true;
            oreThreshold = 0.888f;
            oreScale = 25.77619f;
            mapColor = you.color ;
            effect = SYBSStatusEffects.fushe ;
        }};
        youlixinji = new GenericCrafter("youlixinji"){{
           requirements(Category.crafting, with(copper, 500, taihejin, 260, graphite, 380, silicon, 400));
           size = 3 ;
           health = 3 * 3 * 40 * 6;
           itemCapacity = 500 ;
           outputItems = with(you235, 10, you238, 10) ;
           craftTime = 300 ;
           craftEffect = Fx.producesmoke ;
           //updateEffect = SYBSFx.shengchan4 ;
           drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawPlasma(), new DrawDefault());
           consumeItem(you, 141) ;
           consume(new ConsumeDianYa(7, 0, false, 2)) ;
        }};
        fangfushelichang = new FangFuSheLiChang("fangfushelichang"){{
           requirements(Category.crafting, with(lead, 300, silicon, 350, taihejin, 200)) ;
           size = 4 ;
           health = 4 * 4 * 160 ;
           radius *= 2 ;
           consume(new ConsumeDianYa(12, 0, false, 2)) ;
        }};
        shandian = new ItemTurret("shandian2") {{
            health = 1200 ;
            armor = 2 ;
            size = 3 ;
            shake = 2 ;
            requirements(Category.turret, with(li, 100, taihejin, 200, Items.plastanium, 150, graphite, 150)) ;
            BulletType b = new RailBulletType(){{
                length = 37.5f * tilesize ;
                damage = 105;
                hitColor = Color.valueOf("feb380");
                hitEffect = endEffect = Fx.hitBulletColor;
                pierceDamageFactor = 0f;
                pierce = false ;
                //pierceCap = 4 ;
                //pierceArmor = true ;
                smokeEffect = Fx.colorSpark;
                fragBullets = 3 ;
                fragBullet = new BasicBulletType() {{
                    width = 15 ;
                    height = 15 ;
                    speed = 9 ;
                    damage = 50 ;
                    lifetime = 1 * toSeconds ;
                    hitEffect = despawnEffect = Fx.hitBeam ;
                    hitShake = despawnShake = 5f ;
                    backColor = trailColor = thorium.color ;
                    trailLength = 8 ;
                    trailWidth = 4.5f ;
//                    homingPower = 0.2f ;
//                    homingRange = 90 * tilesize / 2f ;
//                    homingDelay = 1 ;
                }};
                endEffect = new Effect(14f, e -> {
                    color(e.color);
                    Drawf.tri(e.x, e.y, e.fout() * 1.5f, 5f, e.rotation);
                });

                shootEffect = new Effect(10, e -> {
                    color(e.color);
                    float w = 1.2f + 7 * e.fout();

                    Drawf.tri(e.x, e.y, w, 30f * e.fout(), e.rotation);
                    color(e.color);

                    for(int i : Mathf.signs){
                        Drawf.tri(e.x, e.y, w * 0.9f, 18f * e.fout(), e.rotation + i * 90f);
                    }

                    Drawf.tri(e.x, e.y, w, 4f * e.fout(), e.rotation + 180f);
                });

                lineEffect = new Effect(20f, e -> {
                    if(!(e.data instanceof Vec2 v)) return;

                    color(e.color);
                    stroke(e.fout() * 0.9f + 0.6f);

                    Fx.rand.setSeed(e.id);
                    for(int i = 0; i < 7; i++){
                        Fx.v.trns(e.rotation, Fx.rand.random(8f, v.dst(e.x, e.y) - 8f));
                        Lines.lineAngleCenter(e.x + Fx.v.x, e.y + Fx.v.y, e.rotation + e.finpow(), e.foutpowdown() * 20f * Fx.rand.random(0.5f, 1f) + 0.3f);
                    }

                    e.scaled(14f, b -> {
                        stroke(b.fout() * 1.5f);
                        color(e.color);
                        Lines.line(e.x, e.y, v.x, v.y);
                    });
                });
            }};
            shootSound = Sounds.bolt;
//            shoot = new ShootAlternate(){{
//                barrels = 4 ;
//                spread = 4 ;
//            }};
            reload = 12 ;
            recoil = qixuan.recoil ;
            recoilTime = 10f ;
            inaccuracy = 7.5f;
            range = 37.5f * tilesize ;
            coolant = consumeCoolant(0.3f);
            BulletType b1 = b.copy() ;
            b1.hitColor = thorium.color ;
            b1.pierce = true ;
            BulletType b2 = taitan.ammoTypes.get(thorium).copy() ;
            b2.speed *= 3 ;
            b2.splashDamage = 60 ;
            b2.damage = 80 ;
            b2.pierceArmor = true ;
            b2.trailColor = b2.hitColor = ((BasicBulletType) b2).backColor = silicon.color ;
            b2.homingPower = 0.15f ;
            b2.homingRange = range / 2f ;
            b2.homingDelay = 15f ;
            b2.pierce = false ;
            b2.rangeChange = 3.5f * tilesize ;
            b2.reloadMultiplier = 1.15f ;
            b2.fragBullet  = new ShrapnelBulletType(){{
                //trailWidth = 20 ;
                //speed = 10 ;
                length = 32 ;
                lifetime = 32 ;
                toColor = Pal.heal ;
                damage = 70f;
                width = 4f ;
                //buildingDamageMultiplier = 0.3f;
                hitColor = silicon.color ;
                //healPercent = 10 ;
                shake = 1 ;
            }};
            //b2.fragBullet.lifetime /= 3 ;
            //b2.fragBullet.fragBullet.speed /= 1.5f ;
            //b2.fragBullet.fragBullet.trailColor = b2.fragBullet.fragBullet.hitColor = ((BasicBulletType) b2.fragBullet.fragBullet).backColor = silicon.color ;
            b2.fragBullets = 1 ;
            BulletType b3 = b.copy() ;
            //b3.pierceCap = 4 ;
            b3.damage = 0 ;
            b3.splashDamage = 105 ;
            b3.splashDamageRadius = 5 * tilesize ;
            b3.pierceArmor = true ;
            b3.pierce = true ;
            b3.fragBullets = 5 ;
            b3.fragBullet = b.fragBullet.copy() ;
            b3.fragBullet.damage = 70 ;
            b3.fragBullet.homingPower = 0.2f ;
            b3.fragBullet.homingRange = 90 * tilesize / 2f ;
            b3.fragBullet.homingDelay = 1 ;
            b3.ammoMultiplier = 2 ;
            b3.reloadMultiplier = 1.1f ;
            b3.hitColor = ruangang.color ;
            b3.status = StatusEffects.slow ;
            b3.statusDuration = 3 * toSeconds ;
            b3.fragBullet.trailColor = ((BasicBulletType) b3.fragBullet).backColor = ruangang.color ;
            BulletType b4 = b3.copy() ;
            b4.pierceArmor = true ;
            b4.pierce = true ;
            b4.splashDamage = 200 ;
            b4.splashDamageRadius = 7.5f * tilesize ;
            b4.status = SYBSStatusEffects.qiangxiaodianji ;
            b4.fragBullet = b.fragBullet.copy() ;
            b4.fragBullet.damage = 100 ;
            b4.fragBullet.pierceArmor = true ;
            b4.fragBullet.splashDamage = 80 ;
            b4.fragBullet.splashDamageRadius = 4 * tilesize ;
            b4.fragBullet.lightning = 3 ;
            b4.fragBullet.lightningDamage = 20 ;
            b4.fragBullet.trailColor = ((BasicBulletType) b4.fragBullet).backColor = yijihejin.color ;
            b4.hitColor = yijihejin.color ;
            ammo(thorium, b1, silicon, b2, ruangang, b3, yijihejin, b4) ;
        }};
        dianci = new ItemTurret("dianci") {{
            requirements(Category.turret, with(sanjihejin, 350, taihejin, 1000, xiangjiao, 1100, plastanium, 300, silicon, 150)) ;
            health = 2100 ;
            //armor = 10 ;
            size = 4 ;
            shake = 3 ;
            range = 90f * tilesize ;
            reload = 60 ;
            recoil = qixuan.recoil * 5 ;
            recoilTime = 50f ;
            shootSound = Sounds.laser ;
            coolant = consumeCoolant(0.4f);
            shootEffect = new MultiEffect(Fx.shootTitan, Fx.colorSparkBig, new WaveEffect(){{
                colorFrom = colorTo = Pal.techBlue;
                lifetime = 12f;
                sizeTo = 20f;
                strokeFrom = 3f;
                strokeTo = 0.3f;
            }});
            consume(new ConsumeDianYa(50, 0, false, 2)) ;
            consume(new ConsumeCiChang(10)) ;
            BulletType b = new ArtilleryBulletType(10, 800) {{
                damage = 800 ;
                width = 30 ;
                height = 30 ;
                pierceArmor = true ;
                fragBullets = 4 ;
                fragBullet = new BasicBulletType(9, 600) {{
                   width = 15 ;
                   height = 15 ;
                   damage = 600 ;
                   lifetime = 200 ;
                   splashDamage = 500 ;
                   splashDamageRadius = 7 * tilesize ;
                   hitEffect = despawnEffect = Fx.hitBeam ;
                   hitShake = despawnShake = 5f ;
                   trailColor = Pal.redderDust ;
                   trailLength = 8 ;
                   trailWidth = 4.5f ;
                   homingPower = 0.2f ;
                   homingRange = 90 * tilesize / 2f ;
                   homingDelay = 1 ;
                }};
                hitEffect = despawnEffect = Fx.titanExplosion ;
                hitShake = despawnShake = 8f ;
                trailLength = 15 ;
                trailWidth = 9f ;
                trailColor = Pal.techBlue ;
                shootEffect = new MultiEffect(Fx.shootTitan, Fx.colorSparkBig, new WaveEffect(){{
                    colorFrom = colorTo = Pal.techBlue;
                    lifetime = 12f;
                    sizeTo = 20f;
                    strokeFrom = 3f;
                    strokeTo = 0.3f;
                }});

            }};
            BulletType b1 = b.copy() ;
            b1.damage = 0 ;
            b1.pierceArmor = false ;
            b1.splashDamage = 2000 ;
            b1.splashDamageRadius = 15 * tilesize ;
            b1.fragBullets = 3 ;
            b1.fragBullet = b.fragBullet.copy() ;
            b1.fragBullet.damage = 0 ;
            b1.fragBullet.splashDamage = 800 ;
            b1.fragBullet.splashDamageRadius = 10 * tilesize ;
//            b1.fragBullet.lightning = 1 ;
//            b1.fragBullet.lightningDamage = 1000 ;
//            b1.ammoMultiplier = 0.75f ;
            BulletType b2 = b.copy() ;
            b2.damage = 3000 ;
            b2.fragBullets = 15 ;
            b2.fragBullet = b.fragBullet.copy() ;
            b2.fragBullet.damage = 1250 ;
            b2.fragBullet.splashDamage = 0 ;
            b2.fragBullet.splashDamageRadius = 0 ;
            b2.reloadMultiplier = 1.2f ;
            b2.rangeChange = -7 * tilesize ;
            BulletType b3 = b.copy() ;
            b3.damage = 3500 ;
            b3.splashDamage = 3000 ;
            b3.splashDamageRadius = 20 * tilesize ;
            b3.fragBullets = 20 ;
            b3.fragBullet = b.fragBullet.copy() ;
            b3.fragBullet.damage = 0 ;
            b3.fragBullet.splashDamage = 1500 ;
            b3.fragBullet.splashDamageRadius = 5 * tilesize ;
            b3.ammoMultiplier *= 1.15f ;
            b3.reloadMultiplier = 0.9f ;
            b3.rangeChange = 7.5f * tilesize ;
            ammo(yijihejin, b1, erjihejin, b2, sanjihejin, b3) ;
            limitRange();
        }};
        bianyaqi = new PowerNode("bianyaqi"){
            @Override
            protected void getPotentialLinks(Tile tile, Team team, Cons<Building> others){
                if(!autolink) return;

                Boolf<Building> valid = other -> other != null && other.tile() != tile && other.block.connectedPower && other.power != null &&
                        (other.block.outputsPower || other.block.consumesPower || other.block instanceof PowerNode || other.block instanceof DianYaNode) &&
                        overlaps(tile.x * tilesize + offset, tile.y * tilesize + offset, other.tile(), laserRange * tilesize) && other.team == team &&
                        !graphs.contains(other.power.graph) &&
                        !PowerNode.insulated(tile, other.tile) &&
                        !(other instanceof PowerNodeBuild obuild && obuild.power.links.size >= ((PowerNode)obuild.block).maxNodes) &&
                        !(other instanceof DianYaNode.DianYaNodeBuild oobuild && oobuild.power.links.size >= ((DianYaNode)oobuild.block).maxNodes) &&
                        !Structs.contains(Edges.getEdges(size), p -> { //do not link to adjacent buildings
                            var t = world.tile(tile.x + p.x, tile.y + p.y);
                            return t != null && t.build == other;
                        }) && (other instanceof DianYaNode.DianYaNodeBuild || other instanceof PowerNodeBuild);

                tempBuilds.clear();
                graphs.clear();

                //add conducting graphs to prevent double link
                for(var p : Edges.getEdges(size)){
                    Tile other = tile.nearby(p);
                    if(other != null && other.team() == team && other.build != null && other.build.power != null){
                        graphs.add(other.build.power.graph);
                    }
                }

                if(tile.build != null && tile.build.power != null){
                    graphs.add(tile.build.power.graph);
                }

                var worldRange = laserRange * tilesize;
                var tree = team.data().buildingTree;
                if(tree != null){
                    tree.intersect(tile.worldx() - worldRange, tile.worldy() - worldRange, worldRange * 2, worldRange * 2, build -> {
                        if(valid.get(build) && !tempBuilds.contains(build)){
                            tempBuilds.add(build);
                        }
                    });
                }

                tempBuilds.sort((a, b) -> {
                    int type = -Boolean.compare(a.block instanceof PowerNode, b.block instanceof PowerNode);
                    if(type != 0) return type;
                    return Float.compare(a.dst2(tile), b.dst2(tile));
                });

                returnInt = 0;

                tempBuilds.each(valid, t -> {
                    if(returnInt ++ < maxNodes){
                        graphs.add(t.power.graph);
                        others.get(t);
                    }
                });
            }
            {
           requirements(Category.power, with(phaseFabric, 10, silicon, 50, taihejin, 30, surgeAlloy, 10)) ;
           size = 2 ;
           maxNodes = 2 ;
           hasPower = true ;
           consumesPower = true ;
           consume(new ConsumePower(10, 0, false){
               @Override
               public float requestedPower(Building b) {
                   return usage ;
               }
           }) ;
            config(Integer.class, (entity, value) -> {
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
                }else if(linkValid(entity, other) && valid && power.links.size < maxNodes && (other.block instanceof PowerNode || other.block instanceof DianYaNode)){

                    power.links.addUnique(other.pos());

                    if(other.team == entity.team){
                        other.power.links.addUnique(entity.pos());
                    }

                    power.graph.addGraph(other.power.graph);
                }
            });

            config(Point2[].class, (tile, value) -> {
                IntSeq old = new IntSeq(tile.power.links);

                //clear old
                for(int i = 0; i < old.size; i++){
                    configurations.get(Integer.class).get(tile, old.get(i));
                }

                //set new
                for(Point2 pp : value){
                    configurations.get(Integer.class).get(tile, Point2.pack(pp.x + tile.tileX(), pp.y + tile.tileY()));
                }
            });
        }};
        fangfushecailiaofuheji = new GenericCrafter("fangfushecailiaofuheji"){{
            requirements(Category.crafting, with(copper, 500, taihejin, 260, graphite, 380, silicon, 400));
            size = 3 ;
            health = 3 * 3 * 40 * 6;
            itemCapacity = 40 ;
            outputItems = with(fangfushe, 20) ;
            craftTime = 250 ;
            //craftEffect = SYBSFx.huoyanSmoke ;
            updateEffect = SYBSFx.shengchan1 ;
            consumeItems(with(surgeAlloy, 10, plastanium, 10, phaseFabric, 5)) ;
            consume(new ConsumeDianYa(6, 0, false, 1)) ;
            consume(new ConsumeCiChang(20)) ;
        }};
        fangfushibeng = new Pump("fangfushibeng"){{
            requirements(Category.liquid, with(lv, 70, Items.metaglass, 50, Items.silicon, 20, taihejin, 35, hashihejin, 20, fangfushe, 20));
            pumpAmount = 0.2f;
            consumePower(0.3f);
            liquidCapacity = 30f;
            hasPower = true;
            size = 2;
            health = 960 ;
        }};
        hejinduanzaoqi = new GenericCrafter("hejinduanzaoqi"){{
            requirements(Category.crafting, with(taihejin, 150, metaglass, 100, lead, 120, silicon, 400, surgeAlloy, 100));
            size = 3 ;
            health = 1100;
            itemCapacity = 20 ;
            outputItems = with(hashihejin, 2, scrap, 2) ;
            craftTime = 1 / 2.4f * toSeconds ;
            craftEffect = SYBSFx.shengchan2 ;
            updateEffect = SYBSFx.shengchan2 ;
            consumeItems(with(surgeAlloy, 2, lead, 2)) ;
            consume(new ConsumeDianYa(6.5f, 0, false, 1)) ;
            drawer = new DrawMulti(new DrawArcSmelt(), new DrawDefault(), new DrawFlame(Color.valueOf("ffef99")));
            //consume(new ConsumeCiChang(20)) ;
        }};
        fangfushichuansongdai = new Conveyor("fangfushichuansongdai"){{
            requirements(Category.distribution, with(taihejin, 3, li, 3, hashihejin, 3, fangfushe, 3));
            health = 80;
            speed = 0.2f;
            displayedSpeed = 16.5f;
        }};
        fangfushidaoguan = new Conduit("fangfushidaoguan"){{
            requirements(Category.liquid, with(taihejin, 3, metaglass, 3, hashihejin, 3, fangfushe, 3));
            health = 80;
            liquidCapacity = 32 ;
            liquidPressure = 1.2f ;
        }};
        taihejincangku = new StorageBlock("taihejincangku"){{
            requirements(Category.effect, with(taihejin, 500, Items.thorium, 300));
            size = 4;
            itemCapacity = 3500;
            scaledHealth = 240;
            coreMerge = true;
        }};
        taihejinzhuangxieqi = new Unloader("taihejinzhuangxieqi"){{
            requirements(Category.effect, with(taihejin, 40, Items.silicon, 70));
            speed = 60f / 30f;
            group = BlockGroup.transportation;
        }};
        jvxingzaihechuansongdai = new PayloadConveyor("jvxingzaihechuansongdai"){{
            size = 9 ;
            payloadLimit = 9f;
            requirements(Category.units, with(Items.graphite, 90, Items.copper, 90, taihejin, 90, hashihejin, 30));
            canOverdrive = false;
        }};
        jingkong = new TurretJingKong("jingkong") {{
           requirements(Category.turret, with(graphite, 150, taihejin, 150, xiangjiao, 175, ruangang, 300, surgeAlloy, 100));
           size = 2 ;
           health = 980 ;
           armor = 2 ;
           reload = 3 ;
           range = 35 * tilesize ;
           inaccuracy = 5 ;
           shootEffect = youling.shootEffect ;
           shootSound = qixuan.shootSound ;
           targetGround = false ;
           shake = 0.5f ;
           recoil = 2f;
           coolant = consumeCoolant(0.25f);
           shoot = new ShootAlternate(6f) ;
           BulletType b = new FlakBulletType(4f / 220 * 280 * 6, 6) {{
              damage = 10 ;
              lifetime = 10 ;
              splashDamage = 40 ;
              splashDamageRadius = 1.25f * tilesize ;
              fragBullets = 10 ;
              hitShake = despawnShake = 0.5f ;
              width = 2f;
              height = 8f;
              hitEffect = Fx.flakExplosion;
              homingPower = 0.05f ;
              reloadMultiplier = 1.25f ;
              fragBullet = new BasicBulletType(3f / 220 * 280 * 6, 6) {{
                  damage = 20 ;
                  hitShake = despawnShake = 1 ;
                  width = 2f;
                  height = 12f;
                  collidesGround = false ;
                  backColor = Pal.gray;
                  frontColor = Color.white;
                  homingPower = 0.05f ;
                  lifetime /= 6 ;
              }};
           }};
            BulletType b2 = b.copy() ;
            b2.fragBullet = b.fragBullet.copy() ;
            b2.damage = 60 ;
            b2.fragBullets = 15 ;
            b2.fragBullet.damage = 25 ;
            b2.splashDamageRadius = 0 ;
            b2.splashDamage = 0 ;
            b2.reloadMultiplier = 0.95f ;
            BulletType b3 = b.copy() ;
            b3.fragBullet = b.fragBullet.copy() ;
            b3.damage = 18 ;
            b3.splashDamageRadius = 16 ;
            b3.splashDamage = 60 ;
            b3.status = StatusEffects.shocked ;
            b3.statusDuration = 600 ;
            b3.fragBullet.status = StatusEffects.shocked ;
            b3.fragBullet.statusDuration = 600 ;
            b3.fragBullet.damage = 35 ;
            b3.fragBullets = 8 ;
            b3.reloadMultiplier = 0.8f ;
            b3.lightning = 8 ;
            b3.lightningDamage = 20 ;
            b3.lightningLength = 8 ;
            ((BasicBulletType)b3).backColor = surgeAlloy.color ;
            ((BasicBulletType)b3.fragBullet).backColor = surgeAlloy.color ;
            ((FlakBulletType)b3).explodeRange = 20f ;
            /*
            BulletType b4 = b2.copy() ;
            b4.damage = 220 ;
            b4.fragBullets = 1 ;
            b4.pierceArmor = true ;
            b4.homingPower = 0 ;
            b4.reloadMultiplier = 0.05f ;
            ((BasicBulletType)b4).width = ((BasicBulletType)b4).height = 16 ;
            ((BasicBulletType)b4).backColor = phaseFabric.color ;
            b4.fragBullet = new TeSiLaBullet();
            ((BasicBulletType)b4.fragBullet).width = ((BasicBulletType)b4.fragBullet).height = 16 ;
            ((BasicBulletType)b4.fragBullet).backColor = ((BasicBulletType)b4.fragBullet).frontColor = phaseFabric.color ;
            */
            TeSiLaDianQiuBullet b4 = new TeSiLaDianQiuBullet() {{
                damage = 220 ;
                pierceArmor = true ;
                pierce = true ;
                lifetime = 60 ;
                hitShake = despawnShake = 0.5f ;
                width = 16f;
                height = 16f;
                frontColor = backColor = phaseFabric.color ;
                hitEffect = Fx.flakExplosion;
                reloadMultiplier = 1 - 0.999f ;
                speed = 4f / 220 * 280 ;
                TeSiLaDianQiuBullet b5 = new TeSiLaDianQiuBullet() {{
                   damage = 60 * 2.5f ;
                   hitShake = despawnShake = 1 ;
                   width = 16f;
                   height = 16f;
                   backColor = frontColor = phaseFabric.color ;
                   speed = 3f / 220 * 280 ;
                }};
                fragBullets = 4 ;
                fragBullet = b5 ;
                spawnBullets.add(b5) ;
                /*
                ShootPattern.BulletHandler handler1 = (x, y, rotation, delay, move) -> {
                    ShootPattern.BulletHandler handler2 = (x2, y2, rot2, delay2, mover) -> {
                        ShootPattern.BulletHandler handler3 = (xOffset, yOffset, angle, delay3, mover3) -> {
                            if(delay3 > 0f){
                                Time.run(delay3, () -> bullet(b5, xOffset, yOffset, angle, mover3));
                            }else{
                                bullet(b5, xOffset, yOffset, angle, mover3);
                            }
                        } ;
                        handler3.shoot(x + x2, y + y2, rotation + rot2, delay + delay2, move == null && mover == null ? null : b -> {
                            if(move != null) move.move(b);
                            if(mover != null) mover.move(b);
                        });
                    } ;
                    for(int i = 0; i < 10; i++){
                        handler2.shoot(0, 0, 0, 0 + 1f * i);
                    }
                } ;
                for(int i = 0; i < 8; i++){
                    float angleOffset = i * 5 - (8 - 1) * 5 / 2f;
                    handler1.shoot(0, 0, angleOffset, 0 + 2.5f * i);
                }
                */
            }} ;
            ammo(metaglass, b, taihejin, b2, surgeAlloy, b3) ;
            if (Version.build == - 1 || ShaYeBuShi.tiaoshi) {
                ammoTypes.put(phaseFabric, b4) ;
            }
        }};
        hefeiliao = new FangSheXingKuang("hefeiliao", SYBSItems.hefeiliao){{
            enableDrawStatus = true ;
            oreDefault = true;
            oreThreshold = 0.888f;
            oreScale = 25.77619f;
            mapColor = SYBSItems.hefeiliao.color ;
            effect = SYBSStatusEffects.shuaibian ;
        }};
        jianmo = new AbilityContinuousTurret("jianmo") {{
            requirements(Category.turret, with(Items.silicon, 950, Items.graphite, 900, taihejin, 750, Items.carbide, 880, li, 1000));
            size = 5 ;
            shootType = new PointLaserBulletType(){{
                damage = 120f;
                buildingDamageMultiplier = 0.75f;
                color = hitColor = Color.valueOf("909090ff");
                //knockback = -2 ;
                status = SYBSStatusEffects.kuiluan;
                statusDuration = 60 ;
                bulletInterval = 10f ;
                intervalBullet = new MissileBulletType(4f, 120){{
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
            }};
            range = 50 * 8 ;
            shootSound = Sounds.none;
            loopSoundVolume = 1f;
            loopSound = Sounds.laserbeam;
            abilities.add(new JieLieAbility() {{
                type = new PayloadBullet() {{
                    block = Blocks.segment;
                    //blockName = "segment" ;
                    speed = 1 ;
                    lifetime = Integer.MAX_VALUE ;
                    kill = false ;
                }} ;
                //type = shootType.intervalBullet ;
            }}) ;
            shootCone = 360f;


            shootY = 4f;
            outlineColor = Pal.darkOutline;
            envEnabled |= Env.space;
            scaledHealth = 210;

            //TODO is this a good idea to begin with?
            unitSort = UnitSorts.strongest;

            consume(new ConsumeDianYa(60, 0, false, 3));
        }};
        shandian6 = new MultiBlock("shandian", shandian, ShaYeBuShi.circle(0, 14, 0, 0), shandian, ShaYeBuShi.circle(120, 14, 0, 0), shandian, ShaYeBuShi.circle(240, 14, 0, 0)){{
            requirements(Category.turret, with(li, 200, taihejin, 400, Items.plastanium, 300, graphite, 300)) ;
            size = 3 ;
            health = 3600 ;
        }};
        xietongrongliandanyuan = new GenericCrafter("xietongrongliandanyuan") {{
           requirements(Category.crafting, BuildVisibility.sandboxOnly, with()) ;
           size = 2 ;
           ambientSound = Sounds.smelter;
           ambientSoundVolume = 0.07f;
           craftEffect = Fx.smeltsmoke;
           outputItems = with(silicon, 10) ;
           consumeItems(with(sand, 10, coal, 5)) ;
           drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffef99")));
           itemCapacity = 20 ;
           craftTime = 4 / 3f * 60 ;
        }};
        lianheyelianjizu = new MultiBlock("lianheyelianjizu", xietongrongliandanyuan, new Vec2(8, 8), xietongrongliandanyuan, new Vec2(-8, -8), xietongrongliandanyuan, new Vec2(-8, 8), xietongrongliandanyuan, new Vec2(8, -8)) {{
            requirements(Category.crafting, with(silicon, 1500, surgeAlloy, 100, phaseFabric, 100, ruangang, 300, taihejin, 250, li, 500, xiangjiao, 200));
            size = 4 ;
            health = 5200 ;
            itemCapacity = 80 ;
            consume(new ConsumeDianYa(20, 0, false, 2)) ;
        }};
        taihejinzhiqu = new MassDriver("taihejinzhiqu") {{
            requirements(Category.distribution, with(Items.silicon, 375, li, 200, taihejin, 100));
            size = 4;
            itemCapacity = 200;
            reload = 60f;
            range = 440f * 1.5f;
            scaledHealth *= 3 ;
            consume(new ConsumeDianYa(1.75f * 2, 0, false, 1));
        }};
        jisuchongyayitiji = new MultiCrafter("jisuchongyayitiji", qixuan, new Vec2(0, 0)) {{
            requirements(Category.crafting, with(taihejin, 500, Items.silicon, 985, li, 400, Items.graphite, 750, surgeAlloy, 200, phaseFabric, 100, ruangang, 300, xiangjiao, 100));

            craftEffect = new MultiEffect(Fx.pulverizeMedium, Fx.smeltsmoke, Fx.formsmoke);
            updateEffect = Fx.plasticburn ;
            outputItems = with(graphite, 2, plastanium, 2, taihejin, 2);
            craftTime = 12f;
            itemCapacity = 20;
            size = 4;
            hasItems = true;
            hasLiquids = true;
            hasPower = true;
            health = 6400 ;

            consume(new ConsumeDianYa(18f, 0, false, 2));
            consumeItems(with(titanium, 4, lv, 2, coal, 2));
            consumeLiquids(LiquidStack.with(Liquids.water, 0.8f, Liquids.oil, 0.8f));
        }};
        xiangweifadianji = new DianYaConsumeGenerator("xiangweifadianji") {{
            requirements(Category.power, BuildVisibility.sandboxOnly, with());
            powerProduction = 2000 / 60f;
            itemDuration = 1200f;
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.03f;
            generateEffect = Fx.generatespark;
            consume(new ConsumeItems(with(phaseFabric, 1)));
            size = 2 ;
            hasItems = true ;
            itemCapacity = 10 ;
            dianya = 3 ;
        }};
        xiangweifadiangongchang = new MultiCrafter("xiangweifadiangongchang", xiangweifadianji, new Vec2(16, 0)) {{
            requirements(Category.crafting, BuildVisibility.sandboxOnly, with());
            craftEffect = Fx.smeltsmoke;
            outputItem = new ItemStack(Items.phaseFabric, 2);
            craftTime = 60f;
            size = 2;
            //hasPower = true;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawWeave(), new DrawDefault());
            //envEnabled |= Env.space;
            ambientSound = Sounds.techloop;
            ambientSoundVolume = 0.02f;
            consumeItems(with(Items.thorium, 8, Items.sand, 20));
            itemCapacity = 40;
            outputsPower = true ;
        }};
        xiangweizonghegongchang = new MultiShuangChongZuanTou("xiangweizonghegongchang", xiangweifadiangongchang, new Vec2(-8, 8)) {{
            requirements(Category.crafting, with(phaseFabric, 500, surgeAlloy, 250, silicon, 1000, taihejin, 300, ruangang, 300, xiangjiao, 100)) ;
            size = 4 ;
            update = true;
            solid = true;
            hasPower = true;
            outputsPower = true ;
            consumesPower = true ;
            health = 4000 ;
            consume(new ConsumeDianYa(1800 / 60f, 0, false, 3)) ;
            itemCapacity = 80 ;
            tier = 5 ;
            tierr = 5 ;
            range = (int)(10 / 6f * 4) ;
            drillTime = 160 / 4f * 6 / 5f ;
            drillTimee = 40 / 4f * 6 / 5f ;
            drawRim = true ;
            rotateSpeed = 8f ;
            //warmupSpeed = 1f ;
        }};
        cichangdianshifadianji = new CiChangFaDianJi("cichangdianshifadianji") {{
           requirements(Category.power, SYBSBuildVisibility.tiaoshiOnly, with(xiangjiao, 100, ruangang, 100, taihejin, 200, li, 300, phaseFabric, 25)) ;
           size = 2 ;
           powerProduction = 400 / 60f ;
           dianya = 1 ;
        }};
        youqiang = new OreBlock("youqiang", you){{
            wallOre = true;
        }};
        liebianfanyingdui = new HeFanYingDui("liebianfanyingdui") {{
            requirements(Category.power, with(ruangang, 300, Items.silicon, 1200, xiangjiao, 350, fangfushe, 200, Items.metaglass, 150, surgeAlloy, 200, phaseFabric, 200, taihejin, 1400));
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.24f;
            size = 5;
            health = (int)(700 * 5 * 1.5f);
            itemDuration = 600f;
            powerProduction = 155f;
            heating = 0.06f;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawPlasma(), new DrawDefault()) ;
            //consumeItem(Items.thorium);
            consumeLiquid(Liquids.cryofluid, heating / coolantPower).update(false);
            consume(filterItem = new ConsumeItemRadioactive(){
                @Override
                public float efficiencyMultiplier(Building build){
                    float out = 0 ;
                    for (Item i : content.items()) {
                        if (build.items.has(i) && filter.get(i) && i.radioactivity > out) {
                            out = i.radioactivity ;
                        }
                    }
                    return out ;
                }
                {
                filter = item -> item.radioactivity >= this.minRadioactivity && item != SYBSItems.hefeiliao ;
            }}) ;
        }} ;
        xiaoxinghedan = new ZhaDanBlock("xiaoxinghedan") {{
            requirements(Category.effect, with( you235, 100, you238, 100, taihejin, 200, silicon, 200, blastCompound, 150));
            amount = 35 ;
            size = 3 ;
            health = 460 ;
            reloadB = 1.5f * toMinutes ;
            type = new ExplosionBulletType(9600 * 2, 16 * tilesize) {
                @Override
                public void createSplashDamage(Bullet b, float x, float y){
                    super.createSplashDamage(b, x, y);
                    for (StatusEffect e : new StatusEffect[]{SYBSStatusEffects.fushe, SYBSStatusEffects.shuaibian}) {
                        if (ShaYeBuShi.isJianYi(e)) {
                            Damage.status(b.team, x, y, splashDamageRadius, e, statusDuration, collidesAir, collidesGround);
                        }
                    }
                }
                {
               despawnHit = true ;
               hitEffect = SYBSFx.hedanbaozhaxiao ;
               hitSound = Sounds.explosionbig ;
               fragBullets = 3 ;
               fragBullet = SYBSBullets.hedandanpian.copy() ;
               fragBullet.damage /= 5 ;
               fragBullet.splashDamage /= 5 ;
               fragBullet.splashDamageRadius /= 5 ;
               fragBullet.speed /= 3 ;
               fragBullet.speed /= 2 ;
               incendAmount = 400 ;
               incendChance = 1 ;
               incendSpread = 10 * tilesize ;
               hitShake = 40 ;
            }};
        }} ;
        zhadan = new ZhaDanBlock("zhadan") {{
           requirements(Category.effect, with(blastCompound, 200, pyratite, 100, silicon, 50)) ;
           size = 2 ;
           armor = 3 ;
           type = ((ItemTurret)Blocks.scathe).ammoTypes.get(carbide).spawnUnit.weapons.first().bullet.copy() ;
           type.fragBullets = 5 ;
           type.fragSpread = 40 ;
           type.fragBullet = type.copy() ;
           type.incendAmount = 100 ;
           type.incendChance = 1 ;
           type.incendSpread = 3.5f * tilesize ;
           click = false ;
        }} ;
        xielei = new PowerTurret("xielei") {{
           requirements(Category.turret, with(taihejin, 1200, copper, 2000, silicon, 1500, surgeAlloy, 500, phaseFabric, 500)) ;
           size = 5 ;
           health = jianmo.health ;
           armor = jianmo.armor ;
           drawer = new DrawTurret() {{
               parts.add(new RegionPart("-turret") {{
                   moves.add(new PartMove() {{
                       progress = PartProgress.warmup ;
                       rot = -45 ;
                   }}, new PartMove() {{
                       progress = PartProgress.recoil ;
                       rot = 90 ;
                   }}) ;
               }}) ;
           }} ;
           shootType = new RotateBullet() {{
               lifetime = 30 ;
               damage = 1000 ;
               damageInterval = 1 ;
               colors = new Color[]{SYBSPal.yaofeng.a(0.55f), Pal.lightishGray.a(0.7f), SYBSPal.yaofeng.a(0.8f), Pal.lightishGray, Color.white};
               length = 18 * tilesize ;
               drawSize = length;
               intervalBullet = jianmo.shootType.intervalBullet.copy() ;
               ((BasicBulletType)intervalBullet).frontColor = SYBSPal.yaofeng ;
               ((BasicBulletType)intervalBullet).backColor = SYBSPal.yaofeng ;
               intervalBullet.trailColor = SYBSPal.yaofeng ;
               intervalBullet.status = StatusEffects.none ;
               intervalBullet.damage = 200 ;
               intervalBullet.splashDamage = 200 ;
               intervalBullet.pierce = true ;
               intervalBullet.speed *= 4 ;
               intervalBullet.lifetime = (48 - 18) * tilesize / intervalBullet.speed ;
               intervalBullet.homingPower = 0 ;
               intervalBullet.trailLength = 4 ;
               intervalBullet.trailEffect = Fx.none ;
               bulletInterval = 0.33f ;
               trailColor = SYBSPal.yaofeng.a(1) ;
               //trailColor = Team.crux.color;
           }} ;
           //shootDuration = 30 ;
           range = 50 * tilesize ;
           reload = 30 ;
           recoil = 0.000001f ;
           recoilTime = 30 ;
           shootSound = SYBSSounds.flameBig;
           shake = 4 ;
//           minWarmup = 3f ;
           shootCone = 90 ;
           consume(new ConsumeDianYa(60, 0, false, 3));
           consumeLiquid(Liquids.cryofluid, 1f) ;
        }};
        xiulijihuotouyingqi = new MendProjector("xiulijihuotouyingqi") {
            @Override
            public void setStats() {
                super.setStats();
                stats.remove(Stat.booster) ;
            }
            {
            requirements(Category.effect, with(taihejin, 1000, Items.titanium, 500, silicon, 750, Items.phaseFabric, 200));
            health = 1000 ;
            size = 4 ;
            consume(jixianchaosu.consPower) ;
            range = jixianchaosu.range;
            healPercent = 25f;
            reload = 5 * toSeconds ;
            phaseBoost = phaseRangeBoost = 0 ;
            consumeItem(silicon, 10) ;
        }} ;
        zhendanghudunfashengqi = new ZhenDangHuDunBlock("zhendanghudunfashengqi") {{
            requirements(Category.effect, with(taihejin, 1000, Items.titanium, 500, silicon, 750, Items.phaseFabric, 200));
            size = 4;
            sides = 257 ;
            radius = jixianchaosu.range;
            shieldHealth = 40000f;
            cooldownNormal = 2.5f * 20 ;
            cooldownLiquid = 4f * 20 ;
            cooldownBrokenBase = 1f * 20 ;
            health = 1000 ;
            consume(new ConsumeDianYa(60, 0, false, 2));
        }} ;
        zhanquweihuxitong = new MultiBlockXianZhi("zhanquweihuxitong", jixianchaosu, new Vec2(0, 0), xiulijihuotouyingqi, new Vec2(0, 0), xiulijihuotouyingqi, new Vec2(0, 0), Blocks.repairTurret, new Vec2(-tilesize, tilesize), Blocks.repairTurret, new Vec2(tilesize, -tilesize)) {{
            requirements(Category.effect, with(taihejin, 1000, Items.titanium, 2000, silicon, 1500, Items.phaseFabric, 800, ruangang, 800, xiangjiao, 800)) ;
            health = 4000 ;
            consume(new ConsumeDianYa(15000 / 60f, 0, false, 2)) ;
            itemCapacity = 80 ;
            size = 4 ;
            limitPlaceOnCount = 1 ;
            abilities.add(new ZhenDangHuDunAbility(zhendanghudunfashengqi.radius, zhendanghudunfashengqi.cooldownNormal, zhendanghudunfashengqi.shieldHealth, zhendanghudunfashengqi.shieldHealth / zhendanghudunfashengqi.cooldownBrokenBase, zhendanghudunfashengqi.sides, zhendanghudunfashengqi.shieldRotation, zhendanghudunfashengqi.damage + zhendanghudunfashengqi.poxianshangDamage)) ;
            //drawPayload.set(new Boolean[]{false, false, false, false, true, true}) ;
        }} ;
        weixingreliangchuanshuji = new ReLiangChuanShuJi("weixingreliangchuanshuji"){{
            requirements(Category.crafting, with(Items.tungsten, 10, Items.graphite, 10));

            researchCostMultiplier = 10f;

            group = Blocks.heatRedirector.group ;
            size = 1;
            drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput(), new DrawHeatInput("-heat"));
            regionRotated1 = 1;
        }};
        weixingreliangluyouqi = new ReLiangChuanShuJi("weixingreliangluyouqi"){{
            requirements(Category.crafting, with(Items.tungsten, 15, Items.graphite, 10));

            researchCostMultiplier = 10f;

            group = Blocks.heatReactor.group ;
            size = 1;
            drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput(-1, false), new DrawHeatOutput(), new DrawHeatOutput(1, false), new DrawHeatInput("-heat"));
            regionRotated1 = 1;
            splitHeat = true;
            sunhao = 0 ;
        }};
        lixichongzuhejinduanlu = new MultiCrafter("lixichongzuhejinduanlu", Blocks.swarmer, new Vec2(tilesize, tilesize), Blocks.swarmer, new Vec2(-tilesize, -tilesize)) {{
            requirements(Category.crafting, with(taihejin, 550, Items.silicon, 1000, li, 3500, Items.graphite, 500, surgeAlloy, 250, phaseFabric, 50, ruangang, 400, xiangjiao, 50));

            craftEffect = new RadialEffect(Fx.surgeCruciSmoke, 4, 90f, 5f);
            updateEffect = Fx.plasticburn ;
            outputItems = with(metaglass, 5, surgeAlloy, 3);
            craftTime = 60f;
            itemCapacity = 20;
            size = 4;
            hasItems = true;
            hasLiquids = true;
            hasPower = true;
            health = 4000 ;
            drawer = new DrawMulti(new DrawFlame(Color.valueOf("ffef99")), new DrawDefault()) ;

            consume(new ConsumeDianYa(16f, 0, false, 2));
            consumeItems(with(scrap, 20));
            itemCapacity = 40 ;
        }} ;
        daxingdianjieji = new GenericCrafter("daxingdianjieji"){{
            requirements(Category.crafting, with(Items.silicon, 100, Items.graphite, 80, Items.beryllium, 260, Items.tungsten, 160, taihejin, 100, silicon, 300));
            size = 4;

            researchCostMultiplier = 1.2f;
            craftTime = 10f;
            rotate = true;
            invertFlip = true;
            group = BlockGroup.liquids;

            liquidCapacity = 100f;

            consumeLiquid(Liquids.water, 30f / 60f);
            consume(new ConsumeDianYa(3f, 0, false, 1));

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(Liquids.water, 2f),
                    new DrawBubbles(Color.valueOf("7693e3")){{
                        sides = 10;
                        recurrence = 3f;
                        spread = 6;
                        radius = 1.5f;
                        amount = 20;
                    }},
                    new DrawRegion(),
                    new DrawLiquidOutputs(),
                    new DrawGlowRegion(){{
                        alpha = 0.7f;
                        color = Color.valueOf("c4bdf3");
                        glowIntensity = 0.3f;
                        glowScale = 6f;
                    }}
            );

            ambientSound = Sounds.electricHum;
            ambientSoundVolume = 0.08f;

            regionRotated1 = 3;
            outputLiquids = LiquidStack.with(Liquids.ozone, 12f / 60, Liquids.hydrogen, 18f / 60);
            liquidOutputDirections = new int[]{1, 3};
        }};
        xiangjiaohechengji = new HeatCrafter("xiangjiaohechengji") {{
            requirements(Category.crafting, with(ruangang, 100, taihejin, 200, silicon, 300)) ;
            outputItem = new ItemStack(xiangjiao, 4) ;
            consumeLiquid(Liquids.oil, 80 / 60f) ;
            size = 3 ;
            health = 960 ;
            heatRequirement = 20 ;
            consume(new ConsumeDianYa(10, 0, false, 1)) ;
            craftTime = 120 ;
            updateEffect = SYBSFx.shengchan6 ;
        }} ;
        ruangangduandaji = new GenericCrafter("ruangangduandaji") {{
            requirements(Category.crafting, with(plastanium, 100, taihejin, 200, silicon, 300)) ;
            outputItem = new ItemStack(ruangang, 2) ;
            consumeItems(with(taihejin, 1, plastanium, 1));
            consumeLiquid(SYBSLiquids.yanjiang, 20 / 60f) ;
            consume(new ConsumeDianYa(10, 0, false, 1)) ;
            craftTime = 60 ;
            size = 3 ;
            health = 1000 ;
            craftEffect = SYBSFx.shengchan5 ;
        }} ;
        daxingtanhuawuganguo = new HeatCrafter("daxingtanhuawuganguo"){{
            requirements(Category.crafting, with(Items.tungsten, 220, Items.thorium, 300, Items.oxide, 120, taihejin, 100, silicon, 300));
            craftEffect = Fx.none;
            outputItem = new ItemStack(Items.carbide, 10);
            craftTime = 60f * 10f;
            size = 4;
            itemCapacity = 60;
            hasPower = hasItems = true;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawCrucibleFlame(), new DrawDefault(), new DrawHeatInput());
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.09f;

            heatRequirement = 20f;
            maxEfficiency = 10 ;

            consumeItems(with(Items.tungsten, 20, coal, 20, pyratite, 3));
            consume(new ConsumeDianYa(4f, 0, false, 1));
        }};
        daxingyanghuashi = new HeatProducer("daxingyanghuashi"){{
            requirements(Category.crafting, with(Items.tungsten, 240, Items.graphite, 160, Items.silicon, 200, Items.beryllium, 240, silicon, 300, taihejin, 100));
            size = 4;

            outputItem = new ItemStack(Items.oxide, 2);
            researchCostMultiplier = 1.1f;
            hasLiquids = true ;

            consumeLiquid(Liquids.ozone, 4f / 60f);
            consumeItems(with(Items.beryllium, 2));
            consume(new ConsumeDianYa(3, 0, false, 1));

            rotateDraw = false;

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidRegion(), new DrawDefault(), new DrawHeatOutput());
            ambientSound = Sounds.extractLoop;
            ambientSoundVolume = 0.08f;

            regionRotated1 = 2;
            craftTime = 60f * 0.5f;
            liquidCapacity = 30f;
            heatOutput = 20f;
        }};
        daxinglengdongyehunheqi = new GenericCrafter("daxinglengdongyehunheqi"){{
            requirements(Category.crafting, with(Items.lead, 130, Items.silicon, 380, Items.titanium, 120, taihejin, 100));
            outputLiquid = new LiquidStack(Liquids.cryofluid, 60f / 60f);
            size = 3;
            hasPower = true;
            hasItems = true;
            hasLiquids = true;
            rotate = false;
            solid = true;
            outputsLiquid = true;
            envEnabled = Env.any;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.water), new DrawLiquidTile(Liquids.cryofluid){{drawLiquidLight = true;}}, new DrawDefault());
            liquidCapacity = 24f;
            craftTime = 24 ;
            lightLiquid = Liquids.cryofluid;

            consume(new ConsumeDianYa(4, 0, false, 1));
            consumeItems(with(Items.titanium, 1));
            consumeLiquid(Liquids.water, 60f / 60f);
        }};
        fangyoutiquji = new HeatCrafter("fangyoutiquji"){{
            requirements(Category.crafting, with(Items.silicon, 380, Items.beryllium, 180, copper, 180, taihejin, 100));

            heatRequirement = 10f;

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.arkycite),
                    new DrawParticles(){{
                        color = Color.valueOf("89e8b6");
                        alpha = 0.5f;
                        particleSize = 3f;
                        particles = 10;
                        particleRad = 9f;
                        particleLife = 200f;
                        reverse = true;
                        particleSizeInterp = Interp.one;
                    }}, new DrawDefault(), new DrawHeatInput(), new DrawHeatRegion("-heat-top"));

            size = 3;

            ambientSound = Sounds.extractLoop;
            ambientSoundVolume = 0.08f;

            liquidCapacity = 80f;
            outputLiquid = new LiquidStack(Liquids.arkycite, 12f / 60f);

            //consumeLiquids(LiquidStack.with(Liquids.hydrogen, 3f / 60f, Liquids.nitrogen, 2f / 60f));
            consumeItem(sporePod);
            consume(new ConsumeDianYa(3f, 0, false, 1));
        }};
        zaiezujian = new PowerTurret("zaiezujian") {
            @Override
            public void setStats() {
                super.setStats() ;
                stats.remove(Stat.ammo) ;
                stats.add(Stat.ammo, SYBSStatValues.ammo(ObjectMap.of(this, shootType)));
            }
            {
            requirements(Category.turret, SYBSBuildVisibility.tiaoshiOnly, with()) ;
            size = 5 ;
            health = 4000 ;
            range = 78 * tilesize ;
            reload = 240 ;
            shootType = new PayloadBullet() {{
                block = ezhao ;
                speed = 2 ;
                lifetime = 78 * tilesize / speed ;
            }} ;
            outlineIcon = false ;
            Color c = you.color ;
            /*
            drawer = new DrawTurret("none-") {{
                parts.addAll(new ShapePart() {{
                    y = -5 * tilesize ;
                    x = 0 ;
                    circle = true ;
                    hollow = true ;
                    color = c ;
                    radius = 50 / 4f / 2 * 1.35f ;
                }}) ;
                parts.addAll(new HaloPart() {{
                    y = -5 * tilesize ;
                    x = 0 ;
                    tri = true ;
                    triLength = 50 / 4f / 2 * 1.3f ;
                    radius = 50 / 4f / 2 * (float)Math.PI * 2 / 6f * 1.5f ;
                    color = c ;
                    haloRadius = 50 / 4f * 1.1f - 50 / 4f / 2 * 0.95f ;
                    shapeRotation = 180 ;
                }}) ;
                parts.addAll(new ShapePart() {{
                    y = -5 * tilesize ;
                    x = 0 ;
                    circle = true ;
                    color = c ;
                    radius = 2 ;
                }}) ;
            }} ;
            */
            shootSound = ((Turret)Blocks.breach).shootSound ;
            shake = 10 ;
            recoil = 3 ;
        }} ;
        /*
        zaie = new MultiBlock("zaie", zaiezujian, new Vec2(-2.5f * tilesize, 0), zaiezujian, new Vec2(0, 0), zaiezujian, new Vec2(2.5f * tilesize, 0)) {{
            requirements(Category.turret, with(Items.copper, 3000, Items.surgeAlloy, 400, Items.plastanium, 400, Items.silicon, 1200, taihejin, 1000, li, 1000, lv, 1000)) ;
            consume(new ConsumeDianYa(50, 0, false, 1)) ;
            size = 5 ;
            health = 4000 ;
            outlineIcon = false ;
        }} ;
        */
        zaie = new MultiBlock("zaie", zaiezujian, ShaYeBuShi.circle(90, 1.25f * tilesize, 0, 0), zaiezujian, ShaYeBuShi.circle(-30, 1.25f * tilesize, 0, 0), zaiezujian, ShaYeBuShi.circle(210, 1.25f * tilesize, 0, 0)) {{
            requirements(Category.turret, with(Items.copper, 3000, Items.surgeAlloy, 400, Items.plastanium, 400, Items.silicon, 1200, taihejin, 1000, li, 1000, lv, 1000)) ;
            consume(new ConsumeDianYa(50, 0, false, 1)) ;
            size = 5 ;
            health = 4000 ;
            outlineIcon = false ;
        }} ;
        shendizhenghe = new WallPowerTurret("shendizhenghe"){{
            requirements(Category.defense, with(shendidongli, 1, shendiwuzhuang, 1, shendizhuangjia, 1, shendilantu, 1));
            health = 850000 ;
            size = 4 ;
            absorbLasers = true;
            //rotate = true;
            lightningChance = 1f;
            lightningDamage = 100f / 72000 * 800000 ;
            shieldHealth = 4500f / 72000 * 800000 ;
            regenSpeed = 1000 / 60f ;
            outputsPower = false;
            hasPower = true;
            consumesPower = true;
            conductivePower = true;
            chanceDeflect = 50f;
            armor = 100f;
            buildCostMultiplier = 500f;
            abilities.add(new XianShangAbility(), new ShieldArcAbility() {{
                radius = size * tilesize ;
                width *= 2 ;
                max = shieldHealth ;
                regen = regenSpeed ;
                whenShooting = false ;
                angle = 361 ;
            }}, new ShieldArcAbility() {{
                width *= 2 ;
                radius = size * tilesize + width + 1 ;
                max = shieldHealth ;
                regen = regenSpeed ;
                whenShooting = false ;
                angle = 361 ;
            }}) ;
            consumeDianYa(this, 250f, 2) ;
            ((DrawWallTurret)drawer).basePrefix = "shendizhenghe-" ;
            var haloProgress = DrawPart.PartProgress.warmup;
            Color haloColor = Color.valueOf("aa6600");
            float haloY = -15f, haloRotSpeed = 1.5f;

            var circleProgress = DrawPart.PartProgress.warmup.delay(0.9f);
            var circleColor = haloColor;
            float circleY = 0f, circleRad = 16.5f, circleRotSpeed = 3.5f, circleStroke = 1.6f;
            ((DrawWallTurret)drawer).parts.addAll(
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
            reload = 150f;
            recoil = 5f;
            shake = 2f;
            shootSound = Sounds.missileLarge;
            shoot = new ShootSpread(30, 0.75f);
            shoot.shotDelay = 0.2f ;
            shootType = new GaiLvMiaoBulletType(13f, 1600,1000){{
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
            range = shootType.lifetime * shootType.speed ;
            //limitRange(tilesize) ;
        }};
        shengyu = new ZhenDangHuDunBlock("shengyu") {{
            requirements(Category.effect, with(anwuzhihejin, 2000, silicon, 12000, xiangjiao, 8000, sanjihejin, 4000, anjinshu, 4000)) ;
            consumeDianYa(this, 100000 / 60f, 3) ;
            damage = 50000 ;
            poxianshang = false ;
            sides = 4 ;
            shieldRotation = 45 ;
            shieldHealth = 2000000 ;
            cooldownNormal = cooldownLiquid = 80000 ;
            cooldownBrokenBase = shieldHealth / 40 / 60 ;
            radius = 80 * tilesize ;
            abilities.add(new RepairAbility(), new ShieldArcAbility() {{
                whenShooting = true ;
                max = 2000000 ;
                angle = 360 ;
                radius = 80 * tilesize ;
                width *= 5 ;
            }}) ;
            fantan = true ;
            poxianshangDamage = 200000 ;
            health = 40000 ;
            armor = 80 ;
            size = 7 ;
            consumeCoolant = false ;
        }} ;
        sanjihexin = new MultiCore("sanjihexin", TurretDieTai.dietais.get("dietai").get(8), new Vec2(0, 0)) {{
            requirements(Category.effect, with(anwuzhihejin, 5000, silicon, 30000, xiangjiao, 30000, sanjihejin, 10000, anjinshu, 10000, phaseFabric, 20000)) ;
            health = 200000 ;
            armor = 60 ;
            itemCapacity = 4000000 ;
            unitCapModifier = 100 ;
            unitType = pi ;
            abilities.add(new XianShangAbility() {{
                dancixianshang = 20000 ;
                miaoxianshang = 100000 ;
            }}, new RepairAbility(), new ShieldArcAbility() {{
                whenShooting = true ;
                max = 2000000 ;
                angle = 360 ;
                radius = 80 * tilesize ;
                width *= 5 ;
            }}, new ZhenDangHuDunAbility(shengyu.radius, shengyu.cooldownNormal, shengyu.shieldHealth, shengyu.shieldHealth / shengyu.cooldownBrokenBase, shengyu.sides, shengyu.shieldRotation, shengyu.damage + shengyu.poxianshangDamage) {
                @Override
                public void update(Building b) {
                    super.update(b) ;
                    rotation += 0.75f ;
                }
            }) ;
            size = 7 ;
            hasPower = true ;
            //drawPayload.set(1, false) ;
            int n = 5, m = 5, t = 5 * tilesize ;
            for (int i = 1 ; i <= n ; i ++) {
                for (int z = 1 ; z <= m ; z ++) {
                    Vec2 v = ShaYeBuShi.circle(360f / m * z + 360f / n * i, i * t, 0, 0) ;
                    abilities.add(new UnitSpawnAbility(unitType, 10 * toSeconds, v.x, v.y) {{
                        cons = u -> {
                            //System.out.println(u.controller().getClass().getSimpleName());
                            if (u.isCommandable()) {
                                switch (u.team.data().typeCounts[u.type.id] % 3) {
                                    case 0 -> {
                                        u.command().command(UnitCommand.mineCommand);
                                    }
                                    case 1 -> {
                                        u.command().command(UnitCommand.repairCommand);
                                    }
                                    case 2 -> {
                                        u.command().command(UnitCommand.rebuildCommand);
                                    }
                                }
                            }
                        } ;
                    }}) ;
                }
            }
        }} ;











        shanghaixianshi = new ShangHaiXianShiBlock("shanghaixianshi"){{
            requirements(Category.effect, SYBSBuildVisibility.tiaoshiOnly, with()) ;
            size = 3 ;
            health = 1145 ;
        }} ;
        yicixingxiebao = new YiCiXingXieBao("yicixingxiebao") {{
           requirements(Category.effect, SYBSBuildVisibility.tiaoshiOnly, with()) ;
        }};
        wupinyuan = new WuPinYuan("wupinyuan"){{
            requirements(Category.distribution,
                    //BuildVisibility.sandboxOnly,
                    with());
            itemsPerSecond = 500 ;
        }};
        fankongjun = new FanDanWei("fankongjun"){{
           requirements(Category.logic, SYBSBuildVisibility.tiaoshiOnly, with());
        }};
        fanlujun = new FanDanWei("fanlujun"){{
            ground = true ;
            fly = false ;
            requirements(Category.logic, SYBSBuildVisibility.tiaoshiOnly, with());
        }};
        fanhaijun = new FanDanWei("fanhaijun"){{
            naval = true ;
            fly = false ;
            requirements(Category.logic, SYBSBuildVisibility.tiaoshiOnly, with());
        }};
        fanyiqie = new FanDanWei("fanyiqie"){{
            ground = true ;
            naval = true ;
            radius = 100f * tilesize ;
            requirements(Category.logic, SYBSBuildVisibility.tiaoshiOnly, with());
        }};
        danweishengcheng = new DanWeiShengCheng("danweishengcheng"){{
            requirements(Category.effect, BuildVisibility.sandboxOnly, with());
        }};
        wuxianlichang = new FangFuSheLiChang("wuxianlichang") {{
           requirements(Category.crafting, BuildVisibility.sandboxOnly, with()) ;
           size = 1 ;
           radius = Integer.MAX_VALUE ;
           wuxianRange = true ;
           sides = 4 ;
           use = false ;
           alwaysWork = true ;
        }};
        jinyongchuliqi = new JinYongChuLiQi("jinyongchuliqi"){{
            requirements(Category.logic, SYBSBuildVisibility.tiaoshiOnly, with());
        }};
        zhixian = new ZhiXianBlock("zhixian") {{
            requirements(Category.effect, SYBSBuildVisibility.tiaoshiOnly, with()) ;
        }} ;
        xianduan = new XianDuanBlock("xianduan") {{
            requirements(Category.effect, SYBSBuildVisibility.tiaoshiOnly, with()) ;
        }} ;
        chongzhirukou = new ChongZhiRuKou("chongzhirukou") {{
            requirements(Category.units, SYBSBuildVisibility.tiaoshiOnly, with());
            size = 5 ;
            itemCapacity = 1000 ;
        }} ;
        ceshi = new PowerTurret("ceshi"){{
            requirements(Category.turret,
                    //BuildVisibility.sandboxOnly,
                    with());
            range = 1145 ;
            reload = 120 ;
            BulletType b = new KILLERBulletType() {{
                colors = new Color[]{Pal.redderDust, SYBSPal.yaofeng} ;
                length = 24 * tilesize ;
                width = tilesize ;
                lifetime = 90 ;
                damage = 120 ;
                damageInterval = 1;
                hitColor = lightColor = Color.black ;
                lightOpacity = 1 ;
                amount = 0 ;
                overrideRotation = false ;
                //continuous = false ;
            }};
            shootType = new PosBullet() {{
                Vec2 v1 = ShaYeBuShi.circle(10, 12 * tilesize, -10 * tilesize, 10 * tilesize) ;
                Vec2 v2 = ShaYeBuShi.circle(250, 10 * tilesize, v1.x, v1.y) ;
                Vec2 v3 = ShaYeBuShi.circle(10, 5 * tilesize, v2.x, v2.y) ;
                Vec2 v4 = ShaYeBuShi.circle(250, 10 * tilesize, v3.x + 3 * tilesize, v3.y) ;
                Vec2 v5 = ShaYeBuShi.circle(145, 5 * tilesize, v4.x, v4.y) ;
                Vec2 v6 = ShaYeBuShi.circle(280, 12 * tilesize, v1.x + 5 * tilesize, v1.y) ;
                Vec2 v7 = ShaYeBuShi.circle(10, 7.5f * tilesize, v6.x, v6.y - 10 * tilesize) ;
                Vec2 v8 = ShaYeBuShi.circle(100, 5 * tilesize, v7.x + 5 * tilesize, v7.y) ;
                Vec2 v9 = ShaYeBuShi.circle(40, 5 * tilesize, v6.x, v6.y) ;
                bullets = Seq.with(
                        new Data(b, v1, 10),
                        new Data(b, v2, 250, b2 -> ((LineBullet)b2).length = 20 * tilesize),
                        new Data(b, v3, 10, b2 -> ((LineBullet)b2).length = 10 * tilesize),
                        new Data(b, v4, 250, b2 -> ((LineBullet)b2).length = 20 * tilesize),
                        new Data(b, v5, 145, b2 -> ((LineBullet)b2).length = 10 * tilesize),
                        new Data(b, v6, 280),
                        new Data(b, v7, 10, b2 -> ((LineBullet)b2).length = 15 * tilesize),
                        new Data(b, v8, 100, b2 -> ((LineBullet)b2).length = 10 * tilesize),
                        new Data(b, v9, 40, b2 -> ((LineBullet)b2).length = 10 * tilesize)
                        ) ;
            }} ;
        }};
        ceshii = new PowerTurret("ceshii"){{
            requirements(Category.turret, SYBSBuildVisibility.tiaoshiOnly,with(silicon, 0, copper, 0, plastanium, 0, titanium, 0, thorium, 0, lead, 0, xiangjiao, 0, you235, 0));
            range = 1145 ;
            //reload = 3 ;
            reload = 120 ;
            BulletType guidaopao = new PointBulletType() {
                @Override
                public void init(Bullet b) {
                    float px = b.x + b.lifetime * b.vel.x,
                            py = b.y + b.lifetime * b.vel.y,
                            rot = b.rotation();

                    Geometry.iterateLine(0f, b.x, b.y, px, py, trailSpacing, (x, y) -> {
                        intervalBullet.create(b, b.x, b.y, b.rotation() + Mathf.range(intervalRandomSpread) + intervalAngle + ((0 - (intervalBullets - 1f)/2f) * intervalSpread));
                    });
                    super.init(b) ;
                }
                {
                    damage = 8000 ;
                    splashDamageRadius = 30 ;
                    splashDamage = 8000 ;
                    speed = 640 / 60f ;
                    lifetime = 640 / speed ;
                    intervalBullets = 80 ;
                    bulletInterval = lifetime + 1 ;
                    intervalAngle = 0 ;
                    intervalSpread = 0 ;
                    intervalRandomSpread = 0 ;
                    intervalBullet = new BasicBulletType(640 / 20f, 1000) {{
                        lifetime = 20;
                        weaveScale = 4;
                        weaveMag = 3;
                        width = 7;
                        height = 10;
                        trailWidth = 3;
                        trailLength = 40;
                        trailColor = Pal.lightOrange.cpy().a(1) ;
                        pierce = true ;
                        homingPower = 0.1f ;
                    }};
                    trailEffect = SYBSFx.jiangeHuang ;
                }};
            shootType = new BaiFenBiBulletType() {{
                splashDamage = 510 ;
                splashDamageRadius = 4 * tilesize ;
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
            shootType = guidaopao ;
            shootSound = SYBSSounds.impact ;
            shootType = wuji.weapons.get(1).bullet ;/*
            shootSound = ((ItemTurret)Blocks.breach).shootSound ;
            shoot = new ShootSummon(0f, 0f, 1, 2f);
            int i = id ;
            shootType = new TianDingBulletType() {{
                blockId = i ;
                len = 60 * tilesize ;
                wid = 20 * tilesize ;
                speed = 15 ;
                width = height = 2 * tilesize ;
            }} ;*/
        }};
        /*
        ceshiii = new MultiTurret("ceshiii"){{
            requirements(Category.turret,BuildVisibility.sandboxOnly,with());
            size = 6 ;
            Seq<Integer> s = new Seq<>() ;
            s.indexOf(1);
            addTurret(dietai9, 15, -15).addTurret(dietai8, -15, 15).addTurret(dietai7, 15, 15).addTurret(dietai6, -15, -15) ;
            // addTurret((Turret)Blocks.fuse, 15, -15).addTurret((Turret)Blocks.foreshadow, -15, 15) ;
            ammoTypes = dietais.get("dietai").get(9).ammoTypes;
            range = dietais.get("dietai").get(9).range ;
            shootCone = dietais.get("dietai").get(9).shootCone ;
            shoot = dietais.get("dietai").get(9).shoot ;
            shootSound = dietais.get("dietai").get(9).shootSound ;
            shootEffect = dietais.get("dietai").get(9).shootEffect ;
        }} ;
        */
        ceshiiii = new PowerTurret("ceshiiii"){{
            requirements(Category.turret,SYBSBuildVisibility.tiaoshiOnly,with());
            range = 1145 ;
            reload = 120 ;
            shootType = new PointBulletType() {{
                speed = 1145 ;
                trailEffect = Fx.none ;
                fragBullets = 1 ;
                BulletType b = new ExplosionBulletType(140 * tilesize, 10000) {
                    @Override
                    public void hit(Bullet b) {
                        super.hit(b);
                        hitEffect.at(b.x, b.y, 0, Pal.redderDust, 140f * tilesize) ;
                    }
                    {
                    killShooter = false ;
                    hitEffect = SYBSFx.baozha3 ;
                    despawnHit = true ;
                    hitShake = 20 ;
                    hitSound = Sounds.explosionbig ;
                }} ;
                fragBullet = new FixedJiDianBullet() {{
                    type1 = b ;
                    lifetime = 180 ;
                }} ;
            }} ;
        }};
        ceshiiiii = new MultiBlock("ceshiiiii", dietai9, new Vec2(15, -15), youling, new Vec2(-15, 15)){{
            requirements(Category.turret,SYBSBuildVisibility.tiaoshiOnly,with());
            size = 6 ;
//            Seq<Integer> s = new Seq<>() ;
//            s.indexOf(1);
//            addTurret(dietai9, 15, -15).addTurret(dietai8, -15, 15).addTurret(dietai7, 15, 15).addTurret(dietai6, -15, -15) ;
//            // addTurret((Turret)Blocks.fuse, 15, -15).addTurret((Turret)Blocks.foreshadow, -15, 15) ;
//            ammoTypes = dietais.get(9).ammoTypes;
        }} ;
        ceshiiiiii = new PowerTurret("ceshiiiiii"){
            @Override
            public void setStats(){
                super.setStats();
                stats.remove(Stat.ammo);
                stats.add(Stat.ammo, SYBSStatValues.ammo(ObjectMap.of(this, shootType)));
            }
            {
            requirements(Category.turret, SYBSBuildVisibility.tiaoshiOnly, with());
            range = 1145 ;
            reload = 60 ;
            shootType = new PayloadBullet(){{
               speed = 1 ;
               lifetime = 1145 ;
               block = qunxing ;
            }};
        }};
        Blocks.grass.asFloor().wall = Blocks.shrubs ;
        kongjungongchang.plans.add(
                new UnitFactory.UnitPlan(z01, toMinutes, with(surgeAlloy, 50, ruangang, 50, silicon, 150, plastanium, 50)),
                new UnitFactory.UnitPlan(affh01, toMinutes, with(surgeAlloy, 50, ruangang, 50, silicon, 150, plastanium, 50))) ;
    }
    public static void consumeDianYa(Block b, float usage, int dianya) {
        b.consume(new ConsumeDianYa(usage, 0, false, dianya)) ;
    }
}