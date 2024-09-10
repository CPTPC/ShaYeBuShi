package shayebushi;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.Gl;
import arc.graphics.Mesh;
import arc.graphics.g3d.Camera3D;
import arc.graphics.g3d.VertexBatch3D;
import arc.graphics.gl.Shader;
import arc.math.Mathf;
import arc.math.geom.Mat3D;
import arc.math.geom.Vec3;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.noise.Noise;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.content.Weathers;
import mindustry.core.Version;
import mindustry.game.Rules;
import mindustry.game.SectorInfo;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.graphics.Shaders;
import mindustry.graphics.g3d.*;
import mindustry.maps.planet.ErekirPlanetGenerator;
import mindustry.maps.planet.SerpuloPlanetGenerator;
import mindustry.type.Item;
import mindustry.type.Planet;
import mindustry.type.Sector;
import mindustry.type.Weather;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.Env;
import shayebushi.maps.planet.BuLaoEnPlanetGenerator;
import shayebushi.maps.planet.DeLeiKePlanetGenerator;

import static mindustry.Vars.player;
import static mindustry.Vars.state;
import static mindustry.graphics.g3d.PlanetRenderer.*;
import static mindustry.graphics.g3d.PlanetRenderer.borderColor;

public class SYBSPlanets {
    public static Planet deleike, pabuleike, feilifusi, yiduntanding, bulaoen, sin ;

    public static void load() {
        deleike = new SYBSPlanet("deleike", Planets.sun,1,2){{
            generator = new DeLeiKePlanetGenerator();
            meshLoader = () -> new HexMesh(this, 6);
            cloudMeshLoader = () ->
                 new MultiMesh(
                     new HexSkyMesh(this, 5, 0.3f, 0.15f, 5, Color.valueOf("eeff93").a(0.75f), 2, 0.45f, 0.9f, 0.38f),
                     new HexSkyMesh(this, 11, 0.15f, 0.13f, 5, Color.valueOf("93ffee").a(0.75f), 2, 0.45f, 0.9f, 0.38f),
                     new HexSkyMesh(this, 1, 0.6f, 0.16f, 5, Color.valueOf("68ffa7").a(0.75f), 2, 0.45f, 1f, 0.41f)
            );
            launchCapacityMultiplier = 0.8f;
            sectorSeed = 2;
            hiddenItems.clear() ;
            hiddenItems.removeAll(SYBSItems.deleikeItems);
            visible = true;
            accessible = true;
            alwaysUnlocked = true;
            allowWaveSimulation = true;
            allowSectorInvasion = true;
            allowLaunchSchematics = true;
            enemyCoreSpawnReplace = true;
            allowLaunchLoadout = true;
            //doesn't play well with configs
            prebuildBase = false;
            iconColor = Color.valueOf("4dff7d");
            atmosphereColor = iconColor;
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            startSector = 10;
            alwaysUnlocked = true;
            landCloudColor = iconColor.cpy().a(0.5f);
            enemyBuildSpeedMultiplier = 1.1f ;
            defaultCore = Blocks.coreNucleus;
            ruleSetter = r -> {
                r.waveTeam = Team.green;
                r.placeRangeCheck = false;
                r.showSpawns = false;
                r.hiddenBuildItems.clear();
                r.lighting = true ;
                r.coreIncinerates = true ;
            };
            unlockedOnLand.add(SYBSBlocks.yijihexin);
            //updateLighting = true ;
            orbitRadius = 60 ;
            launchCapacityMultiplier = 1f;
            //sectorSeed = 2;
            //allowWaves = true;
            //allowWaveSimulation = true;
            //allowSectorInvasion = true;
            //allowLaunchSchematics = true;
            //enemyCoreSpawnReplace = true;
            //allowLaunchLoadout = true;
            //doesn't play well with configs
            //prebuildBase = false;
//            ruleSetter = r -> {
//                r.waveTeam = Team.crux;
//                r.placeRangeCheck = false;
//                r.showSpawns = false;
//            };
            //iconColor = Color.valueOf("7d4dff");
            //atmosphereColor = Color.valueOf("3c1b8f");
            //atmosphereRadIn = 0.02f;
            //atmosphereRadOut = 0.3f;
            //startSector = 15;
            //alwaysUnlocked = true;
            //landCloudColor = Pal.spore.cpy().a(0.5f);
            //hiddenItems.addAll(Items.erekirItems).removeAll(Items.serpuloItems);
        }};
        sin = new SYBSPlanet("sin", null, 80f){{
            bloom = true;
            accessible = false;

            meshLoader = () -> new SunMesh(
                    this, 4,
                    5, 0.3, 1.7, 1.2, 1,
                    1.1f,
                    Color.valueOf("387aff"),
                    Color.valueOf("3896ff"),
                    Color.valueOf("4cc6ff"),
                    Color.valueOf("4cc6ff"),
                    Color.valueOf("71e3ff"),
                    Color.valueOf("8eeef4")
            );
        }};
        yiduntanding = new SYBSPlanet("yiduntanding", sin, 1f, 3){{
            generator = new ErekirPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 5);
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 2, 0.15f, 0.14f, 5, Color.valueOf("eba768").a(0.75f), 2, 0.42f, 1f, 0.43f),
                    new HexSkyMesh(this, 3, 0.6f, 0.15f, 5, Color.valueOf("eea293").a(0.75f), 2, 0.42f, 1.2f, 0.45f)
            );
            alwaysUnlocked = false;
            landCloudColor = Color.valueOf("ed6542");
            atmosphereColor = Color.valueOf("f07218");
            defaultEnv = Env.scorching | Env.terrestrial;
            startSector = 10;
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            tidalLock = true;
            orbitSpacing = 6f;
            totalRadius += 2.6f;
            lightSrcTo = 0.5f;
            lightDstFrom = 0.2f;
            clearSectorOnLose = true;
            defaultCore = Blocks.coreBastion;
            iconColor = Color.valueOf("ff9266");
            hiddenItems.addAll(Items.serpuloItems).removeAll(Items.erekirItems);
            enemyBuildSpeedMultiplier = 0.4f;

