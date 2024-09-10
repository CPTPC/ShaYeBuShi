package shayebushi.world.blocks;

import mindustry.game.Team;
import mindustry.world.Tile;

public class MultiBlockXianZhi extends MultiBlock implements XianZhic {

    public int limitPlaceOnCount, countt ;

    public MultiBlockXianZhi(String name) {
        super(name);
    }

    public MultiBlockXianZhi(String name, Object... turret) {
        super(name, turret);
    }

    @Override
    public int limitPlaceOnCount() {
        return limitPlaceOnCount;
    }

    @Override
    public int countt() {
        return countt;
    }

    @Override
    public void countt(int c) {
        countt = c ;
    }

    @Override
    public boolean canPlaceOn(Tile t, Team te, int r) {
        return canPlaceOnXianZhi(t, te, r) ;
    }

    @Override
    public void setStats() {
        super.setStats() ;
        setStatsXianZhi();
    }

    @Override
    public void setBars(){
        super.setBars();
        setBarsXianZhi();
    }
}
