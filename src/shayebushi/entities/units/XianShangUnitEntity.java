package shayebushi.entities.units;

import arc.struct.Seq;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.ai.types.CommandAI;
import mindustry.content.Fx;
import mindustry.entities.units.AIController;
import mindustry.entities.units.UnitController;
import mindustry.entities.units.WeaponMount;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Drawf;
import mindustry.world.draw.DrawDefault;

import static mindustry.Vars.*;

public class XianShangUnitEntity extends UnitEntity {
    public float miaoxianshang = 28000 ;
    public float dancixianshang = 3800 ;
    public float fenxianshang = 780000 ;
    public int i = 0 ;
    public int ii = 0 ;
    public float dangqianshanghaimiao = 0 ;
    public float dangqianshanghaifen = 0 ;
    public Team lastTeam = team ;
    public int timer = 0 ;
    public float lastRotation, lx, ly ;
    public Seq<Float> lastReloads = new Seq<>() ;
    public boolean wasPlayer ;
//    public float shangzhen = maxHealth;
    @Override
    public int classId() {
        return 113;
    }
    @Override
    public void update(){
        health = Math.min(health, maxHealth) ;
        if (timer >= 2) {
            if (lastTeam != team) {
                team = lastTeam;
            }
        }
        else {
            lastTeam = team ;
        }
        fix();
        int iz = 0 ;
        for (WeaponMount wm : mounts) {
            if (lastReloads.size < mounts.length) {
                lastReloads.add(wm.reload) ;
            }
            if (Float.isNaN(wm.reload)) {
                wm.reload = lastReloads.get(iz) ;
            }
            if (wm.reload == lastReloads.get(iz)) {
                wm.weapon.update(this, wm) ;
            }
            lastReloads.set(iz, wm.reload) ;
            iz ++ ;
        }
        timer ++ ;
//        if (shangzhen > health){
//            dangqianshanghaimiao += (shangzhen - health) ;
//            dangqianshanghaifen += (shangzhen - health) ;
//            if (dangqianshanghaimiao >= miaoxianshang || dangqianshanghaifen >= fenxianshang){
//                health += (shangzhen - health) ;
//            }
//        }
        i += Time.delta ;
        ii += Time.delta ;
        if (dangqianshanghaimiao >= miaoxianshang){
            dangqianshanghaimiao = miaoxianshang ;
        }
        if (dangqianshanghaifen >= fenxianshang){
            dangqianshanghaifen = fenxianshang ;
        }
        if (i >= Time.toSeconds) {
            i = 0 ;
            dangqianshanghaimiao = 0 ;
        }
        if (ii >= Time.toMinutes){
            ii = 0 ;
            dangqianshanghaifen = 0 ;
        }
        //if (!(this instanceof QiangZhiXianShangUnitEntity)) System.out.println(dangqianshanghaimiao + " " + dangqianshanghaifen + " " + miaoxianshang + " " + fenxianshang);
        //shangzhen = health ;
        lastTeam = team ;
        super.update();
    }
    @Override
    public void rawDamage(float a){
        if (dangqianshanghaimiao < miaoxianshang && dangqianshanghaifen < fenxianshang) {
            boolean hadShields = shield > 1.0E-4F;
            if (hadShields) {
                shieldAlpha = 1.0F;
            }
            float shieldDamage = Math.min(Math.max(shield, 0), a);
            shield -= shieldDamage;
            hitTime = 1.0F;
            a -= shieldDamage;
            //System.out.println("Before:" + a);
            a = Math.min(a, dancixianshang);
            //System.out.println("After:" + a);
            dangqianshanghaimiao += a;
            dangqianshanghaifen += a;
            if (a > 0 && type.killable) {
                health -= a;
                if (health <= 0 && !dead) {
                    kill();
                }
                if (hadShields && shield <= 1.0E-4F) {
                    Fx.unitShieldBreak.at(x, y, 0, team.color, this);
                }
            }
        }
        else {
            //this.heal(a) ;
        }
    }
    @Override
    public void draw(){
        fix();
        super.draw();
        //Drawf.circles(this.x,this.y,this.hitSize * 1.15f,this.team.color);
    }
    public void fix() {
        if (Float.isNaN(rotation)) {
            rotation = lastRotation ;
        }
        if (Float.isNaN(vel.x) || Float.isNaN(vel.y)) {
            vel.set(0, 0) ;
        }
        if (x < 0 || y < 0 || x > world.width() * tilesize || y > world.height() * tilesize || Float.isNaN(x) || Float.isNaN(y)) {
            set(lx, ly) ;
            if (controller instanceof AIController a) {
                if (a instanceof CommandAI) {
                    a.updateUnit() ;
                }
                else {
                    a.updateMovement();
                }
                move(vel.x, vel.y) ;
            }
//            if (this instanceof QiangZhiXianShangUnitEntity && !(this instanceof JieTiUnit)) {
//                System.out.println(vel.x + " " + vel.y);
//            }
        }
        lastRotation = rotation ;
        lx = x ;
        ly = y ;
//        if (this instanceof QiangZhiXianShangUnitEntity && !(this instanceof JieTiUnit)) {
//            System.out.println(x + " " + y);
//        }
        if (controller == null) {
            if (wasPlayer && false) {
                player.unit(this) ;
                controller = player ;
            }
            else {
                controller = type.controller.get(this);
            }
        }
        wasPlayer = isPlayer() ;
//        if (this instanceof QiangZhiXianShangUnitEntity && !(this instanceof JieTiUnit)) {
//            System.out.println(isCommandable() + " " + (controller == null));
//        }
    }
    @Override
    public void read(Reads read){
        super.read(read);
        dangqianshanghaifen = read.f() ;
        dangqianshanghaimiao = read.f() ;
        dancixianshang = read.f() ;
        miaoxianshang = read.f() ;
        fenxianshang = read.f() ;
    }
    @Override
    public void write(Writes writes){
        super.write(writes);
        writes.f(dangqianshanghaifen);
        writes.f(dangqianshanghaimiao);
        writes.f(dancixianshang);
        writes.f(miaoxianshang);
        writes.f(fenxianshang);
    }
}
