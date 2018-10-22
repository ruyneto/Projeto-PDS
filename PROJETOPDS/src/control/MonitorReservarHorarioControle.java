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
import java.util.Vector;
import javax.swing.JOptionPane;
import model.Monitor;
import model.Monitoria;
import view.InscricaoTableModel;
import view.MonitorReservarHorarioTela;
import view.MonitoriaTableModel;
import view.ReservarHorarioTableModel;

/**
 *
 * @author Izaltino
 */
public class MonitorReservarHorarioControle {
    private final Monitor monitor;
    private final MonitorReservarHorarioTela tela;
    private Vector<Monitoria> monitorias;
    private final AcaoBtAlterar aalt = new AcaoBtAlterar();
    private final AcaoBtVoltarAlterar avolalt = new AcaoBtVoltarAlterar();
    private final AcaoBtVoltarVerInscricoes avolver = new AcaoBtVoltarVerInscricoes();
    private final AcaoBtVoltarInscrever avolins = new AcaoBtVoltarInscrever();
    private final AcaoBtVerInscricoes averins = new AcaoBtVerInscricoes();
    private final AcaoBtInscricao ains = new AcaoBtInscricao();
    private final AcaoBtSalvar asal = new AcaoBtSalvar();
    private final AcaoBtSalvarAlterar asalalt = new AcaoBtSalvarAlterar();

