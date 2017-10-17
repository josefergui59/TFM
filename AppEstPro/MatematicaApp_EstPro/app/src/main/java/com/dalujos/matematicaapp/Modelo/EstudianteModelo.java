package com.dalujos.matematicaapp.Modelo;

import android.content.Context;
import com.dalujos.matematicaapp.CapaDatos.EstudianteControlador;
import com.dalujos.matematicaapp.utils.Estudiante;
import java.util.List;

/**
 * Created by JoseFernando on 11/06/2017.
 */

public class EstudianteModelo   {

    private Context contexto;

    private static EstudianteControlador estudianteControlador;

    public EstudianteModelo(Context contexto){
        this.contexto = contexto;
        estudianteControlador = new EstudianteControlador(contexto);
    }

    public boolean guardarEstudiante(
            String ESTMONEDERO,
            String ESTFECHACREACION,
            String ESTLOGIN,
            String ESTCLAVE,
            String ESTNOMBRE,
            String ESTFECHANAC,
            String ESTGENERO,
            String ESTIDENTIFICA,
            String ESTCORREO) {

        return estudianteControlador.guardarEstudiante(ESTMONEDERO, ESTFECHACREACION, ESTLOGIN, ESTCLAVE,
                ESTNOMBRE, ESTFECHANAC, ESTGENERO, ESTIDENTIFICA,ESTCORREO);
    }

    public void actualizarDatos(String ESTNOMBRE ,
                                 String ESTCLAVE ,
                                 String ESTLOGIN ,
                                 String ESTFECHANAC ,
                                 String ESTFECHACREACION  ,
                                 String ESTGENERO,
                                 String idRegistro,
                                 String ESTIDENTIFICA,
                                 String ESTCORREO)
    {
        estudianteControlador.actualizarDatos(ESTNOMBRE ,
            ESTCLAVE ,
            ESTLOGIN ,
            ESTFECHANAC ,
            ESTFECHACREACION  ,
            ESTGENERO,
            idRegistro,
            ESTIDENTIFICA,
            ESTCORREO);

    }
    public void guardarImagen(
            String ESTCODIGO,
            String EJEIMAGEN,
            String EJENOMBRE,
            String EJEDESCRIPCION) {
        estudianteControlador.guardarImagen(
                 ESTCODIGO,
                 EJEIMAGEN,
                 EJENOMBRE,
                 EJEDESCRIPCION);
    }

    public void eliminarEstudiantes(String id){
        estudianteControlador.eliminarRegistro(id);
    }

    public void cargarEstudiantes( ){
        estudianteControlador.cargarEstudiantes();
    }

    public void actualizarCreditos(String ESTMONEDERO,
                                  String idRegistro){
        estudianteControlador.actualizarCreditos(ESTMONEDERO, idRegistro);
    }
    public void actualizarCreditosEjer(String ESTMONEDERO,
                                   String idRegistro){
        estudianteControlador.actualizarCreditosEjer(ESTMONEDERO, idRegistro);
    }
    public void actualizarPago(String ESTCODIGO,String PAGTIPOPAGO,
                               String PAGMONTO, String PAGUSUARIO, String PAGTIPOUSUARIO){
        estudianteControlador.actualizarPago(ESTCODIGO, PAGTIPOPAGO,
                PAGMONTO, PAGUSUARIO, PAGTIPOUSUARIO);
    }
    public boolean probarConexion(){
        return  estudianteControlador.probarConexion();
    }

    public int comprobarDatos(String ESTMONEDERO          ,
                               String ESTFECHACREACION,
                               String ESTLOGIN,
                               String ESTCLAVE,
                               String ESTNOMBRE,
                               String ESTFECHANAC,
                               String ESTGENERO,
                               String ESTIDENTIFICA,
                               String ESTCORREO){
        int retorno = 0; // 0 Error 1 Bien 2 LogIn duplicado

         if (ESTMONEDERO.length()>0 &&
                 ESTFECHACREACION.length()>0 &&
                 ESTCLAVE.length()>0 &&
                 ESTLOGIN.length()>0 &&
                 ESTNOMBRE.length()>0 &&
                 ESTFECHANAC.length()>0 &&
                 ESTGENERO.length()>0 &&
                 ESTIDENTIFICA.length()>0 &&
                 ESTCORREO.length()>0 )
         {
             retorno = 1;
         }
        List<Estudiante> estudiante = estudianteControlador.getEstudiante();

        if(estudiante != null){
            for(int i = 0; i< estudiante.size(); i++){
                String logIn = estudiante.get(i).getESTLOGIN();
                if( logIn.equals(ESTLOGIN))
                {
                    retorno = 2;
                    break;
                }
            }
        }
        return  retorno;
    }

    public String getID(String logInIn){
        String retorno = "";
        List<Estudiante> estudiante = estudianteControlador.getEstudiante();

        if(estudiante != null){
            for(int i = 0; i< estudiante.size(); i++){
                String logIn = estudiante.get(i).getESTLOGIN();
                if( logIn.equals(logInIn))
                {
                    retorno = estudiante.get(i).getESTCODIGO();
                    break;
                }
            }
        }
        return  retorno;
    }

    public String getCredito(String logInIn){
        String retorno = "-1";
        List<Estudiante> estudiante = estudianteControlador.getEstudiante();

        if(estudiante != null){
            for(int i = 0; i< estudiante.size(); i++){
                String logIn = estudiante.get(i).getESTLOGIN();
                if( logIn.equals(logInIn))
                {
                    retorno = estudiante.get(i).getESTMONEDERO();
                    break;
                }
            }
        }
        return  retorno;
    }
}
