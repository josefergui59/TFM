package com.dalujos.matematicaapp.CapaVista;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dalujos.matematicaapp.Adapter.Estilos;
import com.dalujos.matematicaapp.CapaDatos.Encriptacion;
import com.dalujos.matematicaapp.Modelo.EstudianteModelo;
import com.dalujos.matematicaapp.Modelo.ProfesorModelo;
import com.dalujos.matematicaapp.R;
import com.dalujos.matematicaapp.utils.Profesor;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IngresoProfesorVista extends AppCompatActivity  {

    private ProfesorModelo profesorModelo;
    private Estilos estilos;

    Button  btnCrearEstudiante;
    TextView txtFecha, txtTitulo;
    EditText  txtNombre, txtLogIn, txtClave, txtIdentifica, txtCorreo;;
    RadioButton rbMasculino, rbFemenino;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante_vista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inicializar();
        cargarEstilos();
        cargarFecha();
        cargarBotonGuardar();
        cargarLogIn();
    }

    private void inicializar(){
        profesorModelo = new ProfesorModelo(this);
        estilos = new Estilos(getAssets());
        //EditText
        txtTitulo = (TextView)findViewById(R.id.lblTitulo);
        txtTitulo.setText("Nombre Profesor");
        txtNombre =(EditText)findViewById(R.id.txtNombreEstudiante);
        txtFecha = (TextView)findViewById(R.id.txtFechaNacimiento);
        txtLogIn = (EditText)findViewById(R.id.txtLogIn);
        txtClave = (EditText)findViewById(R.id.txtClave);
        txtIdentifica = (EditText)findViewById(R.id.txtID);
        txtCorreo = (EditText)findViewById(R.id.txtMail);
        //RadioButton
        rbMasculino = (RadioButton)findViewById(R.id.rbMas);
        rbFemenino = (RadioButton)findViewById(R.id.rbFem);
        rbMasculino.setChecked(true);
        //Button

        btnCrearEstudiante = (Button) findViewById(R.id.btnCrearEstudiante);
        btnCrearEstudiante.setText("Crear Profesor");
        //Configuraciones Iniciales
        //btnCrearEstudiante.setEnabled(false);
    }

    public void cargarLogIn(){
        profesorModelo.cargarProfesores();
    }

    public void cargarEstilos(){
        estilos.estiloBotones(btnCrearEstudiante);
    }
    public void cargarFecha(){

        txtFecha.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mostrarFecha();

                    }
                }
        );
    }
    public void cargarBotonGuardar(){
        Button btnNuevoProfesor = (Button) findViewById(R.id.btnCrearEstudiante);
        btnNuevoProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd");//dd/MM/yyyy
                Date now = new Date();
                String PROEJERCICIORES = "0";
                String PROFECHACREACION = sdfDate.format(now);
                String PROLOGIN = txtLogIn.getText().toString();
                String PROCLAVE = txtClave.getText().toString();
                String PRONOMBRE = txtNombre.getText().toString();
                String PROFECHANAC = txtFecha.getText().toString();
                String PROIDENTIFICA = txtIdentifica.getText().toString();
                String PROCORREO = txtCorreo.getText().toString();
                String PROGENERO;
                if (rbMasculino.isChecked())
                    PROGENERO = rbMasculino.getText().toString();
                else
                    PROGENERO = rbFemenino.getText().toString();

                try {
                    PROCLAVE = Encriptacion.convertirSHA256(txtClave.getText().toString());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if(profesorModelo.probarConexion()) {
                    int intValidacion = profesorModelo.comprobarDatos(PROEJERCICIORES, PROFECHACREACION, PROLOGIN, PROCLAVE,
                            PRONOMBRE, PROFECHANAC, PROGENERO, PROIDENTIFICA, PROCORREO);
                    if (intValidacion == 1) // Estado 1, los datos son válidos
                    {
                        if (profesorModelo.guardarProfesor(PROEJERCICIORES, PROFECHACREACION, PROLOGIN, PROCLAVE,
                                PRONOMBRE, PROFECHANAC, PROGENERO, PROIDENTIFICA, PROCORREO)) {
                            mensajeCerrar("Éxito", "Profesor Creado");
                        }
                    } else {
                        if (intValidacion == 0) // Estado 0 no todos los campos estan llenos
                        {
                            mensaje("Advertencia", "LLene todos los campos");
                        }
                        if (intValidacion == 2) // logIn en uso
                        {
                            mensaje("Cambiar LogIn", "LogIn ya se encuentra en uso, intente con uno diferente");
                        }
                    }
                }
                else{
                    mensaje("Advertencia", "No se encuentra conectado, verifique la conexión a internet!");
                }
            }
        });

        estilos.estiloBotones(btnNuevoProfesor);
    }

    public void mensajeCerrar(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(titulo);
        builder.setMessage(mensaje);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(final DialogInterface arg0) {
                finish();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void mensaje(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(titulo);
        builder.setMessage(mensaje);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(final DialogInterface arg0) {

            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
    public void mostrarFecha() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        txtFecha.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


}
