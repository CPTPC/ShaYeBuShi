package shayebushi.world.blocks.abilities;

import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.scene.ui.layout.Table;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.meta.BlockStatus;

import static mindustry.Vars.player;

public class ShieldArcAbility extends BlockAbility {
    private static Building paramUnit;
    private static ShieldArcAbility paramField;
    private static Vec2 paramPos = new Vec2();
    private static final Cons<Bullet> shieldConsumer = b -> {
        if(b.team != paramUnit.team && b.type.absorbable && paramField.data > 0 &&
                !paramPos.within(b, paramField.radius + paramField.width/2f) &&
                Tmp.v1.set(b).add(b.vel).within(paramPos, paramField.radius + paramField.width/2f) &&
                Angles.within(paramPos.angleTo(b), paramUnit.rotation + paramField.angleOffset, paramField.angle / 2f)){

            b.absorb();
            Fx.absorb.at(b);

            //break shield
            if(paramField.data <= b.damage()){
                paramField.data -= paramField.cooldown * paramField.regen;

                //TODO fx
            }

            paramField.data -= b.damage();
            paramField.alpha = 1f;
        }
    };

    /** Shield radius. */
    public float radius = 60f;
    /** Shield regen speed in damage/tick. */
    public float regen = 0.1f;
    /** Maximum shield. */
    public float max = 200f;
    /** Cooldown after the shield is broken, in ticks. */
    public float cooldown = 60f * 5;
    /** Angle of shield arc. */
    public float angle = 80f;
    /** Offset parameters for shield. */
    public float angleOffset = 0f, x = 0f, y = 0f;
    /** If true, only activates when shooting. */
    public boolean whenShooting = true;
    /** Width of shield line. */
    public float width = 6f;

    /** Whether to draw the arc line. */
    public boolean drawArc = true;
    /** If not null, will be drawn on top. */
    public @Nullable
    String region;
    /** If true, sprite position will be influenced by x/y. */
    public boolean offsetRegion = false;

    /** State. */
    protected float widthScale, alpha;

    @Override
    public void update(Building unit){
        if(data < max){
            data += Time.delta * regen;
        }

        boolean active = data > 0 && (unit.status() == BlockStatus.active || !whenShooting);
        alpha = Math.max(alpha - Time.delta/10f, 0f);

        if(active){
            widthScale = Mathf.lerpDelta(widthScale, 1f, 0.06f);
            paramUnit = unit;
            paramField = this;
            paramPos.set(x, y).rotate(unit.rotation - 90f).add(unit);

            Groups.bullet.intersect(unit.x - radius, unit.y - radius, radius * 2f, radius * 2f, shieldConsumer);
        }else{
            widthScale = Mathf.lerpDelta(widthScale, 0f, 0.11f);
        }
    }

    @Override
    public void init(Block type){
        data = max;
    }

    @Override
    public void draw(Building unit) {
        applyDraw(unit.team, unit.x, unit.y, unit.rotation, Layer.shields, false) ;
    }

    public void applyDraw(Team team, float x, float y, float rotation, float layer, boolean always) {

        if(widthScale > 0.001f || always){
            Draw.z(layer);

            Draw.color(team.color, Color.white, Mathf.clamp(alpha));
            var pos = paramPos.set(this.x, this.y).rotate(rotation - 90f).add(x, y);

            if(!Vars.renderer.animateShields){
                Draw.alpha(0.4f);
            }

            if(region != null){
                Vec2 rp = offsetRegion ? pos : Tmp.v1.set(x, y);
                Draw.yscl = widthScale;
                Draw.rect(region, rp.x, rp.y, rotation - 90);
                Draw.yscl = 1f;
            }

            if(drawArc){
                Lines.stroke(width * widthScale);
                Lines.arc(pos.x, pos.y, radius, angle / 360f, rotation + angleOffset - angle / 2f);
            }
            Draw.reset();
        }
    }

    @Override
    public void drawSelect(Building b) {
        applyDraw(b.team, b.x, b.y, b.rotation, Layer.block, true) ;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        applyDraw(player.team(), x, y, rotation, Layer.block, true) ;
    }

    @Override
    public void displayBars(Building unit, Table bars){
        bars.add(new Bar("stat.shieldhealth", Pal.accent, () -> data / max)).row();
    }
}
