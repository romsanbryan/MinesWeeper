package com.example.asus.minesweeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Clase encargada de mostrar los diferentes niveles de dificultad
 * @author Bryan Jesús Romero Santos
 */

public class DificultadFragment extends DialogFragment {
    // Declaración de variables
    private String[] dificultad = {"Nivel Principiante", "Nivel Amateur", "Nivel Avanzado"}; // Array que contiene los niveles
    private RespuestaDificultad respuestaDificultad; // variable para guardar la dificultad seleccionada

    /**
     * Permite seleccionar la dificultad
     * @param savedInstanceState
     * @return Cuadro de seleccion de dificultad
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dificultad)
                .setSingleChoiceItems(dificultad, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        respuestaDificultad.onRespuestaDificultad(i);
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // El usuario pulsa Aceptar.
                    }
                });
        return builder.create();
    }

    /**
     * Interfaz que permite devolver la dificultad seleccionada.
     */
    public interface RespuestaDificultad {
        void onRespuestaDificultad(int i);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        respuestaDificultad = (RespuestaDificultad) activity;
    }
}
