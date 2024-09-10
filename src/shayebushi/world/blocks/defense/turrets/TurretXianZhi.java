package shayebushi.world.blocks.defense.turrets;

import arc.Events;
import arc.func.Floatp;
import arc.func.Prov;
import arc.graphics.Color;
import arc.math.*;
import arc.math.geom.Vec2;
import arc.struct.*;
import arc.util.*;
//import com.sun.jdi.Value;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.audio.SoundLoop;
import mindustry.content.UnitTypes;
import mindustry.core.World;
import mindustry.entities.Effect;
import mindustry.entities.Mover;
import mindustry.entities.Predict;
import mindustry.entities.Units;
import mindustry.entities.bullet.*;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.logic.LAccess;
import mindustry.type.*;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Build;
import mindustry.world.Tile;
import mindustry.world.blocks.ControlBlock;
import mindustry.world.blocks.defense.turrets.LaserTurret;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;
import mindustry.world.modules.ItemModule;
import mindustry.world.modules.LiquidModule;
import mindustry.world.modules.PowerModule;
import shayebushi.SYBSBlocks;
import shayebushi.SYBSStats;
import shayebushi.world.blocks.XianZhic;

//import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import static mindustry.Vars.*;

/** A turret that fires a continuous beam with a delay between shots. Liquid coolant is required. Yes, this class name is awful. */
public class TurretXianZhi extends Turret implements XianZhic {
    public float firingMoveFract = 0.25f;
    //public float shootDuration = 100f;
    public int limitPlaceOnCount = 10 ;
    public int countt ;
//    public TurretXianZhiBuilding I() {
//         return TurretXianZhiBuilding.create(this, Team.sharded);
//    }
    public TurretXianZhi(String name){
        super(name);
        TurretXianZhi thiss = this ;
//        this.buildType = new Prov<Building>() {
//            @Override
//            public Building get() {
//                if (thiss instanceof LaserTurretXianZhi) {
//                    return LaserTurretXianZhi.LaserTurretXianZhiBuild.create();
//                }
//                else{
//                    return ItemTurretXianZhi.ItemTurretXianZhiBuild.create() ;
//                }
//            }
//        };
        //coolantMultiplier = 1f;
    }

    @Override
    public void setBars(){
        super.setBars();
        setBarsXianZhi();
    }
    @Override
    public int limitPlaceOnCount() {
        return limitPlaceOnCount ;
    }
    @Override
    public int countt() {
        return countt ;
    }
    @Override
    public void countt(int c) {
        countt = c ;
    }
    @Override
    public void init(){
        super.init();
    }
    @Override
    public boolean canPlaceOn(Tile t, Team te, int r) {
        return canPlaceOnXianZhi(t, te, r) ;
    }
    @Override
    public void setStats() {
        super.setStats() ;
        setStatsXianZhi();
    }
/**    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        if(tile == null) return false;
        if(state.isEditor()) return true;
        //System.out.println(tile.block().localizedName);
        if (tile.build instanceof TurretXianZhiBuild || true){
            Seq<Building> buildings = new Seq<>();
            Seq<Building> cefans = new Seq<>() ;
            Seq<Building> jingus = new Seq<>() ;
            Seq<Building> jizengs = new Seq<>() ;
            int count = 0 ;
            int counttt = 0 ;
            int countttt = 0 ;
//            int II = -114 ;
//            int III = -114 ;
//            int IV = -114 ;
//            int V = -114 ;
//            int VI = -114 ;
//            int VII = -114 ;
//            int IX = -114 ;
            World world = Vars.world ;
            Seq<Building> buildings1 = new Seq<>() ;
            for (int x = -world.width() ; x < world.width() ; x ++){
                for (int y = -world.height() ; y < world.height() ; y ++){
                    Building building = world.build(x,y);
//                    if (building != null) {
//                        System.out.println(building.team);
//                    }
                    if (building != null &&building.block.size != 1 && !(buildings1.contains(building)) && building.team == player.team()){
                        buildings.add(building) ;
                        buildings1.add(building) ;

//                        if (II != -114) {
//                            x += 2;
//                            buildings.add(building);
//                            II = -114 ;
//                        }
//                        else {
//                            II = x+y ;
//                        }
                    }
                    else if (building!= null && building.block.size == 1 && building.team == player.team()){
                        buildings.add(building);
                    }
                }
            }
            //System.out.println(world.height());
            //System.out.println(buildings.size);
            if (!buildings.isEmpty()) {
                for (Building z : buildings) {
//                    System.out.println(z.getDisplayName());
//                    System.out.println(z.getDisplayName().equals("禁锢"));
//                    System.out.println(z.getDisplayName().equals("禁锢") && z.team == Team.derelict);
                    System.out.println(z.block == SYBSBlocks.jingu);
                    System.out.println(z.block.localizedName);
                    if (z.block == SYBSBlocks.jingu) {
                        //count++;
                        jingus.add(z) ;
                    }
                    else if (z.block == SYBSBlocks.jizeng) {
                        //counttt++;
                        jizengs.add(z) ;
                    }
                    else if (z.block == SYBSBlocks.cefan) {
                        //countttt++;
                        cefans.add(z) ;
                    }
                }
            }
            else {
                return true ;
            }
            //System.out.println(count);
            //System.out.println(counttt);
            //System.out.println(countttt);
            //System.out.println(counttt);
            //System.out.println(this.localizedName);
            if (this == SYBSBlocks.jingu){
                this.countt = cefans.size ;
            }
            else if (this == SYBSBlocks.jizeng){
                this.countt = jingus.size ;
            }
            else if (this == SYBSBlocks.cefan){
                this.countt = jizengs.size ;
            }
//            System.out.println(countt);
            if (this.countt < this.limitPlaceOnCount){
                return true ;
            }
        }
        return false ;
    }
*/



