package shayebushi.entities.bullet;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import shayebushi.ShaYeBuShi;

public class ShuiLeiBulletType extends BasicBulletType {
    public Seq<Unit> all = new Seq<>() ;
    public float x, y;
    public boolean targetGround = true, targetAir = true, hitBuildings = true, hitUnits = true;
    public ShuiLeiBulletType(float s , float d){
        super(s,d) ;
        sprite = "mine-bullet";
        speed = 0 ;
        width = 30 ;
        height = 30 ;
        lifetime = Float.POSITIVE_INFINITY ;
        backColor = Pal.lightishGray ;
    }
    @Override
    public void update(Bullet b){
        super.update(b);
        Tmp.v1.trns(b.rotation() - 90, x, y).add(b.x, b.y);
        float rx = Tmp.v1.x, ry = Tmp.v1.y;
        Units.nearby(null, rx, ry, splashDamageRadius, other -> {
            if(other.checkTarget(targetAir, targetGround) && ShaYeBuShi.isNaval(other) && (other.team != b.team)){
                all.add(other);
            }
        });
        if (!all.isEmpty() || !b.floorOn().isLiquid){
            hit(b) ;
            b.remove();
        }
    }
    @Override
    public void draw(Bullet b){
        super.draw(b);
        float shrink = shrinkInterp.apply(b.fout());
        float height = this.height * ((1f - shrinkY) + shrinkY * shrink);
        float width = this.width * ((1f - shrinkX) + shrinkX * shrink);
        float offset = -90 + (spin != 0 ? Mathf.randomSeed(b.id, 360f) + b.time * spin : 0f) + rotationOffset;

        Color mix = Tmp.c1.set(mixColorFrom).lerp(mixColorTo, b.fin());

        Draw.mixcol(mix, mix.a);

        if(backRegion.found()){
            Draw.color(backColor);
            Draw.rect(backRegion, b.x, b.y, width, height, b.rotation() + offset);
        }

        Draw.color(frontColor);
        Draw.rect(frontRegion, b.x, b.y, width, height, b.rotation() + offset);

        Draw.reset();
    }
    @Override
    public void load(){
        super.load();

        backRegion = Core.atlas.find(backSprite == null ? (sprite + "-back") : backSprite);
        frontRegion = Core.atlas.find(sprite);
    }

}
