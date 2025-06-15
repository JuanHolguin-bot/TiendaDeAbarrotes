package Repositorios;


import Entities.Producto;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class GestorFactura {

    private MongoDatabase dataBase;
    private MongoCollection<Document> ventasCollection;

    public GestorFactura() {
        var cliente = MongoClients.create("mongodb+srv://juanholguin3:1035974679@cluster0.lmisdxx.mongodb.net/");
        dataBase = cliente.getDatabase("TiendaDeAbarrotes");
        ventasCollection = dataBase.getCollection("Ventas");
    }

    // Genera el siguiente número de factura (F1, F2, ...)
    public String generarSiguienteNumeroFactura() {
        Document lastVenta = ventasCollection.find()
                .sort(new Document("numeroFactura", -1))
                .limit(1)
                .first();

        int nextFacturaNumber = 1;
        if (lastVenta != null && lastVenta.containsKey("numeroFactura")) {
            String lastFactura = lastVenta.getString("numeroFactura");
            try {
                int lastNumber = Integer.parseInt(lastFactura.substring(1));
                nextFacturaNumber = lastNumber + 1;
            } catch (Exception e) {
                // Si hay error, deja el número en 1
            }
        }
        return "F" + nextFacturaNumber;
    }

    // Guarda una factura completa (una venta con todos sus productos)
    public void guardarFactura(String numeroFactura, String cliente, List<Document> productos, double totalVenta, String vendedor) {
        Document factura = new Document("numeroFactura", numeroFactura)
                .append("cliente", cliente)
                .append("productos", productos)
                .append("totalVenta", totalVenta)
                .append("fecha", java.time.LocalDateTime.now().toString())
                .append("Vendedor", vendedor);
        ventasCollection.insertOne(factura);
    }

    // Carga todas las facturas de la base de datos
    public List<Document> cargarFacturasDesdeBd() throws Exception {
        List<Document> facturasEnBd = new ArrayList<>();
        try {
            for (Document doc : ventasCollection.find()) {
                facturasEnBd.add(doc);
            }
        } catch (Exception exe) {
            throw new Exception("No hay facturas registradas aún: " + exe.getMessage());
        }
        return facturasEnBd;
    }
    
    public List<Document> productosToDocumentos(List<Producto> productos) {
    List<Document> documentos = new ArrayList<>();
    for (Producto producto : productos) {
        Document doc = new Document("idProducto", producto.getIdProducto())
            .append("nombre", producto.getNombre())
            .append("tipo producto", producto.getTipoProducto())
            .append("proveedor", producto.getProveedor())
            .append("fecha vencimiento", producto.getFechaVencimiento())
            .append("precio", producto.getPrecio());
        // Agrega aquí otros campos si tu clase Producto los tiene
        documentos.add(doc);
    }
    return documentos;
}
}
