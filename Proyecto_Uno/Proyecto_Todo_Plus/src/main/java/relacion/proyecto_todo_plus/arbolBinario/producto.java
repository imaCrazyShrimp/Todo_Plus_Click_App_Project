package relacion.proyecto_todo_plus.arbolBinario;

public class producto {

    String nombre;
    String descripcion;
    double precio;
    String color;
    String categoría;
    String marca; 

    public producto() {
    }

    public producto(String nombre, String descripcion, double precio, String color, String categoría, String marca) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.color = color;
        this.categoría = categoría;
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "producto{" + "nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio + ", color=" + color + ", categor\u00eda=" + categoría + ", marca=" + marca + '}';
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

    public String getCategoría() {
        return categoría;
    }

    public void setCategoría(String categoría) {
        this.categoría = categoría;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
  
}
