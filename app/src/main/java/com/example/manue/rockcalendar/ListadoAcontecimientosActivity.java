package com.example.manue.rockcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ListadoAcontecimientosActivity extends AppCompatActivity {

    String TAG = "ListadoAcontecimientosActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_acontecimientos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListadoAcontecimientosActivity.this,
                        BuscarAcontecimientosActivity.class));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.acerca_de:
                /*Intent AcercaDeActivity = new Intent(getApplicationContext(), AcercaDeActivity.class);
                startActivity(AcercaDeActivity);*/
                startActivity(new Intent(ListadoAcontecimientosActivity.this,
                        AcercaDeActivity.class));
                return true;
            case R.id.action_settings:
                MyLog.i("ActionBar", "Settings!");;
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
