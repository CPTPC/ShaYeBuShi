package shayebushi.type.unit;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.struct.Seq;
import mindustry.gen.Unit;
import shayebushi.SYBSFx;
import shayebushi.SYBSUnitTypes;
import shayebushi.entities.units.JieTic;
import shayebushi.entities.units.TouBuUnit;

public class JieTiUnitType extends XianShangUnitType {
    public TextureRegion r1, r2, rw ;
    public Seq<TextureRegion> pieces = new Seq<>() ;

    public JieTiUnitType(String name) {
        super(name);
        //deathExplosionEffect = SYBSFx.xingliubaozha ;
    }

    public void drawBody(Unit unit){
        if (!(unit instanceof JieTic j && j.owner() instanceof TouBuUnit t)) {
            super.drawBody(unit) ;
            return ;
        }
        applyColor(unit);

        Draw.rect(icon(unit), unit.x, unit.y, unit.rotation - 90);

        Draw.reset();
    }
    public TextureRegion icon(Unit unit) {
        if (!(unit instanceof JieTic j && j.owner() instanceof TouBuUnit t)) {
            return SYBSUnitTypes.getError() ;
        }
        int idx = t.jietis.indexOf(unit) ;
        int len = t.jietis.size ;
        return idx == len - 1 ? rw : idx % 2 == 0 ? r2 : r1 ;
    }
    @Override
    public void load() {
        super.load();
        r1 = Core.atlas.find(name + "-r1", region) ;
        r2 = Core.atlas.find(name + "-r2", region) ;
        rw = Core.atlas.find(name + "-rw", region) ;
        for (int i = 0 ; true ; i ++) {
            TextureRegion t = Core.atlas.find(name + "p" + i) ;
            if (!t.found() && i >= 5) {
                break ;
            }
            pieces.add(t) ;
        }
    }
    @Override
    public void killed(Unit u) {
        super.killed(u);
        SYBSFx.pieces.at(u.x, u.y, u.rotation, u);
    }
}
