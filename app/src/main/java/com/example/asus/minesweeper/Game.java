package com.example.asus.minesweeper;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.Random;

import static com.example.asus.minesweeper.MainActivity.*;

/**
 * Se encarga de distintas operaciones del juego
 *
 * @author Bryan Jesús Romero Santos
 */
public class Game {
    // Declaración de variables.
    private int casillas;
    private int[][] matriz;
    private boolean[][] pulsadas;
    public static TextView tiempo;
    public static CountDownTimer cT;
    private int totalEnemigos;

    /**
     * Constructor.
     * @param dificultad Nivel de dificultad del juego para plantar el tablero y seleccionar enemigos para colocar
     */
    public Game(int dificultad) {
        switch (dificultad) {
            case 0:
                casillas = PRINCIPIANTE;
                totalEnemigos = ENEMIGOS_PRINCIPIANTES;
                break;
            case 1:
                casillas = AMATEUR;
                totalEnemigos = ENEMIGOS_AMATEUR;
                break;
            case 2:
                casillas = AVANZADO;
                totalEnemigos = ENEMIGOS_AVANZADO;
                break;
        }
        // Matriz adicional para controlar las celdas pulsadas.
        pulsadas = new boolean[casillas][casillas];
        for (int x = 0; x < casillas; x++) {
            for (int y = 0; y < casillas; y++) {
                pulsadas[x][y] = false;
            }
        }
    }

    /**
     * Llama a los metodos necesarios para poder jugar
     */
    public void jugar() {
        colocar();
        pistas();
    }

    /**
     * Distribuye los enemigos en el tablero de forma aleatoria.
     */
    private void colocar() {
        matriz = new int[casillas][casillas];
        int enemigos = 0;
        Random rnd = new Random();

        while (enemigos <= totalEnemigos) {
            int x = rnd.nextInt(casillas);
            int y = rnd.nextInt(casillas);
            if (matriz[x][y] != -1) {
                matriz[x][y] = -1;
                enemigos++;
            }
        }
    }

    /**
     * Rellena las celdas adyacentes a los enemigos con pistas de su ubicación. Es decir, muestra un número segun cuantos enemigos haya alrededor
     */
    private void pistas() {
        for (int x = 0; x < casillas; x++) {
            for (int y = 0; y < casillas; y++) {
                if (matriz[x][y] != -1) {
                    int contador = 0;
                    if ((x - 1 >= 0) && (y - 1 >= 0) && matriz[x - 1][y - 1] == -1) contador++;
                    if ((x - 1 >= 0) && matriz[x - 1][y] == -1) contador++;
                    if ((x - 1 >= 0) && (y + 1 < casillas) && matriz[x - 1][y + 1] == -1) contador++;
                    if ((y - 1 >= 0) && matriz[x][y - 1] == -1) contador++;
                    if ((y + 1 < casillas) && matriz[x][y + 1] == -1) contador++;
                    if ((x + 1 < casillas) && (y - 1 >= 0) && matriz[x + 1][y - 1] == -1) contador++;
                    if ((x + 1 < casillas) && matriz[x + 1][y] == -1) contador++;
                    if ((x + 1 < casillas) && (y + 1 < casillas) && matriz[x + 1][y + 1] == -1)
                        contador++;
                    matriz[x][y] = contador;
                }
            }
        }
    }

    /**
     * Comprueba si la celda existe y devuelve su valor.
     * @param x Fila.
     * @param y Columna.
     * @return El valor de la celda o -2 si no existe.
     */
    public int compruebaCelda(int x, int y) {
        if (x >= 0 && x < casillas && y >= 0 && y < casillas) {
            return matriz[x][y];
        } else {
            return -2; // Si no existen las coordenadas devolvemos un valor que lo indique.
        }
    }

    /**
     * Devuelve si la celda está pulsada.
     * @param x Fila.
     * @param y Columna.
     * @return Celda pulsada
     */
    public boolean getPulsadas(int x, int y) {
        return pulsadas[x][y];
    }

    /**
     * Marcar una celda como pulsada.
     * @param x Fila.
     * @param y Columna.
     */
    public void setPulsadas(int x, int y) {
        pulsadas[x][y] = true;
    }

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
