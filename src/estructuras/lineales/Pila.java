package estructuras.lineales;

public class Pila {

    // ------------------------------------------ ATRIBUTOS ------------------------------------------ //
    private Nodo tope;

    // ----------------------------------------- CONSTRUCTOR ----------------------------------------- //
    /*
        Crea y devuelve la pila vacía.
    */
    public Pila() {
        // Constructor vacío, crea una instancia vacía de la Pila
        this.tope = null; // No apunta ningún Nodo
    }

    // ---------------------------------------- MODIFICADORES ---------------------------------------- //
    /*
        Pone el elemento nuevoElem en el tope de la pila. Devuelve verdadero si el elemento se pudo
        apilar y falso en caso contrario.
    */
    public boolean apilar(Object nuevoElemento) {
        // Crea una nueva instancia de Nodo delante de la antigua cabecera
        Nodo nuevoNodo = new Nodo(nuevoElemento, this.tope);
        // Actualiza el tope para que apunte al nodo nuevo
        this.tope = nuevoNodo;
        // Nunca hay error de pila llena, entonces devuelve true.
        return true;
        /*
         * otra forma de escribirlo :
         * this.tope = new Nodo(nuevoElemento, this.tope);
         */
    }

    /*
        Saca el elemento del tope de la pila. Devuelve verdadero si la pila no estaba vacía al momento
        de desapilar (es decir que se pudo desapilar) y falso en caso contrario.
    */
    public boolean desapilar() {
        boolean desapilar;
        // Verifica si la pila no está vacía
        if (esVacia()) {
            desapilar = false;
        } else {
            // Actualiza el tope para que apunte al siguiente nodo
            this.tope = this.tope.getEnlace();
            desapilar = true;
        }
        return desapilar;
    }

    /*
        Saca todos los elementos de la pila.
    */
    public void vaciar() {
        this.tope = null;
    }

    // ---------------------------------------- OBSERVADORES ----------------------------------------- //
    /*
        Devuelve el elemento en el tope de la pila. Precondición: la pila no está vacía.
    */
    public Object obtenerTope() {
        Object elementoTope;
        // Verifica si la pila no está vacía
        if (esVacia()) {
            elementoTope = null;
        } else {
            // Obtiene el elemento del nodo que está en el tope
            elementoTope = this.tope.getElemento();
        }
        return elementoTope;
    }

    /*
        Devuelve verdadero si la pila no tiene elementos y falso en caso contrario.
    */
    public boolean esVacia() {
        return this.tope == null;
    }

    // -------------------------------------- PROPIAS DEL TIPO --------------------------------------- //
    /*
        Devuelve una cadena de caracteres formada por todos los elementos de la pila para poder mostrarla
        por pantalla. Es recomendable utilizar este método únicamente en la etapa de prueba y luego
        comentar el código.
    */
    @Override
    public String toString() {
        String cadena = "";
        // Verifica en primer lugar que no esté vacía la Pila
        if (this.tope == null) {
            cadena = "Pila vacía";
        } else {
            // Se ubica para recorrer la pila
            Nodo aux = this.tope;
            cadena = "[";
            while (aux != null) {
                // Agrega el texto del elemento y avanza
                cadena += aux.getElemento().toString();
                aux = aux.getEnlace();
                if (aux != null) {
                    cadena += ", ";
                }
            }
            cadena += "]";
        }
        return cadena;
    }

    /*
        Devuelve una copia exacta de los datos en la estructura original, y respetando el orden de los
        mismos, en otra estructura del mismo tipo.
    */
    public Pila clone() {
        Pila clon = new Pila();
        clon.tope = clonador(this.tope);
        return clon;
    }

    private Nodo clonador(Nodo nodo) {
        Nodo nuevo = null;
        if (nodo != null) {
            nuevo = clonador(nodo.getEnlace());
            nuevo = new Nodo(nodo.getElemento(), nuevo);
        }
        return nuevo;
    }
}


