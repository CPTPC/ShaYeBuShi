package shayebushi.entities.abilities;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Tmp;
import mindustry.entities.abilities.Ability;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import shayebushi.ShaYeBuShi;

import static mindustry.Vars.tilesize;

public class CircleAbility extends Ability {
    public float radFrom = 18 * tilesize, radTo = 0, rotateSpeed = 1, width = tilesize ;
    public int amount = 6 ;
    public BulletType b ;
    public Color colorFrom, colorTo = Color.clear ;
    public Seq<Bullet> bs = new Seq<>() ;

    public float rotation = 0 ;
    @Override
    public void init(UnitType u) {
        super.init(u) ;
        if (b == null) {
            b = new BulletType() {
                @Override
                public void drawTrail(Bullet b) {
                    if(trailLength > 0 && b.trail != null){
                        float z = Draw.z();
                        Draw.z(z - 0.0001f);
                        b.trail.draw(Tmp.c4.set(colorFrom).lerp(colorTo, b.fin()), trailWidth);
                        Draw.z(z);
                    }
                }
                {
                trailWidth = width / tilesize ;
                trailLength = Integer.MAX_VALUE ;
            }} ;
        }
    }
    @Override
    public void update(Unit u) {
        super.update(u) ;
        rotation += rotateSpeed ;
    }
    @Override
    public void draw(Unit u) {
        super.draw(u) ;
        for (int i = 0 ; i < amount ; i ++) {
            Vec2 v = ShaYeBuShi.circle(rotation + 360f / amount * i, radFrom, u.x, u.y) ;
            Bullet b2 = b.create(u, v.x, v.y, 0) ;

        }
    }
}
