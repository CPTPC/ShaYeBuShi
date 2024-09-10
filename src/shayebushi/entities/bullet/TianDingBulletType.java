package shayebushi.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Nullable;
import arc.util.Time;
import mindustry.ctype.ContentType;
import mindustry.entities.Mover;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.gen.Entityc;
import mindustry.gen.Teamc;
import mindustry.graphics.Trail;
import mindustry.type.Item;
import mindustry.type.UnitType;
import mindustry.world.Block;
import shayebushi.ShaYeBuShi;

import java.util.Objects;

import static mindustry.Vars.*;

public class TianDingBulletType extends BasicBulletType {
    public int status = 0 ;
    public Block block ;
    public UnitType unit ;
    public int blockId, unitId ;
    public float len, wid ;
    @Override
    public void init() {
        super.init();
        trailLength = (int)(len / tilesize) ;
        trailWidth = width / 2 ;
        lifetime = (len * 2 + wid) / speed ;
        pierce = true ;
        trailColor = new Color(0, 0, 0, 0) ;
        if (status == 0) {
            block = content.getByID(ContentType.block, blockId) ;
        }
        if (status == 1) {
            unit = content.getByID(ContentType.unit, unitId) ;
        }
    }
    @Override
    public @Nullable
    Bullet create(@Nullable Entityc owner, @Nullable Entityc shooter, Team team, float x, float y, float angle, float damage, float velocityScl, float lifetimeScl, Object data, @Nullable Mover mover, float aimX, float aimY){
        Bullet b = super.create(owner, shooter, team, x, y, angle, damage, velocityScl, lifetimeScl, data, mover, aimX, aimY) ;
        if (status == 0 && block != null) {
            b.data = new Data(Objects.requireNonNull(ShaYeBuShi.random(ShaYeBuShi.r, block.requirements)).item, len + ShaYeBuShi.r.random(-5 * tilesize, 5 * tilesize), wid + ShaYeBuShi.r.random(-2.5f * tilesize, 2.5f * tilesize));
        }
        else if (status == 1 && unit != null) {
            b.data = new Data(Objects.requireNonNull(ShaYeBuShi.random(ShaYeBuShi.r, unit.getFirstRequirements())).item, len + ShaYeBuShi.r.random(-5 * tilesize, 5 * tilesize), wid + ShaYeBuShi.r.random(-2.5f * tilesize, 2.5f * tilesize));
        }
        if (b.data instanceof Data d) {
            b.lifetime = d.lifetime ;
            d.rawRotation = b.rotation() ;
        }
        return b ;
    }
    @Override
    public void update(Bullet b) {
        super.update(b);
        if (!(b.data instanceof Data d)) return ;
        if (speed * b.time >= d.len && d.status == 0) {
            d.status = 1 ;
            //b.time = 0 ;
        }
        if (speed * b.time >= d.wid + d.len && d.status == 1) {
            d.status = 0 ;
            b.rotation(d.rawRotation + 180);
            //b.time = 0 ;
        }
        if (d.status == 0) {
            b.rotation(b.rotation() + 10f / d.len * speed);
        }
        if (d.status == 1) {
            b.rotation(b.rotation() + 180f / d.wid * speed);
        }
    }
    @Override
    public void draw(Bullet b) {
        super.draw(b);
        if (!(b.data instanceof Data d)) return ;
        float shrink = shrinkInterp.apply(b.fout());
        float height = this.height * ((1f - shrinkY) + shrinkY * shrink);
        float width = this.width * ((1f - shrinkX) + shrinkX * shrink);
        float offset = -90 + (spin != 0 ? Mathf.randomSeed(b.id, 360f) + b.time * spin : 0f) + rotationOffset;
        Draw.rect(d.i.uiIcon, b.x, b.y, width, height, b.rotation() + offset);
    }
    @Override
    public void drawTrail(Bullet b) {
        if (!(b.data instanceof Data d)) return ;
        if(trailLength > 0 && b.trail != null){
            //draw below bullets? TODO
            float z = Draw.z();
            Draw.z(z - 0.0001f);
            b.trail.draw(d.i.color, trailWidth);
            Draw.z(z);
        }
    }
    public class Data {
        public Item i ;
        public int status = 0 ;
        public float rawRotation ;
        public float lifetime ;
        public float len ;
        public float wid ;
        public Data(Item i, float len, float wid) {
            this.i = i ;
            this.len = len ;
            this.wid = wid ;
            this.lifetime = (len * 2 + wid) / speed ;
        }
    }
}
