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
 * @author Bryan Jesús Romero Santos
 * @version 1.1
 * @since API 22
 */

public class Personajes extends ListActivity {

    public String[] nombrePersonajes;
    private TypedArray images;
    private List<Informacion> informacionList;
    private Menu menu;
    public static ImageView personaje;
    public static ImageView enemigo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateCountryList();
        ArrayAdapter<Informacion> adapter = new PersonajesListArrayAdapter(this, informacionList);
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
                        personaje.setImageResource(R.drawable.goku);
                        enemigo.setImageResource(R.drawable.saibaman);
                        break;

                    case "Vegeta":
                        Toast.makeText(getApplicationContext(), "Has elegido a Vegeta", Toast.LENGTH_SHORT).show();
                        MainActivity.personaje = 1;
                        MainActivity.enemigo = 1;
                        personaje.setImageResource(R.drawable.vegeta);
                        enemigo.setImageResource(R.drawable.broly);
                        break;

                    case "Gohan":
                        Toast.makeText(getApplicationContext(), "Has elegido a Gohan", Toast.LENGTH_SHORT).show();
                        MainActivity.personaje = 2;
                        MainActivity.enemigo = 2;
                        personaje.setImageResource(R.drawable.gohan);
                        enemigo.setImageResource(R.drawable.jr);
                        break;
                }
            }
        });
    }

    private void populateCountryList() {
        informacionList = new ArrayList<Informacion>();
        nombrePersonajes = getResources().getStringArray(R.array.nombresPersonajes);
        images = getResources().obtainTypedArray(R.array.images_personajes);
        for(int i = 0; i < nombrePersonajes.length; i++){
            informacionList.add(new Informacion(nombrePersonajes[i], images.getDrawable(i)));
        }
    }

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
