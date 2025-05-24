/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LoginApp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class LogicRepository {
    private final File file;
    private final FileWriter fileWriter;
    public static final String RUTA = "C:\\Users\\User\\OneDrive\\Documents\\GitHub\\TiendaDeAbarrotes\\Login.txt";

    public LogicRepository() throws IOException {
        file = new File(RUTA);
        fileWriter = new FileWriter(RUTA);
    }
    
    public List<Login> getLogins(){
        Scanner s = null;
        List<Login> logins = new ArrayList<>();
        
        try{
            System.out.println("Reading Login File...");
            s = new Scanner(file);
            
            while(s.hasNextLine()){
                String loginLine = s.nextLine();
                var login = new Login(loginLine.split("\\|")[0],loginLine.split("\\|")[1]);
                logins.add(login);
                System.out.println("Login obteined: " + login.getUsername());
            }
            
            return logins;
        }catch(Exception exe){
            return new ArrayList<>();
        }
    }
    
    public boolean registerUser(Login login) throws IOException{
        var logins = this.getLogins();
        logins.add(login);    
        
        for(Login loginTemp : logins){
            fileWriter.write(login.getUsername()+"|"+loginTemp.getPassword()+"\n");
        }
        
        return true; 
        
    }
      
}
