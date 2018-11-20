/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import javax.swing.JLabel;
import model.Usuario;
import view.CabecalhoUsuarioComponente;


/**
 *
 * @author IzaltinoNeto
 */
public class CabecalhoUsuarioControle {
    private Usuario usuario;
    private CabecalhoUsuarioComponente tela;

    public CabecalhoUsuarioControle(Usuario usuario, CabecalhoUsuarioComponente tela) {
        this.usuario = usuario;
        this.tela = tela;
    
        tela.getNome().setText(usuario.getNome());
    
    }
    
    
    
}
