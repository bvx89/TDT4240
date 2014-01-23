package no.ntnu.assignment.one;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import no.ntnu.assignment.one.graphics.AniImage;
import no.ntnu.assignment.one.interfaces.LoadListener;
import no.ntnu.assignment.one.model.AniChopper;
import no.ntnu.assignment1.task1.R;
import sheep.collision.Interval;
import sheep.collision.Shape;
import sheep.game.State;
import sheep.math.BoundingBox;
import sheep.math.Vector2;

/**
 * Created by bvx89 on 1/16/14.
 */
public class TaskThree extends State {
    public static final String TAG = "Test";
    private ArrayList<AniChopper> choppers = new ArrayList<>();
    private boolean[] isColliding;

    private int soundId;

    public TaskThree() {

        AniChopper chop;
        for (int i = 0; i < 4; i++) {
            chop = new AniChopper(new AniImage(R.drawable.chopper_sprite, 10, 4));
            chop.setPosition(150f + 100*i, 280 + 280*i);
            chop.setSpeed(-100f, -120f);
            choppers.add(chop);
        }
        isColliding = new boolean[4];
    }

    private boolean clean() {
        getGame().popState();
        getGame().pushState(new TitleScreen());
        for (AniChopper chop : choppers) {
            chop.die();
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Clear bg
        canvas.drawColor(Color.DKGRAY);

        for (AniChopper chop : choppers) {
            chop.draw(canvas);
        }
    }

    @Override
    public void update(float dt) {
        // gameWorld.update(dt);


        AniChopper chop, chop2;
        for (int i = 0; i < choppers.size(); i++) {
            chop = choppers.get(i);

            for (int j = i+1; j < choppers.size(); j++) {
                chop2 = choppers.get(j);

                if (chop.radialCollide(chop2)) {

                    // Handle the collision
                    handleCollision(chop, chop2);
                }
            }
        }

        for (AniChopper c : choppers) {
            c.update(dt);
        }
    }

    private void handleCollision(AniChopper chopperOne, AniChopper chopperTwo) {


/*
        Vector2 v1 = chopperOne.getSpeed();
        Vector2 v2 = chopperTwo.getSpeed();

        float x21 = chopperTwo.getX() - chopperOne.getX();
        float y21 = chopperTwo.getY() - chopperOne.getY();

        float vx21 = v2.getX() - v1.getX();
        float vy21 = v2.getY() - v1.getY();

        float vx_cm = (v1.getX() + v2.getX()) / 2;
        float vy_cm = (v1.getY() + v2.getY()) / 2;

        if ( (vx21 * v2.getX() + vy21 + y21) >= 0)
            return;

        float a = y21 / x21;
        float dvx2 = -2 * (vx21 + a * vy21) / ((1 + a * a) * (1 + 1));
        float vx2 = v2.getX() + dvx2;
        float vy2 = v2.getY() + a * dvx2;
        float vx1 = v1.getX() - dvx2;
        float vy1 = v1.getY() - a * dvx2;

        */


        /*
        Vector2 n = chopperOne.getPosition().getSubtracted(chopperTwo.getPosition());
        Vector2 un = n.getMultiplied(1 / n.getLength());
        Vector2 ut = new Vector2(-un.getY(), un.getX());

        Log.d(TAG, "un " + un.getX() + ", " + un.getY());
        Log.d(TAG, "n " + n.getX() + ", " + n.getY());
        Log.d(TAG, "ut " + ut.getX() + ", " + ut.getY());

        Vector2 v1 = chopperOne.getSpeed();
        Vector2 v2 = chopperTwo.getSpeed();

        Log.d(TAG, "v1 " + v1.getX() + ", " + v1.getY());
        Log.d(TAG, "v2 " + v2.getX() + ", " + v2.getY());

        float v1n = un.dot(v1);
        float v1t = ut.dot(v1);
        float v2n = un.dot(v2);
        float v2t = ut.dot(v2);

        Log.d(TAG, "v1n " + v1n);
        Log.d(TAG, "v1t " + v1t);
        Log.d(TAG, "v2n " + v2n);
        Log.d(TAG, "v2t " + v2t);

        Vector2 newV1n = un.getMultiplied(v2n);
        Vector2 newV1t = un.getMultiplied(v1t);
        Vector2 newV2n = un.getMultiplied(v1n);
        Vector2 newV2t = un.getMultiplied(v2t);

        chopperOne.setSpeed(newV1n.getAdded(newV1t));
        chopperTwo.setSpeed(newV2n.getAdded(newV2t));





        */

        // Contact angle
        double phi = Math.atan((chopperOne.getX() - chopperTwo.getX()) /
                (chopperOne.getY() - chopperTwo.getY()));


        // Movement angles
        Vector2 v1 = chopperOne.getSpeed();
        Vector2 v2 = chopperTwo.getSpeed();

        double thetaOne = Math.atan(v1.getX()/v1.getY());
        double thetaTwo = Math.atan(v2.getX()/v2.getY());

        // Calculate new speed in x
        float v1x = (float)(v2.getLength() * Math.cos(thetaTwo - phi) * Math.cos(phi)
                + v1.getLength() * Math.sin(thetaOne - phi) * Math.cos(phi + (Math.PI/2)));
        float v2x = (float)(v1.getLength() * Math.cos(thetaOne - phi) * Math.cos(phi)
                + v2.getLength() * Math.sin(thetaTwo - phi) * Math.cos(phi + (Math.PI/2)));

        // Calculate new speed in y
        float v1y = (float)(v2.getLength() * Math.cos(thetaTwo - phi) * Math.sin(phi)
                + v1.getLength() * Math.sin(thetaOne - phi) * Math.sin(phi + (Math.PI / 2)));
        float v2y = (float)(v1.getLength() * Math.cos(thetaOne - phi) * Math.sin(phi)
                + v2.getLength() * Math.sin(thetaTwo - phi) * Math.sin(phi + (Math.PI / 2)));

        // Find out if the choppers image needs to be flipped
        if (v1.getX() * v1x < 0) {
            chopperOne.flip();
        }
        if (v2.getX() * v2x < 0) {
            chopperTwo.flip();
        }


        Vector2 newSpeedOne = new Vector2(v1x, v1y);
        Vector2 newSpeedTwo = new Vector2(v2x, v2y);

        Log.d(TAG, "Speed one: " + newSpeedOne.toString());
        Log.d(TAG, "Speed two: " + newSpeedTwo.toString());

        chopperOne.setSpeed(newSpeedOne);
        chopperTwo.setSpeed(newSpeedTwo);

    }
}
