package com.example.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by taylo on 4/6/2017.
 */

public class listAdapter extends ArrayAdapter<BikeData> {

    Context context;


    public listAdapter(Context context, List<BikeData> data ){
        super(context, R.layout.listview_row_layout,data);
        this.context = context;
    }

    @Override
    public View getView(int position, View counterView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View tmp = counterView;
        if(tmp == null){
            tmp = inflater.inflate(R.layout.listview_row_layout,parent,false);
        }


        return super.getView(position, counterView, parent);
    }
}
