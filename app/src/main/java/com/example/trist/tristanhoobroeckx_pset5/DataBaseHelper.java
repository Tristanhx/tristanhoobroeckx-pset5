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

    private static final String DATABASE_NAME = "TodoDataBase";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_ID = "_id";
    private static final String KEY_ITEM = "item";
    private static final String DONE = "done";
    private static final String TABLE = "";

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_DATABASE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ITEM
                + " TEXT NOT NULL," + DONE + " TEXT NOT NULL)";

        db.execSQL(CREATE_DATABASE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void Create(ITEM item){
        SQLiteDatabase db = getWritableDatabase();
        //onUpgrade(db, 1, 1);
        ContentValues values = new ContentValues();
        values.put(KEY_ITEM, item.getItem());
        values.put(DONE, item.getDone());
        db.insert(TABLE, null, values);
        Log.d("check!", values.get(KEY_ITEM).toString());
        Log.d("check!", values.get(DONE).toString());
        db.close();
    }

    public ArrayList<ITEM> Read(String table){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<ITEM> items = new ArrayList<>();
        String query = "SELECT " + KEY_ID + ", " + KEY_ITEM + ", " + DONE + " FROM " + table;
        Cursor cursor =  db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do{
                String name = cursor.getString(cursor.getColumnIndex(KEY_ITEM));
                String done = cursor.getString(cursor.getColumnIndex(DONE));
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));

                ITEM item = new ITEM(name, done, id);

                items.add(item);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return items;
    }

    public int Update(ITEM item){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ITEM, item.getItem());
        values.put(DONE, item.getDone());

        return db.update(TABLE, values, KEY_ID + " = ? ", new String[] {String.valueOf(item.getID())});
    }

    public void Delete(ITEM item){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, " " + KEY_ID + " = ? ", new String[] {String.valueOf(item.getID())});
        db.close();
    }
}
