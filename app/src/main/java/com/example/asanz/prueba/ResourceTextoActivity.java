package com.example.asanz.prueba;

import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by asanz on 11/05/2017.
 */

public class ResourceTextoActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        final String idPasoAgenda = getIntent().getStringExtra("idPasoAgenda");
        final String titulo = getIntent().getStringExtra("titulo");
        final String idRecurso = getIntent().getStringExtra("idRecurso");
        final String tipoRecurso = getIntent().getStringExtra("tipoRecurso");
        AppController global = ((AppController)getApplicationContext());
        String token = global.getToken();
        String idAlumno = global.getIdAlumno();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_texto);

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        final CoursesDAO coursesDAO = new CoursesDAO();
        TextView tituloView = (TextView)findViewById(R.id.titulo);
        tituloView.setText(titulo);

        coursesDAO.obtenerDetalleRecurso(token, idAlumno, idRecurso, tipoRecurso, new ServerCallBack() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    String response = null;
                    response = result.getString(0);
                    JSONObject respuesta = new JSONObject(response);
                    JSONObject array = null;
                    array = respuesta.getJSONObject("data");
                    if(tipoRecurso.equals("10")){
                        Log.d("respuesta", array.toString());
                        String contenido = array.getString("contenido");
                        contenido = android.text.Html.fromHtml(contenido).toString();
                        String descripcion = array.getString("descripcion");
                        descripcion = android.text.Html.fromHtml(descripcion).toString();

                        TextView contenidoView = (TextView)findViewById(R.id.contenido);
                        TextView descripcionView = (TextView)findViewById(R.id.descripcion);
                        contenidoView.setText(contenido);
                        descripcionView.setText(descripcion);
                    }/*else if(tipoRecurso.equals("14")){
                        Log.d("array", array.toString());
                        String contenido = array.getString("url");
                        JSONArray content = new JSONArray(contenido);
                        Log.d("array", content.toString());
                        contenido = content.getJSONObject(0).getString("description") + "  " + content.getJSONObject(0).getString("url");
                        //contenido = android.text.Html.fromHtml(contenido).toString();
                        String descripcion = array.getString("descripcion");
                        descripcion = android.text.Html.fromHtml(descripcion).toString();

                        TextView contenidoView = (TextView)findViewById(R.id.contenido);
                        TextView descripcionView = (TextView)findViewById(R.id.descripcion);
                        contenidoView.setText(contenido);
                        descripcionView.setText(descripcion);
                    }*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError() {
                CharSequence text = "Imposible cargar el recurso";
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
