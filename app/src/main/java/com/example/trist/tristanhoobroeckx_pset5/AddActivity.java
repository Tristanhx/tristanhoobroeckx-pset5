package com.example.trist.tristanhoobroeckx_pset5;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    DataBaseHelper helper;
    Context context;
    ArrayList<String> todoList;
    CAT cat;
    EditText tableText;
    ListView CategoryListView;
    ArrayList<CAT> CategoryList;
    String currentTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        context = this;
        helper = DataBaseHelper.getInstance(context);

        tableText = (EditText) findViewById(R.id.edittxt);
    }

    public void addCategory(View view){
        String content = tableText.getText().toString();
        if(!content.equals("")){
            cat = new CAT(content);
            helper.CreateCAT(cat);
            tableText.getText().clear();
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);

        }
        else{
            Toast.makeText(this, "What did you need to do?", Toast.LENGTH_SHORT).show();
        }

    }
}
