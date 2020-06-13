package com.psm.medreminder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.SigningInfo;
import android.os.Bundle;

import com.psm.medreminder.activity.IconTabsActivity;


public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    sleep(3000);
                    SharedPreferences shared = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

//                    Intent intent = new Intent(SplashScreen.this, SigninActivity.class);
//                    startActivity(intent);
//                    finish();

                    String val = shared.getString("email", "");
                    if (val.length() == 0) {

                        Intent intent = new Intent(SplashScreen.this, SigninActivity.class);
                        startActivity(intent);
                        finish();


                    } else {

                        Intent intent = new Intent(SplashScreen.this, Profile.class);
                        startActivity(intent);
                        finish();
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };

        thread.start();
    }
}
