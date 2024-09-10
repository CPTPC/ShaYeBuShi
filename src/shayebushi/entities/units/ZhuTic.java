package shayebushi.entities.units;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Entityc;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.gen.Unitc;

import static mindustry.Vars.player;

public interface ZhuTic extends Unitc, Entityc {
    Seq<Vec2> pucongweis() ;
    void pucongweis(Seq<Vec2> s) ;
    int pucongweiAmount() ;
    void pucongweiAmount(int i) ;
    Seq<Unit> pucongs() ;
    void pucongs(Seq<Unit> s) ;
    Seq<Integer> pucongIds() ;
    void pucongIds(Seq<Integer> s) ;
    default float beilv() {
        return 0.95f / 4 ;
    }
    default void updateZhuTi() {
        if (!pucongs().isEmpty()) {
            Teamc target = null ;
            if (mounts().length > 0) {
                target = mounts()[0].target ;
            }
            for (Unit u : pucongs()) {
                if (u != null) {
                    if (u.team != team()) {
                        u.team(team()) ;
                    }
                    if (u.health < 0) {
                        pucongs().remove(u) ;
                    }
                    for (WeaponMount w : u.mounts) {
                        if (mounts().length > 0 && mounts()[0].shoot) {
                            if (target != null) {
                                w.target = target;
                            }
                            w.aimX = aimX();
                            w.aimY = aimY();
                            w.shoot = true;
                            u.lookAt(aimX(), aimY());
                            w.weapon.update(u, w);
                        }
                    }
                    if (u.type.canBoost && type().canBoost && isFlying()) {
                        u.updateBoosting(true);
                    }
                    if (isBuilding()) {
                        u.plans().add(buildPlan());
                    }
                }
            }
        }
    }
    default void drawZhuTi() {
        if (!pucongs().isEmpty()) {
            Draw.color(team().color);
            for (Unit u : pucongs()) {
                if (u != null) {
                    if (isPlayer()) {
                        Lines.poly(u.x, u.y, 4, u.hitSize);
                    }
                }
            }
        }
    }
}
