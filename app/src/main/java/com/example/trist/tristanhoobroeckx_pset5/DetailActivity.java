package com.example.trist.tristanhoobroeckx_pset5;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

        detailListView.setOnItemClickListener(new DetailActivity.ShortClickDetail());
        detailListView.setOnItemLongClickListener(new DetailActivity.LongClickDetail());

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
            Log.d("hier", "aangeroepen");

        }
        else{
            Toast.makeText(this, "What did you need to do?", Toast.LENGTH_SHORT).show();
        }

    }

    public void setDetailList(String cat_name){
        itemList = helper.getAllTODObyCAT(cat_name);
        Log.d("gogo", Integer.toString(itemList.size()));
        for (TODO todo : itemList){
            Log.d("gogo", todo.getNote());
            Log.d("gogo", Integer.toString(todo.getStatus()));
        }
        ArrayList<String> strings = makeTodoList(itemList);

        if (strings != null){
            CustomDetailList adapter = new CustomDetailList(DetailActivity.this, strings, itemList);
            assert itemList != null;
            assert imageList != null;
            Log.d("check!", "voor adapter");
            detailListView.setAdapter(adapter);

        }
    }

    public ArrayList<String> makeTodoList(ArrayList<TODO> TODOList){
        todoList = new ArrayList<>();
        for (TODO todo : TODOList){
            todoList.add(todo.getNote());
        }
        Log.d("check!", todoList.toString());
        return todoList;
    }

    private class ShortClickDetail implements AdapterView.OnItemClickListener {
        String selectedFromList;

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            itemList = helper.getAllTODObyCAT(cat_name);
            selectedFromList = (String) detailListView.getItemAtPosition(position);

            Log.d("click!", "you clicked "+selectedFromList);

            for (TODO todos : itemList){
                if (todos.getNote().equals(selectedFromList)){
                    if (todos.getStatus() == 0){
                        todos.setStatus(1);
                        Log.d("lista!", "This was false");
                        Log.d("compare", Long.toString(todos.getID()));
                        int i = helper.Update(todos);
                        Log.d("gogo", Integer.toString(i));
                        break;
                    }
                    if (todos.getStatus() == 1){
                        todos.setStatus(0);
                        Log.d("lista!", "This was true");
                        helper.Update(todos);
                        break;
                    }
                }
            }
            setDetailList(cat_name);
        }
    }

    private class LongClickDetail implements AdapterView.OnItemLongClickListener{
        String selectedFromList;

        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
            itemList = helper.getAllTODObyCAT(cat_name);
            selectedFromList = (String) detailListView.getItemAtPosition(position);

            Log.d("click!", "you longclicked"+selectedFromList);

            for (TODO todos : itemList){
                if (todos.getNote().equals(selectedFromList)){
                    helper.DeleteTODOCAT(todos.getID());
                    helper.DeleteTODO(todos.getID());
                }
            }
            setDetailList(cat_name);
            return true;
        }
    }

    public void ReturnToMain(View view){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        finish();
    }
}
