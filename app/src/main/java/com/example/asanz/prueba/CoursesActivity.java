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
 * Created by asanz on 17/04/2017.
 */

public class CoursesActivity extends BaseActivity {
    private ListView listView;

    private JSONArray cursos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AppController global = ((AppController)getApplicationContext());
        String token = global.getToken();
        String idAlumno = global.getIdAlumno();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        final LinearLayout coursesLayout = (LinearLayout) this.findViewById(R.id.courseslayout);
        final CoursesDAO coursesDAO = new CoursesDAO();
        listView = (ListView) findViewById(R.id.CoursesList);

        registerForContextMenu(listView);

        coursesDAO.obtenerCursos(token, idAlumno, new ServerCallBack() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    cursos = result;
                    // Create an array to specify the fields we want to display in the list
                    String[] from = new String[]{"nombreCurso", "id"};

                    MatrixCursor cursosCursor = new MatrixCursor(
                            new String[]{"_id", "nombreCurso", "id"});
                    startManagingCursor(cursosCursor);
                    String response = null;
                    response = result.getString(0);
                    JSONObject respuesta = new JSONObject(response);
                    JSONArray array = null;
                    array = respuesta.getJSONArray("data");
                    cursos = respuesta.getJSONArray("data");
                    int tam = array.length();
                    for (int i = 0; i < tam; i++) {
                        String titulo = array.getJSONObject(i).getString("nombrePrograma");
                        String id = array.getJSONObject(i).getString("id");
                        cursosCursor.addRow(new Object[]{i, titulo, id});

                    }
                    // and an array of the fields we want to bind those fields to
                    int[] to = new int[]{R.id.nombreCurso,R.id.id,};

                    // Now create an array adapter and set it to display using our row
                    SimpleCursorAdapter curso =
                            new SimpleCursorAdapter(context, R.layout.row_cursos, cursosCursor,
                                    from, to);
                    listView.setAdapter(curso);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            listView.getItemAtPosition(position);
                            try{
                                String idMatricula = cursos.getJSONObject(position)
                                        .getString("id");
                                String idMatriculaPrograma = cursos.getJSONObject(position)
                                        .getString("idPrograma");
                                Intent intent = new Intent(context, SubjectActivity.class);
                                intent.putExtra("idMatricula", idMatricula);
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
                CharSequence text = "Imposible cargar los cursos";
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
