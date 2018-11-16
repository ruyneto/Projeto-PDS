/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import control.LoginControle2;
import view.LoginTela2;


/**
 *
 * @author sandr
 */
public class LoginMVC2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LoginTela2 tela = new LoginTela2();
        new LoginControle2(tela);
        tela.setVisible(true);
    }
}