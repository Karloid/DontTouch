package com.krld.donttouch.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.krld.donttouch.GameContainer;
import com.krld.donttouch.game.model.Player;

public class GameDontTouch {
    private final GameContainer mGameContainer;
    private GameRenderer mRenderer;
    private Player player;
    private int width;
    private int height;

    public GameDontTouch(GameContainer gameContainer) {
        mGameContainer = gameContainer;
    }

    public void init() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        player = new Player(width / 2, (height / 10), width / 40, Colors.PLAYER_COLOR);

        Gdx.input.setInputProcessor(new MyInputProcessor(this));
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
        player.setX(screenX);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
