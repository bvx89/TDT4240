package no.ntnu.assignment.one.model;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import java.util.Random;

import no.ntnu.assignment.one.Config;
import no.ntnu.assignment.one.graphics.AniImage;
import sheep.game.Sprite;
import sheep.graphics.Image;

/**
 * Created by bvx89 on 1/16/14.
 */
public class AniChopper extends Sprite {
    private static final String TAG = "TATAS";

    private float imgWidth;
    private float imgHeight;

    private AniImage img;

    public AniChopper(AniImage img) {
        super(img);
        this.img = img;

        imgWidth = img.getWidth();
        imgHeight = img.getHeight();
        setShape(imgWidth, imgHeight);


        // Set physics
        Random randGen = new Random();
        setAcceleration(0, 0);

        setSpeed(-150f, 100f);
        setPosition(300f, 150f);
        /*
        setSpeed( -(randGen.nextFloat() * 50.0f + 150f),
                    randGen.nextFloat() * 50.0f + 100f);

        setPosition(randGen.nextFloat() * Config.WINDOW_HEIGHT,
                randGen.nextFloat() * Config.WINDOW_WIDTH);
        */
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        // Calculate bounds
        float minx = getX();
        float maxx = minx + imgWidth;
        float miny = getY();
        float maxy = miny + imgHeight;

        // Check left and right edge
        if (minx < 0) { // left edge
            setXSpeed(getSpeed().getX() * -1);
            img.flip();
            setPosition(1, getY());
        } else if (maxx > Config.WINDOW_WIDTH) { // Right edge
            setXSpeed(getSpeed().getX() * -1);
            img.flip();
            setPosition(Config.WINDOW_WIDTH-imgWidth, getY());
        }

        // Check top and bottom
        if (miny < 0 || maxy > Config.WINDOW_HEIGHT) { // Top edge
            setYSpeed(getSpeed().getY() * -1);

        }

    }
}
