/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Calendar;

/**
 *
 * @author sandr
 */
public class Inscricao {
    private Monitoria monitoria;
    private Aluno aluno;
    private Calendar data;

    public Inscricao(Monitoria monitoria, Aluno aluno) {
        this.monitoria = monitoria;
        this.aluno = aluno;
    }
    
    public Inscricao(){
        
    }

    public Monitoria getMonitoria() {
        return monitoria;
    }

    public void setMonitoria(Monitoria monitoria) {
        this.monitoria = monitoria;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }
       
}
