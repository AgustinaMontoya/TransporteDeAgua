package estructuras.grafos;

import estructuras.lineales.*;

public class GrafoEtiquetado {
    // ------------------------------------------ ATRIBUTOS ------------------------------------------ //
    private NodoVertice inicio;

    // ----------------------------------------- CONSTRUCTOR ----------------------------------------- //
    /*
        Constructor vacío
     */
    public GrafoEtiquetado() {
        inicio = null;
    }

    // ---------------------------------------- MODIFICADORES ---------------------------------------- //
    /*
        Dado un elemento de TipoVertice se lo agrega a la estructura controlando que no se inserten
        vértices repetidos. Si puede realizar la inserción devuelve verdadero, en caso contrario devuelve
        falso.
     */
    public boolean insertarVertice(Object nuevoVertice) {
        boolean insertar = false;
        NodoVertice existe = ubicarVertice(nuevoVertice);
        if (existe == null) {
            this.inicio = new NodoVertice(nuevoVertice, this.inicio, null);
            insertar = true;
        }
        return insertar;
    }

    private NodoVertice ubicarVertice(Object buscado) {
        NodoVertice encontrado = this.inicio;
        while (encontrado != null && !encontrado.getElemento().equals(buscado)) {
            encontrado = encontrado.getSigVertice();
        }
        return encontrado;
    }

    /*
        Dado un elemento de TipoVertice se lo quita de la estructura. Si se encuentra el vértice, también
        deben eliminarse todos los arcos que lo tengan como origen o destino. Si se puede realizar la
        eliminación con éxito devuelve verdadero, en caso contrario devuelve falso
     */
    public boolean eliminarVertice(Object vertice) {
        boolean eliminar = false;

        return eliminar;
    }

    /*
        Dados dos elementos de TipoVertice (origen y destino) agrega el arco en la estructura, solo si
        ambos vértices ya existen en el grafo. Si puede realizar la inserción devuelve verdadero, en caso
        contrario devuelve falso.
     */
    public boolean insertarArco(Object ori, Object dest, Object etiqueta) {
        boolean insertar = false;
        if (!ori.equals(dest)) {
            // Busco los vertices origen y destino dentro del grafo.
            NodoVertice[] vertices = ubicarVertices(ori, dest);
            NodoVertice origen = vertices[0];
            NodoVertice destino = vertices[1];
            if (origen != null && destino != null) {
                // Encuentra los vertices, verifica si existe arco entre ellos
                NodoAdy arco = ubicarArco(origen, destino, etiqueta);
                if (arco == null) {
                    // No existe arco, lo agrego
                    origen.setPrimerAdy(new NodoAdy(destino, origen.getPrimerAdy(), etiqueta));
//                    origen.setPrimerAdy(new NodoAdy(destino, origen.getPrimerAdy(), etiqueta));
//                    destino.setPrimerAdy(new NodoAdy(origen, destino.getPrimerAdy(), etiqueta));
                    insertar = true;
                }
            }
        }
        return insertar;
    }

    private NodoVertice[] ubicarVertices(Object origen, Object destino) {
        NodoVertice[] vertices = new NodoVertice[2];
        NodoVertice nodo = this.inicio;
        while (nodo != null && (vertices[0] == null || vertices[1] == null)) {
            if (nodo.getElemento().equals(origen)) {
                // Encontre vertice Origen
                vertices[0] = nodo;
            }
            if (nodo.getElemento().equals(destino)) {
                // Encontre vertice Destino
                vertices[1] = nodo;
            }
            nodo = nodo.getSigVertice();
        }
        return vertices;
    }

    private NodoAdy ubicarArco(NodoVertice origen, NodoVertice destino, Object etiqueta) {
        NodoAdy arco = origen.getPrimerAdy();
        boolean encontrado = false;
        while (arco != null && !encontrado) {
            if (arco.getVertice().getElemento().equals(destino.getElemento()) && arco.getEtiqueta().equals(etiqueta)) {
                // Encontró el arco entre los vertices
                encontrado = true;
            } else {
                // Sigue buscando
                arco = arco.getSigAdy();
            }
        }
        return arco;
    }

