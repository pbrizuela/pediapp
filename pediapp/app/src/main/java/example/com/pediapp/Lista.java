package example.com.pediapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import example.com.pediapp.configuraciones.Configuraciones;

public class Lista extends AppCompatActivity {
    ListView lv;
    ArrayList<String> lista;
    ArrayAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        lv = (ListView) findViewById(R.id.lista);
        lista = new ArrayList<>();
        TareaWSObtener tarea = new TareaWSObtener();
        tarea.execute();
    }
     private class TareaListar extends AsyncTask<String, Integer, Boolean> {
        protected Boolean doInBackground(String... params) {
            boolean resul = true;
            String x = "1";
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet del =
                    new HttpGet(Configuraciones.Irnet.IrDatosHijosLista + "/" + getIntent().getExtras().getInt("parametro"));
            del.setHeader("content-type", "application/json");
             try {
                HttpResponse resp = httpClient.execute(del);
                String respStr = EntityUtils.toString(resp.getEntity());
                System.out.println(respStr);
                JSONArray respJSON = new JSONArray(respStr);

                for (int i = 0; i < respJSON.length(); i++) {
                    JSONObject obj = respJSON.getJSONObject(i);
                    lista.add("C.I.Nº:  " + obj.getInt("cedula"));
                    lista.add("NOMBRE:  " + obj.getString("nombres"));
                    lista.add("APELLIDO:  " + obj.getString("apellidos"));
                    lista.add("LUGAR NACIMIENTO: " + obj.getString("lugarNacimiento"));
                    lista.add("FECHA NACIMIENTO: " + obj.getString("fechaNacimiento"));
                    lista.add("SEXO:  " + obj.getString("sexo"));
                    lista.add("NACIONALIDAD:  " + obj.getString("nacionalidad"));
                }
            } catch (Exception ex) {
                Log.e("ServicioRest", "Error....", ex);
                resul = false;
            }
            return resul;
        }
        protected void onPostExecute(Boolean result) {
            if (result) {
                adaptador = new ArrayAdapter(Lista.this, android.R.layout.simple_list_item_1, lista);
                lv.setAdapter(adaptador);
            } else {
                adaptador = new ArrayAdapter(Lista.this, android.R.layout.simple_list_item_1, lista);
                lv.setAdapter(adaptador);
            }
        }
    }
    private class TareaWSObtener extends AsyncTask<String, Integer, Boolean> {
        protected Boolean doInBackground(String... params) {
            boolean resul = true;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet del =
                    new HttpGet(Configuraciones.Irnet.IrDatosHijosLista + "/" + getIntent().getExtras().getInt("parametro"));
            del.setHeader("content-type", "application/json");
            try {
                HttpResponse resp = httpClient.execute(del);
                String respStr = EntityUtils.toString(resp.getEntity());
                System.out.println(respStr);
                HttpResponse response = httpClient.execute(del);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    InputStream instream = response.getEntity().getContent();
                    BufferedReader r = new BufferedReader(new InputStreamReader(
                            instream), 8000);
                    StringBuilder total = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        total.append(line);
                    }
                    instream.close();
                    String bufstring = total.toString();
                    System.out.println(bufstring);
                    JSONObject obj = new JSONObject(bufstring);
                    lista.add("C.I.Nº:  " + obj.getInt("cedula"));
                    lista.add("NOMBRE:  " + obj.getString("nombres"));
                    lista.add("APELLIDO:  " + obj.getString("apellidos"));
                    lista.add("LUGAR NACIMIENTO: " + obj.getString("lugarNacimiento"));
                    lista.add("FECHA NACIMIENTO: " + obj.getString("fechaNacimiento"));
                    lista.add("SEXO:  " + obj.getString("sexo"));
                    lista.add("NACIONALIDAD:  " + obj.getString("nacionalidad"));
                } else {
                    JSONObject obj = new JSONObject(respStr);
                    lista.add("C.I.Nº:  " + obj.getInt("cedula"));
                    lista.add("NOMBRE:  " + obj.getString("nombres"));
                    lista.add("APELLIDO:  " + obj.getString("apellidos"));
                    lista.add("LUGAR NACIMIENTO: " + obj.getString("lugarNacimiento"));
                    lista.add("FECHA NACIMIENTO: " + obj.getString("fechaNacimiento"));
                    lista.add("SEXO:  " + obj.getString("sexo"));
                    lista.add("NACIONALIDAD:  " + obj.getString("nacionalidad"));
                }
            } catch (Exception ex) {
                Log.e("ServicioRest", "Error.....", ex);
                resul = false;
            }
            return resul;
        }
        protected void onPostExecute(Boolean result) {
            if (result) {
                adaptador = new ArrayAdapter(Lista.this, android.R.layout.simple_list_item_1, lista);
                lv.setAdapter(adaptador);
            } else {
                 adaptador = new ArrayAdapter(Lista.this, android.R.layout.simple_list_item_1, lista);//cargaspinner
                lv.setAdapter(adaptador);
            }
        }
    }

}
