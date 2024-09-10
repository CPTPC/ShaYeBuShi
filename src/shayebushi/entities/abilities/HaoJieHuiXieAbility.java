package shayebushi.entities.abilities ;

import arc.Core;
import arc.Events ;
import arc.util.Time ;
import mindustry.entities.abilities.Ability ;
import mindustry.game.EventType;
import mindustry.gen.Unit ;

public class HaoJieHuiXieAbility extends Ability {
    public float heal = 1 , reload = 60 , jisha = 0 , youdunbeilv = 1;
    public boolean youdun = true , kejisha = false ;
    public int timer = 0 ;
    public HaoJieHuiXieAbility(float heal, float reload, float jisha, float youdunbeilv, boolean youdun, boolean kejisha) {
        this.heal = heal ;
        this.reload = reload ;
        this.jisha = jisha ;
        this.youdun = youdun ;
        this.kejisha = kejisha ;
        this.youdunbeilv = youdunbeilv ;
    }
    public HaoJieHuiXieAbility() {
        this.heal = 1000 ;
        this.reload = 60 ;
        this.jisha = 0 ;
        this.youdun = true ;
        this.kejisha = false ;
        this.youdunbeilv = 1 ;
    }
    public HaoJieHuiXieAbility(int i) {
        this.heal = 1000 ;
        this.reload = 60 ;
        this.jisha = 500 ;
        this.youdun = false ;
        this.kejisha = true ;
        this.youdunbeilv = 2.5f ;
    }
    @Override
    public void update(Unit u) {
        timer += Time.delta ;
        if (timer >= reload) {
            if (youdun) {
                if (u.shield >= 1) {
                    if (youdunbeilv > 1) {
                        u.heal(heal * youdunbeilv);
                    }
                    else {
                        u.heal(heal) ;
                    }
                }
            }
            else {
                u.heal(heal) ;
            }
            timer = 0 ;
        }
        Events.on(EventType.UnitBulletDestroyEvent.class , event -> {
            if (event.unit == u && kejisha){
                u.heal(jisha) ;
            }
        }) ;
    }
    @Override
    public String localized() {
        return Core.bundle.format("ability.haojiehuixie", 60 / reload * heal, jisha);
    }
}
