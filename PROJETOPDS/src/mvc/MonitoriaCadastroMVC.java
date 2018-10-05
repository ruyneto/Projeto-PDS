/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.MonitoriaCadastroControle;
import view.MonitoriaCadastrarTela;

/**
 *
 * @author sandr
 */
public class MonitoriaCadastroMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MonitoriaCadastrarTela tela = new MonitoriaCadastrarTela();
        MonitoriaCadastroControle controle = new MonitoriaCadastroControle(tela);
        tela.setVisible(true);
    }
    
}
