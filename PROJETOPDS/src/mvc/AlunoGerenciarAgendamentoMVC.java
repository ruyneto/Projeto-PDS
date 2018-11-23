/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.AlunoGerenciarAgendamentoControle;
import model.Aluno;
import view.AlunoGerenciarAgendamentoTela;

/**
 *
 * @author sandr
 */
public class AlunoGerenciarAgendamentoMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Aluno aluno = new Aluno("111.111.111-11", "Sandro");
        AlunoGerenciarAgendamentoTela tela = new AlunoGerenciarAgendamentoTela();
        AlunoGerenciarAgendamentoControle controle = new AlunoGerenciarAgendamentoControle(aluno, tela);
        tela.setVisible(true);
    }
}