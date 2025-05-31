/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LoginApp;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import org.bson.Document;

/**
 *
 * @author User
 */
public class LoginRepository {

    private final MongoDatabase dataBase;
    private final  MongoCollection<Document> loginCollection;

    public LoginRepository() {
        var cliente = MongoClients.create("mongodb+srv://juanholguin3:1035974679@cluster0.lmisdxx.mongodb.net/");
        dataBase = cliente.getDatabase("TiendaDeAbarrotes");
        loginCollection = dataBase.getCollection("Usuarios");
    }
    
    public ArrayList<Login> getLogins() throws Exception{
        var loginDocuments = loginCollection.find();
        ArrayList<Login> logins = new ArrayList<>();
        try{
        for (Document doc : loginDocuments){
            var username = (String)doc.get("username"); 
            var password = (String)doc.get("password");
            
            var login = new Login(username,password);
            logins.add(login);
        }
        return logins;
        }catch(Exception exe){
            throw new Exception("Algo ha salido mal buscando los Logins" + exe.getMessage());
        }
    }
    
    public Login getLogin(String username){
        Document docLogin = loginCollection.find(Filters.eq("username",username)).first();
        return docLogin != null ? Login.desdeDocumento(docLogin) : null; 
    }
    
    
    public boolean registerUser(Login login){
        try{
        loginCollection.insertOne(login.toDocument());
        return true;
        }catch(Exception exe){
            System.out.println("Error : " + exe.getMessage());
            return false;
        }        
    }
    
}
