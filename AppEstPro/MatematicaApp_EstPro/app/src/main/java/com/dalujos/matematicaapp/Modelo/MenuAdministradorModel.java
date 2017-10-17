package com.dalujos.matematicaapp.Modelo;

/**
 * Created by JoseFernando on 10/05/2017.
 */
public class MenuAdministradorModel {

    private static final String PREFERENCES_FILE = "mymaterialapp_settings";
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    public void inicializar(String nivel, String subtitulo) {
        nivel = "";
        subtitulo = "";
    }

    public static String getPreferencesFile() {
        return PREFERENCES_FILE;
    }

    public static String getPrefUserLearnedDrawer() {
        return PREF_USER_LEARNED_DRAWER;
    }

    public static String getStateSelectedPosition() {
        return STATE_SELECTED_POSITION;
    }
}
