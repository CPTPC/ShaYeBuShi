package shayebushi.entities.units;

import arc.Events;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.core.World;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.StatusEntry;
import mindustry.entities.units.WeaponMount;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.input.InputHandler;
import mindustry.logic.LAccess;
import mindustry.type.Item;
import mindustry.type.UnitType;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;

import java.util.ArrayList;
import java.util.Arrays;

import static mindustry.Vars.*;

public class QiangZhiXianShangUnitEntity extends XianShangUnitEntity {
    public float shangzhen = maxHealth;
    public UnitType lastType = type ;
    //public Team lastTeam = team ;
    @Override
    public int classId() {
        return 120;
    }
    @Override
    public void update(){
        shangzhen = Math.min(shangzhen, maxHealth) ;
        if (timer >= 2) {
            if (type != lastType) {
                type = lastType;
            }
            lastType = type;
            maxHealth = type.health;
            armor = type.armor;
            abilities = type.abilities.toArray(Ability.class);
            if (dead && (health > 0 || !damageable())) {
                dead = false;
                //drag = type.drag ;
            }
            added = (health > 0 || !damageable()) ;
            //team = lastTeam ;
        }
        else {
            //lastTeam = team ;
            lastType = type ;
        }
        if (shangzhen > health){
            float f = shangzhen - health ;
            //System.out.println(f);
            /*
            if (dangqianshanghaifen > fenxianshang) {
                health = shangzhen + dangqianshanghaifen - fenxianshang ;
            }
            if (dangqianshanghaimiao > miaoxianshang) {
                health = shangzhen + dangqianshanghaimiao - miaoxianshang ;
            }
            if (f > dancixianshang) {
                health = shangzhen +  f - dancixianshang ;
            }
            */
            if (f > dancixianshang) {
                health += f - dancixianshang ;
                dangqianshanghaimiao += dancixianshang ;
                dangqianshanghaifen += dancixianshang ;
            }
            else {
                dangqianshanghaimiao += f ;
                dangqianshanghaifen += f ;
            }
            if (dangqianshanghaimiao > miaoxianshang) {
                //System.out.println(dangqianshanghaimiao + "d");
                //System.out.println(health + "b");
                health += f - (dangqianshanghaimiao == f ? miaoxianshang : 0) ;
                //System.out.println(health + "a");
            }
            if (dangqianshanghaimiao > fenxianshang) {
                health += f - (dangqianshanghaifen == f ? fenxianshang : 0) ;
            }
        }
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
//        if (this.healthf() <= 0.2f){
//            System.out.println(this.health);
//        }
//        if (i % 30 == 0) {
//            System.out.println(dangqianshanghaimiao);
//            System.out.println("------");
//            System.out.println(dangqianshanghaifen);
//        }
        //System.out.println(health + " " + shangzhen);
        //System.out.println(lastTeam.name + " " + team.name);
//        if (!(this instanceof JieTiUnit)) {
//            System.out.println(shangzhen + " " + health + " 2");
//        }
        shangzhen = health ;
        super.update();
        if (dead && (health > 0 || !damageable())) {
            drag = type.drag * dragMultiplier * state.rules.dragMultiplier ;
        }
    }
    public boolean damageable() {
        float f = shangzhen - health ;
        return f < dancixianshang && dangqianshanghaimiao + f < miaoxianshang && dangqianshanghaifen + f < fenxianshang ;
    }
    @Override
    public void rawDamage(float a){
//        System.out.println(dangqianshanghaimiao + " " + dangqianshanghaifen);
//        System.out.println(dangqianshanghaimiao < miaoxianshang && dangqianshanghaifen < fenxianshang);
        if (dangqianshanghaimiao < miaoxianshang && dangqianshanghaifen < fenxianshang) {
            boolean hadShields = shield > 1.0E-4F;
            if (hadShields) {
                shieldAlpha = 1.0F;
            }
            float shieldDamage = Math.min(Math.max(shield, 0), a);
            shield -= shieldDamage;
            hitTime = 1.0F;
            a -= shieldDamage;
            a = Math.min(a, dancixianshang);
            //dangqianshanghaimiao += a;
            //dangqianshanghaifen += a;
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
        super.draw();
//        Draw.color(this.team.color);
//        Lines.stroke(10);
//        Lines.arc(this.x, this.y, this.hitSize * 1.15f,1);
    }
    @Override
    public void kill(){
        if (damageable() && health <= 0) {
            super.kill();
        }
    }
    @Override
    public void killed(){
        if (damageable() && health <= 0) {
            super.killed();
        }
    }
    @Override
    public void destroy(){
//        if (this instanceof JieTiUnit) {
//            System.out.println(health + " " + shangzhen + " " + dangqianshanghaimiao) ;
//            System.out.println(miaoxianshang + " " + dancixianshang) ;
//        }
        if (damageable() && health <= 0) {
            super.destroy();
            //System.out.println(6) ;
        }
    }
    @Override
    public void remove() {
        //System.out.println(damageable() && health <= 0) ;
        if ((damageable() && health <= 0)) {
            super.remove();
            //System.out.println(7) ;
        }
    }
    @Override
    public void read(Reads read){
        super.read(read);
        dangqianshanghaifen = read.f() ;
        dangqianshanghaimiao = read.f() ;
        dancixianshang = read.f() ;
        miaoxianshang = read.f() ;
        fenxianshang = read.f() ;
        shangzhen = read.f();
    }
    @Override
    public void write(Writes writes){
        super.write(writes);
        writes.f(dangqianshanghaifen);
        writes.f(dangqianshanghaimiao);
        writes.f(dancixianshang);
        writes.f(miaoxianshang);
        writes.f(fenxianshang);
        writes.f(shangzhen);
    }
}