    public class TurretXianZhiBuild extends TurretBuild implements ControlBlock {
        //TODO storing these as instance variables is horrible design
        /** Turret sprite offset, based on recoil. Updated every frame. */
        public Vec2 recoilOffset = new Vec2();

        public Seq<AmmoEntry> ammo = new Seq<>();
        public int totalAmmo;
        public float curRecoil, heat, logicControlTime = -1;
        public @Nullable float[] curRecoils;
        public float shootWarmup, charge, warmupHold = 0f;
        public int totalShots, barrelCounter;
        public boolean logicShooting = false;
        public @Nullable Posc target;
        public Vec2 targetPos = new Vec2();
        public BlockUnitc unit = (BlockUnitc) UnitTypes.block.create(team);
        public boolean wasShooting;
        public int queuedBullets = 0;

        public float heatReq;
        public float[] sideHeat = new float[4];

        @Override
        public float estimateDps(){
            if(!hasAmmo()) return 0f;
            return shoot.shots / reload * 60f * (peekAmmo() == null ? 0f : peekAmmo().estimateDPS()) * potentialEfficiency * timeScale;
        }

        @Override
        public float range(){
            if(peekAmmo() != null){
                return range + peekAmmo().rangeChange;
            }
            return range;
        }

        @Override
        public float warmup(){
            return shootWarmup;
        }

        @Override
        public float drawrot(){
            return rotation - 90;
        }

        @Override
        public boolean shouldConsume(){
            return isShooting() || reloadCounter < reload;
        }

        @Override
        public boolean canControl(){
            return playerControllable;
        }

        @Override
        public void control(LAccess type, double p1, double p2, double p3, double p4){
            if(type == LAccess.shoot && !unit.isPlayer()){
                targetPos.set(World.unconv((float)p1), World.unconv((float)p2));
                logicControlTime = logicControlCooldown;
                logicShooting = !Mathf.zero(p3);
            }

            super.control(type, p1, p2, p3, p4);
        }

        @Override
        public void control(LAccess type, Object p1, double p2, double p3, double p4){
            if(type == LAccess.shootp && (unit == null || !unit.isPlayer())){
                logicControlTime = logicControlCooldown;
                logicShooting = !Mathf.zero(p2);

                if(p1 instanceof Posc pos){
                    targetPosition(pos);
                }
            }

            super.control(type, p1, p2, p3, p4);
        }

        @Override
        public double sense(LAccess sensor){
            return switch(sensor){
                case ammo -> totalAmmo;
                case ammoCapacity -> maxAmmo;
                case rotation -> rotation;
                case shootX -> World.conv(targetPos.x);
                case shootY -> World.conv(targetPos.y);
                case shooting -> isShooting() ? 1 : 0;
                case progress -> progress();
                default -> super.sense(sensor);
            };
        }

