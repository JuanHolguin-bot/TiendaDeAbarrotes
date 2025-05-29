/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestioninventario.Service;

import Entities.Producto;
import Repositorios.GestorStockRepositorio;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Juan Holguin
 */
public class GestorStock implements IStockManager {

    //Attributes
    private static Map<Producto, Integer> stockProductos = new HashMap<>(); //producto, Cantidad
    private final GestorStockRepositorio stockRepositorio = new GestorStockRepositorio();

    public void sincronizarGuardarEnStockRepositorio() {
        Map<Integer, Integer> stockPorId = new HashMap<>();
        for (Map.Entry<Producto, Integer> entry : stockProductos.entrySet()) {
            stockPorId.put(entry.getKey().getIdProducto(), entry.getValue());
        }
        stockRepositorio.guardarStockEnBD(stockPorId);
    }

    public void sincronizarCargarDesdeStockRepositorio() {
        stockProductos.clear();

        Map<Integer, Integer> stockPorId = stockRepositorio.cargarStockDesdeBD();
        
        for(Map.Entry<Integer,Integer> entry : stockPorId.entrySet()){
            Producto producto = GestorProductos.productos.get(entry.getKey());
            if(producto != null){
                stockProductos.put(producto,entry.getValue());
            }
        }
    }

    //Methods 
    @Override
    public void registrarStock(Producto producto, int cantidadInicial) {
        stockProductos.put(producto, cantidadInicial);
        sincronizarGuardarEnStockRepositorio();
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
    }

    @Override
    public Map<Producto, Integer> productosStockBajo(int umbral) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
