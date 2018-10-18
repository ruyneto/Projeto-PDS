/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.InscricaoDAO;
import dao.MonitoriaDAO;
import dao.SalaDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Vector;
import javax.swing.JOptionPane;
import model.Materia;
import model.Monitor;
import model.Monitoria;
import model.Sala;
import view.AgendamentoTableModel;
import view.MonitorVisualizarTela;
import view.MonitoriaTableModel;

/**
 *
 * @author Izaltino
 */
public class MonitorPesquisaMonitoriaControle {
    private Monitor monitor;
    private MonitorVisualizarTela tela;
    private Vector<Monitoria> monitorias;
    private AcaoBtVoltar av = new AcaoBtVoltar();
    private AcaoBtVerInscricoes avi = new AcaoBtVerInscricoes();
    private AcaoBtInscricao ains = new AcaoBtInscricao();
    private AcaoBtSalvar asal = new AcaoBtSalvar();

    public MonitorPesquisaMonitoriaControle (Monitor monitor, MonitorVisualizarTela tela) {
        this.tela = tela;
        this.monitor = monitor;
        preencherComboSala();
        listar(tela.getCbSala().getSelectedItem().toString());
        tela.getBtInscrever().addActionListener(new AcaoBtInscricao());
        tela.getCbSala().addActionListener(new ComboMateria());
        tela.getTabela().addMouseListener(new Acao());
        tela.getBtVerInscricoes().addActionListener(avi);
    }
    
    public void preencherComboSala(){
        SalaDAO dao = new SalaDAO();
        tela.getCbSala().removeAllItems();
        dao.consultarSala("_").forEach((m) -> {
            tela.getCbSala().addItem(m);
        });
    }
    
    public void listar(String str){
        MonitoriaDAO dao = new MonitoriaDAO();
        if(tela.getTabela().getModel() instanceof AgendamentoTableModel){
            monitorias = dao.consultarMonitoriasLivre(str);
            tela.getTabela().setModel(new AgendamentoTableModel(monitorias));
        }
        else{
            if(tela.getBtInscrever().isVisible()){
                monitorias = dao.consultarMonitoriasLivre(str);
                tela.getTabela().setModel(new MonitoriaTableModel(monitorias));
            }
            else{
                /*monitorias = dao.consultarMonitoria(str, 3);
                tela.getTabela().setModel(new MonitoriaTableModel(monitorias));*/
            }
        }
        if(monitorias.isEmpty())
            JOptionPane.showMessageDialog(null, "Não há nenhuma monitoria" +
                                                "\nlivre para essa sala");
    }
    
    class AcaoBtInscricao implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            tela.getBtInscrever().setText("Salvar");
            tela.getTabela().setModel(new AgendamentoTableModel(monitorias));
            tela.getBtVerInscricoes().removeActionListener(avi);
            tela.getBtVerInscricoes().setText("Voltar");
            tela.getBtVerInscricoes().addActionListener(av);
            tela.getBtInscrever().removeActionListener(this);
            tela.getBtInscrever().addActionListener(asal);
        }
    }
    
    
    class ComboMateria implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            listar((tela.getCbSala().getSelectedItem()).toString());
        }   
    }
    
    class Acao extends MouseAdapter{
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int i = tela.getTabela().getSelectedRow();
            Monitoria m = monitorias.get(i);
            InscricaoDAO dao = new InscricaoDAO();
            
            /*if(tela.getTabela().getModel() instanceof AgendamentoTableModel){
                if(!monitorias.get(i).isInscrito() && tela.getTabela().getSelectedColumn()==5){
                    dao.removerMonitor(monitor, monitorias.get(i));
                }
                if(new MonitoriaDAO().verificaConflitoMonitor(monitorias.get(i), monitor)==0){
                    if(monitorias.get(i).isInscrito() && tela.getTabela().getSelectedColumn()==5){
                        dao.inserirMonitor(monitor, monitorias.get(i));
                    }
                }
                else{
                    tela.getTabela().getModel().setValueAt(false, i, 5);
                    JOptionPane.showMessageDialog(null, "Você não pode se inscrever"+
                                                         "\nnessa matéria. Há um conflito"+
                                                         "\nde horário com a monitoria"+
                                                         "\n" + m.getMateria().getNome()+
                                                         "-"+m.getDia().getNome()+
                                                         "-"+m.getHora().getHoraInicio());
                }
            }*/
        }
    }
    
    class AcaoBtVerInscricoes implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            tela.getBtVerInscricoes().setText("Voltar");
            tela.getBtVerInscricoes().removeActionListener(this);
            tela.getBtVerInscricoes().addActionListener(av);
            tela.getBtInscrever().setText("Alterar");
            String str = tela.getCbSala().getSelectedItem().toString();
            MonitoriaDAO dao = new MonitoriaDAO();
            monitorias = dao.consultarMonitoriasMonitor(str, monitor);
            tela.getTabela().setModel(new MonitoriaTableModel(monitorias));
        }
        
    }
    
    class AcaoBtVoltar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            MonitoriaDAO dao = new MonitoriaDAO();
            String str = tela.getCbSala().getSelectedItem().toString();
            monitorias=dao.consultarMonitoriasLivre(str);
            tela.getTabela().setModel(new MonitoriaTableModel(monitorias));
            tela.getBtVerInscricoes().setText("Ver Inscrições");
            tela.getBtInscrever().setText("Inscrever-se");
            tela.getBtVerInscricoes().removeActionListener(this);
            tela.getBtVerInscricoes().addActionListener(avi);
        }
        
    }
    
    class AcaoBtSalvar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            MonitoriaDAO dao = new MonitoriaDAO();
            dao.AcaoSalvarDoMonitor(monitorias, monitor);
            String str = tela.getCbSala().getSelectedItem().toString();
            monitorias=dao.consultarMonitoriasLivre(str);
            tela.getTabela().setModel(new MonitoriaTableModel(monitorias));
            tela.getBtVerInscricoes().setText("Ver Inscrições");
            tela.getBtVerInscricoes().removeActionListener(av);
            tela.getBtVerInscricoes().addActionListener(avi);
            tela.getBtInscrever().setText("Inscrever-se");
            tela.getBtInscrever().removeActionListener(asal);
            tela.getBtInscrever().addActionListener(ains);
        }
        
    }
}
