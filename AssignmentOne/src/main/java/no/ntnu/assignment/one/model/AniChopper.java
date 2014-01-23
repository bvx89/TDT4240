package no.ntnu.assignment.one.model;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import java.util.Random;

import no.ntnu.assignment.one.Config;
import no.ntnu.assignment.one.graphics.AniImage;
import sheep.game.Sprite;
import sheep.graphics.Image;
import sheep.math.Vector2;

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
        // Calculate bounds
        float minx = getX();
        float maxx = minx + imgWidth;
        float miny = getY();
        float maxy = miny + imgHeight;

        float speedX = getSpeed().getX();
        float speedY = getSpeed().getY();

        // Check left and right edge
        if ((minx < 0 && speedX < 0) || // Left edge
            (maxx >= Config.WINDOW_WIDTH && speedX > 0)) { // Right edge
            setXSpeed(speedX * -1);
            img.flip();
        }

        // Check top and bottom
        if ((miny < 0 && speedY < 0) ||
            (maxy > Config.WINDOW_HEIGHT && speedY > 0)) {
            setYSpeed(speedY * -1);
        }
        super.update(dt);
    }

    public void flip() {
        img.flip();
    }
    
    public boolean radialCollide(AniChopper other){
        float r1 = (imgHeight + imgWidth) / 4;
        float r2 = (other.imgHeight + other.imgWidth) / 4;
        
        Vector2 dist = getPosition().getSubtracted(other.getPosition());
        Vector2 speed = getSpeed().getSubtracted(other.getSpeed());

        if ((speed.getX()*dist.getX() + speed.getY()*dist.getY()) >= 0)  {
            return false;
        } else if (r1 + r2 > dist.getLength()){
            return true;
        } else {
            return false;
        }
    }
}

