/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.CoordenadorCadastrarMonitoriaControle;
import view.CoordenadorCadastrarMonitoriaTela;

/**
 *
 * @author sandr
 */
public class CoordenadorCadastrarMonitoriaMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CoordenadorCadastrarMonitoriaTela tela = new CoordenadorCadastrarMonitoriaTela();
        CoordenadorCadastrarMonitoriaControle controle = new CoordenadorCadastrarMonitoriaControle(tela);
        tela.setVisible(true);
    }
}
