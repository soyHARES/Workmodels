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

public class DibujarFlecha {

    int arrowLength = 10;	// Arrow length
    int arrowWidth = 8;		// Arrow width
    int halfArrowWidth = arrowWidth / 2;	// Half arrow width
    int xValues[] = new int[3];
    int yValues[] = new int[3];
    Color c;

    /**
     *
     * @param g clase se recibe un grafico el cual se a pintar y se obtiene sus
     * cordenadad para se agregadas al penes
     */
    public void paint(Graphics g) {

    }

    public void DrawArrow() {
    }

    /**
     *
     * @param g necesita de un grafico y cordenadas
     * @param x1 recibe la posicion en x
     * @param y1 recibe la posicion en y
     * @param x2 recibe la posicion x2
     * @param y2 recibe la posicion
     * @param c necesita el color en cual se va pintar
     * @param dir direccion
     */
    public void drawArrow(Graphics g, double x1, double y1, double x2, double y2,
            Color c, boolean dir) {
        this.c = c;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(c);
        g2d.setStroke(new BasicStroke(2.0f));

        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);

        calcValues(x1, y1, x2, y2);
        if (dir) {
            g.fillPolygon(xValues, yValues, 3);
        }
    }

    public void drawArrow(Graphics g, double x1, double y1, double x2, double y2) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(c);
        g2d.setStroke(new BasicStroke(2.0f));
        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);    
        calcValues(x1, y1, x2, y2);

    }

    /**
     *
     * @param x1 son valores de la posicion norte
     * @param y1 son valores de la posicion sur
     * @param x2 son valores de la posicion este
     * @param y2 son valores de la oeste
     */
    public void calcValues(double x1, double y1, double x2, double y2) {
        // North or south	
        if (x1 == x2) {
            // North
            if (y2 < y1) {
                arrowCoords(x2, y2, x2 - halfArrowWidth, y2 + arrowLength, x2
                        + halfArrowWidth, y2 + arrowLength);
            } // South
            else {
                arrowCoords(x2, y2, x2 - halfArrowWidth, y2 - arrowLength, x2
                        + halfArrowWidth, y2 - arrowLength);
            }
            return;
        }
        // East or West	
        if (y1 == y2) {
            // East
            if (x2 > x1) {
                arrowCoords(x2, y2, x2 - arrowLength, y2 - halfArrowWidth, x2
                        - arrowLength, y2 + halfArrowWidth);
            } // West
            else {
                arrowCoords(x2, y2, x2 + arrowLength, y2 - halfArrowWidth, x2
                        + arrowLength, y2 + halfArrowWidth);
            }
            return;
        }
        // Calculate quadrant
        calcValuesQuad(x1, y1, x2, y2);
    }

    /**
     *
     * @param x1 double recibe como paremetro para x
     * @param y1 recibe valores para y
     * @param x2 recibe valores para x
     * @param y2 recibe valores para y es son valores dobles
     */
    public void calcValuesQuad(double x1, double y1, double x2, double y2) {
        double arrowAng = Math.toDegrees(Math.atan((double) halfArrowWidth
                / (double) arrowLength));
        double dist = Math.sqrt(arrowLength * arrowLength + arrowWidth);
        double lineAng = Math.toDegrees(Math.atan(((double) Math.abs(x1 - x2))
                / ((double) Math.abs(y1 - y2))));

        // Adjust line angle for quadrant
        if (x1 > x2) {
            // South East
            if (y1 > y2) {
                lineAng = 180.0 - lineAng;
            }
        } else {
            // South West
            if (y1 > y2) {
                lineAng = 180.0 + lineAng;
            } // North West
            else {
                lineAng = 360.0 - lineAng;
            }
        }
        // Calculate coords
        xValues[0] = (int) x2;
        yValues[0] = (int) y2;
        calcCoords(1, x2, y2, dist, lineAng - arrowAng);
        calcCoords(2, x2, y2, dist, lineAng + arrowAng);
    }

    public void calcCoords(int index, double x, double y, double dist, double dirn) {

        while (dirn < 0.0) {
            dirn = 360.0 + dirn;
        }
        while (dirn > 360.0) {
            dirn = dirn - 360.0;
        }
        // North-East
        if (dirn <= 90.0) {
            xValues[index] = (int) x + (int) (Math.sin(Math.toRadians(dirn))
                    * dist);
            yValues[index] = (int) y - (int) (Math.cos(Math.toRadians(dirn))
                    * dist);
            return;
        }
        // South-East
        if (dirn <= 180.0) {
            xValues[index] = (int) x + (int) (Math.cos(Math.toRadians(dirn - 90))
                    * dist);
            yValues[index] = (int) y + (int) (Math.sin(Math.toRadians(dirn - 90))
                    * dist);
            return;
        }
        // South-West
        if (dirn <= 90.0) {
            xValues[index] = (int) x - (int) (Math.sin(Math.toRadians(dirn - 180))
                    * dist);
            yValues[index] = (int) y + (int) (Math.cos(Math.toRadians(dirn - 180))
                    * dist);
        } // Nort-West    
        else {
            xValues[index] = (int) x - (int) (Math.cos(Math.toRadians(dirn - 270))
                    * dist);
            yValues[index] = (int) y - (int) (Math.sin(Math.toRadians(dirn - 270))
                    * dist);
        }
    }

    /**
     *
     * @param x1 cordenadas x del primero cuadro que se va dibujar
     * @param y1 cordenadas y del primero cuadro que se va dibujar
     * @param x2 cordenadas x2 del primero cuadro que se va dibujar
     * @param y2 cordenadas x2 del primero cuadro que se va dibujar
     * @param x3 cordenadas y del primero cuadro que se va dibujar
     * @param y3 cordenadas y3 del primero cuadro que se va dibujar
     */
    public void arrowCoords(double x1, double y1, double x2, double y2, double x3, double y3) {
        xValues[0] = (int) x1;
        yValues[0] = (int) y1;
        xValues[1] = (int) x2;
        yValues[1] = (int) y2;
        xValues[2] = (int) x3;
        yValues[2] = (int) y3;
    }
}
