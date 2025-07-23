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
<<<<<<< HEAD
    private int[] cantHabitantes = new int[12]; // Cantidad de habitantes por mes en un año
    private Comparable nomenclatura;    // Formato CI1234
=======
    private int [][] cantHabitantes = new int[10][13]; // Cantidad de habitantes por mes en un año
    private Object nomenclatura;    // Formato CI1234
>>>>>>> 91a0a3aa6eafdcb8a6d54513a4e8249a28c0f719
    private double consumoProm; // Consumo promedio de metros cubicos por persona

    // ---------------------------------------- CONSTRUCTOR ---------------------------------------- //
    public Ciudad(String nn, double sp, Comparable nc, double m3) {
        nombre = nn;
        superficie = sp;
        nomenclatura = nc;
        consumoProm = m3;
    }

    // ---------------------------------------- MODIFICADORES -------------------------------------- //

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public void setCantHabitantes(int[][] cantHabitantes) {
        this.cantHabitantes = cantHabitantes;
    }

    public void setNomenclatura(Comparable nomenclatura) {
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

    public Comparable getNomenclatura() {
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

    public String toString() {
    return "Ciudad: " + nombre + ", Nomenclatura: " + nomenclatura +
           ", Superficie: " + superficie + ", Consumo: " + consumoProm;
    }
    public static Ciudad parseCiudad(String nextToken) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseCiudad'");
    }
}
