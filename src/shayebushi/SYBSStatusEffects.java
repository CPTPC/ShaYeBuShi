package shayebushi;

import mindustry.type.StatusEffect;

public class SYBSStatusEffects {
    public static StatusEffect dangji ;
    public static void load(){
        dangji = new StatusEffect("dangji"){{
            speedMultiplier = 0 ;
            reloadMultiplier = 0 ;
        }};
    }
}
