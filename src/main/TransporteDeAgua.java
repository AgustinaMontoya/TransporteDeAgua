/*
 *-------------------Autores-----------------
 * - Denis Agustin Albornoz, Legajo FAI-3383
 *   Agustina Magali Montoya, Legajo FAI-4525
 *   Facundo Diego Tosetto, Legajo FAI-4354
 --------------------------------------------
 */

package main;

import clases.*;

import estructuras.grafos.GrafoEtiquetado;

import java.io.BufferedReader; // Se utiliza para leer texto de un archivo de texto.
import java.io.FileReader; //  Se utiliza para leer caracteres de un archivo de texto existente.
import java.io.FileNotFoundException; // Se utiliza para manejar la excepción cuando no se encuentra un archivo.
import java.io.IOException; // Se utiliza para manejar excepciones de entrada y salida, como errores al leer o escribir archivos.
import java.util.StringTokenizer; // Se utiliza para dividir una cadena separada por delimitadores
import java.util.Scanner;

public class TransporteDeAgua {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // ------------------------------------------- VARIABLES ------------------------------------------- //
        int numeroIngresado = 0;
        String archivoCiudades = "Acá va el directorio del archivo."; // Se pasan por parametro a los metodos
        String archivoTuberias = "Acá va el directorio del archivo."; // de carga

        // ------------------------------------------ ESTRUCTURAS ----------------------------------------- //
        GrafoEtiquetado mapa = new GrafoEtiquetado();

        // --------------------------------------- MENU DE OPCIONES --------------------------------------- //
        while (numeroIngresado != 8) {
            mostrarMenuOpciones();
            numeroIngresado = sc.nextInt();
            switch (numeroIngresado) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    /*
                        Alta = insertar una nueva.
                        Baja = eliminar una que esté.
                        Modificación = cambiar algún dato que no sea la clave a una que esté.
                    */
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    System.out.println("----------------------------------------------------------------------------"
                            + "Programa finalizado.");
                    break;
                default:
                    System.out.println("Número ingresado incorrecto. Volver a ingresar un número.");
                    break;
            }
        }
    }

    // ! MODIFICAR a medida que vayamos avanzando
    public static void mostrarMenuOpciones() {
        System.out.println("----------------------------------------------------------------------------"
                + "\n\t MENÚ DE OPCIONES"
                + "\nIngrese el número correspondiente a la operación que desee realizar: "
                + "\n[0] Cargar datos en la estructura. "
                + "\n[1] Altas, bajas y modificaciones de ciudades."
                + "\n[2] Altas, bajas y modificaciones de tuberías."
                + "\n[3] Altas de información de la cantidad de habitantes para año y ciudad dada."
                + "\n[4] Consultas sobre ciudades."
                + "\n[5] Consultas sobre transporte de agua."
                + "\n[6] Mostrar un listado de las ciudades ordenadas por consumo de agua anual de mayor a menor."
                + "\n[7] Debugging."
                + "\n[8] Finalizar programa."
                + "\n----------------------------------------------------------------------------");
    }

    public static void cargarCiudades(String archivoCiudades, GrafoEtiquetado mapa) {
        Ciudad ciudad;
        String linea, nombre;
        double superficie, consumo;
        Object nomenclatura;
        StringTokenizer st;
        try {
            FileReader archivo = new FileReader(archivoCiudades);
            BufferedReader bf = new BufferedReader(archivo);
            while ((linea = bf.readLine()) != null) {
                st = new StringTokenizer(linea, ",");
                nombre = st.nextToken();
                nomenclatura = st.nextToken();
                // REVISAR SI FUNCIONAN BIEN
                superficie = Double.parseDouble(st.nextToken().trim());
                consumo = Double.parseDouble(st.nextToken().trim());
                ciudad = new Ciudad(nombre, superficie, nomenclatura, consumo);
                mapa.insertarVertice(ciudad);
            }
            bf.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage() + "\n No existe el archivo.");
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algún archivo.");
        }
    }

//    public static void cargarTuberias(String archivoTuberias, GrafoEtiquetado mapa) {
//        // ESTE ES UN ARCHIVO QUE NOSOTROS CREAMOS CON LAS CARACTERISTICAS DE TUBERIAS
//        String linea, ori, dest;
//        StringTokenizer st;
//        Ciudad origen, destino;
//        double minimo, maximo, diametro;
//        char estado;
//        Tuberia tuberia;
//        try {
//            FileReader archivo = new FileReader(archivoTuberias);
//            BufferedReader bf = new BufferedReader(archivo);
//            while ((linea = bf.readLine()) != null) {
//                st = new StringTokenizer(linea, ",");
//                ori = st.nextToken();
//                dest = st.nextToken();
//                minimo = Double.parseDouble(st.nextToken().trim());
//                maximo = Double.parseDouble(st.nextToken().trim());
//                diametro = Double.parseDouble(st.nextToken().trim());
//                estado = st.nextToken().charAt(0);
//                // VER ALGUN METODO PARA DEVOLVER LA MISMA CIUDAD QUE YA EXISTE Y ASOCIARLA CON TUBERIA
//                // Creo que se hace con hash
//                origen = buscarCiudad(ori);
//                destino = buscarCiudad(dest);
//                tuberia = new Tuberia(origen, destino, minimo, maximo, diametro, estado);
//                // Para agregar la tuberia hay que agregar desde que ciudad hasta que ciudad, la etiqueta
//                // seria el caudal minimo o máximo dependiendo de que pide
//                mapa.insertarArco(origen, destino, minimo);
//            }
//        } catch (FileNotFoundException ex) {
//            System.err.println(ex.getMessage() + "\n No existe el archivo.");
//        } catch (IOException ex) {
//            System.err.println("Error leyendo o escribiendo en algún archivo.");
//        }
//    }


}

