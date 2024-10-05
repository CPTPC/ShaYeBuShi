package shayebushi;

import arc.Core;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Plane;
import arc.math.geom.Vec3;
import arc.struct.Seq;
import arc.util.Nullable;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Liquids;
import mindustry.game.Saves;
import mindustry.game.SectorInfo;
import mindustry.gen.Iconc;
import mindustry.graphics.g3d.PlanetGrid;
import mindustry.type.*;
import mindustry.ui.Fonts;
import mindustry.world.modules.ItemModule;

import static mindustry.Vars.net;
import static mindustry.Vars.state;

public class SYBSSector extends Sector {

    public SYBSSector(Planet planet, PlanetGrid.Ptile tile) {
        super(planet, tile);
    }
    @Override
    public String displayThreat(){
        float step = 0.25f;
        String color = Tmp.c1.set(Color.white).lerp(Color.scarlet, Mathf.round(threat, step)).toString();
        String[] threats = {"low", "medium", "high", "extreme", "eradication","siji"};
        int index = Math.min((int)(threat / step), threats.length - 1);
        return "[#" + color + "]" + Core.bundle.get("threat." + threats[index]);
    }
}
