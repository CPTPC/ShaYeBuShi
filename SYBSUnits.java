package shayebushi;

import arc.*;
import arc.func.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.annotations.Annotations.*;
import mindustry.content.*;
import mindustry.entities.Sized;
import mindustry.game.*;
import mindustry.game.Teams.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;

import static mindustry.Vars.*;

/** Utility class for unit and team interactions.*/
public class SYBSUnits{
    private static final Rect hitrect = new Rect();
    private static Unit result;
    private static float cdist, cpriority;
    private static boolean boolResult;
    private static int intResult;
    private static Building buildResult;

    //prevents allocations in anyEntities
    private static boolean anyEntityGround;
    private static float aeX, aeY, aeW, aeH;
    private static final Cons<Unit> anyEntityLambda = unit -> {
        if(boolResult) return;
        if((unit.isGrounded() && !unit.type.allowLegStep) == anyEntityGround){
            unit.hitboxTile(hitrect);

            if(hitrect.overlaps(aeX, aeY, aeW, aeH)){
                boolResult = true;
            }
        }
    };

    /**
     * Validates a target.
     * @param target The target to validate
     * @param team The team of the thing doing tha targeting
     * @param x The X position of the thing doing the targeting
     * @param y The Y position of the thing doing the targeting
     * @param range The maximum distance from the target X/Y the targeter can be for it to be valid
     * @return whether the target is invalid
     */
    public static boolean invalidateTarget(Posc target, Team team, float x, float y, float range, boolean fanqian, boolean ground, boolean air, boolean naval){
//        if (target instanceof Unit unit) {
//            System.out.println(unit.isGrounded() && !unit.type.naval && ground);
//        }
        return target == null ||
                (range != Float.MAX_VALUE && !target.within(x, y, range + (target instanceof Sized hb ? hb.hitSize()/2f : 0f))) ||
                (target instanceof Teamc t && t.team() == team) ||
                (target instanceof Healthc h && !h.isValid()) ||
                (target instanceof Unit u && (!u.targetable(team) && !fanqian)) || (
                (!ground && target instanceof Unit u2 && ShaYeBuShi.realGrounded(u2)) &&
                (!air && target instanceof Unit u3 && u3.isFlying()) &&
                (!naval && target instanceof Unit u4 && ShaYeBuShi.isNaval(u4))) ;
    }

    /** See {@link #invalidateTarget(Posc, Team, float, float, float)} */
    public static boolean invalidateTarget(Posc target, Team team, float x, float y, boolean fanqian, boolean ground, boolean air, boolean naval){
        return invalidateTarget(target, team, x, y, Float.MAX_VALUE, fanqian, ground, air, naval);
    }

    /** See {@link #invalidateTarget(Posc, Team, float, float, float)} */
    public static boolean invalidateTarget(Teamc target, Unit targeter, float range, boolean fanqian, boolean ground, boolean air, boolean naval){
        return invalidateTarget(target, targeter.team(), targeter.x(), targeter.y(), range, fanqian, ground, air, naval);
    }

    /** Returns whether there are any entities on this tile. */
    public static boolean anyEntities(Tile tile, boolean ground){
        float size = tile.block().size * tilesize;
        return anyEntities(tile.drawx() - size/2f, tile.drawy() - size/2f, size, size, ground);
    }

    /** Returns whether there are any entities on this tile. */
    public static boolean anyEntities(Tile tile){
        return anyEntities(tile, true);
    }

    public static boolean anyEntities(float x, float y, float size){
        return anyEntities(x - size/2f, y - size/2f, size, size, true);
    }

    public static boolean anyEntities(float x, float y, float width, float height){
        return anyEntities(x, y, width, height, true);
    }

    public static boolean anyEntities(float x, float y, float width, float height, boolean ground){
        boolResult = false;
        anyEntityGround = ground;
        aeX = x;
        aeY = y;
        aeW = width;
        aeH = height;

        nearby(x, y, width, height, anyEntityLambda);
        return boolResult;
    }

    public static boolean anyEntities(float x, float y, float width, float height, Boolf<Unit> check){
        boolResult = false;

        nearby(x, y, width, height, unit -> {
            if(boolResult) return;
            if(check.get(unit)){
                unit.hitboxTile(hitrect);

                if(hitrect.overlaps(x, y, width, height)){
                    boolResult = true;
                }
            }
        });
        return boolResult;
    }

    /** Returns the nearest damaged tile. */
    public static Building findDamagedTile(Team team, float x, float y){
        return indexer.getDamaged(team).min(b -> b.dst2(x, y));
    }

    /** Returns the nearest ally tile in a range. */
    public static Building findAllyTile(Team team, float x, float y, float range, Boolf<Building> pred){
        return indexer.findTile(team, x, y, range, pred);
    }

