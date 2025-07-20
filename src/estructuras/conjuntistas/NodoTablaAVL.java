package estructuras.conjuntistas;

public class NodoTablaAVL {

    //VARIABLES

    private Comparable clave;
    private Object dato;
    private int altura;
    private NodoTablaAVL izquierdo;
    private NodoTablaAVL derecho;


    //CONSTRUCTOR

    public NodoTablaAVL(Comparable cla,Object da, NodoTablaAVL izq, NodoTablaAVL der) {
        this.clave = cla;
        this.dato = da;
        this.izquierdo= izq;
        this.derecho= der;
        altura=0;
    }

    public NodoTablaAVL(Comparable cla){
        this.clave = cla;
        this.dato = null;
        this.izquierdo = null;
        this.derecho = null;
        altura=0;
    }

    public Comparable getClave(){
        return this.clave;
    }

    public void setClave(Comparable cla){
        this.clave=cla;
    }

    public Object getDato(){return this.dato;}

    public void setDato(Object da){this.dato=da;}


    public int getAltura(){
        return this.altura;
    }

    public void recalcularAltura(){
        int altIzq=-1, altDer=-1;

        if(this.getIzquierdo()!=null){
            altIzq = this.getIzquierdo().getAltura();
        }
        if(this.getDerecho()!=null){
            altDer = this.getDerecho().getAltura();
        }

        this.altura = Math.max(altIzq, altDer)+1;

    }

    public NodoTablaAVL getIzquierdo(){
        return this.izquierdo;
    }

    public void setIzquierdo(NodoTablaAVL izq){
        this.izquierdo=izq;
    }


    public NodoTablaAVL getDerecho(){
        return this.derecho;
    }

    public void setDerecho(NodoTablaAVL der){
        this.derecho=der;
    }

}
