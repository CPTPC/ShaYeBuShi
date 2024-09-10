package shayebushi;

import arc.*;
import arc.struct.*;
import mindustry.gen.Iconc;
import mindustry.logic.LStatements;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;
import mindustry.world.meta.StatUnit;

import java.util.*;

/** Describes one type of stat for content. */
public class SYBSStats{
    public static final Stat
    fangzhishangxian = new Stat("fangzhishangxian"),
    baifenbishanghai = new Stat("baifenbishanghai",StatCat.function),
    zuida = new Stat("zuida",StatCat.function),
    dangqian = new Stat("dangqian",StatCat.function),
    diyadianchi = new Stat("diyadianchi", StatCat.power),
    zhongyadianchi = new Stat("zhongyadianchi", StatCat.power),
    gaoyadianchi = new Stat("gaoyadianchi", StatCat.power);
    public static final StatUnit
    diyadian = new StatUnit("diyadian", "[accent]" + Iconc.power + "[]"),
    zhongyadian = new StatUnit("zhongyadian", "[accent]" + Iconc.power + "[]"),
    gaoyadian = new StatUnit("gaoyadian", "[accent]" + Iconc.power + "[]");
    public static final Stat
    diyadianshuchu = new Stat("diyadianshuchu", StatCat.power),
    zhongyadianshuchu = new Stat("zhongyadianshuchu", StatCat.power),
    gaoyadianshuchu = new Stat("gaoyadianshuchu", StatCat.power),
    shanbi = new Stat("shanbi"),
    fu = new Stat("fu", StatCat.function),
    fm = new Stat("fm", StatCat.function),
    du = new Stat("du", StatCat.function),
    dm = new Stat("dm", StatCat.function),
    cichangqiangdu = new Stat("cichangqiangdu", StatCat.power),
    miaoxianshang = new Stat("miaoxianshang"),
    fenxianshang = new Stat("fenxianshang"),
    dancixianshang = new Stat("dancixianshang"),
    jietishuliang = new Stat("jietishuliang"),
    dps = new Stat("dps"),
    fangfushe = new Stat("fangfushe"),
    fangfushi = new Stat("fangfushi"),
    fushi = new Stat("fushi"),
    kongzhishangxian = new Stat("kongzhishangxian"),
    fusheqiangdu = new Stat("fusheqiangdu"),
    jianzaolengque = new Stat("fangzhishangxian"),
    sunhao = new Stat("sunhao", StatCat.function),
    podunshanghai = new Stat("podunshanghai", StatCat.function)
    ;
}
