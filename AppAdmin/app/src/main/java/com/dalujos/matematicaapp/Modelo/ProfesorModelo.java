package com.dalujos.matematicaapp.Modelo;

import android.content.Context;

import com.dalujos.matematicaapp.CapaDatos.EstudianteControlador;
import com.dalujos.matematicaapp.CapaDatos.ProfesorControlador;
import com.dalujos.matematicaapp.utils.Estudiante;
import com.dalujos.matematicaapp.utils.Profesor;

import java.util.List;

/**
 * Created by JoseFernando on 11/06/2017.
 */

public class ProfesorModelo {

    private Context contexto;

    private static ProfesorControlador profesorControlador;

    public ProfesorModelo(Context contexto){
        this.contexto = contexto;
        profesorControlador = new ProfesorControlador(contexto);
    }

    public boolean guardarProfesor(
            String PROEJERCICIORES,
            String PROFECHACREACION,
            String PROLOGIN,
            String PROCLAVE,
            String PRONOMBRE,
            String PROFECHANAC,
            String PROGENERO,
            String PROIDENTIFICA,
            String PROCORREO) {

        return profesorControlador.guardarProfesor(PROEJERCICIORES, PROFECHACREACION, PROLOGIN, PROCLAVE,
                PRONOMBRE, PROFECHANAC, PROGENERO, PROIDENTIFICA, PROCORREO );
    }

    public void actualizarDatos(String PRONOMBRE ,
                                String PROCLAVE,
                                String PROLOGIN,
                                String PROFECHANAC ,
                                String PROFECHACREACION  ,
                                String PROGENERO,
                                String idRegistro,
                                String PROIDENTIFICA,
                                String PROCORREO)
    {
        profesorControlador.actualizarDatos(PRONOMBRE ,
                PROCLAVE,
                PROLOGIN,
                PROFECHANAC ,
                PROFECHACREACION  ,
                PROGENERO,
                idRegistro,
                PROIDENTIFICA,
                PROCORREO);

    }

    public void eliminarProfesor(String id){
        profesorControlador.eliminarRegistro(id);
    }

    public void cargarProfesores( ){
        profesorControlador.cargarProfesor();
    }

    public boolean probarConexion(){
        return  profesorControlador.probarConexion();
    }

    public int comprobarDatos(String PROMONEDERO          ,
                              String PROFECHACREACION,
                              String PROLOGIN,
                              String PROCLAVE,
                              String PRONOMBRE,
                              String PROFECHANAC,
                              String PROGENERO,
                              String PROIDENTIFICA,
                              String PROCORREO){
        int retorno = 0; // 0 Error 1 Bien 2 LogIn duplicado

         if (PROMONEDERO.length()>0 &&
                 PROFECHACREACION.length()>0 &&
                 PROCLAVE.length()>0 &&
                 PROLOGIN.length()>0 &&
                 PRONOMBRE.length()>0 &&
                 PROFECHANAC.length()>0 &&
                 PROIDENTIFICA.length()>0 &&
                 PROCORREO.length()>0 &&
                 PROGENERO.length()>0 )
         {
             retorno = 1;
         }
        List<Profesor> profesor = profesorControlador.getProfesor();

        if(profesor != null){
            for(int i = 0; i< profesor.size(); i++){
                String logIn = profesor.get(i).getPROLOGIN();
                if( logIn.equals(PROLOGIN))
                {
                    retorno = 2;
                    break;
                }
            }
        }
        return  retorno;
    }


}
