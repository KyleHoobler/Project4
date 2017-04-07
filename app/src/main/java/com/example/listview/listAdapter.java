package com.example.listview;

import android.content.Context;
import android.content.SharedPreferences;
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
    SharedPreferences imagePref;
    String URL = imagePref.getString("listpref","http://www.tetonsoftware.com/bikes/bikes.json");




    public listAdapter(Context context, List<BikeData> data ){
        super(context, R.layout.listview_row_layout, data);
        this.context = context;
        this.myBikes = data;
    }

    @Override
    public View getView(int position, View counterView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View tmp = counterView;
        if(tmp == null){
            tmp = inflater.inflate(R.layout.listview_row_layout,parent,false);
        }

        BikeData currentBike = myBikes.get(position);

        ImageView imageView = (ImageView) tmp.findViewById(R.id.imageView1);
        DownloadImageTask dlImage = new DownloadImageTask(currentBike.Link, imageView);
        //dlImage.execute(URL += "/" + currentBike.picture);



        TextView price = (TextView) tmp.findViewById(R.id.Price);
        price.setText(currentBike.Price + "");

        TextView description = (TextView) tmp.findViewById(R.id.Description);
        description.setText(currentBike.Description);

        TextView model = (TextView) tmp.findViewById(R.id.Model);
        model.setText(currentBike.Model);


        return tmp;
    }




}
