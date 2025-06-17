/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestioninventario.Service;

import Entities.Producto;
import Repositorios.GestorStockRepositorio;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Holguin
 */
public class GestorStock implements IStockManager {

    //Attributes
    private static Map<Integer, Integer> stockProductos = new HashMap<>(); //idProducto, CantidadStock
    private final GestorStockRepositorio stockRepositorio = new GestorStockRepositorio();

    //Methods 
    @Override
    public void registrarStock(int idProdcuto, int cantidadInicial) {
        stockProductos.put(idProdcuto, cantidadInicial);
        stockRepositorio.guardarStockEnBD(stockProductos);
    }

    @Override
    public void actualizarStock(int idProdcuto, int cantidad) {
        if (!stockProductos.containsKey(idProdcuto)) {
            throw new IllegalArgumentException("El producto no está registrado en el inventario.");
        }
        int cantidadActual = stockProductos.get(idProdcuto);
        if (cantidadActual < cantidad) {
            throw new IllegalStateException("Stock insuficiente para el producto con ");
        }
        stockProductos.put(idProdcuto, cantidadActual - cantidad);
        stockRepositorio.guardarStockEnBD(stockProductos);
    }

    @Override
    public int obtenerStock(int idProdcuto) {
        cargarStockDesdeRepositorio();
        return stockProductos.getOrDefault(idProdcuto, 0);
    }

    @Override
    public Map<Integer, Integer> cargarStockDesdeRepositorio() {
        try {
            stockProductos = stockRepositorio.cargarStockDesdeBD();
        } catch (Exception ex) {
            Logger.getLogger(GestorStock.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stockProductos;
    }

    @Override
<<<<<<< HEAD
    public Map<Producto, Integer> productosStockBajo(int umbral) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
=======
    public Map<Integer, Integer> productosStockBajo(int umbral) {
        Map<Integer, Integer> productosBajoStock = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : stockProductos.entrySet()) {
            if (entry.getValue() <= umbral) {
                productosBajoStock.put(entry.getKey(), entry.getValue());
            }
        }
        return productosBajoStock;
>>>>>>> parent of 31125de (Merge branch 'main' into Develop)
    }
}
