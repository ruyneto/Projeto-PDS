/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.tableModels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Inscricao;
import model.Monitoria;
import util.DataUtil;

/**
 *
 * @author sandr
 */
public class AlunosTableModel extends AbstractTableModel{
    List<Inscricao> inscritos;

    @Override
    public int getRowCount() {
        return inscritos.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Inscricao ins = inscritos.get(i);
        switch(i1){
            case 0: return ins.getAluno().getNome();
            case 1: return DataUtil.dateToString(ins.getData().getTimeInMillis());
        }
        return null;
    }
    
    public String getColumnName(int i){
        switch(i){
            case 0: return "Nome do Aluno";
            case 1: return "Data da inscrição";
        }
        return null;
    }

    public AlunosTableModel(List<Inscricao> inscritos) {
        this.inscritos = inscritos;
    }
       
}
