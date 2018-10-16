/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.CoordenadorCadastroMonitoriaControle;
import view.CoordenadorMonitoriaCadastrarTela;

/**
 *
 * @author sandr
 */
public class CoordenadorMonitoriaCadastroMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CoordenadorMonitoriaCadastrarTela tela = new CoordenadorMonitoriaCadastrarTela();
        CoordenadorCadastroMonitoriaControle controle = new CoordenadorCadastroMonitoriaControle(tela);
        tela.setVisible(true);
    }
}
