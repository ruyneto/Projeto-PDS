/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.AlunoDAO;
import dao.MateriaDAO;
import dao.MonitorDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Aluno;
import model.Coordenador;
import model.Materia;
import model.Monitor;
import model.Monitoria;
import view.CoordenadorGerenciarMonitoresTela;
import view.CoordenadorPesquisarMonitoriaTela;
import view.tableModels.MonitorTableModel;

/**
 *
 * @author sandr
 */
public class CoordenadorGerenciarMonitoresControle {
    private CoordenadorGerenciarMonitoresTela tela;
    private List<Monitor> monitores;
    private Coordenador coordenador;
    
    public CoordenadorGerenciarMonitoresControle(CoordenadorGerenciarMonitoresTela tela, Coordenador coordenador){
        this.tela = tela;
        this.coordenador = coordenador;
        new CabecalhoUsuarioControle(coordenador, tela.getUsuarioComponente(), tela);
        preencherComboAluno();
        preencherComboMateria();
        listar("_");
        tela.getBtCadastrar().addActionListener(new BtCadastrar());
        tela.getBtInativar().addActionListener(new BtInativar());
        tela.getCpMonitor().addKeyListener(new Pesquisa());
        tela.getBtVoltar().addActionListener(new listenerBtVoltar());
    }
    
    public void preencherComboAluno(){
        AlunoDAO dao = new AlunoDAO();
        tela.getCbAlunos().removeAllItems();
        List<Aluno> alunos = new ArrayList<>();
        alunos = dao.consultaAlunos();
        if(alunos.isEmpty())
            tela.getBtCadastrar().setEnabled(false);
        for(Aluno a:alunos){
            tela.getCbAlunos().addItem(a);
        }
    }
    
    public void preencherComboMateria(){
        MateriaDAO dao = new MateriaDAO();
        tela.getCbMateria().removeAllItems();
        for(Materia m:dao.consultaMateriasAtivas()){
            tela.getCbMateria().addItem(m);
        }
    }
    
    public void listar(String str){
        MonitorDAO dao = new MonitorDAO();
        monitores = dao.consultaMonitores(str);
        tela.getTabela().setModel(new MonitorTableModel(monitores));
    }
    
    class BtCadastrar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            MonitorDAO dao = new MonitorDAO();
            Aluno a = (Aluno)tela.getCbAlunos().getSelectedItem();
            if (dao.validaAluno(a)){
                Materia m = (Materia)tela.getCbMateria().getSelectedItem();
                dao.registrarMonitor(a, m);
                preencherComboAluno();
                listar("_");
            }
            else{
                JOptionPane.showMessageDialog(tela, "Este aluno só poderá ser monitor"
                                                + "\nno próximo semestre");
            }
        }
        
    }
    
    class BtInativar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            int num = tela.getTabela().getSelectedRow();
            if(num!=-1){
                Monitor mon = monitores.get(num);
                MonitorDAO dao = new MonitorDAO();
                String str = "Quer realmente inativar\n"+
                        "este monitor? Ele só\n"+
                        "poderá ser recadastrado\n"+
                        "no próximo semestre!";
                num =
                JOptionPane.showConfirmDialog(null, str, "Excluir", JOptionPane.YES_NO_OPTION);
                if(num==0){
                    dao.inativarMonitor(mon, mon.getMateria());
                    preencherComboAluno();
                    listar("_");
                }
            }
            else{
                String str = "Você precisa selecionar uma\n"
                        + "linha da tabela!";
                JOptionPane.showMessageDialog(null, str);
            }
        }
        
    }
    
    class Pesquisa extends KeyAdapter{
        @Override
        public void keyReleased(KeyEvent evt){
            listar(tela.getCpMonitor().getText());
        }
    }

    class listenerBtVoltar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
          tela.dispose();
                   CoordenadorPesquisarMonitoriaTela view = new CoordenadorPesquisarMonitoriaTela();
                   new CoordenadorPesquisarMonitoriaControle(view, new Monitoria(), coordenador);
                   view.setVisible(true);
        }
        
    }
}
