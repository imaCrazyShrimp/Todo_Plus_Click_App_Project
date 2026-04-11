package relacion.proyecto_todo_plus.arbolBinario;

public class producto {
    int codigo;
    String nombre;
    String descripcion;
    double precio;
    String color;
    String categoria;
    String marca; 

    public producto() {
    }

    public producto(int codigo, String nombre, String descripcion, double precio, String color, String categoria, String marca) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.color = color;
        this.categoria = categoria;
        this.marca = marca;
    }

    @Override
    public String toString() {
        return codigo + "|" + nombre + "|" + descripcion + "|" + precio + "|" + color + "|" + categoria + "|" + marca;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    
}
