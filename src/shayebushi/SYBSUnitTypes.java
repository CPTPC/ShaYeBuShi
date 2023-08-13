package shayebushi;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import mindustry.annotations.Annotations;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.abilities.ForceFieldAbility;
import mindustry.entities.abilities.UnitSpawnAbility;
import mindustry.entities.bullet.*;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.FlakBulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.entities.bullet.ShrapnelBulletType;
import mindustry.entities.part.DrawPart;
import mindustry.entities.part.HaloPart;
import mindustry.entities.part.ShapePart;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Legsc;
import mindustry.gen.Sounds;
import mindustry.gen.Unitc;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.ammo.ItemAmmoType;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;
import static mindustry.content.UnitTypes.crawler;
import static mindustry.content.UnitTypes.flare;
import static mindustry.world.meta.Stat.weapons;

public class SYBSUnitTypes {
    public static @Annotations.EntityDef(value = {Unitc.class, Legsc.class}, legacy = true) UnitType xieling ;
    public static void load() {
        xieling = new UnitType("xieling") {{
            drag = 0.1f;
            speed = 0.4f;
            hitSize = 34f;
            health = 66000;
            armor = 22f;
            lightRadius = 140f;
            rotateSpeed = 1.9f;
            drownTimeMultiplier = 3f;
            abilities.addAll(new ForceFieldAbility(20f, 1.0f, 4500f, 60f * 6),new UnitSpawnAbility(crawler, 600f, 0f, -7f));
            legCount = 10;
            legMoveSpace = 0.8f;
            legPairOffset = 3;
            legLength = 75f;
            legExtension = -20;
            legBaseOffset = 8f;
            stepShake = 1f;
            legLengthScl = 0.93f;
            rippleScale = 3f;
            legSpeed = 0.19f;
            ammoType = new ItemAmmoType(Items.graphite, 8);

            legSplashDamage = 80;
            legSplashRange = 60;

            hovering = true;
            shadowElevation = 0.95f;
            groundLayer = Layer.legUnit;

            weapons.add(
                    new Weapon("xieling-fupao") {{
                        y = -5f;
                        x = 11f;
                        shootY = 7f;
                        reload = 30;
                        shake = 4f;
                        rotateSpeed = 2f;
                        ejectEffect = Fx.casing1;
                        shootSound = Sounds.shootBig;
                        rotate = true;
                        shadow = 12f;
                        recoil = 3f;

                        shoot = new ShootSpread(4, 17f);

                        bullet = new ShrapnelBulletType() {{
                            length = 90f;
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
                        }};
                    }});

            weapons.add(new Weapon("xieling-molingzhupao") {{
                y = 7f;
                x = 0f;
                shootY = 22f;
                mirror = false;
                reload = 9;
                shake = 10f;
                recoil = 10f;
                rotateSpeed = 1f;
                ejectEffect = Fx.casing3;
                shootSound = Sounds.malignShoot;
                rotate = true;
                shadow = 30f;

                rotationLimit = 80f;
                Color haloColor = Color.valueOf("d370d3"), heatCol = Color.purple;
                var circleColor = haloColor;
                bullet = new FlakBulletType(8f, 100f){{
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
                    buildingDamageMultiplier = 0.1f;

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

                reload = 120f;
                shake = 3f;
                rotateSpeed = 2f;
                shadow = 30f;
                shootY = 7f;
                recoil = 4f;
                cooldownTime = reload - 10f;
                //TODO better sound
                shootSound = Sounds.laser;

                bullet = new mindustry.entities.bullet.EmpBulletType(){{
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
                    hitColor = lightColor = Pal.sapBullet;
                    lightRadius = 70f;
                    clipSize = 250f;
                    shootEffect = Fx.hitEmpSpark;
                    smokeEffect = Fx.shootBigSmoke2;
                    lifetime = 60f;
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
    }
}
