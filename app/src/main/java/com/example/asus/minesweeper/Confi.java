package com.example.asus.minesweeper;

import java.util.Random;

import static com.example.asus.minesweeper.MainActivity.*;


/**
 * Created by ASUS on 23/11/2017.
 */

public class Confi {
    // Declaración de variables.
    int casillas;
    private int[][] matriz;
    private boolean[][] pulsadas;

    /**
     * Constructor.
     * @param dificultad Nivel de dificultad del juego.
     */
    public Confi(int dificultad) {
        switch (dificultad) {
            case 0:
                casillas = PRINCIPIANTE;
                break;
            case 1:
                casillas = AMATEUR;
                break;
            case 2:
                casillas = AVANZADO;
                break;
        }
        // Creamos una matriz adicional para llevar el control de las celdas pulsadas.
        pulsadas = new boolean[casillas][casillas];
        for (int x = 0; x < casillas; x++) {
            for (int y = 0; y < casillas; y++) {
                pulsadas[x][y] = false;
            }
        }
    }

    /**
     * Distribuye las hipotenochas y las pistas de su ubicación en el tablero.
     */
    public void jugar() {
        colocarPersonajesTablero();
        colorcarPistasAdyacentes();
    }

    /**
     * Distribuye las hipotenochas en el tablero de forma aleatoria.
     */
    public void colocarPersonajesTablero() {
        matriz = new int[casillas][casillas];
        int personajes = 0;
        Random rnd = new Random();

        while (personajes < 10) {
            int x = rnd.nextInt(casillas);
            int y = rnd.nextInt(casillas);
            if (matriz[x][y] != -1) {
                matriz[x][y] = -1;
                personajes++;
            }
        }
    }

    /**
     * Rellena las celdas adyacentes a las hipotenochas con pistas de su ubicación.
     */
    private void colorcarPistasAdyacentes() {
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
                // Quitar comentario para ver la matriz en consola (para debuguear).
                // System.out.print(matriz[x][y] == -1 ? "B" : matriz[x][y]);
            }
            // Quitar comentario para ver la matriz en consola (para debuguear).
            // System.out.println("\n");
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
     * Devuelve true si la celda está pulsada.
     * @param x Fila.
     * @param y Columna.
     * @return
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
}
