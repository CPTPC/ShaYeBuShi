package shayebushi.entities.abilities;

import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.part.HaloPart;
import mindustry.entities.part.ShapePart;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import shayebushi.SYBSFx;
import shayebushi.SYBSPal;
import shayebushi.ShaYeBuShi;
import shayebushi.entities.bullet.LineBullet;
import shayebushi.entities.bullet.LinkBullet;

import static arc.util.Time.delta;
import static arc.util.Time.toSeconds;
import static mindustry.Vars.tilesize;
import static shayebushi.SYBSPal.chaokongjian2;

public class TuoYuanGuiDaoAbility extends Ability {
    public float a = 40 * tilesize, b = 7.5f * tilesize, rotation = 0, stroke = 5 * tilesize, rotateSpeed = 5, dmg = 2000 ;
    public Color color1 = SYBSPal.chaokongjian2, color2 = SYBSPal.chaokongjian3 ;
    public BulletType type1, type2, type3 ;
    public int amount = 4 ;
    public float reload1 = 20 * toSeconds, reload2 ;
    public Sound sound1 = Sounds.laserblast, sound2 = Sounds.corexplode ;
    public float shake = 40, trailMultiplier = 2.8f ;

    public Bullet bullet ;
    public Seq<Bullet> bullets ;
    public float time1 = -1/*, rot = 0*/ ;
    public float status1 = 1 ;
    public float timer1 = 0, timer2 = 0 ;
    public boolean done = false ;
    public TuoYuanGuiDaoAbility() {
        type1 = new BasicBulletType() {
            @Override
            public void update(Bullet b2) {
                super.update(b2) ;
                if (b2.time >= b2.lifetime - 10) {
                    b2.lifetime = b2.time + 10 ;
                }
                //System.out.println(b2.time);
            }
            {
            speed = 0 ;
            lifetime = reload1 ;
            trailLength = (int) (Math.PI * (a + b) / 2 / tilesize * trailMultiplier) ;
            trailWidth = stroke / tilesize * 2 ;
            width = height = stroke ;
            trailColor = color1 ;
            frontColor = backColor = color2 ;
            damage = dmg ;
            pierce = true ;
            parts.addAll(new ShapePart() {{
                color = chaokongjian2 ;
                circle = true ;
                radiusTo = 3 * tilesize ;
                progress = PartProgress.life ;
            }}, new ShapePart() {{
                color = Color.white ;
                circle = true ;
                radiusTo = 3 * tilesize / 2f * 1.75f ;
                progress = PartProgress.life ;
            }}, new ShapePart() {{
                color = Color.black ;
                layer = Layer.space ;
                //color.a = 255 ;
                circle = true ;
                radiusTo = 3 * tilesize / 2f * 1.5f ;
                progress = PartProgress.life ;
            }}) ;
            trailEffect = SYBSFx.lizi3 ;
            trailChance = 1 ;
        }} ;
        type2 = new LineBullet() {{
            length = a * trailMultiplier ;
            width = stroke ;
            colors = new Color[]{color1, Color.white, Color.black} ;
            lifetime = 3.5f * toSeconds ;
            onlyOne = true ;
            amount = 0 ;
            overrideRotation = false ;
            damage = 160000 / 60f ;
            can = u -> true ;
        }} ;
        reload2 = type2.lifetime ;
        type3 = new LinkBullet() {{
            frontColor = backColor = chaokongjian2 ;
            width = height = stroke ;
            damage = 40000 / 60f ;
            lifetime = 5 * toSeconds ;
        }} ;
    }
    @Override
    public void update(Unit u) {
        super.update(u) ;
        if (bullet == null || !bullet.isAdded() || bullet.hit) {
            bullet = type1.create(u, u.x - a, u.y, 0) ;
        }
        if (bullets == null) {
            bullets = new Seq<>() ;
        }
        if (time1 == -a - 10) {
            time1 = -a ;
        }
        timer1 += delta ;
        if (timer1 >= reload1) {
            if (!done && (u.isShooting || Units.closestTarget(u.team, bullet.x, bullet.y, u.type.range) != null) && type2 != null) {
                for (int i = 0; i < 360; i += 360 / amount) {
                    bullets.add(type2.create(u, bullet.x, bullet.y, i));
                }
                sound1.at(bullet.x, bullet.y, 1, 2) ;
                bullet.remove() ;
                done = true ;
            }
            if (done) {
                timer2 += delta;
            }
        }
        if (timer2 >= reload2) {
            bullets.clear() ;
            if (type3 != null) {
                for (int i = 0; i < 360; i += 360 / amount) {
                    type3.create(u, bullet.x, bullet.y, i + 45) ;
                }
                sound2.at(bullet.x, bullet.y, 1, 2) ;
                Effect.shake(shake, 60, bullet.x, bullet.y);
            }
            timer1 = 0 ;
            timer2 = 0 ;
            done = false ;
        }
        time1 += delta * rotateSpeed * status1 ;
        //rot += delta * rotateSpeed ;
        if (time1 > a && status1 == 1) {
            status1 = -1 ;
        }
        else if (time1 < -a && status1 == -1) {
            status1 = 1 ;
        }
        Vec2 v = new Vec2(u.x + time1, (float) (u.y + Math.sqrt(Math.max((b * b) - (time1 * time1) * (b * b) / (a * a), 0)) * status1)) ;
        Vec2 v2 = ShaYeBuShi.rotate(v, rotation, u.x, u.y) ;
        bullet.set(v2.x, v2.y) ;
        //System.out.println(v.x + " " + v.y);
        for (Bullet b2 : bullets) {
            //b2.set(bullet);
            b2.rotation(b2.rotation() + Mathf.lerp(rotateSpeed, 0, Math.min(timer2 / reload2 + 0.5f, 1)));
        }
    }
    @Override
    public void init(UnitType u) {
        super.init(u) ;
        time1 = -a - 10 ;
    }
}
