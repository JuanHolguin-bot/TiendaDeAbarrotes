/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestioninventario.Service;

import gestioninventario.Producto;
import java.util.Map;

/**
 *
 * @author Juan Holguin
 */
public interface IProductoManager {    //Define las operaciones relacionadas con los productos
    void registrarProducto(Producto producto);
    Producto obtenerProducto(int idProducto);
    Map<Integer, Producto> obtenerTodosLosProductos();
}
