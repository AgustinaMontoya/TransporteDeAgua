/*
    ----------------- AUTORES -----------------
    Denis Agustin Albornoz, Legajo FAI-3383
    Agustina Magali Montoya, Legajo FAI-4525
    Facundo Diego Tosetto, Legajo FAI-4354
    -------------------------------------------
 */
package estructuras.conjuntistas;

import estructuras.lineales.Lista;

public class ArbolAVL {

    //VARIABLES

    NodoAVL raiz;

    // METODOS

    public ArbolAVL() {
        this.raiz = raiz;
    }

    public boolean pertenece(Comparable elemento) {
        boolean exito = false;

        if (this.raiz != null) {
            exito = encontrarNodo(this.raiz, elemento);
        }
        return exito;
    }

    private boolean encontrarNodo(NodoAVL n, Comparable elemento) {
        boolean encontrado = false;

        if (n.getElem().compareTo(elemento) == 0) {
            encontrado = true;
        } else if (n.getElem().compareTo(elemento) > 0) {
            encontrado = encontrarNodo(n.getIzquierdo(), elemento);
        } else {
            encontrado = encontrarNodo(n.getDerecho(), elemento);
        }

        return encontrado;
    }

    public boolean insertar(Comparable elemento) {
        boolean exito = true;
        NodoAVL[] rebalanceo = new NodoAVL[1];
        if (this.raiz == null) {
            this.raiz = new NodoAVL(elemento, null, null);
            this.raiz.recalcularAltura();
        } else {
            exito = insertarAux(this.raiz, elemento, rebalanceo);
        }

        return exito;
    }

    private boolean insertarAux(NodoAVL n, Comparable elemento, NodoAVL[] rebalanceo) {
        boolean exito = true;


        if ((elemento.compareTo(n.getElem()) == 0)) {
            // Reportar error: Elemento repetido
            exito = false;
        } else if (elemento.compareTo(n.getElem()) < 0) {
            // Elemento menor -> va a la izquierda
            if (n.getIzquierdo() != null) {
                exito = insertarAux(n.getIzquierdo(), elemento, rebalanceo);
                n.setIzquierdo(rebalanceo[0]);
                n.recalcularAltura();
                int balance = obtenerBalance(n);
                if (balance == 2 || balance == -2) {
                    NodoAVL aux = rebalancear(n, balance);
                    rebalanceo[0] = aux;
                    if (this.raiz == n) {
                        this.raiz = aux;
                    }
                } else {
                    rebalanceo[0] = n;
                }
                n.recalcularAltura();
            } else {
                n.setIzquierdo(new NodoAVL(elemento, null, null));
                n.recalcularAltura();
                rebalanceo[0] = n;
            }
        } else { // Elemento mayor -> va a la derecha
            if (n.getDerecho() != null) {
                exito = insertarAux(n.getDerecho(), elemento, rebalanceo);
                n.setDerecho(rebalanceo[0]);
                n.recalcularAltura();
                int balance = obtenerBalance(n);
                if (balance == 2 || balance == -2) {
                    NodoAVL aux = rebalancear(n, balance);
                    rebalanceo[0] = aux;
                    if (this.raiz == n) {
                        this.raiz = aux;
                    }
                } else {
                    rebalanceo[0] = n;
                }
            } else {
                n.setDerecho(new NodoAVL(elemento, null, null));
                n.recalcularAltura();
                rebalanceo[0] = n;
            }
        }
        return exito;
    }

    private NodoAVL rebalancear(NodoAVL n, int balance) {

        NodoAVL retorno;

        if (balance == -2) {
            if (obtenerBalance(n.getDerecho()) != 1) {
                retorno = rotacionAIzquierda(n);
            } else {
                retorno = rotacionADerecha(n.getDerecho());
                n.setDerecho(retorno);
                retorno = rotacionAIzquierda(n);
            }
        } else {
            if (obtenerBalance(n.getIzquierdo()) != -1) {
                retorno = rotacionADerecha(n);
            } else {
                retorno = rotacionAIzquierda(n.getIzquierdo());
                n.setIzquierdo(retorno);
                retorno = rotacionADerecha(n);
            }
        }
        recalcularArbol(retorno);

        return retorno;
    }

