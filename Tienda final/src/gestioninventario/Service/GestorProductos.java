/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestioninventario.Service;

import gestioninventario.Producto;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Juan Holguin
 */
public class GestorProductos implements IProductoManager{
    //attributes 
    public static Map<Integer, Producto> productos = new HashMap<>(); // idProducto , producto 

    //Methods 
    @Override
    public void registrarProducto(Producto producto) {
        productos.put(producto.getIdProducto(), producto);
    }

    @Override
    public Producto obtenerProducto(int idProducto) {
         return productos.get(idProducto);
    }

    @Override
    public Map<Integer, Producto> obtenerTodosLosProductos() {
         return productos;
    }
    
}
