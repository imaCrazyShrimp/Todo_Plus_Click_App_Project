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
        actualizarAltura(nodo);
        //balancear despues de insertar
        return balancear(nodo);
    }
    

    // Recorridos
    // SE DEBE USAR TXT_AREA Y NO TXT_FIELD PARA IMPRIMIR
    public String getPreorden(Nodo aux) {

        if (aux == null) {
            return "";
        }

        return aux.getProd().toString() + "\n"
                + getPreorden(aux.getIzq())
                + getPreorden(aux.getDcha());
    }

    public String getEnorden(Nodo aux) {

        if (aux == null) {
            return "";
        }

        return getEnorden(aux.getIzq())
                + aux.getProd().toString() + "\n"
                + getEnorden(aux.getDcha());
    }

    public String getPostorden(Nodo aux) {
        if (aux == null) {
            return "";
        }

        return getPostorden(aux.getIzq())
                + getPostorden(aux.getDcha())
                + aux.getProd().toString() + "\n";
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
        if (aux == null) {
            return null;
        }

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
    public void eliminar(int codigo) {
        raiz = eliminarRecursivo(raiz, codigo);
    }

    private Nodo eliminarRecursivo(Nodo nodo, int codigo) {
        if (nodo == null) {
            System.out.println("No se encontró el producto con código: " + codigo);
            return null;
        }

        // 1. Búsqueda normal de BST
        if (codigo < nodo.getProd().getCodigo()) {
            nodo.setIzq(eliminarRecursivo(nodo.getIzq(), codigo));
        } else if (codigo > nodo.getProd().getCodigo()) {
            nodo.setDcha(eliminarRecursivo(nodo.getDcha(), codigo));
        } else {
            if ((nodo.getIzq() == null) || (nodo.getDcha() == null)) {
                Nodo temporal = (nodo.getIzq() != null) ? nodo.getIzq() : nodo.getDcha();

                if (temporal == null) {
                    nodo = null;
                } else {
                    nodo = temporal;
                }
            } else {
                // Buscamos el sucesor inorden 
                Nodo temp = obtenerNodoMinimo(nodo.getDcha());

                // Copiamos los datos del sucesor al nodo actual
                nodo.setProd(temp.getProd());

                // Eliminamos el sucesor inorden
                nodo.setDcha(eliminarRecursivo(nodo.getDcha(), temp.getProd().getCodigo()));
            }
        }

        if (nodo == null) {
            return null;
        }
        actualizarAltura(nodo);
        return balancear(nodo);
    }

// Método para encontrar el sucesor
    private Nodo obtenerNodoMinimo(Nodo nodo) {
        Nodo actual = nodo;
        while (actual.getIzq() != null) {
            actual = actual.getIzq();
        }
        return actual;
    }

    public producto buscarPorCodigo(Nodo nodo, int codigo) {
        if (nodo == null) {
            return null;
        }
        if (nodo.getProd().getCodigo() == codigo) {
            return nodo.getProd();
        }
        if (codigo < nodo.getProd().getCodigo()) {
            return buscarPorCodigo(nodo.getIzq(), codigo);
        }
        return buscarPorCodigo(nodo.getDcha(), codigo);
    }

    // Editar info
    // Método público para ser llamado desde el Main o la Interfaz
    public void editarProducto(int codigo, producto nuevosDatos) {
        raiz = editarRecursivo(raiz, codigo, nuevosDatos);
    }

    private Nodo editarRecursivo(Nodo aux, int codigo, producto nuevosDatos) {
        if (aux == null) {
            System.out.println("Error: No se encontró el ID " + codigo + " para editar.");
            return null;
        }

        // Búsqueda binaria eficiente O(log n)
        if (codigo < aux.getProd().getCodigo()) {
            aux.setIzq(editarRecursivo(aux.getIzq(), codigo, nuevosDatos));
        } else if (codigo > aux.getProd().getCodigo()) {
            aux.setDcha(editarRecursivo(aux.getDcha(), codigo, nuevosDatos));
        } else {
            // ¡LO ENCONTRAMOS! 
            // Actualizamos los atributos del producto existente
            producto pExistente = aux.getProd();

            pExistente.setNombre(nuevosDatos.getNombre());
            pExistente.setDescripcion(nuevosDatos.getDescripcion());
            pExistente.setPrecio(nuevosDatos.getPrecio());
            pExistente.setColor(nuevosDatos.getColor());
            pExistente.setCategoria(nuevosDatos.getCategoria());
            pExistente.setMarca(nuevosDatos.getMarca());

            System.out.println("✅ Nodo con ID " + codigo + " actualizado correctamente en el árbol.");
        }
        return aux;
    }

    // Metodos AVL
    // obtener altura de nodo
    private int getaAlturaNodo(Nodo nodo) { // BORRAR ESTA COMMENTARIO: alura -> getAltura
        if (nodo == null) {
            return 0;
        }
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
        if (nodo == null) {
            return 0;
        }
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
        int estabilidad = calcularFE(nodo);

        // Desbalance a la izquierda (FE > 1)
        if (estabilidad > 1) {
            if (calcularFE(nodo.getIzq()) >= 0) {
                return rotarDerecha(nodo); // Rotación Simple
            } else {
                return rotarIzquierda_Derecha(nodo); // Rotación Doble
            }
        }

        // Desbalance a la derecha (FE < -1)
        if (estabilidad < -1) {
            if (calcularFE(nodo.getDcha()) <= 0) {
                return rotarIzquierda(nodo); // Rotación Simple
            } else {
                return rotarDerecha_Izquierda(nodo); // Rotación Doble
            }
        }

        return nodo;
    }

}
