/**
 *
 * @author yoel
 * @author olman
 * @author jonatan
 * @author enzo
 * @author michael
 */
package Clases;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 *
 *
 */
public class Imagen extends javax.swing.JPanel {

    private String imagen;

    public Imagen(String imagen, int ancho, int alto) {
        this.setSize(ancho, alto);
        this.imagen = imagen;//se selecciona el tamaño del panel
    }

    /**
     *
     * @param grafico Se crea un método cuyo parámetro debe ser un objeto
     * Graphics
     */
    public void paint(Graphics grafico) {
        Dimension height = getSize();

//Se selecciona la imagen que tenemos en el paquete de la //ruta del programa
        ImageIcon Img = new ImageIcon(getClass().getResource("/Imagenes/"
                + imagen));

//se dibuja la imagen que tenemos en el paquete Images //dentro de un panel
        grafico.drawImage(Img.getImage(), 0, 0, height.width, height.height,
                null);

        setOpaque(false);
        super.paintComponent(grafico);
    }
}
