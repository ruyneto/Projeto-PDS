/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.MateriaDAO;
import dao.SalaDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Materia;
import model.Sala;
import util.Alert;
import view.CoordenadorAlterarMateriasTela;
import view.CoordenadorAlterarSalasTela;

/**
 *
 * @author Izaltino
 */
public class CoordenadorAlterarSalasControle {

    private CoordenadorAlterarSalasTela tela;
    private Sala sala;

    public CoordenadorAlterarSalasControle(CoordenadorAlterarSalasTela tela, Sala sala) {
        this.tela = tela;
        this.sala = sala;
        this.tela.getCpNome().setText(sala.getNome());
        

        if(sala.getAtiva()==1) {
            this.tela.getBtAtiva().setSelected(true);
            this.tela.getBtAtiva().setText("SIM");

        }
        else {
            this.tela.getBtAtiva().setSelected(false);
            this.tela.getBtAtiva().setText("NÃO");
        
        }
        this.tela.getBtSalvar().addActionListener(new listenerBtSalvar());
        this.tela.getBtAtiva().addActionListener(new listenerBtAtiva());
        
    }
    
    class listenerBtAtiva implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(tela.getBtAtiva().isSelected()) tela.getBtAtiva().setText("SIM");
            else tela.getBtAtiva().setText("NÃO");

            
        }
    
    }
    
    class listenerBtSalvar implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
          sala.setNome(tela.getCpNome().getText());
          if(tela.getBtAtiva().isSelected()) sala.setAtiva(1);
          else sala.setAtiva(0);
          
          SalaDAO dao = new SalaDAO();
            try {
                dao.alterarSala(sala);
                Alert.salaAlterarSucesso(sala);
                tela.dispose();
            } catch (Exception ex) {
                System.out.println("Erro ao inserir!!");
            }
          }
    }
}
