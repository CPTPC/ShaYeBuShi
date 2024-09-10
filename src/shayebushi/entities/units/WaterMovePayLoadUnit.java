package shayebushi.entities.units;

import arc.Core;
import arc.Events;
import arc.func.Boolf;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.*;
import arc.scene.ui.layout.Table;
import arc.struct.Bits;
import arc.struct.Queue;
import arc.struct.Seq;
import arc.util.Structs;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.ai.Pathfinder;
import mindustry.ai.types.CommandAI;
import mindustry.ai.types.LogicAI;
import mindustry.annotations.Annotations;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.core.World;
import mindustry.ctype.Content;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.EntityCollisions;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.*;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.graphics.Trail;
import mindustry.input.InputHandler;
import mindustry.logic.LAccess;
import mindustry.type.Item;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.Build;
import mindustry.world.Tile;
import mindustry.world.blocks.ConstructBlock;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.payloads.BuildPayload;
import mindustry.world.blocks.payloads.Payload;
import mindustry.world.blocks.payloads.UnitPayload;
import mindustry.world.blocks.power.PowerGraph;
import mindustry.world.blocks.storage.CoreBlock;

import java.nio.FloatBuffer;
import java.util.Iterator;

import static mindustry.Vars.*;
import static mindustry.Vars.world;
import static mindustry.logic.GlobalVars.*;

public class WaterMovePayLoadUnit extends PayloadUnit implements Boundedc, Builderc, Drawc, Entityc, Flyingc, Healthc, Hitboxc, Itemsc, Minerc, Physicsc, Posc, Rotc, Shieldc, Statusc, Syncc, Teamc, Unitc, Velc, WaterMovec, Weaponsc, Payloadc {
    public boolean fix = true ;
    public static final float hitDuration = 9.0F;

    protected static final Vec2 tmp1 = new Vec2();

    protected static final Vec2 tmp2 = new Vec2();

    public static final float warpDst = 30.0F;

    protected transient boolean added;

    protected transient Bits applied = new Bits(content.getBy(ContentType.status).size);

    protected transient float buildCounter;

    protected UnitController controller;

    @Annotations.ReadOnly
    protected transient boolean isRotate;

    protected transient BuildPlan lastActive;

    protected transient int lastSize;

    protected transient float resupplyTime = Mathf.random(10.0F);

    private transient float rotation_LAST_;

    private transient float rotation_TARGET_;

    protected Seq<StatusEntry> statuses = new Seq<>();

    protected transient Trail tleft = new Trail(1);

    protected transient Color trailColor = Blocks.water.mapColor.cpy().mul(1.5F);

    protected transient Trail tright = new Trail(1);

    protected transient boolean wasFlying;

    protected transient boolean wasHealed;

    protected transient boolean wasPlayer;

    private transient float x_LAST_;

    private transient float x_TARGET_;

    private transient float y_LAST_;

    private transient float y_TARGET_;

    protected WaterMovePayLoadUnit() {
    }

    @Override
    public boolean isRotate() {
        return isRotate;
    }

    @Override
    public int classId() {
        return 117;
    }

