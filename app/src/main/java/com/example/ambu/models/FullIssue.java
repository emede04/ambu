package com.example.ambu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FullIssue {
    @SerializedName("Description")
    @Expose
    String Description;
    @SerializedName("DescriptionShort")
    @Expose
    String DescriptionShort;
    @SerializedName("MedicalCondition")
    @Expose
    String MedicalCondition;
    @SerializedName("Name")
    @Expose
    String Name;
    @SerializedName("PossibleSymptoms")
    @Expose
    String PossibleSymptoms;
    @SerializedName("ProfName")
    @Expose
    String ProfName;
    @SerializedName("Synonyms")
    @Expose
    String Synonyms;
    @SerializedName("TreatmentDescription")
    @Expose
    String TreatmentDescription;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDescriptionShort() {
        return DescriptionShort;
    }

    public void setDescriptionShort(String descriptionShort) {
        DescriptionShort = descriptionShort;
    }

    public String getMedicalCondition() {
        return MedicalCondition;
    }

    public void setMedicalCondition(String medicalCondition) {
        MedicalCondition = medicalCondition;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPossibleSymptoms() {
        return PossibleSymptoms;
    }

    public void setPossibleSymptoms(String possibleSymptoms) {
        PossibleSymptoms = possibleSymptoms;
    }

    public String getProfName() {
        return ProfName;
    }

    public void setProfName(String profName) {
        ProfName = profName;
    }

    public String getSynonyms() {
        return Synonyms;
    }

    public void setSynonyms(String synonyms) {
        Synonyms = synonyms;
    }

    public String getTreatmentDescription() {
        return TreatmentDescription;
    }

    public void setTreatmentDescription(String treatmentDescription) {
        TreatmentDescription = treatmentDescription;
    }
}



