package shayebushi.entities.weapons;

import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.util.Strings;
import mindustry.entities.Units;
import mindustry.entities.part.DrawPart;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.gen.UnitWaterMove;
import mindustry.graphics.Drawf;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import shayebushi.SYBSStatValues;
import shayebushi.SYBSUnits;
import shayebushi.entities.units.WaterMovePayLoadUnit;
import shayebushi.entities.units.WaterMoveTimedKillUnit;
import shayebushi.entities.units.WaterMoveXianShangUnit;

public class TeShuWeapon extends Weapon {
    public boolean targetGround = true, targetAir = true, targetNaval = true;
    public boolean fanqian = true;
    public TeShuWeapon(String name){
        super(name);
    }
    public TeShuWeapon(){
        super() ;
    }
    @Override
    public Teamc findTarget(Unit unit, float x, float y, float range, boolean air, boolean ground){
        return SYBSUnits.closestTarget(unit.team, x, y, range + Math.abs(shootY), u -> (u.isGrounded() && !u.type.naval && !(u instanceof UnitWaterMove || u instanceof WaterMoveXianShangUnit || u instanceof WaterMoveTimedKillUnit || u instanceof WaterMovePayLoadUnit) && ground && targetGround) || (u.isFlying() && air && targetAir) || (u.type.naval && (u instanceof UnitWaterMove || u instanceof WaterMoveXianShangUnit || u instanceof WaterMoveTimedKillUnit || u instanceof WaterMovePayLoadUnit) && ground && targetNaval), t -> ground, fanqian);
    }
    @Override
    public boolean checkTarget(Unit unit, Teamc target, float x, float y, float range){
        return SYBSUnits.invalidateTarget(target, unit.team, x, y, range + Math.abs(shootY), fanqian, targetGround, targetAir, targetNaval);
    }
    @Override
    public void addStats(UnitType u , Table t){
        if(inaccuracy > 0){
            t.row();
            t.add("[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int)inaccuracy + " " + StatUnit.degrees.localized());
        }
        if(!alwaysContinuous && reload > 0){
            t.row();
            t.add("[lightgray]" + Stat.reload.localized() + ": " + (mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / reload * shoot.shots, 2) + " " + StatUnit.perSecond.localized());
        }
        SYBSStatValues.ammo(ObjectMap.of(u, bullet)).display(t);
    }
    @Override
    public void draw(Unit unit, WeaponMount mount){
        //apply layer offset, roll it back at the end
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

        //fix color
        unit.type.applyColor(unit);

        //if(region.found())
        Draw.rect(region, wx, wy, weaponRotation);

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
    }
}
