/*
    ----------------- AUTORES -----------------
    Denis Agustin Albornoz, Legajo FAI-3383
    Agustina Magali Montoya, Legajo FAI-4525
    Facundo Diego Tosetto, Legajo FAI-4354
    -------------------------------------------
 */

package main;

import clases.*;
import estructuras.conjuntistas.ClaveHashMap;
import estructuras.conjuntistas.HeapMaximo;
import estructuras.grafos.GrafoEtiquetado;
import estructuras.conjuntistas.TablaAVL;
import estructuras.lineales.Lista;
import log.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TransporteDeAgua {

    static Scanner sc = new Scanner(System.in);

    // ------------------------------------------------- ESTRUCTURAS ------------------------------------------------ //
    private static final GrafoEtiquetado mapa = new GrafoEtiquetado();
    private static final HashMap<ClaveHashMap, Tuberia> tuberiasMap = new HashMap<>();
    private static final TablaAVL tablaCiudades = new TablaAVL();
    public static Log archivoLog = new Log();

    // ---------------------------------------------------- MAIN ---------------------------------------------------- //
    public static void main(String[] args) {
        archivoLog.escribir(">   PROGRAMA INICIALIZADO  <");

        // ------------------------------------------- VARIABLES ------------------------------------------ //
        int numeroIngresado = 0;
        int anio;
        Ciudad ciudad;

        // --------------------------------------- MENU DE OPCIONES --------------------------------------- //
        while (numeroIngresado != 8) {
            mostrarMenuOpciones();
            System.out.print("Opción: ");
            numeroIngresado = sc.nextInt();
            switch (numeroIngresado) {
                case 0: {
                    System.out.println("-------------------------------------------------------------------------------------------------"
                            + "\nInicializando carga de datos...");
                    cargarCiudades();
                    cargarTuberias();
                    cargarHabitantes();
                    System.out.println("Se ha finalizado la carga de datos.");
                    archivoLog.escribir("FINALIZÓ CARGA DE DATOS");
                    archivoLog.escribir("\nGrafo de ciudades y tuberias: \n"
                            + mapa.toString()
                            + "\nTabla de ciudades: \n"
                            + tablaCiudades.listarClaves()
                            + "\nMapa de tuberias: \n"
                            + tuberiasMap.toString());
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
                    System.out.println("-------------------------------------------------------------------------------------------------");
                    System.out.println("Altas de información de la cantidad de habitantes para año y ciudad dada.");
                    System.out.print("Ingresar ciudad: ");
                    ciudad = verificarCiudad();
                    System.out.print("Ingresar año: ");
                    anio = verificarAnio(ciudad);
                    cargarHabitantes(ciudad, anio);
                }
                break;
                case 4: {
                    consultaCiudades();
                }
                break;
                case 5: {
                    System.out.println("-------------------------------------------------------------------------------------------------");
                    consultaTransporteAgua();
                }
                break;
                case 6: {
                    System.out.println("-------------------------------------------------------------------------------------------------");
                    System.out.print("Ingrese el año: ");
                    anio = sc.nextInt();
                    Lista lis = consumoDeAguaAnual(anio);
                    System.out.println(lis);
                }
                break;
                case 7: {
                    System.out.println("-------------------------------------------------------------------------------------------------");
                    sistema();
                }
                break;
                case 8: {
                    System.out.println("-------------------------------------------------------------------------------------------------");
                    System.out.println("Saliendo del sistema...");
                    System.out.println("-------------------------------------------------------------------------------------------------");
                    archivoLog.escribir("DEBUGGING"
                            + "\nGrafo de ciudades y tuberias: \n"
                            + mapa.toString()
                            + "\nTabla de ciudades: \n"
                            + tablaCiudades.toString()
                            + "\nMapa de tuberias: \n"
                            + tuberiasMap.toString());
                    archivoLog.escribir(">    PROGRAMA FINALIZADO <");
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
        System.out.println("-------------------------------------------------------------------------------------------------"
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
                + "\n-------------------------------------------------------------------------------------------------");
    }

    // ----------------------------------------------- CARGA DE DATOS -----------------------------------------------//
    public static void cargarCiudades() {
        try {
            FileReader archivo = new FileReader("src/recursos/ciudades.txt");
            BufferedReader bf = new BufferedReader(archivo);
            String linea;
            Comparable nomenclatura;
            String nombre;
            double superficie, consumo;
            Ciudad ciudad;
            while ((linea = bf.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(linea, ",");
                nombre = st.nextToken().toUpperCase();
                // Le agrego el upperCase para cuando se ingrese un nombre por teclado coincida
                nomenclatura = st.nextToken().toUpperCase();
                superficie = Double.parseDouble(st.nextToken());
                consumo = Double.parseDouble(st.nextToken());
                ciudad = new Ciudad(nombre, superficie, nomenclatura, consumo);
                mapa.insertarVertice(ciudad.getNomenclatura());
                tablaCiudades.insertar(ciudad.getNombre(), ciudad);
                archivoLog.escribir("Se ha cargado la ciudad: " + ciudad.getNombre() + " con nomenclatura: " + ciudad.getNomenclatura());
            }
            bf.close();
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
            FileReader archivo = new FileReader("src/recursos/tuberias.txt");
            BufferedReader bf = new BufferedReader(archivo);
            String linea;
            while ((linea = bf.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                StringTokenizer st = new StringTokenizer(linea, ",");
                String nomCiudadD, nomCiudadH;
                double caudalMax, caudalMin, diametro;
                char estado;
                Tuberia tuberia;
                nomCiudadD = st.nextToken();
                nomCiudadH = st.nextToken();
                caudalMin = Double.parseDouble(st.nextToken());
                caudalMax = Double.parseDouble(st.nextToken());
                diametro = Double.parseDouble(st.nextToken());
                estado = st.nextToken().charAt(0);
                tuberia = new Tuberia(nomCiudadD, nomCiudadH, caudalMin, caudalMax, diametro, estado);
                mapa.insertarArco(nomCiudadD, nomCiudadH, caudalMax);
                ClaveHashMap clave = new ClaveHashMap(nomCiudadD, nomCiudadH);
                tuberiasMap.put(clave, tuberia);
                archivoLog.escribir("Se ha cargado la tubería de " + nomCiudadD + " a " + nomCiudadH + " con caudal máximo: " + caudalMax);
            }
            bf.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Significa que el archivo que queríamos leer no existe.");
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algún archivo.");
        }
    }

    public static void cargarHabitantes() {
        try {
            FileReader archivo = new FileReader("src/recursos/CantidadHabitantesPorCiudad.txt");
            BufferedReader bf = new BufferedReader(archivo);
            String linea, nombre;
            int[] habitantes = new int[13];
            while ((linea = bf.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(linea, ",");
                nombre = st.nextToken().toUpperCase();
                for (int col = 0; col < 13; col++) {
                    habitantes[col] = Integer.parseInt(st.nextToken());
                }
                Ciudad ciudad = buscarCiudad(nombre);
                if (ciudad != null) {
                    ciudad.setCantHabitantes(habitantes);
                } else {
                    System.err.println("Ciudad no encontrada: " + nombre);
                }
            }
            bf.close();
        } catch (FileNotFoundException ex) {
            System.err.println("El archivo no existe.");
        } catch (IOException ex) {
            System.err.println("Error de lectura/escritura.");
        }
    }

    //----------------------------------------------------------------------------------------------------------------//
    public static Ciudad verificarCiudad() {
        String nombre = null;
        Ciudad pertenece = null;
        while (pertenece == null) {
            nombre = sc.next().toUpperCase();
            pertenece = buscarCiudad(nombre);
            if (pertenece == null) {
                System.out.print("Vuelva a ingresar una ciudad: ");
            }
        }
        return pertenece;
    }

    public static int verificarAnio(Ciudad ciudad) {
        /* Está pensado para que el rango de años sea de 10 */
        int anio = 0;
        boolean existe = false;
        while (!existe) {
            anio = sc.nextInt();
            existe = ciudad.verificarAnio(anio);
            if (existe) {
                System.out.print("El año ya fue registrado.\nVuelva a ingresar el año: ");
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

    // --------------------------------------   MODIFICACION DE CIUDADES   -------------------------------------------//

    public static void trabajarCiudades() {

        Scanner sc = new Scanner(System.in);
        Ciudad cdad;
        int eleccion = 0;
        String nombre;
        char opcion;
        while (eleccion != 4) {
            System.out.println("Ingrese 1 si desea dar de alta una ciudad");
            System.out.println("Ingrese 2 si desea eliminar una ciudad");
            System.out.println("Ingrese 3 si desea modificar una ciudad");
            System.out.println("Ingrese 4 si desea salir del menu de ciudades");
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
                        cdad = new Ciudad(nombre, superficie, nomenclatura, consumo);
                        mapa.insertarVertice(cdad.getNomenclatura());
                        tablaCiudades.insertar(cdad.getNombre(), cdad);
                        System.out.println("La ciudad fue dada de alta con exito");
                        archivoLog.escribir("Se ha cargado la ciudad: " + cdad.getNombre());
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
                                l.insertar(clave, 1);  // Guardamos la clave que debe eliminarse
                            }

                            i++;
                        }
                        while (!l.esVacia()) {
                            ClaveHashMap claveAEliminar = (ClaveHashMap) l.recuperar(1);
                            tuberiasMap.remove(claveAEliminar);  // Quitamos del HashMap
                            l.eliminar(1);  // Quitamos de la lista
                        }
                        archivoLog.escribir("Se ha eliminado la ciudad: " + cdad.getNombre());
                    } else {
                        System.out.println("La ciudad no se encuentra en el sistema");
                    }
                }
                break;
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
                        archivoLog.escribir("Se ha modificado la ciudad: " + cdad.getNombre());
                    } else {
                        System.out.println("La ciudad no se encuentra en el sistema");
                    }

                }
                break;
            }
        }
    }

    public static void trabajarTuberias() {

        Scanner sc = new Scanner(System.in);
        int eleccion = 0;
        char opcion;
        while (eleccion != 4) {
            System.out.println("Ingrese 1 si desea dar de alta una tuberias");
            System.out.println("Ingrese 2 si desea eliminar una tuberias");
            System.out.println("Ingrese 3 si desea modificar una tuberias");
            System.out.println("Ingrese 4 si desea salir del menu de tuberias");
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
                        archivoLog.escribir("Se ha cargado la tubería de " + origen + " a " + destino + " con caudal máximo: " + caudalMax);
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
                        archivoLog.escribir("Se ha eliminado la tubería de " + origen + " a " + destino);
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
                        archivoLog.escribir("Se ha modificado la tubería de " + origen + " a " + destino + " con caudal máximo: " + tuberia.getCaudalMaximo());
                    } else {
                        System.out.println("La tuberia no se encuentra en el sistema");
                    }
                }
                break;
            }
        }
    }

    //----------------------------------------------------------------------------------------------------------------//

    public static void consultaCiudades() {

        int num = 4;
        int anio;
        int mes;
        Ciudad ciudad;

        while (num != 0) {
            System.out.println("-------------------------------------------------------------------------------------------------"
                    + "\nElija la opción: "
                    + "\n[1] : Obtener la cantidad de habitantes y el volumen de agua distribuido de una ciudad, para " +
                    "      un año y un mes determinado"
                    + "\n[2] : Obtener todas las ciudades en un rango de dos ciudades, que su caudal consumido este " +
                    "      entre dos volumenes, para un año y un mes determinado "
                    + "\n[3] : Salir al menú principal");
            System.out.print("Opción: ");
            num = sc.next().toUpperCase().charAt(0);
            switch (num) {
                case '1': {
                    System.out.print("Ingrese el nombre de la ciudad a consultar: ");
                    ciudad = verificarCiudad();
                    System.out.print("Ingrese el año a consultar: ");
                    anio = verificarExisteAnio(ciudad);
                    System.out.print("Ingrese el mes a consultar: ");
                    mes = verificarExisteMes();
                    Lista datos = obtenerHabitantesYCaudal(ciudad, anio, mes);
                    System.out.println("La cantidad de habitantes es: " + datos.recuperar(1).toString());
                    System.out.println("La el volumen de agua distribuido en la ciudad es: " + datos.recuperar(2).toString());
                }
                break;
                case '2': {
                    Ciudad ciudad2;
                    double vol1, vol2;
                    System.out.print("Ingrese el nombre de la ciudad 1 a consultar: ");
                    ciudad = verificarCiudad();
                    System.out.print("Ingrese el nombre de la ciudad 2 a consultar: ");
                    ciudad2 = verificarCiudad();
                    System.out.print("Ingrese el volumen 1: ");
                    vol1 = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Ingrese el volumen 2: ");
                    vol2 = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Ingrese el año a consultar: ");
                    anio = verificarExisteAnio(ciudad);
                    sc.nextLine();
                    System.out.print("Ingrese el mes a consultar: ");
                    mes = verificarExisteMes();
                    sc.nextLine();
                    Lista ciudades = ciudadesConVolumenDet(ciudad.getNombre(), ciudad2.getNombre(), vol1, vol2, anio, mes);
                    System.out.println(ciudades.toString());
                }
                break;
            }
        }
    }

    private static int verificarExisteMes() {
        int mes = 0;
        boolean existe = false;
        while (!existe) {
            mes = sc.nextInt();
            if ((mes > 12) || (mes < 1)) {
                System.out.print("Mes ingresado incorrecto. \nVuelva a ingresar el mes: ");
            } else {
                existe = true;
            }
        }
        return mes;
    }

    private static int verificarExisteAnio(Ciudad ciudad) {
        int anio = -1;
        boolean existe = false;
        while (!existe) {
            anio = sc.nextInt();
            existe = ciudad.verificarAnio(anio);
            if (!existe) {
                System.out.print("El año ingresado es incorrecto.\nVuelva a ingresar un año: ");
            }
        }
        return anio;
    }

    public static void consultaTransporteAgua() {

        char linea;
        System.out.println("Elija la opcion: " +
                "\n A :Obtener el camino de menor caudal entre 2 ciudades" +
                "\n B :Obtener el camino entre 2 ciudades mas corto,y su estado" +
                "\n C :Obtener el camino entre 2 ciudades de menor caudal,y que NO pase por una ciudad" +
                "\n D :Obtener todos los caminos posibles entre 2 ciudades,que puedan transportar un caudal indicado");
        linea = sc.next().charAt(0);
        switch (linea) {
            case 'A': {
                String ciudad, ciudad2;
                System.out.println("Ingrese el nombre de la ciudad 1 a consultar");
                ciudad = sc.nextLine();
                System.out.println("Ingrese el nombre de la ciudad 2 a consultar");
                ciudad2 = sc.nextLine();
                Lista ciudades = obtenerCaminoMenorCaudal(ciudad, ciudad2);
                ciudades.toString();
                sc.next();
            }
            case 'B': {
                String ciudad, ciudad2;
                System.out.println("Ingrese el nombre de la ciudad 1 a consultar");
                ciudad = sc.nextLine();
                System.out.println("Ingrese el nombre de la ciudad 2 a consultar");
                ciudad2 = sc.nextLine();
                Lista ciudades = caminoMasCortoConEstado(ciudad, ciudad2);
                ciudades.toString();
                sc.next();
            }
            case 'C': {
                String ciudad, ciudad2, ciudadEvitar;
                System.out.println("Ingrese el nombre de la ciudad 1 a consultar");
                ciudad = sc.nextLine();
                System.out.println("Ingrese el nombre de la ciudad 2 a consultar");
                ciudad2 = sc.nextLine();
                System.out.println("Ingrese el nombre de la ciudad por la que no se debe pasar");
                ciudadEvitar = sc.nextLine();
                Lista ciudades = mapa.obtenerCaminoSalteandoCiudad(ciudad, ciudad2, ciudadEvitar);
                ciudades.toString();
                sc.next();
            }
            case 'D': {
                String ciudad, ciudad2;
                double caudal;
                System.out.println("Ingrese el nombre de la ciudad 1 a consultar");
                ciudad = sc.nextLine();
                System.out.println("Ingrese el nombre de la ciudad 2 a consultar");
                ciudad2 = sc.nextLine();
                System.out.println("Ingrese el caudal maximo");
                caudal = sc.nextDouble();
                Lista ciudades = caminoDeMaximoCaudal(ciudad, ciudad2, caudal);
                ciudades.toString();
                sc.next();
            }
        }

    }


    private static Lista obtenerHabitantesYCaudal(Ciudad ciu1, int anio, int mes) {

        Lista datos = new Lista();

        if (!mapa.esVacio()) {

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

    private static Lista ciudadesConVolumenDet(Comparable nom1, Comparable nom2, double vol1, double vol2, int anio,
                                               int mes) {

        //este metodo recibe nombres de ciudades, se verifica si dichas ciudades
        //estan cargadas y devuelve las ciudades

        Lista ciudades = new Lista();

        if (mapa.existeCamino(nom1, nom2)) {
            Lista rango = tablaCiudades.listarRango(nom1, nom2);
            Object elem = rango.recuperar(1);
            double caudal = 0;
            int i = 1;
            while (elem != null) {
                Ciudad ciu = (Ciudad) elem;
                Lista datos = obtenerHabitantesYCaudal(ciu, anio, mes);
                if (!datos.esVacia()) {
                    caudal = (double) datos.recuperar(2);
                    if (caudal > vol1 && caudal < vol2) {
                        ciudades.insertar(ciu, i);
                        i++;
                    }
                }
                rango.eliminar(1);
                elem = rango.recuperar(1);
            }
        }
        return ciudades;
    }

    private static Lista obtenerCaminoMenorCaudal(Object nom1, Object nom2) {

        Lista camino = new Lista();

        if (!mapa.esVacio()) {
            camino = mapa.obtenerCaminoMenorEtiqueta(nom1, nom2);
            camino.eliminar(camino.longitud());
        }

        return camino;
    }

    private static char obtenerEstado(Lista camino) {

        Ciudad ciu1 = (Ciudad) tablaCiudades.obtenerDato((Comparable) camino.recuperar(1));
        Ciudad ciu2 = (Ciudad) tablaCiudades.obtenerDato((Comparable) camino.recuperar(2));
        ClaveHashMap clave;
        Tuberia tub;
        char estado = 'A';

        if (ciu2 != null) {
            while (ciu2 != null) {
                clave = new ClaveHashMap(ciu1.getNomenclatura(), ciu2.getNomenclatura());
                tub = tuberiasMap.get(clave);
                if (tub.getEstado() != 'A') {
                    if (tub.getEstado() == 'R') {
                        estado = 'R';
                    } else if (tub.getEstado() == 'D') {
                        estado = 'D';
                    }
                }
                camino.eliminar(1);
                ciu1 = (Ciudad) tablaCiudades.obtenerDato((Comparable) camino.recuperar(1));
                ciu2 = (Ciudad) tablaCiudades.obtenerDato((Comparable) camino.recuperar(2));
            }
            System.out.println("El estado del camino es: " + estado);
        } else if (camino.longitud() == 1) {
            clave = new ClaveHashMap(ciu1.getNomenclatura(), ciu2.getNomenclatura());
            tub = tuberiasMap.get(clave);
            estado = tub.getEstado();
            System.out.println("El estado del camino es: " + estado);
        }
        return estado;
    }

    public static Lista caminoMasCortoConEstado(Object origen, Object destino) {

        Lista camino = new Lista();

        if (!mapa.esVacio()) {
            Lista visitados = new Lista();

            camino = mapa.recorridoCorto(origen, destino);
            char estado = obtenerEstado(camino);

        }
        return camino;
    }

    public static Lista caminoDeMaximoCaudal(Object origen, Object destino, Object maximoCaudal) {

        Lista posibles = new Lista();

        if (!mapa.esVacio()) {
            mapa.caminosPosibles(origen, destino, (Comparable) maximoCaudal);
        }

        return posibles;
    }

    //----------------------------------------------------------------------------------------------------------------//
    /*
        Dado un año, muestra el listado de ciudades ordenadas por consumo de agua anual de mayor a menor.
     */
    public static Lista consumoDeAguaAnual(int anio) {
        // Creo una lista con todas las ciudades cargadas
        Lista listadoCiudades = tablaCiudades.listarClaves();
        // Lista que devuelve todas las ciudades ordenadas
        Lista consumoCiudades = new Lista();
        // Estructura auxiliar para ordenar los consumos anuales.
        HeapMaximo ordenar = new HeapMaximo();

        String nombreCiudad;
        CiudadAux ciudadConsumo;
        Ciudad ciudad;
        double consumo;

        // Recorro la lista de ciudades.
        while (!listadoCiudades.esVacia()) {
            nombreCiudad = (String) listadoCiudades.recuperar(1);
            ciudad = buscarCiudad(nombreCiudad);
            if (ciudad != null) {
                // Obtengo el consumo de cada ciudad para el año dado.
                consumo = ciudad.getConsumoAnual(anio);
                ciudadConsumo = new CiudadAux(nombreCiudad, consumo);
                // Ordeno los consumos de mayor a menor.
                ordenar.insertar(ciudadConsumo);
            }
            // Elimino la ciudad y sigo avanzando en el listado
            listadoCiudades.eliminar(1);
        }
        // Recorrí todas las ciudades y las agregué al árbol heap

        while (!ordenar.esVacio()) {
            CiudadAux elemento = (CiudadAux) ordenar.recuperarCima();
            ordenar.eliminarCima();
            System.out.println("Ciudad: " + elemento.getCiudad() + " - Consumo Anual: " + elemento.getConsumo());
            consumoCiudades.insertar(elemento.getCiudad(), consumoCiudades.longitud() + 1);
        }
        return consumoCiudades;
    }

    //-------------------------------------------SISTEMA--------------------------------------------------------------------
    public static void sistema() {
        System.out.println("Grafo de ciudades y tuberias: ");
        System.out.println(mapa.toString());
        System.out.println("Tabla de ciudades: ");
        System.out.println(tablaCiudades.listarClaves());
        System.out.println("Mapa de tuberias: ");
        System.out.println(tuberiasMap.toString());
    }
}




