/*
    ----------------- AUTORES -----------------
    Denis Agustin Albornoz, Legajo FAI-3383
    Agustina Magali Montoya, Legajo FAI-4525
    Facundo Diego Tosetto, Legajo FAI-4354
    -------------------------------------------
 */
package estructuras.lineales;

public class Lista {
    // ------------------------------------------ ATRIBUTOS ------------------------------------------ //
    private Nodo cabecera;
    private int longitud;

    // ----------------------------------------- CONSTRUCTOR ----------------------------------------- //
    /*
        Crea y devuelve una lista vacía.
     */
    public Lista() {
        this.cabecera = null;
        this.longitud = 0;
    }

    // ---------------------------------------- MODIFICADORES ---------------------------------------- //
    /*
        Agrega el elemento pasado por parámetro en la posición pos, de manera que la cantidad de
        elementos de la lista se incrementa en 1. Para una inserción exitosa, la posición recibida
        debe ser 1 ≤ pos ≤ longitud(lista) + 1.
        Devuelve verdadero si se puede insertar correctamente y falso en caso contrario.
    */
    public boolean insertar(Object nuevoElemento, int posicion) {
        boolean exito = true;
        if (posicion < 1 || posicion > this.longitud() + 1) {
            // No es posible agregar un elemento, posición incorrecta
            exito = false;
        } else {
            // Caso especial: insertar en la primer posición
            if (posicion == 1) {
                // Crea un nuevo nodo y lo enlaza en la cabecera.
                this.cabecera = new Nodo(nuevoElemento, this.cabecera);
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                // Recorre la lista buscando la posición - 1
                while (i < posicion - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                // Crea el nodo y lo enlaza
                Nodo nuevo = new Nodo(nuevoElemento, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
            this.longitud += 1;
        }
        // Nunca hay error de lista llena por lo que devuelve verdadero si se pudo insertar.
        return exito;
    }

    /*
        Borra el elemento de la posición pos, por lo que la cantidad de elementos de la lista disminuye
        en uno. Para una eliminación exitosa, la lista no debe estar vacía y la posición recibida debe
        ser 1 ≤ pos ≤ longitud(lista). Devuelve verdadero si se pudo eliminar correctamente y falso en
        caso contrario.
    */
    public boolean eliminar(int posicion) {
        boolean exito = false;
        if (!(this.esVacia() || (posicion < 1 || posicion > this.longitud() + 1))) {
            if (posicion == 1) {
                this.cabecera = this.cabecera.getEnlace();
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                // Recorre la lista en busqueda de la posición
                while (i < posicion - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
            longitud -= 1;
            exito = true;
        }
        return exito;
    }

    /*
        Quita todos los elementos de la lista.
     */
    public void vaciar() {
        // Quita todos los elementos de la lista.
        this.cabecera = null;
        this.longitud = 0;
    }

    // ---------------------------------------- OBSERVADORES ----------------------------------------- //
    /*
        Quita todos los elementos de la lista.
    */
    public int longitud() {
        return this.longitud;
    }

    /*
        Devuelve verdadero si la lista no tiene elementos y falso en caso contrario
    */
    public boolean esVacia() {
        // Si la cabecera es nula, entonces la lista está vacia
        return this.cabecera == null;
    }

    /*
        Devuelve el elemento de la posición pos.
        La precondición es que la posición sea válida.
     */
    public Object recuperar(int posicion) {
        Object elemento = null;
        if (!(posicion < 1 || posicion > this.longitud() + 1)) {
            Nodo aux = this.cabecera;
            int i = 1;
            // Recorre la lista
            while (i < posicion) {
                aux = aux.getEnlace();
                i++;
            }
            // Si el nodo no es nulo devuelve el elemento
            if (aux != null) {
                elemento = aux.getElemento();
            }
        }
        return elemento;
    }

    /*
        Devuelve la posición en la que se encuentra la primera ocurrencia de elem dentro de la lista.
        En caso de no encontrarlo devuelve -1.
    */
    public int localizar(Object unElemento) {
        int posicion = -1;
        Nodo aux = this.cabecera;
        int i = 1;
        while (i <= this.longitud()) {
            if (unElemento.equals(aux.getElemento())) {
                posicion = i;
            }
            aux = aux.getEnlace();
            i++;
        }
        return posicion;
    }

    // -------------------------------------- PROPIAS DEL TIPO --------------------------------------- //
    /*
        Crea y devuelve una cadena de caracteres formada por todos los elementos de la lista para poder
        mostrarla por pantalla. Es recomendable utilizar este método únicamente en la etapa de prueba y
        luego comentar el código.
    */
    @Override
    public String toString() {
        String cadena = "\n";
        Nodo aux = this.cabecera;
        while (aux != null) {
            if (aux.getEnlace() == null) {
                cadena += aux.getElemento();
            } else {
                cadena += aux.getElemento() + "\n";
            }
            aux = aux.getEnlace();
        }
        cadena += "\n";
        return cadena;
    }

    /*
        Devuelve una copia exacta de los datos en la estructura original, y respetando el orden de los
        mismos, en otra estructura del mismo tipo.
    */
    @Override
    public Lista clone() {
        Lista clon = new Lista();
        if (!this.esVacia()) {
            clon.cabecera = new Nodo(this.cabecera.getElemento(), null);
            Nodo auxLista = this.cabecera.getEnlace(); // Recorre la lista original
            Nodo auxClon = clon.cabecera;
            // Mientras la lista Original no sea vacia
            while (auxLista != null) {
                auxClon.setEnlace(new Nodo(auxLista.getElemento(), null));
                auxClon = auxClon.getEnlace();
                auxLista = auxLista.getEnlace();
            }
        }
        return clon;
    }

}

