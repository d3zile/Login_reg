/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocinema.telas;

import javafx.scene.control.Alert;

/**
 *
 * @author Luan, Guilherme
 */
public class TelaErro {
//Metodos
    
    /**
     * Cria uma janela para apresentar o erro da excessão jogada.
     * @param ex A exeção cuja mesnagem será apresentada na janela.
     */
    public static void telaErro(Exception ex) {
        Alert telaErro = new Alert(Alert.AlertType.ERROR);
        telaErro.setResizable(true);
        telaErro.setHeaderText("ERRO");
        telaErro.setContentText(ex.getMessage() );
        telaErro.showAndWait();
    }
}
