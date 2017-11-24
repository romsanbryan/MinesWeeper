package com.example.asus.minesweeper;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * @author Bryan Jesús Romero Santos
 * @version 1.7
 * @since API 21
 */

public class MainActivity extends AppCompatActivity implements DificultadFragment.RespuestaDificultad, View.OnClickListener, View.OnLongClickListener {
   // Atributos privados
    private int dificultad = 0;  // posicion de la dificultad
    private Button tiledBoton;
    private LinearLayout tablero; // tablero del juego (contect_main)
    private Game game; // objeto de la clase game
    private boolean jugando = false; // controla si hay un juego activo para activar/desactivar el tablero
    private TypedArray arrayImagenes, arrayImagenesCompleto;
    private int encontradas = 0; // controla cuantos enemigos hemos descubierto
    private AlertDialog.Builder builder;// necesario para hacer alertas con mensajes
    private AlertDialog alert; // necesario para hacer alertas con mensajes

    // Atributos Staticos
    public static int personaje = 0; // posicion del personaje en el array
    public static TableLayout tableLayout;
    public static boolean relojActivado = false; // para controlar que el reloj se activa o desactiva
    public static int enemigo = 0; // posicion del enemigo en el array

    // Atributos finales
    public static final int PRINCIPIANTE = 8; // tablero 8x8
    public static final int AMATEUR = 10; // tablero 10x10
    public static final int AVANZADO = 12; // tablero 12x12
    public static final int ENEMIGOS_PRINCIPIANTES = 10; // enemigos para la clase principiante
    public static final int ENEMIGOS_AMATEUR = 30; // enemigos para la clase amateurs
    public static final int ENEMIGOS_AVANZADO = 60; // enemigos para la clase avanzado
    public static final int TIEMPO_PRINCIPIANTE = 300000; // tiempo 5 minutos
    public static final int TIEMPO_AMATEUR= 450000; // tiempo 7.5 minutos
    public static final int TIEMPO_AVANZADO = 600000; // tiempo 10 minutos


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // Permite añadir el toolbar
        setSupportActionBar(toolbar);

