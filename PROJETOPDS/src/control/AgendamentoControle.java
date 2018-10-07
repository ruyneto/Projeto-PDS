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
import javax.swing.table.DefaultTableModel;
import model.Monitoria;
import view.AgendamentoTableModel;
import view.AgendarPesquisaTela;
import view.MonitoriaCadastrarTela;
import view.MonitoriaPesquisaTela;
import view.MonitoriaTableModel;

/**
 *
 * @author Izaltino
 */
public class AgendamentoControle {
    private AgendarPesquisaTela tela;
    private Monitoria modelo;
    private Vector<Monitoria> monitorias;
    private Boolean[] check;

    public AgendamentoControle (AgendarPesquisaTela tela, Monitoria modelo) {
        this.tela = tela;
        this.modelo = modelo;
        check = new Boolean[new MonitoriaDAO().consultarMonitoria("_", 2).size()];
        for(int i=0; i<check.length; i++){
            check[i]=new Boolean(false);
        }
        System.out.println(check[0]);
        listar("_");
        tela.getCpPesquisa().addKeyListener(new PesquisaAutomatica());
        tela.getBtInscrever().addActionListener(new BtInscricao());
    }
    
    public void listar(String str){
        MonitoriaDAO dao = new MonitoriaDAO();
        monitorias = dao.consultarMonitoria(str,2);
        System.out.println(monitorias.size());
        
        tela.getTabela().setModel(new MonitoriaTableModel(monitorias));
    }
    
    class PesquisaAutomatica extends KeyAdapter{
        public void keyReleased(KeyEvent ke){
            listar(tela.getCpPesquisa().getText());
        }
    }
    
    class BtInscricao implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            tela.getTabela().setModel(new AgendamentoTableModel(monitorias, check));
        }
        
    }
    
}
