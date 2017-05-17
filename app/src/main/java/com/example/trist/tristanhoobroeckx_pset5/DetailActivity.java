package com.example.trist.tristanhoobroeckx_pset5;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    DataBaseHelper helper;
    Context context;
    ArrayList<String> todoList;
    ArrayList<Integer> imageList;
    TODO todo;
    CAT catobject;
    EditText detailText;
    ListView tableListView;
    ListView detailListView;
    ArrayList<TODO> itemList;
    TextView title;
    String cat_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        context = this;
        helper = DataBaseHelper.getInstance(context);

        detailText = (EditText) findViewById(R.id.edittxt);
        detailListView = (ListView) findViewById(R.id.detailslist);
        title = (TextView) findViewById(R.id.title);

        Bundle extras = getIntent().getExtras();
        cat_name = extras.getSerializable("selected").toString();
        catobject = (CAT) extras.getSerializable("cat_object");
        title.setText(cat_name);
        setDetailList(cat_name);
    }

    public void addTODO(View view){
        String content = detailText.getText().toString();
        if(!content.equals("")){
            todo = new TODO(content, 0);
            helper.CreateTODO(todo, catobject.getID());
            detailText.getText().clear();
            setDetailList(cat_name);

        }
        else{
            Toast.makeText(this, "What did you need to do?", Toast.LENGTH_SHORT).show();
        }

    }

    public void setDetailList(String cat_name){
        itemList = helper.getAllTODObyCAT(cat_name);
        Log.d("list!", Integer.toString(itemList.size()));
        for (TODO todo : itemList){
            Log.d("list!", todo.getNote());
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

    public ArrayList<String> makeTodoList(ArrayList<TODO> TODOList){
        todoList = new ArrayList<>();
        for (CAT cats : CATList){
            todoList.add(cats.getCATname());
        }
        Log.d("check!", todoList.toString());
        return todoList;
    }

    public void ReturnToMain(View view){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        finish();
    }
}
