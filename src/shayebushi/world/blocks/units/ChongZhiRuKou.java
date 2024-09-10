package shayebushi.world.blocks.units;

import arc.Core;
import arc.math.geom.Geometry;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Groups;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.payloads.UnitPayload;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitBlock;
import shayebushi.SYBSItems;
import shayebushi.ShaYeBuShi;
import shayebushi.world.blocks.logic.FanDanWei;

import static arc.util.Time.toSeconds;
import static java.lang.Math.pow;
import static mindustry.Vars.tilesize;

public class ChongZhiRuKou extends UnitBlock {
    public float craftTime = 5 * toSeconds ;
    public ChongZhiRuKou(String name) {
        super(name);
        configurable = true ;
        consumeItem(SYBSItems.daole, 648) ;
    }
    @Override
    public void setBars() {
        super.setBars();
        addBar("progress", (ChongZhiRuKouBuild entity) -> new Bar("bar.progress", Pal.ammo, () -> entity.progress / craftTime));
        addBar("level", (ChongZhiRuKouBuild entity) -> new Bar(() -> Core.bundle.format("bar.level", entity.level), () -> Pal.heal, () -> entity.exp / (float)pow(2, entity.level)));
    }
    public class ChongZhiRuKouBuild extends UnitBuild {
        public int status = -1 ;
        public float exp = 0 ;
        public int level = 0 ;
        public boolean done = false ;
        @Override
        public void buildConfiguration(Table t) {
            super.buildConfiguration(t);
            for (int i = 0 ; i < ShaYeBuShi.shuxings ; i ++) {
                int finalI = i;
                float f = 0;
                switch (i) {
                    case 0 -> f = 30;
                    case 1 -> f = 100;
                    case 2 -> f = 20;
                    case 3 -> f = 15;
                    case 4 -> f = 100;
                }
                f *= (1 + level * 0.2f) ;
                int z = i ;
                t.button(Core.bundle.format(ShaYeBuShi.shuxing[i], f), () -> {
                    status = finalI ;
                }).width(400).update(tb -> tb.setChecked(z == status)).row() ;
            }
        }
        @Override
        public boolean shouldConsume() {
            return super.shouldConsume() && status != -1 && payload != null ;
        }
        @Override
        public void updateTile() {
            super.updateTile();
            if (payload != null) {
                payload.set(x, y, drawrot());
            }
            if (efficiency > 0 && shouldConsume() && moveInPayload()) {
                progress += Time.delta ;
                if (progress >= craftTime) {
                    float f = 0;
                    switch (status) {
                        case 0 -> f = 15;
                        case 1 -> f = 100;
                        case 2 -> f = 20;
                        case 3 -> f = 10;
                        case 4 -> f = 100;
                    }
                    f *= (1 + level * 0.2f) ;
                    ShaYeBuShi.beilvs.get(status).put(payload.unit, ShaYeBuShi.beilvs.get(status).get(payload.unit, 0f) + f);
                    exp += 648 * (level + 1) ;
                    while (exp >= pow(2, level) + 1000) {
                        exp -= pow(2, level) + 1000 ;
                        level ++ ;
                    }
                    progress = 0 ;
                    consume() ;
                    Effect.shake(2f, 3f, this);
                    Fx.producesmoke.at(this);
                    done = true ;
                    //System.out.println((front() != null) + " " + (front().acceptPayload(this, payload)));
                    /*
                    if (front() != null && (front().block.outputsPayload || front().block.acceptsPayload)) {
                        if (movePayload(payload)){
                            payload = null;
                        }
                    }
                    else if (front() == null || !front().tile().solid()) {
                        int trns = block.size / 2 + 1;
                        float dx = arc.math.geom.Geometry.d4(rotation).x * trns * tilesize, dy = Geometry.d4(rotation).y * trns * tilesize ;
                        payload.set(x + dx, y + dy, drawrot());
                        if (payload.dump()) {
                            payload = null;
                        }
                    }
                    */
                }
            }
            if (done && payload != null) {
                moveOutPayload();
                if (payload == null) {
                    done = false ;
                }
            }
        }
        @Override
        public void draw() {
            super.draw() ;
            if (payload != null) {
                payload.draw();
            }
        }
        @Override
        public void write(Writes write){
            super.write(write);
            write.i(status);
            write.f(exp);
            write.i(level);
            write.bool(done);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            status = read.i() ;
            exp = read.f() ;
            level = read.i() ;
            done = read.bool() ;
        }
    }
}
