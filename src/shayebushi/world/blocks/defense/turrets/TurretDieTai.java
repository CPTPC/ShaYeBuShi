package shayebushi.world.blocks.defense.turrets;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Interval;
import mindustry.Vars;
import mindustry.audio.SoundLoop;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.ConstructBlock;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.power.PowerGraph;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.modules.ItemModule;
import mindustry.world.modules.LiquidModule;
import mindustry.world.modules.PowerModule;
import shayebushi.SYBSBlocks;

import static mindustry.game.Gamemode.sandbox;
import static mindustry.type.ItemStack.with;
import static shayebushi.SYBSItems.taihejin;

public class TurretDieTai extends ItemTurret implements Cloneable{
    public int jieduan = 1 ;
    public int maxduidie = 10 ;
    public boolean isyushe = true ;
    public int cooldown = 300 ;
    public static ObjectMap<String, Seq<TurretDieTai>> dietais = new ObjectMap<>() ;
    public String rawName = "dietai" ;
    public void initt(){
        dietais.put(rawName, new Seq<>()) ;
        for (int z = 1 ; z <= maxduidie ; z ++){
            int i = z ;
            TurretDieTai c =
                    new TurretDieTai("dietai"+i){{
                        requirements(Category.turret, BuildVisibility.sandboxOnly,with());
                        ammo(
                                Items.surgeAlloy, new BasicBulletType(15f, 220*i){{
                                    hitSize = 5f;
                                    width = 15f;
                                    height = 21f;
                                    shootEffect = Fx.shootBig;
                                    reloadMultiplier = 0.75f;
                                    pierceArmor = true ;
                                    knockback = i/2f;
                                    lightning = 5*i ;
                                    lightningLength = 8*i;
                                    lightningDamage = 50*i ;
                                }},
                                Items.thorium, new BasicBulletType(15f, 180*i){{
                                    hitSize = 5;
                                    width = 16f;
                                    height = 23f;
                                    shootEffect = Fx.shootBig;
                                    ammoMultiplier = 3*i ;
                                    pierceArmor = true ;
                                    pierceCap = 5*i;
                                    pierceBuilding = true;
                                    knockback = i;
                                    rangeChange = 30 * i ;
                                }},
                                Items.pyratite, new BasicBulletType(15f, 150*i){{
                                    hitSize = 5.2f;
                                    width = 16f;
                                    height = 21f;
                                    frontColor = Pal.lightishOrange;
                                    backColor = Pal.lightOrange;
                                    status = StatusEffects.burning;
                                    hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.fireHit);
                                    shootEffect = Fx.shootBig;
                                    makeFire = true;
                                    splashDamagePierce = true ;
                                    pierceCap = 3*i;
                                    pierceArmor = true ;
                                    pierceBuilding = true;
                                    knockback = 0.6f;
                                    ammoMultiplier = 6*i;
                                    splashDamage = 150*i;
                                    splashDamageRadius = 40*i;
                                    reloadMultiplier = 1.1f;
                                    intervalAngle = 15 ;
                                    intervalBullet = new BasicBulletType(6,30*i){{
                                        knockback = (float)0.07*i ;
                                        frontColor = Pal.lightishOrange;
                                        backColor = Pal.lightOrange;
                                    }};
                                    intervalBullets = 6 * i ;
                                }},
                                taihejin , new BasicBulletType(15,200*i){{
                                    hitSize = 5f;
                                    width = 15f;
                                    height = 21f;
                                    shootEffect = Fx.shootBig;
                                    reloadMultiplier = 0.9f;
                                    knockback = (float) 0.7 * i ;
                                    pierceArmor = true ;
                                    fragBullets = 8 *i;
                                    fragBullet = new BasicBulletType(6,40*i){{
                                        knockback = (float)0.1*i ;
                                    }};
                                }}
                        );
                        if (reload / (float)i >= 1) {
                            reload = 20f/i;
                        }
                        else {
                            shoot.shots = i ;
                        }
                        health = 8000*i ;
                        armor = 5*i ;
                        recoilTime = reload * 2f;
                        coolantMultiplier = 0.5f;
                        ammoUseEffect = Fx.casing3;
                        range = 520f;
                        inaccuracy = 3f;
                        recoil = 3f;
                        shoot = new ShootAlternate(10f);
                        shake = 2f;
                        size = 5;
                        rawName = "dietai" ;
                        shootCone = 24f;
                        shootSound = Sounds.shootBig;
                        jieduan = i ;
                        maxAmmo = 12 * i;
                        scaledHealth = 160;
                        coolant = consumeCoolant((float)1.2);
                        if (i != 10) {
                            isyushe = false;
                        }
                        limitRange();
                    }};
            //region = Core.atlas.find("dietai");
            c.loadIcon();
            c.load();
            ((DrawTurret)c.drawer).load(c) ;
            c.init();
            for (BulletType l : c.ammoTypes.values()){
                l.load() ;
                if (l.fragBullet != null){
                    l.fragBullet.load();
                }
            }
            dietais.get(rawName).add(c) ;
        }
    }
    public TurretDieTai(String name) {
        super(name);
        canOverdrive = true ;
    }
    @Override
    public void setBars(){
        super.setBars();
        addBar("jianzaolengque", (TurretDieTaiBuild b) -> new Bar(
            () -> Core.bundle.format("bar.jianzaolengque") ,
            () -> Color.blue ,
            () -> b.timer/(float)cooldown));
        addBar("jieduan", (TurretDieTaiBuild b) -> new Bar(
                () -> Core.bundle.format("bar.jieduan",jieduan) ,
                () -> Color.gold ,
                () -> 1));
    }
    @Override
    public boolean canPlaceOn(Tile t, Team team, int r){
        //super.canPlaceOn(t,team,r) ;
        if(t.block() instanceof TurretDieTai i){
            //System.out.println(i.isyushe) ;
            //System.out.println(i.jieduan) ;
            if (t.build instanceof TurretDieTaiBuild bd){
                //System.out.println(bd.timer);
            }
        }
        //System.out.println((t.build != null) + " " + (t.block() instanceof TurretDieTai d && d.jieduan < d.maxduidie && !d.isyushe) + " " +(t.build instanceof TurretDieTaiBuild b && b.timer >= b.cooldown));
        if (t.build != null && t.block() instanceof TurretDieTai d && d.jieduan < d.maxduidie && !d.isyushe && t.build instanceof TurretDieTaiBuild b && b.timer >= cooldown && d.rawName == rawName){
            return true ;
        }
        else if (!(t.block() instanceof TurretDieTai) && t.floor().canPlaceOn(t,team,r)){
            return true ;
        }
        return false ;
    }
    public TurretDieTai copy(){
        try{
            TurretDieTai copy = (TurretDieTai) clone();
            copy.id = (short) Vars.content.getBy(getContentType()).size;
            Vars.content.handleContent(copy);
            return copy;
        }catch(Exception e){
            throw new RuntimeException("death to checked exceptions", e);
        }
    }
    @Override
    public void placeBegan(Tile tile, Block previous, Unit builder){
        //finish placement immediately when a block is replaced.
        if (dietais.get(rawName).isEmpty()) {
            initt();
        }
        //System.out.println((previous instanceof TurretDieTai) + "" + (previous.localizedName.equals("叠态")));
        if(previous instanceof TurretDieTai t && t.rawName == rawName
                //&& previous == this
        ){
            //System.out.println(t.jieduan+"\n"+"-----");
            TurretDieTai i = dietais.get(rawName).get(Math.min(t.jieduan, t.maxduidie - 1)) ;
            //i.buildVisibility = new BuildVisibility(() -> true) ;
            //Vars.state.rules.revealedBlocks.remove(i) ;
            i.isyushe = false ;
            tile.setBlock(i, tile.team());
            tile.block().placeEffect.at(tile, tile.block().size);
            Fx.upgradeCore.at(tile.drawx(), tile.drawy(), 0f, tile.block());
            Fx.upgradeCoreBloom.at(tile, tile.block().size);
            Events.fire(new EventType.BlockBuildEndEvent(tile, builder, tile.team(), false, null));
            if (tile.build instanceof TurretDieTaiBuild d) {
                d.timer = 0;
            }
            Vars.state.teams.cores(tile.build.team).first().items.remove(new Seq<>(requirements).map(is -> new ItemStack(is.item, Vars.state.rules.mode() == sandbox ? 0 : (int)(is.amount * Vars.state.rules.buildCostMultiplier))));
        }
        else {
            //System.out.println("6\n--");
            if (isyushe) {
                TurretDieTai i = dietais.get(rawName).get(jieduan == maxduidie ? dietais.get(rawName).size -1 : 0);
                i.isyushe = false;
                tile.setBlock(i, tile.team());
                tile.block().placeEffect.at(tile, tile.block().size);
                Vars.state.teams.cores(tile.build.team).first().items.remove(new Seq<>(requirements).map(is -> new ItemStack(is.item, Vars.state.rules.mode() == sandbox ? 0 : (int)(is.amount * Vars.state.rules.buildCostMultiplier))));
            }
            else {
                TurretDieTai i = dietais.get(rawName).get(jieduan - 1);
                i.isyushe = false;
                tile.setBlock(i, tile.team());
                tile.block().placeEffect.at(tile, tile.block().size);
                Vars.state.teams.cores(tile.build.team).first().items.remove(new Seq<>(requirements).map(is -> new ItemStack(is.item, Vars.state.rules.mode() == sandbox ? 0 : (int)(is.amount * Vars.state.rules.buildCostMultiplier))));
            }
        }
    }
    public class TurretDieTaiBuild extends ItemTurretBuild {
        public int timer = 0 ;
        @Override
        public void updateTile(){
            super.updateTile();
            timer ++ ;
        }
    }
//    public class ConstructTurretDieTaiBuild extends ConstructBlock.ConstructBuild {
//        public int timer = 0 ;
//        public int cooldown = 300 ;
//        @Override
//        public void updateTile(){
//            super.updateTile();
//            timer ++ ;
//        }
//    }
}
