package com.example.asanz.prueba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

/**
 * Created by asanz on 17/04/2017.
 */

public class GradesActivity extends BaseActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        final GradesDAO gradesDAO = new GradesDAO();
        gradesDAO.obtenerNotas(new ServerCallBack() {
            @Override
            public void onSuccess(JSONArray result) {
                //TODO Mostrar notas del alumno
            }
            @Override
            public void onError() {
                CharSequence text = "Imposible cargar los eventos";
                TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                textToast.setText(text);
                Toast toast = new Toast(context);
                toast.setDuration(duration);
                toast.setView(layout);
                toast.show();
            }
        }, true);
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
