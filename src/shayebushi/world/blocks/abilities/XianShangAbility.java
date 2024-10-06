package shayebushi.world.blocks.abilities;

import arc.util.Time;
import mindustry.entities.abilities.Ability;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.type.UnitType;
import mindustry.world.Block;
import shayebushi.ShaYeBuShi;

import static mindustry.Vars.state;

public class XianShangAbility extends BlockAbility {
    public float dancixianshang = 20000, miaoxianshang = 100000, fenxianshang = -1 ;

    public float shangzhen, dangqianshanghaimiao, dangqianshanghaifen ;
    public float timer = 0, timerS = 0, timerM = 0 ;
    public Block lastType ;
    public Team lastTeam ;
    @Override
    public void update(Building b) {
        super.update(b) ;
        shangzhen = Math.min(shangzhen, b.maxHealth) ;
        if (timer >= 2) {
            if (b.block != lastType) {
                b.block = lastType;
            }
            lastType = b.block;
            b.maxHealth = b.block.health;
            if (b.dead && (b.health > 0 || !damageable(b))) {
                b.dead = false;
                //drag = type.drag ;
            }
            ShaYeBuShi.setPrivateField(b, "added", b.health > 0 || !damageable(b));
            b.team = lastTeam ;
        }
        else {
            lastTeam = b.team ;
            lastType = b.block ;
        }
        if (shangzhen > b.health){
            float f = shangzhen - b.health ;
            if (f > dancixianshang && dancixianshang != -1) {
                b.health += f - dancixianshang ;
                dangqianshanghaimiao += dancixianshang ;
                dangqianshanghaifen += dancixianshang ;
            }
            else {
                dangqianshanghaimiao += f ;
                dangqianshanghaifen += f ;
            }
            if (dangqianshanghaimiao > miaoxianshang && miaoxianshang != -1) {
                b.health += f - (dangqianshanghaimiao == f ? miaoxianshang : 0) ;
            }
            if (dangqianshanghaimiao > fenxianshang && fenxianshang != -1) {
                b.health += f - (dangqianshanghaifen == f ? fenxianshang : 0) ;
            }
        }
        timerS += Time.delta ;
        timerM += Time.delta ;
        if (dangqianshanghaimiao >= miaoxianshang){
            dangqianshanghaimiao = miaoxianshang ;
        }
        if (dangqianshanghaifen >= fenxianshang){
            dangqianshanghaifen = fenxianshang ;
        }
        if (timerS >= Time.toSeconds) {
            timerS = 0 ;
            dangqianshanghaimiao = 0 ;
        }
        if (timerM >= Time.toMinutes){
            timerM = 0 ;
            dangqianshanghaifen = 0 ;
        }
        shangzhen = b.health ;
    }
    public boolean damageable(Building b) {
        float f = shangzhen - b.health ;
        return f < dancixianshang && dangqianshanghaimiao + f < miaoxianshang && dangqianshanghaifen + f < fenxianshang ;
    }
}
