package shayebushi;

import arc.func.Cons;
import arc.func.Cons2;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.geom.Vec2;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.StatusEffects;
import mindustry.core.Version;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.AIController;
import mindustry.entities.units.WeaponMount;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import shayebushi.entities.abilities.FixBarAbility;
import shayebushi.entities.abilities.FuShiAbility;
import shayebushi.entities.bullet.BaiFenBiBulletType;
import shayebushi.entities.bullet.BaiFenBiChiXuJiGuangBulletType;
import shayebushi.entities.bullet.BaiFenBiLaserBulletType;

import java.lang.reflect.Constructor;

public class SYBSStatusEffects {
    public static StatusEffect dangji , zhongdufushi , huimiesuyi , nothing , denglizihua , diaoling , huaxuedianran, fushe, shuaibian, qiangxiaodianji, fastttt, kuiluan, fangfushe;
    public static ObjectMap<String, Seq<SYBSStatusEffect>> duidies = new ObjectMap<>();
    public static void load(){
        dangji = new SYBSStatusEffect("dangji"){{
            color = Pal.gray;
            show = true ;
            speedMultiplierr = 0 ;
            reloadMultiplierr = 0 ;
        }};
        duidies.put("paichi", new Seq<>()) ;
        duidies.put("biaoji", new Seq<>()) ;
        makeDuiDie("paichi", 9, (s, i) -> {
            s.healthMultiplierr = 1 - 0.1f * i ;
            s.rawName = "paichi" ;
        });
        makeDuiDie("biaoji", 3, (s, i) -> {
            s.healthMultiplierr = 1 - 0.2f * i ;
            s.speedMultiplierr = 1 - 0.1f * i ;
            s.damageMultiplierr = 1 - 0.1f * i ;
            s.rawName = "biaoji" ;
            s.effect = new Effect(30, e -> {
                Draw.color(Color.white);
                for(int z = 0; z < 2; z++){
                    float rot = z * 360f/2 - Time.time * 0.5f;
                    Lines.arc(e.x, e.y, 5 + 3f, 0.14f, rot);
                }
            }) ;
            if (i == s.dm) {
                s.cons = u -> u.armor = 0 ;
            }
        });
        zhongdufushi = new SYBSStatusEffect("zhongdufushi"){{
            damagee = 4 ;
            color = Color.valueOf("33ff33");
            show = true ;
            speedMultiplierr = 0.5f ;
            reloadMultiplierr = 0.9f ;
            healthMultiplierr = 0.7f ;
            fushi = true ;
        }};
        huimiesuyi = new SYBSStatusEffect("huimiesuyi"){{
            damagee = 20 ;
            color = Pal.gray;
            show = true ;
            speedMultiplierr = 0.5f ;
            reloadMultiplierr = 0.9f ;
            huimie = true ;
            hus.addAll(SYBSShenShengUnitTypes.shenshengjidanwei) ;
            hus.remove(SYBSShenShengUnitTypes.weiyang) ;
        }};
        diaoling = new SYBSStatusEffect("diaoling"){{
            damagee = 20 ;
            color = Pal.gray;
            show = true ;
            speedMultiplierr = 0.05f ;
            reloadMultiplierr = 0.4f ;
            healthMultiplierr = 0.7f ;
        }};
        nothing = new SYBSStatusEffect("nothing"){{
            show = Version.build == -1 || ShaYeBuShi.tiaoshi;
            //boss = true ;
            //healthMultiplierr = 1.14f ;
        }};
        denglizihua = new SYBSStatusEffect("denglizihua"){{
            show = true ;
            healthMultiplierr = Float.POSITIVE_INFINITY ;
        }} ;
        huaxuedianran = new SYBSStatusEffect("huaxuedianran"){{
            damagee = 4.167f ;
            speedMultiplierr = 0.84f ;
            damageMultiplierr = 0.9f ;
            healthMultiplierr = 0.86f ;
        }};
        fushe = new SYBSStatusEffect("fushe"){{
           bai = 0.02f / 60 ;
           healthMultiplierr = 0.75f ;
           speedMultiplierr = 0.8f ;
        }};
        shuaibian = new SYBSStatusEffect("shuaibian"){{
           bai = 0.04f / 60 ;
           healthMultiplierr = 0.5f ;
           speedMultiplierr = 0.7f ;
        }};
        qiangxiaodianji = new SYBSStatusEffect("qiangxiaodianji"){{
            //bai = 0.04f ;
            healthMultiplierr = 0.95f ;
            speedMultiplierr = 0.8f ;
            init(() -> {
                opposite(StatusEffects.blasted);
                affinity(StatusEffects.wet, (unit, result, time) -> {
                    unit.damagePierce(30f);
                });
                affinity(zhongdufushi, (unit, result, time) -> {
                    unit.damagePierce(10f);
                });
                for (SYBSStatusEffect p : duidies.get("paichi")) {
                    affinity(p, (unit, result, time) -> {
                        unit.damagePierce(30f * p.dt);
                    });
                }
            });
        }};
        fastttt = new SYBSStatusEffect("fastttt") {{
            color = Pal.boostTo;
            speedMultiplierr = 1.2f ;
        }};
        kuiluan = new SYBSStatusEffect("kuiluan") {{
            color = Color.valueOf("454545") ;
            speedMultiplierr = -2 ;
            reloadMultiplier = 0.2f ;
            //buildSpeedMultiplierr = 0 ;
            healthMultiplierr = 0.8f ;
            effect = SYBSFx.kuiluan ;
            cons = u -> {
                effect.at(u.x, u.y, 0, color, u);
                //System.out.println((u.team.rules().rtsAi) + " " + (u.team.data().rtsAi != null));
                if (u.isCommandable() && u.command().targetPos != null && u.controller() instanceof AIController a) {
                    //u.lookAt(u.command().targetPos);
                    u.speedMultiplier *= 2 ;
//                    if (u.isFlying()) {
//                        u.speedMultiplier /= speedMultiplierr ;
//                    }
                    Vec2 v = u.command().targetPos ;
                    float angle = Angles.angle(u.x, u.y, v.x, v.y) ;
                    //System.out.println(ShaYeBuShi.circle(angle + 180, u.dst(v), u.x, u.y));
                    a.moveTo(ShaYeBuShi.circle(angle + 180, u.dst(v), u.x, u.y), 0) ;
                    u.speedMultiplier /= 2 ;
                }
                else if (u.controller() instanceof AIController a) {
                    u.speedMultiplier *= 2 ;
                    Teamc e = Units.closestTarget(u.team, u.x, u.y, Integer.MAX_VALUE) ;
                    Building b = Units.closestBuilding(u.team, u.x, u.y, Integer.MAX_VALUE, b2 -> b2.team != u.team) ;
                    float angle = b != null ? Angles.angle(u.x, u.y, b.x, b.y) : e != null ? Angles.angle(u.x, u.y, e.x(), e.y()) : u.rotation ;
                    Vec2 v = ShaYeBuShi.circle(angle + 180, u.dst(b != null ? b : e != null ? e : u), u.x, u.y) ;
                    //System.out.println(v);
                    a.moveTo(v, 0) ;
                    u.speedMultiplier /= 2 ;
                }
            } ;
        }} ;
        fangfushe = new SYBSStatusEffect("fangfushe") {{
            opposite(fushe, shuaibian) ;
            TransitionHandler v = (unit, result, time) -> {
                unit.unapply(fushe);
                unit.unapply(shuaibian);
            } ;
            trans(fushe, v);
            trans(shuaibian, v);
        }} ;
    }
    public static class SYBSStatusEffect extends StatusEffect{
        public SYBSStatusEffect(String name) {
            super(name);
        }
        public float damagee ;
        public float damageMultiplierr  = 1 , reloadMultiplierr = 1 , speedMultiplierr = 1 , healthMultiplierr = 1, buildSpeedMultiplierr = 1f;
        public boolean fushi = false ;
//        public float ft = 1 ;
        public float fu = 4 ;
        public float fm = 64 ;
//        public boolean fa = false ;
        public boolean huimie = false ;
        public Cons2<Unit, Seq<UnitType>> ht = (u, uts) -> {
            boolean h = false ;
            can :
            for (UnitType ut : uts) {
                for (Team t : Team.all){
                    if (t != u.team && t.data().countType(ut) > 0){
                        h = true ;
                        break can;
                    }
                }
            }
            if (h) {
                Ability[] a = {new FixBarAbility()};
                StatusEffect[] s = {};
                u.abilities(a);
                try {
//                    Method m = Object.class.getDeclaredMethod("clone") ;
//                    UnitType uut = ((UnitType)m.invoke(u.type)) ;
//                    ObjectSet<StatusEffect> st = ((ObjectSet<StatusEffect>)m.invoke(uut.immunities)) ;
//                    uut.immunities = st ;
//                    st.clear();
//                    u.type(uut) ;
                    Class<?> c = u.type.getClass() ;
                    Constructor<?> co = c.getConstructor(String.class) ;
                    String st = u.type.localizedName ;
                    u.type = (UnitType)co.newInstance(u.id) ;
                    u.type.load();
                    u.type.localizedName = st ;
                    u.type.immunities.clear();
                }
                catch (Throwable t){
                    t.printStackTrace();
                }
                for (WeaponMount m : u.mounts) {
                    m.weapon.bullet.status = StatusEffects.none;
                    m.weapon.bullet.fragBullets = 0;
                    if ((m.weapon.bullet instanceof BaiFenBiBulletType b && b.baifenbi >= 0.0025f) || (m.weapon.bullet instanceof BaiFenBiLaserBulletType bb && bb.baifenbi >= 0.05f) || (m.weapon.bullet instanceof BaiFenBiChiXuJiGuangBulletType bbb && bbb.baifenbi >= 0.05f)){
                        m.weapon.bullet = null ;
                    }
                }
            }
        };
        public Seq<UnitType> hus = new Seq<>() ;
        public boolean duidie = false ;
        public float dm = 9 ;
        //public float du = 1 ;
        public float dt = 0 ;
        public float bai = 0 ;
        public boolean zuida = false ;
        public Cons<Unit> cons = (u) -> {} ;
        public String rawName ;
        public boolean disarmm = false;
        public boolean boss = false ;
        @Override
        public void update(Unit unit, float time){
            super.update(unit,time);
            //ShaYeBuShi.fixedPrintln(unit.speedMultiplier);
//            if (fushi){
//                ft *= fu ;
//                if (ft >= fm){
//                    ft = 1 ;
//                }
//            }
            unit.damageMultiplier *= damageMultiplierr /* * (damageMultiplierr > 1 ? dt : (1 / dt))*/ ;
            unit.reloadMultiplier *= reloadMultiplierr /* * (reloadMultiplierr > 1 ? dt : (1 / dt))*/ ;
            unit.speedMultiplier *= speedMultiplierr /* * (speedMultiplierr > 1 ? dt : (1 / dt))*/ ;
            unit.healthMultiplier *= healthMultiplierr /* * (healthMultiplierr > 1 ? dt : (1 / dt))*/ ;
            unit.buildSpeedMultiplier *= buildSpeedMultiplierr /* * (healthMultiplierr > 1 ? dt : (1 / dt))*/ ;
            unit.disarmed |= disarmm ;
            if (huimie){
                ht.get(unit, hus) ;
            }
            if(damagee > 0){
                unit.damageContinuousPierce(damagee/* * ft /* * dt */ );
            }else if(damagee < 0){ //heal unit
                unit.heal(-1f * damagee * Time.delta /*/ ft /* * dt */ );
            }
            if (bai != 0) {
                if (bai > 0) {
                    unit.damage((zuida ? unit.maxHealth : unit.health) * bai/* * ft /* * dt*/);
                }
                else {
                    unit.heal((zuida ? unit.maxHealth : unit.health) * bai * Time.delta/* / ft /* * dt*/);
                }
            }
            cons.get(unit);
        }
        @Override
        public void setStats(){
            super.setStats();
            if(damagee > 0) stats.add(Stat.damage, damagee * 60f, StatUnit.perSecond);
            if(damagee < 0) stats.add(Stat.healing, -damagee * 60f, StatUnit.perSecond);
            if(damageMultiplierr != 1) stats.addPercent(Stat.damageMultiplier, damageMultiplierr);
            if(healthMultiplierr != 1) stats.addPercent(Stat.healthMultiplier, healthMultiplierr);
            if(speedMultiplierr != 1) stats.addPercent(Stat.speedMultiplier, speedMultiplierr);
            if(reloadMultiplierr != 1) stats.addPercent(Stat.reloadMultiplier, reloadMultiplierr);
            if(buildSpeedMultiplierr != 1) stats.addPercent(Stat.buildSpeedMultiplier, buildSpeedMultiplierr);
            if (fushi) {
                stats.add(SYBSStats.fu, fu);
                stats.add(SYBSStats.fm, fm);
            }
            if (duidie) {
                //stats.add(SYBSStats.du, du);
                stats.add(SYBSStats.dm, dm);
            }
            if (bai != 0){
                if (zuida) {
                    if (bai > 0) stats.add(SYBSStats.zuida, bai * 100 + "%");
                    //if (bai > 0) stats.add(SYBSStats.zuida, bai);
                }
                else {
                    if (bai > 0) stats.add(SYBSStats.dangqian, bai * 100 + "%");
                    //if (bai > 0) stats.add(SYBSStats.dangqian, bai);
                }
            }
        }
        public void trans(StatusEffect effect, TransitionHandler handler){
            transitions.put(effect, handler);
        }

