/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.AgendamentoControle;
import model.Aluno;
import model.Monitoria;
import view.AlunoVisualiazarTela;

/**
 *
 * @author sandr
 */
public class MonitoriaAgendarMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Aluno aluno = new Aluno("111.111.111-11", "Sandro");
        AlunoVisualiazarTela tela = new AlunoVisualiazarTela();
        AgendamentoControle controle = new AgendamentoControle(aluno, tela);
        tela.setVisible(true);
    }
}