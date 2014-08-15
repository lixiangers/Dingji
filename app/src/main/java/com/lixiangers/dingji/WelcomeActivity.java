package com.lixiangers.dingji;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class WelcomeActivity extends Activity {
    public static final int DELAY_MILLIS = 1500;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        }, DELAY_MILLIS);
    }
}
