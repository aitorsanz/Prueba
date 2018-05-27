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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by asanz on 17/05/2017.
 */

public class SubjectActivity extends BaseActivity {

    private ListView listView;

    private JSONArray asignaturas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        final String idMatricula = getIntent().getStringExtra("idMatricula");
        final String idMatriculaPrograma = getIntent().getStringExtra("idMatriculaPrograma");
        AppController global = ((AppController)getApplicationContext());
        String token = global.getToken();
        String idAlumno = global.getIdAlumno();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        final CoursesDAO coursesDAO = new CoursesDAO();
        listView = (ListView) findViewById(R.id.SubjectsList);

        registerForContextMenu(listView);

        coursesDAO.obtenerAsignaturas(token, idAlumno, idMatriculaPrograma, new ServerCallBack() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    // Create an array to specify the fields we want to display in the list
                    String[] from = new String[]{"nombreAsignatura", "id"};

                    MatrixCursor asignaturasCursor = new MatrixCursor(
                            new String[]{"_id", "nombreAsignatura", "id"});
                    startManagingCursor(asignaturasCursor);
                    String response = null;
                    response = result.getString(0);
                    JSONObject respuesta = new JSONObject(response);
                    JSONArray array = null;
                    array = respuesta.getJSONArray("data");
                    asignaturas = respuesta.getJSONArray("data");
                    int tam = array.length();
                    for (int i = 0; i < tam; i++) {
                        String titulo = array.getJSONObject(i).getString("nombre");
                        String id = array.getJSONObject(i).getString("id");
                        asignaturasCursor.addRow(new Object[]{i, titulo, id});

                    }
                    // and an array of the fields we want to bind those fields to
                    int[] to = new int[]{R.id.nombreAsignatura,R.id.id,};

                    // Now create an array adapter and set it to display using our row
                    SimpleCursorAdapter curso =
                            new SimpleCursorAdapter(context, R.layout.row_asignaturas, asignaturasCursor,
                                    from, to);
                    listView.setAdapter(curso);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            listView.getItemAtPosition(position);
                            try{
                                String idAsignatura = asignaturas.getJSONObject(position)
                                        .getString("id");
                                String idMatriculaPrograma = asignaturas.getJSONObject(position)
                                        .getString("fkIdMatriculaPrograma");
                                Intent intent = new Intent(context, ResourceActivity.class);
                                intent.putExtra("idAsignatura", idAsignatura);
                                intent.putExtra("idMatriculaPrograma", idMatriculaPrograma);
                                startActivity(intent);
                            } catch (Exception e){
                                Log.d("Error:", e.toString());
                            }

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError() {
                CharSequence text = "Imposible cargar las asignaturas";
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
