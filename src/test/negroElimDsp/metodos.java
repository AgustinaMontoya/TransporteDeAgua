package test.negroElimDsp;

import clases.*;
import estructuras.conjuntistas.ClaveHashMap;
import estructuras.conjuntistas.TablaAVL;
import estructuras.grafos.*;
import estructuras.lineales.Lista;

import java.time.YearMonth;
import java.util.HashMap;


public class metodos {

    public Lista obtenerNombreYCaudal(TablaAVL t, GrafoEtiquetado g, HashMap m, String nomCiu, int anio, int mes) {

        Lista datos = new Lista();

        if (!g.esVacio()) {
            Ciudad ciu1 = (Ciudad) t.obtenerDato(nomCiu);
            Ciudad ciu2;
            int habitantes = ciu1.getCantHabitantes(anio, mes);
            double consumoProm = ciu1.getConsumoProm();
            //se podria modularizar desde aca
            Lista camino = g.caminoMasCortoDirecto(nomCiu);
            Object elem = camino.recuperar(1);
            Tuberia tuberia;
            double caudalMin = 0;

            while (elem != null) {
                ciu1 = (Ciudad) elem;
                if (camino.recuperar(2) != null) {
                    ciu2 = (Ciudad) camino.recuperar(2);
                    ClaveHashMap clave = new ClaveHashMap(ciu1.getNombre(), ciu2.getNombre());
                    tuberia = (Tuberia) m.get(clave);
                    if (tuberia.getCaudalMinimo() > caudalMin) {
                        caudalMin = tuberia.getCaudalMinimo();
                    }
                }
                camino.eliminar(1);
                elem = camino.recuperar(1);
            }
            //HACER AMBOS CALCULOS Y DEVOLVERLOS

            //calculamos cantHabitantes * consumoProm
            double caudal1 = habitantes * consumoProm;

            //calculamos caudalMinimo por horas de cada dia, para cada dia del mes
            YearMonth fecha = YearMonth.of(anio, mes);
            int diasMes = fecha.lengthOfMonth();
            double caudal2 = diasMes * (24 * caudalMin);

            double caudalFinal = Math.min(caudal1, caudal2);

            datos.insertar(habitantes, 1);
            datos.insertar(caudalFinal, 2);
        }
        return datos;
    }


    public Lista ciudadesConVolumenDet(TablaAVL t, GrafoEtiquetado g, HashMap m, Comparable nom1, Comparable nom2, double vol1, double vol2, int anio, int mes) {

        //este metodo recibe nombres de ciudades, se verifica si dichas ciudades
        //estan cargadas y devuelve las ciudades

        Lista ciudades = new Lista();

        if (t.existeClave(nom1) && t.existeClave(nom2) && g.existeCamino(nom1, nom2)) {
            Lista rango = t.listarRango(nom1, nom2);
            Object elem = rango.recuperar(1);
            double caudal = 0;
            int i = 0;
            while (elem != null) {
                Ciudad ciu = (Ciudad) elem;
                Lista datos = obtenerNombreYCaudal(t, g, m, ciu.getNombre(), anio, mes);
                if (!datos.esVacia()) {
                    caudal = (double) datos.recuperar(2);
                    if (caudal > vol1 && caudal < vol2) {
                        i++;
                        ciudades.insertar(ciu, i);
                    }
                }
                rango.eliminar(1);
            }
        }
        return ciudades;
    }


}
