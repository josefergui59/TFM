package com.dalujos.matematicaapp.utils;

/**
 * Reflejo de la tabla 'meta' en la base de datos
 */
public class Profesor {

    private static final String TAG = Profesor.class.getSimpleName();
    /*
        Atributos
         */
    private String PROCODIGO;
    private String PROEJERCICIORES;
    private String PROFECHACREACION;
    private String PROLOGIN;
    private String PROCLAVE;
    private String PRONOMBRE;
    private String PROFECHANAC;
    private String PROGENERO;
    private String PROIDENTIFICA;
    private String PROCORREO;

    public Profesor(String PROCODIGO,
                    String PROEJERCICIORES,
                    String PROFECHACREACION,
                    String PROLOGIN,
                    String PROCLAVE,
                    String PRONOMBRE,
                    String PROFECHANAC,
                    String PROGENERO,
                    String PROIDENTIFICA,
                    String PROCORREO) {
        this.PROCODIGO = PROCODIGO;
        this.PROEJERCICIORES = PROEJERCICIORES          ;
        this.PROFECHACREACION = PROFECHACREACION;
        this.PROLOGIN = PROLOGIN;
        this.PROCLAVE = PROCLAVE;
        this.PRONOMBRE = PRONOMBRE;
        this.PROFECHANAC = PROFECHANAC;
        this.PROGENERO = PROGENERO;
        this.PROIDENTIFICA = PROIDENTIFICA;
        this.PROCORREO = PROCORREO;
    }

    public String getPROCODIGO() {
        return PROCODIGO;
    }
    public String getPROIDENTIFICA() {
        return PROIDENTIFICA;
    }
    public String getPROCORREO() {
        return PROCORREO;
    }

    public String getPROEJERCICIORES() {
        return PROEJERCICIORES;
    }

    public String getPROFECHACREACION() {
        return PROFECHACREACION;
    }

    public String getPROLOGIN() {
        return PROLOGIN;
    }

    public String getPROCLAVE() {
        return PROCLAVE;
    }

    public String getPRONOMBRE() {
        return PRONOMBRE;
    }

    public String getPROFECHANAC() {
        return PROFECHANAC;
    }

    public String getPROGENERO() {
        return PROGENERO;
    }
}
