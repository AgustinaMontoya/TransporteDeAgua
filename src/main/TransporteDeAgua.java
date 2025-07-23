/*
 *-------------------Autores-----------------
 * - Denis Agustin Albornoz, Legajo FAI-3383
 *   Agustina Magali Montoya, Legajo FAI-4525
 *   Facundo Diego Tosetto, Legajo FAI-4354
 --------------------------------------------
 */

package main;

import clases.*;
import estructuras.conjuntistas.ClaveHashMap;
import estructuras.grafos.GrafoEtiquetado;
import estructuras.conjuntistas.ArbolAVL;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import java.io.IOException;
import estructuras.conjuntistas.TablaAVL;
public class TransporteDeAgua {

    static Scanner sc = new Scanner(System.in);
// ------------------------------------------ ESTRUCTURAS ----------------------------------------- //
    private static GrafoEtiquetado mapa = new GrafoEtiquetado();
    private static HashMap<ClaveHashMap, Tuberia> tuberiasMap = new HashMap<>();
    private static TablaAVL tablaCiudades = new TablaAVL();
    public static void main(String[] args) {
        cargarCiudades();
        cargarTuberias();
        System.out.println(mapa);
        /*int numeroIngresado = 0;
        String archivoCiudades = "Acá va el directorio del archivo."; // Se pasan por parametro a los metodos
        String archivoTuberias = "Acá va el directorio del archivo."; // de carga

        
        // --------------------------------------- MENU DE OPCIONES --------------------------------------- //
        while (numeroIngresado != 8) {
            mostrarMenuOpciones();
            numeroIngresado = sc.nextInt();
            switch (numeroIngresado) {
                case 1: {
                    trabajarCiudades();
                }
                break;

                case 2: {
                    trabajarTuberias();
                }

                break;
                case 3: {
                    cargarInfoCiudades();
                }

                break;
                case 4: {
                    consultaCiudades();
                }

                break;
                case 5: {
                    consultaTransporteAgua();
                }

                break;
                case 6: {
                    listadoConsumo();
                }

                break;
                case 7: {
                    sistema();
                }

                break;
                case 0: {
                    System.out.println("Saliendo del sistema...");
                }
                break;
                default:
                    System.out.println("Número ingresado incorrecto. Volver a ingresar un número.");
                    break;
            }
        }*/
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

    


    public static void cargarCiudades() {
        try {
            FileReader archivo = new FileReader("Listado Ciudades - Hoja 1.csv");
            BufferedReader bf = new BufferedReader(archivo);
            String linea;
            while ((linea = bf.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(linea, ",");
                Comparable  nomenclatura;
                String nombre;
                double superficie,consumo;
                Ciudad ciudad;
                nombre = st.nextToken();
                nomenclatura = st.nextToken();
                superficie =  Double.parseDouble(st.nextToken());
                consumo = Double.parseDouble(st.nextToken());
                ciudad = new Ciudad(nombre,superficie,nomenclatura, consumo);
                mapa.insertarVertice(ciudad);
                tablaCiudades.insertar(ciudad.getNomenclatura(), ciudad);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Significa que el archivo que queriamos leer no existe.");
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
    }
    public static Ciudad buscarCiudad(String nombreCiudad) {
        Ciudad ciudad = null;
        Comparable clave = Ciudad.parseCiudad(nombreCiudad).getNomenclatura();
        if (tablaCiudades.obtenerDato(clave) != null) {
            ciudad = (Ciudad) tablaCiudades.obtenerDato(clave);
        } else {
            System.out.println("La ciudad no se encuentra en el sistema.");
        }
        return ciudad;

    }
    public static void cargarTuberias() {
        try {
            FileReader archivo = new FileReader("Lista Ciudad-Ciudad (GRAFO) - Hoja 1.csv");
            BufferedReader bf = new BufferedReader(archivo);
            String linea;
            while ((linea = bf.readLine()) != null) {
                
                StringTokenizer st = new StringTokenizer(linea, ",");
                Ciudad ciudadD, ciudadH;
                String nombreCiudadD, nombreCiudadH;
                double caudalMax;
                char estado;
                Tuberia tuberia;
                nombreCiudadD = st.nextToken();
                nombreCiudadH = st.nextToken();
                ciudadD = buscarCiudad(nombreCiudadD);
                ciudadH = buscarCiudad(nombreCiudadH);
                caudalMax = Double.parseDouble(st.nextToken());
                estado = st.nextToken().charAt(0);
                tuberia = new Tuberia(ciudadD, ciudadH, caudalMin, caudalMax, diametro,estado);
                mapa.insertarArco(ciudadD, ciudadH, caudalMax);
                ClaveHashMap clave = new ClaveHashMap(nombreCiudadD, nombreCiudadH);
                tuberiasMap.put(clave, tuberia);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Significa que el archivo que queríamos leer no existe.");
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algún archivo.");
        }
    }
}
    

