/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.MateriaDAO;
import dao.SalaDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.Coordenador;
import model.Materia;
import model.Monitoria;
import model.Sala;
import mvc.CoordenadorAlterarMateriasMVC;
import mvc.CoordenadorAlterarSalasMVC;
import mvc.CoordenadorCadastrarMateriasMVC;
import mvc.CoordenadorCadastrarSalasMVC;
import util.Alert;
import view.CoordenadorPesquisarMonitoriaTela;
import view.CoordenadorVisualizarMateriasTela;
import view.CoordenadorVisualizarSalasTela;

/**
 *
 * @author Izaltino
 */
public class CoordenadorVisualizarSalasControle {
    
    private Vector<Sala> salas;
    private CoordenadorVisualizarSalasTela tela;
    private int linhaSelecionada;
    private Coordenador coordenador;
    
    public CoordenadorVisualizarSalasControle(CoordenadorVisualizarSalasTela tela, Coordenador coordenador) {
        this.tela = tela;
        TableModel modelo = consultarSalas("");
        this.coordenador = coordenador;
        
        new CabecalhoUsuarioControle(coordenador, tela.getcCabecalhoUsuarioComponente(), tela);
        this.tela.getTabelaSalas().setModel(modelo);
        this.tela.getCpSala().addKeyListener(new listenerCpSala());
        this.tela.getBtCadastrar().addActionListener(new listenerBtCadastrar());
        this.tela.getTabelaSalas().addMouseListener(new MouselistenerTableModel());
        this.tela.getTabelaSalas().addFocusListener(new FocusListenerTableModel());
        this.tela.addWindowFocusListener(new WindowFocusListenerTela());
                
        this.tela.getbtAlterar().addActionListener(new listenerBtAlterar());
        this.tela.getbtExcluir().addActionListener(new listenerBtExcluir());
        tela.getBtVoltar().addActionListener(new listenerBtVoltar());
    }
    
    class WindowFocusListenerTela implements WindowFocusListener{

        
        @Override
        public void windowGainedFocus(WindowEvent e) {
           
            TableModel modelo = consultarSalas("");
            tela.getTabelaSalas().setModel(modelo);
            tela.getCpSala().setText("");
        
        }

        @Override
        public void windowLostFocus(WindowEvent e) {
        }
    }
            
    public TableModel consultarSalas(String texto){
    SalaDAO dao = new SalaDAO();
    this.salas = dao.pesquisarSala(texto);
    
    
            
            Vector conjuntoLinhas = new Vector();
            
               
                for(Sala c: this.salas){
                       
                     Vector linha = new Vector();

                     linha.add(c.getNome());
                     if(c.getAtiva()==1)
                        linha.add("SIM");
                     else linha.add("NÃO");
                     
                     conjuntoLinhas.add(linha);
                }
                    
            Vector conjuntoColunas = new Vector();
            conjuntoColunas.add("Nome"); 
            conjuntoColunas.add("Ativa?"); 
            
            DefaultTableModel modeloTabela = 
                    new DefaultTableModel(conjuntoLinhas, conjuntoColunas);
            return modeloTabela;
    }
    
    
    
    class listenerCpSala implements KeyListener{
        

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
           TableModel modelo = consultarSalas(tela.getCpSala().getText());
            tela.getTabelaSalas().setModel(modelo);
        
        }

        @Override
        public void keyReleased(KeyEvent e) {
        TableModel modelo = consultarSalas(tela.getCpSala().getText());
            tela.getTabelaSalas().setModel(modelo);
        
        }

    
    }

    class listenerBtCadastrar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
            CoordenadorCadastrarSalasMVC.main(null);
            
        }
    
    }

    class MouselistenerTableModel implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            linhaSelecionada = tela.getTabelaSalas().getSelectedRow();
            System.out.println("index;"+linhaSelecionada);
            if(tela.getTabelaSalas().isCellSelected(linhaSelecionada, 1)){
            tela.getbtAlterar().setEnabled(true);
            tela.getbtExcluir().setEnabled(true);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

    class FocusListenerTableModel implements FocusListener{

        @Override
        public void focusGained(FocusEvent e) {
            if(!tela.getTabelaSalas().isCellSelected(linhaSelecionada,1)){
                tela.getbtAlterar().setEnabled(false);
                tela.getbtExcluir().setEnabled(false);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            
            if(!tela.getTabelaSalas().isCellSelected(linhaSelecionada,1)){
                tela.getbtAlterar().setEnabled(false);
                tela.getbtExcluir().setEnabled(false);
            }
        }
    }
    
    class listenerBtAlterar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
           CoordenadorAlterarSalasMVC.main( salas.get(linhaSelecionada));
            tela.getbtAlterar().setEnabled(false);
            tela.getbtExcluir().setEnabled(false);
                 
        }
    }

    class listenerBtExcluir implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(Alert.salaAlertaExclusao(salas.get(linhaSelecionada))==0){
                SalaDAO dao = new SalaDAO();
                if(!dao.deletarSala(salas.get(linhaSelecionada))){
                    JOptionPane.showMessageDialog(tela, "Erro ao deletar. Contate o Administrador");
                }
                linhaSelecionada = -1;
                tela.getbtAlterar().setEnabled(false);
                tela.getbtExcluir().setEnabled(false);
            }
        
        }
    }
    class listenerBtVoltar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
          tela.dispose();
                   CoordenadorPesquisarMonitoriaTela view = new CoordenadorPesquisarMonitoriaTela();
                   new CoordenadorPesquisarMonitoriaControle(view, new Monitoria(), coordenador);
                   view.setVisible(true);
        }
        
    }
}

