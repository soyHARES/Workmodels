package GUI;

import Clases.Atributos;
import Clases.Entidad;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import Clases.Funciones;
import Clases.Grafico;
import Clases.HiloProgres;
import Clases.Imagen;
import Clases.Lienzo;
import Clases.Nodo;
import Clases.Relaciones;
import javax.swing.JTable;
import Clases.TablasDatos;
import de.javasoft.plaf.synthetica.SyntheticaOrangeMetallicLookAndFeel;
import java.awt.Dimension;
import java.io.File;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import Clases.EscrituraDatos;
import Clases.LecturaDatos;

/**
 *
 *
 */
public class Frm_Principal extends javax.swing.JFrame {

    private short tamanioNodos = 60;
    private java.util.List listaNodos;
    private Grafico grafic;//area para pintar.
    private Lienzo zonaPintar;
    int fila = 0;
    boolean isRelacional = false;
    /**
     * Creates new form GUI
     */
    int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

    /**
     * array list que va a almacenar entidades y atributos con forme lo van
     * agregando
     */
   
    public static ArrayList<String> relacionales = new ArrayList<String>();
    public static ArrayList<Relaciones> relation = new ArrayList<Relaciones>();
    public static ArrayList<TablasDatos> datosFull = new ArrayList<TablasDatos>();
    public static ArrayList<String> relaciones = new ArrayList<String>();
    ArrayList<Entidad> Ent_atrib_key = new ArrayList();
    Funciones funcion = new Funciones();

    /**
     * donde iniciamos los compones iniciamos los que se a mostrar en la
     * pantalla principal y os cambios que vamos a efectuar
     */
    public Frm_Principal() {

        initComponents();
        this.setExtendedState(Frm_Principal.MAXIMIZED_BOTH);
        this.setBounds(0, 0, ancho, alto);
        Jtp_interfaz.setBounds(0, 120, ancho, alto);
        jP_Relacional.setBounds(0, 120, ancho, alto);
        Imagen Imagen = new Imagen("cuadricula.png", ancho, alto);
        jpRelacionGraficaRelacional.add(Imagen);
        jpRelacionGraficaRelacional.repaint();
        Imagen img = new Imagen("workmodels-fondoDorado.png", 1366, 700);
        Jp_informacion.add(img);
        Jp_informacion.repaint();

        grafic = new Grafico();
        listaNodos = grafic.getListaNodos();
        zonaPintar = new Lienzo(grafic, tamanioNodos, Color.BLACK);

        zonaPintar.repintarLienzo();

        jpRelacionGraficaRelacional.setViewportView(zonaPintar);

    }

    //----------------------------- funciones-------------------------------
  
