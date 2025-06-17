<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import gestioninventario.Venta;
import java.util.List;

public class ReporteVentas extends Reportes {

        public ReporteVentas(String titulo, List<Venta> ventas) {
            super(titulo, ventas);
        }

        @Override
        public void generarReporte() {
            imprimirEncabezado();
            System.out.println("Detalle de Ventas:");
            for (Venta venta : ventas) {
                System.out.println(venta.toString());
            }
        }
    }
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import Entities.Venta;
import java.util.List;

public class ReporteVentas extends Reportes {

        public ReporteVentas(String titulo, List<Venta> ventas) {
            super(titulo, ventas);
        }

        @Override
        public void generarReporte() {
            imprimirEncabezado();
            System.out.println("Detalle de Ventas:");
            for (Venta venta : ventas) {
                System.out.println(venta.toString());
            }
        }
    }
>>>>>>> parent of b423e5c (Merge pull request #33 from JuanHolguin-bot/Feature/ReporteBajoStock)
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import Entities.Venta;
import java.util.List;

public class ReporteVentas extends Reportes {

        public ReporteVentas(String titulo, List<Venta> ventas) {
            super(titulo, ventas);
        }

        @Override
        public void generarReporte() {
            imprimirEncabezado();
            System.out.println("Detalle de Ventas:");
            for (Venta venta : ventas) {
                System.out.println(venta.toString());
            }
        }
    }
>>>>>>> parent of b423e5c (Merge pull request #33 from JuanHolguin-bot/Feature/ReporteBajoStock)
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import Entities.Venta;
import java.util.List;

public class ReporteVentas extends Reportes {

        public ReporteVentas(String titulo, List<Venta> ventas) {
            super(titulo, ventas);
        }

        @Override
        public void generarReporte() {
            imprimirEncabezado();
            System.out.println("Detalle de Ventas:");
            for (Venta venta : ventas) {
                System.out.println(venta.toString());
            }
        }
    }
>>>>>>> parent of b423e5c (Merge pull request #33 from JuanHolguin-bot/Feature/ReporteBajoStock)
