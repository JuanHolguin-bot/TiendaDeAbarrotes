/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestioninventario.Service;

import Entities.Producto;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Juan Holguin
 */
public class GestorStock implements IStockManager {

    //Attributes
    private static Map<Producto, Integer> stockProductos = new HashMap<>(); //producto, Cantidad

    //Methods 
    @Override
    public void registrarStock(Producto producto, int cantidadInicial) {
        stockProductos.put(producto, cantidadInicial);
    }

    @Override
    public int obtenerStock(Producto producto) {
        return stockProductos.getOrDefault(producto, 0);
    }

    @Override
    public void actualizarStock(Producto producto, int cantidad) {
        if (!stockProductos.containsKey(producto)) {
            throw new IllegalArgumentException("El producto no está registrado en el inventario.");
        }

        int cantidadActual = stockProductos.get(producto);
        if (cantidadActual < cantidad) {
            throw new IllegalStateException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        stockProductos.put(producto, cantidadActual - cantidad);
        System.out.println("Stock actualizado. Cantidad final: " + stockProductos.get(producto));
    }

    @Override
    public Map<Producto, Integer> productosStockBajo(int umbral) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
