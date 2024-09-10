package shayebushi.world.blocks.power;

import arc.Core;
import arc.func.Boolf;
import arc.func.Cons;
import arc.func.Func;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Intersector;
import arc.math.geom.Point2;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.struct.IntSeq;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.*;
import mindustry.annotations.Annotations;
import mindustry.audio.SoundLoop;
import mindustry.content.Fx;
import mindustry.core.Renderer;
import mindustry.core.UI;
import mindustry.core.World;
import mindustry.entities.Damage;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ContinuousBulletType;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.entities.units.BuildPlan;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.gen.Teamc;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.input.Placement;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Edges;
import mindustry.world.Tile;
import mindustry.world.blocks.power.PowerBlock;
import mindustry.world.blocks.power.PowerGraph;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.meta.Env;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.modules.ItemModule;
import mindustry.world.modules.LiquidModule;
import mindustry.world.modules.PowerModule;
import shayebushi.SYBSBlocks;
import shayebushi.SYBSPal;
import shayebushi.ShaYeBuShi;
import shayebushi.world.consumers.ConsumeDianYa;
import shayebushi.world.modules.DianYaModule;

import java.awt.*;

import static mindustry.Vars.*;
import static mindustry.Vars.tilesize;

public class DianYaNode extends DianYaBlock {
    protected static BuildPlan otherReq;
    protected static int returnInt = 0;
    protected final static ObjectSet<PowerGraph> graphs = new ObjectSet<>();
    /** The maximum range of all power nodes on the map */
    protected static float maxRange;

    public
    //@Annotations.Load(value = "@-laser", fallback = "laser")
    TextureRegion laser
            = Core.atlas.find("" + name + "-laser-end", "laser-end")
            ;
    public
    //@Annotations.Load(value = "@-laser-end", fallback = "laser-end")
    TextureRegion laserEnd
            = Core.atlas.find("" + name + "-laser", "laser")
            ;
    public float laserRange = 6;
    public int maxNodes = 3;
    public boolean autolink = true, drawRange = true;
    public float laserScale = 0.25f;
    public Color laserColor1 = Color.white;
    public Color laserColor2 = Pal.powerLight;
    public int t = timers ++ ;

    public DianYaNode(String name){
        super(name);
        configurable = true;
        consumesPower = false;
        outputsPower = false;
        canOverdrive = false;
        swapDiagonalPlacement = true;
        schematicPriority = -10;
        drawDisabled = false;
        envEnabled |= Env.space;
        destructible = true;
        hasPower = true ;
        //nodes do not even need to update
        update = false;

        config(Integer.class, (DianYaNodeBuild entity, Integer value) -> {
            PowerModule power = entity.power;
            Building other = world.build(value);
            boolean contains = power.links.contains(value), valid = other != null && other.power != null;
//            assert other != null;
            //System.out.println(entity.getDisplayName() + "-" + (other != null ? other.getDisplayName() : "1"));
            //System.out.println((power instanceof DianYaModule) + "-" + (other != null && other.power instanceof DianYaModule));
            //System.out.println((power instanceof PowerModule) + "-" + (other != null && other.power instanceof PowerModule));
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
            }else if(linkValid(entity, other) && valid && power.links.size < maxNodes && ((other.block.consPower instanceof ConsumeDianYa dd && dd.dianya == dianya || (other.block instanceof DianYaBlock d && d.dianya == dianya)) || (other.block == SYBSBlocks.bianyaqi || entity.block == SYBSBlocks.bianyaqi))){

                power.links.addUnique(other.pos());

                if(other.team == entity.team){
                    other.power.links.addUnique(entity.pos());
                }

                power.graph.addGraph(other.power.graph);
            }
        });

        config(Point2[].class, (DianYaNodeBuild tile, Point2[] value) -> {
            IntSeq old = new IntSeq(tile.power.links);

            //clear old
            for(int i = 0; i < old.size; i++){
                configurations.get(Integer.class).get(tile, old.get(i));
            }

            //set new
            for(Point2 p : value){
                configurations.get(Integer.class).get(tile, Point2.pack(p.x + tile.tileX(), p.y + tile.tileY()));
            }
        });
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("power6", makePowerBalance());
        addBar("batteries", makeBatteryBalance());

