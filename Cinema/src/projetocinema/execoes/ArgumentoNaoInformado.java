/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocinema.execoes;

/**
 *
 * @author Luan, Guilherme
 */
public class ArgumentoNaoInformado extends IllegalArgumentException {
    
//Atributos
    String argumento;
    
//Construtores
    public ArgumentoNaoInformado() {
        super ("Argumento não informado");
        argumento = new String();
    }

    /**
     * Mensagem criada => "Campo " + argumento + " não informado".
     * 
     * @param argumento A String referente ao argumento faltante em alguma das
     * telas.
     */
    public ArgumentoNaoInformado(String argumento) {
        super("Campo " + argumento + " não informado");
        this.argumento = argumento;
    }
    
}
