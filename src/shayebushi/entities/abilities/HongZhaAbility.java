package shayebushi.entities.abilities;

import arc.*;
import arc.audio.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Vec2;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.abilities.Ability;
import mindustry.entities.abilities.MoveEffectAbility;
import mindustry.entities.bullet.BombBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ExplosionBulletType;
import mindustry.entities.effect.ExplosionEffect;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.effect.WrapEffect;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.unit.MissileUnitType;
import shayebushi.SYBSStatusEffects;

import static mindustry.Vars.*;

public class HongZhaAbility extends Ability {
    private static final Seq<Healthc> all = new Seq<>();
    public boolean targetBuilding = true ;
    public float damage = 1, reload = 100, range = 114514;
    public Effect healEffect = Fx.heal,
            hitEffect = new MultiEffect(Fx.massiveExplosion, new WrapEffect(Fx.dynamicSpikes, Team.crux.color, 24f), new WaveEffect(){{
        //colorFrom = colorTo = color;
        colorFrom = colorTo = Team.crux.color;
        sizeTo = 40f;
        lifetime = 12f;
        strokeFrom = 4f;
    }},Fx.sapExplosion)
    , damageEffect = Fx.chainLightning;
    public StatusEffect status = SYBSStatusEffects.zhongdufushi;
    public Sound shootSound = Sounds.largeCannon;
    public float statusDuration = 60f * 6f;
    public float x, y;
    public boolean targetGround = true, targetAir = true, hitBuildings = true, hitUnits = true;
    public int maxTargets = 25;
    public float healPercent = 3f;
    public Rand r = new Rand() ;
    public float layer = Layer.bullet - 0.001f, blinkScl = 20f, blinkSize = 0.1f;
    public float effectRadius = 5f, sectorRad = 0.14f, rotateSpeed = 0.5f;
    public int sectors = 5;
    public Color color = Pal.accent;
    public boolean useAmmo = true;
    private static Vec2 paramPos = new Vec2();
    protected float timer, curStroke;
    protected boolean anyNearby = false;
    public float amount ;
    public BulletType b ;
    public UnitType u ;
    public boolean fushi = false ;
    HongZhaAbility(){}

    public HongZhaAbility(int maxTargets , float damage, float reload, float amount, String osn){
        this.maxTargets = maxTargets ;
        this.damage = damage;
        this.reload = reload;
        this.amount = amount;
        this.b = new ExplosionBulletType(damage,80){{
            hitEffect = new MultiEffect(Fx.massiveExplosion, new WrapEffect(Fx.dynamicSpikes, Team.crux.color, 24f), new WaveEffect(){{
                //colorFrom = colorTo = color;
                colorFrom = colorTo = Team.crux.color;
                sizeTo = 40f;
                lifetime = 12f;
                strokeFrom = 4f;
            }},Fx.titanExplosion);
            collidesAir = true ;
            lifetime = 1 ;
            hitShake = 5f ;
            collidesTeam = false ;
            //killShooter = false ;
            status = fushi ? SYBSStatusEffects.zhongdufushi : StatusEffects.none;
            hitSound = Sounds.largeExplosion ;
            buildingDamageMultiplier = 0.25f ;
        }};
        this.u = new MissileUnitType("hongzha-daodan-" + osn){{
            //targetAir = false;
            speed = 4.6f;
            lifetime = 60 * 3 ;
            maxRange = 5f;
            outlineColor = Pal.darkOutline;
            health = 25000;
            homingDelay = 10f;
            lowAltitude = true;
            engineSize = 3f;
            engineColor = Pal.redderDust;
            engineLayer = Layer.effect;
            deathExplosionEffect = Fx.none;
            loopSoundVolume = 0.1f;
            abilities.add(new MoveEffectAbility(){{
                effect = Fx.missileTrailSmoke;
                rotation = 180f;
                y = -9f;
                color = Pal.redderDust;
                interval = 7f;
            }}) ;
            weapons.add(new Weapon(){{
                shootCone = 360f;
                mirror = false;
                reload = 1f;
                shootOnDeath = true;
                bullet = b ;
            }});
//            Weapon w = weapons.get(0).copy() ;
//            w.y = -w.y ;
//            w.x = -w.x ;
//            weapons.add(w) ;
        }};

    }