    /*
        Dados dos elementos de TipoVertice (origen y destino) se quita de la estructura el arco que une
        ambos vértices. Si el arco existe y se puede realizar la eliminación con éxito devuelve verdadero,
        en caso contrario devuelve falso
     */
    public boolean eliminarArco(Object ori, Object dest, Object etiqueta) {
        boolean eliminar = false;
        return eliminar;
    }


    // ---------------------------------------- OBSERVADORES ----------------------------------------- //
    /*
        Dado un elemento, devuelve verdadero si está en la estructura y falso en caso contrario.
     */
    public boolean existeVertice(Object vertice) {
        boolean existe = false;
        NodoVertice aux = this.inicio;
        while (aux != null && !existe) {
            existe = aux.getElemento().equals(vertice);
            aux = aux.getSigVertice();
        }
        return existe;
    }

    /*
        Dados dos elementos de TipoVertice (origen y destino), devuelve verdadero si existe un arco en
        la estructura que los une y falso en caso contrario.
     */
    public boolean existeArco(Object ori, Object dest) {
        boolean existe = false;
        NodoVertice[] vertices = ubicarVertices(ori, dest);
        // Busco los vertices origen y destino dentro del grafo.
        NodoVertice origen = vertices[0];
        NodoVertice destino = vertices[1];
        if (origen != null && destino != null) {
            // Encuentro los vertices, busco el arco entre ellos.
            NodoAdy adyOri = origen.getPrimerAdy();
            while (adyOri != null && !existe) {
                existe = adyOri.getVertice().getElemento().equals(destino.getElemento());
                adyOri = adyOri.getSigAdy();
            }
        }
        return existe;
    }

    /*
        Dados dos elementos de TipoVertice (origen y destino), devuelve verdadero si existe al menos
        un camino que permite llegar del vértice origen al vértice destino y falso en caso contrario.
     */
    public boolean existeCamino(Object ori, Object dest) {
        boolean existe = false;
        NodoVertice[] vertices = ubicarVertices(ori, dest);
        // Busco los vertices origen y destino dentro del grafo.
        NodoVertice origen = vertices[0];
        NodoVertice destino = vertices[1];
        if (origen != null && destino != null) {
            // Si ambos vertices existen busca si existe camino entre ambos.
            Lista visitados = new Lista();
            existe = existeCaminoAux(origen, dest, visitados);
        }
        return existe;
    }

    private boolean existeCaminoAux(NodoVertice nodo, Object destino, Lista visitados) {
        boolean existe = false;
        if (nodo != null) {
            if (nodo.getElemento().equals(destino)) {
                // Si nodo es el destino entonces hay camino.
                existe = true;
            } else {
                // Si no es el destino verifica si hay camino entre nodo y destino
                visitados.insertar(nodo.getElemento(), visitados.longitud() + 1);
                NodoAdy ady = nodo.getPrimerAdy();
                while (!existe && ady != null) {
                    if (visitados.localizar(ady.getVertice().getElemento()) < 0) {
                        existe = existeCaminoAux(ady.getVertice(), destino, visitados);
                    }
                    ady = ady.getSigAdy();
                }
            }
        }
        return existe;
    }

    /*
        Dados dos elementos de TipoVertice (origen y destino), devuelve un camino (lista de vértices)
        que indique el camino que pasa por menos vértices que permite llegar del vértice origen al vértice
        destino. Si hay más de un camino con igual cantidad de vértices, devuelve cualquiera de ellos.
        Si alguno de los vértices no existe o no hay camino posible entre ellos devuelve la lista vacía.
     */
    public Lista caminoMasCorto(Object ori, Object dest) {
        Lista camino = new Lista();
        NodoVertice[] vertices = ubicarVertices(ori, dest);
        // Busco los vertices origen y destino dentro del grafo.
        NodoVertice origen = vertices[0];
        NodoVertice destino = vertices[1];
        if (origen != null && destino != null) {
            Lista visitados = new Lista();
            camino = caminoMasCortoAux(origen, dest, camino, visitados);
        }
        return camino;
    }

    private Lista caminoMasCortoAux(NodoVertice ori, Object destino, Lista camino, Lista visitados) {
        return camino;
    }

