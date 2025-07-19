/*
 *-------------------Autores-----------------
 * - Denis Agustin Albornoz, Legajo FAI-3383
 *   Agustina Magali Montoya, Legajo FAI-4525
 *   Facundo Diego Tosetto, Legajo FAI-4354
 --------------------------------------------
 */

package estructuras.lineales;

public class Cola {
    // ------------------------------------------ ATRIBUTOS ------------------------------------------ //
    private Nodo frente;
    private Nodo fin;

    // ----------------------------------------- CONSTRUCTOR ----------------------------------------- //
    /*
        Crea y devuelve una cola vacía.
    */
    public Cola() {
        frente = null;
        fin = null;
    }

    // ---------------------------------------- MODIFICADORES ---------------------------------------- //
    /*
        Pone el elemento al final de la cola. Devuelve verdadero si el elemento se pudo agregar en la
        estructura y falso en caso contrario.
    */
    public boolean poner(Object nuevoElem) {
        Nodo aux = new Nodo(nuevoElem, null);
        if (this.frente == null) {//esto es para el caso que este vacia la cola
            this.frente = aux;
            this.fin = aux;
        } else {//en el caso que la cola tenga un elemento
            this.fin.setEnlace(aux);
            this.fin = aux;
        }
        return true;
    }

    /*
        Saca el elemento que está en el frente de la cola. Devuelve verdadero si el elemento se pudo
        sacar (la estructura no estaba vacía) y falso en caso contrario.
    */
    public boolean sacar() {
        boolean exito = false;
        if (!this.esVacia()) {
            exito = true;
            this.frente = this.frente.getEnlace();
            if (this.frente == null) {
                this.fin = null;
            }
        }
        return exito;
    }

    /*
        Saca todos los elementos de la estructura.
    */
    public void vaciar() {
        this.frente = null;
        this.fin = null;
    }

    // ---------------------------------------- OBSERVADORES ----------------------------------------- //
    /*
        Devuelve el elemento que está en el frente.
        Precondición: la cola no está vacía.
    */
    public Object obtenerFrente() {
        Object elem = null;
        if (!this.esVacia()) {
            elem = this.frente.getElemento();
        }
        return elem;
    }

    /*
        Devuelve verdadero si la cola no tiene elementos y falso en caso contrario.
    */
    public boolean esVacia() {
        return this.frente == null;
    }

    // -------------------------------------- PROPIAS DEL TIPO --------------------------------------- //
    /*
        Crea y devuelve una cadena de caracteres formada por todos los elementos de la cola para poder
        mostrarla por pantalla. Es recomendable utilizar este método únicamente en la etapa de prueba
        y luego comentar el código.
    */
    @Override
    public String toString() {
        String s = " ";
        Nodo aux = this.frente;
        s = "[";
        while (aux != null) {
            s += aux.getElemento().toString();
            aux = aux.getEnlace();
            if (aux != null) {
                s += ",";
            }
        }
        s += "]";
        return s;
    }

    /*
        Devuelve una copia exacta de los datos en la estructura original, y respetando el orden de los
        mismos, en otra estructura del mismo tipo.
    */
    public Cola clone() {
        Cola copia = new Cola();
        Nodo auxCopia;
        Nodo auxOriginal = this.frente;
        if (!esVacia()) {
            copia.frente = new Nodo(auxOriginal.getElemento(), null);
            auxOriginal = auxOriginal.getEnlace();
            auxCopia = copia.frente;
            while (auxOriginal != null) {
                auxCopia.setEnlace(new Nodo(auxOriginal.getElemento(), null));
                auxCopia = auxCopia.getEnlace();
                auxOriginal = auxOriginal.getEnlace();
            }
            copia.fin = auxCopia;
        }
        return copia;
    }
}
