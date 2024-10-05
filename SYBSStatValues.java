package shayebushi;

import arc.Core;
import arc.func.Boolf;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Collapser;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectFloatMap;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Scaling;
import arc.util.Strings;
import mindustry.Vars;
import mindustry.content.StatusEffects;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Icon;
import mindustry.maps.Map;
import mindustry.type.*;
import mindustry.ui.ItemDisplay;
import mindustry.ui.LiquidDisplay;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.meta.*;
import shayebushi.entities.bullet.*;
import shayebushi.world.blocks.defense.WallTurret;
import shayebushi.world.blocks.defense.turrets.AbilityTurret;
import shayebushi.world.blocks.defense.turrets.TurretWeapon;

import static mindustry.Vars.*;
import static mindustry.Vars.content;

public class SYBSStatValues extends StatValues {

    public static StatValue string(String value, Object... args){
        String result = Strings.format(value, args);
        return table -> table.add(result);
    }

    public static StatValue bool(boolean value){
        return table ->  table.add(!value ? "@no" : "@yes");
    }

    public static String fixValue(float value){
        return Strings.autoFixed(value, 2);
    }

    public static StatValue squared(float value, StatUnit unit){
        return table -> {
            String fixed = fixValue(value);
            table.add(fixed + "x" + fixed);
            table.add((unit.space ? " " : "") + unit.localized());
        };
    }

    public static StatValue number(float value, StatUnit unit, boolean merge){
        return table -> {
            String l1 = (unit.icon == null ? "" : unit.icon + " ") + fixValue(value), l2 = (unit.space ? " " : "") + unit.localized();

            if(merge){
                table.add(l1 + l2).left();
            }else{
                table.add(l1).left();
                table.add(l2).left();
            }
        };
    }

    public static StatValue number(float value, StatUnit unit){
        return number(value, unit, false);
    }

    public static StatValue liquid(Liquid liquid, float amount, boolean perSecond){
        return table -> table.add(new LiquidDisplay(liquid, amount, perSecond));
    }

    public static StatValue liquids(Boolf<Liquid> filter, float amount, boolean perSecond){
        return table -> {
            Seq<Liquid> list = content.liquids().select(i -> filter.get(i) && i.unlockedNow() && !i.isHidden());

            for(int i = 0; i < list.size; i++){
                table.add(new LiquidDisplay(list.get(i), amount, perSecond)).padRight(5);

                if(i != list.size - 1){
                    table.add("/");
                }
            }
        };
    }

    public static StatValue liquids(float timePeriod, LiquidStack... stacks){
        return liquids(timePeriod, true, stacks);
    }

    public static StatValue liquids(float timePeriod, boolean perSecond, LiquidStack... stacks){
        return table -> {
            for(var stack : stacks){
                table.add(new LiquidDisplay(stack.liquid, stack.amount * (60f / timePeriod), perSecond)).padRight(5);
            }
        };
    }

    public static StatValue items(ItemStack... stacks){
        return items(true, stacks);
    }

    public static StatValue items(boolean displayName, ItemStack... stacks){
        return table -> {
            for(ItemStack stack : stacks){
                table.add(new ItemDisplay(stack.item, stack.amount, displayName)).padRight(5);
            }
        };
    }

    public static StatValue items(float timePeriod, ItemStack... stacks){
        return table -> {
            for(ItemStack stack : stacks){
                table.add(new ItemDisplay(stack.item, stack.amount, timePeriod, true)).padRight(5);
            }
        };
    }

    public static StatValue items(Boolf<Item> filter){
        return items(-1, filter);
    }

    public static StatValue items(float timePeriod, Boolf<Item> filter){
        return table -> {
            Seq<Item> list = content.items().select(i -> filter.get(i) && i.unlockedNow() && !i.isHidden());

            for(int i = 0; i < list.size; i++){
                Item item = list.get(i);

                table.add(timePeriod <= 0 ? new ItemDisplay(item) : new ItemDisplay(item, 1, timePeriod, true)).padRight(5);

                if(i != list.size - 1){
                    table.add("/");
                }
            }
        };
    }

    public static StatValue content(UnlockableContent content){
        return table -> {
            table.add(new Image(content.uiIcon)).size(iconSmall).padRight(3);
            table.add(content.localizedName).padRight(3);
        };
    }

