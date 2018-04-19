package com.example.jgchan.ej07listviewbd;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by jgchan on 16/04/18.
 */

public class MiAdaptador extends CursorAdapter {
    private LayoutInflater inflater;
    private TextView tvNombre, tvMAtricula,tvSemestre;

    public MiAdaptador(Context context, Cursor c) {
        super(context, c,false);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View vista=null;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            vista = inflater.inflate(R.layout.renglon,null);

        return vista;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //ACCEDER A LOS CAMPOS
        tvMAtricula = (TextView)view.findViewById(R.id.tvMatricula);
        tvNombre = (TextView)view.findViewById(R.id.tvNombre);
        tvSemestre = (TextView)view.findViewById(R.id.tvSemestre);

        tvMAtricula.setText(cursor.getString(cursor.getColumnIndex("matricula")));
        tvNombre.setText(cursor.getString(cursor.getColumnIndex("nombre")));
        tvSemestre.setText(cursor.getString(cursor.getColumnIndex("semestre")));

    }


}
