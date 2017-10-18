package com.dalujos.matematicaapp.Adapter;

import java.util.ArrayList;

/**
 * Created by JoseFernando on 11/06/2017.
 */

public class MenuDatos {

    public static String[] placeNameArray = {
            "Agregar Estudiante",
            "Consultar Estudiante",
            "Agregar Profesor",
            "Consultar Profesor",
            "Agregar Créditos"

    };

    public static String[] placeImageArray = {
            "ima_lo0",
            "ima_lo0",
            "ima_lo1",
            "ima_lo1",
            "ima_lo2",
            "ima_lo3",
            "ima_lo4"
    };

    public static ArrayList<Menu> placeList() {
        ArrayList<Menu> list = new ArrayList<>();
        for (int i = 0; i < placeNameArray.length; i++) {
            Menu place = new Menu();
            place.name = placeNameArray[i];
            place.imageName = placeImageArray[i];
            list.add(place);
        }
        return (list);
    }

    public static Menu getItem(String _id) {
        for (Menu place : placeList()) {
            if (place.id.equals(_id)) {
                return place;
            }
        }
        return null;
    }
}