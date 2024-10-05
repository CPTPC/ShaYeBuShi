package shayebushi.graphics;

import arc.Core;
import arc.files.Fi;
import arc.files.ZipFi;
import arc.func.Floatc2;
import arc.graphics.Camera;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.graphics.gl.FrameBuffer;
import arc.input.KeyCode;
import arc.math.Angles;
import arc.math.Mat;
import arc.math.Mathf;
import arc.scene.ui.layout.Scl;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Structs;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.noise.Ridged;
import arc.util.noise.Simplex;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.core.GameState;
import mindustry.core.Logic;
import mindustry.core.Version;
import mindustry.ctype.ContentType;
import mindustry.entities.Units;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.input.Binding;
import mindustry.io.SaveIO;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.CachedTile;
import mindustry.world.Tile;
import mindustry.world.Tiles;
import mindustry.world.blocks.defense.ForceProjector;
import shayebushi.SYBSLiquids;
import shayebushi.ShaYeBuShi;

import static mindustry.Vars.*;
import static mindustry.Vars.tilesize;
import static arc.Core.camera ;

public class SYBSMenuRenderer extends MenuRenderer {
    public static float zoom = 5 ;
    public static int t = 0 ;
    private static final float darkness = 0.3f;
    private final int width = !mobile ? 100 : 60, height = !mobile ? 50 : 40;

    private int cacheFloor, cacheWall;
    //private Camera camera = new Camera();
    private Mat mat = new Mat();
    private FrameBuffer shadows;
    private CacheBatch batch;
    private float time = 0f;
    private float flyerRot = 45f;
    private int flyers = Mathf.chance(0.2) ? Mathf.random(35) : Mathf.random(15);
    //no longer random or "dynamic", mod units in the menu look jarring, and it's not worth the configuration effort
    private UnitType flyerType = Seq.with(UnitTypes.flare, UnitTypes.horizon, UnitTypes.zenith, UnitTypes.mono, UnitTypes.poly, UnitTypes.mega, UnitTypes.alpha, UnitTypes.beta, UnitTypes.gamma).random();

    public SYBSMenuRenderer(){
        Time.mark();
        generate();
        cache();
        Log.debug("Time to generate menu: @", Time.elapsed());
    }

