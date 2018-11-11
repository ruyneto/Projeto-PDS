/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.MateriaDAO;
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
import model.Materia;
import mvc.CoordenadorAlterarMateriasMVC;
import mvc.CoordenadorCadastrarMateriasMVC;
import util.Alert;
import view.CoordenadorVisualizarMateriasTela;

/**
 *
 * @author Izaltino
 */
public class CoordenadorVisualizarMateriasControle {
    
    private Vector<Materia> materias;
    private CoordenadorVisualizarMateriasTela tela;
    private int linhaSelecionada;
    
    public CoordenadorVisualizarMateriasControle(CoordenadorVisualizarMateriasTela tela) {
        this.tela = tela;
        TableModel modelo = consultarMaterias("");
        
        this.tela.getTabelaMaterias().setModel(modelo);
        this.tela.getCpMateria().addKeyListener(new listenerCpMateria());
        this.tela.getBtCadastrar().addActionListener(new listenerBtCadastrar());
        this.tela.getTabelaMaterias().addMouseListener(new MouselistenerTableModel());
        this.tela.getTabelaMaterias().addFocusListener(new FocusListenerTableModel());
        this.tela.addWindowFocusListener(new WindowFocusListenerTela());
                
        this.tela.getbtAlterar().addActionListener(new listenerBtAlterar());
        this.tela.getbtExcluir().addActionListener(new listenerBtExcluir());
    }
    
    class WindowFocusListenerTela implements WindowFocusListener{

        
        @Override
        public void windowGainedFocus(WindowEvent e) {
           
            TableModel modelo = consultarMaterias("");
            tela.getTabelaMaterias().setModel(modelo);
            tela.getCpMateria().setText("");
        
        }

        @Override
        public void windowLostFocus(WindowEvent e) {
        }
    }
            
    public TableModel consultarMaterias(String texto){
    MateriaDAO dao = new MateriaDAO();
    this.materias = dao.pesquisarMateria(texto);
    
    
            
            Vector conjuntoLinhas = new Vector();
            
               
                for(Materia c: materias){
                       
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
    
    
    
    class listenerCpMateria implements KeyListener{
        

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
           TableModel modelo = consultarMaterias(tela.getCpMateria().getText());
            tela.getTabelaMaterias().setModel(modelo);
        
        }

        @Override
        public void keyReleased(KeyEvent e) {
        TableModel modelo = consultarMaterias(tela.getCpMateria().getText());
            tela.getTabelaMaterias().setModel(modelo);
        
        }

    
    }

    class listenerBtCadastrar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
            CoordenadorCadastrarMateriasMVC.main(null);
            
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
            linhaSelecionada = tela.getTabelaMaterias().getSelectedRow();
            System.out.println("index;"+linhaSelecionada);
            if(tela.getTabelaMaterias().isCellSelected(linhaSelecionada, 1)){
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
            if(!tela.getTabelaMaterias().isCellSelected(linhaSelecionada,1)){
                tela.getbtAlterar().setEnabled(false);
                tela.getbtExcluir().setEnabled(false);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            
            if(!tela.getTabelaMaterias().isCellSelected(linhaSelecionada,1)){
                tela.getbtAlterar().setEnabled(false);
                tela.getbtExcluir().setEnabled(false);
            }
        }
    }
    
    class listenerBtAlterar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            CoordenadorAlterarMateriasMVC.main( materias.get(linhaSelecionada));
            tela.getbtAlterar().setEnabled(false);
            tela.getbtExcluir().setEnabled(false);
                 
        }
    }

    class listenerBtExcluir implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(Alert.materiaAlertaExclusao(materias.get(linhaSelecionada))==0){
                MateriaDAO dao = new MateriaDAO();
                if(!dao.deletarMateria(materias.get(linhaSelecionada))){
                    JOptionPane.showMessageDialog(tela, "Erro ao deletar. Contate o Administrador");
                }
                linhaSelecionada = -1;
                tela.getbtAlterar().setEnabled(false);
                tela.getbtExcluir().setEnabled(false);
            }
        }
    }
}

