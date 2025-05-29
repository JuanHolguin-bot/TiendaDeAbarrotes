/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositorios;

import com.mongodb.client.MongoClients;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Juan Holguin
 */
public class GestorStockRepositorio {
    
    private MongoDatabase dataBase;
    private MongoCollection<Document> stockCollection;

    public GestorStockRepositorio() {
        var cliente = MongoClients.create("mongodb+srv://juanholguin3:1035974679@cluster0.lmisdxx.mongodb.net/");
        dataBase = cliente.getDatabase("TiendaDeAbarrotes");
        stockCollection = dataBase.getCollection("Stock");
        cargarStockDesdeBD();
    }    
    
    public void guardarStockEnBD(Map<Integer, Integer> stockPorId) {
        for (Map.Entry<Integer, Integer> entry : stockPorId.entrySet()) {
            Document filtro = new Document("_id", entry.getKey());
            Document doc = new Document("_id", entry.getKey())
                .append("stock", entry.getValue());
            stockCollection.replaceOne(filtro, doc, new ReplaceOptions().upsert(true));
        }
    }
    
    public Map<Integer,Integer> cargarStockDesdeBD(){
        Map<Integer,Integer> stockPorId = new HashMap<>();
        for (Document doc : stockCollection.find()){
          Integer id = doc.getInteger("_id");
          Integer stock = doc.getInteger("stock");
          if (id != null && stock != null){
              stockPorId.put(id, stock);
          }
        }
        return stockPorId;
    }
}
