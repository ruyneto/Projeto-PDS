/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import model.Monitor;

/**
 *
 * @author sandr
 */
public class MonitorCellEditor extends AbstractCellEditor
                implements TableCellEditor, ActionListener{
    
    private Monitor monitor;
    private List<Monitor> monitores;

    public MonitorCellEditor(List<Monitor> monitores) {
        this.monitores = monitores;
    }
    
    @Override
    public Object getCellEditorValue() {
        return monitor;
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int i, int i1) {
        if(o instanceof Monitor){
            monitor = (Monitor)o;
        }
        JComboBox<Monitor> comboMonitor = new JComboBox<Monitor>();
        
        for(Monitor m: monitores){
            comboMonitor.addItem(m);
        }
        comboMonitor.setSelectedItem(monitor);
        comboMonitor.addActionListener(this);
        
        if(bln)
            comboMonitor.setBackground(jtable.getSelectionBackground());
        else
            comboMonitor.setBackground(jtable.getSelectionForeground());
        return comboMonitor;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JComboBox<Monitor> comboMonitor = (JComboBox<Monitor>) ae.getSource();
        monitor = (Monitor)comboMonitor.getSelectedItem();
    }
    
}
