package shayebushi;

import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentType;
import mindustry.entities.UnitSorts;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.entities.bullet.FlakBulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.effect.WrapEffect;
import mindustry.entities.part.DrawPart;
import mindustry.entities.part.HaloPart;
import mindustry.entities.part.RegionPart;
import mindustry.entities.part.ShapePart;
import mindustry.entities.pattern.ShootSummon;
import mindustry.game.Team;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.LaserTurret;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.Env;
import shayebushi.entities.bullet.CeFanBulletType;
import shayebushi.world.block.defense.turrets.TurretMoYan;

import static mindustry.type.ItemStack.with;

public class SYBSBlocks {

    public ContentType getContentType() {
        return null;
    }
    public static TurretMoYan moyan ;
    public static ItemTurret cefan;
    public static LaserTurret jingu ;
    public static void load() {
        moyan = new TurretMoYan("moyan"){{
            requirements(Category.turret, with(Items.carbide, 400, Items.beryllium, 2000, Items.silicon, 800, Items.graphite, 800, Items.phaseFabric, 300));

            var haloProgress = DrawPart.PartProgress.warmup;
            Color haloColor = Color.valueOf("d370d3"), heatCol = Color.purple;
            float haloY = -15f, haloRotSpeed = 1.5f;

            var circleProgress = DrawPart.PartProgress.warmup.delay(0.9f);
            var circleColor = haloColor;
            float circleY = 0f, circleRad = 16.5f, circleRotSpeed = 3.5f, circleStroke = 1.6f;

            shootSound = Sounds.malignShoot;
            loopSound = Sounds.spellLoop;
            loopSoundVolume = 1.3f;

            shootType = new FlakBulletType(8f, 70f){{
                sprite = "missile-large";

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
                }};

                fragSpread = fragRandomSpread = 0f;

                splashDamage = 0f;
                hitEffect = Fx.hitSquaresColor;
                collidesGround = true;
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
            }};

            velocityRnd = 0.15f;
            heatRequirement = -1f;
            maxHeatEfficiency = 2f;
            warmupMaintainTime = 300f;
            consumePower(500f);

            shoot = new ShootSummon(0f, 0f, circleRad, 48f);

            minWarmup = 0.96f;
            shootWarmupSpeed = 0.03f;

            shootY = circleY - 5f;

            outlineColor = Pal.darkOutline;
            envEnabled |= Env.space;
            reload = 9f;
            range = 370;
            shootCone = 100f;
            scaledHealth = 370;
            rotateSpeed = 2f;
            recoil = 0.5f;
            recoilTime = 30f;
            shake = 3f;
        }};
        cefan = new ItemTurret("cefan"){{
            float brange = range = 400f;

            requirements(Category.turret, with(Items.copper, 1000, Items.metaglass, 600, Items.surgeAlloy, 300, Items.plastanium, 200, Items.silicon, 600));
            ammo(
                    Items.phaseFabric, new CeFanBulletType(){{
                        shootEffect = Fx.instShoot;
                        hitColor = Team.crux.color;
                        hitEffect = new MultiEffect(Fx.massiveExplosion, new WrapEffect(Fx.dynamicSpikes, Team.crux.color, 24f), new WaveEffect(){{
                            colorFrom = colorTo = Team.crux.color;
                            sizeTo = 40f;
                            lifetime = 12f;
                            strokeFrom = 4f;
                        }});
                        smokeEffect = Fx.smokeCloud;
                        trailEffect = Fx.instTrail;
                        trailColor = Team.crux.color;
                        despawnEffect = Fx.instBomb;
                        trailSpacing = 20f;
                        damage = 0;
                        buildingDamageMultiplier = 0.2f;
                        speed = brange;
                        hitShake = 6f;
                        ammoMultiplier = 1f;
                    }}
            );

            maxAmmo = 40;
            ammoPerShot = 5;
            rotateSpeed = 2f;
            reload = 200f;
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
        jingu = new LaserTurret("jingu"){{
            requirements(Category.turret, with(Items.copper, 1200, Items.lead, 350, Items.graphite, 300, Items.surgeAlloy, 325, Items.silicon, 325));
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

            shootType = new ContinuousLaserBulletType(12f){{
                length = 200f;
                hitEffect = Fx.hitMeltdown;
                hitColor = Pal.meltdownHit;
                status = SYBSStatusEffects.dangji;
                drawSize = 420f;

                incendChance = 0.4f;
                incendSpread = 5f;
                incendAmount = 1;
                ammoMultiplier = 1f;
            }};

            scaledHealth = 200;
            coolant = consumeCoolant(0.5f);
            consumePower(25f);
        }};
//        Blocks.spectre = new ItemTurret("spectre"){{
//            requirements(Category.turret, with(Items.copper, 900, Items.graphite, 300, Items.surgeAlloy, 250, Items.plastanium, 175, Items.thorium, 250));
//            ammo(
//                    Items.graphite, new BasicBulletType(7.5f, 70){{
//                        hitSize = 4.8f;
//                        width = 15f;
//                        height = 21f;
//                        shootEffect = Fx.shootBig;
//                        ammoMultiplier = 4;
//                        reloadMultiplier = 2.4f;
//                        knockback = 0.3f;
//                    }},
//                    Items.thorium, new BasicBulletType(8f, 140){{
//                        hitSize = 5;
//                        width = 16f;
//                        height = 23f;
//                        shootEffect = Fx.shootBig;
//                        pierceCap = 4;
//                        pierceBuilding = true;
//                        knockback = 1.4f;
//                    }},
//                    Items.pyratite, new BasicBulletType(7f, 120){{
//                        hitSize = 5;
//                        width = 16f;
//                        height = 21f;
//                        frontColor = Pal.lightishOrange;
//                        backColor = Pal.lightOrange;
//                        status = StatusEffects.burning;
//                        hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.fireHit);
//                        shootEffect = Fx.shootBig;
//                        makeFire = true;
//                        pierceCap = 4;
//                        pierceBuilding = true;
//                        knockback = 1.6f;
//                        ammoMultiplier = 3;
//                        splashDamage = 46f;
//                        splashDamageRadius = 26f;
//                    }}
//            );
//            reload = 7f;
//            recoilTime = reload * 2f;
//            coolantMultiplier = 0.5f;
//            ammoUseEffect = Fx.casing3;
//            range = 260f;
//            inaccuracy = 3f;
//            recoil = 3f;
//            shoot = new ShootAlternate(8f);
//            shake = 2f;
//            size = 4;
//            shootCone = 24f;
//            shootSound = Sounds.shootBig;
//
//            scaledHealth = 160;
//            coolant = consumeCoolant(1f);
//
//            limitRange();
//        }};
    }
}
