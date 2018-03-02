package com.example.manue.rockcalendar;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BuscarAcontecimientosActivity extends AppCompatActivity {

    Button btnBuscar;
    EditText editBuscar;
    InputMethodManager imm;
    private Context myContext;

    protected boolean comprobarLongitud(String cadena){
        if(cadena.length()<3){
            return false;
        }else{
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_acontecimientos);
        btnBuscar = (Button) findViewById(R.id.buttonBuscarAcontecimiento);
        editBuscar = (EditText) findViewById(R.id.editTextBuscarAcontecimiento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_buscar_acontecimientos);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_buscar_acontecimientos));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        } else {
            MyLog.d("SobreNosotros", "Error al cargar toolbar");
        }



        myContext = this;

        btnBuscar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (comprobarLongitud(editBuscar.getText().toString())){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editBuscar.getWindowToken(), 0);
                    int InternetPermission = ContextCompat.checkSelfPermission(myContext, Manifest.permission.INTERNET);
                    MyLog.d("MainActivity", "INTERNET Permission: " + InternetPermission);

                    //Buscamos la progressbar
                    ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar2);
                    TextView tv = (TextView) findViewById(R.id.textView_errorAcont);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerAcontecimientos);

                    if (isNetDisponible()) {
                        // Permiso aceptado
                        Snackbar.make(view, getResources().getString(R.string.internet_permission_granted), Snackbar.LENGTH_LONG)
                                .show();
                        new RestAcontecimientos(editBuscar.getText().toString(), myContext, pb, tv, recyclerView).execute();
                    } else {
                        // Permiso denegado
                        Snackbar.make(view, getResources().getString(R.string.internet_permission_denied), Snackbar.LENGTH_LONG)
                                .show();
                    }
                }else{
                    Snackbar.make(view, "Necesitas al menos 3 caracteres", Snackbar.LENGTH_LONG).show();
                    imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editBuscar.getWindowToken(), 0);
                }
            }
        });
    }


    private boolean isNetDisponible() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }
}
