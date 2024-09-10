package shayebushi.entities.units;

import arc.graphics.g2d.Lines;
import arc.math.geom.Vec2;
import mindustry.entities.units.AIController;
import mindustry.gen.Entityc;
import mindustry.gen.Unit;
import mindustry.gen.UnitBlockSpawnCallPacket;
import mindustry.gen.Unitc;
import mindustry.graphics.Pal;

import static mindustry.Vars.tilesize;

public interface Ownerc extends Unitc, Entityc {
    Unit owner() ;
    void owner(Unit u) ;
    int pucongwei() ;
    void pucongwei(int i) ;
    default void updateOwner() {
        if (owner() != null && owner().health <= 0) {
            kill();
        }
        if (controller() instanceof AIController a && owner() != null && owner() instanceof ZhuTic z) {
            a.moveTo(new Vec2(owner().x + z.pucongweis().get(pucongwei()).x, owner().y + z.pucongweis().get(pucongwei()).y), 0);
        }
//        if (owner() != null) {
//            health(owner().health);
//        }
    }
    default void drawOwner() {
        if (owner() != null) {
            Lines.stroke(4, Pal.place.cpy().mul(team().color));
            Lines.dashLine(x(), y(), owner().x, owner().y, (int) (dst(owner()) / tilesize));
        }
    }
}
