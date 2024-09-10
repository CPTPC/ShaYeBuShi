package shayebushi.world.blocks.abilities;

import arc.graphics.Color;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import shayebushi.ShaYeBuShi;
import shayebushi.entities.bullet.PayloadBullet;

public class JieLieAbility extends BlockAbility {
    public float reload = 60 / 35f ;
    public float range = 50 * 8 ;
    public float timer = 0 ;
    public Effect effect = Fx.pointBeam ;
    public Color color = Color.valueOf("909090ff") ;
    public int amount = 60 ;
    public BulletType type ;
    public Seq<Bullet> bullets = new Seq<>() ;
    public float rotateSpeed = 0.1f ;
    @Override
    public void update(Building b) {
        super.update(b);
        //System.out.println(6);
        //System.out.println(bullets.size);
        if (timer + Time.delta >= reload) {
            Groups.bullet.intersect(b.x, b.y, range, range, b2 -> {
                if (b2.team != b.team && b2.type.hittable) {
                    b2.remove();
                    effect.at(b.x, b.y, 0, color, new Vec2(b2.x, b2.y));
                }
            }) ;
            timer = 0 ;
        }
        timer += Time.delta ;
        for (Bullet bu : bullets) {
            if (bu.type != type) {
                continue;
            }
            //Bullet bu = bullets.get(i) ;
            Vec2 v = ShaYeBuShi.circle(bu.rotation() + rotateSpeed, range, b.x, b.y) ;
            bu.x = v.x ;
            bu.y = v.y ;
            bu.rotation(bu.rotation() + rotateSpeed);
//            if (bu.type != null) {
//                System.out.println(bu.type.getClass().getSimpleName() + " " + bullets.size);
//            }
        }
    }
    @Override
    public void onBuilt(Building b) {
        super.onBuilt(b);
        if (type == null) return ;
        for (int i = 0 ; i < 360 ; i += 360f / amount) {
            Vec2 v = ShaYeBuShi.circle(i, range, b.x, b.y) ;
            this.bullets.add(type.create(b, v.x, v.y, i)) ;
        }
    }
    @Override
    public void onRead(Building b) {
        onBuilt(b);
    }
    @Override
    public void onRemoved(Building b) {
        super.onRemoved(b);
        for (Bullet bu : this.bullets) {
            bu.remove();
        }
    }
    @Override
    public JieLieAbility copy() {
        try{
            JieLieAbility j = (JieLieAbility) clone();
            j.bullets = new Seq<>() ;
            return j ;
        }catch(CloneNotSupportedException e){
            //I am disgusted
            throw new RuntimeException("java sucks", e);
        }
    }
}
