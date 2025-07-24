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
                   // trabajarTuberias();
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
                tablaCiudades.insertar(ciudad.getNomenclatura(), ciudad);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Significa que el archivo que queriamos leer no existe.");
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
    }
    public static Ciudad buscarCiudad(Comparable nomCiudad) {
        Ciudad ciudad = null;
        Comparable clave = nomCiudad;
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
        
        System.out.println("Ingrese 1 si desea dar de alta una ciudad");
        System.out.println("Ingrese 2 si desea eliminar una ciudad");
        System.out.println("Ingrese 3 si desea modificar una ciudad"); 
        eleccion = sc.nextInt();

        switch (eleccion) {
            case 1: {   //AÑADIR UNA NUEVA CIUDAD
                System.out.println("Ingrese el nombre de la ciudad que desea añadir");
                sc.nextLine();
                nombre = sc.nextLine().toUpperCase();
                if (mapa.existeVertice(nombre)) {
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
                    System.out.println("La ciudad fue dada de alta con exito");
                    log = "Se cargó la ciudad " + cdad.getNombre() + " en el sistema";
                    System.out.println(mapa);
                }
            }
            break;
            /*case 2: {  //ELLMINAR UNA CIUDAD
                System.out.println("Ingrese el nombre de la ciudad que desea eliminar");
                
                if (mapa.existeVertice(cdad)) {
                    mapa.eliminarVertice(cdad);
                    System.out.println("La ciudad fue eliminada con exito");
                    log = "Se eliminó la ciudad " + nombre + " del sistema";
                    actualizarLog(log);
                } else {
                    System.out.println("La ciudad no se ecuentra en el mapa");
                }
            }
            break;
            case 3: {   //MODIFICAR UNA CIUDAD
                System.out.println("Ingrese el nombre de la ciudad que desea modificar");
                nombre = sc.nextLine().toUpperCase();
                Ciudad buscada = (Ciudad) mapa.recuperar(nombre);
                if (buscada != null) {
                    System.out.println("Ingrese S/A si desea cambiar la sede o el alojamiento respectivamente");
                    sede = sc.next().equalsIgnoreCase("s");
                    if (sede) {
                        buscada.camibarSede();
                    } else {
                        buscada.cambiarAlojamiento();
                    }
                    System.out.println("El cambio fue realizado correctamente");
                    log = "Se actualizaron los datos de la ciudad " + nombre + " en el sistema";
                    actualizarLog(log);
                } else {
                    System.out.println("La ciudad ingresada no se encuentra en el mapa");
                }
            }
            break;
            default: {
                System.out.println("Ingresó una opcion invalida");

            }
            ;
            break;*/
        }

    }
}


