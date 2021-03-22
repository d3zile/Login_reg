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
public class Horario implements Comparable<Horario>, Serializable {
    
//Atributos
    private int hora, minuto;

//Construtores
    
    /**
     * @param hora O inteiro para representar a duração do filme em horas, 
     * precisa ser de 0 a 23.
     * @param minuto O inteiro para representar a duração do filme em minutos, 
     * precisa ser de 0 a 59.
     */
    public Horario(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }
    
//Getters/Setters
    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }
    
//Metodos
    @Override
    public String toString()  {
        String t_hora = Integer.toString(this.hora),
            t_minuto = Integer.toString(this.minuto);
        
        //Retornando no formato hh:mm para ficar padronizado.
        return (1 == t_hora.length() ? "0" + t_hora : t_hora) + ":" +
            (1 == t_minuto.length() ? "0" + t_minuto : t_minuto);
    }

    @Override
    public int compareTo(Horario outro) {
        return ( ( hora - outro.getHora() ) * 60) + minuto - outro.getMinuto();
    }
    
}
