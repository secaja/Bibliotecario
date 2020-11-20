
package com.ceiba.biblioteca.dominio.unitaria;


import com.ceiba.biblioteca.dominio.Libro;
import com.ceiba.biblioteca.dominio.excepcion.PrestamoException;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioLibro;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioPrestamo;
import com.ceiba.biblioteca.dominio.servicio.bibliotecario.ServicioBibliotecario;
import com.ceiba.biblioteca.testdatabuilder.LibroTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioBibliotecarioTest {

    @Test
    public void noPermitirPrestarUnLibroNoregistrado(){
        //arrange
        String isbn = "1597";
        String nombreUsuario = "sebas";
        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        when(repositorioLibro.obtenerPorIsbn(isbn)).thenReturn(null);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        //act
        try {
            servicioBibliotecario.prestar(isbn, nombreUsuario);
            fail();
        } catch (PrestamoException exception){
            //assert
            Assertions.assertThat(exception.getMessage()).isEqualTo(ServicioBibliotecario.NO_EXISTE_LIBRO_CON_EL_ISBN_SUMINISTRADO);
        }
    }

    @Test
    public void noPrestarLibroPalindromo(){
        //arrange
        String nombreUsuario = "sebas";
        Libro libro = new Libro("1771", "x-men", 2000);
        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        when(repositorioLibro.obtenerPorIsbn(libro.getIsbn())).thenReturn(libro);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        //act
        try {
            servicioBibliotecario.prestar(libro.getIsbn(), nombreUsuario);
            fail();
        }catch (PrestamoException exception){
            //assert
            Assertions.assertThat(exception.getMessage()).isEqualTo(ServicioBibliotecario.EL_LIBRO_PALINDROMO_NO_SE_PUEDE_PRESTAR);
        }
    }

    @Test
    public void noSePuedePrestarLibroPorqueNoEstaDisponible(){
        //arrange
        String nombreUsuario = "sebas";
        Libro libro = new Libro("1478" , "sedrt" , 1998);
        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        when(repositorioLibro.obtenerPorIsbn(libro.getIsbn())).thenReturn(libro);

        when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(libro);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);
        //act
        try {
            servicioBibliotecario.prestar(libro.getIsbn(), nombreUsuario);
            fail();
        }catch (PrestamoException exception){
            //assert
            Assertions.assertThat(exception.getMessage()).isEqualTo(ServicioBibliotecario.EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);
        }

    }
    @Test
    public void prestadoConExito(){
        //arrange

        String nombreUsuario = "sebas";
        Libro libro = new Libro("4789","qert",2005);


        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(null);
        when(repositorioLibro.obtenerPorIsbn(libro.getIsbn())).thenReturn(libro);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        //act



        }




    @Test
    public void libroYaEstaPrestadoTest() {

        // arrange
        Libro libro = new LibroTestDataBuilder().build();

        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(libro);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        // act
        boolean esPrestado = servicioBibliotecario.esPrestado(libro.getIsbn());

        //assert
        assertTrue(esPrestado);
    }

    @Test
    public void libroNoEstaPrestadoTest() {

        // arrange
        Libro libro = new LibroTestDataBuilder().build();

        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

        when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(null);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        // act
        boolean existeProducto = servicioBibliotecario.esPrestado(libro.getIsbn());

        //assert
        assertFalse(existeProducto);
    }
}

