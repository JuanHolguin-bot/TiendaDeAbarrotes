/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import enums.RolUser;

public class Usuario {

   
    private final RolUser rol;
    private final String nombre;
    private final String password;

    // Constructor
    public Usuario(int id, RolUser rol, String nombre, String password) {
        
        this.rol = rol;
        this.nombre = nombre;
        this.password = password;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public RolUser getRol() {
        return rol;
    }

    // Método para validar credenciales
    public boolean validarCredenciales(String nombre, String password) {
        return this.nombre.equals(nombre) && this.password.equals(password);
    }

    public void logIn() {
        System.out.println("Usuario " + nombre + " ha iniciado sesión.");
    }
}
