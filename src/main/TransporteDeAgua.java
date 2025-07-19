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

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TransporteDeAgua {

    static Scanner sc = new Scanner(System.in);
// ------------------------------------------ ESTRUCTURAS ----------------------------------------- //
        GrafoEtiquetado mapa = new GrafoEtiquetado();

    public static void main(String[] args) {
        int numeroIngresado = 0;
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

    


    public void cargarCiudades() {
        try {
            FileReader archivo = new FileReader("");
            BufferedReader bf = new BufferedReader(archivo);
            String linea;
            while ((linea = bf.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(linea, ",");
                Object  nomenclatura;;
                String nombre;
                double superficie,consumo;
                Ciudad ciudad;
                nombre = st.nextToken();
                nomenclatura = st.nextToken();
                superficie =  Double.parseDouble(st.nextToken());
                consumo = Double.parseDouble(st.nextToken());
                ciudad = new Ciudad(nombre,superficie,nomenclatura, consumo);
                mapa.insertarVertice(ciudad);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Significa que el archivo que queriamos leer no existe.");
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
    }

    public void cargarTuberias() {
        try {
            FileReader archivo = new FileReader("");
            BufferedReader bf = new BufferedReader(archivo);
            String linea;
            while ((linea = bf.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(linea, ",");
                Ciudad ciudadD, ciudadH;
                double caudalMin,caudalMax,diametro;
                char estado;
                Tuberia tuberia;
                ciudadD = Ciudad.parseCiudad(st.nextToken());
                ciudadH = Ciudad.parseCiudad(st.nextToken());
                caudalMin = Double.parseDouble(st.nextToken());
                caudalMax = Double.parseDouble(st.nextToken());
                diametro = Double.parseDouble(st.nextToken());
                estado = st.nextToken().charAt(0);
                tuberia = new Tuberia(ciudadD, ciudadH, caudalMin, caudalMax, diametro,estado);
                mapa.insertarArco(ciudadD, ciudadH, caudalMax);// ver
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Significa que el archivo que queríamos leer no existe.");
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algún archivo.");
        }
    }
}
    

