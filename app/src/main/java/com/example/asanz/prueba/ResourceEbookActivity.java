package com.example.asanz.prueba;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by asanz on 11/05/2017.
 */

public class ResourceEbookActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        final String idPasoAgenda = getIntent().getStringExtra("idPasoAgenda");
        final String titulo = getIntent().getStringExtra("titulo");
        final String idRecurso = getIntent().getStringExtra("idRecurso");
        final String tipoRecurso = getIntent().getStringExtra("tipoRecurso");
        AppController global = ((AppController) getApplicationContext());
        String token = global.getToken();
        String idAlumno = global.getIdAlumno();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_ebook);

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        final CoursesDAO coursesDAO = new CoursesDAO();
        TextView tituloView = (TextView) findViewById(R.id.titulo);
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
                    Log.d("Contenido", array.toString());
                    String contenido = "http://video.seas.es/util/le2/HIB7002/index.html";
                    //String contenido = array.getString("url");
                    //JSONArray content = new JSONArray(contenido);
                    //String url = content.getJSONObject(0).getString("url");
                    String url = "http://video.seas.es/util/le2/HIB7002/index.html";

                    String description = array.getString("descripcion");
                    description = android.text.Html.fromHtml(description).toString();
                    TextView textView =(TextView)findViewById(R.id.ebookLink);
                    textView.setClickable(true);
                    textView.setMovementMethod(LinkMovementMethod.getInstance());
                    String text = "<a href='"+url+"'>"+description+"</a>";
                    textView.setText(Html.fromHtml(text));

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