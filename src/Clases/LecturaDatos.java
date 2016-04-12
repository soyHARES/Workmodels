/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Clases.Atributos;
import Clases.Entidad;
import Clases.Funciones;
import Clases.Relaciones;
import GUI.Frm_Principal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Jerson
 */
public class LecturaDatos {

    public boolean cargarDatos(String absolutePath, ArrayList<Relaciones> relation) {
        System.out.println("llamada al evento-_------------------------------------");
        File fichero = new File(absolutePath);
        Funciones funcion = new Funciones();
        boolean retorno = true;
        try {
            if (fichero.exists()) {
                ArrayList<Entidad> entidades = new ArrayList<Entidad>();
                ArrayList<Atributos> atributos = new ArrayList<Atributos>();
                ArrayList<Relaciones> relacionales = new ArrayList<Relaciones>();
                String URL = absolutePath;
                FileReader lector = new FileReader(URL);
                BufferedReader br = new BufferedReader(lector);
                String linea;
                int cont = 0;
                while ((linea = br.readLine()) != null) {
                    if (cont == 0) {
                        if (linea.endsWith("bdm_system")) {
                            cont++;
                        } else {
                            JOptionPane.showMessageDialog(null, "El archivo se encuentra dañado");
                            retorno = false;
                            break;
                        }
                    } else {
                        if (linea.substring(0, 5).equals("&_1_&")) {
                            Entidad nueva = new Entidad();
                            linea = linea.substring(5, linea.length());
                            String[] var = linea.split("&_1_&");
                            if (var.length == 2) {
                                nueva.setTitle(var[0]);
                                if (var[1].equals("true")) {
                                    nueva.setRelacional(true);
                                    Frm_Principal.relacionales.add(var[0]);
                                }
                                entidades.add(nueva);
                            } else {
                                continue;
                            }

                        } else {
                            if (linea.substring(0, 5).equals("&_2_&")) {
                                linea = linea.substring(5, linea.length());
                                String[] var = linea.split("&_2_&");
                                if (var.length > 1) {
                                    Atributos nuevo = new Atributos();
                                    nuevo.setIdentificadorTabla(var[0]);
                                    ArrayList<String> atri = new ArrayList<String>();
                                    for (int i = 1; i < var.length; i++) {
                                        atri.add(var[i]);
                                    }
                                    nuevo.setAtributosNormales(atri);
                                    atributos.add(nuevo);
                                }
                            } else {
                                if (linea.substring(0, 5).equals("&_3_&")) {
                                    linea = linea.substring(5, linea.length());
                                    String[] var = linea.split("&_3_&");
                                    if (var.length == 3) {
                                        Relaciones renew = new Relaciones();
                                        renew.setEntidad1(var[0]);
                                        renew.setRelacion(var[1]);
                                        renew.setEntidad2(var[2]);
                                        relacionales.add(renew);
                                    } else {
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                }
                for (int i = 0; i < entidades.size(); i++) {
                    funcion.InsertarTablas(entidades.get(i).getTitle(), Frm_Principal.jtable_Principal, Frm_Principal.jcb_entidadesIngresadas);
                }
                
                

                for (int i = 0; i < atributos.size(); i++) {
                    for (int j = 0; j < atributos.get(i).getAtributosNormales().size(); j++) {
                        funcion.InsertarAtributos(atributos.get(i).getAtributosNormales().get(j), Frm_Principal.jtable_Principal, atributos.get(i).getIdentificadorTabla());
                    }
                }
                for (int i = 0; i < relacionales.size(); i++) {
                    funcion.ingresarRelacion(Frm_Principal.jtable_relaciones,
                            relacionales.get(i).getEntidad1(), relacionales.get(i).getRelacion(), relacionales.get(i).getEntidad2());
                }
                br.close();
                lector.close();

            }
        } catch (IOException e) {
            System.err.println("Error Lectura de datos");
        }
        return retorno;
    }

}
