package shayebushi.entities.bullet;

import arc.math.Interp;
import mindustry.content.Fx;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;

public class BaiFenBiArtilleryBulletType extends BaiFenBiBulletType{
    public float trailMult = 1f, trailSize = 4f;

    public BaiFenBiArtilleryBulletType(float speed, float damage, float baifenbi, String bulletSprite){
        super(speed, damage, baifenbi, bulletSprite);
        collidesTiles = false;
        collides = false;
        collidesAir = false;
        scaleLife = true;
        hitShake = 1f;
        hitSound = Sounds.explosion;
        hitEffect = Fx.flakExplosion;
        shootEffect = Fx.shootBig;
        trailEffect = Fx.artilleryTrail;

        //default settings:
        shrinkX = 0.15f;
        shrinkY = 0.63f;
        shrinkInterp = Interp.slope;

        //for trail:

        /*
        trailLength = 27;
        trailWidth = 3.5f;
        trailEffect = Fx.none;
        trailColor = Pal.bulletYellowBack;

        trailInterp = Interp.slope;

        shrinkX = 0.8f;
        shrinkY = 0.3f;
        */
    }

    public BaiFenBiArtilleryBulletType(float speed, float damage, float baifenbi){
        this(speed, damage, baifenbi, "shell");
    }

    public BaiFenBiArtilleryBulletType(){
        this(1f, 1f, 1f, "shell");
    }

    @Override
    public void update(Bullet b){
        super.update(b);

        if(b.timer(0, (3 + b.fslope() * 2f) * trailMult)){
            trailEffect.at(b.x, b.y, b.fslope() * trailSize, backColor);
        }
    }
}
