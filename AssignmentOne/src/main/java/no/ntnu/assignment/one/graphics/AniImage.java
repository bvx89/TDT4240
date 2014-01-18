package no.ntnu.assignment.one.graphics;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.util.Log;

import sheep.game.Game;
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


    public AniImage(int resourceId, int rate, int dx) {
        this(Game.getInstance().getResources().getDrawable(resourceId), rate, dx);
    }

    public AniImage(Drawable drawable, int rate, int dx) {
        this.rate = 1000/rate;
        this.dx = dx * 2;

        this.max = drawable.getMinimumWidth() / dx;
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        this.mDrawable = drawable;
    }

    @Override
    public void update(float dt) {
        accumulatedTime += dt * 1000;
    }

    @Override
    public void draw(Canvas canvas, Matrix transformation) {
        canvas.save();

        canvas.clipRect(dx * index, 0, dx * (index+1),
                mDrawable.getIntrinsicHeight(), Region.Op.REPLACE);

        transformation.preTranslate(-(dx * index), 0);

        // Find out if the bounds need to be updated
        if (accumulatedTime > rate) {

            index = (index+1) % max;

            // Reset counter
            accumulatedTime = 0;
        }
        canvas.concat(transformation);
        mDrawable.draw(canvas);

        canvas.restore();
    }

    public int getWidth() {
        return dx;
    }

    public int getHeight() {
        return mDrawable.getMinimumHeight();
    }
}
