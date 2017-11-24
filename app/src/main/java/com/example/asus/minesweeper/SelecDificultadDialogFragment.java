package com.example.asus.minesweeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by antonio on 30/11/16.
 */

public class SelecDificultadDialogFragment extends DialogFragment {
    // Declaración de variables
    String[] dificultad = {"Nivel Principiante", "Nivel Amateur", "Nivel Avanzado"};
    RespuestaDificultad respuestaDificultad;

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
