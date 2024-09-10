package shayebushi.world.blocks.defense.turrets;

import arc.Events;
import arc.func.Boolf;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Interval;
import mindustry.audio.SoundLoop;
import mindustry.content.Items;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.gen.Teamc;
import mindustry.graphics.Drawf;
import mindustry.type.Item;
import mindustry.ui.MultiReqImage;
import mindustry.ui.ReqImage;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.consumers.Consume;
import mindustry.world.consumers.ConsumeItemFilter;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.Stats;
import mindustry.world.modules.ItemModule;
import mindustry.world.modules.LiquidModule;
import mindustry.world.modules.PowerModule;
import shayebushi.SYBSStatValues;

import static mindustry.Vars.content;

public class MultiTurret extends ItemTurret {
    public Seq<TurretWeapon> turrets = new Seq<>() ;
    public MultiTurret(String name) {
        super(name);
        hasItems = true ;
        itemCapacity = 500 ;
    }
    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.weapons, SYBSStatValues.weapons(this, turrets));
    }
    @Override
    public void setBars(){
        super.setBars();
        for (TurretWeapon t : turrets){
            t.addBar(this);
        }
    }
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);
       for (TurretWeapon t : turrets){
           t.drawPlace(x, y, rotation, valid);
       }
    }
    @Override
    public void init(){
        super.init();
        for (TurretWeapon t : turrets){
            t.init();
        }
    }
    @Override
    public void load(){
        super.load();
        for (TurretWeapon t : turrets){
            t.load();
        }
    }
    public MultiTurret addTurret(Turret t, float xOffset, float yOffset){
        TurretWeapon tt = new TurretWeapon(t, xOffset, yOffset) ;
        turrets.add(tt) ;
        return this ;
    }

    public class MultiTurretBuild extends ItemTurret.ItemTurretBuild {
        public TurretWeaponMount[] mounts;
        @Override
        public void updateTile(){
            super.updateTile();
            for (TurretWeaponMount t : mounts){
                t.weapon.update(this, t);
            }
            //items.add(turrets.first().ammoType, turrets.first().ammoPerShoot);
        }
        @Override
        public void drawSelect(){
            super.drawSelect();
            for (TurretWeapon t : turrets){
                Drawf.dashCircle(x, y, t.range(), team.color);
            }
        }
        @Override
        public Building create(Block block, Team team){

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
            setupWeapons((MultiTurret) block);
            return this;
        }
        public void setupWeapons(MultiTurret def) {
            mounts = new TurretWeaponMount[def.turrets.size];
            for (int i = 0; i < mounts.length; i++) {
                mounts[i] = def.turrets.get(i).mountType.get(def.turrets.get(i));
            }
        }
        @Override
        public void draw(){
            super.draw();
            for (TurretWeaponMount t : mounts){
                t.weapon.draw(this, t);
            }
        }
        @Override
        public int acceptStack(Item item, int amount, Teamc source){
            BulletType type = ammoTypes.get(item);

            if(type == null) return 0;

            return Math.min((int)((maxAmmo - totalAmmo) / ammoTypes.get(item).ammoMultiplier), amount);
        }
        @Override
        public void handleStack(Item item, int amount, Teamc source){
            for(int i = 0; i < amount; i++){
                handleItem(null, item);
            }
        }
        @Override
        public boolean acceptItem(Building source, Item item){
            boolean bb = false ;
            for (TurretWeaponMount t : mounts){
                if (t.weapon.ammoTypes.keys().toSeq().contains(item)){
                    bb = true ;
                }
            }
            return items.get(item) < itemCapacity && (bb || (ammoTypes.get(item) != null && totalAmmo + ammoTypes.get(item).ammoMultiplier <= maxAmmo));
        }
        @Override
        public int removeStack(Item item, int amount){
            return 0;
        }
        @Override
        public void handleItem(Building source, Item item){
            boolean bb = false ;
            for (TurretWeaponMount t : mounts){
                if (t.weapon.ammoTypes.keys().toSeq().contains(item)){
                    bb = true ;
                }
            }
            if (bb) items.add(item, 1);
            super.handleItem(source, item);
        }
        @Override
        public void displayConsumption(Table table) {

            table.left();
            for (Consume cons : block.consumers) {
                if (cons.optional && cons.booster) continue;
                cons.build(this, table);
            }
            for (TurretWeaponMount t : mounts){
                Boolf<Item> filter = i -> false;
                MultiReqImage image = new MultiReqImage();
                content.items().each(i -> filter.get(i) && i.unlockedNow(),
                        item -> image.add(new ReqImage(new Image(item.uiIcon),
                                () -> t.weapon.ammoTypes.containsKey(item))));
                table.add(image).size(8 * 4);
            }
        }
    }
}
