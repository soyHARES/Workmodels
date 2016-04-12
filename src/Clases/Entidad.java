
package Clases;

/**
 * Manejo de los datos de las entidades(titulo,id,atributos)
 * @author Harley
 */
public class Entidad {

    private String title;
    private boolean relacional;
    
    public Entidad(String title,boolean relacional) {
        this.title = title;
        this.relacional = relacional;
    }

    public Entidad() {
        title = "TABLA";
        relacional = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRelacional() {
        return relacional;
    }

    public void setRelacional(boolean relacional) {
        this.relacional = relacional;
    }

}
