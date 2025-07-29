/*
    ----------------- AUTORES -----------------
    Denis Agustin Albornoz, Legajo FAI-3383
    Agustina Magali Montoya, Legajo FAI-4525
    Facundo Diego Tosetto, Legajo FAI-4354
    -------------------------------------------
 */
package log;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Log {

    private FileWriter escritor;
    private final String archivo = "src/log/archivoLog.txt";

    public Log() {
        try {
            escritor = new FileWriter(archivo);
            escritor.write("");
            escritor.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void escribir(String texto) {
        try {
            Date fecha = new Date();
            escritor = new FileWriter(archivo, true);
            escritor.write("[" + fecha + "]\t" + texto + "\n");
            escritor.close();
        } catch (IOException e) {
            throw new RuntimeException("[LOG] Error de escritura.");
        }
    }
}
