/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocinema.telas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import projetocinema.execoes.ArgumentoNaoInformado;
import projetocinema.execoes.MesmoDadoCadastrado;
import projetocinema.execoes.ValorInvalido;
import projetocinema.tiposDados.Ator;
import projetocinema.tiposDados.Cinema;
import projetocinema.tiposDados.Filme;
import projetocinema.tiposDados.Genero;
import projetocinema.tiposDados.Sessao;

/**
 *
 * @author Guilherme, Luan
 */
public class TelaFilmes extends Application {
    
    Cinema cinema;
    ArrayList<Ator> atoresDoFilme;
    
    GridPane root;
    Label lTitulo, lDuracao, ldoisPontos, lAtores, lGenero, lSeta, lImportar;
    TextField txTitulo, txHora, txMinuto, txImportar;
    Button bCadastrar, bExcluir, bEditar, bAdicionarAtor, bRemoverAtor;
    
    ComboBox<Ator> cbAtores, cbAtoresFilme;
    ComboBox<Genero> cbGeneros;
    ComboBox<Filme> cbCadastros;
    
    /** 
     * @param cinema O objeto Cinema referente ao próprio controlador.
     */
    public TelaFilmes(Cinema cinema) {
        
        this.cinema = cinema;
        this.atoresDoFilme = new ArrayList<>();
    }
    
     @Override
    public void init() {
        
        root = new GridPane();
        
        lTitulo = new Label("Título:");
        lDuracao = new Label("Duração:");
        ldoisPontos = new Label(" : ");
        lGenero = new Label("Gênero:");
        lAtores = new Label("Atores:");
        lSeta = new Label("->");
        lImportar = new Label("Endereço de imagem:");
        
        txHora = new TextField();
        txMinuto = new TextField();
        txTitulo = new TextField();
        txImportar = new TextField();
        
        bCadastrar = new Button("Cadastrar");
        bEditar = new Button("Editar");
        bExcluir = new Button("Excluir");
        bAdicionarAtor = new Button("Adicionar");
        bRemoverAtor = new Button("Remover");
        
        cbCadastros = new ComboBox<>
            (FXCollections.observableArrayList(cinema.getFilmes() ));
        cbGeneros = new ComboBox<>
            (FXCollections.observableArrayList(cinema.getGeneros() ));
        cbAtores = new ComboBox<>
            (FXCollections.observableArrayList(cinema.getAtores() ));
        cbAtoresFilme = new ComboBox<>();
        
        txImportar.setMaxWidth(Double.MAX_VALUE);
        txImportar.setPromptText("Campo Opcional...");
        
        root.add(lTitulo, 0, 0);
        root.add(lGenero, 0, 1);
        root.add(lDuracao, 0, 2);
        root.add(lAtores, 0, 5);
        root.add(ldoisPontos, 2, 2);
        root.add(lSeta, 2, 5);
        root.add(lImportar, 0, 3);
        
        root.add(txTitulo, 1, 0, 3, 1);
        root.add(txHora, 1, 2);
        root.add(txMinuto, 3, 2);
        root.add(txImportar, 1, 3, 3, 1);
        
        root.add(bAdicionarAtor, 4, 4);
        root.add(bRemoverAtor, 4, 5);
        root.add(bCadastrar, 4, 7);
        root.add(bEditar, 4, 8);
        root.add(bExcluir, 4, 9);
       
        
        root.add(cbGeneros, 1, 1, 3, 1);
        root.add(cbAtores, 1, 5);
        root.add(cbAtoresFilme, 3, 5);
        root.add(cbCadastros, 0, 8, 4, 1);
        
        txHora.setPromptText("Horas (0-23)");
        txMinuto.setPromptText("Minutos (0-59)");
        
        bCadastrar.setMaxWidth(Double.MAX_VALUE);
        bEditar.setDisable(true);
        bEditar.setMaxWidth(Double.MAX_VALUE);
        bExcluir.setDisable(true);
        bExcluir.setMaxWidth(Double.MAX_VALUE);
        bAdicionarAtor.setMaxWidth(Double.MAX_VALUE);
        bRemoverAtor.setMaxWidth(Double.MAX_VALUE);
        bRemoverAtor.setDisable(true);
        
        cbCadastros.setMaxWidth(Double.MAX_VALUE);
        cbAtores.setMaxWidth(Double.MAX_VALUE);
        cbGeneros.setMaxWidth(Double.MAX_VALUE);
        cbAtoresFilme.setMaxWidth(Double.MAX_VALUE);
        cbAtoresFilme.setDisable(true);
        cbAtores.setPromptText("Cadastrados");
        cbAtoresFilme.setPromptText("Do filme");
        
        for (int i = 0; i < 10; i++)
            root.getRowConstraints().add(new RowConstraints(26) );
        
        root.setMinSize(400, 300);
        root.setVgap(10);
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }  
    
