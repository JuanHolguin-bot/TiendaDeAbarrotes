/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import java.time.LocalDate;

/**
 *
 * @author Juan Holguin
 */
public class ProductoBebidas extends Producto{

    @Override
    public void imprimirInformacionExtra() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ProductoBebidas(String nombre, int idProducto, String tipoProducto, String proveedor, LocalDate fechaVencimiento, double precio) {
        super(nombre, idProducto, tipoProducto, proveedor, fechaVencimiento, precio);
    }
    
}
