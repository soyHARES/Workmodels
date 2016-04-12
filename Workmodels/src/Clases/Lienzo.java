package Clases;

/**
 *
 */
import GUI.Frm_Principal;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public final class Lienzo extends JPanel implements MouseListener, MouseMotionListener {

    private Grafico grafo;
    private java.util.List listaNodos;
    private Color colorArcos;
    private short tamanioNodos;
    private Jscroll tab = null;
    public ArrayList<Flecha> fle = new ArrayList<Flecha>();
    private Nodo nodA = null;//nodo actual
    public Flecha flecha = null;
    boolean destino_encontrado = false;
    private java.util.List listatablas;
    private int offsetX, offsetY;
    private Point click = new Point();

    public static boolean modified = false;
    private int xDiagrama = 0;
    private int yDiagrama = 0;

    public Lienzo() {
    }

    //Constructor
    public Lienzo(Grafico graf, short tam, Color ca) {
        setGrafo(graf);
        this.listaNodos = graf.getListaNodos();
        this.tamanioNodos = tam;
        this.colorArcos = ca;
        this.listatablas = graf.getListatablas();
        addMouseListener(this);
        addMouseMotionListener(this);
        setBackground(Color.WHITE);
        repintarLienzo();

    }
    //fin Constructor

    @Override
    public void mouseExited(MouseEvent evt) {
    }

    @Override
    public void mouseEntered(MouseEvent evt) {
    }

    @Override
    public void mouseClicked(MouseEvent evt) {

    }

    @Override
    public void mouseMoved(MouseEvent evt) {
    }

    /**
     *
     * @param evt recibe click izquierdo del evento que se va a ejecutar
     */
    @Override
    public void mousePressed(MouseEvent evt) {

        boolean encontrado = false;

        int x = evt.getX(); //obtener pos. del mouse.
        int y = evt.getY();
        encontrado = false;
        ListIterator it = listaNodos.listIterator(listaNodos.size());

        while (it.hasPrevious() && encontrado == false) {
            nodA = (Nodo) it.previous();

            //si se da click dentro de un Nodo..
            if (x >= nodA.getPuntoInicial().x && x < nodA.
                    getPuntoInicial().x + nodA.getTamanio()
                    && y >= nodA.getPuntoInicial().y && y < nodA.
                    getPuntoInicial().y + nodA.getTamanio()) {
                encontrado = true;

                offsetX = x - nodA.getPuntoInicial().x;
                offsetY = y - nodA.getPuntoInicial().y;

            }
        }//fin while

        repintarLienzo();
//        }
    }

    /**
     *
     * @param evt este evento capta la tecla derecha que se va ejecutar
     */
    public static boolean activar = false;

    @Override

    public void mouseDragged(MouseEvent evt) {

            grafo.setModificado(true);
            int x = evt.getX() - offsetX;
            int y = evt.getY() - offsetY;
            if (x < 0) {
                x = 0;
            }
            if (y < 0) {
                y = 0;
            }
            try {

                nodA.setPuntoInicial(new Point(x, y));

                //agrandar el lienzo.
                if (nodA.getPuntoInicial().x + nodA.getTamanio() > getWidth()) {
                    setPreferredSize(new Dimension(
                            x + nodA.getTamanio(), getHeight()));
                    revalidate();
                }
                if (nodA.getPuntoInicial().y + nodA.getTamanio() > getHeight()) {
                    setPreferredSize(new Dimension(
                            getWidth(), y + nodA.getTamanio()));
                    revalidate();
                }

                repintarLienzo();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Imposible! No existe un modelo");
            }
        
    }//fin mouseDragged()

    /**
     *
     * @param evt este evento agrega el arco
     */
    @Override
    public void mouseReleased(MouseEvent evt) {

//        nodP = nodA;
//        flecha = null;
//        nod1 = null;
//        nod2 = null;
//        destino_encontrado = false;
//        repintarLienzo();
    }

    /**
     *
     * @param evento este evento traza la linea en el panel principal
     */
    /**
     * captura el nodo cuando se va dibujar en el panel
     */
    /**
     * destruye todo el dibujo que si dibujo en el panel
     */
    public void destruir() {
        nodA = null;
//        nodP = null;
//        nod1 = null;
//        nod2 = null;
        listaNodos = null;
        grafo = null;
    }//fin destruir()

    //crear()
    public void crear(Grafico g) {
        setGrafo(g);
        listaNodos = grafo.getListaNodos();
    }//fin crear()

    /**
     * actualiza el panel cada que se dibuja
     */
    public void repintarLienzo() {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        repaint();
                    }
                }
        );
    }

    public void repintar() {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                       getParent().validate();
                    }
                }
        );
    }
    //fin repintarLienzo()

    /**
     *
     * @param g recibe un grafo e cual se va pintar la fecha
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (grafo != null) {
            grafo.pintar(g);
            repintarLienzo();
        }

        if (flecha != null) {
            ListIterator fl = fle.listIterator(fle.
                    size());
            while (fl.hasPrevious()) {
                flecha = (Flecha) fl.previous();

                flecha.pintar(g);
                repintarLienzo();
            }
        }
    }
    //fin paintComponent()

    /**
     * Genera ER: imprime atributos y entidades.
     *
     * @param entidad
     * @param atributos
     */
    public void generaEntidadAtributo(ArrayList<Entidad> entidad, ArrayList<Atributos> atributos) {
        Point posicionEntidades;
        Point posicionAtributos;
        Point posicionER;
        Point aux;

        int margenER = 0;
        int margenEx = 0;
        int margenEy = 0;
        int margenAx = 0;
        int margenAy = 0;
        String figura;
        for (int i = 0; i < entidad.size(); i++) {//obtener entidades  

            if (entidad.get(i).isRelacional()) {
                margenER += 110;
                posicionER = new Point(margenER * 2, 250);
                figura = "condicion";
                aux = posicionER;
            } else {
                figura = "entidad";
                margenEx += 80;
                margenEy = 150;
                posicionEntidades = new Point(margenEx * 2, margenEy);
                aux = posicionEntidades;
            }

            grafo.agregarNodo(entidad.get(i).getTitle().toUpperCase(), figura, aux, tamanioNodos,
                    null, null);

        }

        for (int i = 0; i < atributos.size(); i++) {
            for (int j = 0; j < atributos.get(i).getAtributosNormales().size(); j++) {

                if (atributos.get(i).getIdentificadorTabla().equalsIgnoreCase(grafo.nodos.get(i).getEtiqueta())) {

                    if (grafo.nodos.get(i).getTipoFigura().equalsIgnoreCase("entidad")) {

                        margenAx += 35;
                        margenAy = 50;

                    } else {
                        margenAy = 350;
                        margenAx -= 35;

                    }
                }
                posicionAtributos = new Point(margenAx * 2, margenAy);
                grafo.agregarNodo(atributos.get(i).getAtributosNormales().get(j).toLowerCase(), "atributo", posicionAtributos, tamanioNodos,
                        null, atributos.get(i).getIdentificadorTabla());

            }

        }

        //redimenciona el lienzo
        xDiagrama = margenAx + 100;
        yDiagrama = margenEy + 100;
    }

    /**
     * Genera ER: relacions entre entidades
     *
     * @param relaciones
     * @param entidades
     */
    public void generaEntidadRelacion(ArrayList<Relaciones> relaciones, ArrayList<Entidad> entidades) {

        System.out.println("------------------relaciones-------------------");
        for (int i = 0; i < relaciones.size(); i++) {
            System.out.println(relaciones.get(i).getEntidad1() + " " + relaciones.get(i).getRelacion() + " " + relaciones.get(i).getEntidad2());
            trazarEnlaces(relaciones.get(i).getEntidad1().toUpperCase(), relaciones.get(i).getEntidad2().toUpperCase(), relaciones.get(i).getRelacion(), entidades);
        }

    }

    /**
     * enlaza figuras
     *
     * @param E1
     * @param E2
     * @param tipoRelacion
     * @param entidades
     *
     */
    public void trazarEnlaces(String E1, String E2, String tipoRelacion, ArrayList<Entidad> entidades) {
        for (int x = 0; x < grafo.nodos.size(); x++) {

            for (int y = 0; y < grafo.nodos.size(); y++) {

                try {
                    //atributos
                    if (grafo.nodos.get(x).getEtiqueta().equalsIgnoreCase(grafo.nodos.get(y).getTablaPertenece())
                            && !grafo.nodos.get(x).getTipoFigura().equalsIgnoreCase(grafo.nodos.get(y).getTipoFigura())
                            && (grafo.nodos.get(x).getTipoFigura().equalsIgnoreCase("atributo")
                            || grafo.nodos.get(y).getTipoFigura().equalsIgnoreCase("atributo"))) {

                        grafo.agregarArco(false, grafo.nodos.get(x).getId(), grafo.nodos.get(y).getId(), "", colorArcos, false);

                    } else {

                        try {
                            // Entidad relacion
                            if ((grafo.nodos.get(x).getEtiqueta().equals(E1))
                                    && (grafo.nodos.get(y).getEtiqueta().equals(E2))) {

                                grafo.agregarArco(false, grafo.nodos.get(x).getId(), grafo.nodos.get(y).getId(), tipoRelacion,
                                        Color.blue, false);

                            }

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "error trazar enlace relacional: " + e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error trazar enlace: " + e.getMessage());
                }

            }
        }
    }

    /**
     * Genera ER: IMPRIME EL DIAGRAMA
     *
     * @param entidad
     * @param atributos
     * @param relaciones
     */
    public void generaER(ArrayList<Entidad> entidad, ArrayList<Atributos> atributos, ArrayList<Relaciones> relaciones) {

        generaEntidadAtributo(entidad, atributos);
        generaEntidadRelacion(relaciones, entidad);

        repintarLienzo();
        JOptionPane.showMessageDialog(null, "DIAGRAMA ENTIDAD RELACION");

    }
    boolean encontrar = true;

    public void fle() {
        for (int i = 0; i < Frm_Principal.relaciones.size(); i++) {

            ListIterator it = listatablas.listIterator(listatablas.
                    size());

            while (it.hasPrevious()) {
                tab = (Jscroll) it.previous();

                if (Frm_Principal.relaciones.get(i).equals(tab.getId())) {

                    if (encontrar == true) {

                        flecha = new Flecha(new Point(tab.getPunto_inicial().x, tab.getPunto_inicial().y));

                        encontrar = false;

                    } else {

                        flecha.setPf(new Point(tab.getPunto_inicial().x, tab.getPunto_inicial().y));
                              //Etiqueta();

                        if (flecha != null) {

                            fle.add(flecha);
                        }

                        encontrar = true;
                    }

                } else {

                    if (Frm_Principal.relaciones.get(i).contains(":")) {
                        flecha.setEtiqueta(Frm_Principal.relaciones.get(i));

                    }
                }

            }

        }

    }

    public int conta = 0;

    public Point generarlocalizcion() {

        Point puntos = new Point();
        puntos.x = conta * 300;
        puntos.y = conta + 100;
        conta++;
        return puntos;

    }
    public int dimensionx;
    public int dimensiony;

    public void buscarpuntos() {

        ListIterator it = listatablas.listIterator(listatablas.
                size());

        while (it.hasPrevious()) {
            tab = (Jscroll) it.previous();

            if (tab.getPunto_inicial().x + 200 > getWidth() || tab.getPunto_inicial().y > getHeight()) {
                dimensionx = tab.getPunto_inicial().x + 200;
                dimensiony = tab.getPunto_inicial().y + 200;

            }
        }

    }
    ArrayList<String> guardar = new ArrayList<String>();

    public void InsertarTabla(String tabla) {

        guardar.add("primero");
        for (int i = 0; i <= guardar.size(); i++) {

            if (tabla.equals(guardar.get(i))) {

                break;
            } else {

                Jtable table = new Jtable();

                DefaultTableModel modelo = (DefaultTableModel) table.getModel();
                guardar.add(i, tabla);
                modelo.addColumn(guardar.get(i));
                modelo.setRowCount(modelo.getRowCount() + 1);
                //JOptionPane.showMessageDialog(null,Atributos);
                crearatributos(modelo, tabla);
                this.setLayout(null);
                this.add(grafo.agregartabla(tabla, generarlocalizcion(), table, this));
                break;
            }

        }
    }

    public void crearatributos(DefaultTableModel modelo, String tabla) {

        int contador = 0;

        for (int j = 0; j < modelo.getRowCount(); j++) {
            if (modelo.getValueAt(0, 0) == null) {

                for (int i = 0; i < Frm_Principal.datosFull.size(); i++) {
                    if (Frm_Principal.datosFull.get(i).getNombreTabla().equals(tabla)) {
                        modelo.setRowCount(modelo.getRowCount() + 1);

                        modelo.setValueAt(Frm_Principal.datosFull.get(i).getNombreAtributo(), contador, 0);
                        contador++;
                    }
                }
                break;
            }
        }

    }

    synchronized public void Diagramar() {

        for (int i = 0; i < Frm_Principal.datosFull.size(); i++) {

            InsertarTabla(Frm_Principal.datosFull.get(i).getNombreTabla());
        }
        fle();
    }

    public void asignarposicion(Point puntos, String Nombretabla) {

        ListIterator it = listatablas.listIterator(listatablas.
                size());

        while (it.hasPrevious()) {
            tab = (Jscroll) it.previous();
            if (tab.getId().equals(Nombretabla)) {
 Point  p = SwingUtilities.convertPoint( tab,  puntos, this );
                ListIterator f = fle.listIterator(fle.
                        size());
                while (f.hasPrevious()) {
                    flecha = (Flecha) f.previous();

                    if (tab.getPunto_inicial().equals(flecha.getPi())) {

                        flecha.setPi(p);
                        repintarLienzo();
                    } else {

                        if (tab.getPunto_inicial().equals(flecha.getPf())) {

                            flecha.setPf(p);
                            repintarLienzo();
                        }

                    }

                }
                 tab.setPunto_inicial(p);
              repintarLienzo();
           
            tab.setLocation(p.x,p.y);
                repintar();
              
                

            }

        }

    }

    //Mutators

    public void setGrafo(Grafico graf) {
        this.grafo = graf;
    }

    public Nodo getSeleccionado() {
        return nodA;
    }

    public void setSeleccionado(Nodo n) {
        nodA = n;
    }

    public int getxDiagrama() {
        return xDiagrama;
    }

    public void setxDiagrama(int xDiagrama) {
        this.xDiagrama = xDiagrama;
    }

    public int getyDiagrama() {
        return yDiagrama;
    }

    public void setyDiagrama(int yDiagrama) {
        this.yDiagrama = yDiagrama;
    }

}//***fin Lienzo***	
