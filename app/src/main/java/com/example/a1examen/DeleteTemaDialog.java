package com.example.a1examen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class DeleteTemaDialog {

    private ArrayList<String> temasList;
    private ArrayAdapter<String> adapter;
    private Context context;
    private ConfBD dbHelper;
    private String temaSeleccionado;

    public DeleteTemaDialog(Context context, ArrayList<String> temasList, ArrayAdapter<String> adapter, ConfBD dbHelper, String temaSeleccionado) {
        this.context = context;
        this.temasList = temasList;
        this.adapter = adapter;
        this.dbHelper = dbHelper;
        this.temaSeleccionado = temaSeleccionado;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Eliminar Tema");
        builder.setMessage("¿Estás seguro de que quieres eliminar el tema '" + temaSeleccionado + "'?, Esta accion tambien elimniará su cuestionario ");

        // Configurar botón positivo para eliminar el tema
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean success = dbHelper.deleteTema(temaSeleccionado);
                if (success) {
                    // Si se eliminó, también eliminar de la lista local
                    temasList.remove(temaSeleccionado);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(context, "Tema eliminado", Toast.LENGTH_SHORT).show();
                } else {
                    // Si no se encontró el tema, mostrar mensaje
                    Toast.makeText(context, "El tema no existe", Toast.LENGTH_SHORT).show();
                }



//                dbHelper.deleteTema(temaSeleccionado); // Borrar el tema de la base de datos
//                temasList.remove(temaSeleccionado); // Eliminar de la lista local
//                adapter.notifyDataSetChanged(); // Notificar el cambio
//                Toast.makeText(context, "Tema eliminado", Toast.LENGTH_SHORT).show();
            }
        });
        // Configurar botón negativo para cancelar la acción
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
