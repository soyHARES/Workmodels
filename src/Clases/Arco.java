package Clases;

/**
 *
 * @author yoel
 * @author olman
 * @author jonatan
 * @author enzo
 * @author michael
 */
import java.awt.*;
import java.io.*;

public class Arco extends DibujarFlecha implements Serializable {


    private String relacion;
    private Color color;
    private Nodo nodoOrigen;
    private Nodo nodoDestino;
    private Point puntoIn;
    private Point puntoFi;
  

    /**
     * Este el contructor de arco
     */
    public Arco() {
        
        setNodoOrigen(null);
        setNodoDestino(null);
        setRelacion("");
        setColor(null);
        setPuntoIn(null);
        setPuntoFi(null);
    }

    /**
     *
     * @param bid este parametro recibe un bollean direccion
     * @param o necesita de un nodo origin y final
     * @param d pide un do destino
     * @param p y si el grafo es dirigido
     * @param direc
     */
    public Arco(boolean bid, Nodo o, Nodo d, String r, boolean direc) {
       
        setNodoOrigen(o);
        setNodoDestino(d);
        setRelacion(r);
      
        setColor(null);
        setPuntoIn(null);
        setPuntoFi(null);
    }

    public Arco(boolean bid, Nodo o, Nodo d, String r, Color c, boolean direc) {
        this(bid, o, d, r, direc);
        setColor(c);

    }

   

    public String getRelacion() {
        return relacion;
    }

    public Color getColor() {
        return color;
    }

    public Nodo getNodoOrigen() {
        return nodoOrigen;
    }

    public Nodo getNodoDestino() {
        return nodoDestino;
    }

    public Point getPuntoIn() {
        return puntoIn;
    }

    public Point getPuntoFi() {
        return puntoFi;
    }

    

    public void setRelacion(String relacion) {
        this.relacion = relacion;
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public void setNodoOrigen(Nodo n) {
        this.nodoOrigen = n;
    }

    public void setNodoDestino(Nodo n) {
        this.nodoDestino = n;
    }

    public void setPuntoIn(Point p) {
        this.puntoIn = p;
    }

    public void setPuntoFi(Point p) {
        this.puntoFi = p;
    }

    //pintar()
    public void pintar(Graphics g) {
        //coordenadas iniciales
        int xi = nodoOrigen.getPuntoInicial().x;
        int yi = nodoOrigen.getPuntoInicial().y;
        //coordenadas finales
        int xf = nodoDestino.getPuntoInicial().x;
        int yf = nodoDestino.getPuntoInicial().y;

        //Puntos que representan el centro del círculo
        int xim = xi + (nodoOrigen.getTamanio() / 2); //centro del nodo origen
        int yim = yi + (nodoOrigen.getTamanio() / 2);
        int xfm = xf + (nodoDestino.getTamanio() / 2); //centro del nodo destino
        int yfm = yf + (nodoDestino.getTamanio() / 2);

        //****Calcular punto donde la flecha toca el perímetro del círculo***
        double co = yim - yfm; //cateto opuesto. asumir que siempre es en y
        double ca = xim - xfm; //catego adiacente. asumir que siempre es en x

                //System.out.println("yim:"+yim+" yfm:"+yfm);
        //System.out.println("xim:"+xim+" xfm:"+xfm);
        //System.out.println("co:"+co);
        //System.out.println("ca:"+ca);
        int signo_co = co == 0 ? 0 : (int) (co / Math.abs(co)); //determinar el signo.
        int signo_ca = ca == 0 ? 0 : (int) (ca / Math.abs(ca)); //determinar el signo.

        //System.out.println("signo_co:"+signo_co);
        //System.out.println("signo_ca:"+signo_ca);
        //ángulo entre los centros de los nodos
        double alfa = Math.abs(Math.atan(co / ca));

                //System.out.println("alfa:"+alfa);
        //System.out.println("sen(alfa):"+Math.sin(alfa));
        //cateto opuesto de tríangulo interno del círculo destino
        double coi = (nodoOrigen.getRadio() * Math.sin(alfa)) * signo_co;
        double cai = (nodoOrigen.getRadio() * Math.cos(alfa)) * signo_ca;

        //System.out.println("coi:"+coi);
        int pfx = (int) (xfm + cai); //punto final en x donde termina la flecha
        int pfy = (int) (yfm + coi); //punto final en y donde termina la flecha

        //System.out.println("Arco x:"+xi+" y:"+yi);
        //g.setColor( getColor() );
        if (nodoOrigen != nodoDestino) {

            super.drawArrow(g, xim, yim, pfx, pfy);

            g.setColor(Color.BLACK);
            g.drawString("" + getRelacion(), xim + ((xfm - xim) / 2), yim
                    + ((yfm - yim) / 2));
        }

    }//fin pintar()

    //toString()
    public String toString() {
        return getNodoOrigen().getEtiqueta()
                + ">" + getNodoDestino().getEtiqueta();
    }

    void setNodoDestino(Object fina) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}//***fin Arco***
