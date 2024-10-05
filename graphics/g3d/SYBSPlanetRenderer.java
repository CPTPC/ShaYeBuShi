package shayebushi.graphics.g3d;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.game.Rules;
import mindustry.game.Team;
import mindustry.graphics.g3d.PlanetRenderer;
import mindustry.maps.planet.ErekirPlanetGenerator;
import mindustry.maps.planet.SerpuloPlanetGenerator;
import mindustry.type.Sector;
import shayebushi.maps.planet.DeLeiKePlanetGenerator;

import static mindustry.Vars.player;

public class SYBSPlanetRenderer extends PlanetRenderer {
    public void drawSelection(Sector sector, Color color, float stroke, float length){
        Rules r = new Rules() ;
        sector.planet.ruleSetter.get(r) ;
        Color enemyColor = sector.save != null ? sector.save.meta.rules.waveTeam.color :
                sector.planet.generator instanceof SerpuloPlanetGenerator ? Team.crux.color : sector.planet.generator instanceof ErekirPlanetGenerator ? Team.malis.color : sector.planet.generator instanceof DeLeiKePlanetGenerator ? Team.green.color :
                        sector.hasSave() ? sector.save.meta.rules.waveTeam.color : r.waveTeam.color;
        color =
                sector.hasBase() ? Tmp.c2.set(player.team().color).lerp(enemyColor, sector.hasEnemyBase() ? 0.5f : 0f) :
                        sector.preset != null ?
                                sector.preset.unlocked() ? Tmp.c2.set(Team.derelict.color).lerp(Color.white, Mathf.absin(Time.time, 10f, 1f)) :
                                        Color.gray :
                                sector.hasEnemyBase() ? enemyColor :
                                        new Color();
        sector.planet.drawSelection(batch, sector, color, stroke, length);
    }

}
