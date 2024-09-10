package shayebushi.entities.bullet;

import arc.Events;
import arc.util.Tmp;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType;
import mindustry.gen.Bullet;
import mindustry.gen.Healthc;
import mindustry.gen.Hitboxc;
import mindustry.gen.Unit;

public class TeShuBulletType extends BulletType {
    public float ground = 1 ;
    public float air = 1 ;
    public float naval = 1 ;
    static final EventType.UnitDamageEvent bulletDamageEvent = new EventType.UnitDamageEvent();
    public TeShuBulletType(float speed, float damage){
        super(speed, damage);
    }
    public TeShuBulletType(){

    }
    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        boolean wasDead = entity instanceof Unit u && u.dead;

        if(entity instanceof Healthc h){
            if (entity instanceof Unit u) {
                if (pierceArmor) {
                    if (u.isGrounded() && !u.type.naval) {
                        u.damagePierce(b.damage * ground);
                    }
                    else if (u.isFlying()){
                        u.damagePierce(b.damage * air);
                    }
                    else if (u.type.naval){
                        u.damagePierce(b.damage * naval);
                    }
                } else {
                    if (u.isGrounded() && !u.type.naval) {
                        u.damage(b.damage * ground);
                    }
                    else if (u.isFlying()){
                        u.damage(b.damage * air);
                    }
                    else if (u.type.naval){
                        u.damage(b.damage * naval);
                    }
                }
            }
            else {
                if (pierceArmor) {
                    h.damagePierce(b.damage);
                } else {
                    h.damage(b.damage);
                }
            }
        }

        if(entity instanceof Unit unit){
            Tmp.v3.set(unit).sub(b).nor().scl(knockback * 80f);
            if(impact) Tmp.v3.setAngle(b.rotation() + (knockback < 0 ? 180f : 0f));
            unit.impulse(Tmp.v3);
            unit.apply(status, statusDuration);
            Events.fire(bulletDamageEvent.set(unit, b));
        }

        if(!wasDead && entity instanceof Unit unit && unit.dead){
            Events.fire(new EventType.UnitBulletDestroyEvent(unit, b));
        }

        handlePierce(b, health, entity.x(), entity.y());
    }

}
