/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.InscricaoDAO;
import dao.MateriaDAO;
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
import view.AgendamentoTableModel;
import view.MonitorVisualizarTela;
import view.MonitoriaTableModel;

/**
 *
 * @author Izaltino
 */
public class MonitorCadastroMonitoriaControle {
    private Monitor monitor;
    private MonitorVisualizarTela tela;
    private Vector<Monitoria> monitorias;
    private AcaoVoltar av = new AcaoVoltar();
    private AcaoVerIncricoes avi = new AcaoVerIncricoes();

    public MonitorCadastroMonitoriaControle (Monitor monitor, MonitorVisualizarTela tela) {
        this.tela = tela;
        this.monitor = monitor;
        preencherComboSala();
        listar(tela.getCbSala().getSelectedItem().toString());
        tela.getBtInscrever().addActionListener(new BtInscricao());
        tela.getCbSala().addActionListener(new ComboMateria());
        tela.getTabela().addMouseListener(new Acao());
        tela.getBtFinalizar().addActionListener(avi);
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
            /*monitorias = dao.consultarMonitoria(str, 2);
            tela.getTabela().setModel(new AgendamentoTableModel(monitorias));*/
        }
        else{
            if(tela.getBtInscrever().isVisible()){
                /*monitorias = dao.consultarMonitoria(str, 2);
                tela.getTabela().setModel(new MonitoriaTableModel(monitorias));*/
            }
            else{
                /*monitorias = dao.consultarMonitoria(str, 3);
                tela.getTabela().setModel(new MonitoriaTableModel(monitorias));*/
            }
        }
        if(monitorias.isEmpty())
            JOptionPane.showMessageDialog(null, "Não há nenhuma monitoria" +
                                                "\npara essa matéria");
    }
    
    class BtInscricao implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            tela.getBtInscrever().setVisible(false);
            tela.getTabela().setModel(new AgendamentoTableModel(monitorias));
            
            tela.getBtFinalizar().removeActionListener(avi);
            tela.getBtFinalizar().setText("Voltar");
            tela.getBtFinalizar().addActionListener(av);
        }
    }
    
    
    class ComboMateria implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            listar(((Materia)tela.getCbSala().getSelectedItem()).toString());
        }   
    }
    
    class Acao extends MouseAdapter{
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int i = tela.getTabela().getSelectedRow();
            Monitoria m = monitorias.get(i);
            InscricaoDAO dao = new InscricaoDAO();
            
            if(tela.getTabela().getModel() instanceof AgendamentoTableModel){
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
            }
        }
    }
    
    class AcaoVerIncricoes implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            MonitoriaDAO dao = new MonitoriaDAO();
            Materia m = (Materia)tela.getCbSala().getSelectedItem();
            tela.getBtFinalizar().setText("Voltar");
            tela.getBtInscrever().setVisible(false);
            tela.getTabela().setModel(new MonitoriaTableModel(dao.consultarMonitoria(m.getNome(), 3)));
            tela.getBtFinalizar().removeActionListener(this);
            tela.getBtFinalizar().addActionListener(av);
        }
        
    }
    
    class AcaoVoltar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            Materia m = (Materia)tela.getCbSala().getSelectedItem();
            tela.getBtFinalizar().setText("Ver Inscrições");
            tela.getBtInscrever().setVisible(true);
            String str = tela.getCbSala().getSelectedItem().toString();
            MonitoriaDAO dao = new MonitoriaDAO();
            monitorias = dao.consultarMonitoria(str, 2);
            tela.getTabela().setModel(new MonitoriaTableModel(monitorias));
            tela.getBtFinalizar().removeActionListener(this);
            tela.getBtFinalizar().addActionListener(avi);
        }
        
    }
}
