package com.example.a1examen;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import java.util.ArrayList;

public class ConfBD extends SQLiteOpenHelper{


    private static final String DATABASE_NAME = "temas.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TEMAS = "temas";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TEMA = "tema";

    public ConfBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Crear la tabla para almacenar los temas
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TEMAS_TABLE = "CREATE TABLE " + TABLE_TEMAS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TEMA + " TEXT)";
        db.execSQL(CREATE_TEMAS_TABLE);
    }


    // Eliminar tabla antigua si existe y crear una nueva
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMAS);
        onCreate(db);
    }



    // Método para agregar un nuevo tema a la base de datos
    public void addTema(String tema) {
        SQLiteDatabase db = this.getWritableDatabase(); // abre la base de datos en modo escritura
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEMA, tema);

        db.insert(TABLE_TEMAS, null, values);
        db.close(); // Cerrar la conexión a la base de datos
    }

    // Método para actualizar un tema en la base de datos
    public void updateTema(String temaActual, String nuevoTema) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEMA, nuevoTema);

        // Actualizar el tema
        db.update(TABLE_TEMAS, values, COLUMN_TEMA + " = ?", new String[]{temaActual});
        db.close(); // Cerrar la conexión a la base de datos
    }

    // Método para eliminar un tema en la base de datos
    public boolean deleteTema(String tema) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_TEMAS, COLUMN_TEMA + " = ?", new String[]{tema});
        db.close();
        return rowsDeleted > 0; // Retorna verdadero si se eliminó al menos una fila
    }


    // Método para obtener todos los temas de la base de datos
    public ArrayList<String> getAllTemas() {
        ArrayList<String> temasList = new ArrayList<>();
        String query = "SELECT " + COLUMN_TEMA + " FROM " + TABLE_TEMAS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                temasList.add(cursor.getString(0)); // Obtener el tema
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return temasList;
    }
}
