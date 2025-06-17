package Entities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



public abstract class Producto {
    private String nombre;
    private int idProducto;
    private String tipoProducto;
    private String proveedor;
    private String fechaVencimiento;
    private double precio;


    public abstract void imprimirInformacionExtra();

    public int getIdProducto() {
        return idProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public Producto() {
    }

    public Producto(String nombre, int idProducto,String tipoProducto, String proveedor, String fechaVencimiento, double precio) {
        this.nombre = nombre;
        this.idProducto = idProducto;
        this.tipoProducto = tipoProducto;
        this.proveedor = proveedor;
        this.fechaVencimiento = fechaVencimiento;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }
    
    
}
