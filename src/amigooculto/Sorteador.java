package amigooculto;

import java.util.Random;

public class Sorteador {
    private static Sorteador instancia = null;
    protected final int MAX_PARTICIPANTES = 20;
    private Participante [] lista; 
    private int numParticipantes = 0;
    private boolean naoMeTirei;
    private boolean quemEuTireiNaoMeTirou;
    private boolean ciclico;

    private Sorteador(){
        naoMeTirei = true;
        quemEuTireiNaoMeTirou = false;
        ciclico = false;
        //Criar lista
        lista = new Participante [MAX_PARTICIPANTES]; 
    }

    public static Sorteador getSorteador(){
        if(instancia == null){
            instancia = new Sorteador();
        }
        return instancia;
    }
    
    public void imprimirParticipantes() {
        // exibe participantes carregados
        System.out.println("\n\n<Participantes registrados (" + 
            numParticipantes +")>:");
        imprimirParticipantes(lista, numParticipantes); 
    }
    
    /*
    private void imprimirParticipantes(lista, numero)
        Entradas: Participante[] lista (vetor a ser percorrido)
                Integer numero (numero de participantes)
        Saidas: void
        Escreve o nome de cada participante dentro de um vetor Participante
        */
        private void imprimirParticipantes(Participante [] aImprimir, Integer
            numero){
                for(int i=0; i < numero; i++){
            if (aImprimir[i] != null){
                System.out.println(aImprimir[i].getName());
            }
        }
        //Adicionar nova linha para menu
        System.out.print("\n");
    }
    
    public void sortear() {
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
    }

    /*
    private void sorteio (Participante[] lista)
        Entradas: Participantes[] lista - o vetor com as classes Participante
        Saidas: void
        Realiza sorteio, depois checa cada uma das condicoes ativadas.
    */
    private void sorteio (Participante [] lista){
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
    private boolean checarNaoMeTirei(Participante[] lista)
        Entradas: Participantes[] lista - o vetor com as classes Participante
        Saidas: boolean - true, caso nenhum dos participantes tenham tirado a si
                                proprios
                        false, caso contrario
    */
    private boolean checarNaoMeTirei(Participante[] lista){
        for(int i=0; i < numParticipantes; i++ ){
            if(lista[i].getName().equals(lista[i].getAmigo().getName()))
                return false;
        }
        return true;
    }

    /*
    private boolean checarQuemEuTireiNaoMeTirou(Participante[] lista)
        Entradas: Participantes[] lista - o vetor com as classes Participante
        Saidas: boolean - true, caso nenhum dos participantes tenham tirado um amigo
                                que tirou o participante em questao.
                        false, caso contrario
    */
    private boolean checarQuemEuTireiNaoMeTirou(Participante[] lista){
        for(int i=0; i < numParticipantes; i++ ){
            if(lista[i].getName().equals(lista[i].getAmigo().getAmigo().getName()))
                return false;
        }
        return true;
    }

    /*
    private boolean checarCiclico(Participante[] lista)
        Entradas: Participantes[] lista - o vetor com as classes Participante
        Saidas: boolean - true, caso os participantes se arranjaram com seus amigos
                                ocultos de forma ciclica.
                        false, caso contrario
    */
    private boolean checarCiclico(Participante[] lista){
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

    /*
    * Getters e Setters
     * */

    public int getNumParticipantes(){
        return numParticipantes;
    }

    public void setNumParticipantes(int value){
        numParticipantes = value;
    } 
        
    public Participante[] getLista(){
        return lista;
    }

    public void setLista(Participante[] value){
        lista = value;
    } 

    public boolean getNaoMeTirei(){
        return naoMeTirei;
    }

    public void setNaoMeTirei(boolean value){
        naoMeTirei = value;
    }

    public boolean getQuemEuTireiNaoMeTirou(){
        return quemEuTireiNaoMeTirou;
    }

    public void setQuemEuTireiNaoMeTirou(boolean value){
        quemEuTireiNaoMeTirou = value;
    }

    public boolean getCiclico(){
        return ciclico;
    }

    public void setCiclico(boolean value){
        ciclico = value;
    }

}
