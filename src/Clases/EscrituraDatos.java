/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Clases.Atributos;
import Clases.Entidad;
import Clases.Relaciones;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Jerson
 */
public class EscrituraDatos {

    public boolean escribaRelacional(ArrayList<Entidad> entidades,
            ArrayList<Atributos> atributos, ArrayList<Relaciones> relation, String url, int entrada) {
        boolean retu = true;
        String n;
        if (entrada == 0) {
            n = url + ".bdm";
        } else {
            n = url;
        }

        System.out.println(n);
        String NombreFile = n;
        try {
            FileWriter escritor = new FileWriter(NombreFile, false);
            escritor.write("bdm_system\n");
            for (int i = 0; i < entidades.size(); i++) {
                escritor.write("&_1_&" + entidades.get(i).getTitle()
                        + "&_1_&" + entidades.get(i).isRelacional() + "\n");
            }

            for (int i = 0; i < atributos.size(); i++) {
                escritor.write("&_2_&" + atributos.get(i).getIdentificadorTabla());
                for (int j = 0; j < atributos.get(i).getAtributosNormales().size(); j++) {
                    escritor.write("&_2_&" + atributos.get(i).getAtributosNormales().get(j));
                }
                escritor.write("\n");
            }
            for (int i = 0; i < relation.size(); i++) {
                escritor.write("&_3_&" + relation.get(i).getEntidad1()
                        + "&_3_&" + relation.get(i).getRelacion() + "&_3_&" + relation.get(i).getEntidad2() + "\n");
            }
            escritor.close();
        } catch (IOException e) {
            retu = false;
            System.err.println("Error metodo de escritura de datos");
        }
        return retu;
    }

}
