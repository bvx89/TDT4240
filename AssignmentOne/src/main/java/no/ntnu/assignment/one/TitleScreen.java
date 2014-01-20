package no.ntnu.assignment.one;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import java.util.HashMap;

import no.ntnu.assignment1.task1.R;
import sheep.game.State;
import sheep.graphics.Font;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.gui.WidgetAction;
import sheep.gui.WidgetListener;

/**
 * Created by bvx89 on 1/16/14.
 */

public class TitleScreen extends State implements WidgetListener {
    private Image chopper = new Image(R.drawable.chopper_static);

    private HashMap<String, TextButton> mButtons;

    public TitleScreen() {

        Paint[] ButtonColors = {
                new Font(255, 255, 255, 50.0f,
                        Typeface.SANS_SERIF, Typeface.BOLD),
                new Font(57, 152, 249, 50.0f,
                        Typeface.SANS_SERIF, Typeface.BOLD)
        };

        String[] tasks = {"Task 1", "Task 2", "Task 3", "Task 4"};

        mButtons = new HashMap<>(tasks.length);

        // Assign tasks to buttons and attach listeners
        TextButton tb;
        for (int i = 0; i < tasks.length; i++) {
            tb = new TextButton(
                    Config.WINDOW_WIDTH*0.425f,
                    Config.WINDOW_HEIGHT*(0.4f + 0.1f*i),
                    tasks[i], ButtonColors);

            tb.addWidgetListener(this);
            addTouchListener(tb);
            mButtons.put(tasks[i], tb);
        }

    }


    @Override
    public void draw(Canvas canvas) {
        if (canvas != null) {
            // Clear bg
            canvas.drawColor(Color.DKGRAY);

            chopper.draw(canvas,
                    Config.WINDOW_WIDTH*0.35f,
                    Config.WINDOW_HEIGHT*0.15f);

            for (TextButton tb : mButtons.values()) {
                tb.draw(canvas);
            }
        }
    }
    @Override
    public void actionPerformed(WidgetAction action) {
        if (mButtons.containsValue(action.getSource())) {
            getGame().popState();
            TextButton tbPushed = (TextButton)action.getSource();

            String task = tbPushed.getLabel();
            if (task.equals("Task 1")) {
                getGame().pushState(new TaskOne());
            } else if (task.equals("Task 2")) {
                getGame().pushState(new TaskTwo());
            } else if (task.equals("Task 3")) {
                getGame().pushState(new TaskThree());
            }
        }
    }

}