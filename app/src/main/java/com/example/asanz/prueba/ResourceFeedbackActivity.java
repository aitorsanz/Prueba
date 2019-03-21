package com.example.asanz.prueba;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by asanz on 11/05/2017.
 */

public class ResourceFeedbackActivity extends BaseActivity {
    ImageView ivAttachment;
    Button bUpload;
    TextView tvFileName;
    ProgressDialog dialog;
    Context context;
    private static final int PICK_FILE_REQUEST = 1;
    private static final String TAG = ResourceFeedbackActivity.class.getSimpleName();
    private String selectedFilePath;

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
        setContentView(R.layout.activity_resource_feedback);

        context = getApplicationContext();
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
                    Log.d("Result", result.toString());
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
        ivAttachment = (ImageView) findViewById(R.id.ivAttachment);
        bUpload = (Button) findViewById(R.id.b_upload);
        tvFileName = (TextView) findViewById(R.id.tv_file_name);
        ivAttachment.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                showFileChooser();
            }
        });
        bUpload.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //on upload button Click
                if(selectedFilePath != null){
                    //dialog = ProgressDialog.show(context,"","Uploading File...",true);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
                            uploadFile(selectedFilePath, idRecurso);
                        }
                    }).start();
                }else{
                    Toast.makeText(context,"Please choose a File First",Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    private void showFileChooser() {
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("*/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE_REQUEST);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == PICK_FILE_REQUEST){
                if(data == null){
                    //no data present
                    return;
                }


                Uri selectedFileUri = data.getData();

                selectedFilePath = FilePath.getPath(this,selectedFileUri);
                Log.i(TAG,"Selected File Path:" + selectedFilePath);

                if(selectedFilePath != null && !selectedFilePath.equals("")){
                    tvFileName.setText(selectedFilePath);
                }else{
                    Toast.makeText(this,"Cannot upload file to server",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    //android upload file to server
    public int uploadFile(final String selectedFilePath, final String idRecurso){
        requestRead();
        int serverResponseCode = 0;
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer = new byte[0];
        int maxBufferSize = 1 * 1024 * 1024;

        File selectedFile = new File(selectedFilePath);

        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length-1];

        java.io.File file = new java.io.File( Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName);
        //try {
            //file.createNewFile();
        //} catch (IOException e) {
          //  e.printStackTrace();
        //}

        File f = new File(selectedFilePath);
        Log.d("nombre", fileName);
        Log.d("file", Boolean.toString(f.isFile()));
        Log.d("TAMF", Long.toString(f.length()));
        Log.d("TAM", Long.toString(file.length()));
        Log.d("TAM2", Long.toString(selectedFile.length()));
        if (!f.isFile()){
            //dialog.dismiss();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvFileName.setText("Source File Doesn't Exist: " + selectedFilePath);
                }
            });
            return 0;
        }else{

            byte[] bytesArray = new byte[(int) selectedFile.length()];
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }
                Log.d("Bu", buffer.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            //String data = Base64.encodeToString(bytesArray, Base64.DEFAULT);
            String data = Base64.encodeToString(buffer, Base64.DEFAULT);
            Log.d("Data", data);
            final CoursesDAO coursesDAO = new CoursesDAO();
            AppController global = ((AppController) getApplicationContext());
            final int duration = Toast.LENGTH_SHORT;
            LayoutInflater inflater = getLayoutInflater();
            final View layout = inflater.inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.toast_layout_root));

            String token = global.getToken();
            String idAlumno = global.getIdAlumno();

            coursesDAO.enviarFeedback(token, idAlumno, idRecurso, fileName, data, new ServerCallBack() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onSuccess(JSONArray result) {
                    Log.d("resultado", result.toString());
                    CharSequence text = "Entrega realizada";
                    TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                    textToast.setText(text);
                    Toast toast = new Toast(context);
                    toast.setDuration(duration);
                    toast.setView(layout);
                    toast.show();
                }

                @Override
                public void onError() {
                    CharSequence text = "Imposible realizar entrega";
                    TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                    textToast.setText(text);
                    Toast toast = new Toast(context);
                    toast.setDuration(duration);
                    toast.setView(layout);
                    toast.show();
                }
            }, true);

            //dialog.dismiss();
            return serverResponseCode;
        }

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
    public void requestRead(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

            }
        }
    }
}