/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.CabecalhoUsuarioControle.listenerCombo;
import dao.LoginDAO;
import dao.MonitorDAO;
import dao.UsuarioDAO;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import model.Aluno;
import model.Coordenador;
import model.Monitor;
import model.Usuario;
import mvc.AlunoGerenciarAgendamentoMVC;
import mvc.LoginMVC2;
import view.AlterarSenhaTela;
import view.AlunoGerenciarAgendamentoTela;
import view.CabecalhoUsuarioComponente;
import view.CoordenadorOpcoes;
import view.MonitorReservarHorarioTela;


/**
 *
 * @author IzaltinoNeto
 */
public class CabecalhoUsuarioControle {
    private Usuario usuario;
    private CabecalhoUsuarioComponente tela;
    private JFrame telaPrincipal;
    private ItemListener combo;
    private int contador = 0;
    public CabecalhoUsuarioControle(Usuario usuario, CabecalhoUsuarioComponente tela, JFrame telaprincipal) {
        contador = 0;
        this.usuario = usuario;
        this.tela = tela;
        this.telaPrincipal = telaprincipal;
        preencherComboBox();
        this.combo = new listenerCombo();
        tela.getComboTipo().addItemListener(this.combo);
        tela.getNome().setText(usuario.getNome());
//        tela.getComboTipo().addActionListener(l);
        tela.getItemAlterarSenha().addActionListener(new listenerAlterarSenha());
        tela.getItemLogout().addActionListener(new listenerLogout());
    
    }
    
     private void preencherComboBox(){
         
        tela.getComboTipo().removeAllItems();
        UsuarioDAO dao =new UsuarioDAO();
        //verifica quais os tipos de usuario do usuario logado
        Vector v = dao.TipoDoUsuario(usuario);
        //carrega os tipos de usuario no combobox
        if(v.get(1)!=null){
            tela.getComboTipo().addItem("Aluno");
        }
        if(v.get(2)!=null){
            tela.getComboTipo().addItem("Coordenador");
        }
        if(v.get(3)!=null){
            tela.getComboTipo().addItem("Monitor");
        }
        //seleciona o modulo do tipo de usuario atual
        if(usuario instanceof Aluno){tela.getComboTipo().setSelectedItem("Aluno");
                System.out.println("É Aluno");
                };
        if(usuario instanceof Coordenador){tela.getComboTipo().setSelectedItem("Coordenador");
                 System.out.println("É Coordenador");
                }
                ;
        if(usuario instanceof Monitor){tela.getComboTipo().setSelectedItem("Monitor");
                 System.out.println("É monitor");
                };
        
     }
    
    class listenerCombo implements ItemListener {
    
        @Override
        public void itemStateChanged(ItemEvent e) {
            System.out.println("Item Mudado: "+tela.getComboTipo().getSelectedItem());
           if(tela.getComboTipo().getSelectedItem() != null && contador==0){ 
            System.out.println("Entrou no IF");
                if(tela.getComboTipo().getSelectedItem() == "Aluno" && !(usuario instanceof Aluno)){
            System.out.println("Entrou no IF do ALUNO");
            
            if(usuario instanceof Aluno)System.out.println("Aluno: "+usuario);
            if(usuario instanceof Monitor)System.out.println("Monitor: "+usuario);
            System.out.println("Usuario: "+usuario);
                    
                    telaPrincipal.dispose();
                    AlunoGerenciarAgendamentoTela view = new AlunoGerenciarAgendamentoTela();
                       Aluno alu = new Aluno(usuario.getCpf(), usuario.getNome());
                       new AlunoGerenciarAgendamentoControle(alu, view);
                       contador = 1;
                       view.setVisible(true);
                    tela.getBotaoOpcoes().removeItemListener(combo);
                }
                else if(tela.getComboTipo().getSelectedItem() == "Coordenador"&& !(usuario instanceof Coordenador)){
                        
                }
                else if(tela.getComboTipo().getSelectedItem() == "Monitor"&& !(usuario instanceof Monitor)){
                       telaPrincipal.dispose();
                       MonitorReservarHorarioTela view = new MonitorReservarHorarioTela();
                       System.out.println(usuario.getCpf());
                       Monitor mon = new MonitorDAO().consultaMonitor(usuario.getCpf());
                       new MonitorReservarHorarioControle(mon, view);
                       contador = 1;
                       view.setVisible(true);
                }
           }
            
        }
    
    }
    
    class listenerComboBox implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
          //String itemSelecionado = (String) tela.getComboTipo().getSelectedItem();
            //switch(itemSelecionado){
              //  case "Aluno": 
            //}
               
        }
    
    }
    class listenerAlterarSenha implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
         AlterarSenhaTela view= new AlterarSenhaTela();
         new AlterarSenhaControle(usuario, view);
         view.setVisible(true);
        }
    
    }
    
        class listenerLogout implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
          telaPrincipal.dispose();
          LoginMVC2.main(null);
        }
    
    }
}
