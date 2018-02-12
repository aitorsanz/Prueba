package com.example.asanz.prueba;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;
import java.io.IOException;

/**
 * Created by asanz on 10/05/2017.
 */

public class ResourceVideoActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_video);
        try {
            this.showVideo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


// TODO: 25/05/2017 Conexión con campus para obtener recurso video
    /**
     * Muestra un tipo de recurso Video
     * */
    public void showVideo() throws IOException {
        VideoView vidView = (VideoView)findViewById(R.id.myVideo);
        String vidAddress = "http://video.seas.es/videos/hiberus/H0001/ModuloI/VideoTutorial2_InstalarunModulo.mp4";
        Uri vidUri = Uri.parse(vidAddress);
        vidView.setVideoURI(vidUri);
        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(vidView);
        vidView.setMediaController(vidControl);
        vidView.start();
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
