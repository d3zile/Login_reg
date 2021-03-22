/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocinema.telas;

import java.util.Collections;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
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
import projetocinema.tiposDados.Filme;
import projetocinema.tiposDados.Genero;

/**
 *
 * @author Guilherme, Luan
 */
public class TelaGeneros extends Application {
    
    Cinema cinema;
    GridPane root;
    Label lGenero;
    TextField tGenero;
    Button bCadastrar, bExcluir, bAlterar;
    ComboBox<Genero> cbCadastros;
    
    /** 
     * 
     * @param cinema O objeto Cinema referente ao próprio controlador.
     */
    public TelaGeneros(Cinema cinema) {
        this.cinema = cinema;
    }
    
    @Override
    public void init(){ 
        root = new GridPane();
        
        lGenero = new Label("Nome:");
        tGenero = new TextField();
        bCadastrar = new Button("Cadastrar");
        cbCadastros = new ComboBox<>(FXCollections.observableArrayList
            (cinema.getGeneros() ));
        bAlterar = new Button("Editar");
        bExcluir = new Button("Excluir");
    
        
        bAlterar.setDisable(true);
        bAlterar.setMaxWidth(Double.MAX_VALUE);
        
        bExcluir.setDisable(true);
        bExcluir.setMaxWidth(Double.MAX_VALUE);
        
        cbCadastros.setMaxWidth(Double.MAX_VALUE);
        
        root.add(lGenero, 0, 0);
        root.add(tGenero, 1, 0);
        root.add(bCadastrar, 2, 0);
        root.add(cbCadastros, 0, 1, 2, 1);
        root.add(bAlterar, 2, 1);
        root.add(bExcluir, 2, 2);
        
        root.setMinSize(400, 300);
        root.setVgap(10);
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
    }  
    
