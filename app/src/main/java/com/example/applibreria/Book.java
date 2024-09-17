package com.example.applibreria;

public class Book {
    private static String codeBook;
    private static String nameBook;
    private static int costeBook;
    private static int availableBook;

    public static String getCodeBook() {
        return codeBook;
    }

    public void setCodeBook(String codeBook) {
        this.codeBook = codeBook;
    }

    public static String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public static int getCosteBook() {
        return costeBook;
    }

    public void setCosteBook(int costeBook) {
        this.costeBook = costeBook;
    }

    public static int getAvailableBook() {
        return availableBook;
    }

    public void setAvailableBook(int availableBook) {
        this.availableBook = availableBook;
    }
}
