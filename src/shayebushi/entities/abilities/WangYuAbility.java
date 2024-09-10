package shayebushi.entities.abilities;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.abilities.Ability;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ExplosionBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.effect.WrapEffect;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.*;
import shayebushi.SYBSUnitTypes;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;

/** Spawns a certain amount of units upon death. */
public class WangYuAbility extends Ability {
    public UnitType unit;
    public UnitType unitt ;
    public int amount = 1, randAmount = 0;
    /** Random spread of units away from the spawned. */
    public float spread = 8f;
    public float fanwei ;
    /** If true, units spawned face outwards from the middle. */
    public boolean faceOutwards = true;
    public WangYuAbility( int amount, float fanwei){
        //this.unit = unit;
        //this.unit = SYBSUnitTypes.shuangxingerjieduan ;
        this.amount = amount;
        this.fanwei = fanwei;
        //this.unitt = unitt;
        //this.unitt = SYBSUnitTypes.shuangxingerjieduan ;
    }

    public WangYuAbility(){
    }
    @Override
    public String localized(){
        return Core.bundle.format("ability.wangyu",amount,fanwei);
    }

    @Override
    public void death(Unit unit){
        super.death(unit);
        BulletType bt = new ExplosionBulletType(amount,fanwei){{
            collidesAir = true ;
            collidesGround = true ;
            collidesTeam = true ;
            healPercent = 100 ;
            //Fx.explosion
            shootEffect = hitEffect = despawnEffect = new MultiEffect(new Effect(90, e->{
                e.scaled(7, i -> {
                    stroke(3f * i.fout());
                    Lines.circle(e.x, e.y, 3f + i.fin() * 10f);
                });

                color(Pal.heal);

                randLenVectors(e.id, 6, 2f + 19f * e.finpow(), (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, e.fout() * 3f + 0.5f);
                    Fill.circle(e.x + x / 2f, e.y + y / 2f, e.fout());
                });

                color(Pal.heal, Color. valueOf("77ff77"), Color.white, e.fin());
                stroke(1.5f * e.fout());

                randLenVectors(e.id + 1, 8, 1f + 23f * e.finpow(), (x, y) -> {
                    lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 3f);
                });
            }), Fx.massiveExplosion, new WrapEffect(Fx.dynamicSpikes, Pal.heal, 24f), new WaveEffect(){{
                //colorFrom = colorTo = color;
                colorFrom = colorTo = Pal.heal;
                sizeTo = 200f;
                lifetime = 12f;
                strokeFrom = 4f;
            }});
        }};
        Bullet b = bt.create(unit,unit.x,unit.y,0) ;
        b.add();
        b.collision(unit,unit.x,unit.y);
        bt.hit(b);
    }
}
