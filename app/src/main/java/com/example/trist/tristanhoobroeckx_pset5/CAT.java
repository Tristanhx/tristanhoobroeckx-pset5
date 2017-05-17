package com.example.trist.tristanhoobroeckx_pset5;

import java.io.Serializable;

/**
 * Created by trist on 5/16/2017.
 */

public class CAT implements Serializable{
    int ID;
    String CATname;

    // Constructors
    public CAT(){

    }

    public CAT(String CATname){
        this.CATname = CATname;
    }

    public CAT(int ID, String CATname){
        this.ID = ID;
        this.CATname = CATname;
    }

    // getters
    public long getID(){
        return this.ID;
    }

    public String getCATname(){
        return this.CATname;
    }

    // setters
    public void setID(int newID){
        this.ID = newID;
    }

    public void setCATname(String newCATname) {
        this.CATname = newCATname;
    }
}
