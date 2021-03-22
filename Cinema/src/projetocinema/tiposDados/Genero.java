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
public class Genero implements Comparable<Genero>, Serializable{

//Atributos
    private String nome;

//Construtores
    
    /**
     * @param nome A String referente ao nome do gênero.
     */
    public Genero(String nome) {    
        this.nome = nome;
    }
//Getters/Setters
    public String getNome() {
        return this.nome;
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
    public int compareTo(Genero outro) {
        return nome.compareTo(outro.getNome() );
    }
}
