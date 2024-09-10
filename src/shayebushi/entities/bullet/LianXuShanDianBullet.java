package shayebushi.entities.bullet;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import mindustry.content.Fx;
import mindustry.entities.Fires;
import mindustry.entities.Lightning;
import mindustry.entities.Units;
import mindustry.entities.bullet.LightningBulletType;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Hitboxc;
import mindustry.gen.Unit;
import mindustry.world.blocks.ConstructBlock;

import static mindustry.Vars.tilesize;

public class LianXuShanDianBullet extends LightningBulletType {
    public int maxLinks = 5 ;
    public float radius = 15 * tilesize ;
    public static Seq<Unit> units = new Seq<>() ;
    public static Seq<Building> builds = new Seq<>() ;
    @Override
    public void init(Bullet b){
        super.init(b);
        var ref = new Object() {
            boolean hit = false;
        };
        Units.nearby(null, b.x(), b.y(), calculateRange(), u -> {
            if (ref.hit) return ;
            if (u.team != b.team) {
                hitEntity(b, u, u.health);
                ref.hit = true ;
            }
        });
        if (!ref.hit) {
            Units.nearbyBuildings(b.x(), b.y(), lightningLength, u -> {
                if (u.team != b.team) {
                    hitTile(b, u, u.x, u.y, u.health, false);
                }
            });
        }
    }
    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        super.hitEntity(b, entity, health);
        apply(b, entity.x(), entity.y());
    }
    @Override
    public void hitTile(Bullet b, Building build, float x, float y, float initialHealth, boolean direct){
        super.hitTile(b, build, x, y, initialHealth, direct);
        apply(b, x, y);
    }
    public void apply(Bullet b, float x, float y) {
        if (!(b.data instanceof Integer)) {
            b.data = 0 ;
        }
        if ((Integer)b.data > maxLinks) {
            units.clear() ;
            builds.clear() ;
            return ;
        }
        int d = (Integer)b.data ;
        Units.nearby(null, x, y, radius, u -> {
            if (u.team != b.team && !units.contains(u)) {
                b.data = ((Integer)b.data) + 1 ;
                hitEntity(b, u, u.health);
                Fx.chainLightning.at(x, y, 0, lightningColor, u);
                units.add(u) ;
            }
        });
        if ((Integer)b.data == d) {
            Units.nearbyBuildings(x, y, radius, u -> {
                if (u.team != b.team && !builds.contains(u)) {
                    b.data = ((Integer)b.data) + 1 ;
                    hitTile(b, u, x, y, u.health, false);
                    Fx.chainLightning.at(x, y, 0, lightningColor, u);
                    builds.add(u) ;
                }
            });
        }
        units.clear() ;
        builds.clear() ;
    }
}
