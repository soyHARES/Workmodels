/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import GUI.Frm_Principal;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yoel1202
 */
public class Funciones {

    ArrayList<String> guardar = new ArrayList<String>();

    public void InsertarTablas(String tabla, JTable tabladatos, JComboBox selecciones) {
        DefaultTableModel modelo = (DefaultTableModel) tabladatos.getModel();

        guardar.add("primero");
        for (int i = 0; i <= guardar.size(); i++) {

            if (tabla.equals(guardar.get(i))) {
                 JOptionPane.showMessageDialog(Frm_Principal.jP_modelado, "Imposible! Ya existe!");
                break;
            } else {
                guardar.add(i, tabla);
                modelo.addColumn(guardar.get(i));
                selecciones.addItem(guardar.get(i));
                Frm_Principal.jcb_entidades2.addItem(guardar.get(i));
                Frm_Principal.jcb_entidades1.addItem(guardar.get(i));
                break;
            }

        }

    }

    public void InsertarRelaciones(String tabla, JTable tabladatos, JComboBox selecciones) {
        DefaultTableModel modelo = (DefaultTableModel) tabladatos.getModel();
        guardar.add("primero");
        for (int i = 0; i <= guardar.size(); i++) {
            if (tabla.equals(guardar.get(i))) {
                 JOptionPane.showMessageDialog(Frm_Principal.jP_modelado, "Imposible! Ya existe!");
                break;
            } else {
                guardar.add(i, tabla);
                modelo.addColumn(guardar.get(i));
                selecciones.addItem(guardar.get(i));
                break;
            }

        }

    }

    public static void InsertarAtributos(String atributos, JTable tabladatos, String selecciones) {

        DefaultTableModel modelo = (DefaultTableModel) tabladatos.getModel();
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            if (modelo.getColumnName(i).equals(selecciones)) {
                for (int j = 0; j < modelo.getRowCount(); j++) {
                    if (modelo.getValueAt(j, i) == null) {
                        if (((Frm_Principal.contarAtributos3()) == (Frm_Principal.jtable_Principal.getRowCount() - 1))) {
                            modelo.setRowCount(modelo.getRowCount() + 1);
                        }
                        modelo.setValueAt(atributos, j, i);
                        break;
                    }
                }
            }
        }
    }


    public static void ingresarRelacion(JTable jt_relacionesEntidades, String jcb_entidades1, String jcb_relacioesEntidades, String jcb_entidades2) {
        DefaultTableModel modelo = (DefaultTableModel) jt_relacionesEntidades.getModel();
        String[] e = new String[3];
        e[0] = jcb_entidades1;
        e[2] = jcb_entidades2;
        e[1] = jcb_relacioesEntidades;
        if (verificar(e)) {
            modelo.addRow(e);
            Relaciones relaciones = new Relaciones(e[0], e[2], e[1]);
            Frm_Principal.relation.add(relaciones);
//            relation.add(relaciones);
        } else {
            JOptionPane.showMessageDialog(Frm_Principal.jP_modelado, "Imposible! La relación ya existe!");
        }
    }

    private static boolean verificar(String[] e) {
        for (int i = 0; i < Frm_Principal.jtable_relaciones.getRowCount(); i++) {
            if ((Frm_Principal.jtable_relaciones.getValueAt(i, 0).toString().equals(e[0]))
                    && (Frm_Principal.jtable_relaciones.getValueAt(i, 1).toString().equals(e[1]))
                    && (Frm_Principal.jtable_relaciones.getValueAt(i, 2).toString().equals(e[2]))) {
                return false;
            }
        }
        return true;
    }

}
