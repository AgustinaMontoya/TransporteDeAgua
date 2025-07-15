package clases;

public class Ciudad {
    // ---------------------------------------- ATRIBUTOS ---------------------------------------- //
    private String nombre;  // Nombre de la ciudad
    private double superficie;
    private int[] cantHabitantes = new int[12];
    private Object nomenclatura;
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

    public void setCantHabitantes(int[] cantHabitantes) {
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

    public int[] getCantHabitantes() {
        return cantHabitantes;
    }

    public double getConsumoProm() {
        return consumoProm;
    }
}
