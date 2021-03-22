/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projetocinema;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projetocinema.telas.TelaAtores;
import projetocinema.telas.TelaErro;
import projetocinema.telas.TelaFilmes;
import projetocinema.telas.TelaGeneros;
import projetocinema.telas.TelaIngressos;
import projetocinema.telas.TelaSalas;
import projetocinema.telas.TelaSessoes;
import projetocinema.telas.TelaVendeIngressos;
import projetocinema.tiposDados.ManipulaDados;
import projetocinema.tiposDados.Cinema;
import projetocinema.tiposDados.Sessao;

/**
 *
 * @author Luan, Guilherme
 */
public class TelaPrincipal extends Application {

//Atributos
    Cinema cinema;
    
    FileInputStream input;
    Image imagem;
    ImageView imageView;
    
    BorderPane root;
    
    MenuBar menu;
        Menu gerenciadores; 
        MenuItem opcoes[];
    GridPane vendeIngresso;
        TableView<Sessao> sessoesEmCartaz;
            TableColumn<Sessao, String> horarioSessao;
            TableColumn<Sessao, String> tituloFilme;
            
    ManipulaDados arqCinema = new ManipulaDados("dados/cinema.dados");


//Metodos
    @Override
    public void init() throws Exception {
        
        root = new BorderPane();
        
        criarDiretorio();

        arqCinema.abreArqLeitura();
        
        cinema = arqCinema.leCinema();
        if(cinema == null) cinema = new Cinema("Starz");
        
        iniciaMenu();
        iniciaVendaIngresso();
        
        root.setTop(menu);
        root.setCenter(vendeIngresso);
        root.setStyle("-fx-background-color: black;");
    }
    
