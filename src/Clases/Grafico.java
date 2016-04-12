package Clases;


import java.awt.*;
import java.util.*;
import java.io.*;

public class Grafico implements Serializable {

    private int counterId;
    private java.util.List listaNodos; //lista de Nodos.(adyacencia)
     private java.util.List listatablas;
    private boolean modificado;
    public ArrayList<Nodo> nodos = new ArrayList<Nodo>();
   

    public Grafico() {
        this.counterId = 0;
        listaNodos = Collections.synchronizedList(new LinkedList());
        listatablas = Collections.synchronizedList(new LinkedList());
        this.modificado = false;
       
    }

    /**
     *
     * @param etiqueta agrega la etique al nodo
     * @param puntoInicial punto inicial del nodo
     * @param tam tamano de nodo que se quiere dibujar
     * @param color color del nodo
     * @return y debuelvo el nodo
     */
    synchronized public Nodo agregarNodo(String etiqueta, String TipoFigura, Point puntoInicial, int tam, Color color,String tablaPertenece) {
        Nodo  nodo = new Nodo(counterId, etiqueta, TipoFigura, puntoInicial, tam, color,tablaPertenece);
        listaNodos.add(nodo);
        
        nodos.add(nodo);
        counterId++;
        setModificado(true);
        return nodo;
    }
 synchronized public Jscroll agregartabla(String etiqueta, Point puntoInicial,Jtable table,Lienzo tabla) {
        
        Jscroll scro = new Jscroll(puntoInicial, etiqueta,table,tabla);
        scro.setViewportView(table);
        table.setNombreTabla(etiqueta);
        setModificado(true);
        listatablas.add(scro);
        
        return scro;
    }
    /**
     *
     * @param bid direccion en que se traza la flecha
     * @param o el origen del arco
     * @param d el final del arco
     * @param p nodo intermedio
     * @param c color del arco
     * @param dirigido para tomar si el grafo es dirigido o no dirigido
     * @return 1
     */
    synchronized public int agregarArco(boolean bid, int o, int d, String p, Color c, boolean dirigido) {

        if (o == d)//si el destino es el mismo que el origen.
        {
            boolean encontrado = false;
            Iterator it = listaNodos.iterator();
            Nodo nodo = null;

            while (it.hasNext() & encontrado == false) { //revistar que exista el nodo.
                nodo = (Nodo) it.next();

                if (nodo.getId() == o) {
                    encontrado = true;
                }
            }

            if (encontrado) {//si encontro el nodo
                nodo.getListaAd().add(new Arco(bid, nodo, nodo, p, c, true)); //agregar nodo a su lista de adiacencia
                setModificado(true);
                return 1; //agregado con exito.
            } else {
                return 2; //Ese nodo no existe.
            }
        } else //destino diferente de origen.
        {

            Nodo arr[] = {null, null}; //arreglo de nodos tmps.
            int cond = 0; //condicion para el while
            Iterator it = listaNodos.iterator();
            Nodo nodo = null;

            while (it.hasNext() & cond != 2) {//revisar q existan los 2 nodos
                nodo = (Nodo) it.next();

                if (nodo.getId() == o) {
                    arr[0] = nodo;
                    cond++;
                } else if (nodo.getId() == d) {
                    arr[1] = nodo;
                    cond++;
                }
            }

            if (arr[0] != null & arr[1] != null)//se encontraron los dos nodos
            {
                if (dirigido) {//si es dirigido, que punga las flechas
                    if (bid) { //si es bidireccional agregar los dos
                        //agregar nodo a lista de adyacencia
                        arr[0].getListaAd().add(new Arco(bid, arr[0], arr[1], p,
                                c, true));
                        arr[1].getListaAd().add(new Arco(bid, arr[1], arr[0], p,
                                c, true));
                        setModificado(true);
                        return 1; //se agrego con exito
                    } else {
                        //agregar nodo a lista de adiacencia	    	
                        arr[0].getListaAd().add(new Arco(bid, arr[0], arr[1], p,
                                c, true));
                        setModificado(true);
                        return 1; //se agrego con exito
                    }
                } else {
                    arr[0].getListaAd().add(new Arco(bid, arr[0], arr[1], p, c,
                            false));
                    arr[1].getListaAd().add(new Arco(bid, arr[1], arr[0], p, c,
                            false));
                    setModificado(true);
                    return 1; //se agrego con exito
                }
            } else if (arr[0] != null) {
                return 3; //no se encontro el destino
            } else {
                return 4; //no se encontro el orgien.
            }
        }

    }//fin agregarArco()

    /**
     * restablecer() cuando se restablece, cada nodo de la lista de adyacencia
     * queda con un key mayor al nodo de origen, lo cual es importante al llamar
     * al metodo relajar que se encarga de armar un arbol en base al nodo origen
     * Primera condicion del algoritmo de dijkstra Inicializar todas las
     * distancias en D con un valor infinito relativo ya que son desconocidas al
     * principio, exceptuando la de x que se debe colocar en 0 debido a que la
     * distancia de x a x sería 0.
     *
     * @param s restablece el nodo
     */
    private void restablecer(Nodo s) {
        restablecer();
        s.setKey(0);

    }//fin restablecer()

    private void restablecer() {

        Iterator it = listaNodos.iterator();
        while (it.hasNext()) {  //Hacer key=infinito de cada nodo
            Nodo nodo = (Nodo) it.next();
            nodo.setKey(Double.POSITIVE_INFINITY);
            nodo.setPadre(null);

            Iterator it2 = nodo.getListaAd().iterator();
            while (it2.hasNext()) {//se recorren los nodos adyacentes
                Arco arco = (Arco) it2.next();
               
                arco.setColor(Color.white);//se cambian los colores de los arcos (visualmente flechas)
            }
        }

    }

    public void pintar(Graphics g) {
        Iterator nodos = listaNodos.iterator();
        Iterator arcos;

        while (nodos.hasNext()) {
            Nodo n = (Nodo) nodos.next();
            arcos = n.getListaAd().iterator();
       n.getPuntoInicial();
            while (arcos.hasNext()) {
                Arco a = (Arco) arcos.next();
                a.pintar(g);

            }
        }

        nodos = listaNodos.iterator();

        while (nodos.hasNext()) {
            Nodo n = (Nodo) nodos.next();
            n.getTipoFigura();
            char opcion = n.getTipoFigura().charAt(0);

            n.pintar(g,  opcion);

        }

    }

    /**
     *
     * @param s se elimina el nodo de las listas de adyacencia
     */
    public void EliminarNodo(Nodo s) {

        Iterator it = listaNodos.iterator();

        while (it.hasNext()) {
            Nodo n = (Nodo) it.next();

            if (n == s) {
                it.remove();
                break;
            }
        }

    }

    //Accesors
    public java.util.List getListaNodos() {
        return listaNodos;
    }
  public java.util.List getListatablas() {
        return listatablas;
    }
    public boolean getModificado() {
        return modificado;
    }

    //Mutators
    public void setModificado(boolean m) {
        this.modificado = m;
    }

  

      public long getCounterId() {
        return counterId;
    }

  

}
