/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package relacion.proyecto_todo_plus.arbolBinario;

/**
 *
 * @author USER
 */
public class Nodo {
    protected producto prod;
    protected Nodo izq;
    protected Nodo dcha;

    protected int altura; // para conocer la altura del arbol

    public Nodo(Nodo rma_izq, producto mi_prod, Nodo rma_dcha) {
        this.prod = mi_prod;
        this.izq = rma_izq;
        this.dcha = rma_dcha;
        this.altura = 1;
    }

    public producto getProd() {
        return prod;
    }

    public Nodo getIzq() {
        return izq;
    }

    public Nodo getDcha() {
        return dcha;
    }

    public int getAltura() {
        return altura;
    }

    public void setProd(producto prod) {
        this.prod = prod;
    }

    public void setIzq(Nodo izq) {
        this.izq = izq;
    }

    public void setDcha(Nodo dcha) {
        this.dcha = dcha;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
 
}
