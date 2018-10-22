/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Monitor;

/**
 *
 * @author sandr
 */
public class MonitorTableModel extends AbstractTableModel{
    List<Monitor> monitores;

    @Override
    public int getRowCount() {
        return monitores.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Monitor m = monitores.get(i);
        switch(i1){
            case 0: return m.getNome();
            case 1: return m.getMateria().toString();
        }
        return null;
    }
    
    public String getColumnName(int i){
        switch(i){
            case 0: return "Monitor";
            case 1: return "Matéria";
        }
        return null;
    }

    public MonitorTableModel(List<Monitor> monitorias) {
        this.monitores = monitorias;
    }
       
}
