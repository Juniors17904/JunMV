package com.example.a1examen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EditTemaDialog {

    private ArrayList<String> temasList;
    private ArrayAdapter<String> adapter;
    private Context context;
    private ConfBD dbHelper;
    private String temaActual;
    private int position;

    public EditTemaDialog(Context context, ArrayList<String> temasList, ArrayAdapter<String> adapter, ConfBD dbHelper, String temaActual, int position) {
        this.context = context;
        this.temasList = temasList;
        this.adapter = adapter;
        this.dbHelper = dbHelper;
        this.temaActual = temaActual;
        this.position = position;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Editar Tema");

        // Crear un EditText para modificar el tema existente
        final EditText input = new EditText(context);
        input.setText(temaActual); // establecer el texto actual
        builder.setView(input);

        // Configurar botones de diálogo
        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nuevoTema = input.getText().toString();
                if (!nuevoTema.isEmpty()) {
                    dbHelper.updateTema(temaActual, nuevoTema); // Método para actualizar el tema en la base de datos
                    updateSpinner(nuevoTema); // Actualizar el spinner
                    Toast.makeText(context, "Tema actualizado", Toast.LENGTH_SHORT).show();
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

    private void updateSpinner(String nuevoTema) {
        temasList.set(position, nuevoTema); // Actualiza el tema en la lista
        adapter.notifyDataSetChanged(); // Notifica que los datos han cambiado
    }
}
