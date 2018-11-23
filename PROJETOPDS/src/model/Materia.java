/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ruyneto
 */
public class Materia {
    private int id;
    private String nome;
    private int ativa;

    public Materia(int id, String nome, int ativa) {
        this.id = id;
        this.nome = nome;
        this.ativa = ativa;
    }

    public int getAtiva() {
        return ativa;
    }

    public void setAtiva(int ativa) {
        this.ativa = ativa;
    }
    
    public Materia(int id, String nome){
        this.id = id;
        this.nome = nome;
    }
    
    public Materia(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String toString(){
        return nome;
    }
}
