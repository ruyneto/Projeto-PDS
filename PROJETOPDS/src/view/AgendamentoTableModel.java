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
public class AgendamentoTableModel extends AbstractTableModel{
    List<Monitoria> monitorias;
    Boolean[] check;

    @Override
    public int getRowCount() {
        return monitorias.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Monitoria m = monitorias.get(i);
        
        switch(i1){
            case 0: return (m.getMonitor().getCpf().equals(""))? null: m.getMonitor().toString();
            case 1: return (m.getMateria().getId()==1)? null: m.getMateria().toString();
            case 2: return m.getDia().toString();
            case 3: return m.getSala().toString();
            case 4: return m.getHora().toString();
            case 5: return check[i];
        }
        return null;
    }
    
    public String getColumnName(int i){
        switch(i){
            case 0: return "Monitor";
            case 1: return "Matéria";
            case 2: return "Dia";
            case 3: return "Sala";
            case 4: return "Horário";
            case 5: return "Inscrito ?";
        }
        return null;
    }
    
    public Class getColumnClass(int i){
        if(i==5)return Boolean.class;
        return String.class;
    }
    
    public boolean isCellEditable(int i, int i1){
        if(i1==5)return true;
        return false;
    }

    public AgendamentoTableModel(List<Monitoria> monitorias, Boolean[] check) {
        this.monitorias = monitorias;
        this.check = check;
    }
    
    public void setValueAt(Object aValue, int rowIndex, int ColumnIndex){
        System.out.println(rowIndex+", "+ColumnIndex);
        System.out.println(check[rowIndex]);
        check[rowIndex]=(ColumnIndex==5 && check[rowIndex].booleanValue()==Boolean.FALSE)?Boolean.TRUE:Boolean.FALSE;
        System.out.println(check[rowIndex]);
        System.out.println(check[rowIndex]);
        fireTableCellUpdated(rowIndex, ColumnIndex);
    }
       
}
