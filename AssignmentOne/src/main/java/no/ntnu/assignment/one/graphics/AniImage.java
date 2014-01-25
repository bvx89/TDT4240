package no.ntnu.assignment.one.graphics;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.util.Log;

import sheep.game.Game;
import android.graphics.Color;
import sheep.graphics.SpriteView;

/**
 * Created by bvx89 on 1/16/14.
 */
public class AniImage extends SpriteView {
    private static final String TAG = "TATAS";

    private Drawable mDrawable;

    // Distance between each image in the drawable
    private int dx = 0;
    private int max;

    // Current image index
    private int index = 0;

    // Time keeping
    private float accumulatedTime = 0;

    // Milliseconds between each image change
    private float rate = 0;

    private boolean isMirrored = false;


    /**
     *
     * @param resourceId Id of the drawable to retrieve
     * @param rate Rate of which to switch frames in the sprite
     * @param n Number of images in the sprite
     */
    public AniImage(int resourceId, int rate, int n) {
        this(Game.getInstance().getResources().getDrawable(resourceId), rate, n);
    }

    /**
     *
     * @param drawable Sprite with all the frames
     * @param rate Rate of which to switch frames in the sprite
     * @param n Number of images in the sprite
     */
    public AniImage(Drawable drawable, int rate, int n) {
        this.rate = 1000/rate;
        this.dx = drawable.getMinimumWidth() / n;

        this.max = 4;
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        this.mDrawable = drawable;
    }

    @Override
    public void update(float dt) {
        accumulatedTime += dt * 1000;
        if (accumulatedTime >= rate) {

            index = (index+1) % max;

            // Reset counter
            accumulatedTime = accumulatedTime - rate;
        }
    }

    private int pre = -1;

    @Override
    public void draw(Canvas canvas, Matrix transformation) {
        canvas.save();

        // Invert the image
        if (isMirrored) {

			/* Translate picture to position it correctly in the frame.
			 * Need to add +1 on index since the rotated picture are now
			 * one image-width outside of the rectangle to be drawn.
			 */
            transformation.preTranslate(dx*(index+1), 0);

			// flip the image along the y-axis            
            transformation.preScale(-1, 1);

        } else {
            // Translate picture to position it correctly in the frame
            transformation.preTranslate(-dx*index, 0);
        }

		// Add the transformation to the canvas
		canvas.concat(transformation);

		// Cut out only the correct picture
		canvas.clipRect(dx * index, 0, dx * (index+1),
			mDrawable.getIntrinsicHeight());

        // Draw on canvas
        mDrawable.draw(canvas);

        canvas.restore();
    }


    public int getWidth() {
        return dx;
    }

    public int getHeight() {
        return mDrawable.getMinimumHeight();
    }

    public void flip() {
        isMirrored = !isMirrored;
        index = 0;
    }
}
