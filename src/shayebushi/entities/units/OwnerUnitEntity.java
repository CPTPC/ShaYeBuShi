package shayebushi.entities.units;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.annotations.Annotations;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;

public class OwnerUnitEntity extends UnitEntity implements Ownerc {
    public Unit owner ;
    public int ownerId = 1145141919 ;
    public int pucongwei ;

    @Override
    public int classId() {
        return 126 ;
    }

    @Override
    @Annotations.CallSuper
    public void read(Reads read) {
        super.read(read);
        ownerId = read.i();
        pucongwei = read.i() ;
    }

    @Override
    @Annotations.CallSuper
    public void write(Writes write) {
        super.write(write);
        write.i(ownerId);
        write.i(pucongwei) ;
    }

    @Override
    public void owner(Unit u) {
        owner = u ;
        ownerId = u.id ;
    }

    @Override
    public Unit owner() {
        if (owner == null && ownerId != 1145141919) {
            owner = Groups.unit.getByID(ownerId) ;
        }
        return owner ;
    }

    @Override
    public int pucongwei() {
        return pucongwei ;
    }

    @Override
    public void pucongwei(int i) {
        pucongwei = i ;
    }

    @Override
    public void update() {
        super.update();
        updateOwner();
    }

    @Override
    public boolean isCommandable() {
        return super.isCommandable() && owner == null ;
    }
    @Override
    public boolean isAI() {
        return super.isAI() && owner == null ;
    }
    @Override
    public void draw() {
        super.draw() ;
        drawOwner();
    }
}
