package no.ntnu.assignment.one;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import sheep.game.Game;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Game game = new Game(this, null);

        // Get resolution of display
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Config.WINDOW_HEIGHT = dm.heightPixels;
        Config.WINDOW_WIDTH  = dm.widthPixels;

        // Push the main state.
        game.pushState(new TitleScreen());

        // View the game.
        setContentView(game);
    }

}
