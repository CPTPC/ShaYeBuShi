package shayebushi.entities.abilities;

import arc.Core;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.gen.BuildHealthUpdateCallPacket;
import mindustry.gen.Entityc;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;

public class CeFanAbility extends Ability {
    public float range = 40 * Vars.tilesize ;
    public float limitHealth = 400 ;
    public float reload = 600 ;
    public float timer = 0 ;
    public float x, y ;
    public boolean buildings = true ;
    public boolean units = false ;
    public Effect effect = new Effect(80f, 100f, e -> {
        color(e.color);
        stroke(e.fout() * 2f);
        Lines.circle(e.x, e.y, 4f + e.fin() * 100f);

        Fill.circle(e.x, e.y, e.fout() * 20);

        randLenVectors(e.id, 20, 40f * e.fin(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 5f);
            Drawf.light(e.x + x, e.y + y, e.fout() * 15f, e.color, 0.7f);
        });

        color();

        Fill.circle(e.x, e.y, e.fout() * 10);
        Drawf.light(e.x, e.y, e.fout() * 20f, e.color, 0.7f);
    }).followParent(true).rotWithParent(true) ;
//    public Seq<Entityc> all = new Seq<>() ;
    public CeFanAbility(float range, float limitHealth, float reload){
        this.range = range ;
        this.limitHealth = limitHealth ;
        this.reload = reload ;
    }
    public CeFanAbility(){

    }
    @Override
    public void update(Unit unit){
        super.update(unit);
        Tmp.v1.trns(unit.rotation - 90, x, y).add(unit.x, unit.y);
        float rx = Tmp.v1.x, ry = Tmp.v1.y;
        timer += Time.delta ;
        if (timer + Time.delta >= reload) {
            if (units) {
                Units.nearby(null, rx, ry, range, other -> {
                    if (other.team != unit.team) {
                        other.team = unit.team;
                    }
                });
            }
            if (buildings) {
                Units.nearbyBuildings(rx, ry, range, other -> {
                    if (other.team != unit.team) {
                        other.changeTeam(unit.team);
                    }
                });
            }
            timer = 0 ;
        }
    }
    @Override
    public String localized(){
        return Core.bundle.format("ability.cefan", range / Vars.tilesize , reload / 60) ;
    }
    @Override
    public void draw(Unit unit){
        super.draw(unit) ;
        Drawf.dashCircle(unit.x, unit.y, timer / reload * range, unit.team.color);
        Tmp.v1.trns(unit.rotation - 90, x, y).add(unit.x, unit.y);
        float rx = Tmp.v1.x, ry = Tmp.v1.y;
        if (timer + Time.delta >= reload) {
            if (units) {
                Units.nearby(null, rx, ry, range, other -> {
                    if (other.team != unit.team) {
                        effect.at(other.x, other.y, unit.team.color);
                    }
                });
            }
            if (buildings) {
                Units.nearbyBuildings(rx, ry, range, other -> {
                    if (other.team != unit.team) {
                        effect.at(other.x, other.y, unit.team.color);
                    }
                });
            }
        }
    }
}
