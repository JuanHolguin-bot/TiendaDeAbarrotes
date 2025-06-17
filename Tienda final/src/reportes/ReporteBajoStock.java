package reportes;

import gestioninventario.Service.GestorStock;
import gestioninventario.Service.IProductoManager;
import gestioninventario.Service.IStockManager;
import java.util.HashMap;
import java.util.Map;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Juan Holguin
 */
public class ReporteBajoStock {

    private IStockManager gestorStock = new GestorStock();
    private IProductoManager gestorProductos;
    private Map<Integer, Integer> productosBajoStock = new HashMap<>();

    public ReporteBajoStock() {
    }

    public Map<Integer, Integer> getProductosBajoStock() {
        try {
            productosBajoStock = gestorStock.productosStockBajo(10);

            return productosBajoStock;
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, e.getMessage(), "Alerta de productos bajo stock", javax.swing.JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

}
