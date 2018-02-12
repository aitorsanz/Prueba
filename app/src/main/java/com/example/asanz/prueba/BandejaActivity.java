package com.example.asanz.prueba;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.net.HttpURLConnection;



/**
 * Created by asanz on 17/04/2017.
 */

public class BandejaActivity extends BaseActivity {

    /*
    Cliente para la conexión al servidor
     */
    HttpURLConnection con;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja);
        // TODO: 25/05/2017 Conexión con campus para obtener los mensajes de la bandeja

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
