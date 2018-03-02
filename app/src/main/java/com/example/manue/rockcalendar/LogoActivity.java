package com.example.manue.rockcalendar;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LogoActivity extends AppCompatActivity {

    String TAG = "LogoActivity";
    protected int _splashTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        int secondsDelayed = 2;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(LogoActivity.this,
                        ListadoAcontecimientosActivity.class));
                finish();
            }
        }, secondsDelayed * 1000);
        //MyLog.d(TAG, "Empezando boton flotante...");
    }
}
