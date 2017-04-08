package com.example.listview;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by taylo on 4/6/2017.
 */

public class listAdapter extends ArrayAdapter<BikeData> {

    Context context;
    List<BikeData> myBikes;
    String link;
    //SharedPreferences imagePref = PreferenceManager.getDefaultSharedPreferences(context);
    //String URL = imagePref.getString("listpref","http://www.tetonsoftware.com/bikes/bikes.json");




    public listAdapter(Context context, List<BikeData> data, String link ){
        super(context, R.layout.listview_row_layout, data);
        this.context = context;
        this.myBikes = data;
        this.link = link;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        LayoutInflater inflater = LayoutInflater.from(context);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.listview_row_layout,null);

            holder = new ViewHolder();
            holder.image =(ImageView) convertView.findViewById(R.id.imageView1);
            holder.price = (TextView) convertView.findViewById(R.id.Price);
            holder.description = (TextView) convertView.findViewById(R.id.Description);
            holder.model = (TextView) convertView.findViewById(R.id.Model);

            convertView.setTag(holder);
        }

        else{
            holder = (ViewHolder) convertView.getTag();
        }

        BikeData currentBike = myBikes.get(position);

//        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);
        DownloadImageTask dlImage = new DownloadImageTask("", holder.image);
        dlImage.execute(link + currentBike.picture);


        //TextView price = (TextView) tmp.findViewById(R.id.Price);
        holder.price.setText(currentBike.Price + "");

        //TextView description = (TextView) tmp.findViewById(R.id.Description);
        holder.description.setText(currentBike.Description);

       // TextView model = (TextView) tmp.findViewById(R.id.Model);
        holder.model.setText(currentBike.Model);


        return convertView;
    }


    public static class ViewHolder{
        public ImageView image;
        public TextView price;
        public TextView model;
        public TextView description;
    }





}
