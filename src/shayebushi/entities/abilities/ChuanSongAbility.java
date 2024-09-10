package shayebushi.entities.abilities;

import arc.Core;
import arc.audio.Sound;
import arc.flabel.effects.ShakeEffect;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.input.Binding;
import mindustry.logic.LExecutor;
import mindustry.world.Tile;
import shayebushi.SYBSBinding;
import shayebushi.SYBSFx;
import shayebushi.ShaYeBuShi;
import shayebushi.entities.bullet.BaiFenBiLaserBulletType;

import static arc.util.Time.delta;
import static arc.util.Time.toSeconds;
import static mindustry.Vars.tilesize;

public class ChuanSongAbility extends ShouDongAbilityBase {
    public float timer = 0 ;
    public float time = 5 * toSeconds ;
    public float timer2 = 0 ;
    public float time2 = 10 * toSeconds ;
    public float damageRange = 12 * tilesize, damageWay = 1200, damageFinal = 4500 ;
    public Effect effect = SYBSFx.baozha1;
    public float amount = 0 ;
    public Seq<Bullet> bs = new Seq<>() ;
    public ChuanSongAbility() {
        key = SYBSBinding.zhujineng ;
    }
    @Override
    public void update(Unit u) {
        timer2 += delta ;
        if (u.isPlayer() && Core.input.keyDown(key) && timer2 >= time2) {
            timer += delta ;
        }
        if (timer >= time && !Core.input.keyDown(key)) {
            Building core = Vars.state.teams.cores(Vars.state.rules.waveTeam).random() ;
            Tile spawn = Vars.spawner.getSpawns().random() ;
            //System.out.println(core != null);
            if (core != null) {
                u.x = core.x ;
                u.y = core.y ;
                applyDamage(u);
                float angle = ShaYeBuShi.r.random(360) ;
                float len = ShaYeBuShi.r.random(5, 60) * tilesize ;
                Vec2 v = ShaYeBuShi.circle(angle, len, u.x, u.y) ;
                u.x = v.x ;
                u.y = v.y ;
                BulletType b = new LaserBulletType(damageWay) {{
                    damage = damageWay;
                    length = len;
                    colors[0] = u.team.color.cpy().mul(1f, 1f, 1f, 0.4f);
                    colors[1] = u.team.color;
                }};
                Bullet bb = b.create(u ,u.x, u.y, angle);
                bb.rotation(angle);
                bb.add();
            }
            else if (spawn != null) {
                float angle = ShaYeBuShi.r.random(360) ;
                Vec2 v = ShaYeBuShi.circle(angle, Vars.state.rules.dropZoneRadius, spawn.x * tilesize, spawn.y * tilesize) ;
                //System.out.println(v.x + " " + v.y);
                u.x = v.x ;
                u.y = v.y ;
            }
            applyDamage(u);
            timer = 0 ;
            timer2 = 0 ;
        }
        else if (timer < time && !Core.input.keyDown(key)) {
            timer = 0 ;
        }
        timer = Math.min(timer, time) ;
    }
    public void applyDamage(Unit u) {
        Units.nearby(null, u.x, u.y, damageRange, un -> {
            if (un.team != u.team) {
                un.damage(damageFinal);
            }
        });
        Units.nearbyBuildings(u.x, u.y, damageRange, un -> {
            if (un.team != u.team) {
                un.damage(damageFinal);
            }
        });
        effect.at(u.x, u.y, u.team.color);
    }
    @Override
    public void draw(Unit u) {
        if (timer > 0) {
            amount = Math.max((ShaYeBuShi.r.random(-1, 1) + timer / toSeconds), 0) ;
            for (Bullet b : bs) {
                if (!b.isAdded()) {
                    bs.remove(b) ;
                }
            }
            if (bs.size < amount) {
                float angle = ShaYeBuShi.r.random(360);
                Bullet b = ShaYeBuShi.circleBulletTypes.get(Math.max((int) (timer / toSeconds) + ShaYeBuShi.r.random(-2, 0), 0)).create(u, u.x, u.y, angle);
                b.add();
                bs.add(b);
            }
        }
    }
}
