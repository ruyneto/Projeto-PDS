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
public class Monitor {
    private String cpf, nome;
    private Materia materia;

    public Monitor(String cpf, String nome, Materia materia) {
        this.cpf = cpf;
        this.nome = nome;
        this.materia = materia;
    }
    
    public Monitor(){
        
    }
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
    
    @Override
    public String toString(){
        return nome;
    }
    
}
