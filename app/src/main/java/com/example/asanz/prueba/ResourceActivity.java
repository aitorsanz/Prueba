package com.example.asanz.prueba;

import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

public class ResourceActivity extends BaseActivity {

    private ListView listView;

    private JSONArray recursos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        final String idAsignatura = getIntent().getStringExtra("idAsignatura");
        final String idMatriculaPrograma = getIntent().getStringExtra("idMatriculaPrograma");
        AppController global = ((AppController)getApplicationContext());
        String token = global.getToken();
        String idAlumno = global.getIdAlumno();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        final CoursesDAO coursesDAO = new CoursesDAO();
        listView = (ListView) findViewById(R.id.ResourcesList);

        registerForContextMenu(listView);

        coursesDAO.obtenerRecursos(token, idAlumno, idAsignatura, new ServerCallBack() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    // Create an array to specify the fields we want to display in the list
                    String[] from = new String[]{"titulo", "id"};

                    MatrixCursor asignaturasCursor = new MatrixCursor(
                            new String[]{"_id", "titulo",  "id"});
                    startManagingCursor(asignaturasCursor);
                    String response = null;
                    response = result.getString(0);
                    JSONObject respuesta = new JSONObject(response);
                    JSONArray array = null;
                    array = respuesta.getJSONArray("data");
                    recursos = respuesta.getJSONArray("data");
                    int tam = array.length();
                    for (int i = 0; i < tam; i++) {
                        String titulo = array.getJSONObject(i).getString("titulo");
                        String nombreUnidad = array.getJSONObject(i).getString("nombreUnidad");
                        String id = array.getJSONObject(i).getString("id");
                        asignaturasCursor.addRow(new Object[]{i, nombreUnidad + "-" +titulo, id});

                    }
                    // and an array of the fields we want to bind those fields to
                    int[] to = new int[]{R.id.nombreRecurso,R.id.id,};

                    // Now create an array adapter and set it to display using our row
                    SimpleCursorAdapter curso =
                            new SimpleCursorAdapter(context, R.layout.row_recurso, asignaturasCursor,
                                    from, to);
                    listView.setAdapter(curso);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            listView.getItemAtPosition(position);
                            try{
                                String idPasoAgenda = recursos.getJSONObject(position)
                                        .getString("id");
                                String titulo = recursos.getJSONObject(position)
                                        .getString("titulo");
                                String idRecurso = recursos.getJSONObject(position)
                                        .getString("fkIdRecurso");
                                String tipoRecurso = recursos.getJSONObject(position)
                                        .getString("tipoRecurso");
                                int idtipoRecurso = Integer.parseInt(tipoRecurso);
                                Intent intent = new Intent();
                                switch (idtipoRecurso){
                                    case 10:
                                        intent = new Intent(context, ResourceTextoActivity.class);
                                        break;
                                    case 11:
                                        intent = new Intent(context, ResourceEjercicioActivity.class);
                                        break;
                                    case 12:
                                        intent = new Intent(context, ResourceVideoActivity.class);
                                        break;
                                    case 13:
                                        intent = new Intent(context, ResourceVideoActivity.class);
                                        break;
                                    case 14:
                                        intent = new Intent(context, ResourceTextoActivity.class);
                                        break;
                                    case 15:
                                        intent = new Intent(context, ResourceFeedbackActivity.class);
                                        break;
                                    case 16:
                                        intent = new Intent(context, BuildingActivity.class);
                                        break;
                                    case 17:
                                        intent = new Intent(context, ResourceEbookActivity.class);
                                        break;
                                    case 18:
                                        intent = new Intent(context, ResourceTestActivity.class);
                                        break;
                                    case 19:
                                        intent = new Intent(context, ResourceEjercicioActivity.class);
                                        break;
                                    default:
                                            break;
                                }
                                intent.putExtra("titulo", titulo);
                                intent.putExtra("idPasoAgenda", idPasoAgenda);
                                intent.putExtra("idRecurso", idRecurso);
                                intent.putExtra("tipoRecurso", tipoRecurso);
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.resource_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.debates:
                Intent intent = new Intent(this, BuildingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            case R.id.docentes:
                intent = new Intent(this, BuildingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            case R.id.temporizacion:
                intent = new Intent(this, BuildingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            case R.id.materiales:
                intent = new Intent(this, BuildingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            case R.id.salir:
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast,
                        (ViewGroup) findViewById(R.id.toast_layout_root));
                CharSequence text = "Saliendo";
                TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                textToast.setText(text);
                Toast toast = new Toast(context);
                toast.setDuration(duration);
                toast.setView(layout);
                toast.show();
                intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
