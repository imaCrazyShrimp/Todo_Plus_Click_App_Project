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
        raiz = insertarRecursivo(raiz, nuevoProducto);
        return raiz;
    }   
    
    private Nodo insertarRecursivo(Nodo nodo, producto p) {
        // insercion BTS
        if (nodo == null) {
            return new Nodo(null, p, null);
        }

        if (p.getCodigo() < nodo.getProd().getCodigo()) {
            nodo.setIzq(insertarRecursivo(nodo.getIzq(), p));
        } else if (p.getCodigo() > nodo.getProd().getCodigo()) {
            nodo.setDcha(insertarRecursivo(nodo.getDcha(), p));
        } else {
            return nodo;
        }

        //balancear despues de insertar
        return balancear(nodo);
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

    // Eliminar un nodo con AVL
    public void eliminarNodo(int codigo) {
        raiz = eliminarRecursivo(raiz, codigo);

    }

    public Nodo eliminarRecursivo(Nodo nodo, int codigo) {
        if (nodo == null) return null;

        if (codigo < nodo.getProd().getCodigo()) {
            nodo.setIzq(eliminarRecursivo(nodo.getIzq(), codigo));
        } else if (codigo > nodo.getProd().getCodigo()) {
            nodo.setDcha(eliminarRecursivo(nodo.getDcha(), codigo));
        } else {
            if (nodo.getIzq() == null) return nodo.getIzq();
            if (nodo.getDcha() == null) return nodo.getDcha();   
        
            int minCodigo = encontrarMin(nodo.getDcha());
            producto minProducto = buscarPorCodigo(nodo.getDcha(), minCodigo);
            nodo.setProd(minProducto);
            nodo.setDcha(eliminarRecursivo(nodo.getDcha(), minCodigo));
        }

        return balancear(nodo);
    }

    private int encontrarMin(Nodo nodo) {
        int min = nodo.getProd().getCodigo();
        while (nodo.izq != null) {
            min = nodo.getIzq().getProd().getCodigo();
            nodo = nodo.izq;
        }
        return min;
    }

    private producto buscarPorCodigo(Nodo nodo, int codigo) {
        if (nodo == null) return null;
        if (nodo.getProd().getCodigo() == codigo) return nodo.getProd();
        if (codigo < nodo.getProd().getCodigo()) {
            return buscarPorCodigo(nodo.getIzq(), codigo);
        }
        return buscarPorCodigo(nodo.getDcha(), codigo);
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

    // Metodos AVL

    // obtener altura de nodo
    private int getaAlturaNodo(Nodo nodo) { // BORRAR ESTA COMMENTARIO: alura -> getAltura
        if (nodo == null) return 0;
        return nodo.getAltura();
    }

    //actualizar altura en base a los hijos
    private void actualizarAltura(Nodo nodo) {
        int alturaIzq = getaAlturaNodo(nodo.getIzq()); //altura -> getaAltura
        int alturaDcha = getaAlturaNodo(nodo.getDcha());
        if (alturaIzq > alturaDcha) {
            nodo.setAltura(1 + alturaIzq);
        } else {
            nodo.setAltura(1 + alturaDcha);
        }
    }

    // Factor de Estabilidad: izq - dcha
    private int calcularFE(Nodo nodo) {
        if (nodo == null) return 0;
       return getaAlturaNodo(nodo.getIzq()) - getaAlturaNodo(nodo.getDcha());
    }

    // ROTACIONES

    // Simple dcha
    private Nodo rotarDerecha(Nodo y) {
        Nodo x = y.getIzq();
        Nodo T2 = x.getDcha();
        x.setDcha(y);
        y.setIzq(T2);

        actualizarAltura(y);
        actualizarAltura(x);
        
        return x;
    }

    // simple izq
    private Nodo rotarIzquierda(Nodo x) {
        Nodo y = x.getDcha();
        Nodo T2 = y.getIzq();

        y.setIzq(x);
        x.setDcha(T2);

        actualizarAltura(x);
        actualizarAltura(y);

        return y;
    }

    // doble izquierda a derecha
    private Nodo rotarIzquierda_Derecha(Nodo nodo) {
        nodo.setIzq(rotarIzquierda(nodo.getIzq()));
        return rotarDerecha(nodo);
    }

    // doble derecha a izquierda
    private Nodo rotarDerecha_Izquierda(Nodo nodo) {
        nodo.setDcha(rotarDerecha(nodo.getDcha()));
        return rotarIzquierda(nodo);
    }

    // Utilizar FE para determinar tipo de rotacion
    private Nodo balancear(Nodo nodo) {
        actualizarAltura(nodo);
        int estabilidad = calcularFE(nodo);

        // desbalance a la izquierda
        if (estabilidad == 2) {
            if (calcularFE(nodo.getIzq()) >= 0) {
                return rotarDerecha(nodo);
            }
        } else {
            return rotarIzquierda_Derecha(nodo);
        }

        // desbalance a la derecha
        if (estabilidad == -2) {
            if (calcularFE(nodo.getDcha()) <= 0) {
                return rotarIzquierda(nodo);
            } else {
                return rotarDerecha_Izquierda(nodo);
            }
        }

        return nodo;
    }

        
}
