package com.example.asanz.prueba;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
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

public class BandejaActivity extends BaseActivity {
    private static final int ELIMINAR = 1;

    TextView tv[];

    private ListView listView;

    private JSONArray mensajes;
    protected void onCreate(Bundle savedInstanceState) {
        AppController global = ((AppController)getApplicationContext());
        String token = global.getToken();
        String idAlumno = global.getIdAlumno();
        String bandeja = getIntent().getStringExtra("bandeja");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja);
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        final LinearLayout gradesLayout = (LinearLayout) this.findViewById(R.id.bandejalayout);
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final MessagesDAO messagesDAO = new MessagesDAO();
        listView = (ListView) findViewById(R.id.MessagesList);

        registerForContextMenu(listView);
        if(bandeja.equals("salida")){
            messagesDAO.obtenerMensajesSalida(token, idAlumno, new ServerCallBack() {
                @Override
                public void onSuccess(JSONArray result) {
                    try {
                        // Create an array to specify the fields we want to display in the list
                        String[] from = new String[]{"mensaje", "id"};

                        MatrixCursor mensajesCursor = new MatrixCursor(
                                new String[]{"_id", "mensaje", "id"});
                        String response = null;
                        response = result.getString(0);
                        JSONObject respuesta = new JSONObject(response);
                        JSONArray array = null;
                        array = respuesta.getJSONArray("data");
                        mensajes = respuesta.getJSONArray("data");
                        int tam = array.length();
                        for (int i = 0; i < tam; i++) {
                            String nombre = array.getJSONObject(i).getString("receiverName");
                            String asunto = array.getJSONObject(i).getString("asunto");
                            String fecha = array.getJSONObject(i).getString("fecha");
                            String id = array.getJSONObject(i).getString("idMensaje");
                            String mensaje = nombre + " - " +  asunto + " - " + fecha;
                            mensajesCursor.addRow(new Object[]{i, mensaje, id});
                        }
                        // and an array of the fields we want to bind those fields to
                        int[] to = new int[]{R.id.asuntoMensaje,R.id.id,};

                        // Now create an array adapter and set it to display using our row
                        SimpleCursorAdapter mensaje =
                                new SimpleCursorAdapter(context, R.layout.row_mensaje, mensajesCursor,
                                        from, to);
                        listView.setAdapter(mensaje);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onError() {
                    CharSequence text = "Imposible cargar los eventos";
                    TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                    textToast.setText(text);
                    Toast toast = new Toast(context);
                    toast.setDuration(duration);
                    toast.setView(layout);
                    toast.show();
                }
            }, true);

        }else{
            messagesDAO.obtenerMensajesEntrada(token, idAlumno, new ServerCallBack() {
                @Override
                public void onSuccess(JSONArray result) {
                    try {
                        // Create an array to specify the fields we want to display in the list
                        String[] from = new String[]{"mensaje", "id"};

                        MatrixCursor mensajesCursor = new MatrixCursor(
                                new String[]{"_id", "mensaje", "id"});

                        String response = null;
                        response = result.getString(0);
                        JSONObject respuesta = new JSONObject(response);
                        JSONArray array = null;
                        array = respuesta.getJSONArray("data");
                        mensajes = respuesta.getJSONArray("data");
                        int tam = array.length();
                        for (int i = 0; i < tam; i++) {
                            String nombre = array.getJSONObject(i).getString("senderName");
                            String apellidos = array.getJSONObject(i).getString("senderSurname");
                            String asunto = array.getJSONObject(i).getString("asunto");
                            String fecha = array.getJSONObject(i).getString("fecha");
                            String id = array.getJSONObject(i).getString("idMensaje");
                            String mensaje = nombre + " " + apellidos + " - " + asunto + " " + fecha;
                            mensajesCursor.addRow(new Object[]{i, mensaje, id});
                        }
                        // and an array of the fields we want to bind those fields to
                        int[] to = new int[]{R.id.asuntoMensaje,R.id.id,};

                        // Now create an array adapter and set it to display using our row
                        SimpleCursorAdapter mensaje =
                                new SimpleCursorAdapter(context, R.layout.row_mensaje, mensajesCursor,
                                        from, to);
                        listView.setAdapter(mensaje);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onError() {
                    CharSequence text = "Imposible cargar los eventos";
                    TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                    textToast.setText(text);
                    Toast toast = new Toast(context);
                    toast.setDuration(duration);
                    toast.setView(layout);
                    toast.show();
                }
            }, true);

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

    View.OnClickListener onclicklistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if(v == tv[0]){
                //do whatever you want....
            }
        }
    };
    View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            // TODO Auto-generated method stub
            if(v == tv[0]){
                //do whatever you want....
                registerForContextMenu(tv[0]);
            }
            return false;
        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, ELIMINAR, Menu.NONE, "Eliminar mensaje");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AppController global = ((AppController)getApplicationContext());
        String token = global.getToken();
        String idAlumno = global.getIdAlumno();
        final Intent intent = new Intent(this, MessagesActivity.class);
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        String bandeja = getIntent().getStringExtra("bandeja");
        switch(item.getItemId()) {
            case ELIMINAR:
                AdapterView.AdapterContextMenuInfo info =
                        (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                View row = info.targetView;
                String id = ((TextView) findViewById(R.id.id)).getText().toString();
                Log.d("String", id);
                final MessagesDAO messagesDAO = new MessagesDAO();
                messagesDAO.borrarMensaje(token, idAlumno, id,bandeja,  new ServerCallBack() {
                    @Override
                    public void onSuccess(JSONArray result) {
                         CharSequence text = "Mensaje eliminado";
                        TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                        textToast.setText(text);
                        Toast toast = new Toast(context);
                        toast.setDuration(duration);
                        toast.setView(layout);
                        toast.show();
                        startActivity(intent);
                    }
                    @Override
                    public void onError() {
                        CharSequence text = "Sucedió un problema al eliminar el mensaje";
                        TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                        textToast.setText(text);
                        Toast toast = new Toast(context);
                        toast.setDuration(duration);
                        toast.setView(layout);
                        toast.show();
                    }
                }, true);
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
