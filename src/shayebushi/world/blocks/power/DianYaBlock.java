package shayebushi.world.blocks.power;

import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Interval;
import arc.util.Time;
import mindustry.annotations.Annotations;
import mindustry.audio.SoundLoop;
import mindustry.core.Renderer;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Build;
import mindustry.world.Tile;
import mindustry.world.blocks.power.PowerBlock;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.blocks.power.VariableReactor;
import mindustry.world.consumers.ConsumePower;
import mindustry.world.modules.ItemModule;
import mindustry.world.modules.LiquidModule;
import shayebushi.SYBSBlocks;
import shayebushi.world.consumers.ConsumeDianYa;

import static mindustry.Vars.*;
import static mindustry.Vars.tilesize;

public class DianYaBlock extends PowerBlock {
    public int dianya = 0 ;
    public Color flashColor1 = Color.red, flashColor2 = Color.valueOf("89e8b6");
    public float flashThreshold = 1f, flashAlpha = 0.4f, flashSpeed = 7f;
    public @Annotations.Load(value = "@-lights", fallback = "flux-reactor-lights") TextureRegion lightsRegion;
    public DianYaBlock(String name) {
        super(name);
        //insulated = true ;
    }
    @Override
    public void drawPotentialLinks(int x, int y){
        if((consumesPower || outputsPower) && hasPower && connectedPower){
            Tile tile = world.tile(x, y);
            if(tile != null){
                DianYaNode.getNodeLinks(tile, this, player.team(), other -> {
                    DianYaNode node = (DianYaNode)other.block;
                    Draw.color(node.laserColor1, Renderer.laserOpacity * 0.5f);
                    node.drawLaser(x * tilesize + offset, y * tilesize + offset, other.x, other.y, size, other.block.size);

                    Drawf.square(other.x, other.y, other.block.size * tilesize / 2f + 2f, Pal.place);
                });
            }
        }
    }

    public class DianYaBuild extends Building {

        @Override
        @Annotations.CallSuper
        public void placed() {

            if (net.client()) return;
            if ((block.consumesPower || block.outputsPower) && block.hasPower && block.connectedPower) {
                DianYaNode.getNodeLinks(tile, block, team, (other)->{
                    if (!other.power.links.contains(pos())) {
                        other.configureAny(pos());
                    }
                });
            }
        }
        @Override
        public Seq<Building> getPowerConnections(Seq<Building> out) {

            out.clear();
            if (power == null) return out;
            for (Building other : proximity) {
                if (other != null && other.power != null && other.team == team && !(block.consumesPower && other.block.consumesPower && !block.outputsPower && !other.block.outputsPower && !block.conductivePower && !other.block.conductivePower) && ((other.block.consPower instanceof ConsumeDianYa d && d.dianya == dianya) || (other.block instanceof DianYaBlock dyb && dyb.dianya == dianya) || (other.block == SYBSBlocks.bianyaqi)) && conductsTo(other) && other.conductsTo(this) && !power.links.contains(other.pos())) {
                    out.add(other);
                }
            }
            for (int i = 0; i < power.links.size; i++) {
                Tile link = world.tile(power.links.get(i));
                if (link != null && link.build != null && link.build.power != null && link.build.team == team) out.add(link.build);
            }
            return out;
        }

        @Override
        public boolean conductsTo(Building b) {
            return true ;
        }
    }
}
