package shayebushi.entities.abilities;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.scene.ui.layout.Table;
import arc.struct.FloatSeq;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.entities.abilities.ForceFieldAbility;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.ui.Bar;
import shayebushi.SYBSPal;
import shayebushi.SYBSStatusEffects;
import shayebushi.ShaYeBuShi;

import static arc.util.Time.delta;
import static mindustry.Vars.tilesize;

public class LiFangHuDunAbility extends Ability {
    public float width = 30 * tilesize, height = 22.5f * tilesize, rotateSpeed = 3 ;
    public float xOffset = 0, yOffset = -10 * tilesize ;
    public float rotation = 0 ;
    public Color color = SYBSPal.chaokongjian1, color2 = SYBSPal.chaokongjian3 ;
    public float multiplier = 0.2f ;
    public float timer = 0 ;
    public float lastHealth ;
    public float shieldHealth = 250000, currentHealth = shieldHealth;
    public Ability other ;
    @Override
    public void update(Unit u) {
        super.update(u) ;
        rotation += rotateSpeed ;
        timer += delta ;
        if (timer <= 10) {
            lastHealth = u.health ;
        }
        if (lastHealth < u.health) {
            u.health += (lastHealth - u.health) * (1 - multiplier) ;
            u.heal((lastHealth - u.health) * (1 - multiplier)) ;
        }
        Vec2 v1 = new Vec2(u.x + width * Mathf.cosDeg(rotation + 90) + xOffset, u.y + height * Mathf.sinDeg(rotation + 90) + yOffset) ;
        Vec2 v2 = new Vec2(u.x + width * Mathf.cosDeg(rotation + 90) + xOffset, u.y + height * Mathf.sinDeg(rotation + 90) + width + yOffset) ;
        Vec2 v7 = new Vec2(u.x + width * Mathf.cosDeg(rotation + 180) + xOffset, u.y + height * Mathf.sinDeg(rotation + 180) + yOffset) ;
        Vec2 v8 = new Vec2(u.x + width * Mathf.cosDeg(rotation + 180) + xOffset, u.y + height * Mathf.sinDeg(rotation + 180) + width + yOffset) ;
        Units.nearby(null, u.x, u.y, v2.dst(v8), v1.dst(v7), u2 -> {
            if (u2.team != u.team) {
                int i = ShaYeBuShi.random(1, 3) ;
                if (i == 1) {
                    u2.apply(SYBSStatusEffects.kuiluan, 120);
                }
                if (i == 2) {
                    currentHealth -= 5 ;
                    u2.damage(10);
                }
                if (i == 3) {
                    currentHealth -= 6 ;
                    u2.health -= 10 ;
                }
            }
        });
        Groups.bullet.intersect(u.x, u.y, v2.dst(v8), v1.dst(v7), b -> {
            if (b.team != u.team) {
                int i = ShaYeBuShi.random(1, 3) ;
                if (i == 1) {
                    currentHealth -= b.damage * 2 ;
                    b.remove();
                }
                if (i == 2) {
                    currentHealth -= b.damage ;
                }
                if (i == 3) {
                    currentHealth -= b.damage * 3 ;
                    b.trns(-b.vel.x, -b.vel.y);

                    float penX = Math.abs(u.x - b.x), penY = Math.abs(u.y - b.y);

                    if(penX > penY){
                        b.vel.x *= -1;
                    }else{
                        b.vel.y *= -1;
                    }

                    b.owner = u;
                    b.team = u.team;
                    b.time += 1f;
                }
            }
        });
        lastHealth = u.health ;
        if (currentHealth <= 0) {
            u.abilities = ShaYeBuShi.remove(u.abilities, this, Ability.class) ;
            if (other != null) {
                u.abilities = ShaYeBuShi.add(u.abilities, other, Ability.class);
            }
        }
    }
    @Override
    public void draw(Unit u) {
        super.draw(u) ;
        Vec2 v1 = new Vec2(u.x + width * Mathf.cosDeg(rotation + 90) + xOffset, u.y + height * Mathf.sinDeg(rotation + 90) + yOffset) ;
        Vec2 v2 = new Vec2(u.x + width * Mathf.cosDeg(rotation + 90) + xOffset, u.y + height * Mathf.sinDeg(rotation + 90) + width + yOffset) ;
        Vec2 v3 = new Vec2(u.x + width * Mathf.cosDeg(rotation) + xOffset, u.y + height * Mathf.sinDeg(rotation) + yOffset) ;
        Vec2 v4 = new Vec2(u.x + width * Mathf.cosDeg(rotation) + xOffset, u.y + height * Mathf.sinDeg(rotation) + width + yOffset) ;
        Vec2 v5 = new Vec2(u.x + width * Mathf.cosDeg(rotation - 90) + xOffset, u.y + height * Mathf.sinDeg(rotation - 90) + yOffset) ;
        Vec2 v6 = new Vec2(u.x + width * Mathf.cosDeg(rotation - 90) + xOffset, u.y + height * Mathf.sinDeg(rotation - 90) + width + yOffset) ;
        Vec2 v7 = new Vec2(u.x + width * Mathf.cosDeg(rotation + 180) + xOffset, u.y + height * Mathf.sinDeg(rotation + 180) + yOffset) ;
        Vec2 v8 = new Vec2(u.x + width * Mathf.cosDeg(rotation + 180) + xOffset, u.y + height * Mathf.sinDeg(rotation + 180) + width + yOffset) ;

        FloatSeq f1 = new FloatSeq() ;
        FloatSeq f2 = new FloatSeq() ;
        FloatSeq f3 = new FloatSeq() ;
        FloatSeq f4 = new FloatSeq() ;
        FloatSeq f5 = new FloatSeq() ;
        FloatSeq f6 = new FloatSeq() ;

        f1.addAll(v1.x, v1.y, v3.x, v3.y, v5.x, v5.y, v7.x, v7.y) ;
        f2.addAll(v2.x, v2.y, v4.x, v4.y, v6.x, v6.y, v8.x, v8.y) ;
        f3.addAll(v1.x, v1.y, v2.x, v2.y, v4.x, v4.y, v3.x, v3.y) ;
        f4.addAll(v3.x, v3.y, v4.x, v4.y, v6.x, v6.y, v5.x, v5.y) ;
        f5.addAll(v5.x, v5.y, v6.x, v6.y, v8.x, v8.y, v7.x, v7.y) ;
        f6.addAll(v7.x, v7.y, v8.x, v8.y, v2.x, v2.y, v1.x, v1.y) ;

        Draw.z(Layer.shields) ;
        Lines.stroke(5);
        Draw.color(color2 == null ? u.team.color.cpy().mul(0.9f, 0.9f, 0.9f, 0.9f) : color2);

        Fill.poly(f1);
        Fill.poly(f2);
        Fill.poly(f3);
        Fill.poly(f4);
        Fill.poly(f5);
        Fill.poly(f6);

        Draw.color(color == null ? u.team.color : color) ;

        Lines.line(v1.x, v1.y, v2.x, v2.y) ;
        Lines.line(v3.x, v3.y, v4.x, v4.y) ;
        Lines.line(v5.x, v5.y, v6.x, v6.y) ;
        Lines.line(v7.x, v7.y, v8.x, v8.y) ;

        Lines.line(v1.x, v1.y, v3.x, v3.y) ;
        Lines.line(v3.x, v3.y, v5.x, v5.y) ;
        Lines.line(v5.x, v5.y, v7.x, v7.y) ;
        Lines.line(v7.x, v7.y, v1.x, v1.y) ;

        Lines.line(v2.x, v2.y, v4.x, v4.y) ;
        Lines.line(v4.x, v4.y, v6.x, v6.y) ;
        Lines.line(v6.x, v6.y, v8.x, v8.y) ;
        Lines.line(v8.x, v8.y, v2.x, v2.y) ;

        Draw.reset();
    }
    @Override
    public void init(UnitType u) {
        super.init(u) ;
        other = new ForceFieldAbility(width + height, shieldHealth * (width + height) / Math.min(width, height) / 1200f, shieldHealth * (width + height) / Math.min(width, height), 1200, 4, 0) ;
    }
    @Override
    public void displayBars(Unit unit, Table bars){
        bars.add(new Bar("stat.shieldhealth", color == null ? Pal.accent : color, () -> currentHealth / shieldHealth).blink(color2 == null ? unit.team.color : color2)).row();
    }
}