    /**
     * esta funcion la hacemos con el fin de que cuando agregamos un nodo al
     * panel esta refreque el panel
     */
    private void repintarLienzo() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                zonaPintar.repaint();
            }
        });
    }
  

    public void relaciones() {
        DefaultTableModel modelo = (DefaultTableModel) jtable_relaciones.getModel();
        for (int j = 0; j < modelo.getRowCount(); j++) {
            for (int i = 0; i < modelo.getColumnCount(); i++) {

                relaciones.add((String) modelo.getValueAt(j, i));

            }

        }

    }

    public void listaEntidades() {

        DefaultTableModel modelo = (DefaultTableModel) jtable_Principal.getModel();
        for (int i = 0; i < modelo.getColumnCount(); i++) {

            //JOptionPane.showMessageDialog(null,modelo.getColumnName(i));
            for (int j = 0; j < modelo.getRowCount(); j++) {
                if (modelo.getValueAt(j, i) != null) {
                    TablasDatos tab = new TablasDatos();
                    tab.setNombreTabla(modelo.getColumnName(i));
                    tab.setNombreAtributo((String) modelo.getValueAt(j, i));

                    datosFull.add(tab);
                }
            }

        }

    }



    private static boolean isKey() {
        if (jtable_Principal.getValueAt(jtable_Principal.getSelectedRow(), jtable_Principal.getSelectedColumn()) != null) {
            String ke = jtable_Principal.getValueAt(jtable_Principal.getSelectedRow(), jtable_Principal.getSelectedColumn()).toString();
            if (ke.length() > 5) {
                if (ke.substring(0, 5).equals("(KEY)")) {
                    return true;
                }
            }
        }
        return false;
    }

    private void eliminarAtributo() {
        try {
            String[] j = new String[jtable_Principal.getRowCount()];
            int cont = 0;
            for (int i = 0; i < jtable_Principal.getRowCount(); i++) {
                if (jtable_Principal.getValueAt(i, jtable_Principal.getSelectedColumn()) != null) {
                    if (i != jtable_Principal.getSelectedRow()) {
                        j[cont] = jtable_Principal.getValueAt(i, jtable_Principal.getSelectedColumn()).toString();
                        cont += 1;
                    }
                }
            }
            imprimir(j);
            JOptionPane.showMessageDialog(jP_modelado, "Atrinuto eliminado correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(jP_modelado, "Error al agregar atributo");
        }
    }

    private void imprimir(String[] j) {
        for (int i = 0; i < jtable_Principal.getRowCount(); i++) {
            if (i <= j.length) {
                jtable_Principal.setValueAt(j[i], i, jtable_Principal.getSelectedColumn());

            }
        }
    }

    /**
     * funcion para contar los atributos por tablas
     *
     * @return
     */
    public static int contarAtributos3() {
        int contador = 0;
        int mayor = 0;
        for (int i = 0; i < jtable_Principal.getColumnCount(); i++) {
            if (jtable_Principal.getColumnName(i).equals(jcb_entidadesIngresadas.getSelectedItem())) {
                for (int j = 0; j < jtable_Principal.getRowCount(); j++) {
                    if (i >= 0) {
                        if (jtable_Principal.getValueAt(j, i) != null) {
                            contador++;
                        }
                    }
                }
                if (mayor < contador) {
                    mayor = contador;
                }
                contador = 0;
            }
        }
        return mayor;
    }

    private ArrayList<Entidad> llenarEntidades() {
        ArrayList<Entidad> ent = null;
        try {

            ent = new ArrayList<Entidad>();
            DefaultTableModel modelo = (DefaultTableModel) jtable_Principal.getModel();
            Entidad nuevaEntidad = null;
            for (int i = 0; i < jtable_Principal.getColumnCount(); i++) {

                if (isRelacional(modelo.getColumnName(i))) {
                    nuevaEntidad = new Entidad(jtable_Principal.getColumnName(i), true);
                } else {
                    nuevaEntidad = new Entidad(jtable_Principal.getColumnName(i), false);
                }
                ent.add(nuevaEntidad);
            }
            for (int i = 0; i < ent.size(); i++) {
                System.out.println("xD  " + ent.get(i));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ingrese datos antes!");
        }
        return ent;
    }

    public boolean isRelacional(String d) {
        for (int j = 0; j < relacionales.size(); j++) {
            if (relacionales.get(j).equals(d)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isKeySelected(String a) {
        if (a.length() > 5) {
            if (a.substring(0, 5).equals("(PK) ")) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Atributos> llenarAtributos() {
        ArrayList<Atributos> atributos = null;
        try {

            atributos = new ArrayList<Atributos>();
            for (int i = 0; i < jtable_Principal.getColumnCount(); i++) {
                Atributos atributo = new Atributos();
                atributo.setIdentificadorTabla(jtable_Principal.getColumnName(i));
                ArrayList<String> atributosNormales = new ArrayList<String>();
                for (int j = 0; j < jtable_Principal.getRowCount(); j++) {
                    if (jtable_Principal.getValueAt(j, i) != null) {
                        atributosNormales.add(jtable_Principal.getValueAt(j, i).toString());
                    }
                }
                atributo.setAtributosNormales(atributosNormales);

                atributos.add(atributo);
            }
        } catch (Exception e) {
        }
        return atributos;
    }

    private void agregarLlave() {
        try {
            String f = jtable_Principal.getValueAt(jtable_Principal.getSelectedRow(), jtable_Principal.getSelectedColumn()).toString();
            jtable_Principal.setValueAt("(PK) " + f, jtable_Principal.getSelectedRow(), jtable_Principal.getSelectedColumn());
            JOptionPane.showMessageDialog(jP_modelado, "Agregada corretamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(jP_modelado, "Error al asignar PK");
        }
    }

    private void eliminarKey() {
        try {
            if (jtable_Principal.getValueAt(jtable_Principal.getSelectedRow(),
                    jtable_Principal.getSelectedColumn()) != null) {
                if (isKey()) {
                    String ke = jtable_Principal.getValueAt(jtable_Principal.getSelectedRow(),
                            jtable_Principal.getSelectedColumn()).toString();
                    if (ke.length() > 5) {
                        if (ke.substring(0, 5).equals("(PK) ")) {
                            jtable_Principal.setValueAt(ke.substring(5, ke.length()),
                                    jtable_Principal.getSelectedRow(), jtable_Principal.getSelectedColumn());
                            JOptionPane.showMessageDialog(jP_modelado, "PK eliminada correctamente", "INFORMACION", WIDTH);
                        }
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(jP_modelado, "Error al eliminar PK" + e.getMessage());
        }

    }

    public boolean enviarDatosFichero(String file, int ida) {
        System.out.println("funcion de enviar datos fichero ");
        ArrayList<Entidad> entidades;
        entidades = llenarEntidades();
        ArrayList<Atributos> atributos;
        atributos = llenarAtributos();
        EscrituraDatos escribir = new EscrituraDatos();
        return (escribir.escribaRelacional(entidades, atributos, relation, file, ida));
    }

    public void cerrar() {
        Object[] opciones = {"Aceptar", "Cancelar"};
        int eleccion = JOptionPane.showOptionDialog(rootPane, "En realidad desea cerrar la aplicacion", "Mensaje",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, "Aceptar");
        if (eleccion == JOptionPane.YES_OPTION) {
            System.exit(0);
        } else {
        }
    }

//----------------------- end funciones-------------------------------------
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    /**
     * este metodo prm necesita de un origen para efectuar su recorrido luego de
     * esto recorremos la lista de nodos para verificar dicho nodo luego se
     * direge a la clase grafos efectua el metodo
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jDialog2 = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        Jtp_interfaz = new javax.swing.JTabbedPane();
        Jp_informacion = new javax.swing.JPanel();
        jP_modelado = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtable_Principal = new JTable(){

            public boolean isCellEditable(int rowIndex, int colIndex) {

                return false; //Las celdas no son editables.

            }
        };
        ;
        jtb_sub = new javax.swing.JTabbedPane();
        jp_datos = new javax.swing.JPanel();
        jbtn_add_Atributos = new javax.swing.JButton();
        jbtn_add_table = new javax.swing.JButton();
        txt_datoEntrada = new javax.swing.JTextField();
        jcb_entidadesIngresadas = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jcb_tipoModelo = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jbtn_continuar = new javax.swing.JButton();
        rbd_Entiddad = new javax.swing.JRadioButton();
        rbd_Relacion = new javax.swing.JRadioButton();
        jp_relaciones = new javax.swing.JPanel();
        jcb_entidades2 = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtable_relaciones = new javax.swing.JTable();
        jbt_relacionar = new javax.swing.JButton();
        jcb_relacioesEntidades = new javax.swing.JComboBox();
        jcb_entidades1 = new javax.swing.JComboBox();
        jbtn_hecho = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jP_Relacional = new javax.swing.JPanel();
        jpRelacionGraficaRelacional = new javax.swing.JScrollPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jmi_lienzo = new javax.swing.JMenuItem();
        jmi_datos = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        jDialog1.setTitle("Ayuda ");
        jDialog1.setModal(true);
        jDialog1.setResizable(false);

        jPanel4.setBackground(new java.awt.Color(153, 255, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setText("Como usar el software:");

        jLabel6.setText("Para agregar un nuevo nodo, de clic sobre el panel");

        jLabel8.setText("Para las aristas, de clic derecho sostenido sobre el ");

        jLabel9.setText("nodo origen y arrastrelo hasta el nodo destino");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addContainerGap(176, Short.MAX_VALUE))
        );

        jDialog1.getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        jDialog2.setModal(true);
        jDialog2.setResizable(false);

        jPanel5.setBackground(new java.awt.Color(153, 255, 204));

        jLabel10.setText("Desarrollado por:");

        jLabel11.setText("Jonatan de la ossa Muñoz");

        jLabel12.setText("Anibal A. Galvan Galeano");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10))
                .addContainerGap(93, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jDialog2.getContentPane().add(jPanel5, java.awt.BorderLayout.CENTER);

        jMenuItem2.setText("Ingresar Primary Key");
        jMenuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem2MousePressed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        jMenuItem4.setText("Eliminar");
        jMenuItem4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem4MousePressed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem4);

        jMenuItem5.setText("Borrar Primary Key");
        jMenuItem5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem5MousePressed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem5);

        jMenuItem6.setText("Eliminar");
        jMenuItem6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem6MousePressed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem6);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Workmodels 1.0");
        setMinimumSize(new java.awt.Dimension(600, 325));

        Jtp_interfaz.setPreferredSize(new java.awt.Dimension(1360, 760));
        Jtp_interfaz.setRequestFocusEnabled(false);

        javax.swing.GroupLayout Jp_informacionLayout = new javax.swing.GroupLayout(Jp_informacion);
        Jp_informacion.setLayout(Jp_informacionLayout);
        Jp_informacionLayout.setHorizontalGroup(
            Jp_informacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1355, Short.MAX_VALUE)
        );
        Jp_informacionLayout.setVerticalGroup(
            Jp_informacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 732, Short.MAX_VALUE)
        );

        Jtp_interfaz.addTab("Principal", Jp_informacion);

        jP_modelado.setBackground(new java.awt.Color(204, 102, 0));
        jP_modelado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jP_modeladoMousePressed(evt);
            }
        });

        jtable_Principal.setBorder(new javax.swing.border.MatteBorder(null));
        jtable_Principal.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jtable_Principal.setForeground(new java.awt.Color(102, 51, 0));
        jtable_Principal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        jtable_Principal.setToolTipText("Tabla de Datos");
        jtable_Principal.setSelectionBackground(new java.awt.Color(240, 240, 240));
        jtable_Principal.setSelectionForeground(new java.awt.Color(204, 0, 0));
        jtable_Principal.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jtable_Principal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jtable_PrincipalMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtable_Principal);

        jtb_sub.setBackground(new java.awt.Color(204, 204, 204));
        jtb_sub.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jp_datos.setBackground(new java.awt.Color(255, 255, 255));
        jp_datos.setFont(new java.awt.Font("Javanese Text", 0, 18)); // NOI18N

        jbtn_add_Atributos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add-row2.png"))); // NOI18N
        jbtn_add_Atributos.setToolTipText("Agragar Atributo");
        jbtn_add_Atributos.setBorderPainted(false);
        jbtn_add_Atributos.setContentAreaFilled(false);
        jbtn_add_Atributos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jbtn_add_Atributos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add-row1.png"))); // NOI18N
        jbtn_add_Atributos.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add-row3.png"))); // NOI18N
        jbtn_add_Atributos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtn_add_Atributos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_add_AtributosActionPerformed(evt);
            }
        });

        jbtn_add_table.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add-table2.png"))); // NOI18N
        jbtn_add_table.setToolTipText("Agregar Entidad");
        jbtn_add_table.setBorderPainted(false);
        jbtn_add_table.setContentAreaFilled(false);
        jbtn_add_table.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jbtn_add_table.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add-table1.png"))); // NOI18N
        jbtn_add_table.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add-table3.png"))); // NOI18N
        jbtn_add_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_add_tableActionPerformed(evt);
            }
        });

        txt_datoEntrada.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        txt_datoEntrada.setToolTipText("Ingrese un dato (Entidad , Atributo)");
        txt_datoEntrada.setCaretColor(new java.awt.Color(204, 102, 0));
        txt_datoEntrada.setDisabledTextColor(new java.awt.Color(204, 102, 0));
        txt_datoEntrada.setMaximumSize(new java.awt.Dimension(10, 10));
        txt_datoEntrada.setName(""); // NOI18N
        txt_datoEntrada.setSelectionColor(new java.awt.Color(153, 51, 0));
        txt_datoEntrada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_datoEntradaKeyTyped(evt);
            }
        });

        jcb_entidadesIngresadas.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jcb_entidadesIngresadas.setForeground(new java.awt.Color(102, 51, 0));
        jcb_entidadesIngresadas.setToolTipText("Entidades en el sistema");

        jLabel7.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 51, 0));
        jLabel7.setText("Tipo de Modelo:");

        jLabel2.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 51, 0));
        jLabel2.setText("Entidad:");

        jcb_tipoModelo.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jcb_tipoModelo.setForeground(new java.awt.Color(102, 51, 0));
        jcb_tipoModelo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Entidad Relacion", "Relacional" }));
        jcb_tipoModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcb_tipoModeloActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 51, 0));
        jLabel15.setText("Dato:");

        jbtn_continuar.setBackground(new java.awt.Color(153, 153, 0));
        jbtn_continuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nex2t.png"))); // NOI18N
        jbtn_continuar.setToolTipText("Continuar");
        jbtn_continuar.setBorderPainted(false);
        jbtn_continuar.setContentAreaFilled(false);
        jbtn_continuar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jbtn_continuar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/next1.png"))); // NOI18N
        jbtn_continuar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nex3t.png"))); // NOI18N
        jbtn_continuar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jbtn_continuar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtn_continuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_continuarActionPerformed(evt);
            }
        });

        rbd_Entiddad.setBackground(new java.awt.Color(255, 255, 255));
        rbd_Entiddad.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        rbd_Entiddad.setForeground(new java.awt.Color(102, 51, 0));
        rbd_Entiddad.setSelected(true);
        rbd_Entiddad.setText("Entidad");
        rbd_Entiddad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbd_EntiddadActionPerformed(evt);
            }
        });

        rbd_Relacion.setBackground(new java.awt.Color(255, 255, 255));
        rbd_Relacion.setFont(new java.awt.Font("Georgia", 0, 14)); // NOI18N
        rbd_Relacion.setForeground(new java.awt.Color(102, 51, 0));
        rbd_Relacion.setText("Entidad Relacional");
        rbd_Relacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbd_RelacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jp_datosLayout = new javax.swing.GroupLayout(jp_datos);
        jp_datos.setLayout(jp_datosLayout);
        jp_datosLayout.setHorizontalGroup(
            jp_datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_datosLayout.createSequentialGroup()
                .addGroup(jp_datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp_datosLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jp_datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(70, 70, 70))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jp_datosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jp_datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_datoEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jcb_entidadesIngresadas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jcb_tipoModelo, 0, 344, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jp_datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtn_add_table, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtn_add_Atributos, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jp_datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbd_Entiddad, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbd_Relacion))
                .addGap(212, 212, 212)
                .addComponent(jbtn_continuar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(207, 207, 207))
        );
        jp_datosLayout.setVerticalGroup(
            jp_datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_datosLayout.createSequentialGroup()
                .addGroup(jp_datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp_datosLayout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(jp_datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jp_datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jp_datosLayout.createSequentialGroup()
                                    .addGroup(jp_datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jbtn_add_table)
                                        .addGroup(jp_datosLayout.createSequentialGroup()
                                            .addGap(9, 9, 9)
                                            .addComponent(rbd_Entiddad, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(rbd_Relacion, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(7, 7, 7)
                                    .addComponent(jbtn_add_Atributos))
                                .addGroup(jp_datosLayout.createSequentialGroup()
                                    .addGap(9, 9, 9)
                                    .addComponent(jbtn_continuar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jp_datosLayout.createSequentialGroup()
                                .addGroup(jp_datosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jcb_tipoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jcb_entidadesIngresadas, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(txt_datoEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jp_datosLayout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(120, Short.MAX_VALUE))
        );

        jtb_sub.addTab("Modelar", jp_datos);

        jp_relaciones.setBackground(new java.awt.Color(255, 255, 255));
        jp_relaciones.setBorder(new javax.swing.border.MatteBorder(null));

        jcb_entidades2.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jcb_entidades2.setForeground(new java.awt.Color(102, 51, 0));
        jcb_entidades2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ninguna" }));

        jtable_relaciones.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jtable_relaciones.setForeground(new java.awt.Color(102, 51, 0));
        jtable_relaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Entidad", "Relacion", "Entidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtable_relaciones.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jtable_relaciones.setEnabled(false);
        jtable_relaciones.setSelectionBackground(new java.awt.Color(204, 102, 0));
        jScrollPane2.setViewportView(jtable_relaciones);

        jbt_relacionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add-relation2.png"))); // NOI18N
        jbt_relacionar.setToolTipText("Agregar relación");
        jbt_relacionar.setBorder(null);
        jbt_relacionar.setBorderPainted(false);
        jbt_relacionar.setContentAreaFilled(false);
        jbt_relacionar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jbt_relacionar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add-relation1.png"))); // NOI18N
        jbt_relacionar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add-relation3.png"))); // NOI18N
        jbt_relacionar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jbt_relacionar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbt_relacionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt_relacionarActionPerformed(evt);
            }
        });

        jcb_relacioesEntidades.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jcb_relacioesEntidades.setForeground(new java.awt.Color(102, 51, 0));
        jcb_relacioesEntidades.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ninguna", "1:1", "1:N", "N:M" }));

        jcb_entidades1.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jcb_entidades1.setForeground(new java.awt.Color(102, 51, 0));
        jcb_entidades1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ninguna" }));

        jbtn_hecho.setBackground(new java.awt.Color(153, 0, 0));
        jbtn_hecho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nex2t.png"))); // NOI18N
        jbtn_hecho.setToolTipText("DIAGRAMA");
        jbtn_hecho.setBorderPainted(false);
        jbtn_hecho.setContentAreaFilled(false);
        jbtn_hecho.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jbtn_hecho.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/next1.png"))); // NOI18N
        jbtn_hecho.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nex3t.png"))); // NOI18N
        jbtn_hecho.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jbtn_hecho.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtn_hecho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_hechoActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 51, 0));
        jLabel17.setText("Entidad");

        jLabel18.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 51, 0));
        jLabel18.setText("Tipo Relacion");

        jLabel19.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 51, 0));
        jLabel19.setText("Entidad");

        javax.swing.GroupLayout jp_relacionesLayout = new javax.swing.GroupLayout(jp_relaciones);
        jp_relaciones.setLayout(jp_relacionesLayout);
        jp_relacionesLayout.setHorizontalGroup(
            jp_relacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_relacionesLayout.createSequentialGroup()
                .addGroup(jp_relacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp_relacionesLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jp_relacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jp_relacionesLayout.createSequentialGroup()
                                .addComponent(jcb_entidades1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jp_relacionesLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jp_relacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcb_relacioesEntidades, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jp_relacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jp_relacionesLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jcb_entidades2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jp_relacionesLayout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jp_relacionesLayout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(jbt_relacionar, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jbtn_hecho)
                .addGap(114, 114, 114))
        );
        jp_relacionesLayout.setVerticalGroup(
            jp_relacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_relacionesLayout.createSequentialGroup()
                .addGroup(jp_relacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jp_relacionesLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(jp_relacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jp_relacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcb_entidades1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcb_relacioesEntidades, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcb_entidades2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbt_relacionar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jp_relacionesLayout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jbtn_hecho, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jp_relacionesLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(67, Short.MAX_VALUE))
        );

        jtb_sub.addTab("Relacionar", jp_relaciones);

        javax.swing.GroupLayout jP_modeladoLayout = new javax.swing.GroupLayout(jP_modelado);
        jP_modelado.setLayout(jP_modeladoLayout);
        jP_modeladoLayout.setHorizontalGroup(
            jP_modeladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jP_modeladoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jP_modeladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jtb_sub, javax.swing.GroupLayout.PREFERRED_SIZE, 1335, Short.MAX_VALUE))
                .addContainerGap())
        );
        jP_modeladoLayout.setVerticalGroup(
            jP_modeladoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jP_modeladoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtb_sub))
        );

        Jtp_interfaz.addTab("Modelado", jP_modelado);

        jpRelacionGraficaRelacional.setBackground(new java.awt.Color(255, 255, 255));
        jpRelacionGraficaRelacional.setViewportBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jpRelacionGraficaRelacional.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jpRelacionGraficaRelacional.setInheritsPopupMenu(true);
        jpRelacionGraficaRelacional.setMaximumSize(new java.awt.Dimension(500, 500));

        javax.swing.GroupLayout jP_RelacionalLayout = new javax.swing.GroupLayout(jP_Relacional);
        jP_Relacional.setLayout(jP_RelacionalLayout);
        jP_RelacionalLayout.setHorizontalGroup(
            jP_RelacionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jP_RelacionalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpRelacionGraficaRelacional, javax.swing.GroupLayout.DEFAULT_SIZE, 1335, Short.MAX_VALUE)
                .addContainerGap())
        );
        jP_RelacionalLayout.setVerticalGroup(
            jP_RelacionalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jP_RelacionalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpRelacionGraficaRelacional, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
                .addContainerGap())
        );

        Jtp_interfaz.addTab("Diagrama", jP_Relacional);

        getContentPane().add(Jtp_interfaz, java.awt.BorderLayout.LINE_START);

        jMenu1.setText("Archivo");

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setText("Abrir");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem8);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setText("Guardar");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setText("Salir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Operaciones");

        jMenu5.setText("Reiniciar");

        jmi_lienzo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.CTRL_MASK));
        jmi_lienzo.setText("Lienzo");
        jmi_lienzo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_lienzoActionPerformed(evt);
            }
        });
        jMenu5.add(jmi_lienzo);

        jmi_datos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.SHIFT_MASK));
        jmi_datos.setText("Datos");
        jmi_datos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmi_datosActionPerformed(evt);
            }
        });
        jMenu5.add(jmi_datos);

        jMenu2.add(jMenu5);

        jMenuBar1.add(jMenu2);

        jMenu4.setText("Ayuda");

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem9.setText("Contenido");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem9);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItem3.setText("Creditos");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem3);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents


private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    cerrar();
}//GEN-LAST:event_jMenuItem1ActionPerformed
    /**
     * Euler necesita de un nodo inicio aqui recorremos la lista para verificar
     * si se cumple el ciclo de euler y tomar las decisiones si no existe arroja
     * un mensaje si existe el ciclo llama al metodo que se encuentra en la
     * clase grafo
     *
     */

    private void jmi_lienzoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_lienzoActionPerformed
        // TODO add your handling code here:
        grafic.getListaNodos().clear();

        repintarLienzo();


    }//GEN-LAST:event_jmi_lienzoActionPerformed

    private void jbtn_hechoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_hechoActionPerformed
        Ent_atrib_key.clear();
        if (jtable_relaciones.getRowCount() == 0 && jtable_relaciones.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Por favor genere una relacion");
        } else {
            if (jcb_tipoModelo.getSelectedIndex() == 1) {
               
                Lienzo.activar = true;
                limpiar();
                repintarLienzo();
                listaEntidades();
                relaciones();
                zonaPintar.Diagramar();
                zonaPintar.buscarpuntos();
                zonaPintar.setPreferredSize(new Dimension(zonaPintar.dimensionx, zonaPintar.dimensiony));

                JOptionPane.showMessageDialog(this, "Generado con éxito");
                Jtp_interfaz.setSelectedIndex(2);

                jpRelacionGraficaRelacional.updateUI();
            
            } else {
                Lienzo.activar = false;
                limpiar();
                ArrayList<Entidad> entidades = new ArrayList<Entidad>();
                entidades = llenarEntidades();
                for (int i = 0; i < entidades.size(); i++) {
                    System.out.println("entidades " + entidades.get(i).getTitle() + "-> " + entidades.get(i).isRelacional());
                }
                ArrayList<Atributos> atributos = new ArrayList<Atributos>();
                atributos = llenarAtributos();
                for (int i = 0; i < atributos.size(); i++) {
                    System.out.println("Identificador de tabla  " + atributos.get(i).getIdentificadorTabla());
                    System.out.println("---------------Todos los Atributos -----------------");
                    for (int j = 0; j < atributos.get(i).getAtributosNormales().size(); j++) {
                        System.out.println("-.-  " + atributos.get(i).getAtributosNormales().get(j));
                    }
                }

                System.out.println("-----------RELACIONALES--------------------");
                for (int i = 0; i < relacionales.size(); i++) {
                    System.out.println("-.-  " + relacionales.get(i));
                }

                try {
                    repintarLienzo();
                    zonaPintar.generaER(entidades, atributos, relation);//Recibe 3 arraysList tipo clase objeto
                    Jtp_interfaz.setSelectedIndex(2);
                    zonaPintar.setPreferredSize(new Dimension(zonaPintar.getxDiagrama(), zonaPintar.getyDiagrama()));//redimenciona la vista del diagrama
                    zonaPintar.updateUI();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }

            }

        }
    }//GEN-LAST:event_jbtn_hechoActionPerformed
    

    public void limpiar() {
        grafic.getListaNodos().clear();

        repintarLienzo();
        datosFull.clear();
        relaciones.clear();
        zonaPintar.fle.clear();
        grafic.getListatablas().clear();
        zonaPintar.conta = 0;
        java.awt.Component[] componentes = zonaPintar.getComponents();
        for (int index = 0; index < componentes.length; index++) {
            zonaPintar.remove(componentes[index]);
        }

    }
    private void jbtn_add_AtributosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_add_AtributosActionPerformed
        try {
            if (txt_datoEntrada.getText().length() >= 1) {
                funcion.InsertarAtributos(txt_datoEntrada.getText(), jtable_Principal, jcb_entidadesIngresadas.getSelectedItem().toString());
                txt_datoEntrada.setText(null);
            } else {
                JOptionPane.showMessageDialog(jp_relaciones, "Ingrese un dato!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(jp_relaciones, "Ingrese antes una Entidad!");
        }

    }//GEN-LAST:event_jbtn_add_AtributosActionPerformed

    private void jbtn_add_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_add_tableActionPerformed
        if (isRelacional) {
            if (txt_datoEntrada.getText().length() >= 1) {
                funcion.InsertarTablas(txt_datoEntrada.getText(), jtable_Principal, jcb_entidadesIngresadas);
                relacionales.add(txt_datoEntrada.getText());
                jcb_entidadesIngresadas.setSelectedIndex(jcb_entidadesIngresadas.getItemCount() - 1);
                txt_datoEntrada.setText(null);
            } else {
                JOptionPane.showMessageDialog(jp_relaciones, "Ingrese un dato!");
            }
        } else {
            if (txt_datoEntrada.getText().length() >= 1) {
                funcion.InsertarTablas(txt_datoEntrada.getText(), jtable_Principal, jcb_entidadesIngresadas);
                jcb_entidadesIngresadas.setSelectedIndex(jcb_entidadesIngresadas.getItemCount() - 1);
                txt_datoEntrada.setText(null);
            } else {
                JOptionPane.showMessageDialog(jp_relaciones, "Ingrese un dato!");
            }
        }
    }//GEN-LAST:event_jbtn_add_tableActionPerformed

    private void jbt_relacionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt_relacionarActionPerformed

        if (jcb_entidades2.getSelectedItem().equals("Ninguna")
                || jcb_entidades1.getSelectedItem().equals("Ninguna")
                || jcb_relacioesEntidades.getSelectedItem().equals("Ninguna")) {
            JOptionPane.showMessageDialog(jp_relaciones, "Debe seleccionar una opcion");
        } else {
            funcion.ingresarRelacion(jtable_relaciones, jcb_entidades1.getSelectedItem().toString(), jcb_relacioesEntidades.getSelectedItem().toString(), jcb_entidades2.getSelectedItem().toString());
        }
    }//GEN-LAST:event_jbt_relacionarActionPerformed

    private void jtable_PrincipalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtable_PrincipalMouseReleased
        if (jtable_Principal.getValueAt(jtable_Principal.getSelectedRow(), jtable_Principal.getSelectedColumn()) != null) {
            if (jtable_Principal.getSelectedColumnCount() == 1 && jtable_Principal.getSelectedRowCount() == 1) {
                if (!isKey()) {
                    jPopupMenu1.show(this.jtable_Principal, evt.getX(), evt.getY());
                } else {
                    jPopupMenu2.show(this.jtable_Principal, evt.getX(), evt.getY());
                }
            }
        }
    }//GEN-LAST:event_jtable_PrincipalMouseReleased

    private void jMenuItem2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem2MousePressed
        agregarLlave();

    }//GEN-LAST:event_jMenuItem2MousePressed

    private void jcb_tipoModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcb_tipoModeloActionPerformed
        if (jcb_tipoModelo.getSelectedIndex() == 0) {
            rbd_Entiddad.setVisible(true);
            rbd_Relacion.setVisible(true);
        } else {
            rbd_Entiddad.setVisible(false);
            rbd_Relacion.setVisible(false);
        }
    }//GEN-LAST:event_jcb_tipoModeloActionPerformed

    private void jbtn_continuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_continuarActionPerformed
        jtb_sub.setSelectedIndex(1);
    }//GEN-LAST:event_jbtn_continuarActionPerformed

    private void jMenuItem5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem5MousePressed
        eliminarKey();

    }//GEN-LAST:event_jMenuItem5MousePressed

    private void jMenuItem4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem4MousePressed
        eliminarAtributo();

    }//GEN-LAST:event_jMenuItem4MousePressed

    private void jMenuItem6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem6MousePressed

        eliminarAtributo();

    }//GEN-LAST:event_jMenuItem6MousePressed

    private void jP_modeladoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jP_modeladoMousePressed
        // TODO add your handling code here:
        agregarLlave();
    }//GEN-LAST:event_jP_modeladoMousePressed

    private void rbd_EntiddadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbd_EntiddadActionPerformed
        rbd_Entiddad.setSelected(true);
        rbd_Relacion.setSelected(false);
        isRelacional = false;
    }//GEN-LAST:event_rbd_EntiddadActionPerformed

    private void rbd_RelacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbd_RelacionActionPerformed
        rbd_Entiddad.setSelected(false);
        rbd_Relacion.setSelected(true);
        isRelacional = true;
    }//GEN-LAST:event_rbd_RelacionActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed

        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "bdm  Archivos", "bdm");
        chooser.setFileFilter(filter);
        chooser.showSaveDialog(this);

        chooser.setApproveButtonText("Guardar");

        System.out.println("chooser " + chooser.getSelectedFile());
        File xd = chooser.getSelectedFile();
        System.out.println("xD  " + xd);
        if (xd != null) {
            if (xd.exists()) {
                System.out.println("ya existe");
                int opc = JOptionPane.showConfirmDialog(jp_relaciones, "El archivo ya existe desea sobreescribirlo");
                System.out.println("opc " + opc);
                if (opc == 0) {
                    if (enviarDatosFichero(xd.toString(), 1)) {
                        JOptionPane.showMessageDialog(jp_relaciones, "Se sobre escribio correctamente");
                    }
                }
            } else {
                if (enviarDatosFichero(xd.toString(), 1)) {
                    JOptionPane.showMessageDialog(jp_relaciones, "Guardado con éxito");
                }
            }
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "bdm  Archivos", "bdm");
        chooser.setFileFilter(filter);
        chooser.setApproveButtonText("Abrir");
        int returnVal = chooser.showDialog(jLabel2, null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            LecturaDatos lectura = new LecturaDatos();
            Thread ne = new HiloProgres();
            System.out.println("url   " + chooser.getSelectedFile().getAbsolutePath());
            ne.start();
            if (!(lectura.cargarDatos(chooser.getSelectedFile().getAbsolutePath(), relation))) {
                jMenuItem8ActionPerformed(evt);
            }

        }

        Jtp_interfaz.setSelectedIndex(1);
        jtb_sub.setSelectedIndex(1);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jmi_datosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmi_datosActionPerformed
        jtable_Principal = null;
        jtable_relaciones = null;
        jcb_entidades1 = null;
        jcb_entidades2 = null;
        jcb_entidadesIngresadas = null;

    }//GEN-LAST:event_jmi_datosActionPerformed

    private void txt_datoEntradaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_datoEntradaKeyTyped
        if (txt_datoEntrada.getText().length() == 12) {
            evt.consume();

        }
    }//GEN-LAST:event_txt_datoEntradaKeyTyped

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        frm_ayuda help = new frm_ayuda();
        help.setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        JOptionPane.showMessageDialog(this, "PROYECTO: Workmodels 1.0 \n"
                + "DESARROLADO POR:\n"
                + "- Cerdas Yoel. \n"
                + "- Espinoza Harley.\n"
                + "- Hidalgo Jerson. \n"
                + "CONTACTO: \n"
                + "yoel1202@hotmail.com \n"
                + "harleyespinoza89@gmail.com \n"
                + "jersonfjl@live.com \n ");
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ParseException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        try {
            //</editor-fold>
            UIManager.setLookAndFeel(new SyntheticaOrangeMetallicLookAndFeel());
            //</editor-fold>
            //</editor-fold>
            //</editor-fold>
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Frm_Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                new Frm_Principal().setVisible(true);

            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JPanel Jp_informacion;
    private javax.swing.JTabbedPane Jtp_interfaz;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jP_Relacional;
    public static javax.swing.JPanel jP_modelado;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbt_relacionar;
    private javax.swing.JButton jbtn_add_Atributos;
    private javax.swing.JButton jbtn_add_table;
    private javax.swing.JButton jbtn_continuar;
    private javax.swing.JButton jbtn_hecho;
    public static javax.swing.JComboBox jcb_entidades1;
    public static javax.swing.JComboBox jcb_entidades2;
    public static javax.swing.JComboBox jcb_entidadesIngresadas;
    private javax.swing.JComboBox jcb_relacioesEntidades;
    private javax.swing.JComboBox jcb_tipoModelo;
    private javax.swing.JMenuItem jmi_datos;
    private javax.swing.JMenuItem jmi_lienzo;
    private javax.swing.JScrollPane jpRelacionGraficaRelacional;
    private javax.swing.JPanel jp_datos;
    private javax.swing.JPanel jp_relaciones;
    public static javax.swing.JTable jtable_Principal;
    public static javax.swing.JTable jtable_relaciones;
    private javax.swing.JTabbedPane jtb_sub;
    private javax.swing.JRadioButton rbd_Entiddad;
    private javax.swing.JRadioButton rbd_Relacion;
    private javax.swing.JTextField txt_datoEntrada;
    // End of variables declaration//GEN-END:variables

}
