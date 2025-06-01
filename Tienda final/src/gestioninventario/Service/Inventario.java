/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestioninventario.Service;

import Entities.Producto;

public class Inventario {

    //Attributes 
    private IProductoManager productoManager;
    private IStockManager stockManager;

    public Inventario(IProductoManager productoManager, IStockManager stockManager) {
        this.productoManager = productoManager;
        this.stockManager = stockManager;
    }

    public void registrarProducto(Producto producto, int cantidadInicial) {
        productoManager.registrarProducto(producto);
        stockManager.registrarStock(producto.getIdProducto(), cantidadInicial);
    }

}