    public void generate() {
        if (!Core.settings.getBool(Core.bundle.get("sybssets.zhucaidan"))) {
            //suppress tile change events.
            world.setGenerating(true);

            Tiles tiles = world.resize(width, height);
            //only uses base game ores now, mod ones usually contrast too much with the floor
            Seq<Block> ores = Seq.with(Blocks.oreCopper, Blocks.oreLead, Blocks.oreScrap, Blocks.oreCoal, Blocks.oreTitanium, Blocks.oreThorium);
            shadows = new FrameBuffer(width, height);
            int offset = Mathf.random(100000);
            int s1 = offset, s2 = offset + 1, s3 = offset + 2;
            Block[] selected = Structs.random(
                    new Block[]{Blocks.sand, Blocks.sandWall},
                    new Block[]{Blocks.shale, Blocks.shaleWall},
                    new Block[]{Blocks.ice, Blocks.iceWall},
                    new Block[]{Blocks.sand, Blocks.sandWall},
                    new Block[]{Blocks.shale, Blocks.shaleWall},
                    new Block[]{Blocks.ice, Blocks.iceWall},
                    new Block[]{Blocks.moss, Blocks.sporePine},
                    new Block[]{Blocks.dirt, Blocks.dirtWall},
                    new Block[]{Blocks.dacite, Blocks.daciteWall}
            );
            Block[] selected2 = Structs.random(
                    new Block[]{Blocks.basalt, Blocks.duneWall},
                    new Block[]{Blocks.basalt, Blocks.duneWall},
                    new Block[]{Blocks.stone, Blocks.stoneWall},
                    new Block[]{Blocks.stone, Blocks.stoneWall},
                    new Block[]{Blocks.moss, Blocks.sporeWall},
                    new Block[]{Blocks.salt, Blocks.saltWall}
            );

            Block ore1 = ores.random();
            ores.remove(ore1);
            Block ore2 = ores.random();

            double tr1 = Mathf.random(0.65f, 0.85f);
            double tr2 = Mathf.random(0.65f, 0.85f);
            boolean doheat = Mathf.chance(0.25);
            boolean tendrils = Mathf.chance(0.25);
            boolean tech = Mathf.chance(0.25);
            int secSize = 10;

            Block floord = selected[0], walld = selected[1];
            Block floord2 = selected2[0], walld2 = selected2[1];

            for(int x = 0; x < width; x++){
                for(int y = 0; y < height; y++){
                    Block floor = floord;
                    Block ore = Blocks.air;
                    Block wall = Blocks.air;

                    if(Simplex.noise2d(s1, 3, 0.5, 1/20.0, x, y) > 0.5){
                        wall = walld;
                    }

                    if(Simplex.noise2d(s3, 3, 0.5, 1/20.0, x, y) > 0.5){
                        floor = floord2;
                        if(wall != Blocks.air){
                            wall = walld2;
                        }
                    }

                    if(Simplex.noise2d(s2, 3, 0.3, 1/30.0, x, y) > tr1){
                        ore = ore1;
                    }

                    if(Simplex.noise2d(s2, 2, 0.2, 1/15.0, x, y+99999) > tr2){
                        ore = ore2;
                    }

                    if(doheat){
                        double heat = Simplex.noise2d(s3, 4, 0.6, 1 / 50.0, x, y + 9999);
                        double base = 0.65;

                        if(heat > base){
                            ore = Blocks.air;
                            wall = Blocks.air;
                            floor = Blocks.basalt;

                            if(heat > base + 0.1){
                                floor = Blocks.hotrock;

                                if(heat > base + 0.15){
                                    floor = Blocks.magmarock;
                                }
                            }
                        }
                    }

                    if(tech){
                        int mx = x % secSize, my = y % secSize;
                        int sclx = x / secSize, scly = y / secSize;
                        if(Simplex.noise2d(s1, 2, 1f / 10f, 0.5f, sclx, scly) > 0.4f && (mx == 0 || my == 0 || mx == secSize - 1 || my == secSize - 1)){
                            floor = Blocks.darkPanel3;
                            if(Mathf.dst(mx, my, secSize/2, secSize/2) > secSize/2f + 1){
                                floor = Blocks.darkPanel4;
                            }


                            if(wall != Blocks.air && Mathf.chance(0.7)){
                                wall = Blocks.darkMetal;
                            }
                        }
                    }

                    if(tendrils){
                        if(Ridged.noise2d(1 + offset, x, y, 1f / 17f) > 0f){
                            floor = Mathf.chance(0.2) ? Blocks.sporeMoss : Blocks.moss;

                            if(wall != Blocks.air){
                                wall = Blocks.sporeWall;
                            }
                        }
                    }

                    Tile tile;
                    tiles.set(x, y, (tile = new CachedTile()));
                    tile.x = (short)x;
                    tile.y = (short)y;
                    tile.setFloor(floor.asFloor());
                    tile.setBlock(wall);
                    tile.setOverlay(ore);
                }
            }

            //don't fire a world load event, it just causes lag and confusion
            world.setGenerating(false);
            return ;
        }
        Fi target = Version.build == -1 ? Core.files.absolute("D:\\Mindustry-master\\core\\build\\libs\\A-files\\maps\\oldVersions\\menu.msav") : mods.getMod("shayebushi").file != null ? new Seq<Fi>(new ZipFi(mods.getMod("shayebushi").file).list()).find(f -> f.name().equals("maps")).child("oldVersions").child("menu.msav") : null ;
        SaveIO.load(target) ;
        shadows = new FrameBuffer(world.width(), world.height());
        state.rules.waves = true ;
        state.rules.waveTimer = true ;
        state.rules.unitCap = 114514 ;
        //state.set(GameState.State.playing) ;
    }

    private void cache(){
        //if (1 + 1 == 2) return ;
        //draw shadows
        Draw.proj().setOrtho(0, 0, shadows.getWidth(), shadows.getHeight());
        shadows.begin(Color.clear);
        Draw.color(Color.black);

        for(Tile tile : world.tiles){
            if(tile.block() != Blocks.air){
                Fill.rect(tile.x + 0.5f, tile.y + 0.5f, 1, 1);
            }
        }

        Draw.color();
        shadows.end();

        Batch prev = Core.batch;

        Core.batch = batch = new CacheBatch(new SpriteCache(world.width() * world.height() * 6, false));
        batch.beginCache();

        for(Tile tile : world.tiles){
            //if (tile.build != null) continue ;
            tile.floor().drawBase(tile);
        }
        for(Tile tile : world.tiles){
            //if (tile.build != null) continue ;
            tile.overlay().drawBase(tile);
        }

        cacheFloor = batch.endCache();
        batch.beginCache();

        for(Tile tile : world.tiles){
            if (tile.build != null) continue ;
            tile.block().drawBase(tile);
        }

        cacheWall = batch.endCache();

        Core.batch = prev;

        camera.position.set(width * tilesize / 2f, height * tilesize / 2f);
        if (state.hasSpawns()) {
            camera.position.set(Vars.spawner.getSpawns().get(0)) ;
        }
    }

