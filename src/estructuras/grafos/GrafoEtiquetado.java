/*
    ----------------- AUTORES -----------------
    Denis Agustin Albornoz, Legajo FAI-3383
    Agustina Magali Montoya, Legajo FAI-4525
    Facundo Diego Tosetto, Legajo FAI-4354
    -------------------------------------------
 */

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
        NodoVertice actual = this.inicio;
        NodoVertice anterior = null;
        if (actual.getElemento().equals(vertice)) {
            // El vertice es inicio
            eliminar = true;
        } else {
            // Busco el vertice a eliminar
            while (actual.getSigVertice() != null && !eliminar) {
                if (actual.getSigVertice().getElemento().equals(vertice)) {
                    eliminar = true;
                } else {
                    actual = actual.getSigVertice();
                }
            }
            anterior = actual;
            actual = actual.getSigVertice();
        }
        if (eliminar) {
            eliminarAdy(this.inicio, vertice);
            if (anterior == null) {
                this.inicio = actual.getSigVertice();
            } else {
                anterior.setSigVertice(actual.getSigVertice());
            }
        }
        return eliminar;
    }

    public Lista listarAdyacentes(Object vertice) {
        Lista adyacentes = new Lista();
        NodoVertice nodo = ubicarVertice(vertice);
        if (nodo != null) {
            NodoAdy ady = nodo.getPrimerAdy();
            while (ady != null) {
                adyacentes.insertar(ady.getVertice().getElemento(), adyacentes.longitud() + 1);
                ady = ady.getSigAdy();
            }
        }
        return adyacentes;
    } 

    public Lista listarOrigenes(Object vertice) {
        Lista origenes = new Lista();
        NodoVertice nodo = this.inicio;
        while (nodo != null) {
            NodoAdy ady = nodo.getPrimerAdy();
            while (ady != null) {
                if (ady.getVertice().getElemento().equals(vertice)) {
                    origenes.insertar(nodo.getElemento(), origenes.longitud() + 1);
                }
                ady = ady.getSigAdy();
            }
            nodo = nodo.getSigVertice();
        }
        return origenes;
    }

    private void eliminarAdy(NodoVertice nodo, Object elemento) {
        boolean eliminar = false;
        while (nodo != null) {
            NodoAdy actual = nodo.getPrimerAdy();
            if (actual != null) {
                if (actual.getVertice().getElemento().equals(elemento)) {
                    nodo.setPrimerAdy(actual.getSigAdy());
                    eliminar = true;
                } else {
                    while (actual != null && !eliminar) {
                        NodoAdy sig = actual.getSigAdy();
                        if (sig != null && sig.getVertice().getElemento().equals(elemento)) {
                            actual.setSigAdy(sig.getSigAdy());
                            eliminar = true;
                        } else {
                            actual = sig;
                        }
                    }
                }
            }
            nodo = nodo.getSigVertice();
        }
    }

    /*
        Dados dos elementos de TipoVertice (origen y destino) agrega el arco en la estructura, solo si
        ambos vértices ya existen en el grafo. Si puede realizar la inserción devuelve verdadero, en caso
        contrario devuelve falso.
     */
    public boolean insertarArco(Object ori, Object dest, Comparable etiqueta) {
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
    public boolean eliminarArco(Object ori, Object dest) {
        boolean eliminar = false;
        if (!ori.equals(dest)) {
            NodoVertice origen = ubicarVertice(ori);
            if (origen != null) {
                NodoAdy ady = origen.getPrimerAdy();
                if (ady != null) {
                    if (ady.getVertice().getElemento().equals(dest)) {
                        // El arco a eliminar es el primer adyacente de vertice origen
                        origen.setPrimerAdy(ady.getSigAdy());
                        eliminar = true;
                    } else {
                        // Busco el arco en la lista de adyacentes
                        NodoAdy sig = ady.getSigAdy();
                        while (sig != null && !eliminar) {
                            if (sig.getVertice().getElemento().equals(dest)) {
                                ady.setSigAdy(sig.getSigAdy());
                                eliminar = true;
                            } else {
                                ady = sig;
                                sig = sig.getSigAdy();//esta linea
                            }
                        }
                    }
                }
            }
        }
        return eliminar;
    }


    // ---------------------------------------- OBSERVADORES ----------------------------------------- //
    /*
        Dado un elemento, devuelve verdadero si está en la estructura y falso en caso contrario.
     */
    public boolean existeVertice(Comparable vertice) {
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
    public Lista recorridoCorto(Object origen, Object destino) {

        Lista camino = new Lista();

        if (this.inicio != null) {
            NodoVertice[] vertices = ubicarVertices(origen, destino);
            Lista visitados = new Lista();
            if (vertices[0] != null && vertices[1] != null) {
                recorridoCortoAux(vertices[0], vertices[1], visitados, camino);
            }
        }
        return camino;
    }

    private void recorridoCortoAux(NodoVertice n, NodoVertice dest, Lista visitados, Lista camino) {

        NodoVertice vertice;
        NodoAdy arista;
        int c = camino.longitud();

            if (!n.getElemento().equals(dest.getElemento())) {
                visitados.insertar(n.getElemento(), visitados.longitud() + 1);
                if (camino.esVacia() || visitados.longitud() < camino.longitud()) {
                if (n.getPrimerAdy() != null) {
                    arista = n.getPrimerAdy();
                    vertice = arista.getVertice();

                    if (vertice.getElemento().equals(dest.getElemento())) {
                        visitados.insertar(vertice.getElemento(), visitados.longitud() + 1);

                        if (visitados.longitud() < c || camino.esVacia()) {
                            visitados.vaciarYcopiar(camino);
                            System.out.println(visitados.toString());
                        }

                    }else {
                        recorridoCortoAux(vertice, dest, visitados, camino);
                            visitados.eliminar(visitados.longitud());

                        while (arista != null) {
                            arista = arista.getSigAdy();
                            if (arista != null) {
                                if (visitados.localizar(arista.getVertice().getElemento()) < 0) {
                                    if (arista.getVertice().getElemento().equals(dest.getElemento())) {
                                        visitados.vaciarYcopiar(camino);
                                        camino.insertar(arista.getVertice().getElemento(), visitados.longitud() + 1);
                                        arista = null;

                                    } else {
                                        recorridoCortoAux(arista.getVertice(), dest, visitados, camino);
                                        visitados.eliminar(visitados.longitud());
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

    }

    /*
        Dados dos elementos de TipoVertice (origen y destino), devuelve un camino (lista de vértices)
        que indique el camino que pasa por más vértices (sin ciclos) que permite llegar del vértice origen
        al vértice destino. Si hay más de un camino con igual cantidad de vértices, devuelve cualquiera de
        ellos. Si alguno de los vértices no existe o no hay camino posible entre ellos devuelve la lista vacía.
     */
    public Lista caminoMasLargo(Object ori, Object dest) {
        Lista camino = new Lista();
        NodoVertice[] vertices = ubicarVertices(ori, dest);
        // Busco los vertices origen y destino dentro del grafo.
        NodoVertice origen = vertices[0];
        NodoVertice destino = vertices[1];
        if (origen != null && destino != null) {
            Lista visitados = new Lista();
            camino = caminoMasLargoAux(origen, dest, camino, visitados);
        }
        return camino;

    }

    private Lista caminoMasLargoAux(NodoVertice nodo, Object destino, Lista camino, Lista visitados) {
        if (nodo.getElemento().equals(destino)) {
            if (camino.esVacia() || camino.longitud() - 1 < visitados.longitud()) {
                camino = visitados.clone();
                camino.insertar(destino, camino.longitud() + 1);
            }
        } else {
            visitados.insertar(nodo.getElemento(), visitados.longitud() + 1);
            NodoAdy aux = nodo.getPrimerAdy();
            while (aux != null) {
                if (visitados.localizar(aux.getVertice().getElemento()) < 0) {
                    camino = caminoMasLargoAux(aux.getVertice(), destino, camino, visitados);
                }
                aux = aux.getSigAdy();
            }
            visitados.eliminar(visitados.longitud());
        }
        return camino;
    }

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
        NodoVertice actual = this.inicio;  // Recorre todos los vértices del grafo.
        while (actual != null) {
            if (visitados.localizar(actual.getElemento()) < 0) {
                // Si el vertice no fue visitado aún, avanza en anchura
                listarEnAnchuraAux(actual, visitados);
            }
            actual = actual.getSigVertice();
        }
        return visitados;
    }

    private void listarEnAnchuraAux(NodoVertice nodo, Lista visitados) {
        Cola cola = new Cola();
        cola.poner(nodo);
        visitados.insertar(nodo.getElemento(), visitados.longitud() + 1);
        NodoAdy ady;
        while (!cola.esVacia()) {
            nodo = (NodoVertice) cola.obtenerFrente();
            cola.sacar();
            ady = nodo.getPrimerAdy();
            while (ady != null) {
                if (visitados.localizar(ady.getVertice().getElemento()) < 0) {
                    visitados.insertar(ady.getVertice().getElemento(), visitados.longitud() + 1);
                    cola.poner(ady.getVertice());
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
    public GrafoEtiquetado clone() {
        GrafoEtiquetado clon = new GrafoEtiquetado();
        if (!this.esVacio()) {
            // Clono todos los vértices del grafo original
            clon.inicio = new NodoVertice(this.inicio.getElemento(), null, null);
            NodoVertice auxGrafo = this.inicio.getSigVertice();
            NodoVertice auxClon = clon.inicio;
            while (auxGrafo != null) {
                auxClon.setSigVertice(new NodoVertice(auxGrafo.getElemento(), null, null));
                auxClon = auxClon.getSigVertice();
                auxGrafo = auxGrafo.getSigVertice();
            }
            // Clono las adyacencias de cada vertice
            NodoVertice ori = this.inicio;
            NodoVertice clonAux;
            while (ori != null) {
                clonAux = clon.ubicarVertice(ori.getElemento());
                clonarAdyacentes(ori, clonAux, clon);
                ori = ori.getSigVertice();
            }
        }
        return clon;
    }

    private void clonarAdyacentes(NodoVertice original, NodoVertice clon, GrafoEtiquetado grafoClon) {
        NodoAdy adyOrig = original.getPrimerAdy();
        NodoAdy anteriorClon = null;
        while (adyOrig != null) {
            // Buscar en el grafo clonado el vértice destino de la adyacencia
            NodoVertice destinoClon = grafoClon.ubicarVertice(adyOrig.getVertice().getElemento());
            NodoAdy nuevoAdy = new NodoAdy(destinoClon, null, adyOrig.getEtiqueta());
            if (anteriorClon == null) {
                clon.setPrimerAdy(nuevoAdy);
            } else {
                anteriorClon.setSigAdy(nuevoAdy);
            }
            anteriorClon = nuevoAdy;
            adyOrig = adyOrig.getSigAdy();
        }
    }

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


    public Lista obtenerCaminoMenorEtiqueta(Object elem1, Object elem2) {

        Lista masChico = new Lista();
        NodoVertice[] vertices = ubicarVertices(elem1, elem2);
        Comparable[] etiqueta = {999999.0};
        masChico.insertar(etiqueta[0], 1);
        Lista visitados = new Lista();
        if (vertices[0] != null && vertices[1] != null) {
            recorrerCamino(vertices[0], vertices[1], visitados, etiqueta, masChico);
            if (masChico.longitud() < 2) {
                masChico.vaciar();
            }
        }
        return masChico;
    }

    private void recorrerCamino(NodoVertice n, NodoVertice dest, Lista visitados, Comparable[] etiqueta, Lista masChico) {

        NodoVertice vertice;
        NodoAdy arista;
        int c = masChico.longitud();
        Comparable etiquetaChica = (Comparable) masChico.recuperar(c);

            if (!n.getElemento().equals(dest.getElemento())) {
                visitados.insertar(n.getElemento(), visitados.longitud() + 1);
                if (n.getPrimerAdy() != null) {
                    arista = n.getPrimerAdy();
                    vertice = arista.getVertice();
                    if (arista.getEtiqueta().compareTo(etiqueta[0]) < 0) {
                        etiqueta[0] = arista.getEtiqueta();
                    }
                    if (vertice.getElemento().equals(dest.getElemento())) {
                        if (etiqueta[0].compareTo(etiquetaChica) < 0) {
                            visitados.insertar(vertice.getElemento(), visitados.longitud() + 1);
                            visitados.vaciarYcopiar(masChico);
                            masChico.insertar(etiqueta[0], masChico.longitud() + 1);
                        }
                    }
                    recorrerCamino(vertice, dest, visitados, etiqueta, masChico);
                    visitados.eliminar(visitados.longitud());
                    while (arista != null) {
                        arista = arista.getSigAdy();
                        if (arista != null) {
                            if (visitados.localizar(arista.getVertice().getElemento()) == -1) {
                                if (arista.getEtiqueta().compareTo(etiqueta[0]) < 0) {
                                    etiqueta[0] = arista.getEtiqueta();
                                }
                                if (arista.getVertice().getElemento().equals(dest.getElemento())) {
                                    visitados.vaciarYcopiar(masChico);
                                    masChico.insertar(arista.getVertice().getElemento(), visitados.longitud() + 1);
                                    masChico.insertar(etiqueta[0], masChico.longitud() + 1);
                                    arista = null;
                                } else {
                                    recorrerCamino(arista.getVertice(), dest, visitados, etiqueta, masChico);
                                    visitados.eliminar(visitados.longitud());
                                }
                            }
                        }
                    }

                }
            }
        }



    public Lista obtenerCaminoSalteandoCiudad(Object elem1, Object elem2, Object nodoEvitar) {

        Lista masChico = new Lista();

        NodoVertice[] vertices = ubicarVertices(elem1, elem2);
        Comparable[] etiqueta = {999999};
        masChico.insertar(etiqueta[0], 1);
        Lista visitados = new Lista();

        if (vertices[0] != null && vertices[1] != null) {

            recorrerCaminoSalteado(vertices[0], vertices[1], nodoEvitar, visitados, etiqueta, masChico);
            if (masChico.longitud() < 2) {
                masChico.vaciar();
            }
            masChico.eliminar(masChico.longitud());
        }
        return masChico;
    }

    private void recorrerCaminoSalteado(NodoVertice n, NodoVertice dest, Object evitar, Lista visitados, Comparable[] etiqueta, Lista masChico) {

        NodoVertice vertice;
        NodoAdy arista;
        int c = masChico.longitud();
        Comparable etiquetaChica = (Comparable) masChico.recuperar(c);

        if (n != dest) {
            visitados.insertar(n.getElemento(), visitados.longitud() + 1);
            if (!n.getElemento().equals(evitar)) {
                if (n.getPrimerAdy() != null) {
                    arista = n.getPrimerAdy();
                    vertice = arista.getVertice();
                    if (arista.getEtiqueta().compareTo(etiqueta[0]) < 0) {
                        etiqueta[0] = arista.getEtiqueta();
                    }
                    if (vertice.equals(dest)) {
                        if (etiqueta[0].compareTo(etiquetaChica) < 0) {

                            visitados.insertar(vertice.getElemento(), visitados.longitud() + 1);
                            visitados.vaciarYcopiar(masChico);
                            masChico.insertar(etiqueta[0], masChico.longitud() + 1);

                        }
                    }
                    recorrerCaminoSalteado(vertice, dest, evitar, visitados, etiqueta, masChico);
                    visitados.eliminar(visitados.longitud());

                    while (arista != null) {
                        arista = arista.getSigAdy();
                        if (arista != null) {
                            if (arista.getEtiqueta().compareTo(etiqueta[0]) < 0) {
                                etiqueta[0] = arista.getEtiqueta();
                            }
                            recorrerCaminoSalteado(arista.getVertice(), dest, evitar, visitados, etiqueta, masChico);
                            visitados.eliminar(visitados.longitud());
                        }
                    }

                }
            }
        }
    }

    public Lista caminosPosibles(Object origen, Object destino, Comparable valor) {

        Lista posibles = new Lista();

        if (this.inicio != null) {
            NodoVertice[] vertices = ubicarVertices(origen, destino);
            Lista visitados = new Lista();
            Comparable[] etiqueta = {9999};
            if (vertices[0] != null && vertices[1] != null) {
                caminosPosiblesAux(vertices[0], vertices[1], etiqueta, valor, visitados, posibles);
            }
        }
        return posibles;
    }

    private void caminosPosiblesAux(NodoVertice n, NodoVertice dest, Comparable[] etiqueta, Comparable valor, Lista visitados, Lista posibles) {

        NodoVertice vertice;
        NodoAdy arista;


        if (n != dest) {
            visitados.insertar(n.getElemento(), visitados.longitud() + 1);
            if (n.getPrimerAdy() != null) {
                arista = n.getPrimerAdy();
                vertice = arista.getVertice();

                if (arista.getEtiqueta().compareTo(etiqueta[0]) < 0) {
                    etiqueta[0] = arista.getEtiqueta();
                }
                if (vertice.equals(dest)) {
                    visitados.insertar(vertice.getElemento(), visitados.longitud() + 1);
                    Lista aux = new Lista();
                    visitados.vaciarYcopiar(aux);
                    if (etiqueta[0].compareTo(valor) < 0) {
                        posibles.insertar(aux, posibles.longitud() + 1);
                    }
                }
                caminosPosiblesAux(vertice, dest, etiqueta, valor, visitados, posibles);
                visitados.eliminar(visitados.longitud());

                while (arista != null) {
                    arista = arista.getSigAdy();
                    if (arista != null) {
                        if (arista.getEtiqueta().compareTo(etiqueta[0]) < 0) {
                            etiqueta[0] = arista.getEtiqueta();
                        }
                        caminosPosiblesAux(arista.getVertice(), dest, etiqueta, valor, visitados, posibles);
                        visitados.eliminar(visitados.longitud());
                    }
                }

            }
        }
    }


}
