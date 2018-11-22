/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.MonitoriaDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import relatorio.Gerador;
import view.CoordenadorRelatorioTela;

/**
 *
 * @author sandr
 */
public class CoordenadorRelatorioControle {
    
    CoordenadorRelatorioTela tela;
    
    public CoordenadorRelatorioControle(CoordenadorRelatorioTela tela){
        this.tela = tela;
        preencherComboPeriodo();
        tela.getBtGerar().addActionListener(new BtGerar());
    }
    
    public void preencherComboPeriodo(){
        MonitoriaDAO dao = new MonitoriaDAO();
        tela.getCbPeriodo().removeAllItems();
        for(String str: dao.periodo()){
            tela.getCbPeriodo().addItem(str);
        }
        if(dao.periodo().isEmpty()){
            tela.getBtGerar().setEnabled(false);
        }
    }
    
    class BtGerar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Gerador.gerarRelatorio((String)tela.getCbPeriodo().getSelectedItem());
        }
        
    }
}
