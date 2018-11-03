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
import view.tableModels.InscricaoTableModel;
import view.AlunoGerenciarAgendamentoTela;
import view.tableModels.MonitoriaDisponivelTableModel;

/**
 *
 * @author Izaltino
 */
public class AlunoGerenciarAgendamentoControle {
    private Aluno aluno;
    private final AlunoGerenciarAgendamentoTela tela;
    private Vector<Monitoria> monitorias;
    private final AcaoBtVerIncricoes averins = new AcaoBtVerIncricoes();
    private final AcaoBtInscrever ains = new AcaoBtInscrever();
    private final AcaoBtVoltarInscrever avolins = new AcaoBtVoltarInscrever();
    private final AcaoBtVoltarVerIncricoes avolver = new AcaoBtVoltarVerIncricoes();
    private final AcaoBtSalvar asal = new AcaoBtSalvar();
    private final AcaoBtSalvarAlterar asalalt = new AcaoBtSalvarAlterar();
    private final AcaoBtAlterar aalt = new AcaoBtAlterar();
    private final AcaoBtVoltarAlterar avolalt = new AcaoBtVoltarAlterar();
    private List<Monitoria> monitoriasSelecionadas = new ArrayList<>();

    public AlunoGerenciarAgendamentoControle (Aluno aluno, AlunoGerenciarAgendamentoTela tela) {
        this.tela = tela;
        this.aluno = aluno;
        preencherComboMateria();
        listar(tela.getCbMateria().getSelectedItem().toString());
        tela.getBtEsquerda().addActionListener(ains);
        tela.getCbMateria().addActionListener(new ComboMateria());
        tela.getTabela().addMouseListener(new Acao());
        tela.getBtDireita().addActionListener(averins);
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
            if(tela.getBtEsquerda().getText().equals("Inscrever-se")){
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
                if(tela.getBtEsquerda().getActionListeners()[0] instanceof AcaoBtSalvar){
                    if(tela.getTabela().getSelectedColumn()==4){
                        if(monitoriasSelecionadas.contains(m)){
                            monitoriasSelecionadas.remove(m);
                        }
                        else{
                            if(new MonitoriaDAO().verificaConflito(m, aluno)==0){
                                if(verConfli(m)==null){
                                    System.out.println("Ok");
                                    System.out.println(m.getId());
                                    monitoriasSelecionadas.add(m);
                                }
                                else{
                                    m=verConfli(m);
                                    tela.getTabela().getModel().setValueAt(false, i, 4);
                                    JOptionPane.showMessageDialog(null, "Você não pode se inscrever"+
                                                                     "\nnessa matéria. Há um conflito"+
                                                                     "\nde horário com a monitoria selecionada"+
                                                                     "\n" + m.getMonitor().getMateria()+
                                                                     "-"+m.getDia().getNome()+
                                                                     "-"+m.getHora().getHoraInicio());
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
                else{
                    if(monitoriasSelecionadas.contains(m)){
                        monitoriasSelecionadas.remove(m);
                    }
                    else{
                        monitoriasSelecionadas.add(m);
                    }
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
            tela.getTabela().setModel(new InscricaoTableModel(monitorias));
        }
        
    }
    
    class AcaoBtVoltarInscrever implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("VOLTAR DO INSCREVER");
            tela.getTabela().setModel(new MonitoriaDisponivelTableModel(monitorias));            
            tela.getBtEsquerda().setText("Inscrever");
            tela.getBtEsquerda().removeActionListener(asal);
            tela.getBtEsquerda().addActionListener(ains);
            tela.getBtDireita().setText("Ver meus horários");
            tela.getBtDireita().removeActionListener(avolins);
            tela.getBtDireita().addActionListener(averins);
            monitoriasSelecionadas = new ArrayList<>();
        }
        
    }
    
    class AcaoBtVerIncricoes implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("VER");
            MonitoriaDAO dao = new MonitoriaDAO();
            Materia m = (Materia)tela.getCbMateria().getSelectedItem();
            tela.getBtDireita().setText("Voltar");
            tela.getBtDireita().removeActionListener(averins);
            tela.getBtDireita().addActionListener(avolver);
            tela.getBtEsquerda().setText("Alterar");
            tela.getBtEsquerda().removeActionListener(ains);
            tela.getBtEsquerda().addActionListener(aalt);
            monitorias = dao.consultarMonitoriasInscrito(m.getNome(),aluno);
            tela.getTabela().setModel(new MonitoriaDisponivelTableModel(monitorias));
        }
        
    }
        
    class AcaoBtVoltarVerIncricoes implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("VOLTAR DO VER");;
            MonitoriaDAO dao = new MonitoriaDAO();
            String str = tela.getCbMateria().getSelectedItem().toString();
            monitorias=dao.consultarMonitoriasDisponiveis(str, aluno);
            tela.getTabela().setModel(new MonitoriaDisponivelTableModel(monitorias));
            tela.getBtDireita().setText("Ver meus horários");
            tela.getBtDireita().removeActionListener(avolver);
            tela.getBtDireita().addActionListener(averins);
            tela.getBtEsquerda().setText("Inscrever-se");
            tela.getBtEsquerda().removeActionListener(aalt);
            tela.getBtEsquerda().addActionListener(ains);
            monitoriasSelecionadas = new ArrayList<>();
        }
        
    }
    
