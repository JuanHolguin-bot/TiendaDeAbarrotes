/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import gestioninventario.Venta;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author User
 */
public abstract class Reportes {
    protected String titulo;
    protected List<Venta> ventas;
    protected String fechaGeneracion;

    public Reportes(String titulo, List<Venta> ventas) {
        this.titulo = titulo;
        this.ventas = ventas;
        this.fechaGeneracion = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // Fecha actual
    }

    // Método abstracto que las subclases deben implementar
    public abstract void generarReporte();

    // Método auxiliar opcional para imprimir encabezado
    protected void imprimirEncabezado() {
        System.out.println("============================");
        System.out.println("Reporte: " + titulo);
        System.out.println("Fecha de generación: " + fechaGeneracion);
        System.out.println("============================");
    }
}    