        public void affinity(StatusEffect effect, TransitionHandler handler){
            affinities.add(effect);
            effect.affinities.add(this);
            trans(effect, handler);
        }
        @Override
        public void applied(Unit unit, float time, boolean e){
            super.applied(unit, time, e);
            if (duidie && dt < dm - 1) {
//                if (unit.hasEffect(nothing)) {
//                    unit.unapply(this);
//                    unit.unapply(nothing);
//                    unit.apply(duidies.get(rawName).get((int) dt + 1), time);
//                }
//                else {
//                    unit.apply(nothing, Integer.MAX_VALUE);
//                }
                if (unit.hasEffect(this)) {
                    unit.apply(duidies.get(rawName).get((int) dt + 1), time);
                }
            }
            if (fushi && !ShaYeBuShi.has(unit.abilities, a -> a instanceof FuShiAbility)) {
                ShaYeBuShi.add(unit.abilities, new FuShiAbility(fm, fu), Ability.class) ;
            }
            if (boss) {
                Vars.state.teams.bosses.add(unit) ;
            }
        }
    }
    public static void makeDuiDie(String name, int max, Cons2<SYBSStatusEffect, Integer> cons) {
        for (int i = 0 ; i < max ; i ++) {
            int z = i ;
            SYBSStatusEffect paichi = new SYBSStatusEffect(name + (z == 1 ? "" : z)) {{
                color = Pal.gray;
                if (z != 1) {
                    show = false;
                }
                duidie = true;
                dt = z ;
                dm = max ;
            }};
            cons.get(paichi, z);
            duidies.get(name).add(paichi) ;
        }

    }
}
