package main;

import clases.*;

import estructuras.grafos.GrafoEtiquetado;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TransporteDeAgua {
    GrafoEtiquetado mapa = new GrafoEtiquetado();

    public static void main(String[] args) {

    }


//    public void cargarCiudades() {
//        try {
//            FileReader archivo = new FileReader("");
//            BufferedReader bf = new BufferedReader(archivo);
//            String linea;
//            while ((linea = bf.readLine()) != null) {
//                StringTokenizer st = new StringTokenizer(linea, ",");
//                Object  nombre, nomenclatura, superficie, consumo;
//                Ciudad ciudad;
//                nombre = (String) st.nextToken();
//                nomenclatura = st.nextToken();
//                superficie =  st.nextToken();
//                consumo = st.nextToken();
//                ciudad = new Ciudad((nombre, nomenclatura, superficie, consumo);
//                mapa.insertarVertice(ciudad);
//            }
//        } catch (FileNotFoundException ex) {
//            System.err.println("Significa que el archivo que queriamos leer no existe.");
//        } catch (IOException ex) {
//            System.err.println("Error leyendo o escribiendo en algun archivo.");
//        }
//    }
//
//    public void cargarTuberias() {
//        try {
//            FileReader archivo = new FileReader("");
//            BufferedReader bf = new BufferedReader(archivo);
//            String linea;
//            while ((linea = bf.readLine()) != null) {
//                StringTokenizer st = new StringTokenizer(linea, ",");
//                Object ciudad, habitantes, consumo, aprovisionado, cobertura;
//                Tuberia tuberia;
//                ciudad = st.nextToken();
//                habitantes = st.nextToken();
//                consumo = st.nextToken();
//                aprovisionado = st.nextToken();
//                cobertura = st.nextToken();
//                tuberia = new Tuberia(ciudad, habitantes, consumo, aprovisionado, cobertura);
//                mapa.insertarArco(tuberia);
//            }
//        } catch (FileNotFoundException ex) {
//            System.err.println("Significa que el archivo que queríamos leer no existe.");
//        } catch (IOException ex) {
//            System.err.println("Error leyendo o escribiendo en algún archivo.");
//        }
//    }


}

