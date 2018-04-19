package com.example.jgchan.ej07listviewbd;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db = null;
    Cursor cursor = null;
    ListView lista;
    TextView letrero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        letrero = (TextView)findViewById(android.R.id.empty);

        db = this.openOrCreateDatabase("escolar.db", MODE_PRIVATE, null);
        //String crearTabla="CREATE TABLE IF NOT EXISTS estudiantes (matricula TEXT, nombre TEXT, semestre INTEGER);";

        String crearTabla="CREATE TABLE IF NOT EXISTS estudiantes (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," + //campo obligatorio
                " matricula TEXT," +
                " nombre TEXT," +
                " semestre INTEGER);";

        db.execSQL(crearTabla);

        //SOLO UNA VEZ para poner datos iniciales

        cursor = db.rawQuery("SELECT * FROM estudiantes",null);

        if(cursor.getCount()>0){
            letrero.setVisibility(View.INVISIBLE);
        }

        lista = (ListView)findViewById(android.R.id.list);


       lista.setAdapter(new MiAdaptador(this, cursor));


       lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String matricula="",nombre="",semestre="";
               db = MainActivity.this.openOrCreateDatabase("escolar.db", MODE_PRIVATE, null);
             Cursor cursor1= db.rawQuery("SELECT * FROM estudiantes WHERE _id="+id,null);

              if(cursor1.moveToNext()){
                  matricula= cursor1.getString(cursor1.getColumnIndex("matricula"));
                  nombre= cursor1.getString(cursor1.getColumnIndex("nombre"));
                  semestre= cursor1.getString(cursor1.getColumnIndex("semestre"));

              }

              cursor1.close();


               Intent i = new Intent(MainActivity.this,AgregarActivity.class);
               i.putExtra("nombre",nombre);
               i.putExtra("matricula", matricula);
               i.putExtra("semestre",semestre);
               i.putExtra("id",id);
               startActivityForResult(i,1);

               db.close();
           }
       });

       //insertarEstudiantes();

       db.close();

    }

    public void insertarEstudiantes() {

        db.execSQL("INSERT INTO estudiantes (matricula, nombre, semestre) " +
                " VALUES ('E14081454','José Geovany',8); ");

        db.execSQL("INSERT INTO estudiantes (matricula, nombre, semestre) " +
                " VALUES ('E14084554','Alberto',8); ");

        db.execSQL("INSERT INTO estudiantes (matricula, nombre, semestre) " +
                " VALUES ('E10081431','Juan Carlos Bodoque',3); ");

        db.execSQL("INSERT INTO estudiantes (matricula, nombre, semestre) " +
                " VALUES ('E14064454','Tulio Trevinio',4); ");

        db.execSQL("INSERT INTO estudiantes (matricula, nombre, semestre) " +
                " VALUES ('E14089434','Tenison',6); ");



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.agregarContacto:

                Intent i = new Intent(this,AgregarActivity.class);
                startActivityForResult(i,1);

                break;

            case R.id.buscarContacto:
                Toast.makeText(this, "Buscar Contacto", Toast.LENGTH_SHORT).show();

                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 1 && resultCode == RESULT_OK){
          //  Toast.makeText(this, "Entro a refrescar1", Toast.LENGTH_SHORT).show();
            refrescarLista();
        }
    }

    private void refrescarLista() {
        //Toast.makeText(this, "Entro a refrescar2", Toast.LENGTH_SHORT).show();

        db = this.openOrCreateDatabase("escolar.db", MODE_PRIVATE, null);
        cursor = db.rawQuery("SELECT * FROM estudiantes",null);
        lista.setAdapter(new MiAdaptador(this, cursor));
        db.close();
    }


}
