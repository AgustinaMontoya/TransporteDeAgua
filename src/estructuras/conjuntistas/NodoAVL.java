/*
    ----------------- AUTORES -----------------
    Denis Agustin Albornoz, Legajo FAI-3383
    Agustina Magali Montoya, Legajo FAI-4525
    Facundo Diego Tosetto, Legajo FAI-4354
    -------------------------------------------
 */

package estructuras.conjuntistas;

public class NodoAVL {

    //VARIABLES

    private Comparable elem;
    private int altura;
    private NodoAVL izquierdo;
    private NodoAVL derecho;


    //CONSTRUCTOR

    public NodoAVL(Comparable elem, NodoAVL izq, NodoAVL der) {
        this.elem = elem;
        this.izquierdo = izq;
        this.derecho = der;
        altura = 0;
    }

    public Comparable getElem() {
        return this.elem;
    }

    public void setElem(Comparable elem) {
        this.elem = elem;
    }


    public int getAltura() {
        return this.altura;
    }

    public void recalcularAltura() {
        int altIzq = -1, altDer = -1;

        if (this.getIzquierdo() != null) {
            altIzq = this.getIzquierdo().getAltura();
        }
        if (this.getDerecho() != null) {
            altDer = this.getDerecho().getAltura();
        }

        this.altura = Math.max(altIzq, altDer) + 1;

    }


    public NodoAVL getIzquierdo() {
        return this.izquierdo;
    }

    public void setIzquierdo(NodoAVL izq) {
        this.izquierdo = izq;
    }


    public NodoAVL getDerecho() {
        return this.derecho;
    }

    public void setDerecho(NodoAVL der) {
        this.derecho = der;
    }


}
