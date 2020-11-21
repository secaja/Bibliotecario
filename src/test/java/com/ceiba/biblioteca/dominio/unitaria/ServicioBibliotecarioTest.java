
package com.ceiba.biblioteca.dominio.unitaria;


import com.ceiba.biblioteca.dominio.Libro;
import com.ceiba.biblioteca.dominio.Prestamo;
import com.ceiba.biblioteca.dominio.excepcion.PrestamoException;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioLibro;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioPrestamo;
import com.ceiba.biblioteca.dominio.servicio.bibliotecario.ServicioBibliotecario;
import com.ceiba.biblioteca.testdatabuilder.LibroTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
            servicioBibliotecario.prestar(isbn, nombreUsuario, new Date());
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
            servicioBibliotecario.prestar(libro.getIsbn(), nombreUsuario, new Date());
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
            servicioBibliotecario.prestar(libro.getIsbn(), nombreUsuario, new Date());
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
        Libro libro = new Libro("1478" , "sedrt" , 1998);
        Prestamo prestamo= new Prestamo(new Date(),libro,null,nombreUsuario);

        RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);
        RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);

        when(repositorioLibro.obtenerPorIsbn(libro.getIsbn())).thenReturn(libro);
        when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(null);
        doNothing().when(repositorioPrestamo).agregar(prestamo);

        ServicioBibliotecario servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);

        // act
        servicioBibliotecario.prestar(libro.getIsbn(),nombreUsuario, new Date());

        //assert
        verify(repositorioLibro).obtenerPorIsbn(libro.getIsbn());
        verify(repositorioPrestamo).obtenerLibroPrestadoPorIsbn(libro.getIsbn());
        verify(repositorioPrestamo).agregar(any());
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