        @Override
        public float progress(){
            return Mathf.clamp(reloadCounter / reload);
        }

        public boolean isShooting(){
            return alwaysShooting || (isControlled() ? unit.isShooting() : logicControlled() ? logicShooting : target != null);
        }

        @Override
        public Unit unit(){
            //make sure stats are correct
            unit.tile(this);
            unit.team(team);
            return (Unit)unit;
        }

        public boolean logicControlled(){
            return logicControlTime > 0;
        }

        public boolean isActive(){
            return (target != null || wasShooting) && enabled;
        }

        public void targetPosition(Posc pos){
            if(!hasAmmo() || pos == null) return;
            BulletType bullet = peekAmmo();

            var offset = Tmp.v1.setZero();

            //when delay is accurate, assume unit has moved by chargeTime already
            if(accurateDelay && !moveWhileCharging && pos instanceof Hitboxc h){
                offset.set(h.deltaX(), h.deltaY()).scl(shoot.firstShotDelay / Time.delta);
            }

            if(predictTarget){
                targetPos.set(Predict.intercept(this, pos, offset.x, offset.y, bullet.speed <= 0.01f ? 99999999f : bullet.speed));
            }else{
                targetPos.set(pos);
            }

            if(targetPos.isZero()){
                targetPos.set(pos);
            }
        }

        @Override
        public void draw(){
            drawer.draw(this);
        }

        @Override
        public void updateTile(){
            //if (countt > limitPlaceOnCount) kill();
            if(!validateTarget()) target = null;

            float warmupTarget = (isShooting() && canConsume()) || charging() ? 1f : 0f;
            if(warmupTarget > 0 && shootWarmup >= minWarmup && !isControlled()){
                warmupHold = 1f;
            }
            if(warmupHold > 0f){
                warmupHold -= Time.delta / warmupMaintainTime;
                warmupTarget = 1f;
            }

            if(linearWarmup){
                shootWarmup = Mathf.approachDelta(shootWarmup, warmupTarget, shootWarmupSpeed * (warmupTarget > 0 ? efficiency : 1f));
            }else{
                shootWarmup = Mathf.lerpDelta(shootWarmup, warmupTarget, shootWarmupSpeed * (warmupTarget > 0 ? efficiency : 1f));
            }

            wasShooting = false;

            curRecoil = Mathf.approachDelta(curRecoil, 0, 1 / recoilTime);
            if(recoils > 0){
                if(curRecoils == null) curRecoils = new float[recoils];
                for(int i = 0; i < recoils; i++){
                    curRecoils[i] = Mathf.approachDelta(curRecoils[i], 0, 1 / recoilTime);
                }
            }
            heat = Mathf.approachDelta(heat, 0, 1 / cooldownTime);
            charge = charging() ? Mathf.approachDelta(charge, 1, 1 / shoot.firstShotDelay) : 0;

            unit.tile(this);
            unit.rotation(rotation);
            unit.team(team);
            recoilOffset.trns(rotation, -Mathf.pow(curRecoil, recoilPow) * recoil);

            if(logicControlTime > 0){
                logicControlTime -= Time.delta;
            }

            if(heatRequirement > 0){
                heatReq = calculateHeat(sideHeat);
            }

            //turret always reloads regardless of whether it's targeting something
            updateReload();

            if(hasAmmo()){
                if(Float.isNaN(reloadCounter)) reloadCounter = 0;

                if(timer(timerTarget, targetInterval)){
                    findTarget();
                }

                if(validateTarget()){
                    boolean canShoot = true;

                    if(isControlled()){ //player behavior
                        targetPos.set(unit.aimX(), unit.aimY());
                        canShoot = unit.isShooting();
                    }else if(logicControlled()){ //logic behavior
                        canShoot = logicShooting;
                    }else{ //default AI behavior
                        targetPosition(target);

                        if(Float.isNaN(rotation)) rotation = 0;
                    }

                    if(!isControlled()){
                        unit.aimX(targetPos.x);
                        unit.aimY(targetPos.y);
                    }

                    float targetRot = angleTo(targetPos);

                    if(shouldTurn()){
                        turnToTarget(targetRot);
                    }

                    if(!alwaysShooting && Angles.angleDist(rotation, targetRot) < shootCone && canShoot){
                        wasShooting = true;
                        updateShooting();
                    }
                }

                if(alwaysShooting){
                    wasShooting = true;
                    updateShooting();
                }
            }

            if(coolant != null){
                updateCooling();
            }
        }

