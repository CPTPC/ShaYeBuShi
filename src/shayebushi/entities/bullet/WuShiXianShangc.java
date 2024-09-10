package shayebushi.entities.bullet;

import arc.Events;
import arc.func.Boolf;
import arc.util.Tmp;
import mindustry.entities.Fires;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.world.blocks.ConstructBlock;
import shayebushi.SYBSShenShengUnitTypes;
import shayebushi.SYBSUnitTypes;

public interface WuShiXianShangc {

    static final EventType.UnitDamageEvent bulletDamageEvent = new EventType.UnitDamageEvent();

    default Boolf<Bullet> can() {
        return b -> true ;
    }

    default void poxianshang(BulletType bt, Bullet b, Hitboxc entity) {
        boolean wasDead = entity instanceof Unit u && u.dead;

        if(entity instanceof Healthc h && b.type == bt){
            if (can().get(b)) {
                h.health(h.health() - b.damage);
            }
            else {
                h.damage(b.damage);
            }
        }

        if(entity instanceof Unit unit){
            Tmp.v3.set(unit).sub(b).nor().scl(bt.knockback * 80f);
            if(bt.impact) Tmp.v3.setAngle(b.rotation() + (bt.knockback < 0 ? 180f : 0f));
            unit.impulse(Tmp.v3);
            unit.apply(bt.status, bt.statusDuration);
            Events.fire(bulletDamageEvent.set(unit, b));
        }

        if(!wasDead && entity instanceof Unit unit && unit.dead){
            Events.fire(new EventType.UnitBulletDestroyEvent(unit, b));
        }
    }
}
