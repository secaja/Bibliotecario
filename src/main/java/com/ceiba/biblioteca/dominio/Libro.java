package com.ceiba.biblioteca.dominio;

public class Libro {

    private final int NUMEROS_SUMATORIA_ISBN_MAYOR_30 = 30;

    private final String isbn;
    private final String titulo;
    private final int anio;

    public Libro(String isbn, String titulo, int anio) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.anio = anio;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAnio() {
        return anio;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean esPalindromo() {
        StringBuilder isbnInvertido = new StringBuilder(this.isbn);
        return this.isbn.equalsIgnoreCase(isbnInvertido.reverse().toString());
    }

    public boolean sumatoriaNumerosIsbnEsMayorA30() {
        String[] isbnDividido = isbn.split("");

        int sumatoria = 0;
        for (String caracter : isbnDividido) {
            try {
                sumatoria += Integer.parseInt(caracter);
            } catch (Exception ignored) { }
        }

        return sumatoria > NUMEROS_SUMATORIA_ISBN_MAYOR_30;
    }
}