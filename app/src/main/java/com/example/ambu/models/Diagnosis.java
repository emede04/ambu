package com.example.ambu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Diagnosis {
    String name;
    double Accuracy;
    String cause;
    @SerializedName("Issue")
    @Expose
    private Issue issue;
    @SerializedName("Specialisation")
    @Expose
    private List<Specialisation> specialisation = null;


    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public List<Specialisation> getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(List<Specialisation> specialisation) {
        this.specialisation = specialisation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAccuracy() {
        return Accuracy;
    }

    public void setAccuracy(double accuracy) {
        Accuracy = accuracy;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Issue getIssue() {
        return issue;
    }
}

