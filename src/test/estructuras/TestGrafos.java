package test.estructuras;

import estructuras.grafos.*;

public class TestGrafos {

    public static void main(String[] args) {

        GrafoEtiquetado grafoUno = new GrafoEtiquetado();
        GrafoEtiquetado grafoDos = new GrafoEtiquetado();
        GrafoEtiquetado clonador;

        // ------------------------------------------ TEST ------------------------------------------ //
        System.out.println("-----------------------------------------------------------------------"
                + "\n¿Está vacío grafoUno? --> " + grafoUno.esVacio()
                + "\n¿Está vacío grafoDos? --> " + grafoDos.esVacio()
        );
        System.out.println("-----------------------------------------------------------------------"
                + "\n\tMÉTODO INSERTAR VÉRTICE"
                + "\ngrafoUno"
                + "\n[A] --> " + grafoUno.insertarVertice("A")
                + "\n[B] --> " + grafoUno.insertarVertice("B")
                + "\n[C] --> " + grafoUno.insertarVertice("C")
                + "\n[D] --> " + grafoUno.insertarVertice("D")
                // Debería dar falso insertar de nuevo A
                + "\n[A] --> " + grafoUno.insertarVertice("A")

                + "\n\ngrafoDos"
                + "\n[A] --> " + grafoDos.insertarVertice("A")
                + "\n[B] --> " + grafoDos.insertarVertice("B")
                + "\n[C] --> " + grafoDos.insertarVertice("C")
                + "\n[D] --> " + grafoDos.insertarVertice("D")
                + "\n[E] --> " + grafoDos.insertarVertice("E")
                + "\n[F] --> " + grafoDos.insertarVertice("F")
                + "\n[G] --> " + grafoDos.insertarVertice("G")
                + "\n[H] --> " + grafoDos.insertarVertice("H")
        );
        System.out.println("-----------------------------------------------------------------------" +
                "\n\tMÉTODO INSERTAR ARCO" +
                "\ngrafoUno" +
                "\n[A] ----AB---> [B] " + grafoUno.insertarArco("A", "B", "AB") +
                "\n[B] ----BC---> [C] " + grafoUno.insertarArco("B", "C", "BC") +
                "\n[A] ----AC---> [C] " + grafoUno.insertarArco("A", "C", "AC") +
                "\n[C] ----CD---> [D] " + grafoUno.insertarArco("C", "D", "CD") +
                "\n[D] ----DA---> [A] " + grafoUno.insertarArco("D", "A", "DA") +
                "\n[D] ----DB---> [B] " + grafoUno.insertarArco("D", "B", "DB") +
               // "\n[B] ----BA---> [A] " + grafoUno.insertarArco("B", "A", "BA") +
                // Deberían dar falso
                "\n[A] ----AB---> [B] " + grafoUno.insertarArco("A", "B", "AB") +
                "\n[A] ----AA---> [A] " + grafoUno.insertarArco("A", "A", "AA") +

                "\n\ngrafoDos" +
                "\n[A] ----AB---> [B] " + grafoDos.insertarArco("A", "B", "AB") +
                "\n[A] ----AB---> [B] " + grafoDos.insertarArco("A", "F", "AF") +
                "\n[B] ----BD---> [D] " + grafoDos.insertarArco("B", "D", "BD") +
                "\n[B] ----BF---> [F] " + grafoDos.insertarArco("B", "F", "BF") +
                "\n[C] ----CF---> [F] " + grafoDos.insertarArco("C", "F", "CF") +
                "\n[C] ----CA---> [A] " + grafoDos.insertarArco("C", "A", "CA") +
                "\n[C] ----CH---> [H] " + grafoDos.insertarArco("C", "H", "CH") +
                "\n[D] ----DG---> [G] " + grafoDos.insertarArco("D", "G", "DG") +
                "\n[D] ----DE---> [E] " + grafoDos.insertarArco("D", "E", "DE")
        );
        System.out.println("-----------------------------------------------------------------------" +
                "\n\tMÉTODO EXISTE VÉRTICE" +
                "\n¿Existe vértice A? --> " + grafoUno.existeVertice("A") +
                "\n¿Existe vértice B? --> " + grafoUno.existeVertice("B") +
                "\n¿Existe vértice C? --> " + grafoUno.existeVertice("C") +
                "\n¿Existe vértice D? --> " + grafoUno.existeVertice("D") +
                // Deberían dar falso los siguientes
                "\n¿Existe vértice X? --> " + grafoUno.existeVertice("X") +
                "\n¿Existe vértice null? --> " + grafoUno.existeVertice(null)
        );
        System.out.println("-----------------------------------------------------------------------" +
                "\n\tMÉTODO EXISTE ARCO" +
                "\n¿Existe arco entre A y B? --> " + grafoUno.existeArco("A", "B") +
                "\n¿Existe arco entre B y C? --> " + grafoUno.existeArco("B", "C") +
                "\n¿Existe arco entre A y C? --> " + grafoUno.existeArco("A", "C") +
                "\n¿Existe arco entre C y D? --> " + grafoUno.existeArco("C", "D") +
                "\n¿Existe arco entre D y A? --> " + grafoUno.existeArco("D", "A") +
                "\n¿Existe arco entre D y B? --> " + grafoUno.existeArco("D", "B") +
                "\n¿Existe arco entre B y A? --> " + grafoUno.existeArco("B", "A") +
                // Deberían dar falso los siguientes
                "\n¿Existe arco entre A y A --> " + grafoUno.existeArco("A", "A") +
                "\n¿Existe arco entre X y A? --> " + grafoUno.existeArco("X", "A") +
                "\n¿Existe arco entre A y X? --> " + grafoUno.existeArco("A", "X") +
                "\n¿Existe arco D y null? --> " + grafoUno.existeArco("D", null)
        );
        System.out.println("-----------------------------------------------------------------------" +
                "\n\tMÉTODO EXISTE CAMINO" +
                "\n¿Existe camino entre A y D? --> " + grafoUno.existeCamino("A", "D") +
                "\n¿Existe camino entre D y A? --> " + grafoUno.existeCamino("A", "D") +
                "\n¿Existe camino entre C y A? --> " + grafoUno.existeCamino("C", "A") +
                // Deberían dar falso
                "\n¿Existe camino entre B y B? --> " + grafoUno.existeCamino("B", "B") +
                "\n¿Existe camino entre X y A? --> " + grafoUno.existeCamino("X", "A")
        );
        System.out.println("-----------------------------------------------------------------------"
                + "\n\tMÉTODO LISTAR EN PROFUNDIDAD"
                + "\n" + grafoDos.listarEnProfundidad()
                + "\n" + grafoUno.listarEnProfundidad()
        );
        System.out.println("-----------------------------------------------------------------------"
                + "\n\tMÉTODO LISTAR EN ANCHURA"
                + "\n" + grafoDos.listarEnAnchura()
                + "\n" + grafoUno.listarEnAnchura()
        );
        System.out.println("-----------------------------------------------------------------------"
                + "\n\tMÉTODO CAMINO MÁS CORTO"
                + "\n" + grafoDos.caminoMasCorto("A", "F")
                + "\n" + grafoUno.caminoMasCorto("A", "D")
        );
        System.out.println("-----------------------------------------------------------------------"
                + "\n\tMÉTODO CAMINO MÁS LARGO"
                + "\n" + grafoDos.caminoMasLargo("A", "F")
                + "\n" + grafoUno.caminoMasLargo("A", "D")
        );
        clonador = grafoDos.clone();
        System.out.println("-----------------------------------------------------------------------"
                + "\n\tMÉTODO CLONAR"
                + "\ngrafoDos\n"
                + grafoDos.toString()
                + "\ngrafoDosClonado\n"
                + clonador.toString()
        );
        System.out.println("-----------------------------------------------------------------------" +
                "\n\tMÉTODO ELIMINAR VERTICE"
                + "\ngrafoUno"
                + "\n[D] --> " + grafoUno.eliminarVertice("D")
                + "\ngrafoDos"
                + "\n[F]  --> " + grafoDos.eliminarVertice("F")
                + "\n[G]  --> " + grafoDos.eliminarVertice("G")
                + "\n[E]  --> " + grafoDos.eliminarVertice("E")
        );
        System.out.println("-----------------------------------------------------------------------" +
                "\n\tMÉTODO ELIMINAR ARCO" +
                "\ngrafoUno" +
                "\n[A] ----AB---> [B] " + grafoUno.eliminarArco("A", "B") +
                "\n[B] ----BC---> [C] " + grafoUno.eliminarArco("B", "C") +
                "\n[A] ----AC---> [C] " + grafoUno.eliminarArco("A", "C") +
                "\n[C] ----CD---> [D] " + grafoUno.eliminarArco("C", "D") +
                "\n[D] ----DA---> [A] " + grafoUno.eliminarArco("D", "A") +
                "\n[D] ----DB---> [B] " + grafoUno.eliminarArco("D", "B") +
                "\n[B] ----BA---> [A] " + grafoUno.eliminarArco("B", "A")
        );
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(grafoUno.toString());
        System.out.println(grafoDos.toString());
        System.out.println(clonador.toString());
    }

}
