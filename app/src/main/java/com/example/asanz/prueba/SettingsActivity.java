package com.example.asanz.prueba;


import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



/**
 * Created by asanz on 17/04/2017.
 */

public class SettingsActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Resources res = getResources();
        // TODO: 25/05/2017 Conexión con campus para obtener la configuración del usuarios
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
