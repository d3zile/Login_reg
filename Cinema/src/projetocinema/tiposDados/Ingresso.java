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
public class Ingresso implements Serializable {
    
    //Atributos
    private boolean meia;
    
    //Construtores
    
    /**
     * @param meia Booleano que representa se o ingresso foi vendido a meia-
     * entrada (true) ou preço integral (false). 
     */
    public Ingresso(boolean meia) {
        this.meia = meia;
    }

    //Getters/Setters
    public boolean isMeia() {
        return meia;
    }

    public void setMeia(boolean meia) {
        this.meia = meia;
    }
    
}
