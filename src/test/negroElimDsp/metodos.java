package test.negroElimDsp;
import estructuras.grafos.*;
import estructuras.lineales.Lista;




public class metodos {




    public static void main(String[] args) {
        GrafoEtiquetado g = new GrafoEtiquetado();


        g.insertarVertice('A');
        g.insertarVertice('B');
        g.insertarVertice('C');
        g.insertarVertice('D');
        g.insertarVertice('E');
        g.insertarVertice('F');
        g.insertarVertice('G');
        g.insertarVertice('H');
        g.insertarVertice('I');
        g.insertarVertice('J');
        g.insertarVertice('K');
        g.insertarVertice('L');
        g.insertarVertice('W');
        g.insertarVertice('P');





        g.insertarArco('A','B',10);
        g.insertarArco('B','E',11);
        g.insertarArco('B','F',40);
        g.insertarArco('E','J',18);
        g.insertarArco('F','J',33);
        g.insertarArco('A','C',9);
        g.insertarArco('C','G',11);
        g.insertarArco('C','H',15);
        g.insertarArco('G','K',9);
        g.insertarArco('K','L',17);
        g.insertarArco('H','K',8);
        g.insertarArco('A','D',10);
        g.insertarArco('D','H',13);
        g.insertarArco('D','I',13);
        g.insertarArco('I','L',7);


        Lista masCorto= g.caminosPosibles('A','L');







    }

}
