package shayebushi.world.blocks;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Nullable;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.UnitTypes;
import mindustry.ctype.ContentType;
import mindustry.entities.Leg;
import mindustry.entities.units.WeaponMount;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Legsc;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.world.Block;

public class ShiKuaiBlock extends Block {
    public ShiKuaiBlock(String name) {
        super(name);
        update = true ;
        hasShadow = false ;
    }
    public class ShiKuaiBuild extends Building {
        public UnitType type = UnitTypes.corvus;
        public float rot = 0;
        public String aString = UnitTypes.corvus.name;
        public int getIdd ;
        public float aFloat = x, aFloat1 = y;
        public @Nullable WeaponMount[] mounts ;
        public @Nullable Leg[] legs ;
        //public Unit unit ;
        public void setUnit(Unit u){
            //unit = u ;
            type = u.type ;
            rot = u.rotation ;
            aString = u.type.name ;
            aFloat = u.x ;
            aFloat1 = u.y ;
            mounts = u.mounts ;
            legs = u instanceof Legsc ? ((Legsc) u).legs() : null ;
        }
        @Override
        public void draw(){
            /**
            if (unit != null) {
                Color c = Team.derelict.color ;
                Draw.color(c);
                if (unit instanceof Legsc) {
                    unit.type.drawLegs((Unit & Legsc)unit);
                }
                Draw.rect(unit.type.region, unit.x, unit.y, unit.rotation - 90);
                float f = Mathf.clamp(unit.healthf());
                //Draw.color(Tmp.c1.set(Color.black).lerp(c, f + Mathf.absin(Time.time, Math.max(f * 5f, 1f), 1f - f)));
                Draw.color(c);
                Draw.rect(unit.type.cellRegion, unit.x, unit.y, unit.rotation - 90);
                Draw.color();
                for (WeaponMount m : unit.mounts) {
                    //apply layer offset, roll it back at the end
                    float z = Draw.z();
                    Draw.z(z + m.weapon.layerOffset);
                    float
                            rotation = unit.rotation - 90,
                            realRecoil = Mathf.pow(m.recoil, m.weapon.recoilPow) * m.weapon.recoil,
                            weaponRotation = rotation + (m.weapon.rotate ? m.rotation : m.weapon.baseRotation),
                            wx = unit.x + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -realRecoil),
                            wy = unit.y + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -realRecoil);
                    Draw.color(c);
                    if (m.weapon.region.found()) Draw.rect(m.weapon.region, wx, wy, weaponRotation);
                }
            }
             */
            if (type != null){
                //System.out.println(idd);
                Draw.z(Layer.groundUnit);
                Color c = Team.derelict.color ;
                Draw.color(c);
                if (type.constructor.get() instanceof Legsc && legs != null) {
                    drawLegs(type, legs);
                }
                Draw.color(c);
                Draw.rect(type.region, aFloat, aFloat1, rot - 90);
                if (Core.atlas.isFound(type.outlineRegion)){
                    Draw.rect(type.outlineRegion, aFloat, aFloat1, rot - 90);
                }
                //Draw.color(Tmp.c1.set(Color.black).lerp(c, f + Mathf.absin(Time.time, Math.max(f * 5f, 1f), 1f - f)));
                Draw.color(c);
                Draw.rect(type.cellRegion, aFloat, aFloat1, rot - 90);
                Draw.color();
                if (mounts != null) {
                    for (WeaponMount m : mounts) {
                        //apply layer offset, roll it back at the end
                        float z = Draw.z();
                        Draw.z(z + m.weapon.layerOffset);
                        float
                                rotation = rot - 90,
                                realRecoil = Mathf.pow(m.weapon.recoil, m.weapon.recoilPow) * m.weapon.recoil,
                                weaponRotation = rotation + (m.weapon.rotate ? m.rotation : m.weapon.baseRotation),
                                wx = aFloat + Angles.trnsx(rotation, x, y) + Angles.trnsx(weaponRotation, 0, -realRecoil),
                                wy = aFloat1 + Angles.trnsy(rotation, x, y) + Angles.trnsy(weaponRotation, 0, -realRecoil);
                        Draw.color(c);
                        //if (m.weapon.region.found())
                        Draw.rect(m.weapon.region, wx, wy, weaponRotation);
                        Draw.rect(m.weapon.outlineRegion, wx, wy, weaponRotation);
                    }
                }
            }
        }
        public <T extends Unit & Legsc> void drawLegs(UnitType u, Leg[] l){
//            u.applyColor(unit);
            Tmp.c3.set(Draw.getMixColor());


            float ssize = u.footRegion.width * u.footRegion.scl() * 1.5f;
            float rotation = rot;
            float invDrown = 0;

            if(u.footRegion.found()){
                for(Leg leg : l){
                    Drawf.shadow(leg.base.x, leg.base.y, ssize, invDrown);
                }
            }

            //legs are drawn front first
            for(int j = l.length - 1; j >= 0; j--){
                int i = (j % 2 == 0 ? j/2 : l.length - 1 - j/2);
                Leg leg = l[i];
                boolean flip = i >= l.length/2f;
                int flips = Mathf.sign(flip);


                Tmp.v1.set(leg.base).sub(leg.joint).inv().setLength(u.legExtension);

                if(u.footRegion.found() && leg.moving && u.shadowElevation > 0){
                    float scl = u.shadowElevation * invDrown;
                    float elev = Mathf.slope(1f - leg.stage) * scl;
                    Draw.color(Pal.shadow);
                    Draw.rect(u.footRegion, leg.base.x + u.shadowTX * elev, leg.base.y + u.shadowTY * elev, rot);
                    Draw.color();
                }

                Draw.mixcol(Tmp.c3, Tmp.c3.a);

                if(u.footRegion.found()){
                    Draw.rect(u.footRegion, leg.base.x, leg.base.y, rot);
                }

                Lines.stroke(u.legRegion.height * u.legRegion.scl() * flips);
                Lines.line(u.legRegion, aFloat, aFloat1, leg.joint.x, leg.joint.y, false);

                Lines.stroke(u.legBaseRegion.height * u.legRegion.scl() * flips);
                Lines.line(u.legBaseRegion, leg.joint.x + Tmp.v1.x, leg.joint.y + Tmp.v1.y, leg.base.x, leg.base.y, false);

                if(u.jointRegion.found()){
                    Draw.rect(u.jointRegion, leg.joint.x, leg.joint.y);
                }
            }

            //base joints are drawn after everything else
            if(u.baseJointRegion.found()){
                for(int j = l.length - 1; j >= 0; j--){
                    //TODO does the index / draw order really matter?
                    Draw.rect(u.baseJointRegion, aFloat, aFloat1, rotation);
                }
            }

            if(u.baseRegion.found()){
                Draw.rect(u.baseRegion, aFloat, aFloat1, rotation - 90);
            }

            Draw.reset();
        }
        @Override
        public byte version(){
            return 1;
        }
        @Override
        public void write(Writes write){
            super.write(write);
//            unit.add();
//            TypeIO.writeUnit(w, unit) ;
//            unit.remove();

            write.f(rot);
            write.f(aFloat) ;
            write.f(aFloat1) ;
            write.str(aString);
        }
        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision) ;
//            unit = TypeIO.readUnit(r) ;
            this.rot = read.f();
            this.aFloat = read.f();
            this.aFloat1 = read.f();
            this.aString = read.str();
            //n = SYBSUnitTypes.shuangxingerjieduan.name ;
            this.type = Vars.content.getByName(ContentType.unit, this.aString);
            Unit u = type.constructor.get();
            this.mounts = u.mounts;
            this.legs = u instanceof Legsc ? ((Legsc) u).legs() : null;
        }
    }
}
