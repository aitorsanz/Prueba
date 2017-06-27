package com.example.asanz.prueba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.TitlePageIndicator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asanz on 17/04/2017.
 */

public class ResourceActivity extends AppCompatActivity {
    ListView resources;
    String[] recurso = {
            "Texto",
            "SCORM",
            "Ejercicio",
            "Video",
            "Ebook"
    } ;
    Integer[] imageId = {
            R.drawable.lupa,
            R.drawable.lupa,
            R.drawable.lupa,
            R.drawable.lupa,
            R.drawable.lupa
    };

    /*
    Cliente para la conexión al servidor
     */
    HttpURLConnection con;
    private ViewPager pager;
    // TODO: 25/05/2017 Conexión con campues para obtener listado de recursos
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
        // Instantiate a ViewPager
        this.pager = (ViewPager) this.findViewById(R.id.pager);

        // Create an adapter with the fragments we show on the ViewPager
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(
                getSupportFragmentManager());
        adapter.addFragment(ScreenSlidePageFragment.newInstance(getResources()
                .getColor(R.color.colorPrimary), 0));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(getResources()
                .getColor(R.color.colorAccent), 1));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(getResources()
                .getColor(R.color.colorPrimaryDark), 2));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(getResources()
                .getColor(R.color.colorAccent), 3));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(getResources()
                .getColor(R.color.colorPrimary), 4));
        this.pager.setAdapter(adapter);

        // Bind the title indicator to the adapter
        TitlePageIndicator titleIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
        titleIndicator.setViewPager(pager);


        //Se carga la pestaña de recursos
        /*GenericList resourcesList = new GenericList(ResourceActivity.this, recurso, imageId);
        resources = (ListView)findViewById(R.id.ResourcesList);
        resources.setAdapter(resourcesList);
        resources.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getApplicationContext(), ResourceTextoActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getApplicationContext(), ResourceScormActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(), ResourceEjercicioActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getApplicationContext(), ResourceVideoActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getApplicationContext(), ResourceEbookActivity.class));
                        break;
                }
            }
        });*/
        //Pestaña de debates

        //Pestaña de docentes

        //Pestaña de materiales

        //Pestaña de temporizaciones

        // Obtener la instancia de la lista
        /*
        courses = (ListView) findViewById(R.id.EventsList);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        URL url = null;
                        try {
                            url = new URL("http://wsmoodle.local/index/courses");
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        HttpURLConnection urlConnection = null;
                        List<String> cursos = null;
                        try {
                            urlConnection = (HttpURLConnection) url.openConnection();
                            boolean respuesta = urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK;
                            if (respuesta) {
                                BufferedReader reader = new BufferedReader(new
                                        InputStreamReader(urlConnection.getInputStream()));
                                reader.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            urlConnection.disconnect();
                        }
                        InputStream in = null;
                        try {
                            in = new BufferedInputStream(urlConnection.getInputStream());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            if(in == null){
                                cursos = null;
                            }else{
                                cursos = readJsonStream(in);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                    getBaseContext(),
                                    android.R.layout.simple_list_item_1, cursos);

                            // Relacionar adaptador a la lista
                            courses.setAdapter(adapter);
                            // Acciones a realizar con el flujo de datos
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();*/
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /*
  Método encargado de coordinar la conversión final de un flujo
  con formato JSON
   */
    public List<String> readJsonStream(InputStream in) throws IOException {

        // Nueva instancia de un lector JSON
        JsonReader reader = new JsonReader(
                new InputStreamReader(in, "UTF-8"));

        try {
            return readCommentsArray(reader);
        } finally {
            reader.close();
        }
    }

    /*
    Este método lee cada elemento al interior de un array JSON
     */
    public List<String> readCommentsArray(JsonReader reader) throws IOException {
        List<String> messages = new ArrayList<>();

        // Se dirige al corchete de apertura del arreglo
        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readMessage(reader));
        }

        // Se dirige al corchete de cierre
        reader.endArray();
        return messages;
    }

    /*
    Lee los atributos de cada objeto
     */
    public String readMessage(JsonReader reader) throws IOException {

        // Cuerpo del comentario
        String body = null;

        // Se dirige a la llave de apertura del objeto
        reader.beginObject();

        while (reader.hasNext()) {

            // Se obtiene el nombre del atributo
            String name = reader.nextName();

            if (name.equals("body")) {
                body = reader.nextString();
            } else {
                reader.skipValue();
            }
        }

        // Se dirige a la llave de cierre del objeto
        reader.endObject();
        return body;
    }
}
