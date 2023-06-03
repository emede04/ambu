package com.example.ambu.models;

import java.io.Serializable;
import java.util.List;

public class DiagnosisFirestore implements Serializable
{
    String Acurracy, Issue, fecha, idcName, sintomas_presentado;

    public String getSpecialidad() {
        return specialidad;
    }

    public void setSpecialidad(String specialidad) {
        this.specialidad = specialidad;
    }

    String specialidad;



    public String getAcurracy() {
        return Acurracy;
    }

    public void setAcurracy(String acurracy) {
        Acurracy = acurracy;
    }

    public String getIssue() {
        return Issue;
    }

    public void setIssue(String issue) {
        Issue = issue;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdcName() {
        return idcName;
    }

    public void setIdcName(String idcName) {
        this.idcName = idcName;
    }

    public String getSintomas_presentado() {
        return sintomas_presentado;
    }

    public void setSintomas_presentado(String sintomas_presentado) {
        this.sintomas_presentado = sintomas_presentado;
    }


}
