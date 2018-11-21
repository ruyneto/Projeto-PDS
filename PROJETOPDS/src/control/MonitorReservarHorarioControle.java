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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import model.Inscricao;
import model.Monitor;
import model.Monitoria;
import view.AlunosInscritosTela;
import view.LoginTela;
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
    private MonitorReservarHorarioTela tela;
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
        new CabecalhoUsuarioControle(monitor, tela.getCabecalhoUsuarioComponente(), tela);
        preencherComboSala();
        listar(tela.getCbSala().getSelectedItem().toString());
        tela.getBtEsquerda().addActionListener(ains);
        tela.getCbSala().addActionListener(new ComboMateria());
        tela.getTabela().addMouseListener(new AcaoMouseTabela());
        tela.getTabela1().addMouseListener(new AcaoMouseTabela1());
        tela.getBtDireita().addActionListener(averins);
        tela.getjScrollPane2().setVisible(false);
        qtdMonitoriasOfertadas = new MonitoriaDAO().numeroDeMonitorias(monitor);
        tela.setTitle("Horários Livres");
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
            monitorias = dao.consultarMonitoriasLivres(str);
            seletor();
            tela.getTabela().setModel(new MonitoriasLivresTableModel(monitorias));
        }
        if(monitorias.isEmpty())
            JOptionPane.showMessageDialog(null, "Não há nenhuma monitoria" +
                                                "\nlivre para essa sala");
    }
    
    public void seletor(){
        List<Monitoria> tempList = new ArrayList<>();
        if(!monitorias.isEmpty()){
            for(int i=0; i<monitorias.size(); i++){
                for(int i2=0; i2<monitoriasSelecionadas.size(); i2++){
                    Monitoria m = monitorias.get(i);
                    Monitoria m2 = monitoriasSelecionadas.get(i2);
                    if(m.getId() == m2.getId()){
                        tempList.add(m);
                    }
                }
            }
            for(Monitoria d: tempList){
                monitorias.remove(d);
            }
        }
    }
     
    
    class ComboMateria implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            listar((tela.getCbSala().getSelectedItem()).toString());
        }   
    }
    
   
    
    class AcaoMouseTabela extends MouseAdapter{
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
                if(qtdMonitoriasOfertadas!=0 && !monitoriasSelecionadas.isEmpty()){
                    tela.getBtEsquerda().setEnabled(true);
                }
                else{
                    if(monitoriasSelecionadas.size()>=6 ||
                            monitoriasSelecionadas.size()<=12){
                        tela.getBtEsquerda().setEnabled(true);
                    }
                    else{
                        tela.getBtEsquerda().setEnabled(false);
                    }
                }
            }
            
            if(tela.getBtEsquerda().getActionListeners()[0] instanceof AcaoBtAlterar){
                List<Inscricao> inscritos = new InscricaoDAO().consultaAlunosMonitoria(m);
                AlunosInscritosTela tela = new AlunosInscritosTela();
                new AlunosInscritosControle(tela, inscritos, m);
                tela.setVisible(true);
            }
            
            if(tela.getBtEsquerda().getActionListeners()[0] instanceof AcaoBtSalvarAlterar){
                if(tela.getTabela().getSelectedColumn()==4){
                    if(monitoriasSelecionadas.contains(m))
                        monitoriasSelecionadas.remove(m);
                    else
                        monitoriasSelecionadas.add(m);
                }
                if(qtdMonitoriasOfertadas-monitoriasSelecionadas.size()<6){
                    JOptionPane.showMessageDialog(null,"Você precisa de no mínimo"+
                                                        "\n6 horários reservados!");
                    tela.getBtEsquerda().setEnabled(false);
                }
                else{
                    if(qtdMonitoriasOfertadas+monitoriasSelecionadas.size()>12){
                        JOptionPane.showMessageDialog(null,"Você só pode reservar no"+
                                                        "\nmáximo 12 horários!");
                        tela.getBtEsquerda().setEnabled(false);
                    }
                    if(!monitoriasSelecionadas.isEmpty())
                        tela.getBtEsquerda().setEnabled(true);
                }
            }
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
    
    class AcaoMouseTabela1 extends MouseAdapter{
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int i = tela.getTabela1().getSelectedRow();
            Monitoria m = monitoriasSelecionadas.get(i);
            monitoriasSelecionadas.remove(m);
            monitorias.add(m);
            tela.getTabela().setModel(new MonitoriasLivresTableModel(monitorias));
            tela.getTabela1().setModel(new HorariosSelecionadosTableModel(monitoriasSelecionadas));
            if(monitoriasSelecionadas.isEmpty()){
                tela.getBtEsquerda().setEnabled(false);
            }
            else{
                if(qtdMonitoriasOfertadas==0 &&
                        monitoriasSelecionadas.size()<6){
                    tela.getBtEsquerda().setEnabled(false);
                }
            }
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
            tela.setTitle("Meus Horários");
        }
        
    }
    
    class AcaoBtVoltarVerReservas implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("VOLTAR DO VER");
            tela.getCpSala().setVisible(true);
            MonitoriaDAO dao = new MonitoriaDAO();
            String str = tela.getCbSala().getSelectedItem().toString();
            monitorias=dao.consultarMonitoriasLivres(str);
            tela.getTabela().setModel(new MonitoriasLivresTableModel(monitorias));
            tela.getBtEsquerda().setText("Reservar horário");
            tela.getBtEsquerda().removeActionListener(aalt);
            tela.getBtEsquerda().addActionListener(ains);
            tela.getBtDireita().setText("Meus horários");
            tela.getBtDireita().removeActionListener(avolver);
            tela.getBtDireita().addActionListener(averins);
            tela.setTitle("Horários Livres");
        }
        
    }
    
    class AcaoBtVoltarReservar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("VOLTAR DO INSCREVER");
            monitoriasSelecionadas.clear();
            tela.getjScrollPane2().setVisible(false);
            tela.getCbSala().setEnabled(true);
            MonitoriaDAO dao = new MonitoriaDAO();
            String str = tela.getCbSala().getSelectedItem().toString();
            monitorias=dao.consultarMonitoriasLivres(str);
            tela.getTabela().setModel(new MonitoriasLivresTableModel(monitorias));            
            tela.getBtEsquerda().setText("Reservar horário");
            tela.getBtEsquerda().removeActionListener(asal);
            tela.getBtEsquerda().addActionListener(ains);
            tela.getBtEsquerda().setEnabled(true);
            tela.getBtDireita().setText("Meus horários");
            tela.getBtDireita().removeActionListener(avolins);
            tela.getBtDireita().addActionListener(averins);
            tela.setTitle("Horários Livres");
            tela.pack();
        }
        
    }
    
    class AcaoBtReservar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("INSCREVER");
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
            tela.setTitle("Reservar Horários");
            tela.pack();
        }
    }
    
    class AcaoBtSalvar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("SALVAR");
            tela.getCbSala().setEnabled(true);
            tela.getjScrollPane2().setVisible(false);
            MonitoriaDAO dao = new MonitoriaDAO();
            dao.acaoSalvarDoMonitor(monitoriasSelecionadas, monitor);
            monitoriasSelecionadas.clear();
            String str = tela.getCbSala().getSelectedItem().toString();
            monitorias=dao.consultarMonitoriasLivres(str);
            tela.getTabela().setModel(new MonitoriasLivresTableModel(monitorias));
            tela.getBtDireita().setText("Meus horários");
            tela.getBtDireita().removeActionListener(avolins);
            tela.getBtDireita().addActionListener(averins);
            tela.getBtEsquerda().setEnabled(true);
            tela.getBtEsquerda().setText("Reservar horário");
            tela.getBtEsquerda().removeActionListener(asal);
            tela.getBtEsquerda().addActionListener(ains);           
            JOptionPane.showMessageDialog(null,"Salvo com sucesso");
            tela.setTitle("Horários Livres");
            tela.pack();
        }
        
    }
    
    class AcaoBtSalvarAlterar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("SALVAR DO ALTERAR");
            tela.getCbSala().setEnabled(true);
            MonitoriaDAO dao = new MonitoriaDAO();
            dao.acaoAlterarDoMonitor(monitoriasSelecionadas, monitor);
            monitoriasSelecionadas.clear();
            String str = tela.getCbSala().getSelectedItem().toString();
            monitorias=dao.consultarMonitoriasMonitor(str, monitor);
            tela.getTabela().setModel(new MonitoriasLivresTableModel(monitorias));
            tela.getBtDireita().setText("Voltar");
            tela.getBtDireita().removeActionListener(avolalt);
            tela.getBtDireita().addActionListener(avolver);
            tela.getBtEsquerda().setText("Alterar");
            tela.getBtEsquerda().removeActionListener(asalalt);
            tela.getBtEsquerda().addActionListener(aalt);
            tela.setTitle("Meus Horários");
        }
        
    }
    
    class AcaoBtAlterar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("ALTERAR");
            tela.getCbSala().setEnabled(false);
            tela.getBtEsquerda().setEnabled(false);
            tela.getBtEsquerda().setText("Salvar");
            tela.getBtEsquerda().removeActionListener(aalt);
            tela.getBtEsquerda().addActionListener(asalalt);
            tela.getTabela().setModel(new AgendamentoTableModel(monitorias));
            tela.getBtDireita().removeActionListener(avolver);
            tela.getBtDireita().setText("Voltar");
            tela.getBtDireita().addActionListener(avolalt);
            tela.setTitle("Cancelar Reserva de Horários");
        }
    }
    
    class AcaoBtVoltarAlterar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("VOLTAR DO ALTERAR");
            monitoriasSelecionadas.clear();
            tela.getCbSala().setEnabled(true);
            tela.getBtEsquerda().setText("Alterar");
            tela.getBtEsquerda().removeActionListener(asalalt);
            tela.getBtEsquerda().addActionListener(aalt);
            tela.getBtEsquerda().setEnabled(true);
            tela.getTabela().setModel(new MonitoriasLivresTableModel(monitorias));
            tela.getBtDireita().removeActionListener(avolalt);
            tela.getBtDireita().addActionListener(avolver);
            tela.setTitle("Meus Horários");
        }
    }
}
