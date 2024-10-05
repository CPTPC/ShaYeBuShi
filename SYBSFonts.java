package shayebushi;

import arc.Core;
import arc.freetype.FreeTypeFontGenerator;
import arc.freetype.FreetypeFontLoader;
import arc.graphics.Color;
import arc.graphics.g2d.Font;
import arc.struct.ObjectMap;
import mindustry.ui.Fonts;

public class SYBSFonts {
    public static ObjectMap<Integer, Font> fonts = new ObjectMap<>() ;
    public static void load() {
        if (true) {
            return ;
        }
        for (int i = 18 ; i <= 100 ; i ++) {
            int z = i ;
            Core.assets.load("default", Font.class, new FreetypeFontLoader.FreeTypeFontLoaderParameter("fonts/font.woff", new FreeTypeFontGenerator.FreeTypeFontParameter(){{
                size = z;
                //shadowColor = Color.darkGray;
                //shadowOffsetY = 2;
                incremental = true;
                borderColor = Color.darkGray;
            }})).loaded = f -> fonts.put(z, f);
        }
    }
}
