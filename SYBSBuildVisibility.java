package shayebushi;

import mindustry.core.Version;
import mindustry.world.meta.BuildVisibility;

public class SYBSBuildVisibility {
    public static BuildVisibility tiaoshiOnly = new BuildVisibility(() -> Version.build == -1 || ShaYeBuShi.tiaoshi) ;
}
