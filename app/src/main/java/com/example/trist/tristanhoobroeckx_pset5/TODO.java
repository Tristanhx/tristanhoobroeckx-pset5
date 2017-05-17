package com.example.trist.tristanhoobroeckx_pset5;

/**
 * Created by trist on 5/16/2017.
 */

public class TODO {
    int ID;
    String note;
    int status;
    String created_at;

    // Constructors
    public TODO(){

    }

    public TODO(String note, int status){
        this.note = note;
        this.status = status;
    }

    public TODO(int ID, String note, int status){
        this.ID = ID;
        this.note = note;
        this.status = status;
    }

    // getters
    public long getID(){
        return this.ID;
    }

    public String getNote(){
        return this.note;
    }

    public int getStatus(){
        return this.status;
    }

    //setters

    public void setID(int newID) {
        this.ID = newID;
    }

    public void setNote(String newNote){
        this.note = newNote;
    }

    public void setStatus(int newStatus){
        this.status = newStatus;
    }
}
