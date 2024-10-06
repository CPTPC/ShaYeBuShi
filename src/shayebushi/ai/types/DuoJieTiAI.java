package shayebushi.ai.types;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Time;
import mindustry.entities.units.AIController;
import mindustry.gen.Teamc;
import mindustry.world.meta.BlockFlag;
import shayebushi.ShaYeBuShi;

import static mindustry.Vars.*;

public class DuoJieTiAI extends AIController {

    @Override
    public void updateMovement(){
        unloadPayloads();

        if(target != null && unit.hasWeapons()){
//            if(unit.type.circleTarget){
//                circleAttack(120f);
//            }else{
//                moveTo(target, unit.type.range * 0.8f);
//                unit.lookAt(target);
//            }
            float rad = (unit.type.range - tilesize * 0.5f) ;
            float angle = (360 / (rad * 2 * (float) Math.PI / unit.speed()) * Time.time) % 360 ;
            Vec2 v = ShaYeBuShi.circle(angle, rad, target.x(), target.y()) ;
//            if (v.x < 0 || v.y < 0) {
//                v = ShaYeBuShi.circle(angle, Math.min(target.x(), target.y()), target.x(), target.y()) ;
//            }
//            if (v.x > world.width() || v.y > world.height()) {
//                v = ShaYeBuShi.circle(angle, Math.min(world.width() - target.x(), world.height() - target.y()), target.x(), target.y()) ;
//            }
            moveTo(v, 0) ;
            unit.lookAt(v) ;
        }

        if(target == null && state.rules.waves && unit.team == state.rules.defaultTeam){
            moveTo(getClosestSpawner(), state.rules.dropZoneRadius + 130f);
        }
    }

    @Override
    public Teamc findTarget(float x, float y, float range, boolean air, boolean ground){
        var result = findMainTarget(x, y, range, air, ground);

        //if the main target is in range, use it, otherwise target whatever is closest
        return checkTarget(result, x, y, range) ? target(x, y, range, air, ground) : result;
    }

    @Override
    public Teamc findMainTarget(float x, float y, float range, boolean air, boolean ground){
        var core = targetFlag(x, y, BlockFlag.core, true);

        if(core != null && Mathf.within(x, y, core.getX(), core.getY(), range)){
            return core;
        }

        for(var flag : unit.type.targetFlags){
            if(flag == null){
                Teamc result = target(x, y, range, air, ground);
                if(result != null) return result;
            }else if(ground){
                Teamc result = targetFlag(x, y, flag, true);
                if(result != null) return result;
            }
        }

        return core;
    }
}
