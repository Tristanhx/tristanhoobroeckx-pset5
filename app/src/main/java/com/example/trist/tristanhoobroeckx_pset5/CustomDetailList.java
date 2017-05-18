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
 * Created by trist on 5/8/2017.
 */

public class CustomDetailList extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> strings;
    private final ArrayList<TODO> itemList;

    public CustomDetailList(Activity context, ArrayList<String> strings, ArrayList<TODO> itemList){
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
        Log.d("lista", Integer.toString(itemList.get(position).getStatus()));

            if (itemList.get(position).getStatus() == 0){
                imageView.setImageResource(R.drawable.unchecked);
                Log.d("list!", "unchecked");
            }
            if (itemList.get(position).getStatus() == 1){
                imageView.setImageResource(R.drawable.checked);
                Log.d("list!", "checked");
            }

        return rowView;
    }
}
