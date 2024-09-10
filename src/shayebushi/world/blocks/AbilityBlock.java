package shayebushi.world.blocks;

import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.abilities.Ability;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.meta.Stat;
import shayebushi.world.blocks.abilities.BlockAbility;

public class AbilityBlock extends Block {

    public Seq<BlockAbility> abilities = new Seq<>() ;

    public AbilityBlock(String name) {
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


    public class AbilityBuild extends Building {
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
