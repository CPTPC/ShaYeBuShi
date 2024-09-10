//package shayebushi.world.draw;
//
//import arc.Core;
//import arc.graphics.g2d.TextureRegion;
//import arc.struct.Seq;
//import mindustry.world.Block;
//import mindustry.world.draw.DrawTurret;
//import shayebushi.world.blocks.defense.turrets.MultiTurret;
//import shayebushi.world.blocks.defense.turrets.PartTurret;
//
//public class DrawMultiTurret extends DrawTurret {
//    public TextureRegion[] finalIcons(MultiTurret block){
//        if(block.turrets.size != 0){
//            Seq<TextureRegion> s = new Seq<>() ;
//            for (PartTurret t : block.turrets){
//                s.addAll(t.drawer.finalIcons(t)) ;
//            }
//            return s.toArray(TextureRegion.class);
//        }
//        return icons(block);
//    }
//
//}
