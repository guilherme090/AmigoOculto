/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amigooculto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 *
 * @author Guilherme
 */

public class Participante {
    private String name;
    private Integer id; // chave primaria
    private Participante amigo; // chave estrangeira: a pessoa sorteada
    private Boolean sorteado;

    public String getName(){
    return name;
    }
    
    public Integer getId(){
    return id;
    }
    
    public Participante getAmigo(){
    return amigo;
    }
    
    public Boolean getSorteado(){
    return sorteado;
    }
    
    public void setName(String name){
    this.name = name;
    }
    
    public void setId(Integer id){
    this.id = id;
    }
    
    public void setAmigo(Participante amigo){
    this.amigo = amigo;
    }
    
    public void setSorteado(Boolean sorteado){
    this.sorteado = sorteado;
    }    
    
/*
public void gerarTxt()
    entradas: void
    saidas: void
    Cria um bloco de notas com o nome da pessoa, uma breve mensagem e o nome de
    seu amigo oculto
*/    
    
    public void  gerarTxt(boolean naoMeTirei, boolean quemEuTireiNaoMeTirou,
                            boolean ciclico){
    try{
    // Abrir arquivo com o nome do participante
        File file = new File(name + ".txt"); // current file
            // Criar novo, se o arquivo nao existir.
            if (!file.exists()) {
                file.createNewFile();
            }
            // File writer element
            FileWriter fWritter = new FileWriter(file.getAbsoluteFile());
        // Escrever nome do participante e um recado.
        try ( // Buffered writer element
            BufferedWriter bWritter = new BufferedWriter(fWritter)) {
            // Escrever nome do participante e um recado.
            bWritter.write("*************************************************");            
            bWritter.newLine(); // move to the next line
            bWritter.write(name);
            bWritter.newLine(); // move to the next line  
            bWritter.write("*************************************************");            
            bWritter.newLine(); // move to the next line            
            bWritter.newLine(); // move to the next line
            bWritter.write("Amigo oculto: " + amigo.name);
            bWritter.newLine();
            bWritter.newLine();
            bWritter.write("Condicoes de sorteio:");
            bWritter.newLine();
            bWritter.write("(");
                if(naoMeTirei) bWritter.write("X");
                else bWritter.write(" ");
                bWritter.write(") 'Nao me tirei'.");
                bWritter.newLine();
                bWritter.write("(");
                if(quemEuTireiNaoMeTirou) bWritter.write("X");
                else bWritter.write(" ");
                bWritter.write(") 'Quem eu tirei nao me tirou'."); 
                bWritter.newLine();
                bWritter.write("(");
                if(ciclico) bWritter.write("X");
                else bWritter.write(" ");
                bWritter.write(") 'Ciclico'");
                bWritter.newLine();
            
            bWritter.write("Sorteio realizado em: " + LocalDateTime.now());
            //close the buffer to flush it to disk.
            bWritter.close();
        }
     } catch (IOException exc) {
            System.out.println("There was a mistake with input data.");
        } catch (NullPointerException exc2){
            System.out.println("Nao ha amigos relacionados ao participante " + 
                name + ".");
        }
    }
}
