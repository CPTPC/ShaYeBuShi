package shayebushi;

import mindustry.entities.Damage;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;

import static mindustry.Vars.tilesize;

public class SYBSBullets {
    public static BulletType hedandanpian ;
    public static void load() {
        hedandanpian = new BasicBulletType() {
            @Override
            public void createSplashDamage(Bullet b, float x, float y){
                super.createSplashDamage(b, x, y);
                for (StatusEffect e : new StatusEffect[]{SYBSStatusEffects.fushe, SYBSStatusEffects.shuaibian}) {
                    if (ShaYeBuShi.isJianYi(e)) {
                        Damage.status(b.team, x, y, splashDamageRadius, e, statusDuration, collidesAir, collidesGround);
                    }
                }
            }
            {
           speed = 16 ;
           lifetime = 30 ;
           damage = 8000 ;
           splashDamage = 12000 ;
           splashDamageRadius = 20 * tilesize ;
           despawnHit = true ;
           hitEffect = SYBSFx.hedanbaozhaxiao ;
           hitSound = Sounds.explosionbig ;
           trailLength = 10 * tilesize ;
           trailWidth = width = height = 2 * tilesize ;
           trailWidth /= tilesize ;
           frontColor = backColor = trailColor = Pal.reactorPurple2 ;
           incendAmount = 400 ;
           incendChance = 1 ;
           incendSpread = 10 * tilesize ;
           hitShake = 40 ;
        }};
    }
}
