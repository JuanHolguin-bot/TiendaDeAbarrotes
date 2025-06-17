<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import gestioninventario.Venta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReportePorUsuario extends Reportes {

    public ReportePorUsuario(String titulo, List<Venta> ventas) {
        super(titulo, ventas);
    }

    @Override
    public void generarReporte() {
        imprimirEncabezado();
        System.out.println("Reporte de Ventas por Vendedor:");

        Map<String, Double> ventasPorVendedor = new HashMap<>();
        for (Venta venta : ventas) {
            String vendedor = venta.getUsuario();
            double monto = venta.getMonto();

            ventasPorVendedor.put(vendedor, ventasPorVendedor.getOrDefault(vendedor, 0.0) + monto);
        }

        for (Map.Entry<String, Double> entry : ventasPorVendedor.entrySet()) {
            System.out.println("Vendedor: " + entry.getKey() + " - Total Ventas: $" + entry.getValue());
        }
    }
}
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;


/*
public class ReportePorUsuario extends Reportes {

    public ReportePorUsuario(String titulo, List<Venta> ventas) {
        super(titulo, ventas);
    }

    @Override
    public void generarReporte() {
        imprimirEncabezado();
        System.out.println("Reporte de Ventas por Vendedor:");

        Map<String, Double> ventasPorVendedor = new HashMap<>();
        for (Venta venta : ventas) {
            String vendedor = venta.getUsuario();
            double monto = venta.getMonto();

            ventasPorVendedor.put(vendedor, ventasPorVendedor.getOrDefault(vendedor, 0.0) + monto);
        }

        for (Map.Entry<String, Double> entry : ventasPorVendedor.entrySet()) {
            System.out.println("Vendedor: " + entry.getKey() + " - Total Ventas: $" + entry.getValue());
        }
    }
}*/
>>>>>>> parent of b423e5c (Merge pull request #33 from JuanHolguin-bot/Feature/ReporteBajoStock)
