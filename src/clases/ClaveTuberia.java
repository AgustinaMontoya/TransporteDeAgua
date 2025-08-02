/*
    ----------------- AUTORES -----------------
    Denis Agustin Albornoz, Legajo FAI-3383
    Agustina Magali Montoya, Legajo FAI-4525
    Facundo Diego Tosetto, Legajo FAI-4354
    -------------------------------------------
 */
package clases;

public class ClaveTuberia {
    private String nom1;
    private String nom2;

    public ClaveTuberia(String nom1, String nom2) {
        this.nom1 = nom1;
        this.nom2 = nom2;
    }

    
    public boolean equals(Object obj) {
        boolean resultado = false;
        if (this == obj) {
            resultado = true;
        } else if (obj != null && getClass() == obj.getClass()) {
            ClaveTuberia otro = (ClaveTuberia) obj;
            if (this.nom1.equals(otro.nom1) && this.nom2.equals(otro.nom2)) {
                resultado = true;
            }
        }
        return resultado;
    }

    public int hashCode() {
        return nom1.hashCode() + nom2.hashCode();
    }

    public String toString() {
        return nom1 +"-->" + nom2;
    }
}
