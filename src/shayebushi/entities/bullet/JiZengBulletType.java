package shayebushi.entities.bullet;

import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Pal;

public class JiZengBulletType extends LightningContinuousLaserBulletType {
    public float chixushijian ;
    public float damagee ;
    public int meimiaofanbeicishu  = 60 ;
    public JiZengBulletType(float damage){
        this.damage = damage;
        this.damagee = damage ;
    }
    {
        removeAfterPierce = false;
        pierceCap = -1;
        speed = 0f;
        despawnEffect = Fx.none;
        shootEffect = Fx.none;
        lifetime = 16f;
        impact = true;
        keepVelocity = false;
        collides = false;
        pierce = true;
        hittable = false;
        absorbable = false;
        lightningColor = Pal.accentBack ;
    }

    @Override
    public float continuousDamage()
    {
        if(!continuous) return -1f;
        return (float)(damage / damageInterval * 60f);
    }
    @Override
    public float estimateDPS(){
        if(!continuous) return super.estimateDPS();
        //assume firing duration is about 100 by default, may not be accurate there's no way of knowing in this method
        //assume it pierces 3 blocks/units
        return (float)(damage * 100f / damageInterval * 3f);
    }

    @Override
    protected float calculateRange(){
        return Math.max(length, maxRange);
    }
    @Override
    public void init(){
        super.init();
        drawSize = Math.max(drawSize, length*2f);
    }

    @Override
    public void init(Bullet b){
        super.init(b);
        if(!continuous){
            applyDamage(b);
        }
    }
    @Override
    public void update(Bullet b){
        super.update(b) ;
        if (!(b.data instanceof float[])) {
            b.data = new float[]{0, 0} ;
        }
        ((float[])b.data)[0] += Time.delta ;
        if (((float[])b.data)[0] >= Time.toSeconds) {
            ((float[])b.data)[0] = 0 ;
            ((float[])b.data)[1] = 0 ;
        }
        if (((float[])b.data)[1] < meimiaofanbeicishu) {
            ((float[])b.data)[1] ++ ;
            b.damage *= 2 ;
        }
        //System.out.println(b.damage * 60 + " " + ((float[])b.data)[1] + " " + ((float[])b.data)[0]);
    }

    public void applyDamage(Bullet b){
        Damage.collideLine(b, b.team, hitEffect, b.x, b.y, b.rotation(), currentLength(b), largeHit, laserAbsorb, pierceCap);
        //this.damage *= 2 ;
    }
    public float currentLength(Bullet b){
        return length;
    }
}
