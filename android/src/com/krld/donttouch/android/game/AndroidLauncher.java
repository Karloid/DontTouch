package com.krld.donttouch.android.game;

import android.os.Bundle;

import android.os.PersistableBundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.krld.donttouch.GameContainer;
import com.krld.donttouch.HostType;

public class AndroidLauncher extends AndroidApplication {
    private GameContainer game;

    @Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        game = new GameContainer(HostType.ANDROID);
        initialize(game, config);
	}

    @Override
    protected void onResume() {
        super.onResume();
        game.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        game.onPause();
    }
}
