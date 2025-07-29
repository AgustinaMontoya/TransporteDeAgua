/*
    ----------------- AUTORES -----------------
    Denis Agustin Albornoz, Legajo FAI-3383
    Agustina Magali Montoya, Legajo FAI-4525
    Facundo Diego Tosetto, Legajo FAI-4354
    -------------------------------------------
 */
package estructuras.conjuntistas;

public class HeapMaximo {
    // ------------------------------------------ ATRIBUTOS ------------------------------------------ //
    private Comparable[] heap;
    private int ultimo;
    private static final int TAMANIO = 50;

    // ----------------------------------------- CONSTRUCTOR ----------------------------------------- //
    /*
        Constructor vacío, crea un árbol sin elementos.
     */
    public HeapMaximo() {
        this.heap = new Comparable[TAMANIO];
        this.ultimo = 0;    // La posición cero nunca es utilizada
    }

    // ---------------------------------------- MODIFICADORES ---------------------------------------- //
    /*
        Recibe un elemento y lo inserta en el árbol. Si la operación termina con éxito devuelve verdadero
        y falso en caso contrario.
        Nota: Los árboles heap aceptan elementos repetidos.
     */
    public boolean insertar(Comparable elemento) {
        boolean insertar = false;
        if (this.ultimo < TAMANIO - 1) {
            this.heap[ultimo + 1] = elemento;
            this.ultimo++;
            insertar = true;
            hacerSubir(this.ultimo);
        }
        return insertar;
    }

    private void hacerSubir(int posicionHijo) {
        int posicionPadre;
        Comparable temp = this.heap[this.ultimo];
        boolean salir = false;
        while (!salir) {
            posicionPadre = posicionHijo / 2;
            if (posicionPadre > 0) {
                if (this.heap[posicionPadre].compareTo(temp) < 0) {
                    this.heap[posicionHijo] = this.heap[posicionPadre];
                    this.heap[posicionPadre] = temp;
                    posicionHijo = posicionPadre;
                } else {
                    salir = true;
                }
            } else {
                salir = true;
            }
        }
    }

    /*
        Elimina el elemento de la raíz. Si la operación termina con éxito devuelve verdadero y falso
         si el árbol estaba vacío.
     */
    public boolean eliminarCima() {
        boolean eliminar;
        if (this.ultimo == 0) {
            // El árbol está vacío
            eliminar = false;
        } else {
            // Saca la raíz y pone la último hoja en su lugar.
            this.heap[1] = this.heap[this.ultimo];
            this.ultimo--;
            // Restablece la propiedad de HeapMaximo.
            hacerBajar(1);
            eliminar = true;
        }
        return eliminar;
    }

    private void hacerBajar(int posicionPadre) {
        int posicionHijo;
        Comparable temp = this.heap[posicionPadre];
        boolean salir = false;
        while (!salir) {
            posicionHijo = posicionPadre * 2;
            if (posicionHijo <= this.ultimo) {
                // temp tiene al menos un hijo (izq) y lo considera menor.
                if (posicionHijo < this.ultimo) {
                    // hijoMenor tiene hermano derecho.
                    if (this.heap[posicionHijo + 1].compareTo(this.heap[posicionHijo]) > 0) {
                        // El hijo derecho es el menor de los dos.
                        posicionHijo++;
                    }
                }

                // compara al hijo menor con el padre
                if (this.heap[posicionHijo].compareTo(temp) > 0) {
                    // el hijo es menor que el padre, los intercambia
                    this.heap[posicionPadre] = this.heap[posicionHijo];
                    this.heap[posicionHijo] = temp;
                    posicionPadre = posicionHijo;
                } else {
                    // El padre es menor que sus hijos, está bien ubicado.
                    salir = true;
                }
            } else {
                // El temp es hoja, está bien ubicado.
                salir = true;
            }
        }
    }

    public void vaciar() {
        this.heap = new Comparable[TAMANIO];
        this.ultimo = 0;
    }

    // ---------------------------------------- OBSERVADORES ----------------------------------------- //
    /*
        Devuelve el elemento que está en la raíz.
        Precondición: el árbol no está vacío (si está vacío no se puede asegurar el funcionamiento de
        la operación).
     */
    public Object recuperarCima() {
        return this.heap[1];
    }

    /*
        Devuelve falso si hay al menos un elemento cargado en la tabla y verdadero en caso contrario.
     */
    public boolean esVacio() {
        return this.ultimo == 0;
    }

    // -------------------------------------- PROPIAS DEL TIPO --------------------------------------- //
    public String toString() {
        String cadena = "Árbol vacío.";
        if (this.ultimo != 0) {
            for (int i = 1; i <= this.ultimo; i++) {
                cadena += "[" + this.heap[i] + "]" + " --> ";
                if (i * 2 <= this.ultimo) {
                    cadena += "HI: [" + this.heap[i * 2] + "]";
                } else {
                    cadena += "null";
                }
                if (i * 2 + 1 <= this.ultimo) {
                    cadena += " HD: [" + this.heap[i * 2 + 1] + "]";
                }
                if (i != this.ultimo) {
                    cadena += "\n";
                }
            }
        }
        return cadena;
    }

    public HeapMaximo clone() {
        HeapMaximo clon = new HeapMaximo();
        clon.heap = this.heap.clone();
        clon.ultimo = this.ultimo;
        return clon;
    }

}
