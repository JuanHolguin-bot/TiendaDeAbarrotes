/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorios;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import Entities.Producto;

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
                .append("tipo producto",producto.getTipoProducto())
                .append("proveedor", producto.getProveedor())
                .append("fecha vencimiento", producto.getFechaVencimiento())
                .append("precio", producto.getPrecio());
        // Agrega aquí los demás campos de Producto que necesites
        prductosCollection.replaceOne(filtro, doc, new com.mongodb.client.model.ReplaceOptions().upsert(true));
    }

}