            //TODO disallowed for now
            allowLaunchToNumbered = false;

            //TODO SHOULD there be lighting?
            updateLighting = false;

            defaultAttributes.set(Attribute.heat, 0.8f);

            ruleSetter = r -> {
                r.waveTeam = Team.malis;
                r.placeRangeCheck = false;
                r.showSpawns = true;
                r.fog = true;
                r.staticFog = true;
                r.lighting = false;
                r.coreDestroyClear = true;
                r.onlyDepositCore = true;
            };
            unlockedOnLand.add(Blocks.coreBastion);

        }};
        bulaoen = new SYBSPlanet("bulaoen", sin, 1, 1) {{
            generator = new BuLaoEnPlanetGenerator() ;
            meshLoader = () -> new HexMesh(this, 5);
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 2, 0.15f, 0.14f, 5, Color.valueOf("6ecdec").a(0.75f), 2, 0.42f, 1f, 0.43f),
                    new HexSkyMesh(this, 3, 0.6f, 0.15f, 5, Color.valueOf("0097f5").a(0.75f), 2, 0.42f, 1.2f, 0.45f)
            );
            alwaysUnlocked = visible = accessible = true ;
            iconColor = Color.valueOf("0097f5") ;

            ruleSetter = r -> {
                r.waveTeam = Team.blue ;
                r.weather.add(new Weather.WeatherEntry(Weathers.snow)) ;
            } ;
            defaultAttributes.set(Attribute.heat, -60f);
            //orbitSpacing = 240 ;
            updateLighting = true;
            defaultCore = Blocks.coreShard;
            startSector = 10;
        }} ;
        Planets.erekir.orbitRadius = 20 ;
        Planets.sun.radius = 16 ;
        if (Version.build == -1 || ShaYeBuShi.tiaoshi) {
            Planets.tantros.accessible = true;
            Planets.tantros.alwaysUnlocked = true;
            Planets.tantros.visible = true;
        }
    }
    public static class SYBSPlanet extends Planet{
//        public static final Mat3D mat = new Mat3D();
        public SYBSPlanet(String name, Planet parent, float radius){
            super(name, parent, radius);
        }
        public SYBSPlanet(String name, Planet parent, float radius, int sectorSize) {
            super(name, parent, radius);
            if(sectorSize > 0){
                grid = PlanetGrid.create(sectorSize);

                sectors.ensureCapacity(grid.tiles.length);
                for(int i = 0; i < grid.tiles.length; i++){
                    sectors.add(new Sector(this, grid.tiles[i]){
                        @Override
                        public String displayThreat(){
                            float step = 0.25f;
                            String color = Tmp.c1.set(Color.white).lerp(Color.scarlet, Mathf.round(threat, step)).toString();
                            String[] threats = ShaYeBuShi.threats;
                            int index = Math.min((int)(threat / step), threats.length - 1);
                            return "[#" + color + "]" + Core.bundle.get("threat." + threats[index]);
                        }
                        {}});
                }

                sectorApproxRadius = sectors.first().tile.v.dst(sectors.first().tile.corners[0].v);
            }
        }
        @Override
        public void updateBaseCoverage(){
            for(Sector sector : sectors){
                float sum = 1f;
                for(Sector other : sector.near()){
                    if(other.generateEnemyBase){
                        sum += 0.9f;
                    }
                }

                if(sector.hasEnemyBase()){
                    sum += 0.88f;
                }
                sector.threat += sector.preset == null ? sum / 5f : Math.max(sector.preset.difficulty / 10f,0f);
            }
        }
        @Override
        public void drawSelection(VertexBatch3D batch, Sector sector, Color color, float stroke, float length){
            Rules r = new Rules() ;
            sector.planet.ruleSetter.get(r) ;
            Color enemyColor = sector.save != null ? sector.save.meta.rules.waveTeam.color :
                    sector.planet.generator instanceof SerpuloPlanetGenerator ? Team.crux.color : sector.planet.generator instanceof ErekirPlanetGenerator ? Team.malis.color : sector.planet.generator instanceof BuLaoEnPlanetGenerator ? Team.blue.color : sector.planet.generator instanceof DeLeiKePlanetGenerator ? Team.green.color :
                    sector.hasSave() ? sector.save.meta.rules.waveTeam.color : r.waveTeam.color;
            color =
                    sector.hasBase() ? Tmp.c2.set(player.team().color).lerp(enemyColor, sector.hasEnemyBase() ? 0.5f : 0f) :
                            sector.preset != null ?
                                    sector.preset.unlocked() ? Tmp.c2.set(Team.derelict.color).lerp(Color.white, Mathf.absin(Time.time, 10f, 1f)) :
                                            Color.gray :
                                    sector.hasEnemyBase() ? enemyColor :
                                            new Color();
            Tmp.c1.set(color).mul(0.8f).a(1) ;
            float arad = (outlineRad + length) * radius;

            for(int i = 0; i < sector.tile.corners.length; i++){
                PlanetGrid.Corner next = sector.tile.corners[(i + 1) % sector.tile.corners.length];
                PlanetGrid.Corner curr = sector.tile.corners[i];

                next.v.scl(arad);
                curr.v.scl(arad);
                sector.tile.v.scl(arad);

                Tmp.v31.set(curr.v).sub(sector.tile.v).setLength(curr.v.dst(sector.tile.v) - stroke).add(sector.tile.v);
                Tmp.v32.set(next.v).sub(sector.tile.v).setLength(next.v.dst(sector.tile.v) - stroke).add(sector.tile.v);

                batch.tri(curr.v, next.v, Tmp.v31, color);
                batch.tri(Tmp.v31, next.v, Tmp.v32, color);

                sector.tile.v.scl(1f / arad);
                next.v.scl(1f / arad);
                curr.v.scl(1f /arad);
            }
        }
    }
}
