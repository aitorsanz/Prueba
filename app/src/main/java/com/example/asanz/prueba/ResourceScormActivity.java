package com.example.asanz.prueba;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by asanz on 11/05/2017.
 */

public class ResourceScormActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_scorm);
        this.showScorm();
    }
    /**
     * Muestra un tipo de recurso Info
     * */
    public void showScorm(){
        TextView vidView = (TextView)findViewById(R.id.myText);
        vidView.setText("Prueba de Info");
    }

}
