/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tienda;

import java.io.IOException;
import vistas.Login;



/**
 *
 * @author jose_
 */
public class Tienda {
    private static Login loginInstance = null;
    /**
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        if (loginInstance == null) {
            loginInstance = new Login();
            loginInstance.setVisible(true);
        }

    }
    
}

