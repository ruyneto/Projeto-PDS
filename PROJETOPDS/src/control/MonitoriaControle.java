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
import model.Horario;
import model.Monitoria;
import view.MonitoriaCadastrarTela;
import view.MonitoriaPesquisaTela;
import view.MonitoriaTableModel;

/**
 *
 * @author Izaltino
 */
public class MonitoriaControle {
    private MonitoriaPesquisaTela tela;
    private Monitoria modelo;
    private Vector<Monitoria> monitorias;

    public MonitoriaControle(MonitoriaPesquisaTela tela, Monitoria modelo) {
        this.tela = tela;
        this.modelo = modelo;
        listar("_");
        tela.getCpPesquisa().addKeyListener(new PesquisaAutomatica());
        tela.getBtCadastar().addActionListener(new BtSalvar());
    }
    
    public void listar(String str){
        MonitoriaDAO dao = new MonitoriaDAO();
        monitorias = dao.consultarMonitoria(str);
        Vector linhas = new Vector();
        
        for(Monitoria p: monitorias){
            Vector linha = new Vector();
            linha.add(p.getMateria());
            linha.add(p.getSala());
            linha.add(p.getDia());
            linha.add(p.getHora());
            linhas.add(linha);
        }
        
        Vector colunas = new Vector();
        colunas.add("Matéria");
        colunas.add("Sala");
        colunas.add("Dia");
        colunas.add("Hora");
        
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
            new MonitoriaCadastroControle(new MonitoriaCadastrarTela());
        }
        
    }
    
}
