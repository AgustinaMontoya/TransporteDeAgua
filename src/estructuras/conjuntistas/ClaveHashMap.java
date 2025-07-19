package estructuras.conjuntistas;

public class ClaveHashMap {
    private String nom1;
    private String nom2;

    public ClaveHashMap(String nom1, String nom2) {
        this.nom1 = nom1;
        this.nom2 = nom2;
    }

    
    public boolean equals(Object obj) {
        boolean resultado = false;
        if (this == obj) {
            resultado = true;
        } else if (obj != null && getClass() == obj.getClass()) {
            ClaveHashMap otro = (ClaveHashMap) obj;
            if (this.nom1.equals(otro.nom1) && this.nom2.equals(otro.nom2)) {
                resultado = true;
            }
        }
        return resultado;
    }

   
    public int hashCode() {
        return nom1.hashCode() * 31 + nom2.hashCode();
    }
}
