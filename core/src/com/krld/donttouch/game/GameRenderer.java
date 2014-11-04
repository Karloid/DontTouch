package com.krld.donttouch.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.krld.donttouch.game.model.Player;

public class GameRenderer {
    private final GameDontTouch game;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    public GameRenderer(GameDontTouch gameDontTouch) {
        game = gameDontTouch;
    }

    public void draw() {
        drawBackground();
        drawObstacles();
        drawPlayer();
    }

    private void drawPlayer() {
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Player player = game.getPlayer();
        float x = player.getX();
        float y = player.getY();
        float radius = player.getRadius();
        shapeRenderer.setColor(player.getColor());
        shapeRenderer.circle(x, y, radius);
        shapeRenderer.end();
        batch.begin();
    }

    private void drawObstacles() {

    }

    private void drawBackground() {
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Colors.BACKGROUND);
        shapeRenderer.rect(0, 0, game.getWidth(), game.getHeight());
        shapeRenderer.end();
        batch.begin();
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }
}
