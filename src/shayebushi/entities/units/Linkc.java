package shayebushi.entities.units;

import arc.graphics.g2d.Lines;
import arc.math.geom.Vec2;
import mindustry.Vars;
import mindustry.entities.Damage;
import mindustry.entities.units.WeaponMount;
import mindustry.game.Team;
import mindustry.gen.Entityc;
import mindustry.gen.Unit;
import mindustry.gen.Unitc;
import mindustry.graphics.Pal;

import static mindustry.Vars.tilesize;

public interface Linkc extends Unitc, Entityc {
    Unit link() ;
    void link(Unit u) ;
    default void updateLink() {
        if (mounts().length > 0) {
            WeaponMount wm = mounts()[0];
            if (link() != null && isShooting()) {
                for (WeaponMount m : link().mounts) {
                    m.shoot = true;
                    m.aimX = aimX();
                    m.aimY = aimY();
                    if (wm.target != null) {
                        m.target = wm.target;
                    }
                    link().lookAt(aimX(), aimY());
                    m.weapon.update(link(), m);
                }
            }
        }
        if (type().canBoost && link() != null && link().type.canBoost && isPlayer()) {
            link().updateBoosting(isFlying());
        }
        if (link() != null && link().health < 0) {
            kill();
        }
        if (link() != null) {
            //health(Math.max(health(), link().health)) ;
            if (link().isCommandable() && !Vars.control.input.selectedUnits.contains(link())) {
                link().command().targetPos = isPlayer() && dst(link()) >= hitSize() * 2 ? new Vec2(x(), y()) : isCommandable() ? command().targetPos : null ;
            }
            if (isBuilding()) {
                link().plans().add(buildPlan());
            }
        }
    }
    default void drawLink() {
        if (link() != null) {
            Lines.stroke(4, Pal.place.cpy().mul(team().color));
            Lines.dashLine(x(), y(), link().x, link().y, (int) (dst(link()) / tilesize));
            if (isPlayer()) {
                Lines.poly(link().x, link().y, 4, link().hitSize);
            }
        }
    }
    default void damageLink(float d) {
        link().rawDamage(Damage.applyArmor(d, armor()) / healthMultiplier() / Vars.state.rules.unitHealth(team()));
    }
}