    class AcaoBtSalvar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("SALVAR");
            tela.getCbMateria().setEnabled(true);
            InscricaoDAO daoins = new InscricaoDAO();
            daoins.AcaoSalvarDoAluno(monitoriasSelecionadas, aluno);
            monitoriasSelecionadas = new ArrayList<>();
            String str = tela.getCbMateria().getSelectedItem().toString();
            monitorias=new MonitoriaDAO().consultarMonitoriasDisponiveis(str, aluno);
            tela.getTabela().setModel(new MonitoriaDisponivelTableModel(monitorias));
            tela.getBtEsquerda().setText("Inscrever-se");
            tela.getBtEsquerda().removeActionListener(asal);
            tela.getBtEsquerda().addActionListener(ains);
            tela.getBtDireita().setText("Ver meus horários");
            tela.getBtDireita().removeActionListener(avolins);
            tela.getBtDireita().addActionListener(averins);           
            JOptionPane.showMessageDialog(null,"Salvo com sucesso");
        }
        
    }
    
    class AcaoBtSalvarAlterar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("SALVAR DO ALTERAR");
            tela.getCbMateria().setEnabled(true);
            InscricaoDAO insdao = new InscricaoDAO();
            insdao.AcaoSalvarDoAluno(monitoriasSelecionadas, aluno);
            monitoriasSelecionadas = new ArrayList<>();
            String str = tela.getCbMateria().getSelectedItem().toString();
            monitorias=new MonitoriaDAO().consultarMonitoriasInscrito(str, aluno);
            tela.getTabela().setModel(new MonitoriaDisponivelTableModel(monitorias));
            tela.getBtEsquerda().setText("Alterar");
            tela.getBtEsquerda().removeActionListener(asalalt);
            tela.getBtEsquerda().addActionListener(aalt);
            tela.getBtDireita().setText("Voltar");
            tela.getBtDireita().removeActionListener(avolalt);
            tela.getBtDireita().addActionListener(avolver);
            JOptionPane.showMessageDialog(null,"Salvo com sucesso");
        }
        
    }
    
    class AcaoBtAlterar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("ALTERAR");
            tela.getCbMateria().setEnabled(false);
            tela.getBtEsquerda().setText("Salvar");
            tela.getBtEsquerda().removeActionListener(aalt);
            tela.getBtEsquerda().addActionListener(asalalt);
            tela.getTabela().setModel(new InscricaoTableModel(monitorias));
            tela.getBtDireita().removeActionListener(avolver);
            tela.getBtDireita().setText("Voltar");
            tela.getBtDireita().addActionListener(avolalt);
        }
    }
    
    class AcaoBtVoltarAlterar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("VOLTAR DO ALTERAR");
            tela.getCbMateria().setEnabled(true);
            tela.getBtEsquerda().setText("Alterar");
            tela.getBtEsquerda().removeActionListener(asalalt);
            tela.getBtEsquerda().addActionListener(aalt);
            tela.getTabela().setModel(new MonitoriaDisponivelTableModel(monitorias));
            tela.getBtDireita().removeActionListener(avolalt);
            tela.getBtDireita().addActionListener(avolver);
            monitoriasSelecionadas = new ArrayList<>();
        }
    }
}
