/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocinema.telas;

import java.io.FileInputStream;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import projetocinema.tiposDados.Ingresso;
import projetocinema.tiposDados.Sessao;

/**
 *
 * @author Luan, Guilherme
 */
public class TelaVendeIngressos extends Application {
    
//Atributos
    int vendidos;
    
    //Preço atual da compra dos ingressos, soma os preços de todos os ingressos
    //que estão no momento no vetor local "ingressos".
    float valorTotal;
    Sessao sessao;
    
    //Os ingressos que forem adicionados no carrinho são passados para esse vetor
    //de ingressos local, quando o botão comprar for acionado, todos os ingressos
    //dele são passados para o vetor do Objeto sessao.
    Ingresso[] ingressos;
    
    FileInputStream input;
    Image imagem;
    ImageView imageView;
    
    GridPane root;
    Label lFilme, lSala, lData, lHorario, lPreco, lMeia, lIngressos, lTotal;
    ComboBox<Integer> cbIngressosDisponiveis, cbCarrinho;
    RadioButton rbInteira, rbMeia;
    Button bInserir, bRemover, bComprar;
    ToggleGroup grupoRb;  
        
//Construtores
    
    /**
     * @param sessao Objeto referente a sessão cujos ingressos serão vendidos.
     */
    public TelaVendeIngressos(Sessao sessao) {
        this.sessao = sessao;
    }
    
//Metodos
    @Override
    public void init() throws Exception {
        
        String endereco = sessao.getFilme().getEndereco();
        
        root = new GridPane();
        
        vendidos = sessao.getIngressosVendidos();
        valorTotal = 0;
        
        imageView = new ImageView();
        
        if(!endereco.isEmpty() ) {
            input = new FileInputStream(endereco);
            imagem = new Image(input);
            imageView.setImage(imagem);
        }

        
        lFilme = new Label("Filme: " + sessao.getFilme().getTitulo() );
        lSala = new Label("Sala: " + sessao.getSala().getNumero() );
        lData = new Label("Data: " + sessao.getData() );
        lHorario = new Label("Horário: " + sessao.getHorario() );
        lPreco = new Label("Preço: R$ " + sessao.getPreco() );
        lTotal = new Label("Total: R$ " + valorTotal);
        grupoRb = new ToggleGroup();
        
        rbInteira = new RadioButton("Inteira");
        rbInteira.setToggleGroup(grupoRb);
        rbInteira.setSelected(true);
        rbInteira.setUserData(false);
       
        rbMeia = new RadioButton("Meia");
        rbMeia.setToggleGroup(grupoRb);
        rbMeia.setUserData(true);
        
        bInserir = new Button("Inserir");
        bInserir.setDisable(true);
        bInserir.setMaxWidth(150);
        bRemover = new Button("Remover");
        bRemover.setDisable(true);
        bRemover.setMaxWidth(150);
        bComprar = new Button("Comprar");
        bComprar.setMaxWidth(Double.MAX_VALUE);
        
        ingressos = new Ingresso[sessao.getSala().getCapacidade() ];
        
        cbIngressosDisponiveis = new ComboBox<>
            (FXCollections.observableArrayList(sessao.ingressosDisponiveis() ));
        cbIngressosDisponiveis.setMaxWidth(Double.MAX_VALUE);
        
        cbCarrinho = new ComboBox<>();
        cbCarrinho.setMaxWidth(Double.MAX_VALUE);
        cbCarrinho.setDisable(true);
        bComprar.setDisable(true);
        
        imageView.setFitHeight(270);
        imageView.setFitWidth(180);
        imageView.setPreserveRatio(true);
        
        root.setPrefHeight(15);
        
        root.add(imageView, 2, 1, 1, 6);
        root.add(lFilme, 0, 0, 3, 1);
        root.add(lSala, 0, 1);
        root.add(lData, 0, 2);
        root.add(lHorario, 0, 3);        
        root.add(rbInteira, 0, 4);
        root.add(rbMeia, 1, 4);
        root.add(lPreco, 0, 5);
        root.add(cbIngressosDisponiveis, 0, 6);
        root.add(bInserir, 1, 6);
        root.add(cbCarrinho, 0, 7);
        root.add(bRemover, 1, 7);
        root.add(lTotal, 0, 8);
        root.add(bComprar, 2, 8);
        
        //Fixando tamanhos das linhas/colunas no GridPane.
        for (int i = 0; i < 3; i++)
            root.getColumnConstraints().add(new ColumnConstraints(200) );
        for (int i = 0; i < 10; i++)
            root.getRowConstraints().add(new RowConstraints(40) );
        
        cbIngressosDisponiveis.setPromptText("Disponíveis");
        cbIngressosDisponiveis.setStyle("-fx-font: normal 12pt Arial;");
        cbCarrinho.setPromptText("No carrinho");
        cbCarrinho.setStyle("-fx-font: normal 12pt Arial;");
        
        root.setPrefSize(700, 500);
        root.setVgap(10);
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-font: normal 16pt Arial;");
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        Scene scene = new Scene(root, 700, 500);
        
        rbInteira.selectedProperty().addListener(e -> {
            lPreco.setText("Preço: R$ ");
            if(rbInteira.isSelected())
                lPreco.setText(lPreco.getText() +
                    String.format("%.2f", sessao.getPreco() ));
            else
                lPreco.setText(lPreco.getText() +
                    String.format("%.2f", sessao.getPreco() / 2) );
        });
        
        cbIngressosDisponiveis.getSelectionModel().selectedItemProperty().
            addListener(e -> bInserir.setDisable(false) );
        
        //Quando selecioano um ingresso do carrinho, trago ao grupo de radio 
        //button a opção escolhida na compra para verificar a escolha.
        cbCarrinho.getSelectionModel().selectedItemProperty().
            addListener(e -> {
                Integer selecionado =
                cbCarrinho.getSelectionModel().getSelectedItem();
                if(!cbCarrinho.getSelectionModel().isEmpty() )
                    grupoRb.selectToggle
                        (ingressos[selecionado].isMeia() ? rbMeia : rbInteira);
                bRemover.setDisable(false);
            } );
        
        bInserir.setOnAction
        (e -> {
            boolean meia;
            ObservableList<Integer> auxiliar;
            Integer selecionado =
                cbIngressosDisponiveis.getSelectionModel().getSelectedItem();

            cbIngressosDisponiveis.getSelectionModel().selectNext();

            cbIngressosDisponiveis.getItems().remove(selecionado);

            if(cbIngressosDisponiveis.getSelectionModel().isEmpty() ) {
                cbIngressosDisponiveis.setDisable(true);
                bInserir.setDisable(true);
            }
                
            if(cbCarrinho.isDisabled() ) {
                cbCarrinho.setDisable(false);
                bComprar.setDisable(false);
            }
            
            cbCarrinho.getSelectionModel().clearSelection();
            cbCarrinho.getItems().add(selecionado);
            meia = (boolean) grupoRb.getSelectedToggle().getUserData();
            
            insereIngresso(meia, selecionado);
            valorTotal += meia ? sessao.getPreco() / 2 : sessao.getPreco() ;
            lTotal.setText("Total: R$ " + String.format("%.2f", valorTotal));
            
            auxiliar = cbCarrinho.getItems();
            FXCollections.sort(auxiliar);
            cbCarrinho.setItems(auxiliar);
            
        } );
        
        bRemover.setOnAction
        (e -> {
            
            ObservableList<Integer> auxiliar;
            Integer selecionado =
                cbCarrinho.getSelectionModel().getSelectedItem();

            cbCarrinho.getSelectionModel().selectNext();

            cbCarrinho.getItems().remove(selecionado);
            
            

            if(cbCarrinho.getSelectionModel().isEmpty() ) {
                cbCarrinho.setDisable(true);
                bRemover.setDisable(true);
                bComprar.setDisable(true);
            }
                
            if(cbIngressosDisponiveis.isDisabled() )
                cbIngressosDisponiveis.setDisable(false);
            
            cbIngressosDisponiveis.getSelectionModel().clearSelection();
            cbIngressosDisponiveis.getItems().add(selecionado);
            
            auxiliar = cbIngressosDisponiveis.getItems();
            FXCollections.sort(auxiliar);
            cbIngressosDisponiveis.setItems(auxiliar);
            
            valorTotal -= ingressos[selecionado].isMeia()
                ? sessao.getPreco() / 2
                : sessao.getPreco();
            lTotal.setText("Total: R$ " + valorTotal);
            
            removeIngresso(selecionado);
            
        } );
        
        bComprar.setOnAction(e -> {
            
            for (int i = 0; i < sessao.getSala().getCapacidade(); i++) {
                if(ingressos[i] != null)
                    sessao.insereIngresso(ingressos[i].isMeia(), i);
            }
            cbCarrinho.getItems().clear();
            cbCarrinho.setDisable(true);
            bRemover.setDisable(true);
            
            valorTotal = 0;
            lTotal.setText("Total: R$ " + valorTotal);
            cbIngressosDisponiveis.getSelectionModel().clearSelection();
            cbCarrinho.setDisable(true);
            
        } );
        
        primaryStage.addEventHandler
        (KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode() )
                primaryStage.close();
        });
        
        primaryStage.setTitle("Venda de ingressos");
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
     * Insere um ingresso no vetor local de ingressos.
     * 
     * @param meia Booleano, true para inserir um ingresso vendido a meia-
     * entrada, false para inserir um ingresso vendido a preço integral.
     * @param assento Inteiro que representa o número do assento vendido.
     * @return Um booleano, true se a inserção foi bem-sucedida, false se não.
     */
    public boolean insereIngresso(boolean meia, int assento) {
        if (vendidos < sessao.getSala().getCapacidade() ) {
            ingressos[assento] = new Ingresso(meia);
            vendidos++;
            return true;
        }
        else return false;
    }
    
    /**
     * Remove um ingresso no vetor local de ingressos.
     * 
     * @param assento Inteiro que representa o número do assento inserido, a ser
     * removido.
     * @return Um booleano, true se a remoção foi bem-sucedida, false se não.
     */
    public boolean removeIngresso(int assento) {
        if (ingressos[assento] != null ) {
            ingressos[assento] = null;
            vendidos--;
            return true;
        }
        else return false;
    }
    
}
