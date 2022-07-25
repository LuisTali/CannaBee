package Clases;

import java.io.Serializable;

public class Cepa implements Serializable {
    private int id;
    private String nombre;
    private String raza;
    private double thc;
    private String comentarios;
    private String banco;

    public Cepa() {
    }

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

    public Cepa(String nombre, String raza, double thc, String comentarios, String banco) {
        this.nombre = nombre;
        this.raza = raza;
        this.thc = thc;
        this.comentarios = comentarios;
        this.banco = banco;
    }

    public int getId() {
        return id;
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

    public String getBanco() {
        return banco;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String toString() {
        return "Nombre: " + getNombre() + ", THC: " + getThc() + ", Raza: " + getRaza() + "\n";
    }
}