    /** Returns the nearest enemy tile in a range. */
    public static Building findEnemyTile(Team team, float x, float y, float range, Boolf<Building> pred){
        if(team == Team.derelict) return null;

        return indexer.findEnemyTile(team, x, y, range, pred);
    }

    /** @return the closest building of the provided team that matches the predicate. */
    public static @Nullable Building closestBuilding(Team team, float wx, float wy, float range, Boolf<Building> pred){
        buildResult = null;
        cdist = 0f;

        var buildings = team.data().buildingTree;
        if(buildings == null) return null;
        buildings.intersect(wx - range, wy - range, range*2f, range*2f, b -> {
            if(pred.get(b)){
                float dst = b.dst(wx, wy) - b.hitSize()/2f;
                if(dst <= range && (buildResult == null || dst <= cdist)){
                    cdist = dst;
                    buildResult = b;
                }
            }
        });

        return buildResult;
    }

    /** Iterates through all buildings in a range. */
    public static void nearbyBuildings(float x, float y, float range, Cons<Building> cons){
        indexer.allBuildings(x, y, range, cons);
    }

    /** Returns the closest target enemy. First, units are checked, then tile entities. */
    public static Teamc closestTarget(Team team, float x, float y, float range, boolean fanqian){
        return closestTarget(team, x, y, range, Unit::isValid, fanqian);
    }

    /** Returns the closest target enemy. First, units are checked, then tile entities. */
    public static Teamc closestTarget(Team team, float x, float y, float range, Boolf<Unit> unitPred, boolean fanqian){
        return closestTarget(team, x, y, range, unitPred, t -> true, fanqian);
    }

    /** Returns the closest target enemy. First, units are checked, then tile entities. */
    public static Teamc closestTarget(Team team, float x, float y, float range, Boolf<Unit> unitPred, Boolf<Building> tilePred, boolean fanqian){
        if(team == Team.derelict) return null;

        Unit unit = closestEnemy(team, x, y, range, unitPred, fanqian);
        if(unit != null){
//            if (!unit.targetable(team)) {
//                System.out.println(unit.getControllerName());
//            }
            return unit;
        }else{
            return findEnemyTile(team, x, y, range, tilePred);
        }
    }

    /** Returns the closest target enemy. First, units are checked, then buildings. */
    public static Teamc bestTarget(Team team, float x, float y, float range, Boolf<Unit> unitPred, Boolf<Building> tilePred, Sortf sort, boolean fanqian){
        if(team == Team.derelict) return null;

        Unit unit = bestEnemy(team, x, y, range, unitPred, sort, fanqian);
        if(unit != null){
            return unit;
        }else{
            return findEnemyTile(team, x, y, range, tilePred);
        }
    }

    /** Returns the closest enemy of this team. Filter by predicate. */
    public static Unit closestEnemy(Team team, float x, float y, float range, Boolf<Unit> predicate, boolean fanqian){
        if(team == Team.derelict) return null;

        result = null;
        cdist = 0f;
        cpriority = -99999f;

        nearbyEnemies(team, x - range, y - range, range*2f, range*2f, e -> {
            if(e.dead() || !predicate.get(e) || e.team == Team.derelict || e.inFogTo(team) || (!e.targetable(team) && !fanqian)) return;

            float dst2 = e.dst2(x, y) - (e.hitSize * e.hitSize);
            if(dst2 < range*range && (result == null || dst2 < cdist || e.type.targetPriority > cpriority) && e.type.targetPriority >= cpriority){
                result = e;
                cdist = dst2;
                cpriority = e.type.targetPriority;
            }
        });

        return result;
    }

    /** Returns the closest enemy of this team using a custom comparison function. Filter by predicate. */
    public static Unit bestEnemy(Team team, float x, float y, float range, Boolf<Unit> predicate, Sortf sort, boolean fanqian){
        if(team == Team.derelict) return null;

        result = null;
        cdist = 0f;
        cpriority = -99999f;

        nearbyEnemies(team, x - range, y - range, range*2f, range*2f, e -> {
            if(e.dead() || !predicate.get(e) || e.team == Team.derelict || !e.within(x, y, range + e.hitSize/2f) || e.inFogTo(team) || (!e.targetable(team) && !fanqian)) return;

            float cost = sort.cost(e, x, y);
            if((result == null || cost < cdist || e.type.targetPriority > cpriority) && e.type.targetPriority >= cpriority){
                result = e;
                cdist = cost;
                cpriority = e.type.targetPriority;
            }
        });

        return result;
    }

    /** Returns the closest ally of this team. Filter by predicate. No range. */
    public static Unit closest(Team team, float x, float y, Boolf<Unit> predicate){
        result = null;
        cdist = 0f;

        for(Unit e : Groups.unit){
            if(!predicate.get(e) || e.team() != team) continue;

            float dist = e.dst2(x, y);
            if(result == null || dist < cdist){
                result = e;
                cdist = dist;
            }
        }

        return result;
    }

