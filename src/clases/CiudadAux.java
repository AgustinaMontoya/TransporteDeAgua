/*
    ----------------- AUTORES -----------------
    Denis Agustin Albornoz, Legajo FAI-3383
    Agustina Magali Montoya, Legajo FAI-4525
    Facundo Diego Tosetto, Legajo FAI-4354
    -------------------------------------------
 */

package clases;

public class CiudadAux implements Comparable {
    private final String ciudad;
    private double consumo;

    public CiudadAux(String ciudad, double consumo) {
        this.ciudad = ciudad;
        this.consumo = consumo;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public double getConsumo() {
        return consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    @Override
    public int compareTo(Object o) {
        CiudadAux otra = (CiudadAux) o;
        return Double.compare(this.consumo, otra.consumo);
    }
}
