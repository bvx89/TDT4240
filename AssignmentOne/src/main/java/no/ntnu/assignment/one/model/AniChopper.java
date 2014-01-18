package no.ntnu.assignment.one.model;

import android.graphics.Canvas;

import no.ntnu.assignment.one.Config;
import no.ntnu.assignment.one.graphics.AniImage;
import sheep.game.Sprite;

/**
 * Created by bvx89 on 1/16/14.
 */
public class AniChopper extends Sprite {
    private float imgWidth;
    private float imgHeight;

    public AniChopper(AniImage img) {
        super(img);
        setAcceleration(0, 0);
        setSpeed(-150f, 100f);
        setPosition(300f, 150f);

        imgWidth = img.getWidth();
        imgHeight = img.getHeight();
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        /*

        // Calculate bounds
        float minx = getPosition().getX() - imgWidth/2;
        float maxx = minx + imgWidth;
        float miny = getPosition().getY() - imgHeight/2;
        float maxy = miny + imgHeight;

        // Check left and right edge
        if (minx < 0) { // Left edge
            setXSpeed(getSpeed().getX() * -1);
            setScale(-1, 1);
            setPosition(getPosition().getX() + imgWidth, getPosition().getY());

        } else if (maxx > Config.WINDOW_WIDTH + imgWidth) { // Right edge
            setXSpeed(getSpeed().getX() * -1);
            setScale(1, 1);
            setPosition(getPosition().getX() - imgWidth, getPosition().getY());

        }

        // Check top and bottom
        if (miny < 0) { // Top edge
            setYSpeed(getSpeed().getY() * -1);
        } else if (maxy > Config.WINDOW_HEIGHT) { // Bottom edge
            setYSpeed(getSpeed().getY() * -1);
        }
        */

    }
}
