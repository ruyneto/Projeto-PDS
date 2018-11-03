/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.MonitoriaDAO;
import dao.SalaDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import model.Monitor;
import model.Monitoria;
import view.tableModels.AgendamentoTableModel;
import view.MonitorReservarHorarioTela;
import view.tableModels.HorariosSelecionadosTableModel;
import view.tableModels.MonitoriasLivresTableModel;

/**
 *
 * @author Izaltino
 */
public class MonitorReservarHorarioControle {
    private final Monitor monitor;
    private final MonitorReservarHorarioTela tela;
    private Vector<Monitoria> monitorias;
    private List<Monitoria> monitoriasSelecionadas = new ArrayList<>();
    private final AcaoBtAlterar aalt = new AcaoBtAlterar();
    private final AcaoBtVoltarAlterar avolalt = new AcaoBtVoltarAlterar();
    private final AcaoBtVoltarVerReservas avolver = new AcaoBtVoltarVerReservas();
    private final AcaoBtVoltarReservar avolins = new AcaoBtVoltarReservar();
    private final AcaoBtVerReservas averins = new AcaoBtVerReservas();
    private final AcaoBtReservar ains = new AcaoBtReservar();
    private final AcaoBtSalvar asal = new AcaoBtSalvar();
    private final AcaoBtSalvarAlterar asalalt = new AcaoBtSalvarAlterar();
    private int qtdMonitoriasOfertadas;

    public MonitorReservarHorarioControle (Monitor monitor, MonitorReservarHorarioTela tela) {
        this.tela = tela;
        this.monitor = monitor;
        preencherComboSala();
        listar(tela.getCbSala().getSelectedItem().toString());
        tela.getBtEsquerda().addActionListener(ains);
        tela.getCbSala().addActionListener(new ComboMateria());
        tela.getTabela().addMouseListener(new Acao());
        tela.getBtDireita().addActionListener(averins);
        tela.getTabela1().setVisible(false);
        tela.getjScrollPane2().setVisible(false);
        qtdMonitoriasOfertadas = new MonitoriaDAO().numeroDeMonitorias(monitor);
        this.tela.pack();
    }
    
    public void preencherComboSala(){
        SalaDAO dao = new SalaDAO();
        tela.getCbSala().removeAllItems();
        dao.consultarSalasAtivas("_").forEach((m) -> {
            tela.getCbSala().addItem(m);
        });
    }
    
    public void listar(String str){
        MonitoriaDAO dao = new MonitoriaDAO();
        if(tela.getTabela().getModel() instanceof MonitoriasLivresTableModel
                && tela.getBtEsquerda().getText().equals("Alterar")){
            monitorias = dao.consultarMonitoriasMonitor(str, monitor);
            seletor();
            tela.getTabela().setModel(new MonitoriasLivresTableModel(monitorias));
        }
        else{
            monitorias = dao.consultarMonitoriasLivre(str);
            seletor();
            tela.getTabela().setModel(new MonitoriasLivresTableModel(monitorias));
        }
        if(monitorias.isEmpty())
            JOptionPane.showMessageDialog(null, "Não há nenhuma monitoria" +
                                                "\nlivre para essa sala");
    }
    
