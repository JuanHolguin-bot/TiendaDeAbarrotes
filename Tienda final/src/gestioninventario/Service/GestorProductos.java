/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestioninventario.Service;

import Entities.Producto;
import Repositorios.GestorProductosRepositorio;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Juan Holguin
 */
public class GestorProductos implements IProductoManager {

    // attributes
    private static  Map<Integer, Producto> productos = new HashMap<>(); // idProducto , producto
    private final GestorProductosRepositorio productoRepositorio = new GestorProductosRepositorio();

    // Methods
    @Override
    public void registrarProducto(Producto producto) {
        productos.put(producto.getIdProducto(), producto);
        productoRepositorio.guardarProducto(producto);
    }

    @Override
    public Producto obtenerProducto(int idProducto) {
        return productos.get(idProducto);
    }
    

    @Override
    public Map<Integer, Producto> obtenerTodosLosProductos() {
        try {
            productos = productoRepositorio.cargarProductodDesdeBd();
        } catch (Exception e) {
            // Maneja el error, por ejemplo:
            System.out.println("Error al cargar productos: " + e.getMessage());
            productos = new HashMap<>(); // O deja el mapa vacío
        }
        
        return productos;
    }
    
}
