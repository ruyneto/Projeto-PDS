/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.CoordenadorPesquisaMonitoriaControle;
import model.Monitoria;
import view.CoordenadorMonitoriaPesquisarTela;

/**
 *
 * @author sandr
 */
public class CoordenadorPesquisaMonitoriaMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Monitoria modelo = new Monitoria();
        CoordenadorMonitoriaPesquisarTela tela = new CoordenadorMonitoriaPesquisarTela();
        CoordenadorPesquisaMonitoriaControle controle = new CoordenadorPesquisaMonitoriaControle(tela, modelo);
        tela.setVisible(true);
        
    }
    
}
