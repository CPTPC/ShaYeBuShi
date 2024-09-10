package shayebushi.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.util.Nullable;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Fires;
import mindustry.entities.Puddles;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.type.Liquid;
import mindustry.world.Tile;
import shayebushi.ShaYeBuShi;

import static mindustry.Vars.*;

public class LiquidBullet extends ArtilleryBulletType {
    public Liquid liquid;
    public float puddleSize = 6f * 26;
    public float orbSize = 2f;
    public float boilTime = 5f;
    public boolean rand = true ;

    public LiquidBullet() {
        super(3.5f, 0);

        ammoMultiplier = 1f;
        lifetime = 34f;
        statusDuration = 60f * 2f;
        despawnEffect = Fx.none;
        hitEffect = Fx.hitLiquid;
        smokeEffect = Fx.none;
        shootEffect = Fx.none;
        drag = 0.001f;
        knockback = 0.55f;
        displayAmmoMultiplier = false;
        hitSound = Sounds.explosion ;
    }

    public LiquidBullet(Liquid liquid) {
        this();
        if(liquid != null){
            this.liquid = liquid;
            this.status = liquid.effect;
            hitColor = liquid.color;
            lightColor = liquid.lightColor;
            lightOpacity = liquid.lightColor.a;
            rand = false;
        }
    }
    public LiquidBullet(float s, float d, String sp, Liquid liquid) {
        super(s, d, sp) ;
        if(liquid != null){
            this.liquid = liquid;
            this.status = liquid.effect;
            hitColor = liquid.color;
            lightColor = liquid.lightColor;
            lightOpacity = liquid.lightColor.a;
            rand = false;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void init(Bullet b) {
        super.init(b) ;
        if (!rand) return ;
        Liquid liquid = content.liquids().copy().filter(l -> !l.gas).get(ShaYeBuShi.r.random(0, content.liquids().copy().filter(l -> !l.gas).size - 1)) ;
        if(liquid != null){
            this.liquid = liquid;
            this.status = liquid.effect;
            hitColor = liquid.color;
            lightColor = liquid.lightColor;
            lightOpacity = liquid.lightColor.a;
        }
    }

    @Override
    public void update(Bullet b){
        super.update(b);

        if(liquid.willBoil() && b.time >= Mathf.randomSeed(b.id, boilTime)){
            Fx.vaporSmall.at(b.x, b.y, liquid.gasColor);
            b.remove();
            return;
        }

        if(liquid.canExtinguish()){
            Tile tile = world.tileWorld(b.x, b.y);
            if(tile != null && Fires.has(tile.x, tile.y)){
                Fires.extinguish(tile, 100f);
                b.remove();
                hit(b);
            }
        }
    }

    @Override
    public void draw(Bullet b){
        super.draw(b);
        if(liquid.willBoil()){
            Draw.color(liquid.color, Tmp.c3.set(liquid.gasColor).a(0.4f), b.time / Mathf.randomSeed(b.id, boilTime));
            Fill.circle(b.x, b.y, orbSize * (b.fin() * 1.1f + 1f));
        }else{
            Draw.color(liquid.color, Color.white, b.fout() / 100f);
            Fill.circle(b.x, b.y, orbSize);
        }

        Draw.reset();
    }

    @Override
    public void despawned(Bullet b){
        super.despawned(b);

        //don't create liquids when the projectile despawns
        if(!liquid.willBoil()){
            hitEffect.at(b.x, b.y, b.rotation(), liquid.color);
        }
    }

    @Override
    public void hit(Bullet b, float hitx, float hity){
        hitEffect.at(hitx, hity, liquid.color);
        Puddles.deposit(world.tileWorld(hitx, hity), liquid, puddleSize);

        float intensity = 400f * puddleSize/6f;
        Fires.extinguish(world.tileWorld(hitx, hity), intensity);
        for(Point2 p : Geometry.d4){
            Fires.extinguish(world.tileWorld(hitx + p.x * tilesize, hity + p.y * tilesize), intensity);
        }
    }
}
