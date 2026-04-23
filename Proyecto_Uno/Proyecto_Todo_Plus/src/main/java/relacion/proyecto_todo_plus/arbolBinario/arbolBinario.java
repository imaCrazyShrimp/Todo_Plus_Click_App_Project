package relacion.proyecto_todo_plus.arbolBinario;

public class arbolBinario {

    protected Nodo raiz;

    public arbolBinario() {
        this.raiz = null;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public Nodo insertar(producto nuevoProducto) {
        raiz = insertarRecursivo(raiz, nuevoProducto);
        return raiz;
    }

    private Nodo insertarRecursivo(Nodo nodo, producto p) {
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
        return balancear(nodo);
    }
    

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

        Nodo encontrado = buscarNodo(aux.getIzq(), producto);
        if (encontrado == null) {
            encontrado = buscarNodo(aux.getDcha(), producto);
        }
        return encontrado;
    }

    public void eliminar(int codigo) {
        raiz = eliminarRecursivo(raiz, codigo);
    }

    private Nodo eliminarRecursivo(Nodo nodo, int codigo) {
        if (nodo == null) {
            System.out.println("No se encontró el producto con código: " + codigo);
            return null;
        }

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
                Nodo temp = obtenerNodoMinimo(nodo.getDcha());

                nodo.setProd(temp.getProd());

                nodo.setDcha(eliminarRecursivo(nodo.getDcha(), temp.getProd().getCodigo()));
            }
        }

        if (nodo == null) {
            return null;
        }
        actualizarAltura(nodo);
        return balancear(nodo);
    }

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

    public void editarProducto(int codigo, producto nuevosDatos) {
        raiz = editarRecursivo(raiz, codigo, nuevosDatos);
    }

    private Nodo editarRecursivo(Nodo aux, int codigo, producto nuevosDatos) {
        if (aux == null) {
            System.out.println("Error: No se encontró el ID " + codigo + " para editar.");
            return null;
        }

        if (codigo < aux.getProd().getCodigo()) {
            aux.setIzq(editarRecursivo(aux.getIzq(), codigo, nuevosDatos));
        } else if (codigo > aux.getProd().getCodigo()) {
            aux.setDcha(editarRecursivo(aux.getDcha(), codigo, nuevosDatos));
        } else {
            producto pExistente = aux.getProd();

            pExistente.setNombre(nuevosDatos.getNombre());
            pExistente.setDescripcion(nuevosDatos.getDescripcion());
            pExistente.setPrecio(nuevosDatos.getPrecio());
            pExistente.setColor(nuevosDatos.getColor());
            pExistente.setCategoria(nuevosDatos.getCategoria());
            pExistente.setMarca(nuevosDatos.getMarca());

            System.out.println("Nodo con ID " + codigo + " actualizado correctamente en el árbol.");
        }
        return aux;
    }

    private int getaAlturaNodo(Nodo nodo) { 
        if (nodo == null) {
            return 0;
        }
        return nodo.getAltura();
    }

    private void actualizarAltura(Nodo nodo) {
        int alturaIzq = getaAlturaNodo(nodo.getIzq()); 
        int alturaDcha = getaAlturaNodo(nodo.getDcha());
        if (alturaIzq > alturaDcha) {
            nodo.setAltura(1 + alturaIzq);
        } else {
            nodo.setAltura(1 + alturaDcha);
        }
    }

    private int calcularFE(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return getaAlturaNodo(nodo.getIzq()) - getaAlturaNodo(nodo.getDcha());
    }

    private Nodo rotarDerecha(Nodo y) {
        Nodo x = y.getIzq();
        Nodo T2 = x.getDcha();
        x.setDcha(y);
        y.setIzq(T2);

        actualizarAltura(y);
        actualizarAltura(x);

        return x;
    }

    private Nodo rotarIzquierda(Nodo x) {
        Nodo y = x.getDcha();
        Nodo T2 = y.getIzq();

        y.setIzq(x);
        x.setDcha(T2);

        actualizarAltura(x);
        actualizarAltura(y);

        return y;
    }

    private Nodo rotarIzquierda_Derecha(Nodo nodo) {
        nodo.setIzq(rotarIzquierda(nodo.getIzq()));
        return rotarDerecha(nodo);
    }

    private Nodo rotarDerecha_Izquierda(Nodo nodo) {
        nodo.setDcha(rotarDerecha(nodo.getDcha()));
        return rotarIzquierda(nodo);
    }

    private Nodo balancear(Nodo nodo) {
        int estabilidad = calcularFE(nodo);

        if (estabilidad > 1) {
            if (calcularFE(nodo.getIzq()) >= 0) {
                return rotarDerecha(nodo); 
            } else {
                return rotarIzquierda_Derecha(nodo);
            }
        }

        if (estabilidad < -1) {
            if (calcularFE(nodo.getDcha()) <= 0) {
                return rotarIzquierda(nodo); 
            } else {
                return rotarDerecha_Izquierda(nodo); 
            }
        }

        return nodo;
    }

}
