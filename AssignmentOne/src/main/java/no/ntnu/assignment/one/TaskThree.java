package no.ntnu.assignment.one;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

import no.ntnu.assignment.one.graphics.AniImage;
import no.ntnu.assignment.one.interfaces.LoadListener;
import no.ntnu.assignment.one.model.AniChopper;
import no.ntnu.assignment1.task1.R;
import sheep.game.State;
import sheep.input.TouchListener;
import sheep.math.Vector2;

/**
 * Created by bvx89 on 1/16/14.
 */
public class TaskThree extends State implements TouchListener {
    public static final String TAG = "Test";
    private AniChopper chopperOne;
    private AniChopper chopperTwo;

    private int soundId;

    public TaskThree() {
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
        chopperOne = new AniChopper(new AniImage(R.drawable.chopper_sprite, 10, 4));
        chopperTwo = new AniChopper(new AniImage(R.drawable.chopper_sprite, 10, 4));

        chopperOne.setPosition(150f, 280f);
        chopperTwo.setPosition(450f, 480f);
        chopperTwo.setSpeed(-200f, -200f);

    }

    private boolean clean() {
        getGame().popState();
        getGame().pushState(new TitleScreen());
        chopperOne.die();
        chopperTwo.die();
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Clear bg
        canvas.drawColor(Color.DKGRAY);

        chopperOne.draw(canvas);
        chopperTwo.draw(canvas);
    }

    @Override
    public void update(float dt) {
        // gameWorld.update(dt);
        chopperOne.update(dt);
        chopperTwo.update(dt);

        if (chopperOne.collides(chopperTwo)) {
            Log.d(TAG, "COLLISION LOL");

            /*
            Vector2 n = new Vector2(    chopperOne.getX() - chopperTwo.getX(),
                                        chopperOne.getY() - chopperTwo.getY());
            Vector2 v1 = chopperOne.getSpeed();
            Vector2 v2 = chopperTwo.getSpeed();

            float a1 = v1.dot(n);
            float a2 = v2.dot(n);

            float p = a1 - a2;
            n.multiply(p);

            v1.subtract(n);
            v2.subtract(n);
            chopperOne.setSpeed(v1);
            chopperTwo.setSpeed(v2);
            Log.d(TAG, "speed1: " + v1.toString());
            Log.d(TAG, "speed2: " + v2.toString());
            */
        }

    }
}
