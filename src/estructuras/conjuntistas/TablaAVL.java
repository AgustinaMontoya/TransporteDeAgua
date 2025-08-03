/*
    ----------------- AUTORES -----------------
    Denis Agustin Albornoz, Legajo FAI-3383
    Agustina Magali Montoya, Legajo FAI-4525
    Facundo Diego Tosetto, Legajo FAI-4354
    -------------------------------------------
 */
package estructuras.conjuntistas;

import estructuras.lineales.Lista;

public class TablaAVL {


    //VARIABLES

    NodoTablaAVL raiz;

    // METODOS

    public TablaAVL() {
        this.raiz = raiz;
    }

    public boolean existeClave(Comparable elemento) {
        boolean exito = false;

        if (this.raiz != null) {
            exito = encontrarNodo(this.raiz, elemento);
        }
        return exito;
    }

    private boolean encontrarNodo(NodoTablaAVL n, Comparable elemento) {
        boolean encontrado = false;

        if (n.getClave().compareTo(elemento) == 0) {
            encontrado = true;
        } else if (n.getClave().compareTo(elemento) > 0) {
            if (n.getIzquierdo() != null) {
                encontrado = encontrarNodo(n.getIzquierdo(), elemento);
            }
        } else {
            if (n.getDerecho() != null) {
                encontrado = encontrarNodo(n.getDerecho(), elemento);
            }
        }

        return encontrado;
    }

    public boolean insertar(Comparable clave, Object dato) {
        boolean exito = true;
        NodoTablaAVL[] rebalanceo = new NodoTablaAVL[1];
        if (this.raiz == null) {
            this.raiz = new NodoTablaAVL(clave, dato, null, null);
            this.raiz.recalcularAltura();
        } else {
            exito = insertarAux(this.raiz, clave, dato, rebalanceo);
        }

        return exito;
    }


    private boolean insertarAux(NodoTablaAVL n, Comparable clave, Object dato, NodoTablaAVL[] rebalanceo) {
        boolean exito = true;


        if ((clave.compareTo(n.getClave()) == 0)) {
            // Reportar error: Elemento repetido
            exito = false;
        } else if (clave.compareTo(n.getClave()) < 0) {
            // Elemento menor -> va a la izquierda
            if (n.getIzquierdo() != null) {
                exito = insertarAux(n.getIzquierdo(), clave, dato, rebalanceo);
                n.setIzquierdo(rebalanceo[0]);
                n.recalcularAltura();
                int balance = obtenerBalance(n);
                if (balance == 2 || balance == -2) {
                    NodoTablaAVL aux = rebalancear(n, balance);
                    rebalanceo[0] = aux;
                    if (this.raiz == n) {
                        this.raiz = aux;
                    }
                } else {
                    rebalanceo[0] = n;
                }
            } else {
                n.setIzquierdo(new NodoTablaAVL(clave, dato, null, null));
                rebalanceo[0] = n;
            }
            n.recalcularAltura();
        } else { // Elemento mayor -> va a la derecha
            if (n.getDerecho() != null) {
                exito = insertarAux(n.getDerecho(), clave, dato, rebalanceo);
                n.setDerecho(rebalanceo[0]);
                n.recalcularAltura();
                int balance = obtenerBalance(n);
                if (balance == 2 || balance == -2) {
                    NodoTablaAVL aux = rebalancear(n, balance);
                    rebalanceo[0] = aux;
                    if (this.raiz == n) {
                        this.raiz = aux;
                    }
                } else {
                    rebalanceo[0] = n;
                }
            } else {
                n.setDerecho(new NodoTablaAVL(clave, dato, null, null));
                n.recalcularAltura();
                rebalanceo[0] = n;
            }
        }
        return exito;
    }

    private NodoTablaAVL rebalancear(NodoTablaAVL n, int balance) {

        NodoTablaAVL retorno;

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


        return retorno;
    }


    private NodoTablaAVL rotacionAIzquierda(NodoTablaAVL n) {
        NodoTablaAVL retorno;

        if (n.getDerecho().getIzquierdo() != null) {
            retorno = n.getDerecho();
            NodoTablaAVL aux = n.getDerecho().getIzquierdo();
            n.getDerecho().setIzquierdo(n);
            n.setDerecho(aux);

        } else {
            n.getDerecho().setIzquierdo(n);
            retorno = n.getDerecho();
            n.setDerecho(null);
        }
        retorno.getDerecho().recalcularAltura();
        retorno.getIzquierdo().recalcularAltura();
        retorno.recalcularAltura();
        return retorno;
    }

    private NodoTablaAVL rotacionADerecha(NodoTablaAVL n) {
        NodoTablaAVL retorno;

        if (n.getIzquierdo().getDerecho() != null) {
            NodoTablaAVL aux = n.getIzquierdo().getDerecho();
            n.getIzquierdo().setDerecho(n);
            retorno = n.getIzquierdo();
            n.setIzquierdo(aux);
        } else {
            n.getIzquierdo().setDerecho(n);
            retorno = n.getIzquierdo();
            n.setIzquierdo(null);
        }
        retorno.getDerecho().recalcularAltura();
        retorno.getIzquierdo().recalcularAltura();
        retorno.recalcularAltura();

        return retorno;
    }


