package shayebushi.world.blocks.abilities;

import arc.Core;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Intersector;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;
import mindustry.ui.Bar;
import mindustry.world.Block;
import shayebushi.SYBSStatusEffects;

public class ZhenDangHuDunAbility extends BlockAbility{
    /** Shield radius. */
    public float radius = 60f;
    /** Shield regen speed in damage/tick. */
    public float regen = 0.1f;
    /** Maximum shield. */
    public float max = 200f;
    /** Cooldown after the shield is broken, in ticks. */
    public float cooldown = 60f * 5;
    /** Sides of shield polygon. */
    public int sides = 6;
    /** Rotation of shield. */
    public float rotation = 0f;

    /** State. */
    protected float radiusScale, alpha;

    private static float realRad;
    private static Building paramUnit;
    private static ZhenDangHuDunAbility paramField;
    public float damage ;
    public Seq<StatusEffect> statuss = Seq.with(SYBSStatusEffects.dangji) ;
    public float stime = 600 ;
    public float heal = 0 ;

    private static final Cons<Bullet> shieldConsumer = trait -> {
        if(trait.team != paramUnit.team && trait.type.absorbable && Intersector.isInRegularPolygon(paramField.sides, paramUnit.x, paramUnit.y, realRad, paramField.rotation, trait.x(), trait.y()) && paramField.data > 0){
            trait.absorb();
            Fx.absorb.at(trait);

            //break shield
            if(paramField.data <= trait.damage()){
                paramField.data -= paramField.cooldown * paramField.regen;

                Fx.shieldBreak.at(paramUnit.x, paramUnit.y, paramField.radius, paramUnit.team.color, paramUnit);
                Units.nearby(null, paramUnit.x, paramUnit.y, paramField.radius * 4 , other -> {
                    if(other.targetable(paramUnit.team) && (other.team != paramUnit.team)){
                        other.health -= paramField.damage ;
                        for (StatusEffect status : paramField.statuss) {
                            other.apply(status, paramField.stime);
                        }
                    }
                });

            }

            paramField.data -= trait.damage();
            paramField.alpha = 1f;
        }
    };

    public ZhenDangHuDunAbility(float radius, float regen, float max, float cooldown,float damage){
        this.radius = radius;
        this.regen = regen;
        this.max = max;
        this.cooldown = cooldown;
        this.damage = damage ;
    }
    @Override
    public String localized(){
        return Core.bundle.format("ability.zhendanghudun", damage);
    }

    public ZhenDangHuDunAbility(float radius, float regen, float max, float cooldown, int sides, float rotation, float damage){
        this.radius = radius;
        this.regen = regen;
        this.max = max;
        this.cooldown = cooldown;
        this.sides = sides;
        this.rotation = rotation;
        this.damage = damage ;
    }

    ZhenDangHuDunAbility(){}

    @Override
    public void update(Building unit){
        if(data < max){
            data += Time.delta * regen;
        }

        alpha = Math.max(alpha - Time.delta/10f, 0f);

        if(data > 0){
            radiusScale = Mathf.lerpDelta(radiusScale, 1f, 0.06f);
            paramUnit = unit;
            paramField = this;
            checkRadius(unit);
            unit.heal(heal / 60f);
            Groups.bullet.intersect(unit.x - realRad, unit.y - realRad, realRad * 2f, realRad * 2f, shieldConsumer);
        }else{
            radiusScale = 0f;
            float rx = Tmp.v1.x, ry = Tmp.v1.y;
        }
    }

    @Override
    public void draw(Building unit){
        checkRadius(unit);

        if(data > 0){
            Draw.color(unit.team.color, Color.white, Mathf.clamp(alpha));
            if(Vars.renderer.animateShields){
                Draw.z(Layer.shields + 0.001f * alpha);
                Fill.poly(unit.x, unit.y, sides, realRad, rotation);
            }else{
                Draw.z(Layer.shields);
                Lines.stroke(1.5f);
                Draw.alpha(0.09f);
                Fill.poly(unit.x, unit.y, sides, radius, rotation);
                Draw.alpha(1f);
                Lines.poly(unit.x, unit.y, sides, radius, rotation);
            }
        }
    }

    @Override
    public void displayBars(Building unit, Table bars){
        bars.add(new Bar("stat.shieldhealth", Pal.accent, () -> data / max)).row();
    }


    public void checkRadius(Building unit){
        //timer2 is used to store radius scale as an effect
        realRad = radiusScale * radius;
    }
}
