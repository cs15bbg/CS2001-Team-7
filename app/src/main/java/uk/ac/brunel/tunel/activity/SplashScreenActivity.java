/*
 * Created by Mohamed Bushra on 20/02/17 14:40
 * Copyright (c) 2017. All rights reserved.
 *
 * Last Modified 19/02/17 12:07.
 */

package uk.ac.brunel.tunel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import uk.ac.brunel.tunel.R;

/**
 * Created by Mohamed Bushra on 05/12/2016.
 */

public class SplashScreenActivity extends AppCompatActivity {


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread myThread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    // The splash screen will show the Logo for 2 seconds
                    sleep(2000);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

            }

        };

        myThread.start();

    }

}
