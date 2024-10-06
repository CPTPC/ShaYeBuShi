package shayebushi.world.blocks.defense;

import arc.scene.ui.layout.Table;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.blocks.defense.ShieldWall;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.Stat;
import shayebushi.world.blocks.abilities.BlockAbility;

public class AbilityWall extends ShieldWall {

    public Seq<BlockAbility> abilities = new Seq<>() ;

    public AbilityWall(String name) {
        super(name);
        update = true ;
    }

    @Override
    public void init() {
        super.init();
        for (BlockAbility ba : abilities) {
            ba.init(this);
        }
    }

    @Override
    public void setStats() {
        super.setStats();
        if(abilities.any()) {
            var unique = new ObjectSet<String>();
            for (BlockAbility a : abilities) {
                if (a.display && unique.add(a.localized())) {
                    stats.add(Stat.abilities, a.localized());
                }
            }
        }
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid) ;
        for (BlockAbility a : abilities) {
            a.drawPlace(x, y, rotation, valid);
        }
    }

    public class AbilityWallBuild extends ShieldWallBuild {
        public Seq<BlockAbility> as = new Seq<>() ;
        @Override
        public void killed() {
            super.killed();
            for (BlockAbility ba : as) {
                ba.death(this);
            }
        }
        @Override
        public void updateTile() {
            super.updateTile();
            for (BlockAbility ba : as) {
                ba.update(this);
            }
        }
        @Override
        public void draw() {
            super.draw() ;
            for (BlockAbility ba : as) {
                ba.draw(this);
            }
        }
        @Override
        public Building create(Block b, Team t) {
            for (BlockAbility ba : abilities) {
                as.add(ba.copy()) ;
            }
            Building build = super.create(b, t) ;
            for (BlockAbility ba : as) {
                ba.onBuilt(this);
            }
            return build ;
        }

        @Override
        public void onRemoved() {
            super.onRemoved();
            for (BlockAbility ba : as) {
                ba.onRemoved(this);
            }
        }

        @Override
        public void drawSelect() {
            super.drawSelect() ;
            for (BlockAbility ba : as) {
                ba.drawSelect(this);
            }
        }

        @Override
        public void displayBars(Table t) {
            super.displayBars(t);
            for (BlockAbility ba : as) {
                ba.displayBars(this, t);
            }
        }

        @Override
        public void write(Writes w) {
            super.write(w);
        }

        @Override
        public void read(Reads r, byte v) {
            super.read(r, v);
            for (BlockAbility a : abilities) {
                as.add(a.copy()) ;
            }
            for (BlockAbility ba : as) {
                ba.onRead(this);
            }
        }

        @Override
        public byte version(){
            return 1;
        }
    }
}
