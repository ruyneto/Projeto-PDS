/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.CoordenadorPesquisarMonitoriaControle;
import model.Coordenador;
import model.Monitoria;
import view.CoordenadorPesquisarMonitoriaTela;

/**
 *
 * @author sandr
 */
public class CoordenadorPesquisarMonitoriaMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Monitoria modelo = new Monitoria();
        CoordenadorPesquisarMonitoriaTela tela = new CoordenadorPesquisarMonitoriaTela();
        CoordenadorPesquisarMonitoriaControle controle = new CoordenadorPesquisarMonitoriaControle(tela, modelo);
        tela.setVisible(true);
    }
    
}
