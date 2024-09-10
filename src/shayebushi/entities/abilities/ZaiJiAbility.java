package shayebushi.entities.abilities;

import arc.Core;
import arc.KeyBinds;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.geom.Intersector;
import arc.math.geom.Position;
import arc.math.geom.Rect;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.ai.types.MissileAI;
import mindustry.content.Fx;
import mindustry.entities.EntityGroup;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.unit.MissileUnitType;
import mindustry.world.blocks.payloads.Payload;
import mindustry.world.blocks.payloads.UnitPayload;
import mindustry.world.blocks.units.UnitAssembler;
import shayebushi.SYBSBinding;
import shayebushi.entities.units.ZhanJiUnitEntity;
import shayebushi.type.unit.ZhanJiUnitType;

import java.util.concurrent.atomic.AtomicBoolean;

import static mindustry.Vars.tilesize;

public class ZaiJiAbility extends Ability {
    public boolean zao = false ;
    public Vec2 be = new Vec2(-10, -40) ;
    public Vec2 en = new Vec2(10, 40) ;
    public int amount = 70;
    public float size = 4 * Vars.tilesize;
    public float heal = 0.05f ;
    public float fly = 0.9f ;
    public float arrows = 20 ;
    public float width = 15 ;
    public float flare = 45 ;
    public float timer = 0 ;
    public Interp interp = Interp.pow5;
    public float daji = 250 * Vars.tilesize ;
    public KeyBinds.KeyBind key = SYBSBinding.fujineng ;
    public static TextureRegion arrow = Core.atlas.find("reinforced-payload-conveyor-top");
    @Override
    public void update(Unit u){
        super.update(u);
        //System.out.println(u.getClass().getName());
        if (!(u instanceof PayloadUnit)) return ;
        Units.nearby(u.team, u.x, u.y, u.hitSize / 2, o ->{
//            if (o.type.localizedName.equals("\"鸾鸟\"")) System.out.println(o instanceof ZhanJiUnitEntity);
            if (o.hitSize < size && ((PayloadUnit) u).payloads.size < amount
            //        && o.type instanceof ZhanJiUnitType
                    && !(o.type instanceof MissileUnitType)
                    && o.type.flying
            ){
                if (!o.isPlayer() || (o.isPlayer() && Core.input.keyDown(key))){
                    ((PayloadUnit)u).pickup(o) ;
                    float rot = u.rotation;
                    Fx.shootPayloadDriver.at(o.x(), o.y(), rot);
                    Fx.payloadDeposit.at(false ? u.x + Angles.trnsx(u.rotation - 90, be.x, be.y) : u.x, false ? u.y + Angles.trnsy(u.rotation - 90, be.x, be.y) : u.y, rot, new UnitAssembler.YeetData(new Vec2(o.x, o.y), o.type));
//                    u.team.data().unitCount -= 1 ;
//                    u.team.data().unitCount = Math.max(u.team.data().unitCount, 0) ;
//                    if (u.team.data().typeCounts == null || u.team.data().typeCounts.length <= o.type.id){
//                        u.team.data().typeCounts = new int[Vars.content.units().size];
//                    }
//                    u.team.data().typeCounts[o.type.id] -= 1 ;
//                    u.team.data().typeCounts[o.type.id] = Math.max(u.team.data().typeCounts[o.type.id], 0) ;
                }
                //System.out.println(u.team.data().countType(o.type) + " " + u.team.data().unitCount);
            }
        });
        for (Payload p : ((PayloadUnit)u).payloads){
            if (p instanceof UnitPayload uu){
                uu.unit.heal(uu.unit.maxHealth * heal / 60);
            }
        }
        Seq<Entityc> s = new Seq<>() ;
        Units.nearby(null, u.x, u.y, daji, o -> {
            if (o != null && o.team != u.team && o.team != Team.derelict){
                s.add(o) ;
            }
        });
        Units.nearbyBuildings(u.x, u.y, daji, o -> {
            if (o != null && o.team != u.team && o.team != Team.derelict){
                s.add(o) ;
            }
        });
        if (!s.isEmpty() && !(((PayloadUnit) u).payloads.isEmpty())) {
            for (Payload p : ((PayloadUnit) u).payloads) {
                if (p instanceof UnitPayload uu && uu.unit.healthf() >= fly) {
                    Entityc uuu = s.random() ;
                    if (!uu.unit.isCommandable()) continue;
                    if (uu.unit.command().targetPos == null){
                        uu.unit.command().targetPos = new Vec2(uuu instanceof Position pos ? new Vec2(pos.getX(), pos.getY()) : new Vec2(0, 0)) ;
                        uu.unit.command().attackTarget = uuu instanceof Teamc t ? t : null ;
                    }
                    else {
                        uu.unit.command().targetPos.set(uuu instanceof Position pos ? pos : new Vec2(0, 0));
                        uu.unit.command().attackTarget = uuu instanceof Teamc t ? t : null ;
                    }
                    uu.unit.x = u.x + Angles.trnsx(u.rotation - 90, en.x, en.y) ;
                    uu.unit.y = u.y + Angles.trnsy(u.rotation - 90, en.x, en.y) ;
                    uu.dump() ;
                    ((PayloadUnit) u).payloads.remove(p) ;
                    float rot = false ? p.angleTo(en.cpy().add(u)) : u.rotation;
                    Fx.shootPayloadDriver.at(p.x(), p.y(), rot);
                    Fx.payloadDeposit.at(p.x(), p.y(), rot, new UnitAssembler.YeetData(new Vec2(u.x + Angles.trnsx(u.rotation - 90, en.x, en.y), u.y + Angles.trnsy(u.rotation - 90, en.x, en.y)), p.content()));
                }
            }
        }
        timer ++ ;
    }
    @Override
    public void draw(Unit unit){
        super.draw(unit);
        if (! (unit instanceof PayloadUnit)) return ;
        float r = unit.rotation ;
        float len = 89 / 8f * 1.8f, w = unit.hitSize / 16f + 7f ;
        width = w ;
//        int ww = arrow.width ;
//        arrow.width = (int)w * 32;
//        arrow.height = arrow.height / ww * (int)w ;
        Vec2 right = Tmp.v1.trns(r, len, w);
        Vec2 left = Tmp.v2.trns(r, len, -w);
        Lines.stroke(1.5f, Pal.accent);
        Lines.line(unit.x + Angles.trnsx(unit.rotation - 90, be.x, be.y) + left.x, unit.y + Angles.trnsy(unit.rotation - 90, be.x, be.y) + left.y, unit.x + Angles.trnsx(unit.rotation - 90, en.x, en.y) - right.x, unit.y + Angles.trnsy(unit.rotation - 90, en.x, en.y) - right.y);
        Lines.line(unit.x + Angles.trnsx(unit.rotation - 90, be.x, be.y) + right.x, unit.y + Angles.trnsy(unit.rotation - 90, be.x, be.y) + right.y, unit.x + Angles.trnsx(unit.rotation - 90, en.x, en.y) - left.x, unit.y + Angles.trnsy(unit.rotation - 90, en.x, en.y) - left.y);
        float dst = 0.8f;

        float glow = Math.max((dst - (Math.abs(fract() - 0.5f) * 2)) / dst, 0);
        Draw.mixcol(unit.team.color, glow);
        float s = tilesize * size;
        float trnext = s * fract(), trprev = s * (fract() - 1), rot = unit.rotation;

        for (int i = 0 ; i < unit.hitSize; i += width) {
            Tmp.v3.set(unit.x + Angles.trnsx(unit.rotation - 90, be.x, be.y), unit.y + Angles.trnsy(unit.rotation - 90, be.x, be.y)).lerp(unit.x + Angles.trnsx(unit.rotation - 90, en.x, en.y), unit.y + Angles.trnsy(unit.rotation - 90, en.x, en.y), 0.5f + (i - 2) * 0.1f);
            //next
            TextureRegion clipped = clipRegion(unit.tileOn().getHitbox(Tmp.r1), unit.tileOn().getHitbox(Tmp.r2).move(trnext, 0), arrow);
            float widthNext = (s - clipped.width * clipped.scl()) * 0.5f;
            float heightNext = (s - clipped.height * clipped.scl()) * 0.5f;
            Tmp.v1.set(widthNext, heightNext).rotate(rot);
            Draw.rect(clipped, unit.x + Tmp.v1.x, unit.y + Tmp.v1.y, rot);

            //prev
            clipped = clipRegion(unit.tileOn().getHitbox(Tmp.r1), unit.tileOn().getHitbox(Tmp.r2).move(trprev, 0), arrow);
            float widthPrev = (clipped.width * clipped.scl() - s) * 0.5f;
            float heightPrev = (clipped.height * clipped.scl() - s) * 0.5f;
            Tmp.v1.set(widthPrev, heightPrev).rotate(rot);
            Draw.rect(clipped, unit.x + Tmp.v1.x, unit.y + Tmp.v1.y, rot);
        }
    }
    protected TextureRegion clipRegion(Rect bounds, Rect sprite, TextureRegion region){
        Rect over = Tmp.r3;

        boolean overlaps = Intersector.intersectRectangles(bounds, sprite, over);

        TextureRegion out = Tmp.tr1;
        out.set(region.texture);
        out.scale = region.scale;

        if(overlaps){
            float w = region.u2 - region.u;
            float h = region.v2 - region.v;
            float x = region.u, y = region.v;
            float newX = (over.x - sprite.x) / sprite.width * w + x;
            float newY = (over.y - sprite.y) / sprite.height * h + y;
            float newW = (over.width / sprite.width) * w, newH = (over.height / sprite.height) * h;

            out.set(newX, newY, newX + newW, newY + newH);
        }else{
            out.set(0f, 0f, 0f, 0f);
        }

        return out;
    }
    public float fract(){
        return interp.apply(timer / flare);
    }
}
