/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author sandr
 */
public class Aluno extends Usuario{
    
    public Aluno(){
        
    }
    
    public Aluno(String cpf, String nome) {
        setCpf(cpf);
        setNome(nome);
    }
}
