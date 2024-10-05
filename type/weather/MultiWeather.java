package shayebushi.type.weather;

import arc.func.Prov;
import arc.struct.Seq;
import mindustry.game.EventType;
import mindustry.gen.WeatherState;
import mindustry.type.Weather;

public class MultiWeather extends Weather {
    public Seq<Weather> weathers = new Seq<>() ;
    public MultiWeather(String name, Prov<WeatherState> type) {
        super(name, type);
    }

    public MultiWeather(String name) {
        super(name);
    }
    public MultiWeather(String name, Weather... ws){
        this(name) ;
        weathers.addAll(ws) ;
    }
    @Override
    public void update(WeatherState state){
        for (Weather we : weathers){
            we.update(state);
        }
    }
    @Override
    public void updateEffect(WeatherState state){
        for (Weather we : weathers){
            we.updateEffect(state);
        }
    }
    @Override
    public void drawOver(WeatherState state){
        for (Weather we : weathers){
            we.drawOver(state);
        }
    }

    @Override
    public void drawUnder(WeatherState state){
        for (Weather we : weathers){
            we.drawUnder(state);
        }
    }

}
