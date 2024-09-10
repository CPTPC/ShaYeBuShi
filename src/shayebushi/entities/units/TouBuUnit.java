package shayebushi.entities.units;

import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.annotations.Annotations;
import mindustry.entities.Units;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Groups;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

public class TouBuUnit extends XianShangUnitEntity {
    public Seq<Unit> jietis = new Seq<>() ;
    public Seq<Integer> jietiIds = new Seq<>() ;
    public UnitType jietiType ;
    public int jietiTypeId ;
    public int jietiAmount = 50 ;
    @Override
    public int classId() {
        return 121 ;
    }
    public void setup(UnitType jietiType) {
        this.jietiType = jietiType ;
        Unit shangjie = this ;
        for (int i = 0 ; i < jietiAmount ; i ++) {
            int z = i ;
            Unit u = jietiType.create(team) ;
            if (u instanceof JieTic j) {
                j.shangjie(shangjie);
                j.owner(this) ;
            }
            shangjie = u ;
            jietis.add(u) ;
            jietiIds.add(u.id) ;
        }
        jietiIds.setSize(jietiAmount) ;
        jietis.setSize(jietiAmount) ;
    }

    @Override
    @Annotations.CallSuper
    public void read(Reads read) {
        super.read(read);
        jietiTypeId = read.i();
        jietiAmount = read.i() ;
        for (int i = 0 ; i < jietiAmount ; i ++) {
            jietiIds.add(read.i());
        }
    }

    @Override
    @Annotations.CallSuper
    public void write(Writes write) {
        super.write(write);
        write.i(jietiTypeId) ;
        write.i(jietiAmount) ;
        for (int i = 0 ; i < jietiAmount ; i ++) {
            write.i(jietiIds.get(i));
        }
    }

    @Override
    public void update() {
        super.update();
        if (jietis.isEmpty()) {
            for (int i = 0 ; i < jietiAmount ; i ++) {
                jietis.add(Groups.unit.getByID(jietiIds.get(i))) ;
            }
        }
        if (jietiType == null) {
            jietiType = Vars.content.unit(jietiTypeId) ;
        }
        if (!jietis.isEmpty()) {
            Teamc target = null;
            if (mounts.length > 0) {
                target = mounts[0].target ;
            }
            for (Unit u : jietis) {
                if (u != null) {
                    if (u.team != team) {
                        u.team(team) ;
                    }
                    for (WeaponMount w : u.mounts) {
                        if (mounts().length > 0 && mounts[0].shoot) {
                            if (target != null) {
                                w.target = target;
                            }
                            w.aimX = aimX;
                            w.aimY = aimY;
                            w.shoot = true;
                            w.weapon.update(u, w);
                        }
                    }
//                    u.aimX = aimX ;
//                    u.aimY = aimY ;
                    if (isBuilding()) {
                        u.plans().add(buildPlan());
                    }
                }
            }
        }
        Units.nearby(null, x(), y(), hitSize(), u -> {
            if (u.team != team() && u.hittable() && !u.isFlying()) {
                u.damage(200) ;
            }
        });
    }

    @Override
    public void add() {
        super.add() ;
        for (Unit u : jietis) {
            u.x = x ;
            u.y = y ;
            u.add();
        }
    }

    @Override
    public float floorSpeedMultiplier() {
        return 1 ;
    }
}
