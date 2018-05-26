package com.example.asanz.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;



/**
 * Created by asanz on 17/04/2017.
 */

public class MessagesActivity extends BaseActivity {

    ListView messages;
    String[] mensajes = {
            "Bandeja de Entrada",
            "Bandeja de Salida"
    } ;
    Integer[] imageId = {
            R.drawable.entrada,
            R.drawable.mensajes
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        final Intent intent = new Intent(this, BandejaActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        GenericList coursesList = new GenericList(MessagesActivity.this, mensajes, imageId);
        messages = (ListView)findViewById(R.id.MessagesList);
        messages.setAdapter(coursesList);
        messages.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        intent.putExtra("bandeja", "entrada");
                        startActivity(intent);
                        break;
                    case 1:
                        intent.putExtra("bandeja", "salida");
                        startActivity(intent);
                        break;
                }

            }
        });


    }

    public void clickCrearMensaje (View view) {
        startActivity(new Intent(getApplicationContext(),CreateMessageActivity.class));
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
