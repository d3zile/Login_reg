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
import projetocinema.TelaPrincipal;
import projetocinema.execoes.ArgumentoNaoInformado;
import projetocinema.execoes.MesmoDadoCadastrado;
import projetocinema.execoes.ValorInvalido;
import projetocinema.tiposDados.Cinema;
import projetocinema.tiposDados.Filme;
import projetocinema.tiposDados.Sala;
import projetocinema.tiposDados.Sessao;

/**
 *
 * @author Luan, Guilherme
 */
public class TelaSessoes extends Application {
    
//Atributos
    Cinema cinema;
    TelaPrincipal telaPrincipal;
    
    GridPane root;
        Label lDoisPontos;
        Label[] lCampos;
        TextField txData, txHora, txMinuto, txPreco;
        Button bCadastrar, bEditar, bExcluir, bDataAtual;
        ComboBox<Sessao> cbCadastros;
        ComboBox<Sala> cbSalas;
        ComboBox<Filme> cbFilmes;
        
//Construtores
        
    /**
     * @param cinema O próprio controlador do Cinema.
     * @param telaPrincipal O root da tela principal do sistema.
     */
    public TelaSessoes(Cinema cinema, TelaPrincipal telaPrincipal) {
        this.cinema = cinema;
        this.telaPrincipal = telaPrincipal;
    }
    
//Metodos
    @Override
    public void init() {
        
        String campos[] = {"Filme:", "Sala:", "Data:", "Horário:", "Preço:"};
        
        root = new GridPane();
        
        lCampos = new Label[campos.length];
        for (int i = 0; i < campos.length; i++) {
            lCampos[i] = new Label(campos[i] );
            root.add(lCampos[i], 0, i);
        }
        lDoisPontos = new Label(" : ");
        
        txData = new TextField();
        txData.setPromptText("mm/dd/aaaa");
        txHora = new TextField();
        txHora.setPromptText("Hora (0-23)");
        txMinuto = new TextField();
        txMinuto.setPromptText("Minuto (0-59)");
        txPreco = new TextField();
        
        bCadastrar = new Button("Cadastrar");
        bEditar = new Button("Editar");
        bExcluir = new Button("Excluir");
        bDataAtual = new Button("Data Atual");
        
        cbCadastros = new ComboBox<>
            ((FXCollections.observableArrayList(cinema.getSessoes() )));
        
        cbSalas = new ComboBox<>
            ((FXCollections.observableArrayList(cinema.getSalas() )));
        
        cbFilmes = new ComboBox<>
            ((FXCollections.observableArrayList(cinema.getFilmes() )));
                
        bEditar.setDisable(true);
        bEditar.setMaxWidth(Double.MAX_VALUE);
        
        bExcluir.setDisable(true);
        bExcluir.setMaxWidth(Double.MAX_VALUE);
        
        bCadastrar.setMaxWidth(Double.MAX_VALUE);
        cbFilmes.setMaxWidth(Double.MAX_VALUE);
        cbSalas.setMaxWidth(Double.MAX_VALUE);
        cbCadastros.setMaxWidth(Double.MAX_VALUE);
        
        root.add(cbFilmes, 1, 0, 3, 1);
        root.add(cbSalas, 1, 1, 3, 1);
        root.add(txData, 1, 2);
        root.add(txHora, 1, 3);
        root.add(lDoisPontos, 2, 3);
        root.add(txMinuto, 3, 3);
        root.add(txPreco, 1, 4);
        
        root.add(cbCadastros, 0, 6, 4, 1);
        root.add(bDataAtual, 4, 2);
        root.add(bCadastrar, 4, 5);
        root.add(bEditar, 4, 6);
        root.add(bExcluir, 4, 7);
        
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

                int hora, minuto, numeroSala;
                float preco;
                String sData, sHora, sMinuto, sPreco, tituloFilme;
                Sala sala;
                Filme filme;
                Sessao novaSessao;

                sData = txData.getText();
                sHora = txHora.getText();
                sMinuto = txMinuto.getText();
                sPreco = txPreco.getText();
                
                testaCampos(sData, sHora, sMinuto, sPreco);
                
                hora = Integer.parseInt(sHora);
                minuto = Integer.parseInt(sMinuto);
                preco = Float.parseFloat(sPreco);
                
                numeroSala =
                    cbSalas.getSelectionModel().getSelectedItem().getNumero();
                sala = cinema.encontraSala(numeroSala);

                //Não deve existir sessão cadastrada com mesma data, hora e sala.
                if(cinema.encontraSessao(numeroSala, hora, sData) != null)
                    throw new MesmoDadoCadastrado("Dados");
                
                tituloFilme =
                    cbFilmes.getSelectionModel().getSelectedItem().getTitulo();
                filme = cinema.encontraFilme(tituloFilme);
                
                //Crio uma nova sessão, insiro na lista e depois repasso a
                //lista para o combo-box.
                novaSessao = new Sessao(preco, sData, sala, filme, hora, minuto);
                novaSessao.verificaSituacao();

                cinema.getSessoes().add(novaSessao);
                Collections.sort(cinema.getSessoes() );
                cbCadastros.setItems
                (FXCollections.observableArrayList(cinema.getSessoes() ));
                telaPrincipal.atualizaTabelaSessoes();

                //No caso de eu cadastrar uma sala em cima das informações
                //puxadas de outra.
                if(!cbCadastros.getSelectionModel().isEmpty() ) {
                    bEditar.setDisable(true);
                    bExcluir.setDisable(true);
                }

                limpaEntradas();
            }
            catch(ArgumentoNaoInformado | NumberFormatException |
                MesmoDadoCadastrado ex) {
                
                TelaErro.telaErro(ex);
            }
        } );
        
        //Ação ao selecionar uma sessão do combo-box, traz seus valores as deter-
        //minadas caixas de entrada para possível edição.
        cbCadastros.getSelectionModel().selectedItemProperty().addListener
        (e -> {
        
            int numeroSalaSessao, horaSessao;
            String dataSessao;
            Sessao selecionada;

            //Checando se há uma sala selecionada para evitar Null Pointer
            //Exception
            if(!cbCadastros.getSelectionModel().isEmpty() ) {
                
                numeroSalaSessao =
                    cbCadastros.getSelectionModel().getSelectedItem().getSala().
                        getNumero();
                horaSessao =
                    cbCadastros.getSelectionModel().getSelectedItem().getHorario().
                        getHora();
                dataSessao =
                    cbCadastros.getSelectionModel().getSelectedItem().getData();
                selecionada =
                    cinema.encontraSessao(numeroSalaSessao, horaSessao, dataSessao);
                cbFilmes.getSelectionModel().select(selecionada.getFilme() );
                cbSalas.getSelectionModel().select(selecionada.getSala() );
                txData.setText(selecionada.getData());
                txHora.setText(Integer.toString
                    (selecionada.getHorario().getHora() ));
                txMinuto.setText(Integer.toString
                    (selecionada.getHorario().getMinuto()));
                txPreco.setText(Float.toString(selecionada.getPreco() ));
                bEditar.setDisable(false);
                bExcluir.setDisable(false);
            }
        } );
        
        //Retiro o item do ArrayList pelo indice encontrado pelo combo-box,
        //depois repasso a lista novamente;
        bExcluir.setOnAction
        (e -> {
        
            int numeroSalaSessao, horaSessao;
            String dataSessao;
            Sessao selecionada;
            
            numeroSalaSessao =
                cbCadastros.getSelectionModel().getSelectedItem().getSala().
                    getNumero();
            horaSessao =
                cbCadastros.getSelectionModel().getSelectedItem().getHorario().
                    getHora();
            dataSessao =
                cbCadastros.getSelectionModel().getSelectedItem().getData();
            selecionada =
                cinema.encontraSessao(numeroSalaSessao, horaSessao, dataSessao);

            cinema.getSessoes().remove(selecionada);
            Collections.sort(cinema.getSessoes() );
            cbCadastros.setItems
                (FXCollections.observableArrayList(cinema.getSessoes() ) );
            telaPrincipal.atualizaTabelaSessoes();
            bEditar.setDisable(true);
            bExcluir.setDisable(true);
            limpaEntradas();
        } );
        
        bEditar.setOnAction
        (e -> {
            try {

                int hora, minuto, numeroSalaSessao, horaSessao, numeroSala;
                float preco;
                String sData, sHora, sMinuto, sPreco, dataSessao, tituloFilme; 
                Filme filme;
                Sala sala;
                Sessao selecionada;

                sData = txData.getText();
                sHora = txHora.getText();
                sMinuto = txMinuto.getText();
                sPreco = txPreco.getText();
                
                testaCampos(sData, sHora, sMinuto, sPreco);
                
                hora = Integer.parseInt(sHora);
                minuto = Integer.parseInt(sMinuto);
                preco = Float.parseFloat(sPreco);

                numeroSalaSessao =
                    cbCadastros.getSelectionModel().getSelectedItem().getSala().
                    getNumero();
                horaSessao =
                    cbCadastros.getSelectionModel().getSelectedItem().getHorario().
                    getHora();
                dataSessao =
                    cbCadastros.getSelectionModel().getSelectedItem().getData();
                selecionada =
                    cinema.encontraSessao
                        (numeroSalaSessao, horaSessao, dataSessao);
                
                numeroSala =
                    cbSalas.getSelectionModel().getSelectedItem().getNumero();
                sala = cinema.encontraSala(numeroSala);
            
                tituloFilme =
                    cbFilmes.getSelectionModel().getSelectedItem().getTitulo();
                filme = cinema.encontraFilme(tituloFilme);
                
                //Se algum dado mudou, é checado se o mesmo conjunto de dados já
                //existe em uma sessão existente, se sim, não cadastro.
                if( (selecionada.getSala().getNumero() != numeroSala ||
                    selecionada.getHorario().getHora() != hora ||
                    selecionada.getData().compareTo(dataSessao) != 0) &&
                    cinema.encontraSessao(numeroSala, hora, sData) != null )
                    throw new MesmoDadoCadastrado("Dados");
                
                selecionada.setFilme(filme);
                selecionada.setSala(sala);
                selecionada.setData(sData);
                selecionada.getHorario().setHora(hora);
                selecionada.getHorario().setMinuto(minuto);
                selecionada.setPreco(preco);
                selecionada.verificaSituacao();
                Collections.sort(cinema.getSessoes() );

                //Maneira encontrada para atualizar a informação com os
                //sets/gets no combo-box
                cbCadastros.setItems(null);
                cbCadastros.setItems
                (FXCollections.observableArrayList(cinema.getSessoes() ));
                limpaEntradas();
                telaPrincipal.atualizaTabelaSessoes();
                bEditar.setDisable(true);
                bExcluir.setDisable(true);
            }
            catch (ArgumentoNaoInformado | NumberFormatException |
                MesmoDadoCadastrado ex) {
                
                TelaErro.telaErro(ex);
            }
        } );
        
        //Gera no campo de data a String no formato correto do dia atual.
        bDataAtual.setOnAction
            (e -> txData.setText(telaPrincipal.dataAtual() ));
        
        primaryStage.addEventHandler
        (KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode() )
                primaryStage.close();
        });
        
        primaryStage.setTitle("Gerenciador de Sessões");
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
     * Limpa as entradas de todos os campos, combo-box inclusos.
     */
    public void limpaEntradas() {
        cbCadastros.getSelectionModel().clearSelection();
        cbFilmes.getSelectionModel().selectFirst();
        cbFilmes.getSelectionModel().clearSelection();
        cbSalas.getSelectionModel().selectFirst();
        cbSalas.getSelectionModel().clearSelection();
        txData.clear();
        txHora.clear();
        txMinuto.clear();
        txPreco.clear();
    }
    
    /**
     * Testa os campos de entradas de dados para verificar se seus conteúdos 
     * respeitam determiandas restrições antes de continuar com o cadastro/
     * edição.
     * 
     * @param data
     * @param hora
     * @param minuto
     * @param preco
     * @throws ArgumentoNaoInformado
     * @throws ValorInvalido 
     */
    public void testaCampos(String data, String hora, String minuto, String preco)
        throws ArgumentoNaoInformado, ValorInvalido {
        
        int iTeste;
        float fTeste;
        
        if(data.isEmpty())
            throw new ArgumentoNaoInformado("Data");
        //Testando se as entradas de números estão corretas.
        if(!dataValida(data))
            throw new ValorInvalido(data, "Data");
        
        if(hora.isEmpty())
            throw new ArgumentoNaoInformado("Hora");
                try {
            iTeste = Integer.parseInt(hora);
        }
        catch(NumberFormatException ex) {
            throw new ValorInvalido(hora, "Hora");
        }
        if(iTeste < 0 || iTeste > 23) throw new ValorInvalido(hora, "Hora");
        
        if(minuto.isEmpty())
            throw new ArgumentoNaoInformado("Minuto");
                try {
            iTeste = Integer.parseInt(minuto);
        }
        catch(NumberFormatException ex){
            throw new ValorInvalido(minuto, "Minuto");
        }
        if(iTeste < 0 || iTeste > 59) throw new ValorInvalido(minuto, "Minuto");
        
        if(preco.isEmpty())
            throw new ArgumentoNaoInformado("Preço");
        try {
            fTeste = Float.parseFloat(preco);
        }
        catch(NumberFormatException ex) {
            throw new ValorInvalido(preco, "Preço");
        }
        if(fTeste < 0)
            throw new ValorInvalido(preco, "Preço");    
    }
    
    /**
     * Verifica se uma data está no padrão mm/dd/aaaa.
     * 
     * @param data A String referente a data a ser verificada.
     * @return Um booleano, true se a data respeita o modelo, false se não.
     */
    public boolean dataValida(String data) {
        
        int dia, mes, ano;
        
        if(data.length() != 10) return false;
        
        for (int i = 0; i < 10; i++) {
            if(i != 2 && i != 5)
            if(!Character.isDigit(data.charAt(i) )) return false;
        }
        mes = Integer.parseInt(data.substring(0, 2) );
        dia = Integer.parseInt(data.substring(3, 5) );
        ano = Integer.parseInt(data.substring(6) );
        
        if(mes < 0 || mes > 12) return false;
        if(dia < 0 || dia > 31) return false;
        return (ano >= 1900);
    }
    
}
