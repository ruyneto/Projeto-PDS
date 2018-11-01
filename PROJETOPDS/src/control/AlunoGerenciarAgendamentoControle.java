/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.InscricaoDAO;
import dao.MateriaDAO;
import dao.MonitoriaDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import model.Aluno;
import model.Materia;
import model.Monitoria;
import view.InscricaoTableModel;
import view.AlunoGerenciarAgendamentoTela;
import view.MonitoriaDisponivelTableModel;

/**
 *
 * @author Izaltino
 */
public class AlunoGerenciarAgendamentoControle {
    private Aluno aluno;
    private AlunoGerenciarAgendamentoTela tela;
    private Vector<Monitoria> monitorias;
    private AcaoBtVerIncricoes aver = new AcaoBtVerIncricoes();
    private AcaoBtInscrever ains = new AcaoBtInscrever();
    private AcaoBtVoltar avolver = new AcaoBtVoltar();
    private List<Monitoria> monitoriasSelecionadas = new ArrayList<>();

    public AlunoGerenciarAgendamentoControle (Aluno aluno, AlunoGerenciarAgendamentoTela tela) {
        this.tela = tela;
        this.aluno = aluno;
        preencherComboMateria();
        listar(tela.getCbMateria().getSelectedItem().toString());
        tela.getBtDireita().addActionListener(new BtInscricao());
        tela.getCbMateria().addActionListener(new ComboMateria());
        tela.getTabela().addMouseListener(new Acao());
        tela.getBtEsquerda().addActionListener(aver);
    }
    
    public void preencherComboMateria(){
        MateriaDAO dao = new MateriaDAO();
        tela.getCbMateria().removeAllItems();
        dao.consultaMateriasAtivas().forEach((m) -> {
            tela.getCbMateria().addItem(m);
        });
    }
    
    public void listar(String str){
        MonitoriaDAO dao = new MonitoriaDAO();
        if(tela.getTabela().getModel() instanceof InscricaoTableModel){
            monitorias = dao.consultarMonitoriasDisponiveis(str, aluno);
            tela.getTabela().setModel(new MonitoriaDisponivelTableModel(monitorias));
        }
        else{
            if(tela.getBtDireita().getText().equals("Inscrever-se")){
                monitorias = dao.consultarMonitoriasDisponiveis(str, aluno);
                tela.getTabela().setModel(new MonitoriaDisponivelTableModel(monitorias));
            }
            else{
                monitorias = dao.consultarMonitoriasInscrito(str, aluno);
                tela.getTabela().setModel(new InscricaoTableModel(monitorias));
            }
        }
        if(monitorias.isEmpty())
            JOptionPane.showMessageDialog(null, "Não há nenhuma monitoria" +
                                                "\npara essa matéria");
    }   
    
    class ComboMateria implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            listar(((Materia)tela.getCbMateria().getSelectedItem()).toString());
        }   
    }
    
    class Acao extends MouseAdapter{
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int i = tela.getTabela().getSelectedRow();
            Monitoria m = monitorias.get(i);
            InscricaoDAO dao = new InscricaoDAO();
            
            if(tela.getTabela().getModel() instanceof InscricaoTableModel){
                if(!monitorias.get(i).isInscrito() && tela.getTabela().getSelectedColumn()==4){
                    dao.removerInscricao(aluno, monitorias.get(i));
                }
                if(new MonitoriaDAO().verificaConflito(monitorias.get(i), aluno)==0){
                    if(monitorias.get(i).isInscrito() && tela.getTabela().getSelectedColumn()==4){
                        dao.inserirInscricao(aluno, monitorias.get(i));
                    }
                }
                else{
                    int num = new MonitoriaDAO().verificaConflito(monitorias.get(i), aluno);
                    MonitoriaDAO n = new MonitoriaDAO();
                    m = n.consultarMonitoria(num);
                    System.out.println(num);
                    tela.getTabela().getModel().setValueAt(false, i, 4);
                    JOptionPane.showMessageDialog(null, "Você não pode se inscrever"+
                                                         "\nnessa matéria. Há um conflito"+
                                                         "\nde horário com a monitoria"+
                                                         "\n" + m.getMonitor().getMateria()+
                                                         "-"+m.getDia().getNome()+
                                                         "-"+m.getHora().getHoraInicio());
                }
            }
        }
    }
    
    class AcaoBtInscrever implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("INSCREVER");
            MonitoriaDAO dao = new MonitoriaDAO();
            tela.getBtEsquerda().setText("Salvar");
            tela.getBtEsquerda().removeActionListener(ains);
            tela.getBtEsquerda().addActionListener(asal);
            tela.getBtDireita().setText("Voltar");
            tela.getBtDireita().removeActionListener(averins);
            tela.getBtDireita().addActionListener(avolins);
            tela.getTabela().setModel(new MonitoriaDisponivelTableModel(monitorias));
        }
        
    }
    
    class AcaoBtVoltarInscrever implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("VOLTAR DO INSCREVER");
            tela.getTabela().setModel(new MonitoriasLivresTableModel(monitorias));            
            tela.getBtEsquerda().setText("Inscrever");
            tela.getBtEsquerda().removeActionListener(asal);
            tela.getBtEsquerda().addActionListener(ains);
            tela.getBtDireita().setText("Ver meus horários");
            tela.getBtDireita().removeActionListener(avolins);
            tela.getBtDireita().addActionListener(averins);
        }
        
    }
    
        class AcaoBtVerIncricoes implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("VER");
            MonitoriaDAO dao = new MonitoriaDAO();
            Materia m = (Materia)tela.getCbMateria().getSelectedItem();
            tela.getBtEsquerda().setText("Alterar");
            tela.getBtEsquerda().removeActionListener(ains);
            tela.getBtEsquerda().addActionListener(aalt);
            tela.getBtDireita().setText("Voltar");
            tela.getBtDireita().removeActionListener(averins);
            tela.getBtDireita().addActionListener(avolver);
            monitorias = dao.consultarMonitoriasInscrito(m.getNome(),aluno);
            tela.getTabela().setModel(new MonitoriaDisponivelTableModel(monitorias));
        }
        
    }
}
