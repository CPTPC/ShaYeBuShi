package shayebushi;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Strings;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.ai.types.FlyingAI;
import mindustry.annotations.Annotations;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentType;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.effect.WrapEffect;
import mindustry.entities.part.DrawPart;
import mindustry.entities.part.HaloPart;
import mindustry.entities.part.ShapePart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootHelix;
import mindustry.entities.pattern.ShootMulti;
import mindustry.entities.pattern.ShootSpread;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import shayebushi.ai.types.JieTiAI;
import shayebushi.entities.bullet.*;
import shayebushi.entities.units.JieTiUnit;
import shayebushi.entities.units.TouBuUnit;
import shayebushi.entities.units.XianShangUnitEntity;
import shayebushi.type.unit.DuoJieTiUnitType;
import shayebushi.type.unit.JieTiUnitType;
import shayebushi.type.unit.XianShangUnitType;

import static arc.util.Time.toMinutes;
import static arc.util.Time.toSeconds;
import static mindustry.Vars.tilesize;
import static shayebushi.SYBSUnitTypes.loadWeapons;

public class SYBSShenShengUnitTypes {
    public static @Annotations.EntityDef(value = {Unitc.class, Payloadc.class}, legacy = true) XianShangUnitType weiyang;
    public static @Annotations.EntityDef(value = {Unitc.class, Payloadc.class}, legacy = true) XianShangUnitType haojieyijieduan,haojieerjieduan,haojiesanjieduan;
    public static UnitType shouge, shougejieti ;
    static {
        EntityMapping.idMap[114] = XianShangUnitEntity::new ;
        EntityMapping.idMap[119] = JieTiUnit::new ;
        EntityMapping.idMap[121] = TouBuUnit::new ;
        EntityMapping.nameMap.put("shayebushi-weiyang", XianShangUnitEntity::new);
        EntityMapping.nameMap.put("weiyang", XianShangUnitEntity::new);
        EntityMapping.nameMap.put("shayebushi-weiyang", EntityMapping.idMap[114]);
        EntityMapping.nameMap.put("weiyang", EntityMapping.idMap[114]);
        EntityMapping.nameMap.put("shayebushi-haojieyijieduan", EntityMapping.idMap[114]);
        EntityMapping.nameMap.put("haojieyijieduan", EntityMapping.idMap[114]);
        EntityMapping.nameMap.put("shayebushi-haojieerjieduan", EntityMapping.idMap[114]);
        EntityMapping.nameMap.put("haojieerjieduan", EntityMapping.idMap[114]);
        EntityMapping.nameMap.put("shayebushi-haojiesanjieduan", EntityMapping.idMap[114]);
        EntityMapping.nameMap.put("haojiesanjieduan", EntityMapping.idMap[114]);
        EntityMapping.nameMap.put("shayebushi-shougejieti", EntityMapping.idMap[119]);
        EntityMapping.nameMap.put("shougejieti", EntityMapping.idMap[119]);
        EntityMapping.nameMap.put("shayebushi-shouge", EntityMapping.idMap[121]);
        EntityMapping.nameMap.put("shouge", EntityMapping.idMap[121]);
    }
    public static Seq<StatusEffect> statuses;
    public static Seq<UnitType> shenshengjidanwei = new Seq<>();
    public static void immunise(UnitType type,boolean isyange,float yangeamount){
        if(statuses == null){
            statuses = Vars.content.statusEffects().copy();
            statuses.remove(StatusEffects.overclock) ;
            statuses.remove(SYBSStatusEffects.denglizihua) ;
            statuses.remove(StatusEffects.boss) ;
            statuses.add(StatusEffects.wet);
        }
        if (!isyange) {
            type.immunities.addAll(statuses);
        }
        else{
            for (int i = 0 ; i <= (int)(statuses.size/yangeamount);i++){
                type.immunities.add(statuses.get(i)) ;
            }
        }
    }
    public static Weapon fuzhi(Weapon w, float x, float y){
        Weapon ww = w ;
        ww.y = y ;
        ww.x = x ;
        ww.reload = w.reload ;
        ww.init();
        return ww ;
    }
    public static class SYBSUnitEngine extends UnitType.UnitEngine implements Cloneable{
        public float x, y, radius, rotation;

        public SYBSUnitEngine(float x, float y, float radius, float rotation){
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.rotation = rotation;
        }

        public SYBSUnitEngine(){
        }

        public void draw(Unit unit){
            UnitType type = unit.type;
            float scale = type.useEngineElevation ? unit.elevation : 1f;

            if(scale <= 0.0001f) return;

            float rot = unit.rotation - 90;
            Color color = type.engineColor == null ? unit.team.color : type.engineColor;

            Tmp.v1.set(x, y).rotate(rot);
            float ex = Tmp.v1.x, ey = Tmp.v1.y;

            //engine outlines (cursed?)
            /*float z = Draw.z();
            Draw.z(z - 0.0001f);
            Draw.color(type.outlineColor);
            Fill.circle(
            unit.x + ex,
            unit.y + ey,
            (type.outlineRadius * Draw.scl + radius + Mathf.absin(Time.time, 2f, radius / 4f)) * scale
            );
            Draw.z(z);*/

            Draw.color(color);
            Fill.circle(
                    unit.x + ex,
                    unit.y + ey,
                    (radius + Mathf.absin(Time.time, 2f, radius / 4f)) * scale
            );
            Draw.color(type.engineColorInner);
            Fill.circle(
                    unit.x + ex - Angles.trnsx(rot + rotation, 1f),
                    unit.y + ey - Angles.trnsy(rot + rotation, 1f),
                    (radius + Mathf.absin(Time.time, 2f, radius / 4f)) / 2f  * scale
            );
            Draw.color(Color.black);
            Fill.circle(
                    unit.x + ex - Angles.trnsx(rot + rotation + rotation / 2, 2f),
                    unit.y + ey - Angles.trnsy(rot + rotation + rotation / 2, 2f),
                    (radius + Mathf.absin(Time.time, 2f, radius / 4f)) / 3f  * scale
            );
        }

