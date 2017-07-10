package example.com.pediapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import example.com.pediapp.actores.Vacuna;
import example.com.pediapp.configuraciones.Configuraciones;
import example.com.pediapp.configuraciones.DibujarTabla;
import example.com.pediapp.configuraciones.Filtro;


public class VerVacunas extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public int orden;
    protected Spinner clientesSpin;
    protected TableLayout tab;
    protected boolean notifi = false;
    ArrayList<Filtro> listita = new ArrayList<>();
    ArrayList<Vacuna> vacunaList;
    DibujarTabla dibujarTabla;
    private Activity actividad = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_vacunas);
        cargar();
        clientesSpin = (Spinner) findViewById(R.id.spinner);
        clientesSpin.setOnItemSelectedListener(this);
        ArrayAdapter<Filtro> dataAdapter = new ArrayAdapter<Filtro>(this, android.R.layout.simple_spinner_item, listita);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientesSpin.setAdapter(dataAdapter);
        notifi = getIntent().getExtras().getBoolean("noti");
        if (notifi) {
            clientesSpin.setSelection(1);
        }
        tab = (TableLayout) findViewById(R.id.tabla);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        tab.removeAllViews();
        dibujarTabla = new DibujarTabla(this, (TableLayout) findViewById(R.id.tabla));
        orden = position;
        if (position != 0) {
            TareaWSVListar tarea = new TareaWSVListar();
            tarea.execute();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void cargar() {
         Filtro aux = new Filtro();
        aux.setNombre("Elija Filtro para verificar Vacunas de su Hijo");
        aux.setPosicion(0);
        listita.add(aux);
        aux = new Filtro();
        aux.setNombre("Vacunas No Aplicadas");
        aux.setPosicion(1);
        listita.add(aux);
        aux = new Filtro();
        aux.setNombre("Vacunas Aplicadas");
        aux.setPosicion(2);
        listita.add(aux);
        aux = new Filtro();
        aux.setNombre("Todas las Vacunas");
        aux.setPosicion(3);
        listita.add(aux);
        aux = new Filtro();
        aux.setNombre("Ordenadas Por Fecha");
        aux.setPosicion(4);
        listita.add(aux);
    }
    private class TareaWSVListar extends AsyncTask<String, Integer, Boolean> {
        protected Boolean doInBackground(String... params) {
            boolean resul = true;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet del;
            if (orden == 0) {
                del =
                        new HttpGet(Configuraciones.Irnet.IrVacunas + "xxxxx");
            } else {
                if (orden == 1) {//vacunas aplicadas
                    del = new HttpGet(Configuraciones.Irnet.IrVacunas + getIntent().getExtras().getInt("parametro") + "/0");
                } else {
                    if (orden == 2) {//vacunas aun no aplicadas
                        del = new HttpGet(Configuraciones.Irnet.IrVacunas + getIntent().getExtras().getInt("parametro") + "/1");
                    } else {
                        if (orden == 3) {//trae todas las vacunas
                            del = new HttpGet(Configuraciones.Irnet.IrVacunas + "ordenado/" + getIntent().getExtras().getInt("parametro"));
                        } else {//ordenado por fecha
                            del = new HttpGet(Configuraciones.Irnet.IrVacunas + "ordenado/" + getIntent().getExtras().getInt("parametro"));
                        }
                    }
                }
            }

            del.setHeader("content-type", "application/json");
            try {
                HttpResponse resp = httpClient.execute(del);
                String respStr = EntityUtils.toString(resp.getEntity());
                JSONArray respJSON = new JSONArray(respStr);
                vacunaList = new ArrayList<>();
                for (int i = 0; i < respJSON.length(); i++) {
                    JSONObject obj = respJSON.getJSONObject(i);
                    Vacuna vacuna;
                    vacuna = new Vacuna();
                    vacuna.setNombre(obj.getString("nombre"));
                    vacuna.setAplicada(obj.getInt("aplicada"));
                    vacuna.setFecha(obj.getString("fecha"));
                    vacunaList.add(i, vacuna);
                }
            } catch (Exception ex) {
                Log.e("ServicioRest", "Error", ex);
                resul = false;
            }
            return resul;
        }
        protected void onPostExecute(Boolean result) {
            if (result) {
                if (orden == 3) {
                    Collections.sort(vacunaList, new Comparator<Vacuna>() {
                        public int compare(Vacuna obj1, Vacuna obj2) {
                            return obj1.getNombre().compareTo(obj2.getNombre());
                        }
                    });
                }
                int tamano = vacunaList.size();
                dibujarTabla.agregarCabecera(R.array.cabecera_tabla);
                for (int i = 0; i < tamano; i++) {
                    ArrayList<String> elementos = new ArrayList<String>();
                    elementos.add(vacunaList.get(i).getNombre());
                    if (vacunaList.get(i).getAplicada() == 0) {
                        elementos.add("NO");
                    } else {
                        elementos.add("SI");
                    }
                    elementos.add(vacunaList.get(i).getFecha());
                    dibujarTabla.agregarFilaTabla(elementos);
                }
            }
        }
    }


}