    @Annotations.CallSuper
    public void read(Reads read) {
        if (fix) {
            super.read(read);
        }
        else {
            short REV = read.s();
            if (REV == 0) {
                this.ammo = read.f();
                read.f();
                this.controller = mindustry.io.TypeIO.readController(read, this.controller);
                read.bool();
                this.elevation = read.f();
                this.health = read.f();
                this.isShooting = read.bool();
                this.mounts = mindustry.io.TypeIO.readMounts(read, this.mounts);
                this.rotation = read.f();
                this.shield = read.f();
                this.spawnedByCore = read.bool();
                this.stack = mindustry.io.TypeIO.readItems(read, this.stack);
                int statuses_LENGTH = read.i();
                this.statuses.clear();
                for (int INDEX = 0; INDEX < statuses_LENGTH; INDEX++) {
                    mindustry.entities.units.StatusEntry statuses_ITEM = mindustry.io.TypeIO.readStatus(read);
                    if (statuses_ITEM != null) this.statuses.add(statuses_ITEM);
                }
                this.team = mindustry.io.TypeIO.readTeam(read);
                this.type = mindustry.Vars.content.getByID(mindustry.ctype.ContentType.unit, read.s());
                this.x = read.f();
                this.y = read.f();
            } else if (REV == 1) {
                this.ammo = read.f();
                read.f();
                this.controller = mindustry.io.TypeIO.readController(read, this.controller);
                this.elevation = read.f();
                this.health = read.f();
                this.isShooting = read.bool();
                this.mounts = mindustry.io.TypeIO.readMounts(read, this.mounts);
                this.rotation = read.f();
                this.shield = read.f();
                this.spawnedByCore = read.bool();
                this.stack = mindustry.io.TypeIO.readItems(read, this.stack);
                int statuses_LENGTH = read.i();
                this.statuses.clear();
                for (int INDEX = 0; INDEX < statuses_LENGTH; INDEX++) {
                    mindustry.entities.units.StatusEntry statuses_ITEM = mindustry.io.TypeIO.readStatus(read);
                    if (statuses_ITEM != null) this.statuses.add(statuses_ITEM);
                }
                this.team = mindustry.io.TypeIO.readTeam(read);
                this.type = mindustry.Vars.content.getByID(mindustry.ctype.ContentType.unit, read.s());
                this.x = read.f();
                this.y = read.f();
            } else if (REV == 2) {
                this.ammo = read.f();
                read.f();
                this.controller = mindustry.io.TypeIO.readController(read, this.controller);
                this.elevation = read.f();
                this.flag = read.d();
                this.health = read.f();
                this.isShooting = read.bool();
                this.mounts = mindustry.io.TypeIO.readMounts(read, this.mounts);
                this.rotation = read.f();
                this.shield = read.f();
                this.spawnedByCore = read.bool();
                this.stack = mindustry.io.TypeIO.readItems(read, this.stack);
                int statuses_LENGTH = read.i();
                this.statuses.clear();
                for (int INDEX = 0; INDEX < statuses_LENGTH; INDEX++) {
                    mindustry.entities.units.StatusEntry statuses_ITEM = mindustry.io.TypeIO.readStatus(read);
                    if (statuses_ITEM != null) this.statuses.add(statuses_ITEM);
                }
                this.team = mindustry.io.TypeIO.readTeam(read);
                this.type = mindustry.Vars.content.getByID(mindustry.ctype.ContentType.unit, read.s());
                this.x = read.f();
                this.y = read.f();
            } else if (REV == 3) {
                this.ammo = read.f();
                read.f();
                this.controller = mindustry.io.TypeIO.readController(read, this.controller);
                this.elevation = read.f();
                this.flag = read.d();
                this.health = read.f();
                this.isShooting = read.bool();
                this.mineTile = mindustry.io.TypeIO.readTile(read);
                this.mounts = mindustry.io.TypeIO.readMounts(read, this.mounts);
                this.rotation = read.f();
                this.shield = read.f();
                this.spawnedByCore = read.bool();
                this.stack = mindustry.io.TypeIO.readItems(read, this.stack);
                int statuses_LENGTH = read.i();
                this.statuses.clear();
                for (int INDEX = 0; INDEX < statuses_LENGTH; INDEX++) {
                    mindustry.entities.units.StatusEntry statuses_ITEM = mindustry.io.TypeIO.readStatus(read);
                    if (statuses_ITEM != null) this.statuses.add(statuses_ITEM);
                }
                this.team = mindustry.io.TypeIO.readTeam(read);
                this.type = mindustry.Vars.content.getByID(mindustry.ctype.ContentType.unit, read.s());
                this.x = read.f();
                this.y = read.f();
            } else if (REV == 4) {
                this.ammo = read.f();
                read.f();
                this.controller = mindustry.io.TypeIO.readController(read, this.controller);
                this.elevation = read.f();
                this.flag = read.d();
                this.health = read.f();
                this.isShooting = read.bool();
                this.mineTile = mindustry.io.TypeIO.readTile(read);
                this.mounts = mindustry.io.TypeIO.readMounts(read, this.mounts);
                this.plans = mindustry.io.TypeIO.readPlansQueue(read);
                this.rotation = read.f();
                this.shield = read.f();
                this.spawnedByCore = read.bool();
                this.stack = mindustry.io.TypeIO.readItems(read, this.stack);
                int statuses_LENGTH = read.i();
                this.statuses.clear();
                for (int INDEX = 0; INDEX < statuses_LENGTH; INDEX++) {
                    mindustry.entities.units.StatusEntry statuses_ITEM = mindustry.io.TypeIO.readStatus(read);
                    if (statuses_ITEM != null) this.statuses.add(statuses_ITEM);
                }
                this.team = mindustry.io.TypeIO.readTeam(read);
                this.type = mindustry.Vars.content.getByID(mindustry.ctype.ContentType.unit, read.s());
                this.x = read.f();
                this.y = read.f();
            } else if (REV == 5) {
                this.ammo = read.f();
                read.f();
                this.controller = mindustry.io.TypeIO.readController(read, this.controller);
                this.elevation = read.f();
                this.flag = read.d();
                this.health = read.f();
                this.isShooting = read.bool();
                this.mineTile = mindustry.io.TypeIO.readTile(read);
                this.mounts = mindustry.io.TypeIO.readMounts(read, this.mounts);
                this.plans = mindustry.io.TypeIO.readPlansQueue(read);
                this.rotation = read.f();
                this.shield = read.f();
                this.spawnedByCore = read.bool();
                this.stack = mindustry.io.TypeIO.readItems(read, this.stack);
                int statuses_LENGTH = read.i();
                this.statuses.clear();
                for (int INDEX = 0; INDEX < statuses_LENGTH; INDEX++) {
                    mindustry.entities.units.StatusEntry statuses_ITEM = mindustry.io.TypeIO.readStatus(read);
                    if (statuses_ITEM != null) this.statuses.add(statuses_ITEM);
                }
                this.team = mindustry.io.TypeIO.readTeam(read);
                this.type = mindustry.Vars.content.getByID(mindustry.ctype.ContentType.unit, read.s());
                this.updateBuilding = read.bool();
                this.x = read.f();
                this.y = read.f();
            } else if (REV == 6) {
                this.ammo = read.f();
                this.controller = mindustry.io.TypeIO.readController(read, this.controller);
                this.elevation = read.f();
                this.flag = read.d();
                this.health = read.f();
                this.isShooting = read.bool();
                this.mineTile = mindustry.io.TypeIO.readTile(read);
                this.mounts = mindustry.io.TypeIO.readMounts(read, this.mounts);
                this.plans = mindustry.io.TypeIO.readPlansQueue(read);
                this.rotation = read.f();
                this.shield = read.f();
                this.spawnedByCore = read.bool();
                this.stack = mindustry.io.TypeIO.readItems(read, this.stack);
                int statuses_LENGTH = read.i();
                this.statuses.clear();
                for (int INDEX = 0; INDEX < statuses_LENGTH; INDEX++) {
                    mindustry.entities.units.StatusEntry statuses_ITEM = mindustry.io.TypeIO.readStatus(read);
                    if (statuses_ITEM != null) this.statuses.add(statuses_ITEM);
                }
                this.team = mindustry.io.TypeIO.readTeam(read);
                this.type = mindustry.Vars.content.getByID(mindustry.ctype.ContentType.unit, read.s());
                this.updateBuilding = read.bool();
                this.vel = mindustry.io.TypeIO.readVec2(read, this.vel);
                this.x = read.f();
                this.y = read.f();
            } else if (REV == 7) {
                this.abilities = mindustry.io.TypeIO.readAbilities(read, this.abilities);
                this.ammo = read.f();
                this.controller = mindustry.io.TypeIO.readController(read, this.controller);
                this.elevation = read.f();
                this.flag = read.d();
                this.health = read.f();
                this.isShooting = read.bool();
                this.mineTile = mindustry.io.TypeIO.readTile(read);
                this.mounts = mindustry.io.TypeIO.readMounts(read, this.mounts);
                this.plans = mindustry.io.TypeIO.readPlansQueue(read);
                this.rotation = read.f();
                this.shield = read.f();
                this.spawnedByCore = read.bool();
                this.stack = mindustry.io.TypeIO.readItems(read, this.stack);
                int statuses_LENGTH = read.i();
                this.statuses.clear();
                for (int INDEX = 0; INDEX < statuses_LENGTH; INDEX++) {
                    mindustry.entities.units.StatusEntry statuses_ITEM = mindustry.io.TypeIO.readStatus(read);
                    if (statuses_ITEM != null) this.statuses.add(statuses_ITEM);
                }
                this.team = mindustry.io.TypeIO.readTeam(read);
                this.type = mindustry.Vars.content.getByID(mindustry.ctype.ContentType.unit, read.s());
                this.updateBuilding = read.bool();
                this.vel = mindustry.io.TypeIO.readVec2(read, this.vel);
                this.x = read.f();
                this.y = read.f();
            } else {
                throw new IllegalArgumentException("Unknown revision '" + REV + "' for entity type 'risso'");
            }

            afterRead();
        }
    }

    @Annotations.CallSuper
    public void write(Writes write) {
        if (fix) {
            super.write(write);
        }
        else {
            write.s(7);
            mindustry.io.TypeIO.writeAbilities(write, this.abilities);
            write.f(this.ammo);
            mindustry.io.TypeIO.writeController(write, this.controller);
            write.f(this.elevation);
            write.d(this.flag);
            write.f(this.health);
            write.bool(this.isShooting);
            mindustry.io.TypeIO.writeTile(write, this.mineTile);
            mindustry.io.TypeIO.writeMounts(write, this.mounts);
            write.i(this.plans.size);
            for (int INDEX = 0; INDEX < this.plans.size; INDEX++) {
                mindustry.io.TypeIO.writePlan(write, this.plans.get(INDEX));
            }
            write.f(this.rotation);
            write.f(this.shield);
            write.bool(this.spawnedByCore);
            mindustry.io.TypeIO.writeItems(write, this.stack);
            write.i(this.statuses.size);
            for (int INDEX = 0; INDEX < this.statuses.size; INDEX++) {
                mindustry.io.TypeIO.writeStatus(write, this.statuses.get(INDEX));
            }
            mindustry.io.TypeIO.writeTeam(write, this.team);
            write.s(this.type.id);
            write.bool(this.updateBuilding);
            mindustry.io.TypeIO.writeVec2(write, this.vel);
            write.f(this.x);
            write.f(this.y);
        }

    }

