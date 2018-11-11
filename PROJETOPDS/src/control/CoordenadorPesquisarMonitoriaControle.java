/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.MonitoriaDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Vector;
import javax.swing.JOptionPane;
import model.Monitoria;
import view.CoordenadorCadastrarMonitoriaTela;
import view.CoordenadorPesquisarMonitoriaTela;
import view.tableModels.MonitoriaTableModel;

/**
 *
 * @author Izaltino
 */
public class CoordenadorPesquisarMonitoriaControle {
    private CoordenadorPesquisarMonitoriaTela tela;
    private Monitoria modelo;
    private Vector<Monitoria> monitorias;

    public CoordenadorPesquisarMonitoriaControle(CoordenadorPesquisarMonitoriaTela tela, Monitoria modelo) {
        this.tela = tela;
        this.modelo = modelo;
        listar("_");
        tela.addWindowFocusListener(new GanedFocus());
        tela.getTabela().addMouseListener(new MouseListenerTabela());
        tela.getBtPesquisar().addActionListener(new BtPesquisar());
        tela.getBtCadastar().addActionListener(new BtCadastrar());
        tela.getBtExcluir().addActionListener(new BtExcluir());
        tela.getBtExcluir().setEnabled(false);
    }
    
    public void listar(String str){
        MonitoriaDAO dao = new MonitoriaDAO();
        monitorias = dao.consultarMonitoriaCoord(str);
        System.out.println(monitorias.size());
        
        tela.getTabela().setModel(new MonitoriaTableModel(monitorias));
    }
    
    class BtCadastrar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            CoordenadorCadastrarMonitoriaTela a = new CoordenadorCadastrarMonitoriaTela();
            new CoordenadorCadastrarMonitoriaControle(a);
            a.setVisible(true);
        }
        
    }
    
    class BtExcluir implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            int num = tela.getTabela().getSelectedRow();
            Monitoria m = monitorias.get(num);
            if(m.getMonitor().getNome().equals("Indefinido")){
                new MonitoriaDAO().excluirMonitoria(m.getId());
                listar("_");
                tela.getTabela().clearSelection();
                tela.getBtExcluir().setEnabled(false);
            }
            else{
                JOptionPane.showMessageDialog(tela, "Você não pode excluir esse horário."
                                                    + "\nUm monitor já o reservou!!");
                tela.getBtExcluir().setEnabled(false);
            }
        }
        
    }
    
    class BtPesquisar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            listar(tela.getCpPesquisa().getText());
        }
        
    }
    
    class MouseListenerTabela implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent me) {
        }

        @Override
        public void mousePressed(MouseEvent me) {
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            if(tela.getTabela().getSelectedRow()>=0){
                tela.getBtExcluir().setEnabled(true);
            }
        }

        @Override
        public void mouseEntered(MouseEvent me) {
        }

        @Override
        public void mouseExited(MouseEvent me) {
        }
    
}
    
    class GanedFocus implements WindowFocusListener{

        @Override
        public void windowGainedFocus(WindowEvent we) {
            listar("_");
        }

        @Override
        public void windowLostFocus(WindowEvent we) {
        }
        
    }
    
}
