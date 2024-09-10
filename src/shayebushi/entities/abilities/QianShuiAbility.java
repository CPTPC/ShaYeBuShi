package shayebushi.entities.abilities;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Liquids;
import mindustry.entities.Effect;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.input.Binding;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.world.Block;
import shayebushi.SYBSBinding;
import shayebushi.type.unit.QianTingUnitType;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;
import static mindustry.Vars.state;
import static mindustry.Vars.tilesize;

public class QianShuiAbility extends ShouDongAbilityBase {
    public boolean isxiaqian = false;
    public int timer = 0 , timerr = 0 ;
    public float reload =  600 , reloadd = 3600 ;
    @Override
    public void update(Unit unit){
        super.update(unit);
        boolean b = unit.team == state.rules.waveTeam && unit.isEnemy() ;
        if (this.timer + Time.delta >= this.reload) {
            if (b || (unit.isPlayer() && unit.getPlayer().team() == unit.team && Core.input.keyDown(this.key))) {
                this.isxiaqian = true;
                this.timer = 0 ;
                new Effect(20, e -> {
                    color(Tmp.c1.set(e.color).shiftValue(0.1f));
                    stroke(e.fout() + 0.2f);
                    randLenVectors(e.id, 60, e.rotation * 0.9f, (x, y) -> {
                        Lines.circle(e.x + x, e.y + y, 5f + e.fin() * 5f);
                    });
                }).at(unit.x,unit.y,Liquids.water.color);
            }
        }
        //System.out.println(timerr+"");
        //System.out.println(unit.isPlayer() && unit.getPlayer().team() == unit.team && Core.input.keyDown(Binding.fujineng));
        if (this.isxiaqian && (this.timerr + Time.delta >= this.reloadd || (unit.isPlayer() && unit.getPlayer().team() == unit.team && Core.input.keyDown(this.key2)))){
            this.isxiaqian = false ;
            this.timerr = 0 ;
            new Effect(20, e -> {
                color(Tmp.c1.set(e.color).shiftValue(0.1f));
                stroke(e.fout() + 0.2f);
                randLenVectors(e.id, 60, e.rotation * 0.9f, (x, y) -> {
                    Lines.circle(e.x + x, e.y + y, 5f + e.fin() * 5f);
                });
            }).at(unit.x,unit.y,Liquids.water.color);
        }
        if (!this.isxiaqian) {
            this.timer += Time.delta;
        }
        if (this.isxiaqian) {
            this.timerr += Time.delta ;
        }
        //System.out.println("---"+isxiaqian);
    }
    @Override
    public void draw(Unit unit){
        super.draw(unit) ;
//        if (isxiaqian){
//            if (unit.type instanceof QianTingUnitType q){
//                q.QianShuiColor(unit);
//            }
//        }
//        Draw.reset();
    }
    @Override
    public void displayBars(Unit unit, Table bars){
        bars.add(new Bar("bar.xiaqianlengque", Liquids.water.color.cpy().b(Liquids.water.color.b + 10), () -> this.timer / this.reload)).row();
        bars.add(new Bar("bar.xiaqianshijian", Liquids.water.color, () -> this.timerr / this.reloadd)).row();
    }
    public QianShuiAbility(){
        this.key = SYBSBinding.zhujineng ;
        this.key2 = SYBSBinding.fujineng ;
//        key = 1 + 1 == 3 ? SYBSBinding.zhujineng : Binding.block_select_right;
//        key2 = Binding.select ;
    }
    public void xiaQian(Unit unit){
        if (this.timer + Time.delta >= this.reload) {
            this.isxiaqian = true;
            this.timer = 0 ;
            new Effect(20, e -> {
                color(Tmp.c1.set(e.color).shiftValue(0.1f));
                stroke(e.fout() + 0.2f);
                randLenVectors(e.id, 60, e.rotation * 0.9f, (x, y) -> {
                    Lines.circle(e.x + x, e.y + y, 5f + e.fin() * 5f);
                });
            }).at(unit.x,unit.y,Liquids.water.color);
        }
    }
    public void shangFu(Unit unit) {
        this.isxiaqian = false ;
        this.timerr = 0 ;
        new Effect(20, e -> {
            color(Tmp.c1.set(e.color).shiftValue(0.1f));
            stroke(e.fout() + 0.2f);
            randLenVectors(e.id, 60, e.rotation * 0.9f, (x, y) -> {
                Lines.circle(e.x + x, e.y + y, 5f + e.fin() * 5f);
            });
        }).at(unit.x,unit.y,Liquids.water.color);
    }
}
