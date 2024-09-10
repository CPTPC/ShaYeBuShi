package shayebushi;

import arc.Core;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import mindustry.content.StatusEffects;
import mindustry.entities.abilities.*;
import mindustry.gen.Entityc;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import shayebushi.entities.abilities.*;

import static arc.util.Time.toSeconds;
import static mindustry.Vars.tilesize;
import static shayebushi.SYBSShenShengUnitTypes.*;
import static shayebushi.SYBSUnitTypes.*;

public class SYBSUnitAbilities {
    public static void load(){
        //shenqi.abilities.add(new ZaiJiAbility()) ;
        shenqi.abilities.addAll(new YouJunJiaQiangAbility(shenqi,0.03f,2f));
        xieling.abilities.add(new ShanXianAbility()) ;
        daodanxing.abilities.add(new ForceFieldAbility(75f,40f,8000f,10 * toSeconds,3,33),
                new EnergyFieldAbility(51.4f,9,200)
                {{
                    status = StatusEffects.electrified ;
                    healPercent = 0.25f ;
                }},
                // new PoFuChenZhouAbility(shuangxingyijieduan,1145),
                new ChongShengAbility(shuangxingyijieduan,1,0,shuangxingerjieduan)
        );
        jiguangxing.abilities.add(new ForceFieldAbility(75f,40f,8000f,10 * toSeconds,3,33)
                ,new EnergyFieldAbility(51.4f,9,200)
                {{
                    status = StatusEffects.electrified ;
                    healPercent = 0.25f ;
                }}
                //,
                //new PoFuChenZhouAbility(shuangxingyijieduan,1145)
        );
        shuangxingerjieduan.abilities.add(new ForceFieldAbility(100f,40f,8000f,10 * toSeconds,3,33),
                new EnergyFieldAbility(51.4f,9,200)
            {{
                status = StatusEffects.electrified ;
                healPercent = 0.5f ;
            }}, new ShangHaiShouRongAbility()
                //,
                // new PoFuChenZhouAbility(shuangxingerjieduan,1145),
                //new WangYuAbility(100000,400)
        );
        tianping.abilities.add(new DuiDiZhuangTaiChangAbility(SYBSStatusEffects.duidies.get("biaoji").get(0), 30 * toSeconds, 180, 35 * tilesize) {
            @Override
            public void update(Unit unit) {
                super.update(unit);
                if (unit.mounts[0].target instanceof Unit u && ShaYeBuShi.DPS(u.type) >= ShaYeBuShi.DPS(tianping) * 1.1f) {
                    super.update(unit);
                }
            }
        }, new Ability() {
            @Override
            public void update(Unit unit) {
                super.update(unit);
                Entityc e = unit.mounts[0].target ;
                Unit u = e instanceof Unit ? (Unit)e : null ;
                if (u != null && !shendijidanwei.contains(u.type) && !shenwangjidanwei.contains(u.type) && !shenshengjidanwei.contains(u.type)) {
                    if (u.maxHealth >= unit.maxHealth * 1.05f) {
                        u.maxHealth = unit.maxHealth ;
                        if (u.health > u.maxHealth) {
                            u.health = u.maxHealth ;
                        }
                    }
                    u.damage(ShaYeBuShi.DPS(unit.type) * 0.35f / 60f) ;
                }
            }
            @Override
            public String localized() {
                return Core.bundle.get("ability.tianping") ;
            }
        }) ;
        wuji.abilities.addAll(new ZhuTiAbility(){{
            spawnTypes.add(zhen, kan, gen, qian) ;
            display = false ;
            spawns = 4 ;
            buquan = false ;
            for (int i : new int[]{0, 90, 45, 135}) {
                pucongweis.add(ShaYeBuShi.circle(i, 7 * tilesize, 0, 0));
                //System.out.println(ShaYeBuShi.circle(360f / 3 * i, 7 * tilesize, 0, 0));
            }
        }}) ;
        weiyang.abilities.add(new ForceFieldAbility(200,75,140000,2700,114514,114),
                new DuiDiZhuangTaiChangAbility(SYBSStatusEffects.dangji,10,300,114514),
                new RepairFieldAbility(2000,300,114514),
                new ShieldRegenFieldAbility(2000f, 200000f, 60f * 5, 114514f));
        weiyang.abilities.add(new ShieldArcAbility(){{
            angle = 360 ;
            max = 140000 ;
            regen = 75 ;
            radius = 250 ;
            width = 20 ;
            cooldown = 60f * 45f;
            whenShooting = false ;
        }},new SpawnDeathAbility(shuangxingyijieduan,30,2),new SuppressionFieldAbility(){{
                    orbRadius = 15f ;
                    particles = 30 ;
                }}
                ,new HongZhaAbility(50,1919,1800,100, "tianqi"){{targetBuilding = false;}}
        );
        for (Vec2 v : new Vec2[]{new Vec2((480 - 292) / 4f, (666.5f - 306) / 4f), new Vec2((480 - 195) / 4f, (666.5f - 785) / 4f), new Vec2((480 - 175) / 4f, (666.5f - 595) / 4f)}){
            weiyang.abilities.add(new UnitSpawnAbility(z01,1200,v.x, v.y));
        }
        for (Vec2 v : new Vec2[]{SYBSUnitTypes.textureToReal(new Vec2(292, 306), 960, 1333), SYBSUnitTypes.textureToReal(new Vec2(195, 785), 960, 1333), SYBSUnitTypes.textureToReal(new Vec2(175, 595), 960, 1333)}){
            weiyang.abilities.add(new UnitSpawnAbility(affh01,1200,v.x,v.y));
        }
        haojieyijieduan.abilities.add(new ZhenDangHuDunAbility(175,75,200000,2700,114514,33,8000),
                new DuiDiZhuangTaiChangAbility(SYBSStatusEffects.huimiesuyi,300,240,114514),
                new HaoJieChongShengAbility(),
                new EnergyFieldAbility(650,60,400){{color = Pal.redderDust;}});
        haojieyijieduan.abilities.add(new StatusFieldAbility(StatusEffects.overclock,600,300,400));
        haojieerjieduan.abilities.add(new ZhenDangHuDunAbility(220,75,250000,2500,114514,33,18000),
                new DuiDiZhuangTaiChangAbility(SYBSStatusEffects.huimiesuyi,300,240,114514),
                new HaoJieChongShengAbilityy(),
                new EnergyFieldAbility(1300,30,800){{color = Pal.redderDust;}});
        haojieerjieduan.abilities.add(new StatusFieldAbility(StatusEffects.overclock,600,120,800),new HaoJieHuiXieAbility());
        haojiesanjieduan.abilities.add(new ZhenDangHuDunAbility(250,75,280000,2000,114514,33,36000),
                new DuiDiZhuangTaiChangAbility(SYBSStatusEffects.huimiesuyi,300,240,114514),
                new EnergyFieldAbility(2600,15,800){{color = Pal.redderDust;}});
        haojiesanjieduan.abilities.add(new StatusFieldAbility(StatusEffects.overclock,600,120,1200),new HaoJieHuiXieAbility(1));
    }
}