    public static StatValue blockEfficiency(Block floor, float multiplier, boolean startZero){
        return table -> table.stack(
                new Image(floor.uiIcon).setScaling(Scaling.fit),
                new Table(t -> t.top().right().add((multiplier < 0 ? "[scarlet]" : startZero ? "[accent]" : "[accent]+") + (int)((multiplier) * 100) + "%").style(Styles.outlineLabel))
        ).maxSize(64f);
    }

    public static StatValue blocks(Attribute attr, boolean floating, float scale, boolean startZero){
        return blocks(attr, floating, scale, startZero, true);
    }

    public static StatValue blocks(Attribute attr, boolean floating, float scale, boolean startZero, boolean checkFloors){
        return table -> table.table(c -> {
            Runnable[] rebuild = {null};
            Map[] lastMap = {null};

            rebuild[0] = () -> {
                c.clearChildren();
                c.left();

                if(state.isGame()){
                    var blocks = Vars.content.blocks()
                            .select(block -> (!checkFloors || block instanceof Floor) && indexer.isBlockPresent(block) && block.attributes.get(attr) != 0 && !((block instanceof Floor f && f.isDeep()) && !floating))
                            .with(s -> s.sort(f -> f.attributes.get(attr)));

                    if(blocks.any()){
                        int i = 0;
                        for(var block : blocks){

                            blockEfficiency(block, block.attributes.get(attr) * scale, startZero).display(c);
                            if(++i % 5 == 0){
                                c.row();
                            }
                        }
                    }else{
                        c.add("@none.inmap");
                    }
                }else{
                    c.add("@stat.showinmap");
                }
            };

            rebuild[0].run();

            //rebuild when map changes.
            c.update(() -> {
                Map current = state.isGame() ? state.map : null;

                if(current != lastMap[0]){
                    rebuild[0].run();
                    lastMap[0] = current;
                }
            });
        });
    }
    public static StatValue content(Seq<UnlockableContent> list){
        return content(list, i -> true);
    }

    public static <T extends UnlockableContent> StatValue content(Seq<T> list, Boolf<T> check){
        return table -> table.table(l -> {
            l.left();

            boolean any = false;
            for(int i = 0; i < list.size; i++){
                var item = list.get(i);

                if(!check.get(item)) continue;
                any = true;

                if(item.uiIcon.found()) l.image(item.uiIcon).size(iconSmall).padRight(2).padLeft(2).padTop(3).padBottom(3);
                l.add(item.localizedName).left().padLeft(1).padRight(4).colspan(item.uiIcon.found() ? 1 : 2);
                if(i % 5 == 4){
                    l.row();
                }
            }

            if(!any){
                l.add("@none.inmap");
            }
        });
    }

    public static StatValue blocks(Boolf<Block> pred){
        return content(content.blocks(), pred);
    }

    public static StatValue blocks(Seq<Block> list){
        return content(list.as());
    }

    public static StatValue statusEffects(Seq<StatusEffect> list){
        return content(list.as());
    }

