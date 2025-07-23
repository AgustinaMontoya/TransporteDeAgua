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
    private final String nombre;  // Nombre de la ciudad
    private double superficie;
<<<<<<< HEAD

    private int [][] cantHabitantes = new int[10][13]; // Cantidad de habitantes por mes en un año
    private Comparable nomenclatura;    // Formato CI1234

=======
    private final int[][] cantHabitantes = new int[10][13]; // Cantidad de habitantes por mes en un año
    private Comparable nomenclatura;    // Formato CI1234
>>>>>>> 720a1ca8da07900d1d6f11c8e0e76ef6a943e245
    private double consumoProm; // Consumo promedio de metros cubicos por persona

    // ---------------------------------------- CONSTRUCTOR ---------------------------------------- //
    public Ciudad(String nn, double sp, Comparable nc, double m3) {
        nombre = nn;
        superficie = sp;
        nomenclatura = nc;
        consumoProm = m3;
    }

    // ---------------------------------------- MODIFICADORES -------------------------------------- //
    /*
        Agrega la cantidad de habitantes para un año dado, en la columna 0 se guardan los años, en
        las siguientes columnas se guardan los meses
        [ año | mes | mes | ... | mes ]
        Anterior a este metodo se verifica que el año no haya sido ingresado en la matriz.
    */
    public void setCantHabitantes(int[] hab) {
        boolean exito = false;
        int fil = 0;
        while (fil < cantHabitantes.length && !exito) {
            if (cantHabitantes[fil][0] == 0) {
                for (int col = 0; col < 13; col++) {
                    cantHabitantes[fil][col] = hab[col];
                }
                exito = true;
            }
            fil++;
        }
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
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

    /*
        Devuelve la cantidad de habitantes para un año y mes dado
    */
    public int getCantHabitantes(int anio, int mes) {
        boolean exito = false;
        int i = 0, habitantes = 0;
        while (i < cantHabitantes.length && !exito) {
            if (cantHabitantes[i][0] == anio) {
                habitantes = cantHabitantes[i][mes];
                exito = true;
            }
            i++;
        }
        return habitantes;
    }

    public double getConsumoProm() {
        return consumoProm;
    }

    public boolean equals(Ciudad otraCiudad) {
        return this.nombre.equals(otraCiudad.nombre);
    }

    // -------------------------------------- PROPIAS DEL TIPO ------------------------------------- //
    public String toString() {
        return "Ciudad: " + nombre + ", Nomenclatura: " + nomenclatura +
                ", Superficie: " + superficie + ", Consumo: " + consumoProm;
    }

    public static Ciudad parseCiudad(String nextToken) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseCiudad'");
    }

    /*
       Verifica que un año dado no haya sido ingresado en la matriz.
     */
    public boolean verificarAnio(int anio) {
        boolean verificar = false;
        // El año se almacena en la primera posición del arreglo
        for (int fil = 0; fil < cantHabitantes.length; fil++) {
            if (cantHabitantes[fil][0] == anio) {
                // El año ya está dentro de la matriz
                verificar = true;
            }
        }
        return verificar;
    }
}
