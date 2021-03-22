/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocinema.telas;

import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import projetocinema.execoes.ArgumentoNaoInformado;
import projetocinema.tiposDados.Sessao;

/**
 *
 * @author Guilherme, Luan
 */
public class TelaIngressos extends Application {
    
    ArrayList<Sessao> sessoes;
    
    GridPane root;
        Label lFilme, lSala, lData, lHorario, lPreco, lMeia, lIngressos,
            lSessoes, lfilme, lsala, ldata, lhorario, lpreco;
        ComboBox<Integer> cbIngressosD,cbIngressosI;
        ComboBox<Sessao> cbSessoes;
        Button bCadastrar, bExcluir;
        ToggleGroup grupo;     
        RadioButton rbInteira, rbMeia;

    /** 
     * @param sessoes ArrayList refente as sessoes cadastradas.
     */   
    public TelaIngressos(ArrayList<Sessao> sessoes) {
        this.sessoes = sessoes;
    }
    
    
    @Override
    public void init() {
        
        root = new GridPane();
        
        rbInteira = new RadioButton("Inteia"); 
        rbMeia = new RadioButton("Meia");
        grupo = new ToggleGroup();
        lFilme = new Label("Filme: ");
        lIngressos = new Label("Ingressos ");
        lSala = new Label("Sala: ");
        lData = new Label("Data: ");
        lHorario = new Label("Horário: ");
        lPreco = new Label("Preço: R$ ");
        lfilme = new Label();
        lsala = new Label();
        ldata = new Label();
        lhorario = new Label();
        lpreco = new Label();
        lSessoes = new Label("Sessões: ");
        bCadastrar = new Button("Cadastrar");
        bExcluir = new Button("Excluir");
        
        cbSessoes = new ComboBox<>
            (FXCollections.observableArrayList(sessoes) );
        cbIngressosD = new ComboBox<>();
        cbIngressosI = new ComboBox<>();
        
        rbInteira.setUserData(false);
        rbMeia.setUserData(true);
        bExcluir.setDisable(true);
        bCadastrar.setDisable(true);
        rbInteira.setSelected(true); 
        rbInteira.setToggleGroup(grupo);
        rbMeia.setToggleGroup(grupo);
        
        cbSessoes.setMaxWidth(Double.MAX_VALUE);
        cbIngressosD.setMaxWidth(Double.MAX_VALUE);
        cbIngressosI.setMaxWidth(Double.MAX_VALUE);
        bCadastrar.setMaxWidth(100);
        bExcluir.setMaxWidth(100);
        cbIngressosD.setPromptText("Disponiveis");
        cbIngressosI.setPromptText("Indisponiveis");
        
        root.add(lSessoes, 0, 0);
        root.add(cbSessoes, 1, 0, 3, 1);
        root.add(lFilme, 0, 1);
        root.add(lfilme, 1, 1, 2, 1);
        root.add(lSala, 0, 2);
        root.add(lsala, 1, 2);
        root.add(lData, 0, 3);
        root.add(ldata, 1, 3);
        root.add(lHorario, 2, 3);
        root.add(lhorario, 3, 3);
        root.add(rbInteira, 0, 4);
        root.add(rbMeia, 1, 4);
        root.add(lPreco, 0, 5);
        root.add(lpreco, 1, 5);
        root.add(cbIngressosD, 0, 6, 2, 1);
        root.add(cbIngressosI, 0, 7, 2, 1);
        root.add(bCadastrar, 2, 6);
        root.add(bExcluir, 2, 7);
        
        //Fixando tamanhos das linhas/colunas no GridPane.
        for (int i = 0; i < 4; i++)
            root.getColumnConstraints().add(new ColumnConstraints(120) );
        for (int i = 0; i < 8; i++)
            root.getRowConstraints().add(new RowConstraints(30) );

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
                    Sessao selecionado;     
                    selecionado = cbSessoes.getSelectionModel().getSelectedItem();
                    // Uma vereficação se algum ascento foi selecionada, para cadastrar um ingresso 
                    if(!cbIngressosD.getSelectionModel().isEmpty() ) {
                        selecionado.insereIngresso((boolean)  grupo.getSelectedToggle().getUserData(), 
                                cbIngressosD.getSelectionModel().getSelectedItem());
                        cbIngressosI.setItems(FXCollections.observableArrayList(ingressosIndisponiveis(selecionado)));
                        cbIngressosD.setItems(FXCollections.observableArrayList(ingressosDisponiveis(selecionado)));
                        bCadastrar.setDisable(true);
                        bExcluir.setDisable(true);
                    }
                    
                } catch(ArgumentoNaoInformado ex){
                      TelaErro.telaErro(ex);
                    }    
            }
        );
        
         rbInteira.selectedProperty().addListener(e -> {
             Sessao selecionado;
             selecionado = cbSessoes.getSelectionModel().getSelectedItem();
             if(!cbSessoes.getSelectionModel().isEmpty() ) {
                if(rbInteira.isSelected()){
                    lpreco.setText(String.format
                        ("%.2f", selecionado.getPreco() ));
                }
                else{
                    lpreco.setText
                        (String.format(".2f", selecionado.getPreco() / 2) );
                }
            }   
         });
        //Ação ao selecionar uma sessão do combo-box, traz seus valores as deter-
        //minadas caixas de entrada para possível cadastro ou exluir o ingresso cadastrado.
        cbSessoes.getSelectionModel().selectedItemProperty().addListener
            (e -> {
                
                Sessao selecionado;
                
               // Uma vereficação se alguma sessão foi selecionada, para cadastrar ou exluir o ingresso. 
                if(!cbSessoes.getSelectionModel().isEmpty() ) {
                    selecionado = cbSessoes.getSelectionModel().getSelectedItem();
                    lfilme.setText(selecionado.getFilme().getTitulo());
                    lhorario.setText(selecionado.getHorario().toString());
                    lsala.setText(selecionado.getSala().toString());
                    ldata.setText(selecionado.getData() );
                    cbIngressosD.setItems(FXCollections.observableArrayList(ingressosDisponiveis(selecionado)));
                    cbIngressosI.setItems(FXCollections.observableArrayList(ingressosIndisponiveis(selecionado)));
                    
                    if(rbInteira.isSelected()){
                        lpreco.setText(String.format
                        ("%.2f", selecionado.getPreco() ));
                    }
                    else{
                        lpreco.setText(String.format(".2f", selecionado.getPreco() / 2) );
                    }
                }
            }
        );
        
        cbIngressosD.getSelectionModel().selectedItemProperty().addListener
            (e -> {
                bCadastrar.setDisable(false);
            });
        
        cbIngressosI.getSelectionModel().selectedItemProperty().addListener
            (e -> {
                if(!cbIngressosI.getSelectionModel().isEmpty() ) {
                    int indiceSeleciona =
                            cbIngressosI.getSelectionModel().getSelectedItem();
                    Sessao selecionado =
                            cbSessoes.getSelectionModel().getSelectedItem();

                    bExcluir.setDisable(false);

                        grupo.selectToggle
                            (selecionado.getIngressos()[indiceSeleciona].isMeia()
                            ? rbMeia
                            : rbInteira );
                }
            });
        
        //Retiro o item do ArrayList pelo indice encontrado pelo combo-box, 
        //depois repasso a lista novamente;
        bExcluir.setOnAction
            (e -> {
                
                Sessao selecionado;     
                selecionado = cbSessoes.getSelectionModel().getSelectedItem();
                // Uma vereficação se algum ingresso foi selecionada, para exclui-lo
                if(!cbIngressosI.getSelectionModel().isEmpty() ) {
                    selecionado.removeIngresso(cbIngressosI.getSelectionModel().getSelectedItem());
                    cbIngressosI.setItems(FXCollections.observableArrayList(ingressosIndisponiveis(selecionado)));
                    cbIngressosD.setItems(FXCollections.observableArrayList(ingressosDisponiveis(selecionado)));
                    bCadastrar.setDisable(true);
                    bExcluir.setDisable(true);
                }    
            }
        );
        
        primaryStage.addEventHandler
        (KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode() )
                primaryStage.close();
        });
        
        primaryStage.setTitle("Gerenciador de ingressos");
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
     * Procura todos os ingressos disponiveis para venda, inserindo seus índices
     * em um Array List e retornando a mesma.
     * 
     * @param sessao ArrayList refente aos ingressos disponiveis.
     * @return Array list de inteiros.
     */
    public ArrayList<Integer> ingressosDisponiveis(Sessao sessao) {
        
        ArrayList<Integer> ingressoDisponiveis = new ArrayList<>();
        
        for (int i = 0; i < sessao.getIngressos().length ; i++) {
            if(sessao.getIngressos()[i] == null)
                ingressoDisponiveis.add(i);
        }
        return ingressoDisponiveis;
    }
    
    /**
     * Procura todos os ingressos indisponiveis, inserindo seus indices
     * em um Array List  e retornando a mesma.
     * 
     * @param sessao ArrayList refente aos ingressos indisponiveis
     * @return Array list de inteiros.
     */
    public ArrayList<Integer> ingressosIndisponiveis(Sessao sessao) {
        
        ArrayList<Integer> ingressoIndisponiveis = new ArrayList<>();
        
        for (int i = 0; i < sessao.getIngressos().length ; i++) {
            if(sessao.getIngressos()[i] != null)
                ingressoIndisponiveis.add(i);
        }
        return ingressoIndisponiveis;
    }
}
