package shayebushi.entities.units;

import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.annotations.Annotations;
import mindustry.gen.Groups;
import mindustry.gen.MechUnit;
import mindustry.gen.Unit;

public class ZhuTiMechUnit extends MechUnit implements ZhuTic {
    public Seq<Vec2> pucongweis ;
    public int pucongweiAmount = 4 ;
    public Seq<Unit> pucongs ;
    public Seq<Integer> pucongIds ;
    public ZhuTiMechUnit() {
        pucongweis = new Seq<>(pucongweiAmount) ;
        pucongs = new Seq<>(pucongweiAmount) ;
        pucongIds = new Seq<>(pucongweiAmount) ;
    }
    @Override
    public int classId() {
        return 125 ;
    }

    @Override
    public Seq<Vec2> pucongweis() {
        return pucongweis;
    }

    @Override
    public void pucongweis(Seq<Vec2> s) {
        pucongweis = s ;
    }

    @Override
    public int pucongweiAmount() {
        return pucongweiAmount;
    }

    @Override
    public void pucongweiAmount(int i) {
        pucongweiAmount = i ;
    }

    @Override
    public Seq<Unit> pucongs() {
        if (pucongs.isEmpty() && !pucongIds.isEmpty()) {
            for (int i = 0 ; i < pucongweiAmount ; i ++) {
                pucongs.add(Groups.unit.getByID(pucongIds.get(i))) ;
            }
        }
        return pucongs;
    }

    @Override
    public void pucongs(Seq<Unit> s) {
        pucongs = s ;
        pucongIds.clear() ;
        for (Unit u : s) {
            pucongIds.add(u.id);
        }
    }

    @Override
    public Seq<Integer> pucongIds() {
        return pucongIds;
    }

    @Override
    public void pucongIds(Seq<Integer> s) {
        pucongIds = s ;
    }

    @Override
    @Annotations.CallSuper
    public void read(Reads read) {
        super.read(read);
        pucongweiAmount = read.i() ;
        for (int i = 0 ; i < pucongweiAmount ; i ++) {
            pucongweis.add(new Vec2(read.f(), read.f())) ;
            pucongIds.add(read.i()) ;
        }
    }

    @Override
    @Annotations.CallSuper
    public void write(Writes write) {
        super.write(write);
        write.i(pucongweiAmount);
        for (int i = 0 ; i < pucongweiAmount ; i ++) {
            write.f(pucongweis.get(i).x);
            write.f(pucongweis.get(i).y);
            write.i(pucongIds.get(i));
        }
    }

    @Override
    public void update() {
        super.update();
        updateZhuTi();
    }

    @Override
    public void rawDamage(float d) {
        super.rawDamage(d * damageMultiplier() * (1 - beilv() * pucongs().size));
    }

    @Override
    public void draw() {
        super.draw() ;
        drawZhuTi();
    }

    @Override
    public float beilv() {
        return 0 ;
    }

    @Override
    public void add() {
        super.add() ;
        damageMultiplier(damageMultiplier() * (1 - beilv() * pucongs().size)) ;
    }
}
