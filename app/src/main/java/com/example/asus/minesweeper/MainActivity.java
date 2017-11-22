package com.example.asus.minesweeper;

import android.content.Intent;
import android.graphics.Color;
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
 * @version 1.2
 * @since API 22
 */

public class MainActivity extends AppCompatActivity {
    private Toast toast1;
    private AlertDialog.Builder builder;
    private AlertDialog alert;
    private static String dificultad = null;
    private TextView cuentaAtras;
    private int opcion = 0;
    private int PRINCIPIANTE = 8;
    private int AMATEUR = 12;
    private int AVANZADO = 16;
    private int TIEMPO_PRINCIPIANTE = 300000; // 5 minutos
    private int TIEMPO_AMATEUR= 450000; // 7.5 minutos
    private int TIEMPO_AVANZADO = 600000; // 10 minutos


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
        int id = item.getItemId();
        switch (id){
            case R.id.nj:
                if(opcion == PRINCIPIANTE) {
                    tiempoRestante(TIEMPO_PRINCIPIANTE);
                } else if(opcion == AMATEUR) {
                    tiempoRestante(TIEMPO_AMATEUR);
                } else if(opcion == AVANZADO) {
                    tiempoRestante(TIEMPO_AVANZADO);
                } else {
                    toast1 = Toast.makeText(getApplicationContext(), "No has seleccinado dificultad", Toast.LENGTH_SHORT);
                    toast1.show();
                }
                break;
            case R.id.configuracion:
                final CharSequence[] items = {"Nivel Principiante", "Nivel Amateur", "Nivel Avanzado"};
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Dificultad");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        String nivel = (String) items[item];
                        MainActivity.dificultad = nivel;
                        switch(nivel){
                            case "Nivel Principiante":
                                opcion = PRINCIPIANTE;
                                break;
                            case "Nivel Amateur":
                                opcion = AMATEUR;
                                break;
                            case "Nivel Avanzado":
                                opcion = AVANZADO;
                                break;
                        }
                        dialog.cancel();
                    }
                });
                alert = builder.create();
                alert.show();
                break;
            case R.id.personaje:
                final Intent intent = new Intent(this, Personajes.class);
                startActivityForResult(intent, 1);
                break;

            case R.id.instrucciones:
                builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.instruccionesExplicadas)
                        .setTitle(R.string.instrucciones).setNeutralButton("Ok",
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

    private void tiempoRestante(int time){
        CountDownTimer cT = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                String v = String.format("%02d",millisUntilFinished/60000);
                int va = (int)((millisUntilFinished%60000)/1000);
                cuentaAtras.setText("Tiempo: " +v+":"+String.format("%02d",va));

                if (va < 1) cuentaAtras.setTextColor(Color.rgb(255,0,0));

            }
            public void onFinish() {
                cuentaAtras.setText("Se acabó el tiempo");
            }
        };
        cT.start();
    }
}
