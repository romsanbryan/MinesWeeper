package com.example.asus.minesweeper;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.DialogInterface;
import android.widget.Toast;
import android.widget.TextView;

/**
 * @author Bryan Jesús Romero Santos
 * @version 1.1
 * @since API 22
 */

public class MainActivity extends AppCompatActivity {
    private Toast toast1;
    AlertDialog.Builder builder;
    AlertDialog alert;
    static String dificultad = null;
    TextView cuentaAtras;
    int opcion = 0;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        cuentaAtras = (TextView) findViewById(R.id.textView2);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.nj ){
            if(opcion == 1) {
                tiempoRestante(600000);
            } else if(opcion == 2) {
                tiempoRestante(450000);
            } else if(opcion == 3) {
                tiempoRestante(300000);
            } else {
                toast1 = Toast.makeText(getApplicationContext(), "No has seleccinado dificultad", Toast.LENGTH_SHORT);
                toast1.show();
            }
            return true;
        }

        if (id == R.id.configuracion ){
            final CharSequence[] items = {"Nivel Principiante", "Nivel Amateur", "Nivel Avanzado"};
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Dificultad");
            builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    String nivel = (String) items[item];
                    MainActivity.dificultad = nivel;
                    switch(nivel){
                        case "Nivel Principiante":
                            opcion = 1;
                            break;

                        case "Nivel Amateur":
                            opcion = 2;
                            break;

                        case "Nivel Avanzado":
                            opcion = 3;
                            break;
                    }
                    dialog.cancel();
                }
            });
            alert = builder.create();
            alert.show();
            return true;
        }

        if (id == R.id.personaje ){
            toast1 = Toast.makeText(getApplicationContext(), "Selecciona personaje", Toast.LENGTH_SHORT);
            toast1.show();
            return true;
        }

       if (id == R.id.instrucciones) {
           builder = new AlertDialog.Builder(this);
           builder.setMessage("El juego es tipo buscaminas: \n" +
                   "Cuando pulsas en una casilla, sale \n" +
                   "un numero que identifica cuántas \n" +
                   "hipotenochas hay alrededor: Ten \n" +
                   "cuidado porque si pulsas en una \n" +
                   "casilla que tenga una hipotenocha\n" +
                   "escondida, perderás. Si crees o \n" +
                   "tienes la certeza de que hay una hipotenocha, haz un click largo \n" +
                   "sobre la casilla para señalarla. No \n" +
                   "hagas un click largo en una casilla \n" +
                   "donde no hay una hipotecnocha \n" +
                   "porque perderás. Ganas una \n" +
                   "vez hayas  encontrado todas las \n" +
                   "hipotenochas")
                   .setTitle("Instrucciones").setNeutralButton("Ok",
                   new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           dialog.cancel();
                       }
                   });
           alert = builder.create();
           alert.show();
       }
        return super.onOptionsItemSelected(item);
    }

    public void tiempoRestante(int time){
        CountDownTimer cT = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                String v = String.format("%02d",millisUntilFinished/60000);
                int va = (int)((millisUntilFinished%60000)/1000);
                cuentaAtras.setText("Tiempo: " +v+":"+String.format("%02d",va));
            }
            public void onFinish() {
                cuentaAtras.setText("Se acabó el tiempo");
            }
        };
        cT.start();
    }

}
