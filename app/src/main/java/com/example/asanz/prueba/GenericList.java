package com.example.asanz.prueba;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by asanz on 10/05/2017.
 */

public class GenericList  extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] list;
    private final Integer[] imageId;

    public GenericList(Activity context, String[] list, Integer[] imageId) {
        super(context, R.layout.list_generic, list);
        this.context = context;
        this.list = list;
        this.imageId = imageId;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_generic, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(list[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