    public int obtenerBalance(NodoTablaAVL n) {
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
            NodoTablaAVL n = this.raiz;
            if (n.getIzquierdo() == null && n.getDerecho() == null && elemento.compareTo(n.getClave()) == 0) {
                this.raiz = null;
                exito = true;
            } else {
                boolean[] eliminado = {false};
                NodoTablaAVL[] rebalanceo = new NodoTablaAVL[1];
                eliminarAux(n, elemento, eliminado, rebalanceo);
                exito = eliminado[0];
            }
        }
        return exito;
    }

    private NodoTablaAVL eliminarAux(NodoTablaAVL n, Comparable elemento, boolean[] indicador, NodoTablaAVL[] rebalanceo) {
        NodoTablaAVL aux = n;

        if (n != null) {
            if (n.getClave().compareTo(elemento) == 0) {
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
                            n.setClave(aux.getClave());
                            n.setDato(aux.getDato());
                        } else if (n.getIzquierdo().getClave().compareTo(aux.getClave()) == 0) {
                            n.setClave(n.getIzquierdo().getClave());
                            n.setDato(n.getIzquierdo().getDato());
                            n.setIzquierdo(null);

                            // unico candidato hijo izquierdo
                        } else {
                            n.setClave(aux.getClave());
                            n.setDato(aux.getDato());
                        }
                        aux=n;
                        n.recalcularAltura();

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
                        n.setClave(n.getDerecho().getClave());
                        n.setDato(n.getDerecho().getDato());
                        n.setDerecho(null);
                    } else {
                        aux = n.getDerecho();
                    }
                } else {
                    if (this.raiz == n) {
                        n.setClave(n.getIzquierdo().getClave());
                        n.setDato(n.getIzquierdo().getDato());
                        n.setIzquierdo(null);
                    } else {
                        aux = n.getIzquierdo();
                    }
                }
                if (aux != null) {
                    int balance = obtenerBalance(aux);
                    if (balance == 2 || balance == -2) {
                        aux = rebalancear(n, balance);
                    }
                }

            } else {
                if (n.getClave().compareTo(elemento) > 0) {
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

    private NodoTablaAVL obtenerCandidato(NodoTablaAVL n, NodoTablaAVL[] rebalanceo) {
        NodoTablaAVL aux = n;
        if (n.getDerecho() != null) {
            if (n.getDerecho().getDerecho() != null) {
                aux = obtenerCandidato(n.getDerecho(), rebalanceo);
                n.recalcularAltura();
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
                    NodoTablaAVL retorno = rebalancear(n, balance);
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

    public Object obtenerDato(Comparable cla) {
        Object dato = null;
        if (this.raiz != null && cla != null) {
            dato = obtenerDatoAux(this.raiz, cla);
        }
        return dato;
    }

    private Object obtenerDatoAux(NodoTablaAVL n, Comparable cla) {

        Object dato = null;

        if (n != null) {
            if (cla.compareTo(n.getClave()) == 0) {
                dato = n.getDato();
            } else {
                if (cla.compareTo(n.getClave()) < 0) {
                    dato = obtenerDatoAux(n.getIzquierdo(), cla);
                } else {
                    dato = obtenerDatoAux(n.getDerecho(), cla);
                }
            }
        }
        return dato;
    }

    public Lista listarClaves() {

        Lista lista = new Lista();

        if (this.raiz != null) {
            listarClavesAux(this.raiz, lista);
        }
        return lista;
    }

    private void listarClavesAux(NodoTablaAVL n, Lista lis) {

        if (n.getIzquierdo() != null) {
            listarClavesAux(n.getIzquierdo(), lis);
        }

        lis.insertar(n.getClave(), lis.longitud() + 1);

        if (n.getDerecho() != null) {
            listarClavesAux(n.getDerecho(), lis);
        }
    }

    public Lista listarDatos() {

        Lista lista = new Lista();

        if (this.raiz != null) {
            listarDatosAux(this.raiz, lista);
        }
        return lista;
    }

    private void listarDatosAux(NodoTablaAVL n, Lista lis) {

        if (n.getIzquierdo() != null) {
            listarDatosAux(n.getIzquierdo(), lis);
        }

        lis.insertar(n.getDato(), lis.longitud() + 1);

        if (n.getDerecho() != null) {
            listarDatosAux(n.getDerecho(), lis);
        }
    }

    public Lista listarRango(Comparable elem1, Comparable elem2) {

        Lista rango = new Lista();

        if (this.raiz != null) {
            listarRangoAux(this.raiz, elem1, elem2, rango);
        }

        return rango;
    }

    private void listarRangoAux(NodoTablaAVL n, Comparable elem1, Comparable elem2, Lista rango) {

        if (n != null) {

            if (elem1.compareTo(n.getClave().toString().toLowerCase()) <= 0) {
                listarRangoAux(n.getIzquierdo(), elem1, elem2, rango);
            }

            if (elem1.compareTo(n.getClave().toString().toLowerCase()) <= 0 && elem2.compareTo(n.getClave().toString().toLowerCase()) >= 0) {
                rango.insertar(n.getDato(), rango.longitud() + 1);
            }

            if (elem2.compareTo(n.getClave().toString().toLowerCase()) >= 0) {
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

    private String toStringAux(NodoTablaAVL n) {
        String cad = "";
        if (n != null) {
            cad += "(" + n.getClave() + ") -> ";
            if (n.getIzquierdo() != null) {
                cad += "HI: " + n.getIzquierdo().getClave() + "  ";
            } else {
                cad += "HI: -  ";
            }
            if (n.getDerecho() != null) {
                cad += "HD: " + n.getDerecho().getClave() + "\n";
            } else {
                cad += "HD: - \n";
            }
            cad += toStringAux(n.getIzquierdo());
            cad += toStringAux(n.getDerecho());
        }
        return cad;
    }
}
