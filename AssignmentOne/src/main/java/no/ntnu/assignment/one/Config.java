package no.ntnu.assignment.one;

import android.graphics.Paint;
import android.graphics.Typeface;

import sheep.graphics.Font;

/**
 * Created by bvx89 on 1/16/14.
 */
public class Config {
    public static int WINDOW_WIDTH  = 0;
    public static int WINDOW_HEIGHT = 0;

    public static Paint[] ButtonColors = {
            new Font(255, 255, 255, 50.0f,
                    Typeface.SANS_SERIF, Typeface.BOLD),
            new Font(57, 152, 249, 50.0f,
                    Typeface.SANS_SERIF, Typeface.BOLD)
    };
}
