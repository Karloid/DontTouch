package com.krld.donttouch.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.krld.donttouch.game.model.Player;

public class GameRenderer {
    public static final float BLINKING_TIME = 20;
    private final GameDontTouch game;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    public GameRenderer(GameDontTouch gameDontTouch) {
        game = gameDontTouch;
    }

    public void draw() {
        drawBackground();
        drawObstacles();
        drawWay();
        drawPlayer();
        drawScore();
    }

    private void drawScore() {
        shapeRenderer.end();
        batch.begin();
        font.draw(batch, "Score: " + game.getCurrentScore(), game.getWidth() / 10, game.getHeight() / 10 * 9);
        font.draw(batch, "Best: " + game.getBestScore(), game.getWidth() / 10, game.getHeight() / 10 * 8.5f);
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    }

    private void drawWay() {
        shapeRenderer.setColor(Colors.WAY);
        if (game.getCopyWay() != null)
            for (Rectangle rect : game.getCopyWay()) {
                shapeRenderer.rect(rect.x, rect.y, rect.getWidth(), rect.getHeight());
            }
    }

    private void drawPlayer() {
        Player player = game.getPlayer();
        float x = player.getX();
        float y = player.getY();
        float radius = player.getRadius();
        shapeRenderer.setColor(player.getColor());
        shapeRenderer.circle(x, y, radius);
    }

    private void drawObstacles() {

    }

    private void drawBackground() {
        if (game.getTick() < BLINKING_TIME) {
            shapeRenderer.setColor(new Color(Colors.BACKGROUND).mul(1f + 1f - game.getTick() / BLINKING_TIME, 1f, 1f, 1f));
        } else {
            shapeRenderer.setColor(Colors.BACKGROUND);
        }
        shapeRenderer.rect(0, 0, game.getWidth(), game.getHeight());
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public void init() {
        font = new BitmapFont(Gdx.files.internal("rotorBoyShadow.fnt"),
                Gdx.files.internal("rotorBoyShadow.png"), false);
        font.setColor(Color.WHITE);
        font.scale(1f);
    }
}
