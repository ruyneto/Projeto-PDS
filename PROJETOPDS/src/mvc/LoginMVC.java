/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.LoginControle;
import view.LoginTela;

/**
 *
 * @author sandr
 */
public class LoginMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LoginTela tela = new LoginTela();
        new LoginControle(tela);
        tela.setVisible(true);
    }
}