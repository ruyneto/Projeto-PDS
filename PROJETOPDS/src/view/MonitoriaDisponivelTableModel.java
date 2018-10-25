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
public class MonitoriaDisponivelTableModel extends AbstractTableModel{
    List<Monitoria> monitorias;
    
    @Override
    public int getRowCount() {
        return monitorias.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Monitoria m = monitorias.get(i);
        
        switch(i1){
            case 0: return m.getMonitor().toString();
            case 1: return m.getDia().toString();
            case 2: return m.getSala().toString();
            case 3: return m.getHora().toString();
            case 4: return m.getVagas();
        }
        return null;
    }
    
    public String getColumnName(int i){
        switch(i){
            case 0: return "Monitor";
            case 1: return "Dia";
            case 2: return "Sala";
            case 3: return "Horário";
            case 4: return "Vagas";
        }
        return null;
    }

    public MonitoriaDisponivelTableModel(List<Monitoria> monitorias) {
        this.monitorias = monitorias;
    }
    
    public void setValueAt(Object aValue, int rowIndex, int ColumnIndex){
        System.out.println(monitorias.get(rowIndex).isInscrito());
        monitorias.get(rowIndex).setInscrito(!monitorias.get(rowIndex).isInscrito());
        System.out.println(monitorias.get(rowIndex).isInscrito());
    }
       
}
