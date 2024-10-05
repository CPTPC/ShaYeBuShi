package shayebushi;

import arc.func.Intc;
import arc.math.Mathf;
import arc.math.Rand;
import arc.struct.Seq;
import arc.util.Structs;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.game.SpawnGroup;
import mindustry.game.Waves;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import shayebushi.SYBSUnitTypes;

import static mindustry.content.UnitTypes.*;
import static shayebushi.SYBSUnitTypes.*;

public class SYBSWaves extends Waves {
    public static final int waveVersion = 7;

    public Seq<SpawnGroup> spawns;

    public Seq<SpawnGroup> get(){
        if(spawns == null && dagger != null){
            spawns = Seq.with(
                    new SpawnGroup(dagger){{
                        end = 10;
                        unitScaling = 2f;
                        max = 30;
                    }},

                    new SpawnGroup(crawler){{
                        begin = 4;
                        end = 13;
                        unitAmount = 2;
                        unitScaling = 1.5f;
                    }},

                    new SpawnGroup(flare){{
                        begin = 12;
                        end = 16;
                        unitScaling = 1f;
                    }},

                    new SpawnGroup(dagger){{
                        begin = 11;
                        unitScaling = 1.7f;
                        spacing = 2;
                        max = 4;
                        shieldScaling = 25f;
                    }},

                    new SpawnGroup(pulsar){{
                        begin = 13;
                        spacing = 3;
                        unitScaling = 0.5f;
                        max = 25;
                    }},

                    new SpawnGroup(mace){{
                        begin = 7;
                        spacing = 3;
                        unitScaling = 2;

                        end = 30;
                    }},

                    new SpawnGroup(dagger){{
                        begin = 12;
                        unitScaling = 1;
                        unitAmount = 4;
                        spacing = 2;
                        shieldScaling = 20f;
                        max = 14;
                    }},

                    new SpawnGroup(mace){{
                        begin = 28;
                        spacing = 3;
                        unitScaling = 1;
                        end = 40;
                        shieldScaling = 20f;
                    }},

                    new SpawnGroup(spiroct){{
                        begin = 45;
                        spacing = 3;
                        unitScaling = 1;
                        max = 10;
                        shieldScaling = 30f;
                        shields = 100;
                        effect = StatusEffects.overdrive;
                    }},

                    new SpawnGroup(pulsar){{
                        begin = 120;
                        spacing = 2;
                        unitScaling = 3;
                        unitAmount = 5;
                        effect = StatusEffects.overdrive;
                    }},

                    new SpawnGroup(flare){{
                        begin = 16;
                        unitScaling = 1;
                        spacing = 2;
                        shieldScaling = 20f;
                        max = 20;
                    }},

                    new SpawnGroup(quasar){{
                        begin = 82;
                        spacing = 3;
                        unitAmount = 4;
                        unitScaling = 3;
                        shieldScaling = 30f;
                        effect = StatusEffects.overdrive;
                    }},

                    new SpawnGroup(pulsar){{
                        begin = 41;
                        spacing = 5;
                        unitAmount = 1;
                        unitScaling = 3;
                        shields = 640f;
                        max = 25;
                    }},

                    new SpawnGroup(fortress){{
                        begin = 40;
                        spacing = 5;
                        unitAmount = 2;
                        unitScaling = 2;
                        max = 20;
                        shieldScaling = 30;
                    }},

                    new SpawnGroup(nova){{
                        begin = 35;
                        spacing = 3;
                        unitAmount = 4;
                        effect = StatusEffects.overdrive;
                        items = new ItemStack(Items.blastCompound, 60);
                        end = 60;
                    }},

                    new SpawnGroup(dagger){{
                        begin = 42;
                        spacing = 3;
                        unitAmount = 4;
                        effect = StatusEffects.overdrive;
                        items = new ItemStack(Items.pyratite, 100);
                        end = 130;
                        max = 30;
                    }},

                    new SpawnGroup(horizon){{
                        begin = 40;
                        unitAmount = 2;
                        spacing = 2;
                        unitScaling = 2;
                        shieldScaling = 20;
                    }},

                    new SpawnGroup(flare){{
                        begin = 50;
                        unitAmount = 4;
                        unitScaling = 3;
                        spacing = 5;
                        shields = 100f;
                        shieldScaling = 10f;
                        effect = StatusEffects.overdrive;
                        max = 20;
                    }},

                    new SpawnGroup(zenith){{
                        begin = 50;
                        unitAmount = 2;
                        unitScaling = 3;
                        spacing = 5;
                        max = 16;
                        shieldScaling = 30;
                    }},

                    new SpawnGroup(nova){{
                        begin = 53;
                        unitAmount = 2;
                        unitScaling = 3;
                        spacing = 4;
                        shieldScaling = 30;
                    }},

                    new SpawnGroup(atrax){{
                        begin = 31;
                        unitAmount = 4;
                        unitScaling = 1;
                        spacing = 3;
                        shieldScaling = 10f;
                    }},

                    new SpawnGroup(scepter){{
                        begin = 41;
                        unitAmount = 1;
                        unitScaling = 1;
                        spacing = 20;
                        shieldScaling = 30f;
                    }},

                    new SpawnGroup(reign){{
                        begin = 81;
                        unitAmount = 1;
                        unitScaling = 1;
                        spacing = 30;
                        shieldScaling = 30f;
                    }},

                    new SpawnGroup(antumbra){{
                        begin = 120;
                        unitAmount = 1;
                        unitScaling = 1;
                        spacing = 30;
                        shieldScaling = 30f;
                    }},

                    new SpawnGroup(vela){{
                        begin = 100;
                        unitAmount = 1;
                        unitScaling = 1;
                        spacing = 20;
                        shieldScaling = 30f;
                    }},

                    new SpawnGroup(corvus){{
                        begin = 145;
                        unitAmount = 1;
                        unitScaling = 1;
                        spacing = 25;
                        shieldScaling = 30f;
                        shields = 100;
                    }},

                    new SpawnGroup(horizon){{
                        begin = 90;
                        unitAmount = 2;
                        unitScaling = 3;
                        spacing = 4;
                        shields = 40f;
                        shieldScaling = 30f;
                    }},

                    new SpawnGroup(toxopid){{
                        begin = 150;
                        unitAmount = 1;
                        unitScaling = 1;
                        spacing = 25;
                        shields = 1000;
                        shieldScaling = 35f;
                    }},
                    new SpawnGroup(shenqi){{
                        begin = 157;
                        unitAmount = 1;
                        unitScaling = 3f;
                        spacing = 30;
                        shields = 4000;
                        shieldScaling = 40f;
                    }},
                    new SpawnGroup(xieling){{
                        begin = 162;
                        unitAmount = 1;
                        unitScaling = 5f;
                        spacing = 30;
                        shields = 6000;
                        shieldScaling = 40f;
                    }},
                    new SpawnGroup(anye){{
                        begin = 174;
                        unitAmount = 1;
                        unitScaling = 3f;
                        spacing = 35;
                        shields = 5000;
                        shieldScaling = 40f;
                    }},
                    new SpawnGroup(shengling){{
                        begin = 180;
                        unitAmount = 1;
                        unitScaling = 3f;
                        spacing = 35;
                        shields = 8000;
                        shieldScaling = 50f;
                    }},
                    new SpawnGroup(shuangxingyijieduan){{
                        begin = 200;
                        unitAmount = 1;
                        unitScaling = 10f;
                        spacing = 40;
                        shields = 4000;
                        shieldScaling = 35f;
                    }}
            );
        }
        return spawns == null ? new Seq<>() : spawns;
    }

