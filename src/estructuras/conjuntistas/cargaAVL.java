package estructuras.conjuntistas;
import java.io.BufferedReader;
import java.io.FileReader;

public class cargaAVL {
    public static void main(String[] args) {

        TablaAVL tabla = new TablaAVL();

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\agust\\OneDrive\\Escritorio\\TXTs\\Ciudad.txt"))) {
            String linea;
            String [] columna;
            while ((linea = br.readLine()) != null) {
                columna=linea.split("-");
                tabla.insertar(columna[0],columna[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(tabla.toString());
    }


}
