package shayebushi.entities.units;

import arc.math.Rand;
import arc.math.geom.Vec2;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.PayloadUnit;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import shayebushi.entities.abilities.ZaiJiAbility;

public class ZhanJiUnitEntity extends UnitEntity {
    public float shanbi = 0.5f ;
    public boolean zhandou = true ;
    public boolean hongzha = false ;
    public boolean paoting = false ;
    public Rand r = new Rand() ;
    public int classId(){
        return 116 ;
    }
    @Override
    public void rawDamage(float amount){
        if (r.random(0.0f, 1) > shanbi){
            super.rawDamage(amount);
        }
        else if (health <= 0 && !dead){
            kill();
        }
    }
    @Override
    public void update(){
        super.update();
        Unit mu ;
        boolean bb = true ;
        for (WeaponMount m : mounts){
            if (m.target != null){
                bb = false ;
            }
        }
        if (healthf() <= 0.2f || (bb && isCommandable() && command().attackTarget == null && command().targetPos == null)){
            mu = Units.closest(team, x, y, o -> {
                boolean b = false ;
                for (Ability a : o.abilities) {
                    if (a instanceof ZaiJiAbility z && o instanceof PayloadUnit p && p.payloads.size < z.amount){
                        b = true ;
                    }
                }
                return b ;
            }) ;
            if (mu != null){
                if (command().targetPos == null){
                    command().targetPos = new Vec2(mu.x, mu.y) ;
                }
                else {
                    command().targetPos.set(mu);
                }
            }
        }
    }
}
