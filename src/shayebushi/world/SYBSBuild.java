package shayebushi.world;

import arc.util.Tmp;
import mindustry.content.Liquids;
import mindustry.entities.Units;
import mindustry.game.Team;
import mindustry.game.Teams;
import mindustry.world.Block;
import mindustry.world.Build;
import mindustry.world.Tile;
import mindustry.world.blocks.ConstructBlock;
import mindustry.world.blocks.storage.CoreBlock;
import shayebushi.world.blocks.defense.turrets.TurretDieTai;

import static mindustry.Vars.*;
import static mindustry.Vars.state;

public class SYBSBuild extends Build {
//    @Override
    public static boolean validPlace(Block type, Team team, int x, int y, int rotation, boolean checkVisible){
        if (type != null && type.localizedName.equals("叠态") && type instanceof TurretDieTai d && d.jieduan < d.maxduidie){
            //Fx.upgradeCore.at(t.x,t.y);
            return true ;
        }
        //the wave team can build whatever they want as long as it's visible - banned blocks are not applicable
        if(type == null || (checkVisible && (!type.environmentBuildable() || (!type.isPlaceable() && !(state.rules.waves && team == state.rules.waveTeam && type.isVisible()))))){
            return false;
        }

        if((type.solid || type.solidifes) && Units.anyEntities(x * tilesize + type.offset - type.size*tilesize/2f, y * tilesize + type.offset - type.size*tilesize/2f, type.size * tilesize, type.size*tilesize)){
            return false;
        }

        if(!state.rules.editor){
            //find closest core, if it doesn't match the team, placing is not legal
            if(state.rules.polygonCoreProtection){
                float mindst = Float.MAX_VALUE;
                CoreBlock.CoreBuild closest = null;
                for(Teams.TeamData data : state.teams.active){
                    for(CoreBlock.CoreBuild tile : data.cores){
                        float dst = tile.dst2(x * tilesize + type.offset, y * tilesize + type.offset);
                        if(dst < mindst){
                            closest = tile;
                            mindst = dst;
                        }
                    }
                }
                if(closest != null && closest.team != team){
                    return false;
                }
            }else if(state.teams.anyEnemyCoresWithin(team, x * tilesize + type.offset, y * tilesize + type.offset, state.rules.enemyCoreBuildRadius + tilesize)){
                return false;
            }
        }

        Tile tile = world.tile(x, y);

        if(tile == null) return false;

        //campaign darkness check
        if(world.getDarkness(x, y) >= 3){
            return false;
        }

        if(!type.requiresWater && !contactsShallows(tile.x, tile.y, type) && !type.placeableLiquid){
            return false;
        }

        if((type.isFloor() && tile.floor() == type) || (type.isOverlay() && tile.overlay() == type)){
            return false;
        }

        if(!type.canPlaceOn(tile, team, rotation)){
            return false;
        }

        int offsetx = -(type.size - 1) / 2;
        int offsety = -(type.size - 1) / 2;

        for(int dx = 0; dx < type.size; dx++){
            for(int dy = 0; dy < type.size; dy++){
                int wx = dx + offsetx + tile.x, wy = dy + offsety + tile.y;

                Tile check = world.tile(wx, wy);

                if(
                        check == null || //nothing there
                                (type.size == 2 && world.getDarkness(wx, wy) >= 3) ||
                                (state.rules.staticFog && state.rules.fog && !fogControl.isDiscovered(team, wx, wy)) ||
                                (check.floor().isDeep() && !type.floating && !type.requiresWater && !type.placeableLiquid) || //deep water
                                (type == check.block() && check.build != null && rotation == check.build.rotation && type.rotate) || //same block, same rotation
                                !check.interactable(team) || //cannot interact
                                !check.floor().placeableOn || //solid wall
                                (!checkVisible && !check.block().alwaysReplace) || //replacing a block that should be replaced (e.g. payload placement)
                                !((type.canReplace(check.block()) || //can replace type
                                        (check.build instanceof ConstructBlock.ConstructBuild build && build.current == type && check.centerX() == tile.x && check.centerY() == tile.y)) && //same type in construction
                                        type.bounds(tile.x, tile.y, Tmp.r1).grow(0.01f).contains(check.block().bounds(check.centerX(), check.centerY(), Tmp.r2))) || //no replacement
                                (type.requiresWater && check.floor().liquidDrop != Liquids.water) //requires water but none found
                ) return false;
            }
        }

        if(state.rules.placeRangeCheck && !state.isEditor() && getEnemyOverlap(type, team, x, y) != null){
            return false;
        }

        return true;
    }
}
