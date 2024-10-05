package shayebushi.game;

import arc.Core;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.type.SectorPreset;
import shayebushi.ShaYeBuShi;

public class SYBSObjectives {
    public static class ChooseOne implements Objectives.Objective {
        public UnlockableContent content ;

        public ChooseOne(UnlockableContent content){
            this.content = content;
        }

        protected ChooseOne(){}

        @Override
        public boolean complete() {
            return !content.unlocked();
        }

        @Override
        public String display() {
            return Core.bundle.format("requirement.chooseone",
                    //TODO broken for multi tech nodes.
                    (content.techNode == null || content.techNode.parent == null || content.techNode.parent.content.unlocked()) ?
                            (content.emoji() + " " + content.localizedName) : "???") ;
        }
    }

    public static class SectorCompleteOr implements Objectives.Objective {
        public Seq<SectorPreset> sectors = new Seq<>() ;

        public SectorCompleteOr(SectorPreset... ss){
            this.sectors.addAll(ss) ;
        }

        @Override
        public boolean complete() {
            return sectors.find(sp -> sp != null && sp.sector.save != null && sp.sector.isCaptured() && sp.sector.hasBase()) != null;
        }

        @Override
        public String display() {
            String out = Core.bundle.format("requirement.capture", sectors.get(0).localizedName) ;
            for (int i = 1 ; i < sectors.size ; i ++) {
                out += Core.bundle.get("requirement.or") ;
                out += sectors.get(i).localizedName ;
            }
            return out ;
        }
    }

    public static class NanDuOnly implements Objectives.Objective {
        public int nandu = 1 ;

        public NanDuOnly(int n) {
            nandu = n ;
        }

        @Override
        public boolean complete() {
            return ShaYeBuShi.nandu == nandu ;
        }

        @Override
        public String display() {
            return Core.bundle.format("requirement.nanduo", Core.bundle.get("nandu." + ShaYeBuShi.nandus[nandu - 1]));
        }
    }

    public static class NanDuExcept implements Objectives.Objective {
        public int nandu = 1 ;

        public NanDuExcept(int n) {
            nandu = n ;
        }

        @Override
        public boolean complete() {
            return ShaYeBuShi.nandu != nandu ;
        }

        @Override
        public String display() {
            return Core.bundle.format("requirement.nandue", Core.bundle.get("nandu." + ShaYeBuShi.nandus[nandu - 1]));
        }
    }
}
