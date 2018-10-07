/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.AgendamentoControle;
import model.Monitoria;
import view.AgendarPesquisaTela;

/**
 *
 * @author sandr
 */
public class MonitoriaAgendarMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Monitoria modelo = new Monitoria();
        AgendarPesquisaTela tela = new AgendarPesquisaTela();
        AgendamentoControle controle = new AgendamentoControle(tela, modelo);
        tela.setVisible(true);
    }
}
