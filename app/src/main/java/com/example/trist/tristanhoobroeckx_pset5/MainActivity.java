package com.example.trist.tristanhoobroeckx_pset5;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);

        // Create new database
        context = this;
        helper = DataBaseHelper.getInstance(context);

        itemList = helper.Read(currentTable);
        for (ITEM items : itemList){
            Log.d("check!", items.getItem());
            Log.d("check!", items.getDone());
            Log.d("check!", items.getID().toString());
        }

        if (itemList.size() == 0){
            addDefaultItems();
        }
        else{
            Log.d("list!", "List is not empty!");
        }
        detailText = (EditText) findViewById(R.id.edittxt);
        tableText = (EditText) findViewById(R.id.edittxt2);
        tableListView = (ListView) findViewById(R.id.list);
        detailListView = (ListView) findViewById(R.id.detailslist);



        setDetailList();





        detailListView.setOnItemClickListener(new ShortClickDetail());
        detailListView.setOnItemLongClickListener(new LongClickDetail());



    }

    public void addDefaultItems(){
        ITEM item1 = new ITEM("Type something in the field above", "false");
        ITEM item2 = new ITEM("Press TODO", "false");
        ITEM item3 = new ITEM("Press shortly on an ITEM to check and uncheck", "false");
        ITEM item4 = new ITEM("Press long on an ITEM to Delete", "false");

        helper.Create(item1, currentTable);
        helper.Create(item2, currentTable);
        helper.Create(item3, currentTable);
        helper.Create(item4, currentTable);
    }

    public void addTable(){
        String tablename = tableText.getText().toString();
        if(!tablename.equals("")){
            helper.CreateTable(tablename);
        }
    }

    public void addItem(View view){
        String content = detailText.getText().toString();
        if(!content.equals("")){
            item = new ITEM("TODO: "+content, "false");
            helper.Create(item, currentTable);
            detailText.getText().clear();
            setDetailList();
        }
        else{
            Toast.makeText(this, "What did you need to do?", Toast.LENGTH_SHORT).show();
        }

    }

    public void setTableList(){

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

    public ArrayList<String> makeTodoList(ArrayList<ITEM> itemList){
        todoList = new ArrayList<>();
        for (ITEM items : itemList){
            todoList.add(items.getItem());
        }
        Log.d("check!", todoList.toString());
        return todoList;
    }



    private class ShortClickDetail implements AdapterView.OnItemClickListener {
        String selectedFromList;

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            itemList = helper.Read(currentTable);
            selectedFromList = (String) tableListView.getItemAtPosition(position);

            Log.d("click!", "you clicked"+selectedFromList);

            for (ITEM items : itemList){
                if (items.getItem().equals(selectedFromList)){
                    if (items.getDone().equals("false")){
                        items.setDone("true");
                        Log.d("list!", "This was false");
                        helper.Update(items);
                        break;
                    }
                    if (items.getDone().equals("true")){
                        items.setDone("false");
                        Log.d("list!", "This was true");
                        helper.Update(items);
                        break;
                    }
                }
            }
            setDetailList();
        }
    }

    private class LongClickDetail implements AdapterView.OnItemLongClickListener{
        String selectedFromList;

        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
            itemList = helper.Read(currentTable);
            selectedFromList = (String) tableListView.getItemAtPosition(position);

            Log.d("click!", "you longclicked"+selectedFromList);

            for (ITEM items : itemList){
                if (items.getItem().equals(selectedFromList)){
                    helper.Delete(items);
                }
            }
            setDetailList();
            return true;
        }
    }
}
