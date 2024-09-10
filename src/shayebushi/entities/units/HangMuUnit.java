package shayebushi.entities.units;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.ai.Pathfinder;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.entities.EntityCollisions;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.StatusEntry;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.graphics.Trail;
import mindustry.input.InputHandler;
import mindustry.type.Item;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;

import static mindustry.Vars.*;

public class HangMuUnit extends PayloadUnit implements WaterMovec {
    protected transient Trail tleft = new Trail(1);
    protected transient Color trailColor = Blocks.water.mapColor.cpy().mul(1.5F);
    protected transient Trail tright = new Trail(1);
    public static HangMuUnit create() {
        return new HangMuUnit();
    }
    public int classId(){
        return 116 ;
    }
    public boolean onSolid() {

        return EntityCollisions.waterSolid(tileX(), tileY());
    }
    public boolean onLiquid() {

        Tile tile = tileOn();
        return tile != null && tile.floor().isLiquid;
    }
    public float floorSpeedMultiplier() {

        Floor on = isFlying() ? Blocks.air.asFloor() : floorOn();
        return (on.shallow ? 1.0F : 1.3F) * speedMultiplier;
    }
    public int pathType() {

        return Pathfinder.costNaval;
    }
    public EntityCollisions.SolidPred solidity() {

        return isFlying() ? null : EntityCollisions::waterSolid;
    }
    public void add() {
        if(added == true) return;
        index__all = Groups.all.addIndex(this);
        index__unit = Groups.unit.addIndex(this);
        index__sync = Groups.sync.addIndex(this);
        index__draw = Groups.draw.addIndex(this);
        entity: {

            added = true;
        }
        hitbox: {

            updateLastPosition();
        }
        unit: {

            team.data().updateCount(type, 1);
            if (type.useUnitCap && count() > cap() && !spawnedByCore && !dead && !state.rules.editor) {
                Call.unitCapDeath(this);
                team.data().updateCount(type, -1);
            }
        }
        watermove: {

            tleft.clear();
            tright.clear();
        }
    }
    public void draw() {
        builder: {

            drawBuilding();
        }
        draw: {

        }
        miner: {

            if (!mining()) break miner;
            float focusLen = hitSize / 2.0F + Mathf.absin(Time.time, 1.1F, 0.5F);
            float swingScl = 12.0F;
            float swingMag = tilesize / 8.0F;
            float flashScl = 0.3F;
            float px = x + Angles.trnsx(rotation, focusLen);
            float py = y + Angles.trnsy(rotation, focusLen);
            float ex = mineTile.worldx() + Mathf.sin(Time.time + 48, swingScl, swingMag);
            float ey = mineTile.worldy() + Mathf.sin(Time.time + 48, swingScl + 2.0F, swingMag);
            Draw.z(Layer.flyingUnit + 0.1F);
            Draw.color(Color.lightGray, Color.white, 1.0F - flashScl + Mathf.absin(Time.time, 0.5F, flashScl));
            Drawf.laser(Core.atlas.find("minelaser"), Core.atlas.find("minelaser-end"), px, py, ex, ey, 0.75F);
            if (isLocal()) {
                Lines.stroke(1.0F, Pal.accent);
                Lines.poly(mineTile.worldx(), mineTile.worldy(), 4, tilesize / 2.0F * Mathf.sqrt2, Time.time);
            }
            Draw.color();
        }
        status: {

            for (StatusEntry e : statuses) {
                e.effect.draw(this, e.time);
            }
        }
        unit: {

            type.draw(this);
        }
        watermove: {

            float z = Draw.z();
            Draw.z(Layer.debris);
            Floor floor = tileOn() == null ? Blocks.air.asFloor() : tileOn().floor();
            Color color = Tmp.c1.set(floor.mapColor.equals(Color.black) ? Blocks.water.mapColor : floor.mapColor).mul(1.5F);
            trailColor.lerp(color, Mathf.clamp(Time.delta * 0.04F));
            tleft.draw(trailColor, type.trailScl);
            tright.draw(trailColor, type.trailScl);
            Draw.z(z);
        }
    }
    public void update() {
        vel: {

            if (!net.client() || isLocal()) {
                float px = x;
                float py = y;
                move(vel.x * Time.delta, vel.y * Time.delta);
                if (Mathf.equal(px, x)) vel.x = 0;
                if (Mathf.equal(py, y)) vel.y = 0;
                vel.scl(Math.max(1.0F - drag * Time.delta, 0));
            }
        }
        bounded: {

            if (!type.bounded) break bounded;
            float bot = 0.0F;
            float left = 0.0F;
            float top = world.unitHeight();
            float right = world.unitWidth();
            if (state.rules.limitMapArea && !team.isAI()) {
                bot = state.rules.limitY * tilesize;
                left = state.rules.limitX * tilesize;
                top = state.rules.limitHeight * tilesize + bot;
                right = state.rules.limitWidth * tilesize + left;
            }
            if (!net.client() || isLocal()) {
                float dx = 0.0F;
                float dy = 0.0F;
                if (x < left) dx += (-(x - left) / warpDst);
                if (y < bot) dy += (-(y - bot) / warpDst);
                if (x > right) dx -= (x - right) / warpDst;
                if (y > top) dy -= (y - top) / warpDst;
                velAddNet(dx * Time.delta, dy * Time.delta);
            }
            if (isGrounded()) {
                x = Mathf.clamp(x, left, right - tilesize);
                y = Mathf.clamp(y, bot, top - tilesize);
            }
            if (x < -finalWorldBounds + left || y < -finalWorldBounds + bot || x >= right + finalWorldBounds || y >= top + finalWorldBounds) {
                kill();
            }
        }
        builder: {

            updateBuildLogic();
        }
        entity: {

        }
        flying: {

            Floor floor = floorOn();
            if (isFlying() != wasFlying) {
                if (wasFlying) {
                    if (tileOn() != null) {
                        Fx.unitLand.at(x, y, floorOn().isLiquid ? 1.0F : 0.5F, tileOn().floor().mapColor);
                    }
                }
                wasFlying = isFlying();
            }
            if (!hovering && isGrounded()) {
                if ((splashTimer += Mathf.dst(deltaX(), deltaY())) >= (7.0F + hitSize() / 8.0F)) {
                    floor.walkEffect.at(x, y, hitSize() / 8.0F, floor.mapColor);
                    splashTimer = 0.0F;
                    if (emitWalkSound()) {
                        floor.walkSound.at(x, y, Mathf.random(floor.walkSoundPitchMin, floor.walkSoundPitchMax), floor.walkSoundVolume);
                    }
                }
            }
            updateDrowning();
        }
        health: {

            hitTime -= Time.delta / hitDuration;
        }
        hitbox: {

        }
        items: {

            stack.amount = Mathf.clamp(stack.amount, 0, itemCapacity());
            itemTime = Mathf.lerpDelta(itemTime, Mathf.num(hasItem()), 0.05F);
        }
        miner: {

            if (mineTile == null) break miner;
            Building core = closestCore();
            Item item = getMineResult(mineTile);
            if (core != null && item != null && !acceptsItem(item) && within(core, mineTransferRange) && !offloadImmediately()) {
                int accepted = core.acceptStack(item(), stack().amount, this);
                if (accepted > 0) {
                    Call.transferItemTo(this, item(), accepted, mineTile.worldx() + Mathf.range(tilesize / 2.0F), mineTile.worldy() + Mathf.range(tilesize / 2.0F), core);
                    clearItem();
                }
            }
            if ((!net.client() || isLocal()) && !validMine(mineTile)) {
                mineTile = null;
                mineTimer = 0.0F;
            } else if (mining() && item != null) {
                mineTimer += Time.delta * type.mineSpeed;
                if (Mathf.chance(0.06 * Time.delta)) {
                    Fx.pulverizeSmall.at(mineTile.worldx() + Mathf.range(tilesize / 2.0F), mineTile.worldy() + Mathf.range(tilesize / 2.0F), 0.0F, item.color);
                }
                if (mineTimer >= 50.0F + (type.mineHardnessScaling ? item.hardness * 15.0F : 15.0F)) {
                    mineTimer = 0;
                    if (state.rules.sector != null && team() == state.rules.defaultTeam) state.rules.sector.info.handleProduction(item, 1);
                    if (core != null && within(core, mineTransferRange) && core.acceptStack(item, 1, this) == 1 && offloadImmediately()) {
                        if (item() == item && !net.client()) addItem(item);
                        Call.transferItemTo(this, item, 1, mineTile.worldx() + Mathf.range(tilesize / 2.0F), mineTile.worldy() + Mathf.range(tilesize / 2.0F), core);
                    } else if (acceptsItem(item)) {
                        InputHandler.transferItemToUnit(item, mineTile.worldx() + Mathf.range(tilesize / 2.0F), mineTile.worldy() + Mathf.range(tilesize / 2.0F), this);
                    } else {
                        mineTile = null;
                        mineTimer = 0.0F;
                    }
                }
                if (!headless) {
                    control.sound.loop(type.mineSound, this, type.mineSoundVolume);
                }
            }
        }
        shield: {

            shieldAlpha -= Time.delta / 15.0F;
            if (shieldAlpha < 0) shieldAlpha = 0.0F;
        }
        status: {

            Floor floor = floorOn();
            if (isGrounded() && !type.hovering) {
                apply(floor.status, floor.statusDuration);
            }
            applied.clear();
            speedMultiplier = damageMultiplier = healthMultiplier = reloadMultiplier = buildSpeedMultiplier = dragMultiplier = 1.0F;
            disarmed = false;
            if (statuses.isEmpty()) break status;
            int index = 0;
            while (index < statuses.size) {
                StatusEntry entry = statuses.get(index++);
                entry.time = Math.max(entry.time - Time.delta, 0);
                if (entry.effect == null || (entry.time <= 0 && !entry.effect.permanent)) {
                    Pools.free(entry);
                    index--;
                    statuses.remove(index);
                } else {
                    applied.set(entry.effect.id);
                    speedMultiplier *= entry.effect.speedMultiplier;
                    healthMultiplier *= entry.effect.healthMultiplier;
                    damageMultiplier *= entry.effect.damageMultiplier;
                    reloadMultiplier *= entry.effect.reloadMultiplier;
                    buildSpeedMultiplier *= entry.effect.buildSpeedMultiplier;
                    dragMultiplier *= entry.effect.dragMultiplier;
                    disarmed |= entry.effect.disarm;
                    entry.effect.update(this, entry.time);
                }
            }
        }
        sync: {

            if ((Vars.net.client() && !isLocal()) || isRemote()) {
                interpolate();
            }
        }
        unit: {

            type.update(this);
            if (wasHealed && healTime <= -1.0F) {
                healTime = 1.0F;
            }
            healTime -= Time.delta / 20.0F;
            wasHealed = false;
            if (team.isOnlyAI() && state.isCampaign() && state.getSector().isCaptured()) {
                kill();
            }
            if (!headless && type.loopSound != Sounds.none) {
                control.sound.loop(type.loopSound, this, type.loopSoundVolume);
            }
            if (!type.supportsEnv(state.rules.env) && !dead) {
                Call.unitEnvDeath(this);
                team.data().updateCount(type, -1);
            }
            if (state.rules.unitAmmo && ammo < type.ammoCapacity - 1.0E-4F) {
                resupplyTime += Time.delta;
                if (resupplyTime > 10.0F) {
                    type.ammoType.resupply(this);
                    resupplyTime = 0.0F;
                }
            }
            for (Ability a : abilities) {
                a.update(this);
            }
            if (trail != null) {
                trail.length = type.trailLength;
                float scale = type.useEngineElevation ? elevation : 1.0F;
                float offset = type.engineOffset / 2.0F + type.engineOffset / 2.0F * scale;
                float cx = x + Angles.trnsx(rotation + 180, offset);
                float cy = y + Angles.trnsy(rotation + 180, offset);
                trail.update(cx, cy);
            }
            drag = type.drag * (isGrounded() ? (floorOn().dragMultiplier) : 1.0F) * dragMultiplier * state.rules.dragMultiplier;
            if (team != state.rules.waveTeam && state.hasSpawns() && (!net.client() || isLocal()) && hittable()) {
                float relativeSize = state.rules.dropZoneRadius + hitSize / 2.0F + 1.0F;
                for (Tile spawn : spawner.getSpawns()) {
                    if (within(spawn.worldx(), spawn.worldy(), relativeSize)) {
                        velAddNet(Tmp.v1.set(this).sub(spawn.worldx(), spawn.worldy()).setLength(0.1F + 1.0F - dst(spawn) / relativeSize).scl(0.45F * Time.delta));
                    }
                }
            }
            if (dead || health <= 0) {
                drag = 0.01F;
                if (Mathf.chanceDelta(0.1)) {
                    Tmp.v1.rnd(Mathf.range(hitSize));
                    type.fallEffect.at(x + Tmp.v1.x, y + Tmp.v1.y);
                }
                if (Mathf.chanceDelta(0.2)) {
                    float offset = type.engineOffset / 2.0F + type.engineOffset / 2.0F * elevation;
                    float range = Mathf.range(type.engineSize);
                    type.fallEngineEffect.at(x + Angles.trnsx(rotation + 180, offset) + Mathf.range(range), y + Angles.trnsy(rotation + 180, offset) + Mathf.range(range), Mathf.random());
                }
                elevation -= type.fallSpeed * Time.delta;
                if (isGrounded() || health <= -maxHealth) {
                    Call.unitDestroy(id);
                }
            }
            Tile tile = tileOn();
            Floor floor = floorOn();
            if (tile != null && isGrounded() && !type.hovering) {
                if (tile.build != null) {
                    tile.build.unitOn(this);
                }
                if (floor.damageTaken > 0.0F) {
                    damageContinuous(floor.damageTaken);
                }
            }
            if (tile != null && !canPassOn()) {
                if (type.canBoost) {
                    elevation = 1.0F;
                } else if (!net.client()) {
                    kill();
                }
            }
            if (!net.client() && !dead) {
                controller.updateUnit();
            }
            if (!controller.isValidController()) {
                resetController();
            }
            if (spawnedByCore && !isPlayer() && !dead) {
                Call.unitDespawn(this);
            }
        }
        watermove: {

            boolean flying = isFlying();
            for (int i = 0; i < 2; i++) {
                Trail t = i == 0 ? tleft : tright;
                t.length = type.trailLength;
                int sign = i == 0 ? -1 : 1;
                float cx = Angles.trnsx(rotation - 90, type.waveTrailX * sign, type.waveTrailY) + x;
                float cy = Angles.trnsy(rotation - 90, type.waveTrailX * sign, type.waveTrailY) + y;
                t.update(cx, cy, world.floorWorld(cx, cy).isLiquid && !flying ? 1 : 0);
            }
        }
        weapons: {

            for (WeaponMount mount : mounts) {
                mount.weapon.update(this, mount);
            }
        }
    }

}
