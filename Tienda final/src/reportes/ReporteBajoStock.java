package reportes;

import Entities.Producto;
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
    private IStockManager gestorStock;
    private Map<Integer, Integer> ProductosBajoStock = new HashMap<>();
    
    public void getProductosBajoStock(){
        ProductosBajoStock = gestorStock.productosStockBajo(10);
        
    }
    
}
