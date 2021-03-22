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
public class ValorInvalido extends NumberFormatException {
    
//Metodos
    String campo;
    String valor;
    
//Construtores
    
    /**
     * Mensagem criada => "\"" + valor + "\" é um valor inválido para o campo " 
     * + campo.
     * 
     * @param valor String referente ao valor recebido.
     * @param campo String referente ao nome do campo testado.
     * 
     */
    public ValorInvalido(String valor, String campo) {
        super("\"" + valor + "\" é um valor inválido para o campo " + campo);
        this.valor = valor;
    }
    
}
