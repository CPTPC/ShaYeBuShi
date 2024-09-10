package shayebushi.world.modules;

import arc.struct.IntSeq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.world.blocks.power.PowerGraph;
import mindustry.world.modules.PowerModule;

public class DianYaModule extends PowerModule {
    public int dianya = 0 ;
    public DianYaModule(int dianya) {
        this.dianya = dianya ;
    }
    @Override
    public void write(Writes write){
        super.write(write);
        //write.i(dianya);
    }

    @Override
    public void read(Reads read){
        super.read(read);
        //dianya = read.i() ;
    }
}
