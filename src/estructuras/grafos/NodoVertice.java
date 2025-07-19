/*
 *-------------------Autores-----------------
 * - Denis Agustin Albornoz, Legajo FAI-3383
 *   Agustina Magali Montoya, Legajo FAI-4525
 *   Facundo Diego Tosetto, Legajo FAI-4354
 --------------------------------------------
 */

package estructuras.grafos;

public class NodoVertice {
    /*
        Nodo Vértice: se crea un nodo de este tipo para cada vértice del grafo. Se enlazan formando una
        lista, comenzando por un nodo cabecera llamado inicio que es almacenado en la clase Grafo. Cada
        nodo de tipo Nodo Vértice almacena el nombre que identifica al vértice, un enlace al siguiente
        vértice y un enlace al primer vértice adyacente, que se representará con un Nodo Adyacente.
     */

    // ------------------------------------------ ATRIBUTOS ------------------------------------------ //
    private Object elemento;
    private NodoVertice sigVertice;
    private NodoAdy primerAdy;

    // ----------------------------------------- CONSTRUCTOR ----------------------------------------- //
    public NodoVertice(Object elemento) {
        this.elemento = elemento;
    }

    public NodoVertice(Object elemento, NodoVertice sigVertice, NodoAdy primerAdy) {
        this.elemento = elemento;
        this.sigVertice = sigVertice;
        this.primerAdy = primerAdy;
    }

    // ---------------------------------------- MODIFICADORES ---------------------------------------- //
    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }

    public void setSigVertice(NodoVertice sigVertice) {
        this.sigVertice = sigVertice;
    }

    public void setPrimerAdy(NodoAdy primerAdy) {
        this.primerAdy = primerAdy;
    }

    // ---------------------------------------- OBSERVADORES ----------------------------------------- //
    public Object getElemento() {
        return elemento;
    }

    public NodoVertice getSigVertice() {
        return sigVertice;
    }

    public NodoAdy getPrimerAdy() {
        return primerAdy;
    }
}