    public MonitorReservarHorarioControle (Monitor monitor, MonitorReservarHorarioTela tela) {
        this.tela = tela;
        this.monitor = monitor;
        preencherComboSala();
        listar(tela.getCbSala().getSelectedItem().toString());
        tela.getBtInscrever().addActionListener(ains);
        tela.getCbSala().addActionListener(new ComboMateria());
        tela.getTabela().addMouseListener(new Acao());
        tela.getBtVerInscricoes().addActionListener(averins);
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
        if(tela.getTabela().getModel() instanceof MonitoriaTableModel
                && tela.getBtInscrever().getText().equals("Alterar")){
            monitorias = dao.consultarMonitoriasMonitor(str, monitor);
            tela.getTabela().setModel(new ReservarHorarioTableModel(monitorias));
        }
        else{
            monitorias = dao.consultarMonitoriasLivre(str);
            tela.getTabela().setModel(new ReservarHorarioTableModel(monitorias));
        }
        if(monitorias.isEmpty())
            JOptionPane.showMessageDialog(null, "Não há nenhuma monitoria" +
                                                "\nlivre para essa sala");
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
            Monitoria m;
            
            int num = new MonitoriaDAO().verificaConflitoMonitor(monitorias.get(i), monitor);
            System.out.println(num);
            if(num!=0 &&
                tela.getBtInscrever().getActionListeners()[0] instanceof AcaoBtSalvar){
                m=new MonitoriaDAO().consultarMonitoria(num);
                tela.getTabela().getModel().setValueAt(false, i, 5);
                JOptionPane.showMessageDialog(null, "Você não pode reservar este horário."+
                                                     "\nHá um conflito de horário com a "+
                                                     "\nmonitoria"+
                                                     "\n" + m.getMateria().getNome()+
                                                     "-"+m.getDia().getNome()+
                                                     "-"+m.getHora().getHoraInicio());
            }
        }
    }
    
    class AcaoBtVerInscricoes implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("VER");
            tela.getCpSala().setVisible(false);
            tela.getCbSala().setVisible(false);
            tela.getBtVerInscricoes().setText("Voltar");
            tela.getBtVerInscricoes().removeActionListener(averins);
            tela.getBtVerInscricoes().addActionListener(avolver);
            tela.getBtInscrever().setText("Alterar");
            tela.getBtInscrever().removeActionListener(ains);
            tela.getBtInscrever().addActionListener(aalt);
            String str = tela.getCbSala().getSelectedItem().toString();
            MonitoriaDAO dao = new MonitoriaDAO();
            monitorias = dao.consultarMonitoriasMonitor(str, monitor);
            tela.getTabela().setModel(new MonitoriaTableModel(monitorias));
        }
        
    }
    
    class AcaoBtVoltarVerInscricoes implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("VOLTAR DO VER");
            tela.getCbSala().setVisible(true);
            MonitoriaDAO dao = new MonitoriaDAO();
            String str = tela.getCbSala().getSelectedItem().toString();
            monitorias=dao.consultarMonitoriasLivre(str);
            tela.getTabela().setModel(new ReservarHorarioTableModel(monitorias));
            tela.getBtInscrever().setText("Inscrever-se");
            tela.getBtInscrever().removeActionListener(aalt);
            tela.getBtInscrever().addActionListener(ains);
            tela.getBtVerInscricoes().setText("Ver Inscrições");
            tela.getBtVerInscricoes().removeActionListener(avolver);
            tela.getBtVerInscricoes().addActionListener(averins);
        }
        
    }
    
    class AcaoBtVoltarInscrever implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("VOLTAR DO INSCREVER");
            tela.getCpSala().setVisible(true);
            tela.getCbSala().setVisible(true);
            MonitoriaDAO dao = new MonitoriaDAO();
            String str = tela.getCbSala().getSelectedItem().toString();
            monitorias=dao.consultarMonitoriasLivre(str);
            tela.getTabela().setModel(new ReservarHorarioTableModel(monitorias));            
            tela.getBtInscrever().setText("Inscrever-se");
            tela.getBtInscrever().removeActionListener(asal);
            tela.getBtInscrever().addActionListener(ains);
            tela.getBtVerInscricoes().setText("Ver Inscrições");
            tela.getBtVerInscricoes().removeActionListener(avolins);
            tela.getBtVerInscricoes().addActionListener(averins);
        }
        
    }
    
    class AcaoBtInscricao implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("INSCREVER");
            tela.getCpSala().setVisible(false);
            tela.getCbSala().setVisible(false);
            tela.getBtInscrever().setText("Salvar");
            tela.getTabela().setModel(new InscricaoTableModel(monitorias));            
            tela.getBtVerInscricoes().removeActionListener(averins);
            tela.getBtVerInscricoes().setText("Voltar");
            tela.getBtVerInscricoes().addActionListener(avolins);
            tela.getBtInscrever().removeActionListener(ains);
            tela.getBtInscrever().addActionListener(asal);
        }
    }
    
    class AcaoBtSalvar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("SALVAR");
            tela.getCbSala().setVisible(true);
            MonitoriaDAO dao = new MonitoriaDAO();
            dao.AcaoSalvarDoMonitor(monitorias, monitor);
            String str = tela.getCbSala().getSelectedItem().toString();
            monitorias=dao.consultarMonitoriasLivre(str);
            tela.getTabela().setModel(new ReservarHorarioTableModel(monitorias));
            tela.getBtVerInscricoes().setText("Ver Inscrições");
            tela.getBtVerInscricoes().removeActionListener(avolins);
            tela.getBtVerInscricoes().addActionListener(averins);
            tela.getBtInscrever().setText("Inscrever-se");
            tela.getBtInscrever().removeActionListener(asal);
            tela.getBtInscrever().addActionListener(ains);           
            JOptionPane.showMessageDialog(null,"Salvo com sucesso");
        }
        
    }
    
    class AcaoBtSalvarAlterar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("SALVAR DO ALTERAR");
            tela.getCbSala().setVisible(true);
            MonitoriaDAO dao = new MonitoriaDAO();
            dao.AcaoSalvarDoMonitor(monitorias, monitor);
            String str = tela.getCbSala().getSelectedItem().toString();
            monitorias=dao.consultarMonitoriasLivre(str);
            tela.getTabela().setModel(new ReservarHorarioTableModel(monitorias));
            tela.getBtVerInscricoes().setText("Ver Inscrições");
            tela.getBtVerInscricoes().removeActionListener(avolalt);
            tela.getBtVerInscricoes().addActionListener(averins);
            tela.getBtInscrever().setText("Inscrever-se");
            tela.getBtInscrever().removeActionListener(asalalt);
            tela.getBtInscrever().addActionListener(ains);
        }
        
    }
    
    class AcaoBtAlterar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("ALTERAR");
            tela.getCbSala().setVisible(false);
            tela.getBtInscrever().setText("Salvar");
            tela.getBtInscrever().removeActionListener(aalt);
            tela.getBtInscrever().addActionListener(asalalt);
            tela.getTabela().setModel(new InscricaoTableModel(monitorias));
            tela.getBtVerInscricoes().removeActionListener(avolver);
            tela.getBtVerInscricoes().setText("Voltar");
            tela.getBtVerInscricoes().addActionListener(avolalt);
        }
    }
    
    class AcaoBtVoltarAlterar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("VOLTAR DO ALTERAR");
            tela.getBtInscrever().setText("Alterar");
            tela.getBtInscrever().removeActionListener(asalalt);
            tela.getBtInscrever().addActionListener(aalt);
            tela.getTabela().setModel(new MonitoriaTableModel(monitorias));
            tela.getBtVerInscricoes().removeActionListener(avolalt);
            tela.getBtVerInscricoes().addActionListener(avolver);
        }
    }
}
