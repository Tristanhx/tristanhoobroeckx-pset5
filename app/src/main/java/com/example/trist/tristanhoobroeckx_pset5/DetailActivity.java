package com.example.trist.tristanhoobroeckx_pset5;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    DataBaseHelper helper;
    Context context;
    ArrayList<String> todoList;
    ArrayList<Integer> imageList;
    ITEM item;
    EditText detailText;
    EditText tableText;
    ListView tableListView;
    ListView detailListView;
    ArrayList<ITEM> itemList;
    String currentTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        context = this;
        helper = DataBaseHelper.getInstance(context);

        detailText = (EditText) findViewById(R.id.edittxt);
        detailListView = (ListView) findViewById(R.id.detailslist);
        setDetailList();
    }

    public void setDetailList(){
        itemList = helper.Read(currentTable);
        Log.d("list!", Integer.toString(itemList.size()));
        for (ITEM items : itemList){
            Log.d("list!", items.getItem());
            Log.d("list!", items.getDone());
        }
        ArrayList<String> strings = makeTodoList(itemList);

        if (strings != null){
            CustomDetailList adapter = new CustomDetailList(MainActivity.this, strings, itemList);
            assert itemList != null;
            assert imageList != null;
            Log.d("check!", "voor adapter");
            tableListView.setAdapter(adapter);

        }
    }
}
