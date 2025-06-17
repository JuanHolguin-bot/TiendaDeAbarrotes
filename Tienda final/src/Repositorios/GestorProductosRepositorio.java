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
        Document doc = new Document("_id", producto.getIdProducto())
                .append("nombre", producto.getNombre())
                .append("tipo producto", producto.getTipoProducto())
                .append("proveedor", producto.getProveedor())
                .append("fecha vencimiento", producto.getFechaVencimiento())
                .append("precio", producto.getPrecio());
        // Agrega aquí los demás campos de Producto que necesites
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

                // Validar campos obligatorios
                if (nombre == null || tipoProducto == null || proveedor == null || fechaVencimiento == null) {
                    // Puedes registrar un log aquí si quieres saber qué documento está incompleto
                    continue; // Salta este documento
                }

                Producto producto = null;
                switch (tipoProducto) {
                    case "Enlatados" ->
                        producto = new ProductoEnlatados(nombre, id, tipoProducto, proveedor, fechaVencimiento, precio);
                    case "Bebidas" ->
                        producto = new ProductoBebidas(nombre, id, tipoProducto, proveedor, fechaVencimiento, precio);
                    case "Aseo" ->
                        producto = new ProductoAseo(nombre, id, tipoProducto, proveedor, fechaVencimiento, precio);
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
