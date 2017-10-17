package com.dalujos.matematicaapp.CapaDatos;


public class Conexion {

    public static final int CODIGO_DETALLE = 100;

    public static final int CODIGO_ACTUALIZACION = 101;

    private static final String PUERTO_HOST = ""; //":63343";

    private static final String IP = "192.168.100.3";
   // private static final String IP = "192.168.1.130";

    public static final String GET_EST = "http://" + IP + PUERTO_HOST + "/WebServicePhp/obtener_estudiante.php";
    public static final String GET_BY_ID_EST = "http://" + IP + PUERTO_HOST + "/WebServicePhp/obtener_estudiante_por_id.php";
    public static final String UPDATE_EST = "http://" + IP + PUERTO_HOST + "/WebServicePhp/actualizar_estudiante.php";
    public static final String DELETE_EST = "http://" + IP + PUERTO_HOST + "/WebServicePhp/borrar_estudiante.php";
    public static final String INSERT_EST = "http://" + IP + PUERTO_HOST + "/WebServicePhp/insertar_estudiante.php";

    public static final String GET_PRO = "http://" + IP + PUERTO_HOST + "/WebServicePhp/obtener_profesor.php";
    public static final String GET_BY_ID_PRO = "http://" + IP + PUERTO_HOST + "/WebServicePhp/obtener_profesor_por_id.php";
    public static final String UPDATE_PRO = "http://" + IP + PUERTO_HOST + "/WebServicePhp/actualizar_profesor.php";
    public static final String DELETE_PRO = "http://" + IP + PUERTO_HOST + "/WebServicePhp/borrar_profesor.php";
    public static final String INSERT_PRO = "http://" + IP + PUERTO_HOST + "/WebServicePhp/insertar_profesor.php";


    public static final String UPDATE_CRE = "http://" + IP + PUERTO_HOST + "/WebServicePhp/actualizar_credito.php";
    public static final String UPDATE_CRE_EJER = "http://" + IP + PUERTO_HOST + "/WebServicePhp/actualizar_credito_ejer.php";
    public static final String UPDATE_CRE_PAGO = "http://" + IP + PUERTO_HOST + "/WebServicePhp/actualizar_pago.php";
    public static final String UPDATE_PRO_EJER = "http://" + IP + PUERTO_HOST + "/WebServicePhp/actualizar_profesor_ejer.php";

    public static final String GET_EST_LOGIN = "http://" + IP + PUERTO_HOST + "/WebServicePhp/obtener_estudiante_login.php";
    public static final String GET_PRO_LOGIN = "http://" + IP + PUERTO_HOST + "/WebServicePhp/obtener_profesor_login.php";
    public static final String GET_EJER = "http://" + IP + PUERTO_HOST + "/WebServicePhp/obtener_ejercicio.php";
    public static final String GET_EJER_ENV = "http://" + IP + PUERTO_HOST + "/WebServicePhp/obtener_imgEnvEst.php";
    public static final String GET_EJER_ID = "http://" + IP + PUERTO_HOST + "/WebServicePhp/obtener_ejercicio_id.php";
    public static final String GET_IMGENV_ID = "http://" + IP + PUERTO_HOST + "/WebServicePhp/obtener_imgEnv_id.php";

    public static final String UPDATE_CLAVE_EST = "http://" + IP + PUERTO_HOST + "/WebServicePhp/actualizar_clave_estudiante.php";
    public static final String UPDATE_CLAVE_PRO = "http://" + IP + PUERTO_HOST + "/WebServicePhp/actualizar_clave_profesor.php";

    public static final String INSERT_EJER = "http://" + IP + PUERTO_HOST + "/WebServicePhp/insertar_nuevo_ejercicio.php";
    public static final String INSERT_EJER_RES = "http://" + IP + PUERTO_HOST + "/WebServicePhp/insertar_respuesta_ejercicio.php";

    public static final String EXTRA_ID = "IDEXTRA";

}
