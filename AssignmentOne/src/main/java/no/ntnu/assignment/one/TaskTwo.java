package no.ntnu.assignment.one;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

import no.ntnu.assignment.one.interfaces.LoadListener;
import no.ntnu.assignment.one.model.Chopper;
import no.ntnu.assignment1.task1.R;
import sheep.game.State;
import sheep.graphics.Font;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.input.TouchListener;

/**
 * Created by bvx89 on 1/18/14.
 */
public class TaskTwo extends State implements TouchListener {
    public static final String TAG = "Test";
    private Chopper chopper;
    private TextButton labelFPS;
    private Image imgChopper;

    private float accumulatedTime = 0;
    private float rate = 100;

    public TaskTwo() {
        this.addTouchListener(new TouchListener() {

            @Override
            public boolean onTouchUp(MotionEvent event) {
                return false;
            }

            @Override
            public boolean onTouchMove(MotionEvent event) {
                //setTarget(event.getX(), event.getY());
                return false;
            }

            @Override
            public boolean onTouchDown(MotionEvent event) {
                setTarget(event.getX(), event.getY());
                return false;
            }

        });

        imgChopper = new Image(R.drawable.chopper_static);
        chopper = new Chopper(imgChopper);

        Paint[] ButtonColors = {
                new Font(255, 255, 255, 50.0f,
                        Typeface.SANS_SERIF, Typeface.BOLD),
                new Font(57, 152, 249, 50.0f,
                        Typeface.SANS_SERIF, Typeface.BOLD)
        };

        labelFPS = new TextButton(0, 50.0f, "X: 0, Y: 0", ButtonColors);

    }

    private void setTarget(float x, float y) {
        float dx=x-chopper.getX();
        float dy=y-chopper.getY();

        float speed=(float)Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
        float currentSpeed=(float)Math.sqrt((double)(chopper.getSpeed().getX() * chopper.getSpeed().getX()) +
                                            (double)(chopper.getSpeed().getY() * chopper.getSpeed().getY()));
        dx=dx * currentSpeed / speed;
        dy=dy * currentSpeed / speed;

        // Check left and right edge
        if (dx < 0 && chopper.getSpeed().getX() >= 0) {
            chopper.setScale(1, 1);
            chopper.setPosition(chopper.getPosition().getX() - imgChopper.getWidth(), chopper.getPosition().getY());
        } else if (chopper.getSpeed().getX() < 0 && dx >= 0) { // Right edge
            chopper.setScale(-1, 1);
            chopper.setPosition(chopper.getPosition().getX() + imgChopper.getWidth(), chopper.getPosition().getY());
        }


        chopper.setSpeed(dx, dy);
    }

    private boolean clean() {
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

        labelFPS.draw(canvas);

        chopper.draw(canvas);
    }

    @Override
    public void update(float dt) {
        // gameWorld.update(dt);
        chopper.update(dt);

        // Count accumulated time
        accumulatedTime += dt * 1000;
        if (accumulatedTime > rate) {

            // Print new position to screen
            int x = (int)chopper.getX();
            int y = (int)chopper.getY();
            labelFPS.setLabel(String.format("X: %d, Y: %d", x, y));

            // Reset counter
            accumulatedTime = accumulatedTime - rate;
        }
    }
}