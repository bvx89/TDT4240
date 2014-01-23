package no.ntnu.assignment.one;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Random;

import no.ntnu.assignment1.task1.R;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.graphics.Font;
import sheep.graphics.Image;
import sheep.gui.TextButton;
import sheep.input.TouchListener;
import sheep.math.Vector2;

/**
 * Created by Ole on 20.01.14.
 */
public class TaskFour extends State implements TouchListener{
	public static final String TAG = "Test";

	private Vector2 fingerPos;
	private Sprite topWall;
	private Sprite botWall;
	private Sprite midLine;
	private Sprite leftPaddle;
	private Sprite rightPaddle;
	private Sprite ball;

	private TextButton leftScore;
	private TextButton rightScore;

	//private int leftScore;
	//private int rightScore;
	private final float speedIncrease = 0.1f;
	private final float botSpeed = 500;

	
	public TaskFour() {
		this.addTouchListener(new TouchListener() {

			@Override
			public boolean onTouchUp(MotionEvent event) {
				fingerPos=null;
				return false;
			}

			@Override
			public boolean onTouchMove(MotionEvent event) {
				fingerPos = new Vector2(event.getX(),
										event.getY());
				return false;
			}

			@Override
			public boolean onTouchDown(MotionEvent event) {
				fingerPos = new Vector2(event.getX(),
										event.getY());
				Log.d(TAG, "Y: " + event.getY());
				return false;
			}
		});

		float lineWidth = Config.WINDOW_HEIGHT * 0.01f;

		midLine = new Sprite(new Image(R.drawable.white_pixel));
		midLine.setScale(Config.WINDOW_WIDTH , lineWidth);
		midLine.setPosition(0 , Config.WINDOW_HEIGHT / 2 - midLine.getScale().getY());

		topWall = new Sprite(new Image(R.drawable.white_pixel));
		botWall = new Sprite(new Image(R.drawable.white_pixel));
		topWall.setScale(lineWidth, Config.WINDOW_WIDTH);
		botWall.setScale(lineWidth, Config.WINDOW_WIDTH);
		topWall.setPosition(Config.WINDOW_WIDTH - topWall.getScale().getX() * 2 + 1, 0);
		botWall.setPosition(0, 0);

		Random r = new Random();
		ball = new Sprite(new Image(R.drawable.white_pixel));
		ball.setScale(lineWidth, lineWidth);
		resetBall();

		leftPaddle = new Sprite(new Image(R.drawable.white_pixel));
		rightPaddle = new Sprite(new Image(R.drawable.white_pixel));
		leftPaddle.setScale(Config.WINDOW_WIDTH * 0.1f, lineWidth);
		rightPaddle.setScale(Config.WINDOW_WIDTH * 0.1f, lineWidth);
		leftPaddle.setPosition(Config.WINDOW_WIDTH / 2 - leftPaddle.getScale().getX(), 0);
		rightPaddle.setPosition(Config.WINDOW_WIDTH / 2 - rightPaddle.getScale().getX(), Config.WINDOW_HEIGHT - rightPaddle.getScale().getY() * 2);

		Paint[] ButtonColors = {
				new Font(255, 255, 255, 50.0f,
						Typeface.SANS_SERIF, Typeface.BOLD),
				new Font(57, 152, 249, 50.0f,
						Typeface.SANS_SERIF, Typeface.BOLD)
		};

		rightScore = new TextButton(2 * lineWidth, midLine.getY() - lineWidth,"0", ButtonColors);
		leftScore = new TextButton(2 * lineWidth, midLine.getY() + 6 * lineWidth,"0", ButtonColors);
	}

	private void resetBall(){
		Random r = new Random();
		ball.setPosition(Config.WINDOW_WIDTH / 2, Config.WINDOW_HEIGHT / 2);
		int speed=350;
		//Shoots ball in one of four random directions
		ball.setSpeed(speed - 2 * speed * r.nextInt(2), speed - 2 * speed * r.nextInt(2));
	}

	private boolean clean() {
		getGame().popState();
		getGame().pushState(new TitleScreen());

		topWall.die();
		botWall.die();
		midLine.die();
		leftPaddle.die();
		rightPaddle.die();
		ball.die();


		return true;
	}

	@Override
	public void update(float dt){
		rightPaddle.update(dt);
		if(fingerPos != null /*&& fingerPos.getX() != leftPaddle.getX() - leftPaddle.getScale().getX()*/){
			rightPaddle.setPosition(fingerPos.getX() - rightPaddle.getScale().getX(), Config.WINDOW_HEIGHT - rightPaddle.getScale().getY() * 2);
			rightPaddle.update(0);
			if (rightPaddle.collides(botWall)){
				rightPaddle.setPosition(botWall.getScale().getX() * 2, Config.WINDOW_HEIGHT - rightPaddle.getScale().getY() * 2);
				rightPaddle.update(0);
			} else if (rightPaddle.collides(topWall)){
				rightPaddle.setPosition(Config.WINDOW_WIDTH - topWall.getScale().getX() * 2 - rightPaddle.getScale().getX() * 2, Config.WINDOW_HEIGHT - rightPaddle.getScale().getY() * 2);
				rightPaddle.update(0);
			}
		}

		if(ball.getX() + ball.getScale().getX() / 2 > leftPaddle.getX() + leftPaddle.getScale().getX() / 2 ){
			leftPaddle.setSpeed(botSpeed, 0);
		} else {
			leftPaddle.setSpeed(-botSpeed, 0);
		}

		midLine.update(dt);
		topWall.update(dt);
		botWall.update(dt);
		ball.update(dt);
		leftPaddle.update(dt);




		if (ball.collides(topWall) || ball.collides(botWall)){
			ball.setSpeed(-ball.getSpeed().getX(), ball.getSpeed().getY());
		}
		if (ball.collides(leftPaddle) && ball.getSpeed().getY() < 0 ||
			ball.collides(rightPaddle) && ball.getSpeed().getY() > 0){
			ball.setSpeed(	ball.getSpeed().getX() * (1 + speedIncrease),
							-ball.getSpeed().getY() * (1 + speedIncrease));
		} else if (ball.getPosition().getY() > Config.WINDOW_HEIGHT - ball.getScale().getY()*2){
			rightScore.setLabel("" + (Integer.parseInt(rightScore.getLabel()) + 1));
			resetBall();
		} else if (ball.getPosition().getY() < 0){
			leftScore.setLabel("" + (Integer.parseInt(leftScore.getLabel()) + 1));
			resetBall();
		}

	}



	@Override
	public void draw(Canvas canvas){
		super.draw(canvas);
		if (canvas != null) {
			canvas.drawColor(Color.BLACK);
			midLine.draw(canvas);
			topWall.draw(canvas);
			botWall.draw(canvas);
			ball.draw(canvas);
			leftPaddle.draw(canvas);
			rightPaddle.draw(canvas);
			rightScore.draw(canvas);
			leftScore.draw(canvas);
		}


	}

}