        @Override
        public void handleLiquid(Building source, Liquid liquid, float amount){
            if(coolant != null && liquids.currentAmount() <= 0.001f){
                Events.fire(EventType.Trigger.turretCool);
            }

            super.handleLiquid(source, liquid, amount);
        }

        protected boolean validateTarget(){
            return !Units.invalidateTarget(target, canHeal() ? Team.derelict : team, x, y) || isControlled() || logicControlled();
        }

        protected boolean canHeal(){
            return targetHealing && hasAmmo() && peekAmmo().collidesTeam && peekAmmo().heals();
        }

        protected void findTarget(){
            float range = range();

            if(targetAir && !targetGround){
                target = Units.bestEnemy(team, x, y, range, e -> !e.dead() && !e.isGrounded() && unitFilter.get(e), unitSort);
            }else{
                target = Units.bestTarget(team, x, y, range, e -> !e.dead() && unitFilter.get(e) && (e.isGrounded() || targetAir) && (!e.isGrounded() || targetGround), b -> targetGround && buildingFilter.get(b), unitSort);
            }

            if(target == null && canHeal()){
                target = Units.findAllyTile(team, x, y, range, b -> b.damaged() && b != this);
            }
        }

        protected void turnToTarget(float targetRot){
            rotation = Angles.moveToward(rotation, targetRot, rotateSpeed * delta() * potentialEfficiency);
        }

        public boolean shouldTurn(){
            return moveWhileCharging || !charging();
        }

        @Override
        public void updateEfficiencyMultiplier(){
            if(heatRequirement > 0){
                efficiency *= Math.min(Math.max(heatReq / heatRequirement, cheating() ? 1f : 0f), maxHeatEfficiency);
            }
        }

        /** Consume ammo and return a type. */
        public BulletType useAmmo(){
            if(cheating()) return peekAmmo();

            AmmoEntry entry = ammo.peek();
            entry.amount -= ammoPerShot;
            if(entry.amount <= 0) ammo.pop();
            totalAmmo -= ammoPerShot;
            totalAmmo = Math.max(totalAmmo, 0);
            return entry.type();
        }

        /** @return the ammo type that will be returned if useAmmo is called. */
        public @Nullable BulletType peekAmmo(){
            return ammo.size == 0 ? null : ammo.peek().type();
        }

        /** @return whether the turret has ammo. */
        public boolean hasAmmo(){
            //used for "side-ammo" like gas in some turrets
            if(!canConsume()) return false;

            //skip first entry if it has less than the required amount of ammo
            if(ammo.size >= 2 && ammo.peek().amount < ammoPerShot && ammo.get(ammo.size - 2).amount >= ammoPerShot){
                ammo.swap(ammo.size - 1, ammo.size - 2);
            }

            return ammo.size > 0 && (ammo.peek().amount >= ammoPerShot || cheating());
        }

        public boolean charging(){
            return queuedBullets > 0 && shoot.firstShotDelay > 0;
        }

        protected void updateReload(){
            float multiplier = hasAmmo() ? peekAmmo().reloadMultiplier : 1f;
            reloadCounter += delta() * multiplier * baseReloadSpeed();

            //cap reload for visual reasons
            reloadCounter = Math.min(reloadCounter, reload);
        }

        protected void updateShooting(){

            if(reloadCounter >= reload && !charging() && shootWarmup >= minWarmup){
                BulletType type = peekAmmo();

                shoot(type);

                reloadCounter %= reload;
            }
        }

        protected void shoot(BulletType type){
            float
                    bulletX = x + Angles.trnsx(rotation - 90, shootX, shootY),
                    bulletY = y + Angles.trnsy(rotation - 90, shootX, shootY);

            if(shoot.firstShotDelay > 0){
                chargeSound.at(bulletX, bulletY, Mathf.random(soundPitchMin, soundPitchMax));
                type.chargeEffect.at(bulletX, bulletY, rotation);
            }

            shoot.shoot(barrelCounter, (xOffset, yOffset, angle, delay, mover) -> {
                queuedBullets++;
                if(delay > 0f){
                    Time.run(delay, () -> bullet(type, xOffset, yOffset, angle, mover));
                }else{
                    bullet(type, xOffset, yOffset, angle, mover);
                }
            }, () -> barrelCounter++);

            if(consumeAmmoOnce){
                useAmmo();
            }
        }

