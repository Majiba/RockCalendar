package com.example.manue.rockcalendar;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Manuel Jiménez on 09/11/2017.
 */

public class RestAcontecimientos extends AsyncTask<String, String, String> {

    private String busqueda;
    private ProgressBar progressBar;
    private String mensaje;
    private boolean flag = true;
    private HttpURLConnection connectionUrl;
    private Context context;
    private ProgressBar pb;
    private TextView tvError;
    private ArrayList<Acontecimiento> acontecimientos;
    private RecyclerView recyclerView;

    public RestAcontecimientos(String busqueda, Context context, ProgressBar pb, TextView tv, RecyclerView recyclerView){
        super();
        this.busqueda = busqueda;
        this.context = context;
        this.pb = pb;
        this.tvError = tv;
        acontecimientos = new ArrayList<>();
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute(){ pb.setVisibility(View.VISIBLE);}

    @Override
    protected String doInBackground(String... args) {
        StringBuilder total = new StringBuilder();

        try {
            URL url = new URL(Constantes.REST_URL+"buscar/nombre/"+this.busqueda);
            connectionUrl = (HttpURLConnection) url.openConnection();

            InputStream input = new BufferedInputStream(connectionUrl.getInputStream());
            BufferedReader breader = new BufferedReader(new InputStreamReader(input));

            String linea;

            while((linea = breader.readLine()) != null){
                total.append(linea);
            }

            JSONObject jsontotal = new JSONObject(total.toString());
            if(jsontotal.has("acontecimientos")){
                JSONArray jsonListaAcont = new JSONArray(jsontotal.getString("acontecimientos"));

                for(int i=0; i< jsonListaAcont.length(); i++){
                    JSONObject jsonAcont = (JSONObject) jsonListaAcont.get(i);
                    String id = (jsonAcont.has("id") ? jsonAcont.getString("id") : "");
                    String nombre = (jsonAcont.has("nombre") ? jsonAcont.getString("nombre") : "");
                    String inicio = (jsonAcont.has("inicio") ? jsonAcont.getString("inicio") : "");

                    Acontecimiento ac = new Acontecimiento(id,nombre,inicio);
                    acontecimientos.add(ac);
                }
            } else if(jsontotal.has("code")) {
                flag = false;
                mensaje = jsontotal.getString("message");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionUrl.disconnect();
        }


        return total.toString();
    }

    @Override
    protected void onPostExecute(String total){
        pb.setVisibility(View.INVISIBLE);

        if(flag){
            tvError.setText("");

            AcontecimientoAdapter adapter = new AcontecimientoAdapter(acontecimientos);

            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = recyclerView.getChildAdapterPosition(v);

                    new RestAcontecimiento(context, acontecimientos.get(position).getId(), pb).execute();
                }
            });

            recyclerView.setAdapter(adapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }else{
            tvError.setText("No existen acontecimientos con ese nombre");
        }
    }
}
