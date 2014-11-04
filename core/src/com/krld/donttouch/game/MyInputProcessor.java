package com.krld.donttouch.game;

import com.badlogic.gdx.InputProcessor;

public class MyInputProcessor implements InputProcessor {
    private final GameDontTouch game;

    public MyInputProcessor(GameDontTouch game) {
        this.game = game;

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        game.actionOn(screenX, screenY, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        game.actionOn(screenX, screenY, pointer, 0);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        game.actionOn(screenX, screenY, 0, 0);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