    public <T extends Entityc> T self() {

        return (T)this;
    }

    public <T> T as() {

        return (T)this;
    }

    public Building buildOn() {

        return world.buildWorld(x, y);
    }

    public Player getPlayer() {

        return isPlayer() ? (Player)controller : null;
    }

    public Color statusColor() {

        if (statuses.size == 0) {
            return Tmp.c1.set(Color.white);
        }
        float r = 1.0F;
        float g = 1.0F;
        float b = 1.0F;
        float total = 0.0F;
        for (StatusEntry entry : statuses) {
            float intensity = entry.time < 10.0F ? entry.time / 10.0F : 1.0F;
            r += entry.effect.color.r * intensity;
            g += entry.effect.color.g * intensity;
            b += entry.effect.color.b * intensity;
            total += intensity;
        }
        float count = statuses.size + total;
        return Tmp.c1.set(r / count, g / count, b / count, 1.0F);
    }

    public TextureRegion icon() {

        return type.fullIcon;
    }

    public Bits statusBits() {

        return applied;
    }

    public boolean acceptsItem(Item item) {

        return !hasItem() || item == stack.item && stack.amount + 1 <= itemCapacity();
    }

    public boolean activelyBuilding() {

        if (isBuilding()) {
            var plan = buildPlan();
            if (!state.isEditor() && plan != null && !within(plan, state.rules.infiniteResources ? Float.MAX_VALUE : type.buildRange)) {
                return false;
            }
        }
        return isBuilding() && updateBuilding;
    }

    public boolean canBuild() {

        return type.buildSpeed > 0 && buildSpeedMultiplier > 0;
    }

    public boolean canDrown() {

        return isGrounded() && !hovering && type.canDrown;
    }

    public boolean canLand() {

        return !onSolid() && Units.count(x, y, physicSize(), (f)->f != this && f.isGrounded()) == 0;
    }

    public boolean canMine() {

        return type.mineSpeed > 0 && type.mineTier >= 0;
    }

    public boolean canMine(Item item) {

        if (item == null) return false;
        return type.mineTier >= item.hardness;
    }

    public boolean canPass(int tileX, int tileY) {

        EntityCollisions.SolidPred s = solidity();
        return s == null || !s.solid(tileX, tileY);
    }

    public boolean canPassOn() {

        return canPass(tileX(), tileY());
    }

    public boolean canShoot() {

        return !disarmed && !(type.canBoost && isFlying());
    }

    public boolean cheating() {

        return team.rules().cheat;
    }

    public boolean checkTarget(boolean targetAir, boolean targetGround) {

        return (isGrounded() && targetGround) || (isFlying() && targetAir);
    }

    public boolean collides(Hitboxc other) {

        return hittable();
    }

    public boolean damaged() {

        return health < maxHealth - 0.001F;
    }

    public boolean displayable() {

        return type.hoverable;
    }

    public boolean emitWalkSound() {

        return false;
    }

    public boolean hasEffect(StatusEffect effect) {

        return applied.get(effect.id);
    }

    public boolean hasItem() {

        return stack.amount > 0;
    }

    public boolean hasWeapons() {

        return type.hasWeapons();
    }

    public boolean hittable() {

        return type.hittable(this);
    }

