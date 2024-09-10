package shayebushi.world.blocks;

import arc.func.Boolf3;
import arc.math.Rand;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.util.Strings;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import shayebushi.SYBSStatValues;
import shayebushi.SYBSStats;
import shayebushi.entities.bullet.ShuiLeiBulletType;
import shayebushi.entities.bullet.TeShuExplosionBulletType;
import shayebushi.entities.weapons.TeShuWeapon;
import shayebushi.type.unit.YuLeiUnitType;

import static arc.util.Time.toMinutes;
import static mindustry.Vars.*;
import static mindustry.content.Fx.titanExplosion;
import static shayebushi.SYBSBlocks.zhadans;

public class ZhaDanBlock extends Block {
    public boolean yulei = false , shuilei = true ;
    public int amount = 10 ;
    public boolean killSelf = true ;
    public float reload = 1 ;
    public BulletType type = new ShuiLeiBulletType(0, 0) {{
        backColor = Pal.lightishGray ;
        splashDamage = 2500;
        splashDamageRadius = 80;
        lifetime = Float.POSITIVE_INFINITY;
        hitEffect = titanExplosion;
        hitSound = Sounds.artillery;
        //floating = true ;
        placeableLiquid = true ;
    }};
    public YuLeiUnitType yu ;
    public Boolf3<Tile, Team, Integer> can = super::canPlaceOn;
    public boolean click = true ;
    public float reloadB = toMinutes ;
    public float timerB = 0 ;
    public ZhaDanBlock(String name) {
        super(name);
        update = true;
        configurable = true ;
        rebuildable = false ;
        buildCostMultiplier = 0 ;
        zhadans.add(this) ;
        yu = new YuLeiUnitType(name + "-yulei"){{
            health = 400 ;
            hittable = false ;
            lifetime = 180 ;
            speed = 4.7f ;
            naval = true ;
            targetAir = false;
            maxRange = 5f;
            outlineColor = Pal.darkOutline;
            lowAltitude = true;
            deathExplosionEffect = Fx.none;
            loopSoundVolume = 0.1f;
            weapons.add(new TeShuWeapon(){
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
                {
                    shootCone = 360f;
                    reload = 1 ;
                    shootOnDeath = true ;
                    //alwaysShooting = true ;
                    bullet = new TeShuExplosionBulletType(6000,80){{
                        hitColor = Pal.accent ;
                        shootEffect = hitEffect = titanExplosion;
                        hitSound = Sounds.artillery;
                        //width = 24 ;
                        //height = 24 ;
                        fragBullets = 10;
                        fragSpread = 5f ;
                        //killShooter = false ;
                        fragBullet = new ShuiLeiBulletType(0, 0) {{
                            backColor = Pal.lightishGray ;
                            splashDamage = 2500;
                            splashDamageRadius = 80;
                            lifetime = Float.POSITIVE_INFINITY;
                            shootEffect = hitEffect = titanExplosion;
                            hitSound = Sounds.artillery;
                        }};
                        naval = 2 ;
                    }} ;
                }}) ;
        }};
    }
    @Override
    public void init(){
        super.init();
        if (yulei){
            rotate = true ;
        }
    }
    @Override
    public void setStats(){
        super.setStats();
        ObjectMap<ZhaDanBlock, BulletType> b = new ObjectMap<>() ;
        b.put(this, type) ;
        ObjectMap<ZhaDanBlock, BulletType> bb = new ObjectMap<>() ;
        bb.put(this, yu.weapons.first().bullet) ;
        if (shuilei){
            stats.add(Stat.ammo, SYBSStatValues.ammo(b));
        }
        if (yulei){
            stats.add(Stat.ammo,SYBSStatValues.ammo(bb));
        }
        stats.add(SYBSStats.jianzaolengque, reloadB / 60, StatUnit.seconds) ;
    }
    @Override
    public boolean canPlaceOn(Tile t, Team tt, int r) {
        return can.get(t, tt, r) && timerB >= reloadB ;
    }
    @Override
    public void placeBegan(Tile t, Block b, Unit u) {
        super.placeBegan(t, b, u) ;
        timerB = 0 ;
    }
    public class ShuiLeiBuShuBuild extends Building {
        public boolean bushu = false ;
        public float time = 0 ;
        @Override
        public void buildConfiguration(Table table){
            super.buildConfiguration(table);
            if(net.client()){
                deselect();
                return;
            }

            table.button(Icon.upOpen, Styles.cleari, () -> {
                if (reload > 1 && (time + Time.delta) >= reload) {
                    bushu = true;
                }
                else if (reload == 1){
                    bushu = true ;
                }
                deselect();
            }).size(40f);
        }
        @Override
        public void updateTile(){
            super.updateTile();
            if (bushu || !click || dead){
                if (shuilei) {
                    for (int i = 0; i < amount; i++) {
                        Rand r = new Rand();
                        type.create(this, x + r.random(-24, 24), y + r.random(-24, 24), 0);
                    }
                }
                if (yulei){
                    Unit u = yu.create(team);
                    u.rotation(rotation);
                    u.add();
                }
                if (killSelf) {
                    kill();
                }
                bushu = false ;
            }
            if (reload > 1){
                time += Time.delta ;
            }
        }
    }
}
