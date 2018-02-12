package com.example.asanz.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.net.HttpURLConnection;

/**
 * Created by asanz on 17/05/2017.
 */

public class SubjectActivity extends BaseActivity {

    /*
    Cliente para la conexión al servidor
     */
    HttpURLConnection con;
    ListView subjects;
    String[] asignaturas = {
            "Asignatura 1",
            "Asignatura 2"
    } ;
    Integer[] imageId = {
            R.drawable.asignatura,
            R.drawable.asignatura
    };
    // TODO: 25/05/2017 Conexión con campues para obtener listado de asignaturas de un curso
    Class[] classArray = new Class[] { ResourceActivity.class };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        GenericList coursesList = new GenericList(SubjectActivity.this, asignaturas, imageId);
        subjects = (ListView)findViewById(R.id.SubjectsList);
        subjects.setAdapter(coursesList);
        subjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getApplicationContext(),classArray[0]);
                startActivity(intent);

            }
        });
        // Obtener la instancia de la lista
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
