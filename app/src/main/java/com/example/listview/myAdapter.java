package com.example.listview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kyle on 4/1/2017.
 */
public class myAdapter extends ArrayAdapter<String> {

    Integer[] imageid;
    Activity context;
    String[] data;
    private static LayoutInflater inflater = null;


    public myAdapter(Activity context, String[] data, Integer[] imageid){
        super(context, R.layout.listview_row_layout, data);
        this.context = context;
        this.data = data;
        this.imageid = imageid;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public String getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_row_layout, null, true);

        ImageView pic = (ImageView)view.findViewById(R.id.imageView1);
        TextView title = (TextView)view.findViewById(R.id.Model);
        TextView price = (TextView)view.findViewById(R.id.Price);
        TextView description = (TextView)view.findViewById(R.id.Description);

        pic.setImageResource(R.drawable.ic_launcher);
        title.setText(R.string.app_name);
        price.setText(R.string.hello_world);
        description.setText(R.string.tetonURL);


        return rowView;
    }
}
