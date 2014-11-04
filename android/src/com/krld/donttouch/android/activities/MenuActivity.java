package com.krld.donttouch.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.krld.donttouch.android.game.AndroidLauncher;
import com.krld.donttouch.android.R;
import com.krld.donttouch.android.views.BackgroundView;


public class MenuActivity extends Activity {

    private BackgroundView mBackground;
    private Button mPlay;
    private Button mRatings;
    private Button mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_menu);
        mBackground = (BackgroundView) findViewById(R.id.generalMenu_background);
        mPlay = (Button) findViewById(R.id.generalMenu_play);
        mRatings = (Button) findViewById(R.id.generalMenu_ratings);
        mSettings = (Button) findViewById(R.id.generalMenu_settings);

        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGameView();
            }
        });
    }

    private void showGameView() {
        Intent intent = new Intent(this, AndroidLauncher.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBackground != null) {
            mBackground.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBackground != null) {
            mBackground.onPause();
        }
    }
}
