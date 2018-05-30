package com.example.asanz.prueba;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * Created by asanz on 11/05/2017.
 */

public class ResourceEjercicioActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        final String idPasoAgenda = getIntent().getStringExtra("idPasoAgenda");
        final String titulo = getIntent().getStringExtra("titulo");
        final String idRecurso = getIntent().getStringExtra("idRecurso");
        final String tipoRecurso = getIntent().getStringExtra("tipoRecurso");
        AppController global = ((AppController) getApplicationContext());
        String token = global.getToken();
        String idAlumno = global.getIdAlumno();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_ejercicio);

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        final CoursesDAO coursesDAO = new CoursesDAO();
        TextView tituloView = (TextView) findViewById(R.id.titulo);
        tituloView.setText(titulo);

        coursesDAO.obtenerDetalleRecurso(token, idAlumno, idRecurso, tipoRecurso, new ServerCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    String response = null;
                    response = result.getString(0);
                    JSONObject respuesta = new JSONObject(response);
                    JSONObject array = null;
                    array = respuesta.getJSONObject("data");
                    String file = array.getString("file");
                    String nombre = array.getString("nombre");
                    String extension = array.getString("extension");
                    int tam = Integer.parseInt(array.getString("tamano"));
                    byte[] data = Base64.decode(file, Base64.DEFAULT);
                    showEjercicio(data, tam, nombre+"."+extension, extension);
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

    /**
     * Muestra un tipo de recurso Ejercicio
     */
    public void showEjercicio(final byte[] data, final int tam, final String fileName, final String extension) {
        request();
        ImageView img = (ImageView)findViewById(R.id.ejercicio);
        //img = (ImageView)findViewById(R.id.ejercicio);

        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String type = "application/pdf";
                if(extension.equals("jpg") || extension.equals("png") || extension.equals("gif") || extension.equals("jpeg")){
                    type = "image/*";
                }else if(extension.equals("mp3")  || extension.equals("wav") ){
                    type = "audio/*";
                }

                OutputStream outputStream = null;
                java.io.File futureStudioIconFile = new java.io.File(Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        + "/" + fileName);
                try {
                    outputStream = new FileOutputStream(futureStudioIconFile);
                    outputStream.write(data, 0, tam);
                    outputStream.flush();
                    MimeTypeMap map = MimeTypeMap.getSingleton();
                    java.io.File file = new java.io.File(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            + "/" + fileName);
                    String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
                    String tipo = map.getMimeTypeFromExtension(ext);
                    if(tipo == null){
                        tipo = "*/*";
                    }
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(file), tipo);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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

    public void request(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);

            }
        }
    }
}