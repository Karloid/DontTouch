package com.krld.donttouch.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.krld.donttouch.ActiveView;
import com.krld.donttouch.GameContainer;
import com.krld.donttouch.game.model.Player;
import com.sun.javafx.accessible.utils.Rect;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameDontTouch implements ActiveView {
    private static final long DELAY = 1000 / 60;
    public static final int Y_WAY_OFFSET = 0;
    private final GameContainer mGameContainer;
    private GameRenderer mRenderer;
    private Player player;
    private int width;
    private int height;
    private float newX;
    private Thread runner;
    private List<Rectangle> way;

    private long tick;
    private float speed;
    private List<Rectangle> copyWay;
    private boolean needCopyWay;

    private int currentScore;
    private int bestScore;
    private float MAGIC_SPEED_DIVIDER = 1280;

    public GameDontTouch(GameContainer gameContainer) {
        mGameContainer = gameContainer;
    }

    public void init() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        Gdx.input.setInputProcessor(new MyInputProcessor(this));

        way = new ArrayList<Rectangle>();
        startNewGame();

    }

    private void startNewGame() {
        needCopyWay = true;
        player = new Player(width / 2, height / 10, width / 40, Colors.PLAYER_COLOR);
        newX = player.getX();
        speed = (height / MAGIC_SPEED_DIVIDER) * 3;
        tick = 0;
        way.clear();
        way.add(new Rectangle(width / 2 - standartWayWidth() / 2, 0, standartWayWidth(), standartWayHeight()));
        way.add(new Rectangle(width / 2 - standartWayWidth() / 2, standartWayHeight() - Y_WAY_OFFSET, standartWayWidth(), standartWayHeight()));
        way.add(new Rectangle(width / 2 - standartWayWidth() / 2, standartWayHeight() * 2 - Y_WAY_OFFSET, standartWayWidth(), standartWayHeight()));
        way.add(new Rectangle(width / 2, getProcHeight(0.7f), standartWayWidth() - Y_WAY_OFFSET, standartWayHeight()));
        way.add(new Rectangle(width / 2, getProcHeight(0.9f) - standartWayWidth() / 2 - Y_WAY_OFFSET, standartWayWidth(), standartWayHeight()));
    }

    private float standartWayHeight() {
        return getProcHeight(0.3f);
    }

    private float standartWayWidth() {
        return getProcWidth(0.2f);
    }

    private float getProcHeight(float value) {
        return height * value;
    }

    private float getProcWidth(float value) {
        return width * value;
    }

    public GameRenderer getRenderer() {
        if (mRenderer == null) {
            mRenderer = new GameRenderer(this);
        }
        return mRenderer;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void actionOn(int screenX, int screenY, int pointer, int button) {
        newX = screenX;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void onPause() {
        if (runner == null) return;
        runner.interrupt();
        try {
            runner.join();
            runner = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        if (runner != null) {
            onPause();
        }
        runner = new Thread(new Runnable() {
            @Override
            public void run() {
                loop();
            }
        });
        runner.start();
    }

    private void loop() {
        try {
            while (true) {
                update();
                Thread.sleep(DELAY);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void update() {
        tick++;
        if (player == null) return;
        updateObstaclesAndWay();
        removeOldsWay();
        createNewWays();
        copyWays();
        player.setX(newX);
        updateScore();
        checkPlayerPosition();
    }

    private void updateScore() {
        currentScore = (int)(tick / 10);
        if (bestScore < currentScore) {
            bestScore = currentScore;
        }
    }

    private void copyWays() {
        if (!needCopyWay) return;
        copyWay = new ArrayList<Rectangle>(way);
        needCopyWay = false;
    }

    private void checkPlayerPosition() {
        Point pointLeft = new Point(player.getX() - player.getRadius(), player.getY());
        Point pointRight = new Point(player.getX() + player.getRadius(), player.getY());
        boolean pointLeftContains = false;
        for (Rectangle rect : way) {
            if (rect.contains(pointLeft.getX(), pointLeft.getY())) {
                pointLeftContains = true;
                break;
            }
        }
        boolean pointRightContains = false;
        for (Rectangle rect : way) {
            if (rect.contains(pointRight.getX(), pointRight.getY())) {
                pointRightContains = true;
                break;
            }
        }
        if (pointLeftContains && pointRightContains) {
            return;
        }
        startNewGame();
    }

    private void createNewWays() {
        Rectangle lastRect = null;
        for (Rectangle rect : way) {
            if (lastRect == null || lastRect.y + lastRect.height < rect.y + rect.height) {
                lastRect = rect;
            }
            if (rect.y + rect.height > height + 100) {
                return;
            }
        }
        float x;
        if (Math.random() > 0.7f) {
            do {
                x = lastRect.x + (standartWayWidth() / 4) * (int) (Math.random() * 5) - (standartWayWidth() / 2);
            } while (x + standartWayWidth() > width || x < 0);
        } else {
            do {
                x = lastRect.x + (standartWayWidth()) * (int) (Math.random() * 3) - standartWayWidth();
            } while (x + standartWayWidth() > width || x < 0);
        }
        Rectangle newRectangle = new Rectangle(x, lastRect.y + lastRect.height - standartWayHeight() / 5, standartWayWidth(), standartWayHeight());
        way.add(newRectangle);
        needCopyWay = true;

    }

    private void removeOldsWay() {
        ArrayList<Rectangle> waysToRemove = null;
        for (Rectangle rect : way) {
            if (rect.y + rect.height < 0) {
                if (waysToRemove == null)
                    waysToRemove = new ArrayList<Rectangle>();
                waysToRemove.add(rect);
            }
        }
        if (waysToRemove != null) {
            way.removeAll(waysToRemove);
            needCopyWay = true;
        }
    }

    private void updateObstaclesAndWay() {
        for (Rectangle rect : way) {
            moveRect(rect);
        }
    }

    private void moveRect(Rectangle rect) {
        rect.y -= speed;
    }

    public List<Rectangle> getWay() {
        return way;
    }

    public Iterable<? extends Rectangle> getCopyWay() {
        return copyWay;
    }

    public void setCopyWay(List<Rectangle> copyWay) {
        this.copyWay = copyWay;
    }

    public long getTick() {
        return tick;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getBestScore() {
        return bestScore;
    }
}
