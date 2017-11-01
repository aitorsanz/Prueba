package com.example.asanz.prueba;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

/**
 * Created by aitor on 23/09/17.
 */

public class PostAdapter extends ArrayAdapter {
    public PostAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }
}
