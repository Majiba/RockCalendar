package com.example.manue.rockcalendar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

public class AcercaDeActivity extends AppCompatActivity {

    String TAG = "AcercaDeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_acerca_de);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.acerca_de));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        } else {
            MyLog.d("SobreNosotros", "Error al cargar toolbar");
        }
    }

    protected void onStart(Bundle savedInstanceState) {
        super.onStart();
        MyLog.d(TAG, "Ejecutando OnStart");
    }

    protected void onResume(Bundle savedInstanceState) {
        super.onResume();
        MyLog.d(TAG, "Ejecutando OnResume");
    }

    protected void onRestart(Bundle savedInstanceState) {
        super.onRestart();
        MyLog.d(TAG, "Ejecutando OnRestart");
    }

    protected void onPause(Bundle savedInstanceState) {
        super.onStop();
        MyLog.d(TAG, "Ejecutando OnPause");
    }

    protected void onStop(Bundle savedInstanceState) {
        super.onStop();
        MyLog.d(TAG, "Ejecutando OnStop");
    }

    protected void onDestroy(Bundle savedInstanceState) {
        super.onDestroy();
        MyLog.d(TAG, "Ejecutando OnDestroy");
    }
}