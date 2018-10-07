/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.MonitoriaControle;
import model.Monitoria;
import view.MonitoriaPesquisaTela;

/**
 *
 * @author sandr
 */
public class MonitoriaPesquisaMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Monitoria modelo = new Monitoria();
        MonitoriaPesquisaTela tela = new MonitoriaPesquisaTela();
        MonitoriaControle controle = new MonitoriaControle(tela, modelo);
        tela.setVisible(true);
        
    }
    
}
