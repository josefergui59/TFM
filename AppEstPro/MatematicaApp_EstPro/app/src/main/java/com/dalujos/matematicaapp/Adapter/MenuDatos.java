package com.dalujos.matematicaapp.Adapter;

import java.util.ArrayList;

/**
 * Created by JoseFernando on 11/06/2017.
 */

public class MenuDatos {

    public static String[] placeNameArrayEstudiante = {
            "Enviar Ejercicio",
            "Consultar Ejercicio",
            "Cambiar Clave"
    };

    public static String[] placeNameArrayProfesor = {
            "Resolver Ejercicio",
            "Cambiar Clave"
    };

    public static String[] placeImageArrayEst = {
            "ima_lo0",
            "ima_lo0",
            "ima_lo0",
            "ima_lo1",
            "ima_lo2",
            "ima_lo3",
            "ima_lo4"
    };
    public static String[] placeImageArrayPro= {
            "ima_lo1",
            "ima_lo1",
            "ima_lo1",
            "ima_lo1",
            "ima_lo2",
            "ima_lo3",
            "ima_lo4"
    };

    public static ArrayList<Menu> placeListEstudiante() {
        ArrayList<Menu> list = new ArrayList<>();
        for (int i = 0; i < placeNameArrayEstudiante.length; i++) {
            Menu place = new Menu();
            place.name = placeNameArrayEstudiante[i];
            place.imageName = placeImageArrayEst[i];
            list.add(place);
        }
        return (list);
    }

    public static ArrayList<Menu> placeListProfesor() {
        ArrayList<Menu> list = new ArrayList<>();
        for (int i = 0; i < placeNameArrayProfesor.length; i++) {
            Menu place = new Menu();
            place.name = placeNameArrayProfesor[i];
            place.imageName = placeImageArrayPro[i];
            list.add(place);
        }
        return (list);
    }

    public static Menu getItem(String _id) {
        for (Menu place : placeListEstudiante()) {
            if (place.id.equals(_id)) {
                return place;
            }
        }
        return null;
    }
}