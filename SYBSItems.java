package shayebushi;

import arc.Core;
import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.core.Version;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.StatusEffect;
import mindustry.world.meta.Stat;
import shayebushi.type.LanTuItem;

import static mindustry.Vars.tilesize;
import static shayebushi.ShaYeBuShi.fangFuSheCiChangQiangDu;

public class SYBSItems {
    public static Item li , lv , taihejin , shenlingwuzhuang , shenlingzhuangjia , shenlingdongli , shenwulantu , shendonglantu , shenzhuanglantu , shenlinglantu,
            //lei,
            you, you235, you238,
                    //you233,
                    fangfushe, ruangang, yijihejin, erjihejin, sanjihejin, xiangjiao, suliao, hashihejin, hefeiliao, daole, yanmieneng, anjinshu, anwuzhihejin, shendiwuzhuang, shendizhuangjia, shendidongli, huimiewuzhuanglantu, huimiezhuangjialantu, huimiedonglilantu, shendilantu;
    public static SYBSItem deleike, yiduntanding, bulaoen, talituosi, liumingjing, yuemingzhu, shayebushi ;
    public static final Seq<Item> deleikeItems = new Seq<>() ;
    public static void load(){
        li = new Item("li" , Color.valueOf("4f4f4f")){{hardness = 2 ;}};
        lv = new Item("lv" , Color.valueOf("7b7b7b")){{hardness = 2 ;}};
        taihejin = new Item("taihejin",Color.valueOf("5268a5"));
        shenlingwuzhuang = new Item("shenlingwuzhuang",Color.valueOf("222222"));
        shenlingzhuangjia = new Item("shenlingzhuangjia",Color.valueOf("333333"));
        shenlingdongli = new Item("shenlingdongli",Color.valueOf("444444"));
        shenwulantu = new LanTuItem("shenwulantu",Color.valueOf("442244"));
        shenzhuanglantu = new LanTuItem("shenzhuanglantu",Color.valueOf("332233"));
        shendonglantu = new LanTuItem("shendonglantu",Color.valueOf("224422"));
        shenlinglantu = new LanTuItem("shenlinglantu",Color.valueOf("223322"));
//        lei = new Item("lei", Color.darkGray){{
//            radioactivity = 3 ;
//            hardness = 5 ;
//        }};
        you = new Item("you", Color.valueOf("719168")){{
           hardness = 6 ;
           radioactivity = 2.5f ;
        }};
        you235 = new Item("you235", Color.valueOf("4c8f3a")){{
            radioactivity = 4f ;
            hardness = 6 ;
            explosiveness = 6.5f ;
        }};
        you238 = new Item("you238", Color.valueOf("d2d65b")){{
            hardness = 6 ;
            radioactivity = 3.5f ;
            explosiveness = 5 ;
        }};
        /*
        you233 = new FangSheXingItem("you233", Color.valueOf("aaaa00")){{
           hardness = 5 ;
           radioactivity = 3.5f ;
           flammability = 5 ;
        }};
        */
        fangfushe = new SYBSItem("fangfushe" , Color.valueOf("497712")) {{
            fangfushe = 1 ;
        }};
        hashihejin = new SYBSItem("hashihejin" , Color.valueOf("fcf387")) {{
            fangfushi = 2.05f ;
        }};
        ruangang = new Item("ruangang" , new Color(){{
            r = 80 / 255f ;
            g = 236 / 255f ;
            b = 255 / 255f ;
            a = 1 ;
            set(Color.valueOf("a9a9a9")) ;
        }});
        yijihejin = new Item("yijihejin" , Color.valueOf("eeeeee")){{
            explosiveness = 0.8f ;
            flammability = 0 ;
            radioactivity = 0 ;
            charge = 1.2f ;
        }};
        erjihejin = new Item("erjihejin" , Color.valueOf("eeeeee")){{
            explosiveness = 0.7f ;
            flammability = 0 ;
            radioactivity = 0 ;
            charge = 1.5f ;
        }};
        sanjihejin = new Item("sanjihejin" , Color.valueOf("eeeeee")){{
            explosiveness = 1f ;
            flammability = 0 ;
            radioactivity = 0 ;
            charge = 2f ;
        }};
        xiangjiao = new Item("xiangjiao" , Color.valueOf("563030")) {{
            flammability = 0.85f ;
        }};
        suliao = new Item("suliao" , Color.valueOf("eeeeee")) {{
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
        }};
        hefeiliao = new SYBSItem("hefeiliao", Color.valueOf("9b9b00")){{
            hardness = 4 ;
            radioactivity = 6f ;
            explosiveness = 6 ;
            fusheqiangdu = 40 / 25f ;
        }};
        shendiwuzhuang = new Item("shendiwuzhuang",Color.valueOf("222222"));
        shendizhuangjia = new Item("shendizhuangjia",Color.valueOf("333333"));
        shendidongli = new Item("shendidongli",Color.valueOf("444444"));
        huimiewuzhuanglantu = new LanTuItem("huimiewuzhuanglantu",Color.valueOf("442244"));
        huimiezhuangjialantu = new LanTuItem("huimiezhuangjialantu",Color.valueOf("332233"));
        huimiedonglilantu = new LanTuItem("huimiedonglilantu",Color.valueOf("224422"));
        shendilantu = new LanTuItem("shendilantu",Color.valueOf("223322"));
        yanmieneng = new Item("yanmieneng",Color.valueOf("333333"));
        anjinshu = new Item("anjinshu",Color.valueOf("444444")) {{
            explosiveness = 0.5f ;
            flammability = 0.5f ;
            radioactivity = 0.2f ;
            charge = 1 ;
        }};
        anwuzhihejin = new LanTuItem("anwuzhihejin",Color.valueOf("442244")) {{
            explosiveness = 0.75f ;
            flammability = 0.75f ;
            radioactivity = 0.25f ;
            charge = 1.5f ;
        }};




        daole = new SYBSItem("daole") {{
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
        }} ;
        deleike = new SYBSItem("deleike", Color.valueOf("4dff7d")) {{
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
        }} ;
        yiduntanding = new SYBSItem("yiduntanding", Color.valueOf("a27ce5")) {{
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
        }} ;
        bulaoen = new SYBSItem("bulaoen", Color.valueOf("6c87fd")) {{
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
        }} ;
        talituosi = new SYBSItem("talituosi", Color.valueOf("ffffff")){{
            colors.addAll(Color.white, Pal.accent) ;
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
        }} ;
        liumingjing = new SYBSItem("liumingjing", Color.valueOf("8ca9e8")) {{
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
        }} ;
        yuemingzhu = new SYBSItem("yuemingzhu", Color.valueOf("ffa480")) {{
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
        }} ;
        shayebushi = new SYBSItem("shayebushi", Pal.accent){{
            colors.addAll(deleike.color, yiduntanding.color, bulaoen.color, talituosi.color, liumingjing.color, yuemingzhu.color) ;
            hidden = !(Version.build == -1 || ShaYeBuShi.tiaoshi) ;
        }} ;
        deleikeItems.add(Items.serpuloItems);
        deleikeItems.addAll(li,lv,taihejin,shenlingwuzhuang,shenlingzhuangjia,shenlingdongli,shenlinglantu, you, you235, you238, fangfushe, ruangang, yijihejin, erjihejin, sanjihejin, xiangjiao, hashihejin, hefeiliao, yanmieneng, anjinshu, anwuzhihejin, shendiwuzhuang, shendizhuangjia, shendidongli, huimiewuzhuanglantu, huimiezhuangjialantu, huimiedonglilantu, shendilantu);
        deleikeItems.addAll(deleike) ;
        deleikeItems.addAll(Items.erekirItems) ;
    }
    public static class SYBSItem extends Item {
        public float fangfushe = 0 ;
        public float fangfushi = 0 ;
        public Seq<Color> colors = new Seq<>() ;
        public int curretColor = -1, nextColor = -1 ;
        public int timer ;
        public String rawLocalizedName ;
        public float fusheqiangdu = 1 ;

        public SYBSItem(String name, Color color) {
            super(name, color);
            rawLocalizedName = Core.bundle.get(getContentType() + "." + this.name + ".raw", this.name) ;
        }

        public SYBSItem(String name) {
            super(name);
            rawLocalizedName = Core.bundle.get(getContentType() + "." + this.name + ".raw", this.name) ;
        }

        @Override
        public void setStats(){
            super.setStats();
            stats.addPercent(SYBSStats.fangfushe, fangfushe);
            stats.addPercent(SYBSStats.fangfushi, fangfushi);
            if (radioactivity > 0) {
                stats.add(SYBSStats.fusheqiangdu, fangFuSheCiChangQiangDu * fusheqiangdu);
            }
        }
    }
}
