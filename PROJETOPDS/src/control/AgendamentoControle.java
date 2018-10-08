/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.MateriaDAO;
import dao.MonitoriaDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;
import model.Materia;
import model.Monitoria;
import view.AgendamentoTableModel;
import view.AlunoVisuliazarTela;
import view.MonitoriaTableModel;

/**
 *
 * @author Izaltino
 */
public class AgendamentoControle {
    private AlunoVisuliazarTela tela;
    private Vector<Monitoria> monitorias;

    public AgendamentoControle (AlunoVisuliazarTela tela) {
        this.tela = tela;
        listar("_");
        tela.getBtInscrever().addActionListener(new BtInscricao());
        preencherComboMateria();
        tela.getCbMateria().addActionListener(new ComboMateria());
    }
    
    public void preencherComboMateria(){
        MateriaDAO dao = new MateriaDAO();
        tela.getCbMateria().removeAllItems();
        for(Materia m: dao.consultarMateriaAluno()){
            tela.getCbMateria().addItem(m);
        }
    }
    
    public void listar(String str){
        MonitoriaDAO dao = new MonitoriaDAO();
        monitorias = dao.consultarMonitoria(str, 2);
        tela.getTabela().setModel(new MonitoriaTableModel(monitorias));
    }
    
    class BtInscricao implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            tela.getBtInscrever().setVisible(false);
            tela.getTabela().setModel(new AgendamentoTableModel(monitorias));
        }
    }
    
    class ComboMateria implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            listar(((Materia)tela.getCbMateria().getSelectedItem()).toString());
        }
        
    }
    
}
