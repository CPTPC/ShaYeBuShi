package shayebushi.entities.abilities;

import arc.Core;
import arc.KeyBinds;
import arc.func.Cons;
import arc.func.Cons3;
import arc.func.Cons4;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.input.KeyCode;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Time;
import mindustry.Vars;
import mindustry.entities.abilities.Ability;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.entities.bullet.ShrapnelBulletType;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.input.Binding;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.environment.Cliff;
import shayebushi.SYBSBinding;
import shayebushi.SYBSBlocks;
import shayebushi.ShaYeBuShi;

import java.util.function.BooleanSupplier;

import static arc.Core.keybinds;
import static arc.graphics.g2d.Lines.useLegacyLine;
import static mindustry.Vars.*;

public class ShanXianAbility extends ShouDongAbilityBase {
    public static float stroke = 10 ;
    public int timer = 0 ;
    public float reload = 60 ;
    public float shanxianchangdu = 10 ;
    public float dam = 1000 ;
    public Color col = Pal.accent ;
    public boolean drawCircle = true ;
    public boolean drawLine = true ;
    public float angle(Unit u) {
        Boolean4 b = ai(u) ;
        //System.out.println(Angles.angle(u.x, u.y, Vars.player.mouseX, Vars.player.mouseY));
        return b.b1 ? (b.b2 && player.team().core() != null) ? Angles.angle(u.x, u.y, player.team().core().x, player.team().core().y) : u.rotation : Angles.angle(u.x, u.y, Vars.player.mouseX, Vars.player.mouseY) ;
    }
    public Boolean4 ai(Unit u) {
        boolean b = u.team == state.rules.waveTeam && u.isEnemy() ;
        boolean bo = false ;
        boolean boo = true ;
        boolean booo = true ;
        for (WeaponMount m : u.mounts){
            if (m.target != null){
                bo = true ;
                if (m.target == player.team().core() || m.target instanceof Unit){
                    boo = false ;
                }
            }
        }
        if (u.isCommandable() && (u.command().targetPos == null || (u.command().targetPos.x == 0 && u.command().targetPos.y == 0))){
            booo = false ;
        }
        Boolean4 out = new Boolean4() ;
        out.b1 = b ;
        out.b2 = bo ;
        out.b3 = boo ;
        out.b4 = booo ;
        return out ;
    }
    public void to_do(Unit u, Cons4<Float, Float, Float, Boolean> c) {
        //System.out.println(unit.isPlayer() + " " + (Math.abs(Core.input.axis(Binding.zhujineng)) > 0));
        float angle = angle(u) ;
        if ((u.isPlayer() && u.getPlayer().team() == u.team && Core.input.keyDown(key)) || ai(u).b1) {
            Vec2 v = ShaYeBuShi.circle(angle, shanxianchangdu * tilesize, u.x, u.y) ;
            float x = v.x, y = v.y ;
                /*
                double x = 0, y = 0;
                if (angle >= 0 && angle < 90) {
                    if (angle <= 45) {
                        x = u.x + shanxianchangdu * Vars.tilesize * Math.cos(angle);
                        y = u.y + shanxianchangdu * Vars.tilesize * Math.sin(angle);
                    } else {
                        y = u.y + shanxianchangdu * Vars.tilesize * Math.cos(angle);
                        x = u.x + shanxianchangdu * Vars.tilesize * Math.sin(angle);
                    }
                } else if (angle >= 90 && angle < 180) {
                    if (angle <= 135) {
                        x = u.x - shanxianchangdu * Vars.tilesize * Math.sin(angle - 90);
                        y = u.y + shanxianchangdu * Vars.tilesize * Math.cos(angle - 90);
                    } else {
                        y = u.y + shanxianchangdu * Vars.tilesize * Math.sin(angle - 90);
                        x = u.x - shanxianchangdu * Vars.tilesize * Math.cos(angle - 90);
                    }
                } else if (angle >= 180 && angle < 270) {
                    if (angle <= 225) {
                        y = u.y - shanxianchangdu * Vars.tilesize * Math.sin(angle - 180);
                        x = u.x - shanxianchangdu * Vars.tilesize * Math.cos(angle - 180);
                    } else {
                        x = u.x - shanxianchangdu * Vars.tilesize * Math.sin(angle - 180);
                        y = u.y - shanxianchangdu * Vars.tilesize * Math.cos(angle - 180);
                    }
                } else if (angle >= 270 && angle < 360) {
                    if (angle <= 315) {
                        y = u.y - shanxianchangdu * Vars.tilesize * Math.cos(angle - 270);
                        x = u.x + shanxianchangdu * Vars.tilesize * Math.sin(angle - 270);
                    } else {
                        x = u.x + shanxianchangdu * Vars.tilesize * Math.sin(angle - 270);
                        y = u.y - shanxianchangdu * Vars.tilesize * Math.cos(angle - 270);
                    }
                }
                */
            Boolean4 b4 = ai(u) ;
            boolean b = (b4.b1 && !(Vars.world.tile((int)x / tilesize, (int)y / tilesize).block() instanceof Cliff)) && b4.b3 && b4.b4 ;
            c.get(x, y, angle, b);
        }
    }
    @Override
    public void draw(Unit u){
        super.draw(u) ;
        if (drawCircle) {
            Draw.color(u.team.color);
            Lines.stroke(10);
            Lines.arc(u.x, u.y, u.hitSize * 0.5f, timer / reload, u.rotation);
        }
        /*
        boolean b = u.team == state.rules.waveTeam && u.isEnemy() ;
        boolean bo = false ;
        boolean boo = true ;
        boolean booo = true ;
        for (WeaponMount m : u.mounts){
            if (m.target != null){
                bo = true ;
                if (m.target == player.team().core() || m.target instanceof Unit){
                    boo = false ;
                }
            }
        }
        if (u.isCommandable() && (u.command().targetPos == null || (u.command().targetPos.x == 0 && u.command().targetPos.y == 0))){
            booo = false ;
        }
        double angle = b ? bo ? Angles.angle(u.x, u.y, player.team().core().x, player.team().core().y) : u.rotation : Angles.angle(u.x, u.y, Vars.player.mouseX, Vars.player.mouseY) ;
        angle = Math.toRadians(angle) ;
        if ((u.isPlayer() && u.getPlayer().team() == u.team && Core.input.keyDown(key)) || (b && boo && booo)) {
            double x = 0, y = 0;
            if (angle >= 0 && angle < 90) {
                if (angle <= 45) {
                    x = u.x + shanxianchangdu * Vars.tilesize * Math.cos(angle);
                    y = u.y + shanxianchangdu * Vars.tilesize * Math.sin(angle);
                } else {
                    y = u.y + shanxianchangdu * Vars.tilesize * Math.cos(angle);
                    x = u.x + shanxianchangdu * Vars.tilesize * Math.sin(angle);
                }
            } else if (angle >= 90 && angle < 180) {
                if (angle <= 135) {
                    x = u.x - shanxianchangdu * Vars.tilesize * Math.sin(angle - 90);
                    y = u.y + shanxianchangdu * Vars.tilesize * Math.cos(angle - 90);
                } else {
                    y = u.y + shanxianchangdu * Vars.tilesize * Math.sin(angle - 90);
                    x = u.x - shanxianchangdu * Vars.tilesize * Math.cos(angle - 90);
                }
            } else if (angle >= 180 && angle < 270) {
                if (angle <= 225) {
                    y = u.y - shanxianchangdu * Vars.tilesize * Math.sin(angle - 180);
                    x = u.x - shanxianchangdu * Vars.tilesize * Math.cos(angle - 180);
                } else {
                    x = u.x - shanxianchangdu * Vars.tilesize * Math.sin(angle - 180);
                    y = u.y - shanxianchangdu * Vars.tilesize * Math.cos(angle - 180);
                }
            } else if (angle >= 270 && angle < 360) {
                if (angle <= 315) {
                    y = u.y - shanxianchangdu * Vars.tilesize * Math.cos(angle - 270);
                    x = u.x + shanxianchangdu * Vars.tilesize * Math.sin(angle - 270);
                } else {
                    x = u.x + shanxianchangdu * Vars.tilesize * Math.sin(angle - 270);
                    y = u.y - shanxianchangdu * Vars.tilesize * Math.cos(angle - 270);
                }
            }
            draww(u, (float) x, (float) y, (float) Math.toDegrees(angle));
        }
        */
        to_do(u, (x, y, angle, b) -> {
            Boolean4 b4 = ai(u) ;
            if ((u.isPlayer() && u.getPlayer().team() == u.team && Core.input.keyDown(key)) || (b && b4.b3 && b4.b4)) {
                draww(u, x, y, angle);
            }
        }) ;
        Draw.reset();
    }
    public void draww(Unit unit,float x,float y,float rotate){
        if (drawLine) {
            stroke = 10;
            Draw.color(unit.team.color);
            Lines.stroke(5);
            Lines.line(unit.x, unit.y, x, y);
            Drawf.tri(x, y, 9, 15, rotate);
            Draw.reset();
        }
    }
    @Override
    public void update(Unit u){
        super.update(u);
        if (timer + Time.delta >= reload) {
            to_do(u, (x, y, angle, b) -> {
                if ((Core.input.keyDown(key2) || b) && u.canPass((int)(x.floatValue() / tilesize), (int)(y.floatValue() / tilesize))) {
                    u.x = (float) x;
                    u.y = (float) y;
                    BulletType bbb = new ShrapnelBulletType() {{
                        damage = dam;
                        length = shanxianchangdu * Vars.tilesize;
                        toColor = u.team.color;
                    }};
                    Bullet bb = bbb.create(u, u.x, u.y, (float) Math.toDegrees(angle));
                    bb.rotation((float) Math.toDegrees(angle));
                    bb.add();
                    timer = 0;
                }
            });
        }
        timer += Time.delta ;
    }
    //public static final transient String TAG = "ShanXianAbility";
    public ShanXianAbility(){
        key = SYBSBinding.zhujineng ;
        key2 = Binding.select ;
    }
    public class Boolean4 {
        public boolean b1, b2, b3, b4 ;
    }
}
