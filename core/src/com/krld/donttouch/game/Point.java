package com.krld.donttouch.game;

import com.krld.donttouch.game.model.Coordinates;

public class Point implements Coordinates {
    private final float x;
    private final float y;

    public Point(float x, float y) {
        this.x = x;
        this.y =y;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}
