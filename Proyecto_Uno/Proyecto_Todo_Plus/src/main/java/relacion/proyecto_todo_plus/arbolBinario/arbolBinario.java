package relacion.proyecto_todo_plus.arbolBinario;

public class arbolBinario {
    protected Nodo raiz;

    // constructor
    public arbolBinario() {
        this.raiz = null;
    }
     
    public Nodo getRaiz() {
        return raiz;
    }

    // metodo de insercion
    public Nodo insertar(producto nuevoProducto) {
        Nodo nuevoNodo = new Nodo(null, nuevoProducto, null );
        
        if (raiz == null) {
            raiz = nuevoNodo;
            return raiz;
        }

        Nodo actual = raiz; // comezamos recorrido desde la raiz

        while (true) {

            // viajamos por la izquierda
            if (nuevoNodo.getProd().getCodigo() < actual.getProd().getCodigo()) {
                
                if (actual.getIzq() == null) {
                    actual.setIzq(nuevoNodo);
                    return nuevoNodo;
                }

                actual = actual.getIzq();
            } else if (nuevoNodo.getProd().getCodigo() > actual.getProd().getCodigo()) { // seguimos por la dcha

                if (actual.getDcha() == null) {
                    actual.setDcha(nuevoNodo);
                    return nuevoNodo;
                }

                actual = actual.getDcha();
            } else {
                return null; //producto cono ese id ya existe!
            }
        }
    }    

    // Recorridos
    
    // SE DEBE USAR TXT_AREA Y NO TXT_FIELD PARA IMPRIMIR
    public String getPreorden(Nodo aux) {
        
        if (aux == null) return "";

        return aux.getProd().toString() + "\n" + 
                getPreorden(aux.getIzq()) + 
                getPreorden(aux.getDcha());
    }

    public String getEnorden(Nodo aux) {
        
        if (aux == null) return "";

        return getEnorden(aux.getIzq()) +
                aux.getProd().toString() + "\n" +
                getEnorden(aux.getDcha());
    }

    public String getPostorden(Nodo aux) {
        if (aux == null) return "";

        return getPostorden(aux.getIzq()) +
                getPostorden(aux.getDcha()) +
                aux.getProd().toString() + "\n";
    }

    // Operación de búsqueda
    public producto buscaProducto(Nodo aux, String nombreProd) {
        Nodo resultado = buscarNodo(aux, nombreProd);

        if (resultado == null) {
            return null;
        }

        return resultado.getProd();
    }

    protected Nodo buscarNodo(Nodo aux, String producto) {
        if (aux == null) return null;

        producto p = aux.getProd();
        if (p.getNombre().equalsIgnoreCase(producto)) {
            return aux;
        }

        // buscar hacia la izq del arbol    
        Nodo encontrado = buscarNodo(aux.getIzq(), producto);
        if (encontrado == null) {
            // buscar hacia la dcha del arbol
            encontrado = buscarNodo(aux.getDcha(), producto);
        }
        return encontrado;
    }

    // Eliminar un Nodo
    public Nodo eliminarNodo(Nodo aux, int codigo) {
        if (raiz == null) return null;

        if (codigo < raiz.getProd().getCodigo()) {
            raiz.izq = eliminarNodo(raiz.getIzq(), codigo);
        } else if (codigo > raiz.getProd().getCodigo()) {
            raiz.dcha = eliminarNodo(raiz.getDcha(), codigo);
        } else {
            if (raiz.izq == null) return raiz.getIzq();
            if (raiz.dcha == null) return raiz.getIzq();

            raiz.prod.codigo = encontrarMin(raiz.getDcha());
            raiz.dcha = eliminarNodo(raiz.dcha, raiz.getProd().getCodigo());
        }

        return raiz;

    }

    private int encontrarMin(Nodo nodo) {
        int min = nodo.getProd().getCodigo();
        while (nodo.izq != null) {
            min = nodo.getIzq().getProd().getCodigo();
            nodo = nodo.izq;
        }
        return min;
    }

    // Editar info
    public Nodo editarProducto(int codigo, producto mi_Producto, Nodo aux) {
        if (aux == null) return null;

        producto p = aux.getProd();
        if (p.getCodigo() == codigo) {
            aux.getProd().setNombre(mi_Producto.getNombre());
            aux.getProd().setDescripcion(mi_Producto.getDescripcion());
            aux.getProd().setPrecio(mi_Producto.getPrecio());
            aux.getProd().setColor(mi_Producto.getColor());
            aux.getProd().setCategoria(mi_Producto.getCategoria());
            aux.getProd().setMarca(mi_Producto.getMarca());

            System.out.println("Cambio realizado");
            return aux;
        }

        Nodo encontrado = editarProducto(codigo, mi_Producto, aux.getIzq());
        if (encontrado == null) {
            encontrado = editarProducto(codigo, mi_Producto, aux.getDcha());
        }

        return encontrado;
    }
}
