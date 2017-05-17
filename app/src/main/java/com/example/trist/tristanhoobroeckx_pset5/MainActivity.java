package com.example.trist.tristanhoobroeckx_pset5;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);

        // Create new database
        context = this;
        helper = DataBaseHelper.getInstance(context);

        tableText = (EditText) findViewById(R.id.edittxt2);
        CategoryListView = (ListView) findViewById(R.id.list);

        setCategoryList();





        CategoryListView.setOnItemClickListener(new ShortClickDetail());
        CategoryListView.setOnItemLongClickListener(new LongClickDetail());



    }

//    public void addDefaultItems(){
//        ITEM item1 = new ITEM("Type something in the field above", "false");
//        ITEM item2 = new ITEM("Press TODO", "false");
//        ITEM item3 = new ITEM("Press shortly on an ITEM to check and uncheck", "false");
//        ITEM item4 = new ITEM("Press long on an ITEM to Delete", "false");
//
//        helper.Create(item1);
//        helper.Create(item2);
//        helper.Create(item3);
//        helper.Create(item4);
//    }

    public void addCategory(View view){
        String content = tableText.getText().toString();
        if(!content.equals("")){
            cat = new CAT(content);
            helper.CreateCAT(cat);
            tableText.getText().clear();
            setCategoryList();
        }
        else{
            Toast.makeText(this, "What did you need to do?", Toast.LENGTH_SHORT).show();
        }

    }

    public void setCategoryList(){
        CategoryList = helper.ReadAllCAT();
        ArrayList<String> strings = makeCategoryList(CategoryList);

        if (CategoryList != null){
            CustomCategoryList adapter = new CustomCategoryList(context, strings, CategoryListView);
            assert CategoryList != null;
            Log.d("check!", "voor adapter");
            CategoryListView.setAdapter(adapter);

        }
    }

    public ArrayList<String> makeCategoryList(ArrayList<CAT> CATList){
        todoList = new ArrayList<>();
        for (CAT cats : CATList){
            todoList.add(cats.getCATname());
        }
        Log.d("check!", todoList.toString());
        return todoList;
    }



    private class ShortClickDetail implements AdapterView.OnItemClickListener {
        String selectedFromList;

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CategoryList = helper.ReadAllCAT();
            selectedFromList = (String) CategoryListView.getItemAtPosition(position);

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("selected", selectedFromList);
            context.startActivity(intent);

            Log.d("click!", "you clicked"+selectedFromList);

            setCategoryList();
        }
    }

    private class LongClickDetail implements AdapterView.OnItemLongClickListener{
        String selectedFromList;

        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
            CategoryList = helper.ReadAllCAT();
            selectedFromList = (String) CategoryListView.getItemAtPosition(position);

            Log.d("click!", "you longclicked"+selectedFromList);

            for (CAT cats : CategoryList){
                if (cats.getCATname().equals(selectedFromList)){
                    CAT cat = new CAT;
                    cat = helper.R
                    helper.DeleteCATandTODOs(items);
                }
            }
            setDetailList();
            return true;
        }
    }


}
