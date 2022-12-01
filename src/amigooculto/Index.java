/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amigooculto;

/**
 *
 * @author Guilherme
 */

import java.util.Scanner;
import java.util.Random;

public class Index {
    protected static final int MAX_PARTICIPANTES = 20;
    private static Participante [] lista; 
    private static int numParticipantes = 0;
    private static final int MAX_MENU_OPCOES = 7;
    private static boolean naoMeTirei = true;
    private static boolean quemEuTireiNaoMeTirou = false;
    private static boolean ciclico = false;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Criar lista
        lista = new Participante [MAX_PARTICIPANTES];        
        try (Scanner keyboard = new Scanner (System.in)) {
            System.out.println("Sistema gerador de combinacoes para amigo oculto.");       
            int userChoseParsed = 0;
            while (userChoseParsed != MAX_MENU_OPCOES){ //opcao != 'Sair'
                userChoseParsed = 0;
                while (userChoseParsed < 1 || userChoseParsed > MAX_MENU_OPCOES ){
                    String userChose = "";
                    try{
                        userChoseParsed = Integer.parseInt(userChose);
                    } catch (NumberFormatException exc){
                    userChoseParsed = 0;
                    }
                    // exibe participantes carregados
                    System.out.println("\n\n<Participantes carregados (" + 
                            numParticipantes +")>:");
                    imprimirParticipantes(lista, numParticipantes); 
                    // exibe menu
                    System.out.println("MENU:");
                    System.out.println("1. Carregar participantes de "
                            + "'entrada.txt'.");
                    // registrar quais condicoes estao aplicadas no momento 
                    System.out.print("2. (");
                    if(naoMeTirei) System.out.print("X");
                    else System.out.print(" ");
                    System.out.println(") Condicao de sorteio: "
                            + "'Nao me tirei' (alternar).");
                    System.out.print("3. (");
                    if(quemEuTireiNaoMeTirou) System.out.print("X");
                    else System.out.print(" ");
                    System.out.println(") Condicao de sorteio: 'Quem eu tirei nao "
                            + "me tirou' (alternar)."); 
                    System.out.print("4. (");
                    if(ciclico) System.out.print("X");
                    else System.out.print(" ");
                    System.out.println(") Condicao de sorteio: 'Ciclico' "
                            + "(alternar)."); 
                    // -----------
                    System.out.println("5. Realizar sorteio.");
                    System.out.println("6. Gerar arquivos de saida.");
                    System.out.println("7. Sair.");

                    userChose = keyboard.next();
                    keyboard.nextLine();
                    System.out.println("");

                // Catch the exception if the answer is not numeric.               
                    try{
                        userChoseParsed = Integer.parseInt(userChose);
                    } catch (NumberFormatException exc){
                        userChoseParsed = 0;
                    }
                }       
                switch(userChoseParsed){
                    case 1:
                        numParticipantes = LeitorDeArquivos.carregarDados(lista);
                        System.out.println(numParticipantes + " participantes "
                            + "carregados com sucesso.");
                        break;
                    case 2:
                        alternarNaoMeTirei();
                        break;
                    case 3:
                        alternarQuemEuTireiNaoMeTirou();
                        break;
                    case 4:
                        alternarCiclico();
                        break;    
                    case 5:
                        if (numParticipantes < 2) {
                            System.out.println("Numero insuficiente de participantes"
                                    + " carreagdos para o programa");
                            break;
                        }
                        int numSorteios = 1;
                        sorteio(lista);
                        while ((naoMeTirei == true && checarNaoMeTirei(lista) == 
                                false) || (quemEuTireiNaoMeTirou == true && 
                                checarQuemEuTireiNaoMeTirou(lista) == false) ||
                                (ciclico == true && checarCiclico(lista) == false)){
                            numSorteios++;
                            sorteio(lista);
                        }
                        System.out.println(numSorteios + " sorteios realizados "
                                + "ate um resultado valido.");
                        break;
                    case 6:
                        LeitorDeArquivos.gerarBlocoDeNotas(lista, naoMeTirei, quemEuTireiNaoMeTirou, ciclico);
                        break;
                    default:
                        
                }        
            }
        }
        
    }

/*
private static void imprimirParticipantes(lista, numero)
    Entradas: Participante[] lista (vetor a ser percorrido)
              Integer numero (numero de participantes)
    Saidas: void
        Escreve o nome de cada participante dentro de um vetor Participante
 */
    private static void imprimirParticipantes(Participante [] aImprimir, Integer
            numero){
        for(int i=0; i < numero; i++){
            if (aImprimir[i] != null){
                System.out.println(aImprimir[i].getName());
            }
        }
        //Adicionar nova linha para menu
        System.out.print("\n");
    }
    
