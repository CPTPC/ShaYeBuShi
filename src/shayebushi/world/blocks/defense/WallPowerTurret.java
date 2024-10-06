package shayebushi.world.blocks.defense;

import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Nullable;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Bullets;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.logic.LAccess;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.consumers.ConsumeLiquidBase;
import mindustry.world.meta.Env;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatValues;
import shayebushi.SYBSStatValues;
import shayebushi.world.blocks.defense.turrets.AbilityTurret;

public class WallPowerTurret extends WallTurret{
    public BulletType shootType;

    public WallPowerTurret(String name){
        super(name);
        hasPower = true;
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.ammo, SYBSStatValues.ammo(ObjectMap.of(this, shootType)));
    }

    public void limitRange(float margin){
        limitRange(shootType, margin);
    }

    public class WallPowerTurretBuild extends WallTurretBuild {

        @Override
        public void updateTile(){
            unit.ammo(power.status * unit.type().ammoCapacity);

            super.updateTile();
        }

        @Override
        public double sense(LAccess sensor){
            return switch(sensor){
                case ammo -> power.status;
                case ammoCapacity -> 1;
                default -> super.sense(sensor);
            };
        }

        @Override
        public BulletType useAmmo(){
            //nothing used directly
            return shootType;
        }

        @Override
        public boolean hasAmmo(){
            //you can always rotate, but never shoot if there's no power
            return true;
        }

        @Override
        public BulletType peekAmmo(){
            return shootType;
        }
    }
}
