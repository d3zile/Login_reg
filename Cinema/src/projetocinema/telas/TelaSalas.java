/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocinema.telas;

import java.util.Collections;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import projetocinema.execoes.ArgumentoNaoInformado;
import projetocinema.execoes.MesmoDadoCadastrado;
import projetocinema.execoes.ValorInvalido;
import projetocinema.tiposDados.Cinema;
import projetocinema.tiposDados.Sala;
import projetocinema.tiposDados.Sessao;

/**
 *
 * @author Luan, Guilherme
 */
public class TelaSalas extends Application {
//Atributos
    Cinema cinema;
    
    GridPane root;
        Label lCapacidade, lNumero;
        TextField txCapacidade, txNumero;
        Button bCadastrar, bEditar, bExcluir;
        ComboBox<Sala> cbCadastros;
       
    /**
     * 
     * @param cinema Controlador do sistema.
     */
    public TelaSalas(Cinema cinema) {
        this.cinema = cinema;
    }
    
    @Override
    public void init() {
        
        root = new GridPane();
        
        lNumero = new Label("Numero:");
        txNumero = new TextField();    
        
        lCapacidade = new Label("Capacidade:");
        txCapacidade = new TextField();
        
        bCadastrar = new Button("Cadastrar");
        bEditar = new Button("Editar");
        bExcluir = new Button("Excluir");
        
        cbCadastros = new ComboBox<>
            (FXCollections.observableArrayList(cinema.getSalas() ));
        
        bEditar.setDisable(true);
        bEditar.setMaxWidth(Double.MAX_VALUE);
        
        bExcluir.setDisable(true);
        bExcluir.setMaxWidth(Double.MAX_VALUE);
        
        cbCadastros.setMaxWidth(Double.MAX_VALUE);
        
        root.add(lNumero, 0, 0);
        root.add(lCapacidade, 0, 1);
        
        root.add(txNumero, 1, 0);
        root.add(txCapacidade, 1, 1);
        
        root.add(bCadastrar, 2, 0);
        root.add(bEditar, 2, 1);
        root.add(bExcluir, 2, 2);
        root.add(cbCadastros, 0, 2, 2, 1);
        
        root.setMinSize(400, 300);
        root.setVgap(10);
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);
    }
    
    @Override
    public void start(Stage primaryStage) {

        Scene scene = new Scene(root, 600, 400);
        
        bCadastrar.setOnAction
            (e -> {
                try {

                    int numero, capacidade;
                    String sNumero = txNumero.getText(),
                            sCapacidade = txCapacidade.getText();
                    Sala novaSala;

                    testaCampos(sNumero, sCapacidade);
                    numero = Integer.parseInt(sNumero);
                    capacidade = Integer.parseInt(sCapacidade);
                    //Se haver um sala com o mesmo número, também não é cadastrada.
                    if(cinema.encontraSala(numero) != null)
                        throw new MesmoDadoCadastrado("número");
                    
                    //Crio uma nova sala, insiro na lista e depois repasso a
                    //lista para o combo-box.
                    novaSala = new Sala(numero,capacidade);
                    cinema.getSalas().add(novaSala);
                    Collections.sort(cinema.getSalas());
                    cbCadastros.setItems
                                (FXCollections.observableArrayList(cinema.getSalas() ));
                    
                    //No caso de eu cadastrar uma sala em cima das informações
                    //puxadas de outra.
                    if(!cbCadastros.getSelectionModel().isEmpty() ) {

                        bEditar.setDisable(true);
                        bExcluir.setDisable(true);
                    }

                    limpaEntradas();
                }
                catch (ArgumentoNaoInformado | NumberFormatException |
                    MesmoDadoCadastrado ex) {
                    
                    TelaErro.telaErro(ex);
                }
            } );
        
        //Ação ao selecionar uma sala do combo-box, traz seus valores as deter-
        //minadas caixas de entrada para possível edição.
        cbCadastros.getSelectionModel().selectedItemProperty().addListener
            (e -> {
                
                int numeroSala;
                Sala selecionada;
                
                //Checando se há uma sala selecionada para evitar Null Pointer
                //Exception
                if(!cbCadastros.getSelectionModel().isEmpty() ) {
                    numeroSala = cbCadastros.getSelectionModel().
                        getSelectedItem().getNumero();
                    selecionada = cinema.encontraSala(numeroSala);
                    txCapacidade.setText(Integer.
                        toString(selecionada.getCapacidade() ));
                    txNumero.setText(Integer.
                        toString(selecionada.getNumero() ));
                    bEditar.setDisable(false);
                    bExcluir.setDisable(false);
                }
            }
        );
        
        //Retiro o item do ArrayList pelo indice encontrado pelo combo-box, 
        //depois repasso a lista novamente;
        bExcluir.setOnAction
            (e -> {
                try {
                    
                    int numeroSala;
                    Sala selecionada;
                    
                    numeroSala = cbCadastros.getSelectionModel().
                        getSelectedItem().getNumero();
                    selecionada = cinema.encontraSala(numeroSala);
                
                    if(verificaSalaDeSessao(numeroSala) )
                        throw new Exception
                        ("Sala pertencente a uma sessão cadastrada");

                    cinema.getSalas().remove(selecionada);
                    Collections.sort(cinema.getSalas());
                    cbCadastros.setItems(FXCollections.observableArrayList
                        (cinema.getSalas() ));
                    bEditar.setDisable(true);
                    bExcluir.setDisable(true);
                    limpaEntradas();
                }
                catch(Exception ex) {
                    TelaErro.telaErro(ex);
                }
            }
        );
        
        bEditar.setOnAction
            (e -> {
                try {
                    
                    int capacidade, numero, numeroSala;
                    String sCapacidade, sNumeroSala;
                    Sala selecionada;
                    
                    sCapacidade = txCapacidade.getText();
                    sNumeroSala = txNumero.getText();
                    
                    testaCampos(sCapacidade, sNumeroSala);
                    
                    capacidade = Integer.parseInt(txCapacidade.getText() );
                    numero = Integer.parseInt(txNumero.getText() );
                    
                    numeroSala = cbCadastros.getSelectionModel().
                        getSelectedItem().getNumero();
                    selecionada = cinema.encontraSala(numeroSala);
                    
                    //Caso onde o numero da sala não vai ser editado.
                    if(numero != selecionada.getNumero() &&
                        cinema.encontraSala(numero) != null)
                        throw new MesmoDadoCadastrado("número");
                    //Se existir uma sala com o numero passado, a sala não
                    //é editada.
                    selecionada.setNumero(numero);
                    selecionada.setCapacidade(capacidade);

                    Collections.sort(cinema.getSalas());
                    
                    //Maneira encontrada para atualizar a informação com os 
                    //sets/gets no combo-box
                    cbCadastros.setItems(null);
                    cbCadastros.setItems
                    (FXCollections.observableArrayList(cinema.getSalas() ));
                    limpaEntradas();
                    bEditar.setDisable(true);
                    bExcluir.setDisable(true);
                }
                catch (ArgumentoNaoInformado | NumberFormatException |
                    MesmoDadoCadastrado ex) {
                    TelaErro.telaErro(ex);
                }
            }
        );
        
        primaryStage.addEventHandler
        (KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode() )
                primaryStage.close();
        });
        
        primaryStage.setTitle("Gerenciador de Salas");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Limpa as entradas txCapacidade e txNumero.
     */
    public void limpaEntradas() {
        txCapacidade.clear();
        txNumero.clear();
        cbCadastros.getSelectionModel().clearSelection();
    }
    
    /**
     * Testa os dois campos para verificar se estão vazios ou com dados 
     * inválidos antes de cadastrar ou editar.
     * 
     * @param numero A string presente no campo Número.
     * @param capacidade A string presente no campo Capacidade.
     * @throws ArgumentoNaoInformado
     * @throws ValorInvalido 
     */
    public void testaCampos(String numero, String capacidade)
        throws ArgumentoNaoInformado, ValorInvalido {
        
        int inteiroTeste;
        
        //Se um dos campos estiver vazio, a sala não é cadastrada.
        if(numero.isEmpty())
            throw new ArgumentoNaoInformado("Numero");
        if(capacidade.isEmpty())
            throw new ArgumentoNaoInformado("Capacidade");

        //Testando se as entradas foram numeros inteiros.
        try {
            inteiroTeste = Integer.parseInt(numero);
        }
        catch (NumberFormatException ex){
            throw new ValorInvalido(numero, "Numero");
        }
        try {
            inteiroTeste = Integer.parseInt(capacidade);
        }
        catch (NumberFormatException ex){
            throw new ValorInvalido(capacidade, "Capacidade");
        }
    }
    
    /**
     * Checa a existencia de uma sala em uma das sessoes cadastradas, se 
     * existir, a sala não pode ser removida.
     * 
     * @param numero O numero da sala a ser buscada.
     * @return Um booleano, true se a sala foi encontrada em uma sessão, false
     * se não.
     */
    public boolean verificaSalaDeSessao(int numero) {
        
        for(Sessao sessao : cinema.getSessoes() ) {

            if(numero == sessao.getSala().getNumero() )
                return true;
        }
        
        return false;
    }
}
