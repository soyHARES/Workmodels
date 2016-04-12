/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;

/**
 *
 * @author yoel1202
 */
public class TablasDatos {
    private String NombreTabla;
    private String NombreAtributo;
    private ArrayList<String> KeysForaneas;  

    public TablasDatos(String NombreTabla, String NombreAtributo, ArrayList<String> KeysForaneas) {
        this.NombreTabla = NombreTabla;
        this.NombreAtributo = NombreAtributo;
        this.KeysForaneas = KeysForaneas;
    }

    public TablasDatos() {
       NombreTabla = "Mi Tabla";
       NombreAtributo = "Atributo";
      KeysForaneas = null;
    }

    public String getNombreTabla() {
        return NombreTabla;
    }

    public void setNombreTabla(String NombreTabla) {
        this.NombreTabla = NombreTabla;
    }

    public String getNombreAtributo() {
        return NombreAtributo;
    }

    public void setNombreAtributo(String NombreAtributo) {
        this.NombreAtributo = NombreAtributo;
    }

    public ArrayList<String> getKeysForaneas() {
        return KeysForaneas;
    }

    public void setKeysForaneas(ArrayList<String> KeysForaneas) {
        this.KeysForaneas = KeysForaneas;
    }
    

    
}