        public UnitType.UnitEngine copy(){
            try{
                return (UnitType.UnitEngine)clone();
            }catch(CloneNotSupportedException awful){
                throw new RuntimeException("fantastic", awful);
            }
        }
    }
    public static void load(){
        weiyang = new XianShangUnitType("weiyang"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            //constructor = XianShangUnitEntityShenSheng::new ;
            //immunities.addAll(Vars.content.statusEffects());
            speed = 0.2f;
            accel = 0.04f;
            drag = 0.04f;
            rotateSpeed = 1f;
            flying = true;
            lowAltitude = true;
            health = 1400000;
            //immunise(this,false,1);
            engineSize = 0;
            hitSize = 240f;
            armor = 500f;
            targetFlags = new BlockFlag[]{BlockFlag.core, null};
            ammoType = new ItemAmmoType(Items.thorium);
            buildSpeed = 15 ;
            mineTier = 5 ;
            mineSpeed = 20 ;
            payloadCapacity = 10000 ;
            itemCapacity = 1000 ;
            engines.add(new UnitEngine(-72.5f, -158.375f, 46.375f, 0), new UnitEngine(72.5f, -158.375f, 46.375f, 0));
            BulletType fragBullet = new BaiFenBiFlakBulletType(4.5f, 1500,0.0005f){{
                shootEffect = Fx.shootBig;
                ammoMultiplier = 4f;
                splashDamage = 1300f;
                splashDamageRadius = 25f;
                collidesGround = true;
                lifetime = 141f;
                height = 35 ;
                width = 25 ;
                lightning = 7;
                lightningLength = 14;
                trailColor = Pal.accent ;
                trailWidth = 2.1f;
                trailLength = 60;
                homingPower = 0.3f ;
                homingRange = 16 ;
                explodeRange = 36f;
//                status = SYBSStatusEffects.tianfa ;
//                statusDuration = 300 ;
            }};
            BulletType fragBullett = new CeFanBulletType(){{
                cefanshangxian = 114514 ;
                collidesGround = true;
                shootEffect = Fx.instShoot;
                //hitColor = color;
                hitColor = Team.crux.color;
                //hitEffect = new MultiEffect(Fx.massiveExplosion, new WrapEffect(Fx.dynamicSpikes, color, 24f), new WaveEffect(){{
                hitEffect = new MultiEffect(Fx.massiveExplosion, new WrapEffect(Fx.dynamicSpikes, Team.crux.color, 24f), new WaveEffect(){{
                    //colorFrom = colorTo = color;
                    colorFrom = colorTo = Team.crux.color;
                    sizeTo = 40f;
                    lifetime = 12f;
                    strokeFrom = 4f;
                }});
                smokeEffect = Fx.smokeCloud;
                trailEffect = Fx.instTrail;
                //trailColor = color;
                trailColor = Team.crux.color;
                despawnEffect = Fx.instBomb;
                trailSpacing = 20f;
                damage = 1145;
                //buildingDamageMultiplier = 0.2f;
                speed = 640;
                hitShake = 6f;
                ammoMultiplier = 1f;
                lifetime = 1 ;
//                status = SYBSStatusEffects.tianfa ;
//                statusDuration = 300 ;
            }};
            var haloProgress = DrawPart.PartProgress.warmup;
            Color haloColor = Pal.accent;
            float haloY = -15f, haloRotSpeed = 1.5f;

            var circleProgress = DrawPart.PartProgress.warmup.delay(0.9f);
            var circleColor = haloColor;
            float circleY = 0f, circleRad = 16.5f, circleRotSpeed = 3.5f, circleStroke = 1.6f;
            weapons.add(
                    new Weapon("tianqi-fuzhupao"){
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
                                        rotateSpeed = circleRotSpeed;
                                        color = circleColor;
                                        sides = 4 ;
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
                                        sides = 4 ;
                                        hollow = true;
                                        rotation = 90 ;
                                        stroke = 0f;
                                        strokeTo = circleStroke;
                                        radius = circleRad;
                                        layer = Layer.effect;
                                        y = circleY;
                                    }}
                            );
                            shake = 4f;
                            shootY = 9f;
                            x = 58f;
                            y = 45f;
                            rotateSpeed = 2f;
                            reload = 60f;
                            recoil = 4f;
                            shootSound = Sounds.laserblast;
                            shadow = 20f;
                            rotate = true;
                            range = 640 ;
                            shoot = new ShootAlternate(){{
                               barrels = 3 ;
                               spread = 8 ;
                               shots = 2 ;
                            }};
                            bullet = new BaiFenBiLaserBulletType(115,0.005f){{
                                damage = 1700f;
                                sideAngle = 20f;
                                sideWidth = 1.5f;
                                sideLength = 80f;
                                width = 45f;
                                lightning = 14 ;
                                lightningLength = 32 ;
                                length = 640f;
//                            status = SYBSStatusEffects.tianfa ;
//                            statusDuration = 300 ;
                                shootEffect = Fx.shockwave;
                                colors = new Color[]{Pal.accent, Color.valueOf("ff9c5a"), Color.white};
                            }};
                        }},
                    new Weapon("tianqi-fupao"){
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
                        x = 56f;
                        y = 70f;
                        rotateSpeed = 2f;
                        reload = 6f;
                        shootSound = Sounds.shootBig;
                        shadow = 7f;
                        rotate = true;
                        recoil = 0.5f;
                        shootY = 7.25f;
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
                                        rotateSpeed = circleRotSpeed;
                                        color = circleColor;
                                        sides = 4 ;
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
                                        sides = 4 ;
                                        hollow = true;
                                        rotation = 90 ;
                                        stroke = 0f;
                                        strokeTo = circleStroke;
                                        radius = circleRad;
                                        layer = Layer.effect;
                                        y = circleY;
                                    }}
                            );
                            bullet = fragBullet;
                    }},
                    new Weapon("tianqi-fupao"){
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
                        x = 75f;
                        y = 55f;
                        rotateSpeed = 2f;
                        reload = 60f;
                        range = 640 ;
                        shootSound = Sounds.railgun;
                        shadow = 7f;
                        rotate = true;
                        recoil = 0.5f;
                        shootY = 7.25f;
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
                                        rotateSpeed = circleRotSpeed;
                                        color = circleColor;
                                        sides = 4 ;
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
                                        sides = 4 ;
                                        hollow = true;
                                        rotation = 90 ;
                                        stroke = 0f;
                                        strokeTo = circleStroke;
                                        radius = circleRad;
                                        layer = Layer.effect;
                                        y = circleY;
                                    }}
                            );
                            bullet = fragBullett;
                    }},
                    new Weapon("tianqi-fupao"){
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
                        y = -56f;
                        x = 66f;
                        reload = 6f;
                        ejectEffect = Fx.casing1;
                        rotateSpeed = 7f;
                        shake = 1f;
                        shootSound = Sounds.shootBig;
                        rotate = true;
                        shadow = 12f;
                        shootY = 7.25f;
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
                                        rotateSpeed = circleRotSpeed;
                                        color = circleColor;
                                        sides = 4 ;
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
                                        sides = 4 ;
                                        hollow = true;
                                        rotation = 90 ;
                                        stroke = 0f;
                                        strokeTo = circleStroke;
                                        radius = circleRad;
                                        layer = Layer.effect;
                                        y = circleY;
                                    }}
                            );
                            bullet = fragBullet;
                    }});
            weapons.add(
                    new Weapon("tianqi-fuzhupao"){
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
                            shake = 4f;
                            shootY = 9f;
                            x = 58f;
                            y = -45f;
                            rotateSpeed = 2f;
                            reload = 60f;
                            recoil = 4f;
                            shootSound = Sounds.laserblast;
                            shadow = 20f;
                            rotate = true;
                            range = 640 ;
                            rotate = true;
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
                                        rotateSpeed = circleRotSpeed;
                                        color = circleColor;
                                        sides = 4 ;
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
                                        sides = 4 ;
                                        hollow = true;
                                        rotation = 90 ;
                                        stroke = 0f;
                                        strokeTo = circleStroke;
                                        radius = circleRad;
                                        layer = Layer.effect;
                                        y = circleY;
                                    }}
                            );
                            shoot = new ShootAlternate(){{
                                barrels = 7 ;
                                spread = 8 ;
                                shots = 9 ;
                            }};
                            bullet = new BaiFenBiLaserBulletType(115,0.005f){{
                                damage = 3400f;
                                sideAngle = 20f;
                                sideWidth = 15f;
                                sideLength = 80f;
                                width = 45f;
                                length = 1280f;
                                lightning = 14 ;
                                lightningLength = 32 ;
//                                status = SYBSStatusEffects.tianfa ;
//                                statusDuration = 300 ;
                                shootEffect = Fx.shockwave;
                                colors = new Color[]{Pal.accent, SYBSPal.yaofeng, Color.white};
                            }};
                        }},
                    new Weapon("tianqi-zhupao"){
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
                            mirror = false ;
                            shake = 4f;
                            shootY = 9f;
                            x = 0f;
                            y = 0f;
                            rotateSpeed = 2f;
                            reload = 300f;
                            recoil = 4f;
                            shootSound = Sounds.laserbig;
                            shadow = 20f;
                            //rotate = true;
                            //range = 1600 ;
                            continuous = true ;
                            parts.addAll(
                                    new ShapePart(){{
                                        progress = circleProgress;
                                        color = circleColor;
                                        circle = true;
                                        hollow = true;
                                        stroke = 0f;
                                        strokeTo = circleStroke;
                                        radius = circleRad*1.5f-1+1;
                                        layer = Layer.effect;
                                        y = circleY;
                                    }},
                                    new ShapePart(){{
                                        progress = circleProgress;
                                        rotateSpeed = -circleRotSpeed;
                                        color = circleColor;
                                        sides = 4 ;
                                        hollow = true;
                                        stroke = 0f;
                                        strokeTo = circleStroke;
                                        radius = circleRad*1.5f-1;
                                        layer = Layer.effect;
                                        y = circleY;
                                    }},
                                    new ShapePart(){{
                                        progress = circleProgress;
                                        rotateSpeed = -circleRotSpeed;
                                        color = circleColor;
                                        sides = 4 ;
                                        hollow = true;
                                        rotation = 90 ;
                                        stroke = 0f;
                                        strokeTo = circleStroke;
                                        radius = circleRad*1.5f-1;
                                        layer = Layer.effect;
                                        y = circleY;
                                    }}
                            );
                            shoot = new ShootSpread(){{
                                shots = 2 ;
                                spread = 7 ;
                            }} ;
                            bullet = new BaiFenBiChiXuJiGuangBulletType(){

                                {
                                lifetime = 180 ;
                                damage = 1000f;
                                baifenbi = 0.0025f ;
                                width = 25f;
                                length = 1600f;
                                drawSize = 1600f ;
//                                status = SYBSStatusEffects.tianfa ;
//                                statusDuration = 300 ;
//                                shootEffect = Fx.shockwave;
                                colors = new Color[]{Pal.accentBack, SYBSPal.yaofeng, Color.gray};
                            }};
                        }}
            );
        }};
        BulletType fragBullet = new BaiFenBiArtilleryBulletType(10,666 * 5,0){{
            shootEffect = Fx.shootBig;
            ammoMultiplier = 4f;
            splashDamage = 666f * 5;
            splashDamageRadius = 25f;
            status = SYBSStatusEffects.zhongdufushi ;
            statusDuration = 300 ;
            despawnHit = true ;
            hitEffect = SYBSFx.baozha2 ;
            hitColor = Pal.redderDust ;
            hitShake = 10 ;
            parts.add(new HaloPart(){{
                color = Pal.redderDust;
                layer = Layer.effect;
                y = 0;
                haloRotateSpeed = -1.5f;

                shapes = 2;
                shapeRotation = 180f;
                triLength = 35f;
                triLengthTo = 35f;
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
                triLength = 45f;
                triLengthTo = 45f;
                haloRotation = 90f;
                haloRadius = 0f;
                tri = true;
                radius = 10f;
            }}) ;
            fragBullet = new ArtilleryBulletType(6,66.6f * 5 * 8){{
                splashDamageRadius = 10 ;
                splashDamage = damage ;
                despawnHit = true ;
                hitEffect = SYBSFx.baozha2 ;
                hitColor = Pal.redderDust ;
                hitShake = 3 ;
                status = SYBSStatusEffects.zhongdufushi ;
                statusDuration = 300 ;
                lifetime = 14f;
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
                    triLength = 35f;
                    triLengthTo = 35f;
                    haloRotation = 90f;
                    haloRadius = 0f;
                    tri = true;
                    radius = 10f;
                }}) ;

            }};
            fragBullets = 5 ;
            collidesGround = true;
            lifetime = 14.1f * 6;
            height = 25 ;
            width = 15 ;
            trailColor = Pal.redderDust ;
            trailEffect = SYBSFx.lizi1;
            trailInterval = 1 ;
            //trailLength = 40 ;
            //trailWidth = 10 ;
