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
public class Monitor extends Usuario{
    private Materia materia;

    public Monitor(String cpf, String nome, Materia materia) {
        super.setCpf(cpf);
        super.setNome(nome);
        this.materia = materia;
    }
    
    public Monitor(){
        
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
    
    @Override
    public String toString(){
        return getNome();
    }
    
}
