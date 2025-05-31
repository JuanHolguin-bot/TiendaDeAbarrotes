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

    private LoginRepository loginRepository;

    public ServiceLogin() {
        loginRepository = new LoginRepository();
    }

    public boolean ValidateUserAndPassword(String username, String password) throws Exception {
        var login = loginRepository.getLogin(username);

        return login.getPassword().equals(password);
    }

    public boolean registerUser(Login login) throws IOException, Exception {
        return loginRepository.registerUser(login);
    }

    public boolean userExistingInDataBase(String userName) {
        var login = loginRepository.getLogin(userName);

        if (login != null) {
            return login.getUsername().equals(userName);
        }
        return false; 
    }

}
