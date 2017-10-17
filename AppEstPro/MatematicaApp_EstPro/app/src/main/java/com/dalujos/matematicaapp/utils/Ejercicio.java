package com.dalujos.matematicaapp.utils;

/**
 * Reflejo de la tabla 'meta' en la base de datos
 */
public class Ejercicio {

    private static final String TAG = Ejercicio.class.getSimpleName();
    /*
        Atributosejercicio
   */
    private String EJECODIGO;
    private String ESTCODIGO;
    private String PROCODIGO;
    private String EJENOMBRE;
    private String EJEDESCRIPCION;
    private String EJEIMAGEN;
    private String EJETEXTO;
    private String EJEIMAGENRES;
    private String EJETEXTORES;
    private String EJEFECHACREACION;
    private String EJEFECHARESUELTO;

    public Ejercicio(String EJECODIGO,
                     String ESTCODIGO,
                     String PROCODIGO,
                     String EJENOMBRE,
                     String EJEDESCRIPCION,
                     String EJEIMAGEN,
                     String EJETEXTO,
                     String EJEIMAGENRES,
                     String EJETEXTORES,
                     String EJEFECHACREACION,
                     String EJEFECHARESUELTO) {
        this.EJECODIGO = EJECODIGO;
        this.ESTCODIGO = ESTCODIGO;
        this.PROCODIGO = PROCODIGO;
        this.EJENOMBRE = EJENOMBRE;
        this.EJEDESCRIPCION = EJEDESCRIPCION;
        this.EJEIMAGEN = EJEIMAGEN;
        this.EJETEXTO = EJETEXTO;
        this.EJEIMAGENRES = EJEIMAGENRES;
        this.EJETEXTORES = EJETEXTORES;
        this.EJEFECHACREACION = EJEFECHACREACION;
        this.EJEFECHARESUELTO = EJEFECHARESUELTO;

    }

    public static String getTAG() {
        return TAG;
    }

    public String getEJECODIGO() {
        return EJECODIGO;
    }

    public String getESTCODIGO() {
        return ESTCODIGO;
    }

    public String getPROCODIGO() {
        return PROCODIGO;
    }

    public String getEJENOMBRE() {
        return EJENOMBRE;
    }

    public String getEJEDESCRIPCION() {
        return EJEDESCRIPCION;
    }

    public String getEJEIMAGEN() {
        return EJEIMAGEN;
    }

    public String getEJETEXTO() {
        return EJETEXTO;
    }

    public String getEJEIMAGENRES() {
        return EJEIMAGENRES;
    }

    public String getEJETEXTORES() {
        return EJETEXTORES;
    }

    public String getEJEFECHACREACION() {
        return EJEFECHACREACION;
    }

    public String getEJEFECHARESUELTO() {
        return EJEFECHARESUELTO;
    }
}
