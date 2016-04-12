/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import GUI.BarraProgreso;
import GUI.Frm_Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Jerson
 */
public class HiloProgres extends Thread {

    public HiloProgres() {
    }

    public void run() {
        BarraProgreso c = new BarraProgreso();
        c.lbl_titulo.setText("");
        c.setVisible(true);
        int i = 0;
        while (i <= 100) {
            BarraProgreso.jProgressBar1.setValue(i);
            i++;
            try {
                Thread.sleep(19);
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloProgres.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        c.cerrar();
        JOptionPane.showMessageDialog(Frm_Principal.Jp_informacion, "Proceso Completado");
    }
}
