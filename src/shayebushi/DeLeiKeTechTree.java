package shayebushi;

import arc.struct.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.game.Objectives.*;
import mindustry.type.*;
import mindustry.type.unit.*;
import mindustry.world.blocks.defense.turrets.*;
import shayebushi.game.SYBSObjectives;

import static mindustry.Vars.*;
import static mindustry.content.Blocks.*;
import static mindustry.content.SectorPresets.*;
import static mindustry.content.TechTree.*;
import static shayebushi.SYBSBlocks.* ;
import static shayebushi.SYBSItems.hashihejin;
import static shayebushi.SYBSSectorPresets.*;
import static shayebushi.SYBSUnitTypes.*;

public class DeLeiKeTechTree{
    static IntSet balanced = new IntSet();

    static void rebalanceBullet(BulletType bullet){
        if(balanced.add(bullet.id)){
            bullet.damage *= 1f;
        }
    }

    //TODO remove this
    public static void rebalance(){
        for(var unit : content.units().select(u -> u instanceof ErekirUnitType)){
            for(var weapon : unit.weapons){
                rebalanceBullet(weapon.bullet);
            }
        }

        for(var block : content.blocks()){
            if(block instanceof Turret turret){
                if(turret instanceof ItemTurret item){
                    for(var bullet : item.ammoTypes.values()){
                        rebalanceBullet(bullet);
                    }
                }else if(turret instanceof ContinuousLiquidTurret cont){
                    for(var bullet : cont.ammoTypes.values()){
                        rebalanceBullet(bullet);
                    }
                }else if(turret instanceof ContinuousTurret cont){
                    rebalanceBullet(cont.shootType);
                }
            }
        }
    }

