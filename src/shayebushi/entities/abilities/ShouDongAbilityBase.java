package shayebushi.entities.abilities;

import arc.Core;
import arc.KeyBinds;
import mindustry.entities.abilities.Ability;
import mindustry.input.Binding;

import static arc.Core.keybinds;

public abstract class ShouDongAbilityBase extends Ability {
    public KeyBinds.KeyBind key ;
    public KeyBinds.KeyBind key2 ;
    @Override
    public String localized(){
        var type = getClass() ;
        return Core.bundle.format("ability." + (type.isAnonymousClass() ? type.getSuperclass() : type).getSimpleName().replace("Ability", "").toLowerCase(), get(key), get(key2));
    }
    public String get(KeyBinds.KeyBind keyBind){
        if (keyBind != null) {
            return keybinds.get(keyBind).key == null ? "" : keybinds.get(keyBind).key.toString();
        }
        return "" ;
    }
}
