package shayebushi.entities.fazhens;

import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.entities.Units;
import mindustry.entities.part.DrawPart;
import mindustry.game.Team;
import mindustry.type.StatusEffect;
import mindustry.world.meta.Stat;

public class FaZhenType {
    public float x ;
    public float y ;
    public float rotation ;
    public Team team ;
    public Seq<DrawPart> parts = new Seq<>(DrawPart.class);
    public float damage ;
    public float reload ;
    public float timer ;
    public float hitSize ;
    public boolean units ;
    public boolean buildings ;
    public StatusEffect[] effects ;
    public void update(){
        Tmp.v1.trns(rotation - 90, x, y).add(x, y);
        float rx = Tmp.v1.x, ry = Tmp.v1.y;
        timer += Time.delta ;
        if (timer >= reload) {
            if (units) {
                Units.nearby(null, rx, ry, hitSize, other -> {
                    if (other.team != team) {
                        other.damage(damage);
                        for (StatusEffect s : effects) {
                            other.apply(s);
                        }
                    }
                });
            }
            if (buildings){
                Units.nearbyBuildings(rx, ry, hitSize, other -> {
                    if (other.team != team) {
                        other.damage(damage);
                    }
                });
            }
        }
    }
    public void draw(){
        for (DrawPart part : parts){
            part.draw(DrawPart.params);
        }
    }
}
