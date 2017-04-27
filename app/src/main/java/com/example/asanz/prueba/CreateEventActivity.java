package com.example.asanz.prueba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;


import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;


/**
 * Created by asanz on 19/04/2017.
 */

public class CreateEventActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createevent);
    }

    public void clickBotonCrearEvento (View view) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        CharSequence text = "Evento Creado";
        TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
        textToast.setText(text);
        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(layout);
        toast.show();

        Intent intent = new Intent(this, CalendarActivity.class);
        CalendarView simpleCalendarView = (CalendarView) findViewById(R.id.createeventcalendar); // get the reference of CalendarView
        long selectedDate = simpleCalendarView.getDate(); // get selected date in milliseconds
        intent.putExtra("selectedDate", selectedDate);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salir:
                Toast.makeText(this, "Saliendo", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
