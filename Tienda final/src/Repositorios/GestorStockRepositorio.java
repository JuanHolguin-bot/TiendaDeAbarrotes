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
    }    

    public void guardarStockEnBD(Map<Integer, Integer> stockPorId) {
        for (Map.Entry<Integer, Integer> entry : stockPorId.entrySet()) {
            Document filtro = new Document("_id", entry.getKey());
            Document doc = new Document("_id", entry.getKey())
                .append("stock", entry.getValue());
            stockCollection.replaceOne(filtro, doc, new ReplaceOptions().upsert(true));
        }
    }
    
    public Map<Integer,Integer> cargarStockDesdeBD() throws Exception{
        Map<Integer,Integer> stockEnBd = new HashMap<>();
        try{
        for (Document doc : stockCollection.find()){
            var id =  (Integer)doc.get("_id");
            var stock = (Integer)doc.get("stock");
            
            if (id != null && stock != null){
                stockEnBd.put(id,stock);
            }
        }
        return stockEnBd;
        }catch(Exception exe){
            throw new Exception("Algo ha salido mal buscando el stock" + exe.getMessage());
        }
    }
    
}