    public void render(){
        if (!Core.settings.getBool(Core.bundle.get("sybssets.zhucaidan"))) {
            super.render() ;
            return ;
        }
        if (world.width() == 0 || world.height() == 0) {
            generate() ;
        }
        if (Groups.unit.size() == 0 && t == 0) {
            Fi f2 = Version.build == -1 ? Core.files.absolute("D:\\Mindustry-master\\core\\build\\libs\\A-files\\maps\\oldVersions\\units.dat") : mods.getMod("shayebushi").file != null ? new Seq<Fi>(new ZipFi(mods.getMod("shayebushi").file).list()).find(f -> f.name().equals("maps")).child("oldVersions").child("units.dat") : null ;
            String[] s = f2.readString().split("\n") ;
            //System.out.println(s.length);
            for (String s1 : s) {
                String[] s2 = s1.split(" ") ;
                Unit u = ((UnitType) content.getByName(ContentType.unit, s2[0])).create(Team.get(Integer.parseInt(s2[4]))) ;
                u.set(Float.parseFloat(s2[1]), Float.parseFloat(s2[2])) ;
                u.rotation(Float.parseFloat(s2[3])) ;
                u.add() ;
            }
            t ++ ;
        }
        time += Time.delta;
        float scaling = Math.max(Scl.scl(4f), Math.max(Core.graphics.getWidth() / ((width - 1f) * tilesize), Core.graphics.getHeight() / ((height - 1f) * tilesize))) / zoom ;
        if (Core.input.keyDown(KeyCode.up)) {
            camera.position.y += Time.delta ;
        }
        if (Core.input.keyDown(KeyCode.down)) {
            camera.position.y -= Time.delta ;
        }
        if (Core.input.keyDown(KeyCode.right)) {
            camera.position.x += Time.delta ;
        }
        if (Core.input.keyDown(KeyCode.left)) {
            camera.position.x -= Time.delta ;
        }
        if (Core.input.keyDown(KeyCode.num1)) {
            zoom += Time.delta / 10 ;
        }
        if (Core.input.keyDown(KeyCode.num2)) {
            zoom -= Time.delta / 10 ;
        }
        camera.resize(Core.graphics.getWidth() / scaling,
                Core.graphics.getHeight() / scaling);

        mat.set(Draw.proj());
        Draw.flush();
        Draw.proj(camera);
        batch.setProjection(camera.mat);
        batch.beginDraw();
        batch.drawCache(cacheFloor);
        batch.endDraw();
        Draw.color();
        Draw.rect(Draw.wrap(shadows.getTexture()),
                world.width() * tilesize / 2f - 4f, world.height() * tilesize / 2f - 4f,
                world.width() * tilesize, -world.height() * tilesize);
        Draw.flush();
        batch.beginDraw();
        batch.drawCache(cacheWall);
        batch.endDraw();
        Draw.flush();
        if (state.getState() == GameState.State.menu) {
            renderer.draw();
        }
//        Units.nearbyBuildings(0, 0, Integer.MAX_VALUE, b -> {
//            if (b instanceof ForceProjector.ForceBuild) {
//                //Draw.z(Layer.shields) ;
//                Shaders.shield.apply() ;
//            }
//            b.draw() ;
//        });
//        Units.nearby(null, 0, 0, Integer.MAX_VALUE, Unit::draw);
//        Groups.bullet.intersect(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE, Bullet::draw) ;

        Draw.proj(mat);
        Draw.color(0f, 0f, 0f, darkness);
        Fill.crect(0, 0, Core.graphics.getWidth(), Core.graphics.getHeight());
        Draw.color();
        if (state.getState() == GameState.State.menu) {
            Units.nearbyBuildings(0, 0, Integer.MAX_VALUE, b -> {
                if (b instanceof ForceProjector.ForceBuild) {
                    b.handleItem(null, Items.phaseFabric);
                    b.handleLiquid(null, SYBSLiquids.huoxingdenglizi, b.block.liquidCapacity);
                }
                if (b.block == Blocks.foreshadow) {
                    b.handleItem(null, Items.surgeAlloy);
                    b.handleLiquid(null, SYBSLiquids.huoxingdenglizi, b.block.liquidCapacity);
                }
                b.update();
            });
            //Units.nearby(null, 0, 0, Integer.MAX_VALUE, Unit::update);
            //Groups.bullet.intersect(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE, Bullet::update) ;
            Groups.unit.update();
            Groups.bullet.update();
            Groups.draw.update();
//        if(state.rules.waves && state.rules.waveTimer && !state.gameOver){
//            if(!logic.isWaitingWave()){
//                state.wavetime = Math.max(state.wavetime - Time.delta, 0);
//            }
//        }
//        if(!net.client() && state.wavetime <= 0 && state.rules.waves){
//            logic.runWave();
//        }
        }
    }

    @Override
    public void dispose(){
        batch.dispose();
        shadows.dispose();
    }
}
