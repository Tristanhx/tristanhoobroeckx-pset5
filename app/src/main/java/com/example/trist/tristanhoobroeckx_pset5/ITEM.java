package com.example.trist.tristanhoobroeckx_pset5;

import java.io.Serializable;

/**
 * Created by Tristan on 05/05/2017.
 */

public class ITEM implements Serializable{
    private String item;
    private String done;
    private int id;

    public ITEM(String description, String check){
        item = description;
        done = check;
    }

    public ITEM(String description, String check, int todoID){
        item = description;
        done = check;
        id = todoID;
    }

    public void setItem(String newDescription){
        item = newDescription;
    }

    public void setDone(String newCheck){
        done = newCheck;
    }

    public void setID(int NewID){
        id = NewID;
    }


    public String getItem(){
        return item;
    }

    public String getDone(){
        return done;
    }

    public Integer getID(){
        return id;
    }

}