        arrayImagenesCompleto = getResources().obtainTypedArray(R.array.images_personajes_completos); // asocia un atributo a los personajes para elegirlos
        arrayImagenes = getResources().obtainTypedArray(R.array.images); // asocia un atributo a los personajes del juegp
        tablero = (LinearLayout) findViewById(R.id.content_main); // layout del juego
        rellenaBotones(PRINCIPIANTE); // nivel de dificultad por defecto
    }

    /**
     * Genera la matriz de botones inicial.
     *
     * @param botones Número de botones de acuerdo al nivel de dificultad.
     */
    private void rellenaBotones(int botones) {
        tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(true);
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        tableLayout.setWeightSum(botones);

        for (int i = 0; i < botones; i++) {
            TableRow tr = new TableRow(this);
            tr.setGravity(Gravity.CENTER);
            tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, 1.0f));

            for (int j = 0; j < botones; j++) {
                tiledBoton = new Button(this);
                tiledBoton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                tiledBoton.setId(View.generateViewId());
                tiledBoton.setText(i + "," + j);
                tiledBoton.setTextSize(0);
                tiledBoton.setOnClickListener(this);
                tiledBoton.setOnLongClickListener(this);
                tr.addView(tiledBoton);
            }
            tableLayout.addView(tr);
        }
        tablero.removeAllViews();
        tablero.addView(tableLayout);

        if (!jugando) deshabilitaTablero(tableLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        game.tiempo = (TextView) findViewById(R.id.textView2);
        Personajes.personaje = (ImageView) findViewById(R.id.imagen1);
        Personajes.enemigo = (ImageView) findViewById(R.id.imagen2);
        return true;
    }

    /**
     * Método que implementa la interfaz para el diálogo de elección de la dificultad.
     *
     * @param i Entero asociado al nivel de dificultad.
     */
    @Override
    public void onRespuestaDificultad(int i) {
        dificultad = i;
        switch (dificultad) {
            case 0:
                rellenaBotones(PRINCIPIANTE);
                jugando = false;
                break;
            case 1:
                rellenaBotones(AMATEUR);
                jugando = false;
                break;
            case 2:
                rellenaBotones(AVANZADO);
                jugando = false;
                break;
        }
    }

    @Override
    public void onClick(View view) {
        // Obtenemos las coordenadas de la celda del texto del botón
        int x = Integer.parseInt(((Button) view).getText().toString().split(",")[0]);
        int y = Integer.parseInt(((Button) view).getText().toString().split(",")[1]);

        int resultado = game.compruebaCelda(x, y);

        if (resultado == -1) { // Hay enemigo
            // Mostrar enemigos
            Button b = (Button) view;
            b.setPadding(0, 0, 0, 0);
            b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
            b.setBackground(arrayImagenesCompleto.getDrawable(enemigo));
            TableLayout tl = (TableLayout) view.getParent().getParent();
            jugando = false;
            encontradas = 0;
            deshabilitaTablero(tl);
            game.cT.cancel();
            mostrarAlerta(R.string.perdedor);

        }
        if (resultado == 0) { // No hay enemigos adyacentes
            Button b = (Button) view;
            b.setPadding(0, 0, 0, 0);
            b.setBackgroundColor(Color.GRAY);
            // Despejar adyacentes con 0
            despejaAdyacentes(view, x, y);
        }
        if (resultado > 0) { // Hay enemigos adyacentes
            Button b = (Button) view;
            b.setPadding(0, 0, 0, 0);
            b.setText(String.valueOf(resultado));
            b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            b.setTextColor(Color.WHITE);
            b.setBackgroundColor(Color.GRAY);
            view.setEnabled(false);
        }
    }

    /**
     * Método que descubre automáticamente todas las casillas adyacentes que no tienen enemigos alrededor.
     *
     * @param view El botón a descubrir.
     * @param x    Fila.
     * @param y    Columna.
     */
    private void despejaAdyacentes(View view, int x, int y) {
        // Recorremos los botones adyacentes y si también están a cero los despejamos
        for (int xt = -1; xt <= 1; xt++) {
            for (int yt = -1; yt <= 1; yt++) {
                if (xt != yt) {
                    if (game.compruebaCelda(x + xt, y + yt) == 0 && !game.getPulsadas(x + xt, y + yt)) {
                        Button b = (Button) traerBoton(x + xt, y + yt);
                        b.setBackgroundColor(Color.GRAY);
                        b.setClickable(false);
                        game.setPulsadas(x + xt, y + yt);
                        String[] coordenadas = b.getText().toString().split(",");
                        despejaAdyacentes(b, Integer.parseInt(coordenadas[0]), Integer.parseInt(coordenadas[1]));
                    }
                }
            }
        }
    }

    /**
     * Recupera una vista de botón desde las coordenadas indicadas.
     *
     * @param x Fila.
     * @param y Columna.
     * @return Devuelve el botón buscado o null si no lo encuentra.
     */
    private View traerBoton(int x, int y) {
        Button b = null;
        // Recorremos la matriz de botones hasta encontrar una coincidencia con las coordenadas
        // buscadas.
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            TableRow tr = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < tr.getChildCount(); j++) {
                b = (Button) tr.getChildAt(j);
                if (b.getText().toString().equals(x + "," + y)) {
                    return b;
                }
            }
        }
        return null;
    }

    /**
     * Deshabilita todos los botones de la matriz cuando no estamos jugando o el juego ha terminado.
     *
     * @param view El elemento a deshabilitar.
     */
    public static void deshabilitaTablero(View view) {
        TableLayout tl = (TableLayout) view;
        // Recorremos la matriz de botones deshabilitando todos.
        for (int i = 0; i < tl.getChildCount(); i++) {
            TableRow tr = (TableRow) tl.getChildAt(i);
            for (int j = 0; j < tr.getChildCount(); j++) {
                Button b = (Button) tr.getChildAt(j);
                b.setEnabled(false);
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        // Obtenemos las coordenadas de la celda del texto del botón
        int x = Integer.parseInt(((Button) view).getText().toString().split(",")[0]);
        int y = Integer.parseInt(((Button) view).getText().toString().split(",")[1]);
        int resultado = game.compruebaCelda(x, y);
        if (resultado == -1) { // Hay enemigo
            Button b = (Button) view;
            b.setPadding(0, 0, 0, 0);
            b.setBackground(arrayImagenes.getDrawable(personaje));
            encontradas++;
            if (encontradas == 10) {
                TableLayout tl = (TableLayout) view.getParent().getParent();
                jugando = false;
                encontradas = 0;
                mostrarAlerta(R.string.ganador);
                deshabilitaTablero(tl);
            }
        } else { // No hay enemigo
            Button b = (Button) view;
            b.setText(String.valueOf(resultado));
            b.setTextSize(20);
            b.setTextColor(Color.WHITE);
            b.setBackgroundColor(Color.GRAY);
            view.setEnabled(false);
            TableLayout tl = (TableLayout) view.getParent().getParent();
            jugando = false;
            encontradas = 0;
            mostrarAlerta(R.string.perdedor);
            deshabilitaTablero(tl);
            game.cT.cancel();
        }
        return true;
    }

    /**
     * Muestra un cuadro de diálogo de alerta con el texto indicado desde un recurso de texto.
     *
     * @param texto Entero indicado el id del recurso de texto asociado.
     */
    public void mostrarAlerta(int texto) {
        builder = new AlertDialog.Builder(this);
        builder.setMessage(texto)
                .setNeutralButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }

    /**
     * Opciones del menu
     * @param item Opcion del menu
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nj:
               empezarJuego();
                break;
            case R.id.configuracion:
                DificultadFragment selecDificultad = new DificultadFragment();
                selecDificultad.show(getFragmentManager(), null);
                break;
            case R.id.personaje:
                final Intent intent = new Intent(this, Personajes.class);
                startActivityForResult(intent, 1);
                break;

            case R.id.instrucciones:
                builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.instruccionesExplicadas)
                        .setTitle(R.string.instrucciones).setNeutralButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alert = builder.create();
                alert.show();
                break;
            }
            return super.onOptionsItemSelected(item);
        }

    /**
     * Inicia el juego desde el menú correspondiente. Tambien desactiva el reloj para poder volver a activarlo sin pisarse al elegir otra dificultad
     *
     */
    private void empezarJuego() {
        jugando = true;
        onRespuestaDificultad(dificultad);
        game = new Game(dificultad);

        if(relojActivado == true) {
            game.cT.cancel();
            relojActivado = false;
        }

        if(dificultad == 0) {
            game.contador(TIEMPO_PRINCIPIANTE);
        }
        if (dificultad==1) {
            game.contador(TIEMPO_AMATEUR);
        }
        if (dificultad==2){
            game.contador(TIEMPO_AVANZADO);
        }
        game.jugar();
    }
}
