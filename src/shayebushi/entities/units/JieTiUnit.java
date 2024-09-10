package shayebushi.entities.units;

import arc.Events;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.annotations.Annotations;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.WeaponMount;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.gen.Hitboxc;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import shayebushi.SYBSUnitTypes;
import shayebushi.type.unit.JieTiUnitType;

import static mindustry.Vars.headless;
import static mindustry.Vars.state;

public class JieTiUnit extends QiangZhiXianShangUnitEntity implements JieTic {
    public Unit owner ;
    public int ownerId ;
    public Unit shangjie ;
    public int shangjieId ;
    private boolean si = false ;

    @Override
    public int classId() {
        return 119 ;
    }

    @Override
    @Annotations.CallSuper
    public void read(Reads read) {
        super.read(read);
        ownerId = read.i();
        shangjieId = read.i() ;
        si = read.bool() ;
    }

    @Override
    @Annotations.CallSuper
    public void write(Writes write) {
        super.write(write);
        write.i(ownerId);
        write.i(shangjieId);
        write.bool(si) ;
    }

    @Override
    public void owner(Unit u) {
        owner = u ;
        ownerId = u.id ;
    }

    @Override
    public void remove() {
        if ((damageable() && health <= 0) || si) {
            if(!added) return;
            Groups.all.removeIndex(this, index__all);;
            index__all = -1;
            Groups.unit.removeIndex(this, index__unit);;
            index__unit = -1;
            Groups.sync.removeIndex(this, index__sync);;
            index__sync = -1;
            Groups.draw.removeIndex(this, index__draw);;
            index__draw = -1;
            entity: {

                added = false;
            }
            sync: {

                if (Vars.net.client()) {
                    Vars.netClient.addRemovedEntity(id());
                }
            }
            unit: {

                team.data().updateCount(type, -1);
                controller.removed(this);
                if (trail != null && trail.size() > 0) {
                    Fx.trailFade.at(x, y, trail.width(), type.trailColor == null ? team.color : type.trailColor, trail.copy());
                }
            }
            weapons: {

                for (WeaponMount mount : mounts) {
                    if (mount.weapon.continuous && mount.bullet != null && mount.bullet.owner == this) {
                        mount.bullet.time = mount.bullet.lifetime - 10.0F;
                        mount.bullet = null;
                    }
                    if (mount.sound != null) {
                        mount.sound.stop();
                    }
                }
            }
        }
    }
    @Override
    public void destroy(){
        if ((damageable() && health <= 0) || si) {
            if (!isAdded() || !type.killable) return;
            float explosiveness = 2.0F + item().explosiveness * stack().amount * 1.53F;
            float flammability = item().flammability * stack().amount / 1.9F;
            float power = item().charge * Mathf.pow(stack().amount, 1.11F) * 160.0F;
            if (!spawnedByCore) {
                Damage.dynamicExplosion(x, y, flammability, explosiveness, power, (bounds() + type.legLength / 1.7F) / 2.0F, state.rules.damageExplosions && state.rules.unitCrashDamage(team) > 0, item().flammability > 1, team, type.deathExplosionEffect);
            } else {
                type.deathExplosionEffect.at(x, y, bounds() / 2.0F / 8.0F);
            }
            float shake = hitSize / 3.0F;
            if (type.createScorch) {
                Effect.scorch(x, y, (int)(hitSize / 5));
            }
            Effect.shake(shake, shake, this);
            type.deathSound.at(this);
            Events.fire(new EventType.UnitDestroyEvent(this));
            if (explosiveness > 7.0F && (isLocal() || wasPlayer)) {
                Events.fire(EventType.Trigger.suicideBomb);
            }
            for (WeaponMount mount : mounts) {
                if (mount.weapon.shootOnDeath && !(mount.weapon.bullet.killShooter && mount.totalShots > 0)) {
                    mount.reload = 0.0F;
                    mount.shoot = true;
                    mount.weapon.update(this, mount);
                }
            }
            if (type.flying && !spawnedByCore && type.createWreck && state.rules.unitCrashDamage(team) > 0) {
                Damage.damage(team, x, y, Mathf.pow(hitSize, 0.94F) * 1.25F, Mathf.pow(hitSize, 0.75F) * type.crashDamageMultiplier * 5.0F * state.rules.unitCrashDamage(team), true, false, true);
            }
            if (!headless && type.createScorch) {
                for (int i = 0; i < type.wreckRegions.length; i++) {
                    if (type.wreckRegions[i].found()) {
                        float range = type.hitSize / 4.0F;
                        Tmp.v1.rnd(range);
                        Effect.decal(type.wreckRegions[i], x + Tmp.v1.x, y + Tmp.v1.y, rotation - 90);
                    }
                }
            }
            for (Ability a : abilities) {
                a.death(this);
            }
            type.killed(this);
            remove();
        }
    }
    protected void fixedKill() {
        si = true ;
        health: {

        }
        unit: {

            wasPlayer = isLocal();
            health = Math.min(health, 0);
            dead = true;
            if (!type.flying || !type.createWreck) {
                destroy();
            }
        }
    }

    @Override
    public int pucongwei() {
        return 0;
    }

    @Override
    public void pucongwei(int i) {

    }

    @Override
    public Unit owner() {
        if (owner == null) {
            owner = Groups.unit.getByID(ownerId) ;
        }
        return owner ;
    }

    @Override
    public void update() {
        super.update();
        updateOwner();
        updateJieti();
    }

    @Override
    public float beilv() {
        return 0.25f ;
    }
    @Override
    public Unit shangjie() {
        if (shangjie == null) {
            shangjie = Groups.unit.getByID(shangjieId) ;
        }
        return shangjie ;
    }
    @Override
    public void shangjie(Unit u) {
        shangjie = u ;
        shangjieId = u.id ;
    }

    @Override
    public void rawDamage(float d) {
        damageJieti(d);
    }

    @Override
    public boolean collides(Hitboxc other) {
        return !(other instanceof JieTic || other instanceof TouBuUnit) && super.collides(other);
    }
    @Override
    public TextureRegion icon() {
        return type instanceof JieTiUnitType j ? j.icon(this) : SYBSUnitTypes.getError() ;
    }
    @Override
    public float floorSpeedMultiplier() {
        return 1 ;
    }
    public void setHealth(float health) {
        shangzhen = this.health = health ;
    }
    public boolean si() {
        return si ;
    }
}
