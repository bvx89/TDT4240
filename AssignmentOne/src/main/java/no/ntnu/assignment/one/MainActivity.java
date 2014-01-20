package no.ntnu.assignment.one;

import android.app.Activity;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;

import java.util.ArrayList;

import no.ntnu.assignment.one.interfaces.LoadListener;
import sheep.game.Game;

public class MainActivity extends Activity implements LoadListener {
    private Game mGame;

    private AudioMngr mAudio = AudioMngr.getInstance();
    private ArrayList<Integer> audioList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get resolution of display
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Config.WINDOW_HEIGHT = dm.heightPixels;
        Config.WINDOW_WIDTH  = dm.widthPixels;

        // Register as a listener with the audio manager
        mAudio.addListener(this);

        init();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("TAG", "onPause called()");

        // Stop all songs
        SoundPool s = mAudio.getSoundPool();
        for (Integer i : audioList) {
            s.stop(i);
        }

        mGame.surfaceDestroyed(null);

    }

    @Override
    protected void onResume() {
        super.onResume();

        init();
    }

    private void init() {
        mGame = new Game(this, null);

        // Push the main state.
        mGame.pushState(new TitleScreen());

        // View the game.
        setContentView(mGame);
    }

    @Override
    public void songLoaded(int id) {
        audioList.add(id);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            mGame.popState();
            mGame.pushState(new TitleScreen());
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
