/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.MonitoriaDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Vector;
import model.Monitoria;
import view.CoordenadorCadastrarMonitoriaTela;
import view.CoordenadorPesquisarMonitoriaTela;
import view.MonitoriaTableModel;

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
        tela.getBtPesquisar().addActionListener(new BtPesquisar());
        tela.getBtCadastar().addActionListener(new BtCadastrar());
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
    
    class BtPesquisar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            listar(tela.getCpPesquisa().getText());
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
