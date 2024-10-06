package shayebushi.entities.units;

import arc.Events;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.annotations.Annotations;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.WeaponMount;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.gen.Hitboxc;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import shayebushi.SYBSUnitTypes;
import shayebushi.type.unit.JieTiUnitType;

import static mindustry.Vars.headless;
import static mindustry.Vars.state;

public class JieTiUnit extends QiangZhiXianShangUnitEntity implements JieTic {
    public Unit owner ;
    public int ownerId ;
    public Unit shangjie ;
    public int shangjieId ;
    private boolean si = false ;

    @Override
    public int classId() {
        return 119 ;
    }

    @Override
    @Annotations.CallSuper
    public void read(Reads read) {
        super.read(read);
        ownerId = read.i();
        shangjieId = read.i() ;
        //si = read.bool() ;
    }

    @Override
    @Annotations.CallSuper
    public void write(Writes write) {
        super.write(write);
        write.i(ownerId);
        write.i(shangjieId);
        //write.bool(si) ;
    }

    @Override
    public void owner(Unit u) {
        owner = u ;
        ownerId = u.id ;
    }

    @Override
    public int pucongwei() {
        return 0;
    }

    @Override
    public void pucongwei(int i) {

    }

    @Override
    public Unit owner() {
        if (owner == null) {
            owner = Groups.unit.getByID(ownerId) ;
        }
        return owner ;
    }

    @Override
    public void update() {
        super.update();
        updateOwner();
        updateJieti();
    }

    @Override
    public float beilv() {
        return 0.75f ;
    }
    @Override
    public Unit shangjie() {
        if (shangjie == null) {
            shangjie = Groups.unit.getByID(shangjieId) ;
        }
        return shangjie ;
    }
    @Override
    public void shangjie(Unit u) {
        shangjie = u ;
        shangjieId = u.id ;
    }

    @Override
    public void rawDamage(float d) {
        damageJieti(d);
    }

    @Override
    public boolean collides(Hitboxc other) {
        return !(other instanceof JieTic || other instanceof TouBuUnit) && super.collides(other);
    }
    @Override
    public TextureRegion icon() {
        return type instanceof JieTiUnitType j ? j.icon(this) : SYBSUnitTypes.getError() ;
    }
    @Override
    public float floorSpeedMultiplier() {
        return 1 ;
    }
    public void setHealth(float health) {
        shangzhen = this.health = health ;
        if (health == 0) {
            dancixianshang = miaoxianshang = fenxianshang = 1 ;
        }
    }
//    public boolean si() {
//        return si ;
//    }
    @Override
    public void heal(float amount) {
        owner.heal(amount) ;
    }
    @Override
    public void team(Team t) {
        super.team(t) ;
        lastTeam = t ;
    }
}
