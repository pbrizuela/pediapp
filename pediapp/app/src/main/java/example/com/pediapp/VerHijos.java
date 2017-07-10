package example.com.pediapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import example.com.pediapp.actores.Hijo;
import example.com.pediapp.actores.Vacuna;
import example.com.pediapp.configuraciones.Configuraciones;

public class VerHijos extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String MY_ACCOUNT_NAME = "Vacunas";
    public static Hijo hijoSeleccionado = new Hijo();
    public int posi;
    protected Spinner clientesSpin;
    protected TextView direccionCli;
    protected TextView telefonoCli;
    protected TextView correoCli;
    protected TextView tdireccionCli;
    protected TextView ttelefonoCli;
    protected TextView tcorreoCli;
    protected List<Hijo> hijoList = new ArrayList<Hijo>();
    protected List<String> nombreHijo = new ArrayList<String>();
    protected String id_usuario;
    Uri uri;
    private ArrayList<Vacuna> lista = new ArrayList();
    private ArrayList<Integer> lista_final = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_hijos);
        direccionCli = (TextView) findViewById(R.id.direccion);
        telefonoCli = (TextView) findViewById(R.id.telefono);
        correoCli = (TextView) findViewById(R.id.email);
        tdireccionCli = (TextView) findViewById(R.id.tdireccion);
        ttelefonoCli = (TextView) findViewById(R.id.ttelefono);
        tcorreoCli = (TextView) findViewById(R.id.temail);
        clientesSpin = (Spinner) findViewById(R.id.nomnacsex);
        clientesSpin.setOnItemSelectedListener(this);
        id_usuario = getIntent().getExtras().getString("id_usuario");
        TareaWSListar tarea = new TareaWSListar();
        tarea.execute();
        TareaWSVListarMes tarea2 = new TareaWSVListarMes();
        tarea2.execute();
        ArrayAdapter<Hijo> dataAdapter = new ArrayAdapter<Hijo>(this, android.R.layout.simple_spinner_item, hijoList);// se carga el spinner
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientesSpin.setAdapter(dataAdapter);
    }

    public void llenar_lv1() {
        Hijo hijo = new Hijo();
        hijo.setId_hijo(0);
        hijo.setNombres("Nombres  ");
        hijo.setFecha_nacimiento(" FechaNacimiento ");
        hijo.setSexo("Sexo");
        nombreHijo.add(0, "Selecionar Hijo/a");
        hijoList.add(0, hijo);
    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if (position == 0) {
            Toast.makeText(parent.getContext(), "Seleccionar hijo/a ", Toast.LENGTH_SHORT).show();
        }
        hijoSeleccionado = hijoList.get(position);
        posi = hijoList.get(position).getCedula();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    public void siguiente1(View v) {
        if (hijoSeleccionado != null
                & hijoSeleccionado != hijoList.get(0)) {
            Intent intento = new Intent(VerHijos.this, VerVacunas.class);
            intento.putExtra("parametro", posi);
            startActivity(intento);
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        } else {
            Toast.makeText(v.getContext(), "SELECCIONE UN HIJO/A!!", Toast.LENGTH_SHORT).show();
        }
    }
    public void mostrarMas(View v) {
        if (hijoSeleccionado != null
                & hijoSeleccionado != hijoList.get(0)) {
            Intent intento = new Intent(VerHijos.this, Lista.class);
            intento.putExtra("parametro", posi);
            startActivity(intento);
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        } else {
            Toast.makeText(v.getContext(), "SELECCIONE UN HIJO/A!!", Toast.LENGTH_SHORT).show();
        }
    }


    private class TareaWSListar extends AsyncTask<String, Integer, Boolean> {


        protected Boolean doInBackground(String... params) {

            boolean resul = true;
            HttpClient httpClient = new DefaultHttpClient();

            HttpGet del =
                    new HttpGet(Configuraciones.Irnet.IrHijosUsuario + id_usuario);

            del.setHeader("content-type", "application/json");
            llenar_lv1();
            try {
                HttpResponse resp = httpClient.execute(del);
                String respStr = EntityUtils.toString(resp.getEntity());
                JSONArray respJSON = new JSONArray(respStr);

                for (int i = 0; i < respJSON.length(); i++) {
                    JSONObject obj = respJSON.getJSONObject(i);
                    Hijo hijo;
                    hijo = new Hijo();
                    hijo.setId_hijo(i + 1);
                    hijo.setCedulaRuc(obj.getInt("cedula"));
                    hijo.setNombres(obj.getString("nombres"));
                    hijo.setApellidos(obj.getString("apellidos"));
                    hijo.setLugar_nacimiento(obj.getString("lugarNacimiento"));
                    hijo.setFecha_nacimiento(obj.getString("fechaNacimiento"));
                    hijo.setNacionalidad(obj.getString("nacionalidad"));
                    hijo.setSexo(obj.getString("sexo"));
                    hijo.setId_usuario(obj.getInt("id_usuario"));
                    nombreHijo.add(i, obj.getString("nombres"));
                    hijoList.add(i + 1, hijo);
                }
            } catch (Exception ex) {
                Log.e("ServicioRest", "ERROR....", ex);
                resul = false;
            }

            return resul;
        }
        protected void onPostExecute(Boolean result) {
            if (result) {
                ArrayAdapter<Hijo> dataAdapter = new ArrayAdapter<Hijo>(VerHijos.this, android.R.layout.simple_spinner_item, hijoList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                clientesSpin.setAdapter(dataAdapter);
            } else {
                ArrayAdapter<Hijo> dataAdapter = new ArrayAdapter<Hijo>(VerHijos.this, android.R.layout.simple_spinner_item, hijoList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                clientesSpin.setAdapter(dataAdapter);
            }
        }
    }
    private class TareaWSVListarMes extends AsyncTask<String, Integer, Boolean> {
        protected Boolean doInBackground(String... params) {
            boolean resul = true;
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet del;
                del = new HttpGet(Configuraciones.Irnet.UriVacunasUsuario + id_usuario);
                HttpResponse resp = httpClient.execute(del);
                String respStr = EntityUtils.toString(resp.getEntity());
                JSONArray respJSON = new JSONArray(respStr);
                int w = 0;
                System.out.println(respJSON.length());
                for (int i = 0; i < respJSON.length(); i++) {
                    Vacuna vacuna = new Vacuna();
                    JSONObject obj = respJSON.getJSONObject(i);
                    vacuna.setId_hijo(obj.getJSONObject("vacunasPK").getInt("idHijo"));
                    vacuna.setMes_aplicacion(obj.getInt("mesAplicacion"));
                    vacuna.setAplicada(obj.getInt("aplicada"));
                    System.out.println(vacuna.getId_hijo() + " " + vacuna.getMes_aplicacion() + " " + vacuna.getAplicada());
                    lista.add(i, vacuna);
                }
            } catch (Exception ex) {
                Log.e("ServicioRest", "ERROR....", ex);
                resul = false;
            }
            return resul;
        }
        protected void onPostExecute(Boolean result) {
            if (result) {

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                if (!prefs.getBoolean("firstTime", false)) {

                    obtenerCantidad();
                    loadNotificaciones();
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("firstTime", true);
                    editor.commit();
                }
            }
        }
        private void loadNotificaciones() {//carga las notificaciones
            Configuraciones util = new Configuraciones();
            if ((hijoList.size()) == (lista_final.size() + 1)) {
                System.out.println(" TAM  " + hijoList.size());
                System.out.println(" TAM  " + hijoList.size());
                System.out.println(" TAM  " + hijoList.size());
                System.out.println(" TAM  " + hijoList.size());
                System.out.println(" TAM  " + hijoList.size());

                System.out.println(" TAM  " + lista_final.size());

                System.out.println(" TAM  " + lista_final.size());

                System.out.println(" TAM  " + lista_final.size());

                System.out.println(" TAM  " + lista_final.size());

                try {
                    for (int k = 1; k < hijoList.size(); k++) {

                        ArrayList<Integer> mes = obtenerMes(hijoList.get(k).getCedula());
                        System.out.println(mes.size());
                        if (mes.size() != 0) {
                            for (int i = 0; i < mes.size(); i++) {
                                String fecha = util.calcularFechaAAplicar(hijoList.get(k).getFecha_nacimiento(), mes.get(i));
                                new Notificacion(getApplicationContext(),
                                        util.calcularNotificacion(fecha),
                                        hijoList.get(k).getCedula(),
                                        hijoList.get(k).getNombres() + " " + hijoList.get(k).getApellidos(),
                                        mes.get(i));
                                System.out.println(fecha);
                            }
                        }
                    }

                } catch (Exception e) {
                    System.out.println("NOTIFICACION");
                }
            }
        }
        ArrayList<Integer> obtenerMes(Integer id_hijo) {
            ArrayList<Integer> aux = new ArrayList<Integer>();
            for (int i = 0; i < lista.size(); i++) {
                lista_final.add(lista.get(i).getId_hijo());
                if ((lista.get(i).getId_hijo() == id_hijo) && (lista.get(i).getAplicada() == 0)) {
                    aux.add(lista.get(i).getMes_aplicacion());
                    System.out.println(lista.get(i).getMes_aplicacion());
                }
            }
            HashSet<Integer> hashSet = new HashSet<Integer>(aux);
            aux.clear();
            aux.addAll(hashSet);
            for (Integer list : aux) {
                System.out.println(list);
            }
            Collections.sort(aux);
            HashSet<Integer> hashSet1 = new HashSet<Integer>(lista_final);
            lista_final.clear();
            lista_final.addAll(hashSet1);
            for (Integer list : lista_final) {
                System.out.println(lista_final);
            }
            Collections.sort(lista_final);
            return aux;
        }
        public void obtenerCantidad() {
            ArrayList<Integer> aux = new ArrayList<Integer>();
            for (int i = 0; i < lista.size(); i++) {
                lista_final.add(lista.get(i).getId_hijo());
            }
            HashSet<Integer> hashSet1 = new HashSet<Integer>(lista_final);
            lista_final.clear();
            lista_final.addAll(hashSet1);
            for (Integer list : lista_final) {
                System.out.println(lista_final);
            }
            Collections.sort(lista_final);
        }
    }
}
