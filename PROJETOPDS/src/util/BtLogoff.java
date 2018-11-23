/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import control.LoginControle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import view.LoginTela;

/**
 *
 * @author sandr
 */
public class BtLogoff implements ActionListener{
    JFrame tela;
    public BtLogoff(JButton bt, JFrame tela){
        this.tela = tela;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tela.dispose();
        tela=null;
        LoginTela frame = new LoginTela();
        new LoginControle(frame);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}
