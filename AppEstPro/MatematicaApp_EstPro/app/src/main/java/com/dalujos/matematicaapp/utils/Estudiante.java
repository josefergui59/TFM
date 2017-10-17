package com.dalujos.matematicaapp.utils;

/**
 * Reflejo de la tabla 'meta' en la base de datos
 */
public class Estudiante {

    private static final String TAG = Estudiante.class.getSimpleName();
    /*
        Atributos
         */
    private String ESTCODIGO;
    private String ESTMONEDERO;
    private String ESTFECHACREACION;
    private String ESTLOGIN;
    private String ESTCLAVE;
    private String ESTNOMBRE;
    private String ESTFECHANAC;
    private String ESTGENERO;
    private String ESTIDENTIFICA;
    private String ESTCORREO;

    public Estudiante(String ESTCODIGO,
                      String ESTMONEDERO          ,
                      String ESTFECHACREACION,
                      String ESTLOGIN,
                      String ESTCLAVE,
                      String ESTNOMBRE,
                      String ESTFECHANAC,
                      String ESTGENERO,
                      String ESTIDENTIFICA,
                      String ESTCORREO) {
        this.ESTCODIGO = ESTCODIGO;
        this.ESTMONEDERO = ESTMONEDERO          ;
        this.ESTFECHACREACION = ESTFECHACREACION;
        this.ESTLOGIN = ESTLOGIN;
        this.ESTCLAVE = ESTCLAVE;
        this.ESTNOMBRE = ESTNOMBRE;
        this.ESTFECHANAC = ESTFECHANAC;
        this.ESTGENERO = ESTGENERO;
        this.ESTIDENTIFICA = ESTIDENTIFICA;
        this.ESTCORREO = ESTCORREO;

    }

    public String getESTCODIGO() {
        return ESTCODIGO;
    }

    public String getESTMONEDERO() {
        return ESTMONEDERO;
    }

    public String getESTFECHACREACION() {
        return ESTFECHACREACION;
    }

    public String getESTLOGIN() {
        return ESTLOGIN;
    }

    public String getESTCLAVE() {
        return ESTCLAVE;
    }

    public String getESTNOMBRE() {
        return ESTNOMBRE;
    }

    public String getESTFECHANAC() {
        return ESTFECHANAC;
    }

    public String getESTGENERO() {
        return ESTGENERO;
    }

    public String getESTIDENTIFICA() {
        return ESTIDENTIFICA;
    }

    public String getESTCORREO() {
        return ESTCORREO;
    }
}