    @Override
    public void start(Stage primaryStage) {
        
        Scene scene = new Scene(root, 700, 400);
        
         bCadastrar.setOnAction
            (e -> {
                try {
                    
                    int horas, minutos;
                    String sTitulo, sHora, sMin, nomeGenero, sEndereco;
                    Genero genero;
                    Filme novoFilme;
                    
                    sTitulo = txTitulo.getText();
                    sHora = txHora.getText();
                    sMin = txMinuto.getText();
                    sEndereco = txImportar.getText();
                    
                    testaCampos(sTitulo, sHora, sMin, sEndereco);
                    sTitulo = formataNome(sTitulo);
                    sTitulo = nomeSemEspacos(sTitulo);
                    
                    //Se haver um com o mesmo nome, o filme não é cadastrado.
                    if(cinema.encontraFilme(sTitulo) != null)
                        throw new MesmoDadoCadastrado("Dado");
                    
                    nomeGenero =
                        cbGeneros.getSelectionModel().getSelectedItem().getNome();
                    genero = cinema.encontraGenero(nomeGenero);

                    horas = Integer.parseInt(sHora);
                    minutos = Integer.parseInt(sMin);
                    
                    //Crio um novo filme, insiro na lista e depois repasso a 
                    //lista para o combo-box.
                    novoFilme =
                        new Filme(sTitulo, genero, horas, minutos, sEndereco);
                    novoFilme.getAtores().addAll(atoresDoFilme);
                    
                    cinema.getFilmes().add(novoFilme);
                    Collections.sort(cinema.getFilmes());
                    
                    cbCadastros.setItems
                        (FXCollections.observableArrayList(cinema.getFilmes() ));

                    //No caso de eu cadastrar um filme em cima das informações
                    //puxadas de outra.
                    if(!cbCadastros.getSelectionModel().isEmpty() ) {
                        bEditar.setDisable(true);
                        bExcluir.setDisable(true);
                        bAdicionarAtor.setDisable(true);
                        cbAtoresFilme.setDisable(true);
                    }

                    limpaDados();
                }
                catch(ArgumentoNaoInformado | NumberFormatException |
                    MesmoDadoCadastrado ex) {
                    
                    TelaErro.telaErro(ex);
                }
                catch(Exception ex) {
                    TelaErro.telaErro(ex);
                }
            }
        );
        
        //Ação ao selecionar um filme do combo-box, traz seus valores as deter-
        //minadas caixas de entrada para possível edição.
        cbCadastros.getSelectionModel().selectedItemProperty().addListener
        (e -> {

            String nomeFilme;
            Filme selecionado;

            //Checando se há um filme selecionado para evitar Null Pointer
            //Exception
            if(!cbCadastros.getSelectionModel().isEmpty() ) {
                
                nomeFilme =
                    cbCadastros.getSelectionModel().getSelectedItem().getTitulo();
                selecionado = cinema.encontraFilme(nomeFilme);
                
                txHora.setText
                    (Integer.toString(selecionado.getDuracao().getHora()));
                txMinuto.setText
                    (Integer.toString(selecionado.getDuracao().getMinuto()));
                cbGeneros.getSelectionModel().select(selecionado.getGenero() );
                txTitulo.setText(selecionado.getTitulo() );
                txImportar.setText(selecionado.getEndereco() );
                bAdicionarAtor.setDisable(false);
                bEditar.setDisable(false);
                bExcluir.setDisable(false);
                cbAtoresFilme.setDisable(false);
                atoresDoFilme.clear();
                atoresDoFilme.addAll(selecionado.getAtores());
                cbAtoresFilme.setItems
                    (FXCollections.observableArrayList(atoresDoFilme));
            }
        } );
        
        //Retiro o item do ArrayList pelo indice encontrado pelo combo-box, 
        //depois repasso a lista novamente;
        bExcluir.setOnAction
            (e -> {
                try {
                    String tituloFilme;
                    Filme selecionado;

                    tituloFilme =
                        cbCadastros.getSelectionModel().getSelectedItem().getTitulo();
                    selecionado = cinema.encontraFilme(tituloFilme);

                    if(encontraFilmeEmSessao(tituloFilme) )
                        throw new Exception("Filme já encontrado em sessões cadastradas");
                    
                    cinema.getFilmes().remove(selecionado);
                    
                    Collections.sort(cinema.getFilmes() );
                    cbCadastros.setItems
                        (FXCollections.observableArrayList(cinema.getFilmes() ));
                    bEditar.setDisable(true);
                    bExcluir.setDisable(true);
                    limpaDados();
                }
                catch(Exception ex) {
                    TelaErro.telaErro(ex);
                }
            }
        );
        
        bEditar.setOnAction
            (e -> {
                try {
                    
                    int horas, minutos;
                    String sTitulo, sHora, sMin, sEndereco;  
                    String nomeFilme, nomeGenero;
                    Genero genero;
                    Filme selecionado;
                    
                    sTitulo = txTitulo.getText();
                    sHora = txHora.getText();
                    sMin = txMinuto.getText();
                    sEndereco = txImportar.getText();
                    
                    nomeGenero =
                        cbGeneros.getSelectionModel().getSelectedItem().
                            getNome();
                    genero = cinema.encontraGenero(nomeGenero);
                    
                    testaCampos(sTitulo, sHora, sMin, sEndereco);
                    sTitulo = formataNome(sTitulo);
                    sTitulo = nomeSemEspacos(sTitulo);
                    
                    nomeFilme =
                        cbCadastros.getSelectionModel().getSelectedItem().
                            getTitulo();
                    selecionado = cinema.encontraFilme(nomeFilme);
                    
                    //Se o nome foi modificado, não pode haver um filme com o 
                    //mesmo.
                    if(selecionado.getTitulo().compareTo(sTitulo) != 0 &&
                        cinema.encontraFilme(sTitulo) != null) {
                        throw new MesmoDadoCadastrado("Dado");
                    }
                    
                    //Se não, o filme, hora, minuto e genero é atualizado.
                    horas = Integer.parseInt(sHora);
                    minutos = Integer.parseInt(sMin);

                    selecionado.setTitulo(sTitulo);
                    selecionado.setGenero(genero);
                    selecionado.setEndereco(sEndereco);
                    selecionado.getDuracao().setHora(horas);
                    selecionado.getDuracao().setMinuto(minutos);
                    selecionado.getAtores().clear();
                    selecionado.getAtores().addAll(atoresDoFilme);
                    
                    Collections.sort(cinema.getFilmes() );

                    //Maneira encontrada para atualizar a informação com os 
                    //sets/gets no combo-box.
                    cbCadastros.setItems(null);
                    cbCadastros.setItems
                        (FXCollections.observableArrayList(cinema.getFilmes() ));
                    
                    bEditar.setDisable(true);
                    bExcluir.setDisable(true);
                    limpaDados();
                }
                catch(ArgumentoNaoInformado | NumberFormatException |
                    MesmoDadoCadastrado ex) {
                    
                    TelaErro.telaErro(ex);
                }
                catch(Exception ex) {
                    TelaErro.telaErro(ex);
                }
            }
        );
        
        cbAtoresFilme.getSelectionModel().selectedItemProperty().addListener
        (e -> {
            if(!cbAtoresFilme.getSelectionModel().isEmpty() )
                bRemoverAtor.setDisable(false);
        } );
        
        bAdicionarAtor.setOnAction
        (e -> {
            Ator selecionado;
            try {
                if(cbAtores.getSelectionModel().isEmpty() )
                    throw new ArgumentoNaoInformado("Ator");
                
                selecionado = cbAtores.getSelectionModel().getSelectedItem();
                
                if(cbAtoresFilme.isDisabled() )
                    cbAtoresFilme.setDisable(false);
                else {
                    for (Ator ator : atoresDoFilme) {
                        if(ator.getNome().compareTo(selecionado.getNome() ) == 0)
                            throw new Exception("Ator já cadastrado no filme");
                    }
                }
                atoresDoFilme.add(selecionado);
                Collections.sort(atoresDoFilme);
                cbAtores.getSelectionModel().clearSelection();
                cbAtoresFilme.getSelectionModel().clearSelection();
                cbAtoresFilme.setItems
                    (FXCollections.observableArrayList(atoresDoFilme) );
                bRemoverAtor.setDisable(true);
            }
            catch(ArgumentoNaoInformado ex) {
                TelaErro.telaErro(ex);
            }
            catch(Exception ex) {
                TelaErro.telaErro(ex);
            }
            
        });
        
        bRemoverAtor.setOnAction
        (e -> {
            if(!cbAtoresFilme.getSelectionModel().isEmpty() ) {
                int indiceAtor =
                    cbAtoresFilme.getSelectionModel().getSelectedIndex();
            
                atoresDoFilme.remove(indiceAtor);

                if(atoresDoFilme.isEmpty() ) {
                    cbAtores.getSelectionModel().clearSelection();
                    cbAtoresFilme.setDisable(true);
                }
                else {
                    Collections.sort(atoresDoFilme);
                    cbAtores.getSelectionModel().clearSelection();
                    cbAtoresFilme.setItems
                        (FXCollections.observableArrayList(atoresDoFilme) );
                }
                bRemoverAtor.setDisable(true);
            }
        });
        
        
        primaryStage.addEventHandler
        (KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode() )
                primaryStage.close();
        });
        
        primaryStage.setTitle("Gereciador de Filmes");
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
     * Limpa todas as áreas de entrada de dados e reinicia a ArrayList loca de 
     * atores para começar novamente a ser usada.
     */
    public void limpaDados(){
        txTitulo.clear();
        txHora.clear();
        txMinuto.clear();
        cbAtores.getSelectionModel().clearSelection();
        cbGeneros.getSelectionModel().selectFirst();
        cbGeneros.getSelectionModel().clearSelection();
        atoresDoFilme.clear();
        txImportar.clear();
        cbAtoresFilme.setItems(null);
        cbAtoresFilme.getSelectionModel().clearSelection();
        cbAtoresFilme.setDisable(true);
        bRemoverAtor.setDisable(true);
        cbCadastros.getSelectionModel().clearSelection();
    }

    /**
     * Formata a String referente ao título do filme para que os caracteres
     * fiquem minúsculos, mas os nomes tenham sua primeira letra maiúscula.
     * 
     * @param titulo A String a ser formatada.
     * @return Uma String com o titulo do filme formatado.
     */
    public String formataNome(String titulo) {
        
        boolean maiuscula = true;
        
        for (int i = 0; i < titulo.length(); i++) {
            if(Character.isWhitespace(titulo.charAt(i)) ) maiuscula = true;
            else if(maiuscula) {
                 titulo = titulo.substring(0, i) +
                        titulo.substring(i, i + 1).toUpperCase() + titulo.substring(i + 1);
                 maiuscula = false;
            }
            else titulo = titulo.substring(0, i) +
                titulo.substring(i, i + 1).toLowerCase() + titulo.substring(i + 1);
        }
        return titulo;
    }
    
    /**
     * Formata o nome referente ao título do filme para retirar espaços a mais 
     * no início, final, ou entre os nomes. 
     * 
     * @param titulo A String a ser formatada.
     * @return Uma String com o titulo do filme formatado.
     * @throws ArgumentoNaoInformado 
     */
    public String nomeSemEspacos(String titulo) throws ArgumentoNaoInformado {
        
        //Tratamento para se haver espaços no começo da String.
        if(Character.isWhitespace(titulo.charAt(0) )) {
            
            for (int i = 0; i < titulo.length(); i++) {
                if(Character.isWhitespace(titulo.charAt(0) ))
                    titulo = titulo.substring(i + 1);
                else break;
            }
        }
        //Se depois de retirado todos os espaços, a String ficar vazia, lanço
        //uma exeção.
        if(titulo.isEmpty()) throw new ArgumentoNaoInformado("Nome");
        
        for (int i = 0; i < titulo.length(); i++) {
            
            if(Character.isWhitespace(titulo.charAt(i) )) {
                
                if(i == titulo.length() - 1) titulo = titulo.substring(0, i);
                else if(Character.isWhitespace(titulo.charAt(i + 1) )) {
                    titulo = titulo.substring(0, i) + titulo.substring(i + 1);
                    i--;
                }
            }
        }
        return titulo;
    }
    
    /**
     * Testa o campo Nome, Horas, Minutos para verificar se esta vazio antes de 
     * cadastrar ou editar.
     * 
     * @param titulo A string presente no campo Nome..
     * @param hor A string presente no campo Hora..
     * @param min A string presente no campo Minuto..
     * @throws ArgumentoNaoInformado
     * @throws java.lang.Exception
     * @throws ValorInvalido 
     */
    public void testaCampos(String titulo, String hor, String min, String endereco)
        throws ArgumentoNaoInformado, Exception {
        
        int horas, minutos;
        FileInputStream entrada;
        
        if(titulo.isEmpty() )
            throw new ArgumentoNaoInformado("Nome");
        
        if(hor.isEmpty() )
            throw new ArgumentoNaoInformado("Hora");
        try {
            horas = Integer.parseInt(hor);
        }
        catch (NumberFormatException ex){
            throw new ValorInvalido(hor, "Horas");
        }
        //Se as horas passar de 24,  filme não é cadastrada.
        if(horas < 0 || horas > 23)
            throw new ValorInvalido(hor, "Horas");
        
        if(min.isEmpty() )
            throw new ArgumentoNaoInformado("Minuto");
        try {
            minutos = Integer.parseInt(min);
        }
        catch (NumberFormatException ex){
            throw new ValorInvalido(min, "Minutos");
        }
        //Se os minutos passar de 60,  filme não é cadastrada.
        if(minutos < 0 || minutos > 59)
            throw new ValorInvalido(min, "Minutos");
        
        if(cbAtoresFilme.isDisabled() )
            throw new ArgumentoNaoInformado("Ator");
        
        try {
            if(!endereco.isEmpty() )
                entrada = new FileInputStream(endereco);
        }
        catch(FileNotFoundException exception) {
            throw new Exception("Endereço inválido");
        }
    }
    
    /**
     * Faz uma busca no ArrayList de Sessões do controlador, para encontrar uma
     * que apresente um determinado nome de filme.
     * 
     * @param titulo A String referente ao titulo de filme buscado.
     * @return Um booleano, true se a sessão foi encontra, false se não.
     */
    public boolean encontraFilmeEmSessao(String titulo) {
        for (Sessao sessao : cinema.getSessoes() ) {
            if(sessao.getFilme().getTitulo().compareTo(titulo) == 0)
                return true;
        }
        return false;
    }
    
}