    public boolean inFogTo(Team viewer) {

        if (this.team == viewer || !state.rules.fog) return false;
        if (hitSize <= 16.0F) {
            return !fogControl.isVisible(viewer, x, y);
        } else {
            float trns = hitSize / 2.0F;
            for (var p : Geometry.d8) {
                if (fogControl.isVisible(viewer, x + p.x * trns, y + p.y * trns)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean inRange(Position other) {

        return within(other, type.range);
    }

    public boolean isAI() {

        return controller instanceof AIController;
    }

    public boolean isAdded() {

        return added;
    }

    public boolean isBoss() {

        return hasEffect(StatusEffects.boss);
    }

    public boolean isBuilding() {

        return plans.size != 0;
    }

    public boolean isCommandable() {

        return controller instanceof CommandAI;
    }

    public boolean isEnemy() {

        return type.isEnemy;
    }

    public boolean isFlying() {

        return elevation >= 0.09F;
    }

    public boolean isGrounded() {

        return elevation < 0.001F;
    }

    public boolean isImmune(StatusEffect effect) {

        return type.immunities.contains(effect);
    }

    public boolean isLocal() {

        return ((Object)this) == player || ((Object)this) instanceof Unitc u && u.controller() == player;
    }

    public boolean isNull() {

        return false;
    }

    public boolean isPathImpassable(int tileX, int tileY) {

        return !type.flying && world.tiles.in(tileX, tileY) && type.pathCost.getCost(team.id, pathfinder.get(tileX, tileY)) == -1;
    }

    public boolean isPlayer() {

        return controller instanceof Player;
    }

    public boolean isRemote() {

        return ((Object)this) instanceof Unitc u && u.isPlayer() && !isLocal();
    }

    public boolean isSyncHidden(Player player) {

        return !isShooting() && inFogTo(player.team());
    }

    public boolean isValid() {

        return !dead && isAdded();
    }

    public boolean mining() {

        return mineTile != null && !this.activelyBuilding();
    }

    public boolean moving() {

        return !vel.isZero(0.01F);
    }

    public boolean offloadImmediately() {

        return this.isPlayer();
    }

    public boolean onLiquid() {

        Tile tile = tileOn();
        return tile != null && tile.floor().isLiquid;
    }

    public boolean onSolid() {

        return EntityCollisions.waterSolid(tileX(), tileY());
    }

    public boolean serialize() {
        return true;
    }

    public boolean shouldSkip(BuildPlan plan, Building core) {

        if (state.rules.infiniteResources || team.rules().infiniteResources || plan.breaking || core == null || plan.isRotation(team) || (isBuilding() && !within(plans.last(), type.buildRange))) return false;
        return (plan.stuck && !core.items.has(plan.block.requirements)) || (Structs.contains(plan.block.requirements, (i)->!core.items.has(i.item, Math.min(i.amount, 15)) && Mathf.round(i.amount * state.rules.buildCostMultiplier) > 0) && !plan.initialized);
    }

    public boolean targetable(Team targeter) {

        return type.targetable(this, targeter);
    }

    public boolean validMine(Tile tile) {

        return validMine(tile, true);
    }

    public boolean validMine(Tile tile, boolean checkDst) {

        if (tile == null) return false;
        if (checkDst && !within(tile.worldx(), tile.worldy(), type.mineRange)) {
            return false;
        }
        return getMineResult(tile) != null;
    }

    public double sense(Content content) {

        if (content == stack().item) return stack().amount;
        return Float.NaN;
    }

    public double sense(LAccess sensor) {

        return switch (sensor) {
            case totalItems ->stack().amount;
            case itemCapacity ->type.itemCapacity;
            case rotation ->rotation;
            case health ->health;
            case shield ->shield;
            case maxHealth ->maxHealth;
            case ammo ->!state.rules.unitAmmo ? type.ammoCapacity : ammo;
            case ammoCapacity ->type.ammoCapacity;
            case x ->World.conv(x);
            case y ->World.conv(y);
            case dead ->dead || !isAdded() ? 1 : 0;
            case team ->team.id;
            case shooting ->isShooting() ? 1 : 0;
            case boosting ->type.canBoost && isFlying() ? 1 : 0;
            case range ->range() / tilesize;
            case shootX ->World.conv(aimX());
            case shootY ->World.conv(aimY());
            case mining ->mining() ? 1 : 0;
            case mineX ->mining() ? mineTile.x : -1;
            case mineY ->mining() ? mineTile.y : -1;
            case flag ->flag;
            case speed ->type.speed * 60.0F / tilesize;
            case controlled ->!isValid() ? 0 : controller instanceof LogicAI ? ctrlProcessor : controller instanceof Player ? ctrlPlayer : controller instanceof CommandAI command && command.hasCommand() ? ctrlCommand : 0;
            case payloadCount ->((Object)this) instanceof Payloadc pay ? pay.payloads().size : 0;
            case size ->hitSize / tilesize;
            case color ->Color.toDoubleBits(team.color.r, team.color.g, team.color.b, 1.0F);
            default ->Float.NaN;
        };
    }

    public float ammof() {

        return ammo / type.ammoCapacity;
    }

    public float bounds() {

        return hitSize * 2.0F;
    }

    public float clipSize() {

        if (isBuilding()) {
            return state.rules.infiniteResources ? Float.MAX_VALUE : Math.max(type.clipSize, type.region.width) + type.buildRange + tilesize * 4.0F;
        }
        if (mining()) {
            return type.clipSize + type.mineRange;
        }
        return type.clipSize;
    }

    public float deltaAngle() {

        return Mathf.angle(deltaX, deltaY);
    }

    public float deltaLen() {

        return Mathf.len(deltaX, deltaY);
    }

    public float floorSpeedMultiplier() {

        Floor on = isFlying() ? Blocks.air.asFloor() : floorOn();
        return (on.shallow ? 1.0F : 1.3F) * speedMultiplier;
    }

    public float getDuration(StatusEffect effect) {

        var entry = statuses.find((e)->e.effect == effect);
        return entry == null ? 0 : entry.time;
    }

    public float getX() {

        return x;
    }

    public float getY() {

        return y;
    }

    public float healthf() {

        return health / maxHealth;
    }

    public float hitSize() {

        return hitSize;
    }

    public float mass() {

        return hitSize * hitSize * Mathf.pi;
    }

    public float physicSize() {

        return hitSize * 0.7F;
    }

    public float prefRotation() {

        if (activelyBuilding() && type.rotateToBuilding) {
            return angleTo(buildPlan());
        } else if (mineTile != null) {
            return angleTo(mineTile);
        } else if (moving() && type.omniMovement) {
            return vel().angle();
        }
        return rotation;
    }

    public float range() {

        return type.maxRange;
    }

    public float speed() {

        float strafePenalty = isGrounded() || !isPlayer() ? 1.0F : Mathf.lerp(1.0F, type.strafePenalty, Angles.angleDist(vel().angle(), rotation) / 180.0F);
        float boost = Mathf.lerp(1.0F, type.canBoost ? type.boostMultiplier : 1.0F, elevation);
        return type.speed * strafePenalty * boost * floorSpeedMultiplier();
    }

    public int cap() {

        return Units.getCap(team);
    }

    public int count() {

        return team.data().countType(type);
    }

    public int itemCapacity() {

        return type.itemCapacity;
    }

    public int maxAccepted(Item item) {

        return stack.item != item && stack.amount > 0 ? 0 : itemCapacity() - stack.amount;
    }

    public int pathType() {

        return Pathfinder.costNaval;
    }

    public int tileX() {

        return World.toTile(x);
    }

    public int tileY() {

        return World.toTile(y);
    }

    public Object senseObject(LAccess sensor) {

        return switch (sensor) {
            case type ->type;
            case name ->controller instanceof Player p ? p.name : null;
            case firstItem ->stack().amount == 0 ? null : item();
            case controller ->!isValid() ? null : controller instanceof LogicAI log ? log.controller : this;
            case payloadType ->((Object)this) instanceof Payloadc pay ? (pay.payloads().isEmpty() ? null : pay.payloads().peek() instanceof UnitPayload p1 ? p1.unit.type : pay.payloads().peek() instanceof BuildPayload p2 ? p2.block() : null) : null;
            default ->noSensed;
        };
    }

    public String getControllerName() {

        if (isPlayer()) return getPlayer().name;
        if (controller instanceof LogicAI ai && ai.controller != null) return ai.controller.lastAccessed;
        return null;
    }

    public String toString() {

        return "Unit#" + id() + ":" + type;
    }

    public CommandAI command() {

        if (controller instanceof CommandAI ai) {
            return ai;
        } else {
            throw new IllegalArgumentException("Unit cannot be commanded - check isCommandable() first.");
        }
    }

    public EntityCollisions.SolidPred solidity() {

        return isFlying() ? null : EntityCollisions::waterSolid;
    }

    public BuildPlan buildPlan() {

        return plans.size == 0 ? null : plans.first();
    }

    public UnitController controller() {

        return controller;
    }

    public Item getMineResult(Tile tile) {

        if (tile == null) return null;
        Item result;
        if (type.mineFloor && tile.block() == Blocks.air) {
            result = tile.drop();
        } else if (type.mineWalls) {
            result = tile.wallDrop();
        } else {
            return null;
        }
        return canMine(result) ? result : null;
    }

    public Item item() {

        return stack.item;
    }

    public Block blockOn() {

        Tile tile = tileOn();
        return tile == null ? Blocks.air : tile.block();
    }

    public Tile tileOn() {

        return world.tileWorld(x, y);
    }

    public Floor drownFloor() {

        return canDrown() ? floorOn() : null;
    }

    public Floor floorOn() {

        Tile tile = tileOn();
        return tile == null || tile.block() != Blocks.air ? (Floor)Blocks.air : tile.floor();
    }

    public CoreBlock.CoreBuild closestCore() {

        return state.teams.closestCore(x, y, team);
    }

    public CoreBlock.CoreBuild closestEnemyCore() {

        return state.teams.closestEnemyCore(x, y, team);
    }

    public CoreBlock.CoreBuild core() {

        return team.core();
    }

    public static WaterMovePayLoadUnit create() {
        return new WaterMovePayLoadUnit();
    }
//    public int classId(){
//        return 117 ;
//    }
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

    public void addBuild(BuildPlan place) {

        addBuild(place, true);
    }

    public void addBuild(BuildPlan place, boolean tail) {

        if (!canBuild()) return;
        BuildPlan replace = null;
        for (BuildPlan plan : plans) {
            if (plan.x == place.x && plan.y == place.y) {
                replace = plan;
                break;
            }
        }
        if (replace != null) {
            plans.remove(replace);
        }
        Tile tile = world.tile(place.x, place.y);
        if (tile != null && tile.build instanceof ConstructBlock.ConstructBuild cons) {
            place.progress = cons.progress;
        }
        if (tail) {
            plans.addLast(place);
        } else {
            plans.addFirst(place);
        }
    }

    public void addItem(Item item) {

        addItem(item, 1);
    }

    public void addItem(Item item, int amount) {

        stack.amount = stack.item == item ? stack.amount + amount : amount;
        stack.item = item;
        stack.amount = Mathf.clamp(stack.amount, 0, itemCapacity());
    }

    public void afterRead() {
        builder: {

            if (plans == null) {
                plans = new Queue<>(1);
            }
        }
        entity: {

        }
        hitbox: {

            updateLastPosition();
        }
        unit: {

            afterSync();
            if (!(controller instanceof AIController ai && ai.keepState())) {
                controller(type.createController(this));
            }
        }
    }

    public void afterSync() {
        sync: {

        }
        unit: {

            setType(this.type);
            controller.unit(this);
        }
    }

    public void aim(Position pos) {

        aim(pos.getX(), pos.getY());
    }

    public void aim(float x, float y) {

        Tmp.v1.set(x, y).sub(this.x, this.y);
        if (Tmp.v1.len() < type.aimDst) Tmp.v1.setLength(type.aimDst);
        x = Tmp.v1.x + this.x;
        y = Tmp.v1.y + this.y;
        for (WeaponMount mount : mounts) {
            if (mount.weapon.controllable) {
                mount.aimX = x;
                mount.aimY = y;
            }
        }
        aimX = x;
        aimY = y;
    }

    public void aimLook(Position pos) {

        aim(pos);
        lookAt(pos);
    }

    public void aimLook(float x, float y) {

        aim(x, y);
        lookAt(x, y);
    }

    public void apply(StatusEffect effect) {

        apply(effect, 1);
    }

    public void apply(StatusEffect effect, float duration) {

        if (effect == StatusEffects.none || effect == null || isImmune(effect)) return;
        if (state.isCampaign()) {
            effect.unlock();
        }
        if (statuses.size > 0) {
            for (int i = 0; i < statuses.size; i++) {
                StatusEntry entry = statuses.get(i);
                if (entry.effect == effect) {
                    entry.time = Math.max(entry.time, duration);
                    effect.applied(this, entry.time, true);
                    return;
                } else if (entry.effect.applyTransition(this, effect, entry, duration)) {
                    return;
                }
            }
        }
        if (!effect.reactive) {
            StatusEntry entry = Pools.obtain(StatusEntry.class, StatusEntry::new);
            entry.set(effect, duration);
            statuses.add(entry);
            effect.applied(this, duration, false);
        }
    }

    public void approach(Vec2 vector) {

        vel.approachDelta(vector, type.accel * speed());
    }

    public void clampHealth() {

        health = Math.min(health, maxHealth);
    }

    public void clearBuilding() {

        plans.clear();
    }

    public void clearItem() {

        stack.amount = 0;
    }

    public void clearStatuses() {

        statuses.clear();
    }

    public void collision(Hitboxc other, float x, float y) {
        hitbox: {

        }
        unit: {

            if (other instanceof Bullet bullet) {
                controller.hit(bullet);
            }
        }
    }

    public void controlWeapons(boolean rotate, boolean shoot) {

        for (WeaponMount mount : mounts) {
            if (mount.weapon.controllable) {
                mount.rotate = rotate;
                mount.shoot = shoot;
            }
        }
        isRotate = rotate;
        isShooting = shoot;
    }

    public void controlWeapons(boolean rotateShoot) {

        controlWeapons(rotateShoot, rotateShoot);
    }

    public void controller(UnitController next) {

        this.controller = next;
        if (controller.unit() != this) controller.unit(this);
    }

    public void damage(float amount) {

        rawDamage(Damage.applyArmor(amount, armor) / healthMultiplier / Vars.state.rules.unitHealth(team));
    }

    public void damage(float amount, boolean withEffect) {

        float pre = hitTime;
        damage(amount);
        if (!withEffect) {
            hitTime = pre;
        }
    }

    public void damageContinuous(float amount) {

        damage(amount * Time.delta, hitTime <= -10 + hitDuration);
    }

    public void damageContinuousPierce(float amount) {

        damagePierce(amount * Time.delta, hitTime <= -20 + hitDuration);
    }

    public void damagePierce(float amount) {

        damagePierce(amount, true);
    }

    public void damagePierce(float amount, boolean withEffect) {

        float pre = hitTime;
        rawDamage(amount / healthMultiplier / Vars.state.rules.unitHealth(team));
        if (!withEffect) {
            hitTime = pre;
        }
    }

    public void destroy() {

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

    public void display(Table table) {

        type.display(this, table);
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

    public void drawBuildPlans() {

        Boolf<BuildPlan> skip = (plan)->plan.progress > 0.01F || (buildPlan() == plan && plan.initialized && (within(plan.x * tilesize, plan.y * tilesize, type.buildRange) || state.isEditor()));
        for (int i = 0; i < 2; i++) {
            for (BuildPlan plan : plans) {
                if (skip.get(plan)) continue;
                if (i == 0) {
                    drawPlan(plan, 1.0F);
                } else {
                    drawPlanTop(plan, 1.0F);
                }
            }
        }
        Draw.reset();
    }

    public void drawBuilding() {

        boolean active = activelyBuilding();
        if (!active && lastActive == null) return;
        Draw.z(Layer.flyingUnit);
        BuildPlan plan = active ? buildPlan() : lastActive;
        Tile tile = plan.tile();
        var core = team.core();
        if (tile == null || !within(plan, state.rules.infiniteResources ? Float.MAX_VALUE : type.buildRange)) {
            return;
        }
        if (core != null && active && !isLocal() && !(tile.block() instanceof ConstructBlock)) {
            Draw.z(Layer.plans - 1.0F);
            drawPlan(plan, 0.5F);
            drawPlanTop(plan, 0.5F);
            Draw.z(Layer.flyingUnit);
        }
        if (type.drawBuildBeam) {
            float focusLen = type.buildBeamOffset + Mathf.absin(Time.time, 3.0F, 0.6F);
            float px = x + Angles.trnsx(rotation, focusLen);
            float py = y + Angles.trnsy(rotation, focusLen);
            drawBuildingBeam(px, py);
        }
    }

    public void drawBuildingBeam(float px, float py) {

        boolean active = activelyBuilding();
        if (!active && lastActive == null) return;
        Draw.z(Layer.flyingUnit);
        BuildPlan plan = active ? buildPlan() : lastActive;
        Tile tile = world.tile(plan.x, plan.y);
        if (tile == null || !within(plan, state.rules.infiniteResources ? Float.MAX_VALUE : type.buildRange)) {
            return;
        }
        int size = plan.breaking ? active ? tile.block().size : lastSize : plan.block.size;
        float tx = plan.drawx();
        float ty = plan.drawy();
        Lines.stroke(1.0F, plan.breaking ? Pal.remove : Pal.accent);
        Draw.z(Layer.buildBeam);
        Draw.alpha(buildAlpha);
        if (!active && !(tile.build instanceof ConstructBlock.ConstructBuild)) {
            Fill.square(plan.drawx(), plan.drawy(), size * tilesize / 2.0F);
        }
        Drawf.buildBeam(px, py, tx, ty, Vars.tilesize * size / 2.0F);
        Fill.square(px, py, 1.8F + Mathf.absin(Time.time, 2.2F, 1.1F), rotation + 45);
        Draw.reset();
        Draw.z(Layer.flyingUnit);
    }

    public void drawPlan(BuildPlan plan, float alpha) {

        plan.animScale = 1.0F;
        if (plan.breaking) {
            control.input.drawBreaking(plan);
        } else {
            plan.block.drawPlan(plan, control.input.allPlans(), Build.validPlace(plan.block, team, plan.x, plan.y, plan.rotation) || control.input.planMatches(plan), alpha);
        }
    }

    public void drawPlanTop(BuildPlan plan, float alpha) {

        if (!plan.breaking) {
            Draw.reset();
            Draw.mixcol(Color.white, 0.24F + Mathf.absin(Time.globalTime, 6.0F, 0.28F));
            Draw.alpha(alpha);
            plan.block.drawPlanConfigTop(plan, plans);
        }
    }

    public void getCollisions(Cons<QuadTree> consumer) {

    }

    public void handleSyncHidden() {
        sync: {

        }
        unit: {

            remove();
            netClient.clearRemovedEntity(id);
        }
    }

    public void heal() {

        dead = false;
        health = maxHealth;
    }

    public void heal(float amount) {
        health: {

            health += amount;
            clampHealth();
        }
        unit: {

            if (health < maxHealth && amount > 0) {
                wasHealed = true;
            }
        }
    }

    public void healFract(float amount) {

        heal(amount * maxHealth);
    }

    public void hitbox(Rect rect) {

        rect.setCentered(x, y, hitSize, hitSize);
    }

    public void hitboxTile(Rect rect) {

        float size = Math.min(hitSize * 0.66F, 7.9F);
        rect.setCentered(x, y, size, size);
    }

    public void impulse(Vec2 v) {

        impulse(v.x, v.y);
    }

    public void impulse(float x, float y) {

        float mass = mass();
        vel.add(x / mass, y / mass);
    }

    public void impulseNet(Vec2 v) {

        impulse(v.x, v.y);
        if (isRemote()) {
            float mass = mass();
            move(v.x / mass, v.y / mass);
        }
    }

    public void interpolate() {
        if(lastUpdated != 0 && updateSpacing != 0) {
            float timeSinceUpdate = Time.timeSinceMillis(lastUpdated);
            float alpha = Math.min(timeSinceUpdate / updateSpacing, 2f);
            rotation = (Mathf.slerp(rotation_LAST_, rotation_TARGET_, alpha));
            x = (Mathf.lerp(x_LAST_, x_TARGET_, alpha));
            y = (Mathf.lerp(y_LAST_, y_TARGET_, alpha));
        } else if(lastUpdated != 0) {
            rotation = rotation_TARGET_;
            x = x_TARGET_;
            y = y_TARGET_;
        }

    }

    public void kill() {

        if (dead || net.client() || !type.killable) return;
        Call.unitDeath(id);
    }

    public void killed() {
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

    public void landed() {
        flying: {

        }
        unit: {

            if (type.mechLandShake > 0.0F) {
                Effect.shake(type.mechLandShake, type.mechLandShake, this);
            }
            type.landed(this);
        }
    }

    public void lookAt(Position pos) {

        lookAt(angleTo(pos));
    }

    public void lookAt(float angle) {

        rotation = Angles.moveToward(rotation, angle, type.rotateSpeed * Time.delta * speedMultiplier());
    }

    public void lookAt(float x, float y) {

        lookAt(angleTo(x, y));
    }

    public void move(Vec2 v) {

        move(v.x, v.y);
    }

    public void move(float cx, float cy) {

        EntityCollisions.SolidPred check = solidity();
        if (check != null) {
            collisions.move(this, cx, cy, check);
        } else {
            x += cx;
            y += cy;
        }
    }

    public void moveAt(Vec2 vector) {

        moveAt(vector, type.accel);
    }

    public void moveAt(Vec2 vector, float acceleration) {

        Vec2 t = tmp1.set(vector);
        tmp2.set(t).sub(vel).limit(acceleration * vector.len() * Time.delta);
        vel.add(tmp2);
    }

    public void movePref(Vec2 movement) {

        if (type.omniMovement) {
            moveAt(movement);
        } else {
            rotateMove(movement);
        }
    }

    public void rawDamage(float amount) {

        boolean hadShields = shield > 1.0E-4F;
        if (hadShields) {
            shieldAlpha = 1.0F;
        }
        float shieldDamage = Math.min(Math.max(shield, 0), amount);
        shield -= shieldDamage;
        hitTime = 1.0F;
        amount -= shieldDamage;
        if (amount > 0 && type.killable) {
            health -= amount;
            if (health <= 0 && !dead) {
                kill();
            }
            if (hadShields && shield <= 1.0E-4F) {
                Fx.unitShieldBreak.at(x, y, 0, team.color, this);
            }
        }
    }

    public void readSync(Reads read) {
        if (fix) {
            super.readSync(read);
        }
        else {
            if (lastUpdated != 0) updateSpacing = Time.timeSinceMillis(lastUpdated);
            lastUpdated = Time.millis();
            boolean islocal = isLocal();
            this.abilities = mindustry.io.TypeIO.readAbilities(read, this.abilities);
            this.ammo = read.f();
            this.controller = mindustry.io.TypeIO.readController(read, this.controller);
            if (!islocal) {
                this.elevation = read.f();
            } else {
                read.f();
            }
            this.flag = read.d();
            this.health = read.f();
            this.isShooting = read.bool();
            if (!islocal) {
                this.mineTile = mindustry.io.TypeIO.readTile(read);
            } else {
                mindustry.io.TypeIO.readTile(read);
            }
            if (!islocal) {
                this.mounts = mindustry.io.TypeIO.readMounts(read, this.mounts);
            } else {
                mindustry.io.TypeIO.readMounts(read);
            }
            if (!islocal) {
                this.plans = mindustry.io.TypeIO.readPlansQueue(read);
            } else {
                mindustry.io.TypeIO.readPlansQueue(read);
            }
            if (!islocal) {
                rotation_LAST_ = this.rotation;
                this.rotation_TARGET_ = read.f();
            } else {
                read.f();
                rotation_LAST_ = this.rotation;
                rotation_TARGET_ = this.rotation;
            }
            this.shield = read.f();
            this.spawnedByCore = read.bool();
            this.stack = mindustry.io.TypeIO.readItems(read, this.stack);
            int statuses_LENGTH = read.i();
            this.statuses.clear();
            for (int INDEX = 0; INDEX < statuses_LENGTH; INDEX++) {
                mindustry.entities.units.StatusEntry statuses_ITEM = mindustry.io.TypeIO.readStatus(read);
                if (statuses_ITEM != null) this.statuses.add(statuses_ITEM);
            }
            this.team = mindustry.io.TypeIO.readTeam(read);
            this.type = mindustry.Vars.content.getByID(mindustry.ctype.ContentType.unit, read.s());
            if (!islocal) {
                this.updateBuilding = read.bool();
            } else {
                read.bool();
            }
            if (!islocal) {
                this.vel = mindustry.io.TypeIO.readVec2(read, this.vel);
            } else {
                mindustry.io.TypeIO.readVec2(read);
            }
            if (!islocal) {
                x_LAST_ = this.x;
                this.x_TARGET_ = read.f();
            } else {
                read.f();
                x_LAST_ = this.x;
                x_TARGET_ = this.x;
            }
            if (!islocal) {
                y_LAST_ = this.y;
                this.y_TARGET_ = read.f();
            } else {
                read.f();
                y_LAST_ = this.y;
                y_TARGET_ = this.y;
            }
            afterSync();
        }
    }

    public void readSyncManual(FloatBuffer buffer) {
        if (fix) {
            super.readSyncManual(buffer);
        }
        else {
            if (lastUpdated != 0) updateSpacing = Time.timeSinceMillis(lastUpdated);
            lastUpdated = Time.millis();
            this.rotation_LAST_ = this.rotation;
            this.rotation_TARGET_ = buffer.get();
            this.x_LAST_ = this.x;
            this.x_TARGET_ = buffer.get();
            this.y_LAST_ = this.y;
            this.y_TARGET_ = buffer.get();
        }
    }

    public void remove() {
        if(added == false) return;
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

    public void removeBuild(int x, int y, boolean breaking) {

        int idx = plans.indexOf((req)->req.breaking == breaking && req.x == x && req.y == y);
        if (idx != -1) {
            plans.removeIndex(idx);
        }
    }

    public void resetController() {

        controller(type.createController(this));
    }

    public void rotateMove(Vec2 vec) {

        moveAt(Tmp.v2.trns(rotation, vec.len()));
        if (!vec.isZero()) {
            rotation = Angles.moveToward(rotation, vec.angle(), type.rotateSpeed * Time.delta);
        }
    }

    public void set(Position pos) {

        set(pos.getX(), pos.getY());
    }

    public void set(float x, float y) {

        this.x = x;
        this.y = y;
    }

    public void set(UnitType def, UnitController controller) {

        if (this.type != def) {
            setType(def);
        }
        controller(controller);
    }

    public void setProp(UnlockableContent content, double value) {

        if (content instanceof Item item) {
            stack.item = item;
            stack.amount = Mathf.clamp((int)value, 0, type.itemCapacity);
        }
    }

    public void setProp(LAccess prop, double value) {

        switch (prop) {
            case health -> {
                health = (float)Mathf.clamp(value, 0, maxHealth);
                if (health <= 0.0F && !dead) {
                    kill();
                }
            }
            case x -> x = World.unconv((float)value);
            case y -> y = World.unconv((float)value);
            case rotation -> rotation = (float)value;
            case team -> {
                if (!net.client()) {
                    Team team = Team.get((int)value);
                    if (controller instanceof Player p) {
                        p.team(team);
                    }
                    this.team = team;
                }
            }
            case flag -> flag = value;
        }
    }

    public void setProp(LAccess prop, Object value) {

        switch (prop) {
            case team -> {
                if (value instanceof Team t && !net.client()) {
                    if (controller instanceof Player p) p.team(t);
                    team = t;
                }
            }
            case payloadType -> {
                if (((Object)this) instanceof Payloadc pay && !net.client()) {
                    if (value instanceof Block b) {
                        Building build = b.newBuilding().create(b, team());
                        if (pay.canPickup(build)) pay.addPayload(new BuildPayload(build));
                    } else if (value instanceof UnitType ut) {
                        Unit unit = ut.create(team());
                        if (pay.canPickup(unit)) pay.addPayload(new UnitPayload(unit));
                    } else if (value == null && pay.payloads().size > 0) {
                        pay.payloads().pop();
                    }
                }
            }
        }
    }

    public void setType(UnitType type) {

        this.type = type;
        this.maxHealth = type.health;
        this.drag = type.drag;
        this.armor = type.armor;
        this.hitSize = type.hitSize;
        this.hovering = type.hovering;
        if (controller == null) controller(type.createController(this));
        if (mounts().length != type.weapons.size) setupWeapons(type);
        if (abilities.length != type.abilities.size) {
            abilities = new Ability[type.abilities.size];
            for (int i = 0; i < type.abilities.size; i++) {
                abilities[i] = type.abilities.get(i).copy();
            }
        }
    }

    public void setWeaponRotation(float rotation) {

        for (WeaponMount mount : mounts) {
            mount.rotation = rotation;
        }
    }

    public void setupWeapons(UnitType def) {

        mounts = new WeaponMount[def.weapons.size];
        for (int i = 0; i < mounts.length; i++) {
            mounts[i] = def.weapons.get(i).mountType.get(def.weapons.get(i));
        }
    }

    public void snapInterpolation() {
        updateSpacing = 16;
        lastUpdated = Time.millis();
        rotation_LAST_ = rotation;
        rotation_TARGET_ = rotation;
        x_LAST_ = x;
        x_TARGET_ = x;
        y_LAST_ = y;
        y_TARGET_ = y;

    }

    public void snapSync() {
        updateSpacing = 16;
        lastUpdated = Time.millis();
        rotation_LAST_ = rotation_TARGET_;
        rotation = rotation_TARGET_;
        x_LAST_ = x_TARGET_;
        x = x_TARGET_;
        y_LAST_ = y_TARGET_;
        y = y_TARGET_;

    }

    public void trns(Position pos) {

        trns(pos.getX(), pos.getY());
    }

    public void trns(float x, float y) {

        set(this.x + x, this.y + y);
    }

    public void unapply(StatusEffect effect) {

        statuses.remove((e)->{
            if (e.effect == effect) {
                Pools.free(e);
                return true;
            }
            return false;
        });
    }

    public void unloaded() {

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

    public void updateBoosting(boolean boost) {

        if (!type.canBoost || dead) return;
        elevation = Mathf.approachDelta(elevation, type.canBoost ? Mathf.num(boost || onSolid() || (isFlying() && !canLand())) : 0.0F, type.riseSpeed);
    }

    public void updateBuildLogic() {

        if (type.buildSpeed <= 0.0F) return;
        if (!headless) {
            if (lastActive != null && buildAlpha <= 0.01F) {
                lastActive = null;
            }
            buildAlpha = Mathf.lerpDelta(buildAlpha, activelyBuilding() ? 1.0F : 0.0F, 0.15F);
        }
        if (!updateBuilding || !canBuild()) {
            validatePlans();
            return;
        }
        float finalPlaceDst = state.rules.infiniteResources ? Float.MAX_VALUE : type.buildRange;
        boolean infinite = state.rules.infiniteResources || team().rules().infiniteResources;
        buildCounter += Time.delta;
        if (Float.isNaN(buildCounter) || Float.isInfinite(buildCounter)) buildCounter = 0.0F;
        buildCounter = Math.min(buildCounter, 10.0F);
        int maxPerFrame = 10;
        int count = 0;
        while (buildCounter >= 1 && count++ < maxPerFrame) {
            buildCounter -= 1.0F;
            validatePlans();
            var core = core();
            if (buildPlan() == null) return;
            if (plans.size > 1) {
                int total = 0;
                int size = plans.size;
                BuildPlan plan;
                while ((!within((plan = buildPlan()).tile(), finalPlaceDst) || shouldSkip(plan, core)) && total < size) {
                    plans.removeFirst();
                    plans.addLast(plan);
                    total++;
                }
            }
            BuildPlan current = buildPlan();
            Tile tile = current.tile();
            lastActive = current;
            buildAlpha = 1.0F;
            if (current.breaking) lastSize = tile.block().size;
            if (!within(tile, finalPlaceDst)) continue;
            if (!headless) {
                Vars.control.sound.loop(Sounds.build, tile, 0.15F);
            }
            if (!(tile.build instanceof ConstructBlock.ConstructBuild cb)) {
                if (!current.initialized && !current.breaking && Build.validPlace(current.block, team, current.x, current.y, current.rotation)) {
                    boolean hasAll = infinite || current.isRotation(team) || !Structs.contains(current.block.requirements, (i)->core != null && !core.items.has(i.item, Math.min(Mathf.round(i.amount * state.rules.buildCostMultiplier), 1)));
                    if (hasAll) {
                        Call.beginPlace(this, current.block, team, current.x, current.y, current.rotation);
                    } else {
                        current.stuck = true;
                    }
                } else if (!current.initialized && current.breaking && Build.validBreak(team, current.x, current.y)) {
                    Call.beginBreak(this, team, current.x, current.y);
                } else {
                    plans.removeFirst();
                    continue;
                }
            } else if ((tile.team() != team && tile.team() != Team.derelict) || (!current.breaking && (cb.current != current.block || cb.tile != current.tile()))) {
                plans.removeFirst();
                continue;
            }
            if (tile.build instanceof ConstructBlock.ConstructBuild && !current.initialized) {
                Events.fire(new EventType.BuildSelectEvent(tile, team, this, current.breaking));
                current.initialized = true;
            }
            if ((core == null && !infinite) || !(tile.build instanceof ConstructBlock.ConstructBuild entity)) {
                continue;
            }
            float bs = 1.0F / entity.buildCost * type.buildSpeed * buildSpeedMultiplier * state.rules.buildSpeed(team);
            if (current.breaking) {
                entity.deconstruct(this, core, bs);
            } else {
                entity.construct(this, core, bs, current.config);
            }
            current.stuck = Mathf.equal(current.progress, entity.progress);
            current.progress = entity.progress;
        }
    }

    public void updateDrowning() {

        Floor floor = drownFloor();
        if (floor != null && floor.isLiquid && floor.drownTime > 0) {
            lastDrownFloor = floor;
            drownTime += Time.delta / floor.drownTime / type.drownTimeMultiplier;
            if (Mathf.chanceDelta(0.05F)) {
                floor.drownUpdateEffect.at(x, y, hitSize, floor.mapColor);
            }
            if (drownTime >= 0.999F && !net.client()) {
                kill();
                Events.fire(new EventType.UnitDrownEvent(this));
            }
        } else {
            drownTime -= Time.delta / 50.0F;
        }
        drownTime = Mathf.clamp(drownTime);
    }

    public void updateLastPosition() {

        deltaX = x - lastX;
        deltaY = y - lastY;
        lastX = x;
        lastY = y;
    }

    public void validatePlans() {

        if (plans.size > 0) {
            Iterator<BuildPlan> it = plans.iterator();
            while (it.hasNext()) {
                BuildPlan plan = it.next();
                Tile tile = world.tile(plan.x, plan.y);
                if (tile == null || (plan.breaking && tile.block() == Blocks.air) || (!plan.breaking && ((tile.build != null && tile.build.rotation == plan.rotation) || !plan.block.rotate) && (tile.block() == plan.block || (plan.block != null && (plan.block.isOverlay() && plan.block == tile.overlay() || (plan.block.isFloor() && plan.block == tile.floor())))))) {
                    it.remove();
                }
            }
        }
    }

    public void velAddNet(Vec2 v) {

        vel.add(v);
        if (isRemote()) {
            x += v.x;
            y += v.y;
        }
    }

    public void velAddNet(float vx, float vy) {

        vel.add(vx, vy);
        if (isRemote()) {
            x += vx;
            y += vy;
        }
    }

    public void wobble() {

        x += Mathf.sin(Time.time + (id() % 10) * 12, 25.0F, 0.05F) * Time.delta * elevation;
        y += Mathf.cos(Time.time + (id() % 10) * 12, 25.0F, 0.05F) * Time.delta * elevation;
    }

    public void writeSync(Writes write) {
        if (fix) {
            super.writeSync(write);
        }
        else {
            mindustry.io.TypeIO.writeAbilities(write, this.abilities);
            write.f(this.ammo);
            mindustry.io.TypeIO.writeController(write, this.controller);
            write.f(this.elevation);
            write.d(this.flag);
            write.f(this.health);
            write.bool(this.isShooting);
            mindustry.io.TypeIO.writeTile(write, this.mineTile);
            mindustry.io.TypeIO.writeMounts(write, this.mounts);
            mindustry.io.TypeIO.writePlansQueueNet(write, this.plans);
            write.f(this.rotation);
            write.f(this.shield);
            write.bool(this.spawnedByCore);
            mindustry.io.TypeIO.writeItems(write, this.stack);
            write.i(this.statuses.size);
            for (int INDEX = 0; INDEX < this.statuses.size; INDEX++) {
                mindustry.io.TypeIO.writeStatus(write, this.statuses.get(INDEX));
            }
            mindustry.io.TypeIO.writeTeam(write, this.team);
            write.s(this.type.id);
            write.bool(this.updateBuilding);
            mindustry.io.TypeIO.writeVec2(write, this.vel);
            write.f(this.x);
            write.f(this.y);
        }
    }

    public void writeSyncManual(FloatBuffer buffer) {
        if (fix) {
            super.writeSyncManual(buffer);
        }
        else{
            buffer.put(this.rotation);
            buffer.put(this.x);
            buffer.put(this.y);
        }
    }
}
