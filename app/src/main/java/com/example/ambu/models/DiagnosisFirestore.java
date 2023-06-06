package com.example.ambu.models;

import java.io.Serializable;
import java.util.List;

public class DiagnosisFirestore implements Serializable
{
    String Acurracy, Issue, descripcion_larga, fecha, medical_condition,sintomas_presentado,specialidad, tratamiento;

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

    public String getDescripcion_larga() {
        return descripcion_larga;
    }

    public void setDescripcion_larga(String descripcion_larga) {
        this.descripcion_larga = descripcion_larga;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMedical_condition() {
        return medical_condition;
    }

    public void setMedical_condition(String medical_condition) {
        this.medical_condition = medical_condition;
    }

    public String getSintomas_presentado() {
        return sintomas_presentado;
    }

    public void setSintomas_presentado(String sintomas_presentado) {
        this.sintomas_presentado = sintomas_presentado;
    }

    public String getSpecialidad() {
        return specialidad;
    }

    public void setSpecialidad(String specialidad) {
        this.specialidad = specialidad;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }
}
