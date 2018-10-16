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
import java.util.Vector;
import model.Monitoria;
import view.CoordenadorMonitoriaCadastrarTela;
import view.CoordenadorMonitoriaPesquisarTela;
import view.MonitoriaTableModel;

/**
 *
 * @author Izaltino
 */
public class CoordenadorPesquisaMonitoriaControle {
    private CoordenadorMonitoriaPesquisarTela tela;
    private Monitoria modelo;
    private Vector<Monitoria> monitorias;

    public CoordenadorPesquisaMonitoriaControle(CoordenadorMonitoriaPesquisarTela tela, Monitoria modelo) {
        this.tela = tela;
        this.modelo = modelo;
        listar("_");
        tela.getCpPesquisa().addKeyListener(new PesquisaAutomatica());
        tela.getBtCadastar().addActionListener(new BtSalvar());
    }
    
    public void listar(String str){
        MonitoriaDAO dao = new MonitoriaDAO();
        monitorias = dao.consultarMonitoria(str,1);
        Vector linhas = new Vector();
        System.out.println(monitorias.size());
        
        tela.getTabela().setModel(new MonitoriaTableModel(monitorias));
    }
    
    class PesquisaAutomatica extends KeyAdapter{
        public void keyReleased(KeyEvent ke){
            listar(tela.getCpPesquisa().getText());
        }
    }
    
    class BtSalvar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            new CoordenadorCadastroMonitoriaControle(new CoordenadorMonitoriaCadastrarTela());
        }
        
    }
    
}