    @Override
    public void start(Stage primaryStage) {

        Scene scene = new Scene(root);
        
        opcoes[0].setOnAction(e -> telaIngresso() );
        opcoes[1].setOnAction(e -> telaSessoes() );
        opcoes[2].setOnAction(e -> telaFilmes() );
        opcoes[3].setOnAction(e -> telaSala() );
        opcoes[4].setOnAction(e -> telaGenero() );
        opcoes[5].setOnAction(e -> telaAtor() );
        
        sessoesEmCartaz.setOnMousePressed
        (e -> {
            if(!sessoesEmCartaz.getSelectionModel().isEmpty() ) {
                Sessao selecionada =
                    sessoesEmCartaz.getSelectionModel().getSelectedItem();
                telaVendeIngresso(selecionada);
                sessoesEmCartaz.refresh();
                sessoesEmCartaz.getSelectionModel().clearSelection();
            }
        } );
        
        //A tabela é atualizada quando o ponteiro do mouse entra na área da 
        //tabela.
        sessoesEmCartaz.setOnMouseEntered(e -> atualizaTabelaSessoes());
        
        primaryStage.setTitle("Sistema de controle de Cinema");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * Inicia a barra superior dos menus de gerenciamento,
     */
    public void iniciaMenu() {
        
        String nomeOpcoes[] = {"Ingressos", "Sessões", "Filmes", "Salas",
        "Gêneros", "Atores"};
        
        menu = new MenuBar();
        opcoes = new MenuItem[nomeOpcoes.length];
        gerenciadores = new Menu("Gerenciadores");
        
        for (int i = 0; i < nomeOpcoes.length; i++) {
            opcoes[i] = new MenuItem(nomeOpcoes[i]);
            gerenciadores.getItems().add(opcoes[i]);
        }
        menu.getMenus().add(gerenciadores);
    }

    /**
     * Inicia a TableView inicial que apresenta as sessões do dia de hoje, que
     * estão disponíveis.
     */
    public void iniciaVendaIngresso() {
        
        vendeIngresso = new GridPane();
        
        try {
            input = new FileInputStream ("logo.jpg");
            imagem = new Image (input);
            imageView = new ImageView(imagem);
            vendeIngresso.add(imageView,0,0);
            imageView.setFitWidth(900);
            imageView.setFitHeight(200);
            vendeIngresso.setAlignment(Pos.TOP_CENTER);
        }
        catch(FileNotFoundException ex) {
            vendeIngresso.setAlignment(Pos.CENTER);
        }
        
        sessoesEmCartaz = new TableView<>(FXCollections.observableArrayList() );
        atualizaTabelaSessoes();
       
        horarioSessao = new TableColumn<>("Horário");
        horarioSessao.setCellValueFactory(new PropertyValueFactory<>
        ("horario") );
        
        tituloFilme = new TableColumn<>("Título");
        tituloFilme.setCellValueFactory(new PropertyValueFactory<>
        ("filme") );
        
        //Adicionando as colunas criadas na TableView
        sessoesEmCartaz.getColumns().add(horarioSessao);
        sessoesEmCartaz.getColumns().add(tituloFilme);
        
        
        horarioSessao.setPrefWidth(200);
        horarioSessao.setStyle( "-fx-alignment: CENTER;");
        
        tituloFilme.setPrefWidth(800);
        tituloFilme.setStyle( "-fx-alignment: CENTER;");
        
        sessoesEmCartaz.setPrefSize(800, 700);
        
        sessoesEmCartaz.setStyle("-fx-font: normal 16pt Arial;");
        vendeIngresso.add(sessoesEmCartaz, 0, 1);
        vendeIngresso.setVgap(6);
        vendeIngresso.setHgap(6);
        vendeIngresso.setPrefSize(1000, 1000);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        
        arqCinema.abreArqGravacao();
        arqCinema.gravaCinema(cinema);
        
        super.stop();
    }
    
    private void telaSala() {
        TelaSalas telaSala =
            new TelaSalas(cinema);
        
        telaSala.init();
        telaSala.start(chamarNovaJanela() );
    }
    
    private void telaAtor() {
        
        TelaAtores telaAtores =
            new TelaAtores(cinema);
        telaAtores.init();
        telaAtores.start(chamarNovaJanela() );
    }
    
    private void telaGenero() {
        TelaGeneros telaGenero = 
            new TelaGeneros(cinema);
        telaGenero.init();
        telaGenero.start(chamarNovaJanela() );
    }
    
    private void telaSessoes() {
        TelaSessoes telaSessao = new TelaSessoes(cinema, this);
        telaSessao.init();
        telaSessao.start(chamarNovaJanela() );
    }
    
    private void telaFilmes() {
        TelaFilmes telaFilmes = new TelaFilmes(cinema);
        telaFilmes.init();
        telaFilmes.start(chamarNovaJanela() );
    }
    
    private void telaVendeIngresso(Sessao sessao) {
        TelaVendeIngressos telaIngresso = new TelaVendeIngressos(sessao);
        try { telaIngresso.init(); }
        catch (Exception ex) { }
        telaIngresso.start(chamarNovaJanela() );
    }
    
    private void telaIngresso() {
        TelaIngressos telaIngresso = new TelaIngressos(cinema.getSessoes() );
        telaIngresso.init();
        telaIngresso.start(chamarNovaJanela() );
    }
    
    private Stage chamarNovaJanela() {
        
        Stage novaJanela = new Stage();
        novaJanela.initOwner((Stage) root.getScene().getWindow());
        novaJanela.initModality(Modality.APPLICATION_MODAL);
        return novaJanela;
    }
    
    /**
     * Atualiza as sessões disponíveis para a compra de ingressos, ou seja, as
     * que estão com a data de hoje e que ainda não começaram.
     */
    public void atualizaTabelaSessoes() {
        
        ArrayList<Sessao> arraySessoes = new ArrayList<>();
        
        //Somente são apresentadas as sessões datadas no dia atual que ainda não
        //foram encerradas.
        for (Sessao sessao : cinema.getSessoes() ) {
            
            sessao.verificaSituacao();
            if(!sessao.isEncerrada() && sessao.getData().
                compareTo(dataAtual() ) == 0) {
                arraySessoes.add(sessao);
            }   
        }
           
        sessoesEmCartaz.setItems(FXCollections.observableArrayList(arraySessoes));
        sessoesEmCartaz.refresh();
        //Definindo a ordem de sorteio (ordem de inserção no caso)
        for (int i = 0; i < sessoesEmCartaz.getColumns().size(); i++) {
            sessoesEmCartaz.getSortOrder().add
                (sessoesEmCartaz.getColumns().get(i) );
        }
    }
    
    /**
     * Retorna em formato de string formatada no modelo mm/dd/aaaa o dia de hoje
     * através do uso da classe Date.
     * @return A String formatada com a data de hoje.
     */
    public String dataAtual() {
        
        Date dataAtual = new Date();
        
        String diaAtual, mesAtual, anoAtual;

        diaAtual = Integer.toString(dataAtual.getDate() );
        diaAtual = diaAtual.length() > 1 ? diaAtual : "0" + diaAtual;

        mesAtual = Integer.toString(dataAtual.getMonth() + 1);
        mesAtual = mesAtual.length() > 1 ? mesAtual : "0" + mesAtual;

        anoAtual = Integer.toString(dataAtual.getYear() + 1900);

        return (mesAtual + "/" + diaAtual + "/" + anoAtual);
    }

    /**
     * Se não criado, cria o diretório "dados" na pasta principal do projeto,
     * pois será o diretório que conterá os arquivos de dados que serão lidos/
     * escritos.
     */
    public void criarDiretorio() {
        try {
            File diretorio = new File("dados");
            diretorio.mkdir();
        } catch (NullPointerException ex) {
            TelaErro.telaErro(ex);
            arqCinema.setNomeArquivo("cinema.dados");
        }
    }
}
