/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tienda;

import Entities.Producto;
import Entities.ProductoBebidas;
import gestioninventario.Service.GestorProductos;
import gestioninventario.Service.GestorStock;
import gestioninventario.Service.IProductoManager;
import gestioninventario.Service.IStockManager;
import gestioninventario.Service.Inventario;
import vistas.ListaProductos;
import vistas.Login;



/**
 *
 * @author jose_
 */
public class Tienda {
    /**
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        IProductoManager productoManager = new GestorProductos();
        IStockManager stockManager = new GestorStock();

        Inventario inventario = new Inventario(productoManager, stockManager);

        // Registrar productos
        Producto cocaCola = new ProductoBebidas("Coca-Cola", 101, "Bebida", "Coca-Cola Company", "2025-06-15", 1500.00);
        inventario.registrarProducto(cocaCola, 200);

        ListaProductos listaProductos = new ListaProductos(productoManager, stockManager, null);

        Login login = new Login(listaProductos);
        login.setVisible(true);
    }
    
}
