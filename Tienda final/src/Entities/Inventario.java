/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

public class Inventario {

    public static Map<Integer, Producto> productos = new HashMap<>(); // id , producto 
    private static Map<Producto, Integer> stockProductos = new HashMap<>(); //producto, Cantidad

    public Inventario() {
    }

    public static void registrarProducto(Producto producto, int cantidadInicial) {
        productos.put(producto.getIdProducto(), producto);
        stockProductos.put(producto, cantidadInicial);
    }

    public static void mostratProductos(DefaultTableModel modeloTabla) {
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de agregar los productos

        for (Producto producto : productos.values()) {
            modeloTabla.addRow(new Object[]{
                producto.getIdProducto(),
                producto.getNombre(),
                producto.getTipoProducto(),
                producto.getPrecio(),
                stockProductos.get(producto),
                producto.getProveedor(),
                producto.getFechaVencimiento()
            });
        }
    }

    public static int obtenerStock(int idProducto) {
        Producto producto = productos.get(idProducto);
        int stock = stockProductos.get(producto);
        return stock;
    }

    public static void actualizarStock(Producto producto, int cantidad) {
        if (!stockProductos.containsKey(producto)) {
            System.out.println("El producto no está registrado en el inventario.");
            return;
        }

        int cantidadActual = stockProductos.get(producto);
        if (cantidadActual < cantidad) {
            System.out.println("Stock insuficiente para el producto: " + producto.getNombre());
            return;
        }

        stockProductos.put(producto, cantidadActual - cantidad);
        System.out.println("Stock actualizado. Cantidad final: " + stockProductos.get(producto));
    }

    public static Map<Integer, Integer> productosStockBajo(int umbral) {
        Map<Integer, Integer> bajos = new HashMap<>();
        for (var entry : stockProductos.entrySet()) {
            if (entry.getValue() < umbral) {
                // bajos.put(entry.getKey(), entry.getValue());
            }
        }
        return bajos;
    }

    public static Map<Integer, Producto> getProductos() {
        return productos;
    }

    public static void setProductos(Map<Integer, Producto> productos) {
        Inventario.productos = productos;
    }

    public static Map<Producto, Integer> getStockProductos() {
        return stockProductos;
    }

    public static void setStockProductos(Map<Producto, Integer> stockProductos) {
        Inventario.stockProductos = stockProductos;
    }

}
