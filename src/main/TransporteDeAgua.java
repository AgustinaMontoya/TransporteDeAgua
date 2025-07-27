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
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import java.io.IOException;
import estructuras.conjuntistas.TablaAVL;
import estructuras.lineales.Lista;

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
    //------------------------------------------------------------------------------------------------------------------

    public static Lista obtenerHabitantesYCaudal(String nomCiu, int anio, int mes) {

        Lista datos = new Lista();

        if (!mapa.esVacio()) {
            Ciudad ciu1 = (Ciudad) tablaCiudades.obtenerDato(nomCiu);
            if (ciu1 != null) {
                int habitantes = ciu1.getCantHabitantes(anio, mes); // ACA HAY UN ERROR
                double consumoProm = ciu1.getConsumoProm();

                //calculamos cantHabitantes * consumoProm
                YearMonth fecha = YearMonth.of(anio, mes);
                int diasMes = fecha.lengthOfMonth();
                double caudalFinal = (habitantes * consumoProm) * diasMes;

                datos.insertar(habitantes, 1);
                datos.insertar(caudalFinal, 2);
            }
        }
        return datos;
    }


    public Lista ciudadesConVolumenDet(Comparable nom1, Comparable nom2, double vol1, double vol2, int anio, int mes) {

        //este metodo recibe nombres de ciudades, se verifica si dichas ciudades
        //estan cargadas y devuelve las ciudades

        Lista ciudades = new Lista();

        if (tablaCiudades.existeClave(nom1) && tablaCiudades.existeClave(nom2) && mapa.existeCamino(nom1, nom2)) {
            Lista rango = tablaCiudades.listarRango(nom1, nom2);
            Object elem = rango.recuperar(1);
            double caudal = 0;
            int i = 0;
            while (elem != null) {
                Ciudad ciu = (Ciudad) elem;
                Lista datos = obtenerHabitantesYCaudal(ciu.getNombre(), anio, mes);
                if (!datos.esVacia()) {
                    caudal = (double) datos.recuperar(2);
                    if (caudal > vol1 && caudal < vol2) {
                        i++;
                        ciudades.insertar(ciu, i);
                    }
                }
                rango.eliminar(1);
                elem = rango.recuperar(1);
            }
        }
        return ciudades;
    }

    //------------------------------------------------------------------------------------------------------------------
}


