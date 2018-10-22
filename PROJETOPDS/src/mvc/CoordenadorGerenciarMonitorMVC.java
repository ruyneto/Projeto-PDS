/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.CoordenadorGerenciarMonitoresControle;
import view.CoordenadorGerenciarMonitoresTela;

/**
 *
 * @author sandr
 */
public class CoordenadorGerenciarMonitorMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CoordenadorGerenciarMonitoresTela tela = new CoordenadorGerenciarMonitoresTela();
        CoordenadorGerenciarMonitoresControle controle = new CoordenadorGerenciarMonitoresControle(tela);
        tela.setVisible(true);
    }
    
}
