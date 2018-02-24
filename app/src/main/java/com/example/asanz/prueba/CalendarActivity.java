package com.example.asanz.prueba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

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
    /*
    Cliente para la conexión al servidor
     */
    HttpURLConnection con;

    ListView events;
    String[] eventos = {
            "Tutoría",
            "Entrega Tarea2"
    } ;
    Integer[] imageId = {
            R.drawable.calendar,
            R.drawable.calendar
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        final CalendarDAO calendarDAO = new CalendarDAO();
        calendarDAO.obtenerEventos(new ServerCallBack() {
            @Override
            public void onSuccess(JSONArray result) {
                //TODO mostras listado de eventos del usuario
                GenericList coursesList = new GenericList(CalendarActivity.this, eventos, imageId);
                events = (ListView)findViewById(R.id.EventsList);
                events.setAdapter(coursesList);
                events.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        startActivity(new Intent(getApplicationContext(), DetailEventActivity.class));
                    }
                });
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
}
