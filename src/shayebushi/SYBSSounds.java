package shayebushi;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Sound;
import arc.files.Fi;
import arc.files.ZipFi;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.core.Version;

import static mindustry.Vars.mods;

public class SYBSSounds {
    public static Sound impact = new Sound() ;
    public static Sound explode = new Sound() ;
    public static Sound anvil = new Sound() ;
    public static Sound bell = new Sound() ;
    public static Sound flameBig = new Sound() ;
    public static void load() {
        //impact = loadSound("impact") ;
        Fi target = Version.build == -1 ? Core.files.absolute("D:\\Mindustry-master\\core\\build\\libs\\A-files\\sounds") : mods.getMod("shayebushi").file != null ? new Seq<Fi>(new ZipFi(mods.getMod("shayebushi").file).list()).find(f -> f.name().equals("sounds")) : null ;
        impact = new Sound(target.child("impact.ogg")) ;
        explode = new Sound(target.child("explode.ogg")) ;
        anvil = new Sound(target.child("anvil.ogg")) ;
        bell = new Sound(target.child("bell.ogg")) ;
        flameBig = new Sound(target.child("flameBig.ogg")) ;
    }
    public static Sound loadSound(String soundName){
        if(!Vars.headless){
            String name = "sounds/" + soundName;
            String path = Vars.tree.get(name + ".ogg").exists() ? name + ".ogg" : name + ".mp3";

            Sound sound = new Sound();

            AssetDescriptor<?> desc = Core.assets.load(path, Sound.class, new SoundLoader.SoundParameter(sound));
            desc.errored = Throwable::printStackTrace;
            return sound;
        }else return new Sound();
    }
}