    public void seletor(){
        for(int i=0; i<monitorias.size(); i++){
            for(int i2=0; i2<monitoriasSelecionadas.size(); i2++){
                Monitoria m = monitorias.get(i);
                Monitoria m2 = monitoriasSelecionadas.get(i2);
                if(m.getId() == m2.getId()){
                    monitorias.remove(m);
                }
            }
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
            
            int num = new MonitoriaDAO().verificaConflitoMonitor(monitorias.get(i), monitor);
            if(tela.getBtEsquerda().getActionListeners()[0] instanceof AcaoBtSalvar){
                if(num==0){
                    if(verConfli(m)==null){
                        monitorias.remove(m);
                        monitoriasSelecionadas.add(m);
                        Collections.sort(monitoriasSelecionadas);
                        tela.getTabela1().setModel(new HorariosSelecionadosTableModel(monitoriasSelecionadas));
                        tela.getTabela().setModel(new MonitoriasLivresTableModel(monitorias));
                    }
                    else{
                        m=verConfli(m);
                        JOptionPane.showMessageDialog(null, "Você não pode efetuar esta reserva"+
                                                         "\nde horário. Há um conflito de horário"+
                                                         "\ncom o local selecionado:"+
                                                         m.getSala().getNome()+
                                                         "-"+m.getDia().getNome()+
                                                         "-"+m.getHora().getHoraInicio());
                    }
                }
                else{
                    m=new MonitoriaDAO().consultarMonitoria(num);
                    JOptionPane.showMessageDialog(null, "Você não pode efetuar esta reserva de horário "+
                                                         "\nHá um conflito de horário com o"+
                                                         "\n local já reservado"+
                                                         m.getSala().getNome()+
                                                         "-"+m.getDia().getNome()+
                                                         "-"+m.getHora().getHoraInicio());
                }
            }
            
            if(qtdMonitoriasOfertadas!=0 && !monitoriasSelecionadas.isEmpty()){
                tela.getBtEsquerda().setEnabled(true);
            }
            else{
                if(monitoriasSelecionadas.size()>=6){
                    tela.getBtEsquerda().setEnabled(true);
                }
                else{
                    tela.getBtEsquerda().setEnabled(false);
                }
            }
            
            
            
            
            
            
            
            
            
            /*int i = tela.getTabela().getSelectedRow();
            Monitoria m;
            
            int num = new MonitoriaDAO().verificaConflitoMonitor(monitorias.get(i), monitor);
            if(num!=0 &&
                tela.getBtEsquerda().getActionListeners()[0] instanceof AcaoBtSalvar){
                m=new MonitoriaDAO().consultarMonitoria(num);
                tela.getTabela().getModel().setValueAt(false, i, 5);
                JOptionPane.showMessageDialog(null, "Você não pode reservar este horário."+
                                                     "\nHá um conflito de horário com a "+
                                                     "\nmonitoria"+
                                                     "\n" + m.getMateria().getNome()+
                                                     "-"+m.getDia().getNome()+
                                                     "-"+m.getHora().getHoraInicio());
            }
            else{
                if(monitorias.get(i).getVagas()<12){
                    tela.getTabela().getModel().setValueAt(false, i, 5);
                    JOptionPane.showMessageDialog(null, "Você não pode cancelar a reserva"+
                                                        "\ndeste horário pois há alunos"+
                                                        "\ninscritos nela. Por favor,"+
                                                        "converse com o Coordenador");
                }
            }*/
        }
        public Monitoria verConfli(Monitoria m){
            for(Monitoria ms: monitoriasSelecionadas){
                String dia = ms.getDia().getNome();
                String hora = ms.getHora().getHoraInicio();
                if(m.getDia().getNome().equals(dia) &&
                        m.getHora().getHoraInicio().equals(hora)){
                    return ms;                    
                }
            }
            return null;
        }
    }
    