    /*
        Dados dos elementos de TipoVertice (origen y destino), devuelve un camino (lista de vértices)
        que indique el camino que pasa por más vértices (sin ciclos) que permite llegar del vértice origen
        al vértice destino. Si hay más de un camino con igual cantidad de vértices, devuelve cualquiera de
        ellos. Si alguno de los vértices no existe o no hay camino posible entre ellos devuelve la lista vacía.
     */
    //public Lista caminoMasLargo(Object origen, Object destino){}

    /*
        Devuelve falso si hay al menos un vértice cargado en el grafo y verdadero en caso contrario.
     */
    public boolean esVacio() {
        return this.inicio == null;
    }

    // ----------------------------------------- RECORRIDOS ------------------------------------------ //
    /*
         Devuelve una lista con los vértices del grafo visitados según el recorrido en profundidad.
     */
    public Lista listarEnProfundidad() {
        Lista visitados = new Lista(); // Lista que almacena los nodos visitados.
        NodoVertice aux = this.inicio; // Vertice para comenzar el recorrido.
        while (aux != null) {
            if (visitados.localizar(aux.getElemento()) < 0) {
                // Si el vertice no fue visitado aún, avanza en profundidad.
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidadAux(NodoVertice nodo, Lista visitados) {
        if (nodo != null) {
            // Marca al vertice nodo como visitado
            visitados.insertar(nodo.getElemento(), visitados.longitud() + 1);
            NodoAdy adyacente = nodo.getPrimerAdy();
            while (adyacente != null) {
                // Visita en profundidad los adyacentes de nodo aun no visitados.
                if (visitados.localizar(adyacente.getVertice().getElemento()) < 0) {
                    listarEnProfundidadAux(adyacente.getVertice(), visitados);
                }
                adyacente = adyacente.getSigAdy();
            }
        }
    }

    /*
        Devuelve una lista con los vértices del grafo visitados según el recorrido en anchura.
     */
    public Lista listarEnAnchura() {
        Lista visitados = new Lista(); // Lista que almacena los nodos visitados.
        NodoVertice aux = this.inicio;  // Vertice para comenzar el recorrido.
        while (aux != null) {
            if (visitados.localizar(aux.getElemento()) < 0) {
                // Si el vertice no fue visitado aún, avanza en anchura
                listarEnAnchuraAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnAnchuraAux(NodoVertice nodo, Lista visitados) {
        Cola cola = new Cola();
        visitados.insertar(nodo.getElemento(), visitados.longitud() + 1);
        cola.poner(nodo);
        NodoVertice aux;
        NodoAdy ady;
        while (!cola.esVacia()) {
            aux = (NodoVertice) cola.obtenerFrente();
            cola.sacar();
            ady = aux.getPrimerAdy();
            while (ady != null) {
                if (visitados.localizar(ady.getVertice().getElemento()) < 0) {
                    cola.poner(ady.getVertice());
                    visitados.insertar(ady.getVertice().getElemento(), visitados.longitud() + 1);
                }
                ady = ady.getSigAdy();
            }
        }

    }

    // -------------------------------------- PROPIAS DEL TIPO --------------------------------------- //
    /*
        Genera y devuelve un grafo que es equivalente (igual estructura y contenido de los nodos) al
        original.
     */
    //public Grafo clone(){}

    /*
        Con fines de debugging, este método genera y devuelve una cadena String que muestra los
        vértices almacenados en el grafo y qué adyacentes tiene cada uno de ellos.
     */
    public String toString() {
        String cadena = "";
        NodoVertice recorrerVertices = this.inicio;
        NodoAdy recorrerAdyacentes;
        while (recorrerVertices != null) {
            cadena += "[" + recorrerVertices.getElemento().toString() + "]\t\t";
            recorrerAdyacentes = recorrerVertices.getPrimerAdy();
            while (recorrerAdyacentes != null) {
                cadena += "----" + recorrerAdyacentes.getEtiqueta().toString() + "--->   ["
                        + recorrerAdyacentes.getVertice().getElemento().toString() + "]\t\t";
                recorrerAdyacentes = recorrerAdyacentes.getSigAdy();
            }
            cadena += "\n";
            recorrerVertices = recorrerVertices.getSigVertice();
        }
        return cadena;
    }
}
