package com.example.asanz.prueba;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.layout.simple_list_item_1;

/**
 * Created by asanz on 17/04/2017.
 */

public class GradesActivity extends BaseActivity {

    private TextView total;

    // Initializing a new String Array
    List<String> notas;
    List<String> indices;

    protected void onCreate(Bundle savedInstanceState) {
        AppController global = ((AppController)getApplicationContext());
        String token = global.getToken();
        String idAlumno = global.getIdAlumno();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);
        Resources res = getResources();
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        notas = new ArrayList<String>();
        indices = new ArrayList<String>();
        notas.add("Calificaciones");
        final LinearLayout gradesLayout = (LinearLayout) this.findViewById(R.id.gradeslayout);
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final GradesDAO gradesDAO = new GradesDAO();
        gradesDAO.obtenerNotas(token, idAlumno, new ServerCallBack() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    String response = null;
                    response = result.getString(0);
                    JSONObject respuesta = new JSONObject(response);
                    JSONArray array = null;
                    array = respuesta.getJSONArray("data");
                    String tiempo = array.getJSONObject(0).getString("total");
                    String cursos = array.getJSONObject(1).getString("courses");
                    JSONArray courses = new JSONArray(cursos);
                    int tam = courses.length();
                    TextView tv ;

                    for(int i = 0; i < tam; i++){
                        JSONObject curso = courses.getJSONObject(i);
                        String nombre =  curso.get("name").toString();
                        String nota = curso.get("nota").toString();
                        notas.add(nombre +" : "+nota);
                        indices.add(curso.get("id").toString());
                        tv = new TextView(getApplicationContext());
                        tv.setLayoutParams(lparams);
                        tv.setText(notas.get(i+1));
                        gradesLayout.addView(tv);
                    }

                    total = (TextView) findViewById(R.id.time);
                    total.setText(tiempo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError() {
                CharSequence text = "Imposible cargar los eventos";
                TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                textToast.setText(text);
                Toast toast = new Toast(context);
                toast.setDuration(duration);
                toast.setView(layout);
                toast.show();
            }
        }, true);
        int numNotas = notas.size();
        Log.d("tam", Integer.toString(numNotas));
        if(numNotas > 0){
            TextView tv ;
            for(int i = 0; i < numNotas; i++){
                tv = new TextView(this);
                tv.setLayoutParams(lparams);
                tv.setText(notas.get(i));
                gradesLayout.addView(tv);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
        //return true;
    }
}
