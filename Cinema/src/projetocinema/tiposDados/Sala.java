/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocinema.tiposDados;

import java.io.Serializable;

/**
 *
 * @author Guilherme, Luan
 */
public class Sala implements Comparable<Sala>, Serializable {
//Atributos
    private int numero;
    private int capacidade;
    
    
//Construtores
    public Sala(int numero, int capacidade) {
        this.numero = numero;
        this.capacidade = capacidade;
    }
    
//Getters/Setters
    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public int compareTo(Sala outro) {
        return numero - outro.getNumero();
    }

    @Override
    public String toString() {
       return numero < 10 ?  "Sala 0" + numero + " (" + capacidade + ")"
                : "Sala " + numero + " (" + capacidade + ")";
    }
}