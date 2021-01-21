package com.example.aklatiapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Background extends AppCompatActivity {
static  int SPLASH_TIME_OUT=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);
//        Backendless.initApp(this, "52C3B5A5-262E-1101-FF57-D6683AFC2500", "D35DFD32-9D27-4B7C-A6AB-68FCE118C85A");
//
//        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//        String value=(mSharedPreference.getString("mail", "Default_Value"));
//        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
//
//
//        Thread welcomeThread = new Thread() {
//
//            @Override
//
//            public void run() {
//                try {
//                    super.run();
//
//
//
//
//
//
//                    sleep(4000);  //Delay of 10 seconds
//                } catch (Exception e) {
//
//                } finally {
//
//                    Intent i = new Intent(Background.this,
//                            LoginActivity.class);
//                    startActivity(i);
//                    finish();
//                }
//            }
//        };
//        welcomeThread.start();
    }

}