    private NodoAVL rotacionAIzquierda(NodoAVL n) {
        NodoAVL retorno;

        if (n.getDerecho().getIzquierdo() != null) {
            retorno = n.getDerecho();
            NodoAVL aux = n.getDerecho().getIzquierdo();
            n.getDerecho().setIzquierdo(n);
            n.setDerecho(aux);

        } else {
            n.getDerecho().setIzquierdo(n);
            retorno = n.getDerecho();
            n.setDerecho(null);
        }
        return retorno;
    }

    private NodoAVL rotacionADerecha(NodoAVL n) {
        NodoAVL retorno;

        if (n.getIzquierdo().getDerecho() != null) {
            NodoAVL aux = n.getIzquierdo().getDerecho();
            n.getIzquierdo().setDerecho(n);
            retorno = n.getIzquierdo();
            n.setIzquierdo(aux);
        } else {
            n.getIzquierdo().setDerecho(n);
            retorno = n.getIzquierdo();
            n.setIzquierdo(null);
        }

        return retorno;
    }

    private void recalcularArbol(NodoAVL n) {

        int izq = -1, der = -1;

        if (n.getIzquierdo() != null) {
            recalcularArbol(n.getIzquierdo());
        }
        if (n.getDerecho() != null) {
            recalcularArbol(n.getDerecho());
        }

        n.recalcularAltura();
    }

    public int obtenerBalance(NodoAVL n) {
        int altIzq = -1, altDer = -1;

        if (n.getIzquierdo() != null) {
            altIzq = n.getIzquierdo().getAltura();
        }
        if (n.getDerecho() != null) {
            altDer = n.getDerecho().getAltura();
        }

        return (altIzq - altDer);
    }

    public boolean eliminar(Comparable elemento) {
        boolean exito = false;
        if (this.raiz != null) {
            NodoAVL n = this.raiz;
            if (n.getIzquierdo() == null && n.getDerecho() == null && elemento.compareTo(n.getElem()) == 0) {
                this.raiz = null;
                exito = true;
            } else {
                boolean[] eliminado = {false};
                NodoAVL[] rebalanceo = new NodoAVL[1];
                eliminarAux(n, elemento, eliminado, rebalanceo);
                exito = eliminado[0];
            }
        }
        return exito;
    }

    private NodoAVL eliminarAux(NodoAVL n, Comparable elemento, boolean[] indicador, NodoAVL[] rebalanceo) {
        NodoAVL aux = n;

        if (n != null) {
            if (n.getElem().compareTo(elemento) == 0) {
                indicador[0] = true;
                if (n.getIzquierdo() == null && n.getDerecho() == null) {
                    if (this.raiz == n) {
                        this.raiz = null;
                    } else {
                        aux = null;
                    }
                } else if (n.getIzquierdo() != null && n.getDerecho() != null) {
                    if (n.getIzquierdo() != null) {
                        aux = obtenerCandidato(n.getIzquierdo(), rebalanceo);
                        if (aux.getIzquierdo() != null) {
                            n.setIzquierdo(aux.getIzquierdo()); // caso candidato inmediato con un hijo izquierdo
                            n.setElem(aux.getElem());
                            aux = n;
                            n.recalcularAltura();
                        } else if (n.getIzquierdo().getElem().compareTo(aux.getElem()) == 0) {
                            n.setElem(n.getIzquierdo().getElem());
                            n.setIzquierdo(null);
                            n.recalcularAltura();
                            // unico candidato hijo izquierdo
                        } else {
                            n.setElem(aux.getElem());
                            aux = n;
                            n.recalcularAltura();

                        }
                        recalcularArbol(n);
                        int balance = obtenerBalance(n);
                        if (balance == 2 || balance == -2) {
                            aux = rebalancear(n, balance);
                            if (this.raiz == n) {
                                this.raiz = aux;
                            }
                        }

                    }

                } else if (n.getDerecho() != null) {
                    if (this.raiz == n) {
                        n.setElem(n.getDerecho().getElem());
                        n.setDerecho(null);
                    } else {
                        aux = n.getDerecho();
                    }
                } else {
                    if (this.raiz == n) {
                        n.setElem(n.getIzquierdo().getElem());
                        n.setIzquierdo(null);
                    } else {
                        aux = n.getIzquierdo();
                    }
                }
                if (aux != null) {
                    recalcularArbol(aux);
                    int balance = obtenerBalance(aux);
                    if (balance == 2 || balance == -2) {
                        aux = rebalancear(n, balance);
                    }
                }

            } else {
                if (n.getElem().compareTo(elemento) > 0) {
                    n.setIzquierdo(eliminarAux(n.getIzquierdo(), elemento, indicador, rebalanceo));
                    int balance = obtenerBalance(n);
                    if (balance == 2 || balance == -2) {
                        aux = rebalancear(n, balance);
                        if (this.raiz == n) {
                            this.raiz = aux;
                        }
                    }
                } else {
                    n.setDerecho(eliminarAux(n.getDerecho(), elemento, indicador, rebalanceo));
                    int balance = obtenerBalance(n);
                    if (balance == 2 || balance == -2) {
                        aux = rebalancear(n, balance);
                        if (this.raiz == n) {
                            this.raiz = aux;
                        }
                    }
                }
            }
        }

        return aux;
    }

