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
public class MesmoDadoCadastrado extends Exception {
 
//Construtores
    public MesmoDadoCadastrado() {
        super ("Item de mesmos dados já cadastrados");
    }

    /**
     * Mensagem criada => "Item de mesmo " + campo + " já cadastrado".
     * 
     * @param campo A String referente ao campo em específico que impediu a 
     * operação.
     */
    public MesmoDadoCadastrado(String campo) {
        super("Item de mesmo " + campo + " já cadastrado");
    }
    
}
