package Clases;

/**
 *
 */
import java.util.*;
import java.awt.*;

public class Nodo {

    private int id;
    private String etiqueta;
    private String tipoFigura;
    private Point punto_inicial; //coordenada de la esquina superior izquierda
    private int tamanio; //tamaio del nodo
    private double radio;
    private Color color; //color del nodo
    private java.util.List listaAd; //lisda de adyacencia de un nodo.<arcos>
    private String tablaPertenece;
    //atributos para uso en algoritmos
    private double key; //sirve como d[]

    private Nodo padre; //pi[], para construir el arbol.

    //Constructores
    public Nodo() {
        setId(-1);
        setEtiqueta("");
        setPuntoInicial(null);
        setTamanio(0);
        setRadio();
        setColor(null);
        listaAd = Collections.synchronizedList(new LinkedList());
        setKey(Double.POSITIVE_INFINITY);
        setPadre(null);
    }

    public Nodo(int id, String eti, String tipo, String tablaPertenece) {
        setId(id);
        setEtiqueta(eti);
        setTipoFigura(tipo);
        setPuntoInicial(null);
        setTamanio(0);
        setRadio();
        setColor(null);
        listaAd = Collections.synchronizedList(new LinkedList());
        setKey(Double.POSITIVE_INFINITY);
        setPadre(null);
        setTablaPertenece(tablaPertenece);
    }

    public Nodo(int id, String eti, String tipo, Point pi, int tam, Color col, String tablaPertenece) {
        this(id, eti, tipo, tablaPertenece);
        setPuntoInicial(pi);
        setTamanio(tam);
        setRadio();
        setColor(col);
        listaAd = Collections.synchronizedList(new LinkedList());
        setKey(Double.POSITIVE_INFINITY);
        setPadre(null);
        setTablaPertenece(tablaPertenece);

    }
	//fin Constructores

    //Accesors
    public int getId() {
        return id;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public String getTipoFigura() {
        return tipoFigura;
    }

    public Point getPuntoInicial() {
        return punto_inicial;
    }

    public int getTamanio() {
        return tamanio;
    }

    public double getRadio() {
        return this.radio;
    }

    public Color getColor() {
        return color;

    }

    public java.util.List getListaAd() {
        return listaAd;
    }

    public double getKey() {
        return key;
    }

    public Nodo getPadre() {
        return padre;
    }

    //Mutators
    public void setId(int id) {
        this.id = id;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public void setTipoFigura(String tipo) {
        this.tipoFigura = tipo;
    }

    public void setPuntoInicial(Point pi) {
        this.punto_inicial = pi;
    }

    public void setTamanio(int t) {
        this.tamanio = t;
    }

    public void setRadio() {
        this.radio = getTamanio() / 2;
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public void setKey(double n) {
        this.key = n;
    }

    public void setPadre(Nodo n) {
        this.padre = n;
    }

    /**
     * @param g
     * @param opcion
     */
    public void pintar(Graphics g, char opcion) {

        Graphics2D g2d = (Graphics2D) g;

        int pix = getPuntoInicial().x;
        int piy = getPuntoInicial().y;
        int pfx = pix + getTamanio();
        int pfy = piy + getTamanio();
        Image img;

        switch (opcion) {

            case 'a':
                img = Toolkit.getDefaultToolkit().getImage("src/imagenes/circulo.png");
                g2d.drawImage(img, pix, piy, null, null);
                g.setColor(Color.BLACK);
                g.setFont(new Font("TimesRoman", Font.PLAIN, getTamanio() * 12 / 75));
                g.drawString(getEtiqueta(), pix + (15), (piy+ 30));

                break;
            case 'e':
                img = Toolkit.getDefaultToolkit().getImage("src/imagenes/rectangulo.png");
                g2d.drawImage(img, pix, piy, null, null);
                g.setColor(Color.BLACK);
                g.setFont(new Font("TimesRoman", Font.BOLD, getTamanio() * 12 / 75));
                g.drawString(getEtiqueta(), pix + (20), (piy+ 30));

                break;
            case 'c':

                img = Toolkit.getDefaultToolkit().getImage("src/imagenes/rombo.png");
                g2d.drawImage(img, pix-20, piy-20, null, null);
                g.setColor(Color.BLACK);
                g.setFont(new Font("TimesRoman", Font.BOLD, getTamanio() * 12 / 75));
                g.drawString(getEtiqueta(), pix + (20), (piy+ 40));
                break;
        }

    }// fin pintar()

    //toString()
    @Override
    public String toString() {
        return getEtiqueta();
    }

    public String getTablaPertenece() {
        return tablaPertenece;
    }

    public void setTablaPertenece(String tablaPertenece) {
        this.tablaPertenece = tablaPertenece;
    }

}//***fin Nodo***
