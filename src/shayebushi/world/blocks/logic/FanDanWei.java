package shayebushi.world.blocks.logic;

import arc.Core;
import arc.Events;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.scene.ui.Dialog;
import arc.scene.ui.layout.Table;
import arc.util.Strings;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Fx;
import mindustry.core.Version;
import mindustry.entities.Units;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.game.EventType;
import mindustry.game.Gamemode;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.input.Binding;
import mindustry.type.unit.MissileUnitType;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;
import mindustry.world.blocks.defense.BaseShield;
import shayebushi.SYBSStatValues;
import shayebushi.SYBSStatusEffects;
import shayebushi.ShaYeBuShi;
import shayebushi.type.unit.YuLeiUnitType;

import static mindustry.Vars.*;
import static shayebushi.ShaYeBuShi.clickTime;
import static shayebushi.ShaYeBuShi.clickTimer;

public class FanDanWei extends Block {
    //TODO game rule? or field? should vary by base.
    public float radius = 330f * tilesize;
    public int sides = 4;
    public boolean ground = false, fly = true, naval = false ;

    protected static FanDanWeiBuild paramBuild;
    //protected static Effect paramEffect;
    public Cons<Bullet> bulletConsumer = bullet -> {
        if(bullet.team != paramBuild.team && bullet.type.absorbable && bullet.within(paramBuild, radius)){
            bullet.absorb();
            //paramEffect.at(bullet);

            //TODO effect, shield health go down?
            //paramBuild.hit = 1f;
            //paramBuild.buildup += bullet.damage;
        }
    };

    public Cons<Unit> unitConsumer = unit -> {
        //if this is positive, repel the unit; if it exceeds the unit radius * 2, it's inside the forcefield and must be killed
        float overlapDst = (unit.hitSize/2f + radius) - unit.dst(paramBuild);
        if ((((fly && unit.isFlying()) || (ground && unit.isGrounded() && !ShaYeBuShi.isNaval(unit)) || (naval && ShaYeBuShi.isNaval(unit)))) && !(unit.type instanceof MissileUnitType) && !(unit.type instanceof YuLeiUnitType)) {
            if (overlapDst > 0) {
                if (overlapDst > unit.hitSize * 1.5f) {
                    //instakill units that are stuck inside the shield (TODO or maybe damage them instead?)
                    unit.kill();
                } else {
                    //stop
                    unit.vel.setZero();
                    //get out
                    unit.move(Tmp.v1.set(unit).sub(paramBuild).setLength(overlapDst + 0.01f));

                    if (Mathf.chanceDelta(0.12f * Time.delta)) {
                        Fx.circleColorSpark.at(unit.x, unit.y, paramBuild.team.color);
                    }
                }
            }
        }
    };
    public final static Data current = new Data() ;

    public FanDanWei(String name){
        super(name);

        //hasPower = true;
        update = solid = true;
        rebuildable = false;
        targetable = false ;
        configurable = true ;
    }

    @Override
    public void init(){
        super.init();

        updateClipRadius(radius);
    }


    public class FanDanWeiBuild extends Building {
        public boolean broken = false; //TODO
        public float hit = 0f;
        public FanDanWeiBuild link ;
        public int lx = -4000, ly = -4000 ;
        public BaseDialog dialog = new BaseDialog(Core.bundle.get("link.title")) ;
        //public float smoothRadius;

