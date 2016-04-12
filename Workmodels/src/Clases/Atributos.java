/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;

/**
 *
 * @author Harley
 */
public class Atributos {

    private String identificadorTabla;//
    private ArrayList<String> foranea;
    private ArrayList<String> atributosNormales;
    
         
    public Atributos(String identificadorTabla,  ArrayList<String> foranea, ArrayList<String> atributos) {
        this.identificadorTabla = identificadorTabla;
        this.foranea = foranea;
        this.atributosNormales = atributos;
    }

    public Atributos() {
        identificadorTabla = null;
        foranea = null;
        atributosNormales = null;
    }

    public String getIdentificadorTabla() {
        return identificadorTabla;
    }

    public void setIdentificadorTabla(String identificadorTabla) {
        this.identificadorTabla = identificadorTabla;
    }
    public ArrayList<String> getForanea() {
        return foranea;
    }

    public void setForanea(ArrayList<String> foranea) {
        this.foranea = foranea;
    }

    public ArrayList<String> getAtributosNormales() {
        return atributosNormales;
    }

    public void setAtributosNormales(ArrayList<String> atributosNormales) {
        this.atributosNormales = atributosNormales;
    }

}