    @Override
    public void start(Stage primaryStage) {
        
        Scene scene = new Scene(root, 600, 400);
        
        bCadastrar.setOnAction
            (e -> {
                try {
                    
                    String sNome = tGenero.getText();

                    Genero novoGenero;

                    //Uma verificação se foi prenchido como devia, se não, 
                    //retorna erro.
                    testaCampos(sNome);
                    //Deixa padronizado com começo da palavra ou nome maiusculo 
                    //e o resto minusculo
                    sNome = formataNome(sNome);
                    //Retira os espaços abusivos.
                    sNome = nomeSemEspacos(sNome);

                    //Se haver um com o mesmo nome, também não é cadastrada.
                    if(encontraGenero(sNome) )
                        throw new MesmoDadoCadastrado("Nome");

                    //Crio um novo genero, insiro na lista e depois repasso a 
                    //lista para o combo-box.
                    novoGenero = new Genero(sNome);
                    cinema.getGeneros().add(novoGenero);
                    Collections.sort(cinema.getGeneros() );
                    cbCadastros.setItems
                        (FXCollections.observableArrayList(cinema.getGeneros() ));

                    //No caso de eu cadastrar um genero em cima das informações
                    //puxadas de outra.
                    if(!cbCadastros.getSelectionModel().isEmpty() ) {

                        cbCadastros.getSelectionModel().clearSelection();
                        bAlterar.setDisable(true);
                        bExcluir.setDisable(true);
                    }

                    tGenero.clear();
                }
                catch (ArgumentoNaoInformado | ValorInvalido |
                    MesmoDadoCadastrado ex) {
                    
                    TelaErro.telaErro(ex);
                }
            } );
        
        //Ação ao selecionar um genero do combo-box, traz seus valores as deter-
        //minadas caixas de entrada para possível edição.
        cbCadastros.getSelectionModel().selectedItemProperty().addListener
            (e -> {
                
                String nomeGenero;
                Genero selecionado;
                
                //Checando se há um genero selecionado para evitar Null Pointer
                //Exception
                if(!cbCadastros.getSelectionModel().isEmpty() ) {
                    
                    nomeGenero =
                        cbCadastros.getSelectionModel().getSelectedItem().getNome();
                    selecionado = cinema.encontraGenero(nomeGenero);
                        
                    tGenero.setText(selecionado.getNome() );
                    bAlterar.setDisable(false);
                    bExcluir.setDisable(false);
                }
            }
        );
        
        //Retiro o item do ArrayList pelo indice encontrado pelo combo-box, 
        //depois repasso a lista novamente;
        bExcluir.setOnAction
            (e -> {
                try {
                    
                    String nomeGenero;
                    Genero selecionado;

                    nomeGenero =
                        cbCadastros.getSelectionModel().getSelectedItem().
                            getNome();
                    selecionado = cinema.encontraGenero(nomeGenero);
                
                    if(encontraGeneroDeFilme(nomeGenero) )
                        throw new Exception("Gênero já cadastrado em um filme");
                    
                    cinema.getGeneros().remove(selecionado);
                    tGenero.clear();
                    cbCadastros.getSelectionModel().clearSelection();
                    Collections.sort(cinema.getGeneros() );
                    cbCadastros.setItems(FXCollections.observableArrayList
                        (cinema.getGeneros() ));
                    bAlterar.setDisable(true);
                    bExcluir.setDisable(true);
                }
                catch(Exception ex) {
                    TelaErro.telaErro(ex);
                } 
            }
        );
        
        bAlterar.setOnAction
            (e -> {
                try {
                    
                    String nomeGenero, nome;
                    Genero selecionado;

                    nomeGenero =
                        cbCadastros.getSelectionModel().getSelectedItem().
                            getNome();
                    selecionado = cinema.encontraGenero(nomeGenero);
                    
                    nome = tGenero.getText();

                    testaCampos(nome);
                    nome = formataNome(nome);
                    nome = nomeSemEspacos(nome);
                    
                    //Se existir um genero com o nome passado, o ator não
                    //é editado.
                    if(encontraGenero(nome) )
                        throw new MesmoDadoCadastrado("Nome");
                    
                    //Se não, o nome é atualizado.
                    selecionado.setNome(nome);
                    Collections.sort(cinema.getGeneros() );

                    //Maneira encontrada para atualizar a informação com os 
                    //sets/gets no combo-box.
                    cbCadastros.setItems(null);
                    cbCadastros.setItems
                    (FXCollections.observableArrayList(cinema.getGeneros() ));
                    cbCadastros.getSelectionModel().clearSelection();
                    tGenero.clear();
                    bAlterar.setDisable(true);
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
        
        primaryStage.setTitle("Gerenciador de Gêneros");
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
     * Busca o nome passado ao metado, com retorno booleano.
     * 
     * @param nome refente ao nome para genero.
     * @return booleano, true se encontrou e false se não encontrou.
     */    
    public boolean encontraGenero(String nome) {
        
            for(Genero genero : cinema.getGeneros() )
                if(genero.getNome().compareTo(nome) == 0) 
                    return true;
            
            return false;
        }

    /**
     * Recebe uma String e retorna o mesmo porém padronizado.
     * 
     * @param nome refente ao nome para genero.
     * @return nome padronizado começo maiusculo e resto da palavra minuscula 
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
                else
                    nome = nome.substring(0, i) +
                    nome.substring(i, i + 1).toLowerCase() +
                    nome.substring(i + 1);
            }
            return nome;
        }

    /**
     * Recebe uma String e retorna o mesmo porem sem espaços abusivos.
     * 
     * @param nome refente ao nome para genero
     * @return nome sem excesso de espaços.
     * @throws ArgumentoNaoInformado 
     */
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
     * Testa o campo tGenero para verificar se esta vazio antes de cadastrar 
     * ou editar.
     * 
     * @param nome A string referente ao campo Nome.
     * @throws ArgumentoNaoInformado
     * @throws ValorInvalido 
     */
    public void testaCampos(String nome) throws ArgumentoNaoInformado {
        
        char letra;
        
        //Se um dos campos estiver vazio, a sala não é cadastrada.
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
     * @param nome String referente ao nome do Genero a ser buscado nos 
     * cadastros dos filmes.
     * 
     * @return Um booleano, true se for encontrado o tal Genero, false se não.
     */
    public boolean encontraGeneroDeFilme(String nome) {
        
        for(Filme filme : cinema.getFilmes() ) {
            if(filme.getGenero().getNome().compareTo(nome) == 0) return true;
        }
        return false;
    }
}