    /** Returns the closest ally of this team in a range. Filter by predicate. */
    public static Unit closest(Team team, float x, float y, float range, Boolf<Unit> predicate){
        result = null;
        cdist = 0f;

        nearby(team, x, y, range, e -> {
            if(!predicate.get(e)) return;

            float dist = e.dst2(x, y);
            if(result == null || dist < cdist){
                result = e;
                cdist = dist;
            }
        });

        return result;
    }

    /** Returns the closest ally of this team in a range. Filter by predicate. */
    public static Unit closest(Team team, float x, float y, float range, Boolf<Unit> predicate, Sortf sort){
        result = null;
        cdist = 0f;

        nearby(team, x, y, range, e -> {
            if(!predicate.get(e)) return;

            float dist = sort.cost(e, x, y);
            if(result == null || dist < cdist){
                result = e;
                cdist = dist;
            }
        });

        return result;
    }

    /** Returns the closest ally of this team. Filter by predicate.
     * Unlike the closest() function, this only guarantees that unit hitboxes overlap the range. */
    public static Unit closestOverlap(Team team, float x, float y, float range, Boolf<Unit> predicate){
        result = null;
        cdist = 0f;

        nearby(team, x - range, y - range, range*2f, range*2f, e -> {
            if(!predicate.get(e)) return;

            float dist = e.dst2(x, y);
            if(result == null || dist < cdist){
                result = e;
                cdist = dist;
            }
        });

        return result;
    }

    /** @return whether any units exist in this square (centered) */
    public static int count(float x, float y, float size, Boolf<Unit> filter){
        return count(x - size/2f, y - size/2f, size, size, filter);
    }

    /** @return whether any units exist in this rectangle */
    public static int count(float x, float y, float width, float height, Boolf<Unit> filter){
        intResult = 0;
        Groups.unit.intersect(x, y, width, height, v -> {
            if(filter.get(v)){
                intResult ++;
            }
        });
        return intResult;
    }

    /** @return whether any units exist in this rectangle */
    public static boolean any(float x, float y, float width, float height, Boolf<Unit> filter){
        return count(x, y, width, height, filter) > 0;
    }

    /** Iterates over all units in a rectangle. */
    public static void nearby(@Nullable Team team, float x, float y, float width, float height, Cons<Unit> cons){
        if(team != null){
            team.data().tree().intersect(x, y, width, height, cons);
        }else{
            for(var other : state.teams.present){
                other.tree().intersect(x, y, width, height, cons);
            }
        }
    }

    /** Iterates over all units in a circle around this position. */
    public static void nearby(@Nullable Team team, float x, float y, float radius, Cons<Unit> cons){
        nearby(team, x - radius, y - radius, radius*2f, radius*2f, unit -> {
            if(unit.within(x, y, radius + unit.hitSize/2f)){
                cons.get(unit);
            }
        });
    }

    /** Iterates over all units in a rectangle. */
    public static void nearby(float x, float y, float width, float height, Cons<Unit> cons){
        Groups.unit.intersect(x, y, width, height, cons);
    }

    /** Iterates over all units in a rectangle. */
    public static void nearby(Rect rect, Cons<Unit> cons){
        nearby(rect.x, rect.y, rect.width, rect.height, cons);
    }

    /** Iterates over all units that are enemies of this team. */
    public static void nearbyEnemies(Team team, float x, float y, float width, float height, Cons<Unit> cons){
        Seq<TeamData> data = state.teams.present;
        for(int i = 0; i < data.size; i++){
            if(data.items[i].team != team){
                nearby(data.items[i].team, x, y, width, height, cons);
            }
        }
    }

    /** Iterates over all units that are enemies of this team. */
    public static void nearbyEnemies(Team team, float x, float y, float radius, Cons<Unit> cons){
        nearbyEnemies(team, x - radius, y - radius, radius * 2f, radius * 2f, u -> {
            if(u.within(x, y, radius + u.hitSize/2f)){
                cons.get(u);
            }
        });
    }

    /** Iterates over all units that are enemies of this team. */
    public static void nearbyEnemies(Team team, Rect rect, Cons<Unit> cons){
        nearbyEnemies(team, rect.x, rect.y, rect.width, rect.height, cons);
    }

    /** @return whether there is an enemy in this rectangle. */
    public static boolean nearEnemy(Team team, float x, float y, float width, float height){
        Seq<TeamData> data = state.teams.present;
        for(int i = 0; i < data.size; i++){
            var other = data.items[i];
            if(other.team != team){
                if(other.tree().any(x, y, width, height)){
                    return true;
                }
                if(other.turretTree != null && other.turretTree.any(x, y, width, height)){
                    return true;
                }
            }
        }
        return false;
    }

    public interface Sortf{
        float cost(Unit unit, float x, float y);
    }
}