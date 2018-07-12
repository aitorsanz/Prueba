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

public class AdditionalsMaterialsActivity extends BaseActivity {


    private ListView listView;
    private JSONArray materiales;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        final String idAsignatura = getIntent().getStringExtra("idAsignatura");
        final String idMatriculaPrograma = getIntent().getStringExtra("idMatriculaPrograma");
        AppController global = ((AppController)getApplicationContext());
        String token = global.getToken();
        String idAlumno = global.getIdAlumno();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtionals_materials);

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        final CoursesDAO coursesDAO = new CoursesDAO();
        listView = (ListView) findViewById(R.id.MaterialesList);

        coursesDAO.obtenerMateriales(token, idAsignatura, new ServerCallBack() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    // Create an array to specify the fields we want to display in the list
                    String[] from = new String[]{"nombre", "id"};

                    MatrixCursor mensajesCursor = new MatrixCursor(
                            new String[]{"_id", "nombre", "id"});
                    String response = null;
                    response = result.getString(0);
                    JSONObject respuesta = new JSONObject(response);
                    JSONArray array = null;
                    array = respuesta.getJSONArray("data");
                    materiales = respuesta.getJSONArray("data");
                    int tam = array.length();
                    for (int i = 0; i < tam; i++) {
                        String texto = array.getJSONObject(i).getString("nombre");

                        String id = array.getJSONObject(i).getString("id");
                        String material = texto;
                        mensajesCursor.addRow(new Object[]{i, material, id});
                    }
                    // and an array of the fields we want to bind those fields to
                    int[] to = new int[]{R.id.nombre,R.id.id,};

                    // Now create an array adapter and set it to display using our row
                    SimpleCursorAdapter mensaje =
                            new SimpleCursorAdapter(context, R.layout.row_materiales, mensajesCursor,
                                    from, to);
                    listView.setAdapter(mensaje);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("resultado", result.toString());
            }
            @Override
            public void onError() {
                CharSequence text = "Imposible cargar los materiales";
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
        Intent intent = new Intent(AdditionalsMaterialsActivity.this, ResourceActivity.class);
        intent.putExtra("idAsignatura", idAsignatura);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
