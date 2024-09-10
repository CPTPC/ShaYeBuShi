package shayebushi.entities.bullet;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Lightning;
import mindustry.entities.bullet.BombBulletType;
import mindustry.entities.bullet.ExplosionBulletType;
import mindustry.gen.Bullet;
import shayebushi.SYBSSounds;

public class YunShiBulletType extends BombBulletType {
    public YunShiBulletType(float damage, float radius){
        super(damage, radius, "yunshi-bullet") ;
        this.splashDamage = this.damage = splashDamageRadius = 0 ;
        width = height = radius ;
        shrinkX = shrinkY = 0.8f;
        fragBullets = 1 ;
        fragBullet = new ExplosionBulletType(damage, radius) {
            @Override
            public void hit(Bullet b, float x, float y){
                //System.out.println(Math.max(width, height) / 16);
                hitEffect.at(x, y, Math.max(width, height) / 8, hitColor);
                hitSound.at(x, y, hitSoundPitch, hitSoundVolume);

                Effect.shake(hitShake, hitShake, b);

                if(fragOnHit){
                    createFrags(b, x, y);
                }
                createPuddles(b, x, y);
                createIncend(b, x, y);
                createUnits(b, x, y);

                if(suppressionRange > 0){
                    //bullets are pooled, require separate Vec2 instance
                    Damage.applySuppression(b.team, b.x, b.y, suppressionRange, suppressionDuration, 0f, suppressionEffectChance, new Vec2(b.x, b.y));
                }

                createSplashDamage(b, x, y);

                for(int i = 0; i < lightning; i++){
                    Lightning.create(b, lightningColor, lightningDamage < 0 ? damage : lightningDamage, b.x, b.y, b.rotation() + Mathf.range(lightningCone/2) + lightningAngle, lightningLength + Mathf.random(lightningLengthRand));
                }
            }
            @Override
            public void despawned(Bullet b){
                if(despawnHit){
                    hit(b);
                }else{
                    createUnits(b, b.x, b.y);
                }

                if(!fragOnHit){
                    createFrags(b, b.x, b.y);
                }

                despawnEffect.at(b.x, b.y, Math.max(width, height) / 8, hitColor);
                despawnSound.at(b);

                Effect.shake(despawnShake, despawnShake, b);
            }
            {
            despawnHit = true ;
            hitEffect = Fx.dynamicExplosion ;
            hitSound = SYBSSounds.explode ;
            hitShake = 10 ;
            killShooter = false ;
        }} ;
    }

    @Override
    public void draw(Bullet b) {
        super.draw(b);
    }
}