    public static Seq<SpawnGroup> generate(float difficulty){
        //apply power curve to make starting sectors easier
        return generate(Mathf.pow(difficulty, 1.12f), new Rand(), false);
    }

    public static Seq<SpawnGroup> generate(float difficulty, Rand rand, boolean attack){
        return generate(difficulty, rand, attack, false);
    }

    public static Seq<SpawnGroup> generate(float difficulty, Rand rand, boolean attack, boolean airOnly){
        return generate(difficulty, rand, attack, airOnly, false);
    }

    public static Seq<SpawnGroup> generate(float difficulty, Rand rand, boolean attack, boolean airOnly, boolean naval){
        UnitType[][] species = {
                {dagger, mace, fortress, scepter, reign, shenqi},
                {nova, pulsar, quasar, vela, corvus, duoxing, shuangxingyijieduan},
                {crawler, atrax, spiroct, arkyid, toxopid, xieling},
                {risso, minke, bryde, sei, omura, chichao, anyong},
                {risso, oxynoe, cyerce, aegires, navanax, shuihua}, //retusa intentionally left out as it cannot damage the core properly
                {flare, horizon, zenith, rand.chance(0.5) ? quad : antumbra, rand.chance(0.1) ? quad : eclipse, anye}
        };

        if(airOnly){
            species = ShaYeBuShi.filter(UnitType[].class, species, v -> v[0].flying);
        }

        if(naval){
            species = ShaYeBuShi.filter(UnitType[].class, species, v -> v[0].flying || v[0].naval);
        }else{
            species = ShaYeBuShi.filter(UnitType[].class, species, v -> !v[0].naval);
        }

        UnitType[][] fspec = species;

        //required progression:
        //- extra periodic patterns

        Seq<SpawnGroup> out = new Seq<>();

        //max reasonable wave, after which everything gets boring
        int cap = 150;

        float shieldStart = 30, shieldsPerWave = 20 + difficulty*30f;
        float[] scaling = {1, 2f, 3f, 3f, 3f, 4f};

        Intc createProgression = start -> {
            //main sequence
            UnitType[] curSpecies = ShaYeBuShi.random(rand, fspec);
            int curTier = 0;

            for(int i = start; i < cap;){
                int f = i;
                int next = rand.random(8, 16) + (int)Mathf.lerp(5f, 0f, difficulty) + curTier * 5;

                float shieldAmount = Math.max((i - shieldStart) * shieldsPerWave, 0);
                int space = start == 0 ? 1 : rand.random(1, 2);
                int ctier = curTier;

                //main progression
                out.add(new SpawnGroup(curSpecies[Math.min(curTier, curSpecies.length - 1)]){{
                    unitAmount = f == start ? 1 : 6 / (int)scaling[ctier];
                    begin = f;
                    end = f + next >= cap ? never : f + next;
                    max = 13;
                    unitScaling = (difficulty < 0.4f ? rand.random(2.5f, 5f) : rand.random(1f, 4f)) * scaling[ctier];
                    shields = shieldAmount;
                    shieldScaling = shieldsPerWave;
                    spacing = space;
                }});

                //extra progression that tails out, blends in
                out.add(new SpawnGroup(curSpecies[Math.min(curTier, curSpecies.length - 1)]){{
                    unitAmount = 3 / (int)scaling[ctier];
                    begin = f + next - 1;
                    end = f + next + rand.random(6, 10);
                    max = 6;
                    unitScaling = rand.random(2f, 4f);
                    spacing = rand.random(2, 4);
                    shields = shieldAmount/2f;
                    shieldScaling = shieldsPerWave;
                }});

                i += next + 1;
                if(curTier < 4 || (rand.chance(0.05) && difficulty > 0.8)){
                    curTier ++;
                }

                //do not spawn bosses
                curTier = Math.min(curTier, 4);

                //small chance to switch species
                if(rand.chance(0.3)){
                    curSpecies = ShaYeBuShi.random(rand, fspec);
                }
            }
        };

        createProgression.get(0);

        int step = 5 + rand.random(5);

        while(step <= cap){
            createProgression.get(step);
            //System.out.println(step);
            step += Math.max((int)(rand.random(15, 30) * Mathf.lerp(0.5f, 0.75f, difficulty)), 1);
        }

        int bossWave = (int)(rand.random(50, 70) * Mathf.lerp(1f, 0.5f, difficulty));
        int bossSpacing = (int)(rand.random(25, 40) * Mathf.lerp(1f, 0.5f, difficulty));

        int bossTier = difficulty < 0.6 ? 3 : difficulty < 1.2 ? 4 : 5;

        //main boss progression
        out.add(new SpawnGroup(ShaYeBuShi.random(rand, species)[bossTier]){{
            unitAmount = 1;
            begin = bossWave;
            spacing = bossSpacing;
            end = never;
            max = 16;
            unitScaling = bossSpacing;
            shieldScaling = shieldsPerWave;
            effect = StatusEffects.boss;
        }});

        //alt boss progression
        out.add(new SpawnGroup(ShaYeBuShi.random(rand, species)[bossTier]){{
            unitAmount = 1;
            begin = bossWave + rand.random(3, 5) * bossSpacing;
            spacing = bossSpacing;
            end = never;
            max = 16;
            unitScaling = bossSpacing;
            shieldScaling = shieldsPerWave;
            effect = StatusEffects.boss;
        }});

        int finalBossStart = 120 + rand.random(30);

        //final boss waves
        out.add(new SpawnGroup(ShaYeBuShi.random(rand, species)[bossTier]){{
            unitAmount = 1;
            begin = finalBossStart;
            spacing = bossSpacing/2;
            end = never;
            unitScaling = bossSpacing;
            shields = 500;
            shieldScaling = shieldsPerWave * 4;
            effect = StatusEffects.boss;
        }});

        //final boss waves (alt)
        out.add(new SpawnGroup(ShaYeBuShi.random(rand, species)[bossTier]){{
            unitAmount = 1;
            begin = finalBossStart + 15;
            spacing = bossSpacing/2;
            end = never;
            unitScaling = bossSpacing;
            shields = 500;
            shieldScaling = shieldsPerWave * 4;
            effect = StatusEffects.boss;
        }});

        //add megas to heal the base.
        if(attack && difficulty >= 0.5){
            int amount = rand.random(1, 3 + (int)(difficulty*2));

            for(int i = 0; i < amount; i++){
                int wave = rand.random(3, 20);
                out.add(new SpawnGroup(mega){{
                    unitAmount = 1;
                    begin = wave;
                    end = wave;
                    max = 16;
                }});
            }
        }

        //shift back waves on higher difficulty for a harder start
        int shift = Math.max((int)(difficulty * 14 - 5), 0);

        for(SpawnGroup group : out){
            group.begin -= shift;
            group.end -= shift;
        }

        return out;
    }
}
