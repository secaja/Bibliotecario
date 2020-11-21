package com.ceiba.biblioteca.aplicacion.manejadores.prestamo;

import com.ceiba.biblioteca.dominio.servicio.bibliotecario.ServicioBibliotecario;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class ManejadorGenerarPrestamo {

    private ServicioBibliotecario servicioBibliotecario;

    public ManejadorGenerarPrestamo(ServicioBibliotecario servicioBibliotecario) {
        this.servicioBibliotecario = servicioBibliotecario;
    }

    @Transactional
    public void ejecutar(String isbn, String nombreCliente) {
        this.servicioBibliotecario.prestar( isbn, nombreCliente);
    }
}
