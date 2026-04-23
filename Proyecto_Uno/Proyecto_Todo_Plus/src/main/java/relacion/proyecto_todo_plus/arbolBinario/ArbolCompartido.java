package relacion.proyecto_todo_plus.arbolBinario;

public class ArbolCompartido {

    private static arbolBinario instancia = null;

    private ArbolCompartido() {}

    public static arbolBinario getArbol() {
        if (instancia == null) {
            instancia = new arbolBinario();
        }
        return instancia;
    }

    public static void reiniciar() {
        instancia = new arbolBinario();
    }
}