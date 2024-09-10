package shayebushi.entities.bullet;

import arc.Events;
import arc.graphics.Color;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.core.World;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.PointBulletType;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Cliff;
import mindustry.world.blocks.environment.StaticWall;
import shayebushi.SYBSBlocks;
import shayebushi.world.blocks.ShiKuaiBlock;

import static mindustry.Vars.tilesize;

public class ShiHuaBullet extends PointBulletType {
//    public ShiHuaBullet(){
//        scaleLife = true;
//        lifetime = 100f;
//        collides = false;
//        reflectable = false;
//        keepVelocity = false;
//        backMove = false;
//    }
    static final EventType.UnitDamageEvent bulletDamageEvent = new EventType.UnitDamageEvent();
    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        boolean wasDead = entity instanceof Unit u && u.dead;

        if(entity instanceof Healthc h){
            if(pierceArmor){
                h.damagePierce(b.damage);
            }else{
                h.damage(b.damage);
            }
        }

        if(entity instanceof Unit unit){
            Tmp.v3.set(unit).sub(b).nor().scl(knockback * 80f);
            if(impact) Tmp.v3.setAngle(b.rotation() + (knockback < 0 ? 180f : 0f));
            unit.impulse(Tmp.v3);
            unit.apply(status, statusDuration);
            Tile t = Vars.world.tile((int)unit.x / tilesize, (int)unit.y / tilesize) ;
            if (t != null && !(t.block() instanceof Cliff) && !(t.block() instanceof StaticWall) && t.build == null) {
                Block block = SYBSBlocks.shikuais[(int)(unit.hitSize / tilesize)] ;
                t.setBlock(block, Team.derelict, 0);
                Building building = Vars.world.build((int)unit.x / tilesize, (int)unit.y / tilesize) ;
                ((ShiKuaiBlock.ShiKuaiBuild)building).setUnit(unit) ;
                //t.setBlock(block, Team.derelict, 0, block::newBuilding);
            }
            unit.trail = null ;
            unit.remove();
            float rx = Tmp.v1.x, ry = Tmp.v1.y;
            Color color = Pal.items ;
            for (int i = 0 ; i < 9 ; i ++) {
                Effect effect1 = Fx.hitLaserBlast;
                Effect effect2 = Fx.lightningCharge;
                effect1.at(unit.x, unit.y, b.angleTo(unit), color);
                effect2.at(b.owner instanceof Posc p ? p.getX() : 0, b.owner instanceof Posc p ? p.getY() : 0, 0f, color, unit);
                effect1.at(rx, ry, unit.angleTo(unit), color);
            }
            Events.fire(bulletDamageEvent.set(unit, b));
        }

        if(!wasDead && entity instanceof Unit unit && unit.dead){
            Events.fire(new EventType.UnitBulletDestroyEvent(unit, b));
        }

        handlePierce(b, health, entity.x(), entity.y());
    }
}
