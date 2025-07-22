/*
 *-------------------Autores-----------------
 * - Denis Agustin Albornoz, Legajo FAI-3383
 *   Agustina Magali Montoya, Legajo FAI-4525
 *   Facundo Diego Tosetto, Legajo FAI-4354
 --------------------------------------------
 */
package clases;

public class Ciudad {
    // ---------------------------------------- ATRIBUTOS ---------------------------------------- //
    private String nombre;  // Nombre de la ciudad
    private double superficie;
    private int [][] cantHabitantes = new int[10][13]; // Cantidad de habitantes por mes en un año
    private Object nomenclatura;    // Formato CI1234
    private double consumoProm; // Consumo promedio de metros cubicos por persona

    // ---------------------------------------- CONSTRUCTOR ---------------------------------------- //
    public Ciudad(String nn, double sp, Object nc, double m3) {
        nombre = nn;
        superficie = sp;
        nomenclatura = nc;
        consumoProm = m3;
    }

    // ---------------------------------------- MODIFICADORES -------------------------------------- //
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public void setCantHabitantes(int[][] cantHabitantes) {
        this.cantHabitantes = cantHabitantes;
    }

    public void setNomenclatura(Object nomenclatura) {
        this.nomenclatura = nomenclatura;
    }

    public void setConsumoProm(double consumoProm) {
        this.consumoProm = consumoProm;
    }

    // ---------------------------------------- OBSERVADORES --------------------------------------- //
    public String getNombre() {
        return nombre;
    }

    public double getSuperficie() {
        return superficie;
    }

    public Object getNomenclatura() {
        return nomenclatura;
    }

    public int getCantHabitantes(int anio ,int mes) {

        int i=0, habitantes = 0;

        while (i < cantHabitantes.length && habitantes != 0) {
            if (cantHabitantes[i][12] == anio) {
                habitantes = cantHabitantes[i][mes - 1];
            }
            i++;
        }
        return habitantes;
    }

    public double getConsumoProm() {
        return consumoProm;
    }

    public static Ciudad parseCiudad(String nextToken) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseCiudad'");
    }
}
