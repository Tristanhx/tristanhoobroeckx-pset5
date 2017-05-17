package com.example.trist.tristanhoobroeckx_pset5;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by trist on 5/16/2017.
 */

public class CustomCategoryList extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> strings;
    private final ArrayList<CAT> itemList;

    public CustomCategoryList(Activity context, ArrayList<String> strings, ArrayList<CAT> itemList){
        super(context, R.layout.list_single, strings);
        Log.d("check!", "ik ben in CustomList");
        this.context = context;
        this.strings = strings;
        this.itemList = itemList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        Log.d("check!", "boven aan");

        Log.d("check!", "ik ben in getView");
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(strings.get(position));

//        imageView.setImageResource(imageID.get(position));


        imageView.setImageResource(R.drawable.arrowright);

        return rowView;
    }
}
