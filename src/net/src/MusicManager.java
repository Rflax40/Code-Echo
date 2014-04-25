package net.src;

import org.lwjgl.openal.AL10;
import shade.src.resource.Resource;
import shade.src.sound.SoundBuffer;
import shade.src.sound.SoundSource;

public class MusicManager {

    private static final String root = "sound/music/";
    public final SoundSource source;
    public final Resource[] musics;
    public int nowPlaying;
    public boolean loading;
    private SoundBuffer oldBuffer;

    public MusicManager() {
        source = new SoundSource().setLooping(false);
        musics = new Resource[] {Resource.getResource(root + "barn.wav"), Resource.getResource(root + "forcedMove.wav"), Resource.getResource(root + "junkyard.wav"), Resource.getResource(root + "pawnshop.wav")};
    }

    public void queueNextSong(final int next) {
        loading = true;
        Runnable loader = new Runnable() {
            @Override
            public void run() {
                SoundBuffer buffer = SoundBuffer.getSoundBuffer(musics[next]);
                System.out.println(next);
                if (oldBuffer != null) {
                    source.stop();
                    System.out.println("STOPPED");
                    source.removeFromQueue(oldBuffer);
                    System.out.println("REMOVED");
                    oldBuffer.unload();
                    System.out.println("UNLOADED");
                }
                oldBuffer = buffer;
                source.addToQueue(buffer);
                System.out.println("ADDED");
                source.play();
                while (source.getState() != AL10.AL_PLAYING) {
                    ;
                }
                System.out.println("PLAYING");
                nowPlaying = next;
                loading = false;
            }
        };
        Thread thread = new Thread(loader);
        thread.setDaemon(true);
        thread.start();
    }
}
