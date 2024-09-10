package shayebushi.world.blocks.defense.turrets;

import mindustry.world.blocks.defense.turrets.ContinuousTurret;
import mindustry.world.blocks.defense.turrets.LaserTurret;

public class TurretSangZhong extends ContinuousTurret {
    public float rotPerSeconds = 1.5f ;
    public TurretSangZhong(String name) {
        super(name);
        shootX = shootY = 0 ;
    }
    public class TurretSangZhongBuild extends ContinuousTurretBuild{
        @Override
        public void update(){
            super.update();
            rotation += rotPerSeconds ;
            shootCone = 361 ;
        }
        @Override
        public void turnToTarget(float a){

        }
    }
}
