package com.ceiba.biblioteca.dominio;

import java.util.Calendar;
import java.util.Date;

public class Prestamo {

    private static final int DIAS_HABILES_MAXIMOS_ENTREGA = 15;
    private static final int SUMAR_UN_DIA = 1;
    private static final int DIA_DOMINGO = 1;

    private final Date fechaSolicitud;
    private final Libro libro;
    private Date fechaEntregaMaxima;
    private String nombreUsuario;

    public Prestamo(Libro libro, String nombreUsuario) {
        this.fechaSolicitud = new Date();
        this.libro = libro;
        this.nombreUsuario = nombreUsuario;
        calcularFechaDeEntrega();
    }

    public Prestamo(Date fechaSolicitud, Libro libro, Date fechaEntregaMaxima, String nombreUsuario) {
        this.fechaSolicitud = fechaSolicitud;
        this.libro = libro;
        this.fechaEntregaMaxima = fechaEntregaMaxima;
        this.nombreUsuario = nombreUsuario;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public Libro getLibro() {
        return libro;
    }

    public Date getFechaEntregaMaxima() {
        return fechaEntregaMaxima;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void calcularFechaDeEntrega() {
        if(libro.sumatoriaNumerosIsbnEsMayorA30()){
            int contador = 1;
            Calendar fechaEntrega = Calendar.getInstance();
            fechaEntrega.setTime(fechaSolicitud);

            while (contador < DIAS_HABILES_MAXIMOS_ENTREGA){
                fechaEntrega.add(Calendar.DAY_OF_YEAR, SUMAR_UN_DIA);
                int diaSemana = fechaEntrega.get(Calendar.DAY_OF_WEEK);

                if(diaSemana != DIA_DOMINGO) {
                    contador++;
                }
            }

            this.fechaEntregaMaxima = fechaEntrega.getTime();
        }
    }
}
