/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author yoel1202
 */
public class Jtable extends JTable {
    private String NombreTabla;
     private Lienzo lienzo;
    public Jtable() {
        
            }

  

    public String getNombreTabla() {
        return NombreTabla;
    }

    public void setNombreTabla(String NombreTabla) {
        this.NombreTabla = NombreTabla;
    }

   
    
}
