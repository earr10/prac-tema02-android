package com.example.practema02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et1, et2;
    Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enlazarComponentes();
    }

    private void enlazarComponentes() {
        et1 = (EditText) findViewById(R.id.etUno);
        et2 = (EditText) findViewById(R.id.etDos);
        bt1 = (Button) findViewById(R.id.btnUno);
    }

    public void guardarConsultar(View vista) {
        // Instanciar la BD SQLite
        Sqligera bd = new Sqligera(this, "datos", null, 1);
        // Crear un objeto que controle la BD RecordSet
        SQLiteDatabase controlarRegistros = bd.getWritableDatabase();
        // Hacer la inserción en la BD

        String nombre = et1.getText().toString();
        int edad = Integer.parseInt(et2.getText().toString());
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("edad", edad);

        long resultado = controlarRegistros.insert("personas", null, valores);
        Toast.makeText(this, ""+resultado, Toast.LENGTH_SHORT).show();

        /*if (resultado >= 1) {
            Toast.makeText(this, "Exito", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ocurrió un error", Toast.LENGTH_SHORT).show();
        }*/

        SQLiteDatabase controlarRegistros2 = bd.getReadableDatabase();
        Cursor cursor = controlarRegistros2.rawQuery("SELECT * FROM personas", null);

        String consulta = "";
        cursor.moveToFirst();
        if (cursor.getCount() >= 1) {
            do {
                consulta += "Nombre: " + cursor.getString(cursor.getColumnIndex("nombre"));
                consulta += "Edad: " + cursor.getString(cursor.getColumnIndex("edad"));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
            Toast.makeText(this, consulta, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No hay registros", Toast.LENGTH_SHORT).show();
        }

    }
}