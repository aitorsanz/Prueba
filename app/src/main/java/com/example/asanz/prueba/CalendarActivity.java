package com.example.asanz.prueba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Created by asanz on 17/04/2017.
 */

public class CalendarActivity extends BaseActivity {
    TextView tv[];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AppController global = ((AppController)getApplicationContext());
        String token = global.getToken();
        String idAlumno = global.getIdAlumno();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        final LinearLayout gradesLayout = (LinearLayout) this.findViewById(R.id.calendarlayout);
        final CalendarDAO calendarDAO = new CalendarDAO();
        calendarDAO.obtenerEventos(token, idAlumno, new ServerCallBack() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    String response = null;
                    response = result.getString(0);
                    JSONObject respuesta = new JSONObject(response);
                    JSONArray array = null;
                    array = respuesta.getJSONArray("data");
                    int tam = array.length();
                    tv = new TextView[tam];
                    for (int i = 0; i < tam; i++) {
                        String titulo = array.getJSONObject(i).getString("texto");
                        String fechaInicio = array.getJSONObject(i).getString("fechaInicio");
                        String fechaFin = array.getJSONObject(i).getString("fechaFin");
                        String mensaje = titulo + " " + fechaInicio + " - " + fechaFin;
                        tv[i] = new TextView(getApplicationContext());
                        tv[i].setText(mensaje);
                        tv[i].setId(i);
                        gradesLayout.addView(tv[i]);
                        tv[i].setOnClickListener(onclicklistener);
                        tv[i].setOnLongClickListener(onLongClickListener);
                    }
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
    }

    public void clickCrearEvento (View view) {
        startActivity(new Intent(getApplicationContext(),CreateEventActivity.class));
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

    View.OnClickListener onclicklistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if(v == tv[0]){
                Log.d("entra", tv[0].toString());
                //do whatever you want....
            }
        }
    };
    View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            // TODO Auto-generated method stub
            if(v == tv[0]){
                Log.d("entra2", tv[0].toString());
                //do whatever you want....
                registerForContextMenu(tv[0]);
            }
            return false;
        }
    };
}
