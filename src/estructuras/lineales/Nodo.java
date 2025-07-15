package estructuras.lineales;

public class Nodo {
    // ------------------------------------------ ATRIBUTOS ------------------------------------------ //
    private Object elemento;
    private Nodo enlace;

    // ----------------------------------------- CONSTRUCTOR ----------------------------------------- //
    public Nodo(Object elemento, Nodo enlace) {
        this.elemento = elemento;
        this.enlace = enlace;
    }

    // ---------------------------------------- MODIFICADORES ---------------------------------------- //
    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }

    public void setEnlace(Nodo enlace) {
        this.enlace = enlace;
    }

    // ---------------------------------------- OBSERVADORES ----------------------------------------- //
    public Object getElemento() {
        return elemento;
    }

    public Nodo getEnlace() {
        return enlace;
    }

    // -------------------------------------- PROPIAS DEL TIPO --------------------------------------- //
}