    public static StatValue drillables(float drillTime, float drillMultiplier, float size, ObjectFloatMap<Item> multipliers, Boolf<Block> filter){
        return table -> {
            table.row();
            table.table(c -> {
                int i = 0;
                for(Block block : content.blocks()){
                    if(!filter.get(block)) continue;

                    c.table(Styles.grayPanel, b -> {
                        b.image(block.uiIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                        b.table(info -> {
                            info.left();
                            info.add(block.localizedName).left().row();
                            info.add(block.itemDrop.emoji()).left();
                        }).grow();
                        if(multipliers != null){
                            b.add(Strings.autoFixed(60f / (Math.max(drillTime + drillMultiplier * block.itemDrop.hardness, drillTime) / multipliers.get(block.itemDrop, 1f)) * size, 2) + StatUnit.perSecond.localized())
                                    .right().pad(10f).padRight(15f).color(Color.lightGray);
                        }
                    }).growX().pad(5);
                    if(++i % 2 == 0) c.row();
                }
            }).growX().colspan(table.getColumns());
        };
    }

    public static StatValue boosters(float reload, float maxUsed, float multiplier, boolean baseReload, Boolf<Liquid> filter){
        return table -> {
            table.row();
            table.table(c -> {
                for(Liquid liquid : content.liquids()){
                    if(!filter.get(liquid)) continue;

                    c.table(Styles.grayPanel, b -> {
                        b.image(liquid.uiIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                        b.table(info -> {
                            info.add(liquid.localizedName).left().row();
                            info.add(Strings.autoFixed(maxUsed * 60f, 2) + StatUnit.perSecond.localized()).left().color(Color.lightGray);
                        });

                        b.table(bt -> {
                            bt.right().defaults().padRight(3).left();

                            float reloadRate = (baseReload ? 1f : 0f) + maxUsed * multiplier * liquid.heatCapacity;
                            float standardReload = baseReload ? reload : reload / (maxUsed * multiplier * 0.4f);
                            float result = standardReload / (reload / reloadRate);
                            bt.add(Core.bundle.format("bullet.reload", Strings.autoFixed(result * 100, 2))).pad(5);
                        }).right().grow().pad(10f).padRight(15f);
                    }).growX().pad(5).row();
                }
            }).growX().colspan(table.getColumns());
            table.row();
        };
    }

    public static StatValue speedBoosters(String unit, float amount, float speed, boolean strength, Boolf<Liquid> filter){
        return table -> {
            table.row();
            table.table(c -> {
                for(Liquid liquid : content.liquids()){
                    if(!filter.get(liquid)) continue;

                    c.table(Styles.grayPanel, b -> {
                        b.image(liquid.uiIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                        b.table(info -> {
                            info.add(liquid.localizedName).left().row();
                            info.add(Strings.autoFixed(amount * 60f, 2) + StatUnit.perSecond.localized()).left().color(Color.lightGray);
                        });

                        b.table(bt -> {
                            bt.right().defaults().padRight(3).left();
                            if(speed != Float.MAX_VALUE) bt.add(unit.replace("{0}", "[stat]" + Strings.autoFixed(speed * (strength ? liquid.heatCapacity : 1f) + (strength ? 1f : 0f), 2) + "[lightgray]")).pad(5);
                        }).right().grow().pad(10f).padRight(15f);
                    }).growX().pad(5).row();
                }
            }).growX().colspan(table.getColumns());
            table.row();
        };
    }

    public static StatValue itemBoosters(String unit, float timePeriod, float speedBoost, float rangeBoost, ItemStack[] items, Boolf<Item> filter){
        return table -> {
            table.row();
            table.table(c -> {
                for(Item item : content.items()){
                    if(!filter.get(item)) continue;

                    c.table(Styles.grayPanel, b -> {
                        for(ItemStack stack : items){
                            if(timePeriod < 0){
                                b.add(new ItemDisplay(stack.item, stack.amount, true)).pad(20f).left();
                            }else{
                                b.add(new ItemDisplay(stack.item, stack.amount, timePeriod, true)).pad(20f).left();
                            }
                            if(items.length > 1) b.row();
                        }

                        b.table(bt -> {
                            bt.right().defaults().padRight(3).left();
                            if(rangeBoost != 0) bt.add("[lightgray]+[stat]" + Strings.autoFixed(rangeBoost / tilesize, 2) + "[lightgray] " + StatUnit.blocks.localized()).row();
                            if(speedBoost != 0) bt.add("[lightgray]" + unit.replace("{0}", "[stat]" + Strings.autoFixed(speedBoost, 2) + "[lightgray]"));
                        }).right().grow().pad(10f).padRight(15f);
                    }).growX().pad(5).padBottom(-5).row();
                }
            }).growX().colspan(table.getColumns());
            table.row();
        };
    }

    public static StatValue weapons(Turret unit, Seq<TurretWeapon> weapons){
        return table -> {
            table.row();
            for(int i = 0; i < weapons.size;i ++){
                TurretWeapon weapon = weapons.get(i);

                if(weapon.flipSprite || !weapon.hasStats(unit)){
                    //flipped weapons are not given stats
                    continue;
                }

                TextureRegion region = !weapon.name.isEmpty() ? Core.atlas.find(weapon.name + "-preview", weapon.region) : null;

                table.table(Styles.grayPanel, w -> {
                    w.left().top().defaults().padRight(3).left();
                    if(region != null && region.found() && weapon.showStatSprite) w.image(region).size(60).scaling(Scaling.bounded).left().top();
                    w.row();

                    weapon.addStats(unit, w);
                }).growX().pad(5).margin(10);
                table.row();
            }
        };
    }

    public static <T extends UnlockableContent> StatValue ammo(ObjectMap<T, BulletType> map){
        return ammo(map, 0, false);
    }

    public static <T extends UnlockableContent> StatValue ammo(ObjectMap<T, BulletType> map, boolean showUnit){
        return ammo(map, 0, showUnit);
    }

    public static <T extends UnlockableContent> StatValue ammo(ObjectMap<T, BulletType> map, int indent, boolean showUnit){
        return table -> {

            table.row();

            var orderedKeys = map.keys().toSeq();
            orderedKeys.sort();

            for(T t : orderedKeys){
                boolean compact = t instanceof UnitType && !showUnit || indent > 0;

                BulletType type = map.get(t);

                if(type.spawnUnit != null && type.spawnUnit.weapons.size > 0){
                    ammo(ObjectMap.of(t, type.spawnUnit.weapons.first().bullet), indent, false).display(table);
                    continue;
                }

                table.table(Styles.grayPanel, bt -> {
                    bt.left().top().defaults().padRight(3).left();
                    //no point in displaying unit icon twice
                    if(!compact && !(t instanceof Turret)){
                        bt.table(title -> {
                            title.image(icon(t)).size(3 * 8).padRight(4).right().scaling(Scaling.fit).top();
                            title.add(t.localizedName).padRight(10).left().top();
                        });
                        bt.row();
                    }

                    if(type.damage > 0 && (type.collides || type.splashDamage <= 0)){
                        if(type.continuousDamage() > 0){
                            bt.add(Core.bundle.format("bullet.damage", type.continuousDamage()) + StatUnit.perSecond.localized());
                        }
                        else{
                            bt.add(Core.bundle.format("bullet.damage", type.damage));
                        }
                    }
                    if (type instanceof BaiFenBiBulletType b){
                        if (b.baifenbi > 0) {
                            sep(bt, Core.bundle.format(b.zuida ? "bullet.zuida" : "bullet.dangqian") + Core.bundle.format("bullet.baifenbi", b.baifenbi * 100));
                        }
                        if (b.splashDamageRadius > 0 && b.fanweibaifenbi > 0) {
                            sep(bt, Core.bundle.format(b.zuida ? "bullet.zuida" : "bullet.dangqian") + Core.bundle.format("bullet.fanweibaifenbi", b.fanweibaifenbi*100, Strings.fixed(type.splashDamageRadius / tilesize, 1)));
                        }
                    }
                    else if (type instanceof BaiFenBiLaserBulletType b){
                        if (b.baifenbi > 0) {
                            sep(bt, Core.bundle.format(b.zuida ? "bullet.zuida" : "bullet.dangqian") + Core.bundle.format("bullet.baifenbi", b.baifenbi * 100));
                        }
                        if (b.wushixianshang) {
                            sep(bt, Core.bundle.get("bullet.wushixianshang")) ;
                        }
                    }
                    else if(type instanceof BaiFenBiChiXuJiGuangBulletType b){
                        if (b.baifenbi > 0) {
                            sep(bt, Core.bundle.format(b.zuida ? "bullet.zuida" : "bullet.dangqian") + Core.bundle.format("bullet.baifenbii", b.baifenbi * 100 * (60 / b.damageInterval) + StatUnit.perSecond.localized()));
                        }
                        if (b.wushixianshang) {
                            sep(bt, Core.bundle.get("bullet.wushixianshang")) ;
                        }
                    }
                    else if(type instanceof CeFanBulletType b){
                        sep(bt, Core.bundle.format("bullet.cefan",b.cefanshangxian));
                    }
                    if (type instanceof GaiLvMiaoBulletType b){
                        sep(bt, Core.bundle.format("bullet.gailv",(1 / b.gailv) * 100));
                    }
                    else if (type instanceof GaiLvMiaoLaserBulletType b){
                        sep(bt, Core.bundle.format("bullet.gailv",(1 / b.gailv) * 100));
                    }
                    if (type instanceof WuShiXianShangc) {
                        sep(bt, Core.bundle.get("bullet.wushixianshang")) ;
                    }
                    if (type instanceof TeShuBulletType te){
                        if (te.ground != 1) {
                            int val = (int) (te.ground * 100 - 100);
                            sep(bt, Core.bundle.format("bullet.ground", ammoStat(val)));
                        }
                        if (te.air != 1) {
                            int val = (int) (te.air * 100 - 100);
                            sep(bt, Core.bundle.format("bullet.air", ammoStat(val)));
                        }
                        if (te.naval != 1) {
                            int val = (int) (te.naval * 100 - 100);
                            sep(bt, Core.bundle.format("bullet.naval", ammoStat(val)));
                        }
                    }
                    if (type instanceof JiZengBulletType j) {
                        sep(bt, Core.bundle.format("bullet.fanbei", j.meimiaofanbeicishu));
                    }
                    if(type.buildingDamageMultiplier != 1){
                        int val = (int)(type.buildingDamageMultiplier * 100 - 100);
                        sep(bt, Core.bundle.format("bullet.buildingdamage", ammoStat(val)));
                    }

                    if(type.rangeChange != 0 && !compact){
                        sep(bt, Core.bundle.format("bullet.range", ammoStat(type.rangeChange / tilesize)));
                    }

                    if(type.splashDamage > 0){
                        sep(bt, Core.bundle.format("bullet.splashdamage", (int)type.splashDamage, Strings.fixed(type.splashDamageRadius / tilesize, 1)));
                    }

                    if(!compact && !Mathf.equal(type.ammoMultiplier, 1f) && type.displayAmmoMultiplier && (!(t instanceof Turret turret) || turret.displayAmmoMultiplier)){
                        sep(bt, Core.bundle.format("bullet.multiplier", (int)type.ammoMultiplier));
                    }

                    if(!compact && !Mathf.equal(type.reloadMultiplier, 1f)){
                        int val = (int)(type.reloadMultiplier * 100 - 100);
                        sep(bt, Core.bundle.format("bullet.reload", ammoStat(val)));
                    }

                    if(type.knockback != 0){
                        sep(bt, Core.bundle.format("bullet.knockback", Strings.autoFixed(type.knockback, 2)));
                    }

                    if(type.healPercent > 0f){
                        sep(bt, Core.bundle.format("bullet.healpercent", Strings.autoFixed(type.healPercent, 2)));
                    }

                    if(type.healAmount > 0f){
                        sep(bt, Core.bundle.format("bullet.healamount", Strings.autoFixed(type.healAmount, 2)));
                    }

                    if(type.pierce || type.pierceCap != -1){
                        sep(bt, type.pierceCap == -1 ? "@bullet.infinitepierce" : Core.bundle.format("bullet.pierce", type.pierceCap));
                    }

                    if(type.incendAmount > 0){
                        sep(bt, "@bullet.incendiary");
                    }

                    if(type.homingPower > 0.01f){
                        sep(bt, "@bullet.homing");
                    }

                    if(type.lightning > 0){
                        sep(bt, Core.bundle.format("bullet.lightning", type.lightning, type.lightningDamage < 0 ? type.damage : type.lightningDamage));
                    }

                    if(type.pierceArmor){
                        sep(bt, "@bullet.armorpierce");
                    }

                    if(type.suppressionRange > 0){
                        sep(bt, Core.bundle.format("bullet.suppression", Strings.autoFixed(type.suppressionDuration / 60f, 2), Strings.fixed(type.suppressionRange / tilesize, 1)));
                    }

                    if(type.status != StatusEffects.none){
                        sep(bt, (type.status.minfo.mod == null ? type.status.emoji() : "") + "[stat]" + type.status.localizedName + (type.status.reactive ? "" : "[lightgray] ~ [stat]" + ((int)(type.statusDuration / 60f)) + "[lightgray] " + Core.bundle.get("unit.seconds")));
                    }

                    if(type.intervalBullet != null){
                        bt.row();

                        Table ic = new Table();
                        ammo(ObjectMap.of(t, type.intervalBullet), indent + 1, false).display(ic);
                        Collapser coll = new Collapser(ic, true);
                        coll.setDuration(0.1f);

                        bt.table(it -> {
                            it.left().defaults().left();

                            it.add(Core.bundle.format("bullet.interval", Strings.autoFixed(type.intervalBullets / type.bulletInterval * 60, 2)));
                            it.button(Icon.downOpen, Styles.emptyi, () -> coll.toggle(false)).update(i -> i.getStyle().imageUp = (!coll.isCollapsed() ? Icon.upOpen : Icon.downOpen)).size(8).padLeft(16f).expandX();
                        });
                        bt.row();
                        bt.add(coll);
                    }

                    if(type.fragBullet != null){
                        bt.row();

                        Table fc = new Table();
                        ammo(ObjectMap.of(t, type.fragBullet), indent + 1, false).display(fc);
                        Collapser coll = new Collapser(fc, true);
                        coll.setDuration(0.1f);

                        bt.table(ft -> {
                            ft.left().defaults().left();

                            ft.add(Core.bundle.format("bullet.frags", type.fragBullets));
                            ft.button(Icon.downOpen, Styles.emptyi, () -> coll.toggle(false)).update(i -> i.getStyle().imageUp = (!coll.isCollapsed() ? Icon.upOpen : Icon.downOpen)).size(8).padLeft(16f).expandX();
                        });
                        bt.row();
                        bt.add(coll);
                    }
                    if (type instanceof PayloadBullet p) {
                        if (p.block instanceof Turret t2) {
                            bt.row();

                            Table fc = new Table();
                            if (!t2.stats.intialized) {
                                t2.setStats();
                            }
                            t2.stats.toMap().get(StatCat.function).get(Stat.ammo).get(0).display(fc);
                            Collapser coll = new Collapser(fc, true);
                            coll.setDuration(0.1f);

                            bt.table(ft -> {
                                ft.left().defaults().left();

                                ft.add(Core.bundle.get("bullet.build"));
                                ft.button(Icon.downOpen, Styles.emptyi, () -> coll.toggle(false)).update(i -> i.getStyle().imageUp = (!coll.isCollapsed() ? Icon.upOpen : Icon.downOpen)).size(8).padLeft(16f).expandX();
                            });
                            bt.row();
                            bt.add(coll);
                        }
                        if (p.block instanceof AbilityTurret t2) {
                            bt.row();

                            Table fc = new Table();
                            if (!t2.stats.intialized) {
                                t2.setStats();
                            }
                            t2.stats.toMap().get(StatCat.function).get(Stat.ammo).get(0).display(fc);
                            Collapser coll = new Collapser(fc, true);
                            coll.setDuration(0.1f);

                            bt.table(ft -> {
                                ft.left().defaults().left();

                                ft.add(Core.bundle.get("bullet.build"));
                                ft.button(Icon.downOpen, Styles.emptyi, () -> coll.toggle(false)).update(i -> i.getStyle().imageUp = (!coll.isCollapsed() ? Icon.upOpen : Icon.downOpen)).size(8).padLeft(16f).expandX();
                            });
                            bt.row();
                            bt.add(coll);
                        }
                        if (p.block instanceof WallTurret t2) {
                            bt.row();

                            Table fc = new Table();
                            if (!t2.stats.intialized) {
                                t2.setStats();
                            }
                            t2.stats.toMap().get(StatCat.function).get(Stat.ammo).get(0).display(fc);
                            Collapser coll = new Collapser(fc, true);
                            coll.setDuration(0.1f);

                            bt.table(ft -> {
                                ft.left().defaults().left();

                                ft.add(Core.bundle.get("bullet.build"));
                                ft.button(Icon.downOpen, Styles.emptyi, () -> coll.toggle(false)).update(i -> i.getStyle().imageUp = (!coll.isCollapsed() ? Icon.upOpen : Icon.downOpen)).size(8).padLeft(16f).expandX();
                            });
                            bt.row();
                            bt.add(coll);
                        }
                    }
                }).padLeft(indent * 5).padTop(5).padBottom(compact ? 0 : 5).growX().margin(compact ? 0 : 10);
                table.row();
            }
        };
    }

    //for AmmoListValue
    private static void sep(Table table, String text){
        table.row();
        table.add(text);
    }

    //for AmmoListValue
    private static String ammoStat(float val){
        return (val > 0 ? "[stat]+" : "[negstat]") + Strings.autoFixed(val, 1);
    }

    private static TextureRegion icon(UnlockableContent t){
        return t.uiIcon;
    }
}