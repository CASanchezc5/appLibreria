 package com.example.applibreria;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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

import java.util.ArrayList;

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
        //Boton Guardar
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mCod = codeBook.getText().toString();
                String mName = nameBook.getText().toString();
                String mCoste = costeBook.getText().toString();
                String mAvailable = availableBook.getSelectedItem().toString();
                //Invocar un metodo para chequear que todos los datos esten diligenciados
                if (checkData(mCod, mName, mCoste)) {
                    //Buscar la referencia en la tabla book
                    if (searchCode(codeBook.getText().toString()).size() == 0) {
                        // Guardar el libro
                        // Crear objeto de SQLiteDatabase en modo escritura
                        SQLiteDatabase osldbBook = oDB.getWritableDatabase();
                        // Crear una tabla temporal con ContentValues con los mismos campos de la tabla book
                        ContentValues cvBook = new ContentValues();
                        cvBook.put("codeBook", mCod);
                        cvBook.put("nameBook", mName);
                        cvBook.put("costeBook", Integer.valueOf(mCoste));
                        int mavailableBook = mAvailable.equals("Disponible") ? 0 : 1;
                        cvBook.put("availableBook", mavailableBook);

                        // Almacenar el nuevo libro
                        osldbBook.insert("book", null, cvBook);
                        message.setTextColor(Color.GREEN);
                        message.setText("El libro se ha guardado correctamente");
                        osldbBook.close();
                        clearFields();
                    } else {
                        message.setTextColor(Color.RED);
                        message.setText("El libro ya EXISTE. Inténtelo con otro...");
                    }
                } else {
                    message.setTextColor(Color.RED);
                    message.setText("Debe diligenciar todos los datos del libro");
                }
            }
        });


//Boton Buscar
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mCod = codeBook.getText().toString();
                if (!mCod.isEmpty()) {
                    if (searchCode(mCod).size() > 0) {
                        // Recuperar los datos del objeto oBook
                        nameBook.setText(oBook.getNameBook());
                        costeBook.setText(String.valueOf(oBook.getCosteBook()));
                        switch (oBook.getAvailableBook()) {
                            case 0:
                                availableBook.setSelection(0);
                                break;
                            case 1:
                                availableBook.setSelection(1);
                                break;
                        }
                    } else {
                        message.setTextColor(Color.RED);
                        message.setText("El libro NO EXISTE. Ingrese de nuevo el codigo...");
                    }
                } else {
                    message.setTextColor(Color.RED);
                    message.setText("Ingrese el codigo del libro que desea buscar");
                }
            }
        });
    }
    // Encontrar Libro en una base de datos SQLite
    private ArrayList<Book> searchCode(String mCod){
        //Definir el ArrayList que se devolverá
        ArrayList<Book> arrBook = new ArrayList<Book>();
        //Generar objeto de la clase SQLiteDataBase en modo lectura
        SQLiteDatabase osdbRead = oDB.getReadableDatabase();
        String query = "select nameBook, costeBook, availableBook from book where codeBook = '"+mCod+"' ";
        //Generar una tabla cursor (es una tabla en memoria, para almacenar los registros enviados por un query
        Cursor cBook = osdbRead.rawQuery(query, null);
        //Verificar si la tabla cBook tiene al menos un registro
        if(cBook.moveToFirst()){
            //Llenar el objeto de Book
            oBook.setCodeBook(mCod);
            oBook.setNameBook(cBook.getString(0));
            oBook.setCosteBook(cBook.getInt(1));
            oBook.setAvailableBook(cBook.getInt(2));
            //Agregar el objeto al ArrayListe arrBook
            arrBook.add(oBook);
        }
        cBook.close();
        return arrBook;
    }

     //Validar la entrada de datos
     private boolean checkData(String mCod, String mName, String mCoste){
        return !mCod.isEmpty() && !mName.isEmpty() && !mCoste.isEmpty();
     }

     // Método para limpiar los campos después de guardar o buscar
     private void clearFields() {
         codeBook.setText("");
         nameBook.setText("");
         costeBook.setText("");
         availableBook.setSelection(0);
         message.setText("");
     }
}