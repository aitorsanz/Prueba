package com.example.asanz.prueba;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


/**
 * Created by asanz on 11/05/2017.
 */

public class ResourceEjercicioActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_ejercicio);
        this.showEjercicio();
    }

    // TODO: 25/05/2017 Conexión con campus para obtener ejercicio
    /**
     * Muestra un tipo de recurso Ejercicio
     */
    public void showEjercicio() {

        ImageView img = (ImageView)findViewById(R.id.ejercicio);
        //img = (ImageView)findViewById(R.id.ejercicio);
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
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
        //return true;
    }
}