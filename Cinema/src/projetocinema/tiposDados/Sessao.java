/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocinema.tiposDados;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Luan, Guilherme
 */
public class Sessao implements Comparable<Sessao>, Serializable {

//Atributos
    private boolean encerrada;
    private float preco;
    private int ingressosVendidos;
    
    private Date data;
    private Sala sala;
    private Filme filme;
    private Horario horario;
    private Ingresso ingressos[];
    
//Construtores
    
    /**
     * @param preco Valor que o ingresso inteiro da sessão terá.
     * @param data String tem que estar formatada no formato mm/dd/aaaa.
     * @param sala O objeto sala onde a sessão irá ocorrer.
     * @param filme O objeto filme que a sessão apresentará.
     * @param hora Inteiro de valor entre 0 e 23.
     * @param minuto Inteiro de valor entre 0 e 59.
     */
    public Sessao(float preco, String data, Sala sala, Filme filme,
        int hora, int minuto) {

        this.horario = new Horario(hora, minuto);
        
        this.ingressosVendidos = 0;
        
        this.preco = preco;
        this.sala = sala;
        this.filme = filme;
        
        this.data = new Date(data);
        this.ingressos = new Ingresso[sala.getCapacidade()];
    }
    
//Getters/Setters
    public boolean isEncerrada() {
        return encerrada;
    }

    public void setEncerrada(boolean encerrada) {
        this.encerrada = encerrada;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public int getIngressosVendidos() {
        return ingressosVendidos;
    }

    public void setIngressosVendidos(int ingressosVendidos) {
        this.ingressosVendidos = ingressosVendidos;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public Ingresso[] getIngressos() {
        return ingressos;
    }

    public void setIngressos(Ingresso[] ingressos) {
        this.ingressos = ingressos;
    }
    
    //Metodo necessário para apresentação da data (tipo Date) no formato 
    //mm/dd/aaaa.
    public String getData() {
        
        String dia, mes;
        
        dia = Integer.toString(data.getDate());
        dia = dia.length() == 1 ? "0" + dia : dia;
        
        mes = Integer.toString((data.getMonth() + 1));
        mes = mes.length() == 1 ? "0" + mes : mes;
        
        return mes + "/" + dia + "/" + (data.getYear() + 1900);
    }

    public void setData(String data) {
        this.data = new Date(data);
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }
    
//Metodos
    public boolean insereIngresso(boolean meia, int assento) {
        if (this.ingressosVendidos < sala.getCapacidade() ) {
            this.ingressos[assento] = new Ingresso(meia);
            this.ingressosVendidos++;
            return true;
        }
        else return false;
    }
    
    public boolean removeIngresso(int assento) {
        if (this.ingressos[assento] != null ) {
            this.ingressos[assento] = null;
            this.ingressosVendidos--;
            return true;
        }
        else return false;
    }

    @Override
    public String toString() {
        return filme.getTitulo() + " - " + getData() + " " + horario +
            (this.isEncerrada() ? " (ENCERRADA)" : " (NÃO ENCERRADA)");
    }

    @Override
    public int compareTo(Sessao outro) {
        return (encerrada ? 0 : 1) - (outro.encerrada ? 0 : 1) + 
            getHorario().compareTo(outro.getHorario());
    }
    
    public void verificaSituacao() {
        
        int diaAtual, mesAtual, anoAtual, horaAtual, minutoAtual;
        Date dataAtual = new Date();
        
        diaAtual = dataAtual.getDate();
        mesAtual = dataAtual.getMonth() + 1;
        anoAtual = dataAtual.getYear() + 1900;
        horaAtual = dataAtual.getHours() - 1;
        minutoAtual = dataAtual.getMinutes();
        
        if((this.data.getYear() + 1900) < anoAtual) this.encerrada = true;
        else if((this.data.getYear() + 1900) > anoAtual) this.encerrada = false;
        else if((this.data.getMonth() + 1)  < mesAtual) this.encerrada = true;
        else if((this.data.getMonth() + 1)  > mesAtual) this.encerrada = false;
        else if(this.data.getDate() < diaAtual) this.encerrada = true;
        else if(this.data.getDate() > diaAtual) this.encerrada = false;
        else if(this.horario.getHora() < horaAtual) this.encerrada = true;
        else if(this.horario.getHora() > horaAtual) this.encerrada = false;
        else if(this.horario.getMinuto() < minutoAtual) this.encerrada = true;
        else if(this.horario.getMinuto() >= minutoAtual) this.encerrada = false;
    }
    
    public ArrayList<Integer> ingressosDisponiveis() {
        
        ArrayList<Integer> ingressoDisponiveis = new ArrayList<>();
        
        for (int i = 0; i < getIngressos().length ; i++) {
            if(getIngressos()[i] == null)
                ingressoDisponiveis.add(i);
        }
        return ingressoDisponiveis;
    }
}
