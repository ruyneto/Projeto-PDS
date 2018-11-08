/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.LoginDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Aluno;
import model.Monitor;
import model.Usuario;
import view.AlunoGerenciarAgendamentoTela;
import view.LoginTela;
import view.MonitorReservarHorarioTela;

/**
 *
 * @author sandr
 */
public class LoginControle {
    private LoginTela tela;

    public LoginControle(LoginTela tela) {
        this.tela = tela;
        preencherCbFuncao();
        tela.getBtEntrar().addActionListener(new AcaoBtEntra());
    }
    
    public void validacao(){
        LoginDAO dao = new LoginDAO();
        String login = tela.getCpUsuario().getText();
        String senha = tela.getCpSenha().getText();
        String funcao = (String)tela.getCbFuncao().getSelectedItem();
        Usuario usu =  dao.validacao(login, senha, funcao);
        if(usu.getCpf()!=null){
            if(tela.getCbFuncao().getSelectedItem().equals("Aluno")){
               tela.dispose();
               AlunoGerenciarAgendamentoTela view = new AlunoGerenciarAgendamentoTela();
               Aluno alu = new Aluno(usu.getCpf(), usu.getNome());
               new AlunoGerenciarAgendamentoControle(alu, view);
               view.setVisible(true);
            }
            
            if(tela.getCbFuncao().getSelectedItem().equals("Coordenador")){
               tela.dispose();
               /*AlunoGerenciarAgendamentoTela view = new AlunoGerenciarAgendamentoTela();
               Aluno alu = new Aluno(usu.getCpf(), usu.getNome());
               new AlunoGerenciarAgendamentoControle(alu, view);
               view.setVisible(true);*/
            }
            
            if(tela.getCbFuncao().getSelectedItem().equals("Monitor")){
               tela.dispose();
               MonitorReservarHorarioTela view = new MonitorReservarHorarioTela();
               Monitor mon = new Monitor();
               mon.setNome(usu.getNome());
               mon.setCpf(usu.getCpf());
               new MonitorReservarHorarioControle(mon, view);
               view.setVisible(true);
            }
        }
    }
    
    public void preencherCbFuncao(){
        tela.getCbFuncao().removeAllItems();
        tela.getCbFuncao().addItem("Aluno");
        tela.getCbFuncao().addItem("Coordenador");
        tela.getCbFuncao().addItem("Monitor");
    }
    
    class AcaoBtEntra implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            validacao();
        }
        
    }
}
