package shayebushi;

import arc.Core;
import arc.KeyBinds;
import arc.input.InputDevice;
import arc.input.KeyCode;

public enum SYBSBinding implements KeyBinds.KeyBind {
    zhujineng(KeyCode.z) ,
    fujineng(KeyCode.i),
    kongzhi(KeyCode.altLeft);
    private final KeyBinds.KeybindValue defaultValue;
    private final String category;

    SYBSBinding(KeyBinds.KeybindValue defaultValue, String category){
        this.defaultValue = defaultValue;
        this.category = category;
    }

    SYBSBinding(KeyBinds.KeybindValue defaultValue){
        this(defaultValue, null);
    }

    @Override
    public KeyBinds.KeybindValue defaultValue(InputDevice.DeviceType type){
        return defaultValue;
    }

    @Override
    public String category(){
        return category;
    }
}
