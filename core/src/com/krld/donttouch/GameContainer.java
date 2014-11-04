package com.krld.donttouch;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.krld.donttouch.game.GameDontTouch;
import com.krld.donttouch.game.GameRenderer;

public class GameContainer extends ApplicationAdapter implements ActiveView{
    private final HostType mType;
    private final GameDontTouch mGame;
    private final GameRenderer mRenderer;
    SpriteBatch mBatch;
    private ShapeRenderer mShapeRenderer;

    public GameContainer(HostType type) {
        mType = type;
        mGame = new GameDontTouch(this);
        mRenderer = mGame.getRenderer();
    }

    @Override
	public void create () {
		mBatch = new SpriteBatch();
		mShapeRenderer = new ShapeRenderer();

        mRenderer.setBatch(mBatch);
        mRenderer.setShapeRenderer(mShapeRenderer);

        mGame.init();
        mRenderer.init();
	}

	@Override
	public void render () {
        mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        mRenderer.draw();
        mShapeRenderer.end();
	}

    @Override
    public void onPause() {
        mGame.onPause();
    }

    @Override
    public void onResume() {
        mGame.onResume();
    }
}
