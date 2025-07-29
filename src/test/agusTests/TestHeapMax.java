package test.agusTests;

import estructuras.conjuntistas.HeapMaximo;

public class TestHeapMax {
    public static void main(String[] args) {
        HeapMaximo heap = new HeapMaximo();

        heap.insertar("A");
        heap.insertar("F");
        heap.insertar("G");
        heap.insertar("E");
        heap.insertar("R");
        heap.insertar("W");
        heap.insertar("E");
        heap.insertar("T");
        heap.insertar("I");
        heap.insertar("O");
        heap.insertar("P");
        System.out.println( heap.toString());

        heap.insertar("Z");
        System.out.println( heap.toString());

        heap.eliminarCima();
        System.out.println(heap.toString());

        System.out.println(heap.recuperarCima());

        System.out.println(heap.esVacio());

        heap.vaciar();

        System.out.println(heap.toString());

    }
}
