/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import java.util.*;

public class Venta {

    private final Map<Producto, Integer> productos = new HashMap<>(); // Almacena los productos a vender
    private final Map<Producto, Double> descuentos = new HashMap<>(); // Nuevo HashMap para descuentos 
    private String cliente;
    private Date fecha;
    private double monto; // Atributo para almacenar el monto total de la venta
    private Usuario vendedor;

    public void agregarProducto(Producto producto, int cantidad, double descuento) {
        productos.put(producto, cantidad);
        descuentos.put(producto, descuento);
    }

    public Map<Producto, Integer> getProductos() {
        return productos;
    }

    public double getDescuento(Producto producto) {
        return descuentos.getOrDefault(producto, 0.0); // Obtener el descuento o 0 si no existe
    }

    // Getters y setters para cliente, fecha, monto, vendedor...
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }
}
