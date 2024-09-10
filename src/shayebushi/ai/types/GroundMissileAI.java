package shayebushi.ai.types;

import arc.math.Mathf;
import arc.util.Nullable;
import mindustry.ai.types.GroundAI;
import mindustry.gen.TimedKillc;
import mindustry.gen.Unit;

public class GroundMissileAI extends GroundAI {
    public @Nullable
    Unit shooter;

    @Override
    public void updateMovement(){
        unloadPayloads();

        float time = unit instanceof TimedKillc t ? t.time() : 1000000f;

        if(time >= unit.type.homingDelay && shooter != null && !shooter.dead()){
            unit.lookAt(shooter.aimX, shooter.aimY);
        }

        //move forward forever
        unit.moveAt(vec.trns(unit.rotation, unit.type.missileAccelTime <= 0f ? unit.speed() : Mathf.pow(Math.min(time / unit.type.missileAccelTime, 1f), 2f) * unit.speed()));

        var build = unit.buildOn();

        //kill instantly on enemy building contact
        if(build != null && build.team != unit.team && (build == target || !build.block.underBullets)){
            unit.kill();
        }
    }

    @Override
    public boolean retarget(){
        //more frequent retarget due to high speed. TODO won't this lag?
        return timer.get(timerTarget, 4f);
    }
}
