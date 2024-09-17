package shayebushi;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.content.StatusEffects;
import mindustry.core.Version;
import mindustry.game.SpawnGroup;
import mindustry.mod.Mods;
import mindustry.type.Planet;
import mindustry.type.Sector;
import mindustry.type.SectorPreset;

import static mindustry.content.UnitTypes.*;
import static shayebushi.SYBSUnitTypes.*;

public class SYBSSectorPresets {
    public static SectorPreset luojiaodian , bingdonggaoyuan, fushekuangshan, liusuanzhaoze, liebianfeiqu, rongyantongdao, qinggongyequ, zhonggongyequ ;
    public static ObjectMap<String, SYBSSectorPreset> map = new ObjectMap<>() ;
    public static void load() {
        luojiaodian = new SectorPreset("luojiaodian",SYBSPlanets.deleike,10){{
            alwaysUnlocked = Version.build == -1;
            addStartingItems = true;
            captureWave = 20;
            difficulty = 4;
            overrideLaunchDefaults = true;
            //noLighting = true;
            startWaveTimeMultiplier = 7f;
        }};
        bingdonggaoyuan = new SectorPreset("bingdonggaoyuan",SYBSPlanets.deleike,84){{
            //alwaysUnlocked = true;
            //addStartingItems = true;
            captureWave = 45 ;
            difficulty = 5 ;
            //overrideLaunchDefaults = true;
            //noLighting = true;
            startWaveTimeMultiplier = 2f;
        }};
        fushekuangshan = new SectorPreset("fushekuangshan",SYBSPlanets.deleike,70){{
            //alwaysUnlocked = true;
            //addStartingItems = true;
            //captureWave = 45 ;
            difficulty = 6 ;
            //overrideLaunchDefaults = true;
            //noLighting = true;
            //startWaveTimeMultiplier = 2f;
        }};
        liusuanzhaoze = new SectorPreset("liusuanzhaoze",SYBSPlanets.deleike,7){{
            //alwaysUnlocked = true;
            //addStartingItems = true;
            //captureWave = 45 ;
            difficulty = 6 ;
            //overrideLaunchDefaults = true;
            //noLighting = true;
            //startWaveTimeMultiplier = 2f;
        }};
        liebianfeiqu = new SYBSSectorPreset("liebianfeiqu", SYBSPlanets.deleike, 69) {{
           difficulty = 6 ;
           //alwaysFushe = true ;
        }};
        qinggongyequ = new SYBSSectorPreset("qinggongyequ", SYBSPlanets.deleike, 88) {{
            difficulty = 7 ;
        }};
        rongyantongdao = new SYBSSectorPreset("rongyantongdao", SYBSPlanets.deleike, 68) {{
            captureWave = 300 ;
            difficulty = 8 ;
            zengyuanjiange = 60 * 60 * 2 ;
            zengyuanboci = 6 ;
            zengyuan.addAll(
                    new SpawnGroup(quad){{
                        begin = 1;
                        unitAmount = 4;
                        end = 1 ;
                    }},
                    new SpawnGroup(disrupt){{
                        begin = 1;
                        unitAmount = 8;
                        end = 1 ;
                    }},
                    new SpawnGroup(eclipse){{
                        begin = 2;
                        unitAmount = 6;
                        end = 2 ;
                    }},
                    new SpawnGroup(reign){{
                        begin = 2;
                        unitAmount = 8 ;
                        end = 2 ;
                    }},
                    new SpawnGroup(conquer){{
                        begin = 3;
                        unitAmount = 10 ;
                        end = 3 ;
                    }},
                    new SpawnGroup(obviate){{
                        begin = 3;
                        unitAmount = 50 ;
                        end = 3 ;
                    }},
                    new SpawnGroup(anye){{
                        begin = 4;
                        unitAmount = 5 ;
                        end = 4 ;
                    }},
                    new SpawnGroup(xieling){{
                        begin = 4;
                        unitAmount = 5 ;
                        end = 4 ;
                    }},
                    new SpawnGroup(duoxing){{
                        begin = 5;
                        unitAmount = 8 ;
                        end = 5 ;
                    }},
                    new SpawnGroup(shengling){{
                        begin = 5;
                        unitAmount = 2 ;
                        end = 5 ;
                    }},
                    new SpawnGroup(wuji){{
                        begin = 6;
                        unitAmount = 1 ;
                        end = 6 ;
                        effect = StatusEffects.boss ;
                    }},
                    new SpawnGroup(shenqi){{
                        begin = 6;
                        unitAmount = 5 ;
                        end = 6 ;
                    }}) ;
        }} ;
        map = ObjectMap.of(
                "熔岩通道", rongyantongdao,
                "ceshi", rongyantongdao,
                "ceshiii", rongyantongdao,
                "ceshi2", rongyantongdao,
                "ceshiii2", rongyantongdao
                ) ;
    }

    public static class SYBSSectorPreset extends SectorPreset {

        public boolean alwaysFushe = false ;
        public float fusheqiangdu = 1 ;
        public Seq<SpawnGroup> zengyuan = new Seq<>() ;
        public float zengyuanjiange = 60 * 60 * 2 ;
        public int zengyuanboci = 0 ;

        public SYBSSectorPreset(String name, Planet planet, int sector) {
            super(name, planet, sector);
        }

        public SYBSSectorPreset(String name, Mods.LoadedMod mod) {
            super(name, mod);
        }

        public SYBSSectorPreset(String name) {
            super(name);
        }
    }
}
