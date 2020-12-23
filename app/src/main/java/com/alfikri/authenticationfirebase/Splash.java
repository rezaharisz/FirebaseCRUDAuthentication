package com.alfikri.authenticationfirebase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class Splash extends Activity {


    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private ProgressBar spinner;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this,MainActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
