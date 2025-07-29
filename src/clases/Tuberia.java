/*
    ----------------- AUTORES -----------------
    Denis Agustin Albornoz, Legajo FAI-3383
    Agustina Magali Montoya, Legajo FAI-4525
    Facundo Diego Tosetto, Legajo FAI-4354
    -------------------------------------------
 */
package clases;

public class Tuberia {
    // ---------------------------------------- ATRIBUTOS ---------------------------------------- //
    private String desdeCiudad; // [ NE3001-CC3002]
    private String hastaCiudad; // [ NE3001-CC3002]
    private double caudalMinimo; // Caudal mínimo en metros cúbicos por hora.
    private double caudalMaximo; // Caudal máximo en metros cúbicos por hora.
    private double diametro; // Diámetro de la tubería en milímetros.
    private char estado; // A: Activo, R: En Reparación, D: En Diseño, I: inactivo.

    // ---------------------------------------- CONSTRUCTOR ---------------------------------------- //
    public Tuberia(String desde, String hasta, double min, double max, double diam, char est) {
        desdeCiudad = desde;
        hastaCiudad = hasta;
        caudalMinimo = min;
        caudalMaximo = max;
        diametro = diam;
        estado = est;
    }

    // ---------------------------------------- MODIFICADORES -------------------------------------- //
    public void setDesdeCiudad(String desdeCiudad) {
        this.desdeCiudad = desdeCiudad;
    }

    public void setHastaCiudad(String hastaCiudad) {
        this.hastaCiudad = hastaCiudad;
    }

    public void setCaudalMinimo(double caudalMinimo) {
        this.caudalMinimo = caudalMinimo;
    }

    public void setCaudalMaximo(double caudalMaximo) {
        this.caudalMaximo = caudalMaximo;
    }

    public void setDiametro(double diametro) {
        this.diametro = diametro;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    // ---------------------------------------- OBSERVADORES --------------------------------------- //
    public String getDesdeCiudad() {
        return desdeCiudad;
    }

    public String getHastaCiudad() {
        return hastaCiudad;
    }

    public double getCaudalMinimo() {
        return caudalMinimo;
    }

    public double getCaudalMaximo() {
        return caudalMaximo;
    }

    public double getDiametro() {
        return diametro;
    }

    public char getEstado() {
        return estado;
    }

    public String toString() {
    return String.format(desdeCiudad + " - " + hastaCiudad +" - " + caudalMaximo);
}
}
