package projetocinema.tiposDados;

import java.io.Serializable;
import projetocinema.controladores.ControladorCinema;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Luan, Guilherme
 */
public class Cinema extends ControladorCinema implements Serializable {

//Atributos
    private String nome;
    
//Construtores
    
    /**
     * @param nome A String referente ao nome do cinema.
     */
    public Cinema(String nome) {
        this.nome = nome;
    }

//Getters/Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
