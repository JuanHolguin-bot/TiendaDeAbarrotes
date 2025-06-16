package gestioninventario.Service;

import Entities.Producto;
import Entities.Venta;

public class GestorVenta {
    private final IStockManager stockManager;

    public GestorVenta(IStockManager stockManager) {
        this.stockManager = stockManager;
    }

    public void agregarProductoAVenta(Venta venta, Producto producto, int cantidad, double descuento) {
        int stockDisponible = stockManager.obtenerStock(producto.getIdProducto());
        if (stockDisponible >= cantidad) {
            venta.agregarProducto(producto, cantidad, descuento);
        } else {
           // excepcion lara cuando no haya suifiente stock 
        }
    }

    public double calcularTotalPorProducto(double precio, int cantidad, double descuento) {
        double totalInicial = precio * cantidad;
        return totalInicial - (totalInicial * descuento / 100);
    }

    public double calcularMontoTotal(Venta venta) {
        double montoTotal = 0.0;
        for (var entry : venta.getProductos().entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();
            double descuento = venta.getDescuento();
            montoTotal += calcularTotalPorProducto(producto.getPrecio(), cantidad, descuento);
        }
        return montoTotal;
    }

    public void generarVenta(Venta venta) {
        for (var entry : venta.getProductos().entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();
            stockManager.actualizarStock(producto.getIdProducto(), cantidad);
        }
    }
}