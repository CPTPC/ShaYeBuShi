package shayebushi.entities.part;

import arc.graphics.g2d.Lines;
import mindustry.entities.part.DrawPart;
import mindustry.graphics.Drawf;

public class LinePart extends DrawPart {
    public PartProgress progress = PartProgress.warmup;
    public float lengthFrom, lengthTo ;
    @Override
    public void draw(PartParams params) {
        float prog = progress.getClamp(params) ;

    }

    @Override
    public void load(String name) {

    }
}
