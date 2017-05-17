package com.example.trist.tristanhoobroeckx_pset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Tristan on 05/05/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper{

    private static DataBaseHelper instance;

    private static final String DATABASE_NAME = "TodoDataBase";
    private static final int DATABASE_VERSION = 1;

    // Common column names
    private static final String KEY_ID = "_id";

    // TODO_TABLE column names
    private static final String KEY_ITEM = "item";
    private static final String DONE = "done";

    // CAT column names
    private static final String KEY_CAT_NAME = "CAT_name";

    // TODOxCAT column names
    private static final String KEY_TODO_ID = "TODO_id";
    private static final String KEY_CAT_ID = "CAT_id";

    // TABLE names
    private static final String TABLE_CAT = "Category";
    private static final String TABLE_DET = "Detail";
    private static final String TABLE_TODOxCAT = "TODOxCAT";

    // CREATE TABLE STATEMENTS
    // TODO_TABLE
    private static final String CREATE_TABLE_TODO = "CREATE TABLE " + TABLE_DET + "(" + KEY_ID
            + " INTEGER PRIMARY KEY," + KEY_ITEM + " TEXT," + DONE + " INTEGER)";

    // CAT TABLE
    private static final String CREATE_TABLE_CAT = "CREATE TABLE " + TABLE_CAT + "(" + KEY_ID
            + " INTEGER PRIMARY KEY," + KEY_CAT_NAME + " TEXT," + DONE + " INTEGER)";

    // CATxTODO
    private static final String CREATE_TABLE_TODOxCAT = "CREATE TABLE " + TABLE_TODOxCAT + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TODO_ID + " INTEGER," + KEY_CAT_ID
            + " INTEGER)";


    public static synchronized DataBaseHelper getInstance(Context context){

        if (instance == null){
            instance = new DataBaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_CAT);
        db.execSQL(CREATE_TABLE_TODO);
        db.execSQL(CREATE_TABLE_TODOxCAT);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAT);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_TODO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOxCAT);
        onCreate(db);
    }

    public long CreateTODO(TODO todo, long[] cat_ids){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM, todo.getNote());
        values.put(DONE, todo.getStatus());

        long todo_id = db.insert(TABLE_DET, null, values);
        Log.d("check!", values.get(KEY_ITEM).toString());
        Log.d("check!", values.get(DONE).toString());
        db.close();

        return todo_id;
    }

    public long CreateCAT(CAT cat){
        SQLiteDatabase db = this.getWritableDatabase();
        //onUpgrade(db, 1, 1);
        ContentValues values = new ContentValues();
        values.put(KEY_CAT_NAME, cat.getCATname());

        long CAT_id = db.insert(TABLE_CAT, null, values);
        db.close();

        return CAT_id;
    }

    public long CreateTODOCAT(long todo_id, long cat_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TODO_ID, todo_id);
        values.put(KEY_CAT_ID, cat_id);

        long id = db.insert(TABLE_TODOxCAT, null, values);
        return id;
    }

    public TODO ReadTODO(long todo_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM" + TABLE_DET + " WHERE " + KEY_ID + " = " + todo_id;
        Cursor cursor =  db.rawQuery(query, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        TODO todo = new TODO();
        todo.setID(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        todo.setNote(cursor.getString(cursor.getColumnIndex(KEY_ITEM)));

        return todo;
    }

    public ArrayList<TODO> ReadAllTODO(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TODO> todos = new ArrayList<>();
        String query = "SELECT * FROM" + TABLE_DET;
        Cursor cursor =  db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do{
                TODO todo = new TODO();
                todo.setID(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                todo.setNote(cursor.getString(cursor.getColumnIndex(KEY_ITEM)));

                todos.add(todo);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return todos;
    }

    public ArrayList<TODO> getAllTODObyCAT(String cat_name){
        ArrayList<TODO> todos = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_DET + " todo, " + TABLE_CAT + " cat, "
                + TABLE_TODOxCAT + " todo WHERE cat." + KEY_CAT_NAME + " = '" + cat_name + "'"
                + " AND cat." + KEY_ID + " = " + "id." + KEY_CAT_ID + " AND todo." + KEY_ID + " = "
                + "id." + KEY_TODO_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do{
                TODO todo = new TODO();
                todo.setID(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                todo.setNote(cursor.getString(cursor.getColumnIndex(KEY_ITEM)));

                todos.add(todo);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return todos;
    }

    public ArrayList<CAT> ReadAllCAT(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CAT> cats = new ArrayList<>();
        String query = "SELECT * FROM" + TABLE_CAT;
        Cursor cursor =  db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do{
                CAT cat = new CAT();
                cat.setID(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                cat.setCATname(cursor.getString(cursor.getColumnIndex(KEY_CAT_NAME)));

                cats.add(cat);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cats;
    }

    public int Update(ITEM item){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ITEM, item.getItem());
        values.put(DONE, item.getDone());

        return db.update(TABLE_CAT, values, KEY_ID + " = ? ", new String[] {String.valueOf(item.getID())});
    }

    public void DeleteTODO(long todo_id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_DET, " " + KEY_ID + " = ? ", new String[] {String.valueOf(todo_id)});
    }

    public void DeleteCATandTODOs(CAT cat){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<TODO> allCATTODOS = getAllTODObyCAT(cat.getCATname());
        for (TODO todo : allCATTODOS){
            DeleteTODO(todo.getID());
        }
        db.delete(TABLE_CAT, " " + KEY_ID + " = ? ", new String[] {String.valueOf(cat.getID())});
        db.close();
    }
}
