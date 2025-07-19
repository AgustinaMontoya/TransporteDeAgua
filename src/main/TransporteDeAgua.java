package main;

import clases.*;

import estructuras.grafos.GrafoEtiquetado;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TransporteDeAgua {
    GrafoEtiquetado mapa = new GrafoEtiquetado();

    public static void main(String[] args) {

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

    public static void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese 1 si desea trabajar con las ciudades");
        System.out.println("Ingrese 2 si desea trabajar con las tuberias");
        System.out.println("Ingrese 3 si desea cargar informacion sobre una ciudad en un año determinado");
        System.out.println("Ingrese 4 si desea realizar consultas sobre las ciudades");
        System.out.println("Ingrese 5 si desea realizar consultas sobre el transporte de agua");
        System.out.println("Ingrese 6 si desea ver un listado de las ciudades ordenadas por consumo en determinado año");
        System.out.println("Ingrese 7 si desea ver las estructuras del sistema");
        System.out.println("Ingrese 0 si desea finalizar");
        int eleccion;
        do {
            System.out.println("\nIngrese una opcion ");
            
            eleccion = sc.nextInt();
            switch (eleccion) {
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
            }
        }while (eleccion != 0);
    }
}

