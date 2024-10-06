package shayebushi.ai.types;

import arc.math.Angles;
import arc.math.geom.Vec2;
import mindustry.entities.units.AIController;
import mindustry.gen.Unit;
import shayebushi.SYBSShenShengUnitTypes;
import shayebushi.SYBSStatusEffects;
import shayebushi.ShaYeBuShi;
import shayebushi.entities.units.JieTic;

import static mindustry.Vars.state;
import static mindustry.Vars.tilesize;
//import static mindustry.content.StatusEffects.fast;
import static shayebushi.SYBSStatusEffects.fastttt;

public class JieTiAI extends AIController {
    @Override
    public void updateMovement(){
        if (!(unit instanceof JieTic j)) return ;
        Unit u = ((JieTic)unit).shangjie() ;
        if (u == null) return ;
        unloadPayloads();
        //System.out.println(((JieTic)unit).shangjie() != null);
        unit.lookAt(u);
        /*
        float f = ((JieTic)unit).shangjie().type == SYBSShenShengUnitTypes.shouge ? 2.6f : 1.3f ;
        f = 0.65f ;
        if (j.owner().moving() && unit.dst(((JieTic) unit).shangjie()) >= unit.hitSize * f) {
            //unit.apply(fastttt, 3);
            moveTo(((JieTic) unit).shangjie(), 0);
        }
        if (!j.owner().moving() && unit.dst(((JieTic) unit).shangjie()) >= unit.hitSize * 0.85f) {
            //unit.apply(fastttt, 3);
            moveTo(((JieTic) unit).shangjie(), 0);
        }
        */
        if (j.owner().moving() || true) {
            Vec2 v = ShaYeBuShi.circle(Angles.angle(unit.x, unit.y, u.x, u.y) + 180, u.hitSize * 0.65f, u.x, u.y);
            unit.set(v);
        }
    }
}
