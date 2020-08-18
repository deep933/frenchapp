package com.example.frenchapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.logging.Level;

public class CustomAdapter extends ArrayAdapter<LessonsList> implements View.OnClickListener{

    private ArrayList<LessonsList> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView lesson_title;
        TextView lesson_type;

    }

    public CustomAdapter(ArrayList<LessonsList> data, Context context) {
        super(context, R.layout.list_lesson, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        LessonsList dataModel=(LessonsList) object;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_lesson, parent, false);
            viewHolder.lesson_title = (TextView) convertView.findViewById(R.id.lesson_title);
            viewHolder.lesson_type = (TextView) convertView.findViewById(R.id.lesson_type);




            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }



        viewHolder.lesson_title.setText(dataSet.get(position).getLessontitle());
        viewHolder.lesson_type.setText(dataSet.get(position).getType());

        // Return the completed view to render on screen
        return convertView;
    }
}