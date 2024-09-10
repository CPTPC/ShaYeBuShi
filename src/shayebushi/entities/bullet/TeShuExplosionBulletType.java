package shayebushi.entities.bullet;

import mindustry.content.Fx;

public class TeShuExplosionBulletType extends TeShuBulletType{

    public TeShuExplosionBulletType(float splashDamage, float splashDamageRadius){
        this.splashDamage = splashDamage;
        this.splashDamageRadius = splashDamageRadius;
        rangeOverride = Math.max(rangeOverride, splashDamageRadius * 2f / 3f);
    }

    public TeShuExplosionBulletType(){
    }

    {
        hittable = false;
        lifetime = 1f;
        speed = 0f;
        rangeOverride = 20f;
        shootEffect = Fx.massiveExplosion;
        instantDisappear = true;
        scaledSplashDamage = true;
        killShooter = true;
        collides = false;
        keepVelocity = false;
    }
//
//    public TeShuExplosionBulletType(float speed, float damage) {
//        super(speed, damage);
//    }
}
