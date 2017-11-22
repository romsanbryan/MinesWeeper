package com.example.asus.minesweeper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * @author Bryan Jesús Romero Santos
 * @version 1.1
 * @since API 22
 */

public class PersonajesListArrayAdapter extends ArrayAdapter<Personajes.Informacion> {

    protected final List<Personajes.Informacion> list;
    protected final Activity context;

    static class ViewHolder {
        public TextView name;
        public ImageView flag;
    }

    public PersonajesListArrayAdapter(Activity context, List<Personajes.Informacion> list) {
        super(context, R.layout.activity_personajes_row, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_personajes_row, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.flag = (ImageView) view.findViewById(R.id.flag);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(list.get(position).getName());
        holder.flag.setImageDrawable(list.get(position).getFlag());
        return view;
    }
}
