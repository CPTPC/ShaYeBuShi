package shayebushi.type.weather;

import arc.flabel.effects.RainbowEffect;
import arc.func.Prov;
import arc.graphics.Color;
import arc.util.Time;
import mindustry.Vars;
import mindustry.gen.WeatherState;
import mindustry.graphics.Drawf;
import mindustry.type.Weather;
import mindustry.type.weather.RainWeather;
import mindustry.world.meta.Attribute;
import shayebushi.ShaYeBuShi;

public class LeiYuWeather extends RainWeather {

    public float lI = 300 ;
    public float lS = 5 ;
    public float lt = 0 ;
    public float lr = 114514 ;
    public Color lc = Color.valueOf("00000000") ;
    public float dr = 30 * Vars.tilesize ;
    public Color dc = Color.black ;

    public LeiYuWeather(String name) {
        super(name);
        attrs.set(Attribute.light, -0.85f);
    }

    @Override
    public void update(WeatherState state){
        super.update(state);
        if (lt + Time.delta >= lI){
            lt = 0 ;
        }
        lt += Time.delta ;
    }

    @Override
    public void drawOver(WeatherState state){
        super.drawOver(state);
        //drawDark(state);
        if (lt + ShaYeBuShi.r.random(-Time.delta * 2, Time.delta * 3) >= lI){
            drawLightning(state);
        }
    }

    @Override
    public void drawUnder(WeatherState state){
        super.drawUnder(state);
        //drawDark(state);
        if (lt + ShaYeBuShi.r.random(-Time.delta * 2, Time.delta * 3) >= lI){
            drawLightning(state);
        }
    }

    public void drawLightning(WeatherState state){
        Drawf.light(state.x, state.y, dr, lc, 1);
    }

    public void drawDark(WeatherState state){
        Drawf.light(state.x, state.y, lr, dc, 1);
    }
}
