/*
 *-------------------Autores-----------------
 * - Denis Agustin Albornoz, Legajo FAI-3383
 *   Agustina Magali Montoya, Legajo FAI-4525
 *   Facundo Diego Tosetto, Legajo FAI-4354
 --------------------------------------------
 */
package clases;

public class Tuberia {
    // ---------------------------------------- ATRIBUTOS ---------------------------------------- //
    private Ciudad desdeCiudad; // [ NE3001-CC3002]
    private Ciudad hastaCiudad; // [ NE3001-CC3002]
    private double caudalMinimo; // Caudal mínimo en metros cúbicos por hora.
    private double caudalMaximo; // Caudal máximo en metros cúbicos por hora.
    private double diametro; // Diámetro de la tubería en milímetros.
    private char estado; // A: Activo, R: En Reparación, D: En Diseño, I: inactivo.

    // ---------------------------------------- CONSTRUCTOR ---------------------------------------- //
    public Tuberia(Ciudad desde, Ciudad hasta, double min, double max, double diam, char est) {
        desdeCiudad = desde;
        hastaCiudad = hasta;
        caudalMinimo = min;
        caudalMaximo = max;
        diametro = diam;
        estado = est;
    }

    // ---------------------------------------- MODIFICADORES -------------------------------------- //
    public void setDesdeCiudad(Ciudad desdeCiudad) {
        this.desdeCiudad = desdeCiudad;
    }

    public void setHastaCiudad(Ciudad hastaCiudad) {
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
    public Ciudad getDesdeCiudad() {
        return desdeCiudad;
    }

    public Ciudad getHastaCiudad() {
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
}
