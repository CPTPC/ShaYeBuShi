package shayebushi.entities.bullet;

import arc.Core;
import arc.graphics.g2d.Draw;
import mindustry.Vars;
import mindustry.ctype.ContentType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Layer;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.payloads.BuildPayload;
import mindustry.world.blocks.power.VariableReactor;
import mindustry.world.blocks.production.HeatCrafter;

import static mindustry.Vars.*;

public class PayloadBullet extends BasicBulletType {
    public String blockName = "" ;
    public Block block ;
    public boolean kill = true ;
    public PayloadBullet() {
        homingPower = 1.2f ;
        despawnHit = true ;
        pierce = true ;
        damage = 0 ;
    }
    @Override
    public void init() {
        super.init();
        if (!blockName.equals("") && block == null) {
            block = content.getByName(ContentType.block, blockName) ;
        }
    }
    @Override
    public void init(Bullet b) {
        super.init(b) ;
        if (block != null) {
            b.data = new BuildPayload(block, b.team);
        }
    }
    @Override
    public void update(Bullet b) {
        super.update(b) ;
        if (b.data instanceof BuildPayload bp) {
            bp.set(b.x, b.y, bp.build.payloadRotation);
            for (Item i : Vars.content.items()) {
                if (bp.build.acceptItem(null, i)) {
                    bp.build.handleItem(null, i);
                }
            }
            for (Liquid l : Vars.content.liquids()) {
                if (bp.build.acceptLiquid(null, l)) {
                    bp.build.handleLiquid(null, l, Integer.MAX_VALUE);
                }
            }
            bp.build.power.status = 1 ;
            bp.build.efficiency = 1 ;
            if (bp.build instanceof Turret.TurretBuild t) {
                t.heat = 1145 ;
            }
            if (bp.build instanceof HeatCrafter.HeatCrafterBuild t) {
                t.heat = 1145 ;
            }
            if (bp.build instanceof VariableReactor.VariableReactorBuild v) {
                v.heat = 1145 ;
            }
            bp.update(null, null);
        }
        if (kill && (b.x > world.width() * tilesize || b.y > world.height() * tilesize)) {
            despawned(b) ;
            b.remove() ;
        }
    }
    @Override
    public void draw(Bullet b) {
        //super.draw(b) ;
        Draw.z(Layer.block);
        if (b.data instanceof BuildPayload bp) {
            bp.draw();
            if (renderer.drawStatus && block.hasConsumers) {
                bp.build.drawStatus();
            }
        }
        Draw.reset();
    }
    @Override
    public void removed(Bullet b) {
        super.removed(b); ;
        if (b.data instanceof BuildPayload bp) {
            bp.build.kill() ;
        }
    }
}
