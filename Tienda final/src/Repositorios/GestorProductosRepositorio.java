/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorios;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import Entities.Producto;
import Entities.ProductoAseo;
import Entities.ProductoBebidas;
import Entities.ProductoEnlatados;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

/**
 *
 * @author Juan Holguin
 */
public class GestorProductosRepositorio {

    private MongoDatabase dataBase;
    private MongoCollection<Document> prductosCollection;

    public GestorProductosRepositorio() {
        var cliente = MongoClients.create("mongodb+srv://juanholguin3:1035974679@cluster0.lmisdxx.mongodb.net/");
        dataBase = cliente.getDatabase("TiendaDeAbarrotes");
        prductosCollection = dataBase.getCollection("Productos");
    }

    public void guardarProducto(Producto producto) {
        Document filtro = new Document("_id", producto.getIdProducto());
        String fechaVencStr = producto.getFechaVencimiento() != null ? producto.getFechaVencimiento().toString() : null;
        Document doc = new Document("_id", producto.getIdProducto())
                .append("nombre", producto.getNombre())
                .append("tipo producto", producto.getTipoProducto())
                .append("proveedor", producto.getProveedor())
                .append("fecha vencimiento", fechaVencStr)
                .append("precio", producto.getPrecio());
        prductosCollection.replaceOne(filtro, doc, new com.mongodb.client.model.ReplaceOptions().upsert(true));
    }

    public Map<Integer, Producto> cargarProductodDesdeBd() throws Exception {
        Map<Integer, Producto> productosEnBd = new HashMap<>();
        try {
            for (Document doc : prductosCollection.find()) {
                int id = doc.getInteger("_id");
                String nombre = doc.getString("nombre");
                String tipoProducto = doc.getString("tipo producto");
                String proveedor = doc.getString("proveedor");
                String fechaVencimiento = doc.getString("fecha vencimiento");
                Double precioObj = doc.getDouble("precio");
                double precio = precioObj != null ? precioObj : 0.0;

                if (nombre == null || tipoProducto == null || proveedor == null || fechaVencimiento == null) {
                    continue;
                }

                Producto producto = null;
                LocalDate fechaVenc = null;
                try {
                    // Intentar formato ISO primero
                    fechaVenc = LocalDate.parse(fechaVencimiento, DateTimeFormatter.ISO_LOCAL_DATE);
                } catch (Exception e1) {
                    try {
                        // Intentar formato alternativo dd/MM/yyyy
                        fechaVenc = LocalDate.parse(fechaVencimiento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    } catch (Exception e2) {
                        continue; // Si falla, ignora el producto
                    }
                }
                switch (tipoProducto) {
                    case "Enlatados" ->
                        producto = new ProductoEnlatados(nombre, id, tipoProducto, proveedor, fechaVenc, precio);
                    case "Bebidas" ->
                        producto = new ProductoBebidas(nombre, id, tipoProducto, proveedor, fechaVenc, precio);
                    case "Aseo" ->
                        producto = new ProductoAseo(nombre, id, tipoProducto, proveedor, fechaVenc, precio);
                }

                if (producto != null) {
                    productosEnBd.put(id, producto);
                }
            }
        } catch (Exception exe) {
            throw new Exception("No hay productos registrados aún: " + exe.getMessage());
        }
        return productosEnBd;
    }
}
