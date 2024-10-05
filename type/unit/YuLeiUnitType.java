package shayebushi.type.unit;

import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.world.meta.Env;
import shayebushi.ai.types.GroundMissileAI;
import shayebushi.entities.units.WaterMoveTimedKillUnit;

public class YuLeiUnitType extends SYBSUnitType {

    public YuLeiUnitType(String name){
        super(name);

        playerControllable = false;
        createWreck = false;
        createScorch = false;
        logicControllable = false;
        isEnemy = false;
        useUnitCap = false;
        allowedInPayloads = false;
        controller = u -> new GroundMissileAI();
        naval = true ;
        constructor = WaterMoveTimedKillUnit::create;
        envEnabled = Env.any;
        envDisabled = Env.none;
        physics = false;
        bounded = false;
        trailLength = 7;
        hidden = true;
        hoverable = false;
        speed = 4f;
        lifetime = 60f * 1.7f;
        rotateSpeed = 2.5f;
        range = 6f;
        targetPriority = -1f;
        outlineColor = Pal.darkOutline;
        fogRadius = 2f;
        loopSound = Sounds.missileTrail;
        loopSoundVolume = 0.05f;
        drawMinimap = false;
        targetable = false ;
        hittable = false ;
    }
    @Override
    public void update(Unit unit){
        super.update(unit);
        if (!(unit.floorOn().isLiquid)){
            unit.kill();
        }
    }
}
