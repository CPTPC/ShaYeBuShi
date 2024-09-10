package shayebushi.entities.bullet;

import arc.Events;
import arc.util.Tmp;
import mindustry.entities.Fires;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.world.blocks.ConstructBlock;
import shayebushi.SYBSShenShengUnitTypes;
import shayebushi.SYBSUnitTypes;
import shayebushi.ShaYeBuShi;

import static arc.util.Time.toSeconds;
import static mindustry.Vars.tilesize;

public class JvChiBulletType extends GaiLvMiaoBulletType implements WuShiXianShangc {
    public float zhuanwan1 ;
    public float zhuanwan2 ;
    public float chang = 60 * tilesize ;
    public float kuan = 16 * tilesize ;
    public float jiange = 0.5f * tilesize ;
    public JvChiBulletType(float d, float s, float g) {
        super(s, d, g);
        pierce = true;
        width = tilesize ;
        height = 4 * width ;
        speed = (jiange + height) ;
        zhuanwan1 = chang / speed ;
        zhuanwan2 = kuan / speed + zhuanwan1 ;
        lifetime = (chang * 2 + kuan) / speed ;
        pierceArmor = true ;
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        //System.out.println((int) b.time + " "  + (int)zhuanwan2);
        if (!(b.data instanceof boolean[])) {
            b.data = new boolean[]{false, false} ;
        }
        if ((b.time >= zhuanwan1 || (int)b.time >= (int)zhuanwan1 - 1) && !((boolean[])b.data)[0]) {
            ((boolean[])b.data)[0] = true ;
            b.rotation(b.rotation() + 90);
        }
        if ((b.time >= zhuanwan2 || (int)b.time >= (int)zhuanwan2 - 1) && !((boolean[])b.data)[1]) {
            ((boolean[])b.data)[1] = true ;
            b.rotation(b.rotation() + 90);
        }
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        poxianshang(this, b, entity);
        handlePierce(b, health, entity.x(), entity.y());
    }

    @Override
    public float calculateRange() {
        return chang ;
    }
}
