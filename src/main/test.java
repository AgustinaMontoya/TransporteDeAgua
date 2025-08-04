package main;
import estructuras.conjuntistas.*;
import estructuras.grafos.GrafoEtiquetado;
import estructuras.lineales.*;

public class test {

    public static void main(String[] args) {

        GrafoEtiquetado g = new GrafoEtiquetado();

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






    }




}
