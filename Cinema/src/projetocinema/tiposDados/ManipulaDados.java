/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocinema.tiposDados;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luan, Guilherme
 */
public class ManipulaDados {
    
//Atributos
    private ObjectOutputStream arqGravacao;
    private ObjectInputStream arqLeitura;
    private String nomeArquivo;

//Construtores
    
    /**
     * @param nomeArquivo A String referente ao nome do arquivo a ser aberto.
     */
    public ManipulaDados(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
    
//Getters/Setters
    public ObjectOutputStream getArqGravacao() {
        return arqGravacao;
    }

    public void setArqGravacao(ObjectOutputStream arqGravacao) {
        this.arqGravacao = arqGravacao;
    }

    public ObjectInputStream getArqLeitura() {
        return arqLeitura;
    }

    public void setArqLeitura(ObjectInputStream arqLeitura) {
        this.arqLeitura = arqLeitura;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
    
//Metodos
    
    /**
     * Abre o arquivo em modo gravação.
     * @throws IOException 
     */
    public void abreArqGravacao() throws IOException {
        arqGravacao =
            new ObjectOutputStream(new FileOutputStream(nomeArquivo) );
    }
    
    /**
     * Abre o arquivo em modo leitura, se o arquivo não existir, primeiro o 
     * cria.
     * 
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void abreArqLeitura() throws FileNotFoundException, IOException {
        try {
            arqLeitura =
                new ObjectInputStream(new FileInputStream(nomeArquivo) );
        } catch (FileNotFoundException e) {
            abreArqGravacao();
            fechaArqGravacao();
            arqLeitura =
                new ObjectInputStream(new FileInputStream(nomeArquivo) );
        }
    }
    
    /**
     * Le objetos do tipo parametrizado T do arquivo e retorna uma ArrayList de
     * objetos do mesmo tipo.
     * 
     * @return Um ArrayList<T> com todos os objetos lidos;
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws RuntimeException 
     */
//    public ArrayList<T> lerArquivo() throws IOException, ClassNotFoundException,
//        RuntimeException {
//        
//        ArrayList<T> lista = new ArrayList<>();
//        try {
//            T registro;
//            while (true) {
//                registro = (T) arqLeitura.readObject();
//                lista.add(registro);
//            }
//        }
//        catch(IOException e) {
//            arqLeitura.close();
//        }
//        return lista;
//    }
    
    public Cinema leCinema() throws IOException, ClassNotFoundException,
        RuntimeException {
        Cinema cinema = null;
        try {
            cinema = (Cinema) arqLeitura.readObject();
        }
        catch(IOException ex) {
            arqLeitura.close();
        }
        return cinema;
    }
    
    /**
     * Grava uma lista de um objeto parametrizado T dentro do arquivo.
     * 
     * @param lista
     * @return Um inteiro referente a quantidade de registros gravados com 
     * sucesso no arquivo.
     * @throws IOException 
     */
//    public int gravaObjetos(List<T> lista) throws IOException{
//        int numRegistros = 0;
//        abreArqGravacao();
//        for (T t : lista) {
//            arqGravacao.writeObject(t);
//            numRegistros++;
//        }
//        if(numRegistros != lista.size())
//            throw new IOException("Não foram gravados todos os registros");
//        fechaArqGravacao();
//        return numRegistros;
//    }
    
    public void gravaCinema(Cinema cinema) throws IOException {
        arqGravacao.writeObject(cinema);
        fechaArqGravacao();
    }
    
    
    /**
     * Fecha o arquivo.
     * 
     * @throws IOException 
     */
    private void fechaArqGravacao() throws IOException {
        arqGravacao.close();
    }
}
