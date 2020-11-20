package com.ceiba.biblioteca.dominio.servicio.bibliotecario;

import com.ceiba.biblioteca.dominio.Libro;
import com.ceiba.biblioteca.dominio.Prestamo;
import com.ceiba.biblioteca.dominio.excepcion.PrestamoException;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioLibro;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioPrestamo;

public class ServicioBibliotecario {

    public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";
    public static final String NO_EXISTE_LIBRO_CON_EL_ISBN_SUMINISTRADO = "El libro no existe";
    public static final String EL_LIBRO_PALINDROMO_NO_SE_PUEDE_PRESTAR = "Los libros palíndromos solo se pueden utilizar en la biblioteca";

    private final RepositorioLibro repositorioLibro;
    private final RepositorioPrestamo repositorioPrestamo;

    public ServicioBibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
        this.repositorioLibro = repositorioLibro;
        this.repositorioPrestamo = repositorioPrestamo;
    }

    public void prestar(String isbn, String nombreUsuario) {
        Libro libro = consultarLibro(isbn);
        validarIsbnPalindromo(libro);
        prestarLibro(libro, nombreUsuario);
    }

    private void prestarLibro(Libro libro, String nombreUsuario){
        if(!esPrestado(libro.getIsbn())){
            Prestamo prestamo = new Prestamo(libro, nombreUsuario);
            this.repositorioPrestamo.agregar(prestamo);
        } else {
            throw new PrestamoException(EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);
        }
    }

    private Libro consultarLibro(String isbn){
        Libro libro = this.repositorioLibro.obtenerPorIsbn(isbn);
        if(libro == null){
            throw new PrestamoException( NO_EXISTE_LIBRO_CON_EL_ISBN_SUMINISTRADO );
        }
        return libro;
    }

    private void validarIsbnPalindromo(Libro libro){
        if (libro.esPalindromo()){
            throw  new PrestamoException(EL_LIBRO_PALINDROMO_NO_SE_PUEDE_PRESTAR);
        }
    }

    public boolean esPrestado(String isbn) {
        Libro libro = this.repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn);
        return libro != null;
    }

}
