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

public class Index {
    protected static final int MAX_PARTICIPANTES = 20; 
    private static final int MAX_MENU_OPCOES = 7;
    private static Sorteador sorteador = Sorteador.getSorteador();
    private static Participante[] lista = sorteador.getLista();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
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
                    sorteador.imprimirParticipantes();
                    
                    // exibe menu
                    System.out.println("MENU:");
                    System.out.println("1. Carregar participantes de "
                            + "'entrada.txt'.");
                    // registrar quais condicoes estao aplicadas no momento 
                    System.out.print("2. (");
                    if(sorteador.getNaoMeTirei()) System.out.print("X");
                    else System.out.print(" ");
                    System.out.println(") Condicao de sorteio: "
                            + "'Nao me tirei' (alternar).");
                    System.out.print("3. (");
                    if(sorteador.getQuemEuTireiNaoMeTirou()) System.out.print("X");
                    else System.out.print(" ");
                    System.out.println(") Condicao de sorteio: 'Quem eu tirei nao "
                            + "me tirou' (alternar)."); 
                    System.out.print("4. (");
                    if(sorteador.getCiclico()) System.out.print("X");
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
                        sorteador.setNumParticipantes(LeitorDeArquivos.carregarDados(lista));
                        System.out.println(sorteador.getNumParticipantes() + " participantes "
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
                        if (sorteador.getNumParticipantes() < 2) {
                            System.out.println("Numero insuficiente de participantes"
                                    + " carreagdos para o programa");
                            break;
                        }
                        sorteador.sortear();
                        break;
                    case 6:
                        LeitorDeArquivos.gerarBlocoDeNotas(
                            lista, sorteador.getNaoMeTirei(), sorteador.getQuemEuTireiNaoMeTirou(), sorteador.getCiclico());
                        break;
                    default:
                        
                }        
            }
        }
        
    }


    
/*
private void alternarNaoMeTirei()
    Entradas: void
    Saidas: void
        Alterna parametro de sorteio 'Nao Me Tirei'. O sorteio sera repetido
        caso qualquer instancia de participante seja igual ao seu atributo
        'amigo'
 */
    private static void alternarNaoMeTirei(){
        sorteador.setNaoMeTirei(!sorteador.getNaoMeTirei());
    }

/*
private void alternarQuemEuTireiNaoMeTirou()
    Entradas: void
    Saidas: void
        Alterna parametro de sorteio 'Quem Eu Tirei Nao Me TIrou'. O sorteio 
        sera repetido caso qualquer instancia de participante possua atributo 
        'amigo' cujo atributo 'amigo' seja igual a instancia.
 */
    private static void alternarQuemEuTireiNaoMeTirou(){
        sorteador.setQuemEuTireiNaoMeTirou(!sorteador.getQuemEuTireiNaoMeTirou());
        if (! sorteador.getQuemEuTireiNaoMeTirou()) {
            sorteador.setCiclico(false);
        }
    }    

/*
private void alternarQuemEuTireiNaoMeTirou()
    Entradas: void
    Saidas: void
        Alterna parametro de sorteio 'Quem Eu Tirei Nao Me TIrou'. O sorteio 
        sera repetido caso qualquer instancia de participante possua atributo 
        'amigo' cujo atributo 'amigo' seja igual a instancia.
 */
    private static void alternarCiclico(){
        sorteador.setCiclico(!sorteador.getCiclico());
        if (sorteador.getCiclico()) {
            sorteador.setQuemEuTireiNaoMeTirou(true);
        }
    }
}