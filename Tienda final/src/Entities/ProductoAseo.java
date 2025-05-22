package Entities;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


public class ProductoAseo extends Producto {
    private String[] advertencias;

    @Override
    public void imprimirInformacionExtra() {
        System.out.println("Advertencias: " + String.join(", ", advertencias));
    }

    public ProductoAseo() {
    }

    public ProductoAseo( String nombre, int idProducto, String tipoProducto, String proveedor, String fechaVencimiento, double precio) {
        super(nombre, idProducto,tipoProducto, proveedor, fechaVencimiento, precio);
 
    }

    public String[] getAdvertencias() {
        return advertencias;
    }

    public void setAdvertencias(String[] advertencias) {
        this.advertencias = advertencias;
    }
}