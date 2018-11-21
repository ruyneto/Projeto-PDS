/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.InscricaoDAO;
import dao.MonitoriaDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import model.Coordenador;
import model.Inscricao;
import model.Monitoria;
import mvc.CoordenadorRelatorioMVC;
import view.AlunosInscritosTela;
import view.CoordenadorCadastrarMonitoriaTela;
import view.CoordenadorGerenciarMonitoresTela;
import view.CoordenadorPesquisarMonitoriaTela;
import view.CoordenadorRelatorioTela;
import view.CoordenadorVisualizarMateriasTela;
import view.CoordenadorVisualizarSalasTela;
import view.tableModels.MonitoriaTableModel;

/**
 *
 * @author Izaltino
 */
public class CoordenadorPesquisarMonitoriaControle {
    private CoordenadorPesquisarMonitoriaTela tela;
    private Monitoria modelo;
    private Vector<Monitoria> monitorias;
    private Coordenador coordenador;
    
    
    
    public CoordenadorPesquisarMonitoriaControle(CoordenadorPesquisarMonitoriaTela tela, Monitoria modelo, Coordenador coordenador){
        this.tela = tela;
        this.modelo = modelo;
        this.coordenador = coordenador;
        new CabecalhoUsuarioControle(coordenador, tela.getUsuarioComponente(), tela);
        
        listar("_");
        tela.addWindowFocusListener(new GanedFocus());
        tela.getTabela().addMouseListener(new MouseListenerTabela());
        tela.getBtPesquisar().addActionListener(new BtPesquisar());
        tela.getBtCadastar().addActionListener(new BtCadastrar());
        tela.getBtExcluir().addActionListener(new BtExcluir());
        tela.getBtExcluir().setEnabled(false);
        tela.getBtDetalhar().addActionListener(new BtDetalhar());
        tela.getBtDetalhar().setEnabled(false);
        tela.getBtGerenciarMaterias().addActionListener(new listenerBtGerenciarMaterias());
        tela.getBtGerenciarMonitor().addActionListener(new listenerBtGerenciarMonitores());
        tela.getBtGerenciarSalas().addActionListener(new listenerBtGerenciarSalas());
        tela.getBtRelatórios().addActionListener(new listenerBtRelatorios());
        
    }
    
    public CoordenadorPesquisarMonitoriaControle(CoordenadorPesquisarMonitoriaTela tela, Monitoria modelo){
    this(tela,modelo, null);
    }
    public void listar(String str){
        MonitoriaDAO dao = new MonitoriaDAO();
        monitorias = dao.consultarMonitoriaCoord(str);
        System.out.println(monitorias.size());
        
        tela.getTabela().setModel(new MonitoriaTableModel(monitorias));
    }
    
    class BtDetalhar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = tela.getTabela().getSelectedRow();
            Monitoria m = monitorias.get(i);
            List<Inscricao> inscritos = new InscricaoDAO().consultaAlunosMonitoria(m);
            AlunosInscritosTela tela2 = new AlunosInscritosTela();
            new AlunosInscritosControle(tela2, inscritos, m);
            tela2.setLocationRelativeTo(tela);
            tela2.setVisible(true);
        }
        
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
                tela.getBtDetalhar().setEnabled(true);
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

    class listenerBtGerenciarMonitores implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            tela.dispose();
            CoordenadorGerenciarMonitoresTela tela = new CoordenadorGerenciarMonitoresTela();
        CoordenadorGerenciarMonitoresControle controle = new CoordenadorGerenciarMonitoresControle(tela,coordenador);
        tela.setVisible(true);
        }
    
    }
    
    class listenerBtGerenciarMaterias implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
           tela.dispose();
            CoordenadorVisualizarMateriasTela tela = new CoordenadorVisualizarMateriasTela();
       CoordenadorVisualizarMateriasControle controle = new CoordenadorVisualizarMateriasControle(tela, coordenador);
       tela.setVisible(true);  
        }
    
    }
    
    class listenerBtGerenciarSalas implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            tela.dispose();
            CoordenadorVisualizarSalasTela tela = new CoordenadorVisualizarSalasTela();
       CoordenadorVisualizarSalasControle controle = new CoordenadorVisualizarSalasControle(tela, coordenador);
       tela.setVisible(true);
        }
    
    }
    class listenerBtRelatorios implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
        
           CoordenadorRelatorioMVC.main(null);
        }
    
    }
}