/*
private static void alternarNaoMeTirei()
    Entradas: void
    Saidas: void
        Alterna parametro de sorteio 'Nao Me Tirei'. O sorteio sera repetido
        caso qualquer instancia de participante seja igual ao seu atributo
        'amigo'
 */
    private static void alternarNaoMeTirei(){
        naoMeTirei = !naoMeTirei;
    }

/*
private static void alternarQuemEuTireiNaoMeTirou()
    Entradas: void
    Saidas: void
        Alterna parametro de sorteio 'Quem Eu Tirei Nao Me TIrou'. O sorteio 
        sera repetido caso qualquer instancia de participante possua atributo 
        'amigo' cujo atributo 'amigo' seja igual a instancia.
 */
    private static void alternarQuemEuTireiNaoMeTirou(){
        quemEuTireiNaoMeTirou = !quemEuTireiNaoMeTirou;
        if (quemEuTireiNaoMeTirou == false) ciclico = false;
    }    

/*
private static void alternarQuemEuTireiNaoMeTirou()
    Entradas: void
    Saidas: void
        Alterna parametro de sorteio 'Quem Eu Tirei Nao Me TIrou'. O sorteio 
        sera repetido caso qualquer instancia de participante possua atributo 
        'amigo' cujo atributo 'amigo' seja igual a instancia.
 */
    private static void alternarCiclico(){
        ciclico = !ciclico;
        if (ciclico == true) quemEuTireiNaoMeTirou = true;
    }
    
/*
private static void sorteio (Participante[] lista)
    Entradas: Participantes[] lista - o vetor com as classes Participante
    Saidas: void
    Realiza sorteio, depois checa cada uma das condicoes ativadas.
*/
private static void sorteio (Participante [] lista){
    // Limpar todos os sinais de sorteado
    for (int s=0; s < numParticipantes; s++){
        lista[s].setSorteado(false);
    }
    if (numParticipantes < 2) return; 
    Random dado = new Random();
    int j; //cursor para percorrer o arranjo de participantes 
    for (int i=0; i < numParticipantes; i++){
        int posicao = dado.nextInt(numParticipantes - i);
        for (j=0; posicao >= 0 ; j++){
            if (lista[j].getSorteado() == false){
                if (posicao == 0){
                    lista[i].setAmigo(lista[j]);
                    lista[j].setSorteado(true);
                }
                posicao--;
            }
        }
    }
}
    
/*
private static boolean checarNaoMeTirei(Participante[] lista)
    Entradas: Participantes[] lista - o vetor com as classes Participante
    Saidas: boolean - true, caso nenhum dos participantes tenham tirado a si
                            proprios
                      false, caso contrario
*/
private static boolean checarNaoMeTirei(Participante[] lista){
    for(int i=0; i < numParticipantes; i++ ){
        if(lista[i].getName().equals(lista[i].getAmigo().getName()))
            return false;
    }
    return true;
}

/*
private static boolean checarQuemEuTireiNaoMeTirou(Participante[] lista)
    Entradas: Participantes[] lista - o vetor com as classes Participante
    Saidas: boolean - true, caso nenhum dos participantes tenham tirado um amigo
                            que tirou o participante em questao.
                      false, caso contrario
*/
private static boolean checarQuemEuTireiNaoMeTirou(Participante[] lista){
    for(int i=0; i < numParticipantes; i++ ){
        if(lista[i].getName().equals(lista[i].getAmigo().getAmigo().getName()))
            return false;
    }
    return true;
}

/*
private static boolean checarCiclico(Participante[] lista)
    Entradas: Participantes[] lista - o vetor com as classes Participante
    Saidas: boolean - true, caso os participantes se arranjaram com seus amigos
                            ocultos de forma ciclica.
                      false, caso contrario
*/
private static boolean checarCiclico(Participante[] lista){
    int cursor = 1; //vai percorrendo a lista para checar furos no ciclo
    Participante primeiroParticipante = lista[0];
    Participante atualParticipante = lista[0];
    for(int i=0; i < numParticipantes; i++ ){
        if (atualParticipante == null) return false;
        if(atualParticipante.getAmigo() == primeiroParticipante){
            cursor = i+1;
            break;
        }
        atualParticipante = atualParticipante.getAmigo();
    }
    if (cursor == numParticipantes) return true;
    return false;
}  
}