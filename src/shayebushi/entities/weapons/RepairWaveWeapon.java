package shayebushi.entities.weapons;

import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.util.Time;
import mindustry.Vars;
import mindustry.core.World;
import mindustry.entities.Sized;
import mindustry.entities.Units;
import mindustry.entities.part.DrawPart;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Building;
import mindustry.gen.Healthc;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import mindustry.type.weapons.RepairBeamWeapon;
import mindustry.world.blocks.units.RepairTurret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.tilesize;

public class RepairWaveWeapon extends RepairBeamWeapon {
    public float healPer = 0.15f / 60f, angle = 50 ;
    public RepairWaveWeapon(String name) {
        super(name) ;
    }
    @Override
    public void addStats(UnitType u, Table t) {
        t.row();
        t.add("[lightgray]" + Stat.repairSpeed.localized() + ": " + (mirror ? "2x " : "") + "[white]" + (int)(healPer * 100 * 60) + "% " + StatUnit.perSecond.localized());
    }
    @Override
    public void update(Unit unit, WeaponMount mount){
        super.update(unit, mount);

        float
                weaponRotation = unit.rotation - 90,
                wx = unit.x + Angles.trnsx(weaponRotation, x, y),
                wy = unit.y + Angles.trnsy(weaponRotation, x, y);

        HealBeamMount heal = (HealBeamMount)mount;
        boolean canShoot = mount.shoot;

        if(!autoTarget){
            heal.target = null;
            if(canShoot){
                heal.lastEnd.set(heal.aimX, heal.aimY);

                if(!rotate && !Angles.within(Angles.angle(wx, wy, heal.aimX, heal.aimY), unit.rotation, shootCone)){
                    canShoot = false;
                }
            }

            //limit range
            heal.lastEnd.sub(wx, wy).limit(range()).add(wx, wy);

            if(targetBuildings){
                //snap to closest building
                World.raycastEachWorld(wx, wy, heal.lastEnd.x, heal.lastEnd.y, (x, y) -> {
                    var build = Vars.world.build(x, y);
                    if(build != null && build.team == unit.team && build.damaged()){
                        heal.target = build;
                        heal.lastEnd.set(x * tilesize, y * tilesize);
                        return true;
                    }
                    return false;
                });
            }
            if(targetUnits){
                //TODO does not support healing units manually yet
            }
        }

        heal.strength = Mathf.lerpDelta(heal.strength, Mathf.num(autoTarget ? mount.target != null : canShoot), 0.2f);

        //create heal effect periodically
        if(canShoot && mount.target instanceof Building b && b.damaged() && (heal.effectTimer += Time.delta) >= reload){
            healEffect.at(b.x, b.y, 0f, healColor, b.block);
            heal.effectTimer = 0f;
        }

        if(canShoot && mount.target instanceof Healthc u){
            float baseAmount = repairSpeed * heal.strength * Time.delta + fractionRepairSpeed * heal.strength * Time.delta * u.maxHealth() / 100f;
            if (targetUnits) {
                Units.nearby(unit.team, wx, wy, range(), u2 -> {
                    float a = Angles.angle(wx, wy, u2.x, u2.y) ;
                    float a2 = Angles.angle(wx, wy, u.x(), u.y()) ;
                    //System.out.println(a + " " + a2 + angle / 2) ;
                    if (a < a2 + angle / 2 && a > a2 - angle / 2) {
                        u2.heal((u instanceof Building b && b.wasRecentlyDamaged() ? recentDamageMultiplier : 1f) * baseAmount);
                        u2.heal(u.maxHealth() * healPer);
                    }
                });
            }
            if (targetBuildings) {
                Units.nearbyBuildings(wx, wy, range(), u2 -> {
                    if (u2.team == unit.team) {
                        float a = Angles.angle(wx, wy, u2.x, u2.y) ;
                        float a2 = Angles.angle(wx, wy, u.x(), u.y()) ;
                        //System.out.println(a + " " + a2 + angle / 2) ;
                        if (a < a2 + angle / 2 && a > a2 - angle / 2) {
                            u2.heal((u instanceof Building b && b.wasRecentlyDamaged() ? recentDamageMultiplier : 1f) * baseAmount);
                            u2.heal(u.maxHealth() * healPer);
                            healEffect.at(u2.x, u2.y, 0f, healColor, u2.block);
                            heal.effectTimer = 0f;
                        }
                    }
                });
            }
            u.heal((u instanceof Building b && b.wasRecentlyDamaged() ? recentDamageMultiplier : 1f) * baseAmount);
            u.heal(u.maxHealth() * healPer) ;
        }
    }

    @Override
    public void draw(Unit unit, WeaponMount mount){
        float z = Draw.z();
        Draw.z(z + layerOffset);

        float
                rotation = unit.rotation - 90,
                realRecoil = Mathf.pow(mount.recoil, recoilPow) * recoil,
                weaponRotation  = rotation + (rotate ? mount.rotation : baseRotation),
                wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -realRecoil),
                wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -realRecoil);

        if(shadow > 0){
            Drawf.shadow(wx, wy, shadow);
        }

        if(top){
            drawOutline(unit, mount);
        }

        if(parts.size > 0){
            DrawPart.params.set(mount.warmup, mount.reload / reload, mount.smoothReload, mount.heat, mount.recoil, mount.charge, wx, wy, weaponRotation + 90);
            DrawPart.params.sideMultiplier = flipSprite ? -1 : 1;

            for(int i = 0; i < parts.size; i++){
                var part = parts.get(i);
                DrawPart.params.setRecoil(part.recoilIndex >= 0 && mount.recoils != null ? mount.recoils[part.recoilIndex] : mount.recoil);
                if(part.under){
                    part.draw(DrawPart.params);
                }
            }
        }

        Draw.xscl = -Mathf.sign(flipSprite);

        unit.type.applyColor(unit);

        if(region.found()) Draw.rect(region, wx, wy, weaponRotation);

        if(cellRegion.found()){
            Draw.color(unit.type.cellColor(unit));
            Draw.rect(cellRegion, wx, wy, weaponRotation);
            Draw.color();
        }

        if(heatRegion.found() && mount.heat > 0){
            Draw.color(heatColor, mount.heat);
            Draw.blend(Blending.additive);
            Draw.rect(heatRegion, wx, wy, weaponRotation);
            Draw.blend();
            Draw.color();
        }

        Draw.xscl = 1f;

        if(parts.size > 0){
            //TODO does it need an outline?
            for(int i = 0; i < parts.size; i++){
                var part = parts.get(i);
                DrawPart.params.setRecoil(part.recoilIndex >= 0 && mount.recoils != null ? mount.recoils[part.recoilIndex] : mount.recoil);
                if(!part.under){
                    part.draw(DrawPart.params);
                }
            }
        }

        Draw.xscl = 1f;

        Draw.z(z);

        HealBeamMount heal = (HealBeamMount)mount;

        if(unit.canShoot()){
            if (heal.strength <= 0.01f) return ;
            Draw.z(Layer.buildBeam);
            Draw.color(healColor);
            //Drawf.tri(wx, wy, pulseRadius * 2 * (float) Math.PI * angle / 360, pulseRadius, 180 + mount.rotation) ;
            Lines.stroke(range() * heal.strength) ;
            float a = Angles.angle(wx, wy, mount.target.x(), mount.target.y()) ;
            Lines.arc(wx, wy, range() / 2 * heal.strength, angle / 360f * heal.strength, a - angle / 2) ;
            Draw.z(z);
        }
    }
    @Override
    public void init() {
        super.init() ;
        bullet.healPercent = healPer ;
    }
}
