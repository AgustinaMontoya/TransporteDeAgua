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
import estructuras.conjuntistas.TablaAVL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TransporteDeAgua {

    static Scanner sc = new Scanner(System.in);

    // ------------------------------------------ ESTRUCTURAS ----------------------------------------- //
    private static final GrafoEtiquetado mapa = new GrafoEtiquetado();
    private static final HashMap<ClaveHashMap, Tuberia> tuberiasMap = new HashMap<>();
    private static final TablaAVL tablaCiudades = new TablaAVL();

    // --------------------------------------------- MAIN --------------------------------------------- //
    public static void main(String[] args) {
        // ------------------------------------------- VARIABLES ------------------------------------------ //
        int numeroIngresado = 0;
        int anio = 0;
        Ciudad ciudad = null;

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
                    System.out.println("----------------------------------------------------------------------------");
                    System.out.println("Altas de información de la cantidad de habitantes para año y ciudad dada.");
                    ciudad = verificarCiudad();
                    anio = verificarAnio(ciudad);
                    cargarHabitantes(ciudad, anio);
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

    public static void cargarCiudades() {
        try {
            FileReader archivo = new FileReader("Listado Ciudades - Hoja 1.csv");
            BufferedReader bf = new BufferedReader(archivo);
            String linea;
            while ((linea = bf.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(linea, ",");
                Comparable nomenclatura;
                String nombre;
                double superficie, consumo;
                Ciudad ciudad;
                nombre = st.nextToken().toUpperCase();
                // Le agrego el upperCase para cuando se ingrese un nombre por teclado coincida
                nomenclatura = st.nextToken().toUpperCase();
                superficie = Double.parseDouble(st.nextToken());
                consumo = Double.parseDouble(st.nextToken());
                ciudad = new Ciudad(nombre, superficie, nomenclatura, consumo);
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
                tuberia = new Tuberia(ciudadD, ciudadH, caudalMin, caudalMax, diametro, estado);
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

    //------------------------------------------------------------------------------------------------------------------
    public static Ciudad verificarCiudad() {
        String nombre = null;
        Ciudad pertenece = null;
        while (pertenece == null) {
            System.out.println("Ingresar ciudad: ");
            nombre = sc.nextLine().toUpperCase();
            System.out.print(nombre);
            pertenece = buscarCiudad(nombre);
            if (pertenece == null) {
                System.out.println("La ciudad ingresada no es correcta.\nVuelva a ingresar una ciudad.");
            }
        }
        return pertenece;
    }

    public static int verificarAnio(Ciudad ciudad) {
        /* Está pensado para que el rango de años sea de 10 */
        int anio = 0;
        boolean existe = false;
        while (anio < 2016 || anio > 2025 && !existe) {
            System.out.println("Ingresar año: ");
            anio = sc.nextInt();
            System.out.print(anio);
            existe = ciudad.verificarAnio(anio);
            if (anio < 2016 || anio > 2025) {
                System.out.println("El año ingresado no es correcto.\nVuelva a ingresar el año.");
            } else if (existe) {
                System.out.println("El año ya fue registrado.");
            }
        }
        sc.next(); // Limpia el scanner
        return anio;
    }

    public static void cargarHabitantes(Ciudad ciudad, int anio) {
        int col;
        int cant = 0;
        int[] cantHabitantes = new int[13];
        cantHabitantes[0] = anio;
        System.out.println("Ingrese la cantidad de habitantes por mes: ");
        for (col = 1; col < cantHabitantes.length + 1; col++) {
            System.out.println("Mes " + col + ": ");
            cant = sc.nextInt();
            cantHabitantes[col] = cant;
            System.out.print(cant);
            sc.next();
        }
        ciudad.setCantHabitantes(cantHabitantes);
    }
    //------------------------------------------------------------------------------------------------------------------
}


