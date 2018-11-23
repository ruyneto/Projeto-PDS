/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.tableModels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Monitoria;

/**
 *
 * @author sandr
 */
public class MonitoriasLivresTableModel extends AbstractTableModel{
    List<Monitoria> monitorias;

    @Override
    public int getRowCount() {
        return monitorias.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Monitoria m = monitorias.get(i);
        switch(i1){
            case 0: return m.getDia().toString();
            case 1: return m.getHora().toString();
            case 2: return m.getVagas();
        }
        return null;
    }
    
    @Override
    public String getColumnName(int i){
        switch(i){
            case 0: return "Dia";
            case 1: return "Horário";
            case 2: return "Vagas";
        }
        return null;
    }

    public MonitoriasLivresTableModel(List<Monitoria> monitorias) {
        this.monitorias = monitorias;
    }
       
}
