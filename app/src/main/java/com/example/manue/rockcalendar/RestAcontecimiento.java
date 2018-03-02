package com.example.manue.rockcalendar;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by manue on 20/02/2018.
 */

public class RestAcontecimiento extends AsyncTask<String, String, String> {
    private Context context;
    private String id;
    private boolean flag = true;
    private HttpURLConnection connectionUrl;
    private String mensaje;
    private ProgressBar pb;
    private View view;

    public RestAcontecimiento(Context context, String id, ProgressBar pb) {
        super();
        this.context = context;
        this.id = id;
        this.pb = pb;
    }

    @Override
    protected void onPreExecute(){
        pb.setVisibility(View.VISIBLE);
    }

    protected String doInBackground(String... args) {
        StringBuilder total = new StringBuilder();

        try {
            URL url = new URL(Constantes.REST_URL + "acontecimiento/" + this.id);
            connectionUrl = (HttpURLConnection) url.openConnection();

            InputStream input = new BufferedInputStream(connectionUrl.getInputStream());
            BufferedReader breader = new BufferedReader(new InputStreamReader(input));

            String linea;

            while ((linea = breader.readLine()) != null) {
                total.append(linea);
            }

            JSONObject jsontotal = new JSONObject(total.toString());
            if (jsontotal.has("acontecimiento")) {

                AcontecimientoSQLiteHelper aconsqlh = new AcontecimientoSQLiteHelper(context, Environment.getExternalStorageDirectory() + "/rockcalendar.db", null, 1);

                SQLiteDatabase db = aconsqlh.getWritableDatabase();

                if (db != null) {
                    JSONObject jsonAcont = new JSONObject(jsontotal.getString("acontecimiento"));
                    String idAcontecimiento = (jsontotal.has("id") ? jsontotal.getString("id") : "");
                    String nombreAcontecimiento = (jsontotal.has("nombre") ? jsontotal.getString("nombre") : "");
                    String organizadorAcontecimiento = (jsontotal.has("organizador") ? jsontotal.getString("organizador") : "");
                    String descripcionAcontecimiento = (jsontotal.has("descripcion") ? jsontotal.getString("descripcion") : "");
                    String tipoAcontecimiento = (jsontotal.has("tipo") ? jsontotal.getString("tipo") : "");
                    String inicioAcontecimiento = (jsontotal.has("inicio") ? jsontotal.getString("inicio") : "");
                    String finAcontecimiento = (jsontotal.has("fin") ? jsontotal.getString("fin") : "");
                    String direccionAcontecimiento = (jsontotal.has("direccion") ? jsontotal.getString("direccion") : "");
                    String localidadAcontecimiento = (jsontotal.has("localidad") ? jsontotal.getString("localidad") : "");
                    String cod_postaldAcontecimiento = (jsontotal.has("cod_postal") ? jsontotal.getString("cod_postal") : "");
                    String provinciaAcontecimiento = (jsontotal.has("provincia") ? jsontotal.getString("provincia") : "");
                    String longitudAcontecimiento = (jsontotal.has("longitud") ? jsontotal.getString("longitud") : "");
                    String latitudAcontecimiento = (jsontotal.has("latitud") ? jsontotal.getString("latitud") : "");
                    String telefonoAcontecimiento = (jsontotal.has("telefono") ? jsontotal.getString("telefono") : "");
                    String emailAcontecimiento = (jsontotal.has("email") ? jsontotal.getString("email") : "");
                    String webAcontecimiento = (jsontotal.has("web") ? jsontotal.getString("web") : "");
                    String facebookAcontecimiento = (jsontotal.has("facebook") ? jsontotal.getString("facebook") : "");
                    String twitterAcontecimiento = (jsontotal.has("twitter") ? jsontotal.getString("twitter") : "");
                    String instagramAcontecimiento = (jsontotal.has("instagram") ? jsontotal.getString("instagram") : "");

                    db.delete("acontecimiento", "id="+idAcontecimiento, null);

                    //Creamos el registro a insertar como objeto ContentValues
                    ContentValues nuevoAcontecimiento = new ContentValues();
                    nuevoAcontecimiento.put("id", idAcontecimiento);
                    nuevoAcontecimiento.put("nombre",nombreAcontecimiento);
                    nuevoAcontecimiento.put("organizador", organizadorAcontecimiento);
                    nuevoAcontecimiento.put("descripcion", descripcionAcontecimiento);
                    nuevoAcontecimiento.put("tipo", tipoAcontecimiento);
                    nuevoAcontecimiento.put("inicio",inicioAcontecimiento);
                    nuevoAcontecimiento.put("fin",finAcontecimiento);
                    nuevoAcontecimiento.put("direccion", direccionAcontecimiento);
                    nuevoAcontecimiento.put("localidad", localidadAcontecimiento);
                    nuevoAcontecimiento.put("cod_postal", cod_postaldAcontecimiento);
                    nuevoAcontecimiento.put("provincia", provinciaAcontecimiento);
                    nuevoAcontecimiento.put("longitud", longitudAcontecimiento);
                    nuevoAcontecimiento.put("latitud", latitudAcontecimiento);
                    nuevoAcontecimiento.put("telefono", telefonoAcontecimiento);
                    nuevoAcontecimiento.put("email", emailAcontecimiento);
                    nuevoAcontecimiento.put("web", webAcontecimiento);
                    nuevoAcontecimiento.put("facebook", facebookAcontecimiento);
                    nuevoAcontecimiento.put("twitter", twitterAcontecimiento);
                    nuevoAcontecimiento.put("instagram", instagramAcontecimiento);

                    //Insertamos el registro en la base de datos
                    db.insert("acontecimiento", null, nuevoAcontecimiento);

                    if(jsontotal.has("eventos")){
                        JSONArray listaEventos = new JSONArray(jsontotal.getString("eventos"));

                        db.delete("evento", "id_acontecimiento="+idAcontecimiento,null);

                        for(int i = 0; i < listaEventos.length(); i++){
                            JSONObject objetoEvento = (JSONObject) listaEventos.get(i);
                            String idEvento = (objetoEvento.has("id") ? objetoEvento.getString("id") : "");
                            String idAcontecimientoEvento = (objetoEvento.has("id_acontecimiento") ? objetoEvento.getString("id_acontecimiento") : "");
                            String nombreEvento = (objetoEvento.has("nombre") ? objetoEvento.getString("nombre") : "");
                            String descripcionEvento = (objetoEvento.has("descripcion") ? objetoEvento.getString("descripcion") : "");
                            String inicioEvento = (objetoEvento.has("inicio") ? objetoEvento.getString("inicio") : "");
                            String finEvento = (objetoEvento.has("fin") ? objetoEvento.getString("fin") : "");
                            String direccionEvento = (objetoEvento.has("direccion") ? objetoEvento.getString("direccion") : "");
                            String localidadEvento = (objetoEvento.has("localidad") ? objetoEvento.getString("localidad") : "");
                            String cod_postalEvento = (objetoEvento.has("cod_postal") ? objetoEvento.getString("cod_postal") : "");
                            String provinciaEvento = (objetoEvento.has("provincia") ? objetoEvento.getString("provincia") : "");
                            String longitudEvento = (objetoEvento.has("longitud") ? objetoEvento.getString("longitud") : "");
                            String latitudEvento = (objetoEvento.has("latitud") ? objetoEvento.getString("latitud") : "");

                            //Creamos el registro a insertar como objeto ContentValues
                            ContentValues nuevoEvento = new ContentValues();
                            nuevoEvento.put("id", idEvento);
                            nuevoEvento.put("id_acontecimiento", idAcontecimientoEvento);
                            nuevoEvento.put("nombre", nombreEvento);
                            nuevoEvento.put("descripcion", descripcionEvento);
                            nuevoEvento.put("inicio", inicioEvento);
                            nuevoEvento.put("fin", finEvento);
                            nuevoEvento.put("direccion", direccionEvento);
                            nuevoEvento.put("localidad", localidadEvento);
                            nuevoEvento.put("cod_postal", cod_postalEvento);
                            nuevoEvento.put("provincia", provinciaEvento);
                            nuevoEvento.put("longitud", longitudEvento);
                            nuevoEvento.put("latitud", latitudEvento);

                            db.insert("evento", null, nuevoEvento);
                        }
                    }

                }
            } else if (jsontotal.has("code")) {
                flag = false;
                mensaje = jsontotal.getString("message");
            }

        } catch (
                Exception e)

        {
            e.printStackTrace();
        } finally

        {
            connectionUrl.disconnect();
        }

        return total.toString();


    }

    @Override
    protected void onPostExecute(String total){
        MyLog.d("OnPostExecute", total);

        //Ocultamos la ProgressBar
        pb.setVisibility(View.INVISIBLE);

        if(flag){
            //Creamos las SharedPreferences
            SharedPreferences prefs = context.getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("id", id);
            editor.commit();

            Intent intent = new Intent(context, MostrarAcontecimientoActivity.class);
            context.startActivity(intent);
        }else{
            //Buscamos la vista por el contexto y con esa vista creamos el snackbar
            View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
            View v = rootView.findViewById(R.id.toolbar_buscar_acontecimientos);
            Snackbar.make(v, "Error en la base de datos", Snackbar.LENGTH_LONG).show();
        }
    }
}
