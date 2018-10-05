/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Monitoria;

/**
 *
 * @author sandr
 */
public class MonitoriaTableModel extends AbstractTableModel{
    List<Monitoria> monitorias;

    @Override
    public int getRowCount() {
        return monitorias.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Monitoria m = monitorias.get(i);
        switch(i1){
            case 0: return m.getMateria().toString();
            case 1: return m.getDia().toString();
            case 2: return m.getSala().toString();
            case 3: return m.getHora().toString();
        }
        return null;
    }
    
    public String getColumnName(int i){
        switch(i){
            case 0: return "Matéria";
            case 1: return "Dia";
            case 2: return "Sala";
            case 3: return "Horário";
        }
        return null;
    }

    public MonitoriaTableModel(List<Monitoria> monitorias) {
        this.monitorias = monitorias;
    }
       
}
