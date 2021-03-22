/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocinema.telas;

import java.util.Collections;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
import projetocinema.tiposDados.Ator;
import projetocinema.tiposDados.Cinema;
import projetocinema.tiposDados.Filme;

/**
 *
 * @author Luan, Guilherme
 */
public class TelaAtores extends Application {
    
//Atributos
    Cinema cinema;
    
    GridPane root;
        Label lNome;
        TextField txNome;
        Button bCadastrar, bEditar, bExcluir;
        ComboBox<Ator> cbCadastros;
       
//Construtores
        
    /**
     * @param cinema O objeto Cinema do próprio gerenciador;
     */
    public TelaAtores(Cinema cinema) {
        this.cinema = cinema;
    }
    
//Metodos
    @Override
    public void init() {
        
        root = new GridPane(); 
        
        lNome = new Label("Nome:");
        txNome = new TextField();
        
        bCadastrar = new Button("Cadastrar");
        bEditar = new Button("Editar");
        bExcluir = new Button("Excluir");
        
        cbCadastros = new ComboBox<>
            ((FXCollections.observableArrayList(cinema.getAtores() )));
        
        bEditar.setDisable(true);
        bEditar.setMaxWidth(Double.MAX_VALUE);
        
        bExcluir.setDisable(true);
        bExcluir.setMaxWidth(Double.MAX_VALUE);
        
        cbCadastros.setMaxWidth(Double.MAX_VALUE);
        
        root.add(lNome, 0, 0);        
        root.add(txNome, 1, 0);
        
        root.add(bCadastrar, 2, 0);
        root.add(bEditar, 2, 1);
        root.add(bExcluir, 2, 2);
        root.add(cbCadastros, 0, 1, 2, 1);
        
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

                String nome = txNome.getText();
                Ator novoAtor;
                
                testaCampos(nome);
                nome = formataNome(nome);
                nome = nomeSemEspacos(nome);
                //Se haver um com o mesmo nome, também não é cadastrada.

                if(encontraAtor(nome) )
                    throw new MesmoDadoCadastrado("Nome");
                
                if(nome.compareTo("Monica Hoeldtke Pietruchinski") == 0)
                    throw new Exception
                    ("Atriz só pode ser cadastrada após o trabalho ser avaliado"
                            + " com nota máxima");
                
                //Crio um novo ator, insiro na lista e depois repasso a 
                //lista para o combo-box.
                novoAtor = new Ator(nome);
                cinema.getAtores().add(novoAtor);
                Collections.sort(cinema.getAtores() );
                cbCadastros.setItems
                    (FXCollections.observableArrayList(cinema.getAtores() ) );

                //No caso de eu cadastrar um ator em cima das informações
                //puxadas de outra.
                if(!cbCadastros.getSelectionModel().isEmpty() ) {

                    cbCadastros.getSelectionModel().clearSelection();
                    bEditar.setDisable(true);
                    bExcluir.setDisable(true);
                }

                txNome.clear();
                }
                catch (ArgumentoNaoInformado ex) {
                    TelaErro.telaErro(ex);
                }
                catch (ValorInvalido ex) {
                    TelaErro.telaErro(ex);
                }
                catch (Exception ex) {
                    TelaErro.telaErro(ex);
                }
            }
        );
        
        //Ação ao selecionar um ator do combo-box, traz seus valores as deter-
        //minadas caixas de entrada para possível edição.
        cbCadastros.getSelectionModel().selectedItemProperty().addListener
            (e -> {
                
                String nomeAtor;
                Ator selecionado;
                
                //Checando se há um ator selecionado para evitar Null Pointer
                //Exception
                if(!cbCadastros.getSelectionModel().isEmpty() ) {
                    nomeAtor = cbCadastros.getSelectionModel().
                        getSelectedItem().getNome();
                    
                    selecionado = cinema.encontraAtor(nomeAtor);
                    txNome.setText(selecionado.getNome() );
                    bEditar.setDisable(false);
                    bExcluir.setDisable(false);
                }
            }
        );
        
        bExcluir.setOnAction
            (e -> {
                
                try {
                    
                    String nomeAtor;
                    Ator selecionado;
                    
                    nomeAtor = cbCadastros.getSelectionModel().
                        getSelectedItem().getNome();
                    
                    selecionado = cinema.encontraAtor(nomeAtor);
                    
                    if(encontraAtorNoFilme(nomeAtor) ) {
                        throw new Exception
                            ("Ator já foi cadastrado num filme existente");
                    }
                
                    cinema.getAtores().remove(selecionado);
                    txNome.clear();
                    cbCadastros.getSelectionModel().clearSelection();
                    Collections.sort(cinema.getAtores() );
                    cbCadastros.setItems
                        (FXCollections.observableArrayList(cinema.getAtores() ));
                    bEditar.setDisable(true);
                    bExcluir.setDisable(true);
                }
                catch(Exception ex){
                    TelaErro.telaErro(ex);
                }
            }
        );
        
        bEditar.setOnAction
            (e -> {
                
                try {
                    
                    String nome = txNome.getText(), nomeAtor;
                    Ator selecionado;

                    testaCampos(nome);
                    nome = formataNome(nome);
                    nome = nomeSemEspacos(nome);
                    
                    nomeAtor = cbCadastros.getSelectionModel().
                        getSelectedItem().getNome();
                    
                    selecionado = cinema.encontraAtor(nomeAtor);
                    
                    //Se existir um ator com o nome passado, o ator não
                    //é editado.
                    if(encontraAtor(nome) )
                        throw new MesmoDadoCadastrado("número");
                    
                    //Se não, o nome é atualizado.
                    selecionado.setNome(nome);
                    Collections.sort(cinema.getAtores() );

                    //Maneira encontrada para atualizar a informação com os 
                    //sets/gets no combo-box.
                    cbCadastros.setItems(null);
                    cbCadastros.setItems
                    (FXCollections.observableArrayList(cinema.getAtores()) );
                    cbCadastros.getSelectionModel().clearSelection();
                    txNome.clear();
                    bEditar.setDisable(true);
                    bExcluir.setDisable(true);
                }
                catch (ArgumentoNaoInformado | ValorInvalido |
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
        
        primaryStage.setTitle("Gerenciador de Atores");
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
     * Verifica no ArrayList membro se existe um ator de um determinado nome.
     * @param nome O nome cuja existencia será checada na lista
     * membra "atores".
     * 
     * @return Retorna um booleano, se o ator existe, retorna true, se não, falso.
     */
    public boolean encontraAtor(String nome) {
        for (Ator ator : cinema.getAtores() )
            if(ator.getNome().compareTo(nome) == 0) return true;
        
        return false;
    }
    
    
    /**
     * Formata a String referente ao nome do ator, colocando a primeira letra do
     * nome e dos sobrenomes em maiúsculo e o restando em minúsculo.
     * @param nome A String contendo o nome do ator.
     * @return Retorna a string do nome formatado.
     */
    public String formataNome(String nome) {
        
        boolean maiuscula = true;
        
        for (int i = 0; i < nome.length(); i++) {
            
            if(Character.isWhitespace(nome.charAt(i)) ) maiuscula = true;
            else if(maiuscula) {
                
                 nome = nome.substring(0, i) +
                    nome.substring(i, i + 1).toUpperCase() +
                    nome.substring(i + 1);
                 maiuscula = false;
            }
            else nome = nome.substring(0, i) +
                    nome.substring(i, i + 1).toLowerCase() +
                    nome.substring(i + 1);
        }
        return nome;
    }
    
    public String nomeSemEspacos(String nome) throws ArgumentoNaoInformado {
        
        //Tratamento para se haver espaços no começo da String.
        if(Character.isWhitespace(nome.charAt(0) )) {
            
            for (int i = 0; i < nome.length(); i++) {
                if(Character.isWhitespace(nome.charAt(0) ))
                    nome = nome.substring(i + 1);
                else break;
            }
        }
        //Se depois de retirado todos os espaços, a String ficar vazia, lanço
        //uma exeção.
        if(nome.isEmpty()) throw new ArgumentoNaoInformado("Nome");
        
        for (int i = 0; i < nome.length(); i++) {
            
            if(Character.isWhitespace(nome.charAt(i) )) {
                
                if(i == nome.length() - 1) nome = nome.substring(0, i);
                else if(Character.isWhitespace(nome.charAt(i + 1) )) {
                    nome = nome.substring(0, i) + nome.substring(i + 1);
                    i--;
                }
            }
        }
        return nome;
    }
    
    /**
     * Testa o campo txNome para verificar se esta vazio antes de cadastrar 
     * ou editar.
     * 
     * @param nome A string presente no campo Nome..
     * @throws ArgumentoNaoInformado
     * @throws ValorInvalido 
     */
    public void testaCampos(String nome) throws ArgumentoNaoInformado {
        
        char letra;
        
        //Se um dos campos estiver vazio, o ator não é cadastrado.
        if(nome.isEmpty() )
            throw new ArgumentoNaoInformado("Nome");
        
        //Se algum caracter usado não for uma letra do alfabeto ou um espaço, 
        //retorna-se um erro.
        for (int i = 0; i < nome.length(); i++) {
            letra = nome.charAt(i);
            if(!Character.isAlphabetic(letra)) {
                
                if(!Character.isWhitespace(letra)) {
                    
                    throw new ValorInvalido
                    (Character.toString(letra), "nome");
                }
            }
                
        }
    }
    
    /**
     * 
     * @param nome String referente ao nome do ator a ser buscado na lista de
     * atores interna de cada filme.
     * 
     * @return Um booleano, true se o ator for encontrado, false se não.
     */
    public boolean encontraAtorNoFilme(String nome) {
        
        for(Filme filme : cinema.getFilmes() ) {
            
            for(Ator ator : filme.getAtores() ) {
                if(ator.getNome().compareTo(nome) == 0) return true;
            }
        }
        return false;
    }

}
