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
    private final String archivo = "archivoLog.txt";

    public Log() throws IOException {
        escritor = new FileWriter(archivo);
        escritor.write("");
        escritor.close();
    }

    public void escribir(String texto) throws IOException {
        Date fecha = new Date();
        escritor = new FileWriter(archivo, true);
        escritor.write("[" + fecha + "]" + texto + "\n");
        escritor.close();
    }
}
