package estructuras.grafos;

public class NodoAdy {
     /*
        Nodo Adyacente: se crea un nodo de este tipo para cada arco del grafo. Estos nodos se enlazan al
        vértice origen del arco. Este nodo almacena un enlace al Nodo Vértice destino del arco y un enlace
        al próximo nodo adyacente. Si se trata de un grafo/dígrafo etiquetado, la etiqueta se agrega en este
        nodo.
     */

    // ------------------------------------------ ATRIBUTOS ------------------------------------------ //
    private NodoVertice vertice;     // Vertice destino
    private NodoAdy sigAdy;
    private Object etiqueta;

    // ----------------------------------------- CONSTRUCTOR ----------------------------------------- //
    /*
        Constructor vacío para grafos no etiquetados
     */
    public NodoAdy(NodoVertice vertice, NodoAdy sigAdyacente) {
        this.vertice = vertice;
        this.sigAdy = sigAdyacente;
    }

    /*
        Constructor vacío para grafos etiquetados
     */
    public NodoAdy(NodoVertice vertice, NodoAdy sigAdyacente, Object etiqueta) {
        this.vertice = vertice;
        this.sigAdy = sigAdyacente;
        this.etiqueta = etiqueta;
    }

    // ---------------------------------------- MODIFICADORES ---------------------------------------- //
    public void setVertice(NodoVertice vertice) {
        this.vertice = vertice;
    }

    public void setSigAdy(NodoAdy sigAdy) {
        this.sigAdy = sigAdy;
    }

    public void setEtiqueta(Object etiqueta) {
        this.etiqueta = etiqueta;
    }

    // ---------------------------------------- OBSERVADORES ----------------------------------------- //
    public NodoVertice getVertice() {
        return this.vertice;
    }

    public NodoAdy getSigAdy() {
        return this.sigAdy;
    }

    public Object getEtiqueta() {
        return this.etiqueta;
    }
}