//                status = SYBSStatusEffects.tianfa ;
//                statusDuration = 300 ;
        }};
        BulletType jidian = new JiDianBulletType(2,400f){{
            shootEffect = Fx.shootBig;
            ammoMultiplier = 4f;
            splashDamage = 60f;
            splashDamageRadius = 25f;
            collidesGround = true;
//                status = SYBSStatusEffects.tianfa ;
//                statusDuration = 300 ;
        }};
        //jidian = new BulletType() ;
        BulletType fragBullett = new LaserBulletType(){{
            damage = 4000;
            lightningLength = 800;
            length = 800;
            width *= 4 ;
            collidesAir = true;
            ammoMultiplier = 1f;
            status = StatusEffects.shocked;
            statusDuration = 300f;
            colors[0] = Pal.redderDust.cpy().mul(1f, 1f, 1f, 0.4f);
            colors[1] = Pal.redderDust;
            lightning = 10 ;
            lightningLength = 16 ;
            lightningType = new BulletType(0.0001f, 2000f) {{
                lifetime = Fx.lightning.lifetime;
                hitEffect = Fx.hitLancer;
                despawnEffect = Fx.none;
                status = StatusEffects.shocked;
                statusDuration = 300f;
                hittable = false;
                lightColor = Color.white;
                collidesAir = true;
            }};
                /*
                intervalBullets = 1;
                bulletInterval = 1;
                intervalAngle = 0;
                intervalSpread = 0;
                intervalRandomSpread = 0;
                intervalBullet = new BulletType() {{
                    for (int i : Mathf.signs) {
                        spawnBullets.addAll(
                                new BasicBulletType(6, 2000) {{
                                    lifetime = 40;
                                    weaveScale = 4;
                                    weaveMag = i * 3;
                                    width = 7;
                                    height = 10;
                                    trailWidth = 3;
                                    trailLength = 40;
                                }}
                        );
                    }
                }};
                */
        }};
        BulletType daodan = new MissileBulletType(){{
            width = height = 16 ;
            lifetime = 120 ;
            speed = 800 / lifetime ;
            damage = 1600 ;
            splashDamage = damage ;
            splashDamageRadius = 8 ;
            trailWidth = 4 ;
            trailLength = 12 ;
            trailColor = Color.white ;
            homingPower = 0.2f ;
            homingRange = 800 ;
            homingDelay = 20 ;
        }};
        BulletType guidaopao2 = new PointBulletType() {
            @Override
            public void init(Bullet b) {
                float px = b.x + b.lifetime * b.vel.x,
                        py = b.y + b.lifetime * b.vel.y,
                        rot = b.rotation();

                Geometry.iterateLine(0f, b.x, b.y, px, py, trailSpacing, (x, y) -> {
                    intervalBullet.create(b, x, y, b.rotation() + Mathf.range(intervalRandomSpread) + intervalAngle + ((0 - (intervalBullets - 1f)/2f) * intervalSpread));
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
                intervalBullet = new BasicBulletType(7, 1000) {{
                    lifetime = 20;
                    weaveScale = 2;
                    weaveMag = -6;
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
        BulletType guidaopao = new BasicBulletType() {{
            damage = 8000 ;
            sprite = "large-orb";
            width = height = 32 ;
            splashDamageRadius = 30 ;
            splashDamage = 8000 ;
            speed = 640 / 60f ;
            lifetime = 640 / speed ;
            intervalBullets = 2 ;
            bulletInterval = 1 ;
            intervalAngle = 0 ;
            intervalSpread = 0 ;
            intervalRandomSpread = 0 ;
            intervalBullet = new BulletType() {{
                for (int i : Mathf.signs) {
                    spawnBullets.addAll(
                            new BasicBulletType(6, 1000) {{
                                lifetime = 40;
                                weaveScale = 1;
                                weaveMag = i * 4;
                                width = 7;
                                height = 10;
                                trailWidth = 3;
                                trailLength = 40;
                            }}
                    );
                }
            }};
        }};
        //TODO
        //FIXME
        haojieyijieduan = new XianShangUnitType("haojieyijieduan"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            //constructor = XianShangUnitEntityShenSheng::new ;
            //immunities.addAll(Vars.content.statusEffects());
            speed = 0.2f;
            accel = 0.04f;
            drag = 0.04f;
            rotateSpeed = 1f;
            flying = true;
            lowAltitude = true;
            health = 200000;
            deathExplosionEffect = Fx.none ;
            deathSound = Sounds.none ;
            //immunise(this,false,1);
            engineOffset = 38;
            engineSize = 7.3f;
            hitSize = 240f;
            armor = 458f;
            targetFlags = new BlockFlag[]{BlockFlag.core, null};
            ammoType = new ItemAmmoType(Items.thorium);
            engineSize = 20 ;
            engineOffset = 145 ;
            buildSpeed = 15 ;
            mineTier = 5 ;
            mineSpeed = 20 ;
            payloadCapacity = 10000 ;
            itemCapacity = 1000 ;
            //engines.add(new SYBSUnitEngine(0,0,40,315));

            var haloProgress = DrawPart.PartProgress.warmup;
            Color haloColor = Pal.redderDust;
            float haloY = -15f, haloRotSpeed = 1.5f;

            var circleProgress = DrawPart.PartProgress.warmup.delay(0.9f);
            var circleColor = haloColor;
            float circleY = 0f, circleRad = 16.5f, circleRotSpeed = 3.5f, circleStroke = 1.6f;
            Weapon jianzaipao =                     new Weapon("haojie-fupaotwo"){
                public float curSize = 16 ;
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
                    x = 75f;
                    y = 55f;
                    rotateSpeed = 2f;
                    reload = 120f;
                    shootSound = Sounds.railgun;
                    shadow = 7f;
                    rotate = true;
                    recoil = 0.5f;
                    shootY = 7.25f;
                    bullet = guidaopao;
                }};
            weapons.add(
                    new Weapon("haojie-fupaoone"){
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
                            x = 56f;
                            y = 70f;
                            rotateSpeed = 2f;
                            reload = 30f;
                            shootSound = Sounds.shootBig;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            bullet = fragBullet;
                            inaccuracy = 5 ;
                        }},
                    new Weapon("haojie-fupaotwo"){
                        public float curSize = 16 ;
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
                            x = 75f;
                            y = 55f;
                            rotateSpeed = 2f;
                            reload = 120f;
                            shootSound = Sounds.cannon;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            bullet = guidaopao;
                        }},
                    new Weapon("haojie-fupaoone"){
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
                            y = -56f;
                            x = 66f;
                            reload = 30f;
                            ejectEffect = Fx.casing1;
                            rotateSpeed = 7f;
                            shake = 1f;
                            shootSound = Sounds.shootBig;
                            rotate = true;
                            shadow = 12f;
                            shootY = 7.25f;
                            bullet = fragBullet;
                            inaccuracy = 5 ;
                        }});
            weapons.add(
                    new Weapon("haojie-fuzhupao"){
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
                            mirror = false ;
                            shake = 4f;
                            shootY = 9f;
                            x = 0f;
                            y = 0f;
                            rotateSpeed = 2f;
                            reload = 60f;
                            recoil = 4f;
                            shadow = 20f;
                            rotate = true;
                            range = 640 ;
                            rotate = true;
                            shootSound = Sounds.shootSmite ;
                            shoot = new ShootMulti(new ShootAlternate(){{
                                spread = 3.3f * 1.9f;
                                shots = barrels = 10;
                            }}, new ShootHelix(){{
                                scl = 4f;
                                mag = 3f;
                            }});

                            bullet = new BasicBulletType(14,5500){{
                                buildingDamageMultiplier = 1.5f ;
                                sprite = "large-orb";
                                width = 17f;
                                height = 21f;
                                hitSize = 8f;
                                status = SYBSStatusEffects.duidies.get("paichi").get(0) ;
                                statusDuration = 300 ;
                                shootEffect = new MultiEffect(Fx.shootTitan, Fx.colorSparkBig, new WaveEffect(){{
                                    colorFrom = colorTo = Pal.redderDust;
                                    lifetime = 12f;
                                    sizeTo = 20f;
                                    strokeFrom = 3f;
                                    strokeTo = 0.3f;
                                }});
                                smokeEffect = Fx.shootSmokeSmite;
                                ammoMultiplier = 1;
                                pierceCap = 40;
                                pierce = true;
                                pierceBuilding = true;
                                hitColor = backColor = trailColor = Pal.redderDust;
                                frontColor = Color.white;
                                trailWidth = 2.8f;
                                trailLength = 9;
                                hitEffect = Fx.hitBulletColor;
                                despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect(){{
                                    sizeTo = 30f;
                                    colorFrom = colorTo = Pal.redderDust;
                                    lifetime = 12f;
                                }});

                                trailRotation = true;
                                trailEffect = Fx.disperseTrail;
                                trailInterval = 3f;
                                lifetime = 130 ;
                                intervalBullet = new LightningBulletType(){{
                                    damage = 2750;
                                    status = SYBSStatusEffects.duidies.get("paichi").get(0) ;
                                    statusDuration = 300 ;
                                    buildingDamageMultiplier = 1.5f ;
                                    collidesAir = false;
                                    ammoMultiplier = 1f;
                                    lightningColor = Pal.redderDust;
                                    lightningLength = 5;
                                    lightningLengthRand = 10;
                                    lightningType = new BulletType(0.0001f, 550f){{
                                        status = SYBSStatusEffects.duidies.get("paichi").get(0) ;
                                        statusDuration = 300 ;
                                        buildingDamageMultiplier = 1.5f ;
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
            weapons.add(
                    new Weapon("haojie-fupaotwo"){
                        public float curSize = 16 ;
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
                            x = 75f;
                            y = 60f;
                            rotateSpeed = 2f;
                            reload = 120f;
                            shootSound = Sounds.railgun;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            bullet = guidaopao2;
                        }},
                    new Weapon("haojie-fupaotwo"){
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
                            x = 70f;
                            y = 40f;
                            rotateSpeed = 2f;
                            reload = 30f;
                            shootSound = Sounds.laserblast;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            bullet = fragBullett;
                        }},
                    new Weapon("haojie-fupaotwo"){
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
                            x = 75f;
                            y = -40f;
                            rotateSpeed = 2f;
                            reload = 15f;
                            shootSound = Sounds.missile;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            bullet = daodan;
                            inaccuracy = 20 ;
                        }},
                    new Weapon("haojie-fupaotwo"){
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
                            x = 90f;
                            y = 50f;
                            rotateSpeed = 2f;
                            reload = 30f;
                            shootSound = Sounds.laserblast;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            bullet = fragBullett;
                        }}) ;
            weapons.add(
                    new Weapon("haojie-jidian"){
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
                    x = 0f;
                    y = -50f;
                    rotateSpeed = 2f;
                    reload = 900f;
                    shootSound = Sounds.shockBlast;
                    shadow = 7f;
                    rotate = true;
                    recoil = 0.5f;
                    shootY = 7.25f;
                    bullet = jidian;
                }});
        }};
        haojieerjieduan = new XianShangUnitType("haojieerjieduan"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            //constructor = XianShangUnitEntityShenSheng::new ;
            //immunities.addAll(Vars.content.statusEffects());
            speed = 0.2f;
            accel = 0.04f;
            drag = 0.04f;
            rotateSpeed = 1f;
            flying = true;
            lowAltitude = true;
            health = 400000;
            deathExplosionEffect = Fx.none ;
            deathSound = Sounds.none ;
            //immunise(this,false,1);
            engineOffset = 38;
            engineSize = 7.3f;
            hitSize = 240f;
            armor = 530f;
            targetFlags = new BlockFlag[]{BlockFlag.core, null};
            ammoType = new ItemAmmoType(Items.thorium);
            engineSize = 20 ;
            engineOffset = 145 ;
            buildSpeed = 15 ;
            mineTier = 5 ;
            mineSpeed = 20 ;
            payloadCapacity = 10000 ;
            itemCapacity = 1000 ;
            //engines.add(new SYBSUnitEngine(0,0,40,315));
            BulletType fragBullett = new GaiLvMiaoLaserBulletType(){{
                damage = 4000;
                gailv = 100 ;
                lightningLength = 800;
                length = 800;
                collidesAir = true;
                ammoMultiplier = 1f;
                status = StatusEffects.shocked;
                statusDuration = 300f;
                colors[0] = Pal.redderDust.cpy().mul(1f, 1f, 1f, 0.4f) ;
                colors[1] = Pal.redderDust ;
                lightningType = new BulletType(0.0001f, 2000f){{
                    lightningColor = hitColor = Pal.redderDust;
                    lifetime = Fx.lightning.lifetime;
                    hitEffect = Fx.hitLancer;
                    despawnEffect = Fx.none;
                    status = StatusEffects.shocked;
                    statusDuration = 300f;
                    hittable = false;
                    lightColor = Color.white;
                    collidesAir = true;
                }};
            }};
            var haloProgress = DrawPart.PartProgress.warmup;
            Color haloColor = Pal.redderDust;
            float haloY = -15f, haloRotSpeed = 1.5f;

            var circleProgress = DrawPart.PartProgress.warmup.delay(0.9f);
            var circleColor = haloColor;
            float circleY = 0f, circleRad = 16.5f, circleRotSpeed = 3.5f, circleStroke = 1.6f;
            Weapon jianzaipao = new Weapon("haojie-fupaotwo"){
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
                    x = 75f;
                    y = 55f;
                    rotateSpeed = 2f;
                    reload = 30f;
                    shootSound = Sounds.laserblast;
                    shadow = 7f;
                    rotate = true;
                    recoil = 0.5f;
                    shootY = 7.25f;
                    shoot = new ShootSpread(){{
                        shots = 4 ;
                        spread = 8 ;
                    }};
                    bullet = fragBullett;
                }};
            weapons.add(
                    new Weapon("haojie-fupaoone"){
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
                            x = 56f;
                            y = 70f;
                            rotateSpeed = 2f;
                            reload = 30f;
                            shootSound = Sounds.shootBig;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            bullet = fragBullet.copy();
                            bullet.damage *= 1.25f ;
                            bullet.splashDamage *= 1.25f ;
                            bullet.fragBullet = fragBullet.fragBullet.copy() ;
                            bullet.fragBullet.damage *= 1.25f ;
                            bullet.fragBullet.splashDamage *= 1.25f ;
                            inaccuracy = 5 ;
                        }},
                    new Weapon("haojie-fupaotwo"){
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
                            x = 75f;
                            y = 55f;
                            rotateSpeed = 2f;
                            reload = 120f;
                            range = 800 ;
                            shootSound = Sounds.railgun;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            shoot = new ShootSpread(){{
                                shots = 4 ;
                                spread = 8 ;
                            }};
                            bullet = guidaopao2;
                        }},
                    new Weapon("haojie-fupaoone"){
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
                            y = -56f;
                            x = 66f;
                            reload = 30f;
                            ejectEffect = Fx.casing1;
                            rotateSpeed = 7f;
                            shake = 1f;
                            shootSound = Sounds.shootBig;
                            rotate = true;
                            shadow = 12f;
                            shootY = 7.25f;
                            bullet = fragBullet.copy();
                            bullet.fragBullet = fragBullet.fragBullet.copy() ;
                            bullet.damage *= 1.25f ;
                            bullet.splashDamage *= 1.25f ;
                            bullet.fragBullet.damage *= 1.25f ;
                            bullet.fragBullet.splashDamage *= 1.25f ;
                            inaccuracy = 5 ;
                        }});
            weapons.add(
                    new Weapon("haojie-fuzhupao"){
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
                            mirror = false ;
                            shake = 4f;
                            shootY = 9f;
                            x = 0f;
                            y = 0f;
                            rotateSpeed = 2f;
                            reload = 60f;
                            recoil = 4f;
                            shadow = 20f;
                            rotate = true;
                            range = 640 ;
                            rotate = true;
                            shootSound = Sounds.shootSmite ;
                            shoot = new ShootMulti(new ShootAlternate(){{
                                spread = 3.3f * 1.9f;
                                shots = barrels = 10;
                            }}, new ShootHelix(){{
                                scl = 4f;
                                mag = 3f;
                            }});

                            bullet = new BasicBulletType(14,5500){{
                                buildingDamageMultiplier = 1.5f ;
                                sprite = "large-orb";
                                width = 17f;
                                height = 21f;
                                hitSize = 8f;
                                status = SYBSStatusEffects.duidies.get("paichi").get(0) ;
                                statusDuration = 300 ;
                                shootEffect = new MultiEffect(Fx.shootTitan, Fx.colorSparkBig, new WaveEffect(){{
                                    colorFrom = colorTo = Pal.redderDust;
                                    lifetime = 12f;
                                    sizeTo = 20f;
                                    strokeFrom = 3f;
                                    strokeTo = 0.3f;
                                }});
                                smokeEffect = Fx.shootSmokeSmite;
                                ammoMultiplier = 1;
                                pierceCap = 40;
                                pierce = true;
                                pierceBuilding = true;
                                hitColor = backColor = trailColor = Pal.redderDust;
                                frontColor = Color.white;
                                trailWidth = 2.8f;
                                trailLength = 9;
                                hitEffect = Fx.hitBulletColor;
                                despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect(){{
                                    sizeTo = 30f;
                                    colorFrom = colorTo = Pal.redderDust;
                                    lifetime = 12f;
                                }});

                                trailRotation = true;
                                trailEffect = Fx.disperseTrail;
                                trailInterval = 3f;
                                lifetime = 130 ;
                                intervalBullet = new LightningBulletType(){{
                                    damage = 2750;
                                    status = SYBSStatusEffects.duidies.get("paichi").get(0) ;
                                    statusDuration = 300 ;
                                    buildingDamageMultiplier = 1.5f ;
                                    collidesAir = false;
                                    ammoMultiplier = 1f;
                                    lightningColor = Pal.redderDust;
                                    lightningLength = 5;
                                    lightningLengthRand = 10;
                                    lightningType = new BulletType(0.0001f, 550f){{
                                        status = SYBSStatusEffects.duidies.get("paichi").get(0) ;
                                        statusDuration = 300 ;
                                        buildingDamageMultiplier = 1.5f ;
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
            weapons.add(
                    new Weapon("haojie-fupaotwo"){
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
                    x = 75f;
                    y = 60f;
                    rotateSpeed = 2f;
                    reload = 120f;
                    shootSound = Sounds.cannon;
                    shadow = 7f;
                    rotate = true;
                    recoil = 0.5f;
                    shootY = 7.25f;
                    shoot = new ShootSpread(){{
                        shots = 4 ;
                        spread = 8 ;
                    }};
                    bullet = guidaopao;
                }},
                    new Weapon("haojie-fupaotwo"){
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
                            x = 70f;
                            y = 40f;
                            rotateSpeed = 2f;
                            reload = 15f;
                            shootSound = Sounds.missile;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            bullet = daodan;
                            inaccuracy = 20 ;
                        }},
                    new Weapon("haojie-fupaotwo"){
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
                            x = 75f;
                            y = -40f;
                            rotateSpeed = 2f;
                            reload = 30f;
                            shootSound = Sounds.laserblast;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            bullet = fragBullett;
                        }},
                    new Weapon("haojie-fupaotwo"){
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
                            x = 90f;
                            y = 50f;
                            rotateSpeed = 2f;
                            reload = 30f;
                            shootSound = Sounds.laserblast;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            bullet = fragBullett;
                        }}) ;
            weapons.add(
                    new Weapon("haojie-jidian"){
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
                            x = 0f;
                            y = -50f;
                            rotateSpeed = 2f;
                            reload = 900f;
                            shootSound = Sounds.laserblast;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            bullet = jidian;
                        }});
        }};
        haojieerjieduan.weapons.add(new Weapon("haojie-zhupao"){
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
                        var haloProgress = DrawPart.PartProgress.warmup;
                        Color haloColor = Pal.redderDust;
                        float haloY = -15f, haloRotSpeed = 1.5f;

                        var circleProgress = DrawPart.PartProgress.warmup.delay(0.9f);
                        var circleColor = haloColor;
                        float circleY = 0f, circleRad = 16.5f, circleRotSpeed = 3.5f, circleStroke = 1.6f;
                        shake = 4f;
                        shootY = 9f;
                        x = 40f;
                        y = 40f;
                        rotateSpeed = 2f;
                        reload = 2700f;
                        recoil = 4f;
                        shootSound = Sounds.laserbig;
                        shadow = 20f;
                        //rotate = true;
                        //range = 1600 ;
                        continuous = true ;
                        bullet = new JiZengBulletType(25){{
                            chixushijian = lifetime = 600 ;
                            damage = 25f;
                            width = 20f;
                            length = 1000f;
                            drawSize = 1000f ;
//                                status = SYBSStatusEffects.tianfa ;
//                                statusDuration = 300 ;
//                                shootEffect = Fx.shockwave;
                            colors = new Color[]{Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.white};
                        }};
                    }});
        haojiesanjieduan = new XianShangUnitType("haojiesanjieduan"){
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
            //constructor = XianShangUnitEntityShenSheng::new ;
            //immunities.addAll(Vars.content.statusEffects());
            speed = 0.2f;
            accel = 0.04f;
            drag = 0.04f;
            rotateSpeed = 1f;
            flying = true;
            lowAltitude = true;
            health = 600000;
            //immunise(this,false,1);
            engineOffset = 38;
            engineSize = 7.3f;
            hitSize = 240f;
            armor = 560f;
            targetFlags = new BlockFlag[]{BlockFlag.core, null};
            ammoType = new ItemAmmoType(Items.thorium);
            engineSize = 20 ;
            engineOffset = 145 ;
            buildSpeed = 15 ;
            mineTier = 5 ;
            mineSpeed = 20 ;
            payloadCapacity = 10000 ;
            itemCapacity = 1000 ;
            //engines.add(new SYBSUnitEngine(0,0,40,315));
            BulletType fragBullett = new BaiFenBiLaserBulletType(){{
                damage = 4000;
                baifenbi = 0f ;
                lightningLength = 800;
                length = 800;
                collidesAir = true;
                ammoMultiplier = 1f;
                status = StatusEffects.shocked;
                statusDuration = 300f;
                colors[0] = Pal.redderDust.cpy().mul(1f, 1f, 1f, 0.4f) ;
                colors[1] = Pal.redderDust ;
                lightningType = new BulletType(0.0001f, 2000f){{
                    lightningColor = hitColor = Pal.redderDust;
                    lifetime = Fx.lightning.lifetime;
                    hitEffect = Fx.hitLancer;
                    despawnEffect = Fx.none;
                    status = StatusEffects.shocked;
                    statusDuration = 300f;
                    hittable = false;
                    lightColor = Color.white;
                    collidesAir = true;
                }};
            }};
            var haloProgress = DrawPart.PartProgress.warmup;
            Color haloColor = Pal.redderDust;
            float haloY = -15f, haloRotSpeed = 1.5f;

            var circleProgress = DrawPart.PartProgress.warmup.delay(0.9f);
            var circleColor = haloColor;
            float circleY = 0f, circleRad = 16.5f, circleRotSpeed = 3.5f, circleStroke = 1.6f;
            Weapon jianzaipao = new Weapon("haojie-fupaotwo"){
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
                    x = 75f;
                    y = 55f;
                    rotateSpeed = 2f;
                    reload = 30f;
                    shootSound = Sounds.laserblast;
                    shadow = 7f;
                    rotate = true;
                    recoil = 0.5f;
                    shootY = 7.25f;
                    shoot = new ShootAlternate(){{
                       shots = 4 ;
                       barrels = 4 ;
                       spread = 8 ;
                    }};
                    bullet = fragBullett;
                }};
            weapons.add(
                    new Weapon("haojie-fupaoone"){
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
                            x = 56f;
                            y = 70f;
                            rotateSpeed = 2f;
                            reload = 30f;
                            shootSound = Sounds.shootBig;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            bullet = fragBullet.copy();
                            bullet.fragBullet = fragBullet.fragBullet.copy() ;
                            bullet.damage *= 1.5f ;
                            bullet.splashDamage *= 1.5f ;
                            bullet.fragBullet.damage *= 1.5f ;
                            bullet.fragBullet.splashDamage *= 1.5f ;
                            inaccuracy = 5 ;
                        }},
                    new Weapon("haojie-fupaotwo"){
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
                            x = 75f;
                            y = 55f;
                            rotateSpeed = 2f;
                            reload = 120f;
                            range = 640 ;
                            shootSound = Sounds.railgun;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            shoot = new ShootAlternate(){{
                                shots = 4 ;
                                barrels = 4 ;
                                spread = 8 ;
                            }};
                            bullet = guidaopao2;
                        }},
                    new Weapon("haojie-fupaoone"){
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
                            y = -56f;
                            x = 66f;
                            reload = 30f;
                            ejectEffect = Fx.casing1;
                            rotateSpeed = 7f;
                            shake = 1f;
                            shootSound = Sounds.shootBig;
                            rotate = true;
                            shadow = 12f;
                            shootY = 7.25f;
                            bullet = fragBullet.copy();
                            bullet.fragBullet = fragBullet.fragBullet.copy() ;
                            bullet.damage *= 1.5f ;
                            bullet.splashDamage *= 1.5f ;
                            bullet.fragBullet.damage *= 1.5f ;
                            bullet.fragBullet.splashDamage *= 1.5f ;
                            inaccuracy = 5 ;
                        }});
            weapons.add(
                    new Weapon("haojie-fuzhupao"){
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
                            mirror = false ;
                            shake = 4f;
                            shootY = 9f;
                            x = 0f;
                            y = 0f;
                            rotateSpeed = 2f;
                            reload = 60f;
                            recoil = 4f;
                            shadow = 20f;
                            rotate = true;
                            range = 640 ;
                            rotate = true;
                            shootSound = Sounds.shootSmite ;
                            shoot = new ShootMulti(new ShootAlternate(){{
                                spread = 3.3f * 1.9f;
                                shots = barrels = 10;
                            }}, new ShootHelix(){{
                                scl = 4f;
                                mag = 3f;
                            }});

                            bullet = new BasicBulletType(14,5500){{
                                buildingDamageMultiplier = 1.5f ;
                                sprite = "large-orb";
                                width = 17f;
                                height = 21f;
                                hitSize = 8f;
                                pierceArmor = true ;
                                status = SYBSStatusEffects.duidies.get("paichi").get(0) ;
                                statusDuration = 300 ;
                                shootEffect = new MultiEffect(Fx.shootTitan, Fx.colorSparkBig, new WaveEffect(){{
                                    colorFrom = colorTo = Pal.redderDust;
                                    lifetime = 12f;
                                    sizeTo = 20f;
                                    strokeFrom = 3f;
                                    strokeTo = 0.3f;
                                }});
                                smokeEffect = Fx.shootSmokeSmite;
                                ammoMultiplier = 1;
                                pierceCap = 40;
                                pierce = true;
                                pierceBuilding = true;
                                hitColor = backColor = trailColor = Pal.redderDust;
                                frontColor = Color.white;
                                trailWidth = 2.8f;
                                trailLength = 9;
                                hitEffect = Fx.hitBulletColor;
                                despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect(){{
                                    sizeTo = 30f;
                                    colorFrom = colorTo = Pal.redderDust;
                                    lifetime = 12f;
                                }});

                                trailRotation = true;
                                trailEffect = Fx.disperseTrail;
                                trailInterval = 3f;
                                lifetime = 130 ;
                                intervalBullet = new LightningBulletType(){{
                                    damage = 2750;
                                    status = SYBSStatusEffects.duidies.get("paichi").get(0) ;
                                    statusDuration = 300 ;
                                    buildingDamageMultiplier = 1.5f ;
                                    collidesAir = false;
                                    ammoMultiplier = 1f;
                                    lightningColor = Pal.redderDust;
                                    lightningLength = 5;
                                    lightningLengthRand = 10;
                                    lightningType = new BulletType(0.0001f, 550f){{
                                        status = SYBSStatusEffects.duidies.get("paichi").get(0) ;
                                        statusDuration = 300 ;
                                        buildingDamageMultiplier = 1.5f ;
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
            weapons.add(
                    new Weapon("haojie-fupaotwo"){
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
                            x = 75f;
                            y = 60f;
                            rotateSpeed = 2f;
                            reload = 120f;
                            shootSound = Sounds.cannon;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            bullet = guidaopao;
                        }},
                    new Weapon("haojie-fupaotwo"){
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
                            x = 70f;
                            y = 40f;
                            rotateSpeed = 2f;
                            reload = 30f;
                            shootSound = Sounds.laserblast;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            bullet = fragBullett;
                        }},
                    new Weapon("haojie-fupaotwo"){
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
                            x = 75f;
                            y = -40f;
                            rotateSpeed = 2f;
                            reload = 30f;
                            shootSound = Sounds.laserblast;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            bullet = fragBullett;
                        }},
                    new Weapon("haojie-fupaotwo"){
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
                            x = 90f;
                            y = 50f;
                            rotateSpeed = 2f;
                            reload = 30f;
                            shootSound = Sounds.laserblast;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            bullet = fragBullett;
                        }}) ;
            weapons.add(
                    new Weapon("haojie-jidian"){
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
                            x = 0f;
                            y = -50f;
                            rotateSpeed = 2f;
                            reload = 900f;
                            shootSound = Sounds.laserblast;
                            shadow = 7f;
                            rotate = true;
                            recoil = 0.5f;
                            shootY = 7.25f;
                            bullet = jidian;
                        }});
        }};
        haojiesanjieduan.weapons.add(new Weapon("haojie-zhupao"){
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
                var haloProgress = DrawPart.PartProgress.warmup;
                Color haloColor = Pal.redderDust;
                float haloY = -15f, haloRotSpeed = 1.5f;

                var circleProgress = DrawPart.PartProgress.warmup.delay(0.9f);
                var circleColor = haloColor;
                float circleY = 0f, circleRad = 16.5f, circleRotSpeed = 3.5f, circleStroke = 1.6f;
                shake = 4f;
                shootY = 9f;
                x = 40f;
                y = 40f;
                rotateSpeed = 2f;
                reload = 2700f;
                recoil = 4f;
                shootSound = Sounds.laserbig;
                shadow = 20f;
                //rotate = true;
                //range = 1600 ;
                continuous = true ;
                bullet = new JiZengBulletType(25){{
                    chixushijian = lifetime = 600 ;
                    damage = 25f;
                    width = 20f;
                    length = 1000f;
                    drawSize = 1000f ;
//                                status = SYBSStatusEffects.tianfa ;
//                                statusDuration = 300 ;
//                                shootEffect = Fx.shockwave;
                    colors = new Color[]{Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.white};
                }};
            }});
        shenshengjidanwei.add(weiyang,haojieyijieduan,haojieerjieduan,haojiesanjieduan) ;
        Weapon shougejiguang = new Weapon("shouge-fuzhupao") {{
            x = 0 ;
            y = 0 ;
            mirror = false ;
            reload = 600 / 2f / 1.25f * 4 ;
            rotate = true ;
            bullet = new LaserBulletType() {{
                length = 65 * tilesize ;
                damage = 4500 * 4 ;
                status = SYBSStatusEffects.huaxuedianran ;
                statusDuration = 30 * toSeconds ;
                width = 1.5f * tilesize ;
                colors = new Color[]{SYBSPal.yaofeng.cpy().mul(1f, 1f, 1f, 0.4f), SYBSPal.yaofeng, Color.white} ;
            }} ;
            shootSound = Sounds.laser ;
            shake = 2 ;
        }};
        Weapon shougefantan = new Weapon("shouge-fupao") {{
            x = 0 ;
            y = 0 ;
            mirror = false ;
            reload = 600 / 2f / 1.25f * 4 ;
            rotate = true ;
            shoot.shots = 10 ;
            shoot.shotDelay = 6 ;
            bullet = new FanTanBullet() {{
                damage = 960 * 4 ;
                status = SYBSStatusEffects.huaxuedianran ;
                statusDuration = 10 * toSeconds ;
                width = 0.25f * tilesize ;
                height = width * 5 ;
                lightColor = hitColor = backColor = Pal.redderDust ;
                speed *= 6f ;
                lifetime *= 2f ;
            }} ;
            shootSound = Sounds.laser ;
            shake = 2 ;
        }};
        shougejieti = new JieTiUnitType("shougejieti") {
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
                health = 1650000 / 51f ;
                dancixianshang = 0 ;
                miaoxianshang = 0 ;
                fenxianshang = 0 ;
                hitSize = 4 * tilesize ;
                speed = 5f ;
                rotateSpeed = 5 ;
                useUnitCap = false ;
                playerControllable = false;
                logicControllable = false;
                aiController = JieTiAI::new;
                hidden = true ;
                hoverable = false ;
                isEnemy = false ;
                weapons.add(shougejiguang, shougefantan) ;
            }};
        shouge = new DuoJieTiUnitType("shouge") {
            @Override
            public void load() {
                super.load();
                loadWeapons(this);
            }
            {
                health = 1650000 ;
                armor = 150 ;
                dancixianshang = 4000 ;
                miaoxianshang = 40000 ;
                //fenxianshang = 1000000 ;
                hitSize = 4 * tilesize ;
                speed = 5 ;
                //rotateSpeed = 3 ;
                jietiAmount = 50 ;
                jietitype = shougejieti ;
                circleTarget = true ;
                aiController = FlyingAI::new;
                outlines = false ;
                weapons.add(shougejiguang, shougefantan) ;
                weapons.add(new Weapon("shouge-zhupao"){{
                    x = 0 ;
                    y = 0 ;
                    mirror = false ;
                    reload = 1200 ;
                    rotate = false ;
                    continuous = true ;
                    bullet = new ZhongJiLengJingBulletType() {{
                        length = 60 * tilesize ;
                        damage = 5000f / toSeconds ;
                        damageInterval = 1 ;
                        status = SYBSStatusEffects.huaxuedianran ;
                        statusDuration = toMinutes ;
                        colors = new Color[]{SYBSPal.yaofeng.cpy().mul(1f, 1f, 1f, 0.4f), SYBSPal.yaofeng, Color.white} ;
                        width = 0.85f * tilesize ;
                        lifetime = 900 ;
                        sanshetype = new BaiFenBiChiXuJiGuangBulletType() {{
                            length = 120 * tilesize ;
                            damage = 5000 / toSeconds ;
                            damageInterval = 1 ;
                            status = SYBSStatusEffects.huaxuedianran ;
                            statusDuration = toMinutes ;
                            colors = new Color[]{SYBSPal.yaofeng.cpy().mul(1f, 1f, 1f, 0.4f), SYBSPal.yaofeng, Color.white} ;
                            width = 0.85f * tilesize ;
                            lifetime = 10 ;
                            baifenbi = 0 ;
                        }};
                    }} ;
                    shootSound = Sounds.laserbig ;
                    shake = 2 ;
                }});
            }};
        shenshengjidanwei.add(shouge, shougejieti) ;
    }
    public static class SYBSUnitType extends UnitType implements Cloneable{
        public SYBSUnitType(String name) {
            super(name);
        }
        public SYBSUnitType copy(){
            try{
                SYBSUnitType copy = (SYBSUnitType) clone();
                copy.id = (short)Vars.content.getBy(ContentType.unit).size;
                Vars.content.handleContent(copy);
                copy.init();
                return copy;
            }catch(Exception e){
                throw new RuntimeException("death to checked exceptions", e);
            }
        }
    }
}
