/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;

/**
 * Hace las relacion entre entidad1
 *
 * @author Harley
 */
public class Relaciones {

    private String entidad1;
    private String entidad2;
    private String relacion;// 1.1

    public Relaciones(String entidad1, String entidad2, String relacion) {
        this.entidad1 = entidad1;
        this.entidad2 = entidad2;
        this.relacion = relacion;
    }

    public Relaciones() {
        entidad1 = "ninguna";
        entidad2 = "ninguna";
        relacion = "ninguna";
    }

    public String getEntidad1() {
        return entidad1;
    }

    public void setEntidad1(String entidad1) {
        this.entidad1 = entidad1;
    }

    public String getEntidad2() {
        return entidad2;
    }

    public void setEntidad2(String entidad2) {
        this.entidad2 = entidad2;
    }

    public String getRelacion() {
        return relacion;
    }

    public void setRelacion(String relacion) {
        this.relacion = relacion;
    }

}