    private NodoAVL obtenerCandidato(NodoAVL n, NodoAVL[] rebalanceo) {
        NodoAVL aux = n;

        if (n.getDerecho() != null) {
            if (n.getDerecho().getDerecho() != null) {
                aux = obtenerCandidato(n.getDerecho(), rebalanceo);
            } else {
                if (n.getDerecho().getIzquierdo() != null) {
                    aux = n.getDerecho();
                    n.setDerecho(n.getDerecho().getIzquierdo()); // candidato con hijo izquierdo
                    aux.setIzquierdo(null);
                } else {
                    aux = n.getDerecho();
                    n.setDerecho(null); // candidato hoja
                }
                n.recalcularAltura();
                int balance = obtenerBalance(n);
                if (balance == 2 || balance == -2) {
                    NodoAVL retorno = rebalancear(n, balance);
                    rebalanceo[0] = retorno;
                } else {
                    rebalanceo[0] = n;
                }
            }
        }
        return aux;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public Lista listarRango(Comparable elem1,Comparable elem2){

        Lista rango=new Lista();

        if(this.raiz!=null){
            listarRangoAux(this.raiz,elem1,elem2,rango);
        }

        return rango;
    }

    private void listarRangoAux(NodoAVL n,Comparable elem1,Comparable elem2,Lista rango){

        if(n!=null) {
            if (elem1.compareTo(n.getElem()) == 0) {
                rango.insertar(n, rango.longitud() + 1);
            } else if (elem1.compareTo(n.getElem()) < 0) {
                listarRangoAux(n.getIzquierdo(), elem1, elem2, rango);
            }

            if (elem1.compareTo(n.getElem()) < 0 && elem2.compareTo(n.getElem()) > 0) {
                rango.insertar(n, rango.longitud() + 1);
            }

            if(elem2.compareTo(n.getElem())==0){
                rango.insertar(n, rango.longitud() + 1);
            }else if (elem2.compareTo(n.getElem()) > 0) {
                listarRangoAux(n.getDerecho(), elem1, elem2, rango);
            }
        }
    }

    public String toString() {
        String cad = "Arbol vacio";
        if (!esVacio()) {
            cad = toStringAux(this.raiz);
        }
        return cad;
    }

    private String toStringAux(NodoAVL n) {
        String cad = "";
        if (n != null) {
            cad += "(" + n.getElem() + ") -> ";
            if (n.getIzquierdo() != null) {
                cad += "HI: " + n.getIzquierdo().getElem() + "  ";
            } else {
                cad += "HI: -  ";
            }
            if (n.getDerecho() != null) {
                cad += "HD: " + n.getDerecho().getElem() + "\n";
            } else {
                cad += "HD: - \n";
            }
            cad += toStringAux(n.getIzquierdo());
            cad += toStringAux(n.getDerecho());
        }
        return cad;
    }

}
