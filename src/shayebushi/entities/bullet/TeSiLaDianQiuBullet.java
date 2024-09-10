package shayebushi.entities.bullet;

import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Nullable;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Mover;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.pattern.ShootMulti;
import mindustry.entities.pattern.ShootPattern;
import mindustry.entities.pattern.ShootSpread;
import mindustry.game.Team;
import mindustry.gen.*;
import shayebushi.ShaYeBuShi;

import static mindustry.Vars.tilesize;

public class TeSiLaDianQiuBullet extends BasicBulletType {
    public float range = 30 * tilesize ;
    public float reload = 12 ;
    public ShootPattern shoot = new ShootMulti(new ShootSpread(4, 15){{shotDelay = 2.5f;}}, new ShootPattern(){{shots = 3;shotDelay = 1f;}}) ;
    public TeSiLaDianQiuBullet() {

    }
    @Override
    public void update(Bullet b) {
        if (ShaYeBuShi.withIn(b.time / reload, 0, 1)) {
            Groups.bullet.intersect(b.x, b.y, range, range, b2 -> {
                if (b2 != null && b2 != b && b2.team == b.team) {
                    Damage.collideLine(b2, b.team, hitEffect, b2.x, b2.y, Angles.angle(b.x, b.y, b2.x, b2.y), b.dst(b2), true, laserAbsorb, pierceCap);
                    Fx.chainLightning.at(b.x, b.y, 0, backColor, b2);
                }
            });
        }
    }

    @Override
    public void init(Bullet b) {
        if(killShooter && b.owner() instanceof Healthc h && !h.dead()){
            h.kill();
        }

        if(instantDisappear){
            b.time = lifetime + 1f;
        }

        if(spawnBullets.size > 0){
            for(var bullet : spawnBullets){
                shoot.shoot(0, (xOffset, yOffset, angle, delay, mover) -> {
                    if(delay > 0f){
                        Time.run(delay, () -> bullet(b, bullet, xOffset, yOffset, angle, mover));
                    }else{
                        bullet(b, bullet, xOffset, yOffset, angle, mover);
                    }
                }, () -> {});
            }
        }
    }

    public void bullet(Bullet b, BulletType type, float xOffset, float yOffset, float angleOffset, Mover mover) {
        float
                bulletX = b.x,
                bulletY = b.y,
                shootAngle = b.rotation() + angleOffset + Mathf.range(inaccuracy + type.inaccuracy);
        type.create(b.owner, b.team, bulletX, bulletY, shootAngle, -1f, (1f - 0) + Mathf.random(0), 1, null, mover, b.aimX, b.aimY) ;
    }
}
