/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestioninventario.Service;

import Entities.Producto;
import java.util.Map;

/**
 *
 * @author Juan Holguin
 */
public interface IStockManager {   //define las operaciones relacionadas con el stock

    void registrarStock(int idProducto, int cantidadInicial);
    int obtenerStock(int idProducto);
    void actualizarStock(int idProdcuto, int cantidad);
    Map<Integer,Integer> cargarStockDesdeRepositorio();
<<<<<<< HEAD
    Map<Producto, Integer> productosStockBajo(int umbral);
=======
    Map<Integer, Integer> productosStockBajo(int umbral);
>>>>>>> parent of 31125de (Merge branch 'main' into Develop)
    
}
