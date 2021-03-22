/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocinema.tiposDados;

import java.io.Serializable;

/**
 *
 * @author Luan, Guilherme
 */
public class Ator implements Comparable<Ator>, Serializable {
        
//Atributos
    private String nome;

//Construtores
    public Ator(String nome) {
        this.nome = nome;
    }

//Getters/Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
//Metodos
    @Override
    public String toString() {
        return nome;
    }

    @Override
    public int compareTo(Ator outro) {
        return this.nome.compareTo(outro.nome);
    }
    
}
