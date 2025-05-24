/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LoginApp;

import java.io.IOException;

/**
 *
 * @author User
 */
public class ServiceLogin {
    private LogicRepository logicRepository;

    public ServiceLogin() throws IOException {
        logicRepository = new LogicRepository();
    }
    
    public boolean validateUserAndPassword(String username, String password){
        var logins = logicRepository.getLogins();
        
        for(var login : logins){
            if(login.getUsername().equals(username) && login.getPassword().equals(password)){
                return true;
            }
        }
        
        return false;
    }
     
    public boolean registerUser(Login login) throws IOException{
        return logicRepository.registerUser(login);
    }
    
    
}
