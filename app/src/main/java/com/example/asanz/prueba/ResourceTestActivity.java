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
import android.widget.CheckBox;
import android.widget.LinearLayout;
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

public class ResourceTestActivity extends BaseActivity {

    private ListView listView;

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
        setContentView(R.layout.activity_resource_test);

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        final CoursesDAO coursesDAO = new CoursesDAO();
        listView = (ListView) findViewById(R.id.PreguntasList);
        final LinearLayout checkboxLayout = (LinearLayout)findViewById(R.id.testlayout);

        coursesDAO.obtenerDetalleRecurso(token, idAlumno, idRecurso, tipoRecurso, new ServerCallBack() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    String[] from = new String[]{"enunciado", "id"};

                    MatrixCursor preguntasCursor = new MatrixCursor(
                            new String[]{"_id", "enunciado", "id"});

                    String response = null;
                    response = result.getString(0);
                    JSONObject respuesta = new JSONObject(response);
                    Log.d("resp", respuesta.toString());
                    JSONArray array = null;
                    array = respuesta.getJSONArray("data");
                    int tam = array.length();
                    String preguntaAnterior = "";
                    String enunciado = "";
                    CheckBox[] respuestas = new CheckBox[tam];
                    for (int i = 0; i < tam; i++) {
                        String pregunta = array.getJSONObject(i).getString("idPregunta");
                        if(pregunta.equals(preguntaAnterior)){
                            if(i == 0){
                                enunciado = array.getJSONObject(i).getString("enunciadoPregunta");
                                enunciado = android.text.Html.fromHtml(enunciado).toString();
                                preguntasCursor.addRow(new Object[]{i, enunciado, pregunta});
                            }

                        }else{
                            preguntasCursor.addRow(new Object[]{i, enunciado, preguntaAnterior});

                        }
                        CheckBox cb = new CheckBox(context);
                        cb.setText(enunciado);


                        respuestas[i]=cb;
                        checkboxLayout.addView(cb);
                        enunciado = array.getJSONObject(i).getString("enunciadoPregunta");
                        enunciado = android.text.Html.fromHtml(enunciado).toString();
                        preguntaAnterior = array.getJSONObject(i).getString("idPregunta");

                    }
                    int[] to = new int[]{R.id.enunciado,R.id.id,};

                    // Now create an array adapter and set it to display using our row
                    SimpleCursorAdapter mensaje =
                            new SimpleCursorAdapter(context, R.layout.row_tests, preguntasCursor,
                                    from, to);
                    listView.setAdapter(mensaje);
                    //Log.d("array", respuestas.toString());

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
