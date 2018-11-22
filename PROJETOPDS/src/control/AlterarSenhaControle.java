/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.UsuarioDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Usuario;
import view.AlterarSenhaTela;

/**
 *
 * @author IzaltinoNeto
 */
public class AlterarSenhaControle {
   private Usuario usuario;
   private AlterarSenhaTela tela;

    public AlterarSenhaControle(Usuario usuario, AlterarSenhaTela tela) {
        this.usuario = usuario;
        this.tela = tela;
        
        tela.getNomeUsuario().setText(usuario.getNome());
        tela.getBtConcluir().addActionListener(new listenerConcluir());
    }
   
    class listenerConcluir implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(tela.getSenha().getText().equals(tela.getSenha2().getText())){
                UsuarioDAO dao = new UsuarioDAO();
                Usuario user = new Usuario(usuario.getCpf(), usuario.getNome(), tela.getSenha().getText());
                if(dao.alterarSenha(user)) JOptionPane.showMessageDialog(null,
                "Senha alterada com sucesso.");
                tela.dispose();
                
            }
            else {
                JOptionPane.showMessageDialog(null,
                    "As senhas não conferem.");
            }
        }
    }
    
}
