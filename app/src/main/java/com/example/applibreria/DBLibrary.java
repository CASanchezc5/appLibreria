package com.example.applibreria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBLibrary extends SQLiteOpenHelper {

    // Definir la (s) variable (s) para la creación de las tablas
    String tblBook = "create table book(codeBook text, nameBook, costeBook integer, availableBook integer)";

    public DBLibrary(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear las tablas
        db.execSQL(tblBook);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table book");
        db.execSQL(tblBook);
    }
}
