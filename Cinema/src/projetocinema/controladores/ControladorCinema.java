/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocinema.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import projetocinema.tiposDados.Ator;
import projetocinema.tiposDados.Filme;
import projetocinema.tiposDados.Genero;
import projetocinema.tiposDados.Sala;
import projetocinema.tiposDados.Sessao;

/**
 *
 * @author Luan, Guilherme
 */
public abstract class ControladorCinema implements Serializable {

//Atributos
    private ArrayList<Sala> salas;
    private ArrayList<Filme> filmes;
    private ArrayList<Genero> generos;
    private ArrayList<Sessao> sessoes;
    private ArrayList<Ator> atores;

//Construtores
    public ControladorCinema() {
        
       this.salas = new ArrayList<>();
       this.filmes = new ArrayList<>();  
       this.generos = new ArrayList<>();
       this.sessoes = new ArrayList<>();
       this.atores = new ArrayList<>();
    }

//Getters/Setters
    public ArrayList<Sala> getSalas() {
        return salas;
    }

    public void setSalas(ArrayList<Sala> salas) {
        this.salas = salas;
    }

    public ArrayList<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(ArrayList<Filme> filmes) {
        this.filmes = filmes;
    }

    public ArrayList<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(ArrayList<Genero> generos) {
        this.generos = generos;
    }

    public ArrayList<Sessao> getSessoes() {
        return sessoes;
    }

    public void setSessoes(ArrayList<Sessao> sessoes) {
        this.sessoes = sessoes;
    }

    public ArrayList<Ator> getAtores() {
        return atores;
    }

    public void setAtores(ArrayList<Ator> atores) {
        this.atores = atores;
    }  

//Metodos
    
    /**
     * Faz uma busca no ArrayList de salas do controlador para encontrar uma 
     * sala de um determinado número.
     * 
     * @param numero Numero da sala a ser encontrada.
     * @return Um objeto Sala pertencente ao ArrayList do controlador que possui
     * o número buscado, ou nulo, se não encontrar.
     */
    public Sala encontraSala(int numero) {
        for (Sala sala : salas)
            if(sala.getNumero() == numero) return sala;
        
        return null;
    }
    
    /**
     * Faz uma busca no ArrayList de generos do controlador para encontrar um 
     * gênero de um determinado nome.
     * 
     * @param nome A String referente ao nome do gênero.
     * @return Um objeto Genero pertencente ao ArrayList do controlador que 
     * possui o nome buscado, ou nulo, se não encontrar.
     */
    public Genero encontraGenero(String nome) {
        for (Genero genero : generos)
            if(genero.getNome().compareTo(nome) == 0) return genero;

        return null;
    }
    
    /**
     * Faz uma busca no ArrayList de atores do controlador para encontrar um 
     * ator de um determinado nome.
     * 
     * @param nome A String referente ao nome do ator.
     * @return Um objeto Ator pertencente ao ArrayList do controlador que 
     * possui o nome buscado, ou nulo, se não encontrar.
     */
    public Ator encontraAtor(String nome) {
        for (Ator ator : atores)
            if(ator.getNome().compareTo(nome) == 0) return ator;

        return null;
    }
    
    /**
     * Faz uma busca no ArrayList de filmes do controlador para encontrar um 
     * filme de um determinado titulo.
     * 
     * @param titulo A String referente ao título do gênero.
     * @return Um objeto Filme pertencente ao ArrayList do controlador que possui
     * o título buscado, ou nulo, se não encontrar.
     */
    public Filme encontraFilme(String titulo) {
        for (Filme filme : filmes)
            if(filme.getTitulo().compareTo(titulo) == 0) return filme;
        
        return null;
    }
    
    /**
     * Faz uma busca no ArrayList de sessões do controlador para encontrar uma 
     * sessão que foi registrada num sala de número específico, em uma data e 
     * hora específicas.
     * 
     * @param numSala Um inteiro referente ao numero da sala da sessão buscada.
     * @param hora Um inteiro referente a hora da sessão buscada.
     * @param data Uma string referente a data(no padrão mm/dd/aaa) da sessão 
     * buscada.
     * @return Um objeto Sessao pertencente ao ArrayList do controlador que 
     * possui os dados passados, ou nulo, se não encontrar.
     */
    public Sessao encontraSessao(int numSala, int hora, String data) {
        
        for(Sessao sessao : sessoes) {
            
            if( sessao.getData().compareTo(data) == 0 &&
                sessao.getHorario().getHora() == hora &&
                sessao.getSala().getNumero() == numSala  )
                return sessao;
        }
        return null;
    }
}
