package no.ntnu.assignment.one;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import no.ntnu.assignment.one.interfaces.LoadListener;
import no.ntnu.assignment.one.model.Chopper;
import no.ntnu.assignment1.task1.R;
import sheep.game.State;
import sheep.graphics.Image;
import sheep.input.TouchListener;

/**
 * Created by bvx89 on 1/18/14.
 */
public class TaskTwo extends State implements TouchListener, LoadListener {
    public static final String TAG = "Test";
    private Chopper chopper;

    private int soundId;
    private AudioMngr audio = AudioMngr.getInstance();

    public TaskTwo() {
        this.addTouchListener(new TouchListener() {

            @Override
            public boolean onTouchUp(MotionEvent event) {
                return clean();
            }

            @Override
            public boolean onTouchMove(MotionEvent event) {
                return clean();
            }

            @Override
            public boolean onTouchDown(MotionEvent event) {
                return clean();
            }

        });

        // Get image
        chopper = new Chopper(new Image(R.drawable.chopper_static));

        // Register as Listener for load
        audio.addListener(this);

        // Get correct id
        soundId = audio.load(R.raw.chopper);

    }

    private boolean clean() {
        audio.getSoundPool().stop(soundId);
        getGame().popState();
        getGame().pushState(new TitleScreen());
        chopper.die();
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Clear bg
        canvas.drawColor(Color.DKGRAY);

        chopper.draw(canvas);
    }

    @Override
    public void update(float dt) {
        // gameWorld.update(dt);
        chopper.update(dt);

    }

    @Override
    public void songLoaded(int id) {
        if (id == soundId) {
            audio.getSoundPool().play(soundId, 1, 1, 1, -1, 1);
        }
    }
}