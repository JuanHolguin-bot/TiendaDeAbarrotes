/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestioninventario;

import java.util.*;

public class Venta {

    private final Map<Producto, Integer> productos = new HashMap<>(); // Almacena los productos a vender
    private final Map<Producto, Double> descuentos = new HashMap<>(); // Nuevo HashMap para descuentos
    private String cliente;
    private Date fecha;
    private double monto; // Atributo para almacenar el monto total de la venta
    private Usuario vendedor;

    public Venta() {
    }

    public void agregarProductoEnLista(Producto producto, int cantidad, double descuento) {
        int idProducto = producto.getIdProducto();
        int stockDisponible = Inventario.obtenerStock(idProducto);
        if (stockDisponible >= cantidad) {
            productos.put(producto, cantidad);
            descuentos.put(producto, descuento); // Guardar el descuento
        } else {
            System.out.println("Stock insuficiente para el producto: tenemos solo " + stockDisponible);
        }
    }

    public double calcularTotalPorProducto(double precio, int cantidad, double descuento) {
        double totalInicial = (precio * cantidad);
        double totalFila = totalInicial - (totalInicial * descuento / 100);
        return totalFila;
    }

    public Map<Producto, Integer> getProductos() {
        return productos;
    }

    public double getDescuento(Producto producto) {
        return descuentos.getOrDefault(producto, 0.0); // Obtener el descuento o 0 si no existe
    }

    public void generarVenta() {
        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            Producto producto = entry.getKey();
            Integer cantidad = entry.getValue();
            Inventario.actualizarStock(producto, cantidad);
            int cantidadFinal = Inventario.obtenerStock(producto.getIdProducto());
            System.out.println("Producto: " + producto.getNombre() + ", Cantidad final: " + cantidadFinal);
            
        }
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getVendedor() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getUsuario() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUsuario'");
    }
}
