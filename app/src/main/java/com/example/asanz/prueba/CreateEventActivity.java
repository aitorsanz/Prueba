package com.example.asanz.prueba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by asanz on 19/04/2017.
 */
// TODO: 25/05/2017 Conexión con campues para crear evento
public class CreateEventActivity extends BaseActivity {
    DatePicker datePickerInicio;
    DatePicker datePickerFinal;
    private EditText titulo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createevent);
    }

    public void clickBotonCrearEvento (View view) {
        AppController global = ((AppController)getApplicationContext());
        String token = global.getToken();
        String idAlumno = global.getIdAlumno();

        datePickerInicio = (DatePicker) findViewById(R.id.fechaInicio);
        datePickerFinal = (DatePicker) findViewById(R.id.fechaFin);
        titulo = (EditText) findViewById(R.id.titulo);
        String title = titulo.getText().toString();

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            int yearInicio = datePickerInicio.getYear();
            int monthInicio = datePickerInicio.getMonth();
            int dayInicio = datePickerInicio.getDayOfMonth();
            String fechaInicio = Integer.toString(yearInicio) + "-" + Integer.toString(monthInicio) + "-" + Integer.toString(dayInicio);
            Date dateInicio = formatter.parse(fechaInicio);
            Timestamp timestampInicio = new java.sql.Timestamp(dateInicio.getTime());

            int yearFin = datePickerFinal.getYear();
            int monthFin = datePickerFinal.getMonth();
            int dayFin = datePickerFinal.getDayOfMonth();
            String fechaFin = Integer.toString(yearFin) + "-" + Integer.toString(monthFin) + "-" + Integer.toString(dayFin);
            Date dateFinal = formatter.parse(fechaFin);
            Timestamp timestampFinal = new java.sql.Timestamp(dateFinal.getTime());
            JSONObject evento = new JSONObject();
            try {
                evento.put("titulo", title);
                evento.put("fechaInicio", timestampInicio);
                evento.put("fechaFin", timestampFinal);
                evento.put("idAlumno", idAlumno);
                final Intent intent = new Intent(this, CalendarActivity.class);
                final Context context = getApplicationContext();
                final int duration = Toast.LENGTH_SHORT;
                LayoutInflater inflater = getLayoutInflater();
                final View layout = inflater.inflate(R.layout.custom_toast,
                        (ViewGroup) findViewById(R.id.toast_layout_root));

                final CalendarDAO calendarDAO = new CalendarDAO();
                calendarDAO.crearEvento(evento, token, new ServerCallBack() {
                    @Override
                    public void onSuccess(JSONArray result) {
                        Log.d("Reultado", result.toString());
                        CharSequence text = "Evento Creado";
                        TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                        textToast.setText(text);
                        Toast toast = new Toast(context);
                        toast.setDuration(duration);
                        toast.setView(layout);
                        toast.show();
                        startActivity(intent);
                    }
                    @Override
                    public void onError() {
                        CharSequence text = "Sucedió un problema guardando el evento";
                        TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                        textToast.setText(text);
                        Toast toast = new Toast(context);
                        toast.setDuration(duration);
                        toast.setView(layout);
                        toast.show();
                    }
                }, true);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (ParseException e) {
            e.printStackTrace();
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
