package com.example.ambu.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Paciente implements Parcelable {
    String nombre;
    String edad;
    String imagen;
    String apedillo;
    String fecha;
    String peso;
    String altura;
    String sintomas;

    protected Paciente(Parcel in) {
        nombre = in.readString();
        edad = in.readString();
        imagen = in.readString();
        apedillo = in.readString();
        fecha = in.readString();
        peso = in.readString();
        altura = in.readString();
        sintomas = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(edad);
        dest.writeString(imagen);
        dest.writeString(apedillo);
        dest.writeString(fecha);
        dest.writeString(peso);
        dest.writeString(altura);
        dest.writeString(sintomas);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Paciente> CREATOR = new Creator<Paciente>() {
        @Override
        public Paciente createFromParcel(Parcel in) {
            return new Paciente(in);
        }

        @Override
        public Paciente[] newArray(int size) {
            return new Paciente[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getApedillo() {
        return apedillo;
    }

    public void setApedillo(String apedillo) {
        this.apedillo = apedillo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }
}
