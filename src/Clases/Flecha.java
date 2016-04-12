package Clases;

/**
 *
 * @author yoel
 * @author olman
 * @author jonatan
 * @author enzo
 * @author  michael
 */

import java.awt.*;

public class Flecha extends DibujarFlecha {

	Point pi;
	Point pf;
        String etiqueta;

     
	public Flecha( Point i){
		pi=i;
		
                this.etiqueta="";
	}

	
	public void setPf( Point p ){
		pf=p;
	}

    public void setPi(Point pi) {
        this.pi = pi;
    }

    public Point getPi() {
        return pi;
    }

    public Point getPf() {
        return pf;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
	
	 /**
          * 
          * @param g dibuja la fecha en pantalla
          */
	public void pintar( Graphics g )
	{
	
		int xi = (int)pi.getX();
		int yi = (int)pi.getY();
		
		int xf = (int)pf.getX();
		int yf = (int)pf.getY();
		
		g.setColor(Color.BLACK);
		super.drawArrow( g,xi,yi,xf,yf );
                
                g.setColor( Color.RED);
                g.drawString("" + etiqueta, xi + ((xf - xi) / 2), yi
                    + ((yf - yi) / 2));
							    
	}

}		