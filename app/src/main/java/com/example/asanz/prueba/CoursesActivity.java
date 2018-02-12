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
 * Created by asanz on 17/04/2017.
 */

public class CoursesActivity extends BaseActivity {
    /*
    Cliente para la conexión al servidor
     */
    HttpURLConnection con;
    ListView courses;
    String[] curso = {
            "Curso 1",
            "Curso 2"
    } ;
    Integer[] imageId = {
            R.drawable.cursos,
            R.drawable.cursos
    };
    Class[] classArray = new Class[] { SubjectActivity.class };
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        GenericList coursesList = new GenericList(CoursesActivity.this, curso, imageId);
        courses = (ListView)findViewById(R.id.CoursesList);
        courses.setAdapter(coursesList);
        courses.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getApplicationContext(),classArray[0]);
                startActivity(intent);

            }
        });
        // TODO: 25/05/2017 Conexión con campus para obtener los cursos del usuario
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
