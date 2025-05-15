/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import gestioninventario.Venta;
import java.util.List;


  public class ReporteStockBajo extends Reportes {

        public ReporteStockBajo(String titulo, List<Venta> ventas) {
            super(titulo, ventas);
        }

        @Override
        public void generarReporte() {
        }
    }