        addBar("connections", entity -> new Bar(() ->
                Core.bundle.format("bar.powerlines", entity.power.links.size, maxNodes),
                () -> Pal.items,
                () -> (float)entity.power.links.size / (float)maxNodes
        ));
    }

    public Func<Building, Bar> makePowerBalance(){
        Seq<String> s = new Seq<>() ;
        s.add("bar.power", "bar.diyadian", "bar.zhongyadian", "bar.gaoyadian") ;
        return entity -> new Bar(() ->
                Core.bundle.format(s.get(dianya),
                        ((entity.power.graph.getPowerBalance() >= 0 ? "+" : "") + UI.formatAmount((long)(entity.power.graph.getPowerBalance() * 60)))),
                () -> Pal.powerBar,
                () -> Mathf.clamp(entity.power.graph.getLastPowerProduced() / entity.power.graph.getLastPowerNeeded())
        );
    }

    public Func<Building, Bar> makeBatteryBalance(){
        Seq<String> s = new Seq<>() ;
        s.add("bar.powerstored", "bar.diyadianchi", "bar.zhongyadianchi", "bar.gaoyadianchi") ;

        return entity -> new Bar(() ->
                Core.bundle.format(s.get(dianya),
                        (UI.formatAmount((long)entity.power.graph.getLastPowerStored())), UI.formatAmount((long)entity.power.graph.getLastCapacity())),
                () -> Pal.powerBar,
                () -> Mathf.clamp(entity.power.graph.getLastPowerStored() / entity.power.graph.getLastCapacity())
        );
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.powerRange, laserRange, StatUnit.blocks);
        stats.add(Stat.powerConnections, maxNodes, StatUnit.none);
    }

    @Override
    public void init(){
        super.init();

        clipSize = Math.max(clipSize, laserRange * tilesize);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        Tile tile = world.tile(x, y);

        if(tile == null || !autolink) return;

        Lines.stroke(1f);
        Draw.color(Pal.placing);
        Drawf.circles(x * tilesize + offset, y * tilesize + offset, laserRange * tilesize);

        getPotentialLinks(tile, player.team(), other -> {
            Draw.color(laserColor1, Renderer.laserOpacity * 0.5f);
            drawLaser(x * tilesize + offset, y * tilesize + offset, other.x, other.y, size, other.block.size);

            Drawf.square(other.x, other.y, other.block.size * tilesize / 2f + 2f, Pal.place);
        });

        Draw.reset();
    }

    @Override
    public void changePlacementPath(Seq<Point2> points, int rotation){
        Placement.calculateNodes(points, this, rotation, (point, other) -> overlaps(world.tile(point.x, point.y), world.tile(other.x, other.y)));
    }

    protected void setupColor(float satisfaction){
        Draw.color(laserColor1, laserColor2, (1f - satisfaction) * 0.86f + Mathf.absin(3f, 0.1f));
        Draw.alpha(Renderer.laserOpacity);
    }

    public void drawLaser(float x1, float y1, float x2, float y2, int size1, int size2){
        float angle1 = Angles.angle(x1, y1, x2, y2),
                vx = Mathf.cosDeg(angle1), vy = Mathf.sinDeg(angle1),
                len1 = size1 * tilesize / 2f - 1.5f, len2 = size2 * tilesize / 2f - 1.5f;

        Drawf.laser(laser, laserEnd, x1 + vx*len1, y1 + vy*len1, x2 - vx*len2, y2 - vy*len2, laserScale);
    }

    public void applyDamage(float x1, float y1, float x2, float y2, float size1, float size2, Building entity, Building other) {
        float angle = Angles.angle(x1, y1, x2, y2),
                vx = Mathf.cosDeg(angle), vy = Mathf.sinDeg(angle),
                len1 = size1 * tilesize / 2f - 1.5f, len2 = size2 * tilesize / 2f - 1.5f;
        Seq<Color> s = new Seq<>() ;
        s.addAll(Pal.accent, Pal.place, Pal.items, Pal.noplace) ;
        if (entity.power.graph.getPowerBalance() > 0) {
            Vec2 v = new Vec2(x1 + vx * len1, y1 + vy * len1) ;
            Vec2 v2 = new Vec2(x2 - vx * len2, y2 - vy * len2) ;
            BulletType b = new ContinuousLaserBulletType(){
                @Override
                public void draw(Bullet b) {
                    Draw.color(colors[0], Core.settings.getInt("preferredlaseropacity", 1) / 100f) ;
                    Lines.stroke(width);
                    Lines.line(v.x, v.y, v2.x, v2.y);
                    Fill.circle(v.x, v.y, width * 1.5f / 2);
                    Fill.circle(v2.x, v2.y, width * 1.5f / 2);
                    Draw.color(colors[1], Core.settings.getInt("preferredlaseropacity", 1) / 100f) ;
                    Lines.stroke(width * 0.5f);
                    Lines.line(v.x, v.y, v2.x, v2.y);
                    Fill.circle(v.x, v.y, width * 0.75f / 2);
                    Fill.circle(v2.x, v2.y, width * 0.75f / 2);
                }
                {
                damage = 6 * entity.power.graph.getPowerBalance() / 1000 * dianya ;
                damageInterval = 1 ;
                length = v.dst(v2) ;
                width = 2 ;
                hitEffect = Fx.hitBeam ;
                lifetime = 2 ;
                fadeTime = 1 ;
                oscMag = 0 ;
                shake = 0 ;
                colors = new Color[]{s.get(dianya), Color.white} ;
            }};
            Bullet bb = b.create(entity, entity.team, v.x, v.y, angle) ;
            bb.add();
            if (entity.timer(t, 30)) {
                Fx.chainLightning.at(v.x, v.y, 0, s.get(dianya), v2);
            }
        }
    }

    protected boolean overlaps(float srcx, float srcy, Tile other, Block otherBlock, float range){
        return Intersector.overlaps(Tmp.cr1.set(srcx, srcy, range), Tmp.r1.setCentered(other.worldx() + otherBlock.offset, other.worldy() + otherBlock.offset,
                otherBlock.size * tilesize, otherBlock.size * tilesize));
    }

    protected boolean overlaps(float srcx, float srcy, Tile other, float range){
        return Intersector.overlaps(Tmp.cr1.set(srcx, srcy, range), other.getHitbox(Tmp.r1));
    }

    protected boolean overlaps(Building src, Building other, float range){
        return overlaps(src.x, src.y, other.tile(), range);
    }

    protected boolean overlaps(Tile src, Tile other, float range){
        return overlaps(src.drawx(), src.drawy(), other, range);
    }

    public boolean overlaps(@Nullable Tile src, @Nullable Tile other){
        if(src == null || other == null) return true;
        return Intersector.overlaps(Tmp.cr1.set(src.worldx() + offset, src.worldy() + offset, laserRange * tilesize), Tmp.r1.setSize(size * tilesize).setCenter(other.worldx() + offset, other.worldy() + offset));
    }

    protected void getPotentialLinks(Tile tile, Team team, Cons<Building> others){
        if(!autolink) return;

        Boolf<Building> valid = other ->{
            boolean b = other != null && ((other.block.consPower instanceof ConsumeDianYa d && d.dianya == dianya) || (other.block instanceof DianYaBlock dyb && dyb.dianya == dianya) || other.block == SYBSBlocks.bianyaqi) && other.tile() != tile && other.block.connectedPower && other.power != null &&
                (other.block.outputsPower || other.block.consumesPower || other.block instanceof DianYaNode) &&
                overlaps(tile.x * tilesize + offset, tile.y * tilesize + offset, other.tile(), laserRange * tilesize) && other.team == team &&
                !graphs.contains(other.power.graph) &&
                !DianYaNode.insulated(tile, other.tile) &&
                !(other instanceof DianYaNode.DianYaNodeBuild obuild && obuild.power.links.size >= ((DianYaNode)obuild.block).maxNodes) &&
                !Structs.contains(Edges.getEdges(size), p -> { //do not link to adjacent buildings
                    var t = world.tile(tile.x + p.x, tile.y + p.y);
                    return t != null && t.build == other;
                }) ;
//            System.out.println((overlaps(tile.x * tilesize + offset, tile.y * tilesize + offset, other.tile(), laserRange * tilesize)) + " " + (!Structs.contains(Edges.getEdges(size), p -> { //do not link to adjacent buildings
//                var t = world.tile(tile.x + p.x, tile.y + p.y);
//                return t != null && t.build == other;
//            })) + " " + (!DianYaNode.insulated(tile, other.tile)) + " ");
            return b ;
        };

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
                //System.out.println(valid.get(build));
                if(valid.get(build) && !tempBuilds.contains(build)){
                    tempBuilds.add(build);
                }
            });
        }

        tempBuilds.sort((a, b) -> {
            int type = -Boolean.compare(a.block instanceof DianYaNode, b.block instanceof DianYaNode);
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

    //TODO code duplication w/ method above?
    /** Iterates through linked nodes of a block at a tile. All returned buildings are power nodes. */
    public static void getNodeLinks(Tile tile, Block block, Team team, Cons<Building> others){
        Boolf<Building> valid = other -> {
            boolean b = other != null && other.tile() != tile && other.block instanceof DianYaNode node &&
                node.autolink && ((block.consPower instanceof ConsumeDianYa d && d.dianya == node.dianya)) &&
                other.power.links.size < node.maxNodes &&
                node.overlaps(other.x, other.y, tile, block, node.laserRange * tilesize) && other.team == team
                && !graphs.contains(other.power.graph) &&
                !DianYaNode.insulated(tile, other.tile) &&
                !Structs.contains(Edges.getEdges(block.size), p -> { //do not link to adjacent buildings
                    var t = world.tile(tile.x + p.x, tile.y + p.y);
                    return t != null && t.build == other;
                });
//            System.out.println(!Structs.contains(Edges.getEdges(block.size), p -> {
//                var t = world.tile(tile.x + p.x, tile.y + p.y);
//                return t != null && t.build == other;
//            }) + " " + (other.block instanceof DianYaNode node && node.overlaps(other.x, other.y, tile, block, node.laserRange * tilesize) && other.team == team) + " " + (other.block instanceof DianYaNode node && other.power.links.size < node.maxNodes) + " " + ( other != null && other.tile() != tile && other.block instanceof DianYaNode node &&
//                    node.autolink && block.consPower instanceof ConsumeDianYa d && d.dianya == node.dianya) + " " + (!graphs.contains(other.power.graph)) + " " + (!DianYaNode.insulated(tile, other.tile)));
            return b ;
        };

        tempBuilds.clear();
        graphs.clear();

        //add conducting graphs to prevent double link
        for(var p : Edges.getEdges(block.size)){
            Tile other = tile.nearby(p);
            if(other != null && other.team() == team && other.build != null && other.build.power != null
                    && !(block.consumesPower && other.block().consumesPower && !block.outputsPower && !other.block().outputsPower) && other.build.block.consPower instanceof ConsumeDianYa d && block.consPower instanceof ConsumeDianYa c && d.dianya == c.dianya){
                graphs.add(other.build.power.graph);
            }
        }

        if(tile.build != null && tile.build.power != null && tile.build.block.consPower instanceof ConsumeDianYa d && block.consPower instanceof ConsumeDianYa c && d.dianya == c.dianya){
            graphs.add(tile.build.power.graph);
        }

        var rangeWorld = maxRange * tilesize;
        var tree = team.data().buildingTree;
        if(tree != null){
            tree.intersect(tile.worldx() - rangeWorld, tile.worldy() - rangeWorld, rangeWorld * 2, rangeWorld * 2, build -> {
                if(valid.get(build) && !tempBuilds.contains(build)){
                    tempBuilds.add(build);
                }
            });
        }

        tempBuilds.sort((a, b) -> {
            int type = -Boolean.compare(a.block instanceof DianYaNode, b.block instanceof DianYaNode);
            if(type != 0) return type;
            return Float.compare(a.dst2(tile), b.dst2(tile));
        });

        tempBuilds.each(valid, t -> {
            graphs.add(t.power.graph);
            others.get(t);
        });
    }

    @Override
    public void drawPlanConfigTop(BuildPlan plan, Eachable<BuildPlan> list){
        if(plan.config instanceof Point2[] ps){
            setupColor(1f);
            for(Point2 point : ps){
                int px = plan.x + point.x, py = plan.y + point.y;
                otherReq = null;
                list.each(other -> {
                    if(other.block != null
                            && (px >= other.x - ((other.block.size-1)/2) && py >= other.y - ((other.block.size-1)/2) && px <= other.x + other.block.size/2 && py <= other.y + other.block.size/2)
                            && other != plan && other.block.hasPower){
                        otherReq = other;
                    }
                });

                if(otherReq == null || otherReq.block == null) continue;

                drawLaser(plan.drawx(), plan.drawy(), otherReq.drawx(), otherReq.drawy(), size, otherReq.block.size);
            }
            Draw.color();
        }
    }

    public boolean linkValid(Building tile, Building link){
        return linkValid(tile, link, true);
    }

    public boolean linkValid(Building tile, Building link, boolean checkMaxNodes){
        if(tile == link || link == null || !link.block.hasPower || !link.block.connectedPower || tile.team != link.team) return false;

        if(overlaps(tile, link, laserRange * tilesize) || (((link.block instanceof DianYaNode node) || (link.block == SYBSBlocks.bianyaqi)) && overlaps(link, tile, (link.block instanceof DianYaNode ? ((DianYaNode)link.block).laserRange : link.block == SYBSBlocks.bianyaqi ? SYBSBlocks.bianyaqi.laserRange : 0) * tilesize))){
            if(checkMaxNodes && ((link.block instanceof DianYaNode node) || (link.block == SYBSBlocks.bianyaqi))){
                return link.power.links.size < (link.block instanceof DianYaNode ? ((DianYaNode)link.block).maxNodes : link.block == SYBSBlocks.bianyaqi ? SYBSBlocks.bianyaqi.maxNodes : 0) || link.power.links.contains(tile.pos());
            }
            return true;
        }
        return false;
    }

    public static boolean insulated(Tile tile, Tile other){
        return insulated(tile.x, tile.y, other.x, other.y);
    }

    public static boolean insulated(Building tile, Building other){
        return insulated(tile.tileX(), tile.tileY(), other.tileX(), other.tileY());
    }

    public static boolean insulated(int x, int y, int x2, int y2){
        return World.raycast(x, y, x2, y2, (wx, wy) -> {
            Building tile = world.build(wx, wy);
            return tile != null && tile.isInsulated();
        });
    }

    public class DianYaNodeBuild extends DianYaBuild{

        @Override
        public void created(){ // Called when one is placed/loaded in the world
            if(autolink && laserRange > maxRange) maxRange = laserRange;

            super.created();
        }


        @Override
        public void placed(){
            if(net.client() || power.links.size > 0) return;

            getPotentialLinks(tile, team, other -> {
                if(!power.links.contains(other.pos())){
                    configureAny(other.pos());
                }
            });

            super.placed();
        }

        @Override
        public void dropped(){
            power.links.clear();
            updatePowerGraph();
        }

        @Override
        public boolean onConfigureBuildTapped(Building other){
            if(linkValid(this, other)){
                configure(other.pos());
                return false;
            }

            if(this == other){ //double tapped
                if(other.power.links.size == 0){ //find links
                    Seq<Point2> points = new Seq<>();
                    getPotentialLinks(tile, team, link -> {
                        if(!insulated(this, link) && points.size < maxNodes){
                            points.add(new Point2(link.tileX() - tile.x, link.tileY() - tile.y));
                        }
                    });
                    configure(points.toArray(Point2.class));
                }else{ //clear links
                    configure(new Point2[0]);
                }
                deselect();
                return false;
            }

            return true;
        }

        @Override
        public void drawSelect(){
            super.drawSelect();

            if(!drawRange) return;

            Lines.stroke(1f);

            Draw.color(Pal.accent);
            Drawf.circles(x, y, laserRange * tilesize);
            Draw.reset();
        }

        @Override
        public void drawConfigure(){

            Drawf.circles(x, y, tile.block().size * tilesize / 2f + 1f + Mathf.absin(Time.time, 4f, 1f));

            if(drawRange){
                Drawf.circles(x, y, laserRange * tilesize);

                for(int x = (int)(tile.x - laserRange - 2); x <= tile.x + laserRange + 2; x++){
                    for(int y = (int)(tile.y - laserRange - 2); y <= tile.y + laserRange + 2; y++){
                        Building link = world.build(x, y);

                        if(link != this && linkValid(this, link, false)){
                            boolean linked = linked(link);

                            if(linked){
                                Drawf.square(link.x, link.y, link.block.size * tilesize / 2f + 1f, Pal.place);
                            }
                        }
                    }
                }

                Draw.reset();
            }else{
                power.links.each(i -> {
                    var link = world.build(i);
                    if(link != null && linkValid(this, link, false)){
                        Drawf.square(link.x, link.y, link.block.size * tilesize / 2f + 1f, Pal.place);
                    }
                });
            }
        }

        @Override
        public void draw(){
            super.draw();
            if(Mathf.zero(Renderer.laserOpacity) || isPayload()) return;

            Draw.z(Layer.power);
            setupColor(power.graph.getSatisfaction());

            for(int i = 0; i < power.links.size; i++){
                Building link = world.build(power.links.get(i));

                if(!linkValid(this, link)) continue;

                if(link.block instanceof DianYaNode && link.id >= id) continue;

                drawLaser(x, y, link.x, link.y, size, link.block.size);
                applyDamage(x, y, link.x, link.y, size, link.block.size, this, link);
            }

            Draw.reset();
        }

        protected boolean linked(Building other){
            return power.links.contains(other.pos());
        }

        @Override
        public Point2[] config(){
            Point2[] out = new Point2[power.links.size];
            for(int i = 0; i < out.length; i++){
                out[i] = Point2.unpack(power.links.get(i)).sub(tile.x, tile.y);
            }
            return out;
        }
    }
}