        @Override
        public void damage(float d) {

        }
        @Override
        public void updateTile(){
            if (/*link == null && */lx != -4000 && ly != -4000) {
                link = (FanDanWeiBuild) world.build(lx, ly) ;
            }
            paramBuild = this;
            if (!(state.isEditor() || state.rules.mode() == Gamemode.sandbox || ShaYeBuShi.tiaoshi || Version.build == -1)) return ;
            //to_do();
            if (link != null) {
                float angle = Angles.angle(x, y, link.x, link.y) ;
                Vec2 v = ShaYeBuShi.circle(angle + 180, tilesize, x, y) ;
                new LaserBulletType() {
                    @Override
                    public void draw(Bullet b) {
                        //super.draw(b) ;
                    }
                    @Override
                    public void hitEntity(Bullet b, Hitboxc entity, float health){
                        boolean wasDead = entity instanceof Unit u && u.dead;

                        if(entity instanceof Healthc h){
                            if(pierceArmor){
                                h.damagePierce(b.damage);
                            }else{
                                h.damage(b.damage);
                            }
                        }

                        if(entity instanceof Unit unit){
                            Tmp.v3.set(unit).sub(b).nor().scl(knockback * 80f);
                            if(impact) Tmp.v3.setAngle(b.rotation() + (knockback < 0 ? 180f : 0f));
                            unit.impulse(Tmp.v3);
                            if ((ShaYeBuShi.isNaval(unit) && naval) || (unit.isFlying() && fly) || (unit.isGrounded() && !ShaYeBuShi.isNaval(unit) && ground)) {
                                unit.apply(status, statusDuration);
                            }
                            if (unit.type instanceof MissileUnitType && fly) {
                                unit.kill() ;
                            }
                            if (unit.type instanceof YuLeiUnitType && naval) {
                                unit.kill() ;
                            }

                            Events.fire(new EventType.UnitDamageEvent().set(unit, b));
                        }

                        if(!wasDead && entity instanceof Unit unit && unit.dead){
                            Events.fire(new EventType.UnitBulletDestroyEvent(unit, b));
                        }

                        handlePierce(b, health, entity.x(), entity.y());
                    }
                    {
                    length = dst(link) + 2 * tilesize ;
                    damage = 0.000001f ;
                    width = tilesize ;
                    hitEffect = Fx.hitBeam ;
                    lifetime = 2 ;
                    status = SYBSStatusEffects.kuiluan ;
                    statusDuration = Version.build == -1 || ShaYeBuShi.tiaoshi ? 30 * tilesize : 10 ;
                }}.create(this, team, v.x, v.y, angle) ;
            }
        }/*
        @Override
        public void update() {
            super.update();
            //System.out.println(6);
            if (state.isEditor()) {
                to_do();
            }
        }*/
        public void to_do() {
            if (Core.input.keyDown(Binding.select) && clickTimer >= clickTime) {
                var ref = new Object() {
                    public boolean found = false ;
                } ;
                Units.nearbyBuildings(player.mouseX, player.mouseY, tilesize, b -> {
                    if (current.status == 0 && b == this) {
                        current.build = this ;
                        current.status = 1 ;
                        clickTime = 0 ;
                    }
                    else if (current.status == 1 && b instanceof FanDanWeiBuild f && b.block == current.build.block && current.build != f) {
                        if (current.build.link != null) {
                            current.build.link.link = null;
                        }
                        current.build.link = f ;
                        if (f.link != null) {
                            f.link.link = null;
                        }
                        f.link = current.build ;
                        current.status = 0 ;
                        clickTime = 0 ;
                    }
                    ref.found = true ;
                });
                if (!ref.found) {
                    current.status = 0 ;
                    current.build = null ;
                }
            }
        }
        @Override
        public Building create(Block b, Team t) {
            dialog.cont.add("x") ;
            dialog.cont.field(lx + "", s -> {
                lx = Integer.parseInt(s) ;
            }).width(200f).valid(Strings::canParseInt) ;
            dialog.cont.add("y") ;
            dialog.cont.field(ly + "", s -> {
                ly = Integer.parseInt(s) ;
            }).width(200f).valid(Strings::canParseInt) ;
            dialog.addCloseButton();
            return super.create(b, t) ;
        }
        @Override
        public void buildConfiguration(Table t) {
            super.buildConfiguration(t);
            dialog.show() ;
        }

        @Override
        public boolean inFogTo(Team viewer){
            return false;
        }
        @Override
        public void draw() {
            super.draw();
            if (current.status == 1) {
                Draw.color(Pal.accent);
                Lines.stroke(2);
                Lines.poly(current.build.x, current.build.y, 4, current.build.block.size * tilesize, 0);
            }
            if (current.status == 2) {
                Draw.color(Pal.place);
                Lines.stroke(2);
                Lines.poly(current.build.link.x, current.build.link.y, 4, current.build.link.block.size * tilesize, 0);
            }
        }
        @Override
        public void write(Writes write){
            super.write(write);
            write.i(lx) ;
            write.i(ly) ;
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            lx = read.i() ;
            ly = read.i() ;
        }
    }
    public static class Data {
        public FanDanWeiBuild build ;
        public int status = 0 ;
    }
}
