/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.LoginDAO;
import dao.MonitorDAO;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Aluno;
import model.Monitor;
import model.Usuario;
import view.AlunoGerenciarAgendamentoTela;
import view.LoginTela2;
import view.MonitorReservarHorarioTela;

/**
 *
 * @author sandr
 */
public class LoginControle2 {
    private LoginTela2 tela;

    public LoginControle2(LoginTela2 tela) {
        this.tela = tela;
        tela.getBtEntrar().addActionListener(new AcaoBtEntra());
        tela.getBtSair().addActionListener(new AcaoBtSair());
        tela.setTitle("Login");
    }
    
    public void validacao(){
        LoginDAO dao = new LoginDAO();
        String login = tela.getCpUsuario().getText();
        String senha = tela.getCpSenha().getText();
        String funcao="";
        if(tela.getRdAluno().isSelected())
            funcao = "Aluno";
        if(tela.getRdCoordenador().isSelected())
            funcao = "Coordenador";
        if(tela.getRdMonitor().isSelected())
            funcao = "Monitor";
        Usuario usu =  dao.validacao(login, senha, funcao);
        if(!senha.equals("") || !login.equals("")){
            if(usu.getCpf()!=null){
                if(funcao.equals("Aluno")){
                   tela.dispose();
                   AlunoGerenciarAgendamentoTela view = new AlunoGerenciarAgendamentoTela();
                   Aluno alu = new Aluno(usu.getCpf(), usu.getNome());
                   new AlunoGerenciarAgendamentoControle(alu, view);
                   view.setVisible(true);
                }

                if(funcao.equals("Coordenador")){
                   tela.dispose();
                   /*AlunoGerenciarAgendamentoTela view = new AlunoGerenciarAgendamentoTela();
                   Aluno alu = new Aluno(usu.getCpf(), usu.getNome());
                   new AlunoGerenciarAgendamentoControle(alu, view);
                   view.setVisible(true);*/
                }

                if(funcao.equals("Monitor")){
                   tela.dispose();
                   MonitorReservarHorarioTela view = new MonitorReservarHorarioTela();
                    System.out.println(usu.getCpf());
                   Monitor mon = new MonitorDAO().consultaMonitor(usu.getCpf());
                   new MonitorReservarHorarioControle(mon, view);
                   view.setVisible(true);
                }
            }
            else{
                Point p = tela.getLocation();
                new Thread(){
                    @Override
                    public void run() {
                        for(int i=0; i<3; i++){
                            try {
                                tela.setLocation(p.x + 10, p.y);
                                sleep(10);
                                tela.setLocation(p.x - 10, p.y);
                                tela.setLocation(p);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(LoginControle2.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    
                }.start();
                /*JOptionPane.showMessageDialog(null, "Não foi possível efetuar o login."
                                                    + "\nVerifique suas credenciais"
                                                    + "\ne o tipo de usuário");
                tela.getCpUsuario().setText("");
                tela.getCpSenha().setText("");*/
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Os campos não podem "
                                                + "estar em branco!");
        }
        
    }
    
    class AcaoBtEntra implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            validacao();
        }
    }
    
    class AcaoBtSair implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
        
    }
}
