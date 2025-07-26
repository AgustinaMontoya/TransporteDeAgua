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
import estructuras.lineales.Lista;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import java.io.IOException;
import estructuras.conjuntistas.TablaAVL;

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
                case 0: {
                    cargarCiudades();
                    cargarTuberias();
                }
                break;
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
                    //consultaCiudades();
                }

                break;
                case 5: {
                    //consultaTransporteAgua();
                }

                break;
                case 6: {
                    //listadoConsumo();
                }

                break;
                case 7: {
                    //sistema();
                }

                break;
                case 8: {
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
            FileReader archivo = new FileReader("src/textos/ciudades.txt");
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
                ciudad = new Ciudad(nombre,superficie,nomenclatura, consumo);
                mapa.insertarVertice(ciudad.getNomenclatura());
                tablaCiudades.insertar(ciudad.getNombre(), ciudad);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Significa que el archivo que queriamos leer no existe.");
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
    }
    public static Ciudad buscarCiudad(Comparable nombreCiudad) {
        Ciudad ciudad = null;
        Comparable clave = nombreCiudad;
        if (tablaCiudades.obtenerDato(clave) != null) {
            ciudad = (Ciudad) tablaCiudades.obtenerDato(clave);
        } else {
            System.out.println("La ciudad no se encuentra en el sistema.");
        }
        return ciudad;

    }

    
    public static void cargarTuberias() {
        try {
            FileReader archivo = new FileReader("src/textos/tuberias.txt");
            BufferedReader bf = new BufferedReader(archivo);
            String linea;
            while ((linea = bf.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                StringTokenizer st = new StringTokenizer(linea, ",");
                String nomCiudadD, nomCiudadH;
                double caudalMax,caudalMin, diametro;
                char estado;
                Tuberia tuberia;
                nomCiudadD = st.nextToken();
                nomCiudadH = st.nextToken();
                caudalMin = Double.parseDouble(st.nextToken());
                caudalMax = Double.parseDouble(st.nextToken());
                diametro = Double.parseDouble(st.nextToken());
                estado = st.nextToken().charAt(0);
                tuberia = new Tuberia(nomCiudadD,nomCiudadH, caudalMin, caudalMax, diametro,estado);
                mapa.insertarArco(nomCiudadD,nomCiudadH,caudalMax);
                ClaveHashMap clave = new ClaveHashMap(nomCiudadD, nomCiudadH);
                tuberiasMap.put(clave, tuberia);
                
            }
            System.out.println(tuberiasMap.toString());
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
    
//--------------------------------------   MODIFICACION DE CIUDADES   -------------------------------------------------------------------------------------------------------------------------------------------------//

    public static void trabajarCiudades() {

        Scanner sc = new Scanner(System.in);
        Ciudad cdad;
        int eleccion;
        String nombre;
        char opcion;
        System.out.println("Ingrese 1 si desea dar de alta una ciudad");
        System.out.println("Ingrese 2 si desea eliminar una ciudad");
        System.out.println("Ingrese 3 si desea modificar una ciudad"); 
        eleccion = sc.nextInt();

        switch (eleccion) {
            case 1: {   //AÑADIR UNA NUEVA CIUDAD
                System.out.println("Ingrese el nombre de la ciudad que desea añadir");
                sc.nextLine();
                nombre = sc.nextLine().toUpperCase();
                if (tablaCiudades.existeClave(nombre)) {
                    System.out.println("La ciudad ya se encuentra cargada");
                } else {
                    Comparable nomenclatura;
                    double superficie, consumo;
                    System.out.println("Ingrese la nomenclatura, superficie y consumo de la ciudad");
                    // Le agrego el upperCase para cuando se ingrese un nombre por teclado coincida
                    nomenclatura = sc.nextLine().toUpperCase();
                    superficie = Double.parseDouble(sc.nextLine());
                    consumo = Double.parseDouble(sc.nextLine());
                    cdad = new Ciudad(nombre,superficie,nomenclatura, consumo);
                    mapa.insertarVertice(cdad.getNomenclatura());
                    tablaCiudades.insertar(cdad.getNombre(), cdad);
                    System.out.println("La ciudad fue dada de alta con exito");
                    System.out.println(mapa);
                }
            }
            break;
            case 2: {   //ELIMINAR UNA CIUDAD
                System.out.println("Ingrese el nombre de la ciudad que desea eliminar");
                sc.nextLine();
                nombre = sc.nextLine().toUpperCase();
                if (tablaCiudades.existeClave(nombre)) {
                    cdad = buscarCiudad(nombre);
                    mapa.eliminarVertice(cdad.getNomenclatura());
                    tablaCiudades.eliminar(nombre);
                    System.out.println("La ciudad fue eliminada con exito");
                    Lista l = new Lista();
                    Object[] claves = tuberiasMap.keySet().toArray();
                    int i = 0;

                    while (i < claves.length) {
                        ClaveHashMap clave = (ClaveHashMap) claves[i];

                        if (clave.getOrigen().equals(nombre) || clave.getDestino().equals(nombre)) {
                            l.insertar(clave,1);  // Guardamos la clave que debe eliminarse
                        }

                        i++;
                    }
                    while (!l.esVacia()) {
                        ClaveHashMap claveAEliminar = (ClaveHashMap) l.recuperar(1);
                        tuberiasMap.remove(claveAEliminar);  // Quitamos del HashMap
                        l.eliminar(1);  // Quitamos de la lista
                    }
                    System.out.println(tuberiasMap.toString());
                } else {
                        System.out.println("La ciudad no se encuentra en el sistema");
                }
            }break;
            case 3: { //MODIFICAR UNA CIUDAD
                System.out.println("Ingrese el nombre de la ciudad que desea modificar");
                sc.nextLine();
                nombre = sc.nextLine().toUpperCase();
                if (tablaCiudades.existeClave(nombre)) {
                    cdad = buscarCiudad(nombre);
                    System.out.println("Ingrese C si desea cambiar el consumo o N si quiere modificar la nomenclatura");
                    opcion = sc.next().charAt(0);
                    if (opcion == 'C') {
                        System.out.println("Ingrese la nueva nomenclatura de la ciudad");
                        sc.nextLine();
                        cdad.setNomenclatura(sc.nextLine().toUpperCase());
                    } else if (opcion == 'N') {
                        System.out.println("Ingrese la nueva nomenclatura de la ciudad");
                        sc.nextLine();
                        cdad.setNomenclatura(sc.nextLine().toUpperCase());
                    }
                    System.out.println(cdad.toString());
                } else {
                    System.out.println("La ciudad no se encuentra en el sistema");
                }

            }break;
        }
    }

    public static void trabajarTuberias() {

        Scanner sc = new Scanner(System.in);
        int eleccion;
        char opcion;
        System.out.println("Ingrese 1 si desea dar de alta una tuberias");
        System.out.println("Ingrese 2 si desea eliminar una tuberias");
        System.out.println("Ingrese 3 si desea modificar una tuberias"); 
        eleccion = sc.nextInt();
        switch (eleccion) {
            case 1: {   //AÑADIR UNA NUEVA TUBERIA
                System.out.println("Ingrese el nombre de la ciudad de origen y destino de la tuberia");
                sc.nextLine();
                String origen = sc.nextLine().toUpperCase();
                String destino = sc.nextLine().toUpperCase();
                Ciudad cdadOrigen = buscarCiudad(origen);
                Ciudad cdadDestino = buscarCiudad(destino);
                if (mapa.existeArco(cdadOrigen.getNomenclatura(), cdadDestino.getNomenclatura())) {
                    System.out.println("La tuberia ya se encuentra cargada");
                } else {
                    double caudalMax, caudalMin, diametro;
                    char estado;
                    System.out.println("Ingrese el caudal minimo, caudal maximo, diametro y estado de la tuberia");
                    caudalMin = Double.parseDouble(sc.nextLine());
                    caudalMax = Double.parseDouble(sc.nextLine());
                    diametro = Double.parseDouble(sc.nextLine());
                    estado = sc.nextLine().charAt(0);
                    Tuberia tuberia = new Tuberia(origen, destino, caudalMin, caudalMax, diametro, estado);
                    mapa.insertarArco(cdadOrigen.getNomenclatura(), cdadDestino.getNomenclatura(), caudalMax);
                    ClaveHashMap clave = new ClaveHashMap(origen, destino);
                    tuberiasMap.put(clave, tuberia);
                    System.out.println("La tuberia fue dada de alta con exito");
                }
            }
            break;
            case 2: {   //ELIMINAR UNA TUBERIA
                System.out.println("Ingrese el nombre de la ciudad de origen y destino de la tuberia que desea eliminar");
                sc.nextLine();
                String origen = sc.nextLine().toUpperCase();
                String destino = sc.nextLine().toUpperCase();
                Ciudad cdadOrigen = buscarCiudad(origen);
                Ciudad cdadDestino = buscarCiudad(destino);
                if (mapa.existeArco(cdadOrigen.getNomenclatura(), cdadDestino.getNomenclatura())) {
                    mapa.eliminarArco(cdadOrigen.getNomenclatura(), cdadDestino.getNomenclatura());
                    ClaveHashMap clave = new ClaveHashMap(origen, destino);
                    tuberiasMap.remove(clave);
                    System.out.println("La tuberia fue eliminada con exito");
                } else {
                    System.out.println("La tuberia no se encuentra en el sistema");
                }
            }
            break;
            case 3: { //MODIFICAR UNA TUBERIA
                System.out.println("Ingrese el nombre de la ciudad de origen y destino de la tuberia que desea modificar");
                sc.nextLine();
                String origen = sc.nextLine().toUpperCase();
                String destino = sc.nextLine().toUpperCase();
                Ciudad cdadOrigen = buscarCiudad(origen);
                Ciudad cdadDestino = buscarCiudad(destino);
                if (mapa.existeArco(cdadOrigen.getNomenclatura(), cdadDestino.getNomenclatura())) {
                    ClaveHashMap clave = new ClaveHashMap(origen, destino);
                    Tuberia tuberia = tuberiasMap.get(clave);
                    System.out.println("Ingrese E si desea cambiar el estado o D si quiere modificar el diametro");
                    opcion = sc.next().charAt(0);
                    if (opcion == 'E') {
                        System.out.println("Ingrese el nuevo estado de la tuberia");
                        sc.nextLine();
                        tuberia.setEstado(sc.nextLine().charAt(0));
                    } else if (opcion == 'N') {
                        System.out.println("Ingrese el nuevo diametro y por lo tanto el nuevo caudal minimo y maximo de la tuberia");
                        tuberia.setCaudalMinimo(Double.parseDouble(sc.nextLine()));
                        tuberia.setCaudalMaximo(Double.parseDouble(sc.nextLine()));
                        tuberia.setDiametro(Double.parseDouble(sc.nextLine()));
                        tuberia.setEstado(sc.nextLine().charAt(0));
                    System.out.println("La tuberia fue modificada con exito");
                    }
                } else {
                    System.out.println("La tuberia no se encuentra en el sistema");
                }
            }
        }
    }
}


