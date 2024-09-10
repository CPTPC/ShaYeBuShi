package shayebushi.world.blocks.defense.turrets;

import arc.util.Nullable;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.entities.Mover;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.type.Item;
import mindustry.world.blocks.defense.turrets.ItemTurret;

public class TurretJingKong extends ItemTurret {
    public float maxReload = 1.5f ;
    public float maxRange = 400 ;
    public float jiacheng = 0.1f ;

    public TurretJingKong(String name) {
        super(name);
    }

    public class TurretJingKongBuild extends ItemTurretBuild {
        public float beilv = 1 ;
        @Override
        public void updateTile() {
            super.updateTile() ;
            var ref = new Object() {
                int amount = 0;
            };
            Units.nearbyBuildings(x, y, range(), b -> {
                if (b.block == block && b.team == team) {
                    ref.amount++ ;
                }
            });
            beilv = ref.amount * jiacheng ;
            reloadCounter += beilv * Time.delta ;
            updateReload();
        }

        @Override
        public float range() {
            return Math.min(super.range() * (beilv + 1), maxRange) ;
        }

        @Override
        public float baseReloadSpeed() {
            return Math.min(super.baseReloadSpeed() * (beilv + 1), maxReload) ;
        }

        @Override
        public void handleBullet(@Nullable Bullet bullet, float offsetX, float offsetY, float angleOffset){
            super.handleBullet(bullet, offsetX, offsetY, angleOffset);
            bullet.lifetime *= range() / range ;
        }

        @Override
        public byte version(){
            return 2;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(beilv);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            beilv = read.f() ;
        }
    }
}
