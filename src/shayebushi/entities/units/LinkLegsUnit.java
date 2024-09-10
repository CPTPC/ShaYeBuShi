package shayebushi.entities.units;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.annotations.Annotations;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.gen.LegsUnit;
import mindustry.gen.Unit;

public class LinkLegsUnit extends LegsUnit implements Linkc {
    public Unit link ;
    public int linkId ;
    @Override
    public int classId() {
        return 122 ;
    }
    @Override
    public Unit link() {
        if (link == null) {
            link = Groups.unit.getByID(linkId) ;
        }
        return link ;
    }
    @Override
    public void link(Unit u) {
        link = u ;
        linkId = u.id ;
    }
    @Override
    @Annotations.CallSuper
    public void read(Reads read) {
        super.read(read);
        linkId = read.i();
    }
    @Override
    @Annotations.CallSuper
    public void write(Writes write) {
        super.write(write);
        write.i(linkId);
    }
    @Override
    public void update() {
        super.update() ;
        updateLink();
    }
    @Override
    public void draw() {
        super.draw();
        drawLink();
    }
    @Override
    public void damage(float d) {
        super.damage(d);
        damageLink(d);
    }
    @Override
    public void team(Team team) {
        super.team(team) ;
        if (link() != null) {
            link().team = team ;
        }
    }
}
