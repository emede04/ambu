package com.example.ambu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Diagnosis {
    @SerializedName("Issue")
    @Expose
    private Issue issue;
    @SerializedName("Specialisation")
    @Expose
    private List<Specialisation> specialisation = null;

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public List<Specialisation> getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(List<Specialisation> specialisation) {
        this.specialisation = specialisation;
    }
}

