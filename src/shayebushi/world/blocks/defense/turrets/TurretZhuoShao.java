package shayebushi.world.blocks.defense.turrets;

import mindustry.type.Liquid;
import mindustry.world.blocks.defense.turrets.LiquidTurret;

public class TurretZhuoShao extends LiquidTurret {

    public TurretZhuoShao(String name) {
        super(name);
    }

    public class TurretZuoShaoBuild extends LiquidTurretBuild{
        @Override
        public void updateTile(){
            super.updateTile();
            if (peekAmmo() != null) {
                smokeEffect = peekAmmo().smokeEffect;
                shootEffect = peekAmmo().shootEffect;
            }
        }
    }
}
