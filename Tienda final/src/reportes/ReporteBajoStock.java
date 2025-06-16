package reportes;

import Entities.Producto;
import gestioninventario.Service.IProductoManager;
import gestioninventario.Service.IStockManager;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Juan Holguin
 */
public class ReporteBajoStock {
    private IStockManager gestorStock;
    private IProductoManager gestorProductos;
    private Map<Integer, Integer> ProductosBajoStock = new HashMap<>();
    
    public void getProductosBajoStock() {
        ProductosBajoStock = gestorStock.productosStockBajo(10);
        if (ProductosBajoStock != null && !ProductosBajoStock.isEmpty()) {
            StringBuilder mensaje = new StringBuilder();
            for (Map.Entry<Integer, Integer> entry : ProductosBajoStock.entrySet()) {
                int id = entry.getKey();
                int stock = entry.getValue();
               
                Producto producto = gestorProductos.obtenerProducto(id); 
                mensaje.append("Producto: ").append(producto.getNombre())
                       .append(" | Stock actual: ").append(stock)
                       .append("\n");
            }
            JOptionPane.showMessageDialog(null, mensaje.toString(), "Alerta de productos bajo stock", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Ejemplo de método auxiliar (ajusta según tu gestor de productos)
    private String obtenerNombreProductoPorId(int idProducto) {
        // Aquí deberías consultar tu gestor de productos o el mapa de productos
        // Por ejemplo:
        // Producto producto = gestorProductos.obtenerProducto(idProducto);
        // return producto != null ? producto.getNombre() : "Desconocido";
        return "NombreProducto" + idProducto; // Placeholder
    }
}
