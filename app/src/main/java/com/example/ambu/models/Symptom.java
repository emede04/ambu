package com.example.ambu.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Symptom {
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("Name")
    @Expose
    private String name;

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        this.ID = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
