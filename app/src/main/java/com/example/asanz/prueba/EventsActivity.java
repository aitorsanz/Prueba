package com.example.asanz.prueba;

import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by asanz on 17/04/2017.
 */

public class EventsActivity extends BaseActivity {


    private ListView listView;
    private JSONArray eventos;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        final String idAsignatura = getIntent().getStringExtra("idAsignatura");
        final String idMatriculaPrograma = getIntent().getStringExtra("idMatriculaPrograma");
        AppController global = ((AppController)getApplicationContext());
        String token = global.getToken();
        String idAlumno = global.getIdAlumno();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        final CoursesDAO coursesDAO = new CoursesDAO();
        listView = (ListView) findViewById(R.id.TemporizacionesList);
        coursesDAO.obtenerTemporizaciones(token, idAlumno, idAsignatura, new ServerCallBack() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    // Create an array to specify the fields we want to display in the list
                    String[] from = new String[]{"temp", "id"};

                    MatrixCursor mensajesCursor = new MatrixCursor(
                            new String[]{"_id", "temp", "id"});
                    String response = null;
                    response = result.getString(0);
                    JSONObject respuesta = new JSONObject(response);
                    JSONArray array = null;
                    array = respuesta.getJSONArray("data");
                    eventos = respuesta.getJSONArray("data");
                    int tam = array.length();
                    for (int i = 0; i < tam; i++) {
                        String texto = array.getJSONObject(i).getString("texto");
                        String fechaInicio = array.getJSONObject(i).getString("fechaInicio");
                        String fechaFin = array.getJSONObject(i).getString("fechaFin");
                        String id = array.getJSONObject(i).getString("id");
                        String temp = texto + " - " +  fechaInicio + " - " + fechaFin;
                        mensajesCursor.addRow(new Object[]{i, temp, id});
                    }
                    // and an array of the fields we want to bind those fields to
                    int[] to = new int[]{R.id.temp,R.id.id,};

                    // Now create an array adapter and set it to display using our row
                    SimpleCursorAdapter mensaje =
                            new SimpleCursorAdapter(context, R.layout.row_temporizacion, mensajesCursor,
                                    from, to);
                    listView.setAdapter(mensaje);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("resultado", result.toString());
            }
            @Override
            public void onError() {
                CharSequence text = "Imposible cargar las temporizaciones";
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
    public void onBackPressed()
    {
        super.onBackPressed();
        final String idAsignatura = getIntent().getStringExtra("idAsignatura");
        Intent intent = new Intent(EventsActivity.this, ResourceActivity.class);
        intent.putExtra("idAsignatura", idAsignatura);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
