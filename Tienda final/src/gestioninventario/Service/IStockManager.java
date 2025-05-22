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

    void registrarStock(Producto producto, int cantidadInicial);
    int obtenerStock(Producto producto);
    void actualizarStock(Producto producto, int cantidad);
    Map<Producto, Integer> productosStockBajo(int umbral);
    
}
