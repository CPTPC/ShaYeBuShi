package shayebushi.entities;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Font;
import arc.graphics.g2d.GlyphLayout;
import arc.math.Mathf;
import arc.math.Rand;
import arc.scene.ui.layout.Scl;
import arc.util.Align;
import arc.util.Time;
import arc.util.pooling.Pools;
import mindustry.gen.WorldLabel;
import mindustry.graphics.Drawf;
import mindustry.ui.Fonts;
import shayebushi.SYBSFonts;
import shayebushi.ShaYeBuShi;

public class TimedWorldLabel extends WorldLabel {
    public float lifetime = 30 ;
    public float time ;
    public String fangxiang1, fangxiang2 ;
    public float amount ;
    @Override
    public void update() {
        super.update();
        time += Time.delta;
        if (fangxiang1 == null) {
            fangxiang1 = new String[]{"left", "right", "up", "down"}[ShaYeBuShi.r.random(0, 3)] ;
        }
        while (fangxiang2 == null || fangxiang2 == fangxiang1) {
            fangxiang2 = new String[]{"left", "right", "up", "down"}[ShaYeBuShi.r.random(0, 3)] ;
        }
        if (fangxiang1 != null && fangxiang2 != null && fangxiang1 != fangxiang2 && false) {
            switch (fangxiang1) {
                case "left" -> x -= amount * 1 / 300;
                case "right" -> x += amount * 1 / 300;
                case "up" -> y += amount * 1 / 300;
                case "down" -> y -= amount * 1 / 300;
            }
            switch (fangxiang2) {
                case "left" -> x -= amount * 1 / 300;
                case "right" -> x += amount * 1 / 300;
                case "up" -> y += amount * 1 / 300;
                case "down" -> y -= amount * 1 / 300;
            }
        }
        if (time >= lifetime && lifetime != -1) {
            hide();
        }
    }
    @Override
    public void draw() {
        draw: {

        }
        worldlabel: {

            draw(text, x, y, z, flags, fontSize);
        }
    }
    public void draw(String text, float x, float y, float layer, int flags, float fontSize) {

        Draw.z(layer);
        float z = Drawf.text();
        Font font = (flags & flagOutline) != 0 ? Fonts.outline : Fonts.def;
        //font = SYBSFonts.fonts.get((int)Mathf.clamp(amount / 80, 18, 100)) ;
        GlyphLayout layout = Pools.obtain(GlyphLayout.class, GlyphLayout::new);
        boolean ints = font.usesIntegerPositions();
        font.setUseIntegerPositions(false);
        font.getData().setScale(0.25F / Scl.scl(1.0F) * fontSize);
        layout.setText(font, text);
        if ((flags & flagBackground) != 0) {
            Draw.color(0.0F, 0.0F, 0.0F, 0.3F);
            //ss
        }
        font.setColor(Color.white);
        font.draw(text, x, y, 0, Align.center, false);
        Draw.reset();
        Pools.free(layout);
        font.getData().setScale(1.0F);
        font.setColor(Color.white);
        font.setUseIntegerPositions(ints);
        Draw.z(z);
    }
}
