package shayebushi;

import arc.*;
import arc.util.*;
//import mindustry.*;
//import mindustry.content.*;
import mindustry.game.EventType.*;
//import mindustry.gen.*;
//import mindustry.mod.*;
import mindustry.mod.Mod;
//import mindustry.ui.dialogs.*;
import mindustry.ui.dialogs.BaseDialog;

public class ShaYeBuShi extends Mod {

    public ShaYeBuShi(){
        Log.info("Loaded ExampleJavaMod constructor.");

        //listen for game load event
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("frog");
                dialog.cont.add("behold").row();
                //mod sprites are prefixed with the mod name (this mod is called 'example-java-mod' in its config)
                dialog.cont.image(Core.atlas.find("example-java-mod-frog")).pad(20f).row();
                dialog.cont.button("I see", dialog::hide).size(100f, 50f);
                dialog.show();
            });
        });
    }

    @Override
    public void loadContent(){
        Log.info("Loading some example content.");
        SYBSStatusEffects.load();
        SYBSBlocks.load();
        SYBSUnitTypes.load();
    }

}
