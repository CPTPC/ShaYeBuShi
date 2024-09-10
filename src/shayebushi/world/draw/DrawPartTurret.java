//package shayebushi.world.draw;
//
//import arc.graphics.g2d.Draw;
//import mindustry.entities.part.DrawPart;
//import mindustry.gen.Building;
//import mindustry.graphics.Drawf;
//import mindustry.graphics.Layer;
//import mindustry.world.blocks.defense.turrets.Turret;
//import mindustry.world.draw.DrawTurret;
//
//public class DrawPartTurret extends DrawTurret {
//    @Override
//    public void draw(Building building){
//        draw(building, 0, 0) ;
//    }
//    public void draw(Building build, float xOffset, float yOffset){
//        Turret turret = (Turret)build.block;
//        Turret.TurretBuild tb = (Turret.TurretBuild)build;
//
//        //Draw.rect(base, build.x + xOffset, build.y + yOffset);
//        Draw.color();
//
//        Draw.z(Layer.turret - 0.5f);
//
//        Drawf.shadow(preview, build.x + xOffset + tb.recoilOffset.x - turret.elevation, build.y + yOffset + tb.recoilOffset.y - turret.elevation, tb.drawrot());
//
//        Draw.z(Layer.turret);
//
//        drawTurret(turret, tb);
//        drawHeat(turret, tb);
//
//        if(parts.size > 0){
//            if(outline.found()){
//                //draw outline under everything when parts are involved
//                Draw.z(Layer.turret - 0.01f);
//                Draw.rect(outline, build.x + xOffset + tb.recoilOffset.x, build.y + yOffset + tb.recoilOffset.y, tb.drawrot());
//                Draw.z(Layer.turret);
//            }
//
//            float progress = tb.progress();
//
//            //TODO no smooth reload
//            var params = DrawPart.params.set(build.warmup(), 1f - progress, 1f - progress, tb.heat, tb.curRecoil, tb.charge, tb.x + xOffset + tb.recoilOffset.x, tb.y + yOffset + tb.recoilOffset.y, tb.rotation);
//
//            for(var part : parts){
//                params.setRecoil(part.recoilIndex >= 0 && tb.curRecoils != null ? tb.curRecoils[part.recoilIndex] : tb.curRecoil);
//                part.draw(params);
//            }
//        }
//    }
//
//}
