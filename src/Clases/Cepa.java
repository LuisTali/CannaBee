package Clases;

import java.io.Serializable;

public class Cepa implements Serializable {
    private String nombre;
    private String raza;
    private double thc;
    private String comentarios;

    public Cepa(String nombre, String raza, double thc) {
        this.nombre = nombre;
        this.raza = raza;
        this.thc = thc;
    }

    public Cepa(String nombre, String raza, double thc, String comentarios) {
        this.nombre = nombre;
        this.raza = raza;
        this.thc = thc;
        this.comentarios = comentarios;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRaza() {
        return raza;
    }

    public double getThc() {
        return thc;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public void setThc(double thc) {
        this.thc = thc;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
