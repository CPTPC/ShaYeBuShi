//package shayebushi.world.blocks.defense.turrets;
//
//import arc.math.Angles;
//import arc.math.Mathf;
//import arc.scene.ui.Image;
//import arc.scene.ui.layout.Table;
//import arc.struct.ObjectMap;
//import arc.struct.OrderedMap;
//import arc.util.Time;
//import mindustry.Vars;
//import mindustry.entities.Effect;
//import mindustry.entities.Mover;
//import mindustry.entities.bullet.BulletType;
//import mindustry.entities.part.DrawPart;
//import mindustry.gen.Building;
//import mindustry.graphics.Pal;
//import mindustry.type.Item;
//import mindustry.ui.Bar;
//import mindustry.ui.MultiReqImage;
//import mindustry.ui.ReqImage;
//import mindustry.world.blocks.defense.turrets.ItemTurret;
//import mindustry.world.blocks.defense.turrets.Turret;
//import mindustry.world.consumers.ConsumeItemFilter;
//import mindustry.world.draw.DrawBlock;
//import mindustry.world.draw.DrawTurret;
//import mindustry.world.meta.Stat;
//import mindustry.world.meta.StatValues;
//import mindustry.world.meta.Stats;
//import shayebushi.world.draw.DrawPartTurret;
//
//import static mindustry.Vars.content;
//
//public class PartTurret extends Turret implements Cloneable{
//    public float xo = 0, yo = 0 ;
//    public ObjectMap<Item, BulletType> ammoTypes = new OrderedMap<>();
//    public PartTurret(String name) {
//        super(name);
//        drawer = new DrawPartTurret();
//    }
////    public PartTurret(Turret t, float xOffset, float yOffset){
////        xo = xOffset ;
////        yo = yOffset ;
////    }
//    public PartTurret copy(){
//        try{
//            PartTurret copy = (PartTurret) clone();
//            copy.id = (short) Vars.content.getBy(getContentType()).size;
//            Vars.content.handleContent(copy);
//            return copy;
//        }catch(Exception e){
//            throw new RuntimeException("death to checked exceptions", e);
//        }
//    }
//    @Override
//    public void setBars(){
//        super.setBars();
//
//        addBar("ammo", (PartTurretBuild entity) ->
//                new Bar(
//                        "stat.ammo",
//                        Pal.ammo,
//                        () -> (float)entity.totalAmmo / maxAmmo
//                )
//        );
//    }
//    @Override
//    public void setStats(){
//        super.setStats();
//
//        stats.remove(Stat.itemCapacity);
//        stats.add(Stat.ammo, StatValues.ammo(ammoTypes));
//    }
//    @Override
//    public void init(){
//        consume(new ConsumeItemFilter(i -> ammoTypes.containsKey(i)){
//            @Override
//            public void build(Building build, Table table){
//                MultiReqImage image = new MultiReqImage();
//                content.items().each(i -> filter.get(i) && i.unlockedNow(),
//                        item -> image.add(new ReqImage(new Image(item.uiIcon),
//                                () -> build instanceof PartTurretBuild it && !it.ammo.isEmpty() && ((ItemTurret.ItemEntry)it.ammo.peek()).item == item)));
//
//                table.add(image).size(8 * 4);
//            }
//
//            @Override
//            public float efficiency(Building build){
//                //valid when there's any ammo in the turret
//                return build instanceof PartTurretBuild it && !it.ammo.isEmpty() ? 1f : 0f;
//            }
//
//            @Override
//            public void display(Stats stats){
//                //don't display
//            }
//        });
//
//        ammoTypes.each((item, type) -> placeOverlapRange = Math.max(placeOverlapRange, range + type.rangeChange + placeOverlapMargin));
//
//        super.init();
//    }
//
//    public class PartTurretBuild extends TurretBuild {
//        @Override
//        public void updateTile(){
//            unit.ammo((float)unit.type().ammoCapacity * totalAmmo / maxAmmo);
//            super.updateTile();
//        }
//        @Override
//        public void draw(){
//            ((DrawPartTurret)(drawer)).draw(this, xo, yo) ;
//        }
//        @Override
//        public void shoot(BulletType type){
//            float
//                    bulletX = x + xo + Angles.trnsx(rotation - 90, shootX, shootY),
//                    bulletY = y + yo + Angles.trnsy(rotation - 90, shootX, shootY);
//
//            if(shoot.firstShotDelay > 0){
//                chargeSound.at(bulletX, bulletY, Mathf.random(soundPitchMin, soundPitchMax));
//                type.chargeEffect.at(bulletX, bulletY, rotation);
//            }
//
//            shoot.shoot(barrelCounter, (xOffset, yOffset, angle, delay, mover) -> {
//                queuedBullets++;
//                if(delay > 0f){
//                    Time.run(delay, () -> bullet(type, xOffset, yOffset, angle, mover));
//                }else{
//                    bullet(type, xOffset, yOffset, angle, mover);
//                }
//            }, () -> barrelCounter++);
//
//            if(consumeAmmoOnce){
//                useAmmo();
//            }
//        }
//        @Override
//        public void bullet(BulletType type, float xOffset, float yOffset, float angleOffset, Mover mover){
//            queuedBullets --;
//
//            if(dead || (!consumeAmmoOnce && !hasAmmo())) return;
//
//            float
//                    xSpread = Mathf.range(xRand),
//                    bulletX = x + xo + Angles.trnsx(rotation - 90, shootX + xOffset + xSpread, shootY + yOffset),
//                    bulletY = y + yo + Angles.trnsy(rotation - 90, shootX + xOffset + xSpread, shootY + yOffset),
//                    shootAngle = rotation + angleOffset + Mathf.range(inaccuracy + type.inaccuracy);
//
//            float lifeScl = type.scaleLife ? Mathf.clamp(Mathf.dst(bulletX, bulletY, targetPos.x, targetPos.y) / type.range, minRange / type.range, range() / type.range) : 1f;
//
//            //TODO aimX / aimY for multi shot turrets?
//            handleBullet(type.create(this, team, bulletX, bulletY, shootAngle, -1f, (1f - velocityRnd) + Mathf.random(velocityRnd), lifeScl, null, mover, targetPos.x, targetPos.y), xOffset, yOffset, shootAngle - rotation);
//
//            (shootEffect == null ? type.shootEffect : shootEffect).at(bulletX, bulletY, rotation + angleOffset, type.hitColor);
//            (smokeEffect == null ? type.smokeEffect : smokeEffect).at(bulletX, bulletY, rotation + angleOffset, type.hitColor);
//            shootSound.at(bulletX, bulletY, Mathf.random(soundPitchMin, soundPitchMax));
//
//            ammoUseEffect.at(
//                    x - Angles.trnsx(rotation, ammoEjectBack),
//                    y - Angles.trnsy(rotation, ammoEjectBack),
//                    rotation * Mathf.sign(xOffset)
//            );
//
//            if(shake > 0){
//                Effect.shake(shake, shake, this);
//            }
//
//            curRecoil = 1f;
//            if(recoils > 0){
//                curRecoils[barrelCounter % recoils] = 1f;
//            }
//            heat = 1f;
//            totalShots++;
//
//            if(!consumeAmmoOnce){
//                useAmmo();
//            }
//        }
//
//    }
//}
