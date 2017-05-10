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

public class CourseList  extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] courses;
    private final Integer[] imageId;

    public CourseList(Activity context, String[] courses, Integer[] imageId) {
        super(context, R.layout.list_course, courses);
        this.context = context;
        this.courses = courses;
        this.imageId = imageId;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_course, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(courses[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
