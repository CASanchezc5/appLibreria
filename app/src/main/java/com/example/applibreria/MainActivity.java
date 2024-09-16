 package com.example.applibreria;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    // Instanciar elementos del archivo xml
    EditText codeBook, nameBook, costeBook;
    TextView message;
    Spinner availableBook;
    ImageButton save, search;

    // Array que llenara el spinner
    String[] arrAvailableBook = {"Disponible", "No Disponible"};

    // Instanciar la clase de sqlite
    DBLibrary oDB = new DBLibrary(this, "dbInventory", null, 1);

    // Generar el objeto de Product
    Book oBook = new Book();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Referenciar los objetos con los ids del archivo
        codeBook = findViewById(R.id.etCodBook);
        nameBook = findViewById(R.id.etNameBook);
        costeBook = findViewById(R.id.etCosteBook);
        message = findViewById(R.id.tvMessage);
        availableBook = findViewById(R.id.spAvailableBook);
        save = findViewById(R.id.ibSave);
        search = findViewById(R.id.ibSearch);

        // Generar el arrarAdapter que será llenado con el arrAvailableBook (spinner)
        ArrayAdapter<String> adpAvailableBook = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, arrAvailableBook);
        // Asignar el anterior adaptador al spinner
        availableBook.setAdapter(adpAvailableBook);

        // Eventos de cada botón
    }
}