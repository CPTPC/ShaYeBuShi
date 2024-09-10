//package shayebushi.world.blocks.defense.turrets;
//
//import arc.math.Angles;
//import arc.math.Mathf;
//import arc.util.Time;
//import mindustry.Vars;
//import mindustry.entities.Effect;
//import mindustry.entities.Mover;
//import mindustry.entities.bullet.BulletType;
//import mindustry.world.blocks.defense.turrets.ItemTurret;
//import shayebushi.world.draw.DrawPartTurret;
//
//public class PartMoYanTurret extends TurretMoYan{
//    public float xo = 0, yo = 0 ;
//    public PartMoYanTurret(String name) {
//        super(name);
//        drawer = new DrawPartTurret();
//    }
//    public PartMoYanTurret copy(){
//        try{
//            PartMoYanTurret copy = (PartMoYanTurret) clone();
//            copy.id = (short) Vars.content.getBy(getContentType()).size;
//            Vars.content.handleContent(copy);
//            return copy;
//        }catch(Exception e){
//            throw new RuntimeException("death to checked exceptions", e);
//        }
//    }
//
//    public class PartMoYanTurretBuild extends TurretMoYanBuild {
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
//
//}
