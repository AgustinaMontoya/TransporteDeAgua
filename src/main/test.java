package main;
import estructuras.conjuntistas.*;
import estructuras.grafos.GrafoEtiquetado;
import estructuras.lineales.*;

public class test {

    public static void main(String[] args) {


        //prueba de camino corto en grafo

        GrafoEtiquetado g = new GrafoEtiquetado();

        /*
        g.insertarVertice("neufuen");
        g.insertarVertice("cupralco");
        g.insertarVertice("maravelica");
        g.insertarVertice("solandina");
        g.insertarVertice("terraviva");
        g.insertarVertice("piedraluna");
        g.insertarVertice("valleverde");
        g.insertarVertice("monteaurora");
        g.insertarVertice("rionegra");
        g.insertarVertice("fuenteluz");
        g.insertarVertice("ventisquero");
        g.insertarVertice("cieloflor");
        g.insertarVertice("bosquemonte");

        g.insertarArco("neufuen","solandina",390);
        g.insertarArco("neufuen","terraviva",443);
        g.insertarArco("bosquemonte","neufuen",540);
        g.insertarArco("bosquemonte","fuenteluz",550);
        g.insertarArco("cupralco","valleverde",550);
        g.insertarArco("cupralco","terraviva",550);
        g.insertarArco("maravelica","monteaurora",550);
        g.insertarArco("maravelica","bosquemonte",550);
        g.insertarArco("solandina","monteaurora",550);
        g.insertarArco("piedraluna","cupralco",550);
        g.insertarArco("piedraluna","solandina",550);
        g.insertarArco("monteaurora","piedraluna",550);
        g.insertarArco("valleverde","rionegra",550);
        g.insertarArco("rionegra","neufuen",550);
        g.insertarArco("rionegra","ventisquero",550);
        g.insertarArco("terraviva","maravelica",550);
        g.insertarArco("fuenteluz","ventisquero",550);
        g.insertarArco("fuenteluz","cieloflor",550);
        g.insertarArco("ventisquero","cieloflor",550);

        Lista camino = g.recorridoCorto("neufuen","monteaurora");
        System.out.println(camino.toString());
        */

        /*Prueba de rotaciones en AVL el cual seria lo mismo con TABLA AVL
          Se elijio esas dos rotaciones porque abarcan las rotaciones simples tambien.
         */
        ArbolAVL arbol = new ArbolAVL();

        // casos para una rotacion doble izq-der
        /*
        arbol.insertar(12);
        arbol.insertar(5);
        arbol.insertar(23);
        arbol.insertar(3);
        arbol.insertar(8);
        arbol.insertar(10)
        arbol.eliminar(8);
        arbol.eliminar(3);
        System.out.println(arbol.toString());
         */

        //casos para una rotacion doble der-izq
        /*
        arbol.insertar(10);
        arbol.insertar(5);
        arbol.insertar(15);
        arbol.insertar(12);
        arbol.insertar(17);
        arbol.insertar(13);
        arbol.eliminar(12);
        arbol.eliminar(5);

        System.out.println(arbol.toString());
        */

    }




}
