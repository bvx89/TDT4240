package no.ntnu.assignment.one;

import no.ntnu.assignment.one.interfaces.LoadListener;
import sheep.game.Game;
import sheep.game.State;

import android.media.SoundPool;

import java.util.ArrayList;

/**
 * Represents the audio device on the system. Contains an instance of the SoundPool
 * class from Android SDK which enables low latency playback of sounds.
 */
public class AudioMngr implements SoundPool.OnLoadCompleteListener {

    // This object manages sounds in a "pool". See Android documentation
    // for more information.
    SoundPool pool;

    ArrayList<LoadListener> mListeners = new ArrayList<LoadListener>();

    /**
     * Private constructor (singleton).
     */
    private AudioMngr() {
        // 16 has here been chosen as an upper limit of simultaneous sounds.
        pool = new SoundPool(16, android.media.AudioManager.STREAM_MUSIC, 0);
    }

    public void addListener(LoadListener l) {
        mListeners.add(l);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int i, int i2) {
        for (LoadListener l : mListeners) {
            l.songLoaded(i);
        }
    }

    /**
     * Holds the sole instance of the Audio singleton. This class exists only
     * to allow lazy allocation of the singleton.
     */
    private static class AudioHolder {
        private final static AudioMngr INSTANCE = new AudioMngr();
    }

    /**
     * Gets the sole instance of this class (creating it on first call).
     * @return The instance of this class.
     */
    public static AudioMngr getInstance() {
        return AudioHolder.INSTANCE;
    }

    /**
     * Gets the SoundPool object this class holds.
     * @return A SoundPool object.
     */
    public SoundPool getSoundPool() {
        return pool;
    }

    /**
     * Loads a sound from the bundle.
     * @param res The resource identifier, for instance R.raw.sound.
     * @return The sound identifier, as provided by the SoundPool.
     */
    public int load(int res) {
        int resourceId = pool.load(Game.getInstance().getContext(), res, 1);
        pool.setOnLoadCompleteListener(this);

        return resourceId;
    }




}