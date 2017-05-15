package com.example.asanz.prueba;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by asanz on 11/05/2017.
 */

public class ResourceEjercicioActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_ejercicio);
        this.showEjercicio();
    }

    /**
     * Muestra un tipo de recurso Ejercicio
     */
    public void showEjercicio() {

        ImageView img = (ImageView)findViewById(R.id.settings_icon);
        img = (ImageView)findViewById(R.id.ejercicio);
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Uri uri = Uri.parse("http://eina.unizar.es/archivos/2014_2015/PFC_TrabajosFinEstudios/Propuesta_TFG.pdf");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salir:
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast,
                        (ViewGroup) findViewById(R.id.toast_layout_root));
                CharSequence text = "Saliendo";
                TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                textToast.setText(text);
                Toast toast = new Toast(context);
                toast.setDuration(duration);
                toast.setView(layout);
                toast.show();
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