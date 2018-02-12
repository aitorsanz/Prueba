package com.example.asanz.prueba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by asanz on 19/04/2017.
 */
// TODO: 25/05/2017 Conexión con campues para crear evento
public class CreateEventActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createevent);
    }

    public void clickBotonCrearEvento (View view) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
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
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
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