    @Override
    public String localized(){
        return Core.bundle.format("ability.hongzha", maxTargets,amount,damage,reload/60);
    }

    @Override
    public void draw(Unit unit){
        super.draw(unit);
        Draw.color(unit.team.color);
        Lines.stroke(10);
        Lines.arc(unit.x, unit.y, unit.hitSize * 1.15f, timer / reload, unit.rotation);
        Draw.reset();
        int len = Math.min(all.size, maxTargets);
        for (int i = 0 ; i < len ; i ++){
            if (all.get(i) instanceof Building bb) {
                drawww(unit,bb) ;
            }
            else if (all.get(i) instanceof Unit u){
                draww(unit,u) ;
            }
        }
    }
    public void draww(Unit unit,Unit u){
        //System.out.println(1);
        Draw.color(unit.team.color);
        Lines.stroke(5);
        Lines.circle(u.x,u.y,u.hitSize * 1.3f) ;
        Lines.line(u.x - u.hitSize * 1.9f,u.y,u.x + u.hitSize * 1.9f,u.y);
        Lines.line(u.x,u.y - u.hitSize * 1.9f,u.x,u.y + u.hitSize * 1.9f);
        Lines.circle(u.x,u.y,u.hitSize * 1.9f - u.hitSize * 0.6f * (timer / reload)) ;
        Draw.reset();
    }
    public void drawww(Unit unit,Building bb){
        Draw.color(unit.team.color);
        Lines.stroke(5);
        Lines.circle(bb.x,bb.y,bb.block.size * 4 * 1.3f) ;
        Lines.line(bb.x,bb.y - bb.block.size * 4 * 1.9f,bb.x,bb.y + bb.block.size * 4 * 1.9f);
        Lines.line(bb.x - bb.block.size * 4 * 1.9f,bb.y,bb.x + bb.block.size * 4 * 1.9f,bb.y);
        Lines.circle(bb.x,bb.y,bb.block.size * 4 * 1.9f - bb.block.size * 4 * 0.6f * (timer / reload)) ;
        Draw.reset();

    }
    @Override
    public void update(Unit unit){

        curStroke = Mathf.lerpDelta(curStroke, anyNearby ? 1 : 0, 0.09f);

        if(true){
            Tmp.v1.trns(unit.rotation - 90, x, y).add(unit.x, unit.y);
            float rx = Tmp.v1.x, ry = Tmp.v1.y;
            anyNearby = false;

            all.clear();

            if(hitUnits){
                Units.nearby(null, rx, ry, range, other -> {
                    if(other.team != unit.team &&other != unit && other.checkTarget(targetAir, targetGround) && other.targetable(unit.team) && (other.team != unit.team)){
                        all.add(other);
                    }
                });
            }
            if (targetBuilding){
                Units.nearbyBuildings( rx, ry, range, other -> {
                    if((other.team != unit.team)){
                        all.add(other);
                    }
                });
            }
            all.sort(h -> h.dst2(rx, ry));
            //System.out.println(len);
            int len = Math.min(all.size, maxTargets);
            for(int i = 0; i < len; i++) {
                for (int z = 0; z < amount; z++) {
                    Healthc other = all.get(i);
                    if (((Teamc) other).team() != unit.team && (timer + Time.delta) >= reload) {
                        anyNearby = true;
                        Unit uu = u.create(unit.team) ;
                        uu.x = unit.x + r.random(-80, 80) ;
                        uu.y = unit.y + r.random(-80, 80) ;
                        uu.rotation = Angles.angle(uu.x, uu.y, other.x(), other.y()) ;
                        uu.add();
                        if (other instanceof Teamc t && uu.isCommandable()) {
                            uu.command().attackTarget = t ;
                        }
                        shootSound.at(unit);
                    }
                }
//                if (all.get(i) instanceof Unit u) {
//                    System.out.println(u.type.localizedName);
//                }
            }
            if (!all.isEmpty() && anyNearby){
                timer = 0 ;
                anyNearby = false ;
            }
            timer += Time.delta ;
        }
    }
}
