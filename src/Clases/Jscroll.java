/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.ComponentUI;

/**
 *
 * @author yoel1202
 */
public class Jscroll extends JScrollPane implements MouseListener, MouseMotionListener{
    
     private Point punto_inicial; 
     private String id;
      private java.util.List listatablas;
        private Lienzo lienzo;

    

   

    public Jscroll(Point punto_inicial, String id,JTable table,Lienzo tabla) {
        setPunto_inicial(punto_inicial);
        setId(id);
        
       
        this.listatablas = Collections.synchronizedList(new LinkedList());
        this.add(table);
        lienzo =tabla;
        this.setPreferredSize(new Dimension(200, 200));
         this.setBounds(getPunto_inicial().x, getPunto_inicial().y, 200,200);
         addMouseListener(this);
         addMouseMotionListener(this);

    }
      
      

    public Point getPunto_inicial() {
        return punto_inicial;
    }

    public void setPunto_inicial(Point punto_inicial) {
        this.punto_inicial = punto_inicial;
    }

    public String getId() {
        return id;
    }
    
   public void SetBound(int x,int y) {
        this.setBounds(x, y, 200,200);
    }
    public void setId(String id) {
        this.id = id;
    }

    public List getListatablas() {
        return listatablas;
    }

    public void setListatablas(List listatablas) {
        this.listatablas = listatablas;
    }

    public Component getLowerLeft() {
        return lowerLeft;
    }

    public void setLowerLeft(Component lowerLeft) {
        this.lowerLeft = lowerLeft;
    }

    public Component getLowerRight() {
        return lowerRight;
    }

    public void setLowerRight(Component lowerRight) {
        this.lowerRight = lowerRight;
    }

    public Component getUpperLeft() {
        return upperLeft;
    }

    public void setUpperLeft(Component upperLeft) {
        this.upperLeft = upperLeft;
    }

    public Component getUpperRight() {
        return upperRight;
    }

    public void setUpperRight(Component upperRight) {
        this.upperRight = upperRight;
    }

    public ComponentUI getUi() {
        return ui;
    }

    public void setUi(ComponentUI ui) {
        this.ui = ui;
    }

    public EventListenerList getListenerList() {
        return listenerList;
    }

    public void setListenerList(EventListenerList listenerList) {
        this.listenerList = listenerList;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
       
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
      
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
   lienzo.asignarposicion(e.getPoint(),getId());
    getParent().validate();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
       
    }

}
