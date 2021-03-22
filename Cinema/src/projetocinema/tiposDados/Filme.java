/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocinema.tiposDados;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Guilherme, Luan
 */
public class Filme implements Comparable<Filme>, Serializable{

//Atributos
    private String titulo, endereco;
    private Genero genero;
    private ArrayList<Ator> atores;
    private Horario duracao;

//Construtores
    /**
     * @param titulo A String referetene ao título.
     * @param genero O objeto Genero que o filme terá.
     * @param hora O inteiro para representar a duração do filme em horas, 
     * precisa ser de 0 a 23.
     * @param minuto O inteiro para representar a duração do filme em minutos, 
     * precisa ser de 0 a 59.
     * @param endereco A String referente ao endereço da imagem de cartaz do 
     * filme.
     */
    public Filme(String titulo, Genero genero, int hora, int minuto,
        String endereco) {
        
        this.duracao = new Horario(hora,minuto);
        this.titulo = titulo;
        this.genero = genero;
        this.atores = new ArrayList<>();
        this.endereco = endereco;
    }

//Getters/Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public ArrayList<Ator> getAtores() {
        return atores;
    }

    public void setAtores(ArrayList<Ator> atores) {
        this.atores = atores;
    }

    public Horario getDuracao() {
        return duracao;
    }

    public void setDuracao(Horario duracao) {
        this.duracao = duracao;
    }
    
    public String getNomeGenero() {
        return genero.getNome();
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

//Metodos
    @Override
    public String toString() {
        return titulo;
    }

    @Override
    public int compareTo(Filme outro) {
        return titulo.compareTo(outro.getTitulo());
    }

}
