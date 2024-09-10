package shayebushi.entities.abilities;

import arc.Core;
import arc.func.Cons2;
import arc.func.Cons3;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.geom.Vec2;
import mindustry.gen.Unit;
import mindustry.input.Binding;
import shayebushi.SYBSBinding;
import shayebushi.ShaYeBuShi;

import static arc.util.Time.delta;
import static arc.util.Time.toSeconds;
import static mindustry.Vars.player;
import static mindustry.Vars.tilesize;

public class YueQianAbility extends ShouDongAbilityBase {
    public float reload = 5 * toSeconds ;
    public float timer = 0 ;
    public float range = 4000 * tilesize ;
    @Override
    public void update(Unit u) {
        super.update(u);
        timer += delta ;
        if (timer >= reload) {
            to_do(u, (x, y) -> {
                if (Core.input.keyDown(key2)) {
                    u.x = x;
                    u.y = y;
                    timer = 0 ;
                }
            }) ;
        }
    }
    @Override
    public void draw(Unit u) {
        super.draw(u);
        to_do(u, (x, y) -> {
            u.type.applyColor(u);
            Draw.alpha(0.25f);
            Draw.rect(u.type.region, u.x, u.y, u.rotation - 90) ;
            Draw.reset();
        }) ;
    }
    public void to_do(Unit u, Cons2<Float, Float> cons) {
        if (!Core.input.keyDown(key)) return ;
        float x = player.mouseX, y = player.mouseY ;
        if (u.dst(x, y) > range) {
            Vec2 v = ShaYeBuShi.circle(Angles.angle(u.x, u.y, x, y), range, u.x, u.y) ;
            x = v.x ;
            y = v.y ;
        }
        cons.get(x, y);
    }
    public YueQianAbility() {
        super() ;
        key = SYBSBinding.zhujineng ;
        key2 = Binding.select ;
    }
}
