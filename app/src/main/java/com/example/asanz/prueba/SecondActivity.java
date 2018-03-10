package com.example.asanz.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;



/**
 * Created by asanz on 07/03/2017.
 */

public class SecondActivity extends  BaseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AppController global = ((AppController)getApplicationContext());
        String token = global.getToken();
        //super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //Settings
        ImageView img = (ImageView)findViewById(R.id.settings_icon);
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
            }
        });
        //Courses
        img = (ImageView)findViewById(R.id.courses_icon);
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),CoursesActivity.class));
            }
        });
        //Messages
        img = (ImageView)findViewById(R.id.message_icon);
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),MessagesActivity.class));
            }
        });
        //Grades
        img = (ImageView)findViewById(R.id.grades_icon);
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),GradesActivity.class));
            }
        });
        //Calendar
        img = (ImageView)findViewById(R.id.calendar_icon);
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(),CalendarActivity.class));
            }
        });

        super.onCreate(savedInstanceState);
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
