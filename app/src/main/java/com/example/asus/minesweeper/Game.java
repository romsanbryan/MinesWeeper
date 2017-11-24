package com.example.asus.minesweeper;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Clase encargada de realizar el juego y distintas opciones de el mismo, como tiempo
 *
 */
public class Game {
    public static TextView tiempo;
    public static CountDownTimer cT;


    /**
     * Realizar y muestra (en un textView) un cronometro hacia atras
     *
     * @param time Tiempo correspondiente según la dificultad de cada modo, en milisegundos
     */
    public void contador(int time){
        cT = new CountDownTimer(time, 1000) {
            /**
             * Realizar la operacion para calcular el tiempo real
             *
             * @param millisUntilFinished Tiempo en milisengundos para operar
             */
            public void onTick(long millisUntilFinished) {

                String v = String.format("%02d",millisUntilFinished/60000);
                int va = (int)((millisUntilFinished%60000)/1000);
                tiempo.setText("Tiempo: " +v+":"+String.format("%02d",va));

               if  (va < 1 ) tiempo.setTextColor(Color.rgb(255,0,0)); // Si llega a 1 minuto, se pone rojo

            }

            /**
             * Cuando el tiempo llega a 0, muestra un mensaje
             */
            public void onFinish() {
                tiempo.setText(R.string.tiempoAcabado);
                MainActivity.deshabilitaTablero(MainActivity.tableLayout);
                MainActivity.relojActivado = false;
                MainActivity ma = new MainActivity();
            }
        };
        cT.start();// Inicia el cronometro
        tiempo.setTextColor(Color.rgb(0,0,0));
        MainActivity.relojActivado = true;
    }
}
