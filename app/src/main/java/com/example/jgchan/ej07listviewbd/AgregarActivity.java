package com.example.jgchan.ej07listviewbd;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AgregarActivity extends AppCompatActivity {

    String id="",nombre,semestre,matricula;
    EditText txtNombre,txtMatricula,txtSemestre;
    Button btnRegistrar, btnEliminar;
    boolean band=true;
    SQLiteDatabase db = null;
    private TextView tvLetrero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        txtMatricula = (EditText)findViewById(R.id.edtMat);
        txtSemestre = (EditText)findViewById(R.id.edtSem);
        txtNombre= (EditText)findViewById(R.id.etdNombre);

        tvLetrero = (TextView)findViewById(R.id.tvLetrero);

        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);
        btnEliminar = (Button)findViewById(R.id.btnEliminar);
        btnEliminar.setVisibility(View.INVISIBLE);
        btnEliminar.setClickable(false);


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leerDatos();
                eliminar();
            }
        });


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String id = ""+bundle.getLong("id");
            String nombre = bundle.getString("nombre");
            String semestre = bundle.getString("semestre");
            String matricula = bundle.getString("matricula");

            this.id=id;

            txtNombre.setText(nombre);
            txtMatricula.setText(matricula);
            txtSemestre.setText(semestre);
            tvLetrero.setText("Editar Contacto");

            btnRegistrar.setText("Actualizar");
            btnEliminar.setVisibility(View.VISIBLE);
            btnEliminar.setClickable(true);
            band=false;


            btnRegistrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    leerDatos();
                     editar();
                }
            });

        }else{
            btnRegistrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    leerDatos();
                    agregar();

                }
            });
        }

    }

    private void leerDatos() {

        matricula=txtMatricula.getText().toString();
        semestre=txtSemestre.getText().toString();
        nombre=txtNombre.getText().toString();

    }

    private void eliminar() {

        db = this.openOrCreateDatabase("escolar.db", MODE_PRIVATE, null);

        String eliminar="DELETE FROM estudiantes" +
                " WHERE _id="+id+";";

        db.execSQL(eliminar);
        db.close();

        mensaje(nombre+" se elimino con éxito ");

    }

    private void agregar() {


        db = this.openOrCreateDatabase("escolar.db", MODE_PRIVATE, null);

        String eliminar="INSERT INTO estudiantes (matricula,nombre,semestre)" +
                " VALUES ('"+matricula+"','"+nombre+"',"+semestre+");";

        db.execSQL(eliminar);
        db.close();


        mensaje(nombre+" se agrego con éxito ");

    }

    private void editar() {

        db = this.openOrCreateDatabase("escolar.db", MODE_PRIVATE, null);

        String update="UPDATE estudiantes SET " +
                "nombre = '"+nombre+"', " +
                "matricula = '"+matricula+"', " +
                "semestre = "+semestre+" " +
                "WHERE _id="+id+";";

        db.execSQL(update);
        db.close();


        mensaje(nombre+" se edito con éxito ");
    }

    public void mensaje(String respuesta){
        AlertDialog.Builder builder = new AlertDialog.Builder(AgregarActivity.this);
        builder.setTitle("¡Advertencia!")
                .setMessage(respuesta)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intentVacio = getIntent();
                        setResult(RESULT_OK,intentVacio);
                        finish();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