    public static void load(){
        rebalance();

        //TODO might be unnecessary with no asteroids
        Seq<Objective> deleikeSector = Seq.with(new OnPlanet(SYBSPlanets.deleike));

        var costMultipliers = new ObjectFloatMap<Item>();
        for(var item : content.items()) costMultipliers.put(item, 0.9f);

        //these are hard to make
        costMultipliers.put(Items.oxide, 0.5f);
        costMultipliers.put(Items.surgeAlloy, 0.7f);
        costMultipliers.put(Items.carbide, 0.3f);
        costMultipliers.put(Items.phaseFabric, 0.2f);

        SYBSPlanets.deleike.techTree = nodeRoot("deleike", coreNucleus,  () -> {
            node(taihejinchuansongdai, () -> {
                node(taihejinjiaochaqi, () -> {
                    node(taihejinluyouqi, () -> {
                        node(fangfushichuansongdai, Seq.with(new OnSector(liusuanzhaoze), new Research(hashihejin)), () -> {

                        }) ;
                        node(taihejincangku, Seq.with(new SectorComplete(luojiaodian)), () -> {
                            node(taihejinzhuangxieqi, Seq.with(new SectorComplete(luojiaodian)), () -> {

                            }) ;
                        }) ;
                        node(taihejinqiao, () -> {
                            node(taihejinzhiqu, () -> {

                            }) ;
                        }) ;
                    }) ;
                }) ;
            });
            node(fangfushibeng, Seq.with(new OnSector(liusuanzhaoze), new Research(hashihejin)), () -> {
                node(fangfushidaoguan, Seq.with(new OnSector(liusuanzhaoze), new Research(hashihejin)), () -> {

                }) ;
            }) ;
            node(SYBSBlocks.ceshi, Seq.with(new SYBSObjectives.NanDuOnly(1)), () -> {
                node(wupinyuan, Seq.with(new SYBSObjectives.NanDuOnly(1)), () -> {
                    node(tiaojieshijian, Seq.with(new SYBSObjectives.NanDuOnly(1)), () -> {
                       node(tiaobo, Seq.with(new SYBSObjectives.NanDuOnly(1)), () -> {
//                           node(shanghaixianshi, Seq.with(new SYBSObjectives.NanDuExcept(1)), () -> {
//                               node(yicixingxiebao, Seq.with(new SYBSObjectives.NanDuExcept(1)), () -> {
//                                   node(wuxianlichang, Seq.with(new SYBSObjectives.NanDuExcept(1)), () -> {
//                                       node(fanyiqie, Seq.with(new SYBSObjectives.NanDuExcept(1)), () -> {
//                                           node(fankongjun, Seq.with(new SYBSObjectives.NanDuExcept(1)), () -> {
//                                               node(fanlujun, Seq.with(new SYBSObjectives.NanDuExcept(1)), () -> {
//                                                   node(fanhaijun, Seq.with(new SYBSObjectives.NanDuExcept(1)), () -> {
//                                                       node(jinyongchuliqi, Seq.with(new SYBSObjectives.NanDuExcept(1)), () -> {
//
//                                                       });
//                                                   });
//                                               });
//                                           });
//                                       });
//                                   });
//                               });
//                           });
                       });
                    });
                });
            });
            context().researchCostMultipliers = costMultipliers;
            node(lvzuantou, () -> {
                node(taihejinzuantou, Seq.with(new SectorComplete(luojiaodian)), () ->{
                    node(duogongnengzuantou, Seq.with(new OnSector(fushekuangshan)), () -> {

                    });
                });
                node(taihejinyelianchang, () -> {
                    node(keyanzhongxin, Seq.with(new SectorComplete(bingdonggaoyuan)), () -> {

                    });
                    node(shenlingmokuai, Seq.with(new SectorComplete(bingdonggaoyuan)), () -> {

                    });
                    node(youlixinji, Seq.with(new OnSector(fushekuangshan)), () -> {
                        node(fangfushecailiaofuheji, Seq.with(new OnSector(fushekuangshan)), () -> {
                            node(hejinduanzaoqi, Seq.with(new OnSector(liusuanzhaoze)), () -> {
                                node(lianheyelianjizu, Seq.with(new SectorComplete(qinggongyequ)), () -> {
                                    node(jisuchongyayitiji, Seq.with(new SectorComplete(qinggongyequ)), () -> {
                                        node(lixichongzuhejinduanlu, Seq.with(new SectorComplete(qinggongyequ)), () -> {
                                            node(xiangweizonghegongchang, Seq.with(new SectorComplete(qinggongyequ)), () -> {

                                            });
                                        });
                                    });
                                });
                                node(ruangangduandaji, Seq.with(new SectorComplete(qinggongyequ)), () -> {
                                    node(xiangjiaohechengji, Seq.with(new SectorComplete(qinggongyequ)), () -> {

                                    });
                                });
                            });
                        });
                    });
                    node(fangyoutiquji, Seq.with(new SectorComplete(bingdonggaoyuan)), () -> {
                        node(daxinglengdongyehunheqi, Seq.with(new SectorComplete(bingdonggaoyuan)), () -> {
                            node(daxingdianjieji, Seq.with(new SectorComplete(bingdonggaoyuan)), () -> {
                                node(daxingyanghuashi, Seq.with(new SectorComplete(bingdonggaoyuan)), () -> {
                                    node(daxingtanhuawuganguo, Seq.with(new SectorComplete(bingdonggaoyuan)), () -> {

                                    }) ;
                                }) ;
                            }) ;
                        }) ;
                    }) ;
                    node(weixingreliangchuanshuji, Seq.with(new SectorComplete(bingdonggaoyuan)), () -> {
                        node(weixingreliangluyouqi, Seq.with(new SectorComplete(bingdonggaoyuan)), () -> {

                        }) ;
                        node(fangfushelichang, Seq.with(new OnSector(fushekuangshan)), () -> {

                        });
                    }) ;
                });
            });
            node(bianyaqi, Seq.with(new SectorComplete(bingdonggaoyuan)), () -> {
                node(diyadianjiedian, Seq.with(new SectorComplete(bingdonggaoyuan)), () -> {

                });
                node(zhongyadianjiedian, Seq.with(new SectorComplete(bingdonggaoyuan)), () -> {

                });
                node(gaoyadianjiedian, Seq.with(new SectorComplete(bingdonggaoyuan)), () -> {
                    node(liebianfanyingdui, Seq.with(new SectorComplete(zhonggongyequ)), () -> {

                    });
                });
                node(xiulijihuotouyingqi, Seq.with(new SectorComplete(fushekuangshan), new SectorComplete(liusuanzhaoze), new SectorComplete(liebianfeiqu)), () -> {
                    node(zhendanghudunfashengqi, Seq.with(new SectorComplete(fushekuangshan), new SectorComplete(liusuanzhaoze), new SectorComplete(liebianfeiqu)), () -> {
                        node(jixianchaosu, Seq.with(new SectorComplete(fushekuangshan), new SectorComplete(liusuanzhaoze), new SectorComplete(liebianfeiqu)), () -> {
                            node(zhanquweihuxitong, Seq.with(new SectorComplete(qinggongyequ)), () -> {

                            }) ;
                        }) ;
                    }) ;
                }) ;
            });
            node(cefan,() -> {
                node(jingu,() ->{
                    node(jizeng,()->{
                        node(zhadan, Seq.with(new SectorComplete(bingdonggaoyuan)), () -> {
                            node(xiaoxinghedan, Seq.with(new SectorComplete(fushekuangshan)), () -> {

                            });
                        }) ;
                    });
                    node(shandian, Seq.with(new SYBSObjectives.SectorCompleteOr(liusuanzhaoze, liebianfeiqu)), () -> {
                        node(shandian6, Seq.with(new SYBSObjectives.SectorCompleteOr(liusuanzhaoze, liebianfeiqu)), () -> {
                            node(zhuoshao, Seq.with(new SectorComplete(rongyantongdao), new Research(yanjiang)), () -> {
                                node(xielei, Seq.with(new SectorComplete(rongyantongdao)), () -> {

                                }) ;
                            }) ;
                            //node(jingkong, Seq.with(new SectorComplete(qinggongyequ)), () -> {

                            //}) ;
                        });
                    });
                    node(jianmo, Seq.with(new SectorComplete(qinggongyequ)), () -> {
                        node(zaie, Seq.with(new SectorComplete(fushekuangshan), new SectorComplete(liusuanzhaoze), new SectorComplete(liebianfeiqu)), () -> {

                        }) ;
                    }) ;
                });
                node(shenlingzhenghe, Seq.with(new SectorComplete(bingdonggaoyuan)), () ->{

                });
            });
            node(danweizhizaochang,Seq.with(new SectorComplete(bingdonggaoyuan)),() -> {
                node(shenlingzhuangpei, Seq.with(new SectorComplete(bingdonggaoyuan),new Research(danweizhizaochang)), () -> {
                    node(shenqi,Seq.with(new Research(shenlingzhuangpei), new SectorComplete(fushekuangshan)),() -> {

                    });
                    node(duoxing,Seq.with(new Research(shenlingzhuangpei), new OnSector(fushekuangshan)),() -> {

                    });
                    node(xieling,Seq.with(new Research(shenlingzhuangpei), new SectorComplete(fushekuangshan)),() -> {

                    });
                    node(anye,Seq.with(new Research(shenlingzhuangpei), new SectorComplete(liebianfeiqu)),() -> {

                    });
                    node(shengling,Seq.with(new Research(shenlingzhuangpei), new OnSector(liebianfeiqu)),() -> {

                    });
                    node(chichao, Seq.with(new Research(shenlingzhuangpei), new OnSector(liusuanzhaoze)),() -> {

                    });
                    node(shuihua, Seq.with(new Research(shenlingzhuangpei), new SectorComplete(liusuanzhaoze)), () -> {

                    });
                });
                node(gaojimokuai,(Seq.with(new Research(shenlingzhuangpei))), ()->{
                    node(moneng, Seq.with(new Research(shenlingzhuangpei), new Research(gaojimokuai), new SectorComplete(liebianfeiqu)), () -> {

                    });
                });
                node(z01, Seq.with(new SectorComplete(qinggongyequ)), () -> {
                    node(affh01, Seq.with(new SectorComplete(qinggongyequ)), () -> {

                    }) ;
                }) ;
            });
            node(luojiaodian,
                    //ItemStack.with(Items.copper,1000,Items.lead,1000,Items.surgeAlloy,1000,Items.plastanium,1000,Items.beryllium,1000,Items.blastCompound,1000,Items.carbide,1000,Items.coal,1000,Items.dormantCyst,1000,Items.fissileMatter,1000,Items.graphite,1000,Items.metaglass,1000,Items.oxide,1000,Items.phaseFabric,1000,Items.pyratite,3333,Items.sand,1000,Items.scrap,1000,Items.silicon,1000,Items.sporePod,1000,Items.thorium,1000,Items.titanium,1000,Items.tungsten,1000),
                    Seq.with(
                            new SectorComplete(planetaryTerminal),
                            new SectorComplete(origin),
                            new Research(origin),
                            new Research(planetaryTerminal)
                    ), () -> {
                        node(SYBSSectorPresets.bingdonggaoyuan,Seq.with(new SectorComplete(luojiaodian),new Research(taihejinzuantou),new Research(taihejinyelianchang)), () -> {
                            node(fushekuangshan,Seq.with(new SectorComplete(bingdonggaoyuan),new Research(danweizhizaochang),new Research(keyanzhongxin)), () -> {
                                node(liusuanzhaoze,Seq.with(new SectorComplete(fushekuangshan), new Research(SYBSItems.fangfushe), new Research(fangfushelichang)), () -> {
                                    node(liebianfeiqu,Seq.with(new SectorComplete(fushekuangshan), new Research(SYBSItems.fangfushe), new Research(fangfushelichang)), () -> {
                                        node(rongyantongdao,Seq.with(new SectorComplete(fushekuangshan), new SectorComplete(liusuanzhaoze), new SectorComplete(liebianfeiqu), new Research(SYBSItems.fangfushe), new Research(fangfushelichang), new Research(SYBSItems.hashihejin), new Research(fangfushibeng), new Research(SYBSItems.hefeiliao)), () -> {
                                            node(qinggongyequ,Seq.with(new SectorComplete(rongyantongdao), new Research(xielei), new Research(zhuoshao)), () -> {

                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
            nodeProduce(Items.copper, () -> {
                nodeProduce(Liquids.water, () -> {

                });

                nodeProduce(Items.lead, () -> {
                    nodeProduce(Items.titanium, () -> {
                        nodeProduce(Liquids.cryofluid, () -> {

                        });

                        nodeProduce(Items.thorium, () -> {
                            nodeProduce(Items.surgeAlloy, () -> {

                            });

                            nodeProduce(Items.phaseFabric, () -> {

                            });
                        });
                    });

                    nodeProduce(Items.metaglass, () -> {

                    });
                });

                nodeProduce(Items.sand, () -> {
                    nodeProduce(Items.scrap, () -> {
                        nodeProduce(Liquids.slag, () -> {

                        });
                    });

                    nodeProduce(Items.coal, () -> {
                        nodeProduce(Items.graphite, () -> {
                            nodeProduce(Items.silicon, () -> {

                            });
                        });

                        nodeProduce(Items.pyratite, () -> {
                            nodeProduce(Items.blastCompound, () -> {

                            });
                        });

                        nodeProduce(Items.sporePod, () -> {

                        });

                        nodeProduce(Liquids.oil, () -> {
                            nodeProduce(Items.plastanium, () -> {

                            });
                        });
                    });
                });
            });
            nodeProduce(Items.beryllium, () -> {
                nodeProduce(Items.sand, () -> {
                    nodeProduce(Items.silicon, () -> {
                        nodeProduce(Items.oxide, () -> {
                            //nodeProduce(Items.fissileMatter, () -> {});
                        });
                    });
                });

                nodeProduce(Liquids.water, () -> {
                    nodeProduce(Liquids.ozone, () -> {
                        nodeProduce(Liquids.hydrogen, () -> {
                            nodeProduce(Liquids.nitrogen, () -> {

                            });

                            nodeProduce(Liquids.cyanogen, () -> {
                                nodeProduce(Liquids.neoplasm, () -> {

                                });
                            });
                        });
                    });
                });

                nodeProduce(Items.graphite, () -> {
                    nodeProduce(Items.tungsten, () -> {
                        nodeProduce(Liquids.slag, () -> {

                        });

                        nodeProduce(Liquids.arkycite, () -> {

                        });

                        nodeProduce(Items.thorium, () -> {
                            nodeProduce(Items.carbide, () -> {

                                //nodeProduce(Liquids.gallium, () -> {});
                            });

                            nodeProduce(Items.surgeAlloy, () -> {
                                nodeProduce(Items.phaseFabric, () -> {

                                });
                            });
                        });
                    });
                });
            });
            nodeProduce(SYBSItems.lv, () -> {
                nodeProduce(SYBSLiquids.yanjiang, () -> {
                    nodeProduce(SYBSLiquids.nongliusuan,()->{

                    });
                });

                nodeProduce(SYBSItems.li, () -> {
                    nodeProduce(SYBSItems.taihejin, () -> {
                        nodeProduce(SYBSItems.ruangang, () -> {
                            nodeProduce(SYBSItems.xiangjiao, () -> {

                            });
                        });
                    });
                });
            });
            nodeProduce(SYBSItems.shenlinglantu, () -> {
                nodeProduce(SYBSItems.shenwulantu, () -> {
                    nodeProduce(SYBSItems.shenlingwuzhuang,()->{
                    });
                });

                nodeProduce(SYBSItems.shendonglantu, () -> {
                    nodeProduce(SYBSItems.shenlingdongli, () -> {
                    });
                });

                nodeProduce(SYBSItems.shenzhuanglantu, () -> {
                    nodeProduce(SYBSItems.shenlingzhuangjia, () -> {
                    });
                });
            });
        });
    }
}
