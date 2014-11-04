package com.krld.donttouch.android.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.krld.donttouch.ActiveView;
import com.krld.donttouch.android.R;

import java.util.ArrayList;
import java.util.List;

public class BackgroundView extends View implements View.OnTouchListener, ActiveView {
	private int mEndColor;
	private List<Level> mLevels;
    private Thread mDrawer;
    private long BACKGROUND_DRAW_DELAY = 100;

    public BackgroundView(Context context) {
		super(context);
		init();
	}

	public BackgroundView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BackgroundView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mEndColor = getResources().getColor(R.color.background_end);
		setOnTouchListener(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		if (mLevels == null) {
			initLevels();
		}
		drawEnd(canvas, paint);
		drawLevels(canvas, paint);
	}


	private void initLevels() {
		mLevels = new ArrayList<Level>();
		mLevels.add(new Level(getResources().getColor(R.color.level_1), 8, 50));
		mLevels.add(new Level(getResources().getColor(R.color.level_2), 5, 100));
		mLevels.add(new Level(getResources().getColor(R.color.level_3), 2, 200));
	}

	private void drawLevels(Canvas canvas, Paint paint) {
		for (Level level : mLevels) {
			level.draw(canvas, paint);
		}
	}

	private void drawEnd(Canvas canvas, Paint paint) {
		paint.setColor(mEndColor);
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
	}

	public void update() {
		if (mLevels != null)
			for (Level level : mLevels) {
				level.update();
			}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int disp = 100;
		int dispDiv2 = disp / 2;
/*		for (int i = 0; i < 3; i++) {
			animations.add(new CloudAnimation(event.getX() + (float) Math.random() * disp - dispDiv2,
					event.getY() + (float) Math.random() * disp - dispDiv2, null));
		}*/
		return true;
	}

    @Override
    public void onPause() {
        mDrawer.interrupt();
        try {
            mDrawer.join();
            Log.i("", "mDrawer has terminated");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        createDrawerThread();
    }

    private void createDrawerThread() {
        mDrawer = new Thread(new Runnable() {
            @Override
            public void run() {
                drawerLoop();
            }
        });
        mDrawer.start();
    }

    private void drawerLoop() {
        try {
            while (true) {
                update();
                postInvalidate();
                Thread.sleep(BACKGROUND_DRAW_DELAY);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private class Level {
		private final int mColor;
		private final int mCount;
		private final int mHeight;
		private List<Rectangle> mRectangles;

		public Level(int color, int count, int width) {
			mColor = color;
			mCount = count;
			mHeight = width;

			createRectangles();
		}

		private void createRectangles() {
			mRectangles = new ArrayList<Rectangle>();
			int space = (int) (getHeight() / ((mCount + 1) * 1f));
			for (int i = 0; i <= mCount; i++) {
				mRectangles.add(new Rectangle(i * space - mHeight));
			}
		}

		public void draw(Canvas canvas, Paint paint) {
			paint.setColor(Color.BLACK);
			paint.setAlpha(100);
			for (Rectangle rect : mRectangles) {
				canvas.drawRect(0, rect.mY + mHeight / 4, canvas.getWidth(), rect.mY + mHeight + mHeight / 4, paint);
			}
			paint.setAlpha(255);
			paint.setColor(mColor);
			for (Rectangle rect : mRectangles) {
				canvas.drawRect(0, rect.mY, canvas.getWidth(), rect.mY + mHeight, paint);
			}
		}

		public void update() {
			for (Rectangle rect : mRectangles) {
				rect.mY += getSpeed();
				if (rect.mY > getHeight()) {
					rect.mY = -mHeight;
				}
			}
		}

		private float getSpeed() {
			return mHeight / 100f;
		}
	}

	private class Rectangle {
		public float mY;

		public Rectangle(int y) {
			mY = y;
		}
	}


}
