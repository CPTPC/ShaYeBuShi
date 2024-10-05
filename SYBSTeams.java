package shayebushi;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.game.Team;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class SYBSTeams {
    public static SYBSTeam bai ;
    public static Seq<SYBSTeam> sybsTeams = new Seq<>() ;
    public static void load() {
        bai = new SYBSTeam(7, "bai", Color.white) ;
        sybsTeams.add(bai) ;
    }

    public static class SYBSTeam extends Team{

        public SYBSTeam(int id, String name, Color color) {
            super(id, name, color);
        }

        public SYBSTeam(int id, String name, Color color, Color pal1, Color pal2, Color pal3) {
            super(id, name, color, pal1, pal2, pal3);
        }
    }
}
