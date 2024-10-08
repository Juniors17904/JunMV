package com.example.a1examen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddTemaDialog {

    private ArrayList<String> temasList;
    private ArrayAdapter<String> adapter;
    private Context context;
    private ConfBD dbHelper;

    public AddTemaDialog(Context context, ArrayList<String> temasList, ArrayAdapter<String> adapter, ConfBD dbHelper) {
        this.context = context;
        this.temasList = temasList;
        this.adapter = adapter;
        this.dbHelper = dbHelper;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Agregar Tema");

        // Crear un EditText para ingresar el nuevo tema
        final EditText input = new EditText(context);
        builder.setView(input);

        // Configurar botones de diálogo
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nuevoTema = input.getText().toString();
                if (!nuevoTema.isEmpty()) {
                    dbHelper.addTema(nuevoTema); // Agregar el nuevo tema a la base de datos
                    updateSpinner(); // Actualizar el spinner con los nuevos datos
                    Toast.makeText(context, "Tema agregado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Ingrese un tema válido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void updateSpinner() {
        temasList.clear();
        temasList.addAll(dbHelper.getAllTemas()); // Obtener la lista actualizada de la base de datos
        adapter.notifyDataSetChanged(); // Notificar que los datos han cambiado
    }
}
