package com.example.asus.minesweeper;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Permite seleccionar el personaje y enemigo del juego
 * @author Bryan Jesús Romero Santos
 */

public class Personajes extends ListActivity {

    private String[] nombrePersonajes;
    private TypedArray images;
    private List<Informacion> informacionList;
    private Menu menu;
    public static ImageView personaje;
    public static ImageView enemigo;

    /**
     * Listado de personajes con los cuales se jugara
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateCountryList();
        ArrayAdapter<Informacion> adapter = new PersonajesAdapter(this, informacionList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Informacion c = informacionList.get(position);
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);

                images.recycle();
                finish();
                String persona = c.getName();
                switch(persona){
                    case "Goku":
                        Toast.makeText(getApplicationContext(), "Has elegido a Goku", Toast.LENGTH_SHORT).show();
                        MainActivity.personaje = 0;
                        MainActivity.enemigo = 0;
                        personaje.setImageResource(R.drawable.goku); // pinta al personaje
                        enemigo.setImageResource(R.drawable.saibaman); // pinta al enemigo
                        break;

                    case "Vegeta":
                        Toast.makeText(getApplicationContext(), "Has elegido a Vegeta", Toast.LENGTH_SHORT).show();
                        MainActivity.personaje = 1;
                        MainActivity.enemigo = 1;
                        personaje.setImageResource(R.drawable.vegeta); // pinta al personaje
                        enemigo.setImageResource(R.drawable.broly); // pinta al enemigo
                        break;

                    case "Gohan":
                        Toast.makeText(getApplicationContext(), "Has elegido a Gohan", Toast.LENGTH_SHORT).show();
                        MainActivity.personaje = 2;
                        MainActivity.enemigo = 2;
                        personaje.setImageResource(R.drawable.gohan); // pinta al personaje
                        enemigo.setImageResource(R.drawable.jr); // pinta al enemigo
                        break;
                }
            }
        });
    }

    /**
     * Lista con el nombre y dibujo de cada personaje
     */
    private void populateCountryList() {
        informacionList = new ArrayList<Informacion>();
        nombrePersonajes = getResources().getStringArray(R.array.nombresPersonajes);
        images = getResources().obtainTypedArray(R.array.images_personajes);
        for(int i = 0; i < nombrePersonajes.length; i++){
            informacionList.add(new Informacion(nombrePersonajes[i], images.getDrawable(i)));
        }
    }

    /**
     * Clase para optener el nombre y dibujo del enemigo seleccionado
     */
    public class Informacion {
        private String name;
        private Drawable flag;

        public Informacion(String name, Drawable flag){
            this.name = name;
            this.flag = flag;
        }
        public String getName() {
            return name;
        }

        public Drawable getFlag() {
            return flag;
        }
    }
}