        protected void bullet(BulletType type, float xOffset, float yOffset, float angleOffset, Mover mover){
            queuedBullets --;

            if(dead || (!consumeAmmoOnce && !hasAmmo())) return;

            float
                    xSpread = Mathf.range(xRand),
                    bulletX = x + Angles.trnsx(rotation - 90, shootX + xOffset + xSpread, shootY + yOffset),
                    bulletY = y + Angles.trnsy(rotation - 90, shootX + xOffset + xSpread, shootY + yOffset),
                    shootAngle = rotation + angleOffset + Mathf.range(inaccuracy + type.inaccuracy);

            float lifeScl = type.scaleLife ? Mathf.clamp(Mathf.dst(bulletX, bulletY, targetPos.x, targetPos.y) / type.range, minRange / type.range, range() / type.range) : 1f;

            //TODO aimX / aimY for multi shot turrets?
            handleBullet(type.create(this, team, bulletX, bulletY, shootAngle, -1f, (1f - velocityRnd) + Mathf.random(velocityRnd), lifeScl, null, mover, targetPos.x, targetPos.y), xOffset, yOffset, shootAngle - rotation);

            (shootEffect == null ? type.shootEffect : shootEffect).at(bulletX, bulletY, rotation + angleOffset, type.hitColor);
            (smokeEffect == null ? type.smokeEffect : smokeEffect).at(bulletX, bulletY, rotation + angleOffset, type.hitColor);
            shootSound.at(bulletX, bulletY, Mathf.random(soundPitchMin, soundPitchMax));

            ammoUseEffect.at(
                    x - Angles.trnsx(rotation, ammoEjectBack),
                    y - Angles.trnsy(rotation, ammoEjectBack),
                    rotation * Mathf.sign(xOffset)
            );

            if(shake > 0){
                Effect.shake(shake, shake, this);
            }

            curRecoil = 1f;
            if(recoils > 0){
                curRecoils[barrelCounter % recoils] = 1f;
            }
            heat = 1f;
            totalShots++;

            if(!consumeAmmoOnce){
                useAmmo();
            }
        }

        protected void handleBullet(@Nullable Bullet bullet, float offsetX, float offsetY, float angleOffset){

        }

        @Override
        public float activeSoundVolume(){
            return shootWarmup;
        }

        @Override
        public boolean shouldActiveSound(){
            return shootWarmup > 0.01f && loopSound != Sounds.none;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(reloadCounter);
            write.f(rotation);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            if(revision >= 1){
                reloadCounter = read.f();
                rotation = read.f();
            }
        }

        @Override
        public byte version(){
            return 1;
        }
    }
    public static class BulletEntry{
        public Bullet bullet;
        public float x, y, rotation, life;

        public BulletEntry(Bullet bullet, float x, float y, float rotation, float life){
            this.bullet = bullet;
            this.x = x;
            this.y = y;
            this.rotation = rotation;
            this.life = life;
        }
    }
    public class TurretXianZhiBuilding extends Building implements Floatp{
        public float Limlt(){
            System.out.println(countt / (float)limitPlaceOnCount);
            return countt / (float)limitPlaceOnCount ;
        }
        @Override
        public float get() {
            return countt;
        }
        public TurretXianZhiBuilding(Block block , Team team){

            this.block = block;
            this.team = team;
            if (block.loopSound != Sounds.none) {
                sound = new SoundLoop(block.loopSound, block.loopSoundVolume);
            }
            health = block.health;
            maxHealth(block.health);
            timer(new Interval(block.timers));
            if (block.hasItems) items = new ItemModule();
            if (block.hasLiquids) liquids = new LiquidModule();
            if (block.hasPower) {
                power = new PowerModule();
                power.graph.add(this);
            }
            initialized = true;
        }
//        @Override
//        public static TurretXianZhiBuilding createe(Block block, Team team) {
//            return (TurretXianZhiBuilding)(super.create(block, team));
//        }
    }
}