    class AcaoBtVerReservas implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("VER");
            tela.getBtDireita().setText("Voltar");
            tela.getBtDireita().removeActionListener(averins);
            tela.getBtDireita().addActionListener(avolver);
            tela.getBtEsquerda().setText("Alterar");
            tela.getBtEsquerda().removeActionListener(ains);
            tela.getBtEsquerda().addActionListener(aalt);
            String str = tela.getCbSala().getSelectedItem().toString();
            MonitoriaDAO dao = new MonitoriaDAO();
            monitorias = dao.consultarMonitoriasMonitor(str, monitor);
            tela.getTabela().setModel(new MonitoriasLivresTableModel(monitorias));
        }
        
    }
    
    class AcaoBtVoltarVerReservas implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("VOLTAR DO VER");
            tela.getCpSala().setVisible(true);
            MonitoriaDAO dao = new MonitoriaDAO();
            String str = tela.getCbSala().getSelectedItem().toString();
            monitorias=dao.consultarMonitoriasLivre(str);
            tela.getTabela().setModel(new MonitoriasLivresTableModel(monitorias));
            tela.getBtEsquerda().setText("Reservar horário");
            tela.getBtEsquerda().removeActionListener(aalt);
            tela.getBtEsquerda().addActionListener(ains);
            tela.getBtDireita().setText("Meus horários");
            tela.getBtDireita().removeActionListener(avolver);
            tela.getBtDireita().addActionListener(averins);
        }
        
    }
    
    class AcaoBtVoltarReservar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("VOLTAR DO INSCREVER");
            monitoriasSelecionadas = new ArrayList<>();
            tela.getjScrollPane2().setVisible(false);
            tela.getCbSala().setEnabled(true);
            MonitoriaDAO dao = new MonitoriaDAO();
            String str = tela.getCbSala().getSelectedItem().toString();
            monitorias=dao.consultarMonitoriasLivre(str);
            tela.getTabela().setModel(new MonitoriasLivresTableModel(monitorias));            
            tela.getBtEsquerda().setText("Reservar horário");
            tela.getBtEsquerda().removeActionListener(asal);
            tela.getBtEsquerda().addActionListener(ains);
            tela.getBtEsquerda().setEnabled(true);
            tela.getBtDireita().setText("Meus horários");
            tela.getBtDireita().removeActionListener(avolins);
            tela.getBtDireita().addActionListener(averins);
            tela.pack();
        }
        
    }
    
    class AcaoBtReservar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("INSCREVER");
            tela.getTabela1().setVisible(true);
            tela.getjScrollPane2().setVisible(true);
            tela.getBtEsquerda().setText("Salvar");
            tela.getBtEsquerda().setEnabled(false);
            tela.getTabela().setModel(new MonitoriasLivresTableModel(monitorias));
            tela.getTabela1().setModel(new HorariosSelecionadosTableModel(monitoriasSelecionadas));
            tela.getBtDireita().removeActionListener(averins);
            tela.getBtDireita().setText("Voltar");
            tela.getBtDireita().addActionListener(avolins);
            tela.getBtEsquerda().removeActionListener(ains);
            tela.getBtEsquerda().addActionListener(asal);
            tela.pack();
        }
    }
    
    class AcaoBtSalvar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("SALVAR");
            tela.getCbSala().setEnabled(true);
            MonitoriaDAO dao = new MonitoriaDAO();
            dao.acaoSalvarDoMonitor(monitoriasSelecionadas, monitor);
            monitoriasSelecionadas = new ArrayList<>();
            String str = tela.getCbSala().getSelectedItem().toString();
            monitorias=dao.consultarMonitoriasLivre(str);
            tela.getTabela().setModel(new MonitoriasLivresTableModel(monitorias));
            tela.getBtDireita().setText("Meus horários");
            tela.getBtDireita().removeActionListener(avolins);
            tela.getBtDireita().addActionListener(averins);
            tela.getBtEsquerda().setEnabled(true);
            tela.getBtEsquerda().setText("Reservar horário");
            tela.getBtEsquerda().removeActionListener(asal);
            tela.getBtEsquerda().addActionListener(ains);           
            JOptionPane.showMessageDialog(null,"Salvo com sucesso");
        }
        
    }
    
    class AcaoBtSalvarAlterar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("SALVAR DO ALTERAR");
            tela.getCbSala().setEnabled(true);
            MonitoriaDAO dao = new MonitoriaDAO();
            dao.acaoAlterarDoMonitor(monitorias, monitor);
            String str = tela.getCbSala().getSelectedItem().toString();
            monitorias=dao.consultarMonitoriasMonitor(str, monitor);
            tela.getTabela().setModel(new MonitoriasLivresTableModel(monitorias));
            tela.getBtDireita().setText("Voltar");
            tela.getBtDireita().removeActionListener(avolalt);
            tela.getBtDireita().addActionListener(avolver);
            tela.getBtEsquerda().setText("Alterar");
            tela.getBtEsquerda().removeActionListener(asalalt);
            tela.getBtEsquerda().addActionListener(aalt);
        }
        
    }
    
    class AcaoBtAlterar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("ALTERAR");
            tela.getCbSala().setEnabled(false);
            tela.getBtEsquerda().setText("Salvar");
            tela.getBtEsquerda().removeActionListener(aalt);
            tela.getBtEsquerda().addActionListener(asalalt);
            tela.getTabela().setModel(new AgendamentoTableModel(monitorias));
            tela.getBtDireita().removeActionListener(avolver);
            tela.getBtDireita().setText("Voltar");
            tela.getBtDireita().addActionListener(avolalt);
        }
    }
    
    class AcaoBtVoltarAlterar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("VOLTAR DO ALTERAR");
            tela.getCbSala().setEnabled(true);
            tela.getBtEsquerda().setText("Alterar");
            tela.getBtEsquerda().removeActionListener(asalalt);
            tela.getBtEsquerda().addActionListener(aalt);
            tela.getTabela().setModel(new MonitoriasLivresTableModel(monitorias));
            tela.getBtDireita().removeActionListener(avolalt);
            tela.getBtDireita().addActionListener(avolver);
        }
    }
}
