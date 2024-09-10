package shayebushi.entities.units;

import arc.Core;
import arc.Events;
import arc.func.Boolf;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.*;
import arc.scene.ui.layout.Table;
import arc.struct.Bits;
import arc.struct.Queue;
import arc.struct.Seq;
import arc.util.Structs;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.ai.Pathfinder;
import mindustry.ai.types.CommandAI;
import mindustry.ai.types.LogicAI;
import mindustry.annotations.Annotations;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.core.World;
import mindustry.ctype.Content;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.EntityCollisions;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.*;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.graphics.Trail;
import mindustry.input.InputHandler;
import mindustry.logic.LAccess;
import mindustry.type.Item;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.Build;
import mindustry.world.Tile;
import mindustry.world.blocks.ConstructBlock;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.payloads.BuildPayload;
import mindustry.world.blocks.payloads.UnitPayload;
import mindustry.world.blocks.storage.CoreBlock;

import java.nio.FloatBuffer;
import java.util.Iterator;

import static mindustry.Vars.*;
import static mindustry.Vars.world;
import static mindustry.logic.GlobalVars.*;

public class WaterMoveXianShangUnit extends UnitWaterMove{
    public float miaoxianshang = 28000 ;
    public float dancixianshang = 3800 ;
    public float fenxianshang = 780000 ;
    public int i = 0 ;
    public int ii = 0 ;
    public float dangqianshanghaimiao = 0 ;
    public float dangqianshanghaifen = 0 ;
    //    public float shangzhen = maxHealth;
    @Override
    public int classId() {
        return 124;
    }
    @Override
    public void update(){
        super.update();
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
        super.draw();
        //Drawf.circles(this.x,this.y,this.hitSize * 1.15f,this.team.color);
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
