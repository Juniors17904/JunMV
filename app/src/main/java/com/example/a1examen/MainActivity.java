package com.example.a1examen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Spinner spTemas;
    private Button btnEntrarCuestionario;
    private FloatingActionButton fabAgregar, fabEditar, fabBorrar;
    private ArrayList<String> temasList;
    private ArrayAdapter<String> adapter;
    private ConfBD dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar componentes
        spTemas = findViewById(R.id.spTemas);
        btnEntrarCuestionario = findViewById(R.id.btnEntrarCuestionario);
        fabAgregar = findViewById(R.id.Fagregar);
        fabEditar = findViewById(R.id.Feditar);
        fabBorrar = findViewById(R.id.Fborrar);

        // Inicializar la base de datos
        dbHelper = new ConfBD(this);

        // Obtener los temas desde la base de datos
        temasList = dbHelper.getAllTemas();

        // Configurar el adaptador del Spinner
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, temasList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTemas.setAdapter(adapter);

        // Listener para entrar al cuestionario
        btnEntrarCuestionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lógica para ir al cuestionario
                Intent intent = new Intent(MainActivity.this, CuestionarioActivity.class); // Cambia a tu actividad de cuestionario
                startActivity(intent);
            }
        });

        // Listener para agregar un nuevo tema
        fabAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear y mostrar el diálogo para agregar un tema
                AddTemaDialog temaDialog = new AddTemaDialog(MainActivity.this, temasList, adapter, dbHelper);
                temaDialog.show();
            }
        });

        // Listener para editar un tema
        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temaSeleccionado = spTemas.getSelectedItem().toString(); // Obtener el tema seleccionado
                int position = spTemas.getSelectedItemPosition(); // Obtener la posición del tema seleccionado

                // Mostrar el diálogo para editar el tema
                EditTemaDialog editTemaDialog = new EditTemaDialog(MainActivity.this, temasList, adapter, dbHelper, temaSeleccionado, position);
                editTemaDialog.show();
            }
        });


        // Listener para borrar un tema
        fabBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lógica para borrar un tema (puedes implementar la lógica aquí)
            }
        });
    }
}