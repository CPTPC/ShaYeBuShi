package shayebushi;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.Colors;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.util.Structs;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.abilities.ForceFieldAbility;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.effect.WrapEffect;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.blocks.defense.ForceProjector;
import shayebushi.entities.abilities.ZhenDangHuDunAbility;
import shayebushi.entities.units.JieTiUnit;
import shayebushi.entities.units.JieTic;
import shayebushi.entities.units.TouBuUnit;
import shayebushi.type.unit.JieTiUnitType;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.randLenVectors;
import static mindustry.Vars.*;
import static mindustry.content.Fx.rand;
import static shayebushi.SYBSPal.*;


public class SYBSFx {
    public static Effect jidianbaozha = new MultiEffect(
        new Effect(150,3200,e -> {
            Draw.z(Layer.bullet + 0.1f) ;
//            Draw.color(Color.valueOf("000000ff"));
//            Fill.circle(e.x,e.y,40);
            Draw.color(Pal.redderDust);
            int angle = 0 ;
            Rand r = new Rand() ;
            float xo = r.random(8, 24) ;
            float yo = r.random(8, 24) ;
            if (!state.isPaused()) {
                Effect.shake(7, 7, e.x, e.y);
                Sounds.explosionbig.at(e.x, e.y);
            }
            for (int i = 1 ; i <= 15 ; i ++){
                xo += angle % 20 ;
                yo += angle % 20 ;
                Draw.color(Color.white);
                Drawf.tri(e.x + xo,e.y + yo,80 / e.lifetime * (e.lifetime - e.time),320 / 64f * 80,360f / 15 + angle);
                Drawf.tri(e.x + xo,e.y + yo,80 / e.lifetime * (e.lifetime - e.time),80,360f / 15 + angle + 180);
                Draw.color(Pal.redderDust);
                Drawf.tri(e.x + xo,e.y + yo,64 / e.lifetime * (e.lifetime - e.time),320,360f / 15 + angle);
                Drawf.tri(e.x + xo,e.y + yo,64 / e.lifetime * (e.lifetime - e.time),64,360f / 15 + angle + 180);
                angle += 360f / 15 ;
            }
            Lines.circle(e.x, e.y, 460 * (1 + e.fin()));
            Draw.reset();
        }),
        new MultiEffect(Fx.bigShockwave, new WrapEffect(Fx.titanSmoke, Color.valueOf("e3ae6f")))
    ),
    huan = new Effect(80f, 700f, e -> {
        color(Pal.redderDust);
        stroke(e.fin() * 4f);
        Lines.circle(e.x, e.y, 4f + e.fout() * 700f);

        //Fill.circle(e.x, e.y, e.fin() * 200);

        randLenVectors(e.id, 20, 40f * e.fout(), (x, y) -> {
            //Fill.circle(e.x + x, e.y + y, e.fin() * 30f);
            Drawf.light(e.x + x, e.y + y, e.fin() * 120f, Pal.redderDust, 0.7f);
        });

        color();

        //Fill.circle(e.x, e.y, e.fin() * 100);
        Drawf.light(e.x, e.y, e.fin() * 180f, Pal.heal, 0.7f);
    }).followParent(true).rotWithParent(true),
    fanhuan = new Effect(80f, 1000f, e -> {
        color(Pal.redderDust);
        stroke(e.fout() * 4f);
        Lines.circle(e.x, e.y, 4f + e.fout() * 2000f);
        Fill.circle(e.x, e.y, e.fout() * 400);
        randLenVectors(e.id, 20, 40f * e.fout(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 100f);
            Drawf.light(e.x + x, e.y + y, e.fout() * 300f, Pal.redderDust, 0.7f);
        });
        color();
        Fill.circle(e.x, e.y, e.fout() * 200);
        Drawf.light(e.x, e.y, e.fout() * 400f, Pal.heal, 0.7f);
    }).followParent(true).rotWithParent(true),
    jidianxiyin = new MultiEffect(
        new Effect(90,e -> {
            Draw.z(Layer.bullet + 0.1f) ;
            Draw.color(Pal.redderDust);
//            Lines.stroke(5);
//            Lines.circle(e.x, e.y, 50 / e.fin());
//            Lines.circle(e.x, e.y, 60 / e.fin());
            Draw.color(Color.valueOf("000000ff"));
            Fill.circle(e.x,e.y,40);
            Draw.reset();
        }),
        huan,huan,huan
    ).followParent(true).rotWithParent(true),
    jidianshifang = new MultiEffect(
        new Effect(90,e -> {
            Draw.z(Layer.bullet + 0.1f) ;
            Draw.color(Pal.redderDust);
//            Lines.stroke(5);
//            Lines.circle(e.x, e.y, 50 * e.fin());
//            Lines.circle(e.x, e.y, 60 * e.fin());
            Draw.color(Color.valueOf("000000ff"));
            Fill.circle(e.x,e.y,40);
            Draw.reset();
        }),
        fanhuan,fanhuan,fanhuan
    ).followParent(true).rotWithParent(true),
    huoyanShoot = new MultiEffect(new ParticleEffect(){{
        particles = 2 ;
        sizeFrom = 6 ;
        sizeTo = 1 ;
        length = 160 ;
        lifetime = 30 ;
        interp = Interp.circleOut ;
        sizeInterp = Interp.pow5In ;
        lightColor = Pal.lightFlame ;
        colorFrom = Pal.darkFlame ;
        colorTo = Pal.lightFlame ;
        cone = 10 ;
    }},new ParticleEffect(){{
        particles = 2 ;
        sizeFrom = 7 ;
        sizeTo = 1 ;
        length = 140 ;
        lifetime = 35 ;
        interp = Interp.circleOut ;
        sizeInterp = Interp.pow5In ;
        lightColor = Pal.lightFlame ;
        colorFrom = Pal.darkFlame ;
        colorTo = Pal.lightFlame ;
        cone = 15 ;
    }},new ParticleEffect(){{
        particles = 2 ;
        sizeFrom = 7 ;
        sizeTo = 1.5f ;
        length = 150 ;
        lifetime = 40 ;
        interp = Interp.fastSlow ;
        //sizeInterp = Interp.pow5In ;
        lightColor = Pal.lightFlame ;
        colorFrom = Pal.darkFlame ;
        colorTo = Pal.lightFlame ;
        cone = 15 ;
    }},new ParticleEffect(){{
        particles = 2 ;
        sizeFrom = 7 ;
        sizeTo = 1.5f ;
        length = 155 ;
        lifetime = 50 ;
        interp = Interp.fastSlow ;
        //sizeInterp = Interp.pow5In ;
        lightColor = Pal.lightFlame ;
        colorFrom = Pal.darkFlame ;
        colorTo = Pal.lightFlame ;
        cone = 10 ;
    }}){{
        layer = 110 ;
    }},
    huoyanSmoke = new MultiEffect(new ParticleEffect(){{
        particles = 3 ;
        sizeFrom = 6 ;
        sizeTo = 1 ;
        length = 200 ;
        lifetime = 50 ;
        lightOpacity = 0 ;
        interp = Interp.fastSlow ;
        //sizeInterp = Interp.fastSlow ;
        //lightColor = Pal.lightFlame ;
        colorFrom = Pal.darkFlame ;
        colorTo = Pal.lightFlame ;
        cone = 15 ;
    }},new ParticleEffect(){{
        particles = 3 ;
        sizeFrom = 8 ;
        sizeTo = 1 ;
        length = 220 ;
        lifetime = 60 ;
        lightOpacity = 0 ;
        interp = Interp.pow5In ;
        sizeInterp = Interp.fastSlow ;
        //lightColor = Pal.lightFlame ;
        colorFrom = Pal.darkFlame ;
        colorTo = Pal.lightFlame ;
        cone = 15 ;
    }}){{
        layer = 109 ;
    }},
    huoyanHit = new MultiEffect(new ParticleEffect(){{
        particles = 4 ;
        line = true ;
        //sizeFrom = 6 ;
        //sizeTo = 1 ;
        length = 200 ;
        offset = 10 ;
        lifetime = 65 ;
        lightOpacity = 0 ;
        interp = Interp.circleOut ;
        //sizeInterp = Interp.fastSlow ;
        lenFrom = 8 ;
        lenTo = 0 ;
        strokeFrom = 3 ;
        strokeTo = 0 ;
        //lightColor = Pal.lightFlame ;
        colorFrom = Pal.darkFlame ;
        colorTo = Pal.lightFlame ;
        cone = -30 ;
    }}),
    jiange = new Effect(30, e -> {
        for(int i = 0; i < 2; i++){
            color(i == 0 ? Pal.items : Pal.heal);

            float m = i == 0 ? 1f : 0.5f;

            float rot = e.rotation + 180f - 45f;
            float w = 15f * e.fout() * m;
            Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot);
            Drawf.tri(e.x, e.y, w, 10f * m, rot + 90f);
        }

        Drawf.light(e.x, e.y, 60f, Pal.items, 0.6f * e.fout());
    }),    jiangeHuang = new Effect(30, e -> {
        for(int i = 0; i < 2; i++){
            color(i == 0 ? Pal.accent : Pal.accentBack);

            float m = i == 0 ? 1f : 0.5f;

            float rot = e.rotation + 180f - 45f;
            float w = 15f * e.fout() * m;
            Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot);
            Drawf.tri(e.x, e.y, w, 10f * m, rot + 90f);
        }

        Drawf.light(e.x, e.y, 60f, Pal.accent, 0.6f * e.fout());
    }),
    fixedInstShoot = new Effect(24f, e -> {
        e.scaled(10f, b -> {
            color(Color.white, e.color, b.fin());
            stroke(b.fout() * 3f + 0.2f);
            Lines.circle(b.x, b.y, b.fin() * 50f);
        });

        color(e.color);

        for(int i : Mathf.signs){
            Drawf.tri(e.x, e.y, 13f * e.fout(), 85f, e.rotation + 90f * i);
            Drawf.tri(e.x, e.y, 13f * e.fout(), 50f, e.rotation + 20f * i);
        }

        Drawf.light(e.x, e.y, 180f, e.color, 0.9f * e.fout());
    }),
    fixedInstTrail = new Effect(30, e -> {
        for(int i = 0; i < 2; i++){
            color(i == 0 ? e.color : e.color);

            float m = i == 0 ? 1f : 0.5f;

            float rot = e.rotation + 180f;
            float w = 15f * e.fout() * m;
            Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot);
            Drawf.tri(e.x, e.y, w, 10f * m, rot + 180f);
        }

        Drawf.light(e.x, e.y, 60f, e.color, 0.6f * e.fout());
    }),fixedInstBomb = new Effect(15f, 100f, e -> {
        color(e.color);
        stroke(e.fout() * 4f);
        Lines.circle(e.x, e.y, 4f + e.finpow() * 20f);

        for(int i = 0; i < 4; i++){
            Drawf.tri(e.x, e.y, 6f, 80f * e.fout(), i*90 + 45);
        }

        color();
        for(int i = 0; i < 4; i++){
            Drawf.tri(e.x, e.y, 3f, 30f * e.fout(), i*90 + 45);
        }

        Drawf.light(e.x, e.y, 150f, e.color, 0.9f * e.fout());
    }), fixedInstHit = new Effect(20f, 200f, e -> {
        color(e.color);

        for(int i = 0; i < 2; i++){
            color(i == 0 ? e.color : e.color);

            float m = i == 0 ? 1f : 0.5f;

            for(int j = 0; j < 5; j++){
                float rot = e.rotation + Mathf.randomSeedRange(e.id + j, 50f);
                float w = 23f * e.fout() * m;
                Drawf.tri(e.x, e.y, w, (80f + Mathf.randomSeedRange(e.id + j, 40f)) * m, rot);
                Drawf.tri(e.x, e.y, w, 20f * m, rot + 180f);
            }
        }

        e.scaled(10f, c -> {
            color(e.color);
            stroke(c.fout() * 2f + 0.2f);
            Lines.circle(e.x, e.y, c.fin() * 30f);
        });

        e.scaled(12f, c -> {
            color(Pal.bulletYellowBack);
            randLenVectors(e.id, 25, 5f + e.fin() * 80f, e.rotation, 60f, (x, y) -> {
                Fill.square(e.x + x, e.y + y, c.fout() * 3f, 45f);
            });
        });
    }), shengchan1 = new Effect(30, e -> {
        randLenVectors(e.id, 10, 24 + e.fin() * 12, (x, y) -> {
            color(Pal.place, Color.lightGray, e.fin());
            Fill.square(e.x + x, e.y + y, 0.2f + e.fout() * 2f, 45);
            if (!Vars.state.isPaused()) {
                Fx.chainLightning.at(e.x + x, e.y + y, 0, Pal.place, new Vec2(e.x, e.y));
            }
        });
    }), shengchan2 = new Effect(20, e -> {
        randLenVectors(e.id, 20,  e.fin() * 12, (x, y) -> {
            color(SYBSItems.you.color, Color.lightGray, e.fin());
            Fill.circle(e.x + x, e.y + y, 0.2f + e.fout() * 2f);
        });
    }), shengchan3 = new MultiEffect(new ParticleEffect(){{
        particles = 3 ;
        sizeFrom = 6 ;
        sizeTo = 2 ;
        length = 150 ;
        lifetime = 50 ;
        lightOpacity = 0 ;
        interp = Interp.fastSlow ;
        //sizeInterp = Interp.fastSlow ;
        //lightColor = Pal.lightFlame ;
        colorFrom = SYBSLiquids.nongliusuan.color ;
        colorTo = SYBSItems.you.color;
        cone = 15 ;
    }},new ParticleEffect(){{
        particles = 3 ;
        sizeFrom = 8 ;
        sizeTo = 3 ;
        length = 170 ;
        lifetime = 60 ;
        lightOpacity = 0 ;
        interp = Interp.pow5In ;
        sizeInterp = Interp.fastSlow ;
        //lightColor = Pal.lightFlame ;
        colorFrom = SYBSLiquids.nongliusuan.color ;
        colorTo = SYBSItems.you.color;
        cone = 15 ;
    }}){{
        layer = 109 ;
    }}, shengchan4 = new Effect(60, e -> {
        for (int i = 0 ; i < 360 ; i += 10) {
            Vec2 v = ShaYeBuShi.circle(i, 3.5f * tilesize * e.fin(), e.x, e.y) ;
            Draw.color(Pal.lightishGray);
            Fill.circle(v.x, v.y, 0.2f + e.fout() * 2f);
        }
    }), baozha1 = new Effect(60f, 160f, e -> {
        color(e.color);
        stroke(e.fout() * 3f);
        float circleRad = 12f + e.finpow() * 60f;
        Lines.circle(e.x, e.y, circleRad);
        color(e.color, 0.5f * e.fout());
        Fill.circle(e.x, e.y, circleRad);
        rand.setSeed(e.id);
        Draw.color(Color.white);
        for(int i = 0; i < circleRad * 2 * Math.PI / 20; i++){
            float angle = rand.random(360f);
            float lenRand = rand.random(5f, 10f) * tilesize / 2f ;
            Vec2 v = ShaYeBuShi.circle(360 / (float)(circleRad * 2 * Math.PI / 20) * i, circleRad, e.x, e.y) ;
            Drawf.tri(v.x, v.y, e.fout() * 3.5f * tilesize / 2f, lenRand, Angles.angle(v.x, v.y, e.x, e.y));
        }
        if (!Vars.state.isPaused()) {
            Sounds.explosionbig.at(e.x, e.y) ;
            Effect.shake(e.fout() * 10, e.fout() * 10, e.x, e.y);
        }
    }), kongzhi = new Effect(15, e -> {
        if (!(e.data instanceof Unit u)) return ;
        Draw.color(u.team.color);
        Lines.poly(u.x, u.y, 4, u.hitSize);
    }), kongzhiguanghuan = new Effect(15, e -> {
        Draw.color(e.data instanceof Unit u ? u.team.color : e.color);Lines.circle(e.x, e.y, e.fin() * (e.data instanceof Unit u ? u.type.range : 10 * tilesize));
    }), guidaopaolujing = new Effect(30, e -> {
        for(int i = 0; i < 2; i++){
            color(i == 0 ? e.color : e.color.cpy().mul(new Color(0.4f, 0.4f, 0.4f)));

            float m = i == 0 ? 1f : 0.5f;

            float rot = e.rotation + 180f;
            float w = 15f * e.fout() * m;
            Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot);
            Drawf.tri(e.x, e.y, w, 10f * m, rot + 180f);
            Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot + 90f);
            Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot - 90f);
        }

        Drawf.light(e.x, e.y, 60f, e.color, 0.6f * e.fout());
    }), guidaopaomingzhong = new MultiEffect(Fx.massiveExplosion, new WrapEffect(new Effect(80f, 100f, e -> {
        color(e.color);
        stroke(e.fout() * 2f);
        float circleRad = (4f + e.finpow() * e.rotation)*2;
        Lines.circle(e.x, e.y, circleRad);

        for(int i = 0; i < 6; i++){
            Drawf.tri(e.x, e.y, 2*6f, (e.rotation * 1.5f * e.fout())*4, i*90);
        }

        color();
        for(int i = 0; i < 6; i++){
            Drawf.tri(e.x, e.y, 2*3f, 4*(e.rotation * 1.45f / 3f * e.fout()), i*90);
        }

        Drawf.light(e.x, e.y, circleRad * 1.6f, Pal.heal, e.fout());
    }), Pal.accent, 24f), new WaveEffect(){{
        //colorFrom = colorTo = color;
        colorFrom = colorTo = Pal.accentBack;
        sizeTo = 80f;
        lifetime = 30f;
        strokeFrom = 4f;
    }}), hedanbaozha = new MultiEffect(new Effect(30, e -> {
        Drawf.light(e.x, e.y, 80 * tilesize, e.color, 10 * e.fout());
        //Lines.circle(e.x, e.y, e.fin() * 100 * tilesize + 40);
    }), new Effect(30, 640f, b -> {
        float intensity = 20;
        float baseLifetime = 25f + intensity * 11f;
        b.lifetime = 50f + intensity * 65f;
        //Lines.circle(b.x, b.y, b.fin() * 100 * tilesize + 40);
        color(Pal.reactorPurple);
        alpha(0.7f);
        for(int i = 0; i < 4; i++){
            rand.setSeed(b.id*2 + i);
            float lenScl = rand.random(0.4f, 1f);
            int fi = i;
            b.scaled(b.lifetime * lenScl, e -> {
                randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int)(2.9f * intensity), 22f * intensity, (x, y, in, out) -> {
                    float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                    float rad = fout * ((2f + intensity) * 2.35f);

                    Fill.circle(e.x + x, e.y + y, rad);
                    Drawf.light(e.x + x, e.y + y, rad * 2.5f, Pal.reactorPurple2, 0.5f);
                });
            });
        }

        b.scaled(baseLifetime, e -> {
            Draw.color();
            e.scaled(5 + intensity * 2f, i -> {
                stroke((3.1f + intensity/5f) * i.fout());
                Lines.circle(e.x, e.y, (3f + i.fin() * 14f) * intensity);
                Drawf.light(e.x, e.y, i.fin() * 14f * 2f * intensity, Color.white, 0.9f * e.fout());
            });

            color(Pal.lighterOrange, Pal.reactorPurple2, e.fin());
            stroke((2f * e.fout()));

            Draw.z(Layer.effect + 0.001f);
            randLenVectors(e.id + 1, e.finpow() + 0.001f, (int)(8 * intensity), 28f * intensity, (x, y, in, out) -> {
                lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + out * 4 * (4f + intensity));
                Drawf.light(e.x + x, e.y + y, (out * 4 * (3f + intensity)) * 3.5f, Draw.getColor(), 0.8f);
            });
        });
    })), fanhuanxiao = new Effect(60f, e -> {
        color(e.color);
        stroke(e.fout() * 4f);
        Lines.stroke(2);
        for (int i = 1 ; i <= 4 ; i ++) {
            Lines.circle(e.x, e.y, 4f + e.fout() * 20f * (1f / i));
        }
    }), lizi1 = new Effect(30, e -> {
        randLenVectors(e.id, 150, 30 + e.fin() * 12, (x, y) -> {
            color(Color.white, Color.lightGray, e.fin());
            Fill.square(e.x + x, e.y + y, 0.2f + e.fout() * 2f, 45);
        });
    }), baozha2 = new Effect(60f, 90f, e -> {
        color(e.color);
        stroke(e.fout() * 3f);
        float circleRad = 6f + e.finpow() * 30f;
        Lines.circle(e.x, e.y, circleRad);
        color(e.color, 0.5f * e.fout());
        Fill.circle(e.x, e.y, circleRad);
        rand.setSeed(e.id);
        Draw.color(Color.white);
        for(int i = 0; i < circleRad * 2 * Math.PI / 20; i++){
            float angle = rand.random(360f);
            float lenRand = rand.random(5f, 10f) * tilesize / 2f ;
            Vec2 v = ShaYeBuShi.circle(360 / (float)(circleRad * 2 * Math.PI / 20) * i, circleRad, e.x, e.y) ;
            Drawf.tri(v.x, v.y, e.fout() * 3.5f * tilesize / 2f, lenRand, Angles.angle(v.x, v.y, e.x, e.y));
        }
        if (!Vars.state.isPaused()) {
            Sounds.explosionbig.at(e.x, e.y) ;
            Effect.shake(e.fout() * 5, e.fout() * 5, e.x, e.y);
        }
    }), kuiluan = new Effect(5, e -> {
        if (!(e.data instanceof Unit)) return ;
        Draw.color(e.color);
        ((Unit)e.data).draw() ;
    }), hedanbaozhaxiao = new MultiEffect(new Effect(30, e -> {
        Drawf.light(e.x, e.y, 20 * tilesize, e.color, 10 * e.fout());
        //Lines.circle(e.x, e.y, e.fin() * 100 * tilesize + 40);
    }), new Effect(30, 160f, b -> {
        float intensity = 5;
        float baseLifetime = 25f + intensity * 11f;
        b.lifetime = 50f + intensity * 65f;
        //Lines.circle(b.x, b.y, b.fin() * 25 * tilesize + 10);
        color(Pal.reactorPurple);
        alpha(0.7f);
        for(int i = 0; i < 4; i++){
            rand.setSeed(b.id*2 + i);
            float lenScl = rand.random(0.4f, 1f);
            int fi = i;
            b.scaled(b.lifetime * lenScl, e -> {
                randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int)(2.9f * intensity), 22f * intensity, (x, y, in, out) -> {
                    float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                    float rad = fout * ((2f + intensity) * 2.35f);

                    Fill.circle(e.x + x, e.y + y, rad);
                    Drawf.light(e.x + x, e.y + y, rad * 2.5f, Pal.reactorPurple2, 0.5f);
                });
            });
        }

        b.scaled(baseLifetime, e -> {
            Draw.color();
            e.scaled(5 + intensity * 2f, i -> {
                stroke((3.1f + intensity/5f) * i.fout());
                Lines.circle(e.x, e.y, (3f + i.fin() * 14f) * intensity);
                Drawf.light(e.x, e.y, i.fin() * 14f * 2f * intensity, Color.white, 0.9f * e.fout());
            });

            color(Pal.lighterOrange, Pal.reactorPurple2, e.fin());
            stroke((2f * e.fout()));

            Draw.z(Layer.effect + 0.001f);
            randLenVectors(e.id + 1, e.finpow() + 0.001f, (int)(8 * intensity), 28f * intensity, (x, y, in, out) -> {
                lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + out * 4 * (4f + intensity));
                Drawf.light(e.x + x, e.y + y, (out * 4 * (3f + intensity)) * 3.5f, Draw.getColor(), 0.8f);
            });
        });
    })), xingliubaozha = new Effect(30, 500f, b -> {
        float intensity = b.rotation;
        float baseLifetime = 26f + intensity * 15f;
        b.lifetime = 43f + intensity * 35f;

        color(Pal.heal, Pal.redderDust, Pal.accent, b.fin());
        //TODO awful borders with linear filtering here
        alpha(0.9f);
        for(int i = 0; i < 4; i++){
            rand.setSeed(b.id*2 + i);
            float lenScl = rand.random(0.4f, 1f);
            int fi = i;
            b.scaled(b.lifetime * lenScl, e -> {
                randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int)(3f * intensity * 2f), 14f * intensity * 2f, (x, y, in, out) -> {
                    float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                    Fill.circle(e.x + x, e.y + y, fout * ((2f + intensity) * 1.8f) * 2f);
                });
            });
        }

        b.scaled(baseLifetime, e -> {
            e.scaled(5 + intensity * 2.5f, i -> {
                stroke((3.1f + intensity/5f) * i.fout());
                Lines.circle(e.x, e.y, (3f + i.fin() * 14f) * intensity);
                Drawf.light(e.x, e.y, i.fin() * 14f * 2f * intensity, Color.white, 0.9f * e.fout());
            });

            color(SYBSPal.yaofeng, Pal.accentBack, Pal.items, e.fin());
            stroke((1.7f * e.fout()) * (1f + (intensity - 1f) / 2f));

            Draw.z(Layer.effect + 0.001f);
            for (int i = 1 ; i <= 360 ; i += 10) {
                Vec2 v = ShaYeBuShi.circle(i, intensity * 11, e.x, e.y) ;
                Lines.arc(v.x, v.y, 1f + 4 * (3f + intensity), 1, i);
            }
        });
    }), pieces = new Effect(30, e -> {
        if (!(e.data instanceof JieTiUnit u && u.type instanceof JieTiUnitType j && u.owner instanceof TouBuUnit t)) {
            return ;
        }
        rand.setSeed(e.id);

        e.lifetime = rand.random(70f, 130f);

        TextureRegion tr = j.pieces.get(t.jietis.indexOf(u) % j.pieces.size) ;
        Tmp.v1.trns(rand.random(360f), rand.random(tr.width / 8f) * e.finpow());
        float ox = Tmp.v1.x, oy = Tmp.v1.y;

        alpha(e.foutpowdown());

        stroke(tr.height * scl);
        line(tr, u.x + ox, u.y + oy, u.x + ox, u.y + oy, false);
    }), jiange2 = new Effect(30, e -> {
        for(int i = 0; i < 2; i++){
            color(i == 0 ? Pal.redderDust : Pal.redLight);

            float m = i == 0 ? 1f : 0.5f;

            float rot = e.rotation + 180f - 45f;
            float w = 15f * e.fout() * m;
            Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot);
            Drawf.tri(e.x, e.y, w, 10f * m, rot + 90f);
        }

        Drawf.light(e.x, e.y, 60f, Pal.redderDust, 0.6f * e.fout());
    }), fixedShieldBreak = new Effect(40, e -> {
        color(e.color);
        stroke(3f * e.fout());
        if(e.data instanceof Unit u){
            var ab = (ForceFieldAbility) Structs.find(u.abilities, a -> a instanceof ForceFieldAbility);
            var abb = (ZhenDangHuDunAbility) Structs.find(u.abilities, a -> a instanceof ZhenDangHuDunAbility);
            if(ab != null){
                Lines.poly(e.x, e.y, ab.sides, ab.radius + e.fin() * tilesize, ab.rotation);
                return;
            }
            if(abb != null){
                Lines.poly(e.x, e.y, abb.sides, abb.radius + e.fin() * tilesize, abb.rotation);
                return;
            }
        }

        if (e.data instanceof ForceProjector f) {
            Lines.poly(e.x, e.y, f.sides, f.radius + e.fin() * tilesize);
        }
        Lines.poly(e.x, e.y, 6, e.rotation + e.fin());
    }).followParent(true),
    fixedForceShrink = new Effect(20, e -> {
        if (!(e.data instanceof Integer i)) return ;
        color(e.color, e.fout());
        if(renderer.animateShields){
            Fill.poly(e.x, e.y, i, e.rotation * e.fout());
        }else{
            stroke(1.5f);
            Draw.alpha(0.09f);
            Fill.poly(e.x, e.y, i, e.rotation * e.fout());
            Draw.alpha(1f);
            Lines.poly(e.x, e.y, i, e.rotation * e.fout());
        }
    }).layer(Layer.shields), shengchan5 = new Effect(30, e -> {
        randLenVectors(e.id, 20, 12 + e.fin() * 12, (x, y) -> {
            color(SYBSItems.ruangang.color, Color.lightGray, e.fin());
            Fill.square(e.x + x, e.y + y, 0.4f + e.fout() * 2f, 45);
        });
    }), shengchan6 = new Effect(30, e -> {
        randLenVectors(e.id, 5, e.fin() * 5f, (x, y) -> {
            color(SYBSItems.xiangjiao.color);
            Fill.square(e.x + x, e.y + y, e.fout() + 0.5f, 45);
        });
    }), fixedChainLightning = new Effect(20f, 300f, e -> {
        if(!(e.data instanceof Position p)) return;
        float tx = p.getX(), ty = p.getY(), dst = Mathf.dst(e.x, e.y, tx, ty);
        float z = Draw.z() ;
        Tmp.v1.set(p).sub(e.x, e.y).nor();

        float normx = Tmp.v1.x, normy = Tmp.v1.y;
        float range = e.rotation ;
        int links = Mathf.ceil(dst / range);
        float spacing = dst / links;

        Lines.stroke(2.5f * e.fout());
        if (e.color.r <= 0.2f && e.color.g <= 0.2f && e.color.b < 0.2f) {
            Draw.z(Layer.space) ;
        }
        Draw.color(Color.white, e.color, e.fin());

        Lines.beginLine();

        Lines.linePoint(e.x, e.y);

        rand.setSeed(e.id);

        for(int i = 0; i < links; i++){
            float nx, ny;
            if(i == links - 1){
                nx = tx;
                ny = ty;
            }else{
                float len = (i + 1) * spacing;
                Tmp.v1.setToRandomDirection(rand).scl(range/2f);
                nx = e.x + normx * len + Tmp.v1.x;
                ny = e.y + normy * len + Tmp.v1.y;
            }

            Lines.linePoint(nx, ny);
        }

        Lines.endLine();
        Draw.z(z) ;
    }).followParent(false).rotWithParent(false),
    jianbian = new Effect(300, e -> {
        if (!(e.data instanceof Float f)) return ;
        float from = e.rotation, to = f, radius = Mathf.lerp(from, to, e.fin()), raw = radius ;
        while (radius > 0.25f * raw) {
            color(e.color.cpy().a(radius / raw - 0.2f));
            Lines.stroke(radius / 16 + to / 1500);
            Lines.circle(e.x, e.y, radius);
            radius -= radius / 16 ;
        }
    }),
    chongjibo = new Effect(300, e -> {
        if (!(e.data instanceof Float f)) return ;
        float from = e.rotation, to = f, radius = Mathf.lerp(from, to, e.fin()) ;
        color(e.color.cpy().lerp(Color.white, Math.max(e.fin() - 0.5f, 0)));
        Lines.stroke(3);
        Lines.circle(e.x, e.y, radius);
        for (int i = 0 ; i < 10 ; i ++) {
            rand.setSeed(e.id + i);
            float angle = rand.random(360f) ;
            float length = radius * 0.75f * Math.max(angle / 360, 0.5f)  ;
            float width = radius * (float) Math.PI * 2 / Math.max(angle, 180) * e.fout() * 5f ;
            Vec2 v = ShaYeBuShi.circle(angle, radius, e.x, e.y) ;
            Drawf.tri(v.x, v.y, width, length, angle + 180);
        }
    }), baozha4 = new Effect(300, b -> {
        float intensity = 20 / 10f ;
        float baseLifetime = 26f + intensity * 15f;
        b.lifetime = 43f + intensity * 35f;
        b.scaled(baseLifetime, e -> {
            color(e.color);
            stroke((1.7f * e.fin()) * (1f + (intensity - 1f) / 2f) * 3);

            Draw.z(Layer.effect + 0.001f);
            randLenVectors(e.id + 1, e.finpow() + 0.001f, (int)(9 * intensity), 40f * intensity, (x, y, in, out) -> {
                lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + out * 4 * (3f + intensity) * 2);
                Drawf.light(e.x + x, e.y + y, (out * 4 * (3f + intensity)) * 3.5f, Draw.getColor(), 0.8f);
            });
        });

    }), baozha3 = new MultiEffect(jianbian, chongjibo),
    baozha5 = new Effect(120, e -> {
        float z = Draw.z() ;
        for (int i = 0 ; i < 360 ; i += 90) {
            Draw.color(e.color);
            Drawf.tri(e.x, e.y, 2 * tilesize * e.fout(), 8 * tilesize, i);
            Draw.color(Color.white);
            Drawf.tri(e.x, e.y, 1.75f * tilesize * e.fout(), 7 * tilesize, i);
            Draw.z(Layer.endPixeled);
            Draw.color(Color.black);
            Drawf.tri(e.x, e.y, 1.5f * tilesize * e.fout(), 6 * tilesize, i);
            Draw.z(z) ;
        }
        Draw.color(e.color);
        Fill.circle(e.x, e.y, 2 * tilesize * e.fout()) ;
        Draw.color(Color.white);
        Fill.circle(e.x, e.y, 1.75f * tilesize * e.fout()) ;
        Draw.z(Layer.endPixeled);
        Draw.color(Color.black);
        Fill.circle(e.x, e.y, 1.5f * tilesize * e.fout()) ;
        Draw.reset();
        Draw.z(z) ;
    }), baozha6 = new Effect(43 + 70, e -> {
        float from = 0, to = 15 * tilesize, radius = Mathf.lerp(from, to, e.fin()) ;
        color(e.color.cpy());
        Lines.stroke(3);
        Lines.circle(e.x, e.y, radius);
        for (int i = 0 ; i < 10 ; i ++) {
            rand.setSeed(e.id + i);
            float angle = rand.random(360f) ;
            float length = radius * 0.75f * Math.max(angle / 360, 0.5f)  ;
            float width = radius * (float) Math.PI * 2 / Math.max(angle, 180) * e.fout() * 5f ;
            Vec2 v = ShaYeBuShi.circle(angle, radius, e.x, e.y) ;
            Drawf.tri(v.x, v.y, width, length, angle + 180);
        }
    }), baozha7 = new MultiEffect(baozha4, baozha6),
    chuangkou = new Effect(300, e -> {
        int status = e.time >= 240 ? 3 : e.time >= 60 ? 2 : 1 ;
        float width = status == 2 ? 8 * tilesize : status == 1 ? Mathf.lerp(0, 8 * tilesize, e.time / 60f) : Mathf.lerp(8 * tilesize, 0, (e.time - 240) / 60f) ;
        float length = 8 * tilesize ;
        Draw.color(e.color) ;
        Fill.rect(e.x, e.y, width, length);
        Draw.color(Color.valueOf("000000ff")) ;
        Fill.rect(e.x, e.y, width - tilesize, length - tilesize);
        Draw.color(Color.white) ;
        randLenVectors(e.id, e.fin(), 80, length, (x, y, in, out) -> {
            if (Math.abs(e.x - x) <= width) {
                Fill.circle(x, y, 2);
            }
        }) ;
    }), hujiao1 = new Effect(45, e -> {
        Draw.color(e.color);
        for (int i = 0 ; i < 5 ; i ++) {
            Lines.stroke(2);
            Lines.circle(e.x, e.y, (8 * tilesize * e.fin()) * (10 - i) / 10f);
        }
    }), hujiao2 = new Effect(60, e -> {
        Draw.color(e.color);
        for (int i = 0 ; i < 10 ; i ++) {
            Lines.stroke(2);
            Lines.arc(e.x, e.y, (24 * tilesize * e.fin()) * (20 - i) / 20f, 60 / 360f, e.rotation);
        }
    }), hujiao3 = new MultiEffect(hujiao1, hujiao2), jiange3 = new Effect(30, e -> {
        for(int i = 0; i < 2; i++){
            color(i == 0 ? chaokongjian2 : chaokongjian1);

            float m = i == 0 ? 1f : 0.5f;

            float rot = e.rotation + 180f - 45f;
            float w = 15f * e.fout() * m;
            Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot);
            Drawf.tri(e.x, e.y, w, 10f * m, rot + 90f);
        }

        Drawf.light(e.x, e.y, 60f, chaokongjian1, 0.6f * e.fout());
    }), hujiao4 = new Effect(45, e -> {
        Draw.color(e.color);
        for (int i = 0 ; i < 10 ; i ++) {
            Lines.stroke(2);
            Lines.circle(e.x, e.y, (16 * tilesize * e.fin()) * (30 - i) / 30f);
        }
    }), hujiao5 = new Effect(120, e -> {
        float len = 15 * tilesize ;
        Draw.color(e.color);
        Lines.stroke(5);
        Lines.circle(e.x, e.y, len * 1.3f) ;
        Lines.line(e.x, e.y - len * 1.9f, e.x, e.y + len * 1.9f);
        Lines.line(e.x - len * 1.9f, e.y, e.x + len * 1.9f, e.y);
        Lines.circle(e.x, e.y, len * 1.9f - len * 0.6f * e.fin()) ;
        Draw.reset();
    }), fixedMassiveExplosion = new Effect(30, e -> {
        color(e.color);

        e.scaled(7, i -> {
            stroke(3f * i.fout());
            Lines.circle(e.x, e.y, 4f + i.fin() * 30f);
        });

        color(Color.gray);

        randLenVectors(e.id, 8, 2f + 30f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 0.5f);
        });

        color(e.color);
        stroke(e.fout());

        randLenVectors(e.id + 1, 6, 1f + 29f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 4f);
        });

        Drawf.light(e.x, e.y, 50f, e.color, 0.8f * e.fout());
    }), line = new Effect(120, e -> {
        if (!(e.data instanceof Position p)) return ;
        Draw.color(e.color);
        Lines.stroke(e.rotation);
        Lines.line(e.x, e.y, p.getX(), p.getY());
    }), fixedShootBigSmoke2 = new Effect(18f, e -> {
        color(chaokongjian3, chaokongjian2, chaokongjian1, e.fin());

        randLenVectors(e.id, 9, e.finpow() * 23f, e.rotation, 20f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 2.4f + 0.2f);
        });
    }), lizi2 = new Effect(30, e -> {
        randLenVectors(e.id, 10, 30 + e.fin() * 12, (x, y) -> {
            color(e.color, Color.white, e.fin());
            Fill.square(e.x - x, e.y - y, 0.2f + e.fout() * 2f, 45);
        });
    }), lizi3 = new Effect(30, e -> {
        randLenVectors(e.id, 10, 30 + e.fout() * 12, (x, y) -> {
            color(e.color, Color.white, e.fin());
            Fill.square(e.x - x, e.y - y, 2.2f + e.fout() * 2f, 45);
        });
    }), baozha8 = new Effect(240, e -> {
        Draw.color(e.color) ;
        //Lines.stroke(5) ;
        //Lines.circle(e.x, e.y, 20 * tilesize * e.fin()) ;
        float pro = e.fin() < 0.6f ? 1 : e.fout() + 0.6f ;
        //Fill.circle(e.x, e.y, 5 * tilesize * pro) ;
        float rotateSpeed = 3 ;
        pro = 10 + e.fin() * 10 ;
        rotateSpeed = 0 ;
        for (int i : new int[]{0, 90, 180, 270}) {
            float rotation = i + e.rotation + e.time * Mathf.lerp(rotateSpeed, 0, Math.min(e.fin() + 0.5f, 1)) ;
            Vec2 v = ShaYeBuShi.circle(rotation, 10 * tilesize * pro, e.x, e.y) ;
            Drawf.tri(v.x, v.y, 5 * tilesize * pro, 5 * tilesize, rotation + 180) ;
            Drawf.tri(v.x, v.y, 10 * tilesize * pro, 5 * tilesize, rotation) ;
        }
        float z = Draw.z() ;
        Draw.color(Color.black) ;
        Draw.z(Layer.space) ;
        Lines.stroke(3.5f) ;
        //Lines.circle(e.x, e.y, 20 * tilesize * e.fin()) ;
        //Fill.circle(e.x, e.y, 5 * tilesize * (e.fin() < 0.6f ? 1 : e.fout() + 0.6f)) ;
        for (int i : new int[]{0, 90, 180, 270}) {
            float rotation = i + e.rotation + e.time * Mathf.lerp(rotateSpeed, 0, Math.min(e.fin() + 0.5f, 1)) ;
            Vec2 v = ShaYeBuShi.circle(rotation, 10 * tilesize * pro, e.x, e.y) ;
            Drawf.tri(v.x, v.y, 5 * tilesize * pro * 0.7f, 5 * tilesize, rotation + 180) ;
            Drawf.tri(v.x, v.y, 10 * tilesize * pro * 0.7f, 5 * tilesize, rotation) ;
        }
        Draw.reset() ;
        Draw.z(z) ;
    }), baozha9 = new Effect(240, e -> {
        Draw.color(e.color) ;
        //Lines.stroke(5) ;
        //Lines.circle(e.x, e.y, 20 * tilesize * e.fin()) ;
        float pro = e.fin() < 0.6f ? 1 : e.fout() + 0.6f ;
        Fill.circle(e.x, e.y, 5 * tilesize * pro) ;
        float rotateSpeed = 3 ;
        pro = e.fout() ;
        rotateSpeed = 0 ;
        for (int i : new int[]{0, 90, 180, 270}) {
            float rotation = i + e.rotation + e.time * Mathf.lerp(rotateSpeed, 0, Math.min(e.fin() + 0.25f, 1)) ;
            Vec2 v = ShaYeBuShi.circle(rotation, 10 * tilesize, e.x, e.y) ;
            Drawf.tri(v.x, v.y, 5 * tilesize * pro, 10 * tilesize * (1 + pro), rotation + 180) ;
            Drawf.tri(v.x, v.y, 5 * tilesize * pro, 25 * tilesize * (1 + pro), rotation) ;
        }
        float z = Draw.z() ;
        Draw.color(Color.black) ;
        Draw.z(Layer.space) ;
        //Lines.stroke(3.5f) ;
        //Lines.circle(e.x, e.y, 20 * tilesize * e.fin()) ;
        Fill.circle(e.x, e.y, 5 * tilesize * pro) ;
        for (int i : new int[]{0, 90, 180, 270}) {
            float rotation = i + e.rotation + e.time * Mathf.lerp(rotateSpeed, 0, Math.min(e.fin() + 0.25f, 1)) ;
            Vec2 v = ShaYeBuShi.circle(rotation, 10 * tilesize, e.x, e.y) ;
            Drawf.tri(v.x, v.y, 5 * tilesize * pro * 0.7f, 10 * tilesize * (1 + pro), rotation + 180) ;
            Drawf.tri(v.x, v.y, 5 * tilesize * pro * 0.7f, 25 * tilesize * (1 + pro), rotation) ;
        }
        Draw.reset() ;
        Draw.z(z) ;
    }), baozha10 = new MultiEffect(baozha8, baozha9),
        fixedMissileTrailSmoke = new Effect(90f, 300f, b -> {
            float intensity = 2f;
            color(b.color, 0.7f);
            for(int i = 0; i < 4; i++){
                rand.setSeed(b.id*2 + i);
                float lenScl = rand.random(0.5f, 1f);
                int fi = i;
                b.scaled(b.lifetime * lenScl, e -> {
                    randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int)(2.9f * intensity), 13f * intensity, (x, y, in, out) -> {
                        float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                        float rad = fout * ((2f + intensity) * 2.35f);
                        Fill.circle(e.x + x, e.y + y, rad);
                        Drawf.light(e.x + x, e.y + y, rad * 2.5f, b.color, 0.5f);
                    });
                });
            }
        }).layer(Layer.bullet - 1f);
}
