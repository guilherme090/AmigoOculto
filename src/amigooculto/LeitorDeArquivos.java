package amigooculto;

import java.io.*;
import java.util.Scanner;

public class LeitorDeArquivos {

    /*
    public static Integer carregarDados()
        Entradas: void
        Saidas: Integer numParticipantes (retorna valor de entradas obtidas do txt)
            Procura um txt na pasta raiz com o nome entrada e carrega para o 
            programa os participantes do amigo oculto
    */  
    public static Integer carregarDados(Participante[] lista){
        Scanner currentFile = null;
        int numParticipantes = 0;
        try { 
                currentFile = new Scanner(new File("entrada.txt"));
            }
            catch (FileNotFoundException exc) {
                System.out.println("Arquivo de entrada nao encontrado.");
                System.exit(1);
            } 
        while (currentFile.hasNext()){
            String input = null;  //temporary input strings
            input = currentFile.nextLine();   //get the next line
            Scanner pieces = new Scanner (input); //break it into pieces
            pieces.useDelimiter("/");
            while (pieces.hasNext()){ // cria novo participante
                lista[numParticipantes] = new Participante();
                lista[numParticipantes].setId(numParticipantes);
                lista[numParticipantes].setName(pieces.next());
                numParticipantes++;
            }
        } 
        for (int i = numParticipantes; i < Index.MAX_PARTICIPANTES; i++){
            lista[i] = null;
        }
        return numParticipantes;
    }

    /*
    public static void gerarBlocoDeNotas(Participante[] lista)
        Entradas: Participantes[] lista - o vetor com as classes Participante
        Saidas: void
            Aplica a cada Participante o metodo de geracao de Txt.
    */
    public static void gerarBlocoDeNotas(Participante[] lista, 
        boolean naoMeTirei, boolean quemEuTireiNaoMeTirou,
        boolean ciclico){
        
            for(int i=0; i < lista.length; i++ ){
            if (lista[i] == null) return;
            lista[i].gerarTxt(naoMeTirei, quemEuTireiNaoMeTirou, ciclico);
        }     
    }